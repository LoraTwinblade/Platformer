package Entity.Doodad.Activatable;

import TileMap.TileMap;
import Audio.JukeBox;
import Entity.Doodad.Doodad;

public class ActivatableChestUncommon extends Doodad
{

	public ActivatableChestUncommon(
			TileMap tileMap, 
			double spawnX,
			double spawnY
			) 
	{
		super(tileMap, 
				spawnX, 
				spawnY, 
				60, 
				60,
				60,
				60,
				"/Sprites/Doodads/ChestUncommon.png", 
				new int[] {0,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0},
				new int[] {1, 4, 1},
				false, 
				true, 
				false,
				true,
				false
				);
		System.out.println("New chest at: " + spawnX + ", " + spawnY);

	}
	
	public void activateSound() 
	{ 
		JukeBox.play("OpenChestUncommon");
	}
	
}