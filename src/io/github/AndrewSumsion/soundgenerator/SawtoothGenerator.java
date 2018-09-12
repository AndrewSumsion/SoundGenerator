package io.github.AndrewSumsion.soundgenerator;

import io.github.AndrewSumsion.soundgenerator.valuecontrollers.ValueController;

public class SawtoothGenerator extends FrequencyGenerator {

    public SawtoothGenerator(ValueController frequency) {
        super(frequency);
    }

    @Override
    public float sample() {
        return (((frequency.value() * 2F * (float) i) / (float) sampleRate) % 2F) - 1F;
    }
}
