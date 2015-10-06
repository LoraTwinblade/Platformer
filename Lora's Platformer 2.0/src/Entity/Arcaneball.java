package Entity;

import java.awt.Graphics2D;

import Entity.Doodad.ElectricballExplosion;
import GameState.MainMap;
import Main.Content;
import TileMap.TileMap;

public class Arcaneball extends Projectile
{
	protected ElectricballExplosion electricballExplosion;
	
	public Arcaneball(
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
				96, 																// Projectile width
				96, 																// Projectile height
				60, 																// Explosion width
				60, 																// Explosion height
				80, 																// Collision width
				60, 																// Collision height
				5, 																	// Projectile speed
				damage,																// Explosion damage
				50,
				96, 																// Explosion radius
				"Electricball"														// Explosion sound
			);
	}
	
	public void setProjectile()
	{
		sprites = Content.Arcaneball[0];
	}
	
		public void explode(TileMap tilemap, double x, double y)
		{
			electricballExplosion = new ElectricballExplosion(tileMap, x, y);
		}
		
		public void updateExplosion()
		{
			if(electricballExplosion != null)
			{
				electricballExplosion.animation.update();
				if(electricballExplosion.animation.hasPlayedOnce())
				{
					electricballExplosion.removeMe();
					remove = true;
				}
			}
		}
		
		public void drawExplosion(Graphics2D graphics)
		{
			electricballExplosion.draw(graphics);
		}
}