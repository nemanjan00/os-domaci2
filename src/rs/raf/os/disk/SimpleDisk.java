package rs.raf.os.disk;

/**
 * Implementation of a disk based on byte arrays.
 * @author Branislav Milojković
 *
 */
public class SimpleDisk implements Disk {

	private int sectorSize;
	private int sectorCount;
	
	private byte[][] diskData;
	
	public SimpleDisk(int sectorSize, int sectorCount) {
		this.sectorCount = sectorCount;
		this.sectorSize = sectorSize;
		
		diskData = new byte[sectorCount][sectorSize];
	}
	
	public SimpleDisk() {
		this(512, 2880);
	}
	
	@Override
	public int getSectorCount() {
		return sectorCount;
	}
	
	@Override
	public int getSectorSize() {
		return sectorSize;
	}
	
	@Override
	public int diskSize() {
		return sectorCount * sectorSize;
	}
	
	@Override
	public void writeSector(int sectorID, byte[] sectorData) throws DiskException {
		if (sectorData.length != sectorSize) {
			throw new DiskException("Error: Trying to write data of length: " + sectorData.length + " to a disk with sectors of size: " + sectorSize);
		}
		if (sectorID < 0 || sectorID >= sectorCount) {
			throw new DiskException("Error: Trying to write to sector: " + sectorID + " on a disk with " + sectorCount + " sectors." );
		}
		diskData[sectorID] = sectorData;
	}
	
	@Override
	public void writeSectors(int startingSector, int count, byte[] sectorData) throws DiskException {
		if (sectorData.length != count * sectorSize) {
			throw new DiskException("Error: Trying to write data of length: " + sectorData.length + " to " + count + " sectors on a disk with sectors of size: " + sectorSize);
		}
		if (startingSector < 0 || startingSector+count > sectorCount) {
			throw new DiskException("Error: Trying to write " + count + " sectors to position: " + startingSector + " on a disk with " + sectorCount + " sectors." );
		}
		
		for(int i = 0; i<count; i++) {
			int currentSector = startingSector + i;
			byte[] currentSectorData = DiskUtil.slice(sectorData, i*sectorSize, sectorSize);
			
			writeSector(currentSector, currentSectorData);
		}
	}
	
	@Override
	public byte[] readSector(int sectorID) throws DiskException {
		if (sectorID < 0 || sectorID >= sectorCount) {
			throw new DiskException("Error: trying to read sector " + sectorID + " on a disk with " + sectorCount + " sectors.");
		}
		return diskData[sectorID];
	}
	
	@Override
	public byte[] readSectors(int startingSector, int count) throws DiskException {
		if (startingSector < 0 || startingSector + count > sectorCount) {
			throw new DiskException("Error: trying to read " + count + " sectors from position " + startingSector + " on a disk with " + sectorCount + " sectors.");
		}
		
		byte[] toReturn = new byte[sectorSize * count];
		
		for (int i = 0; i < count; i++) {
			byte[] currentSector = readSector(startingSector+i);
			
			for (int j = 0; j<sectorSize; j++) {
				toReturn[i*sectorSize+j] = currentSector[j];
			}
		}
		return toReturn;
	}
}
