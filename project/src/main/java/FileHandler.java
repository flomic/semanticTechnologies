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

/**
 * Helper class to handle the reading and writing of the files
 */
public class FileHandler {

    /**
     * Takes the path to a turtle file and returns a repository with the information of the file
     * @param filePath
     * @return
     */
    public static Repository readRepositoryFromFile(String filePath){
        Repository repo = new SailRepository(
                new ForwardChainingRDFSInferencer(
                        new MemoryStore()));
        repo.initialize();


        final RepositoryConnection connection = repo.getConnection();

        try {
            connection.add(new File(filePath), null, RDFFormat.TURTLE); //TODO allow also other formats
        } catch (IOException e) {
            e.printStackTrace(); //TODO handle this exception better
        }
        return repo;
    }

    /**
     * Takes the path to a turtle file and returns a model with the contained information
     * @param filePath
     * @return
     */
    public static Model readModelFromFile(String filePath) {
        final File file = new File(filePath);
        InputStream inputStream = null;
        Model results = null;
        try {
            inputStream = new FileInputStream(file);
            results = Rio.parse(inputStream, "urn:absolute:www.example.com/ontologies/project-ontology#", RDFFormat.TURTLE); //TODO allow also other formats
        } catch (Exception e) {
            e.printStackTrace(); //TODO handle this exception better
        }
        return results;

    }

    /**
     * Takes a model and writes it to the specified file.
     * @param filePath
     * @param model
     * @throws FileNotFoundException //TODO handle the exception here
     */
    public static void writeModelToFile (String filePath, Model model) throws FileNotFoundException {
        FileOutputStream out = new FileOutputStream(filePath);
        RDFWriter writer = Rio.createWriter(RDFFormat.TURTLE, out); //TODO allow also other formats
        writer.handleNamespace("", "urn:absolute:www.example.com/ontologies/project-ontology#");
        writer.handleNamespace("owl","http://www.w3.org/2002/07/owl#");
        writer.handleNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        writer.handleNamespace("xml", "http://www.w3.org/XML/1998/namespace");
        writer.handleNamespace("xsd", "http://www.w3.org/2001/XMLSchema#");
        writer.handleNamespace("rdfs", "http://www.w3.org/2000/01/rdf-schema#");

        try {
            writer.startRDF();
            for (Statement st: model) {
                writer.handleStatement(st);
            }
            writer.endRDF();
        }
        catch (RDFHandlerException e) {
            // oh no, do something! //TODO handle this exception better
            System.err.println("Error");
        }
    }
}
