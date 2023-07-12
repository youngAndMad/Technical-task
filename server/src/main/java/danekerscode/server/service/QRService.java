package danekerscode.server.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface QRService {
    byte [] getQR() throws Exception;
}
