package ru.spbstu.antufievsemen.courseClientOracleDB.web.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.BookLimitException;

@ControllerAdvice
public class BookLimitAdvice {

    @ExceptionHandler(BookLimitException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String bookLimitHandler(RuntimeException e) {
        return e.getMessage();
    }
}
