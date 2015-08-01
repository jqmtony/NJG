package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public class CostAccountFixDataFacade extends AbstractBizCtrl implements ICostAccountFixDataFacade
{
    public CostAccountFixDataFacade()
    {
        super();
        registerInterface(ICostAccountFixDataFacade.class, this);
    }
    public CostAccountFixDataFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ICostAccountFixDataFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5C70EA0B");
    }
    private CostAccountFixDataFacadeController getController() throws BOSException
    {
        return (CostAccountFixDataFacadeController)getBizController();
    }
    /**
     *�޸���֯�ĳɱ���ĿԴ����ID-User defined method
     *@param orgID ��֯ID
     *@param parentOrgList ������֯list
     */
    public void fixOrgSourseID(BOSUuid orgID, List parentOrgList) throws BOSException, EASBizException
    {
        try {
            getController().fixOrgSourseID(getContext(), orgID, parentOrgList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�޸�������Ŀ�ĳɱ���ĿԴID-User defined method
     *@param projectID ������ĿID
     *@param parentNodeList �����ڵ�list
     */
    public void fixPrjSourseID(BOSUuid projectID, List parentNodeList) throws BOSException, EASBizException
    {
        try {
            getController().fixPrjSourseID(getContext(), projectID, parentNodeList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}