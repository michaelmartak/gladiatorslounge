/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

/**
 * Encapsulates a failure in a Gladiator service.
 * <p/>
 * Not the same thing as Java exception handling! Failures are managed
 * conditions, meant to be displayed to the end user, and fully
 * internationalized.
 * <p/>
 * Failures may be associated with Exceptions or error conditions, for debugging
 * purposes.
 * 
 * @author mmartak
 *
 */
public final class ServiceFailure {

	/**
	 * Severity of the failure
	 */
	public static enum Severity {
		/**
		 * Signifies that not only is the service failing, but the integrity of the
		 * process is compromised. All state should be saved, and JGladiator should be
		 * shut down as soon as possible.
		 */
		FATAL,
		/**
		 * Service is down / compromised and should not be restarted. Restart of
		 * JGladiator is required to interact with the service again.
		 */
		SEVERE,
		/**
		 * An error in the service has occurred, but the service should be capable of
		 * further interaction or restarting.
		 * <p/>
		 * This should be the most common type of Failure. Examples might include a loss
		 * of network connectivity, full disk space, or some condition that is
		 * potentially recoverable based on some interaction with the user or
		 * environment.
		 */
		ERROR,
		/**
		 * The service experienced an issue that should alert the user, but has
		 * recovered from the condition. This failure should provide information, but is
		 * not itself an error condition.
		 */
		WARNING,
		/**
		 * Reserved for debugging. Will never be reported to a user.
		 */
		DEBUG
	}

	private final Severity severity;
	private final String localizedMessage;
	private final Exception exception;

	/**
	 * Create a new Service Failure of {@link Severity#ERROR} severity
	 */
	public ServiceFailure(String localizedMessage) {
		this(Severity.ERROR, localizedMessage, /* exception */ null);
	}

	/**
	 * Create a new Service Failure of {@link Severity#ERROR} severity
	 */
	public ServiceFailure(String localizedMessage, Exception exception) {
		this(Severity.ERROR, localizedMessage, exception);
	}

	/**
	 * Create a new Service Failure
	 * 
	 * @param severity         the severity of the failure. If <code>null</code>,
	 *                         severity will be treated as {@link Severity#ERROR}
	 */
	public ServiceFailure(Severity severity, String localizedMessage) {
		this(severity, localizedMessage, /* exception */ null);
	}

	/**
	 * Create a new Service Failure
	 * 
	 * @param severity         the severity of the failure. If <code>null</code>,
	 *                         severity will be treated as {@link Severity#ERROR}
	 */
	public ServiceFailure(Severity severity, String localizedMessage, Exception exception) {
		this.severity = severity == null ? Severity.ERROR : severity;
		this.localizedMessage = localizedMessage;
		this.exception = exception;
	}

	/**
	 * Returns the severity of the failure
	 * 
	 * @return a severity value, never <code>null</code>
	 */
	public Severity getSeverity() {
		return severity;
	}

	/**
	 * The localized message of the failure
	 * 
	 * @return the fully localized, human readable message for the error that will
	 *         be displayed to the user. It is the responsibility of the service to
	 *         provide the correct information to the user
	 */
	public String getLocalizedMessage() {
		return localizedMessage;
	}

	/**
	 * Returns the exception associated with this failure, if there is one. This is
	 * for debugging / informational purposes only.
	 * 
	 * @return an exception, or null if none
	 */
	public Exception getException() {
		return exception;
	}

}
