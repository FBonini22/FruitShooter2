package entities;

import org.newdawn.slick.*;

import view.GameWindow;

public abstract class Entity extends HitBox{
	
	//Constants
	private final int EXTERNAL_PIXELS = 20;							//The amount of pixels an entity may move off screen before it cannot go any further
	
	//Instance Variables
	
	
	public Entity(float x, float y, float width, float height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}


	public Image _entityImg;
	
	//TO-DO Implement acceleration
	//protected float _speedX = 0f;
	//protected float _speedY = 0f;


	
	public abstract void init(GameContainer gc) throws SlickException;
	
	public abstract void render(GameContainer gc, Graphics g) throws SlickException;
	
	public abstract void update(GameContainer gc, int delta);
	
	
	/**
	 * To override. Determines whether the entity will cause harm to the player
	 * @return TRUE for dangerous. FALSE for harmless.
	 */
	public abstract boolean isDangerous();
	
	/**
	 * To override. Built-in method for collision handling.
	 * @param collidedWith The other Entity that this Entity instance has collided with.
	 */
	public abstract void onCollide(Entity collidedWith);
	
	
	
	/**
	 * Method for moving entity to position
	 * @param posX x Coordinate on screen to move to
	 * @param posY y Coordinate on screen to move to
	 */
	public void moveTo(float posX, float posY){
		this.x = (posX < 0)
				? 1
				: (posX > GameWindow.SCREEN_WIDTH)
						? GameWindow.SCREEN_WIDTH - 1
						: posX;
		this.y = (posY < 0)
				? 1
				: (posY > GameWindow.SCREEN_HEIGHT)
						? GameWindow.SCREEN_HEIGHT - 1
						: posY;
	}
	
	
	/**
	 * Method for moving an entity by a distance. Check for World bounds here
	 * @param transX x value to translate entity by
	 * @param transY y value to translate entity by
	 */
	public void moveBy(float transX, float transY){

		//Sets the x-bounds for all entities
		this.x = (this.getEndX() + transX > GameWindow.SCREEN_WIDTH + EXTERNAL_PIXELS)
					? x
					: (x + transX < -EXTERNAL_PIXELS)
						? x
						: x + transX;
		
		//Sets the y-bounds for all entities
		this.y = (this.getEndY() + transY> GameWindow.SCREEN_HEIGHT + EXTERNAL_PIXELS)
				? y
				: (y + transY < -EXTERNAL_PIXELS)
					? y
					: y + transY;
	}
	
	
	
	
	
	
}
