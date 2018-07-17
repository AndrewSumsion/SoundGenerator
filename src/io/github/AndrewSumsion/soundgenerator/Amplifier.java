package io.github.AndrewSumsion.soundgenerator;

public class Amplifier extends SoundHandler {
    private float factor;

    public Amplifier(float factor) {
        this.factor = factor;
    }

    public boolean handle(SoundBuffer buffer) {
        for(int i = 0; i < buffer.size(); i++) {
            float newSample = buffer.get(i) * factor;
            if(newSample > 1F) newSample = 1F;
            if(newSample < -1F) newSample = -1F;
            buffer.set(i, newSample);
        }
        return true;
    }
}
