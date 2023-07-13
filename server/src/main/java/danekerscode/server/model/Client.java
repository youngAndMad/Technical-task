package danekerscode.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer chatId;
    private LocalDateTime startBotTime;
    private LocalDateTime lastActionTime;


    public Client(Integer chatId , LocalDateTime startBotTime) {
        this.chatId = chatId;
        this.startBotTime = startBotTime;
        this.lastActionTime = now();
    }
}
