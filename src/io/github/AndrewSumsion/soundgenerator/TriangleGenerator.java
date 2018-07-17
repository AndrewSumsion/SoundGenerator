package io.github.AndrewSumsion.soundgenerator;

public class TriangleGenerator extends FrequencyGenerator {
    public TriangleGenerator(float frequency) {
        super(frequency);
    }

    @Override
    public float sample() {
        float period = (frequency / 2) / (float)sampleRate;

        return (4F / period) * (Math.abs((((float)i - (period / 4F)) % period) - (period / 2F)) - (period / 4F));
    }
}
