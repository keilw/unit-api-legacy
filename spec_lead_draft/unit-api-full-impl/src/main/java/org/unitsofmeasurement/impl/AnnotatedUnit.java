/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2013-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
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
package org.unitsofmeasurement.impl;

import java.util.Map;

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.function.UnitConverter;

import org.unitsofmeasurement.impl.model.QuantityDimension;

/**
 * <p> This class represents an annotated unit.</p>
 * 
 * <p> Instances of this class are created through the
 *     {@link AbstractUnit#annotate(String)} method.</p>
 *
 * @param <Q> The type of the quantity measured by this unit.
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 5.0, October 12, 2010
 */
public final class AnnotatedUnit<Q extends Quantity<Q>> extends AbstractUnit<Q> {

    /**
     * Holds the actual unit.
     */
    private final AbstractUnit<Q> actualUnit;

    /**
     * Holds the annotation.
     */
    private final String annotation;

    /**
     * Creates an annotated unit equivalent to the specified unit.
     *
     * @param actualUnit the unit to be annotated.
     * @param annotation the annotation.
     * @return the annotated unit.
     */
    public AnnotatedUnit(AbstractUnit<Q> actualUnit, String annotation) {
        this.actualUnit = (actualUnit instanceof AnnotatedUnit) ?
            ((AnnotatedUnit<Q>)actualUnit).actualUnit : actualUnit;
        this.annotation = annotation;
    }

    /**
     * Returns the actual unit of this annotated unit (never an annotated unit
     * itself).
     *
     * @return the actual unit.
     */
    public AbstractUnit<Q> getActualUnit() {
        return actualUnit;
    }

    /**
     * Returns the annotqtion of this annotated unit.
     *
     * @return the annotation.
     */
     public String getAnnotation() {
        return annotation;
    }

    @Override
    public String getSymbol() {
        return actualUnit.getSymbol();
    }

    @Override
    public Map<? extends AbstractUnit<?>, Integer> getProductUnits() {
        return actualUnit.getProductUnits();
    }

    @Override
    public AbstractUnit<Q> toSI() {
        return actualUnit.getSystemUnit();
    }

    @Override
    public Dimension getDimension() {
        return actualUnit.getDimension();
    }

    @Override
    public UnitConverter getConverterToSI() {
        return actualUnit.getConverterToSI();
    }

    @Override
    public int hashCode() {
        return actualUnit.hashCode() + annotation.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof AnnotatedUnit<?>))
            return false;
        AnnotatedUnit<?> thatUnit = (AnnotatedUnit<?>) that;
        return this.actualUnit.equals(thatUnit.actualUnit) &&
                this.annotation.equals(thatUnit.annotation);
    }
}
