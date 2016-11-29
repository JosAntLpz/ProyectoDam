package com.izv.dam.newquip.pdf;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

public class Pdf{

    private static final String NOMBRE_CARPETA_APP = "PDFsQuip";
    private static final String GENERADOS = "MisArchivos";
    private AppCompatActivity yo;
    private Context c;
    ImageView btnPdf;

    public Pdf(AppCompatActivity yo, Context c){
        this.yo = yo;
        this.c = c;
    }
    public Pdf(){

    }

    public void generarPDF(String Titulo, String Nota, String RutaImagen ){
        String NOMBRE_ARCHIVO = Titulo + ".pdf";
        String tarjetaSD = Environment.getExternalStorageDirectory().toString();
        File pdfDir = new File(tarjetaSD + File.separator + NOMBRE_CARPETA_APP);
        Document document = new Document(PageSize.A4);

        if(!pdfDir.exists()){
            pdfDir.mkdir();
        }

        File pdfSubDir = new File(pdfDir.getPath() + File.separator + GENERADOS);
        if(!pdfSubDir.exists()){
            pdfSubDir.mkdir();
        }

        String nombre_completo = Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_APP +
                File.separator + GENERADOS + File.separator + NOMBRE_ARCHIVO;

        File outputfile = new File(nombre_completo);
        if(outputfile.exists()){
            outputfile.delete();
        }

        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(nombre_completo));
            document.open();
            document.addTitle("titulo");

            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            String htmlToPDF = "<html><head></head><body>" +
                    "<h1> Titulo: " + Titulo + "</h1>" +
                    "<h1> Nota: " + Nota + "</h1>" +
                    "<img src=\"" + RutaImagen + "\"/></body></html>";

            try {
                worker.parseXHtml(pdfWriter, document, new StringReader(htmlToPDF));
                document.close();
                Toast.makeText(yo, "Se esta imprimiendo en PDF", Toast.LENGTH_SHORT).show();
                muestraPDF(nombre_completo, yo);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void muestraPDF(String pdf, Context context){
        Toast.makeText(context, "Leyendo el PDF", Toast.LENGTH_SHORT).show();
        File file = new File(pdf);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "aplication/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(intent);

        }catch (ActivityNotFoundException e){
            Toast.makeText(context, "No tiene una aplicacion que abra PDF", Toast.LENGTH_SHORT).show();
        }
    }
}
