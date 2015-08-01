package com.kingdee.eas.fdc.material;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.material.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class MaterialOrderBizBill extends FDCBill implements IMaterialOrderBizBill
{
    public MaterialOrderBizBill()
    {
        super();
        registerInterface(IMaterialOrderBizBill.class, this);
    }
    public MaterialOrderBizBill(Context ctx)
    {
        super(ctx);
        registerInterface(IMaterialOrderBizBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("458C7B99");
    }
    private MaterialOrderBizBillController getController() throws BOSException
    {
        return (MaterialOrderBizBillController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MaterialOrderBizBillCollection getMaterialOrderBizBillCollection() throws BOSException
    {
        try {
            return getController().getMaterialOrderBizBillCollection(getContext());
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
    public MaterialOrderBizBillCollection getMaterialOrderBizBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMaterialOrderBizBillCollection(getContext(), view);
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
    public MaterialOrderBizBillCollection getMaterialOrderBizBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getMaterialOrderBizBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MaterialOrderBizBillInfo getMaterialOrderBizBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialOrderBizBillInfo(getContext(), pk);
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
    public MaterialOrderBizBillInfo getMaterialOrderBizBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialOrderBizBillInfo(getContext(), pk, selector);
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
    public MaterialOrderBizBillInfo getMaterialOrderBizBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialOrderBizBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}