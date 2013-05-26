package cland.google.gdm.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import cland.google.gdm.exceptions.GMUnkownItemException;
import cland.google.gdm.GDirection;
import cland.google.gdm.GMatrix;
import cland.google.gdm.Step;

public class MatrixTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//urlDirectionTest();
		jsonDirectionPath();
		
		//urlTest();
		//jsonPath();
	} //end main

	public static void jsonPath(){		
		 
		try {
			FileReader filereader = new FileReader(".\\files\\jsontest.txt");
			String json = readWithStringBuilder(filereader);
			
			int dist1 = GMatrix.getDistanceValue(json,0,0);
			String dura1 = GMatrix.getDurationText(json,0,0);
			String status1 = GMatrix.getElementStatus(json,0,0);
			String dist2 = GMatrix.getDistanceText(json,0,1);
			String dist3 = GMatrix.getDistanceText(json,0,2);
			int dura3 = GMatrix.getDurationValue(json,0,2);
			String status3 = GMatrix.getElementStatus(json,0,2);
			System.out.println(GMatrix.getOriginAddress(json, 0) + " >> " + dist1 + " - " + dura1 + " - " + status1);
			System.out.println(GMatrix.getOriginAddress(json, 0) + " >> " + dist2);
			System.out.println(GMatrix.getDestinationAddress(json, 0) + " >> " + dist3 + " - " + dura3 + " - " + status3);
			
			System.out.println("\nMain query status: " + GMatrix.getQueryStatus(json));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GMUnkownItemException e) {			
			System.out.println(e.toString());
		} 
	} //end method jsonPath()
	
	public static void urlTest(){
		String origins = "-33.869952,18.537687";
		String  destinations = "-33.96979309231926,18.53386491408955|-33.90630759128996,18.40310809285091|-33.97761,18.46555";
		String avoid = GMatrix.AVOID_HIGHWAYS + "|" +GMatrix.AVOID_TOLLS;
		String units = GMatrix.UNITS_DEFAULT;
		String language = "";
		boolean sensor = true;
		
		String url = GMatrix.getUrl(origins, destinations, GMatrix.JSON, GMatrix.MOD_DRIVING, avoid, units, language, sensor);
		System.out.println(url);
	} //end method testURL
	
	public static void urlDirectionTest(){
		String origin = "-33.869952,18.537687";
		String  destination = "-33.96979309231926,18.53386491408955";
		String avoid = GMatrix.AVOID_HIGHWAYS + "|" +GMatrix.AVOID_TOLLS;
		String units = GMatrix.UNITS_DEFAULT;
		String language = "";
		boolean sensor = true;
		
		//time example
		Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		long seconds = (cal1.getTimeInMillis()/1000);
		
		String url = GDirection.getUrl(origin, destination, GMatrix.JSON, GMatrix.MOD_DRIVING, avoid, units, language, sensor,true,seconds,0);
		System.out.println(url);
				
	} //end method
	
	public static void jsonDirectionPath(){		
		 
		try {
			FileReader filereader = new FileReader(".\\files\\jsonroutes.json");
			String json = readWithStringBuilder(filereader);
			
			Step step = GDirection.getDirectionStep(json, 0, 0, 0);
			System.out.println(step);
			System.out.println("\nMain query status: " + GDirection.getQueryStatus(json));
			
			List<String> polyList = GDirection.getOverviewPolylines(json, 0);
			for(String s:polyList){
				System.out.println(s + " : Lat = " + GDirection.getLatitude(s) + " - Lng = " + GDirection.getLongitude(s));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GMUnkownItemException e) {			
			System.out.println(e.toString());
		} 
	} //end method jsonPath()
	
    static String readWithStringBuffer(Reader fr)
   		 throws IOException {

   		 BufferedReader br = new BufferedReader(fr);
   		 String line;
   		 StringBuffer result = new StringBuffer();
   		 while ((line = br.readLine()) != null) {
   		 result.append(line);
   		 }

   		 return result.toString();
   } //end method

   static String readWithStringBuilder(Reader fr)
   		 throws IOException {

   		 BufferedReader br = new BufferedReader(fr);
   		 String line;
   		 StringBuilder result = new StringBuilder();
   		 while ((line = br.readLine()) != null) {
   		 result.append(line);
   		 }

   		 return result.toString();
    } //end method
   
	
} //end class
