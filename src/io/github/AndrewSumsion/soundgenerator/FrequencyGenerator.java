package io.github.AndrewSumsion.soundgenerator;

import io.github.AndrewSumsion.soundgenerator.valuecontrollers.ValueController;

public abstract class FrequencyGenerator extends SoundGenerator {
    protected ValueController frequency;

    public FrequencyGenerator(ValueController frequency) {
        this.frequency = frequency;
    }

    public float getFrequency() {
        return frequency.checkValue();
    }

    public void setFrequency(ValueController frequency) {
        this.frequency = frequency;
    }
}
