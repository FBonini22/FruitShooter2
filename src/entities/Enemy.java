//TODO Add more enemies and reduce firing to certain types of enemies using a switch statement in the update method
package entities;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import gameStates.Engine;
import globals.Globals;
import utilities.D;
import view.GameWindow;

public class Enemy extends Entity{
	
		

	//Constants		
	private final float ENEMY_EXTRA_BOUNDS = 128f;		//How many pixels outside the screen the enemy may move
	private final float MAX_X = GameWindow.SCREEN_WIDTH + Globals.GRUNT_WIDTH;
	private final float MAX_Y = GameWindow.SCREEN_HEIGHT+ Globals.GRUNT_HEIGHT;
	private final float MIN_X = 0 - Globals.BULLET_WIDTH;
	private final float MIN_Y = 0 - Globals.BULLET_HEIGHT;
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
	Random random = new Random();
	private float multiplier = 1;	//Parameter used to increase the health of an enemy
	private int count;
	
	/**
	 * 
	 * @param selectedEnemy The fruit that the player will play as
	 * @param pNum 			The number of the enemy that is being generated ex. enemy 1, enemy 2...
	 */

public Enemy(float x, float y, EnemyType selectedEnemy, float P1, float P2, EnemyMovement move){ //Added parameters to take in the size of the hitbox desired
		
		super(x, y, P1,P2);
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
			ySpeed = 8;
			moveBy(xSpeed, ySpeed);
			break;
		case SliceToLeft:
			SpawnTopRight();
			xSpeed -= accel;
			ySpeed = 8;
			moveBy(xSpeed, ySpeed);
			break;
		case VShoot:					//Enemy moves in a V. Shoots in the middle.
			SpawnTopLeft();
			ySpeed = 5;
			xSpeed = 10;
			if (x == (GameWindow.SCREEN_WIDTH/2) - 30){
				time += delta; //Time
				if (time >= 1000){
					Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 10, 0, 1, true)));
					x += xSpeed;
				}
				break;
			}
			if (x >= (GameWindow.SCREEN_WIDTH/2) - 30){
				ySpeed = -5;
			}
			moveBy(xSpeed, ySpeed);
			break;
		case Spider:
			if (initialize == false){
				x = random.nextInt(GameWindow.SCREEN_WIDTH - 30);
				y = 0;
				ySpeed = 5;
				initialize = true;
			}
			xSpeed = 0;
			moveBy(xSpeed, ySpeed);
			if (ySpeed > 0 && ySpeed < 0.05){
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 10, 0, 1, true)));
			}
			ySpeed -= (accel/4);
			break;
		case Teleport:
			if (initialize == false){
				x = random.nextInt(GameWindow.SCREEN_WIDTH - 30);
				y = random.nextInt(GameWindow.SCREEN_HEIGHT - 300);
				initialize = true;
			}
			time += delta;
			if (time >= 2000 && count == 1){
				moveTo(random.nextInt(GameWindow.SCREEN_WIDTH - 30), y = random.nextInt(GameWindow.SCREEN_HEIGHT - 300));
				count--;
				time = 0;
			}
			if (time >= 2000){
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 5, 5, 0, 1)));
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 0, 5, 0, 1)));
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, -5, 5, 0, 1)));
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 5, 0, 0, 1)));
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, -5, 0, 0, 1)));
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 0, -5, 0, 1)));
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 5, -5, 0, 1)));
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, -5, -5, 0, 1)));
				count++;
				time = 0;
			}
			break;
		case RightUnderSwing:
			if (initialize == false){
				x = 0;
				y = GameWindow.SCREEN_HEIGHT;
				xSpeed = 15;
				ySpeed = -5;
				initialize = true;
			}
			xSpeed -= accel;
			
			if(xSpeed <= 0){
				time += delta;
				if (time >= 225){
					Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 2, 5, 0, 1)));
					time = 0;
				}
			}
			moveBy(xSpeed, ySpeed);
			break;
		case LeftUnderSwing:
			if (initialize == false){
				x = GameWindow.SCREEN_WIDTH;
				y = GameWindow.SCREEN_HEIGHT;
				xSpeed = -15;
				ySpeed = -5;
				initialize = true;
			}
			xSpeed += accel;
			
			if(xSpeed >= 0){
				time += delta;
				if (time >= 225){
					Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, -2, 5, 0, 1)));
					time = 0;
				}
			}
			moveBy(xSpeed, ySpeed);
			break;
		case RightChase:
			if (initialize == false){
				x = 20;
				y = -10;
				xSpeed = 0;
				ySpeed = 5;
				initialize = true;
			}
			if(y >= Engine.y){
				xSpeed = 15;
				ySpeed = 0;
			}
			moveBy(xSpeed, ySpeed);
			break;
		case LeftChase:
			if (initialize == false){
				x = GameWindow.SCREEN_WIDTH - 50;
				y = -10;
				xSpeed = 0;
				ySpeed = 5;
				initialize = true;
			}
			if(y >= Engine.y){
				xSpeed = -15;
				ySpeed = 0;
			}
			moveBy(xSpeed, ySpeed);
			break;
		case Star:
			if (initialize == false){
				x = GameWindow.SCREEN_WIDTH/2 - 20;
				y = 100;
				initialize = true;
			}
			time += delta;
			if (time > 0){
				xSpeed = -2;
				ySpeed = 6;
			}
			if (time > 500){
				xSpeed = 5;
				ySpeed = -3;
			}
			if (time > 1000){
				xSpeed = -6;
				ySpeed = 0;
			}
			if (time > 1500){
				xSpeed = 5;
				ySpeed = 3;
			}
			if (time > 2000){
				xSpeed = -2;
				ySpeed = -6;
			}
			if (time > 2500){
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 0, 5, 0, 1)));
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, -1, 4, 0, 1)));
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 2, 3, 0, 1)));
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 1, 4, 0, 1)));
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, -2, 3, 0, 1)));
				time = 0;
			}
			moveBy(xSpeed, ySpeed);
			break;
		case Parabola:
			if (initialize == false){
				x = GameWindow.SCREEN_WIDTH - 20;
				xSpeed = -3.5f;
				y = GameWindow.SCREEN_HEIGHT;
				ySpeed = -17;
				initialize = true;
			}
			time += delta;
			ySpeed += accel;
			
			if (time > 100){
				count++;
				time = 0;
			}
			
			if (count == 5){
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, -3, 4, 0, 1)));
				count++;
			}
			else if (count == 14){
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 0, 4, 0, 1)));
				count++;
			}
			else if (count == 22){
				Engine.instance.addEntity((new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 3, 4, 0, 1)));
				count++;
			}
			
			
			moveBy(xSpeed, ySpeed);
		}	
	}
/**
 * 	Method to spawn an enemy in the top left
 */
private void SpawnTopLeft(){
	if (initialize == false){
		x = -10;
		y = -10;
		initialize = true;
	}
}

/**
 * Method to spawn an enemy in the top right
 */
private void SpawnTopRight(){
	if (initialize == false){
		x = GameWindow.SCREEN_WIDTH - 30;
		y = -10;
		initialize = true;
	}
}
/**
 * Method to spawn an enemy in the bottom right
 */
private void SpawnBottomRight(){
	if (initialize == false){
		x = GameWindow.SCREEN_WIDTH;
		y = GameWindow.SCREEN_HEIGHT;
		initialize = true;
	}
}
/**
 * Method to spawn an enemy in the bottom left
 */
private void SpawnBottomLeft(){
	if (initialize == false){
		x = 0;
		y = GameWindow.SCREEN_HEIGHT;
		initialize = true;
	}
}

/**
 * Method to determine random enemy movement
 */
private void RandomMovement(){
	EnemyFire();
	float Low = -MOVEMENT_SPEED;				//Min distance to be moved. Negative means to the left or up.
	float High = MOVEMENT_SPEED;				//Max distance to be moved. Positive means to the right or down.
	int checkX;
	int checkY;
	
	int Frames = 25;
	
	if (Move >= Frames){
		result_x = (int) (random.nextInt((int) (High-Low)) + Low);
		result_y = (int) (random.nextInt((int) (High-Low)) + Low);
		
		checkX = (int)result_x * Frames;
		checkY = (int)result_y * Frames;
		
		do{
			result_x = (int) (random.nextInt((int) (High-Low)) + Low);
			checkX = (int)result_x * Frames;
		}while(x - checkX >= GameWindow.SCREEN_WIDTH || x + checkX <= 0);

		do{
			result_y = (int) (random.nextInt((int) (High-Low)) + Low);
			checkY = (int)result_y * Frames;
		}
		while(y - checkY >= GameWindow.SCREEN_HEIGHT || y + checkY <= 0);
		
		if (y >= 500){
			result_y = -5;
		}
		Move = 0;
	}
	else{
		
		Move++;
		//D.BUG("Move added 1");
	}
	
	
	this.moveBy((result_x), (result_y)); 
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
	
	
	@Override
	public void moveBy(float transX, float transY){

		
//		D.BUG("Inside overridden moveBy method");
		
		//Sets the x-bounds for all bullets
		this.x = (this.getEndX() + transX > (float)GameWindow.SCREEN_WIDTH + ENEMY_EXTRA_BOUNDS)
					? x
					: (x + transX < -ENEMY_EXTRA_BOUNDS)
						? x
						: x + transX;
		
		//Sets the y-bounds for all bullets
		this.y = (this.getEndY() + transY> (float)GameWindow.SCREEN_HEIGHT + ENEMY_EXTRA_BOUNDS)
				? y
				: (y + transY < -ENEMY_EXTRA_BOUNDS)
					? y
					: y + transY;
//		D.BUG(String.valueOf(x));
//		D.BUG(String.valueOf(y));
		
		checkBounds();
	}
	
	private void checkBounds(){
		if(this.y > MAX_Y || this.y < MIN_Y || this.x > MAX_X || this.x < MIN_X){
			Engine.instance.markForRemoval(this);
//			D.BUG("REMOVING OUT OF BOUNDS BULLET");
		}
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
		Engine.instance.addEntity(new EnemyBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 0, 10, 0, 1));
		
		
	}


}
