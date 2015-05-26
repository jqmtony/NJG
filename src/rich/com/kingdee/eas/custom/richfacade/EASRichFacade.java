package com.kingdee.eas.custom.richfacade;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.Date;
import com.kingdee.eas.custom.richfacade.*;

public class EASRichFacade extends AbstractBizCtrl implements IEASRichFacade
{
    public EASRichFacade()
    {
        super();
        registerInterface(IEASRichFacade.class, this);
    }
    public EASRichFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IEASRichFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("452A57AD");
    }
    private EASRichFacadeController getController() throws BOSException
    {
        return (EASRichFacadeController)getBizController();
    }
    /**
     *保存明细表-User defined method
     *@param String dataXml
     *@return
     */
    public String[] saveTempData(String String) throws BOSException
    {
        try {
            return getController().saveTempData(getContext(), String);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *生成到检单-User defined method
     *@param date 体检日期
     *@param String 备用参数
     *@return
     */
    public String[] saveExamBill(Date date, String String) throws BOSException
    {
        try {
            return getController().saveExamBill(getContext(), date, String);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}