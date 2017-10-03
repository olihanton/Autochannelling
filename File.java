//this class is in charge of reading and writing to and from an STL file.

import java.util.*;
import java.io.*;

class File extends Item{

//this method writes a file from scratch using an item stored in the program
    void itemtofile(String filename, Item itm){
      	int n, i;
      	double[] tempnorm = new double[3];
      	double[] tempa = new double[3];
      	double[] tempb = new double[3];
      	double[] tempc = new double[3];
      	n = (itm.numberoftriangles());
      	try{
         	PrintWriter out = new PrintWriter(filename + ".stl");
         	out.print("solid " + filename + "\n"); //n.b. this is different to an stl which is named after its parent file
         	for(i=0; i<n; i++){
            	tempnorm = itm.returntri(i).pick3values("normal");
            	tempa = itm.returntri(i).pick3values("a");
            	tempb = itm.returntri(i).pick3values("b");
            	tempc = itm.returntri(i).pick3values("c");
            	out.print("facet normal " + tempnorm[0] + " " + tempnorm[1] + " " + tempnorm[2] + "\n");
            	out.print("  outer loop\n");
            	out.print("    vertex " + tempa[0] + " " + tempa[1] + " " + tempa[2] + "\n");
            	out.print("    vertex " + tempb[0] + " " + tempb[1] + " " + tempb[2] + "\n");
            	out.print("    vertex " + tempc[0] + " " + tempc[1] + " " + tempc[2] + "\n");
            	out.print("  endloop\n");
            	out.print("endfacet\n");
         	}
         	out.print("endsolid " + filename + "\n");
         	out.close();
        }
        catch (FileNotFoundException ex){
            System.err.println("Caught IOException:");
        }
    }

//this method translates an stl file into an array list that we can then work with
    Item filetoitem(String filename, Item itm){
        int i=0; //i indicates normal, avertex, bvertex or cvertex. j indicates number between 1 and 3
        filename=filename+".stl";
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = new String();
            Triangle trex = new Triangle();
            while ((line = reader.readLine()) != null){
            	line=line.trim();
            	String[] words = line.split("\\s+");
         	    if(words[0].equals("facet")){
         	    	trex = new Triangle();
         	    	i=1;
         	    	double first = Double.parseDouble(words[2]);
         	   		double second = Double.parseDouble(words[3]);
         	    	double third = Double.parseDouble(words[4]);
         	    	trex.inputvalue((i-1), 0, first);
         	    	trex.inputvalue((i-1), 1, second);
         	    	trex.inputvalue((i-1), 2, third);
         	    }
         	    if(words[0].equals("vertex")){
         	    	i++;
         	    	double first = Double.parseDouble(words[1]);
         	    	double second = Double.parseDouble(words[2]);
         	    	double third = Double.parseDouble(words[3]);
         	    	trex.inputvalue((i-1), 0, first);
         	    	trex.inputvalue((i-1), 1, second);
         	    	trex.inputvalue((i-1), 2, third);
         	    	if(i==4){
         	    		itm.insert(trex);
         	    	}
         	    }
         	}        	
        	i=0;
         	reader.close();
        }
        catch (Exception e){
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
        return(itm);
    }

   	public static void main(String[] args){

   	}

}