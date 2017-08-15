package gameStates;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import engine.Engine;
import entities.FruitType;
import globals.Globals;
import sounds.FX;
import view.GameWindow;

public class CharacterSelectState extends GameStateTemplate{
	
	//CONSTANTS
	private final float STARTING_FRUIT_X = GameWindow.SCREEN_WIDTH * 0.125f;		//The X-coordinate of where the first fruit will be drawn
	private final float STARTING_FRUIT_Y = GameWindow.SCREEN_HEIGHT * 0.5f;			//The Y-coordinate for all of the fruits
	private final float X_INCREMENT = GameWindow.SCREEN_WIDTH * 0.25f;				//The value by which to increment each fruit's x-value
	
	//INSTANCE VARIABLES

	private Input input;									//Variable for key/controller input handling
	private Rectangle selectionCursor;						//Cursor to be drawn around the currently selected fruit
	
	int selectionIndex;										//The current index of the player that the user is selecting

	int pNum = 1;											//Number of the current player in fruit-selection
	
	Sound selectionSound;									//The sound to be played when the cursor is moved
	
	
	//List of available fruits that can be selected for a character
	List<Image> fruitSelections = new ArrayList<Image>();
	
	//Variable to keep track of the selected fruit. Used for helping draw the cursor
	Image currentFruit;
	
	//List of each player's selected fruit. Each list item is the fruit that the respective player selected
	List<FruitType> selectedFruits = new ArrayList<FruitType>();

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		selectionCursor = new Rectangle(0, 0, 128, 128);
		

		//THIS ORDER IS BASED ON THE ENUM "FruitType".
		fruitSelections.add(new Image("img/Apple.png"));
		fruitSelections.add(new Image("img/Watermelon.png"));
		fruitSelections.add(new Image("img/Banana.png"));
		fruitSelections.add(new Image("img/Lemon.png"));
		
		selectionSound = new Sound(FX.SPLAT);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		
		
		//Draw each selectable fruit
		for(int i = 0; i < fruitSelections.size(); i++){
			fruitSelections.get(i).drawCentered(STARTING_FRUIT_X + i * X_INCREMENT, STARTING_FRUIT_Y);
		}

		//Draw Hint text at the top of the screen
		g.drawString("Player " + String.valueOf(pNum) + ":", 50, 35);
		g.drawString("Select your fruit for battle!", 50, 50);
		g.drawString("Use the arrow keys to choose a fruit.", 50, 65);
		g.drawString("Press ENTER to confirm selection.", 50, 80);
		
		
		
		//Draw the selection cursor as yellow
		g.setColor(org.newdawn.slick.Color.yellow);
		g.draw(selectionCursor);
		g.setColor(org.newdawn.slick.Color.white);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		
		input = gc.getInput();
		
		//Move character selector to the right
		if(input.isKeyPressed(Input.KEY_RIGHT)){
			if(selectionIndex < 3){
				selectionIndex++;
			
				selectionSound.play();
			}
		}
		else if(input.isKeyPressed(Input.KEY_LEFT)){
			if(selectionIndex > 0){
				selectionIndex--;
				selectionSound.play();
			}
		}
		
		//If the user selects the current fruit
		else if(input.isKeyPressed(Input.KEY_ENTER)){
			selectedFruits.add(FruitType.values()[selectionIndex]);
			
			//Future code for multiplayer handling
			if(Globals.MULTIPLAYER){
				pNum++;
				selectionIndex = 0;
			}
			else{
				Engine.instance.initializePlayers(selectedFruits);
				switchGameState(gc, game, Globals.GAME_ENGINE_STATE_ID);
			}
			
		}
		
		currentFruit = fruitSelections.get(selectionIndex);
		
		selectionCursor.setCenterX(currentFruit.getCenterOfRotationX() + STARTING_FRUIT_X + selectionIndex * X_INCREMENT - Globals.PLAYER_WIDTH/2);
		selectionCursor.setCenterY(currentFruit.getCenterOfRotationY() + STARTING_FRUIT_Y - Globals.PLAYER_HEIGHT/2);
		
	

		
	}

	@Override
	public int getID() {
		return Globals.CHARACTER_SELECT_STATE_ID;
	}

	@Override
	public void disposeObjects() {
		//NOT USED
		
	}

}
