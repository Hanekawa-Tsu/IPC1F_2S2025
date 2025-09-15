import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class Actividad6 {

    private String crearPDF;
    
    public Actividad6(String archivo) {
        //Ruta donde se guardara el PDF
        this.crearPDF = System.getProperty("user.home") + "/Desktop/" + archivo;
    }

    public void paises(String titulo){ //Metodo para agregar los paises
        Document documento = new Document(); //Crear el documento
        
        try{
            PdfWriter.getInstance(documento, new FileOutputStream(this.crearPDF)); //Crear el PDF
            documento.open(); //Abrir el documento

            documento.add (new Paragraph(titulo)); //Titulo del documento
            documento.add (new Paragraph("\n "));

            PdfPTable tabla = new PdfPTable(3);
            tabla.addCell("Pais"); // addCell peromite agregar texto en las columnas
            tabla.addCell("Capital");
            tabla.addCell("Poblacion");

            tabla.addCell("Guatemala");
            tabla.addCell("Ciudad de Guatemala");
            tabla.addCell("17 millones");

            tabla.addCell("Mexico");
            tabla.addCell("Ciudad de Mexico");
            tabla.addCell("126 millones");

            tabla.addCell("España");
            tabla.addCell("Madrid");
            tabla.addCell("47 millones");

            tabla.addCell("Japon");
            tabla.addCell("Tokyo");
            tabla.addCell("125 millones");

            documento.add(tabla); //Agregar la tabla al documento
            
            System.out.println("PDF creado exitosamente en: " + this.crearPDF);
        
        } catch (DocumentException | FileNotFoundException e){
            e.printStackTrace();
        } finally {
            if (documento.isOpen()) {
                documento.close(); //Cerrar el documento
            }
        }
    }
    
}

class GeneradorDePDF {

    public static void main(String[] args) {
        
        Actividad6 miGenerador = new Actividad6("Actividad6.pdf");
        
        miGenerador.paises("Reporte de Países y Capitales");
    }
}
