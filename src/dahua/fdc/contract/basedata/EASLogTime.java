package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ObjectBase;
import com.kingdee.eas.framework.IObjectBase;

public class EASLogTime extends ObjectBase implements IEASLogTime
{
    public EASLogTime()
    {
        super();
        registerInterface(IEASLogTime.class, this);
    }
    public EASLogTime(Context ctx)
    {
        super(ctx);
        registerInterface(IEASLogTime.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C54AD938");
    }
    private EASLogTimeController getController() throws BOSException
    {
        return (EASLogTimeController)getBizController();
    }
    /**
     *启动成功-User defined method
     *@param pk pk
     *@return
     */
    public boolean startSucess(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().startSucess(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *定义启动成功时间-User defined method
     *@param ip ip
     *@param port port
     *@param dbName dbName
     *@param userName userName
     *@param password password
     *@param tableName tableName
     *@param field field
     *@return
     */
    public boolean logTime(String ip, String port, String dbName, String userName, String password, String tableName, String field) throws BOSException, EASBizException
    {
        try {
            return getController().logTime(getContext(), ip, port, dbName, userName, password, tableName, field);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}