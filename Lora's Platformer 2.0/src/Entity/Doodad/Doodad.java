package Entity.Doodad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import TileMap.TileMap;
import Entity.MapObject;
import Entity.Doodad.Activatable.DoodadData;
import Entity.Player.Player;
import GameState.MainMap.MainMap;

public class Doodad extends MapObject
{
	
	protected MainMap mainMap;
	
	protected String spritePath;
	
	protected int[] animationState;
	protected int[] numFrames;
	protected int delay;
	
	protected BufferedImage[] portrait;
	
	protected BufferedImage[] sprites;
	
	protected boolean runOnce;
	protected boolean activatable;
	protected boolean active;
	protected boolean spent;
	
	protected boolean locked;
	
	protected boolean hasPlayedOnce;
	
	protected int stopAt;
	
	protected String doodadType;
	protected String doodadName;
	protected String uniqueID;
	
	public Doodad
		(
			TileMap tileMap,
			MainMap mainMap,
			double spawnLocationX, 
			double spawnLocationY,
			int width,
			int height,
			int collisionWidth,
			int collisionHeight,
			double fallSpeed,
			double maxFallSpeed,
			boolean untouchable,
			boolean invulnerable,
			boolean runOnce,
			boolean activatable,
			boolean active,
			int currentAction,
			String doodadType,
			String descriptionName
		)
	{
		super(tileMap);
		
		this.mainMap = mainMap;
		
		this.width = width;
		this.height = height;
		this.untouchable = untouchable;
		this.invulnerable = invulnerable;
		this.runOnce = runOnce;
		this.activatable = activatable;
		this.active = active;
		this.collisionHeight = collisionHeight;
		this.collisionWidth = collisionWidth;
		this.fallSpeed = fallSpeed;
		this.maxFallSpeed = maxFallSpeed;
		this.doodadType = doodadType;
		this.doodadName = descriptionName;
		this.uniqueID = "";		
		this.locationX = spawnLocationX;
		this.locationY = spawnLocationY;
		this.spawnLocationX = spawnLocationX;
		this.spawnLocationY = spawnLocationY;
		
		facingRight = false;
		
		delay = 70;
		
		setPosition(spawnLocationX, spawnLocationY);
		this.currentAction = currentAction;
		setDoodad(currentAction);
		animation.setFrames(sprites);
		animation.setDelay(delay);		
	}
	
	public void setDoodad(int currentAction) { }
	
	public String getUniqueID()
	{
		return uniqueID;
	}
	
	public BufferedImage[] getPortrait() { return portrait; }
	
	public BufferedImage[] getSprites() { return sprites; }
	
	public void interact(Player player)
	{
//		if(!active) active = true;
	}
	
	public void playSound() { }
	
	public boolean getActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
	
	public boolean getFacingRight() { return facingRight; }
	public boolean getUntouchable() { return untouchable; }
	public boolean getInvulnerable() { return invulnerable; }
	public boolean getUnkillable() { return unkillable; }
	public String getDoodadType() { return doodadType; }
	public double getSpawnLocationX() { return spawnLocationX; }
	public double getSpawnLocationY() { return spawnLocationY; }
	public int getCurrentAction() { return currentAction; }
	public boolean getLocked() { return locked; }
	
	public void setCurrentAction(int currentAction) { this.currentAction = currentAction; }
	
	public void setDoodadType(String newDoodadType)
	{
		doodadType = newDoodadType;
	}
	
	
	public String getDoodadName()
	{
		return doodadName;
	}
	
	public boolean getHasPlayedOnce() { return hasPlayedOnce; }
	
	public void update()
	{		
		getNextPosition();
		try
		{
			checkTileMapCollision();
		}
		catch(Exception exception)
		{
			System.out.println("locationX: " + locationX + ", locationY: "+ locationY);
			System.out.println("Crash at: " + doodadName);
			exception.printStackTrace();
		}
		
		setPosition(xtemp, ytemp);

		hasPlayedOnce = (animation.getFrame() == animation.getFramesLength() - 1);
		
		// I'll change this to work with any toggle-able object later (assuming I ever add another one).
		if(doodadType.equals(DoodadData.Other.Lever.toString()))
		{
			if(currentAction == 1)
			{
				if(hasPlayedOnce)
				{
					hasPlayedOnce = false;
					setDoodad(2);
					mainMap.useDoodad(this);
				}
			}
			else if(currentAction == 3)
			{
				if(hasPlayedOnce)
				{
					hasPlayedOnce = false;
					setDoodad(0);
					mainMap.useDoodad(this);
				}
			}
		}
		
		if(!spent)
		{
			if(active)
			{
				if(currentAction!= 1)
				{
					hasPlayedOnce = false;
					currentAction = 1;
					setDoodad(currentAction);
					animation.setFrames(sprites);
					animation.setDelay(delay);
					playSound();
				}
				
				if(hasPlayedOnce)
				{
					if(currentAction != 2)
					{
						hasPlayedOnce = false;
						currentAction = 2;
						setDoodad(currentAction);
						animation.setFrames(sprites);
						animation.setDelay(delay);
						spent = true;
					}
				}
			}
		}
		
		animation.update();
		
		if(hasPlayedOnce && runOnce)
		{
			removeMe = true;
		}	
	}
		
	
	public void draw(Graphics2D graphics)
	{
		if(removeMe)
		{
			return;
		}
		
		try
		{
			super.draw(graphics);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
}
