package Entity.Doodad;

import Audio.JukeBox;
import Entity.Explosion.BombExplosion;
import GameState.MainMap.MainMap;
import Main.Content;
import TileMap.TileMap;

public class Bomb extends Doodad
{

	protected double start;
	
	public Bomb
		(
			TileMap tileMap,
			MainMap mainMap,
			double spawnLocationX,
			double spawnLocationY
		) 
	{
		super
			(
				tileMap,
				mainMap, 
				spawnLocationX, 
				spawnLocationY, 
				64, 
				128, 
				64, 
				128, 
				0.3,
				8, 
				true, 
				false, 
				false, 
				true, 
				false, 
				0, 
				"Bomb",
				""
			);

	}

	public void setDoodad(int currentAction)
	{
		sprites = Content.Bomb[0];
	}
	
	public void activateBomb()
	{
		currentAction = 1;
		hasPlayedOnce = false;
		delay = 500;
		sprites = Content.BombExploding[0];
		animation.setFrames(sprites);
		animation.setDelay(delay);
		active = true;
		start = System.currentTimeMillis();
		JukeBox.play("BombBurning");
	}
	
	public void update()
	{
		super.update();


		
		if(active)
		{
			if(hasPlayedOnce)
			{
				JukeBox.play("BombExploding");
				mainMap.addExplosion(new BombExplosion(tileMap, mainMap, null, locationX, locationY - height / 2, false));
				removeMe = true;
			}
		}
		
		if(locationX > tileMap.getWidth() || locationX < 0 || locationY < 0 || locationY > tileMap.getHeight())
		{
			removeMe = true;
		}
	}
}