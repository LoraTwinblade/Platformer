package Entity.Doodad;

import GameState.MainMap.MainMap;
import Main.Content;
import TileMap.TileMap;

public class Waterfall extends Doodad
{
	public Waterfall
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
				192,
				192,
				192,
				192,
				0,
				0,
				true,
				true,
				false,
				false,
				false,
				0,
				"Waterfall",
				""
			);
	}
	public void setDoodad(int currentAction)
	{
		sprites = Content.Waterfall[0];
	}
}
