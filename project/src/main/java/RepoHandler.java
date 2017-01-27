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

    /**
     * Takes the repository to query and the query string as input and returns a linked list of the results.
     * @param repo
     * @param query
     * @return
     */
    public static LinkedList<String> returnQueryResult(Repository repo, String query) {
        LinkedList<String> queryResult = new LinkedList<String>();
        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString =
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                            "PREFIX ex: <http://www.example.org/>\n" + query;
            System.out.println(queryString + "\n");
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    String resultString = "";
                    for(String name : bindingSet.getBindingNames()){
                        resultString = resultString + "\n" + bindingSet.getBinding(name);
                    }
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
                            "PREFIX ex: <http://www.example.org/>\n" +
                            "SELECT * WHERE {\n" + "?x rdf:type ex:Author.}";
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    String resultString = "";

                    String id = bindingSet.getBinding("x")+"";
                    id = id.replace("x=http://www.example.org/","");

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
                            "PREFIX ex: <http://www.example.org/>\n" +
                            "SELECT * WHERE {\n" + "?p rdf:type ex:Publisher.\n }";
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();

                    String publisher = bindingSet.getBinding("p")+"";
                    publisher = publisher.replace("p=http://www.example.org/","");

                    if(!queryResult.contains(publisher)){
                        queryResult.add(publisher);
                    }
                }
            }
        }
        return queryResult;
    }
}
