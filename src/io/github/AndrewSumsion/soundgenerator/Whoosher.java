package io.github.AndrewSumsion.soundgenerator;

public class Whoosher extends SoundHandler {
    private float lastSample;
    private LFO maxDifference;
    private long currentSample = 0;

    public Whoosher(float frequency, float amplitude) {
        this.maxDifference = new LFO(sampleRate, frequency, amplitude, true);
    }

    @Override
    public boolean handle(SoundBuffer buffer) {
        buffer.set(0, getSample(this.lastSample, buffer.get(0)));
        currentSample++;
        for(int i = 1; i < buffer.size(); i++) {
            buffer.set(i, getSample(buffer.get(i - 1), buffer.get(i)));
            currentSample++;
        }
        this.lastSample = buffer.get(buffer.size() - 1);
        return true;
    }

    private float getSample(float lastSample, float currentSample) {
        float difference = currentSample - lastSample;
        if(Math.abs(difference) > this.maxDifference.get(this.currentSample)) {
            if(difference > 0) {
                return lastSample + maxDifference.get(this.currentSample);
            } else if(difference < 0) {
                return lastSample - maxDifference.get(this.currentSample);
            }
            return 0F;
        } else {
            return currentSample;
        }
    }
}
