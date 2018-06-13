package pl.traveller.Services;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;

@Service
public class ErrorMessagesServiceImpl implements ErrorMessagesService {

    private HashMap<String, String> messagesPl;
    private HashMap<String, String> messagesEn;

    ErrorMessagesServiceImpl() {
        messagesEn = getMessages("/messages_en.csv");
        messagesPl = getMessages("/messages_pl.csv");
    }

    private HashMap<String, String> getMessages(String filePath) {
        HashMap<String, String> messages = new HashMap<>();
        InputStream inputStream = this.getClass().getResourceAsStream(filePath);
        BufferedReader bufferedReader = null;
        String readedLine;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            if (bufferedReader != null) {
                while ((readedLine = bufferedReader.readLine()) != null) {
                    String[] splittedLine = readedLine.split(";");
                    messages.put(splittedLine[0], splittedLine[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public String getErrorMessage(String language, String key) {
        String message;
        if (language.equals("pl")) {
            message = messagesPl.get(key);
            if (message == null) {
                message = "Nieznany błąd";
            }
        } else {
            message = messagesEn.get(key);
            if (message == null) {
                message = "Unknown error";
            }
        }
        return message;
    }
}
