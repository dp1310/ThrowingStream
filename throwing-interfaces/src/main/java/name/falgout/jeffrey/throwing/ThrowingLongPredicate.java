package name.falgout.jeffrey.throwing;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongPredicate;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingLongPredicate<X extends Throwable> {
  public boolean test(long value) throws X;

  default public LongPredicate fallbackTo(LongPredicate fallback) {
    return fallbackTo(fallback, null);
  }

  default public LongPredicate fallbackTo(LongPredicate fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingLongPredicate<Nothing> t = fallback::test;
    return orTry(t, thrown)::test;
  }

  default public <Y extends Throwable> ThrowingLongPredicate<Y>
      orTry(ThrowingLongPredicate<? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingLongPredicate<Y>
      orTry(ThrowingLongPredicate<? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
    return t -> {
      ThrowingSupplier<Boolean, X> s = () -> test(t);
      return s.orTry(() -> f.test(t), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingLongPredicate<Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return t -> {
      ThrowingSupplier<Boolean, X> s = () -> test(t);
      return s.rethrow(x, mapper).get();
    };
  }
}
