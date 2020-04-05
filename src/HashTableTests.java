import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HashTableTests {
	
	HashTable<String> nulltable = new HashTable<String>(10);
	HashTable<String> stringtable = new HashTable<String>(10);
	HashTable<Integer> inttable = new HashTable<Integer>(10);
	
	@BeforeEach
	void setup() {
		stringtable.add("Minh");
		stringtable.add("Quan");
		stringtable.add("Do");
		
		stringtable.add("Emily");
		stringtable.add("Littell");
		
		stringtable.add("Cho");
		stringtable.add("Jang");
		
		stringtable.add("Jie");
		stringtable.add("Gao");
		
		stringtable.add("Shash");
		
		inttable.add(105);
		inttable.add(26);
		inttable.add(11);
	}
	
	@Test
	void nulltable() {
		
		Object[] nullarr = nulltable.toArray();
		assertEquals(nullarr.length, 0);
		
		assertEquals(-1.0, nulltable.avgTreeHeight(true));
		assertEquals(0.0, nulltable.avgNumLeaves(true));
		assertEquals(0.0, nulltable.avgTreeSize(true));
		
		assertEquals(new Pair<Integer, Integer>(-1, -1), nulltable.minAndMaxTreeHeight());
		assertEquals(new Pair<Integer, Integer>(0, 0), nulltable.minAndMaxNumLeaves());
		assertEquals(new Pair<Integer, Integer>(0, 0), nulltable.minAndMaxTreeSize());
		
		assertTrue(nulltable.rehash(20));
		
		assertFalse(nulltable.remove("Minh"));
		
		assertFalse(nulltable.contains("Minh"));
	}
	
	@Test 
	void rehashTest() {
		assertEquals(20, stringtable.getLength());
		stringtable.rehash(2);
		assertEquals(16, stringtable.getLength());
	}
}
