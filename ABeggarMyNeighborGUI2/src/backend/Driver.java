package backend;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
//DriverV4 Works perfectly, does not need player to flip for computer

public class Driver{

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException {
		
		ArrayList<Card> deckOfCards = new ArrayList<Card>(52);
		Deque<Card> playerDeck = new LinkedList<Card>();
		Deque<Card> cpuDeck = new  LinkedList<Card>();
		Deque<Card> centerDeck = new LinkedList<Card>();
		
		
		String inputFlip;
		Scanner scan = new Scanner(System.in);
		
		Random rand = new Random();
		Card playerCard = new Card(0, "");
		Card cpuCard = new Card(0, "");
		boolean playerTurn = false, cpuTurn = false;
		boolean counter = false;
		boolean winnerDecided = false;
		
		for(int j=1; j<14; j++)							//add Cards to deckOfCards
			deckOfCards.add(new Card(j, "Spades"));
		for(int j=1; j<14; j++)
			deckOfCards.add(new Card(j, "Hearts"));
		for(int j=1; j<14; j++)
			deckOfCards.add(new Card(j, "Diamonds"));
		for(int j=1; j<14; j++)
			deckOfCards.add(new Card(j, "Clubs"));
		
		Collections.shuffle(deckOfCards);				//shuffle the deck
		
		for(int i = 0; i<deckOfCards.size(); i++) {		//split the deck 
			if(i%2 == 0) {								//if i mod 2 = 0
				playerDeck.add(deckOfCards.get(i));		//then add card to playerDeck
			}else {										//if i mod 2 does not equal 0
				cpuDeck.add(deckOfCards.get(i));		//then add card to computerDeck
			}
		}
		
		int turnDecider = rand.nextInt(2);
		switch(turnDecider) {
		
		case 0:
			playerTurn = true;
			break;
		case 1:
			cpuTurn = true;
			break;
		
		}
		
		//initializing all the above
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		
		System.out.println("Player Deck Size: " + playerDeck.size());
		System.out.println("CPU Deck Size: " + cpuDeck.size());
		System.out.println("Turn decider 0 = player, 1 = CPU: " + turnDecider);
		
		
		while(!winnerDecided) {
	
			
				while(playerTurn) { 									//while player turn is true
					System.out.println("\n\n\t\t\t\t\tPlayer Turn --------------------------------------------------");
					
					
					int loop = 0;
					int cardRank = cpuCard.getRank();				//get rank of the opponents to determine how many tries are allowed to counter face card
							switch(cardRank) {
							case 11:
								loop = 1;
								break;
							case 12:
								loop = 2;
								break;
							case 13:
								loop = 3;
								break;
							case 14:
								loop = 4;
								break;
							}
							
							
							
					if(!counter) {
						System.out.println("press f to flip");
						inputFlip = scan.next();
						}else {
							System.out.println("CPU has played a face card! " +cardRank + " You get "+loop+ " flips to counter them!");
						}
					playerCard = playerDeck.peek();						//put try catch here later, if no card in deck the catch the exception and change winnerDecided to true

					if(playerCard == null) {							//if card is null then break from while loop and decide the winner
						winnerDecided = true;					
						playerTurn = false;
						cpuTurn = false;
						System.out.println("No cards in deck to draw from!");
						break;
					}
					
					
					while(counter) {
						System.out.println("to counter the face card, press f to flip !");
						inputFlip = scan.next();
						
						
						if(playerCard.getRank() > 10) {
							centerDeck.addFirst(playerDeck.remove());
							counter = false;
							break;
						}else if(playerCard.getRank() <= 10) {
							for(int i = 0; i < loop-1; i++) {
								
								playerCard = playerDeck.peek();
								if(playerCard == null) {							//if card is null then break from while loop and decide the winner
									winnerDecided = true;					
									playerTurn = false;
									cpuTurn = false;
									System.out.println("No cards in deck to draw from!");

									break;
								}else if(playerCard.getRank() > 10) {
									centerDeck.addFirst(playerDeck.remove());
									counter = false;
									System.out.println("A face card has been drawn to trump previous face card!" + centerDeck.peek());

									break;
								}else if(playerCard.getRank() <= 10) {
									centerDeck.addFirst(playerDeck.remove());
									System.out.println("A face card was not drawn " + centerDeck.peek());
								}
								
								System.out.println("to counter the face card, press f to flip !");
								inputFlip = scan.next();
							}
							counter = false;

						}
						
						if(playerCard.getRank() <= 10) {                            //if OG card is still not a face card then give opponent the center deck
                            System.out.println("Could not counter the face card so these cards: " + centerDeck + "\nWill be added to CPU's Deck");
                            cpuDeck.addAll(centerDeck);
                            centerDeck.clear();
                            counter = false;
                        }
						
					}//end of while loop 

					
					
					///
					
					if(playerCard.getRank() > 10) {					//if current card is face card, counter is true
						counter = true;
					}
					
					
					centerDeck.addFirst(playerDeck.remove()); 		//adding card from current deck to center deck
					
					
					System.out.println("\t\t\t\t\t[[[Player Card: " + playerCard);
					
				
					
					playerTurn =false;
					cpuTurn = true;
					
				}//end of while counter is true loop
					
					

				while(cpuTurn) { //while cpu turn is true
					System.out.println("\n\n\t\t\t\t\tCPU Turn --------------------------------------------------");
					
					int loop = 0;
					int cardRank = playerCard.getRank();				//get rank of the opponents to determine how many tries are allowed to counter face card
							switch(cardRank) {
							case 11:
								loop = 1;
								break;
							case 12:
								loop = 2;
								break;
							case 13:
								loop = 3;
								break;
							case 14:
								loop = 4;
								break;
							}
			
					
					cpuCard = cpuDeck.peek();						//put try catch here later, if no card in deck the catch the exception and change winnerDecided to true

					if(cpuCard == null) {							//if card is null then break from while loop and decide the winner
						winnerDecided = true;					
						playerTurn = false;
						cpuTurn = false;
						break;
					}
					//
					
					
				

					while(counter) {
						
						if(cpuCard.getRank() > 10) {
							centerDeck.addFirst(cpuDeck.remove());
							counter = false;
							break;
						} else if(cpuCard.getRank() <= 10) {
							for(int i = 0; i <loop;i++) {
								
								cpuCard = cpuDeck.peek();
								if(cpuCard == null) {							//if card is null then break from while loop and decide the winner
									winnerDecided = true;					
									playerTurn = false;
									cpuTurn = false;
						            Thread.sleep(1000);
									System.out.println("CPU has no cards in deck to draw from!");
									break;
								}else if(cpuCard.getRank() > 10) {
									centerDeck.addFirst(cpuDeck.remove());
									counter = false;
						            Thread.sleep(1000);
									System.out.println("CPU has drawn a face card to trump players face card!" + centerDeck.peek());
									break;
								}else if(cpuCard.getRank() <= 10) {
									centerDeck.addFirst(cpuDeck.remove());
						            Thread.sleep(1000);
									System.out.println("CPU has not drawn a face card " + centerDeck.peek());

								}
								
								
								
							}
							counter = false;
						}
						
						if(cpuCard.getRank() <= 10) {                            //if OG card is still not a face card then give opponent the center deck
                            System.out.println("Could not counter the face card so these cards: " + centerDeck + "\nWill be added to player's Deck");
                            playerDeck.addAll(centerDeck);
                            centerDeck.clear();
                            counter = false;
                        }								
					}//end of while loop 
					

					
					//
					if(cpuCard.getRank() > 10) {					//if current card is face card, counter is true
						counter = true;
					}
					
					
					centerDeck.addFirst(cpuDeck.remove()); 		//adding card from current deck to center deck
					
					
					System.out.println("\t\t\t\t\t[[[CPU Card: " + cpuCard);
					
					cpuTurn = false;
					playerTurn =true;

				}
					
		}
		
			
		
		if(playerDeck.isEmpty()) {
			System.out.print("CPU is the Winner");
		}else{
			System.out.print("Player is the Winner");

		}//end of if else statement // prints out winner
		
		
	}//end of main 	
	
	
	
	
	
}//end of driver


































