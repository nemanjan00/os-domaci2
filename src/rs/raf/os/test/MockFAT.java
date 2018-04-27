package rs.raf.os.test;

import rs.raf.os.fat.FAT16;
import rs.raf.os.fat.FATException;

import rs.raf.os.disk.SimpleDisk;

public class MockFAT implements FAT16 {

	private int clusterWidth;
	private int clusterCount; 

	public MockFAT(int clusterWidth) {
		this.clusterWidth = clusterWidth;
		this.clusterCount = 0xFFEF-2;
	}
	
	public MockFAT(int clusterWidth, int clusterCount) {
		this.clusterWidth = clusterWidth;
		this.clusterCount = clusterCount;
	}
	
	@Override
	public int getEndOfChain() {
		return 0xFFF8;
	}

	@Override
	public int getClusterCount() {
		return this.clusterCount;
	}

	@Override
	public int getClusterWidth() {
		return this.clusterWidth;
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
