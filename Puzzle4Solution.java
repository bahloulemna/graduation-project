import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Test {

	static public class UF {

		private int[] parent; // parent[i] = parent of i
		private byte[] rank; // rank[i] = rank of subtree rooted at i (never
								// more than 31)
		private int count; // number of components

		public UF(int n) {
			if (n < 0)
				throw new IllegalArgumentException();
			count = n;
			parent = new int[n];
			rank = new byte[n];
			for (int i = 0; i < n; i++) {
				parent[i] = i;
				rank[i] = 0;
			}
		}

		public int find(int p) {
			validate(p);
			while (p != parent[p]) {
				parent[p] = parent[parent[p]]; // path compression by          halving
				p = parent[p];
			}
			return p;
		}

		public int count() {
			return count;
		}

		public boolean connected(int p, int q) {
			return find(p) == find(q);
		}

		public void union(int p, int q) {
			int rootP = find(p);
			int rootQ = find(q);
			if (rootP == rootQ)
				return;
			if (rank[rootP] < rank[rootQ])
				parent[rootP] = rootQ;
			else if (rank[rootP] > rank[rootQ])
				parent[rootQ] = rootP;
			else {
				parent[rootQ] = rootP;
				rank[rootP]++;
			}
			count--;
		}

		private void validate(int p) {
			int n = parent.length;
			if (p < 0 || p >= n) {
				throw new IllegalArgumentException("index " + p
						+ " is not between 0 and " + (n - 1));
			}
		}
	}

	public static void main(String[] args) throws IOException {

		// input porteFeulle
		long debut = System.currentTimeMillis();
		String path = ".//inputs//in.txt";
		File file = new File(path);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		// déclaration des structures
		HashSet<String> dictionnaire = new HashSet<String>();
		HashMap<String, Double> risques = new HashMap<String, Double>();
		HashMap<String, Integer> wallet = new HashMap<String, Integer>();
		HashMap<String, Integer> toInt = new HashMap<String, Integer>();

		// Déclaration des variables
		int i, k, nbCorr;
		int n = Integer.parseInt(br.readLine());
		int nbActionTot = 0;
		String action;
		String action2;
		int nbActions;
		double corr, x, y;
		double risque;
		double risqueOut = 0;
		UF uf = new UF(n);
		// CMPLX O(n)
		for (i = 0; i < n; i++) {
			action = br.readLine();
			nbActions = Integer.parseInt(br.readLine());
			nbActionTot += nbActions;
			wallet.put(action, nbActions);
			toInt.put(action, i);
			dictionnaire.add(action);
		}

		k = Integer.parseInt(br.readLine());
		// CMPLX O(k)
		for (i = 0; i < k; i++) {
			action = br.readLine();
			risque = Double.parseDouble(br.readLine());
			if (dictionnaire.contains(action)) {
				risques.put(action, risque);
			}
		}

		
		nbCorr = Integer.parseInt(br.readLine());
		int c = 0;
		for (i = 0; i < nbCorr; i++) {
			action = br.readLine();
			action2 = br.readLine();
			corr = Double.parseDouble(br.readLine());
			if (corr >= 0.5 && dictionnaire.contains(action)
					&& dictionnaire.contains(action2)) {
				x = risques.get(action);
				y = risques.get(action2);
				if (x > y) {
					uf.union(uf.find(toInt.get(action)),
							uf.find(toInt.get(action2)));
					dictionnaire.remove(action2);
					c = wallet.get(action2);
					wallet.remove(action2);
					c += wallet.get(action);
					wallet.put(action, c);
				} else if (y > x) {
					uf.union(uf.find(toInt.get(action2)),
							uf.find(toInt.get(action)));
					dictionnaire.remove(action);
					c = wallet.get(action);
					wallet.remove(action);
					c += wallet.get(action2);
					wallet.put(action2, c);
				}
			}
		}

		// CMPLX O(n)
		int calcAction = 0;
		Iterator<String> ip = dictionnaire.iterator();
		while (ip.hasNext()) {
			double e = 1 / (double) nbActionTot;
			String s = ip.next();
			calcAction += wallet.get(s);
			risqueOut += e * ((double) wallet.get(s)) * risques.get(s);
		}

		System.out.println(risqueOut);
		System.out.println("tmp exec: " + (System.currentTimeMillis() - debut));

	}

}
