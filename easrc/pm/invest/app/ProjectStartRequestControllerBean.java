package com.kingdee.eas.port.pm.invest.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.assistant.IProject;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.port.pm.base.IInviteType;
import com.kingdee.eas.port.pm.base.InviteTypeFactory;
import com.kingdee.eas.port.pm.invest.ProjectStartRequestEntryInfo;
import com.kingdee.eas.port.pm.invest.ProjectStartRequestFactory;
import com.kingdee.eas.port.pm.invest.ProjectStartRequestInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo;
import com.kingdee.eas.port.pm.invite.IInvitePlan;
import com.kingdee.eas.port.pm.invite.InvitePlanFactory;
import com.kingdee.eas.port.pm.invite.InvitePlanInfo;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.eas.xr.helper.SysPlatformXRHelper;

public class ProjectStartRequestControllerBean extends AbstractProjectStartRequestControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.pm.invest.app.ProjectStartRequestControllerBean");

	protected void _audit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		super._audit(ctx, pk);
		ProjectStartRequestInfo reqInfo = ProjectStartRequestFactory.getLocalInstance(ctx).getProjectStartRequestInfo(pk);
		ProgrammingInfo info = new ProgrammingInfo();
		try {
			ProgrammingCollection coll = ProgrammingFactory.getLocalInstance(ctx).getProgrammingCollection("where SourceBillId='"+reqInfo.getId()+"' order by version desc");
			if(coll.size()>0){
				info = coll.get(0);
				info = ProgrammingFactory.getLocalInstance(ctx).getProgrammingInfo(new ObjectUuidPK(info.getId()));
				for(int i=0;i<info.getEntries().size();i++){
					ProgrammingEntryInfo entry = info.getEntries().get(i);
					entry.setIsInvite(true);//最新
					for(int j=0;j<entry.getCostEntries().size();j++){
						entry.getCostEntries().get(j).setBeizhu("最新");
					}
				}
				ProgrammingFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()), info);
			}
			
			//自动生成招标计划
			
			IInvitePlan IInvitePlan = InvitePlanFactory.getLocalInstance(ctx);
			FilterInfo filInfo = new FilterInfo();
			filInfo.getFilterItems().add(new FilterItemInfo("sourceBillId",pk.toString()));
			
			if(!IInvitePlan.exists(filInfo)){
				IProject IProject = ProjectFactory.getLocalInstance(ctx);
				IInviteType IInviteType = InviteTypeFactory.getLocalInstance(ctx);
				for (int i = 0; i < reqInfo.getEntry().size(); i++) {
					ProjectStartRequestEntryInfo entryInfo = reqInfo.getEntry().get(i);
					InvitePlanInfo planInfo = new InvitePlanInfo();
					planInfo.setNumber(SysPlatformXRHelper.getBillNumber(ctx, planInfo,OrgConstants.DEF_CU_ID));
					planInfo.setSourceBillId(reqInfo.getId().toString());
					
					if(entryInfo.getType()!=null)
						planInfo.setInviteType(IInviteType.getInviteTypeInfo(new ObjectUuidPK(entryInfo.getType().getId())));
					
					if(reqInfo.getProjectName()!=null)
						planInfo.setProject(IProject.getProjectInfo(new ObjectUuidPK(reqInfo.getProjectName().getId())));
					
					planInfo.setPlanName(entryInfo.getInviteName());
					planInfo.setBidAmount(entryInfo.getAmount());
					planInfo.setStartDate(entryInfo.getPlanDate());
					planInfo.setStatus(XRBillStatusEnum.TEMPORARILYSAVED);
					planInfo.setBIMUDF0009(entryInfo.getBeizhu());
					
					IInvitePlan.addnew(planInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		super._unAudit(ctx, pk);
		ProjectStartRequestInfo reqInfo = ProjectStartRequestFactory.getLocalInstance(ctx).getProjectStartRequestInfo(pk);
		ProgrammingInfo info = new ProgrammingInfo();
		try {
			ProgrammingCollection coll = ProgrammingFactory.getLocalInstance(ctx).getProgrammingCollection("where SourceBillId='"+reqInfo.getId()+"' order by version desc");
			if(coll.size()>0){
				info = coll.get(0);
				info = ProgrammingFactory.getLocalInstance(ctx).getProgrammingInfo(new ObjectUuidPK(info.getId()));
				for(int i=0;i<info.getEntries().size();i++){
					ProgrammingEntryInfo entry = info.getEntries().get(i);
					entry.setIsInvite(false);//最新
					for(int j=0;j<entry.getCostEntries().size();j++){
						entry.getCostEntries().get(j).setBeizhu("");
					}
				}
				ProgrammingFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()), info);
			}
			
			FilterInfo filInfo = new FilterInfo();
			filInfo.getFilterItems().add(new FilterItemInfo("sourceBillId",pk.toString()));
			InvitePlanFactory.getLocalInstance(ctx).delete(filInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void _unAudit(Context ctx, IObjectPK[] pks) throws BOSException,EASBizException {
		super._unAudit(ctx, pks);
	}
	
	
   
}