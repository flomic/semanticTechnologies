import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.util.LinkedList;

/**
 * Created by christine on 19.01.17.
 */

/**
 * Helper class to handle the quering of the repository
 */
public class RepoHandler {
    public static TupleQueryResult query(Repository repo, String query) {
        TupleQueryResult result;
        try (RepositoryConnection conn = repo.getConnection()) {
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, query);
            result = tupleQuery.evaluate();
        }
        return result;
    }

    public static LinkedList<Book> searchBookWithFilter(Repository repo, String filter) {
        LinkedList<Book> books = new LinkedList<Book>();

        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString =
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX ex: <urn:absolute:www.example.com/ontologies/project-ontology#>\n" +
                            "SELECT * WHERE { " +
                            "?b rdf:type ex:Book " +
                            "OPTIONAL{?b ex:has_author ?author } " +
                            "OPTIONAL{?b ex:has_title ?title } " +
                            "OPTIONAL{?b ex:has_publisher ?publisher } " +
                            "OPTIONAL{?b ex:has_genre ?genre } " +
                            "OPTIONAL{?b ex:has_publication_year ?year } " +
                            filter +
                            "}";

            //System.out.println(queryString + "\n");
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    String resultString = "";
                    String isbn = cleanString(bindingSet.getValue("b").toString());

                    String author = null;
                    if(bindingSet.hasBinding("author")){
                        author = cleanString(bindingSet.getValue("author").toString());
                    }

                    String title = null;
                    if(bindingSet.hasBinding("title")){
                        title = cleanString(bindingSet.getValue("title").toString());
                    }

                    String publisher = null;
                    if(bindingSet.hasBinding("publisher")){
                        publisher = cleanString(bindingSet.getValue("publisher").toString());
                    }

                    String genre = null;
                    if(bindingSet.hasBinding("genre")){
                        genre = cleanString(bindingSet.getValue("genre").toString());
                    }

                    if(bindingSet.hasBinding("year")){
                        String year = cleanString(bindingSet.getValue("year").toString());
                        books.add(new Book(isbn, author, title, publisher, genre, Integer.parseInt(year)));
                    } else {
                        books.add(new Book(isbn, author, title, publisher, genre, null));
                    }
                }
            }
        }
        return books;
    }

    public static Author searchAuthor(Repository repo, String authorId) {
        Author author = null;
        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString =
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX ex: <urn:absolute:www.example.com/ontologies/project-ontology#>\n" +
                            "SELECT * WHERE { " +
                            "?a rdf:type ex:Author " +
                            "OPTIONAL{?a ex:has_name ?name } " +
                            "OPTIONAL{?a ex:has_gender ?gender } " +
                            "OPTIONAL{?a ex:has_date_of_birth ?date_of_birth} " +
                            "FILTER(?a = ex:" + authorId + ")" + "}";

            //System.out.println(queryString + "\n");
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    String resultString = "";
                    String id = cleanString(bindingSet.getValue("a").toString());

                    String name = null;
                    if(bindingSet.hasBinding("name")){
                        name =cleanString(bindingSet.getValue("name").toString());
                    }

                    String gender = null;
                    if(bindingSet.hasBinding("gender")){
                        gender = cleanString(bindingSet.getValue("gender").toString());
                    }

                    String dateOfBirth = null;
                    if(bindingSet.hasBinding("date_of_birth")){
                        dateOfBirth = cleanString(bindingSet.getValue("date_of_birth").toString());
                    }

                    author = new Author(id, name, gender, dateOfBirth);

                }
            }
        }
        return author;
    }

    public static LinkedList<String> getAll(Repository repo, String type) {
        LinkedList<String> queryResult = new LinkedList<String>();
        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString =
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX ex: <urn:absolute:www.example.com/ontologies/project-ontology#>\n" +
                            "SELECT ?result WHERE { " +
                            "?result rdf:type ex:" + type + " }";
            //System.out.println(queryString + "\n");
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    String resultString = cleanString(bindingSet.getValue("result").toString());
                    queryResult.add(resultString);
                }
            }
        }
        return queryResult;
    }

    static String cleanString(String s) {
        String result = s;
        if (s.contains("=")) result = result.substring(s.indexOf("=") + 1);
        if (result.contains("^^")) result = result.substring(0, result.indexOf("^^"));
        result = result.replace("\"", "");
        result = result.replace("@en", "");
        result = result.replace("urn:absolute:www.example.com/ontologies/project-ontology#", "");
        return result;
    }
}
