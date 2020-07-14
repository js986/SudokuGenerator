
public class App {
	public static void main(String[] args) {
		
		int [][] sudoku = 
			{
			{0,0,7,5,3,0,0,0,0},
			{0,1,0,0,4,0,7,2,5},
			{0,0,0,8,2,7,0,6,0},
			{4,5,1,9,6,0,8,0,0},
			{0,3,6,0,0,0,0,4,0},
			{0,0,0,0,7,4,0,0,0},
			{0,7,5,0,0,6,0,0,3},
			{3,0,9,2,0,8,5,0,0},
			{2,4,8,0,5,3,0,1,0}};
		Puzzle p = new Puzzle();
		p.generatePuzzle();
		p.printPuzzle();
		System.out.println();
		p.solve();
		p.printPuzzle();
	}
}
