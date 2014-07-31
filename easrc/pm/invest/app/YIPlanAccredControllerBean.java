package com.kingdee.eas.port.pm.invest.app;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.IProject;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.assistant.ProjectStatus;
import com.kingdee.eas.basedata.assistant.ProjectTypeEnum;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.port.pm.base.IProjectType;
import com.kingdee.eas.port.pm.base.ProjectTypeFactory;
import com.kingdee.eas.port.pm.invest.AccredTypeEnum;
import com.kingdee.eas.port.pm.invest.IYearInvestPlan;
import com.kingdee.eas.port.pm.invest.ObjectStateEnum;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1Collection;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1Info;
import com.kingdee.eas.port.pm.invest.YIPlanAccredFactory;
import com.kingdee.eas.port.pm.invest.YIPlanAccredInfo;
import com.kingdee.eas.port.pm.invest.YearInvestPlanE3Info;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanInfo;
import com.kingdee.eas.util.app.ContextUtil;
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
    	if(Info.getAccredType().equals(AccredTypeEnum.approve))
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
//    		throw new EASBizException(new NumericExceptionSubItem("100","初审表不能反审核！"));
//    			for (int i = 0; i <coll.size(); i++) {
//					YIPlanAccredE1Info yipInfo = coll.get(i);
//					BOSUuid yipid =yipInfo.getProjectName().getId();
//		    		YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
//		    		planInfo.setObjectState(ObjectStateEnum.declared);
//		    		YearInvestPlan.update(new ObjectUuidPK(yipid), planInfo);
//			 }
    	 }
    	 else if(accredType.equals(AccredTypeEnum.accred)){//评审表反写初审通过
//    		 throw new EASBizException(new NumericExceptionSubItem("100","评审表不能反审核！"));
//    			for (int i = 0; i <coll.size(); i++) {
//					YIPlanAccredE1Info yipInfo = coll.get(i);
//					BOSUuid yipid =yipInfo.getProjectName().getId();
//		    		YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
//		    		planInfo.setObjectState(ObjectStateEnum.throughAudit);
//		    		YearInvestPlan.update(new ObjectUuidPK(yipid), planInfo);
//			   }
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
//    	IProject Iproject = ProjectFactory.getLocalInstance(ctx);
//    	for (int g = 0; g < pks.length; g++) 
//    	{
//    		YIPlanAccredInfo Info = getYIPlanAccredInfo(ctx, pks[g]);
//    		for (int i = 0; i < Info.getE1().size(); i++) 
//        	{
//        		YIPlanAccredE1Info e1Info = Info.getE1().get(i);
//        		String yearPlanId = e1Info.getProjectName().getId().toString();
//        		Iproject.delete("where NJGyearInvest.id='"+yearPlanId+"'");
//        	}
//    		
//    		
//        	AccredTypeEnum accredType = Info.getAccredType();
//        	 IYearInvestPlan YearInvestPlan = YearInvestPlanFactory.getLocalInstance(ctx);
//        	 YIPlanAccredE1Collection coll =Info.getE1();
//        	if(accredType.equals(AccredTypeEnum.trial)){//初审表 反写已申报
//        			for (int i = 0; i <coll.size(); i++) {
//    					YIPlanAccredE1Info yipInfo = coll.get(i);
//    					BOSUuid yipid =yipInfo.getProjectName().getId();
//    		    		YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
//    		    		planInfo.setObjectState(ObjectStateEnum.declared);
//    		    		YearInvestPlan.update(new ObjectUuidPK(yipid), planInfo);
//    			 }
//        			
//        	 }
//        	 else if(accredType.equals(AccredTypeEnum.accred)){//评审表反写初审通过
//        			for (int i = 0; i <coll.size(); i++) {
//    					YIPlanAccredE1Info yipInfo = coll.get(i);
//    					BOSUuid yipid =yipInfo.getProjectName().getId();
//    		    		YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
//    		    		planInfo.setObjectState(ObjectStateEnum.throughAudit);
//    		    		YearInvestPlan.update(new ObjectUuidPK(yipid), planInfo);
//    			   }
//        	 }else{//批准表反写评审通过
//        			for (int i = 0; i <coll.size(); i++) {
//    					YIPlanAccredE1Info yipInfo = coll.get(i);
//    					BOSUuid yipid =yipInfo.getProjectName().getId();
//    		    		YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
//    		    		planInfo.setObjectState(ObjectStateEnum.accredit);
//    		    		YearInvestPlan.update(new ObjectUuidPK(yipid), planInfo);
//    		    		Iproject.delete("where NJGyearInvest.id='"+yipid.toString()+"'");
//    			   }
//        		}
//		}
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
    private void creatProjectBase(Context ctx,YIPlanAccredInfo Info) throws BOSException, EASBizException
    {
    	IProject Iproject = ProjectFactory.getLocalInstance(ctx);
    	IYearInvestPlan IyearInvestPlan = YearInvestPlanFactory.getLocalInstance(ctx);
    	ICompanyOrgUnit ICompanyOrgUnit = CompanyOrgUnitFactory.getLocalInstance(ctx);
    	IProjectType IProjectType = ProjectTypeFactory.getLocalInstance(ctx);
    	
    	for (int i = 0; i < Info.getE1().size(); i++) 
    	{
    		YIPlanAccredE1Info e1Info = Info.getE1().get(i);
    		String yearPlanId = e1Info.getProjectName().getId().toString();
    		if(e1Info.getAccredResu().equals(ObjectStateEnum.approval)&&
    				!Iproject.exists("select id where NJGyearInvest.id='"+yearPlanId+"'"))
    		{
    			String oql = "select id,number,projectName,planStartDate,planEndDate,portProject.id where id='"+yearPlanId+"'";
    			YearInvestPlanInfo planInfo = IyearInvestPlan.getYearInvestPlanInfo(oql);
    			
    			ProjectInfo proInfo = new ProjectInfo();
    			proInfo.setId(BOSUuid.create(proInfo.getBOSType()));
    			proInfo.setNumber(planInfo.getNumber());
    			proInfo.setName(planInfo.getProjectName());
    			proInfo.setCompany(ICompanyOrgUnit.getCompanyOrgUnitInfo("select id,name,number where id='"+e1Info.getCompany().getId().toString()+"'"));
    			proInfo.setType(ProjectTypeEnum.CUS_PROJECT);
    			proInfo.setStatus(ProjectStatus.PREPARE);
    			proInfo.setScheduleStartDate(planInfo.getPlanStartDate());
    			proInfo.setSchedulEndDate(planInfo.getPlanEndDate());
    			proInfo.setNJGyearInvest(planInfo);
    			proInfo.setNJGprojectType(IProjectType.getProjectTypeInfo("select id,name,number where id='"+e1Info.getProjectType().getId().toString()+"'"));
    			
    			if(planInfo.getPortProject()!=null)
    			{
    				proInfo.setParent(planInfo.getPortProject());
    			}
    			Iproject.addnew(proInfo);
    		}
		}
    }
    /**
     * 审核时
     * @param ctx
     * @param Info
     * @throws BOSException
     * @throws EASBizException
     */
    private void creatProjectAudit(Context ctx,YIPlanAccredInfo Info,YIPlanAccredE1Info E1Info) throws BOSException, EASBizException 
    {
    	   IYearInvestPlan YearInvestPlan = YearInvestPlanFactory.getLocalInstance(ctx);
		    BOSUuid yipid =E1Info.getProjectName().getId();
		    YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
		    YearInvestPlanE3Info e3Info = new YearInvestPlanE3Info();
		    e3Info.setReviewStage(Info.getAccredType());
		    e3Info.setAccredConclusion(E1Info.getProjectConclude());
		    e3Info.setReviewTime(Info.getAccredDate());
		    planInfo.getE3().add(e3Info);
		    YearInvestPlan.update(new ObjectUuidPK(yipid), planInfo);
    	
    }   
    
}