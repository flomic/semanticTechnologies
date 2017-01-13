import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.model.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by christine on 10.01.17.
 */
public class Writer {

    public static void writeModelToFile (String fileName, Model results) throws FileNotFoundException {
        FileOutputStream out = new FileOutputStream(fileName);
        RDFWriter writer = Rio.createWriter(RDFFormat.RDFXML, out);

        try {
            writer.startRDF();
            for (Statement st: results) {
                writer.handleStatement(st);
            }
            writer.endRDF();
        }
        catch (RDFHandlerException e) {
            // oh no, do something!
            System.err.println("Error");
        }
    }


}
