package org.oop2023.services.textToSpeechAPI;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Speaker {
    public static final String VOICENAME_KEVIN = "kevin16";
    private Voice voice;

    public void initialize() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(VOICENAME_KEVIN);
        voice.allocate();
    }

    public void speak(String text) {
        voice.speak(text);
    }
}
