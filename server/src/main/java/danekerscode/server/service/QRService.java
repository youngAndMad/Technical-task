package danekerscode.server.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface QRService {
    void generate(int height, int width) throws Exception;

    byte [] getQR() throws Exception;
}
