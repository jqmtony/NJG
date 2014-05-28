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

import com.kingdee.eas.port.equipment.maintenance.RepairOrderFactory;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory;
import com.kingdee.eas.port.equipment.special.AnnualYearPlan;
import com.kingdee.eas.port.equipment.special.AnnualYearPlanFactory;
import com.kingdee.eas.port.equipment.special.AnnualYearPlanInfo;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.base.btp.BTPTransformResult;
import com.kingdee.eas.base.btp.IBTPManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.DefineSysEnum;
import com.kingdee.bos.metadata.bot.IBOTMapping;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.port.equipment.special.AnnualYearPlanCollection;
import com.kingdee.eas.port.equipment.wdx.EqmOverhaulFactory;
import com.kingdee.eas.port.equipment.wdx.EqmOverhaulInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.fm.common.FMException;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.xr.XRBillBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.xr.app.XRBillBaseControllerBean;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.util.NumericExceptionSubItem;

public class AnnualYearPlanControllerBean extends AbstractAnnualYearPlanControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.equipment.special.app.AnnualYearPlanControllerBean");
		    protected void _audit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		    	super._audit(ctx, pk);
		    	IBOTMapping botMapping = BOTMappingFactory.getLocalInstance(ctx);
		    	BOTMappingInfo botInfo = (BOTMappingInfo) botMapping.getValue("where name='NJP000088'");
		    	if(botInfo== null)
		    	{
		    		throw new EASBizException(new NumericExceptionSubItem("100","规则错误，请检查BOTP！"));
		    	}
		    	try {
		    		String oql = "where id='"+pk.toString()+"'";
		    		CoreBillBaseCollection eqcollection = AnnualYearPlanFactory.getLocalInstance(ctx).getCoreBillBaseCollection(oql);
					generateDestBill(ctx, "0AFAAEEF", "22366297",eqcollection , new ObjectUuidPK(botInfo.getId()));
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		    
		    protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		    	AnnualYearPlanInfo aXRBillBaseInfo =  getAnnualYearPlanInfo(ctx, pk);
					checktranFormBill(ctx,aXRBillBaseInfo.getId().toString());
		    	super._unAudit(ctx, pk);
		    }

			protected void _unAudit(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException {
				for(int i=0;i<pks.length;i++){
					AnnualYearPlanInfo aXRBillBaseInfo =  getAnnualYearPlanInfo(ctx, pks[i]);
						checktranFormBill(ctx,aXRBillBaseInfo.getId().toString());
				}
				super._unAudit(ctx, pks);
			}
		    
			private void checktranFormBill(Context ctx,String Id) throws EASBizException, BOSException{
				if(AnnualYearDetailFactory.getLocalInstance(ctx).exists("where SourceBillID='"+Id+"'"))
				{
					throw new EASBizException(new NumericExceptionSubItem("100","所选单据已有下游单据，不允许反审核！"));
				}
			}
		    
		    /**
			 * 参数说明：
			 1、ctx ：服务端上下文
			 2、srcBosType：源单据的BosType
			 3、destBosType：目标单据的BosType
			 4、srcBillCollection：源单据集合，可以批量转换
			 5、botpPK：要使用的BOTP的PK。9AF44A6D
			 可以通过表T_BOT_Mapping查找到id,将id转换成PK。
			 select t.fid from T_BOT_Mapping t where t.fname='***'，***是botp的名称。
			 */
			public static void generateDestBill(Context ctx,String srcBosType,   
		            String destBosType, CoreBillBaseCollection srcBillCollection,   
		            IObjectPK botpPK) throws Exception {   
		        IBOTMapping botMapping = BOTMappingFactory.getLocalInstance(ctx);
		        CoreBillBaseInfo billInfo = srcBillCollection.get(0);   
		        BOTMappingInfo botMappingInfo = botMapping.getMapping(billInfo, destBosType, DefineSysEnum.BTP);   
		        if (botMappingInfo == null) {   
		            throw new FMException(FMException.NODESTBILL);   
		        }   
		        IBTPManager iBTPManager = BTPManagerFactory.getLocalInstance(ctx);
		        BTPTransformResult btpResult = null;   
		        btpResult = iBTPManager.transformForBotp(srcBillCollection,destBosType, botpPK);   
		        IObjectCollection destBillCols = btpResult.getBills();   
		        BOTRelationCollection botRelationCols = btpResult.getBOTRelationCollection();   
		        for (int i = 0; i < destBillCols.size(); i++) {   
		            CoreBillBaseInfo destBillInfo = (CoreBillBaseInfo) destBillCols.getObject(i);   
		            iBTPManager.submitRelations(destBillInfo, botRelationCols);   
		        }   
			}  
}