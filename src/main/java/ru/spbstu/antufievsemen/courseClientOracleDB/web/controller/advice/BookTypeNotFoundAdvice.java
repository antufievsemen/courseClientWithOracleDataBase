package ru.spbstu.antufievsemen.courseClientOracleDB.web.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.BookTypeNotFoundException;

@ControllerAdvice
public class BookTypeNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(BookTypeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String bookTypeNotFoundException(RuntimeException e) {
        return e.getMessage();
    }
}
