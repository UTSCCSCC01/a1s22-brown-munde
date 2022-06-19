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
        JSONObject response1 = new JSONObject();
        ArrayList<String> temp = new ArrayList<String>();
        byte [] res = new byte[0];
        int temp2 = 0;
        if (deserialized.has("actorId")) {
            try {
                actorId = deserialized.getString("actorId");
                if(Objects.equals(actorId, "nm0000102")){
                    response1.put("baconNumber",temp2);
                    res = ((String) response1.toString()).getBytes();
                    exchange.sendResponseHeaders(200, res.length);
                }
                else {
                    String query = "MATCH (a: Actor {id: \"%s\"}) RETURN a.name,a.actorId";
                    query = String.format(query, actorId);
                    temp = njDB.getActor(query);
                    if (temp.isEmpty()) {
                        response1.put("error","No such actor");
                        res = ((String) response1.toString()).getBytes();
                        exchange.sendResponseHeaders(404, res.length);
                    } else {
                        query = "MATCH p=shortestPath((a:Actor {id:\"%s\"})-[*]-(b:Actor {id:\"nm0000102\"})) RETURN [node IN nodes(p) | node.id] as RESULT";
                        query = String.format(query, actorId);
                        temp2 = njDB.computeBacon(query);
                        if (temp2 == 0) {
                            response1.put("error","No such path exists");
                            res = ((String) response1.toString()).getBytes();
                            exchange.sendResponseHeaders(404, res.length);
                        } else {
                            response1.put("baconNumber",temp2);
                            res = ((String) response1.toString()).getBytes();
                            exchange.sendResponseHeaders(200, res.length);
                        }
                    }
                }
            }catch (Neo4jException e) {
                response1.put("error","save or add was unsuccessful");
                res = ((String) response1.toString()).getBytes();
                exchange.sendResponseHeaders(500, res.length);
            }
        }else {
            response1.put("error","required field is missing in the body");
            res = ((String) response1.toString()).getBytes();
            exchange.sendResponseHeaders(400, res.length);
        }

        OutputStream respond = exchange.getResponseBody();
        respond.write(res);
        respond.close();
    }

    public void invalidRoute(HttpExchange exchange) throws IOException, JSONException {
        String response = "Not Found";
        exchange.sendResponseHeaders(404, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

