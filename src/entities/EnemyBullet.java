package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import view.Engine;

public class EnemyBullet extends Bullet{

   //Instance Variables
   private boolean _accel;
   private float _xSpeed;
   private float _ySpeed;
   private float _speed;
   private float _hyp;
   private float _xMove;
   private float _yMove;
   
   
   private boolean _homing;
   private float pX;
   private float pY;
   private boolean _check;

   //Constants
   private final float _acceleration = .5f;
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
   
   /**
    * Secondary Bullet Class
    * @param x is the initial x position
    * @param y is the initial y position
    * @param speed is the speed of the bullet
    * @param width is the width of the hitbox
    * @param height is the height of the hitbox
    * @param accel determines if the bullet accelerates
    * @param homing determines if the bullet will target for the player
    */
   public EnemyBullet(float x, float y, float speed, float width, float height, boolean accel, boolean homing){
		  super(x, y, 0, 0, width, height);
	      _accel = accel;
	      _homing = homing;
	      pX = Engine.x;
	      pY = Engine.y;
	      _speed = speed;
	      
	      _xSpeed = 0;
	      _ySpeed = 0;
	   }
   
   private void Movement(float xMove, float yMove){
	  if (_homing == true){
			 if (_check == false){
				 Homing(pX, pY);
				 _check = true;
			 }
			 
			 if (_accel == true){
				 Accelerate(_xMove, _yMove);
			 }
			 else{
				 this.moveBy(_xMove, _yMove);
			 }
		 } 
	  else if (_accel == true){
		  Accelerate(xMove, yMove);
      }
      else{
         this.moveBy(xMove, yMove);
      }
   } 
   
   private void Homing(float pX, float pY){
	   _hyp = (float) Math.sqrt(((pX-x)*(pX-x)) + ((pY-y)*(pY-y)));
	   _xMove = ((pX - x)/_hyp) * _speed;
	   _yMove = ((pY - y)/_hyp) * _speed;
   }
   
   //Still fixing this
   private void Accelerate(float xMove, float yMove){
		  if (_xSpeed > xMove || _ySpeed > yMove){
			  _xSpeed = xMove;
			  _ySpeed = yMove;
		  }
			  
		  this.moveBy(_xSpeed, _ySpeed);
		  _ySpeed += _acceleration;
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