public class SCArray{
    SatCounter[] bp;
    
    public SCArray(int size, int initVal){
        bp = new SatCounter[size];
        for (int i = 0; i < bp.length; i++){
            bp[i] = new SatCounter(initVal);
        }
    }
    
    public void increment(int index){
        bp[index].increment();
    }
    public void decrement(int index){
        bp[index].decrement();
    }
    public int get(long index1){
        int index=(int)index1;
        return bp[index].get();
    }
}
