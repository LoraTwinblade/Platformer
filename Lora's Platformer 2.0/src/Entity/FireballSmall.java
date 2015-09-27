package Entity;



import java.awt.Graphics2D;

import Entity.Doodad.FireballSmallExplosion;
import GameState.MainMap;
import Main.Content;
import TileMap.TileMap;

public class FireballSmall extends Projectile
{
	protected FireballSmallExplosion fireballSmallExplosion;
	
	public FireballSmall(
			TileMap tileMap,
			MainMap mainMap,
			boolean right, 
			boolean up, 
			boolean down,
			double angle,
			boolean friendly,
			int damage
		)
	{
		
		super(
				tileMap, 															//Tile map
				mainMap,
				right, 																// Facing right
				up,  																// Holding up arrow
				down,  																// Holding down arrow
				angle,
				friendly, 															// Whether the spell is friendly or hostile
				45, 																// Projectile width
				45, 																// Projectile height
				45, 																// Explosion width
				45, 																// Explosion height
				30, 																// Collision width
				30, 																// Collision height
				5, 																	// Projectile speed
				damage,																// Explosion damage
				20,
				30, 																// Explosion radius
				"FireballSmall"														// Explosion sound
			);
	}
	
	public void setProjectile()
	{
		sprites = Content.FireballSmall[0];
	}
	
		public void explode(TileMap tilemap, double x, double y)
		{
			fireballSmallExplosion = new FireballSmallExplosion(tileMap, x, y);
		}
		
		public void updateExplosion()
		{
			if(fireballSmallExplosion != null)
			{
				fireballSmallExplosion.animation.update();
				if(fireballSmallExplosion.animation.hasPlayedOnce())
				{
					fireballSmallExplosion.removeMe();
					remove = true;
				}
			}
		}
		
		public void drawExplosion(Graphics2D graphics)
		{
			fireballSmallExplosion.draw(graphics);
		}
}
