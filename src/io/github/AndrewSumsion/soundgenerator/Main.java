package io.github.AndrewSumsion.soundgenerator;

import io.github.AndrewSumsion.soundgenerator.valuecontrollers.LFO;
import io.github.AndrewSumsion.soundgenerator.valuecontrollers.StaticValue;

import javax.sound.sampled.AudioFormat;
import java.io.File;

public class Main {
    public static AudioFormat format;

    public static void main(String[] args) throws Exception {
        SoundPipeline pipeline = new SoundPipeline(44100, 2048);
        Recorder recorder = new Recorder();
        pipeline.add(new SineGenerator(new StaticValue(440)));
        pipeline.add(new FrequencyModulator(new StaticValue(880F), new LFO(new StaticValue(0F), new StaticValue(3F), new StaticValue(0.5F), 44100),  false));
        pipeline.add(recorder);
        pipeline.run(44100 * 10 / 2048);
        System.out.println("Done. Exporting.");
        recorder.getSound().export(new File("C:/Users/sumsiand000/sounds/fm.wav"));
    }
}
