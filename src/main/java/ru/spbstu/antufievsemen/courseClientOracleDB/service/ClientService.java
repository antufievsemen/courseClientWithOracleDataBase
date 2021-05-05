package ru.spbstu.antufievsemen.courseClientOracleDB.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Client;
import ru.spbstu.antufievsemen.courseClientOracleDB.exception.ClientNotFoundException;
import ru.spbstu.antufievsemen.courseClientOracleDB.repository.ClientRepository;

@Service
@Transactional(rollbackOn = ClientNotFoundException.class)
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(long id) {
        return clientRepository.findById(id);
    }

    public Client deleteClient(long id) throws ClientNotFoundException {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            clientRepository.deleteById(id);
            return client.get();
        }
        throw new ClientNotFoundException("delete null client");
    }

    public Client addClient(Client client) {
        return clientRepository.saveAndFlush(client);
    }

    public Client updateClient(Client client) throws ClientNotFoundException {
        Optional<Client> clientOptional = clientRepository.findById(client.getId());
        if (clientOptional.isPresent()) {
            clientRepository.deleteById(client.getId());
            return clientOptional.get();
        }
        throw new ClientNotFoundException("update null client");
    }
    
    public boolean existClientWithPassportSeriaAndNum(String seria, String number) {
        return clientRepository.existsClientByPassportNumberAndPassportSeria(number, seria);
    }
}
