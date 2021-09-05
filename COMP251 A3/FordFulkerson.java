import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();
		/* YOUR CODE GOES HERE*/
		int num_node = graph.getNbNodes();
		int[] color = new int[graph.getNbNodes()];	//0 is white, 1 is grey, 2 is black
		int if_path_found = visitDFS(source,destination,graph,color,path);
		Collections.reverse(path);
		return path;
	}

	public static int visitDFS(Integer node, Integer destination, WGraph graph, int[] color, ArrayList<Integer> path){
		color[node.intValue()] = 1;
		if(node.intValue()==destination.intValue()){
			path.add(destination);
			return 1;
		}
		for (int z = 0; z<graph.getEdges().size(); z++){
			Edge e = graph.getEdges().get(z);
			int[] n = e.nodes;
			if(n[0]==node.intValue()&&color[n[1]]==0&&e.weight!=0){
				if(visitDFS(new Integer(n[1]),destination,graph,color,path)==1){
					path.add(new Integer(n[0]));
					return 1;
				}
			}
		}
		color[node.intValue()] = 2;
		return 0;
	}

	private void check_graph(WGraph graph){
		// check if weight is negative
	}

	public static String fordfulkerson(WGraph graph){
		String answer="";
		int maxFlow = 0;
		int source = graph.getSource();
		int destination = graph.getDestination();
		//------- flow graph--------
		WGraph flow = new WGraph();
    for(Edge e:graph.getEdges()){
    	flow.addEdge(new Edge(e.nodes[0],e.nodes[1],0));
    }
    flow.setSource(source);
    flow.setDestination(destination);
		//--------residue----------
		WGraph res = new WGraph(graph);

		/* YOUR CODE GOES HERE		*/
		while(true){
			ArrayList<Integer> simple_path = pathDFS(source, destination, res);
			if(simple_path.size()==0){
				break;
			}
			int path_max_flow = -1;
			for (int i = 0; i<simple_path.size()-1; i++){
				int cur_flow = graph.getEdge(simple_path.get(i),simple_path.get(i+1)).weight;
				if (path_max_flow==-1){
					path_max_flow=cur_flow;
				}else if(path_max_flow>cur_flow){
					path_max_flow=cur_flow;
				}
			}
			for (int i = 0; i<simple_path.size()-1;i++){
				// update residue
				Edge tmp1 = res.getEdge(simple_path.get(i),simple_path.get(i+1));
				tmp1.weight -= path_max_flow;
				//res.setEdge(simple_path.get(i),simple_path.get(i+1),res.getEdge(simple_path.get(i),simple_path.get(i+1)).weight-path_max_flow);
				Edge tmp2 = res.getEdge(simple_path.get(i+1),simple_path.get(i));
				if(tmp2==null){
					res.addEdge(new Edge(simple_path.get(i+1),simple_path.get(i),path_max_flow));
				}else{
					tmp2.weight += path_max_flow;
					//res.setEdge(simple_path.get(i+1),simple_path.get(i),graph.getEdge(simple_path.get(i),simple_path.get(i+1)).weight+path_max_flow);
				}
				//update flow
				Edge tmp3 = flow.getEdge(simple_path.get(i),simple_path.get(i+1));
				if(tmp3!=null){	//forward edge
					tmp3.weight += path_max_flow;
				}else{	//backword edge
					tmp3 = flow.getEdge(simple_path.get(i+1),simple_path.get(i));
					tmp3.weight -= path_max_flow;
				}
			}
		}
		// calculate max flow
		for (Edge element: flow.getEdges()){
			if(element.nodes[0]==source){
				maxFlow += element.weight;
			}
		}
		answer += maxFlow + "\n" + flow.toString();
		return answer;
	}


	 public static void main(String[] args){
		String file = args[0];
		File f = new File(file);
		WGraph g = new WGraph(file);
	    System.out.println(fordfulkerson(g));
	 }
}
