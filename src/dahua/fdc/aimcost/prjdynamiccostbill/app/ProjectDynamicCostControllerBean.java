package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.app;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrysAccountCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEntrysAccountInfo;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.earlywarn.DHWarnMsgFacadeFactory;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;

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
		
		//modify by yxl 20160303 建安动态跟踪表审核时，以科目为纬度的页签，当差异率大于“强控指标”
		//一级科目 开发成本总额 >=102；各二级科目>=102；三级科目 主体工程4101.03.03>106
		ProjectDynamicCostEntrysAccountCollection acctColl = info.getEntrysAccount();
		ProjectDynamicCostEntrysAccountInfo acctInfo = null;
		BigDecimal onePointTwo = new BigDecimal("-0.02");
		BigDecimal onePointSix = new BigDecimal("-0.06");
		Set<String> caNumbers = new HashSet<String>();
		for(int i = acctColl.size()-1; i >= 0; i--) {
			acctInfo = acctColl.get(i);
			if(acctInfo.getLevel()==1 && acctInfo.getDiffRate()!=null && acctInfo.getDiffRate().compareTo(onePointTwo)<=0){
				caNumbers.add(acctInfo.getCostAccountNumber());
			}
			if(acctInfo.getLevel()==2 && acctInfo.getDiffRate()!=null && acctInfo.getDiffRate().compareTo(onePointTwo)<=0){
				caNumbers.add(acctInfo.getCostAccountNumber());
			}
			if(acctInfo.getLevel()==3 && acctInfo.getDiffRate()!=null && "4101.03.03".equals(acctInfo.getCostAccountNumber()) 
					&& acctInfo.getDiffRate().compareTo(onePointSix)<0){
				caNumbers.add(acctInfo.getCostAccountNumber());
			}
		}
		if(caNumbers.size() > 0){
			StringBuffer params = new StringBuffer();
			for(Iterator<String> it=caNumbers.iterator(); it.hasNext();) {
				params.append("'");
				params.append(it.next());
				params.append("',");
			}
			params.setLength(params.length()-1);
			StringBuffer sb = new StringBuffer();
			sb.append("update T_CON_ProgrammingContract set cfisqk=1 where FID in");
			sb.append("(select pcont.fid from T_CON_ProgrammingContract pcont left join T_CON_Programming program on pcont.FPROGRAMMINGID=program.fid");
			sb.append(" left join T_CON_ProgrammingContracCost pccost on pcont.fid=pccost.FCONTRACTID ");
			sb.append(" left join T_FDC_CostAccount costAccount on costAccount.fid=pccost.FCOSTACCOUNTID ");
			//把合约规划的状态和是否最新两个条件去掉，以达到这个项目下所有版本的合约规划都一样  and program.FSTATE='4AUDITTED' and program.FISLATEST=1
			sb.append("where program.fprojectid='"+curProject.getId().toString()+"'  ");
			sb.append("and costAccount.FLONGNUMBER in("+params.toString()+"))");
			DbUtil.execute(ctx,sb.toString());
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