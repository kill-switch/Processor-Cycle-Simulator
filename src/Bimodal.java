public class Bimodal extends SCArray{
    public Bimodal(int size){
        super(size, 2);
    }
    public void increment(long pc){
        super.increment((int)(pc % 1024));
    }
    public void decrement(long pc){
        super.decrement((int)(pc % 1024));
    }
    public int get(long pc){
        return super.get((int)(pc % 1024));
    }
}
