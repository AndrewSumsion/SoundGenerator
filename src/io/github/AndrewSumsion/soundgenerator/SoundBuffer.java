package io.github.AndrewSumsion.soundgenerator;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Iterator;

public class SoundBuffer implements Iterable<Float> {
    private int sampleRate;
    private float[] data;

    public SoundBuffer(int sampleRate, float[] data) {
        this.sampleRate = sampleRate;
        this.data = data;
    }

    public float[] getData() {
        return data;
    }

    public void setData(float[] data) {
        this.data = data;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public float get(int index) {
        return data[index];
    }

    public void set(int index, float sample) {
        data[index] = sample;
    }

    public int size() {
        return data.length;
    }

    public SoundBuffer subBuffer(int index) {
        return subBuffer(index, data.length - 1);
    }

    public SoundBuffer subBuffer(int index, int end) {
        float[] newData = Arrays.copyOfRange(data, index, end);
        return new SoundBuffer(sampleRate, newData);
    }

    public SoundBuffer append(SoundBuffer other) {
        if(other.getSampleRate() != sampleRate) {
            throw new IllegalArgumentException("Incompatible sample rates");
        }
        float[] result = Arrays.copyOf(data, data.length + other.size());
        System.arraycopy(other.data, 0, result, data.length, other.size());
        data = result;
        return this;
    }

    public int[] encode(int maxValue) {
        int[] result = new int[data.length];

        for(int i = 0; i < result.length; i++) {
            result[i] = (int)(data[i] * (float) maxValue);
        }

        return result;
    }

    public AudioInputStream toAudioInputStream() {
        AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, true);
        ByteBuffer buffer = ByteBuffer.allocate(data.length * 2);
        buffer.order(ByteOrder.BIG_ENDIAN);
        for(int s : encode(Short.MAX_VALUE)) {
            buffer.putShort((short) s);
        }
        ByteArrayInputStream stream = new ByteArrayInputStream(buffer.array());

        return new AudioInputStream(stream, format, data.length);
    }

    public Iterator<Float> iterator() {
        return new Iterator<Float>() {
            private int pos=0;

            public boolean hasNext() {
                return data.length>pos;
            }

            public Float next() {
                return data[pos++];
            }

            public void remove() {
                throw new UnsupportedOperationException("Cannot remove an element of an array.");
            }
        };
    }
    public void export(File file) throws IOException {
        int bytes = 2;

        int chunkSize = 36 + data.length * bytes;

        ByteBuffer buffer = ByteBuffer.allocate(chunkSize + 8);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.put((byte)'R').put((byte)'I').put((byte)'F').put((byte)'F');

        buffer.putInt(chunkSize); // Total file size

        buffer.put((byte)'W').put((byte)'A').put((byte)'V').put((byte)'E');
        buffer.put((byte)'f').put((byte)'m').put((byte)'t').put((byte)' ');

        buffer.putInt(16);
        buffer.putShort((short) 1); // PCM
        buffer.putShort((short) 1); // mono
        buffer.putInt(sampleRate); // Sample rate
        buffer.putInt(sampleRate * bytes); // Byte rate
        buffer.putShort((short) bytes); // bytes per block
        buffer.putShort((short) 16); // bits per sample

        buffer.put((byte)'d').put((byte)'a').put((byte)'t').put((byte)'a');

        buffer.putInt(data.length * bytes);
        for(int sample : encode(Short.MAX_VALUE)) {
            buffer.putShort((short) sample);
        }
        if(!file.exists()) file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        buffer.flip();
        while (buffer.hasRemaining()) {
            out.write(buffer.get());
        }
        out.close();
    }

    public static SoundBuffer importSound(File file) {
        try {
            RandomAccessFile file1 = new RandomAccessFile(file, "r");
            int fileLength = (int) file1.length();
            byte[] data = new byte[(int) file1.length()];
            file1.readFully(data);
            ByteBuffer buffer = ByteBuffer.wrap(data);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            if (!read4chars(buffer).equals("RIFF")) invalid();
            if (buffer.getInt() + 8 != fileLength) invalid();
            if (!read4chars(buffer).equals("WAVE")) invalid();
            if (!read4chars(buffer).equals("fmt ")) invalid();
            if (buffer.getInt() != 16) throw new IOException("File must be PCM");
            if (buffer.getShort() != 1) throw new IOException("File must be PCM");
            if (buffer.getShort() != 1) throw new IOException("File must be mono");
            int sampleRate = buffer.getInt();
            buffer.getInt();
            int bytes = buffer.getShort();
            int bits = buffer.getShort();
            if (bits / 8 != bytes) invalid();
            if (!read4chars(buffer).equals("data")) invalid();
            float[] newData = new float[buffer.getInt() / 2];
            for(int i = 0; i<newData.length; i++) {
                newData[i] = (float)buffer.getShort() / (float)Short.MAX_VALUE;
            }
            return new SoundBuffer(sampleRate, newData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void invalid() throws IOException {
        throw new IOException("Invalid or corrupt file!");
    }

    private static String read4chars(ByteBuffer buffer) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            builder.append((char)buffer.get());
        }
        return builder.toString();
    }

    public SoundBuffer clone() {
        return new SoundBuffer(sampleRate, data.clone());
    }
}
