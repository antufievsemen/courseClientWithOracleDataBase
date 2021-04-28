package ru.spbstu.antufievsemen.courseClientOracleDB.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
