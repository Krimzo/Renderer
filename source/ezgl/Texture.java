package ezgl;

import math.*;
import window.*;

import javax.imageio.*;
import java.awt.image.*;
import java.io.File;

import static org.lwjgl.opengl.GL33.*;

public class Texture extends GLRequired {
    private final int buffer;

    public Texture(GLContext context, Int2 size, int[] pixelData) {
        super(context);
        buffer = glGenTextures();
        if (buffer == 0) {
            throw new Error("Could not create texture buffer!");
        }
        glBindTexture(GL_TEXTURE_2D, buffer);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, size.x, size.y, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelData);
        glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    public Texture(GLContext context, BufferedImage image) {
        this(context, new Int2(image.getWidth(), image.getHeight()), getImageData(image));
    }
    public Texture(GLContext context, String imagePath) throws Exception {
        this(context, ImageIO.read(new File(imagePath)));
    }
    public void finalize() {
        glDeleteTextures(buffer);
    }

    public static int getIndex(int width, int x, int y) {
        return y * width + x;
    }
    public static int[] getImageData(BufferedImage image) {
        int[] data = toIntBuffer(((DataBufferByte) image.getRaster().getDataBuffer()).getData());
        for(int y = 0; y < image.getHeight() / 2; y++) {
            for(int x = 0; x < image.getWidth(); x++) {
                final int temp = data[getIndex(image.getWidth(), x, y)];
                data[getIndex(image.getWidth(), x, y)] = data[getIndex(image.getWidth(), x, (image.getHeight() - 1 - y))];
                data[getIndex(image.getWidth(), x, (image.getHeight() - 1 - y))] = temp;
            }
        }
        return data;
    }
    public static int[] toIntBuffer(byte[] pixelData) {
        int[] intBuffer = new int[pixelData.length / 3];
        for(int i = 0; i < intBuffer.length; i++) {
            intBuffer[i] = (0xFF << 24) |
                    ((pixelData[i * 3] & 0xFF) << 16) |
                    ((pixelData[i * 3 + 1] & 0xFF) << 8) |
                    (pixelData[i * 3 + 2] & 0xFF);
        }
        return intBuffer;
    }

    public void setWrap(Int2 modes) {
        glBindTexture(GL_TEXTURE_2D, buffer);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, modes.x);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, modes.y);
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    public void setFilters(Int2 filters) {
        glBindTexture(GL_TEXTURE_2D, buffer);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filters.x);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filters.y);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void bind() {
        bind(0);
    }
    public void bind(int slot) {
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, buffer);
    }
    public static void unbind(int slot) {
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
