package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import io.github.cdimascio.dotenv.Dotenv;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.*;
import org.neo4j.driver.GraphDatabase;
import java.io.IOException;
import java.net.InetSocketAddress;


public class App
{
    static int port = 8080;
    static Driver driver;

    private static Server server;
    private static ReqHandler reqHandler;
    private static Neo4jDAO neo4jDAO;
    public static void main(String[] args) throws IOException
    {
        // TODO Create Your Server Context Here, There Should Only Be One Context
        ServerComponent component = DaggerServerComponent.create();
        server = component.buildServer();
        server.run(port);
        System.out.printf("Server started on port %d\n", port);

        // This code is used to get the neo4j address, you must use this so that we can mark :)
        Dotenv dotenv = Dotenv.load();
        String addr = dotenv.get("NEO4J_ADDR");
        System.out.println(addr);
        String username = dotenv.get("USERNAME");
        System.out.println(username);
        String password = dotenv.get("PASSWORD");
        System.out.println(password);

    }
}
