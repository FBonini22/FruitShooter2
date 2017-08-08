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

public class Enemy extends Entity{
	
		

	//Constants		

	private int pointValue;
	
	//Instance Variables
	private EnemyType _currentEnemy;					//The user-selected enemy
	private int _EnemyNum;								//The number enemy created
	private int _offSet;								//The amount that the generated enemy will be offset from the first generated enemy.
	private final float MOVEMENT_SPEED = 5f;			//Movement speed in pixels per second
	private String imgPath = "img/test.png";			//Path to image that will be loaded for this Enemy instance
	private int Move = 100;
	private double _health = 0d;
	private float result_x = 0;
	private float result_y = 0;
	int result = 0;										//Determines if enemy will move randomly
	
	boolean random;
	/**
	 * 
	 * @param selectedEnemy The fruit that the player will play as
	 * @param pNum 			The number of the enemy that is being generated ex. enemy 1, enemy 2...
	 */

	public Enemy(EnemyType selectedEnemy, int pNum, float P1, float P2){ //Added parameters to take in the size of the hitbox desired
		
		super(0, 0, P1,P2);


		//Instantiate instance variables
		_currentEnemy = selectedEnemy;
		_EnemyNum = pNum;		
		_offSet =_EnemyNum*26;		

		//random = randomness;
		
		//Testing Variables

		
		InitializeEnemyAttributes();

	}
		
	//I ADDED THIS CONSTRUCTOR BECAUSE ENEMIES WERE BEING CREATED IN DIFFERENT WAYS. UNIFY OR KEEP BOTH.
	//THIS COULD HAVE BEEN DUE TO AN ERROR DURING A MERGE.
	//-FRANK
	public Enemy(int i, int j, EnemyType selectedEnemy, int pNum, boolean b) {
	
		super(0,0, Globals.GRUNT_WIDTH, Globals.GRUNT_HEIGHT);
		// TODO Auto-generated constructor stub
		_currentEnemy = selectedEnemy;
		_EnemyNum = pNum;		
		_offSet =_EnemyNum*26;	
		random = b;
		
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
			_health = 1.0;
			pointValue = Globals.gruntValue;
			break;
		case JumboSquirrel:
			imgPath = "img/Jumbo_Squirrel.png";
			_health = 2.0;
			pointValue = Globals.bossValue;
			break;
		
		}
		

	}
	/**
	 * Method to update the position of the enemy entities.
	 */
	@Override
	public void update(GameContainer gc, int delta) {

		if(random == true){
			RandomMovement();
		}
		else{
			Movement();
			this.moveTo(result_x, result_y);
		}
           
	}

private void RandomMovement(){
		

		EnemyFire();
		Random r_f = new Random();
		int Frames = r_f.nextInt(35-25) + 25;
				
		if (Move > Frames){

		
		Random r_x = new Random();
		Random r_y = new Random();
		float Low = -MOVEMENT_SPEED;				//Min distance to be moved. Negative means to the left or up.
		float High = MOVEMENT_SPEED;				//Max distance to be moved. Positive means to the right or down.
		result_x = (int) (r_x.nextInt((int) (High-Low)) + Low);
		result_y = (int) (r_y.nextInt((int) (High-Low))+Low);
		
		//D.BUG("New Random Assigned");
		Move = 0;
		}
		else{
			
			Move++;
			//D.BUG("Move added 1");
		}
		this.moveBy((result_x), (result_y)); 
}

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
