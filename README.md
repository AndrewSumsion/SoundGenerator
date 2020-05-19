# SoundGenerator
A Java library written from the ground up that can generate and process audio data or other PCM signals. As of right now it can only import and export 16 bit single channel .wav files.

## How it works:
Buffers of sound are sent through a pipeline. The pipeline consists of several sound handlers that each take the sound buffer, process it, and pass it on the the next sound handler.

This program is designed to let you create your own processing code, but it does come with a few basic generators and processors built in. This includes:
- Amplifier: amplifies sound data with a given multiplier
- Bandwidther: weird experiment that distorts sound
- Distorter: Takes the sign of each sample. Basically as distorted as it gets
- FrequencyModulator: Does frequency modulation
- Recorder: Saves all the data that passes through it to enable exporting
- SawtoothGenerator: Generates a sawtooth wave
- SineGenerator: Generates a sine wave
- WhiteNoiseGenerator: Generate white noise (random samples)

### Example Sound Handler:
```java
public class Distorter extends SoundHandler {
    @Override
    public boolean handle(SoundBuffer buffer) {
        for(int i = 0; i < buffer.size(); i++) {
            float sample = buffer.get(i);
            if(sample > 0) {
                sample = 1;
            }
            if(sample < 0) {
                sample = -1;
            }
            buffer.set(i, sample);
        }
        return true;
    }
}
```

### ValueControllers
A value controller is essentially something that represents a number. It can be used as the frequency of a sine wave, the multiplier for the amplifier, etc. Basically any numeric value can and should be represented by a ValueController.

There are some basic value controllers built in:
- StaticValue: represents a static number
- PanController: goes linearly from one value to another in a given amount of time
- LFO: Oscillates between a given min and max at a given frequency
