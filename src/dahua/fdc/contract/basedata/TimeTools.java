package com.kingdee.eas.fdc.basedata;

import java.io.OutputStream;
import java.io.PrintStream;

import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;

public class TimeTools {
	private long baseTime = 0;
	private long lastTime = 0;
    private PrintStream ps=null;
    private boolean debug=false;
	public TimeTools()
	{
		String p = System.getProperty("DevFDC");
		if(p!=null&&p.equals("fdc")){
			ps=System.out;
			this.debug=true;
		}
//		this.debug=false;//½ûÓÃ
		reset();
	}
	private static class TimeToolsHolder{
		private static final TimeTools singeTimer=new TimeTools(); 
	}
	public static TimeTools getInstance(){
		return TimeToolsHolder.singeTimer;
		
	}
	public void setOutPutStream(OutputStream out){
		this.ps=new PrintStream(out);
	}
	public void setDebug(boolean debug){
		this.debug=debug;
		if(ps==null){
			ps=System.out;
		}
	}
	
    public void reset()
	{
		baseTime = System.currentTimeMillis();
		lastTime=baseTime;
	}
	
    public long msValue()
	{
    	lastTime=System.currentTimeMillis();
		return lastTime-baseTime;
	}
	
    public long difMsValue()
	{
		return System.currentTimeMillis()-lastTime;
	}
    
    public float difValue()
	{
		return difMsValue()/1000.0f;
	}
    public float Value()
	{
		return msValue()/1000.0f;
	}
    
    public void valuePrintln(String desc){
    	float difTime=difValue();
    	if(desc==null) desc="Time";
    	else desc=desc+" Time:";
    	if(debug){
    		ps.println(desc+Value()+"\t use time: "+difTime);
    	}
    }
    public void valuePrintln(){
    	valuePrintln(null);
    }
    public void msValuePrintln(String desc){
    	long difTime=difMsValue();
    	if(desc==null) desc="Time";
    	else desc=desc+" Time:";
    	if(debug){
    		ps.println(desc+msValue()+"\t use time: "+difTime);
    	}
    }
    public void msValuePrintln(){
    	msValuePrintln(null);
    }
}
