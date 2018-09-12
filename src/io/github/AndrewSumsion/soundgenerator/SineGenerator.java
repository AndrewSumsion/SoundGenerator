package io.github.AndrewSumsion.soundgenerator;

import io.github.AndrewSumsion.soundgenerator.valuecontrollers.ValueController;

public class SineGenerator extends FrequencyGenerator {
    public SineGenerator(ValueController frequency) {
        super(frequency);
    }

    public float sample() {
        return (float)Math.sin(i * ((frequency.value() * 2) / sampleRate) * Math.PI);
    }
}
