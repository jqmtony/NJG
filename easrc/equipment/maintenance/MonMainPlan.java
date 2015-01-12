package com.kingdee.eas.port.equipment.maintenance;

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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.port.equipment.maintenance.app.*;
import com.kingdee.eas.xr.XRBillBase;
import com.kingdee.eas.xr.IXRBillBase;

public class MonMainPlan extends XRBillBase implements IMonMainPlan
{
    public MonMainPlan()
    {
        super();
        registerInterface(IMonMainPlan.class, this);
    }
    public MonMainPlan(Context ctx)
    {
        super(ctx);
        registerInterface(IMonMainPlan.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A0CD335E");
    }
    private MonMainPlanController getController() throws BOSException
    {
        return (MonMainPlanController)getBizController();
    }
    /**
     *addnew-System defined method
     *@param pk pk
     *@param model model
     */
    public void addnew(IObjectPK pk, MonMainPlanInfo model) throws BOSException, EASBizException
    {
        try {
            getController().addnew(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *addnew-System defined method
     *@param model model
     *@return
     */
    public IObjectPK addnew(MonMainPlanInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().addnew(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *注意EJB事务-User defined method
     *@param pk 主键
     */
    public void audit(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param pk pk
     */
    public void delete(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param arrayPK arrayPK
     */
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), arrayPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param filter filter
     *@return
     */
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param oql oql
     *@return
     */
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *exists-System defined method
     *@param pk pk
     *@return
     */
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *exists-System defined method
     *@param filter filter
     *@return
     */
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *exists-System defined method
     *@param oql oql
     *@return
     */
    public boolean exists(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MonMainPlanCollection getMonMainPlanCollection() throws BOSException
    {
        try {
            return getController().getMonMainPlanCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public MonMainPlanCollection getMonMainPlanCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMonMainPlanCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public MonMainPlanCollection getMonMainPlanCollection(String oql) throws BOSException
    {
        try {
            return getController().getMonMainPlanCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPKList-System defined method
     *@return
     */
    public IObjectPK[] getPKList() throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPKList-System defined method
     *@param filter filter
     *@param sorter sorter
     *@return
     */
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), filter, sorter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPKList-System defined method
     *@param oql oql
     *@return
     */
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public MonMainPlanInfo getMonMainPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMonMainPlanInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MonMainPlanInfo getMonMainPlanInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMonMainPlanInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public MonMainPlanInfo getMonMainPlanInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMonMainPlanInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量反审核-User defined method
     *@param pk 主键
     */
    public void unAudit(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量反审核-User defined method
     *@param pks pks
     */
    public void unAudit(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *update-System defined method
     *@param pk pk
     *@param model model
     */
    public void update(IObjectPK pk, MonMainPlanInfo model) throws BOSException, EASBizException
    {
        try {
            getController().update(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updateBigObject-System defined method
     *@param pk pk
     *@param model model
     */
    public void updateBigObject(IObjectPK pk, MonMainPlanInfo model) throws BOSException
    {
        try {
            getController().updateBigObject(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updatePartial-System defined method
     *@param model model
     *@param selector selector
     */
    public void updatePartial(MonMainPlanInfo model, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            getController().updatePartial(getContext(), model, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到编码绑定属性-User defined method
     *@return
     */
    public String getBindingProperty() throws BOSException
    {
        try {
            return getController().getBindingProperty(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *关联生成设备二级维保任务书-User defined method
     *@param model model
     */
    public void scrws(MonMainPlanInfo model) throws BOSException
    {
        try {
            getController().scrws(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *导入模板-User defined method
     *@param model model
     */
    public void excel(MonMainPlanInfo model) throws BOSException
    {
        try {
            getController().excel(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}