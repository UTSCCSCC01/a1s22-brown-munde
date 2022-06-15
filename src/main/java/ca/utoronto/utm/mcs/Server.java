package ca.utoronto.utm.mcs;

import ca.utoronto.utm.mcs.handlers.TestHandler;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.inject.Inject;
import javax.inject.Singleton;
public class Server {
    // TODO Complete This Class
  HttpServer httpServer;

  @Inject
  public Server(HttpServer httpServer){
    this.httpServer = httpServer;
  }

  /*private void setRoutes(){
    httpServer.createContext("/", new TestHandler());
  }*/

  public void run(int port) throws IOException {
    InetSocketAddress socketAddress = new InetSocketAddress("localhost", port);
    httpServer.bind(socketAddress, 0);
    //setRoutes();
    ReqHandler requestHandler;
    ReqHandlerComponent reqComponent = DaggerReqHandlerComponent.create();
    requestHandler = reqComponent.buildHandler();
    requestHandler.njDb.insertPokemon("Char","2","red","a","b");
    System.out.println("Starting server at address " + httpServer.getAddress());
    httpServer.setExecutor(null);
    httpServer.start();
  }
}
