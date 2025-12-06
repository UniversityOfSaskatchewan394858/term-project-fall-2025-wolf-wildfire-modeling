double getClimateData()
{/*ALCODESTART::1764655608642*/

// information for climate data url
String provTerr = "MB";
String climateID = "5050919";
String year = "2025";
String urlString =
    "https://dd.weather.gc.ca/today/climate/observations/hourly/csv/"
    + provTerr 
    + "/climate_hourly_" 
    + provTerr 
    + "_"
    + climateID 
    + "_" 
    + year 
    + "_P1H.csv";

try {
	URL url = new URL(urlString);
	URLConnection conn = url.openConnection();
	
	BufferedReader br = new BufferedReader(
		new InputStreamReader(conn.getInputStream(), "UTF-8"));
		
	String line;
	boolean headerSkipped = false;
	
	while ((line = br.readLine()) != null) {
		if (!headerSkipped) {
			headerSkipped = true;
			continue;
		}
	    
	    String[] cols = line.split(",", -1);
	    if (cols.length >= 23) {
			ClimateRecord climRecord = new ClimateRecord(cols);
			// use previous days data for empty spots
	        if (!climateRecordList.isEmpty()) {
	        	climRecord.fillMissingFrom(climateRecordList.get(climateRecordList.size() - 1));
			}
	
			climateRecordList.add(climRecord);
		}
	}

	br.close();
} catch (Exception e) {
	e.printStackTrace();
	System.out.println("Error reading climate data: " + e.getMessage());
}
/*ALCODEEND*/}

