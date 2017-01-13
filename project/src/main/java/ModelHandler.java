import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;

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

    public static Model addItem(String subject, String predicate, String object, char objectType, Model model) {

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
        return (model);
    }

}
