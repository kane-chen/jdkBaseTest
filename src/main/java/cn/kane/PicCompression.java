package cn.kane;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 压缩图片 创建图片缩略图
 * 
 * @author slzs
 */
public class PicCompression {

    /**
     * 压缩图片方法
     * 
     * @param oldFile 将要压缩的图片
     * @param width 压缩宽
     * @param height 压缩高
     * @param quality 压缩清晰度 <b>建议为1.0</b>
     * @param smallIcon 压缩图片后,添加的扩展名（在图片后缀名前添加）
     * @param percentage 是否等比压缩 若true宽高比率将将自动调整
     * @author slzs
     * @return 如果处理正确返回压缩后的文件名 null则参数可能有误
     */
    public static String doCompress(String oldFile, int width, int height, float quality,
            String smallIcon, boolean percentage) {
        if (oldFile != null && width > 0 && height > 0) {
            Image srcFile = null;
            String newImage = null;
            try {
                File file = new File(oldFile);
                // 文件不存在
                if (!file.exists()) {
                    return null;
                }
                /*读取图片信息*/
                srcFile = ImageIO.read(file);
                int new_w = width;
                int new_h = height;
                if (percentage) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) srcFile.getWidth(null)) / (double) width + 0.1;
                    double rate2 = ((double) srcFile.getHeight(null)) / (double) height + 0.1;
                    double rate = rate1 < rate2 ? rate1 : rate2;
                    new_w = (int) (((double) srcFile.getWidth(null)) / rate);
                    new_h = (int) (((double) srcFile.getHeight(null)) / rate);
                }
                /* 宽高设定*/
                BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
                tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);
                /*压缩后的文件名 */
                String filePrex = oldFile.substring(0, oldFile.lastIndexOf('.'));
                newImage = filePrex + smallIcon + oldFile.substring(filePrex.length());
                /*压缩之后临时存放位置*/
                FileOutputStream out = new FileOutputStream(newImage);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
                /* 压缩质量 */
                jep.setQuality(quality, true);
                encoder.encode(tag, jep);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                srcFile.flush();
            }
            return newImage;
        } else {
            return null;
        }
    }

    
    public static byte[] doCompress(byte[] sourceBytes, int width, int height, 
            float quality, boolean percentage) {
        byte[] result = null ;
        if (sourceBytes != null && width > 0 && height > 0) {
            Image srcFile = null;
            InputStream srcInput = null ;
            ByteArrayOutputStream outBytes = new ByteArrayOutputStream() ;
            
            try {
                srcInput = new ByteArrayInputStream(sourceBytes);
                srcFile = ImageIO.read(srcInput);
                int new_w = width;
                int new_h = height;
                if (percentage) {
                    double rate1 = ((double) srcFile.getWidth(null)) / (double) width + 0.1;
                    double rate2 = ((double) srcFile.getHeight(null)) / (double) height + 0.1;
                    double rate = rate1 < rate2 ? rate1 : rate2;
                    new_w = (int) (((double) srcFile.getWidth(null)) / rate);
                    new_h = (int) (((double) srcFile.getHeight(null)) / rate);
                }
                BufferedImage bufferedImage = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
                bufferedImage.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outBytes);
                JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(bufferedImage);
                jep.setQuality(quality, true);// 压缩质量 
                encoder.encode(bufferedImage, jep);
                result = outBytes.toByteArray() ;
                
                FileOutputStream outFile = new FileOutputStream("F:/ttt.png");
                outFile.write(result);
                outFile.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                srcFile.flush();
                try{
                    if(null!=srcInput)
                        srcInput.close();
                    if(null!=outBytes)
                        outBytes.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return result ;
    }
    
    //测试
    public static void main(String str[]) throws IOException {
        System.out.println(PicCompression.doCompress("F:/big.bmp", 500, 100, 1, "_small", true));
//        FileInputStream file = new FileInputStream("F:/big.png");
//        byte[] input = new byte[file.available()] ;
//        file.read(input);
//        byte[] result = PicCompression.doCompress(input, 500, 100, 1, true);
//        System.out.println(result);
//        System.out.print("ok...");
    }
}
