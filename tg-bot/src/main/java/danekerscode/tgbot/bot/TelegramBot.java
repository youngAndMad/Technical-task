package danekerscode.tgbot.bot;

import danekerscode.tgbot.client.BackendFeignClient;
import danekerscode.tgbot.payload.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final BackendFeignClient backend;

    @Value("${telegram.bot.username}")
    private String BOT_USERNAME;

    @Value("${telegram.bot.token}")
    private String BOT_TOKEN;

    @Override
    public String getBotUsername() {
        return this.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return this.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var command = update.getMessage();

        if (command == null || !command.hasText()) {
            return;
        }

        var text = command.getText();
        var chatId = update.getMessage().getChatId();

        System.out.println(text);


        Client response;
        if (text.equals("/start")) {
            response = backend.register(chatId);
        }else {
            response = backend.updateLastActionTime(chatId);
        }
        send(chatId, response.toString());
    }

    private void send(Long chatId, String text) {
        var msg = new SendMessage(String.valueOf(chatId), text);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("error during send response to client");
            throw new RuntimeException(e);
        }
    }

}
