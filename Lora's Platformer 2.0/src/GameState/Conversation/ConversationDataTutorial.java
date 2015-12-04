package GameState.Conversation;

public class ConversationDataTutorial 
{
	
	public ConversationDataTutorial()
	{
	
	}
	
	// Will contain all of the conversations within the Tutorial
	
	
	public String[] tutorialInstructions()
	{
		return new String[]
		{
/* 0 */		"Press E",
/* 1 */		"Use the arrow keys to move",
/* 2 */		"Press space to jump",
/* 3 */		"Explore the world",
/* 4 */		"Jump over the hole using the arrow keys to move and space to jump",
/* 5 */		"Press A to fire a small fireball",
/* 6 */		"Hold up or down to aim, then press S to fire a large fireball",
/* 7 */		"Press D to dash forward",
/* 8 */		"Press F to punch",
/* 9 */		"Defeat the enemy using A/S/D/F",
/* 10 */	"Enter the temple",
/* 11 */	"Move toward the edge, jump and hold down R to glide"
		};
	}
	
	public String[] tutorialWelcomeMessage = new String[]
	{
		"Greetings, and welcome to the tutorial! Please press E.",
		"Good! That's exactly how you progress a conversation! ",
		"During a conversation you are unable to move or act "
		+ "you must therefore end a conversation before you can "
		+ "start doing so.",
		"This is the last frame until this dialog ends, when "
		+ "it does, try moving around with the arrow keys."
	};
	
	public int[] tutorialWelcomeMessageWhoTalks = new int[]
	{
		4, 4, 4, 4
	};
	
	public String[] tutorialMoveMessage = new String[]
	{
		"Good job, now try jumping by pressing space!" 
	};
	
	public int[] tutorialMoveMessageWhoTalks = new int[]
	{
		4
	};
	
	public String[] tutorialJumpMessage = new String[]
	{
		"Nice one! Those are the basic movements you can do. "
		+ "Why don't you try exploring the world for a bit, see what we can find." 
	};
	
	public int[] tutorialJumpMessageWhoTalks = new int[]
	{
		4
	};
	
	public String[] tutorialHoleSmallMessage = new String[]
	{
		"Hold up, see that hole up ahead? Try jumping over it! ",
		"Shouldn't be too hard..."
	};
	
	public int[] tutorialHoleSmallMessageWhoTalks = new int[]
	{
		4, 0
	};
	
	public String[] tutorialHoleSmallPassedMessage = new String[]
	{
		"Good job!",
		"Hah, this is easy!"
	};
	
	public int[] tutorialHoleSmallPassedMessageWhoTalks = new int[]
	{
		4, 0
	};
	
	public String[] tutorialMovementMasteredMessage = new String[]
	{
		"Nice, looks like you have mastered the movement."
	};
	
	public int[] tutorialMovementMasteredMessageWhoTalks = new int[]
	{
		4
	};
	
	public String[] tutorialFireBallSmallMessage = new String[]
	{
		"Now what about attacks? Try pressing A"
	};
	
	public int[] tutorialFireBallSmallMessageWhoTalks = new int[]
	{
		4
	};
	
	public String[] tutorialFireBallLargeMessage = new String[]
	{
		"Excellent, that's a small fireball. See how the blue bar in the upper "
				+ "left corner dropped a bit? That's your mana bar, all spells "
				+ "consume mana.",
				"Don't worry though, it will regenerate back up automatically.",
				"Hey, why don't you try holding the up arrow whilst pressing S?"
	};
	
	public int[] tutorialFireBallLargeMessageWhoTalks = new int[]
	{
		4, 4, 4
	};

	public String[] tutorialDashMessage = new String[]
	{
		"Nice one, not only did you cast a large fireball which is more "
				+ "powerful, you also aimed it!", 
				"You can do the same thing with the small fireball, and you can "
				+ "even aim it downwards by holding down the down arrow.",
				"Play around with it a bit if you want, when you feel ready to "
				+ "proceed, press the D button."
	};
	
	public int[] tutorialDashMessageWhoTalks = new int[]
	{
		4, 4, 4
	};


	
	public String[] tutorialPunchMessage = new String[]
	{
		"That's a dash attack, as you could see it will allow you to dash "
				+ "forward, dealing damage to all enemies that you pass along the "
				+ "way!",
				"You might also have noticed that the yellow bar decreased as you "
				+ "used dash, that's the stamina bar, all your physical attacks "
				+ "drain your stamina.",
				"Don't worry though, just as with your mana, your stamina will "
				+ "regenerate automatically. For our final attack, try pressing F."
	};
	
	public int[] tutorialPunchMessageWhoTalks = new int[]
	{
		4, 4, 4
	};


	public String[] tutorialPunchDoneMessage = new String[]
	{
		"Simple, isn't it? "
				+ "The punch is quite useful if you get up close to your enemy to beat "
				+ "the living shit out of them."
	};
	
	public int[] tutorialPunchDoneMessageWhoTalks = new int[]
	{
		4
	};
	
	public String[] tutorialEnemySuccubusMessage = new String[]
	{
		"Watch out, it looks like an enemy has appeared right in front of you! "
				+ "Try using the attacks you just learnt to defeat it!"
	};
	
	public int[] tutorialEnemySuccubusMessageWhoTalks = new int[]
	{
		4
	};

	public String[] tutorialTempleEnterMessage = new String[]
	{
		"Good job!",
		"Did you hear that? It looks like the way forward just opened up. "
		+ "Why don't you enter the temple so see what awaits you inside?"
	};
	
	public int[] tutorialTempleEnterMessageWhoTalks = new int[]
	{
		4, 4
	};
	
	public String[] tutorialHoleLargeMessage = new String[]
	{
		"This hole is much larger than the last one...",
		"Yes, you would never be able to cross it with a normal jump.",
		"Fortunately, you also know how to glide through the air, "
		+ "whenever you are in the air, try holding down R to "
		+ "start gliding!"
	};
	
	public int[] tutorialHoleLargeMessageWhoTalks = new int[]
	{
		0, 4, 4
	};
	
	public String[] tutorialHoleLargePassedMessage = new String[]
	{
		"Great work!",
		"This is sadly the end of the tutorial at the moment."
	};
	
	public int[] tutorialHoleLargePassedMessageWhoTalks = new int[]
	{
		4, 4
	};

	
}