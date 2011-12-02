public class Tournament{
    Bimodal bp;
    GShare gs;
    SCArray tp;
    public Tournament(){
        bp = new Bimodal(1024);     // 2 ** 10, for 10 bits of pc
        gs = new GShare(4096, 2);   // 2 ** 12, for 12 bits of pc, and 6 of bhr        //CHANGE TO 0
        tp = new SCArray(4096, 2);
    }
    public int get(long pc, int bhr){
        //System.out.println("PC:" + pc);
        int val = tp.get((int)(pc % 4096)) / 2;
        
        if (val == 0){
            return bp.get(pc);
        }
        else{
            return gs.get(pc, bhr);
        }
    }
    public void train(long pc, int bhr, boolean taken, boolean correct){
    //System.out.println(pc + "is the value of error pc Tournament train");
        int val = tp.get((int)(pc % 4096)) / 2;
        
            if (taken){
                bp.increment(pc);
            }
            else{
                bp.decrement(pc);
            }
            gs.train(pc, bhr, taken);
        if ((val == 1 && correct) || (val == 0 && !correct)){
            long valx = (pc % 4096);
            tp.increment((int)valx);
        }
        else{
            long valx = (pc % 4096);
            tp.decrement((int)valx);
        }
    }
}
