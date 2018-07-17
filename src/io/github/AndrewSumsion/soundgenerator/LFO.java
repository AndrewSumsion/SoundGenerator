package io.github.AndrewSumsion.soundgenerator;

public class LFO {
    private float sampleRate;
    private float frequency;
    private float amplitude;
    private boolean abs;

    public LFO(float sampleRate, float frequency, float amplitude, boolean abs) {
        this.sampleRate = sampleRate;
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.abs = abs;
    }

    public LFO(float sampleRate, float frequency, float amplitude) {
        this(sampleRate, frequency, amplitude, false);
    }

    public float get(long currentSample) {
        float result = amplitude * (float)Math.sin(currentSample * ((frequency * 2) / sampleRate) * Math.PI);
        if(abs) {
            result = Math.abs(result);
        }
        return result;
    }
}
