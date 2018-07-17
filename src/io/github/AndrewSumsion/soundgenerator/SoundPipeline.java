package io.github.AndrewSumsion.soundgenerator;

import java.util.ArrayList;
import java.util.List;

public class SoundPipeline {
    private List<SoundHandler> handlers = new ArrayList<SoundHandler>();
    private int bufferSize;
    private int sampleRate;
    private SoundBuffer source;
    private int sourceIndex = 0;

    public SoundPipeline(int sampleRate, int bufferSize, SoundBuffer source) {
        this.sampleRate = sampleRate;
        this.bufferSize = bufferSize;
        this.source = source;
    }

    public SoundPipeline(int sampleRate, int bufferSize) {
        this(sampleRate, bufferSize, null);
    }

    public void add(SoundHandler handler) {
        handlers.add(handler);
        handler.handlerAdded(this);
    }

    public void addFirst(SoundHandler handler) {
        handlers.add(0, handler);
        handler.handlerAdded(this);
    }

    public void addLast(SoundHandler handler) {
        add(handler);
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public List<SoundHandler> getHandlers() {
        return handlers;
    }

    public void run(int times) {
        for(SoundHandler handler : handlers) {
            handler.init();
        }
        for(int i = 0; i < times; i++) {
            SoundBuffer buffer;
            if(source == null) {
                buffer = new SoundBuffer(sampleRate, new float[bufferSize]);
            } else {
                buffer = source.subBuffer(sourceIndex, sourceIndex + bufferSize);
                sourceIndex += bufferSize;
            }

            for(SoundHandler handler : handlers) {
                if(!handler.handle(buffer)) break;
            }
        }
        for(SoundHandler handler : handlers) {
            handler.finish();
        }
    }

    public void run() {
        if(source == null) {
            throw new IllegalArgumentException("No specific source set");
        }
        run((int)Math.floor((float)source.size() / (float)bufferSize));
    }
}
