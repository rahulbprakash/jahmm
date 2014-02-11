package objectattributes;

/**
 *
 * @author kommusoft
 * @param <TSource>
 * @param <TTarget>
 */
public interface ContinuObjectAttribute<TSource, TTarget> extends ObjectAttribute<TSource, TTarget>, OrdinalObjectAttribute<TSource, TTarget> {

    public abstract TTarget getBetween(TSource source1, TSource source2);

    public abstract TTarget compareWith(TSource source, TTarget target);

}