package com.kingdee.eas.port.pm.base;

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
import com.kingdee.eas.port.pm.base.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class ExamineIndicators extends DataBase implements IExamineIndicators
{
    public ExamineIndicators()
    {
        super();
        registerInterface(IExamineIndicators.class, this);
    }
    public ExamineIndicators(Context ctx)
    {
        super(ctx);
        registerInterface(IExamineIndicators.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("85126C64");
    }
    private ExamineIndicatorsController getController() throws BOSException
    {
        return (ExamineIndicatorsController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ExamineIndicatorsInfo getExamineIndicatorsInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getExamineIndicatorsInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public ExamineIndicatorsInfo getExamineIndicatorsInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getExamineIndicatorsInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public ExamineIndicatorsInfo getExamineIndicatorsInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getExamineIndicatorsInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ExamineIndicatorsCollection getExamineIndicatorsCollection() throws BOSException
    {
        try {
            return getController().getExamineIndicatorsCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public ExamineIndicatorsCollection getExamineIndicatorsCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getExamineIndicatorsCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public ExamineIndicatorsCollection getExamineIndicatorsCollection(String oql) throws BOSException
    {
        try {
            return getController().getExamineIndicatorsCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}