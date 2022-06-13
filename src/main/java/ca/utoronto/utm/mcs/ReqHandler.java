package ca.utoronto.utm.mcs;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.OutputStream;
import org.json.*;
import javax.inject.Inject;

public class ReqHandler implements HttpHandler {
    // TODO Complete This Class
    Neo4jDAO njDb;

    @Inject
    public ReqHandler(Neo4jDAO njDb){
        this.njDb = njDb;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equals("POST")) {
                this.handlePost(exchange);
            }
            else if (exchange.getRequestMethod().equals("GET")) {
                this.handleGet(exchange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleGet(HttpExchange exchange) throws IOException, JSONException {
        String response = "This route is not implemented yet";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    public void handlePost(HttpExchange exchange) throws IOException, JSONException {
        String response = "This route is not implemented yet";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}