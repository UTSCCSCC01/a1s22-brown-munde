package ca.utoronto.utm.mcs.handlers;

import ca.utoronto.utm.mcs.Neo4jDAO;
import ca.utoronto.utm.mcs.Utils;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.exceptions.Neo4jException;

public class Movie implements HttpHandler {

  Neo4jDAO njDB;
  /**
   * This method injects the neo4j object in the class
   * @param njDb the neo4j objected to be injected
   */
  public Movie(Neo4jDAO njDb) {
    this.njDB = njDb;
  }

  /**
   * This method handles the which function to call for movie get/put
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
   * This method handles the put request for movie
   * @param exchange the request sent to the server
   * @throws IOException
   * @throws JSONException
   */
  public void handlePut(HttpExchange exchange) throws IOException, JSONException {
    String body = Utils.convert(exchange.getRequestBody());
    JSONObject deserialized = new JSONObject(body);
    String name = "", movieId = "";

    String response = "";

    if (deserialized.has("name") && deserialized.has("movieId")) {
      try {
        name = deserialized.getString("name");
        movieId = deserialized.getString("movieId");

        String query = "CREATE (movie: Movie {name: \"%s\", id: \"%s\"}) RETURN movie";
        query = String.format(query, name, movieId);
        njDB.makeQuery(query);
        response = "Movie was successfully added!";
        exchange.sendResponseHeaders(200, response.length());
      } catch (Neo4jException e) {
        if (e.getMessage().contains("already exists")){
          response = "The movieId is already in use";
          exchange.sendResponseHeaders(400, response.length());
        }
        else {
          response = "Failed to add movie\n" + e.getMessage();
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
   * This method handles the get request for movie
   * @param exchange the request sent to the server
   * @throws IOException
   * @throws JSONException
   */
  public void handleGet(HttpExchange exchange) throws IOException, JSONException {
    String body = Utils.convert(exchange.getRequestBody());
    JSONObject deserialized = new JSONObject(body);
    String movieId = "";
    exchange.getResponseHeaders().set("Content-Type", "application/json");
    String res="";
    ArrayList<String> temp = new ArrayList<String>();
    if (deserialized.has("movieId")) {
      try {
        movieId = deserialized.getString("movieId");
        String query = "MATCH (a: Movie {id: \"%s\"}) RETURN a.name,a.id";
        query = String.format(query, movieId);
        temp = njDB.getMovie(query);
        if (temp.isEmpty()){
          res = "No such movie";
          exchange.sendResponseHeaders(404, res.length());
        }
        else {
          ArrayList<String> res1 = temp;
          res = "{\"name\":" + res1.get(0) + ",\n\"movieId\":" + res1.get(1) + ",\n";
          String res2 = "";
          String temp2 = "";
          query = "MATCH (a: Movie {id: \"%s\"}) <- [:ACTED_IN]-(b) RETURN b.id";
          query = String.format(query, movieId);
          temp = njDB.getActors(query);
          res1 = temp;
          for(int i = 0;i<res1.size();i++){
            if(i == res1.size()-1){
              temp2 = temp2 + res1.get(i) + "\n";
            }
            else {
              temp2 = temp2 + res1.get(i) + ",\n";
            }
          }
          res2 = res2 + "\"actors\":[" + temp2 + "] \n}";
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
