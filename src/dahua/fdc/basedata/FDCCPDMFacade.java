package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.cp.dm.archive.ArchiveDocumentParamsInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.cp.dm.archive.ArchiveResultInfo;

public class FDCCPDMFacade extends AbstractBizCtrl implements IFDCCPDMFacade
{
    public FDCCPDMFacade()
    {
        super();
        registerInterface(IFDCCPDMFacade.class, this);
    }
    public FDCCPDMFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCCPDMFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("27C33563");
    }
    private FDCCPDMFacadeController getController() throws BOSException
    {
        return (FDCCPDMFacadeController)getBizController();
    }
    /**
     *上传文档到协同文档管理、可带附件-User defined method
     *@param params 文档属性
     *@param content 文件内容
     *@param fileNames 附件名称
     *@param fileStream 附件输入流
     *@return
     */
    public ArchiveResultInfo upLoadFileToCP(ArchiveDocumentParamsInfo params, String content, String[] fileNames, List fileStream) throws BOSException, EASBizException
    {
        try {
            return getController().upLoadFileToCP(getContext(), params, content, fileNames, fileStream);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *从协同文档管理中下载附件-User defined method
     *@param bizID 协同文档ID
     *@param docID 为空时，下载该文档所有附件
     *@return
     */
    public List downLoadFileFromCP(String bizID, String docID) throws BOSException, EASBizException
    {
        try {
            return getController().downLoadFileFromCP(getContext(), bizID, docID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除指定ID的协同文档或文档附件-User defined method
     *@param bizID 协同文档ID
     *@param docID 为空时，删除整个协同文档，连同附件
     */
    public void deleteCPFile(String bizID, String docID) throws BOSException, EASBizException
    {
        try {
            getController().deleteCPFile(getContext(), bizID, docID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *添加单个附件到已存在文档-User defined method
     *@param bizID 协同文档ID
     *@param fileName 文件名称
     *@param fileStream 文件流
     */
    public void addFileToCP(String bizID, String fileName, byte[] fileStream) throws BOSException, EASBizException
    {
        try {
            getController().addFileToCP(getContext(), bizID, fileName, fileStream);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}