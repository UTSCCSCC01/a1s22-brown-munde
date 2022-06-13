package ca.utoronto.utm.mcs;

import dagger.internal.DaggerGenerated;
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
public final class DaggerServerComponent implements ServerComponent {
  private final ServerModule serverModule;

  private DaggerServerComponent(ServerModule serverModuleParam) {
    this.serverModule = serverModuleParam;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static ServerComponent create() {
    return new Builder().build();
  }

  @Override
  public Server buildServer() {
    return new Server(ServerModule_ProvideHttpsServerFactory.provideHttpsServer(serverModule));
  }

  public static final class Builder {
    private ServerModule serverModule;

    private Builder() {
    }

    public Builder serverModule(ServerModule serverModule) {
      this.serverModule = Preconditions.checkNotNull(serverModule);
      return this;
    }

    public ServerComponent build() {
      if (serverModule == null) {
        this.serverModule = new ServerModule();
      }
      return new DaggerServerComponent(serverModule);
    }
  }
}
