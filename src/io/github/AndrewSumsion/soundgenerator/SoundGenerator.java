package io.github.AndrewSumsion.soundgenerator;

public abstract class SoundGenerator extends SoundHandler {
    protected int i = 0;

    public boolean handle(SoundBuffer buffer) {
        for(int j = 0; j < buffer.size(); j++) {
            buffer.getData()[j] = sample();
            i++;
        }
        return true;
    }

    public abstract float sample();
}
