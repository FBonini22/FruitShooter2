package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class EnemyBullet extends Entity{

   private float _xMove;
   private float _yMove;
   private boolean _accel;
   
   private final float _accelConstant = 20; //pixels? probably
   
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
   */
   public EnemyBullet(float x, float y, float width, float height, float xMove, float yMove, boolean accel){
      
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      _xMove = xMove;
      _yMove = yMove;
      _accel = accel;
      
      //_entityImg = "img/Acorn.png";
      
   }
   
   //Check to see if this works
   public void Movement(float xMove, float yMove){
      /*
	   if (accel == true){
         
      }
      else{
         this.moveBy(x + xMove, y + yMove);
      }
      */
	  this.moveBy(x + xMove, y + yMove);
   }
   
   private float getxMove(){
	   return _xMove;
   }
   
   private float getyMove(){
	   return _yMove;
   }
   
   
   public void update(GameContainer gc, int delta){
      Movement(getxMove(), getyMove());
   }
	
	public void render(GameContainer gc, Graphics g){
		_entityImg.draw(x,y);
   }

	@Override
	public boolean isDangerous() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		// TODO Auto-generated method stub
		
	}
}