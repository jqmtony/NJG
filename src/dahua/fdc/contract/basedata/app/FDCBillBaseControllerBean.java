package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.app.BillBaseControllerBean;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.FDCBillBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.BillBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCBillBaseInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.ObjectBaseCollection;
import java.util.List;

public class FDCBillBaseControllerBean extends AbstractFDCBillBaseControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.FDCBillBaseControllerBean");
    protected void _audit(Context ctx, List idList)throws BOSException, EASBizException
    {
    }
    protected void _unAudit(Context ctx, List idList)throws BOSException, EASBizException
    {
    }
    protected void _audit(Context ctx, BOSUuid billId)throws BOSException, EASBizException
    {
    }
    protected void _unAudit(Context ctx, BOSUuid billId)throws BOSException, EASBizException
    {
    }
    protected void _setAudittingStatus(Context ctx, BOSUuid billId)throws BOSException, EASBizException
    {
    }
    protected void _setSubmitStatus(Context ctx, BOSUuid billId)throws BOSException, EASBizException
    {
    }
    protected void _cancel(Context ctx, IObjectPK pk)throws BOSException, EASBizException
    {
    }
    protected void _cancelCancel(Context ctx, IObjectPK pk)throws BOSException, EASBizException
    {
    }
    protected void _cancel(Context ctx, IObjectPK[] pkArray)throws BOSException, EASBizException
    {
    }
    protected void _cancelCancel(Context ctx, IObjectPK[] pkArray)throws BOSException, EASBizException
    {
    }
    protected boolean _checkCanSubmit(Context ctx, String id)throws BOSException, EASBizException
    {
        return false;
    }
    protected Map _fetchInitData(Context ctx, Map paramMap)throws BOSException, EASBizException
    {
        return null;
    }
    protected Map _fetchFilterInitData(Context ctx, Map paramMap)throws BOSException, EASBizException
    {
        return null;
    }
    protected void _setRespite(Context ctx, BOSUuid billId, boolean value)throws BOSException, EASBizException
    {
    }
    protected void _setRespite(Context ctx, List ids, boolean value)throws BOSException, EASBizException
    {
    }
}