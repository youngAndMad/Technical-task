package danekerscode.server.service;

import danekerscode.server.model.Client;

public interface ClientService {
    Client register(Integer chatId);

    Client findById(Integer id);
}
