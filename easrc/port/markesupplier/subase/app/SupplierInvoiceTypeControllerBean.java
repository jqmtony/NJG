package com.kingdee.eas.port.markesupplier.subase.app;

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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeFactory;
import com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeInfo;

public class SupplierInvoiceTypeControllerBean extends AbstractSupplierInvoiceTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.markesupplier.subase.app.SupplierInvoiceTypeControllerBean");
    protected IObjectPK _addnew(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	SupplierInvoiceTypeInfo info = (SupplierInvoiceTypeInfo)model;
    	info.setIsEnable(true);
    	return super._addnew(ctx, model);
    }
    
    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
    	super._cancel(ctx, pk, model);
    	SupplierInvoiceTypeInfo Info = (SupplierInvoiceTypeInfo)model;
    	Info.setIsEnable(false);
    	SupplierInvoiceTypeFactory.getLocalInstance(ctx).update(new ObjectUuidPK(Info.getId()), Info);
    }
    
    protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
    	super._cancelCancel(ctx, pk, model);
    	SupplierInvoiceTypeInfo Info = (SupplierInvoiceTypeInfo)model;
    	Info.setIsEnable(true);
    	SupplierInvoiceTypeFactory.getLocalInstance(ctx).update(new ObjectUuidPK(Info.getId()), Info);
    }
}