package csis1410.SimFlame;

/**
 * An interface for callbacks to conform to.
 * 
 * @author Tim Hansen
 */
public interface Callback {

	// Methods

	/**
	 * Method to be called when the callback's condition has been satisfied.
	 */
	public void fire();
}