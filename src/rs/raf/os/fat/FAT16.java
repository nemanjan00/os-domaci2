package rs.raf.os.fat;

/**
 * Representation of the File Allocation Table. Stores cluster values for files that are written to a disk.
 * @author Branislav Milojković
 *
 */
public interface FAT16 {
	
	/**
	 * @return Value representing the end of the chain.
	 * Should be {@code 0xFFF8} for FAT16
	 */
	int getEndOfChain();
	
	/**
	 * @return How many possible data clusters are there in this table.
	 * Should be {@code 0xFFEF-2} for FAT16
	 */
	int getClusterCount();
	
	/**
	 * @return How many sectors a cluster will occupy.
	 */
	int getClusterWidth();
	
	/**
	 * Retrieves the value for a cluster.
	 * @param clusterID Cluster number. Data clusters start at 0x0002, and end at 0xFFEF (for FAT16). Special cluster values are:
	 * <ul>
	 * 	<li>0x0000: Free cluster</li>
	 *  <li>0xFFF8: Last cluster in file - End Of Chain marker</li>
	 * </ul>  
	 * @exception FATException This exception will be thrown if an invalud @{code clusterID} is given
	 */
	int readCluster(int clusterID) throws FATException;
	
	/**
	 * Writes a cluster value in the table.
	 * @param clusterID Cluster number. Data clusters start at 0x0002, and end at 0xFFEF. Special cluster values are:
	 * <ul>
	 * 	<li>0x0000: Free cluster</li>
	 *  <li>0xFFF8: Last cluster in file - End Of Chain</li>
	 * </ul>  
	 * @param valueToWrite A two-byte value to write into the table.
	 * @exception FATException This exception will be thrown if an invalid {@code clusterID} is given.
	 */
	void writeCluster(int clusterID, int valueToWrite) throws FATException;
	
	/**
	 * @return Provides a string representation of the table, which should be of the format:<br>
	 * {@code [cluster2|cluster3|....|clusterN] }<br>
	 * Where each value is the corresponding value in the FAT table, written in base 10.
	 * For example, if we have a disk with four clusters that holds a single file, stored consecutively in all four clusters,
	 * the returned string should be:<br>
	 * {@code [3|4|5|65528]}
	 */
	String getString();
}
