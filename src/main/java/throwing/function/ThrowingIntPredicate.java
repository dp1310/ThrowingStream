package throwing.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;

import javax.annotation.Nullable;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingIntPredicate<X extends Throwable> {
  public boolean test(int value) throws X;

  default public IntPredicate fallbackTo(IntPredicate fallback) {
    return fallbackTo(fallback, null);
  }

  default public IntPredicate fallbackTo(IntPredicate fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingIntPredicate<Nothing> t = fallback::test;
    return orTry(t, thrown)::test;
  }

  default public <Y extends Throwable> ThrowingIntPredicate<Y> orTry(
      ThrowingIntPredicate<? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingIntPredicate<Y> orTry(
      ThrowingIntPredicate<? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
    return t -> {
      ThrowingSupplier<Boolean, X> s = () -> test(t);
      return s.orTry(() -> f.test(t), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingIntPredicate<Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return t -> {
      ThrowingSupplier<Boolean, X> s = () -> test(t);
      return s.rethrow(x, mapper).get();
    };
  }
}
