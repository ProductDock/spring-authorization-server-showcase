package com.productdock.resourceserver.book;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
@Getter
public class Book {

    @Id
    private long id;
    private String title;
    private double price;
}
