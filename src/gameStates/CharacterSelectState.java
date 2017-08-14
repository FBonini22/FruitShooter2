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

import entities.FruitType;
import globals.Globals;
import sounds.FX;
import view.GameWindow;

public class CharacterSelectState extends GameStateTemplate{
	
	//CONSTANTS
	private final float STARTING_FRUIT_X = GameWindow.SCREEN_WIDTH * 0.125f;
	private final float STARTING_FRUIT_Y = GameWindow.SCREEN_HEIGHT * 0.5f;
	private final float X_INCREMENT = GameWindow.SCREEN_WIDTH * 0.25f;
	
	//INSTANCE VARIABLES
	private Input input;
	private Rectangle selectionCursor;
	int selectionIndex;									//The current index of the player that the user is selecting
	int pNum = 1;											//Number of the current player in fruit-selection
	
	Sound selectionSound;
	
	
	//List<FruitType>
	List<Image> fruitSelections = new ArrayList<Image>();
	Image currentFruit;
	
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
		
		
		
		for(int i = 0; i < fruitSelections.size(); i++){
			fruitSelections.get(i).drawCentered(STARTING_FRUIT_X + i * X_INCREMENT, STARTING_FRUIT_Y);
		}
		
		g.drawString("Player " + String.valueOf(pNum) + ":", 50, 35);
		g.drawString("Select your fruit for battle!", 50, 50);
		g.drawString("(Use the arrow keys to choose a fruit and press enter to confirm selection)", 50, 65);
		

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
		// TODO Auto-generated method stub
		return Globals.CHARACTER_SELECT_STATE_ID;
	}

	@Override
	public void disposeObjects() {
		// TODO Auto-generated method stub
		
	}

}
