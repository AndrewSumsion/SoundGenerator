package io.github.AndrewSumsion.soundgenerator;

import java.util.LinkedList;
import java.util.List;

public class Recorder extends SoundHandler {
    private List<SoundBuffer> buffer = new LinkedList<SoundBuffer>();

    @Override
    public boolean handle(SoundBuffer buffer) {
        this.buffer.add(buffer);
        return true;
    }

    public SoundBuffer getSound() {
        SoundBuffer result = null;
        for(SoundBuffer soundBuffer : buffer) {
            if(result == null) {
                result = soundBuffer;
                continue;
            }
            result.append(soundBuffer);
        }
        return result;
    }

    public void reset() {
        buffer = new LinkedList<SoundBuffer>();
    }
}
