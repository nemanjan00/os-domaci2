package rs.raf.os.test;

import static org.junit.Assert.*;

import org.junit.Test;

import rs.raf.os.fat.FAT16;
import rs.raf.os.fat.FATException;

public class FAT16Test {

	@Test
	public void simpleWriteReadTest() {
		//4 clusters, each one sector width
		FAT16 fat = new MockFAT(1, 4);
		
		assertEquals(1, fat.getClusterWidth());
		assertEquals(4, fat.getClusterCount());
		assertEquals(0xFFF8, fat.getEndOfChain());
		
		fat.writeCluster(2, 3);
		fat.writeCluster(5, 4);
		assertEquals(3, fat.readCluster(2));
		assertEquals(4, fat.readCluster(5));
		
		assertEquals("[3|0|0|4]", fat.getString());
	}
	
	@Test(expected=FATException.class)
	public void invalidWriteTest1() {
		//4 clusters, each one sector width
		FAT16 fat = new MockFAT(1, 4);
				
		fat.writeCluster(0, 1);
	}
	
	@Test(expected=FATException.class)
	public void invalidWriteTest2() {
		//4 clusters, each one sector width
		FAT16 fat = new MockFAT(1, 4);
		
		fat.writeCluster(6, 1);
	}
	
	@Test(expected=FATException.class)
	public void invalidReadTest1() {
		//4 clusters, each one sector width
		FAT16 fat = new MockFAT(1, 4);
				
		fat.readCluster(0);
	}
	
	@Test(expected=FATException.class)
	public void invalidReadTest2() {
		//4 clusters, each one sector width
		FAT16 fat = new MockFAT(1, 4);
				
		fat.readCluster(6);
	}

}
