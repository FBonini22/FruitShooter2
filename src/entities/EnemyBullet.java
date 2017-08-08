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
   
   private boolean _homing;
   private float pX;
   private float pY;

   //Constants
   private final float _acceleration = .025f;
   private String imgPath = "img/acorn2.png";	

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
   public EnemyBullet(float x, float y, float xMove, float yMove, float width, float height, boolean accel){
	  super(x, y, xMove, yMove, width, height);
      _accel = accel;
      _homing = false;
      
      _xSpeed = 0;
      _ySpeed = 0;
   }
   
   public EnemyBullet(float x, float y, float xMove, float yMove, float width, float height, boolean accel, boolean homing, float pX, float pY){
		  super(x, y, xMove, yMove, width, height);
	      _accel = accel;
	      _homing = homing;
	      this.pX = pX;
	      this.pY = pY;
	      
	      _xSpeed = 0;
	      _ySpeed = 0;
	   }
   
   private void Movement(float xMove, float yMove){
	  if (_accel == true){
		  Accelerate(xMove, yMove);
      }
      else{
         this.moveBy(xMove, yMove);
      }
   } 
   
   private void Accelerate(float xMove, float yMove){
		  if (_xSpeed >= xMove){
			 _xSpeed = xMove;  
		  }
		  if (_ySpeed >= yMove){
			  _ySpeed = yMove;
		  }
		  this.moveBy(_xSpeed, _ySpeed);
		  _xSpeed = (_xSpeed + _acceleration);
		  _ySpeed = (_ySpeed + _acceleration);
   }
   
   public void update(GameContainer gc, int delta){
      Movement(getxMove(), getyMove());
   }
	
	public void render(GameContainer gc, Graphics g) throws SlickException{
		g.drawImage(_entityImg, x, y);
   }

	@Override
	public boolean isDangerous() {
		return true;
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
	     _entityImg = new Image(imgPath);
	}

	@Override
	public void onCollide(Entity collidedWith) {
		// TODO Auto-generated method stub
		
	}
}