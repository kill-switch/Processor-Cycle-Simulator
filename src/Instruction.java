import java.lang.*;
import java.util.*;

public class Instruction{
	int opcode;
    long address;
	int rd;
	int rs;
	int rt;
	long pc;
	int prediction;
	int bhr;
	boolean correct;
	int myprediction;
	
	public Instruction(){
		opcode = -1;
		address = 0;
		rd = -2;
		rs = -1;
		rt = -1;
		pc = 0;
		prediction = -1;
	}
	
	public Instruction(String Trace)
	{
		StringTokenizer st = new StringTokenizer(Trace);
		pc =  Hexconvert.hexToInt(st.nextToken());
		address = Hexconvert.hexToInt(st.nextToken());
		prediction = Integer.parseInt(st.nextToken());
	}
	
	public void fillTrace(String Trace)
	{
		StringTokenizer st = new StringTokenizer(Trace);
		pc =  Hexconvert.hexToInt(st.nextToken());
		address = Hexconvert.hexToInt(st.nextToken());
		prediction = Integer.parseInt(st.nextToken());
	}
	
	public int fillInstruction(String Instruct)
	{
		StringTokenizer st = new StringTokenizer(Instruct);
		long isFirst = Hexconvert.hexToInt(st.nextToken());
		if(isFirst != pc)
		{
			return 0;
		}
		
		opcode = Integer.parseInt(st.nextToken());
		rs = Integer.parseInt(st.nextToken());
		if(opcode == 0)
		{
			//load instruction
			rt = Integer.parseInt(st.nextToken());
			rd = Integer.parseInt(st.nextToken());
		
		}
		if(opcode == 1)
		{
			rt = Integer.parseInt(st.nextToken());
			rd = Integer.parseInt(st.nextToken());
		}
		if(opcode == 2)
		{
			rt = Integer.parseInt(st.nextToken());
			rd = Integer.parseInt(st.nextToken());
		}
		if(opcode == 3)
		{
			rd = -2;
			rt = -1;
		}
		return 1;
	}
}





class Hexconvert
{
	public static long hexToInt(String hex2)
	{
		int i = 0;
		long result =0;		
		char[] hex = hex2.toCharArray();
		for(i = 2;i<hex2.length();i++)
		{
			result = result*16;
			if(hex[i] < 58)
			{
				result = result+(hex[i] - 48);
			}
			else
			{
				result = result + (10 + hex[i] - 97);
			}
		}
		return result;
	}
}
