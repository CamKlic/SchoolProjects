package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Camden Klicker
 * This program decodes an encrypted message using binary tree algorithms from a file. It
 * builds a binary tree and stores characters in the leaves. Encoded characters are listed below.
 * Character Encoding
 * a 0
 * ! 100
 * d 1010
 * c 1011
 * r 110
 * b 111
 */
public class MsgTree
{
	/*
	 * node character of tree
	 */
	public char payloadChar;
	/*
	 * left subtree or "0"
	 */
	public MsgTree left;
	/*
	 * right subtree or "1"
	 */
	public MsgTree right;
	/*Can use a static char idx to the tree string for recursive
	solution, but it is not strictly necessary*/
	private static int staticCharIdx = 0;
	/*
	 * String for encoded string
	 */
	private static String encodedString;
	
	/*
	 * string of binary code for message
	 */
	private static String binaryEncodedString;
	
	/*
	 * filepath for encoded message
	 */
	private static String treeFile;
	
	private static String message;
	
	private static boolean isnotFile = false;
	
	//Constructor building the tree from a string
	public MsgTree(String encodingString)
	{
		this.payloadChar = encodingString.charAt(staticCharIdx); 
		staticCharIdx++; //Adds one to character index
		this.left = new MsgTree(encodingString.charAt(staticCharIdx));
		
		//Creating tree
		if(this.left.payloadChar == '^')
		{
			this.left = new MsgTree(encodingString);
		}
		
		staticCharIdx++;
		this.right = new MsgTree(encodingString.charAt(staticCharIdx)); //Creates message tree for right node
		if(this.right.payloadChar == '^')
		{
			this.right = new MsgTree(encodingString);
		}
		
	}
	//Constructor for a single node with null children
	public MsgTree(char payloadChar)
	{
		this.payloadChar = payloadChar;
	}
	//method to print characters and their binary codes
	public static void printCodes(MsgTree root, String code)
	{
		if(root==null) //Returns if no roots left
		{
			return;
		}
		char p = root.payloadChar;
		if(p != '^')
		{
			if(p == '\n')
			{
				System.out.print("\\" + "n" + "\t\t\t");
			}
			else
			{
				System.out.print(root.payloadChar + "\t\t\t");
			}
			System.out.println(code);
		}
		printCodes(root.left, code + "0"); // Calls left subtree and adds 0
		printCodes(root.right, code + "1"); //Calls right subtree and adds 1
	}
	public static void main(String[] args) throws FileNotFoundException
	{
		//Scanner scnr = new Scanner(System.in);
		//System.out.println("Enter file path:\n");

		try
		{
//			treeFile = scnr.nextLine().trim(); //Assigns string value
			treeFile = args[3]; //Assigns string value

			File nFile = new File(treeFile);
			if(!nFile.getAbsolutePath().endsWith(".arch")) //If file doesnt end with .arch
			{
				isnotFile = true;
				//scnr.close();
				throw new FileNotFoundException(); //exception thrown
			}
		}
		catch(Exception e)
		{
			if(isnotFile) //File exists but wrong type
			{
				//scnr.close();
				throw new FileNotFoundException("incorrect type must have .arch type File was" + treeFile);
			}
			else //File does not exist
			{
				//scnr.close();
				throw new FileNotFoundException("File does not exist File:" + treeFile);
			}
		}
		//scnr.close();
		createEncodedStrFromFile();
		MsgTree mainTree = new MsgTree(encodedString);
		
		System.out.println("character\t                  code: \n");
		printCodes(mainTree, "");
		System.out.println("------------------------\n");
		decode(mainTree, binaryEncodedString);
		
	}
	
	private static void createEncodedStrFromFile() throws FileNotFoundException
	{
		try(Scanner scnr = new Scanner(new File(treeFile)))
		{
			encodedString = scnr.nextLine();
			StringBuilder buildString = new StringBuilder(encodedString);
			String fileString = scnr.nextLine();
			
			for(int i = 0; i <fileString.length(); i++)
			{
				if(fileString.charAt(i) != '1' && fileString.charAt(i) != '0')
				{
					buildString.append("\n").append(fileString);
					binaryEncodedString = scnr.nextLine();
					break;
				}
				else
				{
					binaryEncodedString = fileString;
				}
			}
			encodedString = buildString.toString();
			
		}
		catch(FileNotFoundException e)
		{
			throw new FileNotFoundException("File does not exist and was:" + treeFile);
		}
	}
	
	public static void decode(MsgTree codes, String msg)
	{
		StringBuilder decodeMsg = new StringBuilder();
		MsgTree curr = codes;
		for(int i=0; i < msg.length(); i++)
		{
			if(msg.charAt(i) == '0')
			{
				curr=curr.left;
			}
			else
			{
				curr=curr.right;
			}
			
			if(curr.left == null || curr.right == null)
			{
				decodeMsg.append(curr.payloadChar);
				curr = codes;
			}
			 
		}
		if(curr != codes)
		{
			decodeMsg.append(curr.payloadChar);
		}
		message = decodeMsg.toString();
		System.out.println("Message:\n" + message);
	}
}