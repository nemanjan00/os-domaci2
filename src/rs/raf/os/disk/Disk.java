package rs.raf.os.disk;

/**
 * Represents a disk. It allows for reading and writing of sectors that are fixed size.
 * The default implementation is {@link SimpleDisk}
 * @author Branislav Milojković
 *
 */
public interface Disk {

	/**
	 * Gives the size of sectors on this disk, in bytes.
	 */
	int getSectorSize();
	
	/**
	 * Gives the number of sectors on this disk.
	 */
	int getSectorCount();
	
	/**
	 * Gives the total amount of space on the disk, in bytes.
	 */
	int diskSize();
	
	/**
	 * Reads a sector from the disk. Returns an array of length
	 * that is the same as {@code getSectorSize()}
	 * @param sectorID Sector number. Sectors start at 0, and end at {@code getSectorCount()-1}
	 * @return Array of {@code byte}, that is of length {@code getSectorSize()}. If no writes have been done to this sector,
	 * the array will be 0-filled.
	 * @exception DiskException Will be thrown if invalid sectorID is provided.
	 */
	byte[] readSector(int sectorID) throws DiskException;
	
	/**
	 * Reads multiple sectors from the disk. Returns an array of length {@code getSectorSize() * count}
	 * @param startingSector Starting sector number. Sectors start at 0, and end at {@code getSectorCount()-1}.
	 * @param count Number of sectors to be read.
	 * @return Array of {@code byte}, that is of length {@code getSectorSize() * count}. If no writes have been done to these sectors,
	 * the array will be 0-filled.
	 * @exception DiskException Will be thrown if invalid parameters are provided.
	 */
	byte[] readSectors(int startingSector, int count) throws DiskException;
	
	/**
	 * Writes a sector to the disk. The {@code sectorID} represents the sector number, and {@code sectorData} is the data
	 * to be written.
	 * @param sectorID Sector number. Sector numbers start with 0, and end at {@code getSectorCount() - 1}.
	 * @param sectorData Data to be written. It must be an array of bytes that is of length {@code getSectorSize()}.
	 * @throws DiskException This exception is thrown in the case that data is of invalid length or if trying to write to an invalid sector.
	 */
	void writeSector(int sectorID, byte[] sectorData) throws DiskException;
	
	/**
	 * Writes {@code count} sectors to the disk, starting from {@code startingSector}.
	 * @param startingSector Starting sector number. Sector numbers start with 0, and end at {@code getSectorCount() - 1}.
	 * @param count Number of sectors to be written.
	 * @param sectorData Data to be written. It must be an array of bytes that is of length {@code getSectorSize() * count}.
	 * @throws DiskException This exception is thrown in the case that data is of invalid length or if trying to write to an invalid sector.
	 */
	void writeSectors(int startingSector, int count, byte[] sectorData) throws DiskException;
}
