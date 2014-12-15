package com.kingdee.eas.port.pm.invest.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.participant.Person;
import com.kingdee.eas.base.permission.IUser;
import com.kingdee.eas.base.permission.UserCollection;
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
import com.kingdee.eas.common.SysConstant;
import com.kingdee.eas.common.SysContextConstant;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.mm.common.MMBaseStatusEnum;
import com.kingdee.eas.mm.project.ProjectGroupFactory;
import com.kingdee.eas.mm.project.ProjectOrgInfo;
import com.kingdee.eas.mm.project.ProjectPriorityEnum;
import com.kingdee.eas.port.pm.base.IProjectType;
import com.kingdee.eas.port.pm.base.InvestYearInfo;
import com.kingdee.eas.port.pm.base.ProjectTypeFactory;
import com.kingdee.eas.port.pm.base.coms.PlanTypeEnum;
import com.kingdee.eas.port.pm.invest.AccredTypeEnum;
import com.kingdee.eas.port.pm.invest.IYearInvestPlan;
import com.kingdee.eas.port.pm.invest.ObjectStateEnum;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1Collection;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1E2Info;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1Info;
import com.kingdee.eas.port.pm.invest.YIPlanAccredFactory;
import com.kingdee.eas.port.pm.invest.YIPlanAccredInfo;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanInfo;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.util.NumericExceptionSubItem;

public class YIPlanAccredControllerBean extends AbstractYIPlanAccredControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.pm.invest.app.YIPlanAccredControllerBean");
    
    protected void _audit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
    	super._audit(ctx, pk);
    	YIPlanAccredInfo Info = getYIPlanAccredInfo(ctx, pk);
    	Info.setAccredDate( new Timestamp(System.currentTimeMillis()));
    	YIPlanAccredFactory.getLocalInstance(ctx).update(pk, Info);
    	
    	if(AccredTypeEnum.approve.equals(Info.getAccredType()))
    	{
    		creatProjectBase(ctx,Info);
    	}
    	AccredTypeEnum accredType = Info.getAccredType();
    	IYearInvestPlan YearInvestPlan = YearInvestPlanFactory.getLocalInstance(ctx);
    	YIPlanAccredE1Collection coll =Info.getE1();
    		for (int i = 0; i <coll.size(); i++) {
					YIPlanAccredE1Info yipInfo = coll.get(i);
					
					BOSUuid yipid =yipInfo.getProjectName().getId();
		    		YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
		    		
		    		if(yipInfo.getAccredResu().equals(ObjectStateEnum.complement))
		    		{
		    			planInfo.setStatus(XRBillStatusEnum.TEMPORARILYSAVED);
		    			planInfo.setDescription(planInfo.getObjectState().getValue());
		    		}
		    		else
		    		{
		    			planInfo.setDescription("");
		    		}
		    		
		    		planInfo.setObjectState(yipInfo.getAccredResu());
		    		YearInvestPlan.update(new ObjectUuidPK(yipid), planInfo);
		    		if(planInfo.getObjectState().equals(ObjectStateEnum.complement)){
		    			YearInvestPlan.save(planInfo);
		    		}
			}
    		if(accredType.equals(AccredTypeEnum.trial)){ 
    			for (int i = 0; i <coll.size(); i++) {
    				YIPlanAccredE1Info yipInfo = coll.get(i);
    				creatProjectAudit(ctx,Info,yipInfo);
			 }
    			
    	 }
    	 else if(accredType.equals(AccredTypeEnum.accred)){ 
    			for (int i = 0; i <coll.size(); i++) {
    				YIPlanAccredE1Info yipInfo = coll.get(i);
    				creatProjectAudit(ctx,Info,yipInfo);
			   }
    	 }
    		else{ 
    			for (int i = 0; i <coll.size(); i++) {
    				YIPlanAccredE1Info yipInfo = coll.get(i);
    				creatProjectAudit(ctx,Info,yipInfo);			 
			   }
    		}
     }
    /**
     * 反审核
     */
    protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {//反审核
    	super._unAudit(ctx, pk);
    	YIPlanAccredInfo Info = getYIPlanAccredInfo(ctx, pk);
    	IProject Iproject = ProjectFactory.getLocalInstance(ctx);
    	IYearInvestPlan IyearInvestPlan = YearInvestPlanFactory.getLocalInstance(ctx);
    	AccredTypeEnum accredType = Info.getAccredType();
    	 IYearInvestPlan YearInvestPlan = YearInvestPlanFactory.getLocalInstance(ctx);
    	 YIPlanAccredE1Collection coll =Info.getE1();
    	if(accredType.equals(AccredTypeEnum.trial)){//初审表 反写已申报
    		throw new EASBizException(new NumericExceptionSubItem("100","初审表不能反审核！"));
    	 }
    	 else if(accredType.equals(AccredTypeEnum.accred)){//评审表反写初审通过
    		 throw new EASBizException(new NumericExceptionSubItem("100","评审表不能反审核！"));
    	 }else{//批准表反写评审通过
    			for (int i = 0; i <coll.size(); i++) {
					YIPlanAccredE1Info yipInfo = coll.get(i);
					BOSUuid yipid =yipInfo.getProjectName().getId();
		    		YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
		    		planInfo.setObjectState(ObjectStateEnum.accredit);
		    		YearInvestPlan.update(new ObjectUuidPK(yipid), planInfo);
		    		CoreBaseInfo info = Iproject.getValue("where NJGyearInvest.id='"+yipid.toString()+"'");
		    		if(info!=null)
		    		{
		    			if(IyearInvestPlan.exists("where portProject.id='"+info.getId().toString()+"'"))
		    			{
		    				throw new EASBizException(new NumericExceptionSubItem("100","项目已被引用不能反审核！"));
		    			}
		    		}
		    		Iproject.delete("where NJGyearInvest.id='"+yipid.toString()+"'");
			   }
    		}
    }
    /**
     * 批量反审核
     */
    protected void _unAudit(Context ctx, IObjectPK[] pks) throws BOSException,EASBizException {
    	super._unAudit(ctx, pks);
    	IProject Iproject = ProjectFactory.getLocalInstance(ctx);
    	for (int g = 0; g < pks.length; g++) 
    	{
    		YIPlanAccredInfo Info = getYIPlanAccredInfo(ctx, pks[g]);
    		for (int i = 0; i < Info.getE1().size(); i++) 
        	{
        		YIPlanAccredE1Info e1Info = Info.getE1().get(i);
        		String yearPlanId = e1Info.getProjectName().getId().toString();
        	}
    		
        	AccredTypeEnum accredType = Info.getAccredType();
        	 IYearInvestPlan YearInvestPlan = YearInvestPlanFactory.getLocalInstance(ctx);
        	 YIPlanAccredE1Collection coll =Info.getE1();
        	if(accredType.equals(AccredTypeEnum.trial)){//初审表 反写已申报
        			for (int i = 0; i <coll.size(); i++) {
    					YIPlanAccredE1Info yipInfo = coll.get(i);
    					BOSUuid yipid =yipInfo.getProjectName().getId();
    		    		YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
    		    		planInfo.setObjectState(ObjectStateEnum.declared);
    		    		YearInvestPlan.update(new ObjectUuidPK(yipid), planInfo);
    			 }
        			
        	 }
        	 else if(accredType.equals(AccredTypeEnum.accred)){//评审表反写初审通过
        			for (int i = 0; i <coll.size(); i++) {
    					YIPlanAccredE1Info yipInfo = coll.get(i);
    					BOSUuid yipid =yipInfo.getProjectName().getId();
    		    		YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
    		    		planInfo.setObjectState(ObjectStateEnum.throughAudit);
    		    		YearInvestPlan.update(new ObjectUuidPK(yipid), planInfo);
    			   }
        	 }else{//批准表反写评审通过
        			for (int i = 0; i <coll.size(); i++) {
    					YIPlanAccredE1Info yipInfo = coll.get(i);
    					BOSUuid yipid =yipInfo.getProjectName().getId();
    		    		YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
    		    		planInfo.setObjectState(ObjectStateEnum.accredit);
    		    		YearInvestPlan.update(new ObjectUuidPK(yipid), planInfo);
//    		    		Iproject.delete("where NJGyearInvest.id='"+yipid.toString()+"'");
    			   }
        		}
		}
    }
    
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
    	super._delete(ctx, pk);
    }
 /**
  * 项目已立项反写到基础资料
  * @param ctx
  * @param Info
  * @throws BOSException
  * @throws EASBizException
  */
    private static void creatProjectBase(Context ctx,YIPlanAccredInfo Info) throws BOSException, EASBizException
    {
    	IProject Iproject = ProjectFactory.getLocalInstance(ctx);
    	IYearInvestPlan IyearInvestPlan = YearInvestPlanFactory.getLocalInstance(ctx);
    	ICompanyOrgUnit ICompanyOrgUnit = CompanyOrgUnitFactory.getLocalInstance(ctx);
    	IProjectType IProjectType = ProjectTypeFactory.getLocalInstance(ctx);
    	for (int i = 0; i < Info.getE1().size(); i++) 
    	{
    		YIPlanAccredE1Info e1Info = Info.getE1().get(i);
    		String yearPlanId = e1Info.getProjectName().getId().toString();
    		String oql = "select id,number,projectName,PlanType,planStartDate,planEndDate,portProject.id,projectType.name,buildType.name,year.number" +
    						",cu.id where id='"+yearPlanId+"'";
			YearInvestPlanInfo planInfo = IyearInvestPlan.getYearInvestPlanInfo(oql);
			ProjectInfo yearProject = createYearDoc(ctx, Iproject, planInfo);
    		if(e1Info.getAccredResu().equals(ObjectStateEnum.approval)
    				&& !Iproject.exists("select id where NJGyearInvest.id='"+yearPlanId+"'") 
    				&& !planInfo.getPlanType().equals(PlanTypeEnum.adjust))
    		{
    			ProjectInfo parentproInfo = createParentProject(ctx,Iproject,IProjectType,planInfo);
    			ProjectInfo proInfo = new ProjectInfo();
    			proInfo.setId(BOSUuid.create(proInfo.getBOSType()));
    			proInfo.setNumber(planInfo.getNumber());
    			proInfo.setCompany(ICompanyOrgUnit.getCompanyOrgUnitInfo("select id,name,number where id='"+e1Info.getCompany().getId().toString()+"'"));
    			proInfo.setType(ProjectTypeEnum.CUS_PROJECT);
    			proInfo.setStatus(ProjectStatus.PREPARE);
    			proInfo.setScheduleStartDate(planInfo.getPlanStartDate());
    			proInfo.setSchedulEndDate(planInfo.getPlanEndDate());
    			proInfo.setNJGyearInvest(planInfo);
    			proInfo.setIsSysCreate(Boolean.TRUE);
    			proInfo.setIsLeaf(true);
    			proInfo.setIsListItem(true);
    			proInfo.setNJGprojectType(IProjectType.getProjectTypeInfo("select id,name,number where id='"+e1Info.getProjectType().getId().toString()+"'"));
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
    /**
     * 审核时,反写评审表意见功能 去除，直接查询展示
     * @param ctx
     * @param Info
     * @throws BOSException
     * @throws EASBizException
     */
    private void creatProjectAudit(Context ctx,YIPlanAccredInfo Info,YIPlanAccredE1Info E1Info) throws BOSException, EASBizException 
    {
//    	   IYearInvestPlan YearInvestPlan = YearInvestPlanFactory.getLocalInstance(ctx);
//		    BOSUuid yipid =E1Info.getProjectName().getId();
//		    YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
//		    YearInvestPlanE3Info e3Info = new YearInvestPlanE3Info();
//		    e3Info.setReviewStage(Info.getAccredType());
//		    e3Info.setAccredConclusion(E1Info.getProjectConclude());
//		    e3Info.setReviewTime(Info.getAccredDate());
//		    planInfo.getE3().add(e3Info);
//		    YearInvestPlan.update(new ObjectUuidPK(yipid), planInfo);
    	
    }   
    
    
    protected Object _getAuditPersonCollection(Context ctx, IObjectPK pk)throws BOSException {
    	
    	Object obj = null;
		try 
		{
			obj = getUserCollection(ctx, getPersonIdCollection(ctx,pk));
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
    	YIPlanAccredInfo yiplanInfo = getYIPlanAccredInfo(ctx, pk);
    	
    	Set<String> set = new HashSet<String>();
    	
    	for (int i = 0; i < yiplanInfo.getE1().size(); i++) 
    	{
    		YIPlanAccredE1Info YIPlanAccredE1Info = yiplanInfo.getE1().get(i);
    		
    		for (int j = 0; j < YIPlanAccredE1Info.getE2().size(); j++) 
    		{
    			YIPlanAccredE1E2Info YIPlanAccredE1E2Info = YIPlanAccredE1Info.getE2().get(j);
    			
    			if(UIRuleUtil.isNotNull(YIPlanAccredE1E2Info.getAccredPerson()))
    				set.add(YIPlanAccredE1E2Info.getAccredPerson().getId().toString());
			}
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