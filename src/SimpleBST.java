
/**
 * Class for a binary search tree implementation.
 * @author minhquando
 *
 * @param <T>
 */
class SimpleBST<T extends Comparable<T>>{

	//-------------------------------------------------------------
	// DO NOT EDIT ANYTHING FOR THIS SECTION EXCEPT TO ADD JAVADOCS
	//-------------------------------------------------------------
	
	/**
	 * root of tree
	 */
  	public Node<T> root;

  	/**
  	 * size of the tree (the number of nodes)
  	 */
  	public int size;
  	
  	/**
  	 * Obtain the number of nodes in the tree (aka the size of the tree).
  	 * @return size of tree
  	 */
  	public int size() { return size; }

  	/**
  	 * binary tree node class
  	 * @author minhquando
  	 *
  	 * @param <T>
  	 */
	public static class Node<T>{
    	T data;
    	Node<T> left, right;
    	
    	public Node(T data) { this.data = data; }
    	public Node(T data,Node<T> l, Node<T> r){
      		this.data=data; this.left=l; this.right=r;
    	}
  	}
	//-------------------------------------------------------------
	// END OF PROVIDED "DO NOT EDIT" SECTION 
	//-------------------------------------------------------------
	

	//-------------------------------------------------------------
	// You can NOT add any instance/static variables in this class.
	// You can add methods if needed but they must be PRIVATE.
	//-------------------------------------------------------------
    
	/**
	 * 
	 * @param value
	 * @return true if value is in tree; false if value is not in tree or if value is null.
	 */
  	public boolean contains(T value){
  		Node<T> current = root;
  		
  		if(root == null) {
  			return false;
  		}
  		
  		if(value == null) {
  			return false;
  			
  		} else if(current.data.compareTo(value) == 0) {
			return true;
		}
  		
  		while( current.right != null || current.left != null ) {	// O(H): H as the tree height
  			
  			if(current.data.compareTo(value) < 0) {					// if current.data < value -> compareTo() < 0
  	  			current = current.right;
  	  			
  	  		} else if(current.data.compareTo(value) > 0) {			// if current.data > value -> compareTo() > 0
  	  			current = current.left;
  	  		} 
  			
  			if(current == null) {
  				return false;
  			}
  			
  			if(current.data.compareTo(value) == 0) {
  				return true;
  			}
  		}
  		
		return false;
  	}

  	/**
  	 * Used to insert value into tree. No duplicates allowed; no null value allowed.
  	 * @param value
  	 * @return true for a successful insertion; false if value cannot be added (duplicate values or null values).
  	 */
  	public boolean insert(T value){
  		
  		if(value == null) {									// check if value = null
  			return false;
  			
  		} else if( root == null ) {							// if the root is null, then it is a null tree
  			root = new Node<T>(value);
  			size++;
  			return true;
  			
  		} else if(contains(value) == true) {				// check for duplicate values
  			return false;
  			
  		} else {
  			Node<T> insertNode = new Node<T>(value);		// create node to be inserted
  			
  			Node<T> current = root; 
  			while( current != null) {						// O(H): H as the tree height 
  				
  				if(current.data.compareTo(value) < 0) {		// if value > current.data, traverse right
  					
  					if(current.right == null) {				// insert node if right is null
  		                current.right = insertNode;
  		                size++;
  		                return true;
  		                
  					} else {
  						current = current.right;			// continue traversing if value cannot be inserted here
  					}
  					
  				} else {									// if value < current.data, traverse left
  					
  					if(current.left == null) {
  		                current.left = insertNode;
  		                size++;
  		                return true;
  		                
  					} else {
  						current = current.left;
  					}
  				}
  			}
  		}
  		
    	return false;
  	}

  	/**
  	 * Used to remove value from tree.
  	 * @param value
  	 * @return true for a successful removal; false if value cannot be removed (values not in tree or null values).
  	 */
  	public boolean remove(T value){
  		if(root == null) {	
  			return false;
  			
  		} else if(root.data == value && root.left == null && root.right == null) {	// special case: the tree only contains the root node and we're removing the root node
  			root = null;
  			size--;
  			return true;
  			
  		} else if(contains(value) == false) {
  			return false;
  			
  		} else {
  			Node<T> current = root;
  			Node<T> parent = root;
  			
  			if(current.right != null && current.left == null) {			// in the case that the root node is the smallest value in the tree
  				root = root.right;										// set the root to the node on the right of root
  				size--;
  				return true;
  			}
  			
  			while(current != null) {												// O(H): H as the tree height  
  				
  				if(current.data.compareTo(value) < 0) {								// if value > current.data, traverse right
  					parent = current;												// update parent & current
  					current = current.right;
  					
  				} else if(current.data.compareTo(value) > 0) {						// if value < current.data, traverse left
  					parent = current;												// update parent & current
  					current = current.left;
  					
  				} else {
  					
  					if(current.left == null && current.right == null) {				// case 1: leaf node, 0 children
  						
  						if(parent.left == current) {			// if left child of parent node is current, set the left child to null
  							parent.left = null;
  							
  						} else if(parent.right == current) {
  							parent.right = null;
  						}
  						
  					} else if(current.left != null && current.right != null) {		// case 2: 2 children
  						current.data = findPredecessor(current);					// using predecessor replacement
  						removeMax(current.left);
  						
  					} else {														// case 3: 1 child
  						Node<T> replacement = null;
  						
  						if(current.left == null && current.right != null) {			// child on the right, set replacement node to right child
  							replacement = current.right;
  	  		  	  			
  	  					} else {													// current.left != null && current.right == null
  	  						replacement = current.left;
  	  					} 
  						
  						if(parent.left == current) {			// if left child of parent node is current, set the left child to replacement
  							parent.left = replacement;
  							
  						} else {								// parent.right == current
  							parent.right = replacement;
  						}
  					}
  					
  					size--;
  					return true;
  				}
  			}
  				
  			return false;
  		}
  	}
  
  	/**
  	 * Finds the biggest value in the tree rooted at t.
  	 * @param t
  	 * @return the biggest value in the tree rooted at t; null if tree is null.
  	 */
  	public static <T> T findMax(Node<T> t){
  		Node<T> current = t;
  		
  		while(current.right != null) {			// O(H): H as the tree height 
  			current = current.right;
  		}
  		
   		return current.data;
  	}

  	/**
  	 * Remove the biggest value in the binary search tree rooted at t.
  	 * @param t
  	 * @return the tree root after removal; null for null trees.
  	 */
  	public static<T> Node<T> removeMax(Node<T> t){
  		if(t == null) {
  			return null;
  		
  		} else if (t.data == findMax(t) && t.left == null && t.right == null) {
  			t = null;
  			return null;
  			
  		} else {
  			Node<T> current = t;
  			Node<T> parent = t;
  			
  			if(current.right == null && current.left != null) {			// if there is no right subtree, remove the root
  	  			current.data = current.left.data;
  	  			current.left = current.left.left;
  	  			
  	  		} else {
  	  			while(current != null) {								// O(H): H as the tree height
  	  				
  	  				if(current.right == null) {
  	  					parent.right = current.left;
  	  				}
  	  				
  	  				parent = current;
  	  				current = current.right;
  	  			}
  	  		}
  		}
  		
 		return t;
  	}

  	/**
  	 * Finds the in-order predecessor of t's data in the tree with t as root.
  	 * This in-order predecessor is the largest value in t's left subtree.
  	 * @param t the root node of the tree.
  	 * @return the largest value in t's left subtree; null if t's data is the smallest or if tree is null.
  	 */
  	public static<T> T findPredecessor(Node<T> t){
  		Node<T> current = t;
  		while(current.left != null) {
  			current = current.left;
  		}
  		
  		T min = current.data;
  		
  		if(t.data.equals(min) || t == null) {
  			return null;
  			
  		} else {
  			return findMax(t.left);		// O(H): H as the tree height 
  		}
  	}

  	/**
  	 * Private recursive implementation of height().
  	 * @param current node currently being checked
  	 * @return the height of the current node
  	 */
  	private int height(Node<T> current) { 
  		if(current == null) {
			return 0;
		}
  		
  		int leftHeight = 1 + height(current.left);		// O(N): N is the tree size
  		int rightHeight = 1 + height(current.right);
  		
  		if(leftHeight > rightHeight) {
  			return leftHeight;
  			
  		} else if(rightHeight > leftHeight) {
  			return rightHeight;
  			
  		} else {
  			return leftHeight;
  		}
  	}
  	
  	/**
  	 * Used to determine the height of the tree.
  	 * @return the height of the tree. Returns -1 for null trees.
  	 */
	public int height(){
		if(root == null) {
			return -1;
		}
		
		return height(root) - 1;
	}
	
	/**
	 * Private recursive implementation of numLeaves()
	 * @param current node currently being checked
	 * @return the number of leaf nodes in the subtree.
	 */
	private int numLeaves(Node<T> current) {
		if(current == null) {
			return 0;
		}
		
		if(current.left == null && current.right == null) {
			return 1;
		}
		
		int totalLeaves = numLeaves(current.left) + numLeaves(current.right);	// O(N): N is the tree size
		
		return totalLeaves;
	}
	
	/**
	 * Used to determine the number of leaf nodes in the tree.
	 * @return the number of leaf nodes in the tree; zero for null trees.
	 */
	public int numLeaves(){
		if(root == null) {
			return 0;
		}
		
		return numLeaves(root);
	}
	
	/**
	 * Private method used to create the string representation of the tree.
	 * @param current current node
	 * @return string representation of the tree
	 */
	private String inOrder(Node<T> current) {
		
		if(current == null) {
			return "";
		}
		
		String str = inOrder(current.left) + current.data + " " + inOrder(current.right);
		
		return str;
	}
	
	/**
	 * Retrieves all of the values in the tree using in-order traversal in the form of a string
	 * 
	 * Example 1: a single-node tree with the root data as "A":
	 * 			  toString() will return "A "
	 * 
	 * Example 2: a tree with three nodes:      B
	 * 										   / \
	 * 										  A   C 
	 *            toString() will return "A B C "
	 * @return string representation of the tree, return empty string for null trees.
	 */
  	public String toString(){
  		return inOrder(root);
  	}
  	
  	/**
  	 * Private method used to input the values in the tree into an array using pre-order traversal
  	 * @param current current node
  	 * @param arr array to store values in
  	 */
  	private void preOrder(Node<T> current, Object[] arr) {
		if(current == null) {
			return;
		}
		
		int index = 0;
		for(int i = 0; i < size; i++) {			// loop through array, find the first index that is null
			if(arr[i] == null) {
				index = i;
				break;
			}
		}
		
		arr[index] = current.data;				// store the element at the first index that is null
		preOrder(current.left, arr);			// traverse left
		preOrder(current.right, arr);			// traverse right
	}
	
  	/**
  	 * Retrieves all of the values in the tree using pre-order traversal in the form of an array
  	 * Following PRE-ORDER traversal.
  	 * - The root value of the tree will be element 0 in the array. 
	 * - The length of the array will be the size of tree.
	 *   
	 * Example 1: a single-node tree with the root data as "A" 
	 * 			  toArray() will return {"A"}
	 * 
	 * Example 2: a tree with three nodes:      B
	 * 										   / \
	 * 										  A   C 
	 *            toArray() should return {"B","A","C"}
	 *            
  	 * @return an array representation of all values. 
  	 */
	public Object[] toArray() {	
		
		Object[] Array = new Object[size];
		
		preOrder(root, Array);					// use preOrder() to fill in Array
		
		return Array;
	}


	//-------------------------------------------------------------
	// Main Method For Your Testing -- Edit all you want
	//-------------------------------------------------------------
	public static void main(String args[]){
  		SimpleBST<Integer> t = new SimpleBST<Integer>();

		//build the tree / set the size manually
		//only for testing purpose
  		Node<Integer> node = new Node<>(112);
  		Node<Integer> node2 = new Node<>(330);
  		node2 = new Node<>(440,node2,null);
  		node = new Node<>(310,node,node2);
  		t.root = node;
  		t.size = 4;

		// Current tree:
		//			  310
		//           /   \
		//        112     440
		//                /  
		//              330 
  		

		//checking basic features
		if (t.root.data == 310 && t.contains(112) && !t.contains(211) && t.height() == 2){
			System.out.println("Yay 1");
		}
		
		//checking more features
		if (t.numLeaves() == 2 && SimpleBST.findMax(t.root)==440 
			&& SimpleBST.findPredecessor(t.root) == 112){
			System.out.println("Yay 2");
		}
	
		//toString and toArray	
		if (t.toString().equals("112 310 330 440 ") &&  t.toArray().length==t.size() 
			&& t.toArray()[0].equals(310) && t.toArray()[1].equals(112) 
			&& t.toArray()[2].equals(440) && t.toArray()[3].equals(330) ){
			System.out.println("Yay 3");		
			//System.out.println(t.toString());		
		}
		
		// start w/ an empty tree and insert to build the same tree as above
		t = new SimpleBST<Integer>();
  		if (t.insert(310) && !t.insert(null) && t.size()==1 && t.height()==0 
  			&& t.insert(112) && t.insert(440) && t.insert(330) && !t.insert(330)
  			){
 			System.out.println("Yay 4"); 			
  		}

  		if (t.size()==4 && t.height()==2 && t.root.data == 310 && 
  			t.root.left.data == 112 && t.root.right.data==440
  			&& t.root.right.left.data == 330){
 			System.out.println("Yay 5"); 			
  		}
		
		// more insertion
		t.insert(465);
		t.insert(321);
		t.insert(211);
		
		//			  310
		//           /   \
		//        112     440
		//          \     /  \
		//         211   330 465
		//               /
		//             321

		

		//remove leaf (211), after removal:
		//			  310
		//           /   \
		//        112     440
		//                /  \
		//               330 465
		//               /
		//             321

		if (t.remove(211) && !t.contains(211) && t.size()==6 && t.height() == 3 
			&& SimpleBST.findMax(t.root.left) == 112){
			System.out.println("Yay 6");					
		}

		//remove node w/ single child (330), after removal:
		//			  310
		//           /   \
		//        112     440
		//                /  \
		//              321 465

		if (t.remove(330) && !t.contains(330) && t.size()==5 && t.height() == 2 
			&& t.root.right.left.data == 321){
			System.out.println("Yay 7");					
		}

		//remove node w/ two children (440), after removal:
		//			  310
		//           /   \
		//        112     321
		//                  \
		//                 465

		if ((t.root!=null) && SimpleBST.findPredecessor(t.root.right) == 321 && t.remove(440) && !t.contains(440) 
			&& t.size()==4 && t.height() == 2 && t.root.right.data == 321){
			System.out.println("Yay 8");					
		}
		
	}

}


