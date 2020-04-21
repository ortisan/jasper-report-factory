import br.ortiz.report.factory.processor.FieldProcessor;
import br.ortiz.to.Person;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {

        FieldProcessor fieldProcessor = new FieldProcessor();
        String jrxml = fieldProcessor.generateJrxml();
        System.out.println("jrxml = " + jrxml);
        try (InputStream jrxmlInput = new ByteArrayInputStream(jrxml.getBytes())) {
            JasperDesign design = JRXmlLoader.load(jrxmlInput);
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            System.out.println("jasperReport = " + jasperReport);

            final Person person = new Person();
            person.setName("Marcelo");
            person.setAge(32);
            person.setBirth(LocalDate.now());
            person.setEmail("tentativafc@gmail.com");

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Arrays.asList(person));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);
            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new FileOutputStream("Example" + System.currentTimeMillis() + ".pdf")));
            pdfExporter.exportReport();
        }
    }
}

