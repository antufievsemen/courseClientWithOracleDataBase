package ru.spbstu.antufievsemen.courseClientOracleDB.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "journal")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @ManyToOne
    @Column(name = "book_id")
    private Book book;

    @ManyToOne
    @Column(name = "client_id")
    private Client client;

    @Column(name = "date_begin")
    private Timestamp dateBeg;

    @Column(name = "date_end")
    private Timestamp dateEnd;

    @Column(name = "date_return")
    private Timestamp dateReturn;

    public Record() {
    }

    public Record(long id, Book book, Client client, Timestamp dateBeg, Timestamp dateEnd, Timestamp dateReturn) {
        this.id = id;
        this.book = book;
        this.client = client;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
        this.dateReturn = dateReturn;
    }

    public Record(Book book, Client client, Timestamp dateBeg, Timestamp dateEnd, Timestamp dateReturn) {
        this.book = book;
        this.client = client;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
        this.dateReturn = dateReturn;
    }

    public long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Timestamp getDateBeg() {
        return dateBeg;
    }

    public void setDateBeg(Timestamp dateBeg) {
        this.dateBeg = dateBeg;
    }

    public Timestamp getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Timestamp getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Timestamp dateReturn) {
        this.dateReturn = dateReturn;
    }
}
