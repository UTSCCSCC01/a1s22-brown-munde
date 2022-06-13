package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
public class Server {
    // TODO Complete This Class
  HttpServer httpServer;

  @Inject
  public Server(HttpServer httpServer){
    this.httpServer = httpServer;
  }


  public void run(int port, HttpHandler handler) throws IOException {
    InetSocketAddress socketAddress = new InetSocketAddress("localhost", port);
    httpServer.bind(socketAddress, 0);
    httpServer.createContext("/", handler);
    System.out.println(handler);
    System.out.println("Starting server at address " + httpServer.getAddress());
    httpServer.setExecutor(null);
    httpServer.start();
  }
}
