/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.ProjectCollection;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.port.pm.base.FundSourceInfo;
import com.kingdee.eas.port.pm.base.ProjectTypeFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.helper.TableXRHelper;

/**
 * output class name
 */
public class YearInvestPlanListUI extends AbstractYearInvestPlanListUI
{
    private static final Logger logger = CoreUIObject.getLogger(YearInvestPlanListUI.class);
    
    /**
     * output class constructor
     */
    public YearInvestPlanListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	tblMain.getDataRequestManager().setDataRequestMode(1);
    }
    public void onShow() throws Exception {
    	super.onShow();
    	TableXRHelper.getFootRow(tblMain, new String[]{"amount","investAmount"});
    }
    protected String getEditUIModal() {
    	return UIFactoryName.NEWTAB;
    }
    void checkProject(YearInvestPlanInfo info){
    	if(info.getProject()==null){
			MsgBox.showInfo("项目未立项，不能进行该操作！");
			SysUtil.abort();
		}
    }
	public void actionAdjust_actionPerformed(ActionEvent e) throws Exception {
		super.actionAdjust_actionPerformed(e);
		UIContext context = new UIContext(this);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(tblMain.getRow(rowIndex)!=null){
			String id = (String)tblMain.getRow(rowIndex).getCell("id").getValue();
			YearInvestPlanInfo info = YearInvestPlanFactory.getRemoteInstance().getYearInvestPlanInfo(new ObjectUuidPK(id),getSelector());
			checkProject(info);
			ProgrammingInfo programmingInfo = ProjectEstimateEditUI.getProgrammingInfo(info.getPortProject());
			programmingInfo.setVersion(null);
			context.put("programmingInfo", programmingInfo);
			context.put("projectInfo-Adjuest", info);
			context.put("project", info.getProject());
			context.put("ID", info.getId());
			UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(YearInvestPlanEditUI.class.getName(), context, null,OprtState.ADDNEW).show();
		}
	}
	
	public void actionChange_actionPerformed(ActionEvent e) throws Exception {
		super.actionChange_actionPerformed(e);
		UIContext context = new UIContext(this);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(tblMain.getRow(rowIndex)!=null){
			String id = (String)tblMain.getRow(rowIndex).getCell("id").getValue();
			YearInvestPlanInfo info = YearInvestPlanFactory.getRemoteInstance().getYearInvestPlanInfo(new ObjectUuidPK(id),getSelector());
			checkProject(info);
			FundSourceInfo source = new FundSourceInfo();
			source.setId(BOSUuid.create(source.getBOSType()));
			info.setFundSource(source);
			context.put("projectInfo-Change", info);
			context.put("project", info.getProject());
			UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(YearInvestPlanEditUI.class.getName(), context, null,OprtState.ADDNEW).show();
		}
	}
	
	public void actionContinue_actionPerformed(ActionEvent e) throws Exception {
		super.actionContinue_actionPerformed(e);
		UIContext context = new UIContext(this);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(tblMain.getRow(rowIndex)!=null){
			String id = (String)tblMain.getRow(rowIndex).getCell("id").getValue();
			YearInvestPlanInfo info = YearInvestPlanFactory.getRemoteInstance().getYearInvestPlanInfo(new ObjectUuidPK(id),getSelector());
			checkProject(info);
			ProgrammingInfo programmingInfo = ProjectEstimateEditUI.getProgrammingInfo(info.getProject());
			programmingInfo.setVersion(null);
			context.put("programmingInfo", programmingInfo);
			context.put("projectInfo-Continue", info);
			context.put("project", info.getProject());
			UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(YearInvestPlanEditUI.class.getName(), context, null,OprtState.ADDNEW).show();
		}
	}

	protected FilterInfo getDefaultFilterForQuery() {
    	FilterInfo  filter = new FilterInfo();
    	String cuNumber=SysContext.getSysContext().getCurrentCtrlUnit().getLongNumber();
    	filter.getFilterItems().add(new FilterItemInfo("CU.longnumber",cuNumber+"%",CompareType.LIKE));
    	return filter;
    }
    
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invest.YearInvestPlanFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invest.YearInvestPlanInfo objectValue = new com.kingdee.eas.port.pm.invest.YearInvestPlanInfo();
		
        return objectValue;
    }
    public void actionQuery_actionPerformed(ActionEvent arg0) throws Exception {
    	super.actionQuery_actionPerformed(arg0);
    	TableXRHelper.getFootRow(tblMain, new String[]{"amount","investAmount"});
    }
    /**
     * output getSelectors method
     */
    public static SelectorItemCollection getSelector()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
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
    	sic.add(new SelectorItemInfo("E3.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E3.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E3.reviewTime"));
    	sic.add(new SelectorItemInfo("E3.reviewStage"));
    	sic.add(new SelectorItemInfo("E3.accredConclusion"));
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
        sic.add(new SelectorItemInfo("portProject.id"));
    	sic.add(new SelectorItemInfo("portProject.number"));
    	sic.add(new SelectorItemInfo("portProject.name"));
    	sic.add(new SelectorItemInfo("projectName"));
        sic.add(new SelectorItemInfo("project.id"));
     	sic.add(new SelectorItemInfo("project.number"));
     	sic.add(new SelectorItemInfo("project.name"));
        return sic;
    }     
}