package Entity.Doodad;

import Main.Content;
import TileMap.TileMap;

public class ElectricBallChargeUp extends Doodad
{

	public ElectricBallChargeUp(
			TileMap tileMap, 
			double spawnX,
			double spawnY
			) 
	{
		super(
				tileMap, 
				spawnX, 
				spawnY, 
				60, 
				60,
				0,
				0,
				0,
				0,
				true, 
				true, 
				true,
				false,
				false,
				0,
				"ElectricballChargeUp"
				);
	}
	public void setDoodad(int currentAction)
	{
		sprites = Content.ElectricBallChargeUp[0];
	}
}