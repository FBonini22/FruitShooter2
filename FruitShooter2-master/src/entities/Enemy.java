//TODO Add more enemies and reduce firing to certain types of enemies using a switch statement in the update method
package entities;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Utilities.D;
import globals.Globals;
import view.Engine;
import view.GameWindow;

public class Enemy extends Entity{
	
		

	//Constants		

	private int pointValue;
	
	//Instance Variables
	private EnemyType _currentEnemy;					//The user-selected enemy
	private final float MOVEMENT_SPEED = 5f;			//Movement speed in pixels per second
	private String imgPath = "img/test.png";			//Path to image that will be loaded for this Enemy instance
	private int Move = 100;
	private double _health = 0d;
	private float result_x = 0;
	private float result_y = 0;
	int result = 0;										//Determines if enemy will move randomly
	
	private float time = 0;			//Incremental time
	private float xSpeed;			//Spawned X coordinate
	private float ySpeed;			//Spawned Y coordinate
	private float accel = .2f;
	private boolean initialize;		//Initializes the preset coordinates
	private EnemyMovement movement;
	
	private boolean dead = false;	//Parameter used to determine if an enemy should be deleted or not
	boolean random;
	private float multiplier = 1;	//Parameter used to increase the health of an enemy
	private float location_y;
	/**
	 * 
	 * @param selectedEnemy The fruit that the player will play as
	 * @param pNum 			The number of the enemy that is being generated ex. enemy 1, enemy 2...
	 */

public Enemy(float x, float y, EnemyType selectedEnemy, float P1, float P2, EnemyMovement move){ //Added parameters to take in the size of the hitbox desired
		
		super(x, y, P1,P2);
		location_y = y;
		//Instantiate instance variables
		_currentEnemy = selectedEnemy;	
		movement = move;

		
		InitializeEnemyAttributes();
}


public Enemy(EnemyType selectedEnemy, float P1, float P2, EnemyMovement move){ //Shorter Constructor for just having a preset movement enemy spawn
	
	super(0, 0, P1,P2);

	//Instantiate instance variables
	_currentEnemy = selectedEnemy;	
	movement = move;
	InitializeEnemyAttributes();
}

public Enemy(float x, float y, EnemyType selectedEnemy, float P1, float P2, EnemyMovement move, float mult){ //Boss constructor. Used to spawn a boss with an increasing amount health
	
	super(x, y, P1,P2);

	//Instantiate instance variables
	_currentEnemy = selectedEnemy;	
	movement = move;
	multiplier = mult;
	
	InitializeEnemyAttributes();
}

/**
 * Method to initialize the attributes of the enemy. Contains a switch statement to pick which enemy type will be generated.
 */
	private void InitializeEnemyAttributes(){
		//D.BUG("Initializing Enemy attributes...");
		
		//All attributes will be those of squirrel for the time being.
		switch(_currentEnemy){
		case Squirrel:
			imgPath = "img/Squirrel.png";
			_health = 1.0*multiplier;
			pointValue = Globals.gruntValue;
			break;
		case JumboSquirrel_1:
			imgPath = "img/Jumbo_Squirrel.png";
			_health = 10.0*multiplier;
			pointValue = Globals.bossValue;
			break;
		case JumboSquirrel_2:
			imgPath = "img/New_Jumbo_Squirrel_edited.png";
			_health = 10.0*multiplier;
			pointValue = Globals.bossValue;
			break;
		}
		

	}
	/**
	 * Method to update the position of the enemy entities.
	 */
	@Override
	public void update(GameContainer gc, int delta) {
		switch(movement){
		case Random:
			RandomMovement();
			break;
		case SliceToRight:
			SpawnTopLeft();
			xSpeed += accel;
			ySpeed = 5;
			moveBy(xSpeed, ySpeed);
			break;
		case SliceToLeft:
			SpawnTopRight();
			xSpeed -= accel;
			ySpeed = 5;
			moveBy(xSpeed, ySpeed);
			break;
		case VShoot:					//Enemy moves in a V. Shoots in the middle.
			SpawnTopLeft();
			ySpeed = 5;
			xSpeed = 10;
			if (x == (GameWindow.SCREEN_WIDTH/2) - 30){
				time += delta; //Time
				if (time >= 1000){
					Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 10, 0, 1, false, true)));
					x += xSpeed;
				}
				break;
			}
			if (x >= (GameWindow.SCREEN_WIDTH/2) - 30){
				ySpeed = -5;
			}
			moveBy(xSpeed, ySpeed);
			break;
		}
	}
/**
 * 	Method to spawn an enemy in the top left
 */
private void SpawnTopLeft(){
	if (initialize == false){
		x = 0;
		y = 0;
		initialize = true;
	}
}

/**
 * Method to spawn an enemy in the top right
 */
private void SpawnTopRight(){
	if (initialize == false){
		x = GameWindow.SCREEN_WIDTH - 50;
		y = 0;
		initialize = true;
	}
}
	
/**
 * Method to spawn an enemy with random movement and to test that the enemy won't pass beyond a certain y value
 */
private void RandomMovement(){
		EnemyFire();
		Random r_f = new Random();
		Random r_x = new Random();
		Random r_y = new Random();
		float Low = -MOVEMENT_SPEED;				//Min distance to be moved. Negative means to the left or up.
		float High = MOVEMENT_SPEED;				//Max distance to be moved. Positive means to the right or down.
		
		int Frames = r_f.nextInt(35-25) + 25;
				
		if (Move > Frames){
			result_x = (int) (r_x.nextInt((int) (High-Low)) + Low);
			result_y = (int) (r_y.nextInt((int) (High-Low))+Low);
			if (y+result_y > 500){ //TODO set this back to < 500. Changed for testing purposes.
				D.BUG("Test Limit Reached");
				result_y = -5;
			}
		
		
		
		
		
		//D.BUG("New Random Assigned");
		Move = 0;

		}
		else{
			
			Move++;
			//D.BUG("Move added 1");
		}
		this.moveBy((result_x), (result_y));

		
		//D.BUG(Float.toString(y));
}
/**
 * Stuff needs to be added to this still
 */
private void Movement(){

}
	
		
	
/**
 * Method to draw the entities in the desired position according to the update method.
 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

		
		
		//Draw the player at the default starting coordinates
		//_entityImg.draw(x,y);
		g.drawImage(_entityImg, x, y);
		
		
	}


/**
 * Method to establish if an entity is dangerous to the player.
 */
	@Override
	public boolean isDangerous() {
		return true;
	}


/**
 * Method to set the file location for the desired enemy .png.
 */
	@Override
	public void init(GameContainer gc) throws SlickException {
		// TODO Auto-generated method stub
		this._entityImg = new Image(imgPath);
	}

	/**
	 * Method to return the current type of enemy.
	 * @return The enum type of this Enemy instance
	 */
	public EnemyType getEnemyType(){
		return _currentEnemy;
	}


/**
 * Method to determine if two entities are currently colliding.
 */
	@Override
	public boolean onCollide(Entity collidedWith) {
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
		
		if(_health<1)
			dead = true;
		return dead;
	}
	
	/**
	 * Method to get the point value for the enemy
	 * @return pointValue The point value of the enemy
	 */
	public int PointValue(){
		return pointValue;
	}
	
	public void EnemyFire(){
		Random r_f = new Random();
		
		int RandomFire = r_f.nextInt(300-0);
		
		if (RandomFire>298)
		Engine.instance.addEntity(new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 0, -20, 0, 1, false));
		
		
	}


}