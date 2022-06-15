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
        Result result = this.session.run(query);
        System.out.println(result.consume());
    }

    public String getMovie(String query) throws Neo4jException {
        Result result = this.session.run(query);
        String res = "";
        String name ="";
        String id = "";
        for(Record rec: result.list()){
            name = rec.get("a.name").toString();
            id = rec.get("a.movieId").toString();
            res = name + " " + id;
        }
        return res;
    }

    public String getActors(String query) throws Neo4jException {
        Result result = this.session.run(query);
        String res = "";
        String actor = "";
        for(Record rec: result.list()){
            actor = rec.get("b.actorId").toString();
            res = res + actor + " ";
        }
        return res;
    }

    public void initialSetup(){
        String query1 =
            "CREATE CONSTRAINT unique_actorId FOR (actor: Actor) REQUIRE actor.actorId IS UNIQUE";
        try {
            this.session.run(query1);
        }
        catch (Neo4jException e){
            System.out.println(e.getMessage());;
        }

        String query2 =
            "CREATE CONSTRAINT unique_movieId FOR (movie: Movie) REQUIRE movie.movieId IS UNIQUE";
        try {
            this.session.run(query2);
        }
        catch (Neo4jException e){
            System.out.println(e.getMessage());;
        }
    }

}