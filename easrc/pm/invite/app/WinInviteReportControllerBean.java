package com.kingdee.eas.port.pm.invite.app;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockFactory;
import com.kingdee.eas.port.pm.invest.ProjectBudgetFacadeFactory;
import com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo;
import com.kingdee.eas.port.pm.invest.uitls.CLUtil;
import com.kingdee.eas.port.pm.invite.WinInviteReportBudgetEntryInfo;
import com.kingdee.eas.port.pm.invite.WinInviteReportFactory;
import com.kingdee.eas.port.pm.invite.WinInviteReportInfo;
import com.kingdee.eas.xr.app.XRBillException;

public class WinInviteReportControllerBean extends AbstractWinInviteReportControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.pm.invite.app.WinInviteReportControllerBean");
    
    protected void _audit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		WinInviteReportInfo info = WinInviteReportFactory.getLocalInstance(ctx).getWinInviteReportInfo(pk);
		IProgrammingEntryCostEntry iProgrammingEntryCostEntry = ProgrammingEntryCostEntryFactory.getLocalInstance(ctx);
		for (int i = 0; i <info.getBudgetEntry().size(); i++) {
			WinInviteReportBudgetEntryInfo entry = info.getBudgetEntry().get(i);
			ProgrammingEntryCostEntryInfo budgetInfo= iProgrammingEntryCostEntry.getProgrammingEntryCostEntryInfo(new ObjectUuidPK(entry.getBudgetNumber().getId()));
			String projectNumber = budgetInfo.getNumber();
			String budgetNumber = budgetInfo.getFeeNumber();
			String budgetName = budgetInfo.getFeeName();
			String year = budgetInfo.getYear();
			BigDecimal backAmount = UIRuleUtil.getBigDecimal(entry.getDiffAmount());
			String[] str = ProjectBudgetFacadeFactory.getLocalInstance(ctx).subBudgetAmount(projectNumber,year,budgetNumber
																 ,String.valueOf(entry.getAmount()),CLUtil.stag_zb,true,backAmount.toString());
			if("Ê§°Ü".equals(str[0])){
				 throw new XRBillException(XRBillException.NOBUDGET, new Object[] {
						 "Ô¤Ëã±àÂë£º"+budgetNumber+","+budgetName , str[1]
			            });
			}
		}
		super._audit(ctx, pk);
		MarketSupplierStockFactory.getLocalInstance(ctx).addToSysSupplier(info.getWinInviteUnit());
	}

	protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		WinInviteReportInfo info = WinInviteReportFactory.getLocalInstance(ctx).getWinInviteReportInfo(pk);
		IProgrammingEntryCostEntry iProgrammingEntryCostEntry = ProgrammingEntryCostEntryFactory.getLocalInstance(ctx);
		for (int i = 0; i <info.getBudgetEntry().size(); i++) {
			WinInviteReportBudgetEntryInfo entry = info.getBudgetEntry().get(i);
			ProgrammingEntryCostEntryInfo budgetInfo= iProgrammingEntryCostEntry.getProgrammingEntryCostEntryInfo(new ObjectUuidPK(entry.getBudgetNumber().getId()));
			String projectNumber = budgetInfo.getNumber();
			String budgetNumber = budgetInfo.getFeeNumber();
			String budgetName = budgetInfo.getFeeName();
			String year = budgetInfo.getYear();
			BigDecimal backAmount = UIRuleUtil.getBigDecimal(entry.getDiffAmount());
			String[] str = ProjectBudgetFacadeFactory.getLocalInstance(ctx).backBudgetAmount(projectNumber,year,budgetNumber
																,String.valueOf(entry.getAmount()),CLUtil.stag_zb,true,backAmount.toString());
			if("Ê§°Ü".equals(str[0])){
				 throw new XRBillException(XRBillException.NOBUDGET, new Object[] {
						 "Ô¤Ëã±àÂë£º"+budgetNumber+","+budgetName , str[1]
			            });
			}
		}
		super._unAudit(ctx, pk);
	}
    
}