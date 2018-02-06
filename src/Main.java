import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		
		String filename1 = "following";
		String filename2 = "followers";
		
		writeToFile(peopleToUnfollow(purgeList("assets/" + filename1 + ".txt"), purgeList("assets/" + filename2 + ".txt")), 
				"assets/users_to_unfollow.txt");
	}

	private static ArrayList<String> purgeList(String fileList) {
		ArrayList<String> followingList = new ArrayList<String>();
		ArrayList<String> followingList2 = new ArrayList<String>();
		ArrayList<String> controlList = new ArrayList<String>();
		ArrayList<String> controlList2 = new ArrayList<String>();
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(fileList));
		    String line = br.readLine();

		    while (line != null) {
		        line = br.readLine();
		        followingList.add(line);
		    }
		    
		    for(int i = 0; i < followingList.size()-1; i++) {
		    	followingList2.add(followingList.get(i).replace("\\", ""));
		    }
		    
		    followingList2.removeAll(Arrays.asList("", null));
		    
		    for(String s : followingList2) {
		    	boolean hasCapital = false;
			    String test2 = s.toLowerCase();
			    hasCapital = !s.equals(test2);
		    	if(s.contains(" ") || hasCapital || !s.chars().allMatch(Character::isLetter) || s.length() == 1) {
		    		controlList.add(s);
		    	}
		    }
		    
		    for(String s : controlList) {
		    	if(s.contains(".") || s.contains("_")) {
		    		controlList2.add(s);
		    	}
		    }
		    
		    controlList.removeAll(controlList2);
		    followingList2.removeAll(controlList);
		    
		    controlList.clear();
		    
		    for(String s : followingList2) {
		    	boolean hasCapital = false;
			    String test2 = s.toLowerCase();
			    hasCapital = !s.equals(test2);
		    	if(s.contains(" ") || hasCapital) {
		    		controlList.add(s);
		    	}
		    }
		    
		    followingList2.removeAll(controlList);
		
		    br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return followingList2;
	}
	
	private static ArrayList<String> peopleToUnfollow(ArrayList<String> following, ArrayList<String> followers) {
		following.removeAll(followers);
		return following;
	}
	
	private static void writeToFile(ArrayList<String> list, String filename) {
		Path fileToDeletePath = Paths.get(filename);
		try {
			Files.delete(fileToDeletePath);
		} catch (IOException e1) {}
		
		FileWriter writer;
		try {
			writer = new FileWriter(filename);
			
			for(String s: list) {
				if(list.indexOf(s) != list.size()-1) {
					writer.write(s + "\n");
				}else {
					writer.write(s);
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

}
