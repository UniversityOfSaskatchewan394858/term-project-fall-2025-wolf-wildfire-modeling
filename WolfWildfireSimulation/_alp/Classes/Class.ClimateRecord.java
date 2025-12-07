public class ClimateRecord {

	public String dateTime;   // YYYY-MM-DD HH:MM
	
	public Double temp;
	public Double relHumPercent;
	public Double precip; // mm
	public Double windDir; // 10s of deg
	public Double windSpd; // km/h


	/*
	 * Builds a climate record from a string array
	 * indexing may change depending on the year
	 */
	public ClimateRecord(String[] c) {
	
	    dateTime = c[4];
	
	    temp = getDoubleData(c[10]);
	    relHumPercent = getDoubleData(c[14]);
	    precip = getDoubleData(c[16]);
	    windDir = getDoubleData(c[18]);
	    windSpd = getDoubleData(c[20]);
	
	}
	
	private Double getDoubleData(String s) {
	    if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
	        s = s.substring(1, s.length() - 1);
	    }
	    try { 
	    	//System.out.println(Double.parseDouble(s));
	    	return Double.parseDouble(s); }
	    catch (Exception e) { return Double.NaN; }
	}

    /*
     * fills in missing data using data from the previous day
     */
	public void fillMissingFrom(ClimateRecord prev) {
		if (Double.isNaN(temp)) temp = prev.temp;
		if (Double.isNaN(relHumPercent)) relHumPercent = prev.relHumPercent;
		if (Double.isNaN(windDir)) windDir = prev.windDir;
		if (Double.isNaN(windSpd)) windSpd = prev.windSpd;
	}
}
