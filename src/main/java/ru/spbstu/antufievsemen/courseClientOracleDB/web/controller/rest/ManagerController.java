package ru.spbstu.antufievsemen.courseClientOracleDB.web.controller.rest;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Book;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.ManagerService;

@RestController
@RequestMapping("/library/manager")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/books/{id}")
    public Integer getCountOfBookByClient(@PathVariable long id) {
        return managerService.countOfBookByClient(id);
    }

    @GetMapping("/largestFine")
    public Integer getLargestFine() {
        return managerService.getLargestFine();
    }

    @GetMapping("/fineByClient/{id}")
    public Integer getFineByClient(@PathVariable long id) {
        return managerService.getFineByClientId(id);
    }

    @GetMapping("/getThreePopularBooks")
    public List<Book> getThreePopularBooks() {
        return managerService.getThreePopularBooks();
    }
}
