package rs.raf.os.test;

public class File {
	String name;
	public int size;
	public int cluster;

	public File(String name, int size, int cluster){
		this.size = size;
		this.name = name;
		this.cluster = cluster;
	}
}