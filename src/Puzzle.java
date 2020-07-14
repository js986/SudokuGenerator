import java.util.*;
public class Puzzle {
	private Cell[][] grid;
	private static Deque<Cell> cellList;
	public Puzzle() {
		grid = new Cell[9][9];
		cellList = new ArrayDeque<Cell>();
		for (int i = 0; i < 9;i++) {
			for (int j = 0; j < 9; j++) {
				grid[i][j] = new Cell(i,j);
				grid[i][j].x = i;
				grid[i][j].y = j;
				cellList.addLast(grid[i][j]);
			}
		}
	}
	
	public Puzzle(int[][] p) {
		grid = new Cell[9][9];
		for (int i = 0; i < 9;i++) {
			for (int j = 0; j < 9; j++) {
				grid[i][j] = new Cell(i,j);
				grid[i][j].x = i;
				grid[i][j].y = j;
				grid[i][j].value = p[i][j];
				//System.out.print(p[i][j] + " ");
			}
			//System.out.println();
		}
	}
	
	public ArrayList<Integer> validRow(int x, int y, ArrayList<Integer> list) {
		for (int i = 0; i < grid[x].length; i++) {
			if (list.contains(Integer.valueOf(grid[x][i].value)) && i != y) {
				list.remove(Integer.valueOf(grid[x][i].value));
			}
		}
		return list;
	}
	
	public ArrayList<Integer> validColumn(int x, int y, ArrayList<Integer> list) {
		for (int i = 0; i < grid.length; i++) {
			if (list.contains(Integer.valueOf(grid[i][y].value)) && i != x) {
				list.remove(Integer.valueOf(grid[i][y].value));
			}
		}
		return list;
	}
	
	public ArrayList<Integer> validSubSquare(int x, int y, ArrayList<Integer> list) {
		for (int i = x-(x%3); i < x-(x%3)+3; i++) {
			for (int j = y-(y%3); j < y-(y%3)+3; j++) {
				//System.out.print(grid[i][j].value + " ");
				if (list.contains(Integer.valueOf(grid[i][j].value)) && grid[x][y] != grid[i][j]) {
					list.remove(Integer.valueOf(grid[i][j].value));
				}
			}
		}
		return list;
	}
	
	public ArrayList<Integer> calculateValid(Cell c) {
		ArrayList<Integer> valid = new ArrayList<Integer>();
		valid.add(1);
		valid.add(2);
		valid.add(3);
		valid.add(4);
		valid.add(5);
		valid.add(6);
		valid.add(7);
		valid.add(8);
		valid.add(9);
		valid = validRow(c.x,c.y,valid); 
		//System.out.println("Row: " + valid);
		valid = validColumn(c.x,c.y,valid);
		//System.out.println("Column: " + valid);
		valid = validSubSquare(c.x,c.y,valid);
		//System.out.println("SubSquare: " + valid);
		//c.validValues = valid;
		return valid;
	}
	
	public void printPuzzle() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(this.grid[i][j].value + " ");
			}
			System.out.println();
		}
	}
	
	
	public Cell findEmptyCell() {
		for (int i = 0; i < grid.length;i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].value == 0) {
					return grid[i][j];
				}
			}
		}
		return null;
	}
	
	public Cell findNextCell(int x, int y) {
		if (x == 8 && y == 8) {
			return null;
		}
		if (y == 8) {
			return grid[0][y+1];
		}
		return grid[x+1][y];
	}
	
	public void solve() {
		Cell first = findEmptyCell();
		if (!solveHelper(first, null, 0)) {
			System.out.println("No solution exists");
		}
	}
	
	public boolean solveHelper(Cell current, Cell forbidden, int forbiddenValue) {
		if (current == null) {
			return true;
		}
		//System.out.println(current.x + " " + current.y);
		ArrayList<Integer> validValues = calculateValid(current);
		//System.out.println(validValues);
		int originalVal = current.value;
		for (int i = 0; i < validValues.size(); i++) {
			if (current == forbidden) {
				if (validValues.get(i) == forbiddenValue) {
					continue;
				}
			}
			current.value = validValues.get(i);
			//System.out.println("New Value: " + current.value);
			
			if (solveHelper(findEmptyCell(),forbidden,forbiddenValue)) {
				return true;
			}
		}
		current.value = originalVal;
		return false;
		
	}
	
	public void generatePuzzle() {
		Random rand = new Random();
		int blankSpaces = rand.nextInt(37-25) + 25;
		Cell first = findEmptyCell();
		if (!generatePuzzleHelper(first)){
			System.out.println("No solution exists");
		}
		for(int i = 0; i < blankSpaces;i++) {
			while(true) {
				int x = rand.nextInt(grid.length);
				int y = rand.nextInt(grid[0].length);
				Cell forbidden = grid[x][y];
				if (forbidden.value != 0) {
					int forbiddenVal = forbidden.value;
					forbidden.value = 0;
					if (!solveHelper(findEmptyCell(),forbidden,forbiddenVal)) {
						break;
					}
					forbidden.value = forbiddenVal;
				}
			}
		}
	}
	
	public boolean generatePuzzleHelper(Cell current) {
		if (current == null) {
			return true;
		}
		ArrayList<Integer> validValues = calculateValid(current);
		//System.out.println(current.x + " " + current.y);
		Collections.shuffle(validValues);
		//System.out.println("Collection : " + validValues);
		int originalVal = current.value;
		for (int i = 0; i < validValues.size(); i++) {

			current.value = validValues.get(i);
			
			if (generatePuzzleHelper(findEmptyCell())) {
				return true;
			}
		}
		
		current.value = originalVal;
		return false;
			
		
	}
}

