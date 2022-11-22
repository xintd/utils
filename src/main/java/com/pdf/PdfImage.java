package com.pdf;

import com.base64.Base64Util;
import com.google.common.collect.Maps;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: PdfImage.java
 * @Description: 向pdf插入图片/二维码
 * @Author: yml
 * @CreateDate: 2022/11/22
 * @UpdateUser: yml
 * @UpdateDate: 2022/11/22
 * @Version: 1.0.0
 */
@Slf4j
public class PdfImage {

    public static void main(String[] args) {
        try (InputStream srcPdfPath = Files.newInputStream(Paths.get("C:\\Users\\Administrator\\Documents\\2.pdf"))) {
            String newPdfPath = "C:\\Users\\Administrator\\Documents\\2i.pdf";
            String qrCodeBase64 = getQrCodeBase64("https://github.com/xintd");
            String qrCodeImgPath = "C:\\Users\\Administrator\\Documents\\qrcode.png";
            Base64Util.decodeToFile(qrCodeImgPath, qrCodeBase64);
            pdfInsertImage(newPdfPath, srcPdfPath, qrCodeImgPath);
            pdfExistImage(newPdfPath);
            Files.deleteIfExists(Paths.get(qrCodeImgPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * pdf嵌入图片
     *
     * @param newPdfPath 嵌入图片文件
     * @param srcPdfPath 源文件
     * @param imagePath  图片路径地址
     * @return : void
     */
    private static void pdfInsertImage(String newPdfPath, InputStream srcPdfPath, String imagePath) {
        PdfReader pdfReader = null;
        PdfStamper pdfStamper;
        try {
            pdfReader = new PdfReader(srcPdfPath);
            //获取最后一页（计算页面插入）
            int numberOfPages = pdfReader.getNumberOfPages();
            System.out.println("页数  " + numberOfPages);
            FileOutputStream out = new FileOutputStream(newPdfPath);
            pdfStamper = new PdfStamper(pdfReader, out);
            float qrWidth = 12.75f / 25.4f * 72,qrHeight = 12.75f / 25.4f * 72;
            for (int i = 1; i <= numberOfPages; i++) {
                PdfContentByte pdfContentByte = pdfStamper.getUnderContent(i);
                Image qrcodeImage = Image.getInstance(imagePath);
                qrcodeImage.scaleToFit(qrWidth, qrHeight);
                // pdf 坐标 从左到右 从下到上 单位pt
                Rectangle pageSize = pdfReader.getPageSize(i);
                qrcodeImage.setAbsolutePosition(qrWidth / 2, (float) (pageSize.getHeight() - (qrWidth * 1.5)));
                pdfContentByte.addImage(qrcodeImage);
                qrcodeImage.setAbsolutePosition(qrWidth / 2, qrWidth / 2);
                pdfContentByte.addImage(qrcodeImage);
                qrcodeImage.setAbsolutePosition((float) (pageSize.getWidth() - (qrWidth * 1.5)), qrWidth / 2);
                pdfContentByte.addImage(qrcodeImage);
                qrcodeImage.setAbsolutePosition((float) (pageSize.getWidth() - (qrWidth * 1.5)), (float) (pageSize.getHeight() - (qrWidth * 1.5)));
                pdfContentByte.addImage(qrcodeImage);
                pdfContentByte.stroke();
            }
            pdfStamper.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (pdfReader != null) {
                pdfReader.close();
            }
        }
    }

    /**
     * 获取二维码base64
     *
     * @return : java.lang.String
     */
    private static String getQrCodeBase64(String qrCodeContent) {
        String qrCodeBase64 = null;
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            Map<EncodeHintType, Object> hits = Maps.newHashMap();
            hits.put(EncodeHintType.MARGIN, 0);
            BitMatrix matrix = new QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, 200, 200, hits);
            MatrixToImageWriter.writeToStream(matrix, "PNG", stream);
            qrCodeBase64 = Base64Util.encode(stream.toByteArray());
        } catch (Exception e) {
            log.error("生成二维码异常：{}", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return qrCodeBase64;
    }

    /**
     * 读取pdf插入的图片
     *
     * @param pdfFilePath
     * @return : boolean
     */
    private static void pdfExistImage(String pdfFilePath) {
        try (InputStream inputStream = Files.newInputStream(Paths.get(pdfFilePath))) {
            //通过文件名加载文档
            PDDocument document = Loader.loadPDF(inputStream);
            PDPageTree pages = document.getPages();
            Iterator<PDPage> iter = pages.iterator();
            while (iter.hasNext()) {
                PDPage page = iter.next();
                PDResources resources = page.getResources();
                AtomicInteger num = new AtomicInteger();
                resources.getXObjectNames().forEach(e -> {
                    if (resources.isImageXObject(e) && e.getName().contains("Xi")) {
                        try {
                            PDImageXObject image = (PDImageXObject) resources.getXObject(e);
                            // same image to local
                            BufferedImage bImage = image.getImage();
                            ImageIO.write(bImage,"PNG",new File("C:\\Users\\Administrator\\Documents\\"+page.hashCode()+num+".png"));
                            num.getAndIncrement();

                            //读取二维码内容
                            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bImage);
                            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                            Hashtable<DecodeHintType, Comparable> hints = new Hashtable<>();
                            hints.put(DecodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
                            hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
                            Result result = new QRCodeReader().decode(bitmap, hints);
                            System.out.println(result.getText());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
