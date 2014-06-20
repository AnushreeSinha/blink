package com.blink;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;

public class CRUDcallsMethodGeneratorImpl {

	
public void generateAbstractActionClass(String pathRepo, String packageName) throws FileNotFoundException, IOException{
	
	try {
		 File source =new File("/Users/asinha1/blink/designer-miniapp/src/main/java/com/blink/AbstractAction.java");
		 System.out.println("Old path repo in generateAbstractAction "+pathRepo);
		 pathRepo=pathRepo.substring(0, pathRepo.length()-5);
		 System.out.println("New path repo in generateAbstractAction "+pathRepo);
		 String finalpath1=pathRepo+"/java/"+packageName;
		 System.out.println("finalPath in generateAbstractAction "+finalpath1);
		 String finalPath2=finalpath1.replace(".","/");
		 System.out.println("finalPath in generateAbstractAction "+finalPath2);
		 String finalPath3=finalPath2+"action";
		 
		 File f=new File(finalPath3);
		 f.mkdirs();
		 
 		System.out.println("finalPath in generateAbstractAction "+finalPath3);
 		String finalPath=finalPath3+"/AbstractAction.java";
 		System.out.println("finalPath in generateAbstractAction "+finalPath);
 		
 	  	 File dest = new File(finalPath);
 	  	 
 	  	 if(!dest.exists()) 
 	  		  dest.createNewFile();
 	  	 
 	  	 InputStream inStream = new FileInputStream(source);
 		 OutputStream outStream = new FileOutputStream(dest);

 		 byte[] buffer = new byte[1024];

 		 int length; 
 		 while ((length = inStream.read(buffer)) > 0){
 		    	outStream.write(buffer, 0, length);
 		    	}

 		 inStream.close();
 		 outStream.close();
// 		 
// 		FileWriter fw = new FileWriter(dest);
// 		BufferedWriter bw = new BufferedWriter(fw);
// 		
 		String firstLine="", nextline="", data;
 		StringBuilder putData=new StringBuilder();
//		System.out.println("The file in dest is hotters "+dest);
//		 
//
		Scanner scanner=new Scanner(dest);
		if(scanner.hasNextLine()){
        firstLine=scanner.nextLine();
 		System.out.println("this is the firstline "+firstLine);
		}
		
 		data = firstLine.replaceAll("com.blink", packageName);
 		System.out.println("this is the data Prashant "+data);
 		putData.append(data);
		System.out.println("this is the putData "+putData);
	
		while(scanner.hasNextLine()){
 			nextline=scanner.nextLine();
 			putData.append(nextline);
 			
 		}
		System.out.println("###################### ");
		System.out.println("this is the putData "+putData);
		System.out.println("###################### ");
		
 //		bw.write(putData.toString());
 	//	bw.flush();
 		//bw.close();
 		System.out.println("Closing Scanner...");
 	      scanner.close();
 	      System.out.println("Scanner Closed.");
 		
 		
		} catch (IOException e) {
 	  	      e.printStackTrace();
 	  	}


}

//public void creatActionForEntity()
}
