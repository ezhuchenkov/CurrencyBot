import currency.ValueLoader;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExchangeBot extends TelegramLongPollingBot {

    private final ValueLoader valueLoader = new ValueLoader();
    private long chat_id;

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String message_text = update.getMessage().getText();
        chat_id = update.getMessage().getChatId();
        String answer = message_text;
        if (message != null && message.hasText()) {
            if (message.getText().equals("/help")) {
                sendMsg(message, "Привет, я Currency bot.");
            } else {
                String value = valueLoader.getValue(message_text);
                sendMsg(message, value);

            }
        }
    }


    @Override
    public String getBotUsername() {
        return "Currency bot";
    }

    @Override
    public String getBotToken() {
        return "591243527:AAEA8b0xY42EvM7iKsMbM-v-mYBLo6x2AMI";
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage().setChatId(chat_id);
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public ExchangeBot(DefaultBotOptions options) {
        super(options);
    }

    private void log(String first_name, String last_name, String user_id, String txt, String bot_answer) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
        System.out.println("Bot answer: \n Text - " + bot_answer);
    }
}