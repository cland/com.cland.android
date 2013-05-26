package cland.google.gdm;



import cland.google.gdm.exceptions.GMUnkownItemException;

import com.jayway.jsonpath.JsonPath;
/**
 * 
 * @author <b>Jason Dembaremba</b><br>
 * <a href='mailto:jay@cleverland.net'>jay@cleverland.net</a><br>
 * <a href='http://www.cleverland.net'>www.cleverland.net</a>
 * @version 0.1
 * 
 * @see
 * <b>Credit to:</b> <br>
 * - Jayway's <a href='http://code.google.com/p/json-path/'>JsonPath Library</a>. <br>
 * - Version: <b>json-path-0.8.0</b> <br>
 * <b>Uses or Requires:</b> <br>
 * - json-path-0.8.0.jar <br>
 * - commons-io-2.1.jar <br>
 * - commons-lang-2.6.jar <br>
 * - json-smart-1.1.jar <br>
 */
public class GMatrix {	
	private static final String matrixUrl = "http://maps.googleapis.com/maps/api/distancematrix/";
	public static final String JSON = "json";
	public static final String XML = "xml";
	public static final String MOD_DRIVING ="driver";
	public static final String MOD_BICYCLING ="driver";
	public static final String MOD_WALKING ="driver";
	public static final String AVOID_TOLLS ="tolls";
	public static final String AVOID_HIGHWAYS ="highways";
	public static final String UNITS_DEFAULT = "metric";
	public static final String UNITS_IMPERIAL = "imperial";
	private static final String DISTANCE = "distance";
	private static final String DURATION = "duration";
	private static final String STATUS = "status";
	
	public static String getUrl(String origins,String destinations, String output, String mode, String avoid,String units, String language, boolean sensor ){
		String url= matrixUrl;
		//** Output
		url = url + output + "?";
		
		//** origins
		//TODO: error checking if no origins supplied
		url = url + "origins=" + origins;	
		
		//** destinations
		//TODO: error checking if no destinations supplied
		url = url + "&destinations=" + destinations;
		
		//** mode - driving,bycycling,walking		
		if (!mode.equals("")) url = url + "&mode=" + mode;
		
		//** avoid - tolls,highways	
		if (!avoid.equals("")) url = url + "&avoid=" + avoid;
		
		//** units - metric,imperial	
		if (!units.equals("")) url = url + "&units=" + units;
		
		//** sensor - true,false
		url = url + "&sensor=" + sensor;
		return url.replace("|", "%7C");
	} //end method
	
	/**
	 * 
	 * @param json - The returned json result from Google Distance Matrix API
	 * @param fromIndex - The array index of the original point as per the origins array send to Google Distance Matrix API
	 * @param toIndex - The array index of the destination point as per the destinations array send to Google Distance Matrix API
	 * @return string distance value 
	 * @throws GMUnkownItemException 
	 */
	public static String getDistanceText(String json,int fromIndex, int toIndex) throws GMUnkownItemException{
		return getMatrixText(json, fromIndex, toIndex, DISTANCE);
	} //end method getDistanceText

	/**
	 * 
	 * @param json - The returned json result from Google Distance Matrix API
	 * @param fromIndex - The array index of the original point as per the origins array send to Google Distance Matrix API
	 * @param toIndex - The array index of the destination point as per the destinations array send to Google Distance Matrix API
	 * @return integer value distance 
	 * @throws GMUnkownItemException 
	 */
	public static Integer getDistanceValue(String json,int fromIndex, int toIndex) throws GMUnkownItemException{
		return getMatrixValue(json, fromIndex, toIndex, DISTANCE);
	} //end method getDistanceValue	
	
	/**
	 * 
	 * @param json - The returned json result from Google Distance Matrix API
	 * @param fromIndex - The array index of the original point as per the origins array send to Google Distance Matrix API
	 * @param toIndex - The array index of the destination point as per the destinations array send to Google Distance Matrix API
	 * @return string duration value 
	 * @throws GMUnkownItemException 
	 */
	public static String getDurationText(String json,int fromIndex, int toIndex) throws GMUnkownItemException{
		return getMatrixText(json, fromIndex, toIndex, DURATION);
	} //end method getDistanceText

	/**
	 * 
	 * @param json - The returned json result from Google Distance Matrix API
	 * @param fromIndex - The array index of the original point as per the origins array send to Google Distance Matrix API
	 * @param toIndex - The array index of the destination point as per the destinations array send to Google Distance Matrix API
	 * @return integer value duration 
	 * @throws GMUnkownItemException 
	 */
	public static Integer getDurationValue(String json,int fromIndex, int toIndex) throws GMUnkownItemException{
		return getMatrixValue(json, fromIndex, toIndex, DURATION);
	} //end method getDurationValue	
	
	/**
	 * 
	 * @param json - The returned json result from Google Distance Matrix API
	 * @param fromIndex - The array index of the original point as per the origins array send to Google Distance Matrix API
	 * @param toIndex - The array index of the destination point as per the destinations array send to Google Distance Matrix API
	 * @return status value of the element 
	 * @throws GMUnkownItemException 
	 */
	public static String getElementStatus(String json,int fromIndex, int toIndex) throws GMUnkownItemException{
		return getMatrixText(json, fromIndex, toIndex, STATUS);
	} //END METHOD getStatus
	/**
	 * 
	 * @param json - The returned json result from Google Distance Matrix API
	 * @param fromIndex - The array index of the original point as per the origins array send to Google Distance Matrix API
	 * @param toIndex - The array index of the destination point as per the destinations array send to Google Distance Matrix API
	 * @param item - The param whose value should be return "duration" or "status" or "distance"
	 * @return string value 
	 * @throws GMUnkownItemException 
	 */
	
	private static String getMatrixText(String json, int fromIndex, int toIndex, String item) throws GMUnkownItemException{
		try{
			if(item.equalsIgnoreCase("status")){
				return JsonPath.read(json, "$.rows[" + fromIndex + "].elements[" + toIndex + "]." + item );	
			}	
			return JsonPath.read(json, "$.rows[" + fromIndex + "].elements[" + toIndex + "]." + item + ".text");				
		}catch(Exception e){
			throw new GMUnkownItemException("Item '" + item.toUpperCase() + "' could not be retrieved using fromIndex '" + fromIndex + "' and toIndex '" + toIndex + "' in the json data. [" + e.getMessage() + "]" , e);
		}
	} //end method getMatrixText 
	
	/**
	 * 
	 * @param json - The returned json result from Google Distance Matrix API
	 * @param fromIndex - The array index of the original point as per the "origins array" send to Google Distance Matrix API
	 * @param toIndex - The array index of the destination point as per the "destinations array" send to Google Distance Matrix API
	 * @param item - The param whose value should be return "duration" or "distance" ONLY!!
	 * @return
	 * @throws GMUnkownItemException
	 */
	private static Integer getMatrixValue(String json, int fromIndex, int toIndex, String item) throws GMUnkownItemException{
		try{
			return JsonPath.read(json, "$.rows[" + fromIndex + "].elements[" + toIndex + "]." + item + ".value");
		}catch(Exception e){
			throw new GMUnkownItemException("Item '" + item.toUpperCase() + "' could not be retrieved using fromIndex '" + fromIndex + "' and toIndex '" + toIndex + "' in the json data. [" + e.getMessage() + "]" , e);
		}		
	} //end method getMatrixValue
	
	/**
	 * 
	 * @param json - The returned json result from Google Distance Matrix API
	 * @param index - the position string value to return from the '<b>origin_addresses array</b>' returned by Google Distance Matrix API
	 * @return string value at position represented by 'index'
	 */
	public static String getOriginAddress(String json, int index){
		return JsonPath.read(json, "$.origin_addresses[" + index + "]");
	}
	
	
	/**
	 * 
	 * @param json - The returned json result from Google Distance Matrix API
	 * @param index - the position string value to return from the '<b>destination_addresses array</b>' returned by Google Distance Matrix API
	 * @return string value at position represented by 'index'
	 */
	public static String getDestinationAddress(String json, int index){
		return JsonPath.read(json, "$.destination_addresses[" + index + "]");
	}
	
	public static String getQueryStatus(String json){
		return JsonPath.read(json, "$.status");
	}
} //end class GMatrix
