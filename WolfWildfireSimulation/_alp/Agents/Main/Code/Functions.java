double setInitialCellProperties()
{/*ALCODESTART::1763779415620*/
int i = 1;
for (Cell c : cells) {

	/*
	// testing for getX and getY
	int x = (int)c.getX();
	int y = (int)c.getY();
	
	c.colour = new Color(100 / (x+1) , 100 / (y+1), 0);
	*/
	
	// elevation 
	c.elevation = Math.random();
	int colourValue = (int)Math.round(c.elevation * 200);
	

	// cell is water or land depending on elevation
	// will be changed to reflect + or - with real data
	if (i % 2 == 0){
	c.colour = new Color(0, colourValue, 0);
	}
	else{c.colour = new Color(0, 0, colourValue);}

	
	c.vegetation = Math.random();
	
	// initilaizing fuelLevel with arbitrary equation
	c.fuelLevel = c.vegetation * 10;
	
	i++;
  }
  
/*ALCODEEND*/}

