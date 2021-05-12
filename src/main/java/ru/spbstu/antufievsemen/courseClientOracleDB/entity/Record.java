package ru.spbstu.antufievsemen.courseClientOracleDB.entity;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "journal")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @ManyToOne()
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "date_begin")
    private Timestamp dateBeg;

    @Column(name = "date_end")
    private Timestamp dateEnd;

    @Column(name = "date_return")
    private Timestamp dateReturn;

    public Record() {
    }

    public Record(Book book, Client client) {
        this.book = book;
        this.client = client;
        this.dateReturn = null;
    }

    @PrePersist
    private void prePersist() {
        this.dateBeg = Timestamp.valueOf(LocalDateTime.now());
        this.dateEnd = Timestamp.valueOf(dateBeg.
                toLocalDateTime().
                plusDays(this.book.getBookType().getDayCount()));
    }


    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", book=" + book.toString() +
                ", client=" + client.toString() +
                ", dateBeg=" + dateBeg +
                ", dateEnd=" + dateEnd +
                ", dateReturn=" + dateReturn +
                '}';
    }
}
