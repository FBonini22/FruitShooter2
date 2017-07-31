package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PlayerBullet extends Bullet{

   private String imgPath = "img/Seed.png";	
   private float dmg;

   public PlayerBullet(float x, float y, float xMove, float yMove, float width, float height){
      super(x, y, xMove, yMove, width, height);
      dmg = 10;
   }
   
   //Check to see if this works
   private void Movement(float xMove, float yMove){
         this.moveBy(xMove, yMove);
   }
   
   public void update(GameContainer gc, int delta){
      Movement(getxMove(), getyMove());
   }
	
	public void render(GameContainer gc, Graphics g) throws SlickException{
		g.drawImage(_entityImg, x, y);
   }

	@Override
	public void init(GameContainer gc) throws SlickException {
		_entityImg = new Image(imgPath);
	}
		

	@Override
	public boolean isDangerous() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCollide(Entity collidedWith) {
		// TODO Auto-generated method stub
		
	}
}