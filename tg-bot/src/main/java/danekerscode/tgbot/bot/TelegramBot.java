package danekerscode.tgbot.bot;

import danekerscode.tgbot.client.BackendFeignClient;
import danekerscode.tgbot.payload.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;


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
        var chatId = update.getMessage().getChatId();

        if (update.getMessage().getLocation() != null) {
            log.info("current user location {}", update.getMessage().getLocation());
            setState(update);
            setVisit(chatId);
            return;
        }

        var command = update.getMessage();

        if (command == null || !command.hasText()) {
            return;
        }

        Client response;
        switch (command.getText()) {
            case "/start" -> {
                response = backend.register(chatId);
                var msg = new SendMessage(String.valueOf(chatId), response.toString());
                msg.setReplyMarkup(createButtons("button which require location", "common button"));
                send(msg);
            }
            case "button which require location" -> {
                /*
                clear context
                 */
                if (!checkLocation()){
                    send(update.getMessage().getChatId(), "firstly u need send your location");
                } else {
                    setVisit(chatId);
                }
            }
            case "common button" -> {
                send(chatId, """
                        u r clicked common button!
                        Hello from Danekerscode
                        """);
            }
            default -> {
                response = backend.updateLastActionTime(chatId);
                send(chatId, response.toString());
            }
        }

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

    private void send(SendMessage msg) {
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("error during send response to client");
            throw new RuntimeException(e);
        }
    }

    private void setState(Update update) {
        /*
        To save current user location in context
        */
        SecurityContextHolder.getContext().setAuthentication(
                new AnonymousAuthenticationToken(
                        "some key",
                        update.getMessage().getLocation(),
                        List.of(new SimpleGrantedAuthority("ROLE_NOTHING"))
                )
        );
    }

    private Boolean checkLocation() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null;
    }

    private ReplyKeyboardMarkup createButtons(String... commands) {
        KeyboardRow row = new KeyboardRow();

        Arrays.stream(commands).map(KeyboardButton::new).forEach(row::add);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setKeyboard(List.of(row));
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    private void setVisit(Long chatId) {
        /*
        location attributes converted to integer,
        because telegram gives approximate location and not exact
         */
        var location = (Location) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var validLocation = backend.setVisit(chatId,
                (int) location.getLatitude().doubleValue(),
                (int) location.getLongitude().doubleValue()
        );

        send(chatId, validLocation ? "valid location" : "invalid location");
    }

}
