package rs.raf.os.GUI;

import processing.core.PApplet;

public class Disk extends PApplet {
	public Disk() {
		
	}
	
	public static void main(String[] args) {
		PApplet.main("rs.raf.os.GUI.Disk");
	}
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public void settings(){
		size(400, 400);
	}

	public void setup(){
		textSize(17);
	}

	public void draw(){
		int sectorCount = GUI.disk.getSectorCount();
		int width = GUI.fat.getClusterWidth();

		for(int i = 0; i < sectorCount; i++){
			if(GUI.fat.readCluster((i / width) + 2) != 0) {
				fill(0, 0, 0);
			} else {
				fill(255, 255, 255);
			}
			
			rect(10 * (i % 40), (i / 40) * 10, 10, 10);
		}
	}
	
	public void mouseClicked() {
		int sector = (mouseY / 10) * 40 + mouseX / 10;
		int sectorCount = GUI.disk.getSectorCount();
		
		if(sectorCount / 40 < mouseY / 10) {
			return;
		}
		
		if(sector < sectorCount) {
			System.out.println(sector + " = " + bytesToHex(GUI.disk.readSector(sector)));
		}
	}
}
