package Entity;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import Entity.Doodad.SummoningEffect;
import GameState.Inventory.Inventory;
import Main.GamePanel;
import TileMap.Tile;
import TileMap.TileMap;

public abstract class MapObject 
{
	
	// Tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double mapPositionX;
	protected double mapPositionY;
	
	protected boolean hidden;
	
	protected boolean rotaded;
	protected double rotation;
	
	protected boolean flying;
	protected boolean gliding;
	protected boolean spawning;
	protected boolean deSpawning;
	protected boolean initializeSpawning;
	protected boolean initializeDeSpawning;
	protected SummoningEffect summoningEffect;
	protected boolean inControl;
	
	// Position and vector
	protected double locationX;
	protected double locationY;
	protected double directionX;
	protected double directionY;
	
	protected double spawnLocationX;
	protected double spawnLocationY;
	
	// Dimensions
	protected int width;
	protected int height;
	
	// Collision box
	protected int collisionWidth;
	protected int collisionHeight;
	
	// Collision
	protected int currentRow;
	protected int currentCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected int topLeft;
	protected int topRight;
	protected int bottomLeft;
	protected int bottomRight;
	
	// Animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight = false;
	
	// Movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;	
	protected boolean jumping;
	protected boolean falling;
	protected boolean running;
	protected boolean inWater;
	
	// Movement attributes
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	protected double dashSpeed;
	protected double waterResistance = 1;
	protected double runSpeedMultiplier = 1.7;
	
	protected boolean untouchable; // Attacks ignore you.
	protected boolean invulnerable; // Attacks are able to hit you but you won't take damage.
	protected boolean unkillable; // Attacks can hit you and you can take damage, but you won't die.
	
	protected boolean removeOutsideScreen;
	protected boolean removeMe;
	
	protected Inventory inventory;
	
	public void setHidden(boolean b) { hidden = b; }
	public boolean getHidden() { return hidden; }
	
	// Constructor
	public MapObject(TileMap tileMap)
	{
		inventory = new Inventory(5, 1, null, this);
		
		if(tileMap == null) return;
		this.tileMap = tileMap;
		tileSize = tileMap.getTileSize();
		animation = new Animation();
		setPosition(locationX, locationY);		
	}
	
	public void setTileMap(TileMap tileMap)
	{
		this.tileMap = tileMap;
		tileSize = tileMap.getTileSize();
		animation = new Animation();
		setPosition(locationX, locationY);
	}
	
	public void setCollisionWidth(int i)
	{
		this.collisionWidth = i;
	}
	
	public void setCollisionHeight(int i)
	{
		this.collisionHeight = i;
	}
	
	public boolean intersects(MapObject mapObject)
	{
		if(untouchable || mapObject.untouchable) return false;
		Rectangle r1 = getRectangle();
		Rectangle r2 = mapObject.getRectangle();
				
		return r1.intersects(r2);
	}
	
	public boolean intersects(Rectangle rectangle)
	{		
		return getRectangle().intersects(rectangle);
	}
	
	public Rectangle getRectangle()
	{		
		return new Rectangle
			(
				(int)locationX - collisionWidth / 2,
				(int)locationY - collisionHeight / 2,
				collisionWidth,
				collisionHeight
			);
	}
	
	public void calculateCorners(double x, double y)
	{
		int leftTile 	= (int) (x - collisionWidth / 2) 		/ tileSize;
		int rightTile 	= (int) (x + collisionWidth / 2 - 1) 	/ tileSize;
		
		int topTile 	= (int) (y - collisionHeight / 2) 		/ tileSize;
		int bottomTile 	= (int) (y + collisionHeight / 2 - 1) 	/ tileSize;
		
		
		topLeft = tileMap.getType(topTile, leftTile);		
		topRight = tileMap.getType(topTile, rightTile);
		bottomLeft = tileMap.getType(bottomTile,leftTile);
		bottomRight = tileMap.getType(bottomTile, rightTile);		
	}
	
	
	protected void getNextPosition()
	{
		if(spawning || deSpawning)
		{
			directionX = 0;
			directionY = 0;
			return;
		}
		
		waterResistance = (inWater ? 0.4 : 1);
		
		// Movement
			if(left && inControl )
			{

				directionX -= moveSpeed * waterResistance;
				if(directionX < -maxSpeed * waterResistance)
				{
					directionX = -maxSpeed * waterResistance;
				}
			}
			else if(right && inControl)
			{

				directionX += moveSpeed * waterResistance;
				if(directionX > maxSpeed * waterResistance)
				{
					directionX = maxSpeed * waterResistance;
				}
			}
			else
			{
				if(directionX > 0)
				{
					directionX -= stopSpeed * waterResistance;
					if(directionX < 0)
					{
						directionX = 0;
					}
				}
				else if(directionX < 0)
				{
					directionX += stopSpeed * waterResistance;
					if(directionX > 0)
					{
						directionX = 0;
					}
				}
			}
			
			if(flying)
			{
				if(down)
				{
					directionY += moveSpeed;
					if(directionY > maxSpeed) directionY = maxSpeed; 
				}
				else if(up)
				{
					directionY -= moveSpeed;
					if(directionY*-1 > maxSpeed) directionY = maxSpeed*-1;
				}
				
				if(right)
				{
					directionX += moveSpeed;
					if(directionX > maxSpeed) directionX = maxSpeed;
				}
				else if(left)
				{
					directionX -= moveSpeed;
					if(directionX*-1 > maxSpeed) directionX = maxSpeed*-1;
				}
				
			}
			
			
			// Jumping
			if(jumping && (inWater || ( !falling && inControl && !flying ) ) )
			{
				if(directionY >= 0)
				{
					playJumpSound();
				}
				
				directionY = jumpStart * (inWater ? waterResistance + 0.1 : 1);
				falling = true;
			}
			
			
			
			// Falling
			if( (falling) && !flying)
			{
				if(directionY > 0 && gliding) directionY += (fallSpeed * 0.1) * waterResistance;
				else directionY += fallSpeed * waterResistance;
			
				if(directionY > 0) jumping = false;
				if(directionY < 0 && !jumping) directionY += stopJumpSpeed * waterResistance;
				
				if(directionY > maxFallSpeed * waterResistance) directionY = maxFallSpeed * waterResistance;
			}

	}
	
	public void emoteExclamation()
	{
	}
	
	public void playJumpSound() { }
	
	public void playCannotCarrySound() { }
	
	public void initializeSpawning()
	{
		initializeSpawning = true;
	}
	
	public void checkTileMapCollision() throws Exception
	{
		currentCol = (int)locationX / tileSize;
		currentRow = (int)locationY / tileSize;
		
		xdest = locationX + directionX;
		ydest = locationY + directionY;
		
		xtemp = locationX;
		ytemp = locationY;
		
		calculateCorners(locationX, ydest);
		
		try
		{
			Tile tile = tileMap.getTile((int)locationY / tileSize, (int)locationX / tileSize);
			
			if(tile == null)
			{
				if(removeOutsideScreen)
				{
					removeMe = true;
					return;
				}
				throw new Exception();
			}

			inWater = tile.isWater();
			
		}
		catch(Exception exception)
		{
			throw exception;
		}
		
		// We're going upwards
		if(directionY < 0)
		{
			// Since we're going upwards we have to check the top tiles
			if(topLeft == 1 || topRight == 1)
			{
				// If they are there then we have to stop going upwards (otherwise we'll move through the wall).
				directionY = 0;
				setHit();
				ytemp = currentRow * tileSize +collisionHeight / 2;
			}
			else
			{
				ytemp += directionY;
			}
		}
		
		// We're going downwards
		if(directionY > 0)
		{
			// Checking if we have landed on something
			if(bottomLeft == 1 || bottomRight == 1)
			{
				setHit();
				directionY = 0;
				falling = false;
				
				if(collisionHeight / 2 < tileSize)
				{
					ytemp = (currentRow + 1) * tileSize - collisionHeight / 2;
				}
			}
			else
			{  
				ytemp += directionY;
			}
		}
		
		calculateCorners(xdest, locationY);
		// Moving left;
		if(directionX < 0)
		{
			// If we've hit a block on our left side
			if(topLeft == 1 || bottomLeft == 1)
			{
				directionX = 0;
				xtemp = currentCol * tileSize + collisionWidth / 2;
			}
			else
			{
				xtemp += (running ? directionX * runSpeedMultiplier : directionX);
			}
		}
		
		// Moving right
		if(directionX > 0)
		{
			// If we've hit a block on the right side
			if(topRight == 1 || bottomRight == 1)
			{
				directionX = 0;
				xtemp = (currentCol + 1) * tileSize - collisionWidth / 2;
			}
			else
			{
				xtemp += (running ? directionX * runSpeedMultiplier : directionX);
			}
		}
		
		// If we are not falling
		if(!falling)
		{
			// Check the corners around the object, one tile down to see if we should start falling.
			calculateCorners(locationX, ydest + 1);
			
			// So if there is no block on the bottom left or right, then we're not standing on anything.
			if(bottomLeft != 1 && bottomRight != 1)
			{
				falling = true;
			}
		}
	}
	public void setHit() { }
	
	public boolean getInWater() { return inWater; }
	public int getLocationX() { return (int)locationX; }
	public int getLocationY() { return (int)locationY; }
	public int getLocationXOnScreen() { return (int) (locationX + mapPositionX); }
	public int getLocationYOnScreen() { return (int) (locationY + mapPositionY); }
	public int getWidth() { return (int)width; }
	public int getHeight() { return (int)height; }
	public int getCollisionWidth() { return (int)collisionWidth; }
	public int getCollisionHeight() { return (int)collisionHeight; }
	
	public Animation getAnimation() { return animation; }
	
	public Inventory getInventory() { return inventory; }
	
	public boolean getRemoveMe() { return removeMe; }

	
	public void setPosition(double x, double y)
	{
		// Regular position
		this.locationX = x;
		this.locationY = y;
	}
	
	public void setVetor(double directionX, double directionY)
	{
		this.directionX = directionX;
		this.directionY = directionY;
	}
	
	public void setMapPosition()
	{
		// Map position, if our player was at 2000, 1000, then he would be off the screen, we have to find out
		// how far the tilemap has moved in order to offset the player back on to the screen, and that's the final position,
		// x + mapPositionX, y + mapPositionY.
		mapPositionX = tileMap.getX();
		mapPositionY = tileMap.getY();
	}
	
	public void setLeft		(boolean left) 	{ this.left 	= left;		}
	public void setRight	(boolean right) { this.right 	= right;	}
	public void setUp		(boolean up)	{ this.up 		= up;		}
	public void setDown		(boolean down) 	{ this.down 	= down;		}
	
	public void setJumping(boolean jumping) 
	{ 
		running = false;
		this.jumping = jumping; 		
	}
	
	public void startRunning()
	{
		if(!falling)
		{
			running = true;
		}
	}
	
	public void setRunning(boolean running)
	{
		this.running = running;

	}
	
	public void draw(java.awt.Graphics2D graphics) throws Exception
	{
		try
		{
			setMapPosition();
			
			if(hidden) return;
			
			if(animation == null)
			{
				System.out.println("Animation was null");
				throw new Exception();
			}
			
			if(rotaded)
			{
				
				double rotationX = width / 2;
				double rotationY = height / 2;
				AffineTransform tx = AffineTransform.getRotateInstance(rotation + Math.PI, rotationX, rotationY);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
				
				
				graphics.drawImage
					(
						op.filter(animation.getImage(), null),
						(int)(locationX + mapPositionX - width / 2), 
						(int)(locationY + mapPositionY - height / 2),
						width,
						height,
						null
					);
				return;
				
			}
			
			if(facingRight)
			{
				graphics.drawImage
					(
						animation.getImage(),
						(int) (locationX + mapPositionX - width / 2 + width),
						(int) (locationY + mapPositionY - height / 2),
						-width,
						height,
						null
					);
	
			}
			else
			{
				if(animation.getImage() != null)
				{
					graphics.drawImage
					(
						animation.getImage(),
						(int) (locationX + mapPositionX - width / 2),
						(int) (locationY + mapPositionY - height / 2),
						width,
						height,
						null
					);
				}
			}
		}
		catch(Exception exception)
		{
			throw exception;
		}
	}
	
	
	// Since there is no point in drawing objects that are not on the screen, this
	// function will determine whether they even are on the screen.
	public boolean notOnScreen()
	{
		// Again, locationX + mapPositionX is the final position on the game screen itself.
		return 
				//If the object is beyond the left side of the screen
				locationX + mapPositionX + width < 0 ||
				// if the object is beyond the right side of the screen
				locationY + mapPositionX - width > GamePanel.WIDTH ||
				//if the object is above the screen
				locationY + mapPositionY + height < 0||
				//if the object is below the screen
				locationY + mapPositionY - height > GamePanel.HEIGHT;
				
	}
	
	
}
