package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UploadedFile {
	private String fileName; // The file to be uploaded
	private boolean uploaded = false; // Boolean to see if file was uploaded
	private ArrayList<Farm> farms; // An array list of all farms

	/**
	 * Constructor for the UploadedFile class
	 * 
	 * @param file the file name
	 * @throws FileNotFoundException if the file is not found
	 */
	public UploadedFile(String file) throws FileNotFoundException {

		// Sets the fileName
		this.fileName = file;

		try {

			// Initializes the farms arrayList
			farms = new ArrayList<Farm>();

			// Calls load file with the fileName
			loadFile(fileName);
		} catch (FileNotFoundException e) {

			// If file is not found
			throw new FileNotFoundException();
		}
	}
	
	public UploadedFile() {
		// Initializes the farms arrayList
		farms = new ArrayList<Farm>();
	}

	/**
	 * The loadFile to load a file
	 * 
	 * @param file name of the file to be loaded
	 * @throws FileNotFoundException if the file does not exist
	 */
	private void loadFile(String file) throws FileNotFoundException {

		boolean yearExists = false;

		// Creates a new file with the file name
		File f = new File(file);

		// Strings to parse through the file
		String line;
		//Strings to split the lines
		String fileSplit = ",";
		String dateSplit = "-";

		try {
			FileReader f1 = new FileReader(f);
			BufferedReader br = new BufferedReader(f1);
			
			try {
				//skips the header line
				br.readLine();
				//recurses through each line of the csv
				while ((line = br.readLine()) != null) {
					
					//splits the csv lines into date, farmID, and weight
					String[] farmInput = line.split(fileSplit);
					//splits the date into year, month, and day
					String[] date= farmInput[0].split(dateSplit);
					//isolates the integer farmID
					String[] isolatedFarmID= farmInput[1].split(" ");

					String farmID = isolatedFarmID[1];
					String year = date[0];
					String month = date[1];
					String day = date[2];
					String milkWeight = farmInput[2];

					//creates a new farm weight and adds it to the arraylist
					Farm farm = new Farm(farmID, year, month, day, milkWeight);
					farms.add(farm);
					
					
				}


				// Close the reader
				br.close();

				// Set uploaded to true
				this.uploaded = true;

			} catch (IOException e) {

				// If file format is wrong
				System.out.println("Cannot read this file");
			}
		} catch (FileNotFoundException e) {

			// If file cannot be found
			throw new FileNotFoundException("Improper file");
		}
	}

	/**
	 * Method used to see if uploaded is true or not
	 * 
	 * @return uploaded
	 */
	public boolean confirmUpload() {
		return uploaded;
	}

	/**
	 * This method is used to add a new farm to the data
	 * 
	 * @param farmID the farmID of the new farm
	 * @param year   the year of the new farm
	 * @param month  the month of the new farm
	 * @param day    the day of the new farm
	 * @param weight the weight of the new farm
	 */
	public void addFarm(String farmID, String year, String month, String day, String weight) {

		try {

			// Creates a new farm with the arguments
			Farm newFarm = new Farm(farmID, year, month, day, weight);

			// Adds the farm to the farms list
			farms.add(newFarm);
			
			if(uploaded==false) {
				uploaded=true;
			}
		} catch (NumberFormatException e) {

			// If input was not numbers
			throw new NumberFormatException();
		}
		
	}

	/**
	 * Removes a farm from the data
	 * 
	 * @param farmID the farmID of the farm
	 * @param year   the year of the farm
	 * @param month  the month of the farm
	 * @param day    the day of the farm
	 * @throws NullPointerException if farm doesn't exist
	 */
	public void removeFarm(String farmID, String year, String month, String day) throws NullPointerException {

		try {

			// Converts all the arguments to ints
			int farmConvert = Integer.parseInt(farmID);
			int yearConvert = Integer.parseInt(year);
			int monthConvert = Integer.parseInt(month);
			int dayConvert = Integer.parseInt(day);

			// Iterates through the farms list
			for (int i = 0; i < farms.size(); i++) {

				// Checks if id is same
				if (farms.get(i).getID() == farmConvert) {

					// Checks if year is same
					if (farms.get(i).getYear() == yearConvert) {

						// Checks if month is same
						if (farms.get(i).getMonth() == monthConvert) {

							// Checks if day is same
							if (farms.get(i).getDay() == dayConvert) {

								// Removes the farm from the list
								farms.remove(i);
								return;
							}
						}
					}
				}
			}

			// Throws a null pointer if farm is not found
			throw new NullPointerException();

		} catch (NumberFormatException e) {

			// If input are not numbers
			throw new NumberFormatException();
		}
	}

	/**
	 * Changes a Farms data
	 * 
	 * @param farmID of the farm
	 * @param year   of the farm
	 * @param month  of the farm
	 * @param day    of the farm
	 * @param weight the new weight of the farm
	 * @throws NullPointerException if the farm doesn't exist
	 */
	public void changeFarm(String farmID, String year, String month, String day, String weight)
			throws NullPointerException {

		try {

			// Converts the arguments to ints
			int farmConvert = Integer.parseInt(farmID);
			int yearConvert = Integer.parseInt(year);
			int monthConvert = Integer.parseInt(month);
			int dayConvert = Integer.parseInt(day);

			// Iterates through the farms list
			for (int i = 0; i < farms.size(); i++) {

				// Checks if the id is same
				if (farms.get(i).getID() == farmConvert) {

					// Checks if year is same
					if (farms.get(i).getYear() == yearConvert) {

						// Checks if month is same
						if (farms.get(i).getMonth() == monthConvert) {

							// Checks if day is same
							if (farms.get(i).getDay() == dayConvert) {

								// Sets the farm's data to the arguments
								farms.get(i).setFarm(farmID, year, month, day, weight);
								;
								return;
							}
						}
					}
				}
			}

			// If farm can't be found
			throw new NullPointerException();

		} catch (NumberFormatException e) {

			// If the input isnt numbers
			throw new NumberFormatException();
		}
	}

	/**
	 * Creates a farmReport
	 * 
	 * @param writeToFile if user chooses to write to a file
	 * @param farmID      farmID to be used
	 * @param farmYear    year to be used
	 * @return the String of the report
	 * @throws NullPointerException if the farmID doesn't exist
	 */
	public String farmReport(boolean writeToFile, String farmID, String farmYear) throws NullPointerException {

		// Variables to see if the farm is in the year and if farm exists
		boolean isInYear = false;
		boolean exists = false;

		// doubles for the total weights of all months
		double janTotalWeight = 0;
		double febTotalWeight = 0;
		double marTotalWeight = 0;
		double aprTotalWeight = 0;
		double maTotalWeight = 0;
		double junTotalWeight = 0;
		double julTotalWeight = 0;
		double augTotalWeight = 0;
		double septTotalWeight = 0;
		double octTotalWeight = 0;
		double novTotalWeight = 0;
		double decTotalWeight = 0;

		try {

			// Parses through the farm and year
			int farm = Integer.parseInt(farmID);
			int finalYear = Integer.parseInt(farmYear);

			// ArrayList of all farms in the same year
			ArrayList<Farm> sameYear = new ArrayList<Farm>();

			// Goes through the farms list
			for (int i = 0; i < farms.size(); i++) {

				// Checks if the id is in the list
				if (farm == farms.get(i).getID()) {

					// If it is then the farm exists
					exists = true;
				}
			}

			// If farm doesnt exist then throw a null pointer
			if (exists == false) {
				throw new NullPointerException();
			}

			// Go through the farms list
			for (int i = 0; i < farms.size(); i++) {

				// Checks if a farm has the same year as the argument
				if (finalYear == farms.get(i).getYear()) {

					// If it does, add the farm to the year list
					sameYear.add(farms.get(i));
				}
			}

			// Iterates through the year list
			for (int i = 0; i < sameYear.size(); i++) {

				// Checks if the farm is in the year
				if (farm == sameYear.get(i).getID()) {
					isInYear = true;
				}
			}

			// If farm isn't in year throw a null pointer
			if (isInYear == false) {
				throw new NullPointerException();
			}

			// ArrayList of farms for each month
			ArrayList<Farm> january = new ArrayList<Farm>();
			ArrayList<Farm> february = new ArrayList<Farm>();
			ArrayList<Farm> march = new ArrayList<Farm>();
			ArrayList<Farm> april = new ArrayList<Farm>();
			ArrayList<Farm> may = new ArrayList<Farm>();
			ArrayList<Farm> june = new ArrayList<Farm>();
			ArrayList<Farm> july = new ArrayList<Farm>();
			ArrayList<Farm> august = new ArrayList<Farm>();
			ArrayList<Farm> september = new ArrayList<Farm>();
			ArrayList<Farm> october = new ArrayList<Farm>();
			ArrayList<Farm> november = new ArrayList<Farm>();
			ArrayList<Farm> december = new ArrayList<Farm>();

			// iterates through the year list
			for (int i = 0; i < sameYear.size(); i++) {

				// Gets the month of the farm and sees if it is a particular month
				if (sameYear.get(i).getMonth() == 1) {

					// adds the farm if it has the same month
					january.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 2) {
					february.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 3) {
					march.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 4) {
					april.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 5) {
					may.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 6) {
					june.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 7) {
					july.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 8) {
					august.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 9) {
					september.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 10) {
					october.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 11) {
					november.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 12) {
					december.add(sameYear.get(i));
					continue;
				}
			}

			/*
			 * Gets the farm for the particular month because one farm can be present in
			 * multiple months
			 */
			Farm jan = getFarmOfMonth(january, farm);
			Farm feb = getFarmOfMonth(february, farm);
			Farm mar = getFarmOfMonth(march, farm);
			Farm apr = getFarmOfMonth(april, farm);
			Farm ma = getFarmOfMonth(may, farm);
			Farm jun = getFarmOfMonth(june, farm);
			Farm jul = getFarmOfMonth(july, farm);
			Farm aug = getFarmOfMonth(august, farm);
			Farm sep = getFarmOfMonth(september, farm);
			Farm oct = getFarmOfMonth(october, farm);
			Farm nov = getFarmOfMonth(november, farm);
			Farm dec = getFarmOfMonth(december, farm);

			// Gets the total weight of all farms for each month
			janTotalWeight = getTotalWeight(january);
			febTotalWeight = getTotalWeight(february);
			marTotalWeight = getTotalWeight(march);
			aprTotalWeight = getTotalWeight(april);
			maTotalWeight = getTotalWeight(may);
			junTotalWeight = getTotalWeight(june);
			julTotalWeight = getTotalWeight(july);
			augTotalWeight = getTotalWeight(august);
			septTotalWeight = getTotalWeight(september);
			octTotalWeight = getTotalWeight(october);
			novTotalWeight = getTotalWeight(november);
			decTotalWeight = getTotalWeight(december);

			// Gets the particular farm's percentage of the total weight for the month
			double janPercentWeight = getPercentTotalWeight(january, jan, janTotalWeight);
			double febPercentWeight = getPercentTotalWeight(february, feb, febTotalWeight);
			double marPercentWeight = getPercentTotalWeight(march, mar, marTotalWeight);
			double aprPercentWeight = getPercentTotalWeight(april, apr, aprTotalWeight);
			double maPercentWeight = getPercentTotalWeight(may, ma, maTotalWeight);
			double junPercentWeight = getPercentTotalWeight(june, jun, junTotalWeight);
			double julPercentWeight = getPercentTotalWeight(july, jul, julTotalWeight);
			double augPercentWeight = getPercentTotalWeight(august, aug, augTotalWeight);
			double sepPercentWeight = getPercentTotalWeight(september, sep, septTotalWeight);
			double octPercentWeight = getPercentTotalWeight(october, oct, octTotalWeight);
			double novPercentWeight = getPercentTotalWeight(november, nov, novTotalWeight);
			double decPercentWeight = getPercentTotalWeight(december, dec, decTotalWeight);

			// Checks if the user selected to write to a file
			if (writeToFile == true) {

				// Creates a new file with the farmReport.txt
				File file = new File("farmReport.txt");

				// Creates a new printWriter
				PrintWriter printWriter;

				try {
					printWriter = new PrintWriter(file);

					// Calls the printReport method for every month
					printReport(january, 1, printWriter, janTotalWeight, janPercentWeight);
					printReport(february, 2, printWriter, febTotalWeight, febPercentWeight);
					printReport(march, 3, printWriter, marTotalWeight, marPercentWeight);
					printReport(april, 4, printWriter, aprTotalWeight, aprPercentWeight);
					printReport(may, 5, printWriter, maTotalWeight, maPercentWeight);
					printReport(june, 6, printWriter, junTotalWeight, junPercentWeight);
					printReport(july, 7, printWriter, julTotalWeight, julPercentWeight);
					printReport(august, 8, printWriter, augTotalWeight, augPercentWeight);
					printReport(september, 9, printWriter, septTotalWeight, sepPercentWeight);
					printReport(october, 10, printWriter, octTotalWeight, octPercentWeight);
					printReport(november, 11, printWriter, novTotalWeight, novPercentWeight);
					printReport(december, 12, printWriter, decTotalWeight, decPercentWeight);

					// Closes the printWriter
					printWriter.close();
				} catch (FileNotFoundException e) {
				}
			}

			// If the user did not choose writeToFile

			// String holder
			String s = "";

			/*
			 * Calls the displayFarmReport for every month and adds the string to the empty
			 * string
			 */
			String janString = displayFarmReport(january, 1, janTotalWeight, janPercentWeight);
			s = s + janString + "\n";
			String febString = displayFarmReport(february, 2, febTotalWeight, febPercentWeight);
			s = s + febString + "\n";
			String marString = displayFarmReport(march, 3, marTotalWeight, marPercentWeight);
			s = s + marString + "\n";
			String aprString = displayFarmReport(april, 4, aprTotalWeight, aprPercentWeight);
			s = s + aprString + "\n";
			String mayString = displayFarmReport(may, 5, maTotalWeight, maPercentWeight);
			s = s + mayString + "\n";
			String junString = displayFarmReport(june, 6, junTotalWeight, junPercentWeight);
			s = s + junString + "\n";
			String julString = displayFarmReport(july, 7, julTotalWeight, julPercentWeight);
			s = s + julString + "\n";
			String augString = displayFarmReport(august, 8, augTotalWeight, augPercentWeight);
			s = s + augString + "\n";
			String septString = displayFarmReport(september, 9, septTotalWeight, sepPercentWeight);
			s = s + septString + "\n";
			String octString = displayFarmReport(october, 10, octTotalWeight, octPercentWeight);
			s = s + octString + "\n";
			String novString = displayFarmReport(november, 11, novTotalWeight, novPercentWeight);
			s = s + novString + "\n";
			String decString = displayFarmReport(december, 12, decTotalWeight, decPercentWeight);
			s = s + decString + "\n";

			// Returns the total report string
			return s;
		} catch (NumberFormatException e) {

			// If the input were not numbers
			throw new NumberFormatException();
		}
	}

	/**
	 * Displays the farmReport as a string
	 * 
	 * @param month              the arraylist of farms in a month
	 * @param monthNum           the number of the month
	 * @param totalWeight        the total weight of the month
	 * @param percentTotalWeight the farm's percent of the total weight
	 * @return the string to be displayed
	 */
	private String displayFarmReport(ArrayList<Farm> month, int monthNum, double totalWeight,
			double percentTotalWeight) {

		// Creates an empty string
		String s = "";

		// Checks the number of the month (1-12)
		if (monthNum == 1) {

			// Appends to the empty string
			s = "January: ";

			// Iterates through the farms in the month
			for (int i = 0; i < month.size(); i++) {

				// Appends farm id, year, and weight to the string
				s = s + "\nFarm ID: " + month.get(i).getID();
				s = s + " Year: " + month.get(i).getYear();
				s = s + " Weight: " + month.get(i).getWeight();
				continue;
			}

			// Adds the total weight and percent weight to the string
			s = s + "\nTotal Weight: " + totalWeight;
			s = s + "\nFarm's percent of Total Weight: " + percentTotalWeight + "%";
		}

		if (monthNum == 2) {
			s = "February: ";
			for (int i = 0; i < month.size(); i++) {
				s = s + "\nFarm ID: " + month.get(i).getID();
				s = s + " Year: " + month.get(i).getYear();
				s = s + " Weight: " + month.get(i).getWeight();
				continue;
			}

			s = s + "\nTotal Weight: " + totalWeight;
			s = s + "\nFarm's percent of Total Weight: " + percentTotalWeight + "%";
		}

		if (monthNum == 3) {
			s = "March: ";
			for (int i = 0; i < month.size(); i++) {
				s = s + "\nFarm ID: " + month.get(i).getID();
				s = s + " Year: " + month.get(i).getYear();
				s = s + " Weight: " + month.get(i).getWeight();
				continue;
			}

			s = s + "\nTotal Weight: " + totalWeight;
			s = s + "\nFarm's percent of Total Weight: " + percentTotalWeight + "%";
		}

		if (monthNum == 4) {
			s = "April: ";
			for (int i = 0; i < month.size(); i++) {
				s = s + "\nFarm ID: " + month.get(i).getID();
				s = s + " Year: " + month.get(i).getYear();
				s = s + " Weight: " + month.get(i).getWeight();
				continue;
			}

			s = s + "\nTotal Weight: " + totalWeight;
			s = s + "\nFarm's percent of Total Weight: " + percentTotalWeight + "%";
		}

		if (monthNum == 5) {
			s = "May: ";
			for (int i = 0; i < month.size(); i++) {
				s = s + "\nFarm ID: " + month.get(i).getID();
				s = s + " Year: " + month.get(i).getYear();
				s = s + " Weight: " + month.get(i).getWeight();
				continue;
			}

			s = s + "\nTotal Weight: " + totalWeight;
			s = s + "\nFarm's percent of Total Weight: " + percentTotalWeight + "%";
		}

		if (monthNum == 6) {
			s = "June: ";
			for (int i = 0; i < month.size(); i++) {
				s = s + "\nFarm ID: " + month.get(i).getID();
				s = s + " Year: " + month.get(i).getYear();
				s = s + " Weight: " + month.get(i).getWeight();
				continue;
			}

			s = s + "\nTotal Weight: " + totalWeight;
			s = s + "\nFarm's percent of Total Weight: " + percentTotalWeight + "%";
		}

		if (monthNum == 7) {
			s = "July: ";
			for (int i = 0; i < month.size(); i++) {
				s = s + "\nFarm ID: " + month.get(i).getID();
				s = s + " Year: " + month.get(i).getYear();
				s = s + " Weight: " + month.get(i).getWeight();
				continue;
			}

			s = s + "\nTotal Weight: " + totalWeight;
			s = s + "\nFarm's percent of Total Weight: " + percentTotalWeight + "%";
		}

		if (monthNum == 8) {
			s = "August: ";
			for (int i = 0; i < month.size(); i++) {
				s = s + "\nFarm ID: " + month.get(i).getID();
				s = s + " Year: " + month.get(i).getYear();
				s = s + " Weight: " + month.get(i).getWeight();
				continue;
			}

			s = s + "\nTotal Weight: " + totalWeight;
			s = s + "\nFarm's percent of Total Weight: " + percentTotalWeight + "%";
		}

		if (monthNum == 9) {
			s = "September: ";
			for (int i = 0; i < month.size(); i++) {
				s = s + "\nFarm ID: " + month.get(i).getID();
				s = s + " Year: " + month.get(i).getYear();
				s = s + " Weight: " + month.get(i).getWeight();
				continue;
			}

			s = s + "\nTotal Weight: " + totalWeight;
			s = s + "\nFarm's percent of Total Weight: " + percentTotalWeight + "%";
		}

		if (monthNum == 10) {
			s = "October: ";
			for (int i = 0; i < month.size(); i++) {
				s = s + "\nFarm ID: " + month.get(i).getID();
				s = s + " Year: " + month.get(i).getYear();
				s = s + " Weight: " + month.get(i).getWeight();
				continue;
			}

			s = s + "\nTotal Weight: " + totalWeight;
			s = s + "\nFarm's percent of Total Weight: " + percentTotalWeight + "%";
		}

		if (monthNum == 11) {
			s = "November: ";
			for (int i = 0; i < month.size(); i++) {
				s = s + "\nFarm ID: " + month.get(i).getID();
				s = s + " Year: " + month.get(i).getYear();
				s = s + " Weight: " + month.get(i).getWeight();
				continue;
			}

			s = s + "\nTotal Weight: " + totalWeight;
			s = s + "\nFarm's percent of Total Weight: " + percentTotalWeight + "%";
		}

		if (monthNum == 12) {
			s = "December: ";
			for (int i = 0; i < month.size(); i++) {
				s = s + "\nFarm ID: " + month.get(i).getID();
				s = s + " Year: " + month.get(i).getYear();
				s = s + " Weight: " + month.get(i).getWeight();
				continue;
			}

			s = s + "\nTotal Weight: " + totalWeight;
			s = s + "\nFarm's percent of Total Weight: " + percentTotalWeight + "%";
		}

		// Returns the string to be displayed
		return s;
	}

	/**
	 * This method is used to get the farm statistics of a particular month
	 * 
	 * @param month  the array list where the farm is
	 * @param farmID the farm id to be found
	 * @return the farm of the particular farm id
	 */
	private Farm getFarmOfMonth(ArrayList<Farm> month, int farmID) {
		Farm f = null;

		// Iterates through the month list
		for (int i = 0; i < month.size(); i++) {

			// Gets the farm id
			if (month.get(i).getID() == farmID) {

				// Sets it to the farm f
				f = month.get(i);
			}
		}

		// Returns the farm
		return f;
	}

	/**
	 * Gets the total weight of an arraylist of farms
	 * 
	 * @param farms the array list of farms
	 * @return the total weight of the farms in the list
	 */
	private double getTotalWeight(ArrayList<Farm> farms) {
		double weight = 0;

		// Iterates through the array list
		for (int i = 0; i < farms.size(); i++) {

			// Adds the weight of every farm to the current weight
			weight = weight + farms.get(i).getWeight();
		}

		// Returns weight
		return weight;
	}

	/**
	 * This method gets the farm's percent of the total weight of all farms
	 * 
	 * @param farms       the list of farms
	 * @param farm        the farm to be compared
	 * @param totalWeight the total weight of all farms
	 * @return
	 */
	private double getPercentTotalWeight(ArrayList<Farm> farms, Farm farm, double totalWeight) {

		// Boolean to see if the farm is in the list
		boolean isIn = false;

		// Holder variable
		double percentTotalWeight = 0;

		// Checks if the farms list is 0
		if (farms.size() == 0) {

			// If it is then return 0
			return percentTotalWeight;
		}

		// Checks if the farm is null
		if (farm == null) {

			// If it is return 0
			return 0;
		}

		// Iterates through the list of farms
		for (int i = 0; i < farms.size(); i++) {

			// Checks if the farm is in the list
			if (farm.getID() == farms.get(i).getID()) {
				isIn = true;
			}
		}

		// If it is
		if (isIn == true) {

			// Get the farms percent of the total weight and return it
			percentTotalWeight = (farm.getWeight() / totalWeight) * 100;
			return percentTotalWeight;
		} else {

			// Otherwise farm is not in the list and return 0
			return 0;
		}
	}

	/**
	 * Prints a report to the farmReport.txt file
	 * 
	 * @param month              the array list of farms in a month
	 * @param monthNum           the number of the month
	 * @param printWriter        the printwriter to write to the file
	 * @param totalWeight        the total weight of a month
	 * @param percentTotalWeight the farms percent of the month
	 */
	private void printReport(ArrayList<Farm> month, int monthNum, PrintWriter printWriter, double totalWeight,
			double percentTotalWeight) {

		// Checks the month's number (1-12)
		if (monthNum == 1) {

			// Prints the month's name
			printWriter.println("January: ");

			// Iterates through the list of farms in the month
			for (int i = 0; i < month.size(); i++) {

				// Prints the farm id, year, and weight to the file
				printWriter.print("Farm ID: " + month.get(i).getID());
				printWriter.print(" Year: " + month.get(i).getYear());
				printWriter.println(" Weight: " + month.get(i).getWeight());
				continue;
			}

			// Prints the total weight and farms percent of total weight to the file
			printWriter.println("Total Weight: " + totalWeight);
			printWriter.println("Farm's percent of Total Weight: " + percentTotalWeight + "%");
		}

		if (monthNum == 2) {
			printWriter.println("February: ");
			for (int i = 0; i < month.size(); i++) {
				printWriter.print("Farm ID: " + month.get(i).getID());
				printWriter.print(" Year: " + month.get(i).getYear());
				printWriter.println(" Weight: " + month.get(i).getWeight());
				continue;
			}

			printWriter.println(" Total Weight: " + totalWeight);
			printWriter.println("Farm's percent of Total Weight: " + percentTotalWeight + "%");
		}

		if (monthNum == 3) {
			printWriter.println("March: ");
			for (int i = 0; i < month.size(); i++) {
				printWriter.print("Farm ID: " + month.get(i).getID());
				printWriter.print(" Year: " + month.get(i).getYear());
				printWriter.println(" Weight: " + month.get(i).getWeight());
				continue;
			}

			printWriter.println(" Total Weight: " + totalWeight);
			printWriter.println("Farm's percent of Total Weight: " + percentTotalWeight + "%");

		}

		if (monthNum == 4) {
			printWriter.println("April: ");
			for (int i = 0; i < month.size(); i++) {
				printWriter.print("Farm ID: " + month.get(i).getID());
				printWriter.print(" Year: " + month.get(i).getYear());
				printWriter.println(" Weight: " + month.get(i).getWeight());
				continue;
			}

			printWriter.println(" Total Weight: " + totalWeight);
			printWriter.println("Farm's percent of Total Weight: " + percentTotalWeight + "%");
		}

		if (monthNum == 5) {
			printWriter.println("May: ");
			for (int i = 0; i < month.size(); i++) {
				printWriter.print("Farm ID: " + month.get(i).getID());
				printWriter.print(" Year: " + month.get(i).getYear());
				printWriter.println(" Weight: " + month.get(i).getWeight());
				continue;
			}

			printWriter.println(" Total Weight: " + totalWeight);
			printWriter.println("Farm's percent of Total Weight: " + percentTotalWeight + "%");
		}

		if (monthNum == 6) {
			printWriter.println("June: ");
			for (int i = 0; i < month.size(); i++) {
				printWriter.print("Farm ID: " + month.get(i).getID());
				printWriter.print(" Year: " + month.get(i).getYear());
				printWriter.println(" Weight: " + month.get(i).getWeight());
				continue;
			}

			printWriter.println(" Total Weight: " + totalWeight);
			printWriter.println("Farm's percent of Total Weight: " + percentTotalWeight + "%");
		}

		if (monthNum == 7) {
			printWriter.println("July: ");
			for (int i = 0; i < month.size(); i++) {
				printWriter.print("Farm ID: " + month.get(i).getID());
				printWriter.print(" Year: " + month.get(i).getYear());
				printWriter.println(" Weight: " + month.get(i).getWeight());
				continue;
			}

			printWriter.println(" Total Weight: " + totalWeight);
			printWriter.println("Farm's percent of Total Weight: " + percentTotalWeight + "%");
		}

		if (monthNum == 8) {
			printWriter.println("August: ");
			for (int i = 0; i < month.size(); i++) {
				printWriter.print("Farm ID: " + month.get(i).getID());
				printWriter.print(" Year: " + month.get(i).getYear());
				printWriter.println(" Weight: " + month.get(i).getWeight());
				continue;
			}

			printWriter.println(" Total Weight: " + totalWeight);
			printWriter.println("Farm's percent of Total Weight: " + percentTotalWeight + "%");
		}

		if (monthNum == 9) {
			printWriter.println("September: ");
			for (int i = 0; i < month.size(); i++) {
				printWriter.print("Farm ID: " + month.get(i).getID());
				printWriter.print(" Year: " + month.get(i).getYear());
				printWriter.println(" Weight: " + month.get(i).getWeight());
				continue;
			}

			printWriter.println(" Total Weight: " + totalWeight);
			printWriter.println("Farm's percent of Total Weight: " + percentTotalWeight + "%");
		}

		if (monthNum == 10) {
			printWriter.println("October: ");
			for (int i = 0; i < month.size(); i++) {
				printWriter.print("Farm ID: " + month.get(i).getID());
				printWriter.print(" Year: " + month.get(i).getYear());
				printWriter.println(" Weight: " + month.get(i).getWeight());
				continue;
			}

			printWriter.println(" Total Weight: " + totalWeight);
			printWriter.println("Farm's percent of Total Weight: " + percentTotalWeight + "%");
		}

		if (monthNum == 11) {
			printWriter.println("November: ");
			for (int i = 0; i < month.size(); i++) {
				printWriter.print("Farm ID: " + month.get(i).getID());
				printWriter.print(" Year: " + month.get(i).getYear());
				printWriter.println(" Weight: " + month.get(i).getWeight());
				continue;
			}

			printWriter.println(" Total Weight: " + totalWeight);
			printWriter.println("Farm's percent of Total Weight: " + percentTotalWeight + "%");
		}

		if (monthNum == 12) {
			printWriter.println("December: ");
			for (int i = 0; i < month.size(); i++) {
				printWriter.print("Farm ID: " + month.get(i).getID());
				printWriter.print(" Year: " + month.get(i).getYear());
				printWriter.println(" Weight: " + month.get(i).getWeight());
				continue;
			}

			printWriter.println(" Total Weight: " + totalWeight);
			printWriter.println("Farm's percent of Total Weight: " + percentTotalWeight + "%");
		}
	}

	/**
	 * The annual report of the farms
	 * 
	 * @param writeToFile true if the user selected to write to file false otherwise
	 * @param year        the year to be displayed
	 * @return the String of the data
	 * @throws NullPointerException if the year does not exist in the data
	 */
	public String annualReport(boolean writeToFile, String year) throws NullPointerException {
		try {

			// Convert the year into an int
			int finalYear = Integer.parseInt(year);

			// Boolean to see if year exists
			boolean yearExists = false;

			// Iterates through the farms list
			for (int i = 0; i < farms.size(); i++) {

				// Checks if the year is in the farms list
				if (finalYear == farms.get(i).getYear()) {

					// If it is then it exists
					yearExists = true;
				}
			}

			// If it doesnt exist then throw null pointer
			if (yearExists == false) {
				throw new NullPointerException();
			}

			// Array list of farms in the same year
			ArrayList<Farm> sameYear = new ArrayList<Farm>();

			// The array list of farms in the entire year
			ArrayList<ArrayList<Farm>> farmIDs = new ArrayList<ArrayList<Farm>>();
			double totalWeight = 0;

			// Iterates through and adds the farms that are in the year into the year list
			for (int i = 0; i < farms.size(); i++) {
				if (finalYear == farms.get(i).getYear()) {
					sameYear.add(farms.get(i));
				}
			}

			// Gets the total weight of all farms in the year
			totalWeight = getTotalWeight(sameYear);

			// Iterates through the list of farms in the same year
			for (int i = 0; i < sameYear.size(); i++) {

				// Checks if the farm is already in the farmIDs list
				boolean isIn = false;

				// The array list of occurrences of the farm in the year
				ArrayList<Farm> occurrences = ocurrences(sameYear.get(i), sameYear);

				// Iterates through the farmIDs list
				for (int j = 0; j < farmIDs.size(); j++) {

					// Checks if the farmIDs list already has the occurences
					if (farmIDs.get(j).get(0).getID() == occurrences.get(0).getID()) {

						// If it does then the farm id has already been added
						isIn = true;
					}
				}

				// If the farm id has not been added
				if (isIn == false) {

					// Check if the farm size is 0
					if (farmIDs.size() == 0) {

						// Add the list of occurrences to the farm ids list
						farmIDs.add(occurrences);
						continue;
					}

					// Otherwise iterate through the farmIDs list
					for (int j = 0; j < farmIDs.size(); j++) {

						// Check if the id is greater than the last id in the list
						if (occurrences.get(0).getID() > farmIDs.get(farmIDs.size() - 1).get(0).getID()) {

							// Add the id to the end of the list
							farmIDs.add(occurrences);
						}

						// If the id is less than the next id
						else if (occurrences.get(0).getID() < farmIDs.get(j).get(0).getID()) {

							// Add the id at the index of the next id
							farmIDs.add(j, occurrences);
						}

					}
				} else {

					// Otherwise farm has already been added
					continue;
				}
			}

			// Iterate through the list of the farm ids
			for (int i = 0; i < farmIDs.size(); i++) {

				// Iterate through the list of occurrences
				for (int j = 0; j < farmIDs.get(i).size(); j++) {

					// Get the total weight of each farm
					farmIDs.get(i).get(j).setTotalWeight(getTotalWeight(farmIDs.get(i)));
				}
			}

			// Iterate through the list of the farm ids
			for (int i = 0; i < farmIDs.size(); i++) {

				// Set the percent of the total weight of all farms
				farmIDs.get(i).get(0)
						.setPercentageTotalWeight(((farmIDs.get(i).get(0).getTotalWeight() / totalWeight)) * 100);
			}

			// Check if user chose to write to a file
			if (writeToFile == true) {

				// Create a new file
				File file = new File("annualReport.txt");
				PrintWriter printWriter;

				try {

					// Create a new print writer
					printWriter = new PrintWriter(file);

					// Iterates through the list of farm ids
					for (int i = 0; i < farmIDs.size(); i++) {

						// Prints the annual report for each occurance of the id
						printAnnualReport(farmIDs.get(i), printWriter);
					}

					// Print the total year weight
					printWriter.println("Total Year Weight: " + totalWeight);
					printWriter.close();
				} catch (FileNotFoundException e) {

				}
			}

			// If user chose to display data instead

			// Holder variable
			String s = "";

			// Iterates through the list of farm ids
			for (int i = 0; i < farmIDs.size(); i++) {

				// Appends the annuaul report of each id to the string
				s = s + "\n" + displayAnnualReport(farmIDs.get(i));
			}

			// Adds the total weight of the year to the string
			s = s + "\nTotal Year Weight: " + totalWeight;

			// Return the string
			return s;
		} catch (NumberFormatException e) {
			// If the input were not numbers
			throw new NumberFormatException();
		}

	}

	/**
	 * Creates a string of data to be displayed
	 * 
	 * @param occurrences the id's occurences throughout the year
	 * @return the string of data
	 */
	private String displayAnnualReport(ArrayList<Farm> occurrences) {

		// Create an empty string
		String s = "";

		// Get the farm id, total weight, and the percent of the total weight for that
		// id
		s = s + "Farm ID: " + occurrences.get(0).getID();
		s = s + " Total Weight: " + occurrences.get(0).getTotalWeight();
		s = s + " Percent of Total Weight: " + occurrences.get(0).getPercentTotalWeight() + "%";

		// Return the string
		return s;
	}

	/**
	 * Returns an array list of occurences of a particular farm id in a year
	 * 
	 * @param farm the farm to be found
	 * @param list the year list
	 * @return the array list of occurences
	 */
	private ArrayList<Farm> ocurrences(Farm farm, ArrayList<Farm> list) {

		// Creates a new array list
		ArrayList<Farm> ocurrencesList = new ArrayList<>();

		// Iterates through the list
		for (int i = 0; i < list.size(); i++) {

			// Checks if the farm id is in the list
			if (farm.getID() == list.get(i).getID()) {

				// If it is, add the farm id to the list of occurences
				ocurrencesList.add(list.get(i));
			}
		}

		// Return the list of occurrences
		return ocurrencesList;
	}

	/**
	 * Prints the annual report to a file
	 * 
	 * @param occurrences the array list of occurrences
	 * @param printer     the printwriter to write to a file
	 */
	private void printAnnualReport(ArrayList<Farm> occurrences, PrintWriter printer) {

		// Prints the farm id, total weight, and percent of total weight to the file
		printer.print("Farm ID: " + occurrences.get(0).getID());
		printer.print(" Total Weight: " + occurrences.get(0).getTotalWeight());
		printer.println(" Percent of Total Weight: " + occurrences.get(0).getPercentTotalWeight() + "%");
	}

	/**
	 * Returns a monthly report to the user given a year and month
	 * 
	 * @param writeToFile true if the user wants to write to a file false otherwise
	 * @param year        the year of the report
	 * @param month       the month of the report
	 * @return a string with the data
	 * @throws IllegalArgumentException if the month is not valid
	 * @throws NullPointerException     if the year does not exist
	 */
	public String monthlyReport(boolean writeToFile, String year, String month)
			throws IllegalArgumentException, NullPointerException {
		try {

			// Converts the year and month to ints
			int finalYear = Integer.parseInt(year);
			int monthNumber = Integer.parseInt(month);

			// Holder variable
			String monthName = null;

			// Checks if the year exists
			boolean yearExists = false;

			// Iterates through the list of farms
			for (int i = 0; i < farms.size(); i++) {

				// Checks if the year is in the list
				if (finalYear == farms.get(i).getYear()) {

					// If it is then the year exists
					yearExists = true;
				}
			}

			// If the year doesn't exist throw a null pointer
			if (yearExists == false) {
				throw new NullPointerException();
			}

			// Create an array list of farms in the same year
			ArrayList<Farm> sameYear = new ArrayList<Farm>();

			// Add all the farms in the ssame year to the list
			for (int i = 0; i < farms.size(); i++) {
				if (finalYear == farms.get(i).getYear()) {
					sameYear.add(farms.get(i));
				}
			}

			// Array list of farms in each month
			ArrayList<Farm> january = new ArrayList<Farm>();
			ArrayList<Farm> february = new ArrayList<Farm>();
			ArrayList<Farm> march = new ArrayList<Farm>();
			ArrayList<Farm> april = new ArrayList<Farm>();
			ArrayList<Farm> may = new ArrayList<Farm>();
			ArrayList<Farm> june = new ArrayList<Farm>();
			ArrayList<Farm> july = new ArrayList<Farm>();
			ArrayList<Farm> august = new ArrayList<Farm>();
			ArrayList<Farm> september = new ArrayList<Farm>();
			ArrayList<Farm> october = new ArrayList<Farm>();
			ArrayList<Farm> november = new ArrayList<Farm>();
			ArrayList<Farm> december = new ArrayList<Farm>();

			// Iterates through the list of the farms in the same year
			for (int i = 0; i < sameYear.size(); i++) {

				// Checks the month of the farm and adds it to the corresponding
				// month list
				if (sameYear.get(i).getMonth() == 1) {
					january.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 2) {
					february.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 3) {
					march.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 4) {
					april.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 5) {
					may.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 6) {
					june.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 7) {
					july.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 8) {
					august.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 9) {
					september.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 10) {
					october.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 11) {
					november.add(sameYear.get(i));
					continue;
				}

				if (sameYear.get(i).getMonth() == 12) {
					december.add(sameYear.get(i));
					continue;
				}
			}

			// Total weight for each month
			double janTotalWeight = getTotalWeight(january);
			double febTotalWeight = getTotalWeight(february);
			double marTotalWeight = getTotalWeight(march);
			double aprTotalWeight = getTotalWeight(april);
			double maTotalWeight = getTotalWeight(may);
			double junTotalWeight = getTotalWeight(june);
			double julTotalWeight = getTotalWeight(july);
			double augTotalWeight = getTotalWeight(august);
			double septTotalWeight = getTotalWeight(september);
			double octTotalWeight = getTotalWeight(october);
			double novTotalWeight = getTotalWeight(november);
			double decTotalWeight = getTotalWeight(december);

			// If the user chooses to write to a file
			if (writeToFile == true) {

				// Check the number of the month
				if (monthNumber == 1) {

					// Get the month's percent weight for each farm
					monthPercentWeight(january, janTotalWeight);
					monthName = "January";

					// Print the report to the file
					printMonthlyReport(writeToFile, january, janTotalWeight, monthName, year);
					return null;
				}

				if (monthNumber == 2) {
					monthPercentWeight(february, febTotalWeight);
					monthName = "February";
					printMonthlyReport(writeToFile, february, febTotalWeight, monthName, year);
					return null;
				}

				if (monthNumber == 3) {
					monthPercentWeight(march, marTotalWeight);
					monthName = "March";
					printMonthlyReport(writeToFile, march, marTotalWeight, monthName, year);
					return null;
				}

				if (monthNumber == 4) {
					monthPercentWeight(april, aprTotalWeight);
					monthName = "April";
					printMonthlyReport(writeToFile, april, aprTotalWeight, monthName, year);
					return null;
				}

				if (monthNumber == 5) {
					monthPercentWeight(may, maTotalWeight);
					monthName = "May";
					printMonthlyReport(writeToFile, may, maTotalWeight, monthName, year);
					return null;
				}

				if (monthNumber == 6) {
					monthPercentWeight(june, junTotalWeight);
					monthName = "June";
					printMonthlyReport(writeToFile, june, junTotalWeight, monthName, year);
					return null;
				}

				if (monthNumber == 7) {
					monthPercentWeight(july, julTotalWeight);
					monthName = "July";
					printMonthlyReport(writeToFile, july, julTotalWeight, monthName, year);
					return null;

				}

				if (monthNumber == 8) {
					monthPercentWeight(august, augTotalWeight);
					monthName = "August";
					printMonthlyReport(writeToFile, august, augTotalWeight, monthName, year);
					return null;

				}

				if (monthNumber == 9) {
					monthPercentWeight(september, septTotalWeight);
					monthName = "September";
					printMonthlyReport(writeToFile, september, septTotalWeight, monthName, year);
					return null;
				}

				if (monthNumber == 10) {
					monthPercentWeight(october, octTotalWeight);
					monthName = "October";
					printMonthlyReport(writeToFile, october, octTotalWeight, monthName, year);
					return null;
				}

				if (monthNumber == 11) {
					monthPercentWeight(november, novTotalWeight);
					monthName = "November";
					printMonthlyReport(writeToFile, november, novTotalWeight, monthName, year);
					return null;
				}

				if (monthNumber == 12) {
					monthPercentWeight(december, decTotalWeight);
					monthName = "December";
					printMonthlyReport(writeToFile, december, decTotalWeight, monthName, year);
					return null;
				}

				else {

					// Otherwise the month was not valid and throw an exception
					throw new IllegalArgumentException();
				}
			}

			// If user chose to display data instead

			// Holder variable
			String s = "";

			// Checks the number of the month
			if (monthNumber == 1) {

				// Gets the percent weight of every farm in the month
				monthPercentWeight(january, janTotalWeight);

				// Adds the data for the month to the string and return the string
				s = s + displayMonthlyReport(january, 1, janTotalWeight);
				return s;
			}

			if (monthNumber == 2) {
				monthPercentWeight(february, febTotalWeight);
				s = s + displayMonthlyReport(february, 2, febTotalWeight);
				return s;
			}

			if (monthNumber == 3) {
				monthPercentWeight(march, marTotalWeight);
				s = s + displayMonthlyReport(march, 3, marTotalWeight);
				return s;
			}

			if (monthNumber == 4) {
				monthPercentWeight(april, aprTotalWeight);
				s = s + displayMonthlyReport(april, 4, aprTotalWeight);
				return s;
			}

			if (monthNumber == 5) {
				monthPercentWeight(may, maTotalWeight);
				s = s + displayMonthlyReport(may, 5, maTotalWeight);
				return s;
			}

			if (monthNumber == 6) {
				monthPercentWeight(june, junTotalWeight);
				s = s + displayMonthlyReport(june, 6, junTotalWeight);
				return s;
			}

			if (monthNumber == 7) {
				monthPercentWeight(july, julTotalWeight);
				s = s + displayMonthlyReport(july, 7, julTotalWeight);
				return s;
			}

			if (monthNumber == 8) {
				monthPercentWeight(august, augTotalWeight);
				s = s + displayMonthlyReport(august, 8, augTotalWeight);
				return s;
			}

			if (monthNumber == 9) {
				monthPercentWeight(september, septTotalWeight);
				s = s + displayMonthlyReport(september, 9, septTotalWeight);
				return s;
			}

			if (monthNumber == 10) {
				monthPercentWeight(october, octTotalWeight);
				s = s + displayMonthlyReport(october, 10, octTotalWeight);
				return s;
			}

			if (monthNumber == 11) {
				monthPercentWeight(november, novTotalWeight);
				s = s + displayMonthlyReport(november, 11, novTotalWeight);
				return s;
			}

			if (monthNumber == 12) {
				monthPercentWeight(december, decTotalWeight);
				s = s + displayMonthlyReport(december, 12, decTotalWeight);
				return s;
			}

			else {

				// Otherwise month wasn't valid and throw an exception
				throw new IllegalArgumentException();
			}
		} catch (NumberFormatException e) {

			// If input was not a number
			throw new NumberFormatException();
		}

	}

	/**
	 * Prints a report of the data as a string
	 * 
	 * @param month       the array list of farms in a month
	 * @param monthNum    the number of the month (1-12)
	 * @param totalWeight the total weight of the month
	 * @return string of the data for the month
	 */
	private String displayMonthlyReport(ArrayList<Farm> month, int monthNum, double totalWeight) {

		// Holder variable
		String s = "";

		// Check's the number of the month
		if (monthNum == 1) {

			// Adds the corresponding name of the month to the string
			s = s + "January\n";

			// Iterates through the list of farms in the month
			for (int i = 0; i < month.size(); i++) {

				// Adds the farm id and percent of the total weight of the month to the string
				s = s + "Farm ID: " + month.get(i).getID();
				s = s + " Percentage of Total Weight: " + month.get(i).getPercentTotalWeight() + "\n";
			}

			// Prints the total weight of the month to the string
			s = s + "Total Weight: " + totalWeight;
		}

		if (monthNum == 2) {
			s = s + "February\n";

			for (int i = 0; i < month.size(); i++) {
				s = s + "Farm ID: " + month.get(i).getID();
				s = s + " Percentage of Total Weight: " + month.get(i).getPercentTotalWeight() + "\n";
			}

			s = s + "Total Weight: " + totalWeight;
		}

		if (monthNum == 3) {
			s = s + "March\n";

			for (int i = 0; i < month.size(); i++) {
				s = s + "Farm ID: " + month.get(i).getID();
				s = s + " Percentage of Total Weight: " + month.get(i).getPercentTotalWeight() + "\n";
			}

			s = s + "Total Weight: " + totalWeight;
		}

		if (monthNum == 4) {
			s = s + "April\n";

			for (int i = 0; i < month.size(); i++) {
				s = s + "Farm ID: " + month.get(i).getID();
				s = s + " Percentage of Total Weight: " + month.get(i).getPercentTotalWeight() + "\n";
			}

			s = s + "Total Weight: " + totalWeight;
		}

		if (monthNum == 5) {
			s = s + "May\n";

			for (int i = 0; i < month.size(); i++) {
				s = s + "Farm ID: " + month.get(i).getID();
				s = s + " Percentage of Total Weight: " + month.get(i).getPercentTotalWeight() + "\n";
			}

			s = s + "Total Weight: " + totalWeight;
		}

		if (monthNum == 6) {
			s = s + "June\n";

			for (int i = 0; i < month.size(); i++) {
				s = s + "Farm ID: " + month.get(i).getID();
				s = s + " Percentage of Total Weight: " + month.get(i).getPercentTotalWeight() + "\n";
			}

			s = s + "Total Weight: " + totalWeight;
		}

		if (monthNum == 7) {
			s = s + "July\n";

			for (int i = 0; i < month.size(); i++) {
				s = s + "Farm ID: " + month.get(i).getID();
				s = s + " Percentage of Total Weight: " + month.get(i).getPercentTotalWeight() + "\n";
			}

			s = s + "Total Weight: " + totalWeight;
		}

		if (monthNum == 8) {
			s = s + "August\n";

			for (int i = 0; i < month.size(); i++) {
				s = s + "Farm ID: " + month.get(i).getID();
				s = s + " Percentage of Total Weight: " + month.get(i).getPercentTotalWeight() + "\n";
			}

			s = s + "Total Weight: " + totalWeight;
		}

		if (monthNum == 9) {
			s = s + "September\n";

			for (int i = 0; i < month.size(); i++) {
				s = s + "Farm ID: " + month.get(i).getID();
				s = s + " Percentage of Total Weight: " + month.get(i).getPercentTotalWeight() + "\n";
			}

			s = s + "Total Weight: " + totalWeight;
		}

		if (monthNum == 10) {
			s = s + "October\n";

			for (int i = 0; i < month.size(); i++) {
				s = s + "Farm ID: " + month.get(i).getID();
				s = s + " Percentage of Total Weight: " + month.get(i).getPercentTotalWeight() + "\n";
			}

			s = s + "Total Weight: " + totalWeight;
		}

		if (monthNum == 11) {
			s = s + "November\n";

			for (int i = 0; i < month.size(); i++) {
				s = s + "Farm ID: " + month.get(i).getID();
				s = s + " Percentage of Total Weight: " + month.get(i).getPercentTotalWeight() + "\n";
			}

			s = s + "Total Weight: " + totalWeight;
		}

		if (monthNum == 12) {
			s = s + "December\n";

			for (int i = 0; i < month.size(); i++) {
				s = s + "Farm ID: " + month.get(i).getID();
				s = s + " Percentage of Total Weight: " + month.get(i).getPercentTotalWeight() + "\n";
			}

			s = s + "Total Weight: " + totalWeight;
		}

		// Returns the string of data
		return s;
	}

	/**
	 * Sets the percent weight of a farm for a particular month
	 * 
	 * @param month       the array list of farms in a month
	 * @param totalWeight the total weight of farms in a month
	 */
	private void monthPercentWeight(ArrayList<Farm> month, double totalWeight) {

		// Iterates through the list
		for (int i = 0; i < month.size(); i++) {

			// Sets the percent weight to the weight of the farm / the total weight *100
			month.get(i).setPercentageTotalWeight((month.get(i).getWeight() / totalWeight) * 100);
		}
	}

	/**
	 * prints the monthly report to a file
	 * 
	 * @param writeToFile if the user chose to write to file
	 * @param month       the list of farms in a month
	 * @param totalWeight the total weight of the month
	 * @param monthName   the name of the month
	 * @param year        the year of the month
	 */
	private void printMonthlyReport(boolean writeToFile, ArrayList<Farm> month, double totalWeight, String monthName,
			String year) {

		if (writeToFile == true) {

			// Creates a new file
			File file = new File("monthlyReport.txt");
			PrintWriter printWriter;

			try {

				// Creates a new print writer
				printWriter = new PrintWriter(file);

				// Prints the name of the month and the year
				printWriter.println(monthName + " " + year);

				// Iterates through the list of farms in the month
				for (int i = 0; i < month.size(); i++) {

					// prints each farm id and percent of the total weight
					printWriter.print("Farm ID: " + month.get(i).getID());
					printWriter.println(" Percentage of Total Weight: " + month.get(i).getPercentTotalWeight());
				}

				// Prints the total weight of the month
				printWriter.println("Total Weight For Month: " + totalWeight);

				printWriter.close();
			} catch (FileNotFoundException e) {

			}
		}
	}
}
