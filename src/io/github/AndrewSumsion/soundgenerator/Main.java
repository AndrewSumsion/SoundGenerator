package io.github.AndrewSumsion.soundgenerator;

import javax.sound.sampled.AudioFormat;
import java.io.File;

public class Main {
    public static AudioFormat format;

    public static void main(String[] args) throws Exception {
        SoundPipeline pipeline = new SoundPipeline(44100, 2048);
        Recorder recorder = new Recorder();
        pipeline.add(new SineGenerator(1));
        pipeline.add(new FrequencyModulator());
        pipeline.add(recorder);
        pipeline.run(44100 * 10 / 2048);
        System.out.println("Done. Exporting.");
        recorder.getSound().export(new File("/home/andrew/sounds/fm.wav"));
    }
}
