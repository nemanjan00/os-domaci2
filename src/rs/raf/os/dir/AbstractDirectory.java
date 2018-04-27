package rs.raf.os.dir;

import rs.raf.os.disk.Disk;
import rs.raf.os.fat.FAT16;

public abstract class AbstractDirectory implements Directory {

	protected FAT16 fat;
	protected Disk disk;
	
	public AbstractDirectory(FAT16 fat, Disk disk) {
		this.fat = fat;
		this.disk = disk;
	}
}
