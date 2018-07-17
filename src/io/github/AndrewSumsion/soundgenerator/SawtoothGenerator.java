package io.github.AndrewSumsion.soundgenerator;

public class SawtoothGenerator extends FrequencyGenerator {

    public SawtoothGenerator(float frequency) {
        super(frequency);
    }

    @Override
    public float sample() {
        return (((frequency * 2F * (float) i) / (float) sampleRate) % 2F) - 1F;
    }
}
