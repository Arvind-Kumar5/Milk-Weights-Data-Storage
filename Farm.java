package application;

public class Farm {
	private int id; //The farm id
	private int year; //The year of the farm
	private int month; // the month of the farm
	private int day; // the day of the farm
	private double weight; // the weight of the farm
	private double totalWeight; // the total weight of the farm for month or year
	private double percentWeight; // the percent weight of the farm for month or year
	
	/**
	 * Constructor for the farm class
	 * @param id the farm id
	 * @param year the year
	 * @param month the month
	 * @param day the day
	 * @param weight the weight
	 * @throws NumberFormatException if the arguments are not numbers
	 */
	public Farm(String id, String year, String month, String day, String weight) 
			throws NumberFormatException{
		
		try {
			
			//Convert the arguments into ints and doubles and initialize the variables
			this.id = Integer.parseInt(id);
			this.year = Integer.parseInt(year);
			this.month = Integer.parseInt(month);
			this.day = Integer.parseInt(day);
			this.weight = Double.parseDouble(weight);	
		} catch(NumberFormatException e) {
			
			//If the arguments were not numbers
			throw new NumberFormatException();
		}
	}
	
	/**
	 * Sets the id, year, month, day, and weight of a farm
	 * @param id farm id
	 * @param year the year
	 * @param month the month
	 * @param day the day
	 * @param weight the weight
	 * @throws NumberFormatException if arguments are not numbers
	 */
	public void setFarm(String id, String year, String month, String day, String weight) 
			throws NumberFormatException{
		try {
			this.id = Integer.parseInt(id);
			this.year = Integer.parseInt(year);
			this.month = Integer.parseInt(month);
			this.day = Integer.parseInt(day);
			this.weight = Double.parseDouble(weight);	
		} catch(NumberFormatException e) {
			
			//If arguments are not numbers
			throw new NumberFormatException();
		}
	}
	
	/**
	 * Gets the farm id
	 * @return id
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Gets the year
	 * @return year
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * Gets the month
	 * @return month
	 */
	public int getMonth() {
		return month;
	}
	
	/**
	 * Gets the day
	 * @return day
	 */
	public int getDay() {
		return day; 
	}
	
	/**
	 * Gets the weight
	 * @return weight
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * Sets the total weight of the farm
	 * @param totalWeight total weight
	 */
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	
	/**
	 * Gets the total weight of a farm
	 * @return totalWeight
	 */
	public double getTotalWeight() {
		return totalWeight;
	}
	
	/**
	 * Sets the percent of the total weight of a farm
	 * @param percentWeight
	 */
	public void setPercentageTotalWeight(double percentWeight) {
		this.percentWeight = percentWeight;
	}
	
	/**
	 * Gets the total weight of a farm
	 * @return the total weight of a farm
	 */
	public double getPercentTotalWeight() {
		return percentWeight;
	}
}
