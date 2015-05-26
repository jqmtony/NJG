package com.kingdee.eas.custom.richbase;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.custom.richbase.app.*;
import com.kingdee.eas.framework.IDataBase;

public class ExamType extends DataBase implements IExamType
{
    public ExamType()
    {
        super();
        registerInterface(IExamType.class, this);
    }
    public ExamType(Context ctx)
    {
        super(ctx);
        registerInterface(IExamType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B021D2EF");
    }
    private ExamTypeController getController() throws BOSException
    {
        return (ExamTypeController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public ExamTypeInfo getExamTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getExamTypeInfo(getContext(), pk);
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
    public ExamTypeInfo getExamTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getExamTypeInfo(getContext(), pk, selector);
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
    public ExamTypeInfo getExamTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getExamTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ExamTypeCollection getExamTypeCollection() throws BOSException
    {
        try {
            return getController().getExamTypeCollection(getContext());
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
    public ExamTypeCollection getExamTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getExamTypeCollection(getContext(), view);
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
    public ExamTypeCollection getExamTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getExamTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}