package com.utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class ImageUtil {

    /**
     * 根据原图与裁切size截取局部图片
     * @param srcImg
     * @return
     * @throws Exception
     */
    public static BufferedImage cutBufferedImage(File srcImg) {
        //图片左上角的点 坐标为(0, 0)
        int x = 0;      //截图的X坐标
        int y = 50;     //截图的Y坐标
        int width = 384;  //图片的宽
        int height = 250; //图片的高

        //QR_(0,50)_384_250

        //需要截取部分的坐标和大小
        Rectangle rect = new Rectangle(x, y, width, height);


        if (srcImg.exists()) {
            FileInputStream fis = null;
            ImageInputStream iis = null;
            try {
                fis = new FileInputStream(srcImg);
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
                String suffix = null;
                // 获取图片后缀
                if (srcImg.getName().indexOf(".") > -1) {
                    suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if (suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase() + ",") < 0) {
                    System.out.println("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return null;
                }
                // 将FileInputStream 转换为ImageInputStream
                iis = ImageIO.createImageInputStream(fis);

                // 根据图片类型获取该种类型的ImageReader
                ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
                reader.setInput(iis, true);
                ImageReadParam param = reader.getDefaultReadParam();
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                return bi;

                //图片输出到output
                //ImageIO.write(bi, suffix, output);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null)
                        fis.close();

                    if (iis != null)
                        iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("the src image is not exist.");
        }

        return null;
    }
}
