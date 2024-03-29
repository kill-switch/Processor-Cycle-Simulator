public class Pipeline{
    Instruction[] IArray;
    int clockcycles=0;
    int prediction;
    L1Cache L1 = new L1Cache();    
    Tournament tp = new Tournament();
    int bhr1=0;
    int bhr2=0;
    int bhr3=0;
    long L1Total=0;
    long L1Miss=0;
    long L2Total=0;
    long L2Miss=0;
    long TotalPrediction=0;
    long CorrectPrediction=0;
    
    
    public Pipeline(){
        Instruction noope=new Instruction();
	    noope.opcode = -1;
	    noope.address = 0;
	    noope.rd = -2;
	    noope.rs = -1;
	    noope.rt = -1;
	    noope.pc = 0;
	    noope.prediction = -1; 
	    
        IArray=new Instruction[5];
        for(int i=0; i<5; i++){
            IArray[i] = noope;
        }   
    }
    
    public int insertToPipeline(Instruction nextInstruction){
        int clockcycles=0;
        boolean correct = true;
        if(IArray[3].opcode!=-1)
        {
            clockcycles=1;
            //System.out.println("I am adding now");
            if(IArray[3].opcode==0){
                L1Total++;
                clockcycles=L1.read((IArray[3].address));
                if(clockcycles>9)
                {
                    L1Miss++;
                    L2Total++;
                    if(clockcycles>150)
                    {
                        L2Miss++;
                    }
                }
                //System.out.println("opcode=0");
            }
            if(IArray[3].opcode==1){
                clockcycles=L1.write(IArray[3].address);
                //System.out.println("opcode=1");
            }
            if(IArray[3].opcode==0 && (IArray[3].rd==IArray[2].rs || IArray[3].rd==IArray[2].rt)){
                clockcycles++;
                //System.out.println("opcode=0 and something is going on");
            }
            if(IArray[0].opcode==3){
                TotalPrediction++;
                //System.out.println("The error is about to come");
                prediction=(tp.get((IArray[0].pc),bhr1))/2;
                IArray[0].myprediction = prediction;
                if(prediction!=IArray[0].prediction){
                    clockcycles++;
                    correct=false;
                }
                else
                {
                    CorrectPrediction++;
                }
                IArray[0].correct = correct;  
            }
            
            if (IArray[2].opcode==3){
            tp.train((IArray[2].pc), IArray[2].bhr, IArray[2].prediction != 0, IArray[2].correct);
            }
            
            IArray[2].bhr = (2*IArray[2].bhr + IArray[2].prediction)%64;
            
        }
        
        ////System.out.println("Calculated cycles are=" + clockcycles);
        
        for(int i = 4 ; i > 0 ; i--){
            IArray[i]=IArray[i-1];
        }
        
        IArray[0]= nextInstruction;
        return clockcycles; 
        
    }
}
