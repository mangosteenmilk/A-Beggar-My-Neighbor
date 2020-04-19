package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.ResourceBundle;

import backend.Card;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
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
	private Line pathTransitionC, pathTransitionP;
	@FXML
	private Rectangle centerDeckRight, centerDeckLeft, compCard, compTransitionCard,playerTransitionCard, playersCard, deck;
	@FXML
	private Button buttonHeartCenterDeck1, buttonLeafCenterDeck1, buttonHeartArrowCenterDeck1, buttonDiamondCenterDeck1;
	@FXML
	private Button buttonLeafCenterDeck, buttonHeartCenterDeck, buttonHeartArrowCenterDeck, buttonDiamondCenterDeck;
	@FXML
	private Button play, deal, flip, helpButton, flipCounter;
	@FXML
	private Label winnerLabel, flipCardLabel, centerDeckTopNumber1, centerDeckTopNumber, centerDeckBottomNumber1, centerDeckBottomNumber, labelStatus;
	
	private PathTransition pathP = new PathTransition();
	private PathTransition pathC = new PathTransition();

	private ParallelTransition parallelP = new ParallelTransition(playerTransitionCard, pathP);
	private ParallelTransition parallelC = new ParallelTransition(compTransitionCard, pathC);

	private Stage popupwindow = new Stage();
	private Image image = new Image("/application/deck.jpg",false);

	//testing 
	private PauseTransition wait = new PauseTransition(Duration.seconds(2));

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
		createPathP();
		createPathC();
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
			hideCenterDeckLeft();

			centerDeckLeft.setVisible(true);
			showCenterDeckLeftNumbers();
			while(playerTurn) {	
				
				cardRank = cpuCard.getRank();				//get rank of the opponents to determine how many tries are allowed to counter face card
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
							
				}else {
					labelStatus.setText("CPU has played a face card! " +cardRank + " You get "+loop+ " flips to counter them!");
				}
					
				playerCard = playerDeck.peek();						//put try catch here later, if no card in deck the catch the exception and change winnerDecided to true
				System.out.println("Player Deck. peek: " + playerDeck.peek() + " PlayerCard: " + playerCard);
				
				if(playerCard == null) {							//if card is null then break from while loop and decide the winner
					playerTurn = false;
					cpuTurn = false;
					labelStatus.setText("No cards in deck to draw from!");
					winner = 1;
					winnerDecided();
					break;
				}
					
					
					
					///////////////////////////
					//if counter is true
					
						while(counter) {
						///here implement a flip button to pause code and wait for flip
							
							
						playerTransitionCard.setVisible(true);		// to show flips 
						parallelP.play();

						
							if(playerCard.getRank() > 10) {
								centerDeck.addFirst(playerDeck.remove());
								counter = false;
								
							}else if(playerCard.getRank() <= 10) {
								for(int i = 0; i < loop-1; i++) {
									
									playerCard = playerDeck.peek();
									if(playerCard == null) {							//if card is null then break from while loop and decide the winner
										playerTurn = false;
										cpuTurn = false;
										labelStatus.setText("No cards in deck to draw from!");
										winner = 1;
										winnerDecided();
										playerTransitionCard.setVisible(false);		// to show flips 
										break;
									}else if(playerCard.getRank() > 10) {
										playerTransitionCard.setVisible(false);		// to show flips 
										centerDeck.addFirst(playerDeck.remove());
										counter = false;
										pickCenterDeckLeftGraphic(playerCard.getSuit());
										setCardNumberLeft(playerCard.getRank());
										labelStatus.setText("A face card has been drawn to trump previous face card!");

										break;
									}else if(playerCard.getRank() <= 10) {
										playerTransitionCard.setVisible(false);		// to show flips 
										centerDeck.addFirst(playerDeck.remove());
										pickCenterDeckLeftGraphic(playerCard.getSuit());
										setCardNumberLeft(playerCard.getRank());
										labelStatus.setText("A face card was not drawn ");
									}
								}
								counter = false;

							}

							if(playerCard.getRank() <= 10 && playerCard != null) {                            //if OG card is still not a face card then give opponent the center deck
								playerTransitionCard.setVisible(false);		// to show flips 
								hideCenterDeckRight();
								hideCenterDeckLeft();
								labelStatus.setText("Player could not counter the face card so the center deck will be added to CPU's Deck");
								cpuDeck.addAll(centerDeck);
								centerDeck.clear();
								
								counter = false;
							}
						
						}//end of while loop 
					////////////////////////////////
					if(playerCard.getRank() > 10) {					// if counter is true
						counter = true;
					}
					
					
					centerDeck.addFirst(playerDeck.remove());
					pickCenterDeckLeftGraphic(playerCard.getSuit());
					setCardNumberLeft(playerCard.getRank());
					
					System.out.println(playerCard.getRank() + playerCard.getSuit());
					
					
					
					playerTurn = false;
					cpuTurn = true;
				}
		computerFlip();
	}

	public void computerFlip() {
		centerDeckRight.setVisible(true);
		showCenterDeckRightNumbers();
		hideCenterDeckRightGraphics();

		labelStatus.setText("");
		while(cpuTurn) {	
			
			cardRank = cpuCard.getRank();				//get rank of the opponents to determine how many tries are allowed to counter face card
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
						
			}else {
				labelStatus.setText("CPU has played a face card! " +cardRank + " You get "+loop+ " flips to counter them!");
			}
				
			cpuCard = cpuDeck.peek();						//put try catch here later, if no card in deck the catch the exception and change winnerDecided to true
			System.out.println("CPU Deck. peek: " + cpuDeck.peek() + " CPUCard: " + cpuCard);
			
			if(cpuCard == null) {							//if card is null then break from while loop and decide the winner
				playerTurn = false;
				cpuTurn = false;
				labelStatus.setText("No cards in deck to draw from!");
				winner = 0;
				winnerDecided();
				break;
			}
				
				
				
				///////////////////////////
				//if counter is true
				
					while(counter) {
					///here implement a flip button to pause code and wait for flip
					
						if(cpuCard.getRank() > 10) {
							centerDeck.addFirst(cpuDeck.remove());
							counter = false;
							
						}else if(cpuCard.getRank() <= 10) {
							for(int i = 0; i < loop-1; i++) {
								
								cpuCard = cpuDeck.peek();
								if(cpuCard == null) {							//if card is null then break from while loop and decide the winner
									playerTurn = false;
									cpuTurn = false;
									labelStatus.setText("No cards in deck to draw from!");
									System.out.println("No cards in deck to draw from!");
									winner = 0;
									winnerDecided();
									break;
								}else if(cpuCard.getRank() > 10) {
									centerDeck.addFirst(cpuDeck.remove());
									counter = false;
									pickCenterDeckRightGraphic(cpuCard.getSuit());
									setCardNumberRight(cpuCard.getRank());
									labelStatus.setText("A face card has been drawn to trump previous face card!");
									System.out.println("A face card has been drawn to trump previous face card!");
									break;
								}else if(cpuCard.getRank() <= 10) {
									centerDeck.addFirst(cpuDeck.remove());
									pickCenterDeckRightGraphic(cpuCard.getSuit());
									setCardNumberRight(cpuCard.getRank());
									labelStatus.setText("A face card was not drawn ");
									System.out.println("A face card was not drawn ");
								}
							}
							counter = false;

						}

						if(cpuCard.getRank() <= 10 && cpuCard != null) {                            //if OG card is still not a face card then give opponent the center deck
							labelStatus.setText("Could not counter the face card so the center deck will be added to CPU's Deck");
							System.out.println("Could not counter the face card so the center deck will be added to CPU's Deck");
							cpuDeck.addAll(centerDeck);
							centerDeck.clear();
							hideCenterDeckRight();
							hideCenterDeckLeft();
							counter = false;
						}
					
					}//end of while loop 
				////////////////////////////////
				if(cpuCard.getRank() > 10) {					// if counter is true
					counter = true;
				}
				
				
				centerDeck.addFirst(cpuDeck.remove());
				pickCenterDeckRightGraphic(cpuCard.getSuit());
				setCardNumberRight(cpuCard.getRank());
				
				System.out.println(cpuCard.getRank() + cpuCard.getSuit());
				
				
				
				playerTurn = true;
				cpuTurn=false;
			}
		
		flip.setVisible(true);					//turn goes to player after computer is done
		flipCardLabel.setVisible(true);
	}
	
	
	private void winnerDecided() {
		hideBeginning();
		
		if(winner == 0) {
			winnerLabel.setText("You are the Winner!!");
			winnerLabel.setVisible(true);
		}else if(winner == 1) {
			winnerLabel.setText("CPU wins!!");
			winnerLabel.setVisible(true);

		}
	}

	public void setCardNumberRight(int rank) {
		centerDeckTopNumber.setText(Integer.toString(rank));
		centerDeckBottomNumber.setText(Integer.toString(rank));

	}
	
	public void setCardNumberLeft(int rank) {
		centerDeckTopNumber1.setText(Integer.toString(rank));
		centerDeckBottomNumber1.setText(Integer.toString(rank));
	}
	
	public void hideCenterDeckRightGraphics() {
		buttonHeartCenterDeck.setVisible(false);
		buttonHeartArrowCenterDeck.setVisible(false);
		buttonDiamondCenterDeck.setVisible(false);
		buttonLeafCenterDeck.setVisible(false);
	}
	
	
	public void hideCenterDeckLeftGraphics() {
		buttonHeartCenterDeck1.setVisible(false);
		buttonHeartArrowCenterDeck1.setVisible(false);
		buttonDiamondCenterDeck1.setVisible(false);
		buttonLeafCenterDeck1.setVisible(false);
	}
	
	public void hideCenterDeckRightNumbers(){
		centerDeckTopNumber1.setVisible(false);
		centerDeckBottomNumber1.setVisible(false);
	}
	
	public void hideCenterDeckLeftNumbers(){
		centerDeckTopNumber.setVisible(false);
		centerDeckBottomNumber.setVisible(false);
	}
	
	public void showCenterDeckRightNumbers(){
		centerDeckTopNumber.setVisible(true);
		centerDeckBottomNumber.setVisible(true);
	}
	
	public void showCenterDeckLeftNumbers(){
		centerDeckTopNumber1.setVisible(true);
		centerDeckBottomNumber1.setVisible(true);
	}
	
	
	public void pickCenterDeckRightGraphic(String suit) {
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
	
	public void pickCenterDeckLeftGraphic(String suit) {
		switch(suit) {
		case "Hearts":
			buttonHeartCenterDeck1.setVisible(true);
			break;
		case "Spades":
			buttonHeartArrowCenterDeck1.setVisible(true);
			break;
		case "Diamonds":
			buttonDiamondCenterDeck1.setVisible(true);
			break;
		case "Clubs":
			buttonLeafCenterDeck1.setVisible(true);
			break;
		}//end of switch statement
	}

	public void hideCenterDeckRight() {
		hideCenterDeckRightGraphics();
		hideCenterDeckRightNumbers();
		playerTransitionCard.setVisible(false);		// to show flips 
		compTransitionCard.setVisible(false);		// to show flips 

	}
	
	public void hideCenterDeckLeft() {
		hideCenterDeckLeftGraphics();
		hideCenterDeckLeftNumbers();
		playerTransitionCard.setVisible(false);		// to show flips 
		compTransitionCard.setVisible(false);		// to show flips 
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
	
	private void createPathC() {
		pathC.setNode(compTransitionCard);
		pathC.setPath(pathTransitionC);
		pathC.setDuration(Duration.seconds(1));
		pathC.setAutoReverse(false);		
	}

	private void createPathP() {
		pathP.setNode(playerTransitionCard);
		pathP.setPath(pathTransitionP);
		pathP.setDuration(Duration.seconds(1));
		pathP.setAutoReverse(false);
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
		hideCenterDeckLeftGraphics();
		hideCenterDeckRightGraphics();
		
		centerDeckRight.setVisible(false);	//hide rectangles
		centerDeckLeft.setVisible(false);
		compCard.setVisible(false);
		playersCard.setVisible(false);
		compTransitionCard.setVisible(false);
		playerTransitionCard.setVisible(false);
		deck.setVisible(false);
		
		winnerLabel.setVisible(false);
		flipCardLabel.setVisible(false);
		
		centerDeckTopNumber.setVisible(false);
		centerDeckBottomNumber.setVisible(false);
		centerDeckTopNumber1.setVisible(false);
		centerDeckBottomNumber1.setVisible(false);
		
		deal.setVisible(false);
		flip.setVisible(false);
		flipCounter.setVisible(false);
	}
	
}
