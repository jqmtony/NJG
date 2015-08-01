package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;

import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractBaseDataInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.basedata.master.auxacct.app.AssistUtil;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.contract.ContractBaseDataCollection;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.eas.framework.util.FilterUtility;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;

public class ContractBaseDataControllerBean extends AbstractContractBaseDataControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractBaseDataControllerBean");

    
    protected void _update(Context ctx , IObjectPK pk , IObjectValue model) throws  BOSException , EASBizException
	{
	    super._update(ctx , pk , model);
	    
	    //
	    AssistUtil.updateAssist(ctx,pk.toString(),((ContractBaseDataInfo)model).getBOSType());
	}
    protected void _checkNumberDup(Context ctx , IObjectValue model) throws
    BOSException , EASBizException
    {
    	if(model==null){
    		return;
    	}
    	ContractBaseDataInfo billInfo=(ContractBaseDataInfo)model;
    	
    	if(billInfo.getNumber()==null){
    		throw new ContractException(ContractException.CHECKNUMBLANK);
    	}
    	
    	//重载进行判断，公司内不唯一，注意区分无文本合同
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("number", billInfo.getNumber()));
		filter.getFilterItems().add(
				new FilterItemInfo("isNoText", Boolean.valueOf(billInfo.isIsNoText())));
		if(billInfo.getCompany()!=null&&billInfo.getCompany().getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("company.id", billInfo.getCompany().getId().toString()));
		}else if(billInfo.getCU()!=null&&billInfo.getCU().getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("cu.id", billInfo.getCU().getId().toString()));
		}
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}
    }
    
    protected IObjectPK[] _delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException {
    	SorterItemCollection sorter=new SorterItemCollection();
    	sorter.add(new SorterItemInfo("id"));
    	IObjectPK[] list = getPKList(ctx, filter, sorter);
    	this._delete(ctx,list);
    	return list;
    }
    
    /* 
     * 
     * (non-Javadoc)
     * @see com.kingdee.eas.framework.app.AbstractCoreBaseControllerBean#_delete(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectPK[])
     */
    protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
    	for(int i=0;i<arrayPK.length;i++){
    		this.isReferenced(ctx, arrayPK[i]);
    	}
    	super._delete(ctx, arrayPK);
    }
}