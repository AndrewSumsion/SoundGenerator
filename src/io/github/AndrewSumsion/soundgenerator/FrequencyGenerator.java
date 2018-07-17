package io.github.AndrewSumsion.soundgenerator;

public abstract class FrequencyGenerator extends SoundGenerator {
    protected float frequency;

    public FrequencyGenerator(float frequency) {
        this.frequency = frequency;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }
}
