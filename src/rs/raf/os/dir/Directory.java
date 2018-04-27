package rs.raf.os.dir;

public interface Directory {

	/**
	 * Writes a file to the disk under a given name.
	 * @param name Name of the file. If a file with this name already exists, it will be overwritten.
	 * @param data Array of bytes representing the contents of the file.
	 * @return {@code true} if the file was successfully written. {@code false} if there was not enough space on disk.
	 * If the file is being overwritten, and there isn't enough space for the new file, the old one must be kept on the disk.
	 */
	boolean writeFile(String name, byte[] data);
	
	/**
	 * Retrieves the contents of a file, based on a given name.
	 * @param name The name of the file. Should be same as the one used when writing.
	 * @return Array of bytes representing the contents of the file.
	 * @exception DirectoryException This exception will be thrown if no such file exists.
	 */
	byte[] readFile(String name) throws DirectoryException;
	
	/**
	 * If a file with the given name exists on the disk, it is removed. Otherwise, nothing.
	 * @param name The name of the file that needs to be deleted.
	 * @exception DirectoryException This exception will be thrown if no such file exists.
	 */
	void deleteFile(String name) throws DirectoryException;
	
	/**
	 * @return Provides a list of names of files that have been written to the disk.
	 */
	String[] listFiles();
	
	/**
	 * @return Provides the total size of the disk in bytes, taking into account the size of the disk, as well
	 * as the parameters of the file system that is used. In particular, it returns whichever is smaller:
	 * <ul>
	 *   <li>Physical disk size</li>
	 *   <li>FAT allocation capacity</li>
	 * </ul>
	 */
	int getUsableTotalSpace();
	
	/**
	 * @return Provides the amount of space left on the disk for writing new files. It should take {@code getUsableDiskSize()}
	 * and subtract the space taken up by allocated sectors.
	 */
	int getUsableFreeSpace();
}
