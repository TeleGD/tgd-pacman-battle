package games.maze.players;

import app.AppPlayer;

import games.maze.Player;
import games.maze.World;

public class VictimPlayer extends Player {

	private boolean invulnerable;
	private boolean immaterial;

	public VictimPlayer (World w, AppPlayer aplayer) {
		super (w, aplayer);
		invulnerable = false;
		immaterial = false;
	}

	public void collideWithPlayer (Player p) {
		if (p instanceof HunterPlayer) {
			super.world.endGame (p);
		}
	}

}