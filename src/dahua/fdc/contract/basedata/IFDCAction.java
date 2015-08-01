package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;

public interface IFDCAction extends Serializable{
	Object actionPerformed(Context ctx) throws Exception;
}
