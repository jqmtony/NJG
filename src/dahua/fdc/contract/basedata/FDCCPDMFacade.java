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
     *�ϴ��ĵ���Эͬ�ĵ������ɴ�����-User defined method
     *@param params �ĵ�����
     *@param content �ļ�����
     *@param fileNames ��������
     *@param fileStream ����������
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
     *��Эͬ�ĵ����������ظ���-User defined method
     *@param bizID Эͬ�ĵ�ID
     *@param docID Ϊ��ʱ�����ظ��ĵ����и���
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
     *ɾ��ָ��ID��Эͬ�ĵ����ĵ�����-User defined method
     *@param bizID Эͬ�ĵ�ID
     *@param docID Ϊ��ʱ��ɾ������Эͬ�ĵ�����ͬ����
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
     *��ӵ����������Ѵ����ĵ�-User defined method
     *@param bizID Эͬ�ĵ�ID
     *@param fileName �ļ�����
     *@param fileStream �ļ���
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