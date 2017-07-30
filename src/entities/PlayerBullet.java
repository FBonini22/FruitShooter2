package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PlayerBullet extends Entity{

   private float _xMove;
   private float _yMove;
   
   private String imgPath = "img/Seed.png";	

   public PlayerBullet(float x, float y, float xMove, float yMove){
      this.x = x;
      this.y = y;
      _xMove = xMove;
      _yMove = yMove;
   }
   
   //Check to see if this works
   public void Movement(float xMove, float yMove){
         this.moveBy(xMove, yMove);
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
	
	public void render(GameContainer gc, Graphics g) throws SlickException{
		g.drawImage(_entityImg, x, y);
   }

	@Override
	public void init(GameContainer gc) throws SlickException {
		this._entityImg = new Image(imgPath);
	}
		

	@Override
	public boolean isDangerous() {
		// TODO Auto-generated method stub
		return false;
	}
}