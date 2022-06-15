package ca.utoronto.utm.mcs.handlers;

import ca.utoronto.utm.mcs.Neo4jDAO;
import ca.utoronto.utm.mcs.ReqHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import javax.inject.Inject;
import org.json.JSONException;
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

  public void handlePut(HttpExchange exchange) throws IOException {
    String response = "";
    try {
      String query = "CREATE (actor: Actor {name: \"Johnson\", actorId: \"123456\"}) RETURN actor";
      njDB.makeQuery(query);

      response = "This route put for /addActor";
      exchange.sendResponseHeaders(200, response.length());
    } catch (Neo4jException e){
      if (e.getMessage().indexOf("already exists") != -1){
        response = "The actorId is already in use";
      }
      else {
        response = e.getMessage();
      }
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
