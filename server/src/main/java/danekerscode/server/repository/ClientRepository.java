package danekerscode.server.repository;

import danekerscode.server.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {
    Optional<Client> findByChatId(Integer chatId);
}
