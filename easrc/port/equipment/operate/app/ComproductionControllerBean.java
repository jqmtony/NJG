package com.kingdee.eas.port.equipment.operate.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.HashMap;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.IPermission;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.port.equipment.operate.ComproductionFactory;
import com.kingdee.eas.port.equipment.operate.ComproductionInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.port.equipment.operate.ComproductionCollection;
import com.kingdee.eas.scm.common.SCMBillException;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.xr.XRBillBaseInfo;
import com.kingdee.eas.xr.app.XRBillException;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class ComproductionControllerBean extends AbstractComproductionControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.equipment.operate.app.ComproductionControllerBean");
    protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
		ComproductionInfo info = (ComproductionInfo)model;
		if(info.getId()==null){
			info.setState(XRBillStatusEnum.TEMPORARILYSAVED);
			info.setId(BOSUuid.create(info.getBOSType()));
		}
		super._addnew(ctx, pk, model);
	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		ComproductionInfo info = (ComproductionInfo)model;
		if(info.getId()==null){
			info.setState(XRBillStatusEnum.TEMPORARILYSAVED);
			info.setId(BOSUuid.create(info.getBOSType()));
		}
		return super._addnew(ctx, model);
	}

	protected IObjectPK _save(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		ComproductionInfo info = (ComproductionInfo)model;
		if(info.getId()==null){
			info.setState(XRBillStatusEnum.TEMPORARILYSAVED);
			info.setId(BOSUuid.create(info.getBOSType()));
		}
		info.setState(XRBillStatusEnum.TEMPORARILYSAVED);
		return super._save(ctx, model);
	}
	//提交
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		ComproductionInfo info = ((ComproductionInfo) model);
		info.setState(XRBillStatusEnum.SUBMITED);
		//处理断号
		 if (info.getId() == null || !this._exists(ctx, new ObjectUuidPK(info.getId()))) {
			handleIntermitNumber(ctx, info);
		}
		super._submit(ctx, pk, info);
	}

	//提交
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		ComproductionInfo info = ((ComproductionInfo) model);
		info.setState(XRBillStatusEnum.SUBMITED);
		//处理断号
		 if (info.getId() == null || !this._exists(ctx, new ObjectUuidPK(info.getId()))) {
			handleIntermitNumber(ctx, info);
		}
		return super._submit(ctx, info);
	}
	
  

	protected void _actionAudit(Context ctx, IObjectValue model)throws BOSException {
		super._actionAudit(ctx, model);
		ComproductionInfo info = ((ComproductionInfo) model);
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime( new Timestamp(System.currentTimeMillis()));
		info.setState(XRBillStatusEnum.AUDITED);
		try {
			ComproductionFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()),(CoreBaseInfo)info);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		
	}


	protected void _actionUnAudit(Context ctx, IObjectValue model)
			throws BOSException {
		super._actionUnAudit(ctx, model);
		ComproductionInfo info = ((ComproductionInfo) model);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setState(XRBillStatusEnum.TEMPORARILYSAVED);
		try {
			ComproductionFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()),(CoreBaseInfo)info);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	}



	protected void _actionUnAudit(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException {
		for(int i=0;i<pks.length;i++){
			ComproductionInfo aComproductionInfo = getComproductionInfo(ctx, pks[i]);
			_checkAudit(ctx, aComproductionInfo, false);
			aComproductionInfo.setAuditor(null);
			aComproductionInfo.setAuditTime( null);
			aComproductionInfo.setState(XRBillStatusEnum.TEMPORARILYSAVED);
			update(ctx, pks[i], aComproductionInfo);
		}
	}


	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		ComproductionInfo aComproductionInfo = getComproductionInfo(ctx, pk);
		_checkSave(ctx, aComproductionInfo, false);
		super._delete(ctx, pk);
	}

	/**
	 * 校验单据是否为保存
	 * */
	protected boolean _checkSave(Context ctx, IObjectValue model, boolean isOk)
    throws EASBizException, BOSException
	{
	    ComproductionInfo aComproductionInfo = (ComproductionInfo)model;
	    if(aComproductionInfo.getState() != null)
	        if(isOk)
	        {
	            if(aComproductionInfo.getState().getValue() == 1)
	            	throw new XRBillException(XRBillException.CHECKSAVEOK, new Object[] {
	    	                aComproductionInfo.getString("number"), ""
	    	            });
	        } else{
	        	if(aComproductionInfo.getState().getValue()== 4)
		            throw new XRBillException(XRBillException.CHECKSAVENOTOK, new Object[] {
		                aComproductionInfo.getString("number"), ""
		            });
	        }
	    return true;
	}
	/**
	 * 校验单据是否为提交
	 * */
	protected boolean _checkSubmit(Context ctx, IObjectValue model, boolean isOk)
    throws EASBizException, BOSException
	{
	    ComproductionInfo aComproductionInfo = (ComproductionInfo)model;
	    if(aComproductionInfo.getState() != null)
	        if(isOk)
	        {
	            if(aComproductionInfo.getState().getValue() == 2)
	                throw new XRBillException(XRBillException.CHECKSUBMITOK, new Object[] {
	                    aComproductionInfo.getString("number")
	                });
	        } else
	        if(aComproductionInfo.getState().getValue() != 2)
	            throw new XRBillException(XRBillException.CHECKSUBMITNOTOK, new Object[] {
	                aComproductionInfo.getString("number"), ""
	            });
	    return true;
	}
	/**
	 * 校验单据是否为审核
	 * */
	protected boolean _checkAudit(Context ctx, IObjectValue model, boolean isOk) throws EASBizException, BOSException
	{
	    ComproductionInfo aComproductionInfo = (ComproductionInfo)model;
	    if(aComproductionInfo.getState() != null)
	        if(isOk)
	        {
	            if(aComproductionInfo.getState().getValue() == 4)
	                throw new XRBillException(XRBillException.CHECKAUDITEDOK, new Object[] {
	                    aComproductionInfo.getString("number"), ""
	                });
	        } else
	        if(aComproductionInfo.getState().getValue() != 4)
	            throw new XRBillException(XRBillException.CHECKAUDITEDNOTOK, new Object[] {
	                aComproductionInfo.getString("number"), ""
	            });
	    return true;
	}
	/**
	 * 校验单据是否为关闭
	 * */
	protected boolean _checkClosed(Context ctx, IObjectValue model, boolean isOk) throws EASBizException, BOSException
	{
	    ComproductionInfo aComproductionInfo = (ComproductionInfo)model;
	    if(aComproductionInfo.getState() != null)
	        if(isOk)
	        {
	            if(aComproductionInfo.getState().getValue() == 7)
	                throw new XRBillException(XRBillException.CHECKCLOSEDOK, new Object[] {
	                    aComproductionInfo.getString("number"), ""
	                });
	        } else
	        if(aComproductionInfo.getState().getValue() != 7)
	            throw new XRBillException(XRBillException.CHECKCLOSEDNOTOK, new Object[] {
	                aComproductionInfo.getString("number"), ""
	            });
	    return true;
	}
	
    /**
	 * 处理
	 * @author sxhong  		Date 2006-11-25
	 */
	protected void handleIntermitNumber(Context ctx, ComproductionInfo info) throws BOSException, CodingRuleException, EASBizException
	{
		//如果用户在客户端手工选择了断号,则此处不必在抢号
		if(info.getNumber() != null && info.getNumber().length() > 0) return;
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		
		//对成本中心进行处理
		String costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		boolean isExist = true;
		if (!iCodingRuleManager.isExist(info, costUnitId)){
			if (!iCodingRuleManager.isExist(info, costUnitId)){
				isExist = false; 
			}
		}
		if(isExist){
    	   // 选择了断号支持或者没有选择新增显示,获取并设置编号
           if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId) || !iCodingRuleManager.isAddView(info, costUnitId))
           // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
           {
               // 启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
               String number = iCodingRuleManager.getNumber(info,costUnitId);
               info.setNumber(number);
           }
       }
	}
}