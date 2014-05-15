package com.kingdee.eas.xr.xrbase.app;

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

import com.kingdee.eas.basedata.assistant.AssistantCtrlUnitUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.xr.xrbase.XRBizDataBaseInfo;
import com.kingdee.eas.xr.xrbase.XRDataBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.xr.xrbase.XRDataBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.eas.framework.DataBaseCollection;

public class XRDataBaseControllerBean extends AbstractXRDataBaseControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.xr.xrbase.app.XRDataBaseControllerBean");
    protected void addnewCheck(Context ctx, IObjectValue model)
    throws BOSException, EASBizException
  {
    ((XRDataBaseInfo)model).setCU(AssistantCtrlUnitUtils.getRootCU(ctx));
    _checkNumberBlank(ctx, model);
    _checkNameBlank(ctx, model);
    _checkNumberDup(ctx, model);
//    _checkNameDup(ctx, model);
//    ((ActivityTypeInfo)model).validate();
  }

  protected IObjectPK _addnew(Context ctx, IObjectValue model)
    throws BOSException, EASBizException
  {
    addnewCheck(ctx, model);
    return super._addnew(ctx, model);
  }

  protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model)
    throws BOSException, EASBizException
  {
    addnewCheck(ctx, model);
    super._addnew(ctx, pk, model);
  }

  protected void updateCheck(Context ctx, IObjectPK pk, IObjectValue model)
    throws BOSException, EASBizException
  {
    _checkNumberBlank(ctx, model);
    _checkNameBlank(ctx, model);
//    ((CityInfo)model).validate();
    XRDataBaseInfo oldModel = getXRDataBaseInfo(ctx, pk);
    if (!((XRDataBaseInfo)model).getNumber().equals(oldModel.getNumber()))
    {
      _checkNumberDup(ctx, model);
    }

    if (!((XRDataBaseInfo)model).getName().equals(oldModel.getName()))
    {
      _checkNameDup(ctx, model);
    }
  }

  protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
    throws BOSException, EASBizException
  {
    updateCheck(ctx, pk, model);
    super._update(ctx, pk, model);
  }
    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model)
	throws BOSException, EASBizException {
    	 super._cancel(ctx, pk, model);
    	 XRDataBaseInfo info = (XRDataBaseInfo)model;
	info.setIsUsed(false); 
	_update(ctx,new ObjectUuidPK(info.getId()),info);
}

protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model)
	throws BOSException, EASBizException {
	 super._cancelCancel(ctx, pk, model);
	 XRDataBaseInfo info = (XRDataBaseInfo)model;
	info.setIsUsed(true); 
	_update(ctx,new ObjectUuidPK(info.getId()),info);
}
}