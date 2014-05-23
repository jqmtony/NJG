package com.kingdee.eas.port.pm.invest.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.port.pm.invest.AccredTypeEnum;
import com.kingdee.eas.port.pm.invest.IYIPlanAccredE1;
import com.kingdee.eas.port.pm.invest.IYearInvestPlan;
import com.kingdee.eas.port.pm.invest.ObjectStateEnum;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1Collection;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1E2Collection;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1E2Info;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1Info;
import com.kingdee.eas.port.pm.invest.YIPlanAccredInfo;
import com.kingdee.eas.port.pm.invest.YearInvestPlanE3Collection;
import com.kingdee.eas.port.pm.invest.YearInvestPlanE3Info;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanInfo;

public class YIPlanAccredControllerBean extends AbstractYIPlanAccredControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.pm.invest.app.YIPlanAccredControllerBean");
    
    protected void _audit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
    	super._audit(ctx, pk);
    	YIPlanAccredInfo Info = getYIPlanAccredInfo(ctx, pk);
    	AccredTypeEnum accredType = Info.getAccredType();
    	IYearInvestPlan YearInvestPlan = YearInvestPlanFactory.getLocalInstance(ctx);
    	YIPlanAccredE1Collection coll =Info.getE1();
    		for (int i = 0; i <coll.size(); i++) {
					YIPlanAccredE1Info yipInfo = coll.get(i);
					BOSUuid yipid =yipInfo.getProjectName().getId();
		    		YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
		    		planInfo.setObjectState(yipInfo.getAccredResu());
		    		YearInvestPlan.update(new ObjectUuidPK(yipid), planInfo);
			}
    		if(accredType.equals(AccredTypeEnum.trial)){ 
    			for (int i = 0; i <coll.size(); i++) {
					YIPlanAccredE1Info yipInfo = coll.get(i);
					BOSUuid yipid =yipInfo.getProjectName().getId();
		    		YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
//		    		planInfo.setObjectState(ObjectStateEnum.throughAudit);
		    		YearInvestPlanE3Info e3Info = new YearInvestPlanE3Info();
		    		e3Info.setReviewStage(Info.getAccredType());
		    		e3Info.setAccredConclusion(yipInfo.getProjectConclude());
		    		e3Info.setReviewTime(Info.getAccredDate());
		    		planInfo.getE3().add(e3Info);
		    		YearInvestPlan.save(planInfo);
			 }
    			
    	 }
    	 else if(accredType.equals(AccredTypeEnum.accred)){ 
    			for (int i = 0; i <coll.size(); i++) {
					YIPlanAccredE1Info yipInfo = coll.get(i);
					BOSUuid yipid =yipInfo.getProjectName().getId();
		    		YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
//		    		planInfo.setObjectState(ObjectStateEnum.accredit);
		    		YearInvestPlanE3Info e3Info = new YearInvestPlanE3Info();
		    		e3Info.setReviewStage(Info.getAccredType());
		    		e3Info.setAccredConclusion(yipInfo.getProjectConclude());
		    		e3Info.setReviewTime(Info.getAccredDate());
		    		planInfo.getE3().add(e3Info);
		    		YearInvestPlan.save(planInfo);
			 
			   }
    	 }
    		else{ 
    			for (int i = 0; i <coll.size(); i++) {
					YIPlanAccredE1Info yipInfo = coll.get(i);
					BOSUuid yipid =yipInfo.getProjectName().getId();
		    		YearInvestPlanInfo planInfo = YearInvestPlan.getYearInvestPlanInfo(new ObjectUuidPK(yipid));
//		    		planInfo.setObjectState(ObjectStateEnum.approval);
		    		YearInvestPlanE3Info e3Info = new YearInvestPlanE3Info();
		    		e3Info.setReviewStage(Info.getAccredType());
		    		e3Info.setAccredConclusion(yipInfo.getProjectConclude());
		    		e3Info.setReviewTime(Info.getAccredDate());
		    		planInfo.getE3().add(e3Info);
		    		YearInvestPlan.save(planInfo);
			 
			   
			   }
    		}
     }
    
    protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {//反审核
    	super._unAudit(ctx, pk);
    	YIPlanAccredInfo Info = getYIPlanAccredInfo(ctx, pk);
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
			   }
    		}
    }
    
    protected void _unAudit(Context ctx, IObjectPK[] pks) throws BOSException,EASBizException {
    	super._unAudit(ctx, pks);
    	
    }
}