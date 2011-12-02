public class GShare{
    SCArray bp;
    public GShare(int size, int initVal){
        bp = new SCArray(size, initVal);
    }
    public int get(long pc, int bhr){
        //System.out.println(pc + "is the Gshare Get value of error pc");
        long index1 = ((pc % 4096)) ^ (bhr % 64);
        int index= (int)index1;
        return bp.get(index);
    }
    public void train(long pc, int bhr, boolean taken){
        //System.out.println(pc + "is the Gshare train entry value of error pc");
        //System.out.println("the value of BHR is:" + bhr);
        long index1 = ((pc % 4096)) ^ (bhr % 64);
        int index=(int) index1;
        if (taken){
            bp.increment(index);
        }
        else{
        //System.out.println(pc + "is the value of error Gshare train else case pc" + index + "  " +index1);
            bp.decrement(index);
        }
    }
}

// BHR used is 3 instructions old
