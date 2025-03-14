package com.changyi.chy.commons.util;

import com.changyi.chy.commons.exception.CheckParamException;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * 图片工具类
 *
 * @author YuRuizhi
 * @date 2020/1/17
 */
@Slf4j
public class ImageUtil {


    /**
     * 图片的url获取图片base64.
     * <p>通过下载二进制流</p>
     *
     * @param     imgUrl    图片url
     * @return    string    返回图片base64的字符串
     */
    public static String image2Base64(String imgUrl, boolean isUrlSafe){
        String base64 = "";
        try {
            // 字节流转换字节数组
            byte[] bytes = image2byte(imgUrl);
            // 对字节数组Base64编码
            if (isUrlSafe) {
                base64 = new String(Base64Util.encodeUrlSafe(bytes), Charsets.UTF_8);
            } else {
                base64 = new String(Base64Util.encode(bytes), Charsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }

    /**
     * 获取图片base64.
     *
     * @param     file      文件
     * @return    string    返回图片base64的字符串
     */
    public static String image2Base64(File file, boolean isUrlSafe ){
        String base64 = "";
        try {
            BufferedImage bi = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();

            // 对字节数组Base64编码
            if (isUrlSafe) {
                base64 = new String(Base64Util.encodeUrlSafe(bytes), Charsets.UTF_8);
            } else {
                base64 = new String(Base64Util.encode(bytes), Charsets.UTF_8);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    /**
     * 图片url读取文件大小. 单位：KB
     */
    public static float getImageLength(String imgUrl) {
        float imgSize = 0;
        try {
            // 字节流转换字节数组
            byte[] bytes = image2byte(imgUrl);
            // 转换
            if (null != bytes) {
                imgSize = byte2kb(bytes.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgSize;
    }

    public static File getFileByUrl(String fileUrl) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        BufferedOutputStream stream = null;
        InputStream inputStream = null;
        File file = null;
        try {
            URL imageUrl = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while( (len=inputStream.read(buffer)) != -1 ){
                outStream.write(buffer, 0, len);
            }
            file = File.createTempFile("pattern", ".jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fileOutputStream);
            stream.write(outStream.toByteArray());
        } catch (Exception e) {
            System.out.println("创建服务器图片异常：" + e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (stream != null) {
                    stream.close();
                }
                outStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 图片URL 转 字节数组
     * @param  imgUrl 图片url
     * @return
     * @throws CheckParamException
     */
    public static byte[] image2byte(String imgUrl) {
        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try {
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            // 设置referer
            httpUrl.setRequestProperty("referer", "https://chyri-staging-stooltracker-backend.app.maiscrm.com/");
            httpUrl.connect();
            // 通过输入流获取图片数据
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();
            outStream = new ByteArrayOutputStream();
            // 创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            // 每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            // 使用一个输入流从buffer里把数据读取出来
            while( (len=is.read(buffer)) != -1 ){
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 字节流转换字节数组
            byte[] data = outStream.toByteArray();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrl != null) {
                httpUrl.disconnect();
            }
        }
        return null;
    }

    /**
     * 根据图片Base64字符串获取图片文件
     *
     * @param imgBase64 图片Base64字符串
     * @param imgPath   待保存的图片路径
     * @return
     */
    public static boolean getImgBase64ToImgFile(String imgBase64, String imgPath) {
        boolean flag = false;
        try {
            // Base64解码
            byte[] bytes = Base64.getDecoder().decode(imgBase64);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imgPath);
            out.write(bytes);
            out.flush();
            out.close();
            flag = true;
        } catch (Exception e) {
            log.error("base64获取图片异常:", e);
        }
        return flag;
    }

    /**
     * 将获取到的字节数转换为KB，MB模式
     * @param bytes
     * @return
     */
    public static float byte2kb(long bytes){
        BigDecimal fileSize = new BigDecimal(bytes);
        // BigDecimal megabyte = new BigDecimal(1024 * 1024);
        // float mbValue = fileSize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();

        BigDecimal kilobyte = new BigDecimal(1024);
        float  kbValue = fileSize.divide(kilobyte, 2, BigDecimal.ROUND_UP).floatValue();

        return kbValue;
    }



    public static void resizeImage(File inFile, int width, int height) {
        File outFile = null;
        try {
            outFile = File.createTempFile("pattern", ".jpg");
            System.out.println("临时文件创建成功：" + outFile.getCanonicalPath());
            Thumbnails.of(inFile).size(width,height).toFile(outFile);
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    public static File compressImage(File inFile, float scale) {
        File outFile = null;
        try {
            outFile = File.createTempFile("pattern", ".jpg");
            System.out.println("临时文件创建成功：" + outFile.getCanonicalPath());
            Thumbnails.of(inFile).scale(scale).toFile(outFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFile;
    }

    /**
     * 递归压缩图片大小
     *
     * @param   file          文件
     * @param   targetSize    限制目标大小
     */
    public static File nounPressImage(File file, int targetSize) {
        long fileLength = file.length();
        float fileSize = byte2kb(fileLength);
        System.out.println(">> 图片大小是：" + fileSize + "KB");
        if (targetSize < fileSize) {
            file = compressImage(file, 0.5f);
            nounPressImage(file, 1024);
        }
        return file;
    }

    public static void main(String[] args) throws Exception {
        // File inFile = getFileByUrl("https://chyri-staging-stooltracker.oss-cn-hangzhou.aliyuncs.com/images/pages/1581667055050WechatIMG2567.jpeg");
        // File outFile = ImageUtil.nounPressImage(inFile, 1024);
        // String base64 = ImageUtil.image2Base64(outFile);

        String imgUrl = "https://resources.vchangyi.com/common/20190125/845C3A9F0A69039F72E7C760BCCE9930/82BB1BC67F0000013D06DA3196FDC20F.jpg?atId=82BB1BC67F0000013D06DA3196FDC20F";
        String base64 = image2Base64(imgUrl, false);
        System.out.println(base64);
        Boolean x = getImgBase64ToImgFile(base64, "/Users/yuruizhi/xxx");
    }
}
