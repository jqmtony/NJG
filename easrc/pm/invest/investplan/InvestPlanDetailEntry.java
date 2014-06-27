package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.port.pm.invest.investplan.app.*;
import com.kingdee.bos.framework.*;

public class InvestPlanDetailEntry extends CoreBillEntryBase implements IInvestPlanDetailEntry
{
    public InvestPlanDetailEntry()
    {
        super();
        registerInterface(IInvestPlanDetailEntry.class, this);
    }
    public InvestPlanDetailEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IInvestPlanDetailEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E7FFB316");
    }
    private InvestPlanDetailEntryController getController() throws BOSException
    {
        return (InvestPlanDetailEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public InvestPlanDetailEntryInfo getInvestPlanDetailEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestPlanDetailEntryInfo(getContext(), pk);
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
    public InvestPlanDetailEntryInfo getInvestPlanDetailEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestPlanDetailEntryInfo(getContext(), pk, selector);
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
    public InvestPlanDetailEntryInfo getInvestPlanDetailEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestPlanDetailEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public InvestPlanDetailEntryCollection getInvestPlanDetailEntryCollection() throws BOSException
    {
        try {
            return getController().getInvestPlanDetailEntryCollection(getContext());
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
    public InvestPlanDetailEntryCollection getInvestPlanDetailEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInvestPlanDetailEntryCollection(getContext(), view);
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
    public InvestPlanDetailEntryCollection getInvestPlanDetailEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getInvestPlanDetailEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}