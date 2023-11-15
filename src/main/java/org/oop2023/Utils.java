package org.oop2023;

import static org.oop2023.services.database.DatabaseMethods.buildDictionaryCLI;

import org.oop2023.services.database.DatabaseController;
import org.oop2023.utils.Dictionary;
import org.oop2023.utils.enums.Language;

public class Utils {
    public static final Dictionary dictionary = new Dictionary();


    static {
        DatabaseController.start();
        dictionary.setLanguage(Language.ENGLISH);
        buildDictionaryCLI(dictionary);
    }
}
