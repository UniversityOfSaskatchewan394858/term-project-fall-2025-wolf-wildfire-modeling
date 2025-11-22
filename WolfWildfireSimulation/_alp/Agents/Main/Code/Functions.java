double setInitialCellProperties()
{/*ALCODESTART::1763779415620*/
int i = 1;
for (Cell c : cells) {
	c.elevation = Math.random();
	int colourValue = (int)Math.round(c.elevation * 200);
	if (i % 2 == 0){
	c.colour = new Color(0, colourValue, 0);
	}
	else{c.colour = new Color(0, 0, colourValue);}
	c.vegetation = Math.random();
	i++;
  }
  
/*ALCODEEND*/}

