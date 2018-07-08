import java.util.*;

public class ProcessManager {
	public int blocksize;
	public boolean block[];
	public String cFlag;
	public String var;
	public int freeSpaceleft;
	HashMap<String,Process> hs=new HashMap<String,Process>();
	
	ProcessManager(int blocksize)
	{
		this.blocksize=blocksize;
		block = new boolean[blocksize];
		cFlag="false";
		this.freeSpaceleft=blocksize;
	}
	
	public void allocate(String P,String var,int bSize,String cflag)
	{
		
		this.cFlag=cflag;
		if(bSize>blocksize/4 || bSize>=freeSpaceleft)
		{
			int al=blocksize-freeSpaceleft;
			System.out.println("error "+al+" "+freeSpaceleft);
		}
		
		else 
		{
			if(cFlag.equals("true"))
			{
				for(int i=0;i<blocksize;i++)
				{
					if(block[i]==false)
					{
						int j;
						int max=i+bSize;
						for(j=i;j<max;j++)
						{
							if(block[j]==true)
							{
								i=j;
								break;
							}
						}
						
						if(j==i+bSize)
						{
							if(hs.get(P)==null)
							{
								Process newP=new Process(P);
								newP=allocateUtil(newP,var,bSize,i,j);
								hs.put(P,newP);
								
								setFlag(block,i,j,true);
							}
							
							else
							{
								Process newP=hs.get(P);
								newP=allocateUtil(newP,var,bSize,i,j);
								setFlag(block,i,j,true);
								
							}
							freeSpaceleft=freeSpaceleft-bSize;
							int al=blocksize-freeSpaceleft;
							System.out.println("sucsess "+al+" "+freeSpaceleft);
							
							break;
						}
					}
				}
			}
			
			else 
			{
				int tbSize=bSize;
				for(int i=0;i<blocksize;i++)
				{
					if(block[i]==false)
					{
						int j=i;
						int max=i+bSize;
						while(j<max && block[j]==false)
							j++;
						
						if(j==max)
						{
							if(hs.get(P)==null)
							{
								Process newP=new Process(P);
								newP=allocateUtil(newP,var,bSize,i,j);
								hs.put(P,newP);
								
								setFlag(block,i,j,true);
							}
							
							else
							{
								Process newP=hs.get(P);
								newP=allocateUtil(newP,var,bSize,i,j);
								setFlag(block,i,j,true);
								
							}						
							break;
						}
						
						else {
								if(hs.get(P)==null)
								{
									int start=i;
									int end=j;
									Process newP=new Process(P);
									newP=allocateUtil(newP,var,bSize,start,end);
									hs.put(P,newP);
									
									setFlag(block,start,end,true);
								}
							
								else
								{
									Process newP=hs.get(P);
									newP=allocateUtil(newP,var,bSize,i,j);
									setFlag(block,i,j,true);
								
								}
								bSize=bSize-(j-i);
							}
						i=j;
					}
			}
				freeSpaceleft=freeSpaceleft-tbSize;
				int al=blocksize-freeSpaceleft;
				System.out.println("sucsess "+al+" "+freeSpaceleft);
		}
		}
	}
	
	public void free(String newP,String v)
	{
		int totalfreed=0;
		Process P=hs.get(newP);
		if(P==null)
		{
			System.out.println("Process doesn't exit");
		}
		
		else {
		Iterator<ProcessVar> it=P.vList.iterator();
		ProcessVar vRem=null;
		
		while(it.hasNext())
		{
			ProcessVar temp=it.next();
			if(temp.pName.equals(v))
			{
				totalfreed=endPointIterator(temp,totalfreed);
				freeSpaceleft+=totalfreed;
				int t=blocksize-freeSpaceleft;
				System.out.println("sucesss "+t+" "+freeSpaceleft);
				vRem=temp; 
			} 	
		} 
		P.removeVar(vRem);
		}
	}
	
	public void kill(String newP)
	{
		int totalfreed=0;
		Process P=hs.get(newP);
		Iterator<ProcessVar> it=P.vList.iterator();
		
		while(it.hasNext())
		{
			ProcessVar temp=it.next();
			totalfreed=endPointIterator(temp,totalfreed);
				freeSpaceleft+=totalfreed;
		}
		P.vList.clear();
		
		int t=blocksize-freeSpaceleft;
		System.out.println("sucesss "+t+" "+freeSpaceleft);
		hs.remove(newP); 
	}
	
	public void inspect(String newP) 
	{
		Process P=hs.get(newP);
		if(P==null)
		{
			System.out.println("error");
		}
		else
		{
			Iterator<ProcessVar> it=P.vList.iterator();
			
			while(it.hasNext())
			{
				ProcessVar temp=it.next();
					Iterator<Endpoint> eit=temp.eList.iterator();
					int start=0;
					int end=0;
					
					System.out.print(newP+" "+ temp.pName );
					while(eit.hasNext())
					{	
						Endpoint e=eit.next();
						start=e.getStart();
						end=e.getEnd();
						System.out.print(" "+start+" "+end);
					}
					System.out.println();
			}
		}
	}
	
	public Process allocateUtil(Process newP,String pVar,int bSize,int start,int end)
	{
		ProcessVar varP=new ProcessVar(pVar);
		Endpoint ep=new Endpoint();
		ep.setStart(start);
		ep.setEnd(end);
		varP.addEp(ep);
		newP.addVar(varP);
		
		return newP;
	}
	
	public void setFlag(boolean block[],int start, int end, boolean flag)
	{
		for(int i=start;i<end;i++)
		{
			block[i]=flag;
		}
	}
	
	public int endPointIterator(ProcessVar V,int totalfreed)
	{
		Iterator<Endpoint> eit=V.eList.iterator();
		int start=0;
		int end=0;
		while(eit.hasNext())
		{	
			Endpoint e=eit.next();
			start=e.getStart();
			end=e.getEnd();
			setFlag(block,start,end,false);
			totalfreed+=end-start;
		}
		return totalfreed;
	}
}
