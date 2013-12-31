/**
 * Copyright (c) 2013-2014 Werner Keil and others.
 * All rights reserved.
 *
 * See LICENSE.txt for details.
 */
package javax.measure.exception;

/**
 * Exception thrown when errors occur during measurement operations.
 *
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.3, $Date$
 *
 */
public class MeasurementException extends RuntimeException {

    /**
     * For cross-version compatibility.
     */
	private static final long serialVersionUID = 8959937033300443361L;

	/**
     * Constructs a {@code MeasurementException} with the given message.
     *
     * @param message the detail message, or {@code null} if none.
     */
    public MeasurementException(final String message) {
        super(message);
    }

    /**
     * Constructs a {@code MeasurementException} with the given cause.
     *
     * @param cause the cause of this exception, or {@code null} if none.
     */
    public MeasurementException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a {@code MeasurementException} with the given message and cause.
     *
     * @param message the detail message, or {@code null} if none.
     * @param cause the cause of this exception, or {@code null} if none.
     *
     */
    public MeasurementException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
	/**
     * Constructs a {@code MeasurementException} with no given message.
     *
     */
    protected MeasurementException() {
        super();
    }
}
