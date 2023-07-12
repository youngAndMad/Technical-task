package danekerscode.server.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface QRService {
    void generate() throws Exception;

    byte [] getQR() throws Exception;
}
