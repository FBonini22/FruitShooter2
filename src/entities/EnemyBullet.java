package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class EnemyBullet extends Bullet{

   //Instance Variables
   private boolean _accel;
   private float _xSpeed;
   private float _ySpeed;

   //Constants
   private final float _acceleration = .025f;
   private String imgPath = "img/test.png";	

   /**
   *Bullet Class
   *@param x is the initial x position
   *@param y is the initial y position
   *@param width is the width of the hitbox/size
   *@param height is the height of the hitbox/size
   *@param xMove is the x speed of bullet in pixels
   *@param yMove is the y speed of the bullet in pixels
   *@param accel determines if the bullet accelerates to max speed
   *@throws SlickException 
   */
   public EnemyBullet(float x, float y, float width, float height, float xMove, float yMove, boolean accel){
	  super(x, y, xMove, yMove, width, height);
      _accel = accel;
      
      _xSpeed = 0;
      _ySpeed = 0;
   }
   
   private void Movement(float xMove, float yMove){
	  if (_accel == true){
		  if (_xSpeed <= xMove && _ySpeed <= yMove){
			  this.moveBy(_xSpeed, _ySpeed);
			  _xSpeed = (_xSpeed + _acceleration);
			  _ySpeed = (_ySpeed + _acceleration);
		  }
		  else{
			  this.moveBy(xMove, yMove);
		  }
      }
      else{
         this.moveBy(xMove, yMove);
      }
   } 
   
   public void update(GameContainer gc, int delta){
      Movement(getxMove(), getyMove());
   }
	
	public void render(GameContainer gc, Graphics g) throws SlickException{
		g.drawImage(_entityImg, x, y);
   }

	@Override
	public boolean isDangerous() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
	     _entityImg = new Image(imgPath);
	}
}