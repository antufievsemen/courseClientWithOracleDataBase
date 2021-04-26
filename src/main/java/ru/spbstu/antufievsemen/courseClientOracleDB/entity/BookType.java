package ru.spbstu.antufievsemen.courseClientOracleDB.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book_types")
public class BookType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    private String name;

    private int count;

    private int fine;

    @Column(name = "day_count")
    private int dayCount;

    public BookType() {
    }

    public BookType(long id, String name, int count, int fine, int dayCount) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.fine = fine;
        this.dayCount = dayCount;
    }

    public BookType(String name, int count, int fine, int dayCount) {
        this.name = name;
        this.count = count;
        this.fine = fine;
        this.dayCount = dayCount;
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

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    public void incrementCount() {
        this.count++;
    }
}
