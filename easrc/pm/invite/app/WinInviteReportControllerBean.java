package com.kingdee.eas.port.pm.invite.app;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.management.LanguageCollection;
import com.kingdee.bos.metadata.management.SolutionInfo;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.workflow.participant.Person;
import com.kingdee.eas.base.permission.IUser;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockFactory;
import com.kingdee.eas.port.pm.invest.ProjectBudgetFacadeFactory;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1E2Info;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1Info;
import com.kingdee.eas.port.pm.invest.YIPlanAccredInfo;
import com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryCostEntry;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo;
import com.kingdee.eas.port.pm.invest.uitls.CLUtil;
import com.kingdee.eas.port.pm.invite.InviteReportEntry3Info;
import com.kingdee.eas.port.pm.invite.InviteReportFactory;
import com.kingdee.eas.port.pm.invite.InviteReportInfo;
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
//		IProgrammingEntryCostEntry iProgrammingEntryCostEntry = ProgrammingEntryCostEntryFactory.getLocalInstance(ctx);
//		for (int i = 0; i <info.getBudgetEntry().size(); i++) {
//			WinInviteReportBudgetEntryInfo entry = info.getBudgetEntry().get(i);
//			ProgrammingEntryCostEntryInfo budgetInfo= iProgrammingEntryCostEntry.getProgrammingEntryCostEntryInfo(new ObjectUuidPK(entry.getBudgetNumber().getId()));
//			String projectNumber = budgetInfo.getNumber();
//			String budgetNumber = budgetInfo.getFeeNumber();
//			String budgetName = budgetInfo.getFeeName();
//			String year = budgetInfo.getYear();
//			BigDecimal backAmount = UIRuleUtil.getBigDecimal(entry.getDiffAmount());
//			String[] str = ProjectBudgetFacadeFactory.getLocalInstance(ctx).subBudgetAmount(projectNumber,year,budgetNumber
//																 ,String.valueOf(entry.getAmount()),CLUtil.stag_zb,true,backAmount.toString());
//			if("失败".equals(str[0])){
//				 throw new XRBillException(XRBillException.NOBUDGET, new Object[] {
//						 "预算编码："+budgetNumber+","+budgetName , str[1]
//			            });
//			}
//		}
		super._audit(ctx, pk);
		MarketSupplierStockFactory.getLocalInstance(ctx).addToSysSupplier(info.getWinInviteUnit());
	}

	protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
//		WinInviteReportInfo info = WinInviteReportFactory.getLocalInstance(ctx).getWinInviteReportInfo(pk);
//		IProgrammingEntryCostEntry iProgrammingEntryCostEntry = ProgrammingEntryCostEntryFactory.getLocalInstance(ctx);
//		for (int i = 0; i <info.getBudgetEntry().size(); i++) {
//			WinInviteReportBudgetEntryInfo entry = info.getBudgetEntry().get(i);
//			ProgrammingEntryCostEntryInfo budgetInfo= iProgrammingEntryCostEntry.getProgrammingEntryCostEntryInfo(new ObjectUuidPK(entry.getBudgetNumber().getId()));
//			String projectNumber = budgetInfo.getNumber();
//			String budgetNumber = budgetInfo.getFeeNumber();
//			String budgetName = budgetInfo.getFeeName();
//			String year = budgetInfo.getYear();
//			BigDecimal backAmount = UIRuleUtil.getBigDecimal(entry.getDiffAmount());
//			String[] str = ProjectBudgetFacadeFactory.getLocalInstance(ctx).backBudgetAmount(projectNumber,year,budgetNumber
//																,String.valueOf(entry.getAmount()),CLUtil.stag_zb,true,backAmount.toString());
//			if("失败".equals(str[0])){
//				 throw new XRBillException(XRBillException.NOBUDGET, new Object[] {
//						 "预算编码："+budgetNumber+","+budgetName , str[1]
//			            });
//			}
//		}
		super._unAudit(ctx, pk);
	}
	

	//在工作流节点设置参与人获取招标组组长
    protected Object _getAuditPersonCollection(Context ctx, String billID)
    		throws BOSException {
    	
    	Object obj = null;
		try 
		{
			obj = getUserCollection(ctx, getPersonIdCollection(ctx,new ObjectUuidPK(billID)));
		} 
		catch (EASBizException e) {
			e.printStackTrace();
		}
    	
    	return obj;
    }
	    
	    private Object getUserCollection(Context ctx, Set<String> set) throws EASBizException, BOSException
	    {
	    	Iterator<String> iter = set.iterator();
	    	
	    	Person[] stPs=new Person[100];
	    	
	    	int index = 0;
	    	while(iter.hasNext())
	    	{
	    		String personId = iter.next().toString();
	    		
	    		UserCollection userColl = getUsersByPerson(ctx, personId);
	    		
	    		if (null == userColl || 0 == userColl.size()) {
					continue;
				}
	    		Person wfPerson = new Person();
				Locale locales[] = getContextLocales(ctx);
				UserInfo user = userColl.get(0);
				wfPerson.setUserId(user.getId().toString());
				for (int i = 0; i < locales.length; i++)
					wfPerson.setUserName(locales[i], user.getName(locales[i]));
				if (user.getPerson() != null) {
					wfPerson.setEmployeeId(user.getPerson().getId().toString());
					for (int i = 0; i < locales.length; i++)
						wfPerson.setEmployeeName(locales[i], user.getPerson().getName(locales[i]));
				}
				if(wfPerson!=null){
					stPs[index]=wfPerson;
					index+=1;
				}
	    	}
	    	return stPs;
	    }
	    
	    private Set getPersonIdCollection(Context ctx, IObjectPK pk) throws EASBizException, BOSException
	    {
	    	WinInviteReportInfo winInfo = getWinInviteReportInfo(ctx, pk);
	    	Set<String> set = new HashSet<String>();
	    	String id = ((InviteReportInfo)winInfo.getInviteReport()).getId().toString();
	    	InviteReportInfo inviteInfo = InviteReportFactory.getLocalInstance(ctx).getInviteReportInfo(new ObjectUuidPK(id));
	    	for (int i = 0; i < inviteInfo.getEntry3().size(); i++) 
	    	{
	    		InviteReportEntry3Info InviteEntry3 = inviteInfo.getEntry3().get(i);
	    		
	    				if(UIRuleUtil.isNotNull(InviteEntry3.getInvitePerson())&&InviteEntry3.isIsLeader())
		    				set.add(InviteEntry3.getInvitePerson().getId().toString());
					}
	    			
	    	return set;
	    }
	    
	    
	    private UserCollection getUsersByPerson(Context context, String personId)throws BOSException {
			UserCollection userColl = null;
			IUser iUser = UserFactory.getLocalInstance(context);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("isDelete", new Integer(0)));
			filter.getFilterItems().add(
					new FilterItemInfo("person.id", personId.trim()));
			EntityViewInfo env = new EntityViewInfo();
			env.setFilter(filter);
			env.getSelector().add(new SelectorItemInfo("id"));
			env.getSelector().add(new SelectorItemInfo("name"));
			env.getSelector().add(new SelectorItemInfo("person.name"));
			userColl = iUser.getUserCollection(env);
			
			return userColl;
		}
	    
	    
	    public static Locale[] getContextLocales(Context ctx) {
			Locale locales[] = null;
			SolutionInfo solu = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx).getSolution();
			if (solu != null) {
				LanguageCollection langs = solu.getLanguages();
				if (langs != null) {
					locales = new Locale[langs.size()];
					for (int i = 0; i < langs.size(); i++)
						locales[i] = langs.get(i).getLocale();
				}
			}
			return locales;
		}
}