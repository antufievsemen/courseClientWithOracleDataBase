package ru.spbstu.antufievsemen.courseClientOracleDB.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Entity
@Table(name = "book_types")
@Scope(value= ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BookType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    private String name;

    private int count;

    private int fine;

    @OneToMany(mappedBy = "bookType")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();

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

    @PreRemove
    private void preDelete() {
        for (Book element: books) {
            element.setBookType(null);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void incrementOn(int number) {
        this.count += number;
    }

    public void decrementOn(int number) {
        this.count -= number;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "BookType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", fine=" + fine +
                ", dayCount=" + dayCount +
                '}';
    }
}
