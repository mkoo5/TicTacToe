import java.util.Scanner;

public class AdvTicTacToe {

	public class Node {
		// the "row" is the board number and the "column" starting from 1 is the
		// position on the board going left to right so it is initialized to new
		// char[9][10]
		char[][] state;
		int val;
		Node parent;

		public Node() {
			this.state = new char[9][10];
			for (int i = 0; i < 9; i++) {
				for (int j = 1; j < 10; j++) {
					this.state[i][j] = '_';
				}
			}
			parent = null;
		}
	}

	public static Node root;

	public AdvTicTacToe() {
		root = new Node();
	}

	// prints all 9 boards
	static void printBoard(char b[][]) {
		String temp1 = "";
		String temp2 = "";
		String temp3 = "";
		for (int i = 0; i < 3; i++) {
			for (int j = 1; j < 10; j++) {
				if (j <= 3) {
					temp1 += "[" + b[i][j] + "]";
				} else if (j <= 6) {
					temp2 += "[" + b[i][j] + "]";
				} else if (j <= 9) {
					temp3 += "[" + b[i][j] + "]";
				}
			}
			temp1 += " ";
			temp2 += " ";
			temp3 += " ";
		}
		System.err.println(temp1);
		System.err.println(temp2);
		System.err.println(temp3);
		temp1 = temp2 = temp3 = "";
		System.err.println();

		for (int i = 3; i < 6; i++) {
			for (int j = 1; j < 10; j++) {
				if (j <= 3) {
					temp1 += "[" + b[i][j] + "]";
				} else if (j <= 6) {
					temp2 += "[" + b[i][j] + "]";
				} else if (j <= 9) {
					temp3 += "[" + b[i][j] + "]";
				}
			}
			temp1 += " ";
			temp2 += " ";
			temp3 += " ";
		}
		System.err.println(temp1);
		System.err.println(temp2);
		System.err.println(temp3);
		temp1 = temp2 = temp3 = "";
		System.err.println();

		for (int i = 6; i < 9; i++) {
			for (int j = 1; j < 10; j++) {
				if (j <= 3) {
					temp1 += "[" + b[i][j] + "]";
				} else if (j <= 6) {
					temp2 += "[" + b[i][j] + "]";
				} else if (j <= 9) {
					temp3 += "[" + b[i][j] + "]";
				}
			}
			temp1 += " ";
			temp2 += " ";
			temp3 += " ";
		}
		System.err.println(temp1);
		System.err.println(temp2);
		System.err.println(temp3);
		temp1 = temp2 = temp3 = "";
		System.err.println();
	}

	// checks if there are any possible moves left on any board
	// returns true if there are spots left on board
	static boolean movesLeft(char b[][]) {
		// loops through the 9 boards
		for (int i = 0; i < 9; i++) {
			for (int j = 1; j < 10; j++) {
				if (b[i][j] == '_') {
					return true;
				}
			}
		}
		return false;
	}

	// returns 10000 if 'X' won and -10000 if 'X' lost and returns 0 for a tie
	// returns -100000 if there are possible moves left
	static int evaluate(char b[][]) {

		// loops through boards
		for (int i = 0; i < 9; i++) {
			// checks diagonals
			if (b[i][1] == 'X' && b[i][1] == b[i][5] && b[i][5] == b[i][9]) {
				return 10000;
			} else if (b[i][3] == 'X' && b[i][3] == b[i][5] && b[i][5] == b[i][7]) {
				return 10000;
			} else if (b[i][1] == 'O' && b[i][1] == b[i][5] && b[i][5] == b[i][9]) {
				return -10000;
			} else if (b[i][3] == 'O' && b[i][3] == b[i][5] && b[i][5] == b[i][7]) {
				return -10000;
			}

			// checks rows
			else if (b[i][1] == 'X' && b[i][1] == b[i][2] && b[i][2] == b[i][3]) {
				return 10000;
			} else if (b[i][4] == 'X' && b[i][4] == b[i][5] && b[i][5] == b[i][6]) {
				return 10000;
			} else if (b[i][7] == 'X' && b[i][7] == b[i][8] && b[i][8] == b[i][9]) {
				return 10000;
			} else if (b[i][1] == 'O' && b[i][1] == b[i][2] && b[i][2] == b[i][3]) {
				return -10000;
			} else if (b[i][4] == 'O' && b[i][4] == b[i][5] && b[i][5] == b[i][6]) {
				return -10000;
			} else if (b[i][7] == 'O' && b[i][7] == b[i][8] && b[i][8] == b[i][9]) {
				return -10000;
			}

			// checks columns
			else if (b[i][1] == 'X' && b[i][1] == b[i][4] && b[i][4] == b[i][7]) {
				return 10000;
			} else if (b[i][2] == 'X' && b[i][2] == b[i][5] && b[i][5] == b[i][8]) {
				return 10000;
			} else if (b[i][3] == 'X' && b[i][3] == b[i][6] && b[i][6] == b[i][9]) {
				return 10000;
			} else if (b[i][1] == 'O' && b[i][1] == b[i][4] && b[i][4] == b[i][7]) {
				return -10000;
			} else if (b[i][2] == 'O' && b[i][2] == b[i][5] && b[i][5] == b[i][8]) {
				return -10000;
			} else if (b[i][3] == 'O' && b[i][3] == b[i][6] && b[i][6] == b[i][9]) {
				return -10000;
			}
		}
		if (movesLeft(b)) {
			return -100000;
		}
		return 0;
	}

	// checks if chosen board has any playable spots returns true if there are
	static boolean playable(char b[][], int board) {
		for (int i = 1; i < 10; i++) {
			if (b[board][i] == '_') {
				return true;
			}
		}
		return false;
	}

	// prints x number of spaces
	static void printSpaces(int x) {
		for (int i = 0; i < x; i++) {
			System.err.println();
		}
	}

	// heuristic function to evaluate non terminal board state
	static int heuristic(char b[][]) {
		if (evaluate(b) == 10000) {
			return 10000;
		}
		if (evaluate(b) == -10000) {
			return -10000;
		}
		// 'X' is maximizer, 'O' is minimizer
		int score = 0;
		for (int i = 0; i < 9; i++) {
			// checks for two in a row with space
			// first checks diagonals
			if (b[i][1] == 'X' && b[i][1] == b[i][5] && b[i][9] == '_') {
				score += 50;
			}
			if (b[i][1] == 'X' && b[i][1] == b[i][9] && b[i][5] == '_') {
				score += 50;
			}
			if (b[i][5] == 'X' && b[i][5] == b[i][9] && b[i][1] == '_') {
				score += 50;
			}
			if (b[i][3] == 'X' && b[i][3] == b[i][5] && b[i][7] == '_') {
				score += 50;
			}
			if (b[i][3] == 'X' && b[i][3] == b[i][7] && b[i][5] == '_') {
				score += 50;
			}
			if (b[i][5] == 'X' && b[i][5] == b[i][7] && b[i][3] == '_') {
				score += 50;
			}
			if (b[i][1] == 'O' && b[i][1] == b[i][5] && b[i][9] == '_') {
				score -= 50;
			}
			if (b[i][1] == 'O' && b[i][1] == b[i][9] && b[i][5] == '_') {
				score -= 50;
			}
			if (b[i][5] == 'O' && b[i][5] == b[i][9] && b[i][1] == '_') {
				score -= 50;
			}
			if (b[i][3] == 'O' && b[i][3] == b[i][5] && b[i][7] == '_') {
				score -= 50;
			}
			if (b[i][3] == 'O' && b[i][3] == b[i][7] && b[i][5] == '_') {
				score -= 50;
			}
			if (b[i][5] == 'O' && b[i][5] == b[i][7] && b[i][3] == '_') {
				score -= 50;
			}
			// check columns
			if (b[i][1] == 'X' && b[i][1] == b[i][4] && b[i][7] == '_') {
				score += 50;
			}
			if (b[i][1] == 'X' && b[i][1] == b[i][7] && b[i][4] == '_') {
				score += 50;
			}
			if (b[i][4] == 'X' && b[i][4] == b[i][7] && b[i][1] == '_') {
				score += 50;
			}
			if (b[i][2] == 'X' && b[i][2] == b[i][5] && b[i][8] == '_') {
				score += 50;
			}
			if (b[i][2] == 'X' && b[i][2] == b[i][8] && b[i][5] == '_') {
				score += 50;
			}
			if (b[i][5] == 'X' && b[i][5] == b[i][8] && b[i][2] == '_') {
				score += 50;
			}
			if (b[i][3] == 'X' && b[i][3] == b[i][6] && b[i][6] == '_') {
				score += 50;
			}
			if (b[i][3] == 'X' && b[i][3] == b[i][9] && b[i][6] == '_') {
				score += 50;
			}
			if (b[i][6] == 'X' && b[i][6] == b[i][9] && b[i][3] == '_') {
				score += 50;
			}

			if (b[i][1] == 'O' && b[i][1] == b[i][4] && b[i][7] == '_') {
				score -= 50;
			}
			if (b[i][1] == 'O' && b[i][1] == b[i][7] && b[i][4] == '_') {
				score -= 50;
			}
			if (b[i][4] == 'O' && b[i][4] == b[i][7] && b[i][1] == '_') {
				score -= 50;
			}
			if (b[i][2] == 'O' && b[i][2] == b[i][5] && b[i][8] == '_') {
				score -= 50;
			}
			if (b[i][2] == 'O' && b[i][2] == b[i][8] && b[i][5] == '_') {
				score -= 50;
			}
			if (b[i][5] == 'O' && b[i][5] == b[i][8] && b[i][2] == '_') {
				score -= 50;
			}
			if (b[i][3] == 'O' && b[i][3] == b[i][6] && b[i][6] == '_') {
				score -= 50;
			}
			if (b[i][3] == 'O' && b[i][3] == b[i][9] && b[i][6] == '_') {
				score -= 50;
			}
			if (b[i][6] == 'O' && b[i][6] == b[i][9] && b[i][3] == '_') {
				score -= 50;
			}
			// check rows
			if (b[i][1] == 'X' && b[i][1] == b[i][2] && b[i][3] == '_') {
				score += 50;
			}
			if (b[i][1] == 'X' && b[i][1] == b[i][3] && b[i][2] == '_') {
				score += 50;
			}
			if (b[i][2] == 'X' && b[i][2] == b[i][3] && b[i][1] == '_') {
				score += 50;
			}
			if (b[i][4] == 'X' && b[i][4] == b[i][5] && b[i][6] == '_') {
				score += 50;
			}
			if (b[i][4] == 'X' && b[i][4] == b[i][6] && b[i][5] == '_') {
				score += 50;
			}
			if (b[i][5] == 'X' && b[i][5] == b[i][6] && b[i][4] == '_') {
				score += 50;
			}
			if (b[i][7] == 'X' && b[i][7] == b[i][8] && b[i][9] == '_') {
				score += 50;
			}
			if (b[i][7] == 'X' && b[i][7] == b[i][9] && b[i][8] == '_') {
				score += 50;
			}
			if (b[i][8] == 'X' && b[i][8] == b[i][9] && b[i][7] == '_') {
				score += 50;
			}

			if (b[i][1] == 'O' && b[i][1] == b[i][2] && b[i][3] == '_') {
				score -= 50;
			}
			if (b[i][1] == 'O' && b[i][1] == b[i][3] && b[i][2] == '_') {
				score -= 50;
			}
			if (b[i][2] == 'O' && b[i][2] == b[i][3] && b[i][1] == '_') {
				score -= 50;
			}
			if (b[i][4] == 'O' && b[i][4] == b[i][5] && b[i][6] == '_') {
				score -= 50;
			}
			if (b[i][4] == 'O' && b[i][4] == b[i][6] && b[i][5] == '_') {
				score -= 50;
			}
			if (b[i][5] == 'O' && b[i][5] == b[i][6] && b[i][4] == '_') {
				score -= 50;
			}
			if (b[i][7] == 'O' && b[i][7] == b[i][8] && b[i][9] == '_') {
				score -= 50;
			}
			if (b[i][7] == 'O' && b[i][7] == b[i][9] && b[i][8] == '_') {
				score -= 50;
			}
			if (b[i][8] == 'O' && b[i][8] == b[i][9] && b[i][7] == '_') {
				score -= 50;
			}
		}
		return score;
	}

	// alpha is max lower bound so it is set to -inf, beta is set to inf
	static int miniMaxValue(char b[][], boolean player, int alpha, int beta, int board, int depth) {
		// terminates at depth 10
		if (depth == 10) {
			return heuristic(b);
		}
		int score = heuristic(b);
		// if x won
		if (score == 10000) {
			return score;
		}
		// if o won
		if (score == -10000) {
			return score;
		}
		// if tie
		if (movesLeft(b) == false) {
			return score;
		}

		if (player) {
			int best = Integer.MIN_VALUE;
			for (int i = 1; i < 10; i++) {
				if (b[board][i] == '_') {
					// makes a move
					b[board][i] = 'X';
					// recursive call, alternates player, sets board for next
					// player, increments depth
					int value = miniMaxValue(b, !player, alpha, beta, i - 1, depth + 1);
					best = Math.max(best, value);
					alpha = Math.max(best, alpha);
					// resets move
					b[board][i] = '_';
					// pruning
					if (alpha >= beta) {
						break;
					}
				}
			}
			return best;
		}
		// copy of above except for beta instead of alpha
		if (!player) {
			int best = Integer.MAX_VALUE;
			for (int i = 1; i < 10; i++) {
				if (b[board][i] == '_') {
					b[board][i] = 'O';
					int value = miniMaxValue(b, !player, alpha, beta, i - 1, depth + 1);
					best = Math.min(best, value);
					beta = Math.min(best, beta);
					b[board][i] = '_';
					if (alpha >= beta) {
						break;
					}
				}
			}
			return best;
		}
		return -2;
	}

	static String miniMax(char b[][], boolean isPlayerX, int board, int depth) {
		String answer = "";

		// iterates through board spots and chooses best move for computer
		// depending on whether player is 'X' or 'O'
		if (isPlayerX) {
			int bestVal = Integer.MAX_VALUE;

			for (int i = 1; i < 10; i++) {
				if (b[board][i] == '_') {
					// computer makes a move
					b[board][i] = 'O';
					// stores utility value of move assuming player makes
					// optimal move
					int move = miniMaxValue(b, true, Integer.MIN_VALUE, Integer.MAX_VALUE, i - 1, depth + 1);
					// resets move
					b[board][i] = '_';

					// if move was better for O (less than), update
					// action answer and set current bestVal to utility
					// value of
					// move
					if (move < bestVal) {
						answer = "" + i + move;
						bestVal = move;
					}
				}

			}
		} else if (!isPlayerX) {
			int bestVal = Integer.MIN_VALUE;
			for (int i = 1; i < 10; i++) {
				if (b[board][i] == '_') {
					// computer makes a move
					b[board][i] = 'X';
					// stores utility value of move assuming player makes
					// optimal move
					int move = miniMaxValue(b, false, Integer.MIN_VALUE, Integer.MAX_VALUE, i - 1, depth + 1);
					// resets move
					b[board][i] = '_';
					if (move > bestVal) {
						answer = "" + i + move;
						bestVal = move;
					}
				}

			}
		}
		return answer;
	}

	// game where computer chooses randomly
	// player starts as 'X'
	static void randomStartGame() {
		AdvTicTacToe game = new AdvTicTacToe();
		printBoard(root.state);
		Scanner input = new Scanner(System.in);
		int x = 0;
		while (evaluate(root.state) == -100000) {
			// y is the position on that board (1-9)
			int y = input.nextInt();
			// board next computer must play
			int board = y - 1;
			// places player move on board
			root.state[x][y] = 'X';

			// checks if board computer must play is playable
			while (!(playable(root.state, board))) {
				board = (int) (Math.random() * 9);
			}

			// chooses random number from 1-9 for computer
			x = (int) (Math.random() * 9) + 1;

			while (!(root.state[board][x] == '_')) {
				x = (int) (Math.random() * 9) + 1;
			}
			root.state[board][x] = 'O';
			printSpaces(10);
			printBoard(root.state);
			System.err.println("Current Board is:" + x);
			x = x - 1;
		}
	}

	static void startGame() {

		AdvTicTacToe game = new AdvTicTacToe();

		// starts with printing board
		printBoard(root.state);

		Scanner input = new Scanner(System.in);

		// prompts user to play as X or O
		// System.err.println("Play as X or O?");

		// initializes string a to input answer
		// String a = input.next();
		String a = "X";

		// checks if user input is valid
		while (!(a.equals("X") || a.equals("x") || a.equals("O") || a.equals("o"))) {
			System.err.println("Play as X or O?");
			a = input.next();
		}

		// initializes isPlayerX as true
		boolean isPlayerX = true;

		// initializes board to -1
		int board = -1;

		// if player chose O or o, changes isPlayerX to false, starts computer
		// at board 5
		if (a.equals("O") || a.equals("o")) {
			// computer plays as X
			isPlayerX = false;
			String temp = miniMax(root.state, isPlayerX, 4, 0);
			int x = Integer.parseInt(temp.substring(0, 1));
			// board player must play on if computer goes first
			board = x - 1;
			root.state[4][x] = 'X';
			printBoard(root.state);
		}
		// if player chooses X or x, prompts user to choose a starting board
		if (isPlayerX) {
			int number = -1;
			do {
				System.err.println("Choose board 1-9");
				while (!input.hasNextInt()) {
					input.next();
					System.err.println("Invalid input");
				}
				number = input.nextInt();
			} while (!(number >= 1 && number <= 9));
			board = number;
		}
		board -= 1;
		// while game isn't over
		while (evaluate(root.state) == -100000) {
			// checks if there are moves on current board
			// while current board is not playable
			while (!playable(root.state, board)) {
				// prompt user to choose a board
				System.err.println("Choose board 1-9");
				// if invalid board, prompts user again
				while (!(board >= 1 && board <= 9)) {
					System.err.println("Invalid board");
					board = input.nextInt();
				}
				board -= 1;
			}
			char player = 'X';
			char comp = 'O';
			// if player is going second as 'O'
			if (isPlayerX == false) {
				player = comp;
				comp = 'X';
			}
			// prompts user to choose a valid move
			int number = -1;
			do {
				System.err.println("Choose a move 1-9");
				while (!input.hasNextInt()) {
					input.next();
					System.err.println("Invalid input");
				}
				number = input.nextInt();
			} while (!(number >= 1) || (!(number <= 9)) || root.state[board][number] != '_');
			int move = number;
			// places move on board for player
			root.state[board][move] = player;
			move -= 1;
			// checks if there are any moves on board computer has to play
			if (!playable(root.state, move)) {
				move = (int) (Math.random() * 9);
			}
			String temp = miniMax(root.state, isPlayerX, move, 0);
			int computermove = Integer.parseInt(temp.substring(0, 1));
			root.state[move][computermove] = comp;
			board = computermove;
			printBoard(root.state);
			int computerboard = move + 1;
			System.err.println("Current board is " + board);
			System.out.println("" + computerboard + board);
			board -= 1;
		}
		input.close();
		if (evaluate(root.state) == 10000) {
			System.err.println("Player X Wins");
		} else if (evaluate(root.state) == -10000) {
			System.err.println("Player O Wins");
		}
	}

	public static void main(String[] args) {
		startGame();
	}
}
