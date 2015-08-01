package com.kingdee.eas.fdc.material;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.material.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.RetValue;

public class MaterialRptFacade extends AbstractBizCtrl implements IMaterialRptFacade
{
    public MaterialRptFacade()
    {
        super();
        registerInterface(IMaterialRptFacade.class, this);
    }
    public MaterialRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IMaterialRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("21F8DE43");
    }
    private MaterialRptFacadeController getController() throws BOSException
    {
        return (MaterialRptFacadeController)getBizController();
    }
    /**
     *��ȡ���Ϻ�̨ͬ��-User defined method
     *@param param param
     *@return
     */
    public RetValue getMaterialContractValues(RetValue param) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialContractValues(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��Ŀ�׹�����̨��-User defined method
     *@param param param
     *@return
     */
    public RetValue getPartAMaterialValues(RetValue param) throws BOSException, EASBizException
    {
        try {
            return getController().getPartAMaterialValues(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��Ŀ�׹������±���-User defined method
     *@param param param
     *@return
     */
    public RetValue getMaterialContractMonthValues(RetValue param) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialContractMonthValues(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}