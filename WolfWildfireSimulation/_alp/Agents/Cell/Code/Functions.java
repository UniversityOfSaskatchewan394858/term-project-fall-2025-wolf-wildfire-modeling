boolean isInRadius(int i)
{/*ALCODESTART::1763947322041*/
/*
this whole script is simply to check whether or 
not the current point is within the circle.
*/
double CellX = 0;
double CellY = 0;

switch (i) { 
	case 0:
		CellX = -1;
		CellY = 1;
		break;
	case 1:
		CellY = 1;
		break;
	case 2:
		CellX = 1;
		CellY = 1;
		break;
	case 3:
		CellX = 1;
		break;
	case 4:
		CellX = 1;
		CellY = -1;
		break;
	case 5:
		CellY = -1;
		break;
	case 6:		
		CellX = -1;
		CellY = -1;
		break;
	case 7:		
		CellX = -1;
		break;
}		

CellX *= get_Main().cellWidth / 2;
CellY *= get_Main().cellWidth / 2;

double vector = Math.pow(CellX - firePointX, 2) + Math.pow(CellY - firePointY, 2);

return vector < Math.pow(burnRadius, 2);
/*ALCODEEND*/}

boolean igniteNeighbor(int i)
{/*ALCODESTART::1763950567108*/
/*
this whole script is simply to tell the neighbors
that they are going to burn, and which point in
the neighbor should act as the source of the fire.
*/
double CellX = 0;
double CellY = 0;

Neighbors[i].isCellBurning = true;
switch (i) { 
	case 0:
		CellX = -1;
		CellY = 1;
		break;
	case 1:
		CellY = 1;
		break;
	case 2:
		CellX = 1;
		CellY = 1;
		break;
	case 3:
		CellX = 1;
		break;
	case 4:
		CellX = 1;
		CellY = -1;
		break;
	case 5:
		CellY = -1;
		break;
	case 6:		
		CellX = -1;
		CellY = -1;
		break;
	case 7:		
		CellX = -1;
	break;
}

Neighbors[i].firePointX = -CellX * get_Main().cellWidth / 2;
Neighbors[i].firePointY	= -CellY * get_Main().cellWidth / 2;

send("Ignite", Neighbors[i]);
/*ALCODEEND*/}

