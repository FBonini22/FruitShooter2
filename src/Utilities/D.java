package Utilities;
import globals.*;

/**
 * Utility class for debugging.
 * @author Frank
 *
 */
public class D {

	public static void BUG(String printStatement){
		
		if(Globals.DEBUGGING){
			System.out.println(printStatement);			
		}

	}
	
}
