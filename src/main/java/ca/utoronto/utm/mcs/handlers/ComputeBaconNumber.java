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
import java.util.List;
import java.util.Objects;

public class ComputeBaconNumber implements HttpHandler {

    Neo4jDAO njDB;
    public ComputeBaconNumber(Neo4jDAO njDb) {
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
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        String response = "";
        ArrayList<String> temp = new ArrayList<String>();
        int temp2 = 0;
        if (deserialized.has("actorId")) {
            try {
                actorId = deserialized.getString("actorId");
                if(Objects.equals(actorId, "nm0000102")){
                    response = "{\"BaconNumber\":" + temp2 + "}";
                    exchange.sendResponseHeaders(200, response.length());
                }
                else {
                    String query = "MATCH (a: Actor {actorId: \"%s\"}) RETURN a.name,a.actorId";
                    query = String.format(query, actorId);
                    temp = njDB.getActor(query);
                    if (temp.isEmpty()) {
                        response = "No such actor";
                        exchange.sendResponseHeaders(404, response.length());
                    } else {
                        query = "MATCH p=shortestPath((a:Actor {actorId:\"%s\"})-[*]-(b:Actor {actorId:\"nm0000102\"})) RETURN [node IN nodes(p) | node.id] as RESULT";
                        query = String.format(query, actorId);
                        temp2 = njDB.computeBacon(query);
                        if (temp2 == 0) {
                            response = "No such path exists";
                            exchange.sendResponseHeaders(404, response.length());
                        } else {
                            response = "{\"BaconNumber\":" + temp2 + "}";
                            exchange.sendResponseHeaders(200, response.length());
                        }
                    }
                }
            }catch (Neo4jException e) {
                response = "save or add was unsuccessful";
                exchange.sendResponseHeaders(500, response.length());
            }
        }else {
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
