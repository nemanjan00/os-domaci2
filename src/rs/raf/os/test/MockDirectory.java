package rs.raf.os.test;

import rs.raf.os.dir.Directory;
import rs.raf.os.dir.DirectoryException;
import rs.raf.os.disk.Disk;
import rs.raf.os.fat.FAT16;

import rs.raf.os.disk.DiskUtil;

import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;

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
		if(this.files.containsKey(name)){
			if(this.getUsableFreeSpace() + (int)Math.ceil((double)this.files.get(name).size * this.fat.getClusterWidth() * this.disk.getSectorSize()) >= data.length){
				this.deleteFile(name);
			}
		}

		if(this.getUsableFreeSpace() < data.length){
			return false;
		}

		int cluster = 0;

		int clustersRemaining = (int)Math.ceil((double)data.length / (this.disk.getSectorSize() * this.fat.getClusterWidth()));
		int clustersWritten = 0;

		int lastSector = 0;

		for(int i = 2; i < this.fat.getClusterCount() + 2; i++){
			if(this.fat.readCluster(i) == 0){
				if(lastSector != 0){
					this.fat.writeCluster(lastSector, i);
				} else {
					cluster = i;
				}

				lastSector = i;

				disk.writeSectors((i - 2) * this.fat.getClusterWidth(), this.fat.getClusterWidth(), DiskUtil.slice(data, clustersWritten * this.fat.getClusterWidth() * this.disk.getSectorSize(), this.fat.getClusterWidth() * this.disk.getSectorSize()));

				clustersRemaining--;
				clustersWritten++;

				if(clustersRemaining == 0){
					this.fat.writeCluster(i, this.fat.getEndOfChain());

					break;
				}
			}
		};

		this.files.put(name, new File(name, data.length, cluster));

		return true;
	}

	@Override
	public byte[] readFile(String name) throws DirectoryException {
		if(this.files.containsKey(name)){
			File file = this.files.get(name);

			int cluster = file.cluster;

			byte[] data = {};
			byte[] newData;
			byte[] newDataConcatenated;

			do {
				newData = this.disk.readSectors((cluster - 2) * this.fat.getClusterWidth(), this.fat.getClusterWidth());

				newDataConcatenated = Arrays.copyOf(data, data.length + newData.length);
				System.arraycopy(newData, 0, newDataConcatenated, data.length, newData.length);
				data = newDataConcatenated;

				cluster = this.fat.readCluster(cluster);
			} while(cluster != this.fat.getEndOfChain());

			data = DiskUtil.slice(data, 0, file.size);

			return data;
		} else {
			throw new DirectoryException("File not found! ");
		}
	}

	@Override
	public void deleteFile(String name) throws DirectoryException {
		if(this.files.containsKey(name)){
			File file = this.files.get(name);

			int cluster = file.cluster;
			int newCluster;

			do {
				newCluster = this.fat.readCluster(cluster);

				this.fat.writeCluster(cluster, 0);

				cluster = newCluster;
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

		int clusterSize = this.fat.getClusterWidth() * this.disk.getSectorSize();

		for(Map.Entry<String, File> entry: this.files.entrySet()) {
			File file = entry.getValue();

			usableTotalSpace -= Math.ceil((double)file.size / clusterSize) * clusterSize;
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

