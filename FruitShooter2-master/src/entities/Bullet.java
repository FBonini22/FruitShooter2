package entities;

import Utilities.D;
import globals.Globals;
import view.Engine;
import view.GameWindow;

/**
 * Abstract class for a bullet entity.
 */
public abstract class Bullet extends Entity{
	
	//Constants
	private final float BULLET_EXTRA_BOUNDS = 64f;		//How many pixels outside the screen may the bullet move
	private final float MAX_X = GameWindow.SCREEN_WIDTH + Globals.BULLET_WIDTH;
	private final float MAX_Y = GameWindow.SCREEN_HEIGHT+ Globals.BULLET_HEIGHT;
	private final float MIN_X = 0 - Globals.BULLET_WIDTH;
	private final float MIN_Y = 0 - Globals.BULLET_HEIGHT;
	
	
	private float _xMove;
	private float _yMove;
	
	public Bullet(float x, float y, float xMove, float yMove, float width, float height){
		
		super(x, y, width, height);

		_xMove = xMove;
		_yMove = yMove;
		this.width = width;
		this.height = height;
	}
	
	public float getxMove(){
		return _xMove;
	}
	   
	public float getyMove(){
		return _yMove;
	}
	
	@Override
	public void moveBy(float transX, float transY){

		
//		D.BUG("Inside overridden moveBy method");
		
		//Sets the x-bounds for all bullets
		this.x = (this.getEndX() + transX > (float)GameWindow.SCREEN_WIDTH + BULLET_EXTRA_BOUNDS)
					? x
					: (x + transX < -BULLET_EXTRA_BOUNDS)
						? x
						: x + transX;
		
		//Sets the y-bounds for all bullets
		this.y = (this.getEndY() + transY> (float)GameWindow.SCREEN_HEIGHT + BULLET_EXTRA_BOUNDS)
				? y
				: (y + transY < -BULLET_EXTRA_BOUNDS)
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
}
