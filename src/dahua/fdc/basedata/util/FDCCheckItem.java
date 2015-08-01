package com.kingdee.eas.fdc.basedata.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListener;

public interface FDCCheckItem extends EventListener{
	public abstract void check(FDCCheckEvent event);  
}
