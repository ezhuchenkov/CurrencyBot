import com.google.common.annotations.VisibleForTesting;
import com.vdurmont.emoji.EmojiParser;
import currency.ValueLoader;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExchangeBot extends TelegramLongPollingBot {

    private final ValueLoader valueLoader = new ValueLoader();
    private final String failToParse = "Привет, я Currency bot. Напиши мне одну из следующих валют: " +
            valueLoader.getNames() + "\n и я верну её курс в рублях";


    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String messageText = update.getMessage().getText();
        String userFirstName = update.getMessage().getChat().getFirstName();
        String userLastName = update.getMessage().getChat().getLastName();
        long userId = update.getMessage().getChat().getId();
        if (message != null && message.hasText()) {
            parseAndReply(message);
            log(userFirstName, userLastName, Long.toString(userId), messageText);
        }
    }

    private void parseAndReply(Message message) {
        String curName;
        try {
            curName = extractCurrency(message.getText());
        } catch (Exception e) {
            sendMsg(message, failToParse);
            return;
        }
        if (valueLoader.hasMatch(curName)) {
            try {
                sendMsg(message, valueLoader.getValue(curName));
            } catch (Exception e) {
                sendMsg(message, failToParse);
            }
        } else {
            sendMsg(message, failToParse);
        }
    }

    //ISO 4217
    @VisibleForTesting
    static String extractCurrency(String text) {
        return text.subSequence(0, 3).toString().toUpperCase();
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
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        //keyboardFirstRow.
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(EmojiParser.parseToUnicode("USD " + "\uD83C\uDDFA\uD83C\uDDF8"));
        keyboardFirstRow.add(EmojiParser.parseToUnicode("EUR " + "\uD83C\uDDEA\uD83C\uDDFA"));
        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

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

    private void log(String first_name, String last_name, String user_id, String txt) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
    }
}