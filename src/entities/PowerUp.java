package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PowerUp extends Entity{
	
	private float _ySpeed;
	private float _accel;
	private float _maxSpeed;
	
	private String imgPath = "img/test.png";	

	public PowerUp(float x, float y, float width, float height) {
		super(x, y, width, height);
		_ySpeed = -2;
		_accel = .05f;
		_maxSpeed = 5;
	}
	
	private void Movement(){
		if(_ySpeed >= _maxSpeed){
			this.moveBy(0, _maxSpeed);
		}
		else{
			this.moveBy(0, (_ySpeed + _accel));
			_ySpeed = _ySpeed + _accel;
		}
	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		Movement();
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		 _entityImg = new Image(imgPath);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawImage(_entityImg, x, y);
		
	}

	@Override
	public boolean isDangerous() {
		return false;
	}

	@Override
	public void onCollide(Entity collidedWith) {
		// TODO Auto-generated method stub
		
	}

}
