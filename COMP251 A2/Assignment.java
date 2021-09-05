import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;

	protected Assignment() {
	}

	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}

	/**
	 * This method is used to sort to compare assignment objects for sorting.
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		// TODO Implement this
		// deadline priority, assignment with closer deadline will be placed at front
		if(a1.deadline==a2.deadline){		// if deadline is the same
			if(a1.weight>a2.weight){			// place the assignment with higher weight at front
				return -1;
			}else if(a1.weight<a2.weight){
				return 1;
			}else{
				return 0;					// weight is the same, return 0;
			}
		}else if(a1.deadline>a2.deadline){
			return 1;
		}else{
			return -1;
		}
	}
}
