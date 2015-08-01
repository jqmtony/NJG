package com.kingdee.eas.fdc.material.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

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
import java.math.BigDecimal;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.material.IMaterialEnterPlanBill;
import com.kingdee.eas.fdc.material.IMaterialEnterPlanEntry;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillFactory;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryFactory;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryInfo;
import com.kingdee.eas.fdc.material.MaterialEnterSumInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.material.MaterialEnterSumCollection;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class MaterialEnterSumControllerBean extends AbstractMaterialEnterSumControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.material.app.MaterialEnterSumControllerBean");

	protected boolean _refSetPlanSumQty(Context ctx, List refList,
			FDCBillStateEnum state) throws BOSException {
		if(refList == null)
			return false;
		String id = "";
		BigDecimal sumQty = null;
		BigDecimal oldSumQty =null;
		for(int i=0;i<refList.size();i++){
			Map map = (Map)refList.get(i);
			if(map.get("sourceBillId")==null)
				break ;
			id = (String)map.get("sourceBillId");
			sumQty = (BigDecimal)map.get("sumQty");
			try {
	            IMaterialEnterPlanEntry iface=  MaterialEnterPlanEntryFactory.getLocalInstance(ctx);
	            MaterialEnterPlanEntryInfo info = iface.getMaterialEnterPlanEntryInfo(new ObjectUuidPK(id));
				if(info == null)
					break;
				oldSumQty = (info.getHasSumQty()==null)?(new BigDecimal(0)):info.getHasSumQty();
				//若是审批事件,则增加已汇总数量
				if(FDCBillStateEnum.AUDITTED.equals(state)){
					info.setHasSumQty(oldSumQty.add(sumQty));
				}
				else //若是反审批事件，则扣减已汇总数量
				{
				   info.setHasSumQty(oldSumQty.subtract(sumQty));				
				}
				iface.update(new ObjectUuidPK(info.getId()), info);//更材料进场计划分录中“已汇总数量”
			} catch (EASBizException e) {
                logger.error("refSet MaterialEnterPlanEntry from MaterialEnterSum error!");
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	private boolean checkHasSumQty(MaterialEnterPlanEntryInfo info,FDCBillStateEnum state,BigDecimal sumQty)
	{
		BigDecimal oldValue = info.getHasSumQty();
		if(oldValue==null)
			oldValue = new BigDecimal(0);
		if(FDCBillStateEnum.AUDITTED.equals(state))//审批
		{
			int compareResult = info.getQuantity().compareTo(oldValue.add(sumQty));
			if(compareResult<0){
				logger.error("MaterialEnterSum  hasSumQty over !");
				return false;
			}		
		}
		else {  //反审批判断已汇总数是否小于0
			
		}
		return true;
	}
	
	protected boolean isUseName() {
		return false;
	}
}