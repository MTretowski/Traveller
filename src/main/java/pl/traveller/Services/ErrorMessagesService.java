package pl.traveller.Services;

import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Service
public class ErrorMessagesService {

    private ResourceBundle resourceBundle;

    ErrorMessagesService() {
        resourceBundle = ResourceBundle.getBundle("messages");
    }

    String getErrorMessage(String language, String key) {

        String error;

        if(!(language.equals("pl") || language.equals("en"))){
            language = "en";
        }
        if (!resourceBundle.getLocale().toString().equals(language)) {
            Locale.setDefault(new Locale(language));
            resourceBundle = ResourceBundle.getBundle("messages");
        }

        try{
            error = resourceBundle.getString(key);
        }catch(MissingResourceException e){
            if(language.equals("pl")){
                error = "Nieznany błąd";
            }
            else{
                error = "Unknown error";
            }
        }

        return error;
    }
}
