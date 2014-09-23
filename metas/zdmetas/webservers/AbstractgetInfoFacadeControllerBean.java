package com.kingdee.eas.bpmdemo.webservers;

import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
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
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import java.lang.String;
import java.util.Date;



public abstract class AbstractgetInfoFacadeControllerBean extends AbstractBizControllerBean implements getInfoFacadeController
{
    protected AbstractgetInfoFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("2969D79D");
    }

    public String[] GetbillInfo(Context ctx, String strBSID, String strBOID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d3896e21-4bd9-4240-b093-005cd26357b7"), new Object[]{ctx, strBSID, strBOID});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String[] retValue = (String[])_GetbillInfo(ctx, strBSID, strBOID);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String[] _GetbillInfo(Context ctx, String strBSID, String strBOID) throws BOSException
    {    	
        return null;
    }

    public String[] GetrRelatedBillInfo(Context ctx, String strBTID, String strBOID, String strRelatedCode) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("aa283911-ff38-4c41-bf63-86381f0ea98d"), new Object[]{ctx, strBTID, strBOID, strRelatedCode});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String[] retValue = (String[])_GetrRelatedBillInfo(ctx, strBTID, strBOID, strRelatedCode);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String[] _GetrRelatedBillInfo(Context ctx, String strBTID, String strBOID, String strRelatedCode) throws BOSException
    {    	
        return null;
    }

    public String[] SubmitResult(Context ctx, String strBSID, String strBOID, boolean bSuccess, int iProcInstID, String procURL, String strMessage) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8faacda0-4c1e-4cfc-ad19-8a4475f3b4df"), new Object[]{ctx, strBSID, strBOID, new Boolean(bSuccess), new Integer(iProcInstID), procURL, strMessage});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String[] retValue = (String[])_SubmitResult(ctx, strBSID, strBOID, bSuccess, iProcInstID, procURL, strMessage);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String[] _SubmitResult(Context ctx, String strBSID, String strBOID, boolean bSuccess, int iProcInstID, String procURL, String strMessage) throws BOSException
    {    	
        return null;
    }

    public String[] ApproveClose(Context ctx, String strBSID, String strBOID, int iProcInstID, String eProcessInstanceResult, String strComment, Date dtTime) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("45bb8340-f348-406c-97de-558c07e3de7f"), new Object[]{ctx, strBSID, strBOID, new Integer(iProcInstID), eProcessInstanceResult, strComment, dtTime});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String[] retValue = (String[])_ApproveClose(ctx, strBSID, strBOID, iProcInstID, eProcessInstanceResult, strComment, dtTime);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String[] _ApproveClose(Context ctx, String strBSID, String strBOID, int iProcInstID, String eProcessInstanceResult, String strComment, Date dtTime) throws BOSException
    {    	
        return null;
    }

}