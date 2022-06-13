package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.inject.Inject;
import javax.net.ssl.SSLContext;

public class Server {
    // TODO Complete This Class
  HttpsServer httpsServer;

  @Inject
  public Server(HttpsServer httpsServer){
    this.httpsServer = httpsServer;
  }

  public void createContext(HttpHandler handler){
    httpsServer.createContext("/", handler);
    System.out.println("created handler for /" + handler.toString());
  }


  public void run(int port) throws IOException {
    System.out.println("server running on " + port);
    httpsServer.bind(new InetSocketAddress("localhost", port), 0);
//    httpsServer.setHttpsConfigurator(config);
    httpsServer.setExecutor(null);
    httpsServer.start();
  }
}
