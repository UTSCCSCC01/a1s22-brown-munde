package ca.utoronto.utm.mcs.handlers;

import ca.utoronto.utm.mcs.Neo4jDAO;
import ca.utoronto.utm.mcs.Utils;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.exceptions.Neo4jException;

public class AddRelationship implements HttpHandler {

    Neo4jDAO njDB;

    public AddRelationship(Neo4jDAO njDb) {
        this.njDB = njDb;
    }

    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equals("PUT")) {
                this.handlePut(exchange);
            }
            else {
                this.invalidRoute(exchange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handlePut(HttpExchange exchange) throws IOException, JSONException {
        String body = Utils.convert(exchange.getRequestBody());
        JSONObject deserialized = new JSONObject(body);
        String actorId = "";
        String movieId = "";
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        String response = "";
        ArrayList<String> temp = new ArrayList<String>();
        if (deserialized.has("actorId") && deserialized.has("movieId")) {
            try {
                actorId = deserialized.getString("actorId");
                movieId = deserialized.getString("movieId");
                String query = "MATCH (a:Actor {id:\"%s\"}), (b:Movie {id:\"%s\"}) RETURN a.id, b.id";

                query = String.format(query, actorId, movieId);
                temp = njDB.getRelation(query);
                if (temp.isEmpty()) {
                    response = "actor or movie does not exist";
                    exchange.sendResponseHeaders(404, response.length());
                } else {
                    query = "MATCH (a:Actor {id: \"%s\"}), (b:Movie {id:\"%s\"}) WHERE NOT (a)-[:ACTED_IN]->(b) CREATE (a)-[r:ACTED_IN]->(b) RETURN a.id";
                    query = String.format(query, actorId, movieId);
                    temp = njDB.getRelation(query);
                    if (temp.isEmpty()) {
                        response = "The relationship already exists";
                        exchange.sendResponseHeaders(400, response.length());
                    } else {
                        response = "Relationship was successfully added!";
                        exchange.sendResponseHeaders(200, response.length());
                    }
                }
            } catch (Neo4jException e) {
                response = "Failed to add relationship";
                exchange.sendResponseHeaders(500, response.length());
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