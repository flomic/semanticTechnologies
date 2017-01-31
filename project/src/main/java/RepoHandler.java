import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.query.algebra.In;
import org.eclipse.rdf4j.query.algebra.evaluation.function.IntegerCast;
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

    public static Book searchByISBN (Repository repo, String isbn){
        Book resultBook = null;

        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString =
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX ex: <urn:absolute:www.example.com/ontologies/project-ontology#>\n" + "SELECT * WHERE { " +
                            "?b rdf:type ex:Book." +
                            "?b ex:has_author ?author. " +
                            "?b ex:has_title ?title. " +
                            "?b ex:has_publisher ?publisher. " +
                            "?b ex:has_genre ?genre. " +
                            "?b ex:has_publication_year ?year. " +
                            "FILTER(?b = ex:"+isbn+")}";
            //System.out.println(queryString + "\n");
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    String resultString = "";
                    String author = removeBindingNameAndType(bindingSet.getValue("author").toString());
                    String title = removeBindingNameAndType(bindingSet.getValue("title").toString());
                    String publisher = removeBindingNameAndType(bindingSet.getValue("publisher").toString());
                    String genre = removeBindingNameAndType(bindingSet.getValue("genre").toString());
                    String year = removeBindingNameAndType(bindingSet.getValue("year").toString());

                    resultBook = new Book(isbn, author,title,publisher,genre,Integer.parseInt(year));
                }
            }
        }
        return resultBook;

    }

    public static LinkedList<Book> searchByPublisherId (Repository repo, String publisherId){
        LinkedList<Book> books = new LinkedList<Book>();

        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString =
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX ex: <urn:absolute:www.example.com/ontologies/project-ontology#>\n" + "SELECT * WHERE { " +
                            "?b rdf:type ex:Book." +
                            "?b ex:has_author ?author. " +
                            "?b ex:has_title ?title. " +
                            "?b ex:has_publisher ?publisher. " +
                            "?b ex:has_genre ?genre. " +
                            "?b ex:has_publication_year ?year. " +
                            "FILTER(?publisher = ex:"+publisherId+")}";
            //System.out.println(queryString + "\n");
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    String resultString = "";
                    String isbn = removeBindingNameAndType(bindingSet.getValue("b").toString());
                    String author = removeBindingNameAndType(bindingSet.getValue("author").toString());
                    String title = removeBindingNameAndType(bindingSet.getValue("title").toString());
                    String publisher = removeBindingNameAndType(bindingSet.getValue("publisher").toString());
                    String genre = removeBindingNameAndType(bindingSet.getValue("genre").toString());
                    String year = removeBindingNameAndType(bindingSet.getValue("year").toString());

                    books.add(new Book(isbn, author,title,publisher,genre,Integer.parseInt(year)));
                }
            }
        }
        return books;

    }

    public static LinkedList<String> getAllBooks (Repository repo){

        LinkedList<String> queryResult = new LinkedList<String>();
        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString =
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX ex: <urn:absolute:www.example.com/ontologies/project-ontology#>\n" + "SELECT ?b WHERE { " +
                            "?b rdf:type ex:Book }";
            //System.out.println(queryString + "\n");
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    String resultString = removeBindingNameAndType(bindingSet.getValue("b").toString());
                    queryResult.add(resultString);
                }
            }
        }
        return queryResult;
    }


    /**
     * Takes the repository as input and returns the list of all authors
     * @param repo
     * @return
     */
    public static LinkedList<String> getAllAuthors(Repository repo){
        LinkedList<String> queryResult = new LinkedList<String>();
        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString =
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX ex: <urn:absolute:www.example.com/ontologies/project-ontology#>\n" +
                            "SELECT * WHERE {\n" + "?x rdf:type ex:Author.}";
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    String resultString = "";

                    String id = bindingSet.getBinding("x")+"";
                    id = id.replace("x=urn:absolute:www.example.com/ontologies/project-ontology#","");

                    queryResult.add(id);
                }
            }
        }
        return queryResult;
    }

    /**
     * Takes the repository as input and returns the list of all publishers as output
     * @param repo
     * @return
     */
    public static LinkedList<String> getAllPublishers(Repository repo){
        LinkedList<String> queryResult = new LinkedList<String>();
        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString =
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX ex: <urn:absolute:www.example.com/ontologies/project-ontology#>\n" +
                            "SELECT * WHERE {\n" + "?p rdf:type ex:Publisher.\n }";
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();

                    String publisher = bindingSet.getBinding("p")+"";
                    publisher = publisher.replace("p=urn:absolute:www.example.com/ontologies/project-ontology#","");

                    if(!queryResult.contains(publisher)){
                        queryResult.add(publisher);
                    }
                }
            }
        }
        return queryResult;
    }

    private static String removeBindingNameAndType(String s){
                String result = s;
                if(s.contains("=")){
                        result = result.substring(s.indexOf("=")+1);
                    }
                if(result.contains("^^")){
                        result = result.substring(0, result.indexOf("^^"));
                    }
                    result = result.replace("\"", "");
                result = result.replace("urn:absolute:www.example.com/ontologies/project-ontology#","");
                return result;
            }
}
