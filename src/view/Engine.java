
package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Utilities.D;
import engine.CollectibleSpawner;
import entities.Bullet;
import entities.Collectible;
import entities.Enemy;
import entities.EnemyMovement;
import entities.EnemyType;
import entities.Entity;
import entities.FruitType;
import entities.Player;
import globals.Globals;

public class Engine extends BasicGame{

	//Public Static Player X and Player Y;
	public static float x;
	public static float y;
	
	//Constants
	private final int FRAME_RATE = 60;							//Frame rate in fps
	private final int NUMBER_OF_SQUIRRELS = 20;					//Number of squirrels. FOR DEBUGGING ONLY
	
	//Instance Variables
	private List<Enemy> enemies = new ArrayList<Enemy>();			//List of drawable entities
	private List<Player> players = new ArrayList<Player>();		//List of players
	private List<Bullet> bullets = new ArrayList<Bullet>();		//List of bullet entities
	private List<Collectible> collectibles = new ArrayList<Collectible>();		//List of collectibles
	private List<Entity> toRemove = new ArrayList<Entity>(); 	//List of entities to be removed
	private List<Entity> toAdd = new ArrayList<Entity>();		//List of entities to be added
	private int PointTotal;
	private Random randomGenerator = new Random();


	private List<Bullet> toRemoveBullets = new ArrayList<Bullet>();



	private boolean isInBossBattle = false;

	private boolean worldClipSet = false;						//Boolean for whether the WorldClip has been initialized

	private int point;

	private int currentWave = 0;								//Variable to keep track of the current wave of enemies
	private int currentLevel = 0;								//Variable to keep track of how many bosses have been defeated
	
	private boolean isDead = false;
	private boolean bossType = false;							//False is for boss type 1, true is for boss type 2
	
	private int time = 0;
	private int interval = 0;								//Time before another wave starts
	private int bossTimer = 0;
	private CollectibleSpawner collecSpawn = new CollectibleSpawner();
	private int Time = 0;
	private int multiplier = 1;
	//TESTING
	private Player p1 = new Player(FruitType.Banana, 1);
	
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
		

		for (int i = 0; i< NUMBER_OF_SQUIRRELS; i++){

			enemies.add(new Enemy(0, 0, EnemyType.Squirrel, Globals.GRUNT_WIDTH, Globals.GRUNT_HEIGHT, EnemyMovement.SliceToRight));
			enemies.add(new Enemy(0, 0, EnemyType.Squirrel, Globals.GRUNT_WIDTH, Globals.GRUNT_HEIGHT, EnemyMovement.SliceToLeft));
			enemies.add(new Enemy(0, 0, EnemyType.Squirrel, Globals.GRUNT_WIDTH, Globals.GRUNT_HEIGHT, EnemyMovement.VShoot));

	}

		//Music openingMenuMusic = new Music(""); //TODO Need to find and insert suitable music
    		//openingMenuMusic.loop(); 
		
		for(Enemy e : enemies){
			e.init(gc);
		}	
		
		for(Player p : players){
			p.init(gc);
		}
		
		
		collecSpawn.init(gc);
	}	
	
	/**
	 * Render graphics and entities here. This method is automatically called each time the 
	 * game updates itself
	 * @param gc The window/container in which the game is running
	 * @param g The graphics back-end of the running game. Use contained methods to update and draw graphics.
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		//Set world clipping
		//We want to utilize the Graphics parameter, so we will call the world
		//clipping function here, once. We cannot call it in the init method as far as I know.
		if(!worldClipSet){
			worldClipSet = true;
			g.setWorldClip(0, 0, GameWindow.SCREEN_WIDTH, GameWindow.SCREEN_HEIGHT);
		}
		
		
		//Initialize new entities that haven't been initialized
		initializeAddedEntities(gc);
		
		
		//Render background image
		Background.draw(0,0);
		
		//Draw the current point total
		g.drawString(String.format("Player Points: %1$s", String.valueOf((int)PointTotal)), 600, 32);
		
		g.drawString(String.format("Time Elapsed: %1$s", String.valueOf((int)time/1000)), 600, 64);
		
		g.drawString(String.format("Enemies: %1$s", String.valueOf((int)enemies.size())), 600, 94);
		
		

				
		
		//Call each entity's render method
		for(Enemy e : enemies){
			e.render(gc, g);
		}
		
		for(Bullet b : bullets){
			b.render(gc,  g);
		}
		
		for(Player p : players){
			p.render(gc, g);
		}
		
		for(Collectible c : collectibles){
			c.render(gc, g);
		}
	}

	/**
	 * Update graphics, entities, and game variables. This method is automatically
	 * called each time the game updates itself. Run the game logic here.
	 * @param gc The container in which the current game is running
	 * @param delta The time between each frame
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

		//Testing Purposes. Not sure how this will work for multiple players yet
		x = p1.x;
		y = p1.y;
		
		
		Time += delta;
		//D.BUG(Integer.toString(Time));
		if (Time > Globals.LevelChange){
			multiplier += 1;
			Time = 0;
			D.BUG("Multiplier increased");
		}	
		collecSpawn.update(gc, delta);
		
		//Call each entity's update method
		for(Enemy e : enemies){
			e.update(gc, delta);
		}
		
		for(Player p : players){
			p.update(gc, delta);
		}
		
		//Update the bullets
		for (Bullet e : bullets){
			e.update(gc, delta);
		}
		
		for(Collectible c : collectibles){
			c.update(gc, delta);
		}
		
		//CHECK FOR COLLISIONS
		//Check each player
		for(Player p : players){
			for(Enemy e : enemies){
				
				//Iff the entity can cause harm to the player, check for a collision
				if(e.isDangerous()){
					if(p.hitTest(e)){
						p.onCollide(e);		
						if(p.onDieCheck())			//Checks to see if the player is dead
							markForRemoval(p);		//If the player is dead it adds the player to the "toRemove" arraylist
					}
				}
			}
			
			for(Bullet b : bullets){
				if(b.isDangerous()){
					if(p.hitTest(b)){
						p.onCollide(b);
					}
				}
			}
			
			for(Collectible c : collectibles){
				if(p.hitTest(c)){
					p.onCollide(c);
				}
			}
		}
		
		for(Bullet b : bullets){
			if(!b.isDangerous()){
				for(Enemy e : enemies){
					if(e.isDangerous()){
						if(e.hitTest(b)){
							//D.BUG("Bullet collided!");
							isDead = e.onCollide(b);

							if (isDead){
								markForRemoval(e);						//Adds entities to be removed to a new arraylist. Erasing an arraylist that's currently running in a loop will cause a crash.
								p1.setPoints(e.PointValue());			//Gets point value for enemy that was just destroyed. TODO change for each player
							}
							p1.setPoints(e.PointValue());			//Gets point value for enemy that was just destroyed. TODO change for each player
							PointTotal = p1.getPoints();			//Adds point value from enemy destroyed to point total TODO add both players point values
							markForRemoval(b);
						}
					}
				}
				
			}
		}

		
		//REMOVE OLD OBJECTS
		dumpObjects();
		
		checkGameProgress(delta);
		
		time += delta;
		interval += delta;
		if (interval >= Globals.TIMER){
			Preset1();
			interval = 0;
		}
	}
	
	/**
	 * Method for checking the game progress. Checks whether player can progress to the next wave or boss.
	 * Call at the end of the update() method.
	 */
	private void checkGameProgress(float delta){
		time += delta;
		interval += delta;
		if (interval >= Globals.TIMER){
			Preset1();
			interval = 0;
		}
		bossTimer += delta;
		
		if (bossTimer>= Globals.BossTimer){
			generateBoss();
			bossTimer = 0;
		}
		//NEW WAVE!
		/*
		if(enemy.size() <= 0){
			D.BUG("No current enemies");
			D.BUG(Integer.toString(enemy.size()));
			if(isInBossBattle){
				//Just defeated a boss
				D.BUG("Boss defeated");
				isInBossBattle = false;
				currentLevel++;
			}
			else{
				generateEnemies();			
				D.BUG("Enemy Wave");
			}
			

		}
		*/
		
		
		
		
	}
	
	/**
	 * Generate new enemies. This should be called after a wave has been completed
	 */
	private void generateEnemies(){
		for (int i = 0; i< NUMBER_OF_SQUIRRELS; i++){

			toAdd.add(new Enemy(0, 0, EnemyType.Squirrel, Globals.GRUNT_WIDTH, Globals.GRUNT_HEIGHT, EnemyMovement.Random));

		}
		
	}
	
	/**
	 * Generate a boss enemy. This should be called when enough waves have been completed
	 * to unlock a boss battle.
	 */
	private void generateBoss(){
		
		int Spawn_X =  randomGenerator.nextInt(GameWindow.SCREEN_WIDTH-375);
		Enemy B;
		if(bossType){		
			B = new Enemy(Spawn_X, 0, EnemyType.JumboSquirrel_1, Globals.BOSS_HEIGHT1, Globals.BOSS_WIDTH1, EnemyMovement.Random); 	//Added Spawn_X and Spawn_Y to change the spawning location of the squirrels to random locations
			bossType = false;
		}
		else{
			B = new Enemy(Spawn_X, 0, EnemyType.JumboSquirrel_2, Globals.BOSS_HEIGHT2, Globals.BOSS_WIDTH2, EnemyMovement.Random); 	//Added Spawn_X and Spawn_Y to change the spawning location of the squirrels to random locations
			bossType = true;
		}
		
		toAdd.add(B);
	}
	
	
	
	/**
	 * Method for adding an entity to the current instance of the game engine
	 * @param e Entity to be added
	 */
	public void addEntity(Entity e){
		toAdd.add(e);
	}
	
	/**
	 * Method for initializing entities that were added to the engine post-initialization.
	 * This method should ONLY be called at the beginning of the Engine class's render() method
	 * @param gc The window/container in which the game is running
	 * @throws SlickException
	 */
	private void initializeAddedEntities(GameContainer gc) throws SlickException{
		
		
		for(Entity e : toAdd){
			
			//Initialize the new entity
			e.init(gc);
			
			String entityType = e.getClass().getSimpleName();
			
			//Add the entity to the appropriate list
			switch(entityType){
			case "PlayerBullet":
			case "EnemyBullet":
				bullets.add((Bullet) e);
				break;
			case "Collectible":
				collectibles.add((Collectible) e);
				break;
			case "Enemy":
			default:
					enemies.add((Enemy)e);	
			}
		}
		
		toAdd.clear();
	}
	
	/**
	 * Method to mark specific entity for removal
	 * @param e
	 */
	public void markForRemoval(Entity e){
		toRemove.add(e);
	}
	
	/**
	 * Method for clearing the game screen
	 */
	public void clearScreen(){
		//Clear enemies list
		enemies.clear();
		
		//Pick out dangerous bullets and clear them
		for(Bullet b : bullets){
			if(b.isDangerous()){
				markForRemoval(b);
			}
		}
	}
	
	/**
	 * Method for cleaning up entities marked for removal
	 */
	private void dumpObjects(){
		
		//Iterate through all entities marked for removal. Remove them from respective list
		for(Entity e : toRemove){
			
			switch(e.getClass().getSimpleName()){
			
			case "PlayerBullet":
			case "EnemyBullet":
				bullets.remove(e);
				break;
				
			case "Enemy":
				enemies.remove(e);
				break;
			case "Collectible":
				collectibles.remove((Collectible)e);
			}
			
		}
		
		//Clear entities marked for removal
		toRemove.clear();
	}
	
 private void Preset1(){
		generateEnemies();
		D.BUG("Preset1");
	}
	
}



