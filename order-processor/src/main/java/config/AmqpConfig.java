package config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.DirectChannelSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.messaging.access.intercept.AuthorizationChannelInterceptor;
import org.springframework.security.messaging.context.SecurityContextChannelInterceptor;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.util.Assert;

public class AmqpConfig {

    public static final String QUEUE = "custom-queue";
    public static final String AUTHORIZATION_HEADER_NAME = "jwt";

    @Bean
    IntegrationFlow inboundAmqpRequestsIntegrationFlow(MessageChannel requests, ConnectionFactory connectionFactory) {
        var inboundAmqpAdapter = Amqp
                .inboundAdapter(connectionFactory, QUEUE);
        return IntegrationFlow
                .from(inboundAmqpAdapter)
                .channel(requests)
                .get();
    }

    @Bean
    IntegrationFlow requestsIntegrationFlow(MessageChannel requests) {
        return IntegrationFlow
                .from(requests)//
                .handle((payload, headers) -> {
                    System.out.println("------------------------------------------------------------------------------");
                    System.out.println(payload.toString());
                    headers.forEach((key, value) -> System.out.println(key + '=' + value));
                    return null;
                })//
                .get();
    }

    @Bean
    DirectChannelSpec requests(JwtAuthenticationProvider jwtAuthenticationProvider) {
        return MessageChannels
                .direct()
                .interceptor(
                        new JwtAuthenticationInterceptor(AUTHORIZATION_HEADER_NAME, jwtAuthenticationProvider),
                        new SecurityContextChannelInterceptor(AUTHORIZATION_HEADER_NAME),
                        new AuthorizationChannelInterceptor(AuthenticatedAuthorizationManager.authenticated()));
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    Exchange exchange() {
        return ExchangeBuilder.directExchange(QUEUE).build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(QUEUE).noargs();
    }

    static class JwtAuthenticationInterceptor implements ChannelInterceptor {

        private final JwtAuthenticationProvider authenticationProvider;

        private final String headerName;

        JwtAuthenticationInterceptor(String headerName, JwtAuthenticationProvider ap) {
            this.headerName = headerName;
            this.authenticationProvider = ap;
        }

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            var token = (String) message.getHeaders().get(headerName);
            Assert.hasText(token, "the token must be non-empty!");
            var authentication = this.authenticationProvider.authenticate(new BearerTokenAuthenticationToken(token));
            if (authentication != null && authentication.isAuthenticated()) {
                var upt = UsernamePasswordAuthenticationToken.authenticated(authentication.getName(),
                        null, AuthorityUtils.NO_AUTHORITIES);
                return MessageBuilder
                        .fromMessage(message)
                        .setHeader(headerName, upt)
                        .build();
            }
            return MessageBuilder
                    .fromMessage(message)
                    .setHeader(headerName, null)
                    .build();
        }
    }
}
