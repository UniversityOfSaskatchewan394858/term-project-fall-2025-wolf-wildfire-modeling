void hourlyUpdate()
{/*ALCODESTART::1764294797749*/
// reset index if end of data is reached
if (climateRecordIdx >= climateRecordList.size()) climateRecordIdx = 0;

// get climate data for current hour
ClimateRecord currClimate = climateRecordList.get(climateRecordIdx);
temperature = currClimate.temp;
windSpeed = currClimate.windSpd; 
windDirection = Math.toRadians(currClimate.windDir * 10);
precipitation = currClimate.precip;
relativeHumidity = currClimate.relHumPercent / 100;
climateRecordIdx++;

setFireSpreadRatePerCell();


/*ALCODEEND*/}

void endSimulation()
{/*ALCODESTART::1765046620965*/
getEngine().finish()
/*ALCODEEND*/}

void dailyUpdate()
{/*ALCODESTART::1765047775599*/
daysSinceRain++;
daysBurning++;

// update burn time for each burning cells
for (Cell c : cells) {
	if (c.isCellBurning) {
		c.durationOfBurn++;
	}
}
/*ALCODEEND*/}

