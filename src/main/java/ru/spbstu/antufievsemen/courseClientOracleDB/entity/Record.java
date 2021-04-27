package ru.spbstu.antufievsemen.courseClientOracleDB.entity;


import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "journal")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Client client;

    @Column(name = "date_begin")
    private Timestamp dateBeg;

    @Column(name = "date_end")
    private Timestamp dateEnd;

    @Column(name = "date_return")
    private Timestamp dateReturn;

    public Record() {
    }

    public Record(long id, Book book, Client client, Timestamp dateBeg, Timestamp dateReturn) {
        this.id = id;
        this.book = book;
        this.client = client;
        this.dateBeg = dateBeg;
        this.dateEnd = Timestamp.valueOf(dateBeg.
                toLocalDateTime().
                plusDays(this.book.getBookType().getDayCount()));
        this.dateReturn = dateReturn;
    }

    public Record(Book book, Client client, Timestamp dateBeg, Timestamp dateReturn) {
        this.book = book;
        this.client = client;
        this.dateBeg = dateBeg;
        this.dateEnd = Timestamp.valueOf(dateBeg.
                toLocalDateTime().
                plusDays(this.book.getBookType().getDayCount()));
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
