package ca.utoronto.utm.mcs.handlers;

import ca.utoronto.utm.mcs.Neo4jDAO;
import ca.utoronto.utm.mcs.Utils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.exceptions.Neo4jException;

public class Actor implements HttpHandler {

  Neo4jDAO njDB;
  /**
   * This method injects the neo4j object in the class
   * @param njDb the neo4j objected to be injected
   */
  public Actor(Neo4jDAO njDb) {
    this.njDB = njDb;
  }


  /**
   * This method handles the which function to call for actor get/put
   * @param exchange the request sent to the server
   * @throws IOException
   */
  public void handle(HttpExchange exchange) throws IOException {
    try {
      if (exchange.getRequestMethod().equals("PUT")) {
        this.handlePut(exchange);
      }
      else if(exchange.getRequestMethod().equals("GET")){
        this.handleGet(exchange);
      }
      else {
        this.invalidRoute(exchange);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This method handles the put request for actor
   * @param exchange the request sent to the server
   * @throws IOException
   * @throws JSONException
   */
  public void handlePut(HttpExchange exchange) throws IOException, JSONException {
    String body = Utils.convert(exchange.getRequestBody());
    JSONObject deserialized = new JSONObject(body);
    String name = "", actorId = "";

    String response = "";

    if (deserialized.has("name") && deserialized.has("actorId")) {
      try {
        name = deserialized.getString("name");
        actorId = deserialized.getString("actorId");

        String query = "CREATE (actor: Actor {name: \"%s\", id: \"%s\"}) RETURN actor";
        query = String.format(query, name, actorId);
        njDB.makeQuery(query);
        response = "Actor was successfully added!";
        exchange.sendResponseHeaders(200, response.length());
      } catch (Neo4jException e) {
        if (e.getMessage().contains("already exists")){
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

  /**
   * This method handles the get request for actor
   * @param exchange the request sent to the server
   * @throws IOException
   * @throws JSONException
   */
  public void handleGet(HttpExchange exchange) throws IOException, JSONException {
    String body = Utils.convert(exchange.getRequestBody());
    JSONObject deserialized = new JSONObject(body);
    String actorId = "";
    exchange.getResponseHeaders().set("Content-Type", "application/json");
    String res="";
    ArrayList<String> temp = new ArrayList<String>();
    if (deserialized.has("actorId")) {
      try {
        actorId = deserialized.getString("actorId");
        String query = "MATCH (a: Actor {id: \"%s\"}) RETURN a.name,a.id";
        query = String.format(query, actorId);
        temp = njDB.getActor(query);
        if (temp.isEmpty()){
          res = "No such actor";
          exchange.sendResponseHeaders(404, res.length());
        }
        else {
          ArrayList<String> res1 = temp;
          res = "{\"name\":" + res1.get(0) + ",\n\"actorId\":" + res1.get(1) + ",\n";
          String res2 = "";
          String temp2 = "";
          query = "MATCH (a: Actor {id: \"%s\"}) - [:ACTED_IN]->(b) RETURN b.id";
          query = String.format(query, actorId);
          temp = njDB.getMovies(query);
          res1 = temp;
          for(int i = 0;i<res1.size();i++){
            if(i == res1.size()-1){
              temp2 = temp2 + res1.get(i) + "\n";
            }
            else {
              temp2 = temp2 + res1.get(i) + ",\n";
            }
          }
          res2 = res2 + "\"movies\":[" + temp2 + "] \n}";
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

  /**
   * This method handles any invalid request
   * @param exchange the request sent to server
   * @throws IOException
   * @throws JSONException
   */
  public void invalidRoute(HttpExchange exchange) throws IOException, JSONException {
    String response = "Not Found";
    exchange.sendResponseHeaders(404, response.length());
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }
}
