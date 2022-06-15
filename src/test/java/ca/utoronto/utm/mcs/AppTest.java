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
    }
    @AfterAll
    public static void teardown() {

    }
    @BeforeEach
    public void setup() throws IOException {
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
                .uri(URI.create("http://127.0.0.1:8080/api/v1/addActor"))
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
                .uri(URI.create("http://127.0.0.1:8080/api/v1/addActor"))
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
                .uri(URI.create("http://127.0.0.1:8080/api/v1/addMovie"))
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
                .uri(URI.create("http://127.0.0.1:8080/api/v1/addMovie"))
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


}
