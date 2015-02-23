package pt.ipg.mcm.salta.blocos.desktop;

import com.neet.blockbunny.main.Provider;
import org.mockito.Mockito;

public class MockProvider<T> implements Provider<T>{
  private T mock;

  public MockProvider(Class<T> cl) {
    this.mock = Mockito.mock(cl);
  }

  @Override
  public T get() {
    return mock;
  }
}
