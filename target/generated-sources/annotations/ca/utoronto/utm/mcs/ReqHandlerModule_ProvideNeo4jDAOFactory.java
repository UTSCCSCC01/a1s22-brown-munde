package ca.utoronto.utm.mcs;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import org.neo4j.driver.GraphDatabase;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ReqHandlerModule_ProvideNeo4jDAOFactory implements Factory<Neo4jDAO> {
  private final ReqHandlerModule module;

  private final Provider<GraphDatabase> driverProvider;

  public ReqHandlerModule_ProvideNeo4jDAOFactory(ReqHandlerModule module,
      Provider<GraphDatabase> driverProvider) {
    this.module = module;
    this.driverProvider = driverProvider;
  }

  @Override
  public Neo4jDAO get() {
    return provideNeo4jDAO(module, driverProvider.get());
  }

  public static ReqHandlerModule_ProvideNeo4jDAOFactory create(ReqHandlerModule module,
      Provider<GraphDatabase> driverProvider) {
    return new ReqHandlerModule_ProvideNeo4jDAOFactory(module, driverProvider);
  }

  public static Neo4jDAO provideNeo4jDAO(ReqHandlerModule instance, GraphDatabase driver) {
    return Preconditions.checkNotNullFromProvides(instance.provideNeo4jDAO(driver));
  }
}
