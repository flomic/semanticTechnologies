import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
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
    public static void outputQueryResult(Repository repo, String query) {
        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString1 =
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "PREFIX ex: <http://www.example.org/>\n" + query;
            System.out.println(query + "\n");
            TupleQuery tupleQuery1 = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString1);
            try (TupleQueryResult result = tupleQuery1.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    System.out.println(bindingSet);
                }
            }
        }
    }

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

        Statement statement = factory.createStatement(subjectPart, predicatePart, objectPart);
        model.add(statement);
    }

    public static void addBook(Book book, Model model){
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasAuthor", "http://www.example.org/"+book.getAuthor().getId(), 'I', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasTitle", book.getTitle(), 'L', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasGenre", book.getGenre(), 'L', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasLanguage", book.getLanguage(), 'L', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasCover", "http://www.example.org/"+book.getCover().getPath(), 'I', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasPublicationYear", book.getPublicationYear().toString(), 'L', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasPublisher", "http://www.example.org/"+book.getPublisher().getPublisherId(), 'I', model);
    }

        public static void addAuthor(Author author, Model model) {
            addItem("http://www.example.org/" + author.getId(), "http://www.example.org/hasGender", author.getGender(), 'L', model);
            addItem("http://www.example.org/" + author.getId(), "http://www.example.org/hasFirstName", author.getFirstName(), 'L', model);
            addItem("http://www.example.org/" + author.getId(), "http://www.example.org/hasLastName", author.getLastName(), 'L', model);
            addItem("http://www.example.org/" + author.getId(), "http://www.example.org/hasDateOfBirth", author.getDateOfBirth().toString(), 'L', model);
        }

        public static void addReader(Reader reader, Model model) {
            addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasGender", reader.getGender(), 'L', model);
            addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasFirstName", reader.getFirstName(), 'L', model);
            addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasLastName", reader.getLastName(), 'L', model);
            addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasDateOfBirth", reader.getDateOfBirth().toString(), 'L', model);
            addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasLibrary", "http://www.example.org/" + reader.getLibrary().getId(), 'I', model);
        }

        public static void addBookToLibrary(String bookId, String libId, Model model){
            addItem("http://www.example.org/" + bookId, "http://www.example.org/belongsTo", "http://www.example.org/" +libId, 'I', model);
        }

}
