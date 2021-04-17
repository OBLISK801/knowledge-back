package com.lei.obtain.utils;

import com.lei.obtain.vo.PdfVO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;

/**
 * @Author LEI
 * @Date 2021/4/16 21:17
 * @Description TODO
 */
public class PdfUtils {
    /**
     * 读取pdf中文字信息(全部)
     */
    public static PdfVO READPDF(String inputFile){
        //创建文档对象
        PDDocument doc;
        PdfVO vo = new PdfVO();
        String content;
        try {
            //加载一个pdf对象
            doc =PDDocument.load(new File(inputFile));
            //获取一个PDFTextStripper文本剥离对象
            PDFTextStripper textStripper =new PDFTextStripper("GBK");
            content=textStripper.getText(doc);
            vo.setContent(content);
//            System.out.println("内容:"+content);
//            System.out.println("全部页数"+doc.getNumberOfPages());
            //关闭文档
            doc.close();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return vo;
    }
}
