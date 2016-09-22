//Crea le cartelle dove inserire le statistiche

package it.uniroma3.gta.utils.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class GtaInit {
	
	private static String graphDate;
	private static String dataFolderPath;
	private static String tempFolderPath;

	public static String getDataFolderPath() {
		return dataFolderPath;
	}

	public static void setDataFolderPath(String dataFolderPath) {
		GtaInit.dataFolderPath = dataFolderPath;
	}

	public static String getTempFolderPath() {
		return tempFolderPath;
	}

	public static void setTempFolderPath(String tempFolderPath) {
		GtaInit.tempFolderPath = tempFolderPath;
	}
	
	public static String getGraphDate() {
		return graphDate;
	}

	public static void setGraphDate(String graphDate) {
		GtaInit.graphDate = graphDate;
	}

	public static void setup() throws IOException {
		setGraphDate(getFileFormatDate());
		File GtaDir = new File("./GTA");
		if (!GtaDir.exists()) {
			(new File("./GTA")).mkdir();
		}
		setDataFolderPath("./GTA/GtaData_" + getGraphDate());
		setTempFolderPath("./GTA/GtaTemp_" + getGraphDate());
		File GtaDataDir = new File(getDataFolderPath());
		File GtaTempDir = new File(getTempFolderPath());
		if (!GtaDataDir.exists())
			(new File("./GTA/GtaData_" + getGraphDate())).mkdir();
		if (!GtaTempDir.exists())
			(new File("./GTA/GtaTemp_" + getGraphDate())).mkdir();
		File toResume = new File("./GTA/GtaTemp_" + getGraphDate() + "/toResumeSP.txt");
		if (!toResume.exists()) {
			toResume.createNewFile();
			FileOutputStream fosToResume = new FileOutputStream(toResume.getAbsoluteFile());
			OutputStreamWriter outToResume = new OutputStreamWriter(fosToResume, Charset.forName("UTF-8")); 
			outToResume.write("0 0 ");
			outToResume.close();
			fosToResume.close();
		}
	}
	
	private static String getFileFormatDate() {
		Calendar calendar = new GregorianCalendar();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH)+1;
		int year = calendar.get(Calendar.YEAR);
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		String AM_PM;
		if(calendar.get(Calendar.AM_PM) == 0)
			AM_PM = "AM";
		else
			AM_PM = "PM";
		return day + "." + month + "." + year + "_" + hour + "." + minute + "." + second +  "." + AM_PM;
	}
	
}
