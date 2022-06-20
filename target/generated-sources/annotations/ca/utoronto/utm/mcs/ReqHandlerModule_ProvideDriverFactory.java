package ca.utoronto.utm.mcs;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import org.neo4j.driver.Driver;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ReqHandlerModule_ProvideDriverFactory implements Factory<Driver> {
  private final ReqHandlerModule module;

  private final Provider<String> uriDbProvider;

  public ReqHandlerModule_ProvideDriverFactory(ReqHandlerModule module,
      Provider<String> uriDbProvider) {
    this.module = module;
    this.uriDbProvider = uriDbProvider;
  }

  @Override
  public Driver get() {
    return provideDriver(module, uriDbProvider.get());
  }

  public static ReqHandlerModule_ProvideDriverFactory create(ReqHandlerModule module,
      Provider<String> uriDbProvider) {
    return new ReqHandlerModule_ProvideDriverFactory(module, uriDbProvider);
  }

  public static Driver provideDriver(ReqHandlerModule instance, String uriDb) {
    return Preconditions.checkNotNullFromProvides(instance.provideDriver(uriDb));
  }
}
