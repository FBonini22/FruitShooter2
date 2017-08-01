

package view;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.*;

import Utilities.D;
import entities.Bullet;
import entities.PlayerBullet;
import entities.PowerUp;
import entities.EnemyBullet;
import entities.Enemy;
import entities.EnemyType;
import entities.Entity;
import entities.FruitType;
import entities.Player;

public class Engine extends BasicGame{

	//Constants
	private final int FRAME_RATE = 60;							//Frame rate in fps
	private final int NUMBER_OF_SQUIRRELS = 10;					//Number of squirrels. FOR DEBUGGING ONLY
	
	//Instance Variables
	private List<Enemy> enemy = new ArrayList<Enemy>();	//List of drawable entities
	private List<Player> players = new ArrayList<Player>();		//List of players
	private List<Bullet> bullets = new ArrayList<Bullet>();		//List of bullet entities
	private List<Enemy> toRemove = new ArrayList<Enemy>(); 	//List of entities to be removed
	private List<Bullet> toRemoveBullets = new ArrayList<Bullet>();
	private boolean worldClipSet = false;
	private int point;
	private int PointTotal;
	//TESTING
	private Player p1 = new Player(FruitType.Watermelon, 1);
	
	// TODO New background and cleaner implementation 
	private Image Background;

	

	//Singleton Engine Instance
	public static Engine instance = new Engine("FruitShooter1.0a");


	private Engine(String title) {
		super(title);
	}
	

	/**
	 * Initialize entities and graphics here. This method is automatically called ONCE.
	 * @param gc The window/container in which the game is running
	 */
	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setTargetFrameRate(FRAME_RATE);
		gc.setAlwaysRender(true);
		gc.setMaximumLogicUpdateInterval(FRAME_RATE);
		gc.setVSync(true);
		gc.setShowFPS(true);
		
		//Initialize Lists
		players.add(p1);
		
		//Initialize Background image
		Background = new Image("img/Background.png"); //TODO Change background to desired image
		
		
		for (int i = 1; i< NUMBER_OF_SQUIRRELS; i++){
			enemy.add(new Enemy(EnemyType.Squirrel, i));
		}
		/*Music openingMenuMusic = new Music(""); //TODO Need to find and insert suitable music
    		openingMenuMusic.loop(); */
		
		for(Enemy e : enemy){
			e.init(gc);
		}	
		
		for(Player p : players){
			p.init(gc);
		}	
	}	
	
	/**
	 * Render graphics and entities here. This method is automatically called each time the 
	 * game updates itself
	 * @param gc The window/container in which the game is running
	 * @param g The graphics back-end of the running game. Use contained methods to update and draw graphics.
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		//Render background image
		Background.draw(0,0);
		
		//Draw the current point total
		g.drawString(String.format("Player Points: %1$s", String.valueOf((int)PointTotal)), 600, 32);
		
		
		//Set world clipping
		//We want to utilize the Graphics parameter, so we will call the world
		//clipping function here, once. We cannot call it in the init method as far as I know.
		if(!worldClipSet){
			worldClipSet = true;
			g.setWorldClip(0, 0, GameWindow.SCREEN_WIDTH, GameWindow.SCREEN_HEIGHT);
		}
				
		
		//Call each entity's render method
		for(Enemy e : enemy){
			e.render(gc, g);
		}
		
		for(Bullet b : bullets){
			b.render(gc,  g);
		}
		
		for(Player p : players){
			p.render(gc, g);
		}
	}

	/**
	 * Update graphics, entities, and game variables. This method is automatically
	 * called each time the game updates itself. Run the game logic here.
	 * @param gc The container in which the current game is running
	 * @param delta UNKNOWN PARAMETER. TO DO: LOOK UP FUNCTION
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		

		//CHECK FOR PLAYER FIRING
		for(Player p : players){
			if(p.getFiring()){
				bullets.add(new PlayerBullet(p.x + 16, p.y, 0, -20, 1, 1));
				switch(p.getFruit()){
				case Apple:
					
//					break;
				case Banana:

//					break;
				case Lemon:

//					break;
				case Watermelon:
					if (p.getPowerLevel() >= 2){
						bullets.add(new PlayerBullet(p.x + 46, p.y + 10, 0, -20, 1, 1));
						bullets.add(new PlayerBullet(p.x - 16, p.y + 10, 0, -20, 1, 1));
					}
					if (p.getPowerLevel() >= 3){
						p.setFirecooldown(200);
					}
					break;
				}
				for(Bullet e : bullets){
					e.init(gc);
				}
			}
		}

		//BULLET GARBAGE COLLECTION
		for(int i = 0; i < bullets.size(); i++){
			if(bullets.get(i).y <= 20 || bullets.get(i).y >= GameWindow.SCREEN_HEIGHT - 20 || bullets.get(i).x <= 20 || bullets.get(i).x >= GameWindow.SCREEN_WIDTH - 20){
				bullets.remove(i);
			}
		}
		
		//Call each entity's update method
		for(Enemy e : enemy){
			e.update(gc, delta);
		}
		
		for(Player p : players){
			p.update(gc, delta);
		}
		
		//Update the bullets
		for (Bullet e : bullets){
			e.update(gc, delta);
		}
		
		//CHECK FOR COLLISIONS
		//Check each player
		for(Player p : players){
			for(Enemy e : enemy){
				
				//Iff the entity can cause harm to the player, check for a collision
				if(e.isDangerous()){
					if(p.hitTest(e)){
						p.onCollide(e);
					}
				}
			}
		}
		
		for(Bullet b : bullets){
			if(!b.isDangerous()){
				for(Enemy e : enemy){
					if(e.isDangerous()){
						if(e.hitTest(b)){
							D.BUG("Bullet collided!");
							e.onCollide(b);
							toRemove.add(e);				//Adds entities to be removed to a new arraylist. Erasing an arraylist that's currently running in a loop will cause a crash.
							toRemoveBullets.add(b); 		//Adds bullets to be removed to a new arraylist. Erasing an arraylist that's currently running in a loop will cause a crash.
							point = e.PointValue();			//Gets point value for enemy that was just destroyed
							PointTotal += point;			//Adds point value from enemy destroyed to point total
							
						}
					}
				}
				
				for (Enemy e : toRemove) {
					enemy.remove(e);
				}	
				toRemove.clear();				//Clears the eraser arraylist to streamline for next iteration of code
				
			}
		}
		for (Bullet s : toRemoveBullets){
			bullets.remove(s);
		}
		toRemoveBullets.clear();
		
	}
	
	
	

}


