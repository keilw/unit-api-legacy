/**
 * Unit-API - Units of Measurement API for Java
 * Copyright (c) 2014 Jean-Marie Dautelle, Werner Keil, V2COM
 * All rights reserved.
 *
 * See LICENSE.txt for details.
 */
package javax.measure.function;

/**
 * Represents a factory that accepts two arguments and creates a result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #create(Object, Object)}.
 *
 * @param <T> the type of the first argument to the factory
 * @param <U> the type of the second argument to the factory
 * @param <R> the type of the result of the factory
 *
 * @see Function
 */
public interface BiFactory<T, U, R> {
	R create(T t, U u);
}
