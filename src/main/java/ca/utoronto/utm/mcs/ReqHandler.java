package ca.utoronto.utm.mcs;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.OutputStream;
import org.json.*;
import org.json.JSONObject;

public class ReqHandler implements HttpHandler {

    // TODO Complete This Class
//    HttpHandler handler;


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Hello World";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}