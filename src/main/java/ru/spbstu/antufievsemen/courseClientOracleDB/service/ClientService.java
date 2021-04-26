package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import org.springframework.stereotype.Service;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Client;
import ru.spbstu.antufievsemen.courseClientOracleDB.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(long id) {
        return clientRepository.getOne(id);
    }

    public boolean deleteClient(long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean addClient(Client client) {
        if (client == null
                || clientRepository.existsClientByPassportNumberAndPassportSeria(client.getPassportNumber(),
                client.getPassportSeria())) {
            return false;
        }
        clientRepository.saveAndFlush(client);
        return true;
    }

    public boolean updateClient(Client client) {
        if (clientRepository.existsById(client.getId())) {
            clientRepository.saveAndFlush(client);
            return true;
        }
        return false;
    }
}
