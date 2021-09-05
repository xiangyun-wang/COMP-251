import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Honors {

	public static int min_moves(int[][] board) {
		//int[][] color = new int[board.length][board[0].length];	// 0 is white, 1 is grey, 2 is black
		int board_width = board[0].length;
		int board_height = board.length;
		int total_length = board_width*board_height;
		int[] color = new int[total_length];
		//int[][] distance = new int[board.length][board[0].length];
		int[] distance = new int[total_length];
		//Queue<int[]> Q = new LinkedList<int[]>();
		Queue<Integer> Q = new LinkedList<Integer>();
		//int[] start = {0,0};
		Q.add(0);
		color[0] = 1;
		int target = total_length-1;
		//int target_y = board.length-1;
		//int target_x = board[0].length-1;
		while(Q.size()!=0){
			//int[] tmp_vertex = Q.remove();
			int tmp_vertex = Q.remove();
			if (tmp_vertex==target) return distance[target];
			int current_y = tmp_vertex/board_width;
			int current_x = tmp_vertex%board_width;
			int current_num = board[current_y][current_x];
			// int[][] possible_connected = {{current_y-current_num,current_x},{current_y,current_x+current_num},{current_y+current_num,current_x},{current_y,current_x-current_num}};
			// for (int[] element : possible_connected){
			// 	//int x = element[1];
			// 	//int y = element[0];
			// 	if(element[0]<board_height&&element[0]>-1&&element[1]<board_width&&element[1]>-1){
			// 		int tmp_index = element[0]*board_width+element[1];
			// 		if(color[tmp_index]==0){
			// 			color[tmp_index] = 1;
			// 			distance[tmp_index] = distance[tmp_vertex]+1;
			// 			if (tmp_index==target) return distance[target];
			// 			Q.add(tmp_index);
			// 		}
			// 	}
			// }

			if(current_y-current_num<board_height&&current_y-current_num>-1&&current_x<board_width&&current_x>-1){
				int tmp_index = (current_y-current_num)*board_width+current_x;
				if(color[tmp_index]==0){
					color[tmp_index] = 1;
					distance[tmp_index] = distance[tmp_vertex]+1;
					//if (tmp_index==target) return distance[target];
					Q.add(tmp_index);
				}
			}
			if(current_y<board_height&&current_y>-1&&current_x+current_num<board_width&&current_x+current_num>-1){
				int tmp_index = current_y*board_width+current_x+current_num;
				if(color[tmp_index]==0){
					color[tmp_index] = 1;
					distance[tmp_index] = distance[tmp_vertex]+1;
					//if (tmp_index==target) return distance[target];
					Q.add(tmp_index);
				}
			}
			if(current_y+current_num<board_height&&current_y+current_num>-1&&current_x<board_width&&current_x>-1){
				int tmp_index = (current_y+current_num)*board_width+current_x;
				if(color[tmp_index]==0){
					color[tmp_index] = 1;
					distance[tmp_index] = distance[tmp_vertex]+1;
					//if (tmp_index==target) return distance[target];
					Q.add(tmp_index);
				}
			}
			if(current_y<board_height&&current_y>-1&&current_x-current_num<board_width&&current_x-current_num>-1){
				int tmp_index = current_y*board_width+current_x-current_num;
				if(color[tmp_index]==0){
					color[tmp_index] = 1;
					distance[tmp_index] = distance[tmp_vertex]+1;
					//if (tmp_index==target) return distance[target];
					Q.add(tmp_index);
				}
			}
			//color[tmp_vertex] = 2;
		}
		return -1; // placeholder
	}

	public void test(String filename) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(filename));
		int num_rows = sc.nextInt();
		int num_columns = sc.nextInt();
		int [][]board = new int[num_rows][num_columns];
		for (int i=0; i<num_rows; i++) {
			char line[] = sc.next().toCharArray();
			for (int j=0; j<num_columns; j++) {
				board[i][j] = (int)(line[j]-'0');
			}

		}
		sc.close();
		int answer = min_moves(board);
		System.out.println(answer);
	}

	public static void main(String[] args) throws FileNotFoundException{
		Honors honors = new Honors();
		honors.test(args[0]); // run 'javac Honors.java' to compile, then run 'java Honors testfilename'

		// or you can test like this
		// int [][]board = {1,2,3}; // not actual example
		// int answer = min_moves(board);
		// System.out.println(answer);
	}

}
