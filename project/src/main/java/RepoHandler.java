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

    public static LinkedList<Book> searchWithFilter(Repository repo, String filter) {
        LinkedList<Book> books = new LinkedList<Book>();

        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString =
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX ex: <urn:absolute:www.example.com/ontologies/project-ontology#>\n" +
                            "SELECT * WHERE { " +
                            "?b rdf:type ex:Book." +
                            "?b ex:has_author ?author. " +
                            "?b ex:has_title ?title. " +
                            "?b ex:has_publisher ?publisher. " +
                            "?b ex:has_genre ?genre. " +
                            "?b ex:has_publication_year ?year. " +
                            filter +
                            "}";

            //System.out.println(queryString + "\n");
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    String resultString = "";
                    String isbn = cleanString(bindingSet.getValue("b").toString());
                    String author = cleanString(bindingSet.getValue("author").toString());
                    String title = cleanString(bindingSet.getValue("title").toString());
                    String publisher = cleanString(bindingSet.getValue("publisher").toString());
                    String genre = cleanString(bindingSet.getValue("genre").toString());
                    String year = cleanString(bindingSet.getValue("year").toString());
                    books.add(new Book(isbn, author, title, publisher, genre, Integer.parseInt(year)));
                }
            }
        }
        return books;
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

    private static String cleanString(String s) {
        String result = s;
        if (s.contains("=")) result = result.substring(s.indexOf("=") + 1);
        if (result.contains("^^")) result = result.substring(0, result.indexOf("^^"));
        result = result.replace("\"", "");
        result = result.replace("urn:absolute:www.example.com/ontologies/project-ontology#", "");
        return result;
    }
}
