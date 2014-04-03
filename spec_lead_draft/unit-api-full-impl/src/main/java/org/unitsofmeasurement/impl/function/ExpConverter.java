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

/**
 * <p> This class represents a exponential converter of limited precision.
 *     Such converter is used to create inverse of logarithmic unit.
 *
 * <p> This class is package private, instances are created
 *     using the {@link LogConverter#inverse()} method.</p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 5.2, April 3, 2014
 */
final class ExpConverter extends AbstractConverter { //implements Immutable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8851436813812059827L;

	/**
     * Holds the logarithmic base.
     */
    private double base;

    /**
     * Holds the natural logarithm of the base.
     */
    private double logOfBase;

    /**
     * Creates a logarithmic converter having the specified base.
     *
     * @param  base the logarithmic base (e.g. <code>Math.E</code> for
     *         the Natural Logarithm).
     */
    public ExpConverter(double base) {
        this.base = base;
        this.logOfBase = Math.log(base);
    }

    /**
     * Returns the exponential base of this converter.
     *
     * @return the exponential base (e.g. <code>Math.E</code> for
     *         the Natural Exponential).
     */
    public double getBase() {
        return base;
    }

    @Override
    public AbstractConverter inverse() {
        return new LogConverter(base);
    }

    @Override
    public final String toString() {
        if (base == Math.E) {
            return "e";
        } else {
            return "Exp(" + base + ")";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ExpConverter))
            return false;
        ExpConverter that = (ExpConverter) obj;
        return this.base == that.base;
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(base);
        return (int) (bits ^ (bits >>> 32));
    }

    @Override
    public double convert(double amount) {
            return Math.exp(logOfBase * amount);
    }

    @Override
    public BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException {
        return BigDecimal.valueOf(convert(value.doubleValue())); // Reverts to double conversion.
    }

    @Override
    public boolean isLinear() {
        return false;
    }

	public Object value() {
		// TODO Auto-generated method stub
		return null;
	}


}
