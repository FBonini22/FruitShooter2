

package view;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.*;

import entities.Enemy;
import entities.EnemyType;
import entities.Entity;
import entities.FruitType;
import entities.Player;

public class Engine extends BasicGame{

	//Constants
	private final int FRAME_RATE = 30;		//Frame rate in fps
	
	// Add comments

	//TESTING
	private Player p1 = new Player(FruitType.Watermelon, 1);
	// TODO New background and cleaner implementation 
	private Image Background;

			
	
	private List<Entity> entities = new ArrayList<Entity>();	//List of drawable entities

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
		entities.add(p1);
		for (int i = 1; i<15; i++){
			entities.add(new Enemy(EnemyType.Squirrel, i));
		}
		/*Music openingMenuMusic = new Music(""); //TODO Need to find and insert suitable music
    		openingMenuMusic.loop(); */
		
		
	}	
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		Background = new Image("img/Background.png"); //TODO Change background to desired image
		Background.draw(0,0);
				
		
		
		for(Entity e : entities){
			e.render(gc, g);
			
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
	
		
		for(Entity e : entities){
			e.update(gc, delta);
			
		}
	}
	
	
	

}


