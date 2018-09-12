package io.github.AndrewSumsion.soundgenerator.valuecontrollers;

public abstract class ValueController {
    protected int sampleRate;
    protected int index = 0;

    public ValueController(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public abstract float value();
    public float checkValue() {
        float value = value();
        index--;
        return value;
    }

    public void reset() {
        index = 0;
    }
}
