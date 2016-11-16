package util;

import java.util.ArrayList;

public class BuildQueue<T>  {
	private ArrayList<T> buildableObjects;
	private ArrayList<Integer> buildCosts;
	
	/**
	 * Method used to progress the building of objects in the queue.  
	 * 
	 * @param buildAmount The amount of progress to be put towards building the next object in the queue
	 * @return returns the object built if there has been enough build progress to finish construction, otherwise returns null
	 */
	public T build(int buildAmount){
		T t = null;
		buildCosts.set(0, buildCosts.get(0).intValue() - buildAmount);
		if(buildCosts.get(0).intValue() <= 0){
			t = buildableObjects.get(0);
			buildableObjects.remove(t);
		}
		return t;
	}
	
	public ArrayList<T> getBuildableObjects(){
		return buildableObjects;
	}
}
