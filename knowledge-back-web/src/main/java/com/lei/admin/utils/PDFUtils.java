package com.lei.admin.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @Author LEI
 * @Date 2021/3/9 14:02
 * @Description TODO
 */
public class PDFUtils {
    public  static void HtmlToPdf(String str, String path) throws Exception {
        Document document = new Document();
        PdfWriter pdfwriter = PdfWriter.getInstance(document, new FileOutputStream(path));
        pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
        document.open();

        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
        ByteArrayInputStream is = new ByteArrayInputStream("".getBytes());
        XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, in,is,Charset.forName("UTF-8"));

//        InputStream in = new ByteArrayInputStream(str.getBytes());
//        XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, in, Charset.forName("UTF-8"));
        document.close();
    }
}
