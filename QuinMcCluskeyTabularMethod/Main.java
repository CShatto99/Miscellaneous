import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		char[] variables = setVariables(in);
		int[] minTerms = setMinTerms(in);
		String[] binaryMinTerms = decimalToBinary(variables, minTerms);
		
		ArrayList<Group> groups = computeSuccessiveGroups(minTerms, binaryMinTerms);
		
		displayInput(variables, minTerms);
		printPIs(groups, variables);
	}
	
	private static char[] setVariables(Scanner in) {
		System.out.print("Enter the number of variables: ");
		int numVariables = in.nextInt();
		char[] variables = new char[numVariables];
		
		System.out.print("Enter each variable separated by spaces: ");
		for(int i = 0; i < numVariables; i++) {
			variables[i] = in.next().charAt(0);
		}
		return variables;
	}
	
	private static int[] setMinTerms(Scanner in) {
		System.out.print("Enter the number of min terms: ");
		int numMinTerms = in.nextInt();
		int[] minTerms = new int[numMinTerms];
		
		System.out.print("Enter each min term separated by spaces: ");
		for(int i = 0; i < numMinTerms; i++) {
			minTerms[i] = in.nextInt();
		}
		return minTerms;
	}
	
	private static String[] decimalToBinary(char[] variables, int[] minTerms) {
		String[] binaryMinTerms = new String[minTerms.length];
		for(int i = 0; i < minTerms.length; i++) {
			binaryMinTerms[i] = Integer.toBinaryString(minTerms[i]);
			if(binaryMinTerms[i].length() < variables.length) {
				String zeros = "";
				int len = binaryMinTerms[i].length();
				while(len < variables.length) {
					zeros = zeros.concat("0");
					len++;
				}
				binaryMinTerms[i] = zeros + binaryMinTerms[i];
			}
		}
		return binaryMinTerms;
	}
	
	private static void displayInput(char[] variables, int[] minTerms) {
		System.out.print("Function: f(");
		for(int i = 0; i < variables.length; i++) {
			if(i == variables.length-1)
				System.out.print(variables[i]);
			else
				System.out.print(variables[i] + ", ");
		}
		System.out.print(") = SIGMAm(");
		for(int i = 0; i < minTerms.length; i++) {
			if(i == minTerms.length-1)
				System.out.print(minTerms[i]);
			else
				System.out.print(minTerms[i] + ", ");
		}
		System.out.println(")");
	}
	
	private static ArrayList<Group> computeSuccessiveGroups(int[] minTerms, String[] binaryMinTerms) {
		ArrayList<Group> groups = new ArrayList<Group>();
		for(int i = 0; i < minTerms.length; i++)
			groups.add(new Group(Integer.toString(minTerms[i]), binaryMinTerms[i]));
		
		int sizeBeforeRemoval = 0, currentSize;
		while(sizeBeforeRemoval != groups.size()) {
			currentSize = groups.size();
			for(int i = 0; i < currentSize; i++) {
				for(int j = i+1; j < currentSize; j++) {
					if(differences(groups.get(i).binary, groups.get(j).binary) == 1) {
						String combinedBin = combineBinary(groups.get(i).binary, groups.get(j).binary);
						
						groups.get(i).grouped = true;
						groups.get(j).grouped = true;
						
						if(isUniqueBin(groups, combinedBin)) {
							String newTuple = groups.get(i).decimal + "," + groups.get(j).decimal;
							groups.add(new Group(newTuple, combinedBin));
						}
						else break;
					}
				}
			}
			
			sizeBeforeRemoval = groups.size();
			
			Iterator<Group> iter = groups.iterator();
			while(iter.hasNext()) {
				Group next = iter.next();
				if(next.grouped)
					iter.remove();
			}
		}
		return groups;
	}
	
	private static int differences(String a, String b) {
		int diff = 0;
		
		for(int i = 0; i < a.length(); i++)
			if(a.charAt(i) != b.charAt(i))
				diff++;
		
		return diff;
	}
	
	private static String combineBinary(String a, String b) {
		String combined = "";
		for(int i = 0; i < a.length(); i++) {
			if(a.charAt(i) != b.charAt(i))
				combined = combined.concat("-");
			else
				combined = combined.concat(Character.toString(a.charAt(i)));
		}
		return combined;
	}
	
	private static boolean isUniqueBin(ArrayList<Group> groups, String s) {
		for(int i = 0; i < groups.size(); i++)
			if(groups.get(i).binary.equals(s))
				return false;
		
		return true;
	}
	
	private static void printPIs(ArrayList<Group> groups, char[] variables) {
		System.out.println("Prime Implicants: ");
		for(int i = 0; i < groups.size(); i++)
			System.out.println(groups.get(i).decimal + " | " + binaryToMinTerm(variables, groups.get(i).binary));
	}
	
	private static String binaryToMinTerm(char[] variables, String s) {
		String minTerm = "";
		
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) != '-') {
				if(s.charAt(i) == '0')
					minTerm = minTerm.concat(Character.toString(variables[i]) + "'");
				else
					minTerm = minTerm.concat(Character.toString(variables[i]));
			}
		}
		
		return minTerm;
	}
}
