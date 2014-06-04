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

import com.kingdee.eas.basedata.assistant.AddressFactory;
import com.kingdee.eas.basedata.assistant.AddressInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.xr.XRBillBaseCollection;
import com.kingdee.eas.xr.XRBillBaseInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.xr.app.XRBillBaseControllerBean;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.port.equipment.operate.EqmIOCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.port.equipment.operate.EqmIOInfo;
import com.kingdee.eas.port.equipment.record.EquIdFactory;
import com.kingdee.eas.port.equipment.record.EquIdInfo;

public class EqmIOControllerBean extends AbstractEqmIOControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.equipment.operate.app.EqmIOControllerBean");
    //设备调出调入审核之后把调入单位、使用部门反写到设备档案的所属组织和使用部门
    protected void _audit(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {
    	super._audit(ctx, pk);
    	EqmIOInfo eoInfo = getEqmIOInfo(ctx, pk);
    	if(eoInfo.getEqmNumber()!=null){
    		String id = ((EquIdInfo)eoInfo.getEqmNumber()).getId().toString();
    		EquIdInfo edInfo = EquIdFactory.getLocalInstance(ctx).getEquIdInfo(new ObjectUuidPK(id));
    		if(eoInfo.getInOrgUnit()!=null&&eoInfo.getUseingOrgUnit()!=null){
    			edInfo.setSsOrgUnit(eoInfo.getInOrgUnit());
        		edInfo.setUsingDept(eoInfo.getUseingOrgUnit());
    		}
    		
    	}
    }
    
    //设备调出调入反审核之后把调出单位、原使用部门反写到设备档案的所属组织和使用部门
    protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {
    	super._unAudit(ctx, pk);
    	EqmIOInfo eoInfo = getEqmIOInfo(ctx, pk);
    	if(eoInfo.getEqmNumber()!=null){
    		String id = ((EquIdInfo)eoInfo.getEqmNumber()).getId().toString();
    		EquIdInfo edInfo = EquIdFactory.getLocalInstance(ctx).getEquIdInfo(new ObjectUuidPK(id));
    		if(eoInfo.getOutOrgUnit()!=null&&eoInfo.getOldUseingDept()!=null){
    			edInfo.setSsOrgUnit(eoInfo.getOutOrgUnit());
        		edInfo.setUsingDept(eoInfo.getOldUseingDept());
    		}
    		
    	}
    }
}