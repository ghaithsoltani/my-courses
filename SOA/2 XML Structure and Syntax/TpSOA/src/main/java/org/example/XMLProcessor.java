package org.example;

import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.awt.Desktop;
import java.io.File;

public class XMLProcessor {
    public static void main(String[] args) {
        String xmlPath = "src/main/resources/data.xml";
        String xsdPath = "src/main/resources/data.xsd";
        String xslPath = "src/main/resources/transform.xsl";
        String outputPath = "src/main/resources/output.html";

        try {
            // Step 1: validate XML against XSD
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
            System.out.println("XML valide selon le schéma XSD.");

            // Step 2: transform XML to HTML with XSLT
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File(xslPath)));
            transformer.transform(new StreamSource(new File(xmlPath)), new StreamResult(new File(outputPath)));
            System.out.println("Transformation réussie. Résultat enregistré dans : " + outputPath);

            // Step 3: open the HTML file in the browser (Ubuntu-safe)
            File html = new File(outputPath);
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(html.toURI());
            } else {
                new ProcessBuilder("xdg-open", html.getAbsolutePath()).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
