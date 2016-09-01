/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.jobs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 *
 * @author erichake
 */
public class StringCompressionUtils {

    /**
     * Compress data.
     * @param bytesToCompress is the byte array to compress.
     * @return a compressed byte array.
     * @throws java.io.IOException
     */
    public static byte[] Compress(byte[] bytesToCompress) throws IOException {
        // Compressor with highest level of compression.
        Deflater compressor=new Deflater(Deflater.BEST_COMPRESSION);
        compressor.setInput(bytesToCompress); // Give the compressor the data to compress.
        compressor.finish();

        // Create an expandable byte array to hold the compressed data.
        // It is not necessary that the compressed data will be smaller than
        // the uncompressed data.
        ByteArrayOutputStream bos=new ByteArrayOutputStream(bytesToCompress.length);

        // Compress the data
        byte[] buf=new byte[bytesToCompress.length+100];
        while (!compressor.finished()) {
            bos.write(buf, 0, compressor.deflate(buf));
        }

        bos.close();

        // Get the compressed data
        return bos.toByteArray();
    }

    /**
     * Decompress data.
     * @param compressedBytes is the compressed byte array.
     * @return decompressed byte array.
     * @throws java.io.IOException
     * @throws java.util.zip.DataFormatException
     */
    public static byte[] Decompress(byte[] compressedBytes) throws IOException, DataFormatException {
        // Initialize decompressor.
        Inflater decompressor=new Inflater();
        decompressor.setInput(compressedBytes);  // Give the decompressor the data to decompress.
        decompressor.finished();

        // Create an expandable byte array to hold the decompressed data.
        // It is not necessary that the decompressed data will be larger than
        // the compressed data.
        ByteArrayOutputStream bos=new ByteArrayOutputStream(compressedBytes.length);

        // Decompress the data
        byte[] buf=new byte[compressedBytes.length+100];
        while (!decompressor.finished()) {
            bos.write(buf, 0, decompressor.inflate(buf));
        }

        bos.close();

        // Get the decompressed data.
        return bos.toByteArray();
    }



}
