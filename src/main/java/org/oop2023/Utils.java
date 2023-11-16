package org.oop2023;

import static org.oop2023.services.database.DatabaseMethods.buildDictionaryCLI;

import org.oop2023.services.database.DatabaseController;
import org.oop2023.utils.Dictionary;
import org.oop2023.utils.enums.Language;

import org.oop2023.services.translateAPI.Translator;;

public class Utils {
    public static final Dictionary dictionary = new Dictionary();
    public static final Translator translator = new Translator();


    static void initialize() {
        // Initialize database and dictionary
        DatabaseController.start();
        dictionary.setLanguage(Language.ENGLISH);
        System.out.println("Building dictionary...");
        buildDictionaryCLI(dictionary);

        // Initialize translator
    }
}
