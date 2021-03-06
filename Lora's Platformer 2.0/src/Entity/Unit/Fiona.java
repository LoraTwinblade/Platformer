package Entity.Unit;

import java.util.ArrayList;
import TileMap.TileMap;
import Audio.JukeBox;
import Entity.Player.Player;
import Entity.Projectile.ArcaneBall;
import GameState.Conversation.ConversationData;
import GameState.Maps.FionasSanctum;
import Main.Content;

public class Fiona extends Unit
{
	protected int attackCooldown;
	protected int attackTimer;
	
	protected boolean arcaneBallMode;
	protected int arcaneBallMoving;
	protected int arcaneBallTimer;
	protected int arcaneBallCooldown;
	
	protected int moving = 0; // 0 don't move, 1 = move left, 2 = move right
	protected int leftX = 240;
	protected int rightX = 940;
	
	protected int upperY = 450;
	
	protected boolean isHit = false;

	protected boolean playerHit;
	protected boolean recovered;
	
	
	protected boolean defeated;
	
	protected Player player;
	
	protected ConversationData conversation;
		
	protected FionasSanctum fionasSanctum;
	
	public Fiona
		(
			TileMap tileMap,
			boolean facingRight,
			boolean friendly,
			boolean untouchable,
			boolean invulnerable,
			boolean unkillable,
			String name,
			double spawnLocationX,
			double spawnLocationY,
			FionasSanctum fionasSanctum,
			Player player
		) 
	{
		super
			(
				tileMap,  															// TileMap
				100, 	 															// Width
				100, 	 															// Height
				80, 	 															// Collision width
				80, 	 															// Collision height
				0.5, 	 															// Move speed
				5.0, 	 															// Max speed
				0.4, 	 															// stopSpeed
				0.3, 	 															// fallSpeed
				8.0, 	 															// maxFallSpeed
				-9.6, 	 															// jumpStart
				0.6, 	 															// stopJumpSpeed
				facingRight,														// facingRight
				true,  																// inControl
				500,		 														// health
				500, 		 														//maxHealth
				0,		 															// healthRegen
				100,	 															// stamina
				100, 	 															// maxStamina
				30,		 															// staminaRegen
				100,		 														// mana
				100,		 														// maxMana
				30,		 															// manaRegen
				2000,																// sightRange
				2000,
				0,	 	 															// punchCost
				0, 		 															// punchDamage
				0,	 	 															// punchRange
				0,		 															// dashCost
				2,		 															// dashDamage
				40,		 															// dashRange
				20, 	 															// dashSpeed
				"/Art/Sprites/Characters/Succubus.png",									// spritePath
				new int[] {0,0,0,0,1,2,0,0,1,2,1,2,3,0,0,0,0},						// animationStates
				new int[]{7, 2, 2, 1, 2, 0, 0, 0, 0},								// numImages
				new int[] { 200, 200, 200, 200, 125, 120, 100, 100, 100, 100, 100, 100, 500, 200, 200, 200, 200 }, // animationDelay
				0,																	// damageOnTouch
				friendly,															// friendly			
				untouchable,
				invulnerable,
				unkillable,
				false,
				name,
				"Fiona",
				spawnLocationX,
				spawnLocationY,
				fionasSanctum
			);
		this.player = player;
		this.fionasSanctum = fionasSanctum;
		attackTimer = 0;
		attackCooldown = 300;
				
		arcaneBallTimer = 30;
		arcaneBallCooldown = 30;
		
		flying = true;
		
		portrait = Content.PortraitSuccubus[0];
		
		setTennisPlayer(true);
	}
	
	public void hit(double damage, Unit owner)
	{
		if(dead || invulnerable || spawning || hidden)
		{
			return;
		}
		
		if(!stunned)
		{
			if(!owner.getName().equals(name) && damage != 9001)
			{
				return;
			}
		}
		
		health -= damage;
		if( health < 0)
		{
			health = 0;
		}
		
		if(health == 0 && !unkillable)
		{
			dead = true;
		}
		
		
		if(!isHit)
		{
			setStunned(5000);
			isHit = true;
		}
		
		playIsHit();
		inControl = false;
	}
	
	public void castArcaneBall()
	{
		double tempX = locationX;
		double tempY = locationY + 100;
		
		
		aim = Math.atan2(tempY - locationY, tempX - locationX);
		ArcaneBall arcaneBall = new ArcaneBall(tileMap, mainMap, this, facingRight, up, down, aim, friendly);
		arcaneBall.setPosition(locationX, locationY - 20);
		mainMap.addProjectile(arcaneBall);
	}
	
	public void update(ArrayList<Unit> characterList)
	{
		super.update(characterList);
		
	}
	
	public void updateAI(ArrayList<Unit> characterList)
	{

		// GANNONDORF TENNIS THIS SHIT!
		// She will hug the corners, channel up a new energy Ball spell,
		// it will go towards the player, she's immune to all other attacks,
		// bounce the Ball back to her until one of you fail!
		// She will also have another attack that you
		// can not bounce back, you need to avoid this attack,
		// this as to not make it too easy for the player as they need to move!
		
		//If the player moves to one corner, she moves to the other!
		
				
		if(isHit)
		{
			directionY += 0.1;

			if(!stunned)
			{
				isHit = false;
				playRecoverSound();
				flying = true;
			}
			
			if(health <= 0)
			{
				defeated = true;
				health = 1;
				fionasSanctum.setDefeated(true);
				
			}
			
			return;
		}
		
		if(!inControl || defeated) return;
		

		
		ArrayList<Unit> enemiesDetected = detectEnemy(characterList, false);
		
		if(enemiesDetected != null)
		{
			if(enemiesDetected.size() > 0)
			{
				Unit enemy = enemiesDetected.get(0);
				if(enemy.getLocationX() > locationX) facingRight = true;
				else facingRight = false;
				
				
				if(locationY > upperY)
				{
					directionY -= 0.1;
					untouchable = true;
					invulnerable = true;
				}
				else
				{
					
					if(directionY != 0)
					{
						directionY = 0;
						locationY = upperY;
						if(locationX > leftX)
						{
							moving = 1;
						}
						else 
						{
							moving = 2;
						}
					}
				}
				
				if(locationY < upperY)
				{
					locationY = upperY;
				}
			}

		}
		
		if(moving == 0 && directionY == 0)
		{
			untouchable = false;
			invulnerable = false;
		}
		
		if(moving == 1)
		{
			if(locationX >= leftX)
			{
				left = true;
			}
			else 
			{
				left = false;
				moving = 0;
			}
		}
		else if(moving == 2)
		{
			if(locationX <= rightX) right = true;
			else
			{
				right = false;
				moving = 0;
			}
		}
		
		
		if(arcaneBallMode && !stunned)
		{
			invulnerable = true;
			arcaneBallTimer++;
			
			if(arcaneBallTimer >= arcaneBallCooldown)
			{
				arcaneBallTimer = 0;
				
				castArcaneBall();
			}
			
			if(arcaneBallMoving == 0)
			{
				JukeBox.play("FionaChargeup02");
				if(locationX >= leftX)
				{
					left = true;
					arcaneBallMoving = 1;
				}
				else
				{
					left = false;
				}
				if(locationX <= rightX)
				{
					right = true;
					arcaneBallMoving = 2;
				}
			}
			else if(directionX == 0)
			{
				castArcaneBall();
				
				arcaneBallMode = false;
				arcaneBallMoving = 0;
				right = false;
				left = false;
				
				if(locationX > leftX) moving = 1;
				else moving = 2;
			}
		}
		

		if( !hidden )
		{
			if(player.getStunned() && !playerHit)
			{
				playHitSound();
				playerHit = true;
			}
			
			
			if((electricBall == null || electricBall.getHit()) && directionX == 0 && directionY == 0)
			{
				attackTimer++;
				if(attackTimer > attackCooldown)
				{	
					attackTimer = 0;
					playerHit = false;
					
					if(mainMap.RNG(1, 2) == 1)
					{
						tennisSuccessChance = 90;
						JukeBox.play("FionaChargeup01");
						this.setStunned(1000);
						arcaneBallMode = true;
						return;
					}
					
					electricBallCasting = true;
				}
			}
		}


		
	}
	
	

}
