package ca.utoronto.utm.mcs;

import dagger.internal.DaggerGenerated;
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
public final class DaggerReqHandlerComponent implements ReqHandlerComponent {
  private final ReqHandlerModule reqHandlerModule;

  private DaggerReqHandlerComponent(ReqHandlerModule reqHandlerModuleParam) {
    this.reqHandlerModule = reqHandlerModuleParam;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static ReqHandlerComponent create() {
    return new Builder().build();
  }

  private Neo4jDAO neo4jDAO() {
    return ReqHandlerModule_ProvideNeo4jDAOFactory.provideNeo4jDAO(reqHandlerModule, ReqHandlerModule_ProvideDriverFactory.provideDriver(reqHandlerModule));
  }

  @Override
  public ReqHandler buildHandler() {
    return new ReqHandler(neo4jDAO());
  }

  public static final class Builder {
    private ReqHandlerModule reqHandlerModule;

    private Builder() {
    }

    public Builder reqHandlerModule(ReqHandlerModule reqHandlerModule) {
      this.reqHandlerModule = Preconditions.checkNotNull(reqHandlerModule);
      return this;
    }

    public ReqHandlerComponent build() {
      if (reqHandlerModule == null) {
        this.reqHandlerModule = new ReqHandlerModule();
      }
      return new DaggerReqHandlerComponent(reqHandlerModule);
    }
  }
}
