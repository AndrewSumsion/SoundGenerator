package io.github.AndrewSumsion.soundgenerator;

public abstract class SoundHandler {
    protected int sampleRate;

    public void init(){}
    public abstract boolean handle(SoundBuffer buffer);
    public void finish(){}
    public void handlerAdded(SoundPipeline pipeline) {
        sampleRate = pipeline.getSampleRate();
    }
}
