package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;

@Module
public class ReqHandlerModule {
    // TODO Complete This Module
  @Provides
  HttpHandler provideHttpHandler() {
    return new HttpHandler() {
      @Override
      public void handle(HttpExchange exchange) throws IOException {

      }
    };
  }
}
