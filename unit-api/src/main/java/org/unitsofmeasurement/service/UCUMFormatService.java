/**
 * Unit-API - Units of Measurement API for Java (http://unitsofmeasurement.org)
 * Copyright (c) 2005-2010, Unit-API contributors, JScience and others
 * All rights reserved.
 *
 * See LICENSE.txt for details.
 */
package org.unitsofmeasurement.service;

import java.io.IOException;
import java.text.ParsePosition;
import org.unitsofmeasurement.Unit;

/**
 * <p> This interface provides methods for OSGi bundles to parse/format units
 *     as per the <a href="http://www.unitsofmeasure.org/">
 *     Unified Code for Units of Measure (UCUM)</a> specification.</p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @see <a href="http://aurora.regenstrief.org/~ucum/ucum.html">UCUM Full Specification</a>
 * @version 1.0
 * @deprecated this belongs to UCUM bundle like UOMo UCUM
 */
public interface UCUMFormatService {

    /**
     * Formats the specified unit.
     *
     * @param unit the unit to format.
     * @param appendable the appendable destination.
     * @return the appendable destination passed in with formatted text appended.
     * @throws IOException if an error occurs.
     */
    Appendable format(Unit<?> unit, Appendable appendable)
            throws IOException;

    /**
     * Parses a portion of the specified {@link CharSequence} from the
     * specified position to produce a unit. If there is no unit to parse
     * the unitary unit (dimensionless) is returned.
     *
     * @param csq the <code>CharSequence</code> to parse.
     * @param cursor the cursor holding the current parsing index or <code>
     *        null</code> to parse the whole character sequence.
     * @return the unit parsed from the specified character sub-sequence.
     * @throws IllegalArgumentException if any problem occurs while parsing the
     *         specified character sequence (e.g. illegal syntax).
     */
    Unit<?> parse(CharSequence csq, ParsePosition cursor)
            throws IllegalArgumentException;
}
