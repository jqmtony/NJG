package com.kingdee.eas.fdc.costdb.app;

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
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.BillBaseControllerBean;
import com.kingdee.eas.fdc.costdb.MarketInfoCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.costdb.MarketInfoInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.BillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

public class MarketInfoControllerBean extends AbstractMarketInfoControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.costdb.app.MarketInfoControllerBean");
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		return super._save(ctx, model);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		MarketInfoInfo info = (MarketInfoInfo)model;
		handleIntermitNumber(ctx,info);
		return super._submit(ctx, model);
	}
	/**
	 * 处理
	 * @author sxhong  		Date 2006-11-25
	 */
	protected void handleIntermitNumber(Context ctx, MarketInfoInfo info) throws BOSException, CodingRuleException, EASBizException
	{
		
		//如果用户在客户端手工选择了断号,则此处不必在抢号
		if(info.getNumber() != null && info.getNumber().length() > 0) return;
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		
		//对成本中心进行处理
		String costUnitId= ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
			//ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		
		
       if(StringUtils.isEmpty(costUnitId)){
    	   return;
       }
       boolean isExist = true;
//       if (!iCodingRuleManager.isExist(info, costUnitId)){
//    	   costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
      		if (!iCodingRuleManager.isExist(info, costUnitId)){
      			isExist = false; 
           }
//      }
       if(isExist){
    	   // 选择了断号支持或者没有选择新增显示,获取并设置编号
           if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId) || !iCodingRuleManager.isAddView(info, costUnitId))
           // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
           {
               // 启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
               String number = iCodingRuleManager.getNumber(info,costUnitId);
               info.setNumber(number);
           }
       }
	}
}