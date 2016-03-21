package com.kingdee.eas.fdc.gcftbiaoa.app;

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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.gcftbiaoa.IndoorengFactory;
import com.kingdee.eas.fdc.gcftbiaoa.IndoorengInfo;
import com.kingdee.eas.fdc.gcftbiaoa.IndoorengCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.fdc.gcftbiaoa.IndoorengInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class IndoorengControllerBean extends AbstractIndoorengControllerBean
{
    private static Logger logger =Logger.getLogger("com.kingdee.eas.fdc.gcftbiaoa.app.IndoorengControllerBean");
    
	protected void _actionAudit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		IndoorengInfo IndoorengInfo =  (IndoorengInfo)model;
    	if(IndoorengInfo.getState().equals(FDCBillStateEnum.AUDITTED))
    		throw new EASBizException(new NumericExceptionSubItem("100","已审核，不能再审核"));

    	IndoorengInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
    	IndoorengInfo.setState(FDCBillStateEnum.AUDITTED);
    	IndoorengInfo.setLasted(true);
    	IndoorengInfo.setAuditTime(SysUtil.getAppServerTime(ctx));

    	DbUtil.execute(ctx, "update CT_001_Indooreng set CFLasted=0 where CFProjectNameID='"+IndoorengInfo.getProjectName().getId()+"'and CFRoomID='"+IndoorengInfo.getRoom().getId()+"'");
    	_update(ctx, new ObjectUuidPK(IndoorengInfo.getId()), IndoorengInfo);	//更新数据
	}


	protected void _actionUnAudit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("lasted");
    	sic.add("Version");
    	sic.add("ProjectName.id");
    	sic.add("Room.id");
    	IndoorengInfo IndoorengInfo =  IndoorengFactory.getLocalInstance(ctx).getIndoorengInfo(new ObjectUuidPK(model.get("id").toString()),sic);

    	FilterInfo filInfo = new FilterInfo();
    	filInfo.getFilterItems().add(new FilterItemInfo("ProjectName.id",IndoorengInfo.getProjectName().getId()));
    	filInfo.getFilterItems().add(new FilterItemInfo("Version",IndoorengInfo.getVersion(),CompareType.GREATER));
    	filInfo.getFilterItems().add(new FilterItemInfo("Room.id",IndoorengInfo.getRoom().getId()));

    	if(IndoorengFactory.getLocalInstance(ctx).exists(filInfo)){
    		//	throw new EASBizException(new NumericExceptionSubItem("100","请选择最新版进行反审批操作！"));
    		if("1".equals(IndoorengInfo.getVersion()))
    			IndoorengInfo.setAuditor(null);
    		IndoorengInfo.setState(FDCBillStateEnum.SAVED);
    		IndoorengInfo.setLasted(false);
    		IndoorengInfo.setAuditTime(null);
    		DbUtil.execute(ctx, "update CT_001_Indooreng set CFLasted=1 where CFProjectNameID='"+IndoorengInfo.getProjectName().getId()+"'and CFRoomID='"+IndoorengInfo.getRoom().getId()+"'"+" and CFVersion='"+(IndoorengInfo.getVersion())+"'");
    	}
    	else {
    		IndoorengInfo.setAuditor(null);
    		IndoorengInfo.setState(FDCBillStateEnum.SAVED);
    		IndoorengInfo.setLasted(false);
    		IndoorengInfo.setAuditTime(null);
    		DbUtil.execute(ctx, "update CT_001_Indooreng set CFLasted=1 where CFProjectNameID='"+IndoorengInfo.getProjectName().getId()+"'and CFRoomID='"+IndoorengInfo.getRoom().getId()+"'"+" and CFVersion='"+(IndoorengInfo.getVersion()-1)+"'");
    	}
    	_update(ctx, new ObjectUuidPK(IndoorengInfo.getId()), IndoorengInfo);	//更新数据
	}
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	IndoorengInfo IndoorengInfo = (IndoorengInfo) model;
    	IndoorengInfo.setState(FDCBillStateEnum.SUBMITTED);
    	return super._submit(ctx, model);
    }
}