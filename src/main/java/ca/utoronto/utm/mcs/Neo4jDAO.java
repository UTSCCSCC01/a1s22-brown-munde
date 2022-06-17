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

    @Inject
    public Neo4jDAO(Driver driver) {
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

    public void initialSetup() {
        String query1 =
            "CREATE CONSTRAINT unique_actorId FOR (actor: Actor) REQUIRE actor.id IS UNIQUE";
        try {
            this.session.run(query1);
        } catch (Neo4jException e) {
            System.out.println(e.getMessage());
            ;
        }

        String query2 =
            "CREATE CONSTRAINT unique_movieId FOR (movie: Movie) REQUIRE movie.id IS UNIQUE";
        try {
            this.session.run(query2);
        } catch (Neo4jException e) {
            System.out.println(e.getMessage());
        }

        /*String query =
            "MATCH (n) DETACH DELETE n";
        try {
            this.session.run(query);
        } catch (Neo4jException e) {
            System.out.println(e.getMessage());
        }
        }*/
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


    public int computeBacon(String query) {
        Result result = this.session.run(query);
        int bnum = 0;
        for(Record rec: result.list()){
            bnum = rec.get("RESULT").size();
        }
        return bnum/2;
    }

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