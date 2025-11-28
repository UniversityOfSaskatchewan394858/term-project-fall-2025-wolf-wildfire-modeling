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
	private Raster raster;
	private int[] pixelData;
	
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
    	File file = new File("./sample.tif");
    	File waterFile = new File("./WaterTiff4.tif");
    	File woodFile = new File("./WoodTiff.tif");
    	File ss_soilFile = new File("./SaturatedSoil.tif");
    	
    	try {
    	    // Create a GeoTiffReader
    	    GeoTiffReader reader = new GeoTiffReader(file);

    	    // Read the coverage (the image data and georeferencing information)
    	    GridCoverage2D coverage = reader.read(null);
    	    
    	    // You can now access metadata, raster data, and coordinate information
    	    CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem();
    	    // Example: get the raster data
    	    raster = coverage.getRenderedImage().getData();
    	    
    	    // Example: access pixel data directly
    	    width = raster.getWidth();
    	    height = raster.getHeight();
    	    pixelData = new int[raster.getNumBands()];

    	    // Loop through pixels or access a specific one
    	    // For example, reading the value at pixel (x, y) = (100, 100)
    	    //raster.getPixel(100, 100, pixelData);
    	    //System.out.println("Pixel value at (100, 100): " + pixelData[0]);
    	    // Close the reader
    	    reader.dispose();
    	    
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
    
    public double getPixelDataAtPosition(int x, int y) {
    	raster.getPixel(clamp(x,0,width),clamp(y,0,height),pixelData);
    	return pixelData[0];
    }
    public double getPixelDataAtPosition(double x, double y) {
    	raster.getPixel(clamp((int)x,0,width),clamp((int)y,0,height),pixelData);
    	return pixelData[0];
    }
    
    public Color getPixelColourAtPosition(double x, double y) {
    	waterRaster.getPixel(clamp((int)x,0,width-1),clamp((int)y,0,height-1),waterPixelData);
    	woodRaster.getPixel(clamp((int)x,0,width-1),clamp((int)y,0,height-1),woodPixelData);
    	sSoilRaster.getPixel(clamp((int)x,0,width-1),clamp((int)y,0,height-1),sSoilPixelData);

    	return new Color(clamp((int)sSoilPixelData[0],0,255),
    			clamp((int)woodPixelData[0],0,255),
    			clamp((int)waterPixelData[0],0,255));
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