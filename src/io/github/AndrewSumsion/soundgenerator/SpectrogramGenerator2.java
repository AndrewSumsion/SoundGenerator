package io.github.AndrewSumsion.soundgenerator;

import io.github.AndrewSumsion.soundgenerator.valuecontrollers.StaticValue;

import java.awt.*;
import java.awt.image.BufferedImage;

// An attempt at a more efficient algorithm for generating spectrograms
// Does not work yet, pretend it doesn't exist :)
public class SpectrogramGenerator2 {
    private BufferedImage image;
    private double minFreq;
    private double maxFreq;
    private double length;

    private int pixelWidthInSamples;
    private double pixelHeightInHz;

    private int sampleRate;

    private float[][] data;

    private SoundPipeline[] generators;
    private StaticValue[] intensities;

    public SpectrogramGenerator2(BufferedImage image, double length, double minFreq, double maxFreq, int sampleRate) {
        this.image = image;
        this.minFreq = minFreq;
        this.maxFreq = maxFreq;
        this.length = length;
        this.sampleRate = sampleRate;

        pixelWidthInSamples = (int) ((((double) sampleRate) * length) / (double) image.getWidth());
        pixelHeightInHz = (maxFreq - minFreq) / (double) image.getHeight();

        data = new float[image.getWidth()][image.getHeight()];
        generators = new SoundPipeline[image.getHeight()];
        intensities = new StaticValue[image.getHeight()];
    }

    public SoundBuffer generate() {
        generateData();
        System.out.println("Data Generated");
        populateGenerators();
        System.out.println("Generators Populated");
        SoundBuffer result = new SoundBuffer(sampleRate, new float[0]);
        for(int i = 0; i < image.getWidth(); i++) {
            float[] samples = new float[pixelWidthInSamples];
            updateIntensities(i);
            for(SoundPipeline pipeline : generators) {
                pipeline.run(1);
                SoundBuffer buffer = ((Recorder) pipeline.getHandlers().get(pipeline.getHandlers().size() - 1)).getSound();
                for(int j = 0; j < pixelWidthInSamples; j++) {
                    samples[j] += buffer.get(j);
                }
            }
            result.append(new SoundBuffer(sampleRate, samples));
        }
        return result;
    }

    private void generateData() {
        for(int i = 0; i < image.getWidth(); i++) {
            for(int j = 0; j < image.getHeight(); j++) {
                //Color color = new Color(image.getRGB(i, (image.getHeight() - j) - 1));
                Color color = new Color(image.getRGB(i, j));
                int grayscale = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                double intensity = ((double) grayscale / 255D) * ((double) color.getAlpha() / 255D);
                data[i][j] = (float) intensity;
            }
        }
    }

    private void populateGenerators() {
        for(int i = 0; i < image.getHeight(); i++) {
            SoundPipeline pipeline = new SoundPipeline(sampleRate, pixelWidthInSamples);
            double frequency = (pixelHeightInHz * (double) i) + minFreq;
            SineGenerator toneGenerator = new SineGenerator(new StaticValue((float) frequency));
            pipeline.add(toneGenerator);
            StaticValue intensity = new StaticValue(0);
            pipeline.add(new Amplifier(intensity));
            pipeline.add(new Amplifier(new StaticValue(1F / (float) image.getHeight())));
            Recorder recorder = new Recorder();
            pipeline.add(recorder);
            intensities[i] = intensity;
            generators[i] = pipeline;
        }
    }

    private void updateIntensities(int index) {
        System.out.println("Intensities updated\n...");
        for(int i = 0; i < image.getHeight(); i++) {
            intensities[i].setValue(data[index][i]);
        }
    }
}
