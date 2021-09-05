import java.util.*;
import java.lang.*;
import java.io.*;

public class Midterm {
	private static int[][] dp_table;
	private static int[] penalization;


	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int chairs;
		try {
			chairs = Integer.valueOf(reader.readLine());
			penalization = new int[chairs];
			for (int i=0; i< chairs; i++) {
				penalization[i] = Integer.valueOf(reader.readLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int answer = lost_marks(penalization);
		System.out.println(answer);
	}

	public static int lost_marks(int[] penalization) {
		//To Do => Please start coding your solution here
		int max = 2*500*penalization.length;
		dp_table = new int[penalization.length][penalization.length];
		for (int i = 0; i<penalization.length; i++) {
			for(int j = 0; j<penalization.length; j++) {
				dp_table[i][j] = max;	// set dp table to 2*500*number of chairs
				// the worst case would be jumping back and forth from 1 to a larger number
				// then the chair visited will be 2*the number of chairs
				// since each chair would not exceed 500 points, then the highest total points would be 2*500*number of chairs
			}
		}
		dp_table[0][0] = 0;		// base case
		int optimal_solution = max;		// preset optimal solution to largest number
		int possible_solution = -1;						// set possible solution
		for (int i = 1; i<penalization.length;i++){			// loop over all possible last forward jump to the final chair
			possible_solution = find_optimal(penalization.length-1, i, penalization);	// find the possible optimal solution in each case
			if(possible_solution!=max&&(optimal_solution==max||possible_solution<optimal_solution)){
				optimal_solution = possible_solution;			// update optimal solution
			}
		}
		return optimal_solution;
	}

	private static int find_optimal(int index, int last_step, int[] penalization){

		int max = 2*500*penalization.length;	// max penalty
		// base case and termination case
		if(last_step==0&&index==0){
			return 0;
		}else if(last_step==0&&index!=0) {
			return max;
		}

		//recursion
		int possible_forward_from = index-last_step;		//possible previous chairs
		int possible_backward_from = index+last_step;
		int forward_optimal = max;
		int backward_optimal = max;
		if(possible_forward_from>=0&&possible_forward_from<penalization.length){		//if possible previous chair is valid
			if(dp_table[last_step-1][possible_forward_from]==max) {				// if not in dp table
				dp_table[last_step-1][possible_forward_from] = find_optimal(possible_forward_from, last_step-1,penalization);	//update dptable
			}
			forward_optimal = dp_table[last_step-1][possible_forward_from];		//get from dp table
		}
		if(possible_backward_from>=0&&possible_backward_from<penalization.length){	//if possible previous chair is valid
			if(dp_table[last_step][possible_backward_from]==max) {				// if not in dp table
				//backward_optimal = dp_table[last_step][possible_backward_from];
				dp_table[last_step][possible_backward_from] = find_optimal(possible_backward_from, last_step,penalization);	//update dptable
			}
			backward_optimal = dp_table[last_step][possible_backward_from];	//get from dp table
		}

		return penalization[index]+Math.min(backward_optimal,forward_optimal);	//return optimal solution

	}

}
