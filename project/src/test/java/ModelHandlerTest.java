import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.Assert;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Created by christine on 20.01.17.
 */
public class ModelHandlerTest {
    private Model m;
    @Before
    public void setUp() throws Exception {
        m = FileHandler.readModelFromFile("input.ttl");
    }

    @org.junit.Test
    public void addItem() throws Exception {
        String subject = "http://www.example.org/Book";
        String predicate = "http://www.example.org/isWrittenBy";
        String object = "Author";

        ModelHandler.addItem(subject,predicate, object,'L', m);
        ValueFactory factory = SimpleValueFactory.getInstance();
        Resource subjectPart = factory.createIRI(subject);
        IRI predicatePart = factory.createIRI(predicate);
        Value objectPart = factory.createLiteral(object);

        Assert.assertTrue(m.contains(subjectPart, predicatePart, objectPart));
    }

    @org.junit.Test
    public void addBook() throws Exception {
        Author a = new Author("AB1", "m", "A", "B", "1960-12-31");

        ValueFactory factory = SimpleValueFactory.getInstance();
        Resource subjectPart = factory.createIRI("ABI");
        IRI predicatePart = factory.createIRI("http://www.example.org/hasGender");
        Value objectPart = factory.createLiteral("m");

        Assert.assertTrue(m.contains(subjectPart, predicatePart, objectPart));
    }

    @org.junit.Test
    public void addAuthor() throws Exception {

    }

    @org.junit.Test
    public void addReader() throws Exception {

    }

    @org.junit.Test
    public void addBookToLibrary() throws Exception {

    }

}