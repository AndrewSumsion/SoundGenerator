package io.github.AndrewSumsion.soundgenerator;

public class FrequencyModulator extends SoundHandler {
    private float min;
    private float max;
    private int i = 0;

    public FrequencyModulator(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public FrequencyModulator() {
        this(870F, 880F);
    }

    @Override
    public boolean handle(SoundBuffer buffer) {
        for(int i = 0; i < buffer.size(); i++) {
            float sample = buffer.get(i);
            float frequency = getFrequency(sample);
            float newSample = sampleSine(frequency);
            buffer.set(i, newSample);
        }
        return true;
    }

    private float getFrequency(float sample) {
        float percentage = (sample + 1) / 2;
        return ((max - min) * percentage) + min;
    }

    private float sampleSine(float frequency) {
        this.i++;
        return (float)Math.sin((float)i * ((frequency * 2F) / (float)sampleRate) * Math.PI);
    }
}
