import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.repository.Repository;

import java.io.FileNotFoundException;

/**
 * Created by christine on 13.01.17.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Repository repo = FileHandler.readRepositoryFromFile("src/main/resources/exercise5.ttl");
        ModelHandler.outputQueryResult(repo, "SELECT * WHERE {\n" +
                "ex:Museion rdf:type ?y.\n }");

            Model m = FileHandler.readModelFromFile("src/main/resources/exercise5.ttl");
            FileHandler.writeModelToFile("src/main/resources/output.ttl", m);

    }
}
