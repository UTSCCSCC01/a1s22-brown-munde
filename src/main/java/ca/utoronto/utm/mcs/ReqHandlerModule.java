package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dagger.Module;
import dagger.Provides;
import io.github.cdimascio.dotenv.Dotenv;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

import java.io.IOException;

@Module
public class ReqHandlerModule {
    // TODO Complete This Module

  @Provides
  String provideUriDb() {
    Dotenv dotenv = Dotenv.load();
    String addr = dotenv.get("NEO4J_ADDR");
    return "bolt://" + addr + ":7687";
  }

  @Provides
  Driver provideDriver(String uriDb) {
    return GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j", "123456"));
  }

  @Provides
  public Neo4jDAO provideNeo4jDAO(Driver driver){
    return new Neo4jDAO(driver);

  }
}
