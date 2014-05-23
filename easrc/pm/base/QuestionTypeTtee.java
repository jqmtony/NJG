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

public class QuestionTypeTtee extends DataBase implements IQuestionTypeTtee
{
    public QuestionTypeTtee()
    {
        super();
        registerInterface(IQuestionTypeTtee.class, this);
    }
    public QuestionTypeTtee(Context ctx)
    {
        super(ctx);
        registerInterface(IQuestionTypeTtee.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("56AED9C1");
    }
    private QuestionTypeTteeController getController() throws BOSException
    {
        return (QuestionTypeTteeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public QuestionTypeTteeInfo getQuestionTypeTteeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionTypeTteeInfo(getContext(), pk);
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
    public QuestionTypeTteeInfo getQuestionTypeTteeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionTypeTteeInfo(getContext(), pk, selector);
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
    public QuestionTypeTteeInfo getQuestionTypeTteeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionTypeTteeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public QuestionTypeTteeCollection getQuestionTypeTteeCollection() throws BOSException
    {
        try {
            return getController().getQuestionTypeTteeCollection(getContext());
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
    public QuestionTypeTteeCollection getQuestionTypeTteeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuestionTypeTteeCollection(getContext(), view);
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
    public QuestionTypeTteeCollection getQuestionTypeTteeCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuestionTypeTteeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}