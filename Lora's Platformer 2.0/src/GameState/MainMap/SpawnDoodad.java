package GameState.MainMap;

import Entity.Doodad.Bomb;
import Entity.Doodad.EmotionBubble;
import Entity.Doodad.RainDrop;
import Entity.Doodad.SummoningEffect;
import Entity.Doodad.Torch;
import Entity.Doodad.Waterfall;
import Entity.Doodad.Activatable.CampFire;
import Entity.Doodad.Activatable.Chest;
import Entity.Doodad.Activatable.DoodadData;
import Entity.Doodad.Activatable.Door;
import Entity.Doodad.Activatable.Lever;
import Entity.Doodad.Activatable.Sign;
import Entity.Doodad.Activatable.StatueSave;

public class SpawnDoodad 
{

	protected MainMap mainMap;
	
	public SpawnDoodad(MainMap mainMap)
	{
		this.mainMap = mainMap;
	}
	
	
	public void spawnWaterfall(double locationX, double locationY)
	{
		Waterfall waterfall = new Waterfall(mainMap.tileMap, mainMap, locationX, locationY);
		mainMap.stuff.add(waterfall);
		
	}
	
	public void spawnRainDrop(double locationX, double locationY)
	{
		RainDrop rainDrop = new RainDrop(mainMap.tileMap, mainMap, locationX, locationY);
		mainMap.stuff.add(rainDrop);
	}
	
	public void spawnSummonEffect(double locationX, double locationY)
	{
		SummoningEffect summoningEffect = new SummoningEffect(mainMap.tileMap, mainMap, locationX, locationY);
		mainMap.stuff.add(summoningEffect);
	}
	
	public void spawnEmotionBubble(double locationX, double locationY, DoodadData.EmotionBubbles emotionBubbleType)
	{
		EmotionBubble emotionBubble = new EmotionBubble(mainMap.tileMap, mainMap, locationX, locationY, emotionBubbleType);
		mainMap.stuff.add(emotionBubble);
	}
	
	public void spawnTorch(double locationX, double locationY)
	{
		Torch torch = new Torch(mainMap.tileMap, mainMap, locationX, locationY);
		mainMap.stuff.add(torch);
	}
	
	public Bomb spawnBomb(double locationX, double locationY)
	{
		Bomb bomb = new Bomb(mainMap.tileMap, mainMap, locationX, locationY);
		mainMap.stuff.add(bomb);
		mainMap.activatables.add(bomb);
		return bomb;
	}
	
	public CampFire spawnCampFire(double locationX, double locationY)
	{
		CampFire campFire = new CampFire(mainMap.tileMap, mainMap, mainMap.getGameStateManager(), locationX, locationY);
		mainMap.stuff.add(campFire);
		mainMap.activatables.add(campFire);
		return campFire;
	}
	
	public void spawnSign(double locationX, double locationY, String[] conversation, int[] whoTalks)
	{
		Sign activatableSign = new Sign(mainMap.tileMap, mainMap, locationX, locationY, mainMap.player, conversation, whoTalks);
		mainMap.activatables.add(activatableSign);
		mainMap.stuff.add(activatableSign);
	}
	
	public Door spawnDoor(double locationX, double locationY, boolean locked, String uniqueID, int currentAction, String doorType)
	{
		Door door = new Door(mainMap.tileMap, mainMap, mainMap.getGameStateManager(), locationX, locationY, locked, uniqueID, currentAction, doorType);
		if(currentAction == 2)
		{
			door.setActive(true);
			door.setUsed(true);
		}
		mainMap.activatables.add(door);
		mainMap.alterableDoodads.add(door);
		mainMap.stuff.add(door);
		return door;
	}
	
	public Lever spawnLever(double locationX, double locationY, String uniqueID, int currentAction)
	{
		Lever lever = new Lever(mainMap.tileMap, mainMap, mainMap.getGameStateManager(), locationX, locationY, uniqueID, currentAction);
		mainMap.activatables.add(lever);
		mainMap.stuff.add(lever);
		mainMap.alterableDoodads.add(lever);
		return lever;
	}
	
	public Chest spawnChest(double locationX, double locationY, boolean locked, int currentAction, String chestType)
	{
		Chest chest = new Chest(mainMap.tileMap, mainMap, locationX, locationY, locked, currentAction, chestType);
		if(currentAction == 2)
		{
			chest.setActive(true);
			chest.setUsed(true);
		}
		
		mainMap.activatables.add(chest);
		mainMap.alterableDoodads.add(chest);
		mainMap.stuff.add(chest);
		return chest;
	}
	

	
	public void spawnStatueSave(double locationX, double locationY)
	{
		StatueSave statueSave = new StatueSave(mainMap.tileMap, mainMap, locationX, locationY + 10, mainMap.getGameStateManager());
		mainMap.activatables.add(statueSave);
		mainMap.stuff.add(statueSave);
	}
}
