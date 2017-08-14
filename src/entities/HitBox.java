package entities;

public abstract class HitBox{

	protected float x;			//X location of the hitbox
	protected float y;			//Y location of the hitbox
	protected float width;		//Width of the hitbox
	protected float height;		//Height of the hitbox
	

	/**
	 * 
	 * @param x	Horizontal location of the hitbox
	 * @param y Vertical location of the hitbox
	 * @param width Width of the hitbox
	 * @param height Height of the hitbox
	 */
	public HitBox(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
	/**
	 * Method to return the farthest right point of the hitbox
	 * @return Returns a float of the location of the farthest right point on the hitbox
	 */
	public float getEndX() {
		return (x + width);
	}
	
	/**
	 * Method to return the bottom location of the hitbox
	 * @return Returns a float value of the bottom point on the hitbox
	 */
	public float getEndY() {
		return (y + height);
	}
	
	/**
	 * Method to return half the width of the hitbox
	 * @return Returns a flaot value of half the width of the hitbox
	 */
	public float getHalfWidth() {
		return (width / 2);
	}
	
	/**
	 * Method to return half the height of the hitbox
	 * @return Returns a float value of half the height of the hitbox
	 */
	public float getHalfHeight() {
		return (height / 2);
	}
	
	/**
	 * Method to return the center horizontal point of the hitbox
	 * @return Returns a float value for the horizontal center of the hitbox
	 */
	public float getCenterX() {
		return (x + getHalfWidth());
	}
	
	/**
	 * Method to return the center vertical point of the hitbox
	 * @return Returns a float value for the vertical center of the hitbox
	 */
	public float getCenterY() {
		return (y + getHalfHeight());
	}
	
	/**
	 * Determines whether two hitboxes are overlapping/colliding. This hitbox class is compared with the paramater
	 * @param b The hitbox to compare to this instance
	 * @return TRUE for collision. FALSE for no collision
	 */
	public boolean hitTest(HitBox b) {
		return (b.getEndX() >= x && b.getEndY() >= y && getEndX() >= b.x && getEndY() >= b.y);
	}
}
