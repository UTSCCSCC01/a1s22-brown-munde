package ca.utoronto.utm.mcs;

import dagger.Module;
import dagger.Provides;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

@Module
public class ReqHandlerModule {
    // TODO Complete This Module

    @Provides
    Driver provideDriver(String addr, String username, String password){
        return GraphDatabase.driver(addr, AuthTokens.basic(username, password));
    }
    @Provides
    Neo4jDAO provideNeo4jDAO(Driver driver){
        return new Neo4jDAO(driver);
    }

}
