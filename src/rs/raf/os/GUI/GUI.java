package rs.raf.os.GUI;

public class GUI {
	public static rs.raf.os.disk.Disk disk = new rs.raf.os.disk.SimpleDisk(8, 128);
	public static rs.raf.os.fat.FAT16 fat = new rs.raf.os.test.MockFAT(4, 32);
	public static rs.raf.os.dir.Directory directory = new rs.raf.os.test.MockDirectory(fat, disk);

	public static void main(String[] args) {
		new GUI();
		Disk.main(args);
		FAT.main(args);
	}

	GUI() {
		directory.writeFile("test", "Neki totalno lud string... ".getBytes());
		directory.writeFile("test1", "Neki totalno lud string... 1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111".getBytes());

	}
}
