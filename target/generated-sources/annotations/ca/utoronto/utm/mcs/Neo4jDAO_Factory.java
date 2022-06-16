package ca.utoronto.utm.mcs;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class Neo4jDAO_Factory implements Factory<Neo4jDAO> {
  private final Provider<Driver> driverProvider;

  public Neo4jDAO_Factory(Provider<Driver> driverProvider) {
    this.driverProvider = driverProvider;
  }

  @Override
  public Neo4jDAO get() {
    return newInstance(driverProvider.get());
  }

  public static Neo4jDAO_Factory create(Provider<Driver> driverProvider) {
    return new Neo4jDAO_Factory(driverProvider);
  }

  public static Neo4jDAO newInstance(Driver driver) {
    return new Neo4jDAO(driver);
  }
}
