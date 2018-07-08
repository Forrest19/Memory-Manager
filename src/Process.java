import java.util.*;

public class Process {
	public String pId;
	public List<ProcessVar> vList;
	
	Process(String pId)
	{
		this.pId=pId;
		vList= new LinkedList<ProcessVar>();
	}
	
	public void addVar(ProcessVar v)
	{
		vList.add(v);
	}
	
	public void removeVar(ProcessVar v)
	{
		vList.remove(v);
	}
	
	public ProcessVar getVar(String vName)
	{
		Iterator<ProcessVar> it=vList.iterator();
		while(it.hasNext())
		{
			ProcessVar pV=it.next();
			if(pV.pName.equals(vName))
			{
				return pV;
			}
		}
		return null;
	}

}
