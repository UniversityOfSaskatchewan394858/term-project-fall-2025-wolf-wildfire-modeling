import org.geotools.gce.geotiff.GeoTiffReader;
import java.io.File;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.coverage.grid.GridGeometry2D;
import net.opengis.gml311.DirectPositionListType;
import java.io.File;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.referencing.CRS;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.DataBuffer;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;

/**
 * DataAccessorAndFormatter
 */	
public class DataAccessorAndFormatter implements Serializable {
	private Raster waterRaster;
	private int[] waterPixelData;
	
	private Raster woodRaster;
	private int[] woodPixelData;
	
	private Raster sSoilRaster;
	private int[] sSoilPixelData;
	
	private int width;
	private int height;
    /**
     * Default constructor
     */
    public DataAccessorAndFormatter() {
    	// Define the path to your GeoTIFF file within the AnyLogic model's resources
    	File waterFile = new File("./input_data/WaterTiff4.tif");
    	File woodFile = new File("./input_data/WoodTiff.tif");
    	File ss_soilFile = new File("./input_data/SaturatedSoil.tif");
    	
    	try {
    	    // Create a GeoTiffReader
    	    GeoTiffReader reader;

    	    // Read the coverage (the image data and georeferencing information)
    	    GridCoverage2D coverage;
    	    
    	    // You can now access metadata, raster data, and coordinate information
    	    CoordinateReferenceSystem crs;

    	    // Loop through pixels or access a specific one
    	    // For example, reading the value at pixel (x, y) = (100, 100)
    	    //raster.getPixel(100, 100, pixelData);
    	    //System.out.println("Pixel value at (100, 100): " + pixelData[0]);
    	    
    	    reader = new GeoTiffReader(waterFile);
    	    coverage = reader.read(null);
    	    crs = coverage.getCoordinateReferenceSystem();
    	    waterRaster = coverage.getRenderedImage().getData();
    	    waterPixelData = new int[waterRaster.getNumBands()];
    	    reader.dispose();
    	    
    	    reader = new GeoTiffReader(woodFile);
    	    coverage = reader.read(null);
    	    crs = coverage.getCoordinateReferenceSystem();
    	    woodRaster = coverage.getRenderedImage().getData();
    	    
    	    width = woodRaster.getWidth()-1;
    	    height = woodRaster.getHeight()-1;
    	    
    	    woodPixelData = new int[woodRaster.getNumBands()];
    	    reader.dispose();
    	    
    	    reader = new GeoTiffReader(ss_soilFile);
    	    coverage = reader.read(null);
    	    crs = coverage.getCoordinateReferenceSystem();
    	    sSoilRaster = coverage.getRenderedImage().getData();
    	    sSoilPixelData = new int[sSoilRaster.getNumBands()];
    	    reader.dispose();
    	    

    	} catch (Exception e) {
    	    e.printStackTrace();
    	    System.out.println("Error reading GeoTIFF: " + e.getMessage());
    	}
    	
    }
    
    private int clamp(int val, int min, int max) {
    	return Math.max(min, Math.min(max, val));
    }
    
    public float averageAroundPosition(Raster raster, int width_in, int height_in, int x, int y) {
    	int[] pixelData = {0};
    	float sum = 0;
    	float total = 0;
    	//raster.getPixel(clamp((int)x,0,width),clamp((int)y,0,height),waterPixelData);
    	//return waterPixelData[0];/*
    	for(int x_offset = -width_in/2; x_offset < width_in/2; x_offset++) {
        	for(int y_offset = -height_in/2; y_offset < height_in/2; y_offset++) {
        		raster.getPixel(clamp(x+x_offset,0,width),clamp(y+y_offset,0,height), pixelData);
        		
        		if(pixelData[0] != 0) {
        			sum++;
        		}
        		total++;
        	}
    	}
    	return sum/total;
    }
    
    public boolean isWaterAtPosition(double x, double y) {
    	x = x * Math.min(width,height);
    	y = y * Math.min(width,height);
    	float p = averageAroundPosition(waterRaster,10,10,
    			clamp((int)x,0,width),clamp((int)y,0,height));
    		
    	return p > 0.5;
    	//waterRaster.getPixel(clamp((int)x,0,width),clamp((int)y,0,height),waterPixelData);
    	//return waterPixelData[0] != 0;
    }
    
    public boolean isWoodedAtPosition(double x, double y) {
    	x = x * Math.min(width,height);
    	y = y * Math.min(width,height);
    	float p = averageAroundPosition(woodRaster,10,10,
    			clamp((int)x,0,width),clamp((int)y,0,height));
    	return p > 0.5;
    	//woodRaster.getPixel(clamp((int)x,0,width),clamp((int)y,0,height),woodPixelData);
    	//return woodPixelData[0] != 0;
    }
    
    public boolean isSSoilAtPosition(double x, double y) {
    	x = x * Math.min(width,height);
    	y = y * Math.min(width,height);
    	float p = averageAroundPosition(sSoilRaster,10,10,
    			clamp((int)x,0,width),clamp((int)y,0,height));
    	return p > 0.25;
    	//sSoilRaster.getPixel(clamp((int)x,0,width),clamp((int)y,0,height),sSoilPixelData);
    	//return sSoilPixelData[0] != 0;
    }
    
    public Color getPixelColourAtPosition(double x, double y) {
    	int r = 0;
    	int g = 0;
    	int b = 0;
    	if(isWaterAtPosition(x,y)) {
    		b=255;
    	}
    	else if(isWoodedAtPosition(x,y)) {
    		g = 255;
    	}
    	if(isSSoilAtPosition(x,y)) {
    		r = 150;
    		g = 75;
    	}

    	return new Color(r,g,b);
    }
	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * This number is here for model snapshot storing purpose<br>
	 * It needs to be changed when this class gets changed
	 */ 
	private static final long serialVersionUID = 1L;

}