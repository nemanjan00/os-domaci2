package rs.raf.os.GUI;

import processing.core.PApplet;
import rs.raf.os.test.MockDirectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

public class FAT extends PApplet  {
	public static String filename = "";
	
	public static TreeMap<Integer, Boolean> red = new TreeMap<Integer, Boolean>();
	
	public FAT() {
		new Thread(new FATThread()).start();
	}
	
	public static void main(String[] args) {
		PApplet.main("rs.raf.os.GUI.FAT");
	}
	
	public void settings(){
		size(400, 400);
	}

	public void setup(){
		textSize(17);
	}

	public void draw(){
		int clusterCount = GUI.fat.getClusterCount();

		for(int i = 2; i < clusterCount + 2; i++){
			if(FAT.red.containsKey(i)) {
				fill(255, 0, 0);
			} else {
				fill(255, 255, 255);
			}
			rect(40 * ((i - 2) % 10), ((i - 2) / 10) * 20, 40, 20);
			
			fill(0, 0, 0);
			text(GUI.fat.readCluster(i),  40 * ((i - 2) % 10) + 7, 17 + ((i - 2) / 10) * 20);
		}
	}
}

class FATThread implements Runnable {
	@Override
	public void run() {		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter file name: ");
		
		while(true) {
			try {
				FAT.filename = in.readLine();
				
				if(((MockDirectory)GUI.directory).files.containsKey(FAT.filename)) {
					System.out.println("Exists");
					FAT.red = new TreeMap<Integer, Boolean>();
					
					int cluster = ((MockDirectory)GUI.directory).files.get(FAT.filename).cluster;
					FAT.red.put(cluster, true);
					
					do {
						cluster = GUI.fat.readCluster(cluster);

						FAT.red.put(cluster, true);
					} while(cluster != GUI.fat.getEndOfChain());

				} else {
					System.out.println("Does not exist");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
