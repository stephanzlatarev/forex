package forex.advisor;

import forex.account.Quote;

public interface Advisor {

  public Advice getAdvice(Quote quote);

}
