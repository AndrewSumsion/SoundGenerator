package io.github.AndrewSumsion.soundgenerator;

public class Bandwither extends SoundHandler {
    private float lastSample;
    private float maxDifference;

    public Bandwither(float maxDifference) {
        if(maxDifference < 0) throw new IllegalArgumentException("maxDifference must be positive");
        this.maxDifference = maxDifference;
    }

    @Override
    public boolean handle(SoundBuffer buffer) {
        buffer.set(0, getSample(this.lastSample, buffer.get(0)));
        for(int i = 1; i < buffer.size(); i++) {
            buffer.set(i, getSample(buffer.get(i - 1), buffer.get(i)));
        }
        this.lastSample = buffer.get(buffer.size() - 1);
        return true;
    }

    private float getSample(float lastSample, float currentSample) {
        float difference = currentSample - lastSample;
        if(Math.abs(difference) > this.maxDifference) {
            if(difference > 0) {
                return lastSample + maxDifference;
            } else if(difference < 0) {
                return lastSample - maxDifference;
            }
            return 0F;
        } else {
            return currentSample;
        }
    }
}
