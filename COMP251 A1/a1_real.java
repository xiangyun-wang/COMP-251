// no collaborator

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class a1_real {

	public static int silence(int[] positions) {
		int nearest = positions.length;		// get the total number of students
		HashMap<Integer, Integer> Table = new HashMap<Integer, Integer>();	//create a hashmap
		int i = 0;	// to track current student location
		for (int key: positions){		// enhance for-loop to check each student
			// put the key into hashtable, the return value is the value originally stored at that location
			Integer tmp = Table.put(positions[i], i);
			if(tmp != null && i-tmp<nearest) {	// if the original value is not null, and a new nearest number detected
				nearest = i-tmp;	// update nearest
				tmp = i;			// update seat number
			}
			i++;	// increase student location by 1
		}
		return nearest;
	}

	public static void main(String[] args) {
		try {
			String path = args[0];
      		File myFile = new File(path);
      		Scanner sc = new Scanner(myFile);
      		int num_students = sc.nextInt();
      		int[] positions = new int[num_students];
      		for (int student = 0; student<num_students; student++){
				positions[student] =sc.nextInt();
      		}
      		sc.close();
      		int answer = silence(positions);
      		System.out.println(answer);
    	} catch (FileNotFoundException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
    	}
  	}
}
