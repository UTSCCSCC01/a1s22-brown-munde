package ca.utoronto.utm.mcs.handlers;

import ca.utoronto.utm.mcs.Neo4jDAO;
import ca.utoronto.utm.mcs.ReqHandler;
import ca.utoronto.utm.mcs.Utils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.inject.Inject;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.exceptions.Neo4jException;

public class GetMovie implements HttpHandler {

    Neo4jDAO njDB;
    public GetMovie(Neo4jDAO njDb) {
        this.njDB = njDb;
    }

    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equals("GET")) {
                this.handleGet(exchange);
            }
            else {
                this.invalidRoute(exchange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleGet(HttpExchange exchange) throws IOException, JSONException {
        String body = Utils.convert(exchange.getRequestBody());
        JSONObject deserialized = new JSONObject(body);
        String movieId = "";
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        JSONObject response = new JSONObject();
        String res="";
        if (deserialized.has("movieId")) {
            try {
                movieId = deserialized.getString("movieId");
                String query = "MATCH (a: Movie {movieId: \"%s\"}) RETURN a.name,a.movieId";
                query = String.format(query, movieId);
                res = njDB.getMovie(query);
                //String[] res = actor.split("\\s+");
                //response.append("name", res[0]);
                //response.append("actorId", actor);
                //res = response.JSONString();
                if (res == ""){
                    res = "No such movie";
                    exchange.sendResponseHeaders(404, res.length());
                }
                else {
                    String res2 = "";
                    query = "MATCH (a: Movie {movieId: \"%s\"}) <- [:ACTED_IN]-(b) RETURN b.actorId";
                    query = String.format(query, movieId);
                    res2 = njDB.getActors(query);
                    res = res + "\n" + res2;
                    exchange.sendResponseHeaders(200, res.length());
                }
            } catch (Neo4jException e) {
                //response.append("error message","No such actor");
                res = "save or add was unsuccessful";
                exchange.sendResponseHeaders(500, res.length());
            }
        }
        else {
            //response.append("error message", "required field is missing in the body");
            res = "required field is missing in the body";
            exchange.sendResponseHeaders(400, res.length());
        }
        OutputStream os = exchange.getResponseBody();
        os.write(res.getBytes());
        os.close();
    }

    public void invalidRoute(HttpExchange exchange) throws IOException, JSONException {
        String response = "Not Found";
        exchange.sendResponseHeaders(404, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}