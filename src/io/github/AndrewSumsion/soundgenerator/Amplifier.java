package io.github.AndrewSumsion.soundgenerator;

import io.github.AndrewSumsion.soundgenerator.valuecontrollers.ValueController;

public class Amplifier extends SoundHandler {
    private ValueController factor;

    public Amplifier(ValueController factor) {
        this.factor = factor;
    }

    public boolean handle(SoundBuffer buffer) {
        for(int i = 0; i < buffer.size(); i++) {
            float newSample = buffer.get(i) * factor.value();
            if(newSample > 1F) newSample = 1F;
            if(newSample < -1F) newSample = -1F;
            buffer.set(i, newSample);
        }
        return true;
    }
}
