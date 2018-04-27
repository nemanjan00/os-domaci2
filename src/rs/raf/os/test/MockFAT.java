package rs.raf.os.test;

import rs.raf.os.fat.FAT16;
import rs.raf.os.fat.FATException;

public class MockFAT implements FAT16 {

	public MockFAT(int clusterWidth) {
		// TODO Auto-generated constructor stub
	}
	
	public MockFAT(int clusterWidth, int clusterCount) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getEndOfChain() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getClusterCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getClusterWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readCluster(int clusterID) throws FATException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeCluster(int clusterID, int valueToWrite) throws FATException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}

}
