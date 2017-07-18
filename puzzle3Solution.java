import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Test {

	// Product est objet comparable on peut l'utiliser comme objet dans
	// l'implementation
	// d'un treeSet ou d'une classe qui utilise une comparaison pour classer ses
	// élements
	static class Product implements Comparable<Product> {
		private char type;
		private int fixVal;
		private int initVal;
		private double pente;

		public Product(char t, int f) {
			this.type = t;
			this.fixVal = f;
			this.initVal = 0;
			this.pente = 0;
		}

		public Product(char t, int v, double p) {
			this.type = t;
			this.fixVal = 0;
			this.initVal = v;
			this.pente = p;
		}

		public String getVal() {
			if (type == 'F') {
				return String.valueOf(this.fixVal);
			} else {
				return (String.valueOf(this.initVal)) + " "
						+ (String.valueOf(pente));
			}
		}

		public char getType() {
			return this.type;
		}

		public Double calc() {
			if (type == 'F') {
				return (double) this.fixVal * 12;
			}
			if (type == 'V') {
			return (double) ((double) this.initVal * 12 + pente * 66);
			}
			return 0.0;
		}

		public int compareTo(Product P) {
			if (this.calc() > P.calc()) {
				return -1;
			} else if (this.calc() < P.calc()) {
				return 1;
			} else if (this.getType() == 'F' && P.getType() == 'V') {
				return -1;
			} else if (this.getType() == 'V' && P.getType() == 'F') {
				return 1;
			} else {
				return 0;
			}
		}

	}

	public static void main(String[] args) throws IOException {
		String path = ".//inputs//in.txt";
		File file = new File(path);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		Scanner sc = new Scanner(br.readLine());
		int n = sc.nextInt();
		int k = sc.nextInt();
		int b = sc.nextInt();
		int v = sc.nextInt();
		// TreeMap nous permet de classer les objets avec un ordre croissant
		// pour les clefs
		TreeMap<Integer, Integer> M = new TreeMap<Integer, Integer>();
		for (int i = 0; i < n; i++) {
			sc = new Scanner(br.readLine());
			int lo = sc.nextInt();
			int hi = sc.nextInt();
			for (int j = lo; j <= hi; j++) {
				if (M.containsKey(j)) {
					M.put(j, M.get(j) + 1);
				} else {
					M.put(j, 1);
				}
			}
		}
		// le treeSet permet d'avoir une liste croissante d'éléments
		// deux possibilté peuvent coexister pour avoir une liste
		// décroissant :
		// 1- changer la méthode de comparaison
		// 2- lire la treeSet à l'envers
		// L'objet Product est utile pour avoir un output simple comme il est
		// demandé
		TreeSet<Product> outputBuild = new TreeSet<Product>();
		for (int i = 0; i < b; i++) {
			sc = new Scanner(br.readLine());
			int val = sc.nextInt();
			Product p = new Product('F', val);
			if (M.containsKey((int) Math.round(p.calc()))) {
				if (M.get((int) Math.round(p.calc())) >= k) {
					outputBuild.add(p);
				}
			}
		}
		for (int i = 0; i < v; i++) {
			sc = new Scanner(br.readLine());
			int initVal = sc.nextInt();
			double p = sc.nextDouble();
			Product pr = new Product('V', initVal, p);
			int val = (int) Math.round(pr.calc());
			if (M.containsKey(val)) {
				if (M.get(val) >= k) {
					outputBuild.add(pr);
				}
			}
		}

		Iterator<Product> it = outputBuild.iterator();
		while (it.hasNext()) {
			Product pr = it.next();
			System.out.println(pr.getType() + " " + pr.getVal() + " "
					+ (int) Math.round(pr.calc()));
		}
	}
}







