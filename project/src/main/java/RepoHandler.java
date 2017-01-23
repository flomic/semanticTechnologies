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
public class RepoHandler {

    public static LinkedList<String> returnQueryResult(Repository repo, String query) {
        LinkedList<String> queryResult = new LinkedList<String>();
        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString =
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
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

    public static LinkedList<String> getAllAuthors(Repository repo){
        LinkedList<String> queryResult = new LinkedList<String>();
        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString =
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "PREFIX ex: <http://www.example.org/>\n" +
                            "SELECT * WHERE {\n" + "?x rdf:type ex:Author;\n " +
                            "ex:hasFirstName ?fn;\n " +
                            "ex:hasLastName ?ln;\n " +
                            "ex:hasDateOfBirth ?dob.}";
            System.out.println(queryString + "\n");
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    String resultString = "";

                    String id = bindingSet.getBinding("x")+"";
                    id = id.replace("x=http://www.example.org/","");

                    String fn = bindingSet.getBinding("fn")+" ";
                    fn = removeBindingNameAndType(fn);

                    String ln = bindingSet.getBinding("ln")+"";
                    ln = removeBindingNameAndType(ln);

                    String dob = bindingSet.getBinding("dob")+"";
                    dob = removeBindingNameAndType(dob);

                    resultString = id + ": " + fn + " " + ln + " " + dob;

                    queryResult.add(resultString);
                }
            }
        }
        return queryResult;
    }

    public static LinkedList<String> getAllPublishers(Repository repo){
        LinkedList<String> queryResult = new LinkedList<String>();
        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString =
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                            "PREFIX ex: <http://www.example.org/>\n" +
                            "SELECT * WHERE {\n" + "_:b ex:hasPublisher ?p.\n }";
            System.out.println(queryString + "\n");
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

    private static String removeBindingNameAndType(String s){
        String result = "";
        if(s.contains("=")){
            result = s.substring(s.indexOf("=")+1);
        }
        if(result.contains("^^")){
            result = result.substring(0, result.indexOf("^^"));
        }
        return result;
    }
}
