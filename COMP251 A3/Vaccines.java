import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Vaccines {

	public class Country{
		private int ID;
		private int vaccine_threshold;
		private int vaccine_to_receive;
		private ArrayList<Integer> allies_ID;
		private ArrayList<Integer> allies_vaccine;
		private int color;
		public Country() {
			this.allies_ID = new ArrayList<Integer>();
			this.allies_vaccine = new ArrayList<Integer>();
			this.vaccine_threshold = 0;
			this.vaccine_to_receive = 0;
			this.color = 0;
		}

		public int get_color(){
			return this.color;
		}
		public void change_color(){
			this.color = 1;
		}

		public int get_ID() {
			return this.ID;
		}
		public int get_vaccine_threshold() {
			return this.vaccine_threshold;
		}
		public ArrayList<Integer> get_all_allies_ID() {
			return allies_ID;
		}
		public ArrayList<Integer> get_all_allies_vaccine() {
			return allies_vaccine;
		}
		public int get_allies_ID(int index) {
			return allies_ID.get(index);
		}
		public int get_allies_vaccine(int index) {
			return allies_vaccine.get(index);
		}
		public int get_num_allies() {
			return allies_ID.size();
		}
		public int get_vaccines_to_receive() {
			return vaccine_to_receive;
		}
		public void set_allies_ID(int value) {
			allies_ID.add(value);
		}
		public void set_allies_vaccine(int value) {
			allies_vaccine.add(value);
		}
		public void set_ID(int value) {
			this.ID = value;
		}
		public void set_vaccine_threshold(int value) {
			this.vaccine_threshold = value;
		}
		public void set_vaccines_to_receive(int value) {
			this.vaccine_to_receive = value;
		}

	}

	public int vaccines(Country[] graph){
		//Computing the exceed in vaccines per country.
		//graph[0].set_vaccine_threshold(0);
		graph[0].change_color();
		Queue<Country> Q = new LinkedList<Country>();
		Q.add(graph[0]);

		int country_success = graph.length-1;
		while(Q.size()!=0){
			Country current_country = Q.remove();
			ArrayList<Integer> ally = current_country.get_all_allies_ID();
			ArrayList<Integer> share = current_country.get_all_allies_vaccine();
			for (int i = 0; i<ally.size(); i++){
				Country country = graph[ally.get(i).intValue()-1];
				if(country.get_color()==0){
					//System.out.println("Vaccine Before: " + country.get_vaccines_to_receive());
					country.set_vaccines_to_receive(country.get_vaccines_to_receive()-share.get(i).intValue());
					//System.out.println("Vaccine After: " + country.get_vaccines_to_receive());
					//System.out.println("Check if shallow copy: " + graph[ally.get(i).intValue()-1].get_vaccines_to_receive());
					if (country.get_vaccine_threshold()>country.get_vaccines_to_receive()){
						Q.add(country);
						country.change_color();
						country_success--;
					}
				}
			}
			// if(current_country.get_vaccine_threshold()>=current_country.get_vaccines_to_receive()||current_country.get_vaccine_threshold()==0){
			// 	country_success--;
			// 	ArrayList<Integer> ally = current_country.get_all_allies_ID();
			// 	ArrayList<Integer> share = current_country.get_all_allies_vaccine();
			// 	current_country.change_color();
			// 	for (int i = 0; i<ally.size(); i++){
			// 		Country country = graph[ally.get(i).intValue()-1];
			// 		if(country.get_color()==0){
			// 			System.out.println("Vaccine Before: " + country.get_vaccines_to_receive());
			// 			country.set_vaccines_to_receive(country.get_vaccines_to_receive()-share.get(i));
			// 			System.out.println("Vaccine After: " + country.get_vaccines_to_receive());
			// 			System.out.println("Check if shallow copy: " + graph[ally.get(i).intValue()-1].get_vaccines_to_receive());
			// 			Q.add(country);
			// 		}
			// 	}
			// }
		}
		return country_success;
	}

	public int country_visit(Country current_country, Country[] graph){
		if(current_country.get_vaccine_threshold()>current_country.get_vaccines_to_receive()||current_country.get_vaccine_threshold()==0){
			ArrayList<Integer> ally = current_country.get_all_allies_ID();
			ArrayList<Integer> share = current_country.get_all_allies_vaccine();
			current_country.change_color();
			int country_turned_gray = 1;
			for (int i = 0; i<ally.size(); i++){
				Country country = graph[ally.get(i).intValue()-1];
				if(country.get_color()==0){
					country.set_vaccines_to_receive(country.get_vaccines_to_receive()-share.get(i));
					country_turned_gray+=country_visit(country, graph);
				}
			}
			return country_turned_gray;
		}
		return 0;
	}

	public void test(String filename) throws FileNotFoundException {
		Vaccines hern = new Vaccines();
		Scanner sc = new Scanner(new File(filename));
		int num_countries = sc.nextInt();
		Country[] graph = new Country[num_countries];
		for (int i=0; i<num_countries; i++) {
			graph[i] = hern.new Country();
		}
		for (int i=0; i<num_countries; i++) {
			if (!sc.hasNext()) {
                sc.close();
                sc = new Scanner(new File(filename + ".2"));
            }
			int amount_vaccine = sc.nextInt();
			graph[i].set_ID(i+1);
			graph[i].set_vaccine_threshold(amount_vaccine);
			int other_countries = sc.nextInt();
			for (int j =0; j<other_countries; j++) {
				int neighbor = sc.nextInt();
				int vaccine = sc.nextInt();
				graph[neighbor -1].set_allies_ID(i+1);
				graph[neighbor -1].set_allies_vaccine(vaccine);
				graph[i].set_vaccines_to_receive(graph[i].get_vaccines_to_receive() + vaccine);
			}
		}
		sc.close();
		//System.out.println("Check for error: "+graph.length);
		//System.out.println("First threshold: "+graph[0].get_vaccine_threshold());
		//System.out.println("First received: "+graph[0].get_vaccines_to_receive());
		int answer = vaccines(graph);
		System.out.println(answer);
	}

	public static void main(String[] args) throws FileNotFoundException{
		Vaccines vaccines = new Vaccines();
		vaccines.test(args[0]); // run 'javac Vaccines.java' to compile, then run 'java Vaccines testfilename'
	}

}
