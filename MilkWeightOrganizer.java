import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

/**
 * MilkWeightOrganizer - collect data from input file
 */
public class MilkWeightOrganizer {
	
	@SuppressWarnings("unused")
	private static final int jan = 0; // index 0
	@SuppressWarnings("unused")
	private static final int feb = 1; // index 1
	@SuppressWarnings("unused")
	private static final int mar = 2; // index 2
	@SuppressWarnings("unused")
	private static final int apr = 3; // index 3
	@SuppressWarnings("unused")
	private static final int may = 4; // index 4
	@SuppressWarnings("unused")
	private static final int jun = 5; // index 5
	@SuppressWarnings("unused")
	private static final int jul = 6; // index 6
	@SuppressWarnings("unused")
	private static final int aug = 7; // index 7
	@SuppressWarnings("unused")
	private static final int sep = 8; // index 8
	@SuppressWarnings("unused")
	private static final int oct = 9; // index 9
	@SuppressWarnings("unused")
	private static final int nov = 10;// index 10
	@SuppressWarnings("unused")
	private static final int dec = 11;// index 11
	
	//                FarmID  Farm
	private Hashtable<String, Farm> farms;
	
	/**
	 * MilkWeightOrganizer constructor
	 */
	public MilkWeightOrganizer() {
		farms = new Hashtable<String, Farm>();
	}
	
	/**
	 * add new farm to farms hashtable
	 * 
	 * @param farmID to add
	 */
	public void addFarm(String farmID) {
		farms.putIfAbsent(farmID, new Farm());
	}
	
	/**
	 * add specified farm to farms hashtable
	 * 
	 * @param farmID of farm to add
	 * @param farm to add
	 */
	public void addFarm(String farmID, Farm farm) {
		farms.putIfAbsent(farmID, farm);
	}
	
	/**
	 * display the total milk weight and percent of the total of all farm for each month. 
	 *     Sort, the list by month number 1-12, show total weight, 
	 *     then that farm's percent of the total milk received for each month.
	 * 
	 * @param farmID to search for
	 * @param year to search for
	 * @return 2D array containing -> 1) monthly total 2) percent contributed to yearly total
	 * @throws Exception 
	 */
	public float[][] getFarmReport(String farmID, int year) throws Exception {
		Farm farm = farms.get(farmID);
		if(farm == null) {
			throw new Exception("Farm Not Found");
		}
		// array of size 12 (each index assigned with a month
		// each index contains array of size 2 storing total for that month and percentage for the year
		float[][] results = new float[12][2];
		
		Hashtable<Integer, Hashtable<Integer, Integer>> y = farm.getMilkWeights().get(year);
		Set<Integer> months = y.keySet();
		for(Integer m : months) {
			float monthlyTotal = (float) 0.0;
			Set<Integer> days = y.get(m).keySet();
			for(Integer d : days) {
				monthlyTotal = monthlyTotal + y.get(m).get(d);
			}
			results[m][0] = monthlyTotal;
		}
		
		float yearTotal = 0;
		for(float[] result : results) {
			yearTotal = yearTotal + result[0];
		}
		for(float[] result : results) {
			result[1] = result[0] / yearTotal;
		}
		
		return results;
	}
	
	/**
	 * display list of total weight and percent of total weight of all farms by farm for the year. 
	 *     Sort by Farm ID, or you can allow the user to select display ascending or descending by weight.
	 * 
	 * @param year to search for
	 * @return 2D array containing -> 1) farm ID 2) yearly total for farm 3) percent contributed to yearly total for all farms
	 */
	public Object[][] getAnnualReport(int year) {
		
		ArrayList<Object[]> elements = new ArrayList<Object[]>();
		
		for(String farmID : farms.keySet()) {
			Hashtable<Integer, Hashtable<Integer, Integer>> thisFarmYear = farms.get(farmID).getMilkWeights().get(year);
			if(thisFarmYear != null) {
				int farmYearTotal = 0;
				for(Integer m : thisFarmYear.keySet()) {
					Hashtable<Integer, Integer> thisMonth = thisFarmYear.get(m);
					for(Integer d : thisMonth.keySet()) {
						farmYearTotal = farmYearTotal + thisMonth.get(d);
					}
				}
				
				Object[] element = new Object[3];
				element[0] = farmID;
				element[1] = farmYearTotal;
				
				elements.add(element);
			}
		}
		
		int yearTotal = 0;
		for(Object[] element : elements) {
			yearTotal = yearTotal + (int) element[1];
		}
		
		// index 0 = farm id
		// index 1 = total weight for the year for that farm
		// index 2 = percent of total weight for the year that came from that farm
		Object[][] results = new Object[elements.size()][3];
		
		for(Object[] element : elements) {
			element[2] = (float) ( (int) element[1] / yearTotal);
			results[elements.indexOf(element)] = element;
		}
		
		return results;
	}
	
	/**
	 * display a list of totals and percent of total by farm. 
	 *     The list must be sorted by Farm ID, or you can prompt for ascending or descending by weight.
	 * 
	 * @param year to search for
	 * @param month to search for
	 * @return 2D array containing -> 1) farm ID 2) monthly total for farm 3) percent contributed to monthly total for all farms
	 */
	public Object[][] getMonthlyReport(int year, int month) {
		
		ArrayList<Object[]> elements = new ArrayList<Object[]>();
		
		for(String farmID : farms.keySet()) {
			Hashtable<Integer, Hashtable<Integer, Integer>> thisFarmYear = farms.get(farmID).getMilkWeights().get(year);
			if(thisFarmYear != null) {
				
				Hashtable<Integer, Integer> thisFarmMonth = thisFarmYear.get(month);
				if(thisFarmMonth != null) {
					
					int farmMonthTotal = 0;
					for(Integer d : thisFarmMonth.keySet()) {
						farmMonthTotal = farmMonthTotal + thisFarmMonth.get(d);
					}
					Object[] element = new Object[3];
					element[0] = farmID;
					element[1] = farmMonthTotal;
					
					elements.add(element);
				}
			}
		}
		
		int monthTotal = 0;
		for(Object[] element : elements) {
			monthTotal = monthTotal + (int) element[1];
		}
		
		// index 0 = farm id
		// index 1 = total weight for the month for that farm
		// index 2 = percent of total weight for the month that came from that farm
		Object[][] results = new Object[elements.size()][3];
		
		for(Object[] element : elements) {
			element[2] = (float) ( (int) element[1] / monthTotal);
			results[elements.indexOf(element)] = element;
		}
		
		return results;
	}
	
	/**
	 * display the total milk weight per farm and the percentage of the total for each farm over that date range. 
	 *     The list must be sorted by Farm ID, or you can prompt for ascending or descending order by weight or percentage.
	 * 
	 * @param startYear year of start date
	 * @param startMonth month of start date
	 * @param startDay day of start date
	 * @param endYear year of end date
	 * @param endMonth month of end date
	 * @param endDay day of end date
	 * @return 
	 */
	public Object[][] getDateRangeReport(int startYear, int startMonth, int startDay, 
			int endYear, int endMonth, int endDay) {
		Object[][] results = new Object[farms.size()][3];
		
		
		
		return results;
	}
	
}
