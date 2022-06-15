package ca.utoronto.utm.mcs;

// All your database transactions or queries should 
// go in this class
import javax.inject.Inject;
import org.neo4j.driver.*;

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

    public void insertPokemon(String name, String pid, String description, String type1, String type2) {
        String query;
        query = "CREATE (n:pokemon {name: \"%s\", pid: \"%s\", description: \"%s\", type1: \"%s\", type2: \"%s\"})";
        query = String.format(query, name, pid, description, type1, type2);
        this.session.run(query);
        return;
    }

    public void makeQuery(String query){
        this.session.run(query);
    }

}