import java.util.ArrayList;
import java.util.Collections;


public class TeamFormer {
	
	public static void main(String[] args) {
		ArrayList<Float> speeds = new ArrayList<Float>();
		int numTeams = 4, players = 16, teamSize = players/numTeams;
		
		// insert random speeds
		for(int i = 0; i < players; i++) {
			float speed = (float) (Math.random()*10);
			speeds.add(speed);
		}
		
		float[][] teams = formTeams(speeds, numTeams);
		displayTeams(teams, teamSize);
	}
	
	public static float[][] formTeams(ArrayList<Float> speeds, int numTeams) {
		int teamSize = speeds.size() / numTeams;
		float[][] teams = new float[numTeams][teamSize];
		
		// sort the speeds
		Collections.sort(speeds);
		
		// print out the sorted speeds
		System.out.print("Speeds: ");
		for(float speed : speeds)
			System.out.print(speed + " ");
		System.out.println("\n");
		
		// for every team
		for(int i = 0; i < numTeams; i++) {
			// add the front "teamSize/2" speeds to the current team.
			for(int j = 0; j < teamSize/2; j++) {
				teams[i][j] = speeds.get(0);
				speeds.remove(0);
			}
			// add the back "teamSize/2" speeds to the current team.
			for(int j = teamSize/2; j < teamSize; j++) {
				teams[i][j] = speeds.get(speeds.size()-1);
				speeds.remove(speeds.size()-1);
			}
		}
		return teams;
	}
	
	public static void displayTeams(float[][] teams, int teamSize) {
		for(int i = 0; i < teams.length; i++) {
			float teamAvg = 0;
			System.out.print("Team " + (i+1) + ": ");
			for(int j = 0; j < teams[0].length; j++) {
				System.out.print(teams[i][j] + " ");
				teamAvg += teams[i][j];
			}
			System.out.println("\nTeam Average: " + teamAvg/teamSize + "\n");
		}
	}
}
