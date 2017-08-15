package com.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

public class QRCodeDecoderHandler {

    /**
     * 解码二维码
     * @param imgPath
     * @return String
     */
    public String decoderQRCode(String imgPath) {

        // QRCode 二维码图片的文件
        File imageFile = new File(imgPath);

        BufferedImage bufImg = null;
        String decodedData = null;
        try {
            bufImg = ImageIO.read(imageFile);

            QRCodeDecoder decoder = new QRCodeDecoder();
            decodedData = new String(decoder.decode(new J2SEImage(bufImg)),"gb2312");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (DecodingFailedException dfe) {
            System.out.println("Error: " + dfe.getMessage());
            dfe.printStackTrace();
        }
        return decodedData;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        QRCodeDecoderHandler handler = new QRCodeDecoderHandler();
        //String imgPath = "C:/二维码生成/Relieved_QRCode.png";
        String imgPath = "C:/二维码生成/Relieved_QRCode.png";
        String decoderContent = handler.decoderQRCode(imgPath);
        System.out.println("解析结果如下：");
        System.out.println(decoderContent);
        System.out.println("========decoder success!!!");
    }

    class J2SEImage implements QRCodeImage {
        BufferedImage bufImg;

        public J2SEImage(BufferedImage bufImg) {
            this.bufImg = bufImg;
        }

        public int getWidth() {
            return bufImg.getWidth();
        }

        public int getHeight() {
            return bufImg.getHeight();
        }

        public int getPixel(int x, int y) {
            return bufImg.getRGB(x, y);
        }

    }
}
