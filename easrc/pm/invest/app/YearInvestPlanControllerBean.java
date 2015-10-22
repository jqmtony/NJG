package com.kingdee.eas.port.pm.invest.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.IProject;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.assistant.ProjectStatus;
import com.kingdee.eas.basedata.assistant.ProjectTypeEnum;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.StorageOrgUnitFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysContextConstant;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.mm.project.ProjectOrgInfo;
import com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo;
import com.kingdee.eas.port.pm.base.IProjectType;
import com.kingdee.eas.port.pm.base.InvestYearInfo;
import com.kingdee.eas.port.pm.base.ProjectTypeFactory;
import com.kingdee.eas.port.pm.base.coms.PlanTypeEnum;
import com.kingdee.eas.port.pm.invest.IYearInvestPlan;
import com.kingdee.eas.port.pm.invest.ObjectStateEnum;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1Info;
import com.kingdee.eas.port.pm.invest.YIPlanAccredInfo;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingFactory;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.UuidException;

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
			ProjectInfo yearProject = createYearDoc(ctx, Iproject, planInfo);
			ProjectInfo parentproInfo = createParentProject(ctx,Iproject,IProjectType,planInfo);
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
    
    
    /**
     * 工作流自动节点调用，生成项目
     */
    protected void _creatProjectBase(Context ctx, IObjectPK pk)throws BOSException, EASBizException {
    	super._creatProjectBase(ctx, pk);
    	 SelectorItemCollection sic = new SelectorItemCollection();
 		String selectorAll = "true";
 		if(StringUtils.isEmpty(selectorAll)){
 			selectorAll = "true";
 		}
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("creator.*"));
 		}
 		else{
         	sic.add(new SelectorItemInfo("creator.id"));
         	sic.add(new SelectorItemInfo("creator.number"));
         	sic.add(new SelectorItemInfo("creator.name"));
 		}
         sic.add(new SelectorItemInfo("createTime"));
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
 		}
 		else{
         	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
         	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
         	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
 		}
         sic.add(new SelectorItemInfo("lastUpdateTime"));
         sic.add(new SelectorItemInfo("description"));
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("auditor.*"));
 		}
 		else{
         	sic.add(new SelectorItemInfo("auditor.id"));
         	sic.add(new SelectorItemInfo("auditor.number"));
         	sic.add(new SelectorItemInfo("auditor.name"));
 		}
         sic.add(new SelectorItemInfo("bizStatus"));
         sic.add(new SelectorItemInfo("auditTime"));
         sic.add(new SelectorItemInfo("planStartDate"));
         sic.add(new SelectorItemInfo("planEndDate"));
         sic.add(new SelectorItemInfo("BIMUDF0027"));
         sic.add(new SelectorItemInfo("address"));
     	sic.add(new SelectorItemInfo("Entry.seq"));
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("Entry.*"));
 		}
 		else{
 		}
     	sic.add(new SelectorItemInfo("Entry.costName"));
     	sic.add(new SelectorItemInfo("Entry.estimate"));
     	sic.add(new SelectorItemInfo("Entry.yearInvestBudget"));
     	sic.add(new SelectorItemInfo("Entry.planStartT"));
     	sic.add(new SelectorItemInfo("Entry.acceptTime"));
     	sic.add(new SelectorItemInfo("Entry.description"));
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("Entry.costType.*"));
 		}
 		else{
 	    	sic.add(new SelectorItemInfo("Entry.costType.id"));
 			sic.add(new SelectorItemInfo("Entry.costType.name"));
         	sic.add(new SelectorItemInfo("Entry.costType.number"));
 		}
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("companyProperty.*"));
 		}
 		else{
         	sic.add(new SelectorItemInfo("companyProperty.id"));
         	sic.add(new SelectorItemInfo("companyProperty.number"));
         	sic.add(new SelectorItemInfo("companyProperty.name"));
 		}
         sic.add(new SelectorItemInfo("status"));
         sic.add(new SelectorItemInfo("bizDate"));
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("project.*"));
 		}
 		else{
         	sic.add(new SelectorItemInfo("project.id"));
         	sic.add(new SelectorItemInfo("project.number"));
         	sic.add(new SelectorItemInfo("project.name"));
 		}
         sic.add(new SelectorItemInfo("analyse"));
         sic.add(new SelectorItemInfo("scheme"));
     	sic.add(new SelectorItemInfo("E2.seq"));
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("E2.*"));
 		}
 		else{
 		}
     	sic.add(new SelectorItemInfo("E2.apIndex"));
     	sic.add(new SelectorItemInfo("E2.planComplete"));
         sic.add(new SelectorItemInfo("desc"));
         sic.add(new SelectorItemInfo("remark"));
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("costTemp.*"));
 		}
 		else{
         	sic.add(new SelectorItemInfo("costTemp.id"));
         	sic.add(new SelectorItemInfo("costTemp.number"));
         	sic.add(new SelectorItemInfo("costTemp.tempName"));
 		}
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("year.*"));
 		}
 		else{
         	sic.add(new SelectorItemInfo("year.id"));
         	sic.add(new SelectorItemInfo("year.number"));
         	sic.add(new SelectorItemInfo("year.name"));
 		}
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("portProject.*"));
 		}
 		else{
         	sic.add(new SelectorItemInfo("portProject.id"));
         	sic.add(new SelectorItemInfo("portProject.number"));
         	sic.add(new SelectorItemInfo("portProject.name"));
 		}
         sic.add(new SelectorItemInfo("amount"));
         sic.add(new SelectorItemInfo("investAmount"));
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("requestPerson.*"));
 		}
 		else{
         	sic.add(new SelectorItemInfo("requestPerson.id"));
         	sic.add(new SelectorItemInfo("requestPerson.number"));
         	sic.add(new SelectorItemInfo("requestPerson.name"));
 		}
         sic.add(new SelectorItemInfo("number"));
         sic.add(new SelectorItemInfo("planType"));
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("requestOrg.*"));
 		}
 		else{
         	sic.add(new SelectorItemInfo("requestOrg.id"));
         	sic.add(new SelectorItemInfo("requestOrg.number"));
         	sic.add(new SelectorItemInfo("requestOrg.name"));
 		}
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("fundSource.*"));
 		}
 		else{
         	sic.add(new SelectorItemInfo("fundSource.id"));
         	sic.add(new SelectorItemInfo("fundSource.number"));
         	sic.add(new SelectorItemInfo("fundSource.name"));
 		}
         sic.add(new SelectorItemInfo("addInvestAmount"));
         sic.add(new SelectorItemInfo("chancedAmount"));
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("buildType.*"));
 		}
 		else{
         	sic.add(new SelectorItemInfo("buildType.id"));
         	sic.add(new SelectorItemInfo("buildType.number"));
         	sic.add(new SelectorItemInfo("buildType.name"));
 		}
         sic.add(new SelectorItemInfo("balance"));
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("projectType.*"));
 		}
 		else{
         	sic.add(new SelectorItemInfo("projectType.id"));
         	sic.add(new SelectorItemInfo("projectType.number"));
         	sic.add(new SelectorItemInfo("projectType.name"));
 		}
 		if(selectorAll.equalsIgnoreCase("true"))
 		{
 			sic.add(new SelectorItemInfo("CU.*"));
 		}
 		else{
         	sic.add(new SelectorItemInfo("CU.id"));
         	sic.add(new SelectorItemInfo("CU.number"));
         	sic.add(new SelectorItemInfo("CU.name"));
 		}
         sic.add(new SelectorItemInfo("objectState"));
         sic.add(new SelectorItemInfo("seq"));
         sic.add(new SelectorItemInfo("projectName"));
    	YearInvestPlanInfo Info = YearInvestPlanFactory.getLocalInstance(ctx).getYearInvestPlanInfo(pk,sic);
    	creatProjectBase(ctx, Info);
    }
    
    
    /**
     * 项目已立项反写到基础资料
     * @param ctx
     * @param Info
     * @throws BOSException
     * @throws EASBizException
     */
       private static void creatProjectBase(Context ctx,YearInvestPlanInfo planInfo) throws BOSException, EASBizException
       {
       	IProject Iproject = ProjectFactory.getLocalInstance(ctx);
       	IYearInvestPlan IyearInvestPlan = YearInvestPlanFactory.getLocalInstance(ctx);
       	ICompanyOrgUnit ICompanyOrgUnit = CompanyOrgUnitFactory.getLocalInstance(ctx);
       	IProjectType IProjectType = ProjectTypeFactory.getLocalInstance(ctx);
			ProjectInfo yearProject = createYearDoc(ctx, Iproject, planInfo);
   		if( !Iproject.exists("select id where NJGyearInvest.id='"+planInfo.getId()+"'") 
   				&& !planInfo.getPlanType().equals(PlanTypeEnum.adjust))
   		{
   			ProjectInfo parentproInfo = createParentProject(ctx,Iproject,IProjectType,planInfo);
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
   			
   			createNewProject(ctx, proInfo);
   		}
       }
       /**
        * 生成父项目（项目前期、基本建设）
        * @throws BOSException 
        * @throws EASBizException 
        */
       static ProjectInfo createParentProject(Context ctx,IProject iproject,IProjectType IProjectType,YearInvestPlanInfo planInfo) throws EASBizException, BOSException{
       	ProjectInfo parentproInfo = new ProjectInfo();
       	if(planInfo.getPortProject()!=null && planInfo.getBuildType().getName().equals("续建项目")){
       		parentproInfo = iproject.getProjectInfo(new ObjectUuidPK(planInfo.getPortProject().getId())).getParent();
       	}
       	else{
       		if(planInfo.getProjectType().getName().equals("项目前期")
       				|| planInfo.getProjectType().getName().equals("基本建设")){
       			parentproInfo.setId(BOSUuid.create(parentproInfo.getBOSType()));
       			if(planInfo.getProjectType().getName().equals("项目前期")){
       				parentproInfo.setNumber(""+planInfo.getNumber()+"-0");
       				parentproInfo.setName(planInfo.getProjectName().replace("前期", ""));
       			}else{
       				parentproInfo.setNumber(planInfo.getNumber()+"-0");
       				parentproInfo.setName(planInfo.getProjectName());
       			}
       			parentproInfo.setCompany(CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(planInfo.getCU().getId())));
       			parentproInfo.setType(ProjectTypeEnum.CUS_PROJECT);
       			parentproInfo.setStatus(ProjectStatus.PREPARE);
       			parentproInfo.setScheduleStartDate(planInfo.getPlanStartDate());
       			parentproInfo.setSchedulEndDate(planInfo.getPlanEndDate());
       			parentproInfo.setNJGyearInvest(null);
       			parentproInfo.setIsSysCreate(Boolean.TRUE);
       			parentproInfo.setIsLeaf(false);
       			parentproInfo.setNJGprojectType(IProjectType.getProjectTypeInfo("select id,name,number where name='基本建设'"));
       			
       			iproject.addnew(parentproInfo);
       		}
       	}
   		return parentproInfo;
       }
       /**
        * 生成项目---年度文件夹（2014）
        */
       static ProjectInfo createYearDoc(Context ctx,IProject iproject,YearInvestPlanInfo planInfo) throws EASBizException, BOSException{
       	InvestYearInfo  year = planInfo.getYear();
       	ProjectInfo proInfo = new ProjectInfo();
       	CtrlUnitInfo cu = CtrlUnitFactory.getLocalInstance(ctx).getCtrlUnitInfo(new ObjectUuidPK(planInfo.getCU().getId()));
       	String number = year.getNumber()+"-"+cu.getNumber();
       	if(!iproject.exists(" where number='"+number+"' and company='"+planInfo.getCU().getId()+"'")){
   			proInfo.setId(BOSUuid.create(proInfo.getBOSType()));
   			proInfo.setNumber(number);
   			proInfo.setName(year.getNumber()+"年投资项目");
   			proInfo.setCompany(CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(planInfo.getCU().getId())));
   			proInfo.setType(ProjectTypeEnum.CUS_PROJECT);
   			proInfo.setStatus(ProjectStatus.PREPARE);
   			proInfo.setIsSysCreate(Boolean.TRUE);
   			proInfo.setIsLeaf(false);
   			iproject.addnew(proInfo);
       	}else{
       		FilterInfo filter = new FilterInfo();
       		filter.getFilterItems().add(new FilterItemInfo("number", number));
       		filter.getFilterItems().add(new FilterItemInfo("company", planInfo.getCU().getId()));
       		EntityViewInfo view = new EntityViewInfo();
       		view.setFilter(filter);
       		proInfo = iproject.getProjectCollection(view).get(0);
       	}	
   		return proInfo;
       }
       /**
        * 生成订单中的项目---生成制造项目
        */
       protected static com.kingdee.eas.mm.project.ProjectInfo createNewProject(Context ctx,ProjectInfo proInfo )
       {
       	com.kingdee.eas.mm.project.ProjectInfo objectValue = null;
       	try
           {
       		CompanyOrgUnitInfo company = (CompanyOrgUnitInfo)ctx.get(SysContextConstant.COMPANYINFO);
       		com.kingdee.eas.mm.project.IProject IProject = com.kingdee.eas.mm.project.ProjectFactory.getLocalInstance(ctx);
   			objectValue = IProject.getProjectInfo("where number='99999'");//模板项目
   			objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
   			objectValue.setName(proInfo.getName());
   			objectValue.setNumber(proInfo.getNumber());
   			objectValue.setCurrency(CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection().get(0));
   			objectValue.setCU(CtrlUnitFactory.getLocalInstance(ctx).getCtrlUnitInfo(new ObjectUuidPK(company.getId())));
   			ProjectOrgInfo projectOrg = new ProjectOrgInfo();
   			projectOrg.setProject(objectValue);
   			projectOrg.setStorageOrgUnit(StorageOrgUnitFactory.getLocalInstance(ctx).getStorageOrgUnitInfo(new ObjectUuidPK(objectValue.getCU().getId())));
   			objectValue.getProjectOrg().clear();
   			objectValue.getProjectOrg().add(projectOrg);
   			IProject.addnew(objectValue);
           }catch(Exception e)
   		{
   		}
           return objectValue;
       }
}