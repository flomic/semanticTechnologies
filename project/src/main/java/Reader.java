import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

import java.io.File;
import java.io.IOException;

/**
 * Created by christine on 10.01.17.
 */
public class Reader {


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
}
