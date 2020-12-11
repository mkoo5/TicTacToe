import java.util.Scanner;

public class TicTacToe {

	public static class Node {
		// representing board state as a 2d char array
		char[][] state;
		Node[] children;
		Node parent;
		boolean player;

		public Node() {
			state = new char[][] { { '_', '_', '_' }, { '_', '_', '_' }, { '_', '_', '_' } };
			children = new Node[9];
			parent = null;
			player = true;
		}

		public Node(char b[][], boolean player) {
			state = b;
			this.player = player;
			children = new Node[9];
		}

		public void addChild(char b[][]) {
			for (int i = 0; i < 9; i++) {
				if (this.children[i] == null) {
					this.children[i] = new Node(b, !this.player);
					this.children[i].parent = this;
					break;
				}
			}
		}
	}

	public static Node root;

	// calls default constructor of node for root
	public TicTacToe() {
		root = new Node();
	}

	// checks board state and returns value of board
	// maximizer is player 'X'
	// returns 1 if player (X) won and returns -1 if loss
	// if tie, returns 0
	// if game is not over, returns -2
	static int evaluate(char b[][]) {
		// checks diagonals
		if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
			if (b[0][0] == 'x' || b[0][0] == 'X') {
				return 1;
			}
			if (b[0][0] == 'o' || b[0][0] == 'O') {
				return -1;
			}
		}
		if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
			if (b[0][2] == 'x' || b[0][2] == 'X') {
				return 1;
			}
			if (b[0][2] == 'o' || b[0][2] == 'O') {
				return -1;
			}
		}
		// checks rows
		for (int i = 0; i < 3; i++) {
			if (b[i][0] == b[i][1] && b[i][1] == b[i][2]) {
				if (b[i][0] == 'x' || b[i][0] == 'X') {
					return 1;
				}
				if (b[i][0] == 'o' || b[i][0] == 'O') {
					return -1;
				}
			}
		}
		// checks columns
		for (int i = 0; i < 3; i++) {
			if (b[0][i] == b[1][i] && b[1][i] == b[2][i]) {
				if (b[0][i] == 'x' || b[0][i] == 'X') {
					return 1;
				}
				if (b[0][i] == 'o' || b[0][i] == 'O') {
					return -1;
				}
			}
		}
		// else return 0 for a tie
		if (!movesLeft(b)) {
			return 0;
		}

		// returns -2 if game is not over
		return -2;
	}

	// prints board
	static void printBoard(char b[][]) {
		System.err.println("[" + b[0][0] + "]" + "[" + b[0][1] + "]" + "[" + b[0][2] + "]");
		System.err.println("[" + b[1][0] + "]" + "[" + b[1][1] + "]" + "[" + b[1][2] + "]");
		System.err.println("[" + b[2][0] + "]" + "[" + b[2][1] + "]" + "[" + b[2][2] + "]");
	}

	// checks if there are any open spots left
	// returns true if there are moves left
	static boolean movesLeft(char b[][]) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (b[i][j] == '_') {
					return true;
				}
			}
		}
		return false;
	}

	// returns utility value of state, initialize alpha to -inf, beta to +inf
	// alpha is max lower bound, beta is min upper bound
	// decided to test implementing alpha and beta in minimax for this
	static int miniMaxValue(char b[][], boolean player, int alpha, int beta) {
		// stores value of board in score
		int score = evaluate(b);

		// terminates if game is over

		// player win
		if (score == 1) {
			return score;
		}

		// computer win
		if (score == -1) {
			return score;
		}

		// tie
		if (movesLeft(b) == false) {
			return score;
		}

		// player 'X' wants to maximize
		if (player) {

			int best = Integer.MIN_VALUE;

			outer: for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (b[i][j] == '_') {
						// makes a move on empty spot
						b[i][j] = 'X';
						// stores board state value in value
						int value = miniMaxValue(b, !player, alpha, beta);
						best = Math.max(best, value);
						alpha = Math.max(best, alpha);
						b[i][j] = '_';
						// prunes if alpha >= beta
						if (alpha >= beta) {
							break outer;
						}
					}
				}
			}
			return best;
		}
		// player 'O' wants to minimize
		if (!player) {

			int best = Integer.MAX_VALUE;

			outer: for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (b[i][j] == '_') {
						// makes a move on empty spot
						b[i][j] = 'O';
						// stores board state value in value
						int value = miniMaxValue(b, !player, alpha, beta);
						best = Math.min(best, value);
						beta = Math.min(best, beta);
						b[i][j] = '_';
						// prunes
						if (alpha >= beta) {
							break outer;
						}
					}
				}
			}
			return best;
		}
		return -2;
	}

	// returns an action in a string in the form "xyz" where x and y are the
	// coordinates of the move and z is the value
	static String miniMax(char b[][], boolean isPlayerX) {
		String answer = "";

		// iterates through board spots and chooses best move for computer
		// depending on whether player is 'X' or 'O'
		if (isPlayerX) {
			int bestVal = Integer.MAX_VALUE;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (b[i][j] == '_') {
						// computer makes a move
						b[i][j] = 'O';
						// stores utility value of move assuming player makes
						// optimal move
						int move = miniMaxValue(b, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
						// resets move
						b[i][j] = '_';

						// if move was better for computer (less than), update
						// action answer and set current bestVal to utility
						// value of
						// move
						if (move < bestVal) {
							answer = "" + i + j + move;
							bestVal = move;
						}
					}
				}
			}
		} else if (!isPlayerX) {
			int bestVal = Integer.MIN_VALUE;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (b[i][j] == '_') {
						// computer makes a move
						b[i][j] = 'X';
						// stores utility value of move assuming player makes
						// optimal move
						int move = miniMaxValue(b, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
						// resets move
						b[i][j] = '_';

						// if move was better for computer (less than), update
						// action answer and set current bestVal to utility
						// value of
						// move
						if (move > bestVal) {
							answer = "" + i + j + move;
							bestVal = move;
						}
					}
				}
			}
		}
		return answer;
	}

	// move from 1-9, returns 2 digit string representing coordinates
	static String move(int move) {
		String answer = "";
		if (move <= 3) {
			move -= 1;
			answer += "0" + move;
		} else if (move <= 6) {
			move -= 4;
			answer += "1" + move;
		} else if (move <= 9) {
			move -= 7;
			answer += "2" + move;
		}
		return answer;
	}

	// input is row and column (x and y respectively), output is a 1 digit
	// number representing
	// position
	static String move(int x, int y) {
		String answer = "";
		if (x == 0) {
			if (y == 0) {
				answer = "1";
			}
			if (y == 1) {
				answer = "2";
			}
			if (y == 2) {
				answer = "3";
			}
		} else if (x == 1) {
			if (y == 0) {
				answer = "4";
			}
			if (y == 1) {
				answer = "5";
			}
			if (y == 2) {
				answer = "6";
			}
		} else if (x == 2) {
			if (y == 0) {
				answer = "7";
			}
			if (y == 1) {
				answer = "8";
			}
			if (y == 2) {
				answer = "9";
			}
		}
		return answer;
	}

	static void startGame() {
		TicTacToe game = new TicTacToe();
		printBoard(root.state);
		Scanner input = new Scanner(System.in);

		// prompts user to choose to play as X or O
		System.err.println("Play as X or O?");
		String a = input.next();

		// checks to make sure input is valid
		while (!(a.equals("X") || a.equals("x") || a.equals("O") || a.equals("o"))) {
			System.err.println("Play as X or O?");
			a = input.next();
		}

		// default sets isPlayerX to true
		boolean isPlayerX = true;

		// if user chooses to play as O, makes computer X and has computer make
		// the first move
		// prints out what position computer played on to System.out in a single
		// int representing board position played (1-9)
		if (a.equals("O") || a.equals("o")) {
			isPlayerX = false;
			String temp = miniMax(root.state, isPlayerX);
			int x = Integer.parseInt(temp.substring(0, 1));
			int y = Integer.parseInt(temp.substring(1, 2));
			root.state[x][y] = 'X';
			System.out.println(move(x, y));
			printBoard(root.state);
		}
		while (evaluate(root.state) == -2) {
			char player = 'X';
			char comp = 'O';
			if (isPlayerX == false) {
				player = comp;
				comp = 'X';
			}
			int number = -1;
			String move = "";
			// makes sure input is an integer and valid (must be from 1-9 and
			// must be on an empty spot)
			do {
				System.err.println("Choose position 1-9");
				while (!input.hasNextInt()) {
					input.next();
					System.err.println("Invalid input");
				}
				number = input.nextInt();
				move = move(number);
			} while (!(number >= 1) || (!(number <= 9)) || root.state[Integer.parseInt(move.substring(0, 1))][Integer
					.parseInt(move.substring(1, 2))] != '_');
			root.state[Integer.parseInt(move.substring(0, 1))][Integer.parseInt(move.substring(1, 2))] = player;
			String temp = miniMax(root.state, isPlayerX);
			// makes sure there are moves left for computer to play
			if (movesLeft(root.state)) {
				int x = Integer.parseInt(temp.substring(0, 1));
				int y = Integer.parseInt(temp.substring(1, 2));
				root.state[x][y] = comp;
				System.out.println("Computer played position " + move(x, y));
				printBoard(root.state);
			}
			// terminating conditions
			if (evaluate(root.state) == 1) {
				System.err.println("Player X Won");
				break;
			}
			if (evaluate(root.state) == -1) {
				System.err.println("Player O Won");
				break;
			}
			if (evaluate(root.state) == 0) {
				System.err.println("Tie");
				break;
			}
		}
		input.close();
	}

	public static void main(String[] args) {
		startGame();
	}
}
