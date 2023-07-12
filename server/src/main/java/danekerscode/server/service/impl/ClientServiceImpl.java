package danekerscode.server.service.impl;

import danekerscode.server.exception.ClientNotFoundException;
import danekerscode.server.exception.ClientRegisteredYetException;
import danekerscode.server.model.Client;
import danekerscode.server.repository.ClientRepository;
import danekerscode.server.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client register(Integer chatId) {
        if (clientRepository.findByChatId(chatId).isPresent()){
            throw new ClientRegisteredYetException();
        }
        return clientRepository.save(new Client(chatId));
    }

    @Override
    public Client findById(Integer id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException( format("client by id %d not found" , id)));
    }
}
