package io.github.AndrewSumsion.soundgenerator;

public class WhiteNoiseGenerator extends SoundGenerator {
    @Override
    public float sample() {
        return (float)((Math.random() - 0.5D) * 2D);
    }
}
