package ru.spbstu.antufievsemen.courseClientOracleDB.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    private String name;

    private int count;

    @ManyToOne
    private BookType bookType;

    public Book() {
    }

    public Book(long id, String name, int count, BookType bookType) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.bookType = bookType;
    }

    public Book(String name, int count, BookType bookType) {
        this.name = name;
        this.count = count;
        this.bookType = bookType;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public void incrementOn(int number) {
        this.count += number;
    }

    public void decrementOn(int number) {
        this.count -= number;
    }
}
