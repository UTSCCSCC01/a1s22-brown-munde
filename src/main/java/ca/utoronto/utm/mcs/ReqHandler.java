package ca.utoronto.utm.mcs;

import ca.utoronto.utm.mcs.handlers.AddActor;
import ca.utoronto.utm.mcs.handlers.AddMovie;
import java.io.IOException;
import ca.utoronto.utm.mcs.handlers.GetActor;
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
        njDb.initialSetup();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            System.out.println(exchange.getRequestURI());
            switch (exchange.getRequestURI().toString()){
                case "/api/v1/addActor":
                    new AddActor(njDb).handle(exchange);
                    break;
                case "/api/v1/addMovie":
                    new AddMovie(njDb).handle(exchange);
                    break;
                case "/api/v1/getActor":
                    new GetActor(njDb).handle(exchange);
                    break;
                default:
                    invalidRoute(exchange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void invalidRoute(HttpExchange exchange) throws IOException, JSONException {
        String response = "Not Found";
        exchange.sendResponseHeaders(404, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}