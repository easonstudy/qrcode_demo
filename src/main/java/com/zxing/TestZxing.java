package com.zxing;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.utils.ImageUtil;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析图片中的二维码截取
 */
public class TestZxing {

    public static void main(String[] args) {
        TestZxing tz = new TestZxing();
        //解析二维码
        tz.parseCode(new File("C:/二维码生成/test.png"), 2);
        //tz.parseCode(new File("C:/二维码生成/cut__1502778977456_test.png"), Boolean.TRUE);
    }

    /**
     * @param file   原图
     * @param method 解析的方式 1:普通二维码  2:截取图中的局部 解析
     */
    public void parseCode(File file, Integer method) {
        try {
            MultiFormatReader formatReader = new MultiFormatReader();
            if (!file.exists()) {
                return;
            }

            BufferedImage image = null;

            switch (method) {
                case 1:
                    image = ImageIO.read(file);
                    break;
                case 2:
                    image = ImageUtil.cutBufferedImage(file);
                    break;
            }
            System.out.println("width:" + image.getWidth() + ", height:" + image.getHeight());

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            Result result = formatReader.decode(binaryBitmap, hints);

            System.out.println("解析结果 = " + result.toString());
            System.out.println("二维码格式类型 = " + result.getBarcodeFormat());
            System.out.println("二维码文本内容 = " + result.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
