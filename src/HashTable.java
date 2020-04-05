
// imports for debugging only
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * A hash table implemented with separate chaining where every chain is organized as a binary search tree.
 * @author minhquando
 *
 * @param <T>
 */
class HashTable<T extends Comparable<T>> {

	//-------------------------------------------------------------
	// DO NOT EDIT ANYTHING FOR THIS SECTION EXCEPT TO ADD JAVADOCS
	//-------------------------------------------------------------

	/**
	 * Minimum length of the hash table = 2
	 */
	static private int minLength = 2;
	
	/**
	 * number of elements in the hash table
	 */
	private int size = 0;
	
	/**
	 * array of BSTs
	 */
	private SimpleBST<T>[] storage;

	/**
	 * Obtain the number of indexes of the hash table (aka table length)
	 * @return the length of the hash table.
	 */
	public int getLength() {
		return storage.length;
	}
	
	/**
	 * Obtain the number of values that has been entered into the hash table.
	 * @return the number of values that has been entered into the hash table.
	 */
	public int size() {
		return size;
	}

	/**
	 * Retrieve all values entered into the hash table in the form of a string.
	 * @return string representation of all values in hash table
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			if(storage[i] != null) {
				s.append(storage[i].toString());
			}
		}
		return s.toString().trim();
	}
	
	/**
	 * Give the contents of each table entry.
	 * @param verbose set to true to get a detailed report of tree features for every entry.
	 * @return a detailed string representation of the hash table;
	 */
	public String toStringDebug(boolean verbose) {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			if (storage[i]!=null && storage[i].size()!=0){
				s.append("[" + i + "]: " + storage[i].toString().trim() + "\n");
				if (verbose){
					s.append("\t tree size:" + storage[i].size() + "\n");
					s.append("\t tree height:" + storage[i].height() + "\n");
					s.append("\t number of leaves:" + storage[i].numLeaves() + "\n");
				}
			}
			else
				s.append("[" + i + "]: null\n");
			
		}
		return s.toString().trim();
	}

	//-------------------------------------------------------------
	// END OF PROVIDED "DO NOT EDIT" SECTION 
	//-------------------------------------------------------------

	/**
	 * Class constructor that initializes the table with the desired length;
	 * if desired length is less than 2, table will automatically initialize the table with length 2.
	 * @param length desired table length (desired length must be greater than 2)
	 */
	@SuppressWarnings("unchecked")
	public HashTable(int length) {
		
		if(length < minLength) {					// check that length is more than minLength and initialize array
			storage = new SimpleBST[minLength];
		} else {
			storage = new SimpleBST[length];
		}
		
		for(int i = 0; i < getLength(); i++) {		// initialize elements of storage
			storage[i] = new SimpleBST<T>();
		}
	}
	
	/**
	 * Private method to find the load of the table.
	 * @return load
	 */
	private double load(int length) {
		return ((double) size)/length;
	}
	
	/**
	 * Private method used to calculate the index in the hash table to store the value 
	 * @param value value to be stored
	 * @return index of hash table where value must be stored
	 */
	private int computeHash(T value) {
		return value.hashCode() % getLength();
	}
	
	// Using SimpleBST.insert(T value): Big O = Worst case: O(N), Average case: O(load), 
	// N is the number of values in hash table not considering rehashing overhead
	/**
	 * Add value into hash table.
	 * - Uses separate chaining for collision.
	 * - Table is rehashed to twice the size if the table has a load>= 80% after adding a new value.
	 * @param value
	 * @return true for a successful insertion, false if value cannot be added (i.e. duplicate values or null values).
	 */
	public boolean add(T value) {		
		
		int index = computeHash(value);					// compute index in hash table to store value
		
		if(storage[Math.abs(index)].insert(value)) {	// if insertion is successful,
			size++;										// increment the size
			
			if( load(getLength()) >= 0.8) {				// check if load >= 80%
				rehash(2 * getLength());				// rehash if needed
			}
			
			return true;
			
		} else {
			return false;
		}
	}
	
	// Using SimpleBST.contains(T value): Big O: Worst case: O(N), Average case: O(load), N is the number of values in hash table
	/**
	 * Check whether value is in hash table.
	 * @param value
	 * @return true is present, false otherwise
	 */
	public boolean contains(T value){		
		int index = computeHash(value);							// compute index of where value is stored in hash table
		return storage[Math.abs(index)].contains(value);	
	}
	
	// Using SimpleBST.remove(T value): Big O: Worst case: O(N), Average case: O(load), N is the number of values in hash table
	/**
	 * Remove and return true if value is in hash table.
	 * @param value
	 * @return true for a successful removal; false if value cannot be removed (values not in tree or null values) 
	 */
	public boolean remove(T value) {			
		
		int index = computeHash(value);						// compute index of where value is stored in hash table
		
		if(storage[Math.abs(index)].remove(value)) {		// if removal is successful,
			size--;											// decrement the size
			return true;
			
		} else {
			return false;
		}
	}

	/**
	 * Rehash hash table to newLength.
	 * - This can be used to increase or decrease the capacity of the storage. 
	 * - If the new capacity is smaller than minLength (newLength < 2), method will return false and table will not be rehashed.
	 * - If load >= 0.8, it is standard procedure to rehash the table by doubling the length over and over again until load < 0.8.
	 *   However, if the length of the table has to go beyond Integer.MAX_VALUE in an effort to lower load to less than 0.8, 
	 *   method will automatically return false and table will not be rehashed.
	 *   
	 * All values will be rehashed following this order:
	 * - Hash table entries should be rehashed based on array index in ascending order.
	 * - Multiple values in a chain (i.e. a binary search tree) will be rehashed in pre-order. 
	 * 
	 * @param newLength desired length of hash table 
	 * @return true if table was rehashed, false if rehashing cannot be accomplished.
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int newLength) {
		
		int tablelength = newLength;					// increase table length by twice the length if load >= 0.8
		while(load(tablelength) >= 0.8) {
			tablelength *= 2;
		}
		
		if(tablelength < minLength || tablelength > Integer.MAX_VALUE) {
			return false;
		} 
		
		SimpleBST<T>[] temp = storage;					// temporary array to hold values of storage
		storage = new SimpleBST[tablelength];			// reset size of storage
		size = 0;
		for(int i = 0; i < getLength(); i++) {			// initialize elements in storage
			storage[i] = new SimpleBST<T>();
		}
		
		for(int i = 0; i < temp.length; i++) {			// O(N+M): N as the number of values in hash table; M as the table length.
			
			Object[] elements = temp[i].toArray();		// get values of each index in preorder 
			for(int j = 0; j < elements.length; j++) {
				if( add((T) elements[j]) == false) {	// add value to storage
					return false;
				} 
			}
		}
		
		return true;
	}
   
	/**
	 * Retrieve all values entered into the hash table in the form of an array.
	 * The array will be assembled following this order:
	 * - Hash table entries should be checked based on array index in ascending order.
	 * - Multiple values in a chain (i.e. a binary search tree) will be returned in the array in pre-order.
	 * 
	 * @return an array representation of all value in hash table.
	 */
	public Object[] toArray(){
		
		Object[] elements = new Object[size];
		int index = 0;
		
		for(int i = 0; i < getLength(); i++) {			// O(N+M): N as the number of values in hash table; M as the table length.
			Object[] vals = storage[i].toArray();		// obtain array of values at index i of hash table in preorder
			
			for(int j = 0; j < vals.length; j++) {		// add all values in index i of hash table into element array
				elements[index] = vals[j];
				index++;
			}
		}
		
		return elements;		
	}
	
	/**
	 * Calculates the average height of all the trees in the hash table.
	 * @param nonEmptyOnly If nonEmptyOnly is true, only consider non-empty trees; otherwise all trees are considered.
	 * @return the average tree height, -1.0 if all trees are empty but only non-empty trees are considered. 
	 */
	public double avgTreeHeight(boolean nonEmptyOnly){
		
		int numTrees = 0;									// number of trees being considered
		int totalHeight = 0;								// sum of all the height of the trees
		
		if(size == 0) {
			return -1.0;
		}
		
		if(nonEmptyOnly == true) {							// checking non-empty trees only

			for(int i = 0; i < getLength(); i++) {			
				if(storage[i].height() != -1) {
					numTrees++;								// adding to the number of trees considered
					totalHeight += storage[i].height();		// adding the height of the trees to totalHeight
				}
			}
			
		} else {											// checking all trees
			
			for(int i = 0; i < getLength(); i++) {
				numTrees++;
				totalHeight += storage[i].height();
			}
		}
		
		return (double) totalHeight / numTrees;
	}

	/**
	 * Calculates the average size of all the trees in the hash table.
	 * @param nonEmptyOnly If nonEmptyOnly is true, only consider non-empty trees; otherwise all trees are considered.
	 * @return the average tree size of all the BSTs in the hash table, 0.0 if all trees are empty but only non-empty trees are considered. 
	 */
	public double avgTreeSize(boolean nonEmptyOnly){		// works the same way as avgTreeHeight
		
		int numTrees = 0;
		int totalSize = 0;
		
		if(size == 0) {
			return 0.0;
		}
		
		if(nonEmptyOnly == true) {
			
			for(int i = 0; i < getLength(); i++) {
				if(storage[i].height() != -1) {
					numTrees++;
					totalSize += storage[i].size();
				}
			}
			
		} else {
			
			for(int i = 0; i < getLength(); i++) {
				numTrees++;
				totalSize += storage[i].size();
			}
		}
		
		return (double) totalSize / numTrees;
	}

	/**
	 * Calculates the average number of leaves of all the trees in the hash table.
	 * @param nonEmptyOnly If nonEmptyOnly is true, only consider non-empty trees; otherwise all trees are considered.
	 * @return the average number of leaves of all the BSTs in the hash table, 0.0 if all trees are empty but only non-empty trees are considered.
	 */
	public double avgNumLeaves(boolean nonEmptyOnly){		// works the same way as avgTreeHeight
		
		int numTrees = 0;
		int totalLeaves = 0;
		
		if(size == 0) {
			return 0.0;
		}
		
		if(nonEmptyOnly == true) {
			
			for(int i = 0; i < getLength(); i++) {
				if(storage[i].height() != -1l) {
					numTrees++;
					totalLeaves += storage[i].numLeaves();
				}
			}

		} else {
			
			for(int i = 0; i < getLength(); i++) {
				numTrees++;
				totalLeaves += storage[i].numLeaves();
			}
		}
		
		return (double) totalLeaves / numTrees;
	}
	
	/**
	 * Finds the smallest and largest tree size in the table.
	 * @return the min and max tree size as a pair.
	 */
	public Pair<Integer,Integer> minAndMaxTreeSize(){		
		
		int min = storage[0].size();
		int max = storage[0].size();
		
		for(int i = 0; i < getLength(); i++) {
			if(storage[i].size() < min) {				// finds the entry with the smallest size
				min = storage[i].size();
			}
			
			if(storage[i].size() > max) {				// finds the entry with the largest size
				max = storage[i].size();
			}
		}
		
		return new Pair<Integer, Integer>(min, max);
	}

	/**
	 * Finds the shortest and largest tree height in the table.
	 * @return the min and max tree height as a pair.
	 */
	public Pair<Integer,Integer> minAndMaxTreeHeight(){		
		
		int min = storage[0].height();
		int max = storage[0].height();
		
		for(int i = 0; i < getLength(); i++) {
			if(storage[i].height() < min) {				// finds the entry with the shortest height
				min = storage[i].height();
			}
			
			if(storage[i].height() > max) {				// finds the entry with the tallest height
				max = storage[i].height();
			}
		}
		
		return new Pair<Integer, Integer>(min, max);
	}

	/**
	 * Finds the least/most number of leaves in the table.
	 * @return the min and max number of leaves in trees as a pair.
	 */
	public Pair<Integer,Integer> minAndMaxNumLeaves(){
		
		int min = storage[0].numLeaves();
		int max = storage[0].numLeaves();
		
		for(int i = 0; i < getLength(); i++) {			
			if(storage[i].numLeaves() < min) {			// finds the entry with the fewest leaves
				min = storage[i].numLeaves();
			}
			
			if(storage[i].numLeaves() > max) {			// finds the entry with the most leaves
				max = storage[i].numLeaves();
			}
		}
		
		return new Pair<Integer, Integer>(min, max);
	}

	//-------------------------------------------------------------
	// Main Method For Your Testing -- Edit all you want
	//-------------------------------------------------------------

	public static void main(String[] args) {
		BufferedWriter writer = null;
		boolean debug=false;
		
		try{
			
			if (args.length==1 && args[0].equals("-debug")){
				writer = new BufferedWriter(new FileWriter(new File("debug.txt")));
				debug = true;
			}
			
			writer = new BufferedWriter(new FileWriter(new File("debug.txt")));;
			debug=true;
		
			HashTable<String> ht1 = new HashTable<>(10);
		
			//empty hash table
			if(ht1.getLength() == 10 && ht1.size() == 0 && ht1.toString().equals("")) {
				System.out.println("Yay 1");
			}
		
			//add
			if (ht1.add("a") && ht1.add("c") && ht1.add("computer") && !ht1.add("c")
				&& ht1.getLength() == 10 && ht1.size() == 3){
				System.out.println("Yay 2");		
			}
			// hashCode() references:
			// "a": 97, "c": 99, "computer": -599163109
		
						
			//basic features of hash table
			if(ht1.contains("a") && ht1.contains("computer") && ht1.contains("c") && !ht1.contains("cs")
				&& ht1.toString().equals("a c computer") 
				&& ht1.toStringDebug(false).equals("[0]: null\n[1]: null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a\n[8]: null\n[9]: c computer")) {
				System.out.println("Yay 3");
			}
			//System.out.println(ht1.toStringDebug(true));
				
			if (debug){
				writer.write("====================================================="+System.getProperty("line.separator"));			
				writer.write("ht1 after add(\"a\"), add(\"c\"), add(\"computer\")"+System.getProperty("line.separator"));			
				writer.write("-----------------------------------------------------"+System.getProperty("line.separator"));			
				writer.write(ht1.toStringDebug(true)+System.getProperty("line.separator")+System.getProperty("line.separator"));
			}
		
			//remove
			if (!ht1.remove("data") && ht1.remove("c") && ht1.size() == 2
				&& !ht1.contains("c")  && ht1.contains("computer")){
				System.out.println("Yay 4");
			
			}
		
			//rehash
			HashTable<Integer> ht2 = new HashTable<>(5);
			ht2.add(105);
			ht2.add(26);
			ht2.add(11);
			if (ht2.getLength()==5 && ht2.size()==3 && ht2.add(55) && ht2.getLength()==10 
				&& ht2.add(5) && ht2.add(-11) && ht2.add(31) && ht2.getLength()==10 && ht2.size()==7){
				System.out.println("Yay 5");		
			}

			//System.out.println(ht2.toString());
			//System.out.println(ht2.toStringDebug(true));
			if (debug){
				writer.write("====================================================="+System.getProperty("line.separator"));			
				writer.write("ht2 after adding these in order: 105, 26, 11, 55, 5, -11, 31"+System.getProperty("line.separator"));			
				writer.write("-----------------------------------------------------"+System.getProperty("line.separator"));			
				writer.write(ht2.toStringDebug(true)+System.getProperty("line.separator")+System.getProperty("line.separator"));
			}

			if (ht2.toString().equals("-11 11 31 5 55 105 26") && ht2.size() == 7 && 
				ht2.toStringDebug(false).equals("[0]: null\n[1]: -11 11 31\n[2]: null\n[3]: null\n[4]: null\n[5]: 5 55 105\n[6]: 26\n[7]: null\n[8]: null\n[9]: null")) {
				System.out.println("Yay 6");
			}
			
			//tree features from hash table
			if (ht2.avgTreeSize(false)==0.7 && ht2.avgTreeSize(true)==7.0/3 &&
				ht2.avgTreeHeight(false)==-4.0/10 && ht2.avgTreeHeight(true)==1 &&
				ht2.avgNumLeaves(false)==0.4 && ht2.avgNumLeaves(true)==4.0/3){
				System.out.println("Yay 7");					
			}

			//more tree features
			if (ht2.minAndMaxTreeSize().toString().equals("<0,3>") &&
				ht2.minAndMaxTreeHeight().toString().equals("<-1,2>") &&
				ht2.minAndMaxNumLeaves().toString().equals("<0,2>")){
				System.out.println("Yay 8");					
			}
		
			//more rehash				
			if(ht2.rehash(1) == false && ht2.size() == 7 && ht2.getLength() == 10) {
				System.out.println("Yay 9");
			}
		
			if(ht2.rehash(11) == true && ht2.size() == 7 && ht2.getLength() == 11) {
				System.out.println("Yay 10");
			}
			//System.out.println(ht2.toString());
			//System.out.println(ht2.toStringDebug(true));
		
			if (debug){
				writer.write("====================================================="+System.getProperty("line.separator"));			
				writer.write("ht2 after rehash to length 11"+System.getProperty("line.separator"));			
				writer.write("-----------------------------------------------------"+System.getProperty("line.separator"));			
				writer.write(ht2.toStringDebug(true)+System.getProperty("line.separator")+System.getProperty("line.separator"));
				
				//bigger hashtable w/ major clusterings
				HashTable<Integer> ht3 = new HashTable<>(20);
				ht3.add(160); ht3.add(20);ht3.add(100); ht3.add(80);
				ht3.add(400); ht3.add(280); ht3.add(640);
				
				ht3.add(543); ht3.add(3); ht3.add(283); ht3.add(343); ht3.add(443);
				
				ht3.add(334); ht3.add(974); ht3.add(454);
				
				writer.write("====================================================="+System.getProperty("line.separator"));			
				writer.write("ht3 of 15 values clustered into three trees"+System.getProperty("line.separator"));			
				writer.write("-----------------------------------------------------"+System.getProperty("line.separator"));			
				writer.write(ht3.toStringDebug(true)+System.getProperty("line.separator")+System.getProperty("line.separator"));
				
				//bigger hashtable w/ uniform distribution
				ht3 = new HashTable<>(20);
				int count=0;
				Random r = new Random(0);
				while (count<15){
					if (ht3.add(r.nextInt(1000)))
						count++;
				}
				writer.write("====================================================="+System.getProperty("line.separator"));			
				writer.write("ht3 of 15 values uniformly distributed"+System.getProperty("line.separator"));			
				writer.write("-----------------------------------------------------"+System.getProperty("line.separator"));			
				writer.write(ht3.toStringDebug(true)+System.getProperty("line.separator"));
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try{
				if (writer!=null)
					writer.close();		
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
		
	}


}