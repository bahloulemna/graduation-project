import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Test {
	//Edge est une classe qui nous permet de mieux modéliser un graphe
	static class Edge  {
		int from,to;
		double weight;
		Edge(int f,int t,double w){
			from = f;
			to = t;
			weight = w;
		}
	}
	//Fonctions prims ayant comme input un graphe dynamique et le point source
	public static double prim(ArrayList<ArrayList<Edge>> G,int s) {
		//Cas critique permet le débug si le graphe est mal construit
		if(G.isEmpty()) throw new NullPointerException("Le graphe est vide");
		//Implementation utilisant PriorityQueue
		//Cette dernière nous permet d'éviter certains calculs en 
                    prévilégiant
		//les noeud avec une accessibilité moindre
		//(Ceci est un ecriture de la priority queue avec la fonction de comparaison associée)
		double mst=0;
		PriorityQueue<Edge> pq = new PriorityQueue<>(
			(Object o1 , Object o2) -> {
			Edge first = (Edge)o1;
			Edge second = (Edge)o2;
			if(first.weight<second.weight)return -1;
			else if (first.weight>second.weight)return 1;
			else return 0;
		});

		//Début de l'algorithme de prim : Ajout des connexions avec la source 
		// à la priority queue
		for (Edge e:G.get(s)){
			pq.add(e);
		}
		//Création d'un tableau visited pour éviter le passage double dans un         noeud
		boolean[] visited = new boolean[G.size()];
		// La source est visitée par défaut
		visited[s]=true;
              //les itérations de l'algorithme passage au noeud le moin couteux à chaque fois d'ou                                           l'interret de la priority queue
		while(!pq.isEmpty()){
			Edge e = pq.peek();
			pq.poll();
			if(visited[e.from] && visited[e.to])continue;
			visited[e.from]=true;
			for(Edge edge:G.get(e.to)){
				if(!visited[edge.to]){
					pq.add(edge);
				}
			}
			visited[e.to]=true;
			mst+=e.weight;
		}
		//Fin de l'algorithme et retour de cout total pour établir une liaison complète
		return mst;
	}
	public static void main(String[] args) throws IOException {
		//input
		String path = ".//inputs//InHard";
		BufferedReader br = null;
		File file = new File(path);
		FileReader fr = new FileReader(file);
		br = new BufferedReader(fr);
		int n = Integer.parseInt(br.readLine());
		int s = Integer.parseInt(br.readLine());
		// initialisation ArrayList
		ArrayList<ArrayList<Edge>> G = new ArrayList<>();
		for(int i = 0 ; i <= n ; i++){
			G.add(new ArrayList<>());
		}
		//Lecture d'un graphe non ORIENTé PONDERé
		int nbEdge = Integer.parseInt(br.readLine());
		for(int i = 0 ; i < nbEdge ; i++){
			int a = Integer.parseInt(br.readLine());
			int b = Integer.parseInt(br.readLine());
			double w = Double.parseDouble(br.readLine());
			Edge tmp1 = new Edge(a,b,w);
			Edge tmp2 = new Edge(b,a,w);
			G.get(a).add(tmp1);
			G.get(b).add(tmp2);
		}
		//Résultat en minutes -- ALGORITHME DE PRIM
		double minutesOut = prim(G,s);
		System.out.println(minutesOut);
		//Conversion des minutes en heures pour l'affichage
		int heuresOut = 0;
		while(minutesOut>=60.0){
			heuresOut+=1;
			minutesOut-=60.0;
		}
		int mins=(int) Math.round(minutesOut+0.49);
		int hout=9-heuresOut;
		if(mins!=0){
			System.out.println((hout-1)+":"+(60-mins));
		}else{
			System.out.println(hout+":00");
		}
	}

}




