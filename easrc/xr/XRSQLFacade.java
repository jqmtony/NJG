package com.kingdee.eas.xr;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.xr.app.*;
import java.util.List;
import java.sql.Timestamp;

public class XRSQLFacade extends AbstractBizCtrl implements IXRSQLFacade
{
    public XRSQLFacade()
    {
        super();
        registerInterface(IXRSQLFacade.class, this);
    }
    public XRSQLFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IXRSQLFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F29FFEF2");
    }
    private XRSQLFacadeController getController() throws BOSException
    {
        return (XRSQLFacadeController)getBizController();
    }
    /**
     *ִ��Update���-User defined method
     *@param sql sql
     *@param params ����
     */
    public void executeUpdate(String sql, List params) throws BOSException
    {
        try {
            getController().executeUpdate(getContext(), sql, params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ִ�в�ѯ-User defined method
     *@param sql sql
     *@param params ����
     *@return
     */
    public IRowSet executeQuery(String sql, List params) throws BOSException
    {
        try {
            return getController().executeQuery(getContext(), sql, params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ������ʱ��-User defined method
     *@return
     */
    public Timestamp getServerTime() throws BOSException
    {
        try {
            return getController().getServerTime(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����KSQL����-User defined method
     *@param enable �Ƿ�����
     *@param logFile ��־�ļ�
     */
    public void setSQLTrace(boolean enable, String logFile) throws BOSException
    {
        try {
            getController().setSQLTrace(getContext(), enable, logFile);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�в�������-User defined method
     *@param sqls  sql����
     */
    public void executeBatch(List sqls) throws BOSException
    {
        try {
            getController().executeBatch(getContext(), sqls);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�в�������-User defined method
     *@param sql sql
     *@param params ��������
     */
    public void executeBatch(String sql, List params) throws BOSException
    {
        try {
            getController().executeBatch(getContext(), sql, params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}