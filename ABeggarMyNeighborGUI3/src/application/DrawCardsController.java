package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;

import backend.Card;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DrawCardsController implements Initializable{
	

	@FXML
	private Rectangle centerCard, compCard, playersCard, deck;
	@FXML
	private Button buttonLeafCenterDeck, buttonHeartCenterDeck, buttonHeartArrowCenterDeck, buttonDiamondCenterDeck;
	@FXML
	private Button play, deal, flip, helpButton, flipCounter;
	@FXML
	private Label winnerLabel, flipCardLabel, centerDeckTopNumber, centerDeckBottomNumber, labelStatus, cpuDeckSize, playerDeckSize;

	
	private Stage popupwindow = new Stage();
	private Image image = new Image("/images/deck.jpg",false);

	//testing 
	private PauseTransition wait = new PauseTransition(Duration.seconds(1));

	//instances for decks
	private ArrayList<Card> deckOfCards = new ArrayList<Card>(52);
	private Deque<Card> playerDeck = new LinkedList<Card>();
	private Deque<Card> cpuDeck = new  LinkedList<Card>();
	private Deque<Card> centerDeck = new LinkedList<Card>();
	
	Random rand = new Random();
	Card playerCard = new Card(0, "");
	Card cpuCard = new Card(0, "");
	boolean playerTurn = false, cpuTurn = false;
	boolean counter = false;
	int turnDecider = rand.nextInt(2);
	int loop = 0;
	int cardRank;
	int winner;


	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		hideBeginning();
		addDeckImages();
		dealCards();
		
		System.out.println(playerDeck.size());
		System.out.println(cpuDeck.size());
		

		
	}


	@FXML
	public void handlePlay(ActionEvent event) {
		helpButton.setVisible(false);
		play.setVisible(false);
		deal.setVisible(true);
		deck.setVisible(true);
	}
	@FXML
	public void handleDeal(ActionEvent event) {
		deal.setVisible(false);
		deck.setVisible(false);
		compCard.setVisible(true);
		playersCard.setVisible(true);
		if(playerTurn) {
			flip.setVisible(true);
			flipCardLabel.setVisible(true);
		}else {
			computerFlip();
		}
	}
	
	@FXML
	public void handleFlip(ActionEvent event) {
			flip.setVisible(false);
			labelStatus.setText("");
			centerCard.setVisible(true);
			hideCenterDeckGraphics();
			
			while(playerTurn) {	
				
				

					
				playerCard = playerDeck.peek();						//put try catch here later, if no card in deck the catch the exception and change winnerDecided to true
				
				
				
				
				if(playerCard == null) {							//if card is null then break from while loop and decide the winner
					playerTurn = false;
					cpuTurn = false;
					labelStatus.setText("No cards in deck to draw from!");
					winner = 1;
					winnerDecided();
					break;
				}
					
					
					
					///////////////////////////
						while(counter) {
							cardRank = cpuCard.getRank();				//get rank of the opponents to determine how many tries are allowed to counter face card
							computeLoop(cardRank);
							hideCenterDeckGraphics();
						///here implement a flip button to pause code and wait for flip

							
							if(playerCard.getRank() > 10) {
								//if the card is a face card break out of loop and go to if player card is face card
								counter = false;
								
								
							}else if(playerCard.getRank() <= 10) {
								for(int i = 0; i < loop-1; i++) {
									hideCenterDeckGraphics();

									playerCard = playerDeck.peek();
									if(playerCard == null) {							//if card is null then break from while loop and decide the winner
										playerTurn = false;
										cpuTurn = false;
										labelStatus.setText("No cards in deck to draw from!");
										System.out.println("Player has no cards in deck to draw from");
										winner = 1;
										winnerDecided();
										break;
									}else if(playerCard.getRank() > 10) {
										centerDeck.addFirst(playerDeck.remove());
										counter = false;
										pickCenterDeckGraphic(playerCard.getSuit());
										setCardNumber(playerCard.getRank());
										labelStatus.setText("A face card has been drawn to trump previous face card!");
										System.out.println("Player drew a face card!");

										break;
									}else if(playerCard.getRank() <= 10) {
										centerDeck.addFirst(playerDeck.remove());
										pickCenterDeckGraphic(playerCard.getSuit());
										setCardNumber(playerCard.getRank());
										labelStatus.setText("A face card was not drawn ");
										System.out.println("Player did not draw face card");
									}
								}
								counter = false;

							}

							if(playerCard.getRank() <= 10 && playerCard != null) {                            //if OG card is still not a face card then give opponent the center deck

								labelStatus.setText("Player could not counter the face card so the center deck will be added to CPU's Deck");
								cpuDeck.addAll(centerDeck);
								centerDeck.clear();
								
								counter = false;
							}
						
						}//end of while loop 
					////////////////////////////////
						
						
						
					if(playerCard.getRank() > 10) {					
						cardRank = playerCard.getRank();				//get rank of the opponents to determine how many tries are allowed to counter face card
						computeLoop(cardRank);
						counter = true;
						labelStatus.setText("Player has played a face card! " +cardRank + " CPU gets "+loop + " flips to counter them!");
						System.out.println("Player has played a face card! " +cardRank + " CPU gets "+loop + " flips to counter them!");
						
						centerDeck.addFirst(playerDeck.remove());
						pickCenterDeckGraphic(playerCard.getSuit());
						setCardNumber(playerCard.getRank());
						showCenterDeckNumbers();
					}
					
					if(!counter) {
					centerDeck.addFirst(playerDeck.remove());
					pickCenterDeckGraphic(playerCard.getSuit());
					setCardNumber(playerCard.getRank());
					showCenterDeckNumbers();
					}
					System.out.println("\t\t\t\t\tPlayerCard: " + playerCard);
					System.out.println("\t\t\t\t\tPlayer Deck Size: " + playerDeck.size());
					
					
					
					playerTurn = false;
					cpuTurn = true;
				}
		playerDeckSize.setText("Deck Size: " + playerDeck.size());
		
		computerFlip();

	}

	public void computerFlip() {
		labelStatus.setText("");
		centerCard.setVisible(true);
		hideCenterDeckGraphics();
		
		while(cpuTurn) {	
			
			

			
			cpuCard = cpuDeck.peek();						//put try catch here later, if no card in deck the catch the exception and change winnerDecided to true
			
			
			
			
			if(cpuCard == null) {							//if card is null then break from while loop and decide the winner
				playerTurn = false;
				cpuTurn = false;
				labelStatus.setText("No cards in deck to draw from!");
				winner = 0;
				winnerDecided();
				break;
			}
				
				
				
				///////////////////////////
					while(counter) {
						cardRank = playerCard.getRank();				//get rank of the opponents to determine how many tries are allowed to counter face card
						computeLoop(cardRank);
						hideCenterDeckGraphics();
					///here implement a flip button to pause code and wait for flip

						
						if(cpuCard.getRank() > 10) {
							//if the card is a face card break out of loop and go to if player card is face card
							counter = false;
							
							
						}else if(cpuCard.getRank() <= 10) {
							for(int i = 0; i < loop-1; i++) {
								hideCenterDeckGraphics();

								cpuCard = cpuDeck.peek();
								if(cpuCard == null) {							//if card is null then break from while loop and decide the winner
									playerTurn = false;
									cpuTurn = false;
									labelStatus.setText("No cards in deck to draw from!");
									System.out.println("Player has no cards in deck to draw from");
									winner = 0;
									winnerDecided();
									break;
								}else if(cpuCard.getRank() > 10) {
									centerDeck.addFirst(cpuDeck.remove());
									counter = false;
									pickCenterDeckGraphic(cpuCard.getSuit());
									setCardNumber(cpuCard.getRank());
									labelStatus.setText("A face card has been drawn to trump previous face card!");
									System.out.println("Player drew a face card!");

									break;
								}else if(cpuCard.getRank() <= 10) {
									centerDeck.addFirst(cpuDeck.remove());
									pickCenterDeckGraphic(cpuCard.getSuit());
									setCardNumber(cpuCard.getRank());
									labelStatus.setText("A face card was not drawn ");
									System.out.println("Player did not draw face card");
								}
							}
							counter = false;

						}

						if(cpuCard.getRank() <= 10 && cpuCard != null) {                            //if OG card is still not a face card then give opponent the center deck

							labelStatus.setText("CPU could not counter the face card so the center deck will be added to Player's Deck");
							playerDeck.addAll(centerDeck);
							centerDeck.clear();
							
							counter = false;
						}
					
					}//end of while loop 
				////////////////////////////////
					
					
					
				if(cpuCard.getRank() > 10) {					
					cardRank = cpuCard.getRank();				//get rank of the opponents to determine how many tries are allowed to counter face card
					computeLoop(cardRank);
					counter = true;
					labelStatus.setText("CPU has played a face card! " + "Player gets "+loop + " flips to counter them!");
					System.out.println("CPU has played a face card! " +cardRank + " Player gets "+loop + " flips to counter them!");
					
					centerDeck.addFirst(cpuDeck.remove());
					pickCenterDeckGraphic(cpuCard.getSuit());
					setCardNumber(cpuCard.getRank());
					showCenterDeckNumbers();
				}
				
				if(!counter) {
				centerDeck.addFirst(cpuDeck.remove());
				pickCenterDeckGraphic(cpuCard.getSuit());
				setCardNumber(cpuCard.getRank());
				showCenterDeckNumbers();
				}
				System.out.println("\t\t\t\t\tCPU Card: " + cpuCard);
				System.out.println("\t\t\t\t\tCPU Deck Size: " + cpuDeck.size());
				
				
				
				playerTurn = true;
				cpuTurn = false;
			}	
				
				
				
		cpuDeckSize.setText("Deck Size: " + cpuDeck.size());
		
		
		givePlayerTurn();

	}
	
	
	public void givePlayerTurn() {
		flip.setVisible(true);					//turn goes to player after computer is done
		flipCardLabel.setVisible(true);
	}
	public void computeLoop(int rank) {
		switch(rank) {
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
		
	}


	private void winnerDecided() {
		hideBeginning();
		labelStatus.setVisible(false);
		flip.setVisible(false);
		flipCardLabel.setVisible(false);
		cpuDeckSize.setVisible(false);
		playerDeckSize.setVisible(false);
		if(winner == 0) {
			winnerLabel.setText("Player Wins!!");
			winnerLabel.setVisible(true);
		}else if(winner == 1) {
			winnerLabel.setText("CPU wins!!");
			winnerLabel.setVisible(true);

		}
	}

	public void setCardNumber(int rank) {
		centerDeckTopNumber.setText(Integer.toString(rank));
		centerDeckBottomNumber.setText(Integer.toString(rank));

	}
	

	public void hideCenterDeckGraphics() {
		buttonHeartCenterDeck.setVisible(false);
		buttonHeartArrowCenterDeck.setVisible(false);
		buttonDiamondCenterDeck.setVisible(false);
		buttonLeafCenterDeck.setVisible(false);
	}

	public void hideCenterDeckNumbers(){
		centerDeckTopNumber.setVisible(false);
		centerDeckBottomNumber.setVisible(false);
	}
	
	public void showCenterDeckNumbers(){
		centerDeckTopNumber.setVisible(true);
		centerDeckBottomNumber.setVisible(true);
	}
	
	public void pickCenterDeckGraphic(String suit) {
		switch(suit) {
		case "Hearts":
			buttonHeartCenterDeck.setVisible(true);
			break;
		case "Spades":
			buttonHeartArrowCenterDeck.setVisible(true);
			break;
		case "Diamonds":
			buttonDiamondCenterDeck.setVisible(true);
			break;
		case "Clubs":
			buttonLeafCenterDeck.setVisible(true);
			break;
		}//end of switch statement
	}

	
	@FXML
	public void handleHelpButton(ActionEvent event) {
		popupwindow.setTitle("How to Play");
		Label label= new Label("\tStart the game by flipping the card and putting it in the middle of the table. If the card has a rank of 2 to 10, the next person of CPU will flip their card and place it in the middle.\r\n" + 
				"\r\n" + 
				"\tWhen a face card or an Ace is flipped up, the next player must pay an \"honor\" according to the following:\r\n" + 
				"[•]\tIf an Ace is played, the next player must turn over four cards, one at a time.\r\n" + 
				"[•]\tIf a King is played, the next player must turn over three cards, one at a time.\r\n" + 
				"[•]\tIf a Queen is played, the next player must turn over two cards, one at a time.\r\n" + 
				"[•]\tIf a Jack is played, the next player must turn over one card.\r\n\n" + 
				"\tIf all of the cards in the honor are number cards, the player who played the court card collects all of the cards in the middle of the table. However, if one of the cards in the honor is a court card, the player paying the honor stops immediately and the turn goes to the next player and they must pay an honor based on the protocol above. If that honor is paid with only number cards, the cards in the middle of the table are collected by the last player who played a court card.\r\n" + 
				"\r\n" + 
				"\tThis continues until one player wins the pile. That player then puts the pile at the bottom of his or her stack, face down. If a player runs out of cards then they lose!\r\n" + 
				"\r\n" + 
				"\t\t\t\tWhoever runs out of cards first loses!");
		VBox layout= new VBox(10);
		layout.getChildren().add(label);
		label.setContentDisplay(ContentDisplay.TOP);
		label.setAlignment(Pos.CENTER);
		label.setWrapText(true);
		Scene scene= new Scene(layout, 400, 450);
		popupwindow.setResizable(false);
		popupwindow.setScene(scene);
		popupwindow.showAndWait();
	}


	public void dealCards() {
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
		
		switch(turnDecider) {
		
		case 0:
			playerTurn = true;
			break;
		case 1:
			cpuTurn = true;
			break;
		
		}
	}

	public void addDeckImages() {
		deck.setFill(new ImagePattern(image));
		playersCard.setFill(new ImagePattern(image));
		compCard.setFill(new ImagePattern(image));;
	}
	
	public void hideBeginning() {

		compCard.setVisible(false);
		playersCard.setVisible(false);
		centerCard.setVisible(false);
		deck.setVisible(false);
		
		winnerLabel.setVisible(false);
		flipCardLabel.setVisible(false);
		
		centerDeckTopNumber.setVisible(false);
		centerDeckBottomNumber.setVisible(false);

		hideCenterDeckGraphics();
		
		deal.setVisible(false);
		flip.setVisible(false);
		flipCounter.setVisible(false);
	}
	
}
