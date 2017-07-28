package view;

import org.newdawn.slick.*;

public class RunGameTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Engine game = new Engine("FruitShooter0.1a");
		

		

		
		try { 
		    AppGameContainer container = new AppGameContainer(game); 
		    container.setDisplayMode(GameWindow.SCREEN_WIDTH,GameWindow.SCREEN_HEIGHT,false);	//boolean is for fullscreen 
		    container.start(); 
		} catch (SlickException e) { 
		    e.printStackTrace(); 
		}
		
	}

}
