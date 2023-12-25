package com.productdock.authorizationserver.config.keys;

import org.springframework.context.ApplicationEvent;

import java.time.Instant;

public class RsaKeyParGenerationRequestEvent extends ApplicationEvent {
    public RsaKeyParGenerationRequestEvent(Instant instant) {
        super(instant);
    }

    @Override
    public Instant getSource() {
        return (Instant) super.getSource();
    }

}
