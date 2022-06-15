package ca.utoronto.utm.mcs.handlers;

import ca.utoronto.utm.mcs.Neo4jDAO;
import ca.utoronto.utm.mcs.ReqHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import org.json.JSONException;

public class TestHandler extends ReqHandler {

  public TestHandler(Neo4jDAO njDb) {
    super(njDb);
  }

  @Override
  public void handleGet(HttpExchange exchange) throws IOException, JSONException {
    String response = "This is GET request to /test";
    exchange.sendResponseHeaders(200, response.length());
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }

  @Override
  public void handlePost(HttpExchange exchange) throws IOException, JSONException {
    String response = "This is POST request to /test";
    exchange.sendResponseHeaders(200, response.length());
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }
}
