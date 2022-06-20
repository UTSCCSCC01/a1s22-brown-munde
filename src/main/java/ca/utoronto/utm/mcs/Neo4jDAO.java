package ca.utoronto.utm.mcs;

// All your database transactions or queries should 
// go in this class
import javax.inject.Inject;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.Neo4jException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;

public class Neo4jDAO implements AutoCloseable {

    // TODO Complete This Class
    private final Session session;
    private final Driver driver;
    boolean setupDone = false;

    /**
     * Injects the neo4j driver into the class
     * @param driver the driver object that is to be inserted
     */
    @Inject
    public Neo4jDAO(Driver driver) {
        this.driver = driver;
        this.session = this.driver.session();
    }

    /**
     * Implements the close function for the neo4j server
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        driver.close();
    }

    /**
     * This functions runs the query on the neo4j database and throws exception if
     * any else returns nothing
     * @param query The query that is to be to run on the database
     * @throws Neo4jException
     */
    public void makeQuery(String query) throws Neo4jException {
        Result result = this.session.run(query);
    }

    /**
     * This function returns an array list containing the actorId and movieId
     * if the actor and movie have a relationship else returns empty array list
     * @param query The query to check if a relationship exists, if not create one
     * @return the actorId and movieId if the actor and movie have a relationship else returns empty array list
     * @throws Neo4jException
     */
    public ArrayList<String> getRelation(String query) throws Neo4jException {
        Result result = this.session.run(query);
        ArrayList<String> res = new ArrayList<String>();
        String actorId = "";
        String movieId = "";
        for (Record rec : result.list()) {
            actorId = rec.get("a.id").toString();
            movieId = rec.get("b.id").toString();
            res.add(actorId);
            res.add(movieId);
        }
        return res;
    }

    /**
     * This functions returns the name and id for the actor in an array list if it exists
     * in the database else returns an empty list
     * @param query The query to check if actor exists in the database
     * @return the name and id for the actor in an array list if it exists in the database else returns an empty list
     * @throws Neo4jException
     */
    public ArrayList<String> getActor(String query) throws Neo4jException {
        Result result = this.session.run(query);
        ArrayList<String> res = new ArrayList<String>();
        String name = "";
        String id = "";
        for (Record rec : result.list()) {
            name = rec.get("a.name").toString();
            id = rec.get("a.id").toString();
            res.add(name);
            res.add(id);
        }
        return res;
    }

    /**
     * This functions returns the name and id for the movie in an array list if it exists
     * in the database else returns an empty list
     * @param query The query to check if movie exists in the database
     * @return the name and id for the movie in an array list if it exists in the database else returns an empty list
     * @throws Neo4jException
     */
    public ArrayList<String> getMovie(String query) throws Neo4jException {
        Result result = this.session.run(query);
        ArrayList<String> res = new ArrayList<String>();
        String name = "";
        String id = "";
        for (Record rec : result.list()) {
            name = rec.get("a.name").toString();
            id = rec.get("a.id").toString();
            res.add(name);
            res.add(id);
        }
        return res;
    }

    /**
     * The function returns a list of movies an actor has acted in.
     * @param query The query to return the list of movies of the actor
     * @return returns a list of movies an actor has acted in.
     * @throws Neo4jException
     */
    public ArrayList<String> getMovies(String query) throws Neo4jException {
        Result result = this.session.run(query);
        ArrayList<String> res = new ArrayList<String>();
        String movie = "";
        for (Record rec : result.list()) {
            movie = rec.get("b.id").toString();
            res.add(movie);
        }
        return res;
    }

    /**
     * The function returns a list of actors in the movie.
     * @param query The query to return the list of actors of the movie
     * @return returns a list of actors in the movie.
     * @throws Neo4jException
     */
    public ArrayList<String> getActors(String query) throws Neo4jException {
        Result result = this.session.run(query);
        ArrayList<String> res = new ArrayList<String>();
        String actor = "";
        for (Record rec : result.list()) {
            actor = rec.get("b.id").toString();
            res.add(actor);
        }
        return res;
    }

    /**
     * This functions adds constraints on the database so that the actorId and movieId
     * are unique fields.
     */
    public void initialSetup() {
        String query1 =
            "CREATE CONSTRAINT unique_actorId FOR (actor: Actor) REQUIRE actor.id IS UNIQUE";
        try {
            this.session.run(query1);
            setupDone = true;
        } catch (Neo4jException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().indexOf("already exists") != -1){
                setupDone = true;
            }
        }

        String query2 =
            "CREATE CONSTRAINT unique_movieId FOR (movie: Movie) REQUIRE movie.id IS UNIQUE";
        try {
            this.session.run(query2);
        } catch (Neo4jException e) {
            System.out.println(e.getMessage());
        }

    }

    public void cleanup() {
        String query =
            "MATCH (n) DETACH DELETE n";
        try {
            this.session.run(query);
        } catch (Neo4jException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * The function returns the baconNumber of an actor to kevin bacon
     * @param query The query to return the baconList to get the number
     * @return the baconNumber of an actor to kevin bacon
     */
    public int computeBacon(String query) {
        Result result = this.session.run(query);
        int bnum = 0;
        for(Record rec: result.list()){
            bnum = rec.get("RESULT").size();
        }
        return bnum/2;
    }

    /**
     * The function returns the baconPath of an actor to kevin bacon
     * @param query The query to return the baconList to get the path
     * @return the baconPath of an actor to kevin bacon
     */
    public List<String> computeBaconPath(String query) {
        List<String> list = new ArrayList<String>();
        Result result = this.session.run(query);
        if(result.hasNext()) {
            String nodes = result.next().get(0).asObject().toString();
            nodes = nodes.substring(1, nodes.length() - 1);
            list = Arrays.asList(nodes.split(", "));
        }
        return list;
    }
}