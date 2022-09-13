package NineMensMorris;

//Kevin Yu & Yash Balwanti
//6-12-22
//Creates the game Nine Men's Morris and is a 2 player game. Take turns placing pieces and moving them until one player has only 2 pieces left

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class NineMensMorris implements ActionListener{
	
	JFrame frame = new JFrame();
	JButton[][] button = new JButton[7][7];
	int [][]board = new int[7][7];
	int [][]colorboard = new int[7][7];
	//The value of color on the board
	final int BLANK = 0;
	final int R_MOVE = 1;
	final int Y_MOVE = 2;
	
	//Keeps track of who's turn
	final int R_TURN = 0;
	final int Y_TURN = 1;
	int turn = R_TURN;
	final int three = 3;
	
	Container center = new Container();
	JButton yChangeName = new JButton("Change Yellow's Name.");
	JButton rChangeName = new JButton("Change Red's Name.");
	JTextField yChangeField = new JTextField();
	JTextField rChangeField = new JTextField();
	Container north = new Container();
	String yPlayerName = "Yellow";
	String rPlayerName = "Red";
	boolean remove = false;
	boolean select = false;
	
	//Holds the pins to place during phase 1
	int rplaced = 9;
	int yplaced = 9;
	
	//Holds the pins after phase 1
	int rpins = 9;
	int ypins = 9;
	
	//holds the selected values
	int holdx = 0;
	int holdy = 0;
	
	//18 turns in phase 1
	boolean phase1 = true;
	boolean phase2 = false;
	
	public NineMensMorris() {
	frame.setSize(900, 900);
	//Center grid container
	frame.setLayout(new BorderLayout());
	center.setLayout(new GridLayout(7,7));
	for (int i = 0; i < button.length; i++) {
		for (int j = 0; j < button[0].length; j++) {
			button[j][i] = new JButton();
			center.add(button[j][i]);
			board[j][i] = BLANK;
			button[j][i].addActionListener(this);
		}
	}
	//Blacking out the buttons that are "Dead Zones"
	button[0][1].setBackground(Color.DARK_GRAY);
	button[0][2].setBackground(Color.DARK_GRAY);
	button[0][4].setBackground(Color.DARK_GRAY);
	button[0][5].setBackground(Color.DARK_GRAY);
	button[1][0].setBackground(Color.DARK_GRAY);
	button[1][2].setBackground(Color.DARK_GRAY);
	button[1][4].setBackground(Color.DARK_GRAY);
	button[1][6].setBackground(Color.DARK_GRAY);
	button[2][0].setBackground(Color.DARK_GRAY);
	button[2][1].setBackground(Color.DARK_GRAY);
	button[2][5].setBackground(Color.DARK_GRAY);
	button[2][6].setBackground(Color.DARK_GRAY);
	button[3][3].setBackground(Color.DARK_GRAY);
	button[4][0].setBackground(Color.DARK_GRAY);
	button[4][1].setBackground(Color.DARK_GRAY);
	button[4][5].setBackground(Color.DARK_GRAY);
	button[4][6].setBackground(Color.DARK_GRAY);
	button[6][1].setBackground(Color.DARK_GRAY);
	button[6][2].setBackground(Color.DARK_GRAY);
	button[6][4].setBackground(Color.DARK_GRAY);
	button[6][5].setBackground(Color.DARK_GRAY);
	button[5][0].setBackground(Color.DARK_GRAY);
	button[5][2].setBackground(Color.DARK_GRAY);
	button[5][4].setBackground(Color.DARK_GRAY);
	button[5][6].setBackground(Color.DARK_GRAY);
	
	//Setting the playable sections to white
	button[0][0].setBackground(Color.WHITE);
	button[0][3].setBackground(Color.WHITE);
	button[0][6].setBackground(Color.WHITE);
	button[1][1].setBackground(Color.WHITE);
	button[1][3].setBackground(Color.WHITE);
	button[1][5].setBackground(Color.WHITE);
	button[2][2].setBackground(Color.WHITE);
	button[2][3].setBackground(Color.WHITE);
	button[2][4].setBackground(Color.WHITE);
	button[3][0].setBackground(Color.WHITE);
	button[3][1].setBackground(Color.WHITE);
	button[3][2].setBackground(Color.WHITE);
	button[3][4].setBackground(Color.WHITE);
	button[3][5].setBackground(Color.WHITE);
	button[3][6].setBackground(Color.WHITE);
	button[5][1].setBackground(Color.WHITE);
	button[5][3].setBackground(Color.WHITE);
	button[5][5].setBackground(Color.WHITE);
	button[6][0].setBackground(Color.WHITE);
	button[6][3].setBackground(Color.WHITE);
	button[6][6].setBackground(Color.WHITE);
	button[4][2].setBackground(Color.WHITE);
	button[4][3].setBackground(Color.WHITE);
	button[4][4].setBackground(Color.WHITE);


	frame.add(center, BorderLayout.CENTER);

	//North container
	north.setLayout(new GridLayout(3,2));
	north.add(rChangeName);
	rChangeName.addActionListener(this);
	north.add(yChangeName);
	yChangeName.addActionListener(this);
	north.add(rChangeField);
	north.add(yChangeField);
	frame.add(north, BorderLayout.NORTH);
	
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
	}

	public static void main(String[] args) {
		new NineMensMorris();
	}
	
	//checks if you selected a piece in a 3 group
	public boolean check3(int x, int y) {
		if (board[x][y] >= 3) {
			return true;
		}
		else {
			return false;
		}
	}
	
	int tracker = 0;
	
	//places the piece
	public void placePiece(int x, int y) {
		Color color = (button[x][y]).getBackground();
		if (color.equals(Color.DARK_GRAY)) {
			JOptionPane.showMessageDialog(frame, "Please do not place on blacked out tiles.");
		}
		else if (color.equals(Color.WHITE)) {
			if (turn == R_TURN) {
				if (rplaced != 0) {
					(button[x][y]).setBackground(Color.RED);
					rplaced -= 1;
					board[x][y] = R_MOVE;
					colorboard[x][y] = R_MOVE;
					boolean ate = eat(R_MOVE);
					if(ate == true) {
						remove = true;
					}
				
					turn = Y_TURN;
				}
			}
			else {
				if (yplaced != 0) {
					(button[x][y]).setBackground(Color.YELLOW);
					board[x][y] = Y_MOVE;
					yplaced -= 1;
					colorboard[x][y] = Y_MOVE;
					if(eat(Y_MOVE) == true) {
						remove = true;
					}
					if (yplaced == 0) {
						if (remove != true) {
							JOptionPane.showMessageDialog(frame, "End of Phase 1, on to Phase 2");
							phase2 = true;
							phase1 = false;
						}
					}
					turn = R_TURN;
				}
			}
		}
	}
	
	boolean removed = false;
	
	//removes the piece
	public void removePiece(int x, int y) {
		Color color = (button[x][y]).getBackground();
		if (turn == Y_TURN) {
			if (color.equals(Color.YELLOW)) {
				if (check3(x, y) == false) {
					(button[x][y]).setBackground(Color.WHITE);
					board[x][y] = BLANK;
					colorboard[x][y] = BLANK;
					ypins -= 1;
					remove = false;
					removed = true;
					if (ypins == 2) {
						JOptionPane.showMessageDialog(frame, "Game Over, Red Wins!");
					}
				}
				else if (ypins <= 3) {
					(button[x][y]).setBackground(Color.WHITE);
					board[x][y] = BLANK;
					colorboard[x][y] = BLANK;
					ypins -= 1;
					remove = false;
					removed = true;
				}
				else {
					JOptionPane.showMessageDialog(frame, "Can't, 3 in a row here");
				}
			}
			else if(color.equals(Color.WHITE)) {
				JOptionPane.showMessageDialog(frame, "Please remove a piece");
			}
			else {
				JOptionPane.showMessageDialog(frame, "Please remove the oppenents piece");
			}
		}
		else {
			if (color.equals(Color.RED)) {
				if (check3(x, y) == false) {
					(button[x][y]).setBackground(Color.WHITE);
					board[x][y] = BLANK;
					colorboard[x][y] = BLANK;
					rpins -= 1;
					remove = false;
					removed = true;
					if (rpins == 2) {
						JOptionPane.showMessageDialog(frame, "Game Over, Yellow Wins!");
					}
				}
				else if (rpins <= 3) {
					(button[x][y]).setBackground(Color.WHITE);
					board[x][y] = BLANK;
					colorboard[x][y] = BLANK;
					rpins -= 1;
					remove = false;
					removed = true;
				}
				else {
					JOptionPane.showMessageDialog(frame, "Can't, 3 in a row here");
				}
			}
			else if(color.equals(Color.WHITE)) {
				JOptionPane.showMessageDialog(frame, "Please remove a piece");
			}
			else {
				JOptionPane.showMessageDialog(frame, "Please remove the oppenents piece");
			}
		}
	}
	
	//checks if selected is valid for yellow
	public void selectY(int x, int y) {
		Color color = (button[x][y]).getBackground();
		if (color == Color.YELLOW) {
			holdx = x;
			holdy = y;
			select = true;
		}
		else if (color.equals(Color.DARK_GRAY)) {
			JOptionPane.showMessageDialog(frame, "Not a Piece!");
		}
		else if (color.equals(Color.WHITE)) {
			JOptionPane.showMessageDialog(frame, "Not a Piece!");
		}
		else {
			JOptionPane.showMessageDialog(frame, "Not Your Turn!");
		}
	}
	
	//checks if selected is valid for red
	public void selectR(int x, int y) {
		Color color = (button[x][y]).getBackground();
		if (color == Color.RED) {
			holdx = x;
			holdy = y;
			select = true;
		}
		else if (color.equals(Color.DARK_GRAY)) {
			JOptionPane.showMessageDialog(frame, "Not a Piece!");
		}
		else if (color.equals(Color.WHITE)) {
			JOptionPane.showMessageDialog(frame, "Not a Piece!");
		}
		else {
			JOptionPane.showMessageDialog(frame, "Not Your Turn!");
		}
	}
	
	//checks if you can move to the next place and if you can, it will move it for yellow
	public void moveY(int x, int y, int j, int i) {
		Color color = (button[j][i]).getBackground();
		if (color.equals(Color.DARK_GRAY)) {
			JOptionPane.showMessageDialog(frame, "Invalid Move!");
		}
		else if (color.equals(Color.RED)) {
			JOptionPane.showMessageDialog(frame, "Invalid Move!");
		}
		else { 
			if (movePiece(x, y, j, i) == true) {
				check(x, y);	
				//Moving it
				(button[x][y]).setBackground(Color.WHITE);
				colorboard[x][y] = 0;
				board[x][y] = 0;
				(button[j][i]).setBackground(Color.YELLOW);
				colorboard[j][i] = Y_MOVE;
				board[j][i] = R_MOVE;
				if (eat(Y_MOVE) == true) {
					remove = true;
				}
				turn = R_TURN;
				current = Color.RED;
				select = false;
			}
			else {
				JOptionPane.showMessageDialog(frame, "Invalid Move!");
			}
		}
	}
	
	//checks if you can move to the next place and if you can, it will move it for red
	public void moveR(int x, int y, int j, int i) {
		Color color = (button[j][i]).getBackground();
		if (color.equals(Color.DARK_GRAY)) {
			JOptionPane.showMessageDialog(frame, "Invalid Move!");
		}
		else if (color.equals(Color.YELLOW)) {
			JOptionPane.showMessageDialog(frame, "Invalid Move!");
		}
		else {
			if (movePiece(x, y, j, i) == true) {
				check(x, y);	
				//Moving it
				(button[x][y]).setBackground(Color.WHITE);
				colorboard[x][y] = 0;
				board[x][y] = 0;
				(button[j][i]).setBackground(Color.RED);
				colorboard[j][i] = R_MOVE;
				board[j][i] = R_MOVE;
				if (eat(R_MOVE) == true) {
					remove = true;
				}
				turn = Y_TURN;
				current = Color.YELLOW;
				select = false;
			}
			else {
				JOptionPane.showMessageDialog(frame, "Invalid Move!");
			}

		}
	}
	
	//Moving for Yellow when it has less than 3 pieces left
	public void flyY(int x, int y, int j, int i) {
		Color color = (button[j][i]).getBackground();
		if (color.equals(Color.DARK_GRAY)) {
			JOptionPane.showMessageDialog(frame, "Invalid Move!");
		}
		else if (color.equals(Color.RED)) {
			JOptionPane.showMessageDialog(frame, "Invalid Move!");
		}
		else { 
			check(x, y);	
			//Moving it
			(button[x][y]).setBackground(Color.WHITE);
			colorboard[x][y] = 0;
			board[x][y] = 0;
			(button[j][i]).setBackground(Color.YELLOW);
			colorboard[j][i] = Y_MOVE;
			board[j][i] = R_MOVE;
			if (eat(Y_MOVE) == true) {
				remove = true;
			}
			turn = R_TURN;
			current = Color.RED;
			select = false;
		}
	}
	//Moving for red when it has less than 3 pieces left
	public void flyR(int x, int y, int j, int i) {
		Color color = (button[j][i]).getBackground();
		if (color.equals(Color.DARK_GRAY)) {
			JOptionPane.showMessageDialog(frame, "Invalid Move!");
		}
		else if (color.equals(Color.YELLOW)) {
			JOptionPane.showMessageDialog(frame, "Invalid Move!");
		}
		else {
			check(x, y);	
			//Moving it
			(button[x][y]).setBackground(Color.WHITE);
			colorboard[x][y] = 0;
			board[x][y] = 0;
			(button[j][i]).setBackground(Color.RED);
			colorboard[j][i] = R_MOVE;
			board[j][i] = R_MOVE;
			if (eat(R_MOVE) == true) {
				remove = true;
			}
			turn = Y_TURN;
			current = Color.YELLOW;
			select = false;
		}
	}
	
	Color current = Color.RED;
	
	@Override
	public void actionPerformed(ActionEvent event) {
		//what happens when you press buttons divided into phases
		boolean gridButton = false;
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if (event.getSource().equals(button[j][i])) {
					gridButton = true;
					//phase 1
					if (phase1 == true) {
						if (remove == false) {
							placePiece(j, i);
						}
						else {
							removed = false;
							removePiece(j, i);
							if (yplaced == 0) {
								if (removed == true) {
									JOptionPane.showMessageDialog(frame, "End of Phase 1, on to Phase 2");
									phase2 = true;
									phase1 = false;
									remove = false;
								}
							}
						}
					}
					//phase 3
					else if (turn == Y_TURN & ypins <= 3) {
						if (remove == false) {
							if (select == false) {
									selectY(j, i);
							}
							else if ((current == button[j][i].getBackground())) {
								select = false;
							}
							else {
								flyY(holdx, holdy, j ,i);
							}
						}
						else {
							removePiece(j, i);
						}
					}
					else if (turn == R_TURN & rpins <= 3) {
						if (remove == false) {
							if (select == false) {
									selectR(j, i);
							}
							else if ((current == button[j][i].getBackground())) {
								select = false;
							}
							else {
								flyR(holdx, holdy, j ,i);
							}
						}
						else {
							removePiece(j, i);
						}
					}
		
					//phase 2
					else {
						if (remove == false) {
							if (select == false) {
								if (turn == Y_TURN) {
									selectY(j, i);
								}
								else {
									selectR(j, i);
								}
							}
							else if ((current == button[j][i].getBackground())) {
								select = false;
							}
							else {
								if (turn == Y_TURN) {
									moveY(holdx, holdy, j ,i);

								}	
								else {
									moveR(holdx, holdy, j ,i);

								}
							}
						}
						else {
							removePiece(j, i);
						}
					}
				}
			}
		}
		
		//This is where you rename yourself
		if (gridButton == false) {				
			if (event.getSource().equals(rChangeName) == true) {
				if (rChangeField.getText().equals("") == false) {
					rPlayerName = rChangeField.getText();							
				}
			}
			else if (event.getSource().equals(yChangeName) == true) {
				if (yChangeField.getText().equals("") == false) {
					yPlayerName = yChangeField.getText();
				}
			}
		}
	}
	
	//Checks for duplicate 3 in a rows
	boolean T1 = false;
	boolean T2 = false;
	boolean T3 = false;
	boolean T4 = false;	
	boolean T5 = false;
	boolean T6 = false;
	boolean T7 = false;
	boolean T8 = false;
	boolean S1 = false;
	boolean S2 = false;
	boolean S3 = false;
	boolean S4 = false;
	boolean S5 = false;
	boolean S6 = false;
	boolean S7 = false;
	boolean S8 = false;
	
	
	//All the conditions on when you are allowed to take a piece from enemy
	public boolean eat(int player) {
		if (T1 == false) {
			if ((colorboard[3][0] == player ) & (colorboard[0][0] == player) & (colorboard[6][0] == player)) {
				board[3][0] += 2;
				board[0][0] += 2;
				board[6][0] += 2;
				T1 = true;
				return true;
			}
		}
		if (T2 == false) {
			if ((colorboard[1][1] == player) & (colorboard[3][1] == player) & (colorboard[5][1] == player)) {
				board[1][1] += 2;
				board[3][1] += 2;
				board[5][1] += 2;
				T2 = true;
				return true;
			}
		}
		if (T3 == false) {
			if ((colorboard[2][2] == player) & (colorboard[3][2] == player) & (colorboard[4][2] == player)) {
				board[2][2] += 2;
				board[3][2] += 2;
				board[4][2] += 2;
				T3 = true;
				return true;
			}
		}
		if (T4 == false) {
			if ((colorboard[0][3] == player) & (colorboard[1][3] == player) & (colorboard[2][3] == player)) {
				board[0][3] += 2;
				board[1][3] += 2;
				board[2][3] += 2;
				T4 = true;
				return true;
			}
		}
		if (T5 == false) {
			if ((colorboard[4][3] == player) & (colorboard[5][3] == player) & (colorboard[6][3] == player)) {
				board[4][3] += 2;
				board[5][3] += 2;
				board[6][3] += 2;
				T5 = true;
				return true;
			}
		}
		if (T6 == false) {
			if ((colorboard[2][4] == player) & (colorboard[3][4] == player) & (colorboard[4][4] == player)) { 
				board[2][4] += 2;
				board[3][4] += 2;
				board[4][4] += 2;
				T6 = true;
				return true;
			}
		}
		if (T7 == false) {
			if ((colorboard[1][5] == player) & (colorboard[3][5] == player) & (colorboard[5][5] == player)) {
				board[1][5] += 2;
				board[3][5] += 2;
				board[5][5] += 2;
				T7 = true;
				return true;
			}
		}
		if (T8 == false) {
			if ((colorboard[0][6] == player) & (colorboard[3][6] == player) & (colorboard[6][6] == player)) {
				board[0][6] += 2;
				board[3][6] += 2;
				board[6][6] += 2;
				T8 = true;
				return true;
			}
		}
		if (S1 == false) {
			if ((colorboard[0][0] == player) & (colorboard[0][3] == player) & (colorboard[0][6] == player)) {
				board[0][0] += 2;
				board[0][3] += 2;
				board[0][6] += 2;
				S1 = true;
				return true;
			}
		}
		if (S2 == false) {
			if ((colorboard[1][1] == player) & (colorboard[1][3] == player) & (colorboard[1][5] == player)) {
				board[1][1] += 2;
				board[1][3] += 2;
				board[1][5] += 2;
				S2 = true;
				return true;
			}
		}
		if (S3 == false) {
			if ((colorboard[2][2] == player) & (colorboard[2][3] == player) & (colorboard[2][4] == player)) {
				board[2][2] += 2;
				board[2][3] += 2;
				board[2][4] += 2;
				S3 = true;
				return true;
			}
		}
		if (S4 == false) {
			if ((colorboard[3][0] == player) & (colorboard[3][1] == player) & (colorboard[3][2] == player)) {
				board[3][0] += 2;
				board[3][1] += 2;
				board[3][2] += 2;
				S4 = true;
				return true;
			}
		}
		if (S5 == false) {
			if ((colorboard[3][4] == player) & (colorboard[3][5] == player) & (colorboard[3][6] == player)) {
				board[3][4] += 2;
				board[3][5] += 2;
				board[3][6] += 2;
				S5 = true;
				return true;
			}
		}
		if (S6 == false) {
			if ((colorboard[4][2] == player) & (colorboard[4][3] == player) & (colorboard[4][4] == player)) {
				board[4][2] += 2;
				board[4][3] += 2;
				board[4][4] += 2;
				S6 = true;
				return true;
			}
		}
		if (S7 == false) {
			if ((colorboard[5][1] == player) & (colorboard[5][3] == player) & (colorboard[5][5] == player)) {
				board[5][1] += 2;
				board[5][3] += 2;
				board[5][5] += 2;
				S7 = true;
				return true;
			}
		}
		if (S8 == false) {
			if ((colorboard[6][0] == player) & (colorboard[6][3] == player) & (colorboard[6][6] == player)) {
				board[6][0] += 2;
				board[6][3] += 2;
				board[6][6] += 2;
				S8 = true;
				return true;
			}
		}
		return false;
	}
	
	//Checks for existing 3 in a rows during the move phase and checks for overlapping pieces in more than 1 rows
	public void check(int x, int y) {
		if (x == 0 & y == 0) {
			if ((board[0][0] >= 3) & (board[3][0] >= 3) & (board[6][0] >= 3)) {
				board[0][0] -= 2;
				board[3][0] -= 2;
				board[6][0] -= 2;
				T1 = false;
			}
			if ((board[0][0] >= 3) & (board[0][3] >= 3) & (board[0][6] >= 3)) {
				board[0][0] -= 2;
				board[0][3] -= 2;
				board[0][6] -= 2;
				S1 = false;
			}
		}
		if (x == 3 & y == 0) {
			if ((board[0][0] >= 3) & (board[3][0] >= 3) & (board[6][0] >= 3)) {
				board[0][0] -= 2;
				board[3][0] -= 2;
				board[6][0] -= 2;
				T1 = false;
			}
			if ((board[3][0] >= 3) & (board[3][1] >= 3) & (board[3][2] >= 3)) {
				board[3][0] -= 2;
				board[3][1] -= 2;
				board[3][2] -= 2;
				S4 = false;
			}
		}
		if (x == 6 & y == 0) {
			if ((board[0][0] >= 3) & (board[3][0] >= 3) & (board[6][0] >= 3)) {
				board[0][0] -= 2;
				board[3][0] -= 2;
				board[6][0] -= 2;
				T1 = false;
			}
			if ((board[6][0] >= 3) & (board[6][3] >= 3) & (board[6][6] >= 3)) {
				board[6][0] -= 2;
				board[6][3] -= 2;
				board[6][6] -= 2;
				S8 = false;
			}
		}
		if (x == 1 & y == 1) {
			if ((board[1][1] >= 3) & (board[3][1] >= 3) & (board[5][1] >= 3)) {
				board[1][1] -= 2;
				board[3][1] -= 2;
				board[5][1] -= 2;
				T2 = false;
			}
			if ((board[1][1] >= 3) & (board[1][3] >= 3) & (board[1][5] >= 3)) {
				board[1][1] -= 2;
				board[1][3] -= 2;
				board[1][5] -= 2;
				S2 = false;
			}
		}
		if (x == 3 & y == 1) {
			if ((board[1][1] >= 3) & (board[3][1] >= 3) & (board[5][1] >= 3)) {
				board[1][1] -= 2;
				board[3][1] -= 2;
				board[5][1] -= 2;
				T2 = false;
			}
			if ((board[3][0] >= 3) & (board[3][1] >= 3) & (board[3][2] >= 3)) {
				board[3][0] -= 2;
				board[3][1] -= 2;
				board[3][2] -= 2;
				S4 = false;
			}
		}
		if (x == 5 & y == 1) {
			if ((board[1][1] >= 3) & (board[3][1] >= 3) & (board[5][1] >= 3)) {
				board[1][1] -= 2;
				board[3][1] -= 2;
				board[5][1] -= 2;
				T2 = false;
			}
			if ((board[5][1] >= 3) & (board[5][3] >= 3) & (board[5][5] >= 3)) {
				board[5][1] -= 2;
				board[5][3] -= 2;
				board[5][5] -= 2;
				S7 = false;
			}
		}
		if (x == 2 & y == 2) {
			if ((board[2][2] >= 3) & (board[2][3] >= 3) & (board[2][4] >= 3)) {
				board[2][2] -= 2;
				board[2][3] -= 2;
				board[2][4] -= 2;
				S3 = false;
			}
			if ((board[2][2] >= 3) & (board[3][2] >= 3) & (board[4][2] >= 3)) {
				board[2][2] -= 2;
				board[3][2] -= 2;
				board[4][2] -= 2;
				T3 = false;
			}
		}
		if (x == 3 & y == 2) {
			if ((board[2][2] >= 3) & (board[3][2] >= 3) & (board[4][2] >= 3)) {
				board[2][2] -= 2;
				board[3][2] -= 2;
				board[4][2] -= 2;
				T3 = false;
			}
			if ((board[3][0] >= 3) & (board[3][1] >= 3) & (board[3][2] >= 3)) {
				board[3][0] -= 2;
				board[3][1] -= 2;
				board[3][2] -= 2;
				S4 = false;
			}
		}
		if (x == 4 & y == 2) {
			if ((board[2][2] >= 3) & (board[3][2] >= 3) & (board[4][2] >= 3)) {
				board[2][2] -= 2;
				board[3][2] -= 2;
				board[4][2] -= 2;
				T3 = false;
			}
			if ((board[4][2] >= 3) & (board[4][3] >= 3) & (board[4][4] >= 3)) {
				board[4][2] -= 2;
				board[4][3] -= 2;
				board[4][4] -= 2;
				S6 = false;
			}
		}
		if (x == 0 & y == 3) {
			if ((board[0][3] >= 3) & (board[1][3] >= 3) & (board[2][3] >= 3)) {
				board[0][3] -= 2;
				board[1][3] -= 2;
				board[2][3] -= 2;
				T4 = false;
			}
			if ((board[0][0] >= 3) & (board[0][3] >= 3) & (board[0][6] >= 3)) {
				board[0][0] -= 2;
				board[0][3] -= 2;
				board[0][6] -= 2;
				S1 = false;
			}
		}
		if (x == 1 & y == 3) {
			if ((board[0][3] >= 3) & (board[1][3] >= 3) & (board[2][3] >= 3)) {
				board[0][3] -= 2;
				board[1][3] -= 2;
				board[2][3] -= 2;
				T4 = false;
			}
			if ((board[1][1] >= 3) & (board[1][3] >= 3) & (board[1][5] >= 3)) {
				board[1][1] -= 2;
				board[1][3] -= 2;
				board[1][5] -= 2;
				S2 = false;
			}
		}
		if (x == 2 & y == 3) {
			if ((board[0][3] >= 3) & (board[1][3] >= 3) & (board[2][3] >= 3)) {
				board[0][3] -= 2;
				board[1][3] -= 2;
				board[2][3] -= 2;
				T4 = false;
			}
			if ((board[2][2] >= 3) & (board[2][3] >= 3) & (board[2][4] >= 3)) {
				board[2][2] -= 2;
				board[2][3] -= 2;
				board[2][4] -= 2;
				S3 = false;
			}
		}
		if (x == 4 & y == 3) {
			if ((board[4][3] >= 3) & (board[5][3] >= 3) & (board[6][3] >= 3)) {
				board[4][3] -= 2;
				board[5][3] -= 2;
				board[6][3] -= 2;
				T5 = false;
			}
			if ((board[4][2] >= 3) & (board[4][3] >= 3) & (board[4][4] >= 3)) {
				board[4][2] -= 2;
				board[4][3] -= 2;
				board[4][4] -= 2;
				S6 = false;
			}
		}
		if (x == 5 & y == 3) {
			if ((board[4][3] >= 3) & (board[5][3] >= 3) & (board[6][3] >= 3)) {
				board[4][3] -= 2;
				board[5][3] -= 2;
				board[6][3] -= 2;
				T5 = false;
			}
			if ((board[5][1] >= 3) & (board[5][3] >= 3) & (board[5][5] >= 3)) {
				board[5][1] -= 2;
				board[5][3] -= 2;
				board[5][5] -= 2;
				S7 = false;
			}
		}
		if (x == 6 & y == 3) {
			if ((board[4][3] >= 3) & (board[5][3] >= 3) & (board[6][3] >= 3)) {
				board[4][3] -= 2;
				board[5][3] -= 2;
				board[6][3] -= 2;
				T5 = false;
			}
			if ((board[6][0] >= 3) & (board[6][3] >= 3) & (board[6][6] >= 3)) {
				board[6][0] -= 2;
				board[6][3] -= 2;
				board[6][6] -= 2;
				S8 = false;
			}
		}
		if (x == 2 & y == 4) {
			if ((board[2][4] >= 3) & (board[3][4] >= 3) & (board[4][4] >= 3)) {
				board[2][4] -= 2;
				board[3][4] -= 2;
				board[4][4] -= 2;
				T6 = false;
			}
			if ((board[2][2] >= 3) & (board[2][3] >= 3) & (board[2][4] >= 3)) {
				board[2][2] -= 2;
				board[2][3] -= 2;
				board[2][4] -= 2;
				S3 = false;
			}
		}
		if (x == 3 & y == 4) {
			if ((board[2][4] >= 3) & (board[3][4] >= 3) & (board[4][4] >= 3)) {
				board[2][4] -= 2;
				board[3][4] -= 2;
				board[4][4] -= 2;
				T6 = false;
			}
			if ((board[3][4] >= 3) & (board[3][5] >= 3) & (board[3][6] >= 3)) {
				board[3][4] -= 2;
				board[3][5] -= 2;
				board[3][6] -= 2;
				S5 = false;
			}
		}
		if (x == 4 & y == 4) {
			if ((board[2][4] >= 3) & (board[3][4] >= 3) & (board[4][4] >= 3)) {
				board[2][4] -= 2;
				board[3][4] -= 2;
				board[4][4] -= 2;
				T6 = false;
			}
			if ((board[4][2] >= 3) & (board[4][3] >= 3) & (board[4][4] >= 3)) {
				board[4][2] -= 2;
				board[4][3] -= 2;
				board[4][4] -= 2;
				S6 = false;
			}
		}
		if (x == 1 & y == 5) {
			if ((board[1][5] >= 3) & (board[3][5] >= 3) & (board[5][5] >= 3)) {
				board[1][5] -= 2;
				board[3][5] -= 2;
				board[5][5] -= 2;
				T7 = false;
			}
			if ((board[1][1] >= 3) & (board[1][3] >= 3) & (board[1][5] >= 3)) {
				board[1][1] -= 2;
				board[1][3] -= 2;
				board[1][5] -= 2;
				S2 = false;
			}
		}
		if (x == 3 & y == 5) {
			if ((board[1][5] >= 3) & (board[3][5] >= 3) & (board[5][5] >= 3)) {
				board[1][5] -= 2;
				board[3][5] -= 2;
				board[5][5] -= 2;
				T7 = false;
			}
			if ((board[3][4] >= 3) & (board[3][5] >= 3) & (board[3][6] >= 3)) {
				board[3][4] -= 2;
				board[3][5] -= 2;
				board[3][6] -= 2;
				S5 = false;
			}
		}
		if (x == 5 & y == 5) {
			if ((board[1][5] >= 3) & (board[3][5] >= 3) & (board[5][5] >= 3)) {
				board[1][5] -= 2;
				board[3][5] -= 2;
				board[5][5] -= 2;
				T7 = false;
			}
			if ((board[5][5] >= 3) & (board[5][3] >= 3) & (board[5][1] >= 3)) {
				board[5][1] -= 2;
				board[5][3] -= 2;
				board[5][5] -= 2;
				S7 = false;
			}
		}
		if (x == 0 & y == 6) {
			if ((board[0][6] >= 3) & (board[3][6] >= 3) & (board[6][6] >= 3)) {
				board[0][6] -= 2;
				board[3][6] -= 2;
				board[6][6] -= 2;
				T8 = false;
			}
			if ((board[0][0] >= 3) & (board[0][3] >= 3) & (board[0][6] >= 3)) {
				board[0][0] -= 2;
				board[0][3] -= 2;
				board[0][6] -= 2;
				S1 = false;
			}
		}
		if (x == 3 & y == 6) {
			if ((board[0][6] >= 3) & (board[3][6] >= 3) & (board[6][6] >= 3)) {
				board[0][6] -= 2;
				board[3][6] -= 2;
				board[6][6] -= 2;
				T8 = false;
			}
			if ((board[3][4] >= 3) & (board[3][5] >= 3) & (board[3][6] >= 3)) {
				board[3][4] -= 2;
				board[3][5] -= 2;
				board[3][6] -= 2;
				S5 = false;
			}
		}
		if (x == 6 & y == 6) {
			if ((board[0][6] >= 3) & (board[3][6] >= 3) & (board[6][6] >= 3)) {
				board[0][6] -= 2;
				board[3][6] -= 2;
				board[6][6] -= 2;
				T8 = false;
			}
			if ((board[6][0] >= 3) & (board[6][3] >= 3) & (board[6][6] >= 3)) {
				board[6][0] -= 2;
				board[6][3] -= 2;
				board[6][6] -= 2;
				S8 = false;
			}
		}
	}
		
	public boolean movePiece(int x, int y, int j, int i) {
		//Every Move Possible from each point on the board
		if (x == 0 & y == 0) {
			if (j == 3 & i == 0) {
				return true;
			}
			if (j == 0 & i == 3) {
				return true;
			}
		}
		if (x == 3 & y == 0) {
			if (j == 0 & i == 0) {
				return true;
			}
			if (j == 6 & i == 0) {
				return true;
			}
			if (j == 3 & i == 1) {
				return true;
			}
		}
		if (x == 6 & y == 0) {
			if (j == 3 & i == 0) {
				return true;
			}
			if (j == 6 & i == 3) {
				return true;
			}
		}
		if (x == 1 & y == 1) {
			if (j == 3 & i == 1) {
				return true;
			}
			if (j == 1 & i == 3) {
				return true;
			}
		}
		if (x == 3 & y == 1) {
			if (j == 3 & i == 0) {
				return true;
			}
			if (j == 1 & i == 1) {
				return true;
			}
			if (j == 5 & i == 1) {
				return true;
			}
			if (j == 3 & i == 2) {
				return true;
			}
		}
		if (x == 5 & y == 1) {
			if (j == 3 & i == 1) {
				return true;
			}
			if (j == 5 & i == 3) {
				return true;
			}
		}
		if (x == 2 & y == 2) {
			if (j == 3 & i == 2) {
				return true;
			}
			if (j == 2 & i == 3) {
				return true;
			}
		}
		if (x == 3 & y == 2) {
			if (j == 3 & i == 1) {
				return true;
			}
			if (j == 2 & i == 2) {
				return true;
			}
			if (j == 4 & i == 2) {
				return true;
			}
		}
		if (x == 4 & y == 2) {
			if (j == 3 & i == 2) {
				return true;
			}
			if (j == 4 & i == 3) {
				return true;
			}
		}
		if (x == 0 & y == 3) {
			if (j == 1 & i == 3) {
				return true;
			}
			if (j == 0 & i == 0) {
				return true;
			}
			if (j == 0 & i == 6) {
				return true;
			}
		}
		if (x == 0 & y == 3) {
			if (j == 1 & i == 3) {
				return true;
			}
			if (j == 0 & i == 0) {
				return true;
			}
			if (j == 0 & i == 6) {
				return true;
			}
		}
		if (x == 1 & y == 3) {
			if (j == 0 & i == 3) {
				return true;
			}
			if (j == 2 & i == 3) {
				return true;
			}
			if (j == 1 & i == 1) {
				return true;
			}
			if (j == 1 & i == 5) {
				return true;
			}
		}
		if (x == 2 & y == 3) {
			if (j == 2 & i == 2) {
				return true;
			}
			if (j == 1 & i == 3) {
				return true;
			}
			if (j == 2 & i == 4) {
				return true;
			}
		}
		if (x == 4 & y == 3) {
			if (j == 4 & i == 2) {
				return true;
			}
			if (j == 5 & i == 3) {
				return true;
			}
			if (j == 4 & i == 4) {
				return true;
			}
		}
		if (x == 5 & y == 3) {
			if (j == 4 & i == 3) {
				return true;
			}
			if (j == 6 & i == 3) {
				return true;
			}
			if (j == 5 & i == 1) {
				return true;
			}
			if (j == 5 & i == 5) {
				return true;
			}
		}
		if (x == 6 & y == 3) {
			if (j == 5 & i == 3) {
				return true;
			}
			if (j == 6 & i == 0) {
				return true;
			}
			if (j == 6 & i == 6) {
				return true;
			}
		}
		if (x == 2 & y == 4) {
			if (j == 2 & i == 3) {
				return true;
			}
			if (j == 3 & i == 4) {
				return true;
			}
		}
		if (x == 3 & y == 4) {
			if (j == 2 & i == 4) {
				return true;
			}
			if (j == 4 & i == 4) {
				return true;
			}
			if (j == 3 & i == 5) {
				return true;
			}
		}
		if (x == 4 & y == 4) {
			if (j == 4 & i == 3) {
				return true;
			}
			if (j == 3 & i == 4) {
				return true;
			}
		}
		if (x == 1 & y == 5) {
			if (j == 1 & i == 3) {
				return true;
			}
			if (j == 3 & i == 5) {
				return true;
			}
		}
		if (x == 3 & y == 5) {
			if (j == 1 & i == 5) {
				return true;
			}
			if (j == 5 & i == 5) {
				return true;
			}
			if (j == 3 & i == 4) {
				return true;
			}
			if (j == 3 & i == 6) {
				return true;
			}
		}
		if (x == 5 & y == 5) {
			if (j == 5 & i == 3) {
				return true;
			}
			if (j == 3 & i == 5) {
				return true;
			}
		}
		if (x == 0 & y == 6) {
			if (j == 0 & i == 3) {
				return true;
			}
			if (j == 3 & i == 6) {
				return true;
			}
		}
		if (x == 3 & y == 6) {
			if (j == 3 & i == 5) {
				return true;
			}
			if (j == 0 & i == 6) {
				return true;
			}
			if (j == 6 & i == 6) {
				return true;
			}
		}
		if (x == 6 & y == 6) {
			if (j == 3 & i == 6) {
				return true;
			}
			if (j == 6 & i == 3) {
				return true;
			}
		}
		
		return false;
	}
}
