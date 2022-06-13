package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpsServer;
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
public final class ServerModule_ProvideHttpsServerFactory implements Factory<HttpsServer> {
  private final ServerModule module;

  public ServerModule_ProvideHttpsServerFactory(ServerModule module) {
    this.module = module;
  }

  @Override
  public HttpsServer get() {
    return provideHttpsServer(module);
  }

  public static ServerModule_ProvideHttpsServerFactory create(ServerModule module) {
    return new ServerModule_ProvideHttpsServerFactory(module);
  }

  public static HttpsServer provideHttpsServer(ServerModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideHttpsServer());
  }
}
