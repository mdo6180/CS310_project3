import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleBST_Tests {
	
	private static boolean IsEqual(SimpleBST.Node<Integer> nodet1, SimpleBST.Node<Integer> nodet2) {
		
		if( nodet1 == null && nodet2 == null ) {
			return true;
		} 
		
		if( (nodet1 != null && nodet2 == null) || (nodet1 == null && nodet2 != null) ) {
			return false;
		}
		
		if( nodet1.data.compareTo(nodet2.data) != 0) {
			return false;
		}
		
		if( IsEqual(nodet1.left, nodet2.left) && IsEqual(nodet1.right, nodet2.right)) {
			return true;
			
		} else {
			return false;
		}
	}
	
	private static SimpleBST.Node<Integer> find(SimpleBST.Node<Integer> current, int value){
		if(current == null) {
			return null;
		}
		
		if(current.data.compareTo(value) == 0) {
			return current;
			
		} 
		
		SimpleBST.Node<Integer> left = find(current.left, value);
		SimpleBST.Node<Integer> right = find(current.right, value);
		
		if(left == null && right != null) {
			return right;
			
		} else if(left != null && right == null) {
			return left;
		}
		
		return null;
	}
	
	private SimpleBST<Integer> expected = new SimpleBST<Integer>();
	private SimpleBST<Integer> actual = new SimpleBST<Integer>();
	private SimpleBST<Integer> nullTreeExpected = new SimpleBST<Integer>();
	private SimpleBST<Integer> nullTreeActual = new SimpleBST<Integer>();
	private SimpleBST<Integer> singleNodeTreeExpected = new SimpleBST<Integer>();
	private SimpleBST<Integer> singleNodeTreeActual = new SimpleBST<Integer>();
	
	@BeforeEach
	void setup() {
		
		SimpleBST.Node<Integer> expectednode112 = new SimpleBST.Node<>(112);
		SimpleBST.Node<Integer> expectednode330 = new SimpleBST.Node<>(330);
		SimpleBST.Node<Integer> expectednode440 = new SimpleBST.Node<>(440, expectednode330, null);
		SimpleBST.Node<Integer> expectednode310 = new SimpleBST.Node<>(310, expectednode112, expectednode440);
  		expected.root = expectednode310;
  		
  		SimpleBST.Node<Integer> actualnode112 = new SimpleBST.Node<>(112);
		SimpleBST.Node<Integer> actualnode330 = new SimpleBST.Node<>(330);
		SimpleBST.Node<Integer> actualnode440 = new SimpleBST.Node<>(440, actualnode330, null);
		SimpleBST.Node<Integer> actualnode310 = new SimpleBST.Node<>(310, actualnode112, actualnode440);
		actual.root = actualnode310;
  		
		// Current tree:
		//			  310
		//           /   \
		//        112     440
		//                /  
		//              330 
  		
		SimpleBST.Node<Integer> singleExpectedNode112 = new SimpleBST.Node<>(310);
		singleNodeTreeExpected.root = singleExpectedNode112;
		
		SimpleBST.Node<Integer> singleActualNode112 = new SimpleBST.Node<>(310);
		singleNodeTreeActual.root = singleActualNode112;
	}
	
	@Test
	void containsTest() {
		assertFalse(nullTreeActual.contains(310));
	}
	
	@Test
	void insertTest1() {
		
		SimpleBST.Node<Integer> node440 = find(expected.root, 440);
		node440.right = new SimpleBST.Node<Integer>(465);
		actual.insert(465);
		assertTrue(IsEqual(expected.root, actual.root));
		
		//		      310
		//           /   \
		//        112     440
		//                /  \
		//         	   330    465
		
		
		SimpleBST.Node<Integer> node112 = find(expected.root, 112);
		node112.right = new SimpleBST.Node<Integer>(211);
		actual.insert(211);
		assertTrue(IsEqual(expected.root, actual.root));
		
		//		      310
		//           /   \
		//        112     440
		//           \    /  \
		//         	211  330  465
		
		
		SimpleBST.Node<Integer> node330 = find(expected.root, 330);
		node330.left = new SimpleBST.Node<Integer>(321);
		actual.insert(321);
		assertTrue(IsEqual(expected.root, actual.root));
		
		//		  	  310
		//           /   \
		//        112     440
		//          \     /  \
		//         211   330 465
		//               /
		//             321
		
		// testing the null tree case
		nullTreeExpected.root = new SimpleBST.Node<Integer>(321);
		nullTreeActual.insert(321);
		assertTrue(IsEqual(nullTreeExpected.root, nullTreeActual.root));

		// testing the single node case
		singleNodeTreeExpected.root.right = new SimpleBST.Node<Integer>(321);
		singleNodeTreeExpected.root.left = new SimpleBST.Node<Integer>(112);
		singleNodeTreeActual.insert(321);
		singleNodeTreeActual.insert(112);
		assertTrue(IsEqual(singleNodeTreeExpected.root, singleNodeTreeActual.root));
	}
	
	@Test 
	void removeMaxTest() {
		actual.insert(465);
		SimpleBST.removeMax(actual.root);
		assertTrue(IsEqual(expected.root, actual.root));
		
		singleNodeTreeActual.root.right = new SimpleBST.Node<Integer>(440);
		SimpleBST.removeMax(singleNodeTreeActual.root);
		assertTrue(IsEqual(singleNodeTreeActual.root, singleNodeTreeExpected.root));
		
		SimpleBST.Node<Integer> n = new SimpleBST.Node<Integer>(112);
		singleNodeTreeActual.root.left = n;
		SimpleBST.removeMax(singleNodeTreeActual.root);			// max value is the root node, removing this should leave 112 as the root node
		assertTrue(IsEqual(singleNodeTreeActual.root, n));
		
		singleNodeTreeActual.remove(112);						// removing the root node again should leave us with a null tree
		assertTrue(IsEqual(singleNodeTreeActual.root, nullTreeExpected.root));
		
		SimpleBST<Integer> expected = new SimpleBST<Integer>();
		SimpleBST.Node<Integer> expectednode105 = new SimpleBST.Node<Integer>(105);
		SimpleBST.Node<Integer> expectednode112 = new SimpleBST.Node<>(112, expectednode105, null);
		expected.root = expectednode112;
		
		SimpleBST<Integer> actual = new SimpleBST<Integer>();
		SimpleBST.Node<Integer> actualnode105 = new SimpleBST.Node<Integer>(105);
		SimpleBST.Node<Integer> actualnode112 = new SimpleBST.Node<>(112, actualnode105, null);
		SimpleBST.Node<Integer> actualnode310 = new SimpleBST.Node<>(112, actualnode112, null);
		actual.root = actualnode310;
		SimpleBST.removeMax(actual.root);
		
		assertTrue(IsEqual(actual.root, expected.root));
	}
	
	
	@Test
	void removeTest1() {
		actual.insert(465);
		actual.insert(321);
		actual.insert(211);
		
		expected.insert(465);
		expected.insert(321);
		
		actual.remove(211);
		
		assertTrue(IsEqual(expected.root, actual.root));
	}
	
	@Test
	void removeTest2() {
		actual.insert(465);
		actual.insert(321);
		actual.insert(211);
		
		actual.remove(440);
		
		SimpleBST<Integer> expected = new SimpleBST<Integer>();
		SimpleBST.Node<Integer> expectednode211 = new SimpleBST.Node<Integer>(211);
		SimpleBST.Node<Integer> expectednode112 = new SimpleBST.Node<>(112, null, expectednode211);
		
		SimpleBST.Node<Integer> expectednode321 = new SimpleBST.Node<>(321);
		SimpleBST.Node<Integer> expectednode465 = new SimpleBST.Node<>(465);
		SimpleBST.Node<Integer> expectednode330 = new SimpleBST.Node<>(330, expectednode321, expectednode465);
		
		SimpleBST.Node<Integer> expectednode310 = new SimpleBST.Node<>(310, expectednode112, expectednode330);
  		expected.root = expectednode310;
  		
  		assertTrue(IsEqual(expected.root, actual.root));
	}
	
	@Test
	void removeTest3() {
		actual.insert(465);
		actual.insert(321);
		actual.insert(211);
		
		actual.remove(112);
		
		SimpleBST<Integer> expected = new SimpleBST<Integer>();
		SimpleBST.Node<Integer> expectednode211 = new SimpleBST.Node<Integer>(211);
		SimpleBST.Node<Integer> expectednode321 = new SimpleBST.Node<>(321);
		SimpleBST.Node<Integer> expectednode465 = new SimpleBST.Node<>(465);
		SimpleBST.Node<Integer> expectednode330 = new SimpleBST.Node<>(330, expectednode321, null);
		SimpleBST.Node<Integer> expectednode440 = new SimpleBST.Node<>(440, expectednode330, expectednode465);
		
		SimpleBST.Node<Integer> expectednode310 = new SimpleBST.Node<>(310, expectednode211, expectednode440);
  		expected.root = expectednode310;
  		
  		assertTrue(IsEqual(expected.root, actual.root));
	}
	
	@Test
	void removeMin() {
		
		SimpleBST.Node<Integer> expectednode321 = new SimpleBST.Node<>(321);
		SimpleBST.Node<Integer> expectednode465 = new SimpleBST.Node<>(465);
		SimpleBST.Node<Integer> expectednode330 = new SimpleBST.Node<>(330, expectednode321, null);
		SimpleBST.Node<Integer> expectednode440 = new SimpleBST.Node<>(440, expectednode330, expectednode465);
		
		SimpleBST<Integer> expected = new SimpleBST<Integer>();
		expected.root = expectednode440;
		
		SimpleBST.Node<Integer> actualnode321 = new SimpleBST.Node<>(321);
		SimpleBST.Node<Integer> actualnode465 = new SimpleBST.Node<>(465);
		SimpleBST.Node<Integer> actualnode330 = new SimpleBST.Node<>(330, actualnode321, null);
		SimpleBST.Node<Integer> actualnode440 = new SimpleBST.Node<>(440, actualnode330, actualnode465);
		SimpleBST.Node<Integer> actualnode310 = new SimpleBST.Node<>(310, null, actualnode440);
		
		SimpleBST<Integer> actual = new SimpleBST<Integer>();
		actual.root = actualnode310;
		
		actual.remove(310);
		
		assertTrue(IsEqual(expected.root, actual.root));
	}
	
	@Test
	void removeEmpty() {
		assertFalse(nullTreeActual.remove(310));
	}
	
	@Test
	void removeNull() {
		assertFalse(expected.remove(null));
	}
	
	@Test
	void numLeavesTest() {
		assertEquals(2, actual.numLeaves());
		
		actual.insert(465);
		actual.insert(321);
		actual.insert(211);
		
		assertEquals(3, actual.numLeaves());
		
		assertEquals(0, nullTreeActual.numLeaves());
		
		assertEquals(1, singleNodeTreeActual.numLeaves());
	}
	
	@Test
	void heightTest() {
		assertEquals(2, actual.numLeaves());
		
		actual.insert(465);
		actual.insert(321);
		actual.insert(211);
		
		assertEquals(3, actual.numLeaves());
		
		assertEquals(-1, nullTreeActual.height());
		
		assertEquals(0, singleNodeTreeActual.height());
		
	}
	
	public static void main(String[] args) {
		
		SimpleBST<Integer> tree1 = new SimpleBST<Integer>();
		
		SimpleBST.Node<Integer> tree1node112 = new SimpleBST.Node<>(112);
		SimpleBST.Node<Integer> tree1node330 = new SimpleBST.Node<>(330);
		SimpleBST.Node<Integer> tree1node440 = new SimpleBST.Node<>(440,tree1node330,null);
		SimpleBST.Node<Integer> tree1node310 = new SimpleBST.Node<>(310,tree1node112,tree1node440);
  		tree1.root = tree1node310;
  		
  		SimpleBST.Node<Integer> tree2node112 = new SimpleBST.Node<>(112);
		SimpleBST.Node<Integer> tree2node330 = new SimpleBST.Node<>(330);
		SimpleBST.Node<Integer> tree2node440 = new SimpleBST.Node<>(440,tree2node330,null);
		SimpleBST.Node<Integer> tree2node310 = new SimpleBST.Node<>(310,tree2node112,tree2node440);
  		SimpleBST<Integer> tree2 = new SimpleBST<Integer>();
  		tree2.root = tree2node310;
  		
  		if(IsEqual(tree1.root, tree2.root) == true) {
  			System.out.println("trees are equal.");
  			
  		} else {
  			System.out.println("trees are not equal.");
  		}
  		
  		tree2.insert(465);
  		if(IsEqual(tree1.root, tree2.root) == true) {
  			System.out.println("trees are equal.");
  			
  		} else {
  			System.out.println("trees are not equal.");
  		}
  		
  		SimpleBST.Node<Integer> desiredNode = find(tree2.root, 440);
  		if(desiredNode.data == 440) {
  			System.out.println("find method works");
  		}
	}

}
