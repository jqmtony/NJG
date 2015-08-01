package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import java.lang.Object;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public class FDCBillFacade extends AbstractBizCtrl implements IFDCBillFacade
{
    public FDCBillFacade()
    {
        super();
        registerInterface(IFDCBillFacade.class, this);
    }
    public FDCBillFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCBillFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5138CB14");
    }
    private FDCBillFacadeController getController() throws BOSException
    {
        return (FDCBillFacadeController)getBizController();
    }
    /**
     *自动变更结算-User defined method
     *@param settleId 合同ID
     *@param isAll 是否包含已变更结算的更单
     */
    public void autoChangeSettle(String settleId, boolean isAll) throws BOSException, EASBizException
    {
        try {
            getController().autoChangeSettle(getContext(), settleId, isAll);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *通用执行操作程序-User defined method
     *@param param param
     *@return
     */
    public Object execAction(Map param) throws BOSException
    {
        try {
            return getController().execAction(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *自动比例拆分-User defined method
     *@param type 类型
     *@param dataMap 数据集合
     *@return
     */
    public FDCSplitBillInfo autoPropSplit(String type, Map dataMap) throws BOSException, EASBizException
    {
        try {
            return getController().autoPropSplit(getContext(), type, dataMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置单据已审批-User defined method
     *@param billId 单据Id
     */
    public void setBillAudited4wf(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setBillAudited4wf(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置单据已审批指定审批人-User defined method
     *@param billId 单据ID
     *@param auditorId 审批人
     */
    public void setBillAudited4wf2(BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException
    {
        try {
            getController().setBillAudited4wf2(getContext(), billId, auditorId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置单据审批中指定审批人-User defined method
     *@param billId 单据Id
     *@param auditorId 审批人
     */
    public void setBillAuditing4wf2(BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException
    {
        try {
            getController().setBillAuditing4wf2(getContext(), billId, auditorId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *dealSaveAboutConChange-User defined method
     *@param settleId 结算单ID
     */
    public void dealSaveAboutConChange(String settleId) throws BOSException, EASBizException
    {
        try {
            getController().dealSaveAboutConChange(getContext(), settleId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}