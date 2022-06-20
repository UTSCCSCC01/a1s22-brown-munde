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


  /**
   * This method returns the neo4j driver Uri
   * @return neo4j driver uri
   */
  @Provides
  String provideUriDb() {
    Dotenv dotenv = Dotenv.load();
    String addr = dotenv.get("NEO4J_ADDR");
    return "bolt://" + addr + ":7687";
  }

  /**
   * This method provides(returns) the neo4j driver that is to injected
   * in the neo4j class
   * @param uriDb the uri of the driver
   * @return neo4j driver object
   */
  @Provides
  Driver provideDriver(String uriDb) {
    return GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j", "123456"));
  }


  /**
   * This method provides(returns) the neo4j object that is to injected
   * in the req handler class
   * @param driver the neo4j driver object
   * @return neo4j object
   */
  @Provides
  public Neo4jDAO provideNeo4jDAO(Driver driver){
    return new Neo4jDAO(driver);

  }
}
