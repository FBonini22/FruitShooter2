package entities;

import org.newdawn.slick.*;

public abstract class Entity extends HitBox{
	
	public Image _entityImg;
	
	
	//protected float _speedX = 0f;
	//protected float _speedY = 0f;

	public Entity(){
		
	}
	
	
	public abstract void update(GameContainer gc, int delta);
	
	public abstract void render(GameContainer gc, Graphics g) throws SlickException;
	
	
	
	/**
	 * Method for moving entity to position
	 * @param posX x Coordinate on screen to move to
	 * @param posY y Coordinate on screen to move to
	 */
	public void moveTo(float posX, float posY){
		this.x = posX;
		this.y = posY;
	}
	/**
	 * Method for moving an entity by a distance
	 * @param posX x values to translate entity by
	 * @param posY y value to translate entity by
	 */
	public void moveBy(float posX, float posY){
		this.x = this.x+posX;
		this.y = this.y+posY;
	}
	
	
	
	
}
