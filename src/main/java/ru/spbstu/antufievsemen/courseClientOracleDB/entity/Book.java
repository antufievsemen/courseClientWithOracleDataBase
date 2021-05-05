package ru.spbstu.antufievsemen.courseClientOracleDB.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    private String name;

    private int count;

    @ManyToOne()
    @JoinColumn(name = "bookType_id")
    private BookType bookType;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<Record> records = new HashSet<>();

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

    public void setId(long id) {
        this.id = id;
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

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", bookType=" + bookType.toString() +
                '}';
    }
}
