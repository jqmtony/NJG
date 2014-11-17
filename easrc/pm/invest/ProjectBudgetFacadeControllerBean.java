package com.kingdee.eas.port.pm.invest;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo;
import com.kingdee.eas.port.pm.invest.uitls.CLUtil;

public class ProjectBudgetFacadeControllerBean extends AbstractProjectBudgetFacadeControllerBean
{
    private static Logger logger = Logger.getLogger("com.kingdee.eas.port.pm.invest.ProjectBudgetFacadeControllerBean");
    
    /**
     * backAmount:节省金额（负数为节省，正数为超支）
     * */
	protected String[] _subBudgetAmount(Context ctx, String projectNumber,String year, 
			String budgetNumber, String amount,String stag,boolean isBack,String backAmount)throws BOSException {
		super._subBudgetAmount(ctx, projectNumber, year, budgetNumber, amount,stag,isBack,backAmount);
		String[] str = new String[3];
		str[0] = "成功";
		String where = "where number='"+projectNumber+"' and feeNumber='"+budgetNumber+"' and beizhu='最新'";
		BigDecimal amt = UIRuleUtil.getBigDecimal(amount);
		BigDecimal backAmt = UIRuleUtil.getBigDecimal(backAmount);
		ProgrammingEntryCostEntryCollection coll = ProgrammingEntryCostEntryFactory.getLocalInstance(ctx).getProgrammingEntryCostEntryCollection(where);
		IProgrammingEntryCostEntry iProgrammingEntryCostEntry = ProgrammingEntryCostEntryFactory.getLocalInstance(ctx);
		for (int i = 0; i < coll.size(); i++) {
			ProgrammingEntryCostEntryInfo info = coll.get(i);
			BigDecimal budgetAmount = UIRuleUtil.getBigDecimal(info.getContractAssign());
			BigDecimal reportInviteAmount = UIRuleUtil.getBigDecimal(info.getInvitReportedAmount());
			BigDecimal InvitedAmount = UIRuleUtil.getBigDecimal(info.getInvitedAmount());
			BigDecimal contractAmount = UIRuleUtil.getBigDecimal(info.getContractedAmount());
			BigDecimal nocontractAmount = UIRuleUtil.getBigDecimal(info.getNoContractedAmount());
			BigDecimal noInviteContractAmount = UIRuleUtil.getBigDecimal(info.getNoInviteContractAmount());
			
			if(CLUtil.stag_sb.equals(stag)){
				BigDecimal balanceAmount = budgetAmount.subtract(reportInviteAmount).subtract(nocontractAmount).subtract(noInviteContractAmount);
				if(balanceAmount.subtract(amt).intValue()<0){
					str[0] = "失败";
					str[1] = "招标申报可用预算不足，剩余预算为："+balanceAmount;
					return str;
				}
			}
			else if(CLUtil.stag_zb.equals(stag)){
				BigDecimal balanceAmount = budgetAmount.subtract(InvitedAmount).subtract(nocontractAmount).subtract(noInviteContractAmount);
				if(balanceAmount.subtract(amt).intValue()<0){
					str[0] = "失败";
					str[1] = "中标可用预算不足，剩余预算为："+balanceAmount;
					return str;
				}
			}
			else if(CLUtil.stag_ht.equals(stag)){
				BigDecimal balanceAmount = budgetAmount.subtract(contractAmount).subtract(nocontractAmount).subtract(noInviteContractAmount);
				if(balanceAmount.subtract(amt).intValue()<0){
					str[0] = "失败";
					str[1] = "合同可用预算不足，剩余预算为："+balanceAmount;
					return str;
				}
			}
			else if(CLUtil.stag_wht.equals(stag)){
				BigDecimal balanceAmount = budgetAmount.subtract(contractAmount).subtract(nocontractAmount).subtract(noInviteContractAmount);
				if(balanceAmount.subtract(amt).intValue()<0){
					str[0] = "失败";
					str[1] = "无文本合同可用预算不足，剩余预算为："+balanceAmount;
					return str;
				}
			}
		}
		
		for (int i = 0; i < coll.size(); i++) {
			ProgrammingEntryCostEntryInfo info = coll.get(i);
			if(CLUtil.stag_sb.equals(stag)){
				info.setInvitReportedAmount(UIRuleUtil.getBigDecimal(info.getInvitReportedAmount()).add(amt));
			}
			else if(CLUtil.stag_zb.equals(stag)){
				if(isBack){
					info.setInvitReportedAmount(UIRuleUtil.getBigDecimal(info.getInvitReportedAmount()).add(backAmt));
				}
				info.setInvitedAmount(UIRuleUtil.getBigDecimal(info.getInvitedAmount()).add(amt));
			}
			else if(CLUtil.stag_ht.equals(stag)){
				if(isBack){
					info.setInvitReportedAmount(UIRuleUtil.getBigDecimal(info.getInvitReportedAmount()).add(backAmt));
					info.setInvitedAmount(UIRuleUtil.getBigDecimal(info.getInvitedAmount()).add(backAmt));
				}
				info.setContractedAmount(UIRuleUtil.getBigDecimal(info.getContractedAmount()).add(amt));
				info.setBalanceAmount(UIRuleUtil.getBigDecimal(info.getBalanceAmount()).subtract(amt));
			}
			else if(CLUtil.stag_htjs.equals(stag)){
				if(isBack){
					info.setInvitReportedAmount(UIRuleUtil.getBigDecimal(info.getInvitReportedAmount()).add(backAmt));
					info.setInvitedAmount(UIRuleUtil.getBigDecimal(info.getInvitedAmount()).add(backAmt));
					info.setContractedAmount(UIRuleUtil.getBigDecimal(info.getContractedAmount()).add(backAmt));
					info.setBalanceAmount(UIRuleUtil.getBigDecimal(info.getBalanceAmount()).add(backAmt));
				}
			}else if(CLUtil.stag_wzbht.equals(stag)){
				info.setNoInviteContractAmount(UIRuleUtil.getBigDecimal(info.getNoInviteContractAmount()).add(amt));
				info.setBalanceAmount(UIRuleUtil.getBigDecimal(info.getBalanceAmount()).subtract(amt));
			}
			else if(CLUtil.stag_wzbhtjs.equals(stag)){
				if(isBack){
					info.setInvitReportedAmount(UIRuleUtil.getBigDecimal(info.getInvitReportedAmount()).add(backAmt));
					info.setInvitedAmount(UIRuleUtil.getBigDecimal(info.getInvitedAmount()).add(backAmt));
					info.setNoInviteContractAmount(UIRuleUtil.getBigDecimal(info.getNoInviteContractAmount()).add(backAmt));
					info.setBalanceAmount(UIRuleUtil.getBigDecimal(info.getBalanceAmount()).add(backAmt));
				}
			}
			else if(CLUtil.stag_wht.equals(stag)){
				info.setNoContractedAmount(UIRuleUtil.getBigDecimal(info.getNoContractedAmount()).add(amt));
				info.setBalanceAmount(UIRuleUtil.getBigDecimal(info.getBalanceAmount()).subtract(amt));
			}else if(CLUtil.stag_fksq.equals(stag)){
				info.setRequestPayAmount(UIRuleUtil.getBigDecimal(info.getRequestPayAmount()).add(amt));
			}else if(CLUtil.stag_yfk.equals(stag)){
				info.setPayedAmount(UIRuleUtil.getBigDecimal(info.getPayedAmount()).add(amt));
			}
			try {
				iProgrammingEntryCostEntry.update(new ObjectUuidPK(info.getId()), info);
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	/**
     * backAmount:节省金额（负数为节省，正数为超支）
     * */
	protected String[] _backBudgetAmount(Context ctx, String projectNumber,String year, 
			String budgetNumber, String amount,String stag,boolean isBack,String backAmount)throws BOSException {
		super._backBudgetAmount(ctx, projectNumber, year, budgetNumber, amount,stag,isBack,backAmount);
		String[] str = new String[3];
		String where = "where number='"+projectNumber+"' and feeNumber='"+budgetNumber+"' and beizhu='最新'";
		BigDecimal amt = UIRuleUtil.getBigDecimal(amount);
		BigDecimal backAmt = UIRuleUtil.getBigDecimal(backAmount);
		ProgrammingEntryCostEntryCollection coll = ProgrammingEntryCostEntryFactory.getLocalInstance(ctx).getProgrammingEntryCostEntryCollection(where);
		IProgrammingEntryCostEntry iProgrammingEntryCostEntry = ProgrammingEntryCostEntryFactory.getLocalInstance(ctx);
		for (int i = 0; i < coll.size(); i++) {
			ProgrammingEntryCostEntryInfo info = coll.get(i);
			if(CLUtil.stag_sb.equals(stag)){
				info.setInvitReportedAmount(UIRuleUtil.getBigDecimal(info.getInvitReportedAmount()).subtract(amt));
			}
			else if(CLUtil.stag_zb.equals(stag)){
				if(isBack){
					info.setInvitReportedAmount(UIRuleUtil.getBigDecimal(info.getInvitReportedAmount()).subtract(backAmt));
				}
				info.setInvitedAmount(UIRuleUtil.getBigDecimal(info.getInvitedAmount()).subtract(amt));
			}
			else if(CLUtil.stag_ht.equals(stag)){
				if(isBack){
					info.setInvitReportedAmount(UIRuleUtil.getBigDecimal(info.getInvitReportedAmount()).subtract(backAmt));
					info.setInvitedAmount(UIRuleUtil.getBigDecimal(info.getInvitedAmount()).subtract(backAmt));
				}
				info.setContractedAmount(UIRuleUtil.getBigDecimal(info.getContractedAmount()).subtract(amt));
				info.setBalanceAmount(UIRuleUtil.getBigDecimal(info.getBalanceAmount()).add(amt));
			}
			else if(CLUtil.stag_htjs.equals(stag)){
				if(isBack){
					info.setInvitReportedAmount(UIRuleUtil.getBigDecimal(info.getInvitReportedAmount()).subtract(backAmt));
					info.setInvitedAmount(UIRuleUtil.getBigDecimal(info.getInvitedAmount()).subtract(backAmt));
					info.setContractedAmount(UIRuleUtil.getBigDecimal(info.getContractedAmount()).subtract(backAmt));
					info.setBalanceAmount(UIRuleUtil.getBigDecimal(info.getBalanceAmount()).subtract(backAmt));
				}
			}else if(CLUtil.stag_wzbht.equals(stag)){
				info.setNoInviteContractAmount(UIRuleUtil.getBigDecimal(info.getNoInviteContractAmount()).subtract(amt));
				info.setBalanceAmount(UIRuleUtil.getBigDecimal(info.getBalanceAmount()).add(amt));
			}
			else if(CLUtil.stag_wzbhtjs.equals(stag)){
				if(isBack){
					info.setInvitReportedAmount(UIRuleUtil.getBigDecimal(info.getInvitReportedAmount()).subtract(backAmt));
					info.setInvitedAmount(UIRuleUtil.getBigDecimal(info.getInvitedAmount()).subtract(backAmt));
					info.setNoInviteContractAmount(UIRuleUtil.getBigDecimal(info.getNoInviteContractAmount()).subtract(backAmt));
					info.setBalanceAmount(UIRuleUtil.getBigDecimal(info.getBalanceAmount()).subtract(backAmt));
				}
			}
			else if(CLUtil.stag_wht.equals(stag)){
				info.setNoContractedAmount(UIRuleUtil.getBigDecimal(info.getNoContractedAmount()).subtract(amt));
				info.setBalanceAmount(UIRuleUtil.getBigDecimal(info.getBalanceAmount()).add(amt));
			}
			else if(CLUtil.stag_fksq.equals(stag)){
				info.setRequestPayAmount(UIRuleUtil.getBigDecimal(info.getRequestPayAmount()).subtract(amt));
			}
			else if(CLUtil.stag_yfk.equals(stag)){
				info.setPayedAmount(UIRuleUtil.getBigDecimal(info.getPayedAmount()).subtract(amt));
			}
			try {
				iProgrammingEntryCostEntry.update(new ObjectUuidPK(info.getId()), info);
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
    
}