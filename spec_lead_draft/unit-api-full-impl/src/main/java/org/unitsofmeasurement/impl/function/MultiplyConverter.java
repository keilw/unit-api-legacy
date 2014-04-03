/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2005-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
 *  contributors by the @author tag.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.unitsofmeasurement.impl.function;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.measure.function.UnitConverter;

/**
 * <p> This class represents a converter multiplying numeric values by a
 *     constant scaling factor (<code>double</code> based).</p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 5.1, April 3, 2014
 */
public final class MultiplyConverter extends AbstractConverter { // implements Immutable<Double> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6588759878444545649L;
	
	/**
     * Holds the scale factor.
     */
    private double factor;

    /**
     * Creates a multiply converter with the specified scale factor.
     *
     * @param  factor the scaling factor.
     * @throws IllegalArgumentException if coefficient is <code>1.0</code>
     *        (would result in identity converter)
     */
    public MultiplyConverter(double factor) {
        if (factor == 1.0)
            throw new IllegalArgumentException("Would result in identity converter");
        this.factor = factor;
    }

    /**
     * Returns the scale factor of this converter.
     *
     * @return the scale factor.
     */
    public double getFactor() {
        return factor;
    }

    @Override
    public UnitConverter concatenate(UnitConverter converter) {
        if (!(converter instanceof MultiplyConverter))
            return super.concatenate(converter);
        double newfactor = factor * ((MultiplyConverter) converter).factor;
        return newfactor == 1.0 ? IDENTITY : new MultiplyConverter(newfactor);
    }

    @Override
    public MultiplyConverter inverse() {
        return new MultiplyConverter(1.0 / factor);
    }

    @Override
    public double convert(double value) {
        return value * factor;
    }

    @Override
    public BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException {
        return value.multiply(BigDecimal.valueOf(factor), ctx);
    }

    @Override
    public final String toString() {
        return "MultiplyConverter(" + factor + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MultiplyConverter))
            return false;
        MultiplyConverter that = (MultiplyConverter) obj;
        return this.factor == that.factor;
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(factor);
        return (int) (bits ^ (bits >>> 32));
    }

    @Override
    public boolean isLinear() {
        return true;
    }

	public Double value() {
		return Double.valueOf(factor);
	}
}
