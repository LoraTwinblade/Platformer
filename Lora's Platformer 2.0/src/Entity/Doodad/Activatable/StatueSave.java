package Entity.Doodad.Activatable;

import Audio.JukeBox;
import Entity.Doodad.Doodad;
import Entity.Player.CreateBuff;
import Entity.Player.Player;
import GameState.GameStateManager;
import GameState.Conversation.ConversationData;
import GameState.Conversation.ConversationState;
import GameState.MainMap.MainMap;
import Main.Content;
import Main.JSONWriter;
import TileMap.TileMap;

public class StatueSave extends Doodad
{

	protected Player player;
	
	protected ConversationData conversationData;
	
	protected ConversationState conversationBox;
	
	protected int choiceMade;
	
	protected GameStateManager gameStateManager;
	
	protected MainMap mainMap;
	
	public StatueSave
		(
			TileMap tileMap,
			MainMap mainMap,
			double spawnLocationX,
			double spawnLocationY,
			GameStateManager gameStateManager
		) 
	{
		super
			(
				tileMap,
				mainMap,
				spawnLocationX, 
				spawnLocationY, 
				116, 
				110,
				116,
				110,
				0.3, 
				8, 
				false, 
				true, 
				false,
				true,
				false,
				0,
				DoodadData.Other.StatueSave.toString(),
				DoodadData.doodadName.get(DoodadData.Other.StatueSave.toString())
				);
				
		
		this.spawnLocationX = spawnLocationX;
		this.spawnLocationY = spawnLocationY;
		this.gameStateManager = gameStateManager;
		this.mainMap = mainMap;
	}
	
	public void setDoodad(int currentAction)
	{
		sprites = Content.StatueSave[0];
		portrait = Content.PortraitStatueSave[0];
	}
	
	public void interact(Player player)
	{
		
		if(this.player == null)
		{
			this.player = player;
			this.conversationBox = player.getConversationState();
			this.conversationData = new ConversationData(player);
		}
		
		if(!player.getInConversation() && choiceMade == 0)
		{
			JukeBox.play("Restore");
			player.addBuff(CreateBuff.createBuff(CreateBuff.Buffs.RestoreHealth, 5, 100, player, sprites[0]));
			player.addBuff(CreateBuff.createBuff(CreateBuff.Buffs.RestoreMana, 5, 100, player, sprites[0]));
			player.addBuff(CreateBuff.createBuff(CreateBuff.Buffs.RestoreStamina, 5, 100, player, sprites[0]));
			player.getConversationState().startConversation(player, null, this, conversationData.statueSave(), conversationData.statueSaveWhoTalks());
			return;
		}
		
		if(player.getInConversation() && choiceMade == 0)
		{
			if(conversationBox.getConversationOver())
			{
				choiceMade = conversationBox.getChoiceMade();
				if(choiceMade == 1)
				{
					player.getConversationState().startConversation(player, null, this, conversationData.statueSaveChoiceYes(), conversationData.statueSaveChoiceYesWhoTalks());
					
					player.setSpawnLocationX(spawnLocationX);
					player.setSpawnLocationY(spawnLocationY);
					mainMap.saveToRAM();
					JukeBox.play("Save");
					JSONWriter.saveFile(player);
				}
				else
				{
					player.getConversationState().startConversation(player, null, this, conversationData.statueSaveChoiceNo(), conversationData.statueSaveChoiceNoWhoTalks());	
				}
				return;
			}
		}
		
		if(conversationBox.getConversationOver())
		{
			choiceMade = 0;
			conversationBox.endConversation();
		}
	}
}