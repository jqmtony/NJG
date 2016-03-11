package com.kingdee.eas.fdc.gcftbiaoa.app;

import org.apache.log4j.Logger;
import com.kingdee.bos.*;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.dao.IObjectValue;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.gcftbiaoa.OutdoorEngineeringFactory;
import com.kingdee.eas.fdc.gcftbiaoa.OutdoorEngineeringInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class OutdoorEngineeringControllerBean extends AbstractOutdoorEngineeringControllerBean
{
    private static Logger logger =Logger.getLogger("com.kingdee.eas.fdc.gcftbiaoa.app.OutdoorEngineeringControllerBean");
    
    protected void _aduit(Context ctx, IObjectValue model) throws BOSException,
    EASBizException {
    	OutdoorEngineeringInfo OutdoorEngineeringInfo =  (OutdoorEngineeringInfo)model;
    	if(OutdoorEngineeringInfo.getState().equals(FDCBillStateEnum.AUDITTED))
    		throw new EASBizException(new NumericExceptionSubItem("100","已审核，不能再审核"));

    	OutdoorEngineeringInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
    	OutdoorEngineeringInfo.setState(FDCBillStateEnum.AUDITTED);
    	OutdoorEngineeringInfo.setLasted(true);
    	OutdoorEngineeringInfo.setAuditTime(SysUtil.getAppServerTime(ctx));

    	DbUtil.execute(ctx, "update CT_001_OutdoorEngineering set CFLasted=0 where CFProjectNameID='"+OutdoorEngineeringInfo.getProjectName().getId()+"'");
    	_update(ctx, new ObjectUuidPK(OutdoorEngineeringInfo.getId()), OutdoorEngineeringInfo);	//更新数据
    }


    protected void _unAudit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("lasted");
    	sic.add("Version");
    	sic.add("ProjectName.id");
    	OutdoorEngineeringInfo OutdoorEngineeringInfo =  OutdoorEngineeringFactory.getLocalInstance(ctx).getOutdoorEngineeringInfo(new ObjectUuidPK(model.get("id").toString()),sic);

    	FilterInfo filInfo = new FilterInfo();
    	filInfo.getFilterItems().add(new FilterItemInfo("ProjectName.id",OutdoorEngineeringInfo.getProjectName().getId()));
    	filInfo.getFilterItems().add(new FilterItemInfo("Version",OutdoorEngineeringInfo.getVersion(),CompareType.GREATER));

    	if(OutdoorEngineeringFactory.getLocalInstance(ctx).exists(filInfo))
    		throw new EASBizException(new NumericExceptionSubItem("100","请选择最新版进行反审批操作！"));
    	OutdoorEngineeringInfo.setAuditor(null);
    	OutdoorEngineeringInfo.setState(FDCBillStateEnum.SAVED);
    	OutdoorEngineeringInfo.setLasted(false);
    	OutdoorEngineeringInfo.setAuditTime(null);


    	DbUtil.execute(ctx, "update CT_001_OutdoorEngineering set CFLasted=1 where CFProjectNameID='"+OutdoorEngineeringInfo.getProjectName().getId()+"'"+"' and CFVersion='"+(OutdoorEngineeringInfo.getVersion()-1)+"'");
    	_update(ctx, new ObjectUuidPK(OutdoorEngineeringInfo.getId()), OutdoorEngineeringInfo);	//更新数据
    }



    protected IObjectPK _submit(Context ctx, IObjectValue model)
    throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	OutdoorEngineeringInfo OutdoorEngineeringInfo = (OutdoorEngineeringInfo) model;
    	OutdoorEngineeringInfo.setState(FDCBillStateEnum.SUBMITTED);
    	return super._submit(ctx, model);
    }
}