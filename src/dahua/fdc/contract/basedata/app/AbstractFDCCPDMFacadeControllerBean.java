package com.kingdee.eas.fdc.basedata.app;

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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.dm.archive.ArchiveDocumentParamsInfo;
import java.util.List;
import com.kingdee.eas.cp.dm.archive.ArchiveResultInfo;



public abstract class AbstractFDCCPDMFacadeControllerBean extends AbstractBizControllerBean implements FDCCPDMFacadeController
{
    protected AbstractFDCCPDMFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("27C33563");
    }

    public ArchiveResultInfo upLoadFileToCP(Context ctx, ArchiveDocumentParamsInfo params, String content, String[] fileNames, List fileStream) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("649e3560-78f0-4dbd-bdc8-40329a7b903e"), new Object[]{ctx, params, content, fileNames, fileStream});
            invokeServiceBefore(svcCtx);
            ArchiveResultInfo retValue = (ArchiveResultInfo)_upLoadFileToCP(ctx, params, content, fileNames, fileStream);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract ArchiveResultInfo _upLoadFileToCP(Context ctx, ArchiveDocumentParamsInfo params, String content, String[] fileNames, List fileStream) throws BOSException, EASBizException;

    public List downLoadFileFromCP(Context ctx, String bizID, String docID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("651fb408-8f38-47e4-b4ea-c26d3fb97dcf"), new Object[]{ctx, bizID, docID});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_downLoadFileFromCP(ctx, bizID, docID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract List _downLoadFileFromCP(Context ctx, String bizID, String docID) throws BOSException, EASBizException;

    public void deleteCPFile(Context ctx, String bizID, String docID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fc71e747-5a64-4374-a671-87c238ef82c4"), new Object[]{ctx, bizID, docID});
            invokeServiceBefore(svcCtx);
            _deleteCPFile(ctx, bizID, docID);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _deleteCPFile(Context ctx, String bizID, String docID) throws BOSException, EASBizException;

    public void addFileToCP(Context ctx, String bizID, String fileName, byte[] fileStream) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bd246acf-9a60-473e-b2c8-3efa03128f3e"), new Object[]{ctx, bizID, fileName, fileStream});
            invokeServiceBefore(svcCtx);
            _addFileToCP(ctx, bizID, fileName, fileStream);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _addFileToCP(Context ctx, String bizID, String fileName, byte[] fileStream) throws BOSException, EASBizException;

}