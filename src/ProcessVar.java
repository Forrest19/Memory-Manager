import java.util.LinkedList;
import java.util.List;

public class ProcessVar {
	public String pName;
	public List<Endpoint> eList;
	
	ProcessVar(String pName)
	{
		this.pName=pName;
		eList= new LinkedList<Endpoint>();

	}
 
	public void addEp(Endpoint v)
	{
		eList.add(v);
	}
	
	public void removeEp(Endpoint v)
	{
		eList.remove(v);
	}
	
	
}
