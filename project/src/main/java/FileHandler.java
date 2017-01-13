import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

import java.io.*;

/**
 * Created by christine on 13.01.17.
 */
public class FileHandler {
    public static Repository readRepositoryFromFile(String fileName){
        Repository repo = new SailRepository(
                new ForwardChainingRDFSInferencer(
                        new MemoryStore()));
        repo.initialize();

        final RepositoryConnection connection = repo.getConnection();

        try {
            connection.add(new File(fileName), null, RDFFormat.TURTLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return repo;
    }
    public static Model readModelFromFile(String filename) {
        final File file = new File(filename);
        InputStream inputStream = null;
        Model results = null;
        try {
            inputStream = new FileInputStream(file);
            results = Rio.parse(inputStream, file.toURL().toString(), RDFFormat.TURTLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;

    }
    public static void writeModelToFile (String fileName, Model results) throws FileNotFoundException {
        FileOutputStream out = new FileOutputStream(fileName);
        RDFWriter writer = Rio.createWriter(RDFFormat.TURTLE, out);

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
