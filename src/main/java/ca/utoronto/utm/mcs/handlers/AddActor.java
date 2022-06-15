package ca.utoronto.utm.mcs.handlers;

import ca.utoronto.utm.mcs.Neo4jDAO;
import ca.utoronto.utm.mcs.ReqHandler;
import ca.utoronto.utm.mcs.Utils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import javax.inject.Inject;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.exceptions.Neo4jException;

public class AddActor implements HttpHandler {

  Neo4jDAO njDB;
  public AddActor(Neo4jDAO njDb) {
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
    String name = "", actorId = "";

    String response = "";

    if (deserialized.has("name") && deserialized.has("actorId")) {
      try {
        name = deserialized.getString("name");
        actorId = deserialized.getString("actorId");

        String query = "CREATE (actor: Actor {name: \"%s\", actorId: \"%s\"}) RETURN actor";
        query = String.format(query, name, actorId);
        njDB.makeQuery(query);
        System.out.println("Actor was successfully added!");
        response = "Actor was successfully added!";
        exchange.sendResponseHeaders(200, response.length());
      } catch (Neo4jException e) {
        if (e.getMessage().indexOf("already exists") != -1){
          response = "The actorId is already in use";
          exchange.sendResponseHeaders(400, response.length());
        }
        else {
          response = "Failed to add actors\n" + e.getMessage();
          exchange.sendResponseHeaders(500, response.length());
        }
      }
      catch (Exception e){
        response = "Please properly format the body\n" + e.getMessage();
        exchange.sendResponseHeaders(400, response.length());
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
