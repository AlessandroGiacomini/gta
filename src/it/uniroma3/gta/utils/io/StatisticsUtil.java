package it.uniroma3.gta.utils.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import cytoscape.CyNode;

public class StatisticsUtil {
	
	public StatisticsUtil() {}

	public boolean makeStats(String path, HashMap<String, ArrayList<String>> statistics, ArrayList<LinkedList<CyNode>> shortestPaths) {
			
		String finalPath;
		if (path.endsWith("/"))
			finalPath = path + "stats.xlsx";
		else
			finalPath = path + "/stats.xlsx";
		File statsXLSX = new File(finalPath);
		if (!statsXLSX.exists()) {
			try {	
				
				XSSFWorkbook workbook = new XSSFWorkbook(); 
				XSSFSheet firstSheet = workbook.createSheet("Sheet1");
				
				XSSFRow row0 = firstSheet.createRow(0);
				XSSFCell title = row0.createCell(0); 
				title.setCellValue(new XSSFRichTextString("NETWORK STATISTICS"));
				
				XSSFRow row1 = firstSheet.createRow(1);
				XSSFCell cellNumNodeName0 = row1.createCell(0); 
				cellNumNodeName0.setCellValue(new XSSFRichTextString("#"));
				XSSFCell cellNumBetweenness0 = row1.createCell(1);
				cellNumBetweenness0.setCellValue(new XSSFRichTextString("Betweenness Centrality"));
				XSSFCell cellNumCloseness0 = row1.createCell(2);
				cellNumCloseness0.setCellValue(new XSSFRichTextString("Closeness Centrality"));
				XSSFCell cellNumDegree0 = row1.createCell(3);
				cellNumDegree0.setCellValue(new XSSFRichTextString("Degree Centrality"));
				XSSFCell cellNumStress0 = row1.createCell(4);
				cellNumStress0.setCellValue(new XSSFRichTextString("Stress Centrality"));
				
				for (String node: statistics.keySet()) {
					XSSFRow rowIter = firstSheet.createRow(firstSheet.getLastRowNum()+1);
					XSSFCell cellNumNodeName = rowIter.createCell(0); 
					cellNumNodeName.setCellValue(new XSSFRichTextString(node));
					int col = 1;
					for (int i=0; i<statistics.get(node).size(); i++) {
						if (i < statistics.get(node).size()) {
							XSSFCell cell = rowIter.createCell(col); 
							cell.setCellValue(new XSSFRichTextString(statistics.get(node).get(i)));
							col++;
						}
					}
				}
				
				FileOutputStream fos = new FileOutputStream(finalPath);
				workbook.write(fos);
				fos.close();
				return true;
			} catch (IOException ex) {
				return false;
			}	
		}
		else
			return false;
	}	
}
