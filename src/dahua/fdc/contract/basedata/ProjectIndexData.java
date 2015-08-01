package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.List;

public class ProjectIndexData extends FDCBill implements IProjectIndexData
{
    public ProjectIndexData()
    {
        super();
        registerInterface(IProjectIndexData.class, this);
    }
    public ProjectIndexData(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectIndexData.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("734D0775");
    }
    private ProjectIndexDataController getController() throws BOSException
    {
        return (ProjectIndexDataController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public ProjectIndexDataInfo getProjectIndexDataInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectIndexDataInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public ProjectIndexDataInfo getProjectIndexDataInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectIndexDataInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public ProjectIndexDataInfo getProjectIndexDataInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectIndexDataInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ProjectIndexDataCollection getProjectIndexDataCollection() throws BOSException
    {
        try {
            return getController().getProjectIndexDataCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public ProjectIndexDataCollection getProjectIndexDataCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectIndexDataCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public ProjectIndexDataCollection getProjectIndexDataCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectIndexDataCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param projIdList projIdList
     *@param productTypeId productTypeId
     *@return
     */
    public IRowSet sum(List projIdList, String productTypeId) throws BOSException, EASBizException
    {
        try {
            return getController().sum(getContext(), projIdList, productTypeId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ύ-User defined method
     *@param colls ����
     *@return
     */
    public Map submitAreaIndex(CoreBaseCollection colls) throws BOSException, EASBizException
    {
        try {
            return getController().submitAreaIndex(getContext(), colls);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ָ��ˢ��-User defined method
     *@param param ����
     *@return
     */
    public Map idxRefresh(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().idxRefresh(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}