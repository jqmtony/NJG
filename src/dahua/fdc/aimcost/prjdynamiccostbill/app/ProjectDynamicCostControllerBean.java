package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

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
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostCollection;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.earlywarn.DHWarnMsgFacadeFactory;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;

public class ProjectDynamicCostControllerBean extends AbstractProjectDynamicCostControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.app.ProjectDynamicCostControllerBean");
    /**
     * 提交
     */
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	ProjectDynamicCostInfo info = (ProjectDynamicCostInfo) model;
    	info.setState(FDCBillStateEnum.SUBMITTED);
    	return super._submit(ctx, info);
    }
    /**
     * 审核
     */
    protected void _audit(Context ctx, IObjectValue model) throws BOSException {
    	ProjectDynamicCostInfo info = (ProjectDynamicCostInfo) model;
    	CurProjectInfo curProject = info.getCurProject();
    	int version  = info.getVersion()-1; //上一版
    	int year = info.getYear();
    	int month = info.getMonth();
    	
    	info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
    	info.setAuditTime(Calendar.getInstance().getTime());
    	info.setState(FDCBillStateEnum.AUDITTED);
    	info.setIsLatest(true);
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("auditor"));
    	sic.add(new SelectorItemInfo("auditTime"));
    	sic.add(new SelectorItemInfo("state"));
    	sic.add(new SelectorItemInfo("isLatest"));
    	try {
			_updatePartial(ctx, info, sic);
			

			FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx); 
			fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
			
			StringBuilder sql = new StringBuilder();
			sql.append(" update CT_AIM_ProjectDynamicCost set CFIsLatest = '0'");
			sql.append(" where CFCurProjectID ='"+curProject.getId().toString()+"'");
			sql.append(" and FYear='"+year+"'");
			sql.append(" and FMonth='"+month+"'");
			sql.append(" and CFVersion='"+version+"'");
			fdcSB.appendSql(sql.toString());
			fdcSB.execute();
			//审批通过发送预警消息
			DHWarnMsgFacadeFactory.getLocalInstance(ctx).aimCostDiffWarnMsg(info.getId().toString());
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    /**
     * 反审核
     */
    protected void _unAudit(Context ctx, IObjectValue model)
    		throws BOSException {
    	ProjectDynamicCostInfo info = (ProjectDynamicCostInfo) model;
    	CurProjectInfo curProject = info.getCurProject();
    	int version  = info.getVersion()-1; //上一版
    	int year = info.getYear();
    	int month = info.getMonth();
    	
    	info.setAuditor(null);
    	info.setAuditTime(null);
    	info.setState(FDCBillStateEnum.SUBMITTED);
    	info.setIsLatest(false);
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("auditor"));
    	sic.add(new SelectorItemInfo("auditTime"));
    	sic.add(new SelectorItemInfo("state"));
    	sic.add(new SelectorItemInfo("isLatest"));
    	try {
			_updatePartial(ctx, info, sic);
			FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
			fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
			
			StringBuilder sql = new StringBuilder();
			sql.append(" update CT_AIM_ProjectDynamicCost set CFIsLatest = '1'");
			sql.append(" where CFCurProjectID ='"+curProject.getId().toString()+"'");
			sql.append(" and FYear='"+year+"'");
			sql.append(" and FMonth='"+month+"'");
			sql.append(" and CFVersion='"+version+"'");
			fdcSB.appendSql(sql.toString());
			fdcSB.execute();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
}