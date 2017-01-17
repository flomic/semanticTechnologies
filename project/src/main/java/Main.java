import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.repository.Repository;

import java.io.FileNotFoundException;

/**
 * Created by christine on 13.01.17.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Repository repo = FileHandler.readRepositoryFromFile("src/main/resources/input.ttl");
        ModelHandler.outputQueryResult(repo, "SELECT * WHERE {\n" +
                "ex:Museion rdf:type ?y.\n }");

            Model m = FileHandler.readModelFromFile("src/main/resources/input.ttl");
            Author a = new Author("AB1", "m", "A", "B", "1960-12-31");
            Reader r = new Reader("R1", "f", "C", "L","1993-01-25");
            Book b = new Book("B1", a, "Title1", "de", new Publisher("P1"), "Thriller", 2016, new Cover(null, "path") );


            //m = ModelHandler.addItem("http://www.example.org/Book","http://www.example.org/isWrittenBy", "Author",'L', m);
            ModelHandler.addAuthor(a,m);
            ModelHandler.addBook(b,m);
            ModelHandler.addReader(r, m);
            ModelHandler.addBookToLibrary(b.getBookId(), r.getLibrary().getId(),m);
            FileHandler.writeModelToFile("src/main/resources/output.ttl", m);

    }
}
