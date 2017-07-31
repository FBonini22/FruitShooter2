package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


import org.newdawn.slick.SlickException;

import Utilities.D;
import view.*;

public class Enemy extends Entity{
	
		
	//Constants
	private final float startingX = 0;
	private final float startingY = 0;		
	private int xPosition = 0;
	private int xPosition1 = 0;
	
	//Instance Variables
	private EnemyType _currentEnemy;					//The user-selected enemy
	private int _EnemyNum;								//The number enemy created
	private int _offSet;
	private final float MOVEMENT_SPEED = 5f;			//Movement speed in pixels per second
	private String imgPath = "img/test.png";			//Path to image that will be loaded for this Enemy instance
	
	private double _health = 0d;
	
	
	public Enemy(EnemyType selectedFruit, int pNum){
		
		super(0, 0, 48,48);

		//Instantiate instance variables
		_currentEnemy = selectedFruit;
		_EnemyNum = pNum;		
		_offSet =_EnemyNum*26;		
		this.x = startingX + _offSet;
		this.y = startingY;
		
		InitializeEnemyAttributes();

	}
		
	

	private void InitializeEnemyAttributes(){
		D.BUG("Initializing Enemy attributes...");
		
		//All attributes will be those of squirrel for the time being.
		switch(_currentEnemy){
		case Squirrel:
			imgPath = "img/Squirrel.png";
			_health = 1.0;
			break;
		case JumboSquirrel:
			imgPath = "img/JumboSquirrel.png";
			_health = 2.0;
			break;
		
		}
		

	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		
		
		
//		if (xPosition1 > 0){
//			this.moveBy((-MOVEMENT_SPEED), 0);
//			xPosition1--;
//
//			
//		}
//		else if (xPosition1 == 0){
//			this.moveBy(0, 26);
//			xPosition = 0;
//			xPosition1 = -1;
//
//		}
//		else if (xPosition < 100){
//			this.moveBy(MOVEMENT_SPEED, 0);
//			xPosition++;
//
//			
//		}
//		else if (xPosition == 100){
//			this.moveBy(0, 26);
//			xPosition1 = 100;
//
//		}
		
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

		
		
		//Draw the player at the default starting coordinates
		//_entityImg.draw(x,y);
		g.drawImage(_entityImg, x, y);
		
		
	}



	@Override
	public boolean isDangerous() {
		return true;
	}



	@Override
	public void init(GameContainer gc) throws SlickException {
		// TODO Auto-generated method stub
		this._entityImg = new Image(imgPath);
	}

	/**
	 * 
	 * @return The enum type of this Enemy instance
	 */
	public EnemyType getEnemyType(){
		return _currentEnemy;
	}



	@Override
	public void onCollide(Entity collidedWith) {
		D.BUG("Bullet hit enemy");
		
		
		String collisionType = collidedWith.getClass().getSimpleName();
		
		switch(collisionType){
		
		case "PlayerBullet":
			_health -= 1;
		}
		
		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	


}
