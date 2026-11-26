package engine;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {

    public static void playSound(String soundName) {

        try {
            URL soundURL = SoundManager.class.getResource(
                "/sounds/" + soundName
            );

            if (soundURL == null) {
                System.out.println("Son introuvable : " + soundName);
                return;
            }

            AudioInputStream audioStream =
                AudioSystem.getAudioInputStream(soundURL);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.start();

            // Ferme les ressources lorsque le son est terminé.
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    try {
                        audioStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (UnsupportedAudioFileException |
                 IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}