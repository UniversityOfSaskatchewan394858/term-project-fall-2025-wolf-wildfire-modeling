void hourlyClimateUpdate()
{/*ALCODESTART::1764294797749*/
ClimateRecord currClimate = climateRecordList.get(climateRecordIdx);
temperature = currClimate.temp;
windSpeed = currClimate.windSpd; 
windDirection = Math.toRadians(currClimate.windDir * 10);
climateRecordIdx++;
System.out.println(temperature);
System.out.println(windSpeed);
System.out.println(windDirection);
/*ALCODEEND*/}

