package gamecode;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL sounds[] = new URL[30];
    
    public Sound(){
        sounds[0] = getClass().getResource("/resources/sound/Theme.wav");

    }
    public void setFile(int i){
        try {
            AudioInputStream audioIS = AudioSystem.getAudioInputStream(sounds[i]);
            clip = AudioSystem.getClip();
            clip.open(audioIS);
        } catch (Exception e) {
            System.out.println("Audio not working!");
            e.printStackTrace();
        }
    }
    public void playAudio(){
        clip.start();
    }
    public void loopAudio(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stopAudio(){
        clip.stop();
    }

}
