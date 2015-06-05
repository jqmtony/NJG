package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fi.ar.OtherBillCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.custom.richinf.app.*;

public class RichCompayWriteOff extends CoreBillBase implements IRichCompayWriteOff
{
    public RichCompayWriteOff()
    {
        super();
        registerInterface(IRichCompayWriteOff.class, this);
    }
    public RichCompayWriteOff(Context ctx)
    {
        super(ctx);
        registerInterface(IRichCompayWriteOff.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4539EF41");
    }
    private RichCompayWriteOffController getController() throws BOSException
    {
        return (RichCompayWriteOffController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RichCompayWriteOffCollection getRichCompayWriteOffCollection() throws BOSException
    {
        try {
            return getController().getRichCompayWriteOffCollection(getContext());
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
    public RichCompayWriteOffCollection getRichCompayWriteOffCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRichCompayWriteOffCollection(getContext(), view);
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
    public RichCompayWriteOffCollection getRichCompayWriteOffCollection(String oql) throws BOSException
    {
        try {
            return getController().getRichCompayWriteOffCollection(getContext(), oql);
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
    public RichCompayWriteOffInfo getRichCompayWriteOffInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRichCompayWriteOffInfo(getContext(), pk);
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
    public RichCompayWriteOffInfo getRichCompayWriteOffInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRichCompayWriteOffInfo(getContext(), pk, selector);
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
    public RichCompayWriteOffInfo getRichCompayWriteOffInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRichCompayWriteOffInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *核销和反核销-User defined method
     *@param fpColl 发票数组
     *@param richExamColl 到检单数组
     *@param hxType 核销类型
     *@param ov 核销单info
     *@return
     */
    public boolean aboutHxAndFanHx(OtherBillCollection fpColl, RichExamedCollection richExamColl, int hxType, RichCompayWriteOffInfo ov) throws BOSException
    {
        try {
            return getController().aboutHxAndFanHx(getContext(), fpColl, richExamColl, hxType, ov);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}