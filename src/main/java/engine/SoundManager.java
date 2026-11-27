package engine;

import javax.sound.sampled.*;

public class SoundManager {

    // volume général en pourcentage (0 = muet, 100 = maximum), réglé depuis le menu Paramètres
    private static volatile int volume = 100;

    public static int getVolume() {
        return volume;
    }

    public static void setVolume(int pourcentage) {
        volume = Math.max(0, Math.min(100, pourcentage));
    }

    public static void playSound(String fichier) {
        if (volume == 0) return;
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                SoundManager.class.getResource("/sounds/" + fichier)
            );

            Clip clip = AudioSystem.getClip();
            clip.open(audio);

            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float db = (float) (20.0 * Math.log10(volume / 100.0));
                gain.setValue(Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), db)));
            }

            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}