package ca.utoronto.utm.mcs;

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
public final class ReqHandlerModule_ProvideUriDbFactory implements Factory<String> {
  private final ReqHandlerModule module;

  public ReqHandlerModule_ProvideUriDbFactory(ReqHandlerModule module) {
    this.module = module;
  }

  @Override
  public String get() {
    return provideUriDb(module);
  }

  public static ReqHandlerModule_ProvideUriDbFactory create(ReqHandlerModule module) {
    return new ReqHandlerModule_ProvideUriDbFactory(module);
  }

  public static String provideUriDb(ReqHandlerModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideUriDb());
  }
}
