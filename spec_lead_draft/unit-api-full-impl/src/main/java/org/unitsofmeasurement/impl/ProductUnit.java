/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2010 - JScience (http://jscience.org/)
 * All rights reserved.
 *
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package org.unitsofmeasurement.impl;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.function.UnitConverter;

import org.unitsofmeasurement.impl.function.AbstractConverter;
import org.unitsofmeasurement.impl.model.QuantityDimension;
import org.unitsofmeasurement.impl.system.SI;

/**
 * <p>  This class represents units formed by the product of rational powers of
 *      existing physical units.</p>
 *
 * <p> This class maintains the canonical form of this product (simplest form
 *     after factorization). For example: <code>METRE.pow(2).divide(METRE)</code>
 *     returns <code>METRE</code>.</p>
 *
 * @param <Q> The type of the quantity measured by this unit.
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 5.1, December 25, 2013
 */
public final class ProductUnit<Q extends Quantity<Q>> extends AbstractUnit<Q> {

	/**
     * Holds the units composing this product unit.
     */
    private final Element[] elements;

    /**
     * Holds the hashcode (optimization).
     */
    private int hashCode;

    /**
     * Holds the symbol for this unit.
     */
    private final String symbol;
    
    /**
     * Default constructor (used solely to create <code>ONE</code> instance).
     */
    public ProductUnit() {
    	this.symbol = "";
        elements = new Element[0];
    }

    /**
     * Copy constructor (allows for parameterization of product units).
     *
     * @param productUnit the product unit source.
     * @throws ClassCastException if the specified unit is not a product unit.
     */
    public ProductUnit(AbstractUnit<?> productUnit) {
    	this.symbol = productUnit.getSymbol();
        this.elements = ((ProductUnit<?>) productUnit).elements;
    }

    /**
     * Product unit constructor.
     *
     * @param elements the product elements.
     */
    private ProductUnit(Element[] elements) {
        this.elements = elements;
        this.symbol = elements[0].getUnit().getSymbol(); //FIXME this should contain ALL elements
    }


    /**
     * Returns the product of the specified units.
     *
     * @param left the left unit operand.
     * @param right the right unit operand.
     * @return <code>left * right</code>
     */
    public static AbstractUnit<?> getProductInstance(AbstractUnit<?> left, AbstractUnit<?> right) {
        Element[] leftElems;
        if (left instanceof ProductUnit<?>)
            leftElems = ((ProductUnit<?>) left).elements;
        else
            leftElems = new Element[]{new Element(left, 1, 1)};
        Element[] rightElems;
        if (right instanceof ProductUnit<?>)
            rightElems = ((ProductUnit<?>) right).elements;
        else
            rightElems = new Element[]{new Element(right, 1, 1)};
        return getInstance(leftElems, rightElems);
    }

    /**
     * Returns the quotient of the specified units.
     *
     * @param left the dividend unit operand.
     * @param right the divisor unit operand.
     * @return <code>dividend / divisor</code>
     */
    public static AbstractUnit<?> getQuotientInstance(AbstractUnit<?> left, AbstractUnit<?> right) {
        Element[] leftElems;
        if (left instanceof ProductUnit<?>)
            leftElems = ((ProductUnit<?>) left).elements;
        else
            leftElems = new Element[]{new Element(left, 1, 1)};
        Element[] rightElems;
        if (right instanceof ProductUnit<?>) {
            Element[] elems = ((ProductUnit<?>) right).elements;
            rightElems = new Element[elems.length];
            for (int i = 0; i < elems.length; i++) {
                rightElems[i] = new Element(elems[i].unit, -elems[i].pow,
                        elems[i].root);
            }
        } else
            rightElems = new Element[]{new Element(right, -1, 1)};
        return getInstance(leftElems, rightElems);
    }

    /**
     * Returns the product unit corresponding to the specified root of the
     * specified unit.
     *
     * @param unit the unit.
     * @param n the root's order (n &gt; 0).
     * @return <code>unit^(1/nn)</code>
     * @throws ArithmeticException if <code>n == 0</code>.
     */
    public static AbstractUnit<?> getRootInstance(AbstractUnit<?> unit, int n) {
        Element[] unitElems;
        if (unit instanceof ProductUnit<?>) {
            Element[] elems = ((ProductUnit<?>) unit).elements;
            unitElems = new Element[elems.length];
            for (int i = 0; i < elems.length; i++) {
                int gcd = gcd(Math.abs(elems[i].pow), elems[i].root * n);
                unitElems[i] = new Element(elems[i].unit, elems[i].pow / gcd,
                        elems[i].root * n / gcd);
            }
        } else
            unitElems = new Element[]{new Element(unit, 1, n)};
        return getInstance(unitElems, new Element[0]);
    }

    /**
     * Returns the product unit corresponding to this unit raised to the
     * specified exponent.
     *
     * @param unit the unit.
     * @param nn the exponent (nn &gt; 0).
     * @return <code>unit^n</code>
     */
    static AbstractUnit<?> getPowInstance(AbstractUnit<?> unit, int n) {
        Element[] unitElems;
        if (unit instanceof ProductUnit<?>) {
            Element[] elems = ((ProductUnit<?>) unit).elements;
            unitElems = new Element[elems.length];
            for (int i = 0; i < elems.length; i++) {
                int gcd = gcd(Math.abs(elems[i].pow * n), elems[i].root);
                unitElems[i] = new Element(elems[i].unit, elems[i].pow * n / gcd, elems[i].root / gcd);
            }
        } else
            unitElems = new Element[]{new Element(unit, n, 1)};
        return getInstance(unitElems, new Element[0]);
    }

    /**
     * Returns the number of unit elements in this product.
     *
     * @return the number of unit elements.
     */
    public int getUnitCount() {
        return elements.length;
    }

    /**
     * Returns the unit element at the specified position.
     *
     * @param index the index of the unit element to return.
     * @return the unit element at the specified position.
     * @throws IndexOutOfBoundsException if index is out of range
     *         <code>(index &lt; 0 || index &gt;= getUnitCount())</code>.
     */
    public AbstractUnit<?> getUnit(int index) {
        return elements[index].getUnit();
    }

    /**
     * Returns the power exponent of the unit element at the specified position.
     *
     * @param index the index of the unit element.
     * @return the unit power exponent at the specified position.
     * @throws IndexOutOfBoundsException if index is out of range
     *         <code>(index &lt; 0 || index &gt;= getUnitCount())</code>.
     */
    public int getUnitPow(int index) {
        return elements[index].getPow();
    }

    /**
     * Returns the root exponent of the unit element at the specified position.
     *
     * @param index the index of the unit element.
     * @return the unit root exponent at the specified position.
     * @throws IndexOutOfBoundsException if index is out of range
     *         <code>(index &lt; 0 || index &gt;= getUnitCount())</code>.
     */
    public int getUnitRoot(int index) {
        return elements[index].getRoot();
    }

    @Override
    public Map<AbstractUnit<?>, Integer> getProductUnits() {
        final Map<AbstractUnit<?>, Integer> units = new HashMap<>(); // Diamond (Java7+)
        for (int i = 0; i < getUnitCount(); i++) {
            units.put(getUnit(i), getUnitPow(i));
        }
        return units;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (!(that instanceof ProductUnit<?>))
            return false;
        // Two products are equals if they have the same elements
        // regardless of the elements' order.
        Element[] elems = ((ProductUnit<?>) that).elements;
        if (elements.length != elems.length)
            return false;
        for (int i = 0; i < elements.length; i++) {
            boolean unitFound = false;
            Element e = elements[i];
            for (int j = 0; j < elems.length; j++) {
                if (e.unit.equals(elems[j].unit))
                    if ((e.pow != elems[j].pow) || (e.root != elems[j].root))
                        return false;
                    else {
                        unitFound = true;
                        break;
                    }
            }
            if (!unitFound)
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (this.hashCode != 0)
            return this.hashCode;
        int code = 0;
        for (int i = 0; i < elements.length; i++) {
            code += elements[i].unit.hashCode() * (elements[i].pow * 3 - elements[i].root * 2);
        }
        this.hashCode = code;
        return code;
    }

    @Override
    public AbstractUnit<Q> toSI() {
        Unit<?> systemUnit = SI.ONE;
        for (int i = 0; i < elements.length; i++) {
            Unit<?> unit = elements[i].unit.toSI();
            unit = unit.pow(elements[i].pow);
            unit = unit.root(elements[i].root);
            systemUnit = systemUnit.multiply(unit);
        }
        return (AbstractUnit<Q>) systemUnit;
    }

    public UnitConverter getConverterToSI() {
        UnitConverter converter = AbstractConverter.IDENTITY;
        for (int i = 0; i < elements.length; i++) {
            Element e = elements[i];
            UnitConverter cvtr = e.unit.getConverterToSI();
            if (!(cvtr.isLinear()))
                throw new UnsupportedOperationException(e.unit + " is non-linear, cannot convert");
            if (e.root != 1)
                throw new UnsupportedOperationException(e.unit + " holds a base unit with fractional exponent");
            int pow = e.pow;
            if (pow < 0) { // Negative power.
                pow = -pow;
                cvtr = cvtr.inverse();
            }
            for (int j = 0; j < pow; j++) {
                converter = converter.concatenate(cvtr);
            }
        }
        return converter;
    }

    @Override
    public QuantityDimension getDimension() {
    	QuantityDimension dimension = QuantityDimension.NONE;
        for (int i = 0; i < this.getUnitCount(); i++) {
            AbstractUnit<?> unit = this.getUnit(i);
            QuantityDimension d = unit.getDimension().pow(this.getUnitPow(i)).root(this.getUnitRoot(i));
            dimension = dimension.multiply(d);
        }
        return dimension;
    }

    /**
     * Returns the unit defined from the product of the specified elements.
     *
     * @param leftElems left multiplicand elements.
     * @param rightElems right multiplicand elements.
     * @return the corresponding unit.
     */
    private static AbstractUnit<?> getInstance(Element[] leftElems, Element[] rightElems) {

        // Merges left elements with right elements.
        Element[] result = new Element[leftElems.length + rightElems.length];
        int resultIndex = 0;
        for (int i = 0; i < leftElems.length; i++) {
            AbstractUnit unit = leftElems[i].unit;
            int p1 = leftElems[i].pow;
            int r1 = leftElems[i].root;
            int p2 = 0;
            int r2 = 1;
            for (int j = 0; j < rightElems.length; j++) {
                if (unit.equals(rightElems[j].unit)) {
                    p2 = rightElems[j].pow;
                    r2 = rightElems[j].root;
                    break; // No duplicate.
                }
            }
            int pow = (p1 * r2) + (p2 * r1);
            int root = r1 * r2;
            if (pow != 0) {
                int gcd = gcd(Math.abs(pow), root);
                result[resultIndex++] = new Element(unit, pow / gcd, root / gcd);
            }
        }

        // Appends remaining right elements not merged.
        for (int i = 0; i < rightElems.length; i++) {
            AbstractUnit<?> unit = rightElems[i].unit;
            boolean hasBeenMerged = false;
            for (int j = 0; j < leftElems.length; j++) {
                if (unit.equals(leftElems[j].unit)) {
                    hasBeenMerged = true;
                    break;
                }
            }
            if (!hasBeenMerged)
                result[resultIndex++] = rightElems[i];
        }

        // Returns or creates instance.
        if (resultIndex == 0)
            return SI.ONE;
        else if ((resultIndex == 1) && (result[0].pow == result[0].root))
            return result[0].unit;
        else {
            Element[] elems = new Element[resultIndex];
            for (int i = 0; i < resultIndex; i++) {
                elems[i] = result[i];
            }
            return new ProductUnit(elems);
        }
    }

    /**
     * Returns the greatest common divisor (Euclid's algorithm).
     *
     * @param m the first number.
     * @param nn the second number.
     * @return the greatest common divisor.
     */
    private static int gcd(int m, int n) {
        if (n == 0)
            return m;
        else
            return gcd(n, m % n);
    }


    /**
     * Inner product element represents a rational power of a single unit.
     */
    private final static class Element implements Serializable {

        /**
         * Holds the single unit.
         */
        private final AbstractUnit<?> unit;

        /**
         * Holds the power exponent.
         */
        private final int pow;

        /**
         * Holds the root exponent.
         */
        private final int root;

        /**
         * Structural constructor.
         *
         * @param unit the unit.
         * @param pow the power exponent.
         * @param root the root exponent.
         */
        private Element(AbstractUnit<?> unit, int pow, int root) {
            this.unit = unit;
            this.pow = pow;
            this.root = root;
        }

        /**
         * Returns this element's unit.
         *
         * @return the single unit.
         */
        public AbstractUnit<?> getUnit() {
            return unit;
        }

        /**
         * Returns the power exponent. The power exponent can be negative but is
         * always different from zero.
         *
         * @return the power exponent of the single unit.
         */
        public int getPow() {
            return pow;
        }

        /**
         * Returns the root exponent. The root exponent is always greater than
         * zero.
         *
         * @return the root exponent of the single unit.
         */
        public int getRoot() {
            return root;
        }
    }


	@Override
	public String getSymbol() {
		return symbol;
	}
}
