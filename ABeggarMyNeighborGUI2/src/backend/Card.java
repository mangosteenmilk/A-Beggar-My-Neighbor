package backend;

public class Card {
	
	private int rank;
	private String suit;
	
	public Card(int rank, String suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	public int getRank() {
		return rank;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public String toString() {
		return rank + " " + suit;
	}
	
}
