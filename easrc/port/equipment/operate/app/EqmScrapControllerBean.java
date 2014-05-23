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
import com.kingdee.eas.port.equipment.base.enumbase.sbStatusType;
import com.kingdee.eas.port.equipment.operate.ComproductionInfo;
import com.kingdee.eas.port.equipment.operate.EqmScrapCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.port.equipment.operate.EqmScrapInfo;
import com.kingdee.eas.port.equipment.record.EquIdFactory;
import com.kingdee.eas.port.equipment.record.EquIdInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class EqmScrapControllerBean extends AbstractEqmScrapControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.equipment.operate.app.EqmScrapControllerBean");
    public void audit(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {
    	super.audit(ctx, pk);
    	EqmScrapInfo eqmInfo = getEqmScrapInfo(ctx, pk);
    	 if(eqmInfo.getEqmNumber()!=null){
         	String id = ((EquIdInfo)eqmInfo.getEqmNumber()).getId().toString();
         	EquIdInfo eqInfo = EquIdFactory.getLocalInstance(ctx).getEquIdInfo(new ObjectUuidPK(id));
         	eqInfo.setSbStatus(sbStatusType.discarded);
         	EquIdFactory.getLocalInstance(ctx).update(new ObjectUuidPK(eqInfo.getId()), eqInfo);
         }
    }
    

    public void unAudit(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {
    	super.unAudit(ctx, pk);
    	EqmScrapInfo eqmInfo = getEqmScrapInfo(ctx, pk);
   	 if(eqmInfo.getEqmNumber()!=null){
        	String id = ((EquIdInfo)eqmInfo.getEqmNumber()).getId().toString();
        	EquIdInfo eqInfo = EquIdFactory.getLocalInstance(ctx).getEquIdInfo(new ObjectUuidPK(id));
        	eqInfo.setSbStatus(sbStatusType.inUse);
        	EquIdFactory.getLocalInstance(ctx).update(new ObjectUuidPK(eqInfo.getId()), eqInfo);
        }
    }
}