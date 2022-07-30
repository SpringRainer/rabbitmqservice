package com.example.zxingqrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/18 23:11
 */
//@Component
public class BinaryPictureUtils extends JFrame {
    private static final int width = 100;

    private static final int height = 100;

    private BufferedImage image = null;

    public byte[] encodeUrl(String url) throws WriterException, IOException {
        // 显示图片
        setSize(600,600);
        JPanel jPanel = new JPanel();
        add(jPanel);

        // 设置二维码参数 长100 宽100 容错等级高 使用map存储参数 内间距
        Map<EncodeHintType, Object> params = new LinkedHashMap<>();
        params.put(EncodeHintType.MARGIN,1);
        params.put(EncodeHintType.CHARACTER_SET, "utf-8");
        params.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        // 字符串转二维码矩阵
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, width, height, params);

        image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        setVisible(true);

        // 创建字节输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        return outputStream.toByteArray();
    }

    public String decodePicture(byte[] picture) throws IOException, NotFoundException {
        MultiFormatReader multiFormatReader = new MultiFormatReader();

        // 灰度处理
        LuminanceSource luminanceSource = new BufferedImageLuminanceSource(ImageIO.read(new ByteArrayInputStream(picture)));

        // 织入binarybitmap
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));

        // 定义解码参数
        Map<DecodeHintType, Object> params = new LinkedHashMap<>();
        params.put(DecodeHintType.CHARACTER_SET, "utf-8");

        // 解码
        Result result = multiFormatReader.decode(binaryBitmap, params);

        return result.getText();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 100,100, null);
    }
}
