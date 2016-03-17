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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.gcftbiaoa.SwbCollection;
import com.kingdee.eas.fdc.gcftbiaoa.SwbFactory;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.fdc.gcftbiaoa.SwbInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class SwbControllerBean extends AbstractSwbControllerBean
{
    private static Logger logger =Logger.getLogger("com.kingdee.eas.fdc.gcftbiaoa.app.SwbControllerBean");
    protected void _aduit(Context ctx, IObjectValue model) throws BOSException,EASBizException {
    	SwbInfo SwbInfo =  (SwbInfo)model;
    	if(SwbInfo.getState().equals(FDCBillStateEnum.AUDITTED))
    		throw new EASBizException(new NumericExceptionSubItem("100","已审核，不能再审核"));

    	SwbInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
    	SwbInfo.setState(FDCBillStateEnum.AUDITTED);
    	SwbInfo.setLasted(true);
    	SwbInfo.setAuditTime(SysUtil.getAppServerTime(ctx));

    	DbUtil.execute(ctx, "update CT_001_Swb set CFLasted=0 where CFProjectNameID='"+SwbInfo.getProjectName().getId()+"'");
    	_update(ctx, new ObjectUuidPK(SwbInfo.getId()), SwbInfo);	//更新数据
    }


    protected void _unAudit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("lasted");
    	sic.add("Version");
    	sic.add("ProjectName.id");
    	SwbInfo SwbInfo =  SwbFactory.getLocalInstance(ctx).getSwbInfo(new ObjectUuidPK(model.get("id").toString()),sic);

    	FilterInfo filInfo = new FilterInfo();
    	filInfo.getFilterItems().add(new FilterItemInfo("ProjectName.id",SwbInfo.getProjectName().getId()));
    	filInfo.getFilterItems().add(new FilterItemInfo("Version",SwbInfo.getVersion(),CompareType.GREATER));

    	if(SwbFactory.getLocalInstance(ctx).exists(filInfo))
    		throw new EASBizException(new NumericExceptionSubItem("100","请选择最新版进行反审批操作！"));
    	SwbInfo.setAuditor(null);
    	SwbInfo.setState(FDCBillStateEnum.SAVED);
    	SwbInfo.setLasted(false);
    	SwbInfo.setAuditTime(null);


    	DbUtil.execute(ctx, "update CT_001_Swb set CFLasted=1 where CFProjectNameID='"+SwbInfo.getProjectName().getId()+"'"+"' and CFVersion='"+(SwbInfo.getVersion()-1)+"'");
    	_update(ctx, new ObjectUuidPK(SwbInfo.getId()), SwbInfo);	//更新数据
    }



    protected IObjectPK _submit(Context ctx, IObjectValue model)
    throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	SwbInfo SwbInfo = (SwbInfo) model;
    	SwbInfo.setState(FDCBillStateEnum.SUBMITTED);
    	return super._submit(ctx, model);
    }
}