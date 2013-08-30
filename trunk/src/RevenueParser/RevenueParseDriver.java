/**
 * TODO Class description
 * Class Invariants:
 * TODO invariants list
 * 
 * @author Matt Scholes <mscholes3@gmail.com>
 * @version Jul 12, 2013
 *
 */
package RevenueParser;

import java.util.Arrays;
import java.util.Scanner;
import java.io.*;


public class RevenueParseDriver {

	public static void main(String[] args) throws IOException
	{
		int numTransLines = 0;
		int numFields = 0;
		
		//Assign the file's input stream to the Scanner object.
		  //check to see if the program was run with the command line argument
	    if(args.length < 1) {
	        System.out.println("Error, usage: java ClassName inputfile");
	        System.exit(1);
	    }
	     //check that 2nd and 3rd args are int's
	    if (args.length > 1) {
	        try {
	            numTransLines = Integer.parseInt(args[1]);
	            numFields = Integer.parseInt(args[2]);
	        } catch (NumberFormatException e) {
	            System.err.println("Arguments [" + args[1] + 
	            						 "] & [" + args[2]+ "] must be  integers");
	            System.exit(1);
	        }
	    }
		System.out.println("The number of transaction lines is: " + numTransLines);
		
		//initialize files for IO
		Scanner fileScanner = new Scanner(new FileInputStream(args[0]));
		PrintWriter fileWriter = new PrintWriter("ExcludedLines.txt");
		fileWriter.println("LINE\tCONTENTS");
		fileWriter.println("----\t--------");
		PrintWriter fileWriterReturn = new PrintWriter(args[0] + "_mod.txt");
		fileWriterReturn.println("Customer\tInvoice\tItem number\tDescription\t" +
				"Quantity\tUnit\tBalance\tValue\tMargin\tContribution ratio\tSales\tCost");
		
		//initialized array of valid transaction lines
		RevenueParser[] transLines = new RevenueParser[numTransLines];
		
//---------------------------------------------------------------------------------------

		
		int lineNo    = 0;
		int lineNoMod = 0;
		while(lineNo < numTransLines) {
			//debug
			if (lineNoMod == 123)
				System.out.println("HOLD");
			
			
			//parse by tab
			String[] lineTokens = fileScanner.nextLine().split("\t");
			
			/** Skip unimportant lines:
			 * empty lines
			 * subtotals
			 * header lines
			 */
			 //determine if the line is a subtotal
			boolean isSubtotal = true;
			if (lineTokens.length != 0){
				for(int i = 0; i < 6 && isSubtotal; i++){
					if (!(lineTokens[i].equals(""))) isSubtotal = false;
				}
			}
			
			 // skip and record unimportant lines
			if ((lineTokens.length == 0) ||
					(isSubtotal) ||
					(lineTokens[0].equals("\"Global Microwave Systems, Inc\"") ||
					   lineTokens[0].equals("Global Microwave Systems, Inc") ||
					   lineTokens[0].equals("Customer")))
			{
				//record skipped lines
				fileWriter.printf("%04d\t", lineNo+1);
				fileWriter.println(Arrays.toString(lineTokens));
				
				lineNo++;
				continue;
			}

			//make sure the appropriate number of fields is received
			if (lineTokens.length>numFields){
	            System.err.println("Program Logic Failure: More fields than expected.");
	            System.exit(1);
			}
			
			
			//make a new entry for a transaction line
			String[] line = new String[numFields];
			switch (lineTokens.length) {
				//nominal case
				case (10):
					for (int i = 0; i < line.length; i++){
						if(i<lineTokens.length)
								line[i] = lineTokens[i];
						else {
							line[i] = "";
						}
					}
					transLines[lineNoMod] = new RevenueParser(line);
					break;
				
				//account for empty fields
				case (6):
					int fieldShift1 = 2;
					int fieldShift2 = 4;
					for (int i = 0; i < line.length; i++){
						switch(i){
							case 0: case 1: case 4: case 5: case 10: case 11:
								line[i] = "";
								break;
							case 2: case 3:
								line[i] = lineTokens[i-fieldShift1];
								break;
							case 6: case 7: case 8: case 9:
								line[i] = lineTokens[i-fieldShift2];
								break;
						}
					}
					transLines[lineNoMod] = new RevenueParser(line);
					break;
			}
			
			lineNo++;
			lineNoMod++;
		}
		
		for (int i = 0; i <= lineNoMod; i++){
			fileWriterReturn.println(transLines[i]);
		}
		
		fileWriter.close();
		fileScanner.close();
	}
}