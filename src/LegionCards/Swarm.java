package LegionCards;

import java.awt.Color;

public class Swarm extends Card {

	// Swarms: Get +1 to all ranks for each allied swarm on field

	private int[] cardStats1 = new int[] { 7, 3, 3, 3 };
	private int[] cardStats2 = new int[] { 3, 7, 3, 3 };
	private int[] cardStats3 = new int[] { 3, 3, 7, 3 };
	private int[] cardStats4 = new int[] { 3, 3, 3, 7 };
	private int[][] classStats = new int[][] { cardStats1, cardStats2, cardStats3, cardStats4 };

	public Swarm(Color pTeamColor) {
		super(pTeamColor);
	}

	public Swarm(int[] pStats, Color pTeamColor) {
		super(pStats, pTeamColor);
	}

	@Override
	public void bePlayed() {
		sendAdjustToAll();
		attack(true);
	}

	private void sendAdjustToAll() {
		// Passe alle stats von allen Swarms auf dem Spielfeld an
		for (CardSocket[] sockets : getSpielfeld()) {
			for (CardSocket socket : sockets) {
				if (!socket.isEmpty()) {
					Card card = socket.getCard();
					if (card instanceof Swarm) {
						((Swarm) card).adjustStats();
					}
				}
			}
		}
	}

	private void adjustStats() {
		stats = originalStats.clone();
		upgrade(getSwarmCount() - 1);
	}

	@Override
	public void convert() {
		super.convert();
		sendAdjustToAll();
	}

	private int getSwarmCount() {
		int thisTeamSwarms = 0;

		for (CardSocket[] sockets : getSpielfeld()) {
			for (CardSocket socket : sockets) {
				if (!socket.isEmpty())
					if (socket.getCard().getTeam().equals(getTeam()))
						if (socket.getCard() instanceof Swarm)
							thisTeamSwarms++;
			}
		}
		return thisTeamSwarms;
	}

	@Override
	public int[][] getClassStats() {
		return classStats;
	}

	@Override
	public String getBezeichner() {
		return "Swarms";
	}

	@Override
	public Card clone() {
		return new Swarm(stats.clone(), new Color(getTeam().getRGB()));
	}
}
