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

public class Relationship implements HttpHandler {

    Neo4jDAO njDB;
    /**
     * This method injects the neo4j object in the class
     * @param njDb the neo4j objected to be injected
     */
    public Relationship(Neo4jDAO njDb) {
        this.njDB = njDb;
    }

    /**
     * This method handles the which function to call for actor get/put
     * @param exchange the request sent to the server
     * @throws IOException
     */
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equals("PUT")) {
                this.handlePut(exchange);
            }
            else if (exchange.getRequestMethod().equals("GET")) {
                this.handleGet(exchange);
            }
            else {
                this.invalidRoute(exchange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method handles the put request for relationship
     * @param exchange the request sent to the server
     * @throws IOException
     * @throws JSONException
     */
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

    /**
     * This method handles the get request for relationship
     * @param exchange the request sent to the server
     * @throws IOException
     * @throws JSONException
     */
    public void handleGet(HttpExchange exchange) throws IOException, JSONException {
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

                String query = "MATCH (a:Actor {id: \"%s\"}), (b:Movie {id:\"%s\"}) RETURN a.id,b.id";
                query = String.format(query, actorId, movieId);
                temp = njDB.getRelation(query);
                if (temp.isEmpty()) {
                    response = "actor or movie does not exist";
                    exchange.sendResponseHeaders(404, response.length());
                } else {
                    query = "MATCH (a:Actor {id: \"%s\"}), (b:Movie {id:\"%s\"}) WHERE (a)-[:ACTED_IN]->(b) RETURN a.id, b.id";
                    query = String.format(query, actorId, movieId);
                    temp = njDB.getRelation(query);
                    if (temp.isEmpty()) {
                        response = String.format("{\"actorId\": \"%s\" ,\n\"movieId\": \"%s\",\n\"hasRelationship\": \"False\"}", actorId, movieId);
                        exchange.sendResponseHeaders(200, response.length());
                    } else {
                        response = "{\"actorId\":" + temp.get(0) + ",\n\"movieId\":" + temp.get(1) + ",\n\"hasRelationship\": \"True\"}";
                        exchange.sendResponseHeaders(200, response.length());
                    }
                }
            }catch (Neo4jException e) {
                response = "save or add was unsuccessful";
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

    /**
     * This method handles any invalid request
     * @param exchange the request sent to server
     * @throws IOException
     * @throws JSONException
     */
    public void invalidRoute(HttpExchange exchange) throws IOException, JSONException {
        String response = "Not Found";
        exchange.sendResponseHeaders(404, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}