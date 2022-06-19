package ca.utoronto.utm.mcs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.exceptions.Neo4jException;

// TODO Please Write Your Tests For CI/CD In This Class. You will see
// these tests pass/fail on github under github actions.
public class AppTest {
    private static Neo4jDAO njDb;
    @BeforeAll
    public static void init() throws IOException {
        njDb = DaggerReqHandlerComponent.create().buildHandler().njDb;
        Runtime.getRuntime().exec("mvn exec:java");
    }
    @AfterAll
    public static void teardown() {

    }
    @BeforeEach
    public void setup() throws IOException {
        System.out.println("Starting test");
    }
    @AfterEach
    public void cleanup()
    {
        njDb.cleanup();
    }
    @Test
    public void addActorPass() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body = "{\"name\": \"John\", \"actorId\": \"123456\"}";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addActor"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body))
                    .build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }

    @Test
    public void addActorFail() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body = "{\"name\": \"John\"}";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addActor"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body))
                    .build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            assertEquals(400, response.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }

    @Test
    public void addMoviePass() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body = "{\"name\": \"The Power\", \"movieId\": \"123456\"}";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addMovie"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body))
                    .build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }

    @Test
    public void addMovieFail() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body = "{\"name\": \"The Power\"}";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addMovie"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body))
                    .build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            assertEquals(400, response.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }

    @Test
    public void addRelationshipPass() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body1 = "{\"name\": \"John\", \"actorId\": \"123456\"}";
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addActor"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body1))
                    .build();
            client.send(request1, BodyHandlers.ofString());

            String body2 = "{\"name\": \"The Power\", \"movieId\": \"123456\"}";
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addMovie"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body2))
                    .build();
            client.send(request2, BodyHandlers.ofString());

            String body3 = "{\"actorId\": \"123456\", \"movieId\": \"123456\"}";
            HttpRequest request3 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addRelationship"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body3))
                    .build();
            HttpResponse<String> response = client.send(request3, BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }

    @Test
    public void addRelationshipFail() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body1 = "{\"actorId\": \"123456\", \"movieId\": \"123456\"}";
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addRelationship"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body1))
                    .build();
            HttpResponse<String> response1 = client.send(request1, BodyHandlers.ofString());

            String body2 = "{\"actorId\": \"123456\"}";
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addRelationship"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body2))
                    .build();
            HttpResponse<String> response2 = client.send(request2, BodyHandlers.ofString());
            assertTrue(404 == response1.statusCode() && 400 == response2.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }

    @Test
    public void getActorPass() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body1 = "{\"name\": \"John\", \"actorId\": \"123456\"}";
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addActor"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body1))
                    .build();
            client.send(request1, BodyHandlers.ofString());

            String body2 = "{\"actorId\": \"123456\"}";
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/getActor"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body2))
                    .build();
            HttpResponse<String> response = client.send(request2, BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }

    @Test
    public void getActorFail() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body1 = "{\"name\": \"John\", \"actorId\": \"123456\"}";
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addActor"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body1))
                    .build();
            client.send(request1, BodyHandlers.ofString());

            String body2 = "{\"actorId\": \"123456789\"}";
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/getActor"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body2))
                    .build();
            HttpResponse<String> response2 = client.send(request2, BodyHandlers.ofString());

            String body3 = "{\"name\": \"John\"}";
            HttpRequest request3 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/getActor"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body3))
                    .build();
            HttpResponse<String> response3 = client.send(request3, BodyHandlers.ofString());
            assertTrue(404 == response2.statusCode() && 400 == response3.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }

    @Test
    public void getMoviePass() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body1 = "{\"name\": \"The Power\", \"movieId\": \"123456\"}";
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addMovie"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body1))
                    .build();
            client.send(request1, BodyHandlers.ofString());

            String body2 = "{\"movieId\": \"123456\"}";
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/getMovie"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body2))
                    .build();
            HttpResponse<String> response = client.send(request2, BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }

    @Test
    public void getMovieFail() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body1 = "{\"name\": \"The Power\", \"movieId\": \"123456\"}";
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addMovie"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body1))
                    .build();
            client.send(request1, BodyHandlers.ofString());

            String body2 = "{\"movieId\": \"123456789\"}";
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/getMovie"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body2))
                    .build();
            HttpResponse<String> response2 = client.send(request2, BodyHandlers.ofString());

            String body3 = "{\"name\": \"The Power\"}";
            HttpRequest request3 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/getMovie"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body3))
                    .build();
            HttpResponse<String> response3 = client.send(request3, BodyHandlers.ofString());
            assertTrue(404 == response2.statusCode() && 400 == response3.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }


    @Test
    public void hasRelationshipPass() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body1 = "{\"name\": \"John\", \"actorId\": \"123456\"}";
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addActor"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body1))
                    .build();
            client.send(request1, BodyHandlers.ofString());

            String body2 = "{\"name\": \"The Power\", \"movieId\": \"123456\"}";
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addMovie"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body2))
                    .build();
            client.send(request2, BodyHandlers.ofString());

            String body3 = "{\"actorId\": \"123456\", \"movieId\": \"123456\"}";
            HttpRequest request3 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addRelationship"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body3))
                    .build();
            client.send(request3, BodyHandlers.ofString());

            String body4 = "{\"actorId\": \"123456\", \"movieId\": \"123456\"}";
            HttpRequest request4 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/hasRelationship"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body4))
                    .build();
            HttpResponse<String> response = client.send(request4, BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }

    @Test
    public void hasRelationshipFail() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body1 = "{\"actorId\": \"123456\", \"movieId\": \"123456\"}";
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/hasRelationship"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body1))
                    .build();
            HttpResponse<String> response1 = client.send(request1, BodyHandlers.ofString());

            String body2 = "{\"actorId\": \"123456\"}";
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/hasRelationship"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body2))
                    .build();
            HttpResponse<String> response2 = client.send(request2, BodyHandlers.ofString());
            assertTrue(404 == response1.statusCode() && 400 == response2.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }

    @Test
    public void computeBaconNumberPass() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body1 = "{\"name\": \"John\", \"actorId\": \"123456\"}";
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addActor"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body1))
                    .build();
            client.send(request1, BodyHandlers.ofString());

            String body2 = "{\"name\": \"The Power\", \"movieId\": \"123456\"}";
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addMovie"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body2))
                    .build();
            client.send(request2, BodyHandlers.ofString());

            String body4 = "{\"name\": \"Kevin Bacon\", \"actorId\": \"nm0000102\"}";
            HttpRequest request4 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addActor"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body4))
                    .build();
            client.send(request4, BodyHandlers.ofString());

            String body5 = "{\"name\": \"Parasite\", \"movieId\": \"4321\"}";
            HttpRequest request5 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addMovie"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body5))
                    .build();
            client.send(request5, BodyHandlers.ofString());

            String body6 = "{\"actorId\": \"nm0000102\", \"movieId\": \"4321\"}";
            HttpRequest request6 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addRelationship"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body6))
                    .build();
            client.send(request6, BodyHandlers.ofString());

            String body3 = "{\"actorId\": \"123456\", \"movieId\": \"4321\"}";
            HttpRequest request3 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addRelationship"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body3))
                    .build();
            client.send(request3, BodyHandlers.ofString());

            String body7 = "{\"actorId\": \"123456\"}";
            HttpRequest request7 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/computeBaconNumber"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body7))
                    .build();
            HttpResponse<String> response1 = client.send(request7, BodyHandlers.ofString());

            String body8 = "{\"actorId\": \"nm0000102\"}";
            HttpRequest request8 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/computeBaconNumber"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body8))
                    .build();
            HttpResponse<String> response2 = client.send(request8, BodyHandlers.ofString());

            String body9 = "{\"baconNumber\":1}";
            String body10 = "{\"baconNumber\":0}";
            assertTrue(200 == response1.statusCode() && 200 == response2.statusCode() && body9.equals(response1.body()) && body10.equals(response2.body()));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }

    @Test
    public void computeBaconNumberFail() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body1 = "{\"name\": \"John\", \"actorId\": \"123456\"}";
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addActor"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body1))
                    .build();
            client.send(request1, BodyHandlers.ofString());

            String body2 = "{\"name\": \"The Power\", \"movieId\": \"123456\"}";
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addMovie"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body2))
                    .build();
            client.send(request2, BodyHandlers.ofString());

            String body4 = "{\"name\": \"Kevin Bacon\", \"actorId\": \"nm0000102\"}";
            HttpRequest request4 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addActor"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body4))
                    .build();
            client.send(request4, BodyHandlers.ofString());

            String body5 = "{\"name\": \"Parasite\", \"movieId\": \"4321\"}";
            HttpRequest request5 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addMovie"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body5))
                    .build();
            client.send(request5, BodyHandlers.ofString());

            String body6 = "{\"actorId\": \"nm0000102\", \"movieId\": \"4321\"}";
            HttpRequest request6 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addRelationship"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body6))
                    .build();
            client.send(request6, BodyHandlers.ofString());

            String body3 = "{\"acd\": \"234\"}";
            HttpRequest request3 = HttpRequest.newBuilder()
                    .uri(URI.create("http://127.0.0.1:8080/api/v1/computeBaconNumber"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body3))
                    .build();
            HttpResponse<String> response3 = client.send(request3, BodyHandlers.ofString());

            String body7 = "{\"actorId\": \"123456\"}";
            HttpRequest request7 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/computeBaconNumber"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body7))
                    .build();
            HttpResponse<String> response1 = client.send(request7, BodyHandlers.ofString());

            String body8 = "{\"actorId\": \"234\"}";
            HttpRequest request8 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/computeBaconNumber"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body8))
                    .build();
            HttpResponse<String> response2 = client.send(request8, BodyHandlers.ofString());

            assertTrue(404 == response1.statusCode() && 404 == response2.statusCode() && 400 == response3.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }

    @Test
    public void computeBaconPathPass() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body1 = "{\"name\": \"John\", \"actorId\": \"123456\"}";
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addActor"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body1))
                    .build();
            client.send(request1, BodyHandlers.ofString());

            String body2 = "{\"name\": \"The Power\", \"movieId\": \"123456\"}";
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addMovie"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body2))
                    .build();
            client.send(request2, BodyHandlers.ofString());

            String body4 = "{\"name\": \"Kevin Bacon\", \"actorId\": \"nm0000102\"}";
            HttpRequest request4 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addActor"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body4))
                    .build();
            client.send(request4, BodyHandlers.ofString());

            String body5 = "{\"name\": \"Parasite\", \"movieId\": \"4321\"}";
            HttpRequest request5 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addMovie"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body5))
                    .build();
            client.send(request5, BodyHandlers.ofString());

            String body6 = "{\"actorId\": \"nm0000102\", \"movieId\": \"4321\"}";
            HttpRequest request6 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addRelationship"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body6))
                    .build();
            client.send(request6, BodyHandlers.ofString());

            String body3 = "{\"actorId\": \"123456\", \"movieId\": \"4321\"}";
            HttpRequest request3 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addRelationship"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body3))
                    .build();
            client.send(request3, BodyHandlers.ofString());

            String body7 = "{\"actorId\": \"123456\"}";
            HttpRequest request7 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/computeBaconPath"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body7))
                    .build();
            HttpResponse<String> response1 = client.send(request7, BodyHandlers.ofString());

            String body8 = "{\"actorId\": \"nm0000102\"}";
            HttpRequest request8 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/computeBaconPath"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body8))
                    .build();
            HttpResponse<String> response2 = client.send(request8, BodyHandlers.ofString());
            assertTrue(200 == response1.statusCode() && 200 == response2.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }

    @Test
    public void computeBaconPathFail() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body1 = "{\"name\": \"John\", \"actorId\": \"123456\"}";
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addActor"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body1))
                    .build();
            client.send(request1, BodyHandlers.ofString());

            String body2 = "{\"name\": \"The Power\", \"movieId\": \"123456\"}";
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addMovie"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body2))
                    .build();
            client.send(request2, BodyHandlers.ofString());

            String body4 = "{\"name\": \"Kevin Bacon\", \"actorId\": \"nm0000102\"}";
            HttpRequest request4 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addActor"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body4))
                    .build();
            client.send(request4, BodyHandlers.ofString());

            String body5 = "{\"name\": \"Parasite\", \"movieId\": \"4321\"}";
            HttpRequest request5 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addMovie"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body5))
                    .build();
            client.send(request5, BodyHandlers.ofString());

            String body6 = "{\"actorId\": \"nm0000102\", \"movieId\": \"4321\"}";
            HttpRequest request6 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/addRelationship"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(body6))
                    .build();
            client.send(request6, BodyHandlers.ofString());

            String body3 = "{\"actod\": \"234\"}";
            HttpRequest request3 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/computeBaconPath"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body3))
                    .build();
            HttpResponse<String> response3 = client.send(request3, BodyHandlers.ofString());

            String body7 = "{\"actorId\": \"123456\"}";
            HttpRequest request7 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/computeBaconPath"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body7))
                    .build();
            HttpResponse<String> response1 = client.send(request7, BodyHandlers.ofString());

            String body8 = "{\"actorId\": \"234\"}";
            HttpRequest request8 = HttpRequest.newBuilder()
                    .uri(URI.create("http://0.0.0.0:8080/api/v1/computeBaconPath"))
                    .header("Content-Type", "application/json")
                    .method("GET", BodyPublishers.ofString(body8))
                    .build();
            HttpResponse<String> response2 = client.send(request8, BodyHandlers.ofString());
            assertTrue(404 == response1.statusCode() && 404 == response2.statusCode() && 400 == response3.statusCode());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "error");
        }
    }
}

