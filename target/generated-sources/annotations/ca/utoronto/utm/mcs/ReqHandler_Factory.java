package ca.utoronto.utm.mcs;

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
public final class ReqHandler_Factory implements Factory<ReqHandler> {
  private final Provider<Neo4jDAO> njDbProvider;

  public ReqHandler_Factory(Provider<Neo4jDAO> njDbProvider) {
    this.njDbProvider = njDbProvider;
  }

  @Override
  public ReqHandler get() {
    return newInstance(njDbProvider.get());
  }

  public static ReqHandler_Factory create(Provider<Neo4jDAO> njDbProvider) {
    return new ReqHandler_Factory(njDbProvider);
  }

  public static ReqHandler newInstance(Neo4jDAO njDb) {
    return new ReqHandler(njDb);
  }
}
