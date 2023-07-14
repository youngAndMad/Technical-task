package danekerscode.server.service.impl;

import danekerscode.server.exception.ClientNotFoundException;
import danekerscode.server.model.Client;
import danekerscode.server.payload.Location;
import danekerscode.server.repository.ClientRepository;
import danekerscode.server.service.ClientService;
import danekerscode.server.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final LocationService locationService;

    @Override
    public Client register(Integer chatId) {
        var client = clientRepository.findByChatId(chatId);
        if (client.isPresent()) {
            var presentClient = client.get();
            presentClient.setLastActionTime(now());
            return clientRepository.save(presentClient);
        }
        return clientRepository.save(new Client(chatId, now()));
    }

    @Override
    public Client updateLastActionTime(Integer id) {
        var client = this.clientRepository.findByChatId(id)
                .orElseThrow(() -> new ClientNotFoundException(format("client by id %d not found", id)));
        client.setLastActionTime(now());
        return clientRepository.save(client);
    }

    @Override
    public boolean setVisited(Integer id, Integer latitide, Integer longitude) {
        /*
         * find client from database and change some fields
         */

        var accepted = locationService.validate(longitude, latitide);
        if (accepted) {
            updateLastActionTime(id);
            return true;
        }
        return false;
    }

}
