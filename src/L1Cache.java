import java.io.*;
class L1Cache{
    L1Set[] l1set;
    L2Cache l2;
    
    public L1Cache(){
        l2 = new L2Cache();
        l1set = new L1Set[512];
        for (int i = 0; i < 512; i++){
            l1set[i] = new L1Set();
        }
    }
    
    public int read(long address){
    	long index2 = (address / 32) % 512; 
        int index = (int )index2;
        long tag = address / (32 * 512);
        L1Record[] s = l1set[index].get();
        if ((s[0].valid() == 1 && s[0].tag() == tag ) || (s[1].valid() == 1 && s[1].tag() == tag)){
                return 1;
        }
        
        long evict_address = l1set[index].set(address);// L1Set.set is for writing data from L2 to L1 implement fifo return -1 if either is invalid or not modified
        if (evict_address != -1){
            l2.write(evict_address);
        }
        return l2.read(address);
    }
    
    public int write(long address){
        long index2 = (address / 32) % 512; 
        int index = (int )index2;
        long tag = address / (32 * 512);
        L1Record[] s = l1set[index].get();
        if ((s[0].valid() == 1 && s[0].tag() == tag )){
            s[0].modified(1);
            return 1;
        }
        if (s[1].valid() == 1 && s[1].tag() == tag){
            s[1].modified(1);
            return 1;
        }
        long evict_address = l1set[index].write(address);    // L1Set.write is for writing data to L1, and will return -1 if it encounters an invalid record, so that there is no need of an eviction.
        if (evict_address != -1){
            l2.write(evict_address);
        }
        return l2.read(address);
    }
}

class L1Set
{
	L1Record[] l1record;
	public  L1Set()
	{
		l1record = new L1Record[2];
		l1record[0] = new L1Record();
		l1record[1] = new L1Record(); 
	}
	
	public L1Record[] get()
	{
		return l1record;
	}
	
	// set return -1 
	public long set(long address)
	{
		long evect_address;
		int min;
		if(l1record[0].valid() == 0 && l1record[1].valid() == 0)
		{
			l1record[0].set(address);
			l1record[0].rank(1);
			return -1;	
		}
		if(l1record[0].valid() == 0)
		{
			l1record[0].set(address);
			l1record[0].rank(2);
			return -1;
		}
		if(l1record[1].valid()== 0)
		{
			l1record[1].set(address);
			l1record[1].rank(2);
			return -1;		
		}
		else
		{
			// both are 
			if(l1record[0].getrank() < l1record[1].getrank())
			{
				min =0;
			}
			else
			{
				min =1;
			}
			evect_address = l1record[min].getaddress();
			if(l1record[min].getmodified() == 0)
			{
				evect_address = -1;
			}
			l1record[min].set(address);
			l1record[min].rank(2);
			l1record[1-min].rank(1);
			return evect_address;
		}
	}	
		
	
	public long write(long address)
	{
		long evect_address;
		int min;
		if(l1record[0].valid() == 0 && l1record[1].valid() == 0)
		{
			l1record[0].set(address);
			l1record[0].rank(1);
			l1record[0].modified(1);
			return -1;	
		}
		if(l1record[0].valid() == 0)
		{
			l1record[0].set(address);
			l1record[0].rank(2);
			l1record[0].modified(1);
			return -1;
		}
		if(l1record[1].valid()== 0)
		{
			l1record[1].set(address);
			l1record[1].rank(2);
			l1record[1].modified(1);
			return -1;		
		}
		else
		{
			// both are 
			if(l1record[0].getrank() < l1record[1].getrank())
			{
				min =0;
			}
			else
			{
				min =1;
			}
			evect_address = l1record[min].getaddress();
			if(l1record[min].getmodified() == 0)
			{
				evect_address = -1;
			}
			l1record[min].set(address);
			l1record[min].rank(2);
			l1record[min].modified(1);
			l1record[1-min].rank(1);
			return evect_address;
		}	
	}
	
	public void L1SetPrint()
	{
		l1record[0].printData();
		l1record[1].printData();
	}
}

class L1Record
{
	long address;
	int isValid;
	int isModified;
	int rank;
	
	public L1Record()
	{
		address =0;
		isValid =0;
		isModified =0;
	}
	
	public void rank(int rank)
	{
		this.rank = rank;
	}
	
	public long getaddress()
	{
		return address;
	}
	
	public void set(long address)
	{
		this.address = address;
		isValid =1;	
	}
	
	public void modified(int isModified)
	{
		this.isModified = isModified;
	}
	
	public int getmodified()
	{
		return this.isModified;
	}
	
	public int getrank()
	{
		return rank;
	}
	
	public int valid()	
	{
		return isValid;
	}
	public long tag()
	{
		return address / (32 * 512);			
	}
	public void printData()
	{
		//System.out.println(address + " " + isValid + " "+ isModified+ " " + rank + "\n");
	}
}
