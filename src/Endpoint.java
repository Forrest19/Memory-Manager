public class Endpoint {
	private int pIdStart;
	private int pIdEnd;
	
	Endpoint()
	{
		pIdStart=0;
		pIdEnd=0;
	}
	
	public void setStart(int start)
	{
		this.pIdStart=start;
	}
	
	public int getStart() 
	{
		return pIdStart;
	}
	
	
	public void setEnd(int end)
	{
		this.pIdEnd=end;
	}
	
	public int getEnd() 
	{
		return pIdEnd;
	}

}
