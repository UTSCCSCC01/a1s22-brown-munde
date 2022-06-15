package ca.utoronto.utm.mcs.handlers;

import ca.utoronto.utm.mcs.Neo4jDAO;
import ca.utoronto.utm.mcs.ReqHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import javax.inject.Inject;
import org.json.JSONException;

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
      else if (exchange.getRequestMethod().equals("GET")) {
        this.handleGet(exchange);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void handleGet(HttpExchange exchange) throws IOException, JSONException {
    String query = "CREATE (actor: Actor {name: \"Johnson\", actorId: \"123456\"})";
    njDB.makeQuery(query);

    String response = "This route get for /addActor";
    exchange.sendResponseHeaders(200, response.length());
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }

  public void handlePut(HttpExchange exchange) throws IOException, JSONException {
    String query = "CREATE (actor: Actor: {name: \"Johnson\", actorId: \"123456\"})";
    njDB.makeQuery(query);

    String response = "This route put for /addActor";
    exchange.sendResponseHeaders(200, response.length());
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }

}
