package lib.UILA;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class HandleData {	
  //Constructor:
  public HandleData() {
	  //TODO: Constructor Stub  
  }
	
  //File Writer, Save Data Method:
  public static void saveData(String fileName, double yourDouble) throws Exception {    
    FileOutputStream fileOut = new FileOutputStream(fileName);
    DataOutputStream outDouble = new DataOutputStream(fileOut);
  
    outDouble.writeDouble(yourDouble);

    outDouble.close();
  }
  
  //File Reader, Open Data Method:
  public static double openData(String fileName) throws Exception {	
	  FileInputStream fileInDouble = new FileInputStream(fileName);
	  DataInputStream inDouble = new DataInputStream(fileInDouble);
	
	  double newDouble = inDouble.readDouble();
	
	  inDouble.close();
	
	  return newDouble;
  }
  
  public static void delete(String fileName) throws Exception {
	  File fileToDelete = new File(fileName); 
	
	  if (fileToDelete.delete()) {
	    System.out.println( fileName + " Successfully Deleted.");	
	  }
	
	  else {
	    System.out.println( "Delete Not Completed.");	
	  }
  }

  //API USER: Override Method to access data!
  public void commandData(String fileName, double yourDouble) throws Exception{}
}