package com.kingdee.eas.fdc.gcftbiaoa.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringInfo;
import com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringCollection;
import com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringFactory;

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class DecorationEngineeringControllerBean extends AbstractDecorationEngineeringControllerBean
{
    private static Logger logger =Logger.getLogger("com.kingdee.eas.fdc.gcftbiaoa.app.DecorationEngineeringControllerBean");
    
	
	protected void _aduit(Context ctx, IObjectValue model) throws BOSException,
			EASBizException {
		DecorationEngineeringInfo DecorationEngineeringInfo =  (DecorationEngineeringInfo)model;
		if(DecorationEngineeringInfo.getState().equals(FDCBillStateEnum.AUDITTED))
			throw new EASBizException(new NumericExceptionSubItem("100","已审核，不能再审核"));
		
		DecorationEngineeringInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		DecorationEngineeringInfo.setState(FDCBillStateEnum.AUDITTED);
		DecorationEngineeringInfo.setLasted(true);
		DecorationEngineeringInfo.setAuditTime(SysUtil.getAppServerTime(ctx));
		
		DbUtil.execute(ctx, "update CT_001_DecorationEngineering set CFLasted=0 where CFProjectNameID='"+DecorationEngineeringInfo.getProjectName().getId()+"'and CFStyleID='"+DecorationEngineeringInfo.getStyle().getId()+"'");
		_update(ctx, new ObjectUuidPK(DecorationEngineeringInfo.getId()), DecorationEngineeringInfo);	//更新数据
	}

	
	protected void _unAudit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("lasted");
		sic.add("Version");
		sic.add("ProjectName.id");
		sic.add("Style.id");
		DecorationEngineeringInfo DecorationEngineeringInfo =  DecorationEngineeringFactory.getLocalInstance(ctx).getDecorationEngineeringInfo(new ObjectUuidPK(model.get("id").toString()),sic);
		
		FilterInfo filInfo = new FilterInfo();
		filInfo.getFilterItems().add(new FilterItemInfo("ProjectName.id",DecorationEngineeringInfo.getProjectName().getId()));
		filInfo.getFilterItems().add(new FilterItemInfo("Version",DecorationEngineeringInfo.getVersion(),CompareType.GREATER));
		filInfo.getFilterItems().add(new FilterItemInfo("Style.id",DecorationEngineeringInfo.getStyle().getId()));
		
		if(DecorationEngineeringFactory.getLocalInstance(ctx).exists(filInfo)){
//			throw new EASBizException(new NumericExceptionSubItem("100","请选择最新版进行反审批操作！"));
			if("1".equals(DecorationEngineeringInfo.getVersion()))
				DecorationEngineeringInfo.setAuditor(null);
				DecorationEngineeringInfo.setState(FDCBillStateEnum.SAVED);
				DecorationEngineeringInfo.setLasted(false);
				DecorationEngineeringInfo.setAuditTime(null);
				DbUtil.execute(ctx, "update CT_001_DecorationEngineering set CFLasted=1 where CFProjectNameID='"+DecorationEngineeringInfo.getProjectName().getId()+"'and CFStyleID='"+DecorationEngineeringInfo.getStyle().getId()+"'"+" and CFVersion='"+(DecorationEngineeringInfo.getVersion())+"'");
		}
		else {
			DecorationEngineeringInfo.setAuditor(null);
			DecorationEngineeringInfo.setState(FDCBillStateEnum.SAVED);
			DecorationEngineeringInfo.setLasted(false);
			DecorationEngineeringInfo.setAuditTime(null);
			DbUtil.execute(ctx, "update CT_001_DecorationEngineering set CFLasted=1 where CFProjectNameID='"+DecorationEngineeringInfo.getProjectName().getId()+"'and CFStyleID='"+DecorationEngineeringInfo.getStyle().getId()+"'"+" and CFVersion='"+(DecorationEngineeringInfo.getVersion()-1)+"'");
		}
		_update(ctx, new ObjectUuidPK(DecorationEngineeringInfo.getId()), DecorationEngineeringInfo);	//更新数据
	}
	

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		DecorationEngineeringInfo DecorationEngineeringInfo = (DecorationEngineeringInfo) model;
		DecorationEngineeringInfo.setState(FDCBillStateEnum.SUBMITTED);
		return super._submit(ctx, model);
	}

}