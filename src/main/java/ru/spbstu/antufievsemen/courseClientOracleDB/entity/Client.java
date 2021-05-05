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
import javax.persistence.Table;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "passport_seria")
    private String passportSeria;

    @Column(name = "passport_number")
    private String passportNumber;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<Record> records = new HashSet<>();

    public Client() {
    }

    public Client(long id, String firstName, String lastName, String fatherName, String passportSeria, String passportNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.passportSeria = passportSeria;
        this.passportNumber = passportNumber;
    }

    public Client(String firstName, String lastName, String fatherName, String passportSeria, String passportNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.passportSeria = passportSeria;
        this.passportNumber = passportNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getPassportSeria() {
        return passportSeria;
    }

    public void setPassportSeria(String passportSeria) {
        this.passportSeria = passportSeria;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", passportSeria='" + passportSeria + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                '}';
    }
}
