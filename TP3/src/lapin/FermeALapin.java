package lapin;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;


public class FermeALapin {
	protected ArrayList<Lapin> lapins = null;
	protected int lastIdUsed = 0;
	protected ArrayList<Lapin> age_index = null;
	protected ArrayList<Lapin> taille_index = null;
	protected ArrayList<Lapin> poids_index = null;
			
	public FermeALapin() {
		lapins = new ArrayList<Lapin>();
		age_index = new ArrayList<Lapin>();
		taille_index = new ArrayList<Lapin>();
		poids_index = new ArrayList<Lapin>();
	}
	
	public FermeALapin(ArrayList<Lapin> lapins) {
		this.lapins = lapins;
		generateIndex();
	}

	public void addLapin(Lapin l) {
		lapins.add(l);
		generateIndex(); // not the best way to do it (but it works)
	}
	
	public void deleteLaptin(int id) {
		for(int i=0 ; i<this.lapins.size() ; i++) {
			if(this.lapins.get(i).ID == id) {
				_remove(this.lapins.get(i));
			}
		}
	}

	public void deleteLapin(Lapin l) {
		_remove(l);
	}
	
	
	private void _remove(Lapin l) {
		this.lapins.remove(l);
		this.age_index.remove(l);
		this.taille_index.remove(l);
		this.poids_index.remove(l);
	}

	// We replaced the normal call by the threaded one
	public void generateLapinList(int amount) {
		generateLapinList(amount, null, null);
	}

	// We replaced the normal call by the threaded one
	public void generateIndex() {
		generateIndex(null, null);
	}
	
	public void generateLapinList(int amount, PropertyChangeListener listener, 
			OnGeneratorEndListener endListener) {
		SwingWorker<Void, Void> worker = new RunGenerator(this, amount, false, endListener);
		worker.addPropertyChangeListener(listener);
		worker.execute();
	}

	public void generateIndex(PropertyChangeListener listener, 
			OnGeneratorEndListener endListener) {
		SwingWorker<Void, Void> worker = new RunGenerator(this, 0, true, endListener);
		worker.addPropertyChangeListener(listener);
		worker.execute();
	}
	
//	public void generateLapinList(int amount) {
//		this.lapins = new ArrayList<Lapin>();
//		Random rand = new Random();
//		
//		for(int i=1 ; i<=amount ; i++) {
//			int id = i;
//			int age = (int) (1+rand.nextDouble()*(15.0-1.0));
//			float taille = (float) (0.2+rand.nextDouble()*(20.0-0.2));
//			float poids = (float) (5.0+rand.nextDouble()*(45.0-5.0));
//			Lapin l = new Lapin(id, age, taille, poids);
//			this.lapins.add(l);
//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		this.lastIdUsed = amount;
//		generateIndex();
//	}
//	
//	private void generateIndex() {
//		age_index = new ArrayList<Lapin>(this.lapins);
//		Collections.sort(age_index, new CompareLapinAge()); 
//		try {
//			Thread.sleep(400);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		taille_index = new ArrayList<Lapin>(this.lapins);
//		Collections.sort(taille_index, new CompareLapinTaille());   
//		try {
//			Thread.sleep(400);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		poids_index = new ArrayList<Lapin>(this.lapins);
//		Collections.sort(poids_index, new CompareLapinPoids());   
//		try {
//			Thread.sleep(400);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	
	public Iterator<Lapin> getLapinList(int type, boolean ascending) throws Exception {
		if(type == Lapin.AGE) {
			if(ascending)
				return age_index.iterator();
			else
				return new ReverseIterator<Lapin>(age_index);
		} else if(type == Lapin.TAILLE) {
			if(ascending)
				return taille_index.iterator();
			else
				return new ReverseIterator<Lapin>(taille_index);
		} else if(type == Lapin.POIDS) {
			if(ascending)
				return poids_index.iterator();
			else
				return new ReverseIterator<Lapin>(poids_index);
		} else {
			throw new Exception("Sorting type not supported.");
		}
	}
	
}

class RunGenerator extends SwingWorker<Void, Void> {
	private FermeALapin ferme;
	private int amount;
	private boolean indexingOnly;
	private OnGeneratorEndListener endListener;
	
	public RunGenerator(FermeALapin ferme, int amount, boolean indexingOnly,
			OnGeneratorEndListener endListner) {
		this.ferme = ferme;
		this.amount = amount;
		this.indexingOnly = indexingOnly;
		this.endListener = endListner;
	}
	
    // Task Executed in background thread.
    @Override
    public Void doInBackground() {
        double progress = 0;
        //Initialize progress property.
        setProgress(0);
        
        ferme.lapins = new ArrayList<Lapin>();
		Random rand = new Random();
		double progress_step;
		if(!indexingOnly) {
			progress_step = 50/amount;
			
			for(int i=1 ; i<=amount ; i++) {
				int id = i;
				int age = (int) (1+rand.nextDouble()*(15.0-1.0));
				float taille = (float) (0.2+rand.nextDouble()*(20.0-0.2));
				float poids = (float) (5.0+rand.nextDouble()*(45.0-5.0));
				Lapin l = new Lapin(id, age, taille, poids);
				ferme.lapins.add(l);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				progress += progress_step;
	            setProgress(Math.min((int)progress, 100));
			}
			ferme.lastIdUsed = amount;
			progress_step = 17.0;
		} else {
			progress_step = 34.0;
		}
		
		// Launch the indexing
		ferme.age_index = new ArrayList<Lapin>(ferme.lapins);
		Collections.sort(ferme.age_index, new CompareLapinAge()); 
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		progress += progress_step;
        setProgress(Math.min((int)progress, 100));
		ferme.taille_index = new ArrayList<Lapin>(ferme.lapins);
		Collections.sort(ferme.taille_index, new CompareLapinTaille());   
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		progress += progress_step;
        setProgress(Math.min((int)progress, 100));
		ferme.poids_index = new ArrayList<Lapin>(ferme.lapins);
		Collections.sort(ferme.poids_index, new CompareLapinPoids());   
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        setProgress(100);
		return null;
    }

    @Override
    public void done() {
    	setProgress(0); 
    	if (endListener != null)
    		endListener.onGeneratorEnd();
    }
}

class ReverseIterator<T> implements Iterator<T>, Iterable<T> {

    private final List<T> list;
    private int position;

    public ReverseIterator(List<T> list) {
        this.list = list;
        this.position = list.size() - 1;
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return position >= 0;
    }

    @Override
    public T next() {
        return list.get(position--);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}

interface OnGeneratorEndListener {
	public void onGeneratorEnd();
}