import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.RDF;

/**
 * Created by christine on 13.01.17.
 */

/**
 * Helper class to handle the addition of items to the model
 */
public class ModelHandler {
    /**
     * Adds an item to the given model
     * @param subject
     * @param predicate
     * @param object
     * @param objectType is used to decide which value type to use for the object part
     * @param model
     */
    private static void addItem(String subject, String predicate, String object, char objectType, Model model) {

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
        model.add(subjectPart, predicatePart, objectPart);
    }

    /**
     * Adds an item to the given model. The IRI for the predicate is given as parameter.
     * @param subject
     * @param predicate
     * @param object
     * @param objectType is used to decide which value type to use for the object part
     * @param model
     */
    private static void addItem(String subject, IRI predicate, String object, char objectType, Model model) {

        ValueFactory factory = SimpleValueFactory.getInstance();
        Resource subjectPart = factory.createIRI(subject);
        IRI predicatePart = predicate;
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
        model.add(subjectPart, predicatePart, objectPart);
    }

    /**
     * Takes a book and adds all the needed information of the book to the model.
     * @param book
     * @param model
     */
    //TODO check if book already exists
    //TODO don't add line if the value is missing
    public static void addBook(Book book, Model model){
        addItem("http://www.example.org/"+book.getBookId(), RDF.TYPE, "http://www.example.org/Book", 'I', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasAuthor", "http://www.example.org/"+book.getAuthor(), 'I', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasTitle", book.getTitle(), 'L', model);
        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasGenre", book.getGenre(), 'L', model);

        if(book.getPublicationYear()!= null){
            addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasPublicationYear", book.getPublicationYear().toString(), 'L', model);
        }

        addItem("http://www.example.org/"+book.getBookId(), "http://www.example.org/hasPublisher", "http://www.example.org/"+book.getPublisher(), 'I', model);
    }

    /**
     * Takes an author and and adds all the needed information of the author to the model.
     * @param author
     * @param model
     */
    //TODO check if author already exists
    //TODO don't add line if the value is missing
    public static void addAuthor(Author author, Model model) {
        addItem("http://www.example.org/" + author.getId(), RDF.TYPE, "http://www.example.org/Author", 'I', model);
        addItem("http://www.example.org/" + author.getId(), "http://www.example.org/hasGender", author.getGender(), 'L', model);
        addItem("http://www.example.org/" + author.getId(), "http://www.example.org/hasFirstName", author.getFirstName(), 'L', model);
        addItem("http://www.example.org/" + author.getId(), "http://www.example.org/hasLastName", author.getLastName(), 'L', model);
        if(author.getDateOfBirth()!= null){
            addItem("http://www.example.org/" + author.getId(), "http://www.example.org/hasDateOfBirth", author.getDateOfBirth().toString(), 'L', model);
        }
    }

    /**
     * Takes a reader and adds all the needed information of the reader with the associated library to the model.
     * @param reader
     * @param model
     */
    //TODO check if reader already exists
    //TODO don't add line if the value is missing
    public static void addReader(Reader reader, Model model) {
        addItem("http://www.example.org/" + reader.getId(), RDF.TYPE, "http://www.example.org/Reader", 'I', model);
        addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasGender", reader.getGender(), 'L', model);
        addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasFirstName", reader.getFirstName(), 'L', model);
        addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasLastName", reader.getLastName(), 'L', model);
        addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasDateOfBirth", reader.getDateOfBirth().toString(), 'L', model); //TODO dateOfBirth must be added as date and not as string
        addItem("http://www.example.org/" + reader.getId(), "http://www.example.org/hasLibrary", "http://www.example.org/" + reader.getLibrary().getId(), 'I', model);
        addItem("http://www.example.org/" + reader.getLibrary().getId(), RDF.TYPE, "http://example.org/Library", 'I', model);
    }

    /**
     * Takes a bookId and a libraryId and adds them to the model.
     * @param bookId
     * @param libId
     * @param model
     */
    //TODO check if book is already in the library
    //TODO don't add line if one value is missing
    public static void addBookToLibrary(String bookId, String libId, Model model){
        addItem("http://www.example.org/" + bookId, "http://www.example.org/belongsTo", "http://www.example.org/" +libId, 'I', model);
    }

    /**
     * Takes a publisher and adds it to the model.
     * @param publisher
     * @param model
     */
    //TODO check if publisher already exists
    //TODO don't add line if the value is missing
    public static void addPublisher(Publisher publisher, Model model){
        addItem("http://www.example.org/" + publisher.getId(), RDF.TYPE, "http://www.example.org/Publisher", 'I', model);
    }

}
