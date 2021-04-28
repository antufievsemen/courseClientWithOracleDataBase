package ru.spbstu.antufievsemen.courseClientOracleDB.web.controller.rest;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.BookType;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.BookTypeNotFoundException;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.BookTypeService;

@RestController
@RequestMapping("/library/types")
public class RestControllerBookType {

    private final BookTypeService bookTypeService;

    public RestControllerBookType(BookTypeService bookTypeService) {
        this.bookTypeService = bookTypeService;
    }

    @GetMapping
    public List<BookType> getList() {
        return bookTypeService.getBookTypes();
    }

    @GetMapping("/{id}")
    public BookType getOne(@PathVariable long id) {
        return bookTypeService.getBookTypeById(id).
                orElseGet(() -> {throw new BookTypeNotFoundException("book type not found");});
    }

    @PostMapping("/addType")
    public BookType addBookType(@RequestBody BookType bookType) {
        return bookTypeService.addBookType(bookType);
    }

    @PutMapping("/updateType/{id}")
    public BookType updateBooKType(@RequestBody BookType bookType, @PathVariable long id) {
        bookType.setId(id);
        return bookTypeService.updateBooKType(bookType);
    }

    @DeleteMapping("/deleteType/{id}")
    public BookType deleteBookType(@PathVariable long id) {
        return bookTypeService.deleteBookType(id);
    }
}
