package com.kingdee.eas.fdc.aimcost.costkf.app;

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
import com.kingdee.eas.fdc.aimcost.costkf.CQGSCollection;
import com.kingdee.eas.fdc.aimcost.costkf.CQGSFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.aimcost.costkf.CQGSInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class CQGSControllerBean extends AbstractCQGSControllerBean
{	
	
	protected void _aduit(Context ctx, IObjectValue model) throws BOSException,
			EASBizException {
		CQGSInfo cqgsInfo =  (CQGSInfo)model;
		if(cqgsInfo.getState().equals(FDCBillStateEnum.AUDITTED))
			throw new EASBizException(new NumericExceptionSubItem("100","已审核，不能再审核"));
		
		cqgsInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		cqgsInfo.setState(FDCBillStateEnum.AUDITTED);
		cqgsInfo.setLasted(true);
		
		DbUtil.execute(ctx, "update CT_COS_CQGS set CFLasted=0 where CFProjectNameID='"+cqgsInfo.getProjectName().getId()+"'");
		_update(ctx, new ObjectUuidPK(cqgsInfo.getId()), cqgsInfo);	//更新数据
	}

	
	protected void _unAudit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("lasted");
		sic.add("Version");
		sic.add("ProjectName.id");
		CQGSInfo cqgsInfo =  CQGSFactory.getLocalInstance(ctx).getCQGSInfo(new ObjectUuidPK(model.get("id").toString()),sic);
		
		FilterInfo filInfo = new FilterInfo();
		filInfo.getFilterItems().add(new FilterItemInfo("ProjectName.id",cqgsInfo.getProjectName().getId()));
		filInfo.getFilterItems().add(new FilterItemInfo("Version",cqgsInfo.getVersion(),CompareType.GREATER));
		
		if(CQGSFactory.getLocalInstance(ctx).exists(filInfo))
			throw new EASBizException(new NumericExceptionSubItem("100","请选择最新版进行反审批操作！"));
		cqgsInfo.setAuditor(null);
		cqgsInfo.setState(FDCBillStateEnum.SAVED);
		cqgsInfo.setLasted(false);
		
		
		DbUtil.execute(ctx, "update CT_COS_CQGS set CFLasted=1 where CFProjectNameID='"+cqgsInfo.getProjectName().getId()+"' and CFVersion='"+(cqgsInfo.getVersion()-1)+"'");
		_update(ctx, new ObjectUuidPK(cqgsInfo.getId()), cqgsInfo);	//更新数据
	}
	
	

	@Override
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		CQGSInfo cqgsInfo = (CQGSInfo) model;
		cqgsInfo.setState(FDCBillStateEnum.SUBMITTED);
		return super._submit(ctx, model);
	}



	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.costkf.app.CQGSControllerBean");
}