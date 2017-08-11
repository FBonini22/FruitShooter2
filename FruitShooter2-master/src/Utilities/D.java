package Utilities;
import globals.*;

/**
 * Utility class for debugging.
 * @author Frank
 *
 */
public class D {

	/**
	 * Utility method for printing debugging statements. Only prints if Globals Variable "Debugging" is set to TRUE
	 * @param printStatement The String object to print to console
	 */
	public static void BUG(String printStatement){
		
		if(Globals.DEBUGGING){
			System.out.println(printStatement);			
		}

	}
	
}
