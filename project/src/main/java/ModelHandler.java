import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.util.Date;

/**
 * Created by christine on 13.01.17.
 */
public class ModelHandler {

    public static void addItem(String subject, String predicate, String object, char objectType, Model model) {

        ValueFactory factory = SimpleValueFactory.getInstance();
        Resource subjectPart = factory.createIRI(subject);
        IRI predicatePart = factory.createIRI(predicate);
        Value objectPart = null;

        switch (objectType) {
            case 'I':
            case 'i':
                objectPart = factory.createIRI(object);
                break;
            case 'L':
            case 'l':
                objectPart = factory.createLiteral(object);
                break;
            case 'B':
            case 'b':
                objectPart = factory.createBNode(object);
                break;
        }

        if(predicate.equals("rdf:type")){
            //statement = factory.createStatement(subjectPart, RDF.TYPE, objectPart);
            model.add(subjectPart, RDF.TYPE, objectPart);
        } else {
            model.add(subjectPart, predicatePart, objectPart);
        }

    }

    public static void addBook(Book book, Model model){
        addItem("http://www.example.org/"+book.getBookId(), "rdf:type", "http://www.example.org/Book", 'I', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasAuthor", "http://www.example.org/"+book.getAuthor(), 'I', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasTitle", book.getTitle(), 'L', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasGenre", book.getGenre(), 'L', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasLanguage", book.getLanguage(), 'L', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasCover", "http://www.example.org/"+book.getCover().getPath(), 'I', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasPublicationYear", book.getPublicationYear().toString(), 'L', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasPublisher", "http://www.example.org/"+book.getPublisher(), 'I', model);
    }

        public static void addAuthor(Author author, Model model) {
            addItem("http://www.example.org/" + author.getId(), "rdf:type", "http://www.example.org/Author", 'I', model);
            addItem("http://www.example.org/" + author.getId(), "http://www.example.org/hasGender", author.getGender(), 'L', model);
            addItem("http://www.example.org/" + author.getId(), "http://www.example.org/hasFirstName", author.getFirstName(), 'L', model);
            addItem("http://www.example.org/" + author.getId(), "http://www.example.org/hasLastName", author.getLastName(), 'L', model);
            addItem("http://www.example.org/" + author.getId(), "http://www.example.org/hasDateOfBirth", author.getDateOfBirth().toString(), 'L', model);
        }

        public static void addReader(Reader reader, Model model) {
            addItem("http://www.example.org/" + reader.getId(), "rdf:type", "http://www.example.org/Reader", 'I', model);
            addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasGender", reader.getGender(), 'L', model);
            addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasFirstName", reader.getFirstName(), 'L', model);
            addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasLastName", reader.getLastName(), 'L', model);
            addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasDateOfBirth", reader.getDateOfBirth().toString(), 'L', model);
            addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasLibrary", "http://www.example.org/" + reader.getLibrary().getId(), 'I', model);
            addItem("http://www.example.org/" + reader.getLibrary().getId(), "rdf:type", "http://example.org/Library", 'I', model);
        }

        public static void addBookToLibrary(String bookId, String libId, Model model){
            addItem("http://www.example.org/" + bookId, "http://www.example.org/belongsTo", "http://www.example.org/" +libId, 'I', model);
        }

}
