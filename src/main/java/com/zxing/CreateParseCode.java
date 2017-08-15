package com.zxing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.zxing.BufferedImageLuminanceSource;


/**
 * @Description: (普通二维码生成) 和解析
 * @author：Relieved
 * @date：2014-11-7 下午04:42:35
 */
public class CreateParseCode {
    public static void main(String[] args) throws IOException, WriterException {
        CreateParseCode cpCode = new CreateParseCode();
//		生成二维码
        cpCode.createCode();
//		解析二维码
        cpCode.parseCode(new File("C:/二维码生成/TDC-test.png"));

    }

    /**
     * 二维码的生成
     */
    public void createCode() {
        String text = "http://blog.csdn.net/gao36951";
        int width = 300;
        int height = 300;
        // 二维码的图片格式
        String format = "png";
        /**
         * 设置二维码的参数
         */
        HashMap hints = new HashMap();
        // 内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            // 生成二维码
            File outputFile = new File("C:" + File.separator + "二维码生成" + File.separator + "TDC-test.png");

            MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 二维码的解析
     *
     * @param file
     */
    public void parseCode(File file) {
        try {
            MultiFormatReader formatReader = new MultiFormatReader();

            if (!file.exists()) {
                return;
            }

            BufferedImage image = ImageIO.read(file);

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

