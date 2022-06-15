package ca.utoronto.utm.mcs;

// All your database transactions or queries should 
// go in this class
import javax.inject.Inject;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.Neo4jException;

public class Neo4jDAO implements AutoCloseable{
    // TODO Complete This Class
    private final Session session;
    private final Driver driver;

    @Inject
    public Neo4jDAO(Driver driver){
        this.driver = driver;
        this.session = this.driver.session();
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public void makeQuery(String query) throws Neo4jException {
        this.session.run(query);
    }

    public void initialSetup(){
        String query =
            "CREATE CONSTRAINT unique_actorId FOR (actor: Actor) REQUIRE actor.actorId IS UNIQUE";
        try {
            this.session.run(query);
        }
        catch (Neo4jException e){
            System.out.println(e.getMessage());;
        }
    }

}