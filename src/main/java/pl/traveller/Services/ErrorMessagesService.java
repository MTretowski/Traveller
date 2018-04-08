package pl.traveller.Services;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;

@Service
public class ErrorMessagesService {

    private HashMap<String, String> messagesPl = new HashMap<>();
    private HashMap<String, String> messagesEn = new HashMap<>();

    ErrorMessagesService() {
        messagesEn = getMessages("/messages_en.csv");
        messagesPl = getMessages("/messages_pl.csv");
    }

    private HashMap<String, String> getMessages(String filePath){

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
        }catch (IOException e) {
            e.printStackTrace();
        }

        return messages;

    }

    String getErrorMessage(String language, String key) {
        if(language.equals("pl")){
            return messagesPl.get(key);
        }
        else{
            return messagesEn.get(key);
        }

    }
}
