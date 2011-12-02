import java.io.*;

class L2Cache{
    L2Set[] l2set;

    public L2Cache(){
        l2set = new L2Set[2048];
        for (int i = 0; i < 2048; i++){
            l2set[i] = new L2Set();
        }
    }
    
    public int read(long address){
       long index2 = (address / 128) % 2048; 
        int index = (int )index2;
        long tag = address / (128 * 2048);
        L2Record[] s = l2set[index].get();
        for (int i = 0; i < 8; i++){
            if (s[i].valid() == 1 && s[i].tag() == tag){
                l2set[index].setLRU(i);
                return 10;
            }
        }
        // Cache Miss
        long evict_address = l2set[index].set(address);      // L1Set.set is for writing data from Mem to L2
        if (evict_address != -1){
        }
        return 200;
    }
    public int write(long address){
       	long index2 = (address / 128) % 2048; 
        int index = (int )index2;
        long tag = address / (128 * 2048);
		L2Record[] s = l2set[index].get();
        for (int i = 0; i < 8; i++){
            if (s[i].valid() == 1 && s[i].tag() == tag){
                l2set[index].setLRU(i);
                s[i].modified(1);
                return 10;
            }
        }
        long evict_address = l2set[index].write(address);    // L1Set.write is for writing data to L2, and will return -1 if it encounters an invalid record, so that there is no need of an eviction.
        if (evict_address != -1)
        {
        
        }
        return 200;
    }
    /*
   public void evectingThis(int address)
    {
    	int index = (address / 32) %(16 * 1024);
    	int tag = address / (128 * 2048); 
    	
    	l2set[index].evectThis(address);
    }
*/
    
}


class L2Set
{
	L2Record[] l2record;
	
	public L2Set()
	{
		l2record = new L2Record[8];
		for(int i= 0;i <8;i++)
		{
			l2record[i] = new L2Record();
		}
	}
	
	public L2Record[] get()
	{
		return l2record;
	}
	
	public long set(long address)
	{
		long evect_address;
		int min;
		int i;
		int evtrank = 1;
		
		for(i=0; i< 8;i++)
		{
			if(l2record[i].valid() == 0)
			{
				l2record[i].set(address);
				setLRU(i);
				return -1;
			}	
		}
		
		
		for(i =0;i<8;i++)
		{
			if(l2record[i].getrank()==1)
			{
				evect_address = l2record[i].getaddress();
				if(l2record[i].getmodified() == 0)
				{
					evect_address = -1;
				}
				l2record[i].set(address);
				setLRU(i);
				return evect_address;
			}
		}
		return -1;
	}	
		
	
	public long write(long address)
	{
		long evect_address;
		int min;
		int i;
		for(i=0; i< 8;i++)
		{
			if(l2record[i].valid() == 0)
			{
				l2record[i].set(address);
				l2record[i].modified(1);
				setLRU(i);
				return -1;
			}	
		}
		for(i =0;i<8;i++)
		{
			if(l2record[i].getrank()==1)
			{
				evect_address = l2record[i].getaddress();
				if(l2record[i].getmodified() == 0)
				{
					evect_address = -1;
				}
				l2record[i].set(address);
				l2record[i].modified(1);
				setLRU(i);
				return evect_address;
			}
		}
		return -1;
	}	
	
	public void setLRU(int isMost)
	{
		int i;
		int noofvalid =8; 
		for(i = 0;i<8;i++)
		{
			if(l2record[i].valid() == 0)
			{
				noofvalid--;
			}
			else
			{
				if(l2record[i].getrank() > l2record[isMost].getrank() && l2record[isMost].getrank() != 0)
				{
					l2record[i].rank(l2record[i].getrank() - 1);
				}
			}
		}
		if(noofvalid != 8)
		{
			l2record[isMost].rank(noofvalid );
		}
		else
		{
			l2record[isMost].rank(8);
		}
	}
	
	public void L2SetPrint()
	{
		l2record[0].printData();
		l2record[1].printData();
		l2record[2].printData();
		l2record[3].printData();
		l2record[4].printData();
		l2record[5].printData();
		l2record[6].printData();
		l2record[7].printData();
	}
}

class L2Record
{
	long address;
	int isValid;
	int isModified;
	int rank;
	//int isInCache;
	
	public L2Record()
	{
		address =0;
		isValid =0;
		isModified =0;
		rank =0;
		//isInCache =0;
	}
	
	public long getaddress()
	{
		return address;
	}
	
	public void rank(int rank)
	{
		this.rank = rank;
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
		return  address / (128 * 2048);			
	}
	public void printData()
	{
		//System.out.println(address + " " + isValid + " "+ isModified+ " " + rank + "\n");
	}
/*	public int isInCache()
	{
		return isInCache;
	}
	public void setIsInCache(int isInCache)
	{
		this.isInCache = isInCache;
	}*/
}
