double temperatureRegulation()
{/*ALCODESTART::1763781827539*/
double angledWeight = 1;
if (get_Main().directionalWeighting) { 
	angledWeight = 1 / Math.sqrt(2);
}

// OOB stands for Out Of Bounds and this only impacts the world border
double OOB_Temperature = temperature;
if (get_Main().edgeHeatSink) { 
	OOB_Temperature = get_Main().baseTemperature;
}

Cell[] Neighbors = new Cell[8];

Neighbors[0] = (Cell) this.getAgentNextToMe(NORTH);
Neighbors[1] = (Cell) this.getAgentNextToMe(NORTHEAST);
Neighbors[2] = (Cell) this.getAgentNextToMe(EAST);
Neighbors[3] = (Cell) this.getAgentNextToMe(SOUTHEAST);
Neighbors[4] = (Cell) this.getAgentNextToMe(SOUTH);
Neighbors[5] = (Cell) this.getAgentNextToMe(SOUTHWEST);
Neighbors[6] = (Cell) this.getAgentNextToMe(WEST);
Neighbors[7] = (Cell) this.getAgentNextToMe(NORTHWEST);

double budget = 0;
double denominator = 0;

// pre-calculate how much temperature needs to be spread to
// neighbours to avoid going all in on the first cell looked at
// and evenly distribute heat as needed.
for (int i = 0; i < 8; i++) {
	double differance = temperature;
	if (Neighbors[i] != null) {
		differance -= Neighbors[i].temperature;
	} else {
		differance -= OOB_Temperature;
	} 
	
	if (differance <= 0) { 
		continue; // cell is same temperature so no need to change it
	} 
	
	if (i % 2 == 0) { 
		denominator += differance;
	} else { // odd numbers are diagonal and subject to the angleWeight
		denominator += angledWeight * differance; 
	}
}

if (denominator <= 1e-12) {
	return; // all cells surrounding it are the same temperature
}

budget = get_Main().woodConductivity * denominator;

double biasTotal = 0;
double[] biases = new double[8];

// Now figure out how wind effects the bias towards each direction.
for (int i = 0; i < 8; i++) {
	double differance = temperature;
	double change = 0;
	if (Neighbors[i] != null) {
		differance -= Neighbors[i].temperature;
	} else {
		differance -= OOB_Temperature;
	} 
	
	if (differance <= 0) {
		biases[i] = 0;
		continue; // cell is same temperature so no need to change it
	} 
	
	if (i % 2 == 0) { 
		change = differance / denominator;
	} else { // odd numbers are diagonal and subject to the angleWeight
		change = angledWeight * differance / denominator;
	}
	
	double angleToNeighbor = i * (Math.PI / 4);
	
	biases[i] = 1.0;
	biases[i] += (get_Main().windSpeed * get_Main().windPower) * Math.cos(angleToNeighbor - get_Main().windDirection);
	biases[i] = Math.max(0, biases[i]);
	biases[i] *= change;
	
	biasTotal += biases[i];
}
if (biasTotal <= 1e-12) {
	return; // something went wrong and it should stop if this happens.
}

double lostTemperature = 0;

// Finally apply the temperature change.
for (int i = 0; i < 8; i++) {	
	if (biases[i] == 0) {
		continue; // cell is same temperature so no need to change it
	} 
	double change = budget * (biases[i] / biasTotal);
	change /= get_Main().heatCapacity;
	
	if (Neighbors[i] != null) {
		Neighbors[i].temperature += change;
	}
	lostTemperature += change;
}

//System.out.println("temperature delta: " + lostTemperature);
//System.out.println("temperature: " + temperature);

// this should never set it to base but it is still a safty percausion
// for vary weird edge cases
temperature = Math.max(get_Main().baseTemperature, temperature - lostTemperature);

/*ALCODEEND*/}

double notes()
{/*ALCODESTART::1763927011571*/
/* 
both temperature regulation and fuel consumption is based on
area of the cell and surface area of the fuel in the cell.

This would require a complete rework of the first half of burning
to account for surface area of fuel, as well as a update to the
heat spread system to conduct heat normally through fuel to fuel, air
to air, and ground to ground.
*/
/*ALCODEEND*/}

