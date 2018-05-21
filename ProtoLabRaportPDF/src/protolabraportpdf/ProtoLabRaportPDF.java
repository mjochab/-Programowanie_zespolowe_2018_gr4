package protolabraportpdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ICC_Profile;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
//import javafx.*;

public class ProtoLabRaportPDF {
    
    public static final String FONT="resources/fonts/open-sans.regular.ttf";
    public static final String FONTB="resources/fonts/open-sans.bold.ttf";
    
    protected static Font font10;
    protected static Font font10b;
    protected static Font font12;
    protected static Font font12b;
    protected static Font font14;
    protected static Font font14b;
        public ProtoLabRaportPDF() throws DocumentException,IOException{
        BaseFont bf =BaseFont.createFont(FONT, BaseFont.WINANSI, BaseFont.EMBEDDED);
        BaseFont bfb =BaseFont.createFont(FONTB,BaseFont.WINANSI,BaseFont.EMBEDDED);
        font10=new Font(bf,10);
        font10b= new Font(bfb,10);
        font12=new Font(bf,12);
        font12b=new Font(bfb,12);
        font14=new Font(bf,14);
        font14b=new Font(bfb,14);
        }
    public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException, DocumentException, IOException {
        int rownum = 0;
        BaseConnection base = new BaseConnection();
        Connection conn = base.baseConnection();
        ProtoLabRaportPDF app=new ProtoLabRaportPDF();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM przedmioty");

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnc = rsmd.getColumnCount();

        while (rs.next()) {
            rownum += 1;
        }
        rs.first();

        Document document = new Document(PageSize.A4);
       
        
        String nazwaRaportu = "raport "+LocalDate.now();
        
        nazwaRaportu = nazwaRaportu + ".pdf";
        System.out.println(nazwaRaportu);
        try {
           PdfWriter writer= PdfWriter.getInstance(document, new FileOutputStream(nazwaRaportu));
           writer.setPdfVersion(PdfWriter.VERSION_1_7);
        } catch (FileNotFoundException e) {
            System.out.println("Proces nie może uzyskać dostępu do pliku, ponieważ jest on używany przez inny proces");

        }

       
        //////////////////////////////////////////
        document.open();
        
        Paragraph p;
        
        PdfPTable headerTab=new PdfPTable(2);
        PdfPCell cell=new PdfPCell();
        headerTab.setWidthPercentage(100);
        Image img =Image.getInstance("resources/images/protolab.png");
//        img.setWidthPercentage(20);
        img.scaleAbsolute(new Rectangle(210,68));
//        Chunk c=new Chunk(img,0, -24);
        cell.setImage(img);
//        cell.addElement(c);
        cell.setBorderColor(BaseColor.WHITE);
        headerTab.addCell(cell);
        cell=new PdfPCell();
        cell.setBorderColor(BaseColor.WHITE);
        p =new Paragraph("Rzeszów",font14);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);  
        p = new Paragraph (""+LocalDate.now(),font12);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        headerTab.addCell(cell);
        
        
//        p=new Paragraph();
//        p.setAlignment(Element.ALIGN_LEFT);
//        
//        p.add(c);
//        document.add(img);
//        p =new Paragraph("Rzeszów",font14);
//        p.setAlignment(Element.ALIGN_RIGHT);
//        document.add(p);  
//        p = new Paragraph (""+LocalDate.now(),font12);
//        p.setAlignment(Element.ALIGN_RIGHT);
        document.add(headerTab);
        //tabele z informacjami o dostawcy
        PdfPTable tabInfo= new PdfPTable(2);
        tabInfo.setWidthPercentage(100);
        cell= new PdfPCell();
        cell.setBorderColor(BaseColor.WHITE);
        cell.addElement(new Paragraph("Dostawca",font14b));
        cell.addElement(new Paragraph("Dominik Maga",font12));
        cell.addElement(new Paragraph("zamieszkały",font12));
        tabInfo.addCell(cell);
        cell=new PdfPCell();
        cell.setBorderColor(BaseColor.WHITE);
        cell.addElement(new Paragraph("Odbiorca",font14b));
        cell.addElement(new Phrase("UR Sp. z o.o.",font12));
        cell.addElement(new Phrase("Pigonia 1",font12));
        tabInfo.addCell(cell);
        document.add(tabInfo);
        
        
        //tabele z danymi
        PdfPTable ptab = new PdfPTable(columnc);
        ptab.setWidthPercentage(100);
        ptab.setSpacingAfter(10);
        ptab.setSpacingBefore(10);
        
        ptab.addCell(new Paragraph("ID",font12b));
        ptab.addCell(new Paragraph("Nazwa przedmiotu",font12b));
        ptab.addCell(new Paragraph("Rodzaj przedmiotu",font12b));
        ptab.addCell(new Paragraph("Ile",font12b));
        ptab.addCell(new Paragraph("Stan",font12b));
        
        for (int i = 0; i < rownum; i++) {
            ptab.addCell("" + rs.getString(1));
            ptab.addCell("" + rs.getString(2));
            ptab.addCell("" + rs.getString(3));
            ptab.addCell("" + rs.getString(4));
            ptab.addCell("" + rs.getString(5));
            rs.next();
        }
        rs.first();
        document.add(ptab);
        ptab =new PdfPTable(columnc);
        ptab.setWidthPercentage(100);
        ptab.setSpacingAfter(10);
        ptab.setSpacingBefore(10);

       
        ptab.addCell(new Paragraph("ID",font12b));
        ptab.addCell(new Paragraph("Nazwa przedmiotu",font12b));
        ptab.addCell(new Paragraph("Rodzaj przedmiotu",font12b));
        ptab.addCell(new Paragraph("Ile",font12b));
        ptab.addCell(new Paragraph("Stan",font12b));
       
        document.add(ptab);
        p=new Paragraph(document.getPageNumber());
        document.add(p);


        document.close();

    
//        Paragraph p=new Paragraph();
//        p.setFont(new Font(Font.FontFamily.HELVETICA,20));
//        Chunk c =new Chunk("d");
//        
//        document.add(new Paragraph("VisitorReport"));
//        document.addTitle(nazwaRaportu);
//        for (int i = 0; i < rownum; i++) {
//            ptab.addCell("" + rs.getString(1));
//            ptab.addCell("" + rs.getString(2));
//            ptab.addCell("" + rs.getString(3));
//            ptab.addCell("" + rs.getString(4));
//            ptab.addCell("" + rs.getString(5));
//            rs.next();
//        }
//        String z=" "+document.getPageNumber();
//        document.bottom(1);
//        document.add(new Paragraph(z));
////        document.newPage();
//         
//
//        System.out.println(document.getPageNumber());
////        document.newPage();
//        System.out.println(document.getPageNumber());
//        document.add(ptab);

 
    }
}
