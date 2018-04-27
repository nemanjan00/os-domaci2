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
		return true;
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
		if(this.files.containsKey(name)){
			File file = this.files.get(name);

			int cluster = file.cluster;

			do {
				cluster = this.fat.readCluster(cluster);
				this.fat.writeCluster(cluster, 0);
			} while(cluster != this.fat.getEndOfChain());

			this.files.remove(name);
		} else {
			throw new DirectoryException("File not found! ");
		}
		
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
			File file = entry.getValue();

			// TODO: ceil file.size to * of cluster size
			usableTotalSpace -= file.size;
		}

		return usableTotalSpace;
	}
}

class File {
	String name;
	int size;
	int cluster;

	public File(String name, int size, int cluster){
		this.size = size;
		this.name = name;
		this.cluster = cluster;
	}
}

