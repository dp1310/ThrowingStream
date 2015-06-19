package throwing.stream.union;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;

import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingDoubleBinaryOperator;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingDoubleFunction;
import throwing.function.ThrowingDoublePredicate;
import throwing.function.ThrowingDoubleToIntFunction;
import throwing.function.ThrowingDoubleToLongFunction;
import throwing.function.ThrowingDoubleUnaryOperator;
import throwing.function.ThrowingObjDoubleConsumer;
import throwing.function.ThrowingSupplier;
import throwing.stream.ThrowingDoubleStream;

public interface UnionDoubleStream<X extends UnionThrowable> extends
        UnionBaseStream<Double, X, UnionDoubleStream<X>, ThrowingDoubleStream<Throwable>>,
        ThrowingDoubleStream<Throwable> {
    @Override
    public UnionIterator.OfDouble<X> iterator();

    @Override
    public UnionSpliterator.OfDouble<X> spliterator();

    @Override
    public UnionDoubleStream<X> filter(ThrowingDoublePredicate<? extends Throwable> predicate);

    @Override
    public UnionDoubleStream<X> map(ThrowingDoubleUnaryOperator<? extends Throwable> mapper);

    @Override
    public <U> UnionStream<U, X> mapToObj(
            ThrowingDoubleFunction<? extends U, ? extends Throwable> mapper);

    @Override
    public UnionIntStream<X> mapToInt(ThrowingDoubleToIntFunction<? extends Throwable> mapper);

    @Override
    public UnionLongStream<X> mapToLong(ThrowingDoubleToLongFunction<? extends Throwable> mapper);

    @Override
    public UnionDoubleStream<X> flatMap(
            ThrowingDoubleFunction<? extends ThrowingDoubleStream<? extends Throwable>, ? extends Throwable> mapper);

    @Override
    public UnionDoubleStream<X> distinct();

    @Override
    public UnionDoubleStream<X> sorted();

    @Override
    public UnionDoubleStream<X> peek(ThrowingDoubleConsumer<? extends Throwable> action);

    @Override
    public UnionDoubleStream<X> limit(long maxSize);

    @Override
    public UnionDoubleStream<X> skip(long n);

    @Override
    public void forEach(ThrowingDoubleConsumer<? extends Throwable> action) throws X;

    @Override
    public void forEachOrdered(ThrowingDoubleConsumer<? extends Throwable> action) throws X;

    @Override
    public double[] toArray() throws X;

    @Override
    public double reduce(double identity, ThrowingDoubleBinaryOperator<? extends Throwable> op)
            throws X;

    @Override
    public OptionalDouble reduce(ThrowingDoubleBinaryOperator<? extends Throwable> op) throws X;

    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
            ThrowingObjDoubleConsumer<R, ? extends Throwable> accumulator,
            ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X;

    @Override
    public double sum() throws X;

    @Override
    public OptionalDouble min() throws X;

    @Override
    public OptionalDouble max() throws X;

    @Override
    public long count() throws X;

    @Override
    public OptionalDouble average() throws X;

    @Override
    public DoubleSummaryStatistics summaryStatistics() throws X;

    @Override
    public boolean anyMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws X;

    @Override
    public boolean allMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws X;

    @Override
    public boolean noneMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws X;

    @Override
    public OptionalDouble findFirst() throws X;

    @Override
    public OptionalDouble findAny() throws X;

    @Override
    public UnionStream<Double, X> boxed();
}