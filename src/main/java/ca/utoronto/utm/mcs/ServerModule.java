package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpsServer;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import java.net.InetSocketAddress;

@Module
public class ServerModule {
    // TODO Complete This Module

  @Provides
  HttpsServer provideHttpsServer() {
    try {
      return HttpsServer.create();
    }
    catch (IOException err){
      System.out.println(err);
    }
    return null;
  }
}
