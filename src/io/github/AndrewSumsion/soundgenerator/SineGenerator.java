package io.github.AndrewSumsion.soundgenerator;

public class SineGenerator extends FrequencyGenerator {
    public SineGenerator(float frequency) {
        super(frequency);
    }

    public float sample() {
        return (float)Math.sin(i * ((frequency * 2) / sampleRate) * Math.PI);
    }
}
