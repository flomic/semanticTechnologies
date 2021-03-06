import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.RDF;

import java.util.Date;

/**
 * Created by christine on 13.01.17.
 */

/**
 * Helper class to handle the addition of items to the model
 */
public class ModelHandler {
    private static final String EX_PREFIX = "urn:absolute:www.example.com/ontologies/project-ontology#";

    private static Statement makeStatement(String subject, String predicate, String object, char objectType){
        ValueFactory factory = SimpleValueFactory.getInstance();
        Resource subjectPart = factory.createIRI(EX_PREFIX, subject);
        IRI predicatePart = factory.createIRI(EX_PREFIX, predicate);
        Value objectPart = null;

        switch (objectType) {
            case 'I':
            case 'i':
                objectPart = factory.createIRI(EX_PREFIX, object);
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
        return factory.createStatement(subjectPart,predicatePart, objectPart);
    }

    private static Statement makeStatement(String subject, IRI predicate, String object, char objectType){
        ValueFactory factory = SimpleValueFactory.getInstance();
        Resource subjectPart = factory.createIRI(EX_PREFIX,subject);
        IRI predicatePart = predicate;
        Value objectPart = null;

        switch (objectType) {
            case 'I':
            case 'i':
                objectPart = factory.createIRI(EX_PREFIX,object);
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

        return factory.createStatement(subjectPart,predicatePart, objectPart);
    }

    private static Statement makeStatementWithDate(String subject, String predicate, Date object){
        ValueFactory factory = SimpleValueFactory.getInstance();
        Resource subjectPart = factory.createIRI(EX_PREFIX, subject);
        IRI predicatePart = factory.createIRI(EX_PREFIX, predicate);
        Value objectPart = factory.createLiteral(object);
        return factory.createStatement(subjectPart,predicatePart, objectPart);
    }

    /**
     * Adds an item to the given model
     * @param subject
     * @param predicate
     * @param object
     * @param objectType is used to decide which value type to use for the object part
     * @param model
     */
    private static void addItem(String subject, String predicate, String object, char objectType, Model model) {
        model.add(makeStatement(subject, predicate, object, objectType));
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
        model.add(makeStatement(subject, predicate, object, objectType));
    }



    public static void removeItem(String subject, String predicate, String object, char objectType, Model model){
        model.remove(makeStatement(subject, predicate, object, objectType));
    }

    public static void removeItem(String subject, IRI predicate, String object, char objectType, Model model){
        model.remove(makeStatement(subject, predicate, object, objectType));
    }

    /**
     * Takes a book and adds all the needed information of the book to the model.
     * @param book
     * @param model
     */
    public static void addBook(Book book, String reader, Model model){
        addItem(book.getIsbn(), RDF.TYPE, "Book", 'I', model);

        if(book.getIsbn()!=null && !book.getIsbn().equals("") ){
            addItem(book.getIsbn(), "has_isbn", book.getIsbn(), 'L', model);
        }

        if(book.getAuthor()!=null && !book.getAuthor().equals("") && !book.getAuthor().equals("Please select an author")){
            addItem(book.getIsbn(), "has_author", book.getAuthor(), 'I', model);
        }

        if(book.getTitle()!=null && !book.getTitle().equals("") ){
            addItem(book.getIsbn(), "has_title", book.getTitle(), 'L', model);
        }
        if(book.getGenre()!=null && !book.getGenre().equals("")){
            addItem(book.getIsbn(), "has_genre", book.getGenre(), 'L', model);
        }

        if(book.getPublicationYear()!= null){
            addItem(book.getIsbn(), "has_publication_year", book.getPublicationYear().toString(), 'L', model);
        }

        if(book.getPublisher()!=null && !book.getPublisher().equals("") && !book.getPublisher().equals("Please select a publisher")){
            addItem(book.getIsbn(), "has_publisher", book.getPublisher(), 'I', model);
        }

        addItem(book.getIsbn(), "is_contained_in", "Lib_"+reader, 'I', model);
    }

    /**
     * Takes an author and and adds all the needed information of the author to the model.
     * @param author
     * @param model
     */
    public static void addAuthor(Author author, Model model) {
        addItem(author.getId(), RDF.TYPE, "Author", 'I', model);
        if(author.getGender()!=null && !author.getGender().equals("")){
            addItem(author.getId(), "has_gender", author.getGender(), 'L', model);
        }
        if(author.getName()!=null && !author.getName().equals("")){
            addItem(author.getId(), "has_name", author.getName(), 'L', model);
        }
        if(author.getDateOfBirth()!= null){
            model.add(makeStatementWithDate(author.getId(), "has_date_of_birth", author.getDateOfBirth()));
        }
    }

    /**
     * Takes a reader and adds all the needed information of the reader with the associated library to the model.
     * @param reader
     * @param model
     */
    public static void addReader(Reader reader, Model model) {
        addItem(reader.getId(), RDF.TYPE, "Reader", 'I', model);
        if(reader.getGender()!=null && !reader.getGender().equals("")){
            addItem(reader.getId(), "has_gender", reader.getGender(), 'L', model);
        }
        if(reader.getName()!=null && !reader.getName().equals("")){
            addItem(reader.getId(), "has_name", reader.getName(), 'L', model);
        }
        if(reader.getDateOfBirth()!=null){
            addItem(reader.getId(), "has_date_of_birth", reader.getDateOfBirth().toString(), 'L', model);
        }

        addItem(reader.getId(), "has_library",  reader.getLibrary().getId(), 'I', model);

        addItem(reader.getLibrary().getId(), RDF.TYPE, "Library", 'I', model);
    }

    /**
     * Takes a isbn and a libraryId and adds them to the model.
     * @param isbn
     * @param libId
     * @param model
     */
    public static void addBookToLibrary(String isbn, String libId, Model model){
        addItem(isbn, "is_contained_in", libId, 'I', model);
    }

    /**
     * Takes a publisher and adds it to the model.
     * @param publisher
     * @param model
     */
    public static void addPublisher(Publisher publisher, Model model){
        addItem(publisher.getId(), RDF.TYPE, "Publisher", 'I', model);
    }

    public static void removeBook(Book book, String reader, Model model){
        removeItem(book.getIsbn(), "is_contained_in", "Lib_"+reader, 'I', model);
    }


    public static boolean contains(Model model, String subject, IRI predicate, String object, char objectType){
        return model.contains(makeStatement(subject,predicate,object,objectType));
    }




}
