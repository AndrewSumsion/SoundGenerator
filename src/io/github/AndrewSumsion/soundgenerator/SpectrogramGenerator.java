package io.github.AndrewSumsion.soundgenerator;

import io.github.AndrewSumsion.soundgenerator.valuecontrollers.StaticValue;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpectrogramGenerator {
    private BufferedImage image;
    private double length;
    private double minFreq;
    private double maxFreq;
    private int sampleRate;

    private int pixelWidthInSamples;
    private double pixelHeightInHz;

    public SpectrogramGenerator(BufferedImage image, double length, double minFreq, double maxFreq, int sampleRate) {
        this.image = image;
        this.length = length;
        this.minFreq = minFreq;
        this.maxFreq = maxFreq;
        this.sampleRate = sampleRate;

        pixelWidthInSamples = (int) ((((double) sampleRate) * length) / (double) image.getWidth());
        pixelHeightInHz = (maxFreq - minFreq) / (double) image.getHeight();
    }

    public SoundBuffer generate() {
        SoundBuffer result = new SoundBuffer(sampleRate, new float[0]);
        for(int i = 0; i < image.getWidth(); i++) {
            SoundBuffer column = new SoundBuffer(sampleRate, new float[pixelWidthInSamples]);
            for(int j = 0; j < image.getHeight(); j++) {
                SoundPipeline pipeline = new SoundPipeline(sampleRate, pixelWidthInSamples);
                double frequency = (pixelHeightInHz * (double) j) + minFreq;
                SineGenerator toneGenerator = new SineGenerator(new StaticValue((float) frequency));
                toneGenerator.i = i * pixelWidthInSamples;
                pipeline.add(toneGenerator);
                Color color = new Color(image.getRGB(i, (image.getHeight() - j) - 1));
                int grayscale = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                double intensity = ((double) grayscale / 255D) * ((double) color.getAlpha() / 255D);
                pipeline.add(new Amplifier(new StaticValue(1 - (float) intensity)));
                pipeline.add(new Amplifier(new StaticValue(1F / (float) image.getHeight())));
                Recorder recorder = new Recorder();
                pipeline.add(recorder);
                pipeline.run(1);
                for(int k = 0; k < pixelWidthInSamples; k++) {
                    column.set(k, column.get(k) + recorder.getSound().get(k));
                }
            }
            result.append(column);
        }
        return result;
    }
}
