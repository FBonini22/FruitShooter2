package entities;

import org.newdawn.slick.*;

import view.GameWindow;

public abstract class Entity extends HitBox{
	
	public Image _entityImg;
	
	
	//protected float _speedX = 0f;
	//protected float _speedY = 0f;

	public Entity(){
		
	}
	
	
	public abstract void update(GameContainer gc, int delta);
	
	public abstract void render(GameContainer gc, Graphics g);
	
	
	
	/**
	 * Method for moving entity to position
	 * @param posX x Coordinate on screen to move to
	 * @param posY y Coordinate on screen to move to
	 */
	public void moveTo(float posX, float posY){
		this.x = (posX < 0)
				? 0
				: (posX > GameWindow.SCREEN_WIDTH)
						? GameWindow.SCREEN_WIDTH
						: posX;
		this.y = (posY < 0)
				? 0
				: (posY > GameWindow.SCREEN_HEIGHT)
						? GameWindow.SCREEN_HEIGHT
						: posY;
	}
	
	
	/**
	 * Method for moving an entity by a distance
	 * @param posX x value to translate entity by
	 * @param posY y value to translate entity by
	 */
	public void moveBy(float posX, float posY){

		this.x += posX;
		this.y += posY;
	}
	
	
	
	
}
