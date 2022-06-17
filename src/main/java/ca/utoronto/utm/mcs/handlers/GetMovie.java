package ca.utoronto.utm.mcs.handlers;

import ca.utoronto.utm.mcs.Neo4jDAO;
import ca.utoronto.utm.mcs.ReqHandler;
import ca.utoronto.utm.mcs.Utils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
        String res="";
        ArrayList<String> temp = new ArrayList<String>();
        if (deserialized.has("movieId")) {
            try {
                movieId = deserialized.getString("movieId");
                String query = "MATCH (a: Movie {id: \"%s\"}) RETURN a.name,a.id";
                query = String.format(query, movieId);
                temp = njDB.getMovie(query);
                if (temp.isEmpty()){
                    res = "No such movie";
                    exchange.sendResponseHeaders(404, res.length());
                }
                else {
                    ArrayList<String> res1 = temp;
                    res = "{\"name\":" + res1.get(0) + ",\n\"movieId\":" + res1.get(1) + ",\n";
                    String res2 = "";
                    String temp2 = "";
                    query = "MATCH (a: Movie {id: \"%s\"}) <- [:ACTED_IN]-(b) RETURN b.id";
                    query = String.format(query, movieId);
                    temp = njDB.getActors(query);
                    res1 = temp;
                    for(int i = 0;i<res1.size();i++){
                        if(i == res1.size()-1){
                            temp2 = temp2 + res1.get(i) + "\n";
                        }
                        else {
                            temp2 = temp2 + res1.get(i) + ",\n";
                        }
                    }
                    res2 = res2 + "\"actors\":[" + temp2 + "] \n}";
                    res = res + res2;
                    exchange.sendResponseHeaders(200, res.length());
                }
            } catch (Neo4jException e) {
                res = "save or add was unsuccessful";
                exchange.sendResponseHeaders(500, res.length());
            }
        }
        else {
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