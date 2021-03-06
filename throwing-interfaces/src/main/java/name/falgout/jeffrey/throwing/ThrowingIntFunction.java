package name.falgout.jeffrey.throwing;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingIntFunction<R, X extends Throwable> {
  public R apply(int value) throws X;

  default public IntFunction<R> fallbackTo(IntFunction<? extends R> fallback) {
    return fallbackTo(fallback, null);
  }

  default public IntFunction<R> fallbackTo(IntFunction<? extends R> fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingIntFunction<R, Nothing> t = fallback::apply;
    return orTry(t, thrown)::apply;
  }

  default public <Y extends Throwable> ThrowingIntFunction<R, Y>
      orTry(ThrowingIntFunction<? extends R, ? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingIntFunction<R, Y> orTry(
      ThrowingIntFunction<? extends R, ? extends Y> f,
      @Nullable Consumer<? super Throwable> thrown) {
    return t -> {
      ThrowingSupplier<R, X> s = () -> apply(t);
      return s.orTry(() -> f.apply(t), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingIntFunction<R, Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return t -> {
      ThrowingSupplier<R, X> s = () -> apply(t);
      return s.rethrow(x, mapper).get();
    };
  }

  default public <RR> ThrowingIntFunction<RR, X>
      andThen(ThrowingFunction<? super R, ? extends RR, ? extends X> after) {
    Objects.requireNonNull(after);
    return i -> after.apply(apply(i));
  }
}
