package com.javarush.telegrambot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.util.Map;

public class MyFirstTelegramBot extends MultiSessionTelegramBot {
    public static final String NAME = "GameJavaRush_bot"; // TODO: добавьте имя бота в кавычках
    public static final String TOKEN = "7034350936:AAFOffCU098C-ud62hcnoLuxza5-gl4WhWw"; //TODO: добавьте токен бота в кавычках

    public MyFirstTelegramBot() {
        super(NAME, TOKEN);
    }

    @Override
    public void onUpdateEventReceived(Update updateEvent) {
        if (updateEvent.hasCallbackQuery()) {
            handleCallbackQuery(updateEvent);
        } else if (updateEvent.hasMessage() && updateEvent.getMessage().hasText()) {
            handleMessage(updateEvent);
        }
    }

    private void handleCallbackQuery(Update updateEvent) {
        String callbackData = updateEvent.getCallbackQuery().getData();

        if (callbackData.equals("yes")) {
            String step1Message = TelegramBotContent.STEP_1_TEXT;
            sendTextMessageAsync(step1Message);
            sendImageMessageAsync("src/main/resources/images/step_1_pic.jpg");
            String message1 = "Твои действия?";
            sendTextMessageAsync(message1, Map.of("Взламываем холодильник", "Hack_the_refrigerator", "Отступаем", "We_retreat"));

        } else if (callbackData.equals("Hack_the_refrigerator")) {
            String step2Message = TelegramBotContent.STEP_2_TEXT;
            sendTextMessageAsync(step2Message);
            sendImageMessageAsync("src/main/resources/images/step_2_pic.jpg");
            String step3Message = TelegramBotContent.STEP_3_TEXT;
            sendTextMessageAsync(step3Message);
            sendImageMessageAsync("src/main/resources/images/step_3_pic.jpg");

            String message2 = "Твои действия?";
            sendTextMessageAsync(message2, Map.of("Отправить робопылесос за едой!", "Send_a_robot_vacuum_cleaner_for_food!", "Проехаться на робопылесосе!",
                    "Ride_a_robot_vacuum_cleaner!", "Убегать от робопылесоса!", "Run_away_from_the_robot_vacuum_cleaner!"));

        } else if (callbackData.equals("We_retreat")) {
            String step10Message = TelegramBotContent.GAME_OVER;
            sendTextMessageAsync(step10Message);
            sendImageMessageAsync("src/main/resources/images/gameover.jpg");


        } else if (callbackData.equals("Ride_a_robot_vacuum_cleaner!")) {
            String step4Message = TelegramBotContent.STEP_4_TEXT;
            sendTextMessageAsync(step4Message);
            sendImageMessageAsync("src/main/resources/images/step_4_pic.jpg");

            String step5Message = TelegramBotContent.STEP_5_TEXT;
            sendTextMessageAsync(step5Message);
            sendImageMessageAsync("src/main/resources/images/step_5_pic.jpg");

            String message3 = "Твои действия?";
            sendTextMessageAsync(message3, Map.of("Включить", "Turn_on", "Игнорировать", "Ignore"));

        } else if (callbackData.equals("Turn_on")) {
            String step6Message = TelegramBotContent.STEP_6_TEXT;
            sendTextMessageAsync(step6Message);
            sendImageMessageAsync("src/main/resources/images/step_6_pic.jpg");
            String message3 = "Идём дальше?";
            sendTextMessageAsync(message3, Map.of("Да, идём", "Yes_lets_go", "Нет, не идём", "No_we_are_not_going"));

        } else if (callbackData.equals("Yes_lets_go")) {
            String step7Message = TelegramBotContent.STEP_7_TEXT;
            sendTextMessageAsync(step7Message);
            sendImageMessageAsync("src/main/resources/images/step_7_pic.jpg");
            String message4 = "Взламываем?";
            sendTextMessageAsync(message4, Map.of("Взламываем", "Hack", "Нет, я же котик и у меня лапки", "No_I_am_a_cat_and_I_have_paws"));

        } else if (callbackData.equals("Hack")) {
            String step8Message = TelegramBotContent.STEP_8_TEXT;
            sendTextMessageAsync(step8Message);
            sendImageMessageAsync("src/main/resources/images/step_8_pic.jpg");
            String message3 = "Идём дальше?";
            sendTextMessageAsync(message3, Map.of("Да", "Yes", "Нет", "No"));

        } else if (callbackData.equals("Yes")) {
            String step9Message = TelegramBotContent.FINAL_TEXT;
            sendTextMessageAsync(step9Message);
            sendImageMessageAsync("src/main/resources/images/final_pic.jpg");

        }
    }

    private void handleMessage(Update updateEvent) {
        String messageText = updateEvent.getMessage().getText();
        String firstName = updateEvent.getMessage().getFrom().getFirstName();

        if (messageText.equals("/start")) {
            handleStartCommand(firstName);
        }
    }

    private void handleStartCommand(String firstName) {
        sendImageMessageAsync("src/main/resources/images/hello.jpg");
        String greetingMessage = "*Привет*, будущий программист " + firstName + "!";
        String message = "Хочешь ли ты узнать по истене удивительную историю?";
        sendTextMessageAsync(greetingMessage);
        sendTextMessageAsync(message, Map.of("Да", "yes", "Нет", "no"));
    }


    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MyFirstTelegramBot());
    }
}