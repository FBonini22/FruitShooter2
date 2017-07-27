

package view;

import org.newdawn.slick.*;

import entities.FruitType;
import entities.Player;

public class Engine extends BasicGame{

	//Constants
	private final float MOVE_CONST = 2.0f;
	private final int FRAME_RATE = 30;		//Frame rate in fps
	
	public float x = 100f;
	public float y = 400f;
	public float x1 = (x+16);
	public float y1 = (y);
	// Add comments

	//TESTING
	private Player p1 = new Player(FruitType.Watermelon, 1);

	private Image Watermelon;
	private Image Seed;
	private Image Background;
	/*private Image Lemon;
	private Image Apple;
	private Image Banana;
	private Image Enemy;
	private Image Squirrel;
	*/
	public Engine(String title) {
		super(title);
	}
	

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setTargetFrameRate(FRAME_RATE);
		gc.setAlwaysRender(true);
		gc.setMaximumLogicUpdateInterval(FRAME_RATE);
		gc.setVSync(true);
		gc.setShowFPS(false);
		/*Music openingMenuMusic = new Music(""); //TODO Need to find and insert suitable music
    		openingMenuMusic.loop(); */
		
		
	}	
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		Background = new Image("img/Background.png"); //TODO Change background to desired image
		Background.draw(0,0);
		Watermelon = new Image("img/Watermelon.png");
		Seed = new Image("img/seed.png");
		Seed.draw(x1,y1);
		Watermelon.draw(x,y);
		
		p1.render(gc, g);
		
		
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		p1.update(gc, delta);
	}
	
	
	

}


