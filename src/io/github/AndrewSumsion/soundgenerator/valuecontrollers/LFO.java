package io.github.AndrewSumsion.soundgenerator.valuecontrollers;

public class LFO extends ValueController {
    private ValueController min;
    private ValueController max;
    private ValueController frequency;

    public LFO(ValueController min, ValueController max, ValueController frequency, int sampleRate) {
        super(sampleRate);
        this.min = min;
        this.max = max;
        this.frequency = frequency;
    }

    @Override
    public float value() {
        float sample = ((float)Math.sin(index * ((frequency.value() * 2) / sampleRate) * Math.PI)+1)/2F;
        float min = this.min.value();
        float max = this.max.value();
        sample *= max - min;
        sample += min;
        index++;
        return sample;
    }
}
