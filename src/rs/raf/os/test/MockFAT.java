package rs.raf.os.test;

import rs.raf.os.fat.FAT16;
import rs.raf.os.fat.FATException;

import rs.raf.os.disk.Disk;

public class MockFAT implements FAT16 {

	private int clusterWidth;
	private int clusterCount; 

	private int[] table;

	private Disk disk;

	public MockFAT(int clusterWidth) {
		this.clusterWidth = clusterWidth;
		this.clusterCount = 0xFFEF-2;

		this.table = new int[this.clusterCount];
	}
	
	public MockFAT(int clusterWidth, int clusterCount) {
		this.clusterWidth = clusterWidth;
		this.clusterCount = clusterCount;

		this.table = new int[this.clusterCount];
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
		if(clusterID >= 2 && clusterID < this.clusterCount + 2){
			return this.table[clusterID - 2];
		} else {
			throw new FATException("Cluster ID out of range");
		}
	}

	@Override
	public void writeCluster(int clusterID, int valueToWrite) throws FATException {
		if(clusterID >= 2 && clusterID < this.clusterCount + 2){
			this.table[clusterID - 2] = valueToWrite;
		} else {
			throw new FATException("Cluster ID out of range");
		}
	}

	@Override
	public String getString() {
		String result = "[";

		for(int i = 0; i < this.table.length; i++){
			if(i != 0){
				result += "|";
			}

			result += table[i];
		}

		result += "]";

		return result;
	}
}
