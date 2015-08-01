package com.kingdee.eas.fdc.basedata;

import org.jgroups.tests.MergeTest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;

public abstract class AbstractSplitImporter implements ISplitImporter {

	public abstract IObjectValue importSplit() throws BOSException,EASBizException;

	protected abstract String getContractBillId();
	
	private IObjectValue mergeObjectValue(IObjectValue info1,IObjectValue info2){
		IObjectCollection c1=(IObjectCollection)info1.get("entrys");
		IObjectCollection c2=(IObjectCollection)info2.get("entrys");
		if(c1==null){
			return info2;
		}
		if(c2==null){
			return info1;
		}
		
		c1.addObjectCollection(c2);
		return info1;
	}
	
	protected void setEntrySelector(SelectorItemCollection selector,boolean isEntry){
		FDCCostSplit.setSelectorsEntry(selector, isEntry);
	}
}
