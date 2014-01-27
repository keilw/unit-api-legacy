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

import java.math.BigDecimal;

import javax.measure.quantity.Pressure;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.unitsofmeasurement.impl.system.SI.*;
import static org.junit.Assert.*;

/**
 *
 * @author Werner Keil
 */
public class AbstractMeasurementTest {

    public AbstractMeasurementTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testOf() {
    	AbstractMeasurement<Pressure> pressure = AbstractMeasurement.of(BigDecimal.ONE, PASCAL); 
        assertEquals(PASCAL, pressure.getUnit()); // TODO: Problem with kg...
    }

    @Test
    public void testAnnotate() {
    }

    @Test
    public void testGetAnnotation() {
    }

    @Test
    public void testGetUnannotatedUnit() {
    }

    @Test
    public void testIsSystemUnit() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void testGetConverterToSystemUnit() {
    }

    @Test
    public void testGetSymbol() {
    }

    @Test
    public void testGetSystemUnit() {
    }

    @Test
    public void testGetProductUnits() {
    }

    @Test
    public void testGetDimension() {
    }

    @Test
    public void testIsCompatible() {
    }

    @Test
    public void testAsType() {
    }

    @Test
    public void testGetConverterTo() {
    }

    @Test
    public void testGetConverterToAny() {
    }

    @Test
    public void testAlternate() {
    }

    @Test
    public void testTransform() {
    }

    @Test
    public void testAdd() {
    }

    @Test
    public void testMultiply_double() {
    }

    @Test
    public void testMultiply_ErrorType() {
    }

    @Test
    public void testInverse() {
    }

    @Test
    public void testDivide_double() {
    }

    @Test
    public void testDivide_ErrorType() {
    }

    @Test
    public void testRoot() {
    }

    @Test
    public void testPow() {
    }

    @Test
    public void testHashCode() {
    }

    @Test
    public void testEquals() {
    }


}