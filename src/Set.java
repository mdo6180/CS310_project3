import java.util.Collection; 

/**
 * A Set using HashTable.
 * 
 * @author minhquando
 *
 * @param <T>
 */
class Set<T extends Comparable<T>>{

	//-------------------------------------------------------------
	// DO NOT EDIT ANYTHING FOR THIS SECTION EXCEPT TO ADD JAVADOCS
	//-------------------------------------------------------------
	
	/**
	 * 
	 */
	private HashTable<T> storage = new HashTable<>(5);

	/**
	 * Retrieves the number of elements in the set (aka the cardinality).
	 * @return the cardinality of the set.
	 */
	public int size() {
		return storage.size();
	}
	
	/**
	 * Adds a value to the set if it is not already in the set.
	 * @param value value to be added to the set.
	 * @return true if value added to the set, false otherwise.
	 */
	public boolean add(T value){
		return storage.add(value);
	}

	/**
	 * Used to determine if value is in set.
	 * @param value
	 * @return true if value in set, false otherwise.
	 */
	public boolean contains(T value){
		return storage.contains(value);
	}

	/**
	 * Removes a value from the set.
	 * @param value value to be removed
	 * @return true if value was removed from set, false otherwise (i.e. if value is not in the set or if value is null).
	 */
	public boolean remove(T value){
		return storage.remove(value);
	}

	/**
	 * Retrieve all the elements of the set in the form of a string.
	 * @return string representation of the set.
	 */
	public String toString(){
		return storage.toString();
	}

	/**
	 * Retrieve all the elements of the set in the form of an Object array.
	 * @return array containing all the elements in the set.
	 */
	public Object[] toArray(){
		return storage.toArray();
	}

	//-------------------------------------------------------------
	// END OF PROVIDED "DO NOT EDIT" SECTION 
	//-------------------------------------------------------------

	/**
	 * Accept a collection of values and add them into set one by one.
	 * @param c collection of values
	 * @return the number of values successfully added.
	 */
	public int addAll(Collection<T> c){
		int successes = 0;					// number of values successfully added to the table
		
		for(T element : c) {
			if(add(element) == true) {		// use the iterator to add all the elements of the collection into the set
				successes++;				
			}
		}
		
		return successes;
	}
			
	/**
	 * Construct and return the intersection set of this and other.
	 * Intersection set should include all values that are in both this set and other.
	 * Original sets are not be modified.
	 * @param other other set
	 * @return set containing all values that are in both this set and other.
	 */
	@SuppressWarnings("unchecked")
	public Set<T> intersection(Set<T> other){
		Set<T> intersection = new Set<T>();
		
		Object[] thisArray = this.toArray();
		
		for(int i = 0; i < thisArray.length; i++) {
			if(other.contains((T) thisArray[i]) == true) {		// if both the other set and this set contains the element
				intersection.add((T) thisArray[i]);				// add the element to the intersection set 
			}
		}
		
		return intersection;
	}
	
	/**
	 * Construct and return the union set of this and other.
	 * Union set includes all values that belongs to at least one of this set and other.
	 * Original sets are not be modified.
	 * @param other other set
	 * @return set containing all values that belongs to at least one of this set and other.
	 */
	@SuppressWarnings("unchecked")
	public Set<T> union(Set<T> other){
		Set<T> union = new Set<T>();
		
		Object[] otherArray = other.toArray();
		Object[] thisArray = this.toArray();
		//union.addAll((Collection<T>) java.util.Arrays.asList(thisArray));		// (old code) add all the elements in this set to the union set
		
		for(int i = 0; i < thisArray.length; i++) {						// (new code: quick fix) add all the elements in this set to the union set
			union.add((T) thisArray[i]);
		}
		
		for(int i = 0; i < otherArray.length; i++) {						// add all of the elements in the other set that are not included in this set
			if(union.contains((T) otherArray[i]) == false) {	
				union.add((T) otherArray[i]);					
			}
		}
		
		return union;
	}

	/**
	 * Construct and return the difference set: this - other.
	 * Result includes all values in this set but not in other.
	 * Original sets are not be modified.
	 * @param other other set
	 * @return set containing all values in this set but not in other.
	 */
	@SuppressWarnings("unchecked")
	public Set<T> difference(Set<T> other){
		Set<T> difference = new Set<T>();
		Object[] thisArray = this.toArray();
		
		for(int i = 0; i < thisArray.length; i++) {		// add all elements that are only in this set and not in the other set to the difference set
			if(other.contains((T) thisArray[i]) == false) {		
				difference.add((T) thisArray[i]);
			}
		}
		
		return difference;
	}

	/**
	 * Construct and return the symmetric difference set.
	 * Result includes all values in exactly one set of this set and other but not both.
	 * Original sets are not be modified.
	 * @param other other set
	 * @return set containing all values in this set and other but not both.
	 */
	@SuppressWarnings("unchecked")
	public Set<T> symmetricDifference(Set<T> other){
		
		Set<T> union = this.union(other);
		Set<T> intersection = this.intersection(other);
		
		return union.difference(intersection);				// symmetric difference = union - intersection
	}

	/**
	 * Used to determine if this set is a subset of the other set.
	 * @param possible superset of this set. 
	 * @return true if this set is a subset of other, false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean isSubset(Set<T> other){
		
		if(this.size() == 0) {			// if this set is the null set then the null set is a subset of every set including the null set itself
			return true;				// therefore, whether other is a null set or not, this set is a subset of other
			
		} else {						
			if(other.size() == 0) {		// if this set is not the null set and other is the null set, then this set is not a subset of other 
				return false;	
			} 
		}
		
		Object[] thisArray = this.toArray();
		
		for(int i = 0; i < thisArray.length; i++) {
			if(other.contains((T) thisArray[i]) == false) {		// if any elements of this set is not in the other set, 
				return false;									// then this set is not a subset of the other set.
			}
		}
		
		return true;
	}
	
	/**
	 * Used to determine if this set has any overlap with another set.
	 * @param other set to compare this set too. 
	 * @return true if if there is no overlap between this set and other; return false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean isDisjoint(Set<T> other){
		
		if(this.intersection(other).size() == 0) {				// if the intersection is the null set, then this set and other are disjointed
			return true;
		} else {
			return false;
		}
	}
	
	
	//-------------------------------------------------------------
	// Main Method For Your Testing -- Edit all you want
	//-------------------------------------------------------------

	public static void main(String args[]){
		// arrays only used for testing here
		Integer[] data1 = { 1, 2, 3,   5,    7 };
		Integer[] data2 = {    2,   4, 5, 6    };
		String[] duplicate = {"a","a","a","b","c","b","c","d","a","e","c","b"};
		Set<Integer> set1 = new Set<>();
		Set<Integer> set2 = new Set<>();
		Set<String> noduplicate = new Set<>();
		
		//addAll
		if (set1.addAll(java.util.Arrays.asList(data1))==5 &&
			set2.addAll(java.util.Arrays.asList(data2))==4 &&
			noduplicate.addAll(java.util.Arrays.asList(duplicate)) == 5){
			System.out.println("Yay 1");
		}
		
		//System.out.println(set1);
		//System.out.println(set2);
		
		Set<Integer> set3 = set1.intersection(set2);
		//System.out.println(set3);  //should have 2 and 5 only
		if (set3.contains(2) && set3.contains(5) && !set3.contains(1) && set3.size()==2){
			System.out.println("Yay 2");
		}
		
		Set<Integer> set4 = set1.union(set2);
		//System.out.println(set4);  //should have 1,2,3,4,5,6,7 (not necessarily in that order)
		boolean ok = true;
		for (int i=1;i<8;i++){
			ok = ok && set4.contains(i);
		}
		if (ok && set4.size()==7){
			System.out.println("Yay 3");		
		}
		
		Set<Integer> set5 = set1.difference(set2);
		//System.out.println(set5);  //should have 1,3,7 (unordered)
		if (set5.size()==3 && set5.contains(1) && set5.contains(3) && set5.contains(7) 
			&& !set5.contains(2) && !set5.contains(5) && !set5.contains(4)){
			System.out.println("Yay 4");
		}

		if (!set1.isSubset(set2) && set3.isSubset(set2) 
			&& !set1.isDisjoint(set2) && set5.isDisjoint(set2)){
			System.out.println("Yay 5");			
		}
		
		Set<Integer> set6 = set1.symmetricDifference(set2);
		//System.out.println(set6);  // should have 1,3,4,6,7 (unordered)
		if (set6.size()==5 && set6.contains(1) && set6.contains(3) && set6.contains(4) 
			&& !set6.contains(2) && !set6.contains(5) && set6.contains(7)){
			System.out.println("Yay 6");
		}
	
	}
	
}