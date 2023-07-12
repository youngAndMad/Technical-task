package danekerscode.tgbot.payload;

import java.time.LocalDateTime;

public record Client(
        Integer id,
        Integer chatId,
        LocalDateTime startBotTime,
        LocalDateTime lastActionTime
) {
    //todo copy from server
}
