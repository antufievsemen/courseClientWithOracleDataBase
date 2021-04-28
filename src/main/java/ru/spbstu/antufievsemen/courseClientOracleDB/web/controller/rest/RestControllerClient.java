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
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Client;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.ClientNotFoundException;
import ru.spbstu.antufievsemen.courseClientOracleDB.service.ClientService;

@RestController
@RequestMapping("/library/clients")
public class RestControllerClient {

    private final ClientService clientService;

    public RestControllerClient(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getList() {
        return clientService.getClients();
    }

    @GetMapping("/{id}")
    public Client getOne(@PathVariable long id) {
        return clientService.getClientById(id).
                orElseGet(()-> {throw new ClientNotFoundException("client not found");});
    }

    @PostMapping("/addClient")
    public Client addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @PutMapping("/updateClient")
    public Client updateClient(@RequestBody Client client, @PathVariable long id) {
        client.setId(id);
        return clientService.updateClient(client);
    }

    @DeleteMapping("/deleteClient/{id}")
    public Client deleteClient(@PathVariable long id) {
        return clientService.deleteClient(id);
    }
}
