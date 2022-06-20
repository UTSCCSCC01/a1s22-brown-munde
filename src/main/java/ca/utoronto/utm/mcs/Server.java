package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.inject.Inject;

public class Server {
    // TODO Complete This Class
  HttpServer httpServer;

  @Inject
  public Server(HttpServer httpServer){
    this.httpServer = httpServer;
  }

  private void setRoutes(){
    ReqHandlerComponent reqComponent = DaggerReqHandlerComponent.create();
    ReqHandler reqHandler = reqComponent.buildHandler();
    httpServer.createContext("/api/v1/", reqHandler);
  }

  public void run(int port) throws IOException {
    InetSocketAddress socketAddress = new InetSocketAddress("0.0.0.0", port);
    httpServer.bind(socketAddress, 0);
    setRoutes();
    System.out.println("Starting server at address " + httpServer.getAddress());
    httpServer.setExecutor(null);
    httpServer.start();
  }
}
