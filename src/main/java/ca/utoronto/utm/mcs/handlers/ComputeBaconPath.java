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

public class ComputeBaconPath implements HttpHandler {

    Neo4jDAO njDB;
    public ComputeBaconPath(Neo4jDAO njDb) {
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
        String res1 = "";
        ArrayList<String> temp = new ArrayList<String>();
        List<String> temp2 = new ArrayList<String>();
        if (deserialized.has("actorId")) {
            try {
                actorId = deserialized.getString("actorId");
                if(Objects.equals(actorId, "nm0000102")){
                    response = "{\"baconPath\":[\"nm0000102\" ]\n}";
                    exchange.sendResponseHeaders(200, response.length());
                }
                else {
                    String query = "MATCH (a: Actor {id: \"%s\"}) RETURN a.name,a.id";
                    query = String.format(query, actorId);
                    temp = njDB.getActor(query);
                    if (temp.isEmpty()) {
                        response = "No such actor";
                        exchange.sendResponseHeaders(404, response.length());
                    } else {
                        query = "MATCH p=shortestPath((a:Actor {id:\"%s\"})-[*]-(b:Actor {id:\"nm0000102\"})) RETURN [node IN nodes(p) | node.id] as RESULT";
                        query = String.format(query, actorId);
                        temp2 = njDB.computeBaconPath(query);
                        if (temp2.isEmpty()) {
                            response = "No such path exists";
                            exchange.sendResponseHeaders(404, response.length());
                        } else {
                            for(int i = 0;i<temp2.size();i++){
                                if(i == temp2.size()-1){
                                    res1 = String.format("%s \"%s\" \n",res1, temp2.get(i));
                                }
                                else{
                                    res1 = String.format("%s \"%s\" ,\n",res1, temp2.get(i));
                                }

                            }
                            response = "{\"baconPath\":[" + res1 + "]\n}";
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
