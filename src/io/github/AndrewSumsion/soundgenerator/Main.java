package io.github.AndrewSumsion.soundgenerator;

import io.github.AndrewSumsion.soundgenerator.valuecontrollers.StaticValue;

import javax.sound.sampled.AudioFormat;
import java.io.File;

public class Main {
    public static AudioFormat format;

    public static void main(String[] args) throws Exception {
        SoundPipeline pipeline = new SoundPipeline(44100, 2048);
        Recorder recorder = new Recorder();
        pipeline.add(new SineGenerator(new StaticValue(110)));
        pipeline.add(new FrequencyModulator(new StaticValue(1760F), new StaticValue(1F),  false));
        pipeline.add(recorder);
        pipeline.run(44100 * 60 / 2048);
        System.out.println("Done. Exporting.");
        recorder.getSound().export(new File("/home/andrew/sounds/fm2.wav"));
    }
}
