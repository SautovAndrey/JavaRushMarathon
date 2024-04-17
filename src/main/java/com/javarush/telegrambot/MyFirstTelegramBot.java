package com.javarush.telegrambot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;

public class MyFirstTelegramBot extends MultiSessionTelegramBot {
    public static final String NAME = "GameJavaRush_bot"; // TODO: добавьте имя бота в кавычках
    public static final String TOKEN = "7034350936:AAFOffCU098C-ud62hcnoLuxza5-gl4WhWw"; //TODO: добавьте токен бота в кавычках

    public MyFirstTelegramBot() {
        super(NAME, TOKEN);
    }

    @Override
    public void onUpdateEventReceived(Update updateEvent) {
        if (updateEvent.hasMessage() && updateEvent.getMessage().hasText()) {
            String messageText = updateEvent.getMessage().getText();
            String firstName = updateEvent.getMessage().getFrom().getFirstName();

            if (messageText.equals("/bye")) {
                sendTextMessageAsync("Asta la vista, baby!");
            } else if (messageText.equals("/start")) {
                String greetingMessage = "*Привет*, будущий программист " + firstName + "!";
                sendTextMessageAsync(greetingMessage);

                List<KeyboardButton> buttons = new ArrayList<>();
                buttons.add(new KeyboardButton("Кот"));
                buttons.add(new KeyboardButton("Собака"));

                sendTextMessageWithButtons("Ваше любимое животное?", buttons, updateEvent.getMessage().getChatId().toString());
            }
        }

        if (updateEvent.hasCallbackQuery()) {
            String chosenAnimal = updateEvent.getCallbackQuery().getData();
            if (chosenAnimal.equals("Кот")) {
                sendImageMessageAsync("src/main/resources/images/step_4_pic.jpg");
            } else if (chosenAnimal.equals("Собака")) {
                sendImageMessageAsync("src/main/resources/images/step_6_pic.jpg");
            }
        }
    }

    private void sendTextMessageWithButtons(String messageText, List<KeyboardButton> buttons, String chatId) {
        SendMessage message = new SendMessage();
        message.setText(messageText);
        message.setChatId(chatId);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        for (KeyboardButton button : buttons) {
            row.add(button);
        }
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MyFirstTelegramBot());
    }
}