package rs.raf.os.test;

import rs.raf.os.dir.Directory;
import rs.raf.os.dir.DirectoryException;
import rs.raf.os.disk.Disk;
import rs.raf.os.fat.FAT16;

import java.util.Map;
import java.util.TreeMap;

import java.util.ArrayList;

public class MockDirectory implements Directory {

	FAT16 fat;
	Disk disk;

	TreeMap<String, File> files;

	public MockDirectory(FAT16 fat, Disk disk) {
		this.fat = fat;
		this.disk = disk;

		this.files = new TreeMap<String, File>();
	}
	
	@Override
	public boolean writeFile(String name, byte[] data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte[] readFile(String name) throws DirectoryException {
		if(this.files.containsKey(name)){
			return new byte[1];	
		} else {
			throw new DirectoryException("File not found! ");
		}
	}

	@Override
	public void deleteFile(String name) throws DirectoryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] listFiles() {
		ArrayList<String> fileList = new ArrayList<String>();

		for(Map.Entry<String, File> entry: this.files.entrySet()) {
			String name = entry.getKey();

			fileList.add(name);
		}

		String[] list = fileList.toArray(new String[fileList.size()]);

		return list;
	}

	@Override
	public int getUsableTotalSpace() {
		int FATNumberOfSectors = this.fat.getClusterCount() * this.fat.getClusterWidth();
		int DiskNumberOfSectors = this.disk.getSectorCount();

		int numberOfSectors = (FATNumberOfSectors < DiskNumberOfSectors)?FATNumberOfSectors:DiskNumberOfSectors;

		int usableTotalSpace = numberOfSectors * this.disk.getSectorSize();

		return usableTotalSpace;
	}

	@Override
	public int getUsableFreeSpace() {
		int usableTotalSpace = this.getUsableTotalSpace();

		for(Map.Entry<String, File> entry: this.files.entrySet()) {
			String name = entry.getKey();
			File file = entry.getValue();

			usableTotalSpace -= file.getSize();
		}

		return 0;
	}

}

class File {
	int cluster;
	String name;

	MockDirectory directory;

	public File(int cluster, String name, MockDirectory directory){
		this.cluster = cluster;
		this.name = name;

		this.directory = directory;
	}

	public int getSize(){
		int clusterSize = this.directory.fat.getClusterWidth();
		int sectorSize = this.directory.disk.getSectorSize();

		clusterSize *= sectorSize;

		int counter = 0;
		int cluster = this.cluster;

		do {
			counter++;
			
			cluster = this.directory.fat.readCluster(cluster);
		} while(cluster != this.directory.fat.getEndOfChain());

		return counter * clusterSize;
	}
}

