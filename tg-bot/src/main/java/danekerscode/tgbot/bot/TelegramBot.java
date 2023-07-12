package danekerscode.tgbot.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import danekerscode.tgbot.client.BackendFeignClient;
import danekerscode.tgbot.payload.Client;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.security.sasl.SaslServer;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot{

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

        if (command == null || !command.hasText()){
            return;
        }

        var text = command.getText();
        var chatId = update.getMessage().getChatId();

        if (text.equals("/start")){
            var response = backend.register(chatId);
            send(chatId,response.toString());
        }
    }

    private void send(Long chatId , String text)  {
        var msg = new SendMessage(String.valueOf(chatId) , text);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("error during send response to client");
            throw new RuntimeException(e);
        }
    }

    private static Client parseModelResponse(Response response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bodyBytes = response.body().asInputStream().readAllBytes();
            return objectMapper.readValue(bodyBytes, Client.class);
        } catch (IOException e) {
            log.error("error during casting");
        }
        return null;
    }
}
