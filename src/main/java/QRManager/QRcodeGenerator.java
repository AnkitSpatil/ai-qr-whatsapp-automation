package QRManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QRcodeGenerator {

    private static final Logger LOGGER = Logger.getLogger(QRcodeGenerator.class.getName());

    /**
     * Generate QR code with proper exception handling
     * @param url The URL to encode
     * @param width The width of the QR code
     * @param height The height of the QR code
     * @param fileName The output file name
     * @return true if QR code generated successfully, false otherwise
     */
    public boolean generateQRcode(String url, int width, int height, String fileName) {
        // Validate input parameters
        if (url == null || url.trim().isEmpty()) {
            LOGGER.log(Level.SEVERE, "URL cannot be null or empty");
            return false;
        }
        if (width <= 0 || height <= 0) {
            LOGGER.log(Level.SEVERE, "Width and height must be positive values");
            return false;
        }
        if (fileName == null || fileName.trim().isEmpty()) {
            LOGGER.log(Level.SEVERE, "File name cannot be null or empty");
            return false;
        }

        LOGGER.log(Level.INFO, "Generating QR Code for URL: {0}", url);
        System.out.println("Generating QR Code for URL: " + url);

        try {
            // Generate QR Code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);

            // Get the path for the output file
            Path path = FileSystems.getDefault().getPath(fileName);

            // Write the QR code to file
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            LOGGER.log(Level.INFO, "QR Code generated successfully at: {0}", fileName);
            System.out.println("QR Code generated successfully! Check the project directory for " + fileName);
            return true;

        } catch (WriterException e) {
            LOGGER.log(Level.SEVERE, "Error encoding data to QR Code", e);
            System.err.println("Error while generating QR Code: " + e.getMessage());
            return false;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing QR Code to file: " + fileName, e);
            System.err.println("Error saving QR Code file: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Invalid arguments for QR Code generation", e);
            System.err.println("Invalid parameters: " + e.getMessage());
            return false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error generating QR Code", e);
            System.err.println("Unexpected error: " + e.getMessage());
            return false;
        } finally {
            LOGGER.log(Level.INFO, "QR Code generation process completed");
            System.out.println("QR Code generation process completed.");
        }
    }
}
