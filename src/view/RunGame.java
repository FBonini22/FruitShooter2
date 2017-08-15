package view;


import java.io.File;

import org.newdawn.slick.*;

import gameStates.GameStateHandler;

public class RunGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Engine game = Engine.instance;
		
		
		//Change 
		File f = new File("natives/lwjgl-2.9.3/native/windows");
		if(f.exists()){
			System.setProperty("org.lwjgl.librarypath", f.getAbsolutePath());
			System.setProperty("org.lwjgl64.librarypath", f.getAbsolutePath());
		}
		

		GameStateHandler game = new GameStateHandler("FruitShooter 1.0a");
		

		//Start the game
		try { 
		    AppGameContainer container = new AppGameContainer(game); 
		    container.setDisplayMode(GameWindow.SCREEN_WIDTH, GameWindow.SCREEN_HEIGHT,false);	//boolean is for fullscreen 
		    container.setTargetFrameRate(60);
		    container.start(); 
		} catch (SlickException e) { 
		    e.printStackTrace(); 
		}
		
	}

}
