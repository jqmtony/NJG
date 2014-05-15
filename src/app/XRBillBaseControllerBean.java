package com.kingdee.eas.xr.app;

import java.sql.Timestamp;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.IPermission;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.scm.common.SCMBillException;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.xr.XRBillBaseInfo;
import com.kingdee.eas.xr.helper.StringXRHelper;

public class XRBillBaseControllerBean extends AbstractXRBillBaseControllerBean
{
    private static Logger logger =Logger.getLogger("com.kingdee.eas.xr.app.XRBillBaseControllerBean");

	protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
		scmCheckNumberBlank(ctx,model);
		_checkNumberDup(ctx,null,model);
		XRBillBaseInfo info = (XRBillBaseInfo)model;
		if(info.getId()==null){
			info.setStatus(XRBillStatusEnum.TEMPORARILYSAVED);
			info.setId(BOSUuid.create(info.getBOSType()));
		}
		super._addnew(ctx, pk, model);
	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		scmCheckNumberBlank(ctx,model);
		_checkNumberDup(ctx,null,model);
		XRBillBaseInfo info = (XRBillBaseInfo)model;
		if(info.getId()==null){
			info.setStatus(XRBillStatusEnum.TEMPORARILYSAVED);
			info.setId(BOSUuid.create(info.getBOSType()));
		}
		return super._addnew(ctx, model);
	}

	protected IObjectPK _save(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		scmCheckNumberBlank(ctx,model);
		_checkNumberDup(ctx,null,model);
		XRBillBaseInfo info = (XRBillBaseInfo)model;
		   boolean isAddNew = isAddNew(ctx, info);
		   setBillNewNumber(ctx, info, isAddNew, true, "");
		if(info.getId()==null){
			info.setStatus(XRBillStatusEnum.TEMPORARILYSAVED);
			info.setId(BOSUuid.create(info.getBOSType()));
		}
		info.setStatus(XRBillStatusEnum.TEMPORARILYSAVED);
		return super._save(ctx, model);
	}
	//提交
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		XRBillBaseInfo info = ((XRBillBaseInfo) model);
		info.setStatus(XRBillStatusEnum.SUBMITED);
		//处理断号
		 if (info.getId() == null || !this._exists(ctx, new ObjectUuidPK(info.getId()))) {
			handleIntermitNumber(ctx, info);
		}
		super._submit(ctx, pk, info);
	}

	//提交
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		XRBillBaseInfo info = ((XRBillBaseInfo) model);
		info.setStatus(XRBillStatusEnum.SUBMITED);
		//处理断号
		 if (info.getId() == null || !this._exists(ctx, new ObjectUuidPK(info.getId()))) {
			handleIntermitNumber(ctx, info);
		}
		return super._submit(ctx, info);
	}
	protected void _audit(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		XRBillBaseInfo aXRBillBaseInfo = getXRBillBaseInfo(ctx, pk);
		_checkAudit(ctx, aXRBillBaseInfo, true);
		_checkSubmit(ctx, aXRBillBaseInfo, false);
		aXRBillBaseInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		aXRBillBaseInfo.setAuditTime( new Timestamp(System.currentTimeMillis()));
		aXRBillBaseInfo.setStatus(XRBillStatusEnum.AUDITED);
		update(ctx, pk, aXRBillBaseInfo);
	}

	protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
			XRBillBaseInfo aXRBillBaseInfo = getXRBillBaseInfo(ctx, pk);
			_checkAudit(ctx, aXRBillBaseInfo, false);
			aXRBillBaseInfo.setAuditor(null);
			aXRBillBaseInfo.setAuditTime(null);
			aXRBillBaseInfo.setStatus(XRBillStatusEnum.TEMPORARILYSAVED);
			update(ctx, pk, aXRBillBaseInfo);
	}

	protected void _unAudit(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException {
		for(int i=0;i<pks.length;i++){
			XRBillBaseInfo aXRBillBaseInfo = getXRBillBaseInfo(ctx, pks[i]);
			_checkAudit(ctx, aXRBillBaseInfo, false);
			aXRBillBaseInfo.setAuditor(null);
			aXRBillBaseInfo.setAuditTime( null);
			aXRBillBaseInfo.setStatus(XRBillStatusEnum.TEMPORARILYSAVED);
			update(ctx, pks[i], aXRBillBaseInfo);
		}
	}


	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		XRBillBaseInfo aXRBillBaseInfo = getXRBillBaseInfo(ctx, pk);
		_checkSave(ctx, aXRBillBaseInfo, false);
		super._delete(ctx, pk);
	}
	/**
	 * 校验编码是否已经存在
	 * */
	protected boolean _checkNumberDup(Context ctx, IObjectPK pk, IObjectValue model)
    throws EASBizException, BOSException
	{
	    XRBillBaseInfo aXRBillBaseInfo = (XRBillBaseInfo)model;
	    if(isSameNumber(ctx, pk, model))
	        throw new EASBizException(EASBizException.CHECKNUMDUP, new Object[] {
	            aXRBillBaseInfo.getString("number"), ""
	        });
	    else
	        return true;
	}
	/**
	 * 校验单据是否为保存
	 * */
	protected boolean _checkSave(Context ctx, IObjectValue model, boolean isOk)
    throws EASBizException, BOSException
	{
	    XRBillBaseInfo aXRBillBaseInfo = (XRBillBaseInfo)model;
	    if(aXRBillBaseInfo.getStatus() != null)
	        if(isOk)
	        {
	            if(aXRBillBaseInfo.getStatus().getValue() == 1)
	            	throw new XRBillException(XRBillException.CHECKSAVEOK, new Object[] {
	    	                aXRBillBaseInfo.getString("number"), ""
	    	            });
	        } else{
	        	if(aXRBillBaseInfo.getStatus().getValue()== 4)
		            throw new XRBillException(XRBillException.CHECKSAVENOTOK, new Object[] {
		                aXRBillBaseInfo.getString("number"), ""
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
	    XRBillBaseInfo aXRBillBaseInfo = (XRBillBaseInfo)model;
	    if(aXRBillBaseInfo.getStatus() != null)
	        if(isOk)
	        {
	            if(aXRBillBaseInfo.getStatus().getValue() == 2)
	                throw new XRBillException(XRBillException.CHECKSUBMITOK, new Object[] {
	                    aXRBillBaseInfo.getString("number")
	                });
	        } else
	        if(aXRBillBaseInfo.getStatus().getValue() != 2)
	            throw new XRBillException(XRBillException.CHECKSUBMITNOTOK, new Object[] {
	                aXRBillBaseInfo.getString("number"), ""
	            });
	    return true;
	}
	/**
	 * 校验单据是否为审核
	 * */
	protected boolean _checkAudit(Context ctx, IObjectValue model, boolean isOk) throws EASBizException, BOSException
	{
	    XRBillBaseInfo aXRBillBaseInfo = (XRBillBaseInfo)model;
	    if(aXRBillBaseInfo.getStatus() != null)
	        if(isOk)
	        {
	            if(aXRBillBaseInfo.getStatus().getValue() == 4)
	                throw new XRBillException(XRBillException.CHECKAUDITEDOK, new Object[] {
	                    aXRBillBaseInfo.getString("number"), ""
	                });
	        } else
	        if(aXRBillBaseInfo.getStatus().getValue() != 4)
	            throw new XRBillException(XRBillException.CHECKAUDITEDNOTOK, new Object[] {
	                aXRBillBaseInfo.getString("number"), ""
	            });
	    return true;
	}
	/**
	 * 校验单据是否为关闭
	 * */
	protected boolean _checkClosed(Context ctx, IObjectValue model, boolean isOk) throws EASBizException, BOSException
	{
	    XRBillBaseInfo aXRBillBaseInfo = (XRBillBaseInfo)model;
	    if(aXRBillBaseInfo.getStatus() != null)
	        if(isOk)
	        {
	            if(aXRBillBaseInfo.getStatus().getValue() == 7)
	                throw new XRBillException(XRBillException.CHECKCLOSEDOK, new Object[] {
	                    aXRBillBaseInfo.getString("number"), ""
	                });
	        } else
	        if(aXRBillBaseInfo.getStatus().getValue() != 7)
	            throw new XRBillException(XRBillException.CHECKCLOSEDNOTOK, new Object[] {
	                aXRBillBaseInfo.getString("number"), ""
	            });
	    return true;
	}
	/**
	 * 校验编码是否为空
	 * */
	protected boolean scmCheckNumberBlank(Context ctx, IObjectValue model)
    throws EASBizException, BOSException
	{
//	    XRBillBaseInfo aXRBillBaseInfo = (XRBillBaseInfo)model;
//	    String number = aXRBillBaseInfo.getNumber();
//        if(number == null || number.trim().equals(""))
//            throw new XRBillException(XRBillException.CHECKBILLNUMBLANK, new Object[] {
//                "number"});
	    return true;
	}
	/**
	 * 校验编码是否已经存在
	 * */
	protected boolean isSameNumber(Context ctx, IObjectPK pk, IObjectValue model)
    throws EASBizException, BOSException
	{
	    XRBillBaseInfo aXRBillBaseInfo = (XRBillBaseInfo)model;
	    FilterInfo filter = new FilterInfo();
	    FilterItemInfo filterItem = null;
	    if(aXRBillBaseInfo.getNumber() == null)
	        filterItem = new FilterItemInfo("number", aXRBillBaseInfo.getNumber(), CompareType.EQUALS);
	    else
	        filterItem = new FilterItemInfo("number", aXRBillBaseInfo.getNumber().trim(), CompareType.EQUALS);
	    filter.getFilterItems().add(filterItem);
	    if(aXRBillBaseInfo.getId() != null)
	    {
	        filterItem = new FilterItemInfo("id", aXRBillBaseInfo.getId(), CompareType.NOTEQUALS);
	        filter.getFilterItems().add(filterItem);
	    }
	    StringBuffer sb = new StringBuffer();
	    for(int i = 0; i < filter.getFilterItems().size(); i++)
	        if(i != 0)
	            sb.append(" and #" + i);
	        else
	            sb.append("#" + i);
	
	    filter.setMaskString(sb.toString());
	    EntityViewInfo view = new EntityViewInfo();
	    view.setFilter(filter);
	    SorterItemCollection sorter = new SorterItemCollection();
	    sorter.add(new SorterItemInfo("id"));
	    return super._exists(ctx, filter);
	}
	
	private void checkHasAddVoucherPermission(Context context, CompanyOrgUnitInfo orgUnitInfo)
    throws BOSException, EASBizException
	{
	    UserInfo userInfo = ContextUtil.getCurrentUserInfo(context);
	    IObjectPK userPK = new ObjectUuidPK(userInfo.getId());
	    IObjectPK orgPK = new ObjectUuidPK(orgUnitInfo.getId());
	    IPermission ipermission = PermissionFactory.getLocalInstance(context);
	    ipermission.checkFunctionPermission(userPK, orgPK, "gl_voucher_add");
	}

	protected String _getBindingProperty(Context ctx) throws BOSException {
		return null;
	}

	protected String _getNewNumber(Context ctx, IObjectValue model,String orgId, String customString) throws EASBizException,BOSException  {
	 ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
      XRBillBaseInfo aSCMBillBaseInfo = (XRBillBaseInfo)model;
	       String number = "";
	        boolean isNumberNoSet = false;
	        if(aSCMBillBaseInfo.getNumber() == null || aSCMBillBaseInfo.getNumber().length() == 0)
	           isNumberNoSet = true;
       String strBindProp = getBindingProperty(ctx);
       if(strBindProp != null && strBindProp.trim().length() > 0)
	                 {
	         number = iCodingRuleManager.getNumber(aSCMBillBaseInfo, orgId, strBindProp, customString);
           aSCMBillBaseInfo.setNumber(number);
	                 } else
	                 {
	            number = iCodingRuleManager.getNumber(aSCMBillBaseInfo, orgId, customString);
	         aSCMBillBaseInfo.setNumber(number);
	                 }
	      ObjectUuidPK pk = new ObjectUuidPK(aSCMBillBaseInfo.getId());
	      if(isSameNumber(ctx, pk, aSCMBillBaseInfo))
	                 {
	            String newNumber = "";
	          if(strBindProp != null && strBindProp.trim().length() > 0)
	               newNumber = iCodingRuleManager.getNumber(aSCMBillBaseInfo, orgId, strBindProp, customString);
            else
	                newNumber = iCodingRuleManager.getNumber(aSCMBillBaseInfo, orgId, customString);
	            if(newNumber.equals(aSCMBillBaseInfo.getNumber()))
	               throw new SCMBillException(SCMBillException.NUMBERRULEERROR);
	           number = newNumber;
	           aSCMBillBaseInfo.setNumber(newNumber);
	          if(isSameNumber(ctx, pk, aSCMBillBaseInfo))
	                return _getNewNumber(ctx, ((IObjectValue) (aSCMBillBaseInfo)), orgId, customString);
	                 }
	       return number;
     }
    protected void setBillNewNumber(Context ctx, IObjectValue model, boolean isAddNew, boolean isRuleAutoNumber, String strCompanyID)
    throws BOSException, EASBizException
{
        XRBillBaseInfo aSCMBillBaseInfo = (XRBillBaseInfo)model;
}

    protected boolean isAddNew(Context ctx, IObjectValue model)
        throws EASBizException, BOSException
    {
        boolean ret = false;
        if(model != null && model.get("isAddNew") != null)
           return model.getBoolean("isAddNew");
       XRBillBaseInfo aSCMBillBaseInfo = (XRBillBaseInfo)model;
       if(aSCMBillBaseInfo.getId() == null || aSCMBillBaseInfo.getId().toString().trim().length() == 0 || !_exists(ctx, new ObjectUuidPK(aSCMBillBaseInfo.getId())))
           ret = true;
       else
          ret = false;
       model.put("isAddNew", new Boolean(ret));
       return ret;
    }
    protected HashMap getNumberRuleSet(Context ctx, XRBillBaseInfo aSCMBillBaseInfo, String strCompanyID)
    throws EASBizException, BOSException
   {
       ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
       HashMap hs = new HashMap();
       boolean isExist = false;
        boolean isNotAddView = false;
       String strBindProp = getBindingProperty(ctx);
      if(strBindProp != null && strBindProp.trim().length() > 0)
    {
          if(iCodingRuleManager.isExist(aSCMBillBaseInfo, strCompanyID, strBindProp))
        {
               isExist = true;
               if(!iCodingRuleManager.isAddView(aSCMBillBaseInfo, strCompanyID, strBindProp))
                   isNotAddView = true;
        }
    } else
      if(iCodingRuleManager.isExist(aSCMBillBaseInfo, strCompanyID))
    {
           isExist = true;
           if(!iCodingRuleManager.isAddView(aSCMBillBaseInfo, strCompanyID))
                isNotAddView = true;
    }
       hs.put("isExist", Boolean.valueOf(isExist));
       hs.put("isNotAddView", Boolean.valueOf(isNotAddView));
        return hs;
   }
    protected boolean isRuleAutoNumber(Context ctx, XRBillBaseInfo aSCMBillBaseInfo, String strCompanyID)
    throws EASBizException, BOSException
   {
      ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
        String strBindProp = getBindingProperty(ctx);
       if(strBindProp != null && strBindProp.trim().length() > 0)
    {
           if(iCodingRuleManager.isExist(aSCMBillBaseInfo, strCompanyID, strBindProp) && !iCodingRuleManager.isAddView(aSCMBillBaseInfo, strCompanyID, strBindProp))
               return true;
    } else
        if(iCodingRuleManager.isExist(aSCMBillBaseInfo, strCompanyID) && !iCodingRuleManager.isAddView(aSCMBillBaseInfo, strCompanyID))
           return true;
        return false;
   }
    protected boolean rolbackNumber(Context ctx, IObjectValue model)
    throws BOSException, EASBizException
{
/* 469*/        XRBillBaseInfo aSCMBillBaseInfo = (XRBillBaseInfo)model;
/* 473*/        ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
/* 476*/        String strCompanyID = "";
/* 477*/        String strBindProp = getBindingProperty(ctx);
/* 479*/        if(strBindProp != null && strBindProp.trim().length() > 0)
    {
/* 481*/            if(iCodingRuleManager.isExist(aSCMBillBaseInfo, strCompanyID, strBindProp) && iCodingRuleManager.isUseIntermitNumber(aSCMBillBaseInfo, strCompanyID, strBindProp))
/* 482*/                return iCodingRuleManager.recycleNumber(aSCMBillBaseInfo, strCompanyID, strBindProp, "", aSCMBillBaseInfo.getNumber());
    } else
/* 486*/        if(iCodingRuleManager.isExist(aSCMBillBaseInfo, strCompanyID) && iCodingRuleManager.isUseIntermitNumber(aSCMBillBaseInfo, strCompanyID))
/* 487*/            return iCodingRuleManager.recycleNumber(aSCMBillBaseInfo, strCompanyID, aSCMBillBaseInfo.getNumber());
/* 490*/        return false;
}
	
    /**
	 * 处理
	 * @author sxhong  		Date 2006-11-25
	 */
	protected void handleIntermitNumber(Context ctx, XRBillBaseInfo info) throws BOSException, CodingRuleException, EASBizException
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