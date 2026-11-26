package engine;

import javax.sound.sampled.*;

public class SoundManager {

    public static void playSound(String fichier) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                SoundManager.class.getResource("/sounds/" + fichier)
            );

            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}