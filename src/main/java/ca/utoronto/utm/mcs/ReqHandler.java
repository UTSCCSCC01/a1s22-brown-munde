package ca.utoronto.utm.mcs;

import ca.utoronto.utm.mcs.handlers.*;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.OutputStream;

import javax.inject.Inject;

public class ReqHandler implements HttpHandler {

    // TODO Complete This Class
    Neo4jDAO njDb;

    /**
     * This function injects the neo4j object in the req handler
     * @param njDb the neo4j object to be injected
     */
    @Inject
    public ReqHandler(Neo4jDAO njDb){
        this.njDb = njDb;
        njDb.initialSetup();
    }

    /**
     * This functions calls the appropriate routes based on the request
     * @param exchange The request from the client to the server
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!njDb.setupDone){
            njDb.initialSetup();
        }

        try {
            System.out.println(exchange.getRequestURI());
            switch (exchange.getRequestURI().toString()){
                case "/api/v1/addActor":
                    new Actor(njDb).handle(exchange);
                    break;
                case "/api/v1/addMovie":
                    new Movie(njDb).handle(exchange);
                    break;
                case "/api/v1/hasRelationship":
                    new Relationship(njDb).handle(exchange);
                    break;
                case "/api/v1/getActor":
                    new Actor(njDb).handle(exchange);
                    break;
                case "/api/v1/getMovie":
                    new Movie(njDb).handle(exchange);
                    break;
                case "/api/v1/addRelationship":
                    new Relationship(njDb).handle(exchange);
                    break;
                case "/api/v1/computeBaconPath":
                    new ComputeBaconPath(njDb).handle(exchange);
                    break;
                case "/api/v1/computeBaconNumber":
                    new ComputeBaconNumber(njDb).handle(exchange);
                    break;
                default:
                    invalidRoute(exchange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function deals with any invalid routes requested by the client
     * @param exchange The request from the client to the server
     * @throws IOException
     */
    public void invalidRoute(HttpExchange exchange) throws IOException{
        String response = "Not Found";
        exchange.sendResponseHeaders(404, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}