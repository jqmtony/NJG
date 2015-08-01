package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.bireport.BireportBaseFacade;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;

public class FDCBIRptBaseFacade extends BireportBaseFacade implements IFDCBIRptBaseFacade
{
    public FDCBIRptBaseFacade()
    {
        super();
        registerInterface(IFDCBIRptBaseFacade.class, this);
    }
    public FDCBIRptBaseFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCBIRptBaseFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3C72D0C7");
    }
    private FDCBIRptBaseFacadeController getController() throws BOSException
    {
        return (FDCBIRptBaseFacadeController)getBizController();
    }
}