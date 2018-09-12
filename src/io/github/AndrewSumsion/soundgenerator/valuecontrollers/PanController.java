package io.github.AndrewSumsion.soundgenerator.valuecontrollers;

public class PanController extends ValueController {
    private float startValue;
    private float endValue;
    private int length;
    private int delay;

    public PanController(float startValue, float endValue, int length, int delay, int sampleRate) {
        super(sampleRate);
        this.startValue = startValue;
        this.endValue = endValue;
        this.length = length;
        this.delay = delay;
    }

    public PanController(float startValue, float endValue, int length, int sampleRate) {
        this(startValue, endValue, length, 0, sampleRate);
    }

    public float value() {
        return equation(index++);
    }

    public float valueAt(int index) {
        return equation(index);
    }

    private float equation(int x) {
        float y = endValue / (float) length * (float)(x - delay) + startValue;
        if(y>endValue) y = endValue;
        if(y<startValue) y = startValue;
        return y;
    }

    public float getStartValue() {
        return startValue;
    }

    public float getEndValue() {
        return endValue;
    }

    public int getLength() {
        return length;
    }

    public int getDelay() {
        return delay;
    }
}
