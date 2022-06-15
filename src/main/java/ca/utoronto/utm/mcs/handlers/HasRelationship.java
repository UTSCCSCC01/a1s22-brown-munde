package ca.utoronto.utm.mcs.handlers;

import ca.utoronto.utm.mcs.Neo4jDAO;
import ca.utoronto.utm.mcs.Utils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.exceptions.Neo4jException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class HasRelationship implements HttpHandler {

    Neo4jDAO njDB;
    public HasRelationship(Neo4jDAO njDb) {
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
        String actorId = "";
        String movieId = "";
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        String response = "";
        ArrayList<String> temp = new ArrayList<String>();
        if (deserialized.has("actorId") && deserialized.has("movieId")) {
            actorId = deserialized.getString("actorId");
            movieId = deserialized.getString("movieId");

            String query = "MATCH (a:Actor {actorId: \"%s\"}), (b:Movie {movieId:\"%s\"}) RETURN a.actorId,b.movieId";
            query = String.format(query, actorId, movieId);
            temp = njDB.getRelation(query);
            if (temp.isEmpty()) {
                response = "actor or movie does not exist";
                exchange.sendResponseHeaders(404, response.length());
            } else {
                query = "MATCH (a:Actor {actorId: \"%s\"}), (b:Movie {movieId:\"%s\"}) WHERE (a)-[:ACTED_IN]->(b) RETURN a.actorId, b.movieId";
                query = String.format(query, actorId, movieId);
                temp = njDB.getRelation(query);
                if(temp.isEmpty()){
                    response = String.format("{\"actorId\": \"%s\" ,\n\"movieId\": \"%s\",\n\"hasRelationship\": \"False\"}",actorId,movieId);
                    exchange.sendResponseHeaders(200, response.length());
                }
                else {
                    response = "{\"actorId\":" + temp.get(0) + ",\n\"movieId\":" + temp.get(1) + ",\n\"hasRelationship\": \"True\"}";
                    exchange.sendResponseHeaders(200, response.length());
                }
            }
        }
        else {
            response = "required field is missing in the body";
            exchange.sendResponseHeaders(400, response.length());
        }
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
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
