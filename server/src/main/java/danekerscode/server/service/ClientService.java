package danekerscode.server.service;

import danekerscode.server.model.Client;

import java.time.LocalDateTime;

public interface ClientService {
    Client register(Integer chatId);

    Client findById(Integer id);

    Client updateLastActionTime(Integer id);
}
