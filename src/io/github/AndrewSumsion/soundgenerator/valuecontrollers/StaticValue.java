package io.github.AndrewSumsion.soundgenerator.valuecontrollers;

public class StaticValue extends ValueController {
    private float value;

    public StaticValue(float value) {
        super(0);
        this.value = value;
    }

    public float value() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
