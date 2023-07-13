package danekerscode.server.service.impl;

import danekerscode.server.exception.ClientNotFoundException;
import danekerscode.server.exception.ClientRegisteredYetException;
import danekerscode.server.model.Client;
import danekerscode.server.repository.ClientRepository;
import danekerscode.server.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client register(Integer chatId) {
        var client = clientRepository.findByChatId(chatId);
        if (client.isPresent()){
            var presentClient =  client.get();
            presentClient.setLastActionTime(now());
            return clientRepository.save(presentClient);
        }
        return clientRepository.save(new Client(chatId,now()));
    }

    @Override
    public Client findById(Integer id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException( format("client by id %d not found" , id)));
    }

    @Override
    public Client updateLastActionTime(Integer id) {
        var client = this.clientRepository.findByChatId(id)
                .orElseThrow(() -> new ClientNotFoundException( format("client by id %d not found" , id)));
        client.setLastActionTime(now());
        return clientRepository.save(client);
    }
}
