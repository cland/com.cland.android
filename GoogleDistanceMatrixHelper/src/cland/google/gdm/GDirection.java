package cland.google.gdm;

import java.util.ArrayList;
import java.util.List;

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
public class GDirection {	
	/* API Documentation:
	 * https://developers.google.com/maps/documentation/directions/
	 * Example:
	 * http://maps.googleapis.com/maps/api/directions/json?origin=-33.869952,18.537687&destination=-33.96979309231926,18.53386491408955&mode=driver&alternatives=false&sensor=false
	 */
	
	//TODO: pointers here => https://github.com/tato469/Android/tree/master/routes%20googleMaps%20v2 
	// http://stackoverflow.com/questions/14702621/answer-draw-path-between-two-points-using-google-maps-android-api-v2
	private static final String matrixUrl = "http://maps.googleapis.com/maps/api/directions/";
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
	private static final String START_LOCATION = "start_location";
	private static final String END_LOCATION = "end_location";
	private static final String INSTRUCTIONS = "html_instructions";
	private static final String TRAVEL_MODE = "travel_mode";
	private static final String OVERVIEW_POLYLINES = "overview_polyline";	
	
	public static String getUrl(String origins,String destinations, String output, String mode, String avoid,String units, String language, boolean sensor, boolean alternatives ){
		String url= matrixUrl;
		//** Output
		url = url + output + "?";
		
		//** origins
		//TODO: error checking if no origins supplied
		url = url + "origin=" + origins;	
		
		//** destinations
		//TODO: error checking if no destinations supplied
		url = url + "&destination=" + destinations;
		
		//** mode - driving,bicycling,walking		
		if (!mode.equals("")) url = url + "&mode=" + mode;
		
		//** avoid - tolls,highways	
		if (!avoid.equals("")) url = url + "&avoid=" + avoid;
		
		//** units - metric,imperial	
		if (!units.equals("")) url = url + "&units=" + units;
		
		//** sensor - true,false
		url = url + "&sensor=" + sensor;
		
		//** alternatives - true,false
		url = url + "&alternatives=" + alternatives;
		return url.replace("|", "%7C");
	} //end method
	
	public static String getUrl(String origins,String destinations, String output, String mode, String avoid,String units, String language, boolean sensor, boolean alternatives, long departure_time, long arrival_time ){
		String url= matrixUrl;
		//** Output
		url = url + output + "?";
		
		//** origins
		//TODO: error checking if no origins supplied
		url = url + "origin=" + origins;	
		
		//** destinations
		//TODO: error checking if no destinations supplied
		url = url + "&destination=" + destinations;
		
		//** mode - driving,bicycling,walking		
		if (!mode.equals("")) url = url + "&mode=" + mode;
		
		//** avoid - tolls,highways	
		if (!avoid.equals("")) url = url + "&avoid=" + avoid;
		
		//** units - metric,imperial	
		if (!units.equals("")) url = url + "&units=" + units;
		
		//** sensor - true,false
		url = url + "&sensor=" + sensor;
		
		//** alternatives - true,false
		url = url + "&alternatives=" + alternatives;
		//** departure time in seconds - true,false
		if (departure_time > 1 ) url = url + "&departure_time=" + departure_time;
		//** arrival time in seconds - true,false
		if (arrival_time > 1 ) url = url + "&arrival_time=" + arrival_time;
		return url.replace("|", "%7C");		
	} //end method getUrl
	
	
	public static String getQueryStatus(String json){
		return JsonPath.read(json, "$." + STATUS);
	}
	
	public static String getEncodedOverviewPolylines(String json,int routeIndex){
		return JsonPath.read(json, "$.routes[" + routeIndex + "]." + OVERVIEW_POLYLINES + ".points");
	}
	public static List<String> getOverviewPolylines(String json,int routeIndex){
		return decodePoly(getEncodedOverviewPolylines(json, routeIndex));
	}
	public static Double getLatitude(String latLng){
		return ((!(latLng.equals("") & latLng.indexOf(",") > 0) ? Double.valueOf(((latLng.split(","))[0]).trim()) : 0.0));
	}
	public static Double getLongitude(String latLng){
		return ((!(latLng.equals("") & latLng.indexOf(",") > 0) ? Double.valueOf(((latLng.split(","))[1]).trim()) : 0.0));
	}
	public static Step getDirectionStep(String json, int routeIndex, int legIndex, int stepIndex) throws GMUnkownItemException{
		
		String distText = (String)getStepItem(json,routeIndex,legIndex,stepIndex,DISTANCE,"text"); //JsonPath.read(json, "$.routes[" + routeIndex + "].legs[" + legIndex + "].steps[" + stepIndex + "].distance.text" + item ); 
		Integer distValue = (Integer)getStepItem(json,routeIndex,legIndex,stepIndex,DISTANCE,"value"); 
		String durationText = (String)getStepItem(json,routeIndex,legIndex,stepIndex,DURATION,"text"); 
		Integer durationValue = (Integer)getStepItem(json,routeIndex,legIndex,stepIndex,DURATION,"value"); 
		Double startLocationLat = (Double)getStepItem(json,routeIndex,legIndex,stepIndex,START_LOCATION,"lat");
		Double startLocationLng = (Double)getStepItem(json,routeIndex,legIndex,stepIndex,START_LOCATION,"lng"); 
		Double endLocationLat = (Double)getStepItem(json,routeIndex,legIndex,stepIndex,END_LOCATION,"lat");
		Double endLocationLng = (Double)getStepItem(json,routeIndex,legIndex,stepIndex,END_LOCATION,"lng"); 
		String instructions = (String)getStepItem(json,routeIndex,legIndex,stepIndex,INSTRUCTIONS,"text");
		
		return new Step(distText, 
				distValue, 
				durationText, 
				durationValue, 
				startLocationLat, 
				startLocationLng, 
				endLocationLat, 
				endLocationLng, 
				instructions);								
	} //end method

	private static Object getStepItem(String json, int routeIndex, int legIndex, int stepIndex, String item, String itemType) throws GMUnkownItemException{
		try{
			if(item.equalsIgnoreCase(INSTRUCTIONS) || item.equalsIgnoreCase(TRAVEL_MODE) ){
				return JsonPath.read(json, "$.routes[" + routeIndex + "].legs[" + legIndex + "].steps[" + stepIndex + "]." + item);
			}			
			return JsonPath.read(json, "$.routes[" + routeIndex + "].legs[" + legIndex + "].steps[" + stepIndex + "]." + item + "." + itemType );
		}catch(Exception e){
			throw new GMUnkownItemException("Item '" + item.toUpperCase() + "' could not be retrieved using routeIndex '" + routeIndex + "' and legIndex '" + legIndex + "' and stepIndex '" + stepIndex + "' in the json data. [" + e.getMessage() + "]" , e);
		}
	} //END method getStepItem
	
	 private static List<String> decodePoly(String encoded) {

		    List<String> poly = new ArrayList<String>();
		    int index = 0, len = encoded.length();
		    int lat = 0, lng = 0;

		    while (index < len) {
		        int b, shift = 0, result = 0;
		        do {
		            b = encoded.charAt(index++) - 63;
		            result |= (b & 0x1f) << shift;
		            shift += 5;
		        } while (b >= 0x20);
		        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
		        lat += dlat;

		        shift = 0;
		        result = 0;
		        do {
		            b = encoded.charAt(index++) - 63;
		            result |= (b & 0x1f) << shift;
		            shift += 5;
		        } while (b >= 0x20);
		        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
		        lng += dlng;

		        
		        //LatLng p = new LatLng( (((double) lat / 1E5)), (((double) lng / 1E5) ));
		        String latLng = String.valueOf((((double) lat / 1E5))) + "," + (((double) lng / 1E5));
		        poly.add(latLng);
		    }

		    return poly;
	} //end method decodePoly
	 
	 

} //end class GDirection
