package ca.utoronto.utm.mcs;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ReqHandler implements HttpHandler {

    // TODO Complete This Class

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "This is the response";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}