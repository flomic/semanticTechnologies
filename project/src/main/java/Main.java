import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.repository.Repository;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by christine on 13.01.17.
 */

/**
 * At the moment just used for testing
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Repository repo = FileHandler.readRepositoryFromFile("src/main/resources/output.ttl");
        /*System.out.println(RepoHandler.returnQueryResult(repo,
                        "SELECT * WHERE {\n" +
                        "?x rdf:type ?y.\n" +
                        "}"));

        System.out.println("\n\n");*/

        for(String s : RepoHandler.getAllAuthors(repo)){
            System.out.println(s);
        }

        System.out.println("\n\n");
        for(String p : RepoHandler.getAllPublishers(repo)){
            System.out.println(p);
        }


            Model m = FileHandler.readModelFromFile("src/main/resources/input.ttl");
            Author a = new Author("AB1", "m", "A", "B", "1960-12-31");
            Author a2 = new Author("AB2", "m", "A", "B", "1962-12-31");
            Reader r = new Reader("R1", "f", "C", "L","1993-01-25");
            Book b = new Book("B1", "AB1", "Title1", "de", "P1", "Thriller", 2016, new Cover("https://d30y9cdsu7xlg0.cloudfront.net/png/1009-200.png") );
            Book b2 = new Book("B2", "AB2", "Title2", "de", "P1", "Thriller", 2016, new Cover("https://d30y9cdsu7xlg0.cloudfront.net/png/1009-200.png") );
            Publisher p = new Publisher("P1");


            //m = ModelHandler.addItem("http://www.example.org/Book","http://www.example.org/isWrittenBy", "Author",'L', m);
            ModelHandler.addAuthor(a,m);
            ModelHandler.addAuthor(a2,m);
            ModelHandler.addBook(b,m);
            ModelHandler.addBook(b2,m);
            ModelHandler.addReader(r, m);
            ModelHandler.addBookToLibrary(b.getBookId(), r.getLibrary().getId(),m);
            ModelHandler.addPublisher(p,m);
            FileHandler.writeModelToFile("src/main/resources/output.ttl", m);

    }
}
