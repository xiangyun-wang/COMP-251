import java.util.*;
import java.lang.*;
import java.io.*;


public class Game {

	Board sudoku;

	public class Cell{
		private int row = 0;
		private int column = 0;

		public Cell(int row, int column) {
			this.row = row;
			this.column = column;
		}
		public int getRow() {
			return row;
		}
		public int getColumn() {
			return column;
		}
	}

	public class Region{
		private Cell[] matrix;
		private int num_cells;
		public Region(int num_cells) {
			this.matrix = new Cell[num_cells];
			this.num_cells = num_cells;
		}
		public Cell[] getCells() {
			return matrix;
		}
		public void setCell(int pos, Cell element){
			matrix[pos] = element;
		}

	}

	public class Board{
		private int[][] board_values;
		private boolean[][] board_preset;
		private Region[] board_regions;
		private int num_rows;
		private int num_columns;
		private int num_regions;

		public Board(int num_rows,int num_columns, int num_regions){
			this.board_values = new int[num_rows][num_columns];
			this.board_regions = new Region[num_regions];
			this.num_rows = num_rows;
			this.num_columns = num_columns;
			this.num_regions = num_regions;
			// added
			this.board_preset = new boolean[num_rows][num_columns];		// used to track whether or not the cell is given
		}

		public int[][] getValues(){
			return board_values;
		}
		public int getValue(int row, int column) {
			return board_values[row][column];
		}
		public Region getRegion(int index) {
			return board_regions[index];
		}
		public Region[] getRegions(){
			return board_regions;
		}
		public void setValue(int row, int column, int value){
			board_values[row][column] = value;
		}
		public void setRegion(int index, Region initial_region) {
			board_regions[index] = initial_region;
		}
		public void setValues(int[][] values) {
			board_values = values;
		}
		//added
		public boolean getState(int row, int column){				// to know if the cell is given or not
			return board_preset[row][column];
		}
		public void setState(int row, int column, boolean state){		// set whether the cell is given or not
			board_preset[row][column] = state;
		}

	}

	public int[][] solver() {
		//To Do => Please start coding your solution here

		int cell_counter = 0;			// used to track cell
		int region_counter = 0;		// used to track region
		int region_num = sudoku.getRegions().length;		// total number of region
		int region_cell_num;					// total number of cell in a region
		boolean backing_off = false;	// whether or not back off is needed

		for (int i = 0; i<sudoku.getValues().length;i++){						// set state to each cell, to indicate whether or not the cell is given
			for (int j = 0; j<sudoku.getValues()[0].length;j++){
				sudoku.setState(i,j,sudoku.getValue(i,j) != (-1));
			}
		}

		while(true){
			region_cell_num = sudoku.getRegion(region_counter).getCells().length;			// get number of cells in a region
			int row = sudoku.getRegion(region_counter).getCells()[cell_counter].getRow();		// get current row
			int column = sudoku.getRegion(region_counter).getCells()[cell_counter].getColumn();		// get current column

			if(!sudoku.getState(row,column)){				// if the cell is not given
				if(!insert(region_counter,cell_counter)){		// if new number cannot be inserted
					backing_off = true;									// need to backoff
				}else{
					backing_off = false;								// else, no need to back off, keep inserting
				}
			}

			if(backing_off){									// if need to back off
				if(cell_counter!=0){						// if this is not the first cell of a region
					cell_counter--;								// back to the previous cell
				}else{
					region_counter--;							// if this is the first cell, back to the previous region
					cell_counter = sudoku.getRegion(region_counter).getCells().length-1;	// set new cell number
				}
			}else{									// if no backing off
				if (cell_counter<region_cell_num-1){		// if there is a next cell
					cell_counter++;												// jump to the next cell
				}else if (region_counter<region_num-1){	// if there is a next region
					region_counter++;								// jump to the next region
					cell_counter = 0;							// set cell counter to 0
				}else {													// if no more cell and region
					break;												// break the while loop
				}
			}
		}

		return sudoku.getValues();			// return the answer
	}

	private boolean insert(int region, int cell){
		Cell cur_cell = sudoku.getRegion(region).getCells()[cell];		// get the cell needs to be inserted
		int row = cur_cell.getRow();										// get the row
		int column = cur_cell.getColumn();							// get the column
		int next_try = sudoku.getValue(row,column)+1;		// get the next possible answer
		if (next_try == 0){						// if next possible answer is 0, then set it to 1
			next_try = 1;
		}
		for (int i = next_try; i<=sudoku.getRegion(region).getCells().length;i++){	// keep trying to insert
			if(check(i,region,cell,row,column)){			// check if the insert is valid
				sudoku.setValue(row,column,i);					// set the value to cell
				return true;														// return true
			}
		}
		sudoku.setValue(row,column,-1);			// if the insert is not valid
		return false;												// return false
	}

	private boolean check(int next_try, int region, int cell, int row, int column){

		int board_width = sudoku.getValues()[0].length;		// get the width of the board
		int board_height = sudoku.getValues().length;			// get the height of the board
		//check around
		int[][] around = {{row-1,column-1},{row-1,column},{row-1,column+1},{row,column-1},{row,column+1},{row+1,column-1},{row+1,column},{row+1,column+1}};
		for (int i = 0; i<around.length; i++){
			if (around[i][0]>=0&&around[i][0]<board_height&&around[i][1]>=0&&around[i][1]<board_width){	// if the check position is valid
				if (sudoku.getValue(around[i][0],around[i][1]) == next_try){	// check if the insertion is valid
					return false;
				}
			}
		}
		// check within region
		for (int i = 0; i<sudoku.getRegion(region).getCells().length;i++){			
			int check_row = sudoku.getRegion(region).getCells()[i].getRow();
			int check_column = sudoku.getRegion(region).getCells()[i].getColumn();
			if(!(check_row == row && check_column == column)){
				if(sudoku.getValue(check_row,check_column)==next_try){
					return false;
				}
			}
		}
		// all test passed, return true
		return true;
	}


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int rows = sc.nextInt();
		int columns = sc.nextInt();
		int[][] board = new int[rows][columns];
		//Reading the board
		for (int i=0; i<rows; i++){
			for (int j=0; j<columns; j++){
				String value = sc.next();
				if (value.equals("-")) {
					board[i][j] = -1;
				}else {
					try {
						board[i][j] = Integer.valueOf(value);
					}catch(Exception e) {
						System.out.println("Ups, something went wrong");
					}
				}
			}
		}
		int regions = sc.nextInt();
		Game game = new Game();
	    game.sudoku = game.new Board(rows, columns, regions);
		game.sudoku.setValues(board);
		for (int i=0; i< regions;i++) {
			int num_cells = sc.nextInt();
			Game.Region new_region = game.new Region(num_cells);
			for (int j=0; j< num_cells; j++) {
				String cell = sc.next();
				String value1 = cell.substring(cell.indexOf("(") + 1, cell.indexOf(","));
				String value2 = cell.substring(cell.indexOf(",") + 1, cell.indexOf(")"));
				Game.Cell new_cell = game.new Cell(Integer.valueOf(value1)-1,Integer.valueOf(value2)-1);
				new_region.setCell(j, new_cell);
			}
			game.sudoku.setRegion(i, new_region);
		}
		int[][] answer = game.solver();
		for (int i=0; i<answer.length;i++) {
			for (int j=0; j<answer[0].length; j++) {
				System.out.print(answer[i][j]);
				if (j<answer[0].length -1) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}

}
