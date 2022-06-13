package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpHandler;
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
public final class ReqHandlerModule_ProvideHttpHandlerFactory implements Factory<HttpHandler> {
  private final ReqHandlerModule module;

  public ReqHandlerModule_ProvideHttpHandlerFactory(ReqHandlerModule module) {
    this.module = module;
  }

  @Override
  public HttpHandler get() {
    return provideHttpHandler(module);
  }

  public static ReqHandlerModule_ProvideHttpHandlerFactory create(ReqHandlerModule module) {
    return new ReqHandlerModule_ProvideHttpHandlerFactory(module);
  }

  public static HttpHandler provideHttpHandler(ReqHandlerModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideHttpHandler());
  }
}
