import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Client 
{
  public static void main(String[] args)throws Exception
  {
	  File file = new File("C:\\Users\\rharshva.ORADEV\\Desktop\\Test.txt");
	 
	  BufferedReader br = new BufferedReader(new FileReader(file));
	  String s=null;
	  ProcessManager pmanager=null;
	  boolean firstLine=true;
	  while ((s = br.readLine()) != null)
	  {
		String [] st = s.split("\\s+");
		if(firstLine)
		{
			int blockSize=Integer.parseInt(st[0]);
			pmanager=new ProcessManager(blockSize);
			firstLine=false;
		}
		
		else
		{
			if(st[0].equals("allocate"))
			{	
				int bSize=Integer.parseInt(st[3]);
				pmanager.allocate(st[1],st[2],bSize,st[4]);
			}
	  	
			else if(st[0].equals("free"))
			{
				 pmanager.free(st[1],st[2]);
			}
	  	
			else if(st[0].equals("kill"))
			{
				pmanager.kill(st[1]);
			}
	  	
			else
			{
				pmanager.inspect(st[1]);
			}
		}
	  }
  }
}
