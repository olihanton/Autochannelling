/*This class control all other classes. It is this class which should be called to create STL files corresponding to a
channel and a channeled object */

import java.util.*;
import java.io.*;
import java.lang.*;
import java.lang.Math.*;


public class Control{

	//this is the main function to do the program's work and to produce a channel base and object base...
	void producecnnlobj(int cbx, int cby, String inputname, int nochnnls, double tpx, double tpy, double tpz){
		File filex = new File();
		Item itex = new Item();
		Item flatlayer = new Item();
		Autochannel ac = new Autochannel();
		int nchannelsides = 16;
		itex=filex.filetoitem(inputname, itex);
		Item objwalls = ac.extractobjwalls(itex, tpz);
		Item cnnlitem = new Item();
		Item finalobj = new Item();
		double tpz2 = 3; 		//height of contact point disk
		double rad1 = 1.5; 		//radius of touchpoint for channel
		double rad2 = 5; 		//radius of contactpoint for channels
		for(int i=0; i<nochnnls; i++){
			Base chnbase = ac.createchannelbase(ac.findcontactpoint().get(0), nchannelsides);
			Ceiling chnceil = ac.createchannelceiling(chnbase, tpx, tpy, tpz, nchannelsides, rad1);
			Ceiling midringsmall = ac.createchannelceiling(chnbase, ac.findcontactpoint().get(0).getX(), ac.findcontactpoint().get(0).getY(), tpz2, nchannelsides, rad1);
			Ceiling midringbig = ac.createchannelceiling(chnbase, ac.findcontactpoint().get(0).getX(), ac.findcontactpoint().get(0).getY(), tpz2, nchannelsides, rad2);
			cnnlitem=ac.combine2items(cnnlitem, ac.createchannels(chnbase, midringbig, midringsmall, chnceil, tpx, tpy, tpz, nchannelsides, 1));
			Base objbs=ac.recreateobjectbase(nchannelsides, itex, inputname);
			Ceiling objceil = ac.createobjectceiling(objbs, tpz); 
			Item cnnlparts = ac.createchannels(chnbase, midringbig, midringsmall, chnceil, tpx, tpy, tpz, nchannelsides, 0);
			finalobj=ac.combine2items(finalobj, ac.createobject(objbs, objceil, chnbase, chnceil, cnnlparts, objwalls, tpx, tpy, tpz, nchannelsides));
		}			
		filex.itemtofile(inputname + "channels", cnnlitem);
		filex.itemtofile(inputname + "object", finalobj);
	}

		/*//user interactive version - more choice over otherwise hardcoded variables
		//(maybe change so that touch points are uploaded from a text file.):
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the name of your object's STL file: ");
		String inputname = scanner.next(); // or scanner.nextInt(); to have it as an int...
		System.out.print("Enter how many channels you would like to have in your object: ");
		int channelno = scanner.nextInt();
		for(int i=0; i<channelno; i++){
			System.out.print("Enter x coordinate of your " + i + "th channel: ");
			int tpx = scanner.nextInt();
			System.out.print("Enter x coordinate of your first channel: ");
			int tpy = scanner.nextInt();
			System.out.print("Enter x coordinate of your first channel: ");
			int tpz = scanner.nextInt();
			//store these values for when channelno>1. store as an array list and adapt producecnnlobj to accept an array
			// list for the touch points...
		}
		ctrl.producecnnlobj(inputname, channelno, tpx, tpy, tpz);*/

//potential testing function for unit testing:
	void test(){
		System.out.println("testing in progress");
    }

    public static void main(String[] args) {
      	boolean testing = false;
        assert(testing = true);
        Control ctrl = new Control();
        if(testing){
        	ctrl.test();
      	}
      	else{
      		ctrl.producecnnlobj(15, 15, "samplecube", 1, 15, 15, 30);
      	}
   	}

}
