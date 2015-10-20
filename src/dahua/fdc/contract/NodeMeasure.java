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
import java.util.Map;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class NodeMeasure extends FDCBill implements INodeMeasure
{
    public NodeMeasure()
    {
        super();
        registerInterface(INodeMeasure.class, this);
    }
    public NodeMeasure(Context ctx)
    {
        super(ctx);
        registerInterface(INodeMeasure.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("62B483E1");
    }
    private NodeMeasureController getController() throws BOSException
    {
        return (NodeMeasureController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public NodeMeasureInfo getNodeMeasureInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNodeMeasureInfo(getContext(), pk);
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
    public NodeMeasureInfo getNodeMeasureInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNodeMeasureInfo(getContext(), pk, selector);
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
    public NodeMeasureInfo getNodeMeasureInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNodeMeasureInfo(getContext(), oql);
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
    public NodeMeasureCollection getNodeMeasureCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNodeMeasureCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public NodeMeasureCollection getNodeMeasureCollection() throws BOSException
    {
        try {
            return getController().getNodeMeasureCollection(getContext());
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
    public NodeMeasureCollection getNodeMeasureCollection(String oql) throws BOSException
    {
        try {
            return getController().getNodeMeasureCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *初始化数据-User defined method
     *@param contractId 合同id
     *@param nodeMeasureId 节点计价id
     *@return
     */
    public Map fetchData(String contractId, String nodeMeasureId) throws BOSException, EASBizException
    {
        try {
            return getController().fetchData(getContext(), contractId, nodeMeasureId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *查询取数-User defined method
     *@param contractId 合同id
     *@param startDate 开始日期
     *@param endDate 结束日期
     *@return
     */
    public Map fetchExecData(String contractId, Date startDate, Date endDate) throws BOSException, EASBizException
    {
        try {
            return getController().fetchExecData(getContext(), contractId, startDate, endDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}