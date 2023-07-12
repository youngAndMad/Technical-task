package danekerscode.server.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import danekerscode.server.service.QRService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Service
public class QRServiceImpl implements QRService {

    @Value("${qr.charset}")
    private String charset;
    @Value("${qr.url}")
    private String qrURL;
    @Value("${qr.image.path}")
    private String imagePath;

    @Scheduled(cron = "0 0 0 * * *")
    public void generate() throws Exception {
        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(qrURL.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, 400, 400);

        MatrixToImageWriter.writeToFile(
                matrix,
                imagePath.substring(imagePath.lastIndexOf('.') + 1),
                new File(imagePath));
    }

    @Override
    public byte[] getQR() throws Exception {

        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

}
