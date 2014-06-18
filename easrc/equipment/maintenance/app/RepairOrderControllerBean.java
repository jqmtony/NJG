package com.kingdee.eas.port.equipment.maintenance.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;

import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.xr.XRBillBaseCollection;
import com.kingdee.eas.xr.XRBillBaseInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.xr.app.XRBillBaseControllerBean;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.port.equipment.maintenance.MonMainPlanE1Factory;
import com.kingdee.eas.port.equipment.maintenance.MonMainPlanE1Info;
import com.kingdee.eas.port.equipment.maintenance.RepairOrderCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo;
import com.kingdee.eas.port.equipment.wdx.EqmOverhaulInfo;
import com.kingdee.util.UuidException;

public class RepairOrderControllerBean extends AbstractRepairOrderControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.equipment.maintenance.app.RepairOrderControllerBean");
    
    protected IObjectPK _save(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	RepairOrderInfo aXRBillBaseInfo = (RepairOrderInfo)model;
    	 aXRBillBaseInfo.setNumber(getAutoCode(ctx, aXRBillBaseInfo));
    	return super._save(ctx, aXRBillBaseInfo);
    }
    
    protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
    	RepairOrderInfo aXRBillBaseInfo = (RepairOrderInfo)model;
   	 aXRBillBaseInfo.setNumber(getAutoCode(ctx, aXRBillBaseInfo));
    	super._submit(ctx, pk, model);
    }
    
	
	/** 获取编码规则生成的编码 
     * @throws UuidException 
     * @throws BOSException 
     * @throws EASBizException */
	public static String getAutoCode(Context ctx ,IObjectValue objValue) throws EASBizException, BOSException, UuidException {
		    String NumberCode = "";
		    String companyId;
			com.kingdee.eas.base.codingrule.ICodingRuleManager codeRuleMgr = null;
			if(ctx==null){
				companyId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
				codeRuleMgr = CodingRuleManagerFactory.getRemoteInstance();
			}else{
				companyId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
				codeRuleMgr = CodingRuleManagerFactory.getLocalInstance(ctx);
			}

			if (codeRuleMgr.isExist(objValue, companyId)) {
				if (codeRuleMgr.isUseIntermitNumber(objValue, companyId)) {
					NumberCode = codeRuleMgr.readNumber(objValue, companyId);
				} else {
					NumberCode = codeRuleMgr.getNumber(objValue, companyId);
				}
			}
		return NumberCode;
	}
	
  
		protected void _audit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
			super._audit(ctx, pk);
			RepairOrderInfo roInfo = getRepairOrderInfo(ctx, pk);
				if(roInfo.getSourceBillId() != null){
					IObjectValue billInfo = (IObjectValue) DynamicObjectFactory.getLocalInstance(ctx).getValue(new ObjectUuidPK(roInfo.getSourceBillId() ).getObjectType(), new ObjectUuidPK(roInfo.getSourceBillId()));
					if(billInfo instanceof MonMainPlanE1Info)
					{
						MonMainPlanE1Info mmeInfo = MonMainPlanE1Factory.getLocalInstance(ctx).getMonMainPlanE1Info(new ObjectUuidPK(roInfo.getSourceBillId()));
						if(roInfo.getTransferTime()!=null){
							mmeInfo.setActualCompleteT(roInfo.getTransferTime());
						}
						if(roInfo.getAcceptSituation() !=null){
							mmeInfo.setComplete(roInfo.getAcceptSituation());
						}
						if(roInfo.getSlDepart() !=null){
							String id = ((AdminOrgUnitInfo)roInfo.getSlDepart()).getId().toString();
							AdminOrgUnitInfo aoInfo = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitInfo(new ObjectUuidPK(id));
							mmeInfo.setImplementDepart(aoInfo);
						}
						MonMainPlanE1Factory.getLocalInstance(ctx).update((new ObjectUuidPK(mmeInfo.getId())),mmeInfo);
					}
				}
		}
		
		protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
			super._unAudit(ctx, pk);
			RepairOrderInfo roInfo = getRepairOrderInfo(ctx, pk);
			if(roInfo.getSourceBillId() != null){
				IObjectValue billInfo = (IObjectValue) DynamicObjectFactory.getLocalInstance(ctx).getValue(new ObjectUuidPK(roInfo.getSourceBillId() ).getObjectType(), new ObjectUuidPK(roInfo.getSourceBillId()));
					if(billInfo instanceof MonMainPlanE1Info)
					{
					MonMainPlanE1Info mmeInfo = MonMainPlanE1Factory.getLocalInstance(ctx).getMonMainPlanE1Info(new ObjectUuidPK(roInfo.getSourceBillId()));
					mmeInfo.setActualCompleteT(null);
					mmeInfo.setComplete(null);
					mmeInfo.setImplementDepart(null);
					MonMainPlanE1Factory.getLocalInstance(ctx).update((new ObjectUuidPK(mmeInfo.getId())),mmeInfo);
					}
			}
		}
}