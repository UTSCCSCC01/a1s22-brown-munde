package ca.utoronto.utm.mcs;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
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
public final class ReqHandlerModule_ProvideDriverFactory implements Factory<GraphDatabase> {
  private final ReqHandlerModule module;

  public ReqHandlerModule_ProvideDriverFactory(ReqHandlerModule module) {
    this.module = module;
  }

  @Override
  public GraphDatabase get() {
    return provideDriver(module);
  }

  public static ReqHandlerModule_ProvideDriverFactory create(ReqHandlerModule module) {
    return new ReqHandlerModule_ProvideDriverFactory(module);
  }

  public static GraphDatabase provideDriver(ReqHandlerModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideDriver());
  }
}
