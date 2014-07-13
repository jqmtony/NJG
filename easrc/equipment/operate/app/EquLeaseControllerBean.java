package com.kingdee.eas.port.equipment.operate.app;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.xr.XRBillBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.xr.app.XRBillBaseControllerBean;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.port.equipment.base.enumbase.nowStatusType;
import com.kingdee.eas.port.equipment.operate.EqmIOInfo;
import com.kingdee.eas.port.equipment.operate.EquLeaseE1Collection;
import com.kingdee.eas.port.equipment.operate.EquLeaseE1Factory;
import com.kingdee.eas.port.equipment.operate.EquLeaseE1Info;
import com.kingdee.eas.port.equipment.operate.EquLeaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.port.equipment.operate.EquLeaseCollection;
import com.kingdee.eas.port.equipment.record.EquIdFactory;
import com.kingdee.eas.port.equipment.record.EquIdInfo;

public class EquLeaseControllerBean extends AbstractEquLeaseControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.equipment.operate.app.EquLeaseControllerBean");
    
		    protected void _audit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		    	super._audit(ctx, pk);
		    	EquLeaseInfo eoInfo = getEquLeaseInfo(ctx, pk);
		    	EquLeaseE1Collection eleCollection = EquLeaseE1Factory.getLocalInstance(ctx).getEquLeaseE1Collection("where parent = '"+eoInfo.getId()+"'");
		    	for (int i = 0; i < eleCollection.size(); i++) {
		    		String id = ((EquIdInfo)eleCollection.get(i).getEquNumber()).getId().toString();
		    		EquIdInfo eqInfo = EquIdFactory.getLocalInstance(ctx).getEquIdInfo(new ObjectUuidPK(id));
		    		eqInfo.setNowStatus(nowStatusType.outrent);
		    		EquIdFactory.getLocalInstance(ctx).update(new ObjectUuidPK(eqInfo.getId()), eqInfo);
				}
		    }
		    
		    protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		    	super._unAudit(ctx, pk);
		    	EquLeaseInfo eoInfo = getEquLeaseInfo(ctx, pk);
		    	EquLeaseE1Collection eleCollection = EquLeaseE1Factory.getLocalInstance(ctx).getEquLeaseE1Collection("where parent = '"+eoInfo.getId()+"'");
		    	for (int i = 0; i < eleCollection.size(); i++) {
		    		String id = ((EquIdInfo)eleCollection.get(i).getEquNumber()).getId().toString();
		    		EquIdInfo eqInfo = EquIdFactory.getLocalInstance(ctx).getEquIdInfo(new ObjectUuidPK(id));
		    		eqInfo.setNowStatus(nowStatusType.NULL);
		    		EquIdFactory.getLocalInstance(ctx).update(new ObjectUuidPK(eqInfo.getId()), eqInfo);
				}
		    }
}