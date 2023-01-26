package com.arcade.process;

import java.io.ByteArrayOutputStream;
import java.util.logging.Logger;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageProcessing {
    private final static Logger logger = Logger.getLogger(ImageProcessing.class.getName());

    public static byte[] compressImage(byte[] image) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(image);
        deflater.finish();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(image.length);
        byte[] tempImage = new byte[4 * 1024];

        while (!deflater.finished()) {
            int size = deflater.deflate(tempImage);
            byteArrayOutputStream.write(tempImage, 0, size);
        }
        try {
            byteArrayOutputStream.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] image) {
        Inflater inflater = new Inflater();
        inflater.setInput(image);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(image.length);
        byte[] tempImage = new byte[4 * 1024];

        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tempImage);
                outputStream.write(tempImage, 0, count);
            }
            outputStream.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return outputStream.toByteArray();
    }
}
