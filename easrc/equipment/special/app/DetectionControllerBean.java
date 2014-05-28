package com.kingdee.eas.port.equipment.special.app;

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
import com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo;
import com.kingdee.eas.port.equipment.special.DetectionInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.xr.XRBillBaseCollection;
import com.kingdee.eas.xr.XRBillBaseInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.xr.app.XRBillBaseControllerBean;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.port.equipment.special.DetectionCollection;

public class DetectionControllerBean extends AbstractDetectionControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.equipment.special.app.DetectionControllerBean");
  
    protected void _audit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
    	super._audit(ctx, pk);
    	DetectionInfo dtInfo = getDetectionInfo(ctx, pk);
    	if(dtInfo.getSourceBillId() != null){
	    	 AnnualYearDetailInfo ayInfo = AnnualYearDetailFactory.getLocalInstance(ctx).getAnnualYearDetailInfo(new ObjectUuidPK(dtInfo.getSourceBillId()));
	    	 ayInfo.setStatus(XRBillStatusEnum.FINISH);
	    	 AnnualYearDetailFactory.getLocalInstance(ctx).update((new ObjectUuidPK(ayInfo.getId())),ayInfo);
    	}
    }
    	
    
    protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {
    	super._unAudit(ctx, pk);
    	DetectionInfo dtInfo = getDetectionInfo(ctx, pk);
    	if(dtInfo.getSourceBillId() != null){
	    	 AnnualYearDetailInfo ayInfo = AnnualYearDetailFactory.getLocalInstance(ctx).getAnnualYearDetailInfo(new ObjectUuidPK(dtInfo.getSourceBillId()));
	    	 ayInfo.setStatus(XRBillStatusEnum.EXECUTION);
	    	 AnnualYearDetailFactory.getLocalInstance(ctx).update((new ObjectUuidPK(ayInfo.getId())),ayInfo);
    	}
    }
}