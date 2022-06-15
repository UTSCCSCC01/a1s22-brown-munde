package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpServer;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class Server_Factory implements Factory<Server> {
  private final Provider<HttpServer> httpServerProvider;

  public Server_Factory(Provider<HttpServer> httpServerProvider) {
    this.httpServerProvider = httpServerProvider;
  }

  @Override
  public Server get() {
    return newInstance(httpServerProvider.get());
  }

  public static Server_Factory create(Provider<HttpServer> httpServerProvider) {
    return new Server_Factory(httpServerProvider);
  }

  public static Server newInstance(HttpServer httpServer) {
    return new Server(httpServer);
  }
}
