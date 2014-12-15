package com.kingdee.eas.port.pm.invest.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.IProject;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.assistant.ProjectStatus;
import com.kingdee.eas.basedata.assistant.ProjectTypeEnum;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.port.pm.base.IProjectType;
import com.kingdee.eas.port.pm.base.ProjectTypeFactory;
import com.kingdee.eas.port.pm.base.coms.PlanTypeEnum;
import com.kingdee.eas.port.pm.invest.IYearInvestPlan;
import com.kingdee.eas.port.pm.invest.ObjectStateEnum;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingFactory;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class YearInvestPlanControllerBean extends AbstractYearInvestPlanControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.pm.invest.app.YearInvestPlanControllerBean");

	protected IObjectPK _addnew(Context ctx , IObjectValue model) throws BOSException , EASBizException {
//		if (model instanceof com.kingdee.eas.framework.ObjectBaseInfo) {
//			setAutoNumberByOrg(ctx,(com.kingdee.eas.framework.ObjectBaseInfo)model,"NONE");
//		}
		return super._addnew(ctx,model);
	}
	protected void setAutoNumberByOrg(Context ctx,com.kingdee.eas.framework.ObjectBaseInfo model,String orgType) {
			String sysNumber = null;
			String strCompanyID =  com.kingdee.eas.util.app.ContextUtil.getCurrentOrgUnit(ctx).getString("id");
			try {
				if (!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.util.app.ContextUtil.getCurrentOrgUnit(ctx,com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					sysNumber = com.kingdee.eas.framework.FrameWorkUtils.getCodeRuleServer(ctx,model,com.kingdee.eas.util.app.ContextUtil.getCurrentOrgUnit(ctx,com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id"));
				}
				else if (com.kingdee.eas.util.app.ContextUtil.getCurrentOrgUnit(ctx) != null) {
					sysNumber = com.kingdee.eas.framework.FrameWorkUtils.getCodeRuleServer(ctx,model,com.kingdee.eas.util.app.ContextUtil.getCurrentOrgUnit(ctx).getString("id"));
				}
				if (!com.kingdee.util.StringUtils.isEmpty(sysNumber)) {
					model.setString("number",sysNumber);
				}
			}
			catch (Exception e) {
			}
	}
    public void _audit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
    	super._audit(ctx, pk);
    	YearInvestPlanInfo info = getYearInvestPlanInfo(ctx, pk);
    	
    	if(UIRuleUtil.isNotNull(info.getDescription()))
    	{
    		info.setObjectState(ObjectStateEnum.getEnum(info.getDescription().trim()));
    	}
    	else
    	{
    		info.setObjectState(ObjectStateEnum.declared);
    	}
    	YearInvestPlanFactory.getLocalInstance(ctx).update(pk, info);
    	
    	createProject(ctx, info); // 
    }
    
    public void createProject(Context ctx,YearInvestPlanInfo info) throws BOSException,EASBizException{
    	IProject Iproject = ProjectFactory.getLocalInstance(ctx);
    	IYearInvestPlan IyearInvestPlan = YearInvestPlanFactory.getLocalInstance(ctx);
    	ICompanyOrgUnit ICompanyOrgUnit = CompanyOrgUnitFactory.getLocalInstance(ctx);
    	IProjectType IProjectType = ProjectTypeFactory.getLocalInstance(ctx);
    	if(PlanTypeEnum.companyPlan.equals(info.getPlanType())){// 自有项目
    		String oql = "select id,number,projectName,PlanType,planStartDate,planEndDate,portProject.id,projectType.name,buildType.name,year.number" +
			",cu.id where id='"+info.getId()+"'";
			YearInvestPlanInfo planInfo = IyearInvestPlan.getYearInvestPlanInfo(oql);
			ProjectInfo yearProject = YIPlanAccredControllerBean.createYearDoc(ctx, Iproject, planInfo);
			ProjectInfo parentproInfo = YIPlanAccredControllerBean.createParentProject(ctx,Iproject,IProjectType,planInfo);
			ProjectInfo proInfo = new ProjectInfo();
			proInfo.setId(BOSUuid.create(proInfo.getBOSType()));
			proInfo.setNumber(planInfo.getNumber());
			proInfo.setCompany(ICompanyOrgUnit.getCompanyOrgUnitInfo("select id,name,number where id='"+planInfo.getCU().getId().toString()+"'"));
			proInfo.setType(ProjectTypeEnum.CUS_PROJECT);
			proInfo.setStatus(ProjectStatus.PREPARE);
			proInfo.setScheduleStartDate(planInfo.getPlanStartDate());
			proInfo.setSchedulEndDate(planInfo.getPlanEndDate());
			proInfo.setNJGyearInvest(planInfo);
			proInfo.setIsSysCreate(Boolean.TRUE);
			proInfo.setIsLeaf(true);
			proInfo.setIsListItem(true);
			proInfo.setNJGprojectType(IProjectType.getProjectTypeInfo("select id,name,number where id='"+planInfo.getProjectType().getId().toString()+"'"));
			if(planInfo.getProjectType().getName().equals("项目前期")){
				if(planInfo.getProjectName().contains("前期"))
					proInfo.setName(planInfo.getProjectName()+planInfo.getYear().getNumber());
				else
					proInfo.setName(planInfo.getProjectName()+"前期"+planInfo.getYear().getNumber());
			}else if(planInfo.getProjectType().getName().equals("基本建设")){
				proInfo.setName(planInfo.getProjectName()+planInfo.getYear().getNumber());
			}else{
				proInfo.setName(planInfo.getProjectName());
			}
			
			if((planInfo.getProjectType().getName().equals("项目前期")||planInfo.getProjectType().getName().equals("基本建设") )){
				proInfo.setParent(parentproInfo);
			}else{
				proInfo.setParent(yearProject);
			}
			Iproject.addnew(proInfo);
			
			planInfo.setProject(proInfo);
			IyearInvestPlan.update(new ObjectUuidPK(planInfo.getId()), planInfo);
			
			YIPlanAccredControllerBean.createNewProject(ctx, proInfo);

    	}
    }
    
    protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
    	super._unAudit(ctx, pk);
    	YearInvestPlanInfo info = getYearInvestPlanInfo(ctx, pk);
    	if(info.getObjectState().equals(ObjectStateEnum.throughAudit)||info.getObjectState().equals(ObjectStateEnum.accredit)||info.getObjectState().equals(ObjectStateEnum.approval)||info.getObjectState().equals(ObjectStateEnum.veto)){
    		throw new EASBizException(new NumericExceptionSubItem("100","当前状态下不能反审核！"));
    	}
    	info.setObjectState(ObjectStateEnum.save);
    	YearInvestPlanFactory.getLocalInstance(ctx).update(pk, info);
    }
    
    public void unAudit(Context ctx, IObjectPK[] pks) throws BOSException,EASBizException {
    	super.unAudit(ctx, pks);
    	for (int i = 0; i < pks.length; i++) {
    		YearInvestPlanInfo info = getYearInvestPlanInfo(ctx, pks[i]);
        	info.setObjectState(ObjectStateEnum.save);
        	YearInvestPlanFactory.getLocalInstance(ctx).update(pks[i], info);
		}
    }
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
    	String number = getYearInvestPlanInfo(ctx, pk).getNumber();
    	YearInvestPlanInfo aSCMBillBaseInfo = (YearInvestPlanInfo)YearInvestPlanFactory.getLocalInstance(ctx).getYearInvestPlanInfo(pk);
    	super._delete(ctx, pk);
    	 ProgrammingFactory.getLocalInstance(ctx).delete("where SourceBillId='"+number+"'");
    	 ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
     	String strCompanyID = com.kingdee.eas.util.app.ContextUtil.getCurrentOrgUnit(ctx).getString("id");
     	if(iCodingRuleManager.isExist(aSCMBillBaseInfo, strCompanyID) && iCodingRuleManager.isUseIntermitNumber(aSCMBillBaseInfo, strCompanyID))
     		iCodingRuleManager.recycleNumber(aSCMBillBaseInfo, strCompanyID, aSCMBillBaseInfo.getNumber());
    }
}