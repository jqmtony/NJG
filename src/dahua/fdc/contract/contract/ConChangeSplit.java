package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.FDCSplitBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCSplitBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.contract.app.*;

public class ConChangeSplit extends FDCSplitBill implements IConChangeSplit
{
    public ConChangeSplit()
    {
        super();
        registerInterface(IConChangeSplit.class, this);
    }
    public ConChangeSplit(Context ctx)
    {
        super(ctx);
        registerInterface(IConChangeSplit.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9134D170");
    }
    private ConChangeSplitController getController() throws BOSException
    {
        return (ConChangeSplitController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ConChangeSplitInfo getConChangeSplitInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getConChangeSplitInfo(getContext(), pk);
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
    public ConChangeSplitInfo getConChangeSplitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getConChangeSplitInfo(getContext(), pk, selector);
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
    public ConChangeSplitInfo getConChangeSplitInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getConChangeSplitInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ConChangeSplitCollection getConChangeSplitCollection() throws BOSException
    {
        try {
            return getController().getConChangeSplitCollection(getContext());
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
    public ConChangeSplitCollection getConChangeSplitCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getConChangeSplitCollection(getContext(), view);
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
    public ConChangeSplitCollection getConChangeSplitCollection(String oql) throws BOSException
    {
        try {
            return getController().getConChangeSplitCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *自动按结算金额拆分-User defined method
     *@param changePK 变更单pk
     */
    public void autoSplit(IObjectPK changePK) throws BOSException, EASBizException
    {
        try {
            getController().autoSplit(getContext(), changePK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *变更结算-User defined method
     *@param changeInfo 变更单
     */
    public void changeSettle(ContractChangeBillInfo changeInfo) throws BOSException, EASBizException
    {
        try {
            getController().changeSettle(getContext(), changeInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *自动引入合同及变更拆分-User defined method
     *@param billID billID
     */
    public void autoSplit4(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().autoSplit4(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}