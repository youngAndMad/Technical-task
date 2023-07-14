package danekerscode.server.service;

import danekerscode.server.model.Client;
import danekerscode.server.payload.Location;

public interface ClientService {
    Client register(Integer chatId);

    Client updateLastActionTime(Integer id);

    boolean setVisited(Integer id, Integer latitude, Integer longitude);

}
