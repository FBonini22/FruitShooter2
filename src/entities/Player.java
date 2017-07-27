package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;

import Utilities.D;
import view.*;

public class Player extends Entity{
	
	//Controls
	private int UP_CONTROL = Input.KEY_UP;
	private int DOWN_CONTROL = Input.KEY_DOWN;
	private int LEFT_CONTROL = Input.KEY_LEFT;
	private int RIGHT_CONTROL = Input.KEY_RIGHT;
	
	//Constants
	private final float startingX = (float)(GameWindow.SCREEN_WIDTH) / 2f;
	private final float startingY = (float)(GameWindow.SCREEN_HEIGHT) * 0.90f;		//Player starts 10% up the screen
	private final float MOVEMENT_SPEED = 10f;
	
	
	//Instance Variables
	private Input input;								//Variable that is called to poll for key inputs
	private FruitType _currentFruit;					//The user-selected fruit
	private int _playerNum;								//The player number. 1 or 2 ONLY
	
	private String imgPath = "img/test.png";			//Path to image that will be loaded for this Player instance
	
	private double _health = 0d;
	
	
	public Player(FruitType selectedFruit, int pNum){

		//Instantiate instance variables
		_currentFruit = selectedFruit;
		_playerNum = pNum;		
		input = new Input(pNum);		//Instantiate input. Unknown function of integer. CHECK JAVADOC
		
		this.x = startingX;
		this.y = startingY;
		
		InitializePlayerAttributes();
		InitializeControls();

		

//		try {
//			p.initControllers();
//		} catch (SlickException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		p.resume();
//		
//		try {
//			this._entityImg = new Image("img/test.png");
//			_entityImg.draw(100,100);
//		} catch (SlickException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
	

	/**
	 * Method for initializing player controls. SHOULD ONLY BE CALLED ONCE AND ONLY AFTER VARIABLE INSTANTIATION!
	 */
	private void InitializeControls(){
		D.BUG("Initializing player controls...");
		//TO DO: Initialize controls here
		if(_playerNum == 2){
			//Change the controls. Otherwise do nothing
		}
		
	}

	private void InitializePlayerAttributes(){
		D.BUG("Initializing player attributes...");
		
		//All attributes will be those of watermelon for the time being.
		switch(_currentFruit){
		case Apple:
		case Banana:
		case Lemon:
		case Watermelon:
			imgPath = "img/Watermelon.png";
			break;
		}
		

	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		checkForUserInput();
	}

	@Override
	public void render(GameContainer gc, Graphics g){

		//Fix this. We need to minimize try/catch statements. Their bulk will add up quickly
		try {
			this._entityImg = new Image(imgPath);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Draw the player at the default starting coordinates
		_entityImg.draw(x,y);
		
		
	}

	
	private void checkForUserInput(){

		if(input.isKeyDown(UP_CONTROL)){
			this.moveBy(x, y - MOVEMENT_SPEED);
		}
		else if(input.isKeyDown(DOWN_CONTROL)){
			this.moveBy(x, y + MOVEMENT_SPEED);
		}
		
		if(input.isKeyDown(LEFT_CONTROL)){
			this.moveBy(x - MOVEMENT_SPEED, y);
		}
		else if(input.isKeyDown(RIGHT_CONTROL)){
			this.moveBy(x + MOVEMENT_SPEED, y);
		}
	}


}
