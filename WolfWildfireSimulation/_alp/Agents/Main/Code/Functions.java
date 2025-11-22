double setInitialCellProperties()
{/*ALCODESTART::1763779415620*/
for (Cell c : cells) {
	c.elevation = Math.random();
	int greenValue = (int)Math.round(c.elevation * 255);
	c.colour = new Color(0, greenValue, 0);
  }
  
/*ALCODEEND*/}

