package io.github.AndrewSumsion.soundgenerator;

import io.github.AndrewSumsion.soundgenerator.valuecontrollers.ValueController;

public class FrequencyModulator extends SoundHandler {
    private ValueController carrierFrequency;
    private ValueController amplitude;
    private int currentSample = 0;
    private boolean removeCarrierFrequency;

    public FrequencyModulator(ValueController carrierFrequency, ValueController amplitude, boolean removeCarrierFrequency) {
        this.carrierFrequency = carrierFrequency;
        this.amplitude = amplitude;
        this.removeCarrierFrequency = removeCarrierFrequency;
    }

    @Override
    public boolean handle(SoundBuffer buffer) {
        for(int i = 0; i < buffer.size(); i++) {
            float sample = buffer.get(i);
            float newSample = (float)Math.sin((currentSample * ((carrierFrequency.value() * 2) / sampleRate) + sample * amplitude.value()) * Math.PI);
            if(removeCarrierFrequency) {
                newSample -= (float)Math.sin(currentSample * ((carrierFrequency.value() * 2) / sampleRate) * Math.PI);
            }
            buffer.set(i, newSample);
            currentSample++;
        }

        return true;
    }
}
