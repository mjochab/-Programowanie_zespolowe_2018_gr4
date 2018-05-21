
package protolabraportpdf;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException, DocumentException, IOException {
    ProtoLabRaportPDF app = new ProtoLabRaportPDF();
    ResultSet rs;
    ProtoLabRaportPDF.savePdf();
   rs=ProtoLabRaportPDF.executeQuery();
   ProtoLabRaportPDF.createDocument(rs);
   
    }
}
