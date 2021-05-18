package ru.spbstu.antufievsemen.courseClientOracleDB.exception;

public class BookTypeNotFoundException extends RuntimeException {
    public BookTypeNotFoundException(String message) {
        super(message);
    }
}
