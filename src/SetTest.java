import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SetTest {
	
	Set<Integer> set1 = new Set<>();
	Set<Integer> set2 = new Set<>();
	
	@Test
	void intersectionTest() {
		Set<Integer> intersection = set1.intersection(set2);
		assertEquals(0, intersection.size());
		
		Set<Integer> union = set1.union(set2);
		assertEquals(0, union.size());
		
		Set<Integer> difference = set1.difference(set2);
		assertEquals(0, difference.size());
		
		Set<Integer> symmetric = set1.symmetricDifference(set2);
		assertEquals(0, symmetric.size());
		
		assertTrue(set1.isSubset(set2));
		assertTrue(set1.isDisjoint(set2));
	}

}
