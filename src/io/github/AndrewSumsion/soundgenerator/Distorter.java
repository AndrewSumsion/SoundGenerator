package io.github.AndrewSumsion.soundgenerator;

public class Distorter extends SoundHandler {
    @Override
    public boolean handle(SoundBuffer buffer) {
        for(int i = 0; i < buffer.size(); i++) {
            float sample = buffer.get(i);
            if(sample > 0) {
                sample = 1;
            }
            if(sample < 0) {
                sample = -1;
            }
            buffer.set(i, sample);
        }
        return true;
    }
}
