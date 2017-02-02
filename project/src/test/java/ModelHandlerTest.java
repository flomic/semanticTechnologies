import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by christine on 20.01.17.
 */
public class ModelHandlerTest {
    private Model m;
    @Before
    public void setUp() throws Exception {
        m = FileHandler.readModelFromFile("src/test/java/input.ttl");
    }

    @org.junit.Test
    public void addBook() throws Exception {
        Book b = new Book("B1", "AB1", "Title1", "P1", "Thriller", 2016);

        ValueFactory factory = SimpleValueFactory.getInstance();
        Resource subjectPart = factory.createIRI("http://www.example.org/B1");
        IRI predicatePart = RDF.TYPE;
        Value objectPart = factory.createIRI("http://www.example.org/Book");
        ModelHandler.addBook(b, MainWindow.reader, m);

        Assert.assertTrue(m.contains(subjectPart, predicatePart, objectPart));

    }

    @org.junit.Test
    public void addAuthor() throws Exception {
        Author a = new Author("AB1", "A", "f", "1960-12-31");

        ValueFactory factory = SimpleValueFactory.getInstance();
        Resource subjectPart = factory.createIRI("http://www.example.org/AB1");
        IRI predicatePart = RDF.TYPE;
        Value objectPart = factory.createIRI("http://www.example.org/Author");
        ModelHandler.addAuthor(a,m);

        Assert.assertTrue(m.contains(subjectPart, predicatePart, objectPart));
    }

    @org.junit.Test
    public void addReader() throws Exception {

        Reader r = new Reader("R1", "C", "f", "1993-01-25");

        ValueFactory factory = SimpleValueFactory.getInstance();
        Resource subjectPart = factory.createIRI("http://www.example.org/R1");
        IRI predicatePart = RDF.TYPE;
        Value objectPart = factory.createIRI("http://www.example.org/Reader");
        ModelHandler.addReader(r,m);

        Assert.assertTrue(m.contains(subjectPart, predicatePart, objectPart));
    }

    @org.junit.Test
    public void addBookToLibrary() throws Exception {
        Reader r = new Reader("R1", "C", "f", "1993-01-25");
        Book b = new Book("B1", "AB1", "Title1", "P1", "Thriller", 2016);

        ValueFactory factory = SimpleValueFactory.getInstance();
        Resource subjectPart = factory.createIRI("http://www.example.org/B1");
        IRI predicatePart = factory.createIRI("http://www.example.org/belongsTo");
        Value objectPart = factory.createIRI("http://www.example.org/Lib_R1");

        ModelHandler.addBookToLibrary(b.getIsbn(), r.getLibrary().getId(),m);

        Assert.assertTrue(m.contains(subjectPart, predicatePart, objectPart));
    }

    @Test
    public void addPublisherTest() throws Exception {
        Publisher p = new Publisher("P1");

        ValueFactory factory = SimpleValueFactory.getInstance();
        Resource subjectPart = factory.createIRI("http://www.example.org/P1");
        IRI predicatePart = RDF.TYPE;
        Value objectPart = factory.createIRI("http://www.example.org/Publisher");
        ModelHandler.addPublisher(p,m);

    }
}