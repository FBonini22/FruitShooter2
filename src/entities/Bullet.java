package entities;

/**
 * Abstract class for a bullet entity.
 */
public abstract class Bullet extends Entity{
	private float _xMove;
	private float _yMove;
	private float _angle;
	
	public Bullet(float x, float y, float xMove, float yMove, float width, float height, float radius, float angle){
		
		super(x, y, width, height, radius);
		
		this.x = x;
		this.y = y;
		_xMove = xMove;
		_yMove = yMove;
		_angle = angle;
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
		
		float adjustedX = (5 * (float)Math.cos(Math.toRadians((double)_angle)));
		float adjustedY = (5 * (float)Math.sin(Math.toRadians((double)_angle)));
		
		super.moveBy(adjustedX, adjustedY);
		
	}
}
