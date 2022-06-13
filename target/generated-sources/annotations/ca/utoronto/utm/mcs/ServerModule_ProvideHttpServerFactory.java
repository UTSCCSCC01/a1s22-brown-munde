package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpServer;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ServerModule_ProvideHttpServerFactory implements Factory<HttpServer> {
  private final ServerModule module;

  public ServerModule_ProvideHttpServerFactory(ServerModule module) {
    this.module = module;
  }

  @Override
  public HttpServer get() {
    return provideHttpServer(module);
  }

  public static ServerModule_ProvideHttpServerFactory create(ServerModule module) {
    return new ServerModule_ProvideHttpServerFactory(module);
  }

  public static HttpServer provideHttpServer(ServerModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideHttpServer());
  }
}
