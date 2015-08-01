package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.bos.framework.*;

public class PaySplitUtilFacade extends AbstractBizCtrl implements IPaySplitUtilFacade
{
    public PaySplitUtilFacade()
    {
        super();
        registerInterface(IPaySplitUtilFacade.class, this);
    }
    public PaySplitUtilFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IPaySplitUtilFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8BFF02A0");
    }
    private PaySplitUtilFacadeController getController() throws BOSException
    {
        return (PaySplitUtilFacadeController)getBizController();
    }
    /**
     *红冲凭证中转科目-User defined method
     *@param pk pk
     */
    public void traceReverseVoucher(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().traceReverseVoucher(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}