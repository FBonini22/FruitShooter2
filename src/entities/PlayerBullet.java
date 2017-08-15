package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PlayerBullet extends Bullet{

   private String imgPath = "img/Seed.png";	
   private int pNum;									//Number of player who fired the bullet

   public PlayerBullet(float x, float y, float xMove, float yMove, float width, float height, int playerNum){
      super(x, y, xMove, yMove, width, height);
      pNum = playerNum;
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
		return false;
	}

	@Override
	public boolean onCollide(Entity collidedWith) {	
		return false;
	}
	
	public int getPlayerWhoFired(){
		return pNum;
	}

}
