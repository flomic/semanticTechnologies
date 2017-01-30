/*import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;*/
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.repository.Repository;

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
            Book b = new Book("9780002318525", "AB1", "Title1", "P1", "Thriller", 2016);
            Book b2 = new Book("B2", "AB2", "Title2", "P1", "Thriller", 2016);
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



        /* ----------------------------------------- GET BOOK FROM DBPEDIA ----------------------------------------- */

        String isbn = "9780002318525";

        /* book */
        String author;
        String title;
        String language; //TODO konn verschiedene hobn
        String publisher;
        String genre;
        Integer publicationYear; //TODO gibs net auf dbpedia

        /* author */
        String gender; //TODO gibs net auf dbpedia
        String firstName; //TODO gibs net auf dbpedia ->
        String lastName; //TODO gibs net auf dbpedia
        String dateOfBirth;


        String query =  /*"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +*/
                        "PREFIX dbpedia: <http://dbpedia.org/ontology/>" +
                        "select * " +
                        "where {" +
                        "?book_uri rdf:type dbpedia:Book ." +
                        "?book_uri rdfs:label ?title ." +
                        "?book_uri dbpedia:publisher ?publisher_uri ." +
                        "?publisher_uri rdfs:label ?publisher ." +
                        "?book_uri dbpedia:literaryGenre ?genre_uri ." +
                        "?genre_uri rdfs:label ?genre ." +
                        "?book_uri dbpedia:author ?author_uri ." +
                        "?book_uri dbpedia:isbn ?isbn." +
                        "?author_uri dbpedia:birthName ?author." +
                        "?author_uri dbpedia:birthDate ?birthDate." +
                        "FILTER (regex(?isbn, '9780002318525'))" +
                        "FILTER (lang(?author) = 'en')" +
                        "FILTER (lang(?genre) = 'en')" +
                        "FILTER (lang(?title) = 'en')" +
                        "FILTER (lang(?publisher) = 'en')" +
                        "}";

      /*  Repository repo2 = new SPARQLRepository("http://dbpedia.org/sparql");
        repo2.initialize();
        LinkedList<String> res = RepoHandler.returnQueryResult(repo2, query);

        System.out.println("Result:");
        for(String s : res){
            System.out.println(s);
        }*/

        RepoHandler.searchByISBN(repo, isbn);
        /*org.apache.jena.query.Query sparqlquery = QueryFactory.create(query);
        QueryExecution result = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", sparqlquery);
        org.apache.jena.query.ResultSet results = result.execSelect();
        QuerySolution book = results.next();
        result.close() ;

        isbn = String.valueOf(book.getLiteral("isbn"));
        title = String.valueOf(book.getLiteral("title")).replace("@en", "");
        author = String.valueOf(book.getLiteral("author")).replace("@en", "");
        publisher = String.valueOf(book.getLiteral("publisher")).replace("@en", "");
        genre = String.valueOf(book.getLiteral("genre")).replace("@en", "");
        dateOfBirth = String.valueOf(book.getLiteral("birthDate")).substring(0, 10);







*/

        /* OUTPUT */
        /*System.out.println("\n\n\n\n----------------------------------------");
        System.out.println("ISBN: " + isbn);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Date of birth: " + dateOfBirth);
        System.out.println("Publisher: " + publisher);
        System.out.println("Genre: " + genre);
        System.out.println("----------------------------------------");*/
    }
}
