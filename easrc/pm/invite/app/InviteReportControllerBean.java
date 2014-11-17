package com.kingdee.eas.port.pm.invite.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.port.pm.invest.ProjectBudgetFacadeFactory;
import com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo;
import com.kingdee.eas.port.pm.invest.uitls.CLUtil;
import com.kingdee.eas.port.pm.invite.InviteReportEntry4Info;
import com.kingdee.eas.port.pm.invite.InviteReportFactory;
import com.kingdee.eas.port.pm.invite.InviteReportInfo;
import com.kingdee.eas.xr.app.XRBillException;

public class InviteReportControllerBean extends AbstractInviteReportControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.pm.invite.app.InviteReportControllerBean");

	protected void _audit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		InviteReportInfo info = InviteReportFactory.getLocalInstance(ctx).getInviteReportInfo(pk);
		IProgrammingEntryCostEntry iProgrammingEntryCostEntry = ProgrammingEntryCostEntryFactory.getLocalInstance(ctx);
		for (int i = 0; i <info.getEntry4().size(); i++) {
			InviteReportEntry4Info entry = info.getEntry4().get(i);
			ProgrammingEntryCostEntryInfo budgetInfo= iProgrammingEntryCostEntry.getProgrammingEntryCostEntryInfo(new ObjectUuidPK(entry.getBudgetNumber().getId()));
			String projectNumber = budgetInfo.getNumber();
			String budgetNumber = budgetInfo.getFeeNumber();
			String budgetName = budgetInfo.getFeeName();
			String year = budgetInfo.getYear();
			String[] str = ProjectBudgetFacadeFactory.getLocalInstance(ctx).subBudgetAmount(projectNumber,year,budgetNumber
																						,String.valueOf(entry.getAmount()),CLUtil.stag_sb,true,"0");
			if("Ê§°Ü".equals(str[0])){
				 throw new XRBillException(XRBillException.NOBUDGET, new Object[] {
						 "Ô¤Ëã±àÂë£º"+budgetNumber+","+budgetName , str[1]
			            });
			}
		}
		super._audit(ctx, pk);
	}

	protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		InviteReportInfo info = InviteReportFactory.getLocalInstance(ctx).getInviteReportInfo(pk);
		IProgrammingEntryCostEntry iProgrammingEntryCostEntry = ProgrammingEntryCostEntryFactory.getLocalInstance(ctx);
		for (int i = 0; i <info.getEntry4().size(); i++) {
			InviteReportEntry4Info entry = info.getEntry4().get(i);
			ProgrammingEntryCostEntryInfo budgetInfo= iProgrammingEntryCostEntry.getProgrammingEntryCostEntryInfo(new ObjectUuidPK(entry.getBudgetNumber().getId()));
			String projectNumber = budgetInfo.getNumber();
			String budgetNumber = budgetInfo.getFeeNumber();
			String budgetName = budgetInfo.getFeeName();
			String year = budgetInfo.getYear();
			String[] str = ProjectBudgetFacadeFactory.getLocalInstance(ctx).backBudgetAmount(projectNumber,year,budgetNumber
																						,String.valueOf(entry.getAmount()),CLUtil.stag_sb,true,"0");
			if("Ê§°Ü".equals(str[0])){
				 throw new XRBillException(XRBillException.NOBUDGET, new Object[] {
						 "Ô¤Ëã±àÂë£º"+budgetNumber+","+budgetName , str[1]
			            });
			}
		}
		super._unAudit(ctx, pk);
	}
    
}