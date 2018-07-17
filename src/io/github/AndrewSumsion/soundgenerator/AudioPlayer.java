package io.github.AndrewSumsion.soundgenerator;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioPlayer extends SoundHandler {

    private AudioFormat format = null;
    private SourceDataLine line = null;

    public boolean handle(SoundBuffer buffer) {
        /*AudioInputStream stream = buffer.toAudioInputStream();

        DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());

        Clip clip;
        try {
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
        } catch (LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
        clip.start();

        while (clip.isRunning()) {
            Thread.yield();
        }
        */
        System.out.println("Fired");
        AudioInputStream stream = buffer.toAudioInputStream();
        if(line == null) {
            try {
                line = AudioSystem.getSourceDataLine(stream.getFormat());
                line.open(line.getFormat());
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Line opened");
        }
        byte[] bytes;
        try {
            bytes = new byte[stream.available()];
            stream.read(bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        line.write(bytes, 0, bytes.length);
        while (line.isRunning()) {
            Thread.yield();
            System.out.println("Yielded");
        }
        return true;
    }

    public void init() {

    }
}
