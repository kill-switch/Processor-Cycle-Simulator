public class SatCounter{
    int val;
    public SatCounter(int v){
        if (v > 3){
            val = 3;
        }
        else if (v < 0){
            val = 0;
        }
        else{
            val = v;
        }
    }
    public void increment(){
        if (val != 3){
            val++;
        }
    }
    public void decrement(){
        if (val != 0){
            val--;
        }
    }
    public int get(){
        return val;
    }
}