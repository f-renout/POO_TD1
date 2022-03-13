package lapin;

import java.util.Comparator;

public class Lapin {
	public static final int AGE = 0;
	public static final int TAILLE = 1;
	public static final int POIDS = 2;
	
	protected int ID;
	protected int age;
	protected double taille;
	protected double poids;
	
	public Lapin(int ID, int age, float taille, float poids) {
		this.ID = ID;
		this.age = age;
		this.taille = taille;
		this.poids = poids;
	}
	
	public String toString() {
		return "Lapin "+this.ID+" a "+this.age+" ans, mesure "+this.taille+" et pèse "+this.poids;
	}
}

class CompareLapinAge implements Comparator<Lapin> {

	@Override
	public int compare(Lapin o1, Lapin o2) {
		int diff = o1.age - o2.age;
		if(diff == 0) 
			return o1.ID - o2.ID;
		return diff;
	}
}

class CompareLapinTaille implements Comparator<Lapin> {

	@Override
	public int compare(Lapin o1, Lapin o2) {
		double diff = o1.taille - o2.taille;
		if(diff == 0) {
			return o1.ID - o2.ID;
		} else if(diff < 0) {
			return -1;
		}
		return 1;
	}
}

class CompareLapinPoids implements Comparator<Lapin> {

	@Override
	public int compare(Lapin o1, Lapin o2) {
		double diff = o1.poids - o2.poids;
		if(diff == 0) {
			return o1.ID - o2.ID;
		} else if(diff < 0) {
			return -1;
		}
		return 1;
	}
}

