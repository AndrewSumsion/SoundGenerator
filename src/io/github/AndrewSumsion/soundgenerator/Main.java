package io.github.AndrewSumsion.soundgenerator;

import io.github.AndrewSumsion.soundgenerator.valuecontrollers.LFO;
import io.github.AndrewSumsion.soundgenerator.valuecontrollers.PanController;
import io.github.AndrewSumsion.soundgenerator.valuecontrollers.StaticValue;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static AudioFormat format;

    public static void main(String[] args) throws Exception {
        BufferedImage image = ImageIO.read(new File("C:/Users/andre/Workspace/spectrostuff/black.png"));

        System.out.println("Image loaded...");

        SpectrogramGenerator generator = new SpectrogramGenerator(image, 10, 0, 22050, 44100);
        SoundBuffer result = generator.generate();
        System.out.println("Finished generating, exporting...");
        result.export(new File("C:/Users/andre/Workspace/spectrostuff/black.wav"));
    }
}
