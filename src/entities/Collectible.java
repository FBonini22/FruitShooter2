package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Collectible extends Entity{
	
	//Instance Variables
	private float ySpeed = -2;
	private float accel = .05f;
	private float maxSpeed = 5;
	
	private String imgPath = "img/test.png";	
	private CollectibleType type;

	/**
	 * Power up Constructor
	 * @param x position
	 * @param y position
	 * @param width of hit box
	 * @param height of hit box
	 */
	public Collectible(float x, float y, float width, float height, CollectibleType cType) {
		super(x, y, width, height);
		type = cType;
	}
	
	public CollectibleType getType(){
		return type;
	}
	
	/**
	 * Moves the power up up before going back down
	 */
	private void Movement(){
		if(ySpeed >= maxSpeed){
			this.moveBy(0, maxSpeed);
		}
		else{
			this.moveBy(0, (ySpeed + accel));
			ySpeed += accel;
		}
	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		Movement();
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		switch(type){
		case Health:
			imgPath = "img/Health.png";
			break;
		case PowerUp:
			imgPath = "";
			break;
		case Points:
			imgPath = "";
			break;
		case Bomb:
			imgPath = "img/Bomb.png";
			break;
		}
		 _entityImg = new Image(imgPath);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawImage(_entityImg, x, y);
		
	}

	@Override
	public boolean isDangerous() {
		return false;
	}

	@Override
	public boolean onCollide(Entity collidedWith) {
		return false;
	}

}
