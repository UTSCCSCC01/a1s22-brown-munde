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
    GraphDatabase provideDriver(){
        return new GraphDatabase();
    }

    @Provides
    Neo4jDAO provideNeo4jDAO(GraphDatabase driver){
        return new Neo4jDAO((Driver) driver);
    }

}
