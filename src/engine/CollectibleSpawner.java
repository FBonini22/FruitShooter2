package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Utilities.D;
import entities.Collectible;
import entities.CollectibleType;
import gameStates.Engine;
import view.GameWindow;

public class CollectibleSpawner{
	
	//CONSTANTS
	private final int HEALTH_FREQUENCY = 36000;						//Frequency in ms between health spawns
	private final int POWERUP_FREQUENCY = 28000;					//Frequency in ms between powerup spawns
	private final int POINTS_FREQUENCY = 100000000;				    //DO NOT USE. PLACEHOLDER VARIABLE
	private final int BOMB_FREQUENCY = 36000;						//Frequency in ms between bomb spawns

	
	private final float RANDOM_DEVIATION = 0.1f;					//Randomness deviation is set to 10%
	
	//INSTANCE VARIABLES
	
	//List of time since last spawn. Ordinal of CollectibleType enum is used 
	//to keep track of these values
	private List<Integer> timeSinceLastSpawns = new ArrayList<Integer>();
	
	//THIS LIST IS HARD-CODED.
	//Order of elements must be identical to CollectibleType enum
	private final List<Integer> frequencyValues = new ArrayList<Integer>(){{
		add(HEALTH_FREQUENCY);
		add(POWERUP_FREQUENCY);
		add(POINTS_FREQUENCY);
		add(BOMB_FREQUENCY);
		
	}};
	
	private Random rand = new Random();
	
	public void init(GameContainer gc) throws SlickException{
		
		//Initialize the timeSinceLastSpawns list
		for(CollectibleType t : CollectibleType.values()){
			timeSinceLastSpawns.add(0);
		}
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException{
		
	}
	
	public void update(GameContainer gc, int delta){
		
		
		for(int i = 0; i < timeSinceLastSpawns.size(); i++){
			timeSinceLastSpawns.set(i, timeSinceLastSpawns.get(i) + delta);
		}		

	
		//For each collectible type, run the random generation algorithm
		for(CollectibleType t : CollectibleType.values()){
			
			//If the CollectibleType enum is larger than the amount of frequency values, break out of this loop to
			//avoid game crash
			if(t.ordinal() >= frequencyValues.size()){
				break;
			}
			
			int currentFrequency = frequencyValues.get(t.ordinal());
			
			//Generate a random number
			int randGen = rand.nextInt((int)((float)currentFrequency * RANDOM_DEVIATION));
			randGen = (rand.nextBoolean()) ?-randGen :randGen;
			
			//D.BUG("RandGen is: " + String.valueOf(randGen));
			
			//If enough time has elapsed within the random deviation range, spawn a collectible
			if(timeSinceLastSpawns.get(t.ordinal()).intValue() > (currentFrequency + randGen)){
				
				//D.BUG("We have Spawned another health collectible after " + String.valueOf(randGen + HEALTH_FREQUENCY) + " milliseconds!");
				timeSinceLastSpawns.set(t.ordinal(), 0);
				
				//SPAWN COLLECTIBLE HERE
				switch(t){
				case Health:
					break;
				case PowerUp:
					break;
				case Points:
					break;
				case Bomb:
					
					Engine.instance.addEntity(new Collectible(rand.nextInt(GameWindow.SCREEN_WIDTH), rand.nextInt(GameWindow.SCREEN_HEIGHT),
							64, 64, t));
					break;
				}
			}
		}

		
		//These methods are obsolete. But keep them for reference
//		GenerateHealth();
//		GenerateBomb();
//		GeneratePowerUp();

		

		
		
		
	}
	
	/**
	 * Method for randomly generating health collectibles. Call this ONCE during the update method.
	 */
	private void GenerateHealth(){		
		
		//Update for the health powerup
		int randGen = rand.nextInt((int)((float)HEALTH_FREQUENCY * RANDOM_DEVIATION));
		randGen = (rand.nextBoolean()) ?-randGen :randGen;
		
		//D.BUG("RandGen is: " + String.valueOf(randGen));
		
		if(timeSinceLastSpawns.get(CollectibleType.Health.ordinal()).intValue() > (HEALTH_FREQUENCY + randGen)){
			
			//D.BUG("We have Spawned another health collectible after " + String.valueOf(randGen + HEALTH_FREQUENCY) + " milliseconds!");
			timeSinceLastSpawns.set(CollectibleType.Health.ordinal(), 0);
			
			//SPAWN HEALTH COLLECTIBLE HERE
		}
	}
	
	/**
	 * Method for randomly generating bomb collectibles. Call this ONCE during the update method
	 */
	private void GenerateBomb(){
		
		//Update for the bomb powerup
		int randGen = rand.nextInt((int)((float)BOMB_FREQUENCY * RANDOM_DEVIATION));
		randGen = (rand.nextBoolean()) ?-randGen :randGen;
		

		
		if(timeSinceLastSpawns.get(CollectibleType.Bomb.ordinal()).intValue() > (BOMB_FREQUENCY + randGen)){
			

			timeSinceLastSpawns.set(CollectibleType.Bomb.ordinal(), 0);
			
			//SPAWN BOMB COLLECTIBLE HERE
		}
	}
	private void GeneratePowerUp(){
		
		int randGen = rand.nextInt((int)((float)POWERUP_FREQUENCY * RANDOM_DEVIATION));
		randGen = (rand.nextBoolean()) ?-randGen :randGen;
		

		
		if(timeSinceLastSpawns.get(CollectibleType.PowerUp.ordinal()).intValue() > (POWERUP_FREQUENCY + randGen)){
			
			timeSinceLastSpawns.set(CollectibleType.PowerUp.ordinal(), 0);
			
			//SPAWN POWERUP COLLECTIBLE HERE
		}
	}
	

}
