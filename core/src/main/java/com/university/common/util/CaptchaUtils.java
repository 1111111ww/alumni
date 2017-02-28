package com.university.common.util;

import org.patchca.background.BackgroundFactory;
import org.patchca.color.ColorFactory;
import org.patchca.filter.predefined.*;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.text.renderer.BestFitTextRenderer;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created by songrenfei on 16/7/18.
 */
public class CaptchaUtils {

    private Random random = new Random();

    public static void main(String[] args) throws FileNotFoundException {
        CaptchaUtils captchaUtils = new CaptchaUtils();

        File file = new File("/Users/songrenfei/Documents/temp/captcha.png");
        FileOutputStream fos = new FileOutputStream(file);
        captchaUtils.createCaptcha(fos);
    }



    // ok
    public String createCaptcha(OutputStream os){
        String token = "";
        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        this.init(cs);
        Integer ra = random.nextInt(5);
        ra = 3;
        switch (ra) {
            case 0:
                cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
                break;
            case 1:
                cs.setFilterFactory(new MarbleRippleFilterFactory());
                break;
            case 2:
                cs.setFilterFactory(new DoubleRippleFilterFactory());
                break;
            case 3:
                cs.setFilterFactory(new WobbleRippleFilterFactory());
                break;
            case 4:
                cs.setFilterFactory(new DiffuseRippleFilterFactory());
                break;
        }

        try {

            token = EncoderHelper.getChallangeAndWriteImage(cs, "png", os);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }

    // ok
    private void init(ConfigurableCaptchaService cs){

        MyColorFactory myColorFactory = new MyColorFactory();
        cs.setColorFactory(myColorFactory);

        RandomFontFactory fontFactory = new RandomFontFactory();
        fontFactory.setMaxSize(32);
        fontFactory.setMinSize(28);
        cs.setFontFactory(fontFactory);

        // 测试 暂时去掉背景干扰
        //MyCustomBackgroundFactory backgroundFactory = new MyCustomBackgroundFactory();
        //cs.setBackgroundFactory(backgroundFactory);

        BestFitTextRenderer textRenderer = new BestFitTextRenderer();
        textRenderer.setBottomMargin(3);
        textRenderer.setTopMargin(3);
        cs.setTextRenderer(textRenderer);

        RandomWordFactory wf = new RandomWordFactory();
        wf.setCharacters("23456789abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ");
        wf.setMaxLength(6);
        wf.setMinLength(4);
        cs.setWordFactory(wf);

        cs.setWidth(102);
        cs.setHeight(32);
    }

    // ok
    private class MyColorFactory implements ColorFactory {

        @Override
        public Color getColor(int x) {
            int[] c = new int[3];
            int i = random.nextInt(c.length);
            for (int fi = 0; fi < c.length; fi++) {
                if (fi == i) {
                    c[fi] = random.nextInt(71);
                } else {
                    c[fi] = random.nextInt(256);
                }
            }
            return new Color(c[0], c[1], c[2]);
        }
    }


    // 自定义验证码图片背景,主要画一些噪点和干扰线 ok
    private class MyCustomBackgroundFactory implements BackgroundFactory {

        @Override
        public void fillBackground(BufferedImage bufferedImage) {
            Graphics graphics = bufferedImage.getGraphics();

            // 验证码图片的宽高
            int imgWidth = bufferedImage.getWidth();
            int imgHeight = bufferedImage.getHeight();

            // 填充为白色背景
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, imgWidth, imgHeight);

            // 画100个噪点(颜色及位置随机)
            for(int i = 0; i < 100; i++) {
                // 随机颜色
                int rInt = random.nextInt(255);
                int gInt = random.nextInt(255);
                int bInt = random.nextInt(255);

                graphics.setColor(new Color(rInt, gInt, bInt));

                // 随机位置
                int xInt = random.nextInt(imgWidth - 3);
                int yInt = random.nextInt(imgHeight - 2);

                // 随机旋转角度
                int sAngleInt = random.nextInt(360);
                int eAngleInt = random.nextInt(360);

                // 随机大小
                int wInt = random.nextInt(6);
                int hInt = random.nextInt(6);

                graphics.fillArc(xInt, yInt, wInt, hInt, sAngleInt, eAngleInt);

                // 画5条干扰线
                if (i % 20 == 0) {
                    int xInt2 = random.nextInt(imgWidth);
                    int yInt2 = random.nextInt(imgHeight);
                    graphics.drawLine(xInt, yInt, xInt2, yInt2);
                }
            }
        }
    }
}
