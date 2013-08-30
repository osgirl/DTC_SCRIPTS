package RevenueParser;
/**
 * Defines class behavior for parsing GMS revenue report
 * Class Invariants:
 * TODO invariants list
 * 
 * @author Matt Scholes <mscholes3@gmail.com>
 * @version Jul 11, 2013
 *
 */

import java.util.Arrays;

public class RevenueParser {
	
	private String customerNumber;
	private String invoiceNumber;
	private String itemNumber;
	private String transDescription;
	private String orderQuantity;
	private String unitOfMeasure;
	private String orderBalance;
	private String orderValue;
	private String orderMargin;
	private String contributionRatio;
	private String orderSales;
	private String orderCost;
	private int numFields = 12;
	
	public RevenueParser(String[] line){
		if (line.length != numFields)
			throw new RuntimeException("Not enought fields: " + numFields + 
					" expected, " + line.length + "recieved.");
		
		this.customerNumber		= line[0];
		this.invoiceNumber		= line[1];
		this.itemNumber			= line[2];
		this.transDescription	= line[3];
		this.orderQuantity		= line[4];
		this.unitOfMeasure		= line[5];
		this.orderBalance		= line[6];
		this.orderValue			= line[7];
		this.orderMargin		= line[8];
		this.contributionRatio	= line[9];
		this.orderSales			= line[10];
		this.orderCost			= line[11];
	}
	
	
	public String toString(){
		return (customerNumber 		+ "\t" + invoiceNumber	+ "\t" + itemNumber		+ "\t" +
				transDescription 	+ "\t" + orderQuantity	+ "\t" + unitOfMeasure	+ "\t" +
				orderBalance 		+ "\t" + orderValue		+ "\t" + orderMargin	+ "\t" +
				contributionRatio	+ "\t" + orderSales		+ "\t" + orderCost);
	}
}
