import java.io.*;
import java.util.*;

public class Main
{
	public static void main(String[] args)
	{   long timeinit=System.currentTimeMillis();
	    int completeCycles=0;
		//Instruction[] CompInstrArray = new Instruction[20000000];
		//CompInstrArray = ReadFile.readTraceFile("dynamic_trace_5000.txt","static_trace.txt");
		Pipeline myPipeline = new Pipeline();
		
		String strLine=null;
        String temp=null;
        int i=0;
        StringTokenizer st;
        Element[] instString= new Element[2000];
        
        try{
                  FileInputStream fstream = new FileInputStream(args[0]);
                  DataInputStream in = new DataInputStream(fstream);
                  BufferedReader br = new BufferedReader(new InputStreamReader(in));
                  
                  while ((strLine = br.readLine()) != null){
                      st = new StringTokenizer(strLine);
        	          String instrTemp;
        	          instrTemp = strLine;
        	          long pcTemp = Hexconvert.hexToInt(st.nextToken());
        	          instString[(int)pcTemp] = new Element();
        	          instString[(int)pcTemp].instruction=instrTemp;
        	          instString[(int)pcTemp].pc=pcTemp;
        	          i++;
                  }//System.out.println(i);
                  in.close();
         }
        catch (Exception e){
              //System.out.println("Error in the Instruction File");
        }
        
        //String strLine=null;
        //String temp=null;
        //Instruction[] traceArray = new Instruction[20000000];
        //int i=0;
        //int traceInstruction;
        
        i=0;
        
        Instruction noope=new Instruction();
	    noope.opcode = -1;
	    noope.address = 0;
	    noope.rd = -2;
	    noope.rs = -1;
	    noope.rt = -1;
	    noope.pc = 0;
	    noope.prediction = -1;

        try{    
                  FileInputStream fstream = new FileInputStream(args[1]);
                  DataInputStream in = new DataInputStream(fstream);
                  BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
		          i=0;
		        
		        while ((strLine = br.readLine()) != null){
                    Instruction nextInstruction = new Instruction(strLine);
                    //long initval=instString[0].pc;          	 Not Sure
		            int indexArray=(int)(nextInstruction.pc);
                    nextInstruction.fillInstruction(instString[indexArray].instruction);
            	    completeCycles=completeCycles+myPipeline.insertToPipeline(nextInstruction);
                    i++;
                  }//System.out.println(i);		        
		        in.close();
	
	   }
       catch (Exception e){
              System.out.println("Couldnt Decode the Trace file\n");
       }
	    
		for(int j=0; j<5; j++)
		{   
            completeCycles=completeCycles+myPipeline.insertToPipeline(noope);
	    
		}
		
		//System.out.println("The Final Answer is = " + completeCycles);
		float L1MissRate=myPipeline.L1Miss/(float)myPipeline.L1Total;
		float L2MissRate=myPipeline.L2Miss/(float)myPipeline.L2Total;
		float predictionRate=(float)myPipeline.CorrectPrediction/myPipeline.TotalPrediction;
		//System.out.println(i);
		float IPC = (float)i/completeCycles;
		//System.out.println("L1 Miss Rate = " + L1MissRate);
		//System.out.println("L2 Miss Rate = " + L2MissRate);
		//System.out.println("Prediction Rate = " + predictionRate);
		//System.out.println("IPC = " + IPC);
		System.out.println (IPC + " "+ L1MissRate*100 +"% "+ L2MissRate*100 +"% "+ predictionRate*100 + "% ");
		System.out.println(System.currentTimeMillis()-timeinit);
	}
}	
	
class Element{
    String instruction;
    long pc;
}	
