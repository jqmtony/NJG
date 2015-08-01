/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.permission.OrgRangeIncludeSubOrgCollection;
import com.kingdee.eas.base.permission.OrgRangeIncludeSubOrgFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeCollection;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * 描述:项目汇总查询
 * @author jackwang  date:2007-6-8 <p>
 * @version EAS5.3
 */
public class ProjectUnionFilterUI extends AbstractProjectUnionFilterUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectUnionFilterUI.class);

	protected ItemAction actionListOnLoad;

	private boolean isLoaded;

	protected ListUI listUI;

	private TreeSelectDialog projectSelectDlg;

	private static final String PROJECT_IDS = "projectIds";
	private static final String PROJECT_TYPE = "projectType";
	
   //add starting date and ending date for limitation
	public static final String UNION_START_DATE = "startDate";
	public static final String UNION_END_DATE = "endDate";

	/**
	 * output class constructor
	 */
	public ProjectUnionFilterUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
		this.bizProjectType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectTypeQuery");
		this.bizProjectType.setEditable(true);
		this.bizProjectType.setRequired(false);
		
		bizProjectType.setEnabledMultiSelection(true);
		bizProjectType.setDisplayFormat("$name$");
		bizProjectType.setEditFormat("$number$");
		bizProjectType.setCommitFormat("$number$");
		bizProjectType.setRequired(false);
		prmtProject.setEnabledMultiSelection(true);
		prmtProject.setDisplayFormat("$name$");
		prmtProject.setEditFormat("$number$");
		prmtProject.setCommitFormat("$number$");
		prmtProject.setRequired(true);
		
//		this.kDUnionStartDate.setValue(null);
//		this.kDUnionEndDate.setValue(null);
	}

	public void clear() {
		this.bizProjectType.setValue(null);
		this.prmtProject.setText(null);
	}

	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		FilterInfo filter = new FilterInfo();
		if (para.isNotNull(PROJECT_TYPE)) {
			filter.getFilterItems().add(new FilterItemInfo("projectType.id", para.getString(PROJECT_TYPE),CompareType.INCLUDE));
		}
		if (para.isNotNull(PROJECT_IDS)) {
			filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.getSetByArray(para.getStringArray(PROJECT_IDS)), CompareType.INCLUDE));
		}
		
		//add two restrictions of start-date and end-date 
		if(para.isNotNull(ProjectUnionFilterUI.UNION_START_DATE))
		{
			filter.getFilterItems().add(
					new FilterItemInfo("createTime", DateTimeUtils.truncateDate((Date)para.getDate(ProjectDFFilterUI.START_DATE)),
							CompareType.GREATER_EQUALS));
		}
		if(para.isNotNull(ProjectUnionFilterUI.UNION_END_DATE))
		{
			filter.getFilterItems().add(
					new FilterItemInfo("createTime", DateTimeUtils.truncateDate((Date)para.getDate(ProjectDFFilterUI.END_DATE)),
							CompareType.LESS_EQUALS));
		}
		
		return filter;
	}

	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();
		String[] projTypeIds = FDCHelper.getF7Ids(bizProjectType);
		String[] projIds = FDCHelper.getF7Ids(prmtProject);
		if (!FDCHelper.isEmpty(projIds)) {
			param.add(PROJECT_IDS, projIds);
		}
		
		/*
         * add starting date and ending date for limitation
         * two dates can be empty
         */
		
		if(this.kDUnionStartDate.getValue()== null || this.kDUnionEndDate.getValue() == null)
		{
			java.text.SimpleDateFormat   df   =   new   java.text.SimpleDateFormat("yyyy-MM-dd");   
	        try {
				Date   startDate   =   df.parse("1900-01-01");
				param.add(UNION_START_DATE, startDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}  
			
			Date endDate = new Date();
			param.add(UNION_END_DATE, endDate);
		}
		else
		{
			Date startDate = (Date)this.kDUnionStartDate.getValue();
			param.add(UNION_START_DATE, startDate);
			
			Date endDate = (Date)this.kDUnionEndDate.getValue();
			param.add(UNION_END_DATE, endDate);
		}
		
		if (this.bizProjectType.getValue() != null) {
			param.add(PROJECT_TYPE, projTypeIds);
		} else {
			//给出提示
		}
		return param.getCustomerParams();
	}

	public void setCustomerParams(CustomerParams cp) {
		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		if(para.getString(PROJECT_TYPE) != null) {
			String[] ids = para.getStringArray(PROJECT_TYPE);
			ProjectTypeCollection infos = null;
			try {
				EntityViewInfo view=new EntityViewInfo();
				view.setFilter(new FilterInfo());
				view.getFilter().getFilterItems().add(new FilterItemInfo("id",FDCHelper.getSetByArray(ids), CompareType.INCLUDE));
				view.getSelector().add("id");
				view.getSelector().add("number");
				view.getSelector().add("name");
				infos = ProjectTypeFactory.getRemoteInstance().getProjectTypeCollection(view);
			} catch (Exception e) {
				handUIException(e);
			} 
			bizProjectType.setValue(infos.toArray());
		}
		if(para.getString(PROJECT_IDS) != null) {
			String[] ids = para.getStringArray(PROJECT_IDS);
			CurProjectCollection infos = null;
			try {
				EntityViewInfo view=new EntityViewInfo();
				view.setFilter(new FilterInfo());
				view.getFilter().getFilterItems().add(new FilterItemInfo("id",FDCHelper.getSetByArray(ids), CompareType.INCLUDE));
				view.getSelector().add("id");
				view.getSelector().add("number");
				view.getSelector().add("name");
				infos = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);
			} catch (Exception e) {
				handUIException(e);
			} 
			prmtProject.setValue(infos.toArray());
		}

		super.setCustomerParams(para.getCustomerParams());
	}

	public boolean verify() {
//		if (this.bizProjectType.getValue() == null) {
//			MsgBox.showWarning(this, "项目系列不能为空");
//			return false;
//		}
		if (FDCHelper.isEmpty(FDCHelper.getF7Ids(prmtProject))) {
			MsgBox.showWarning(this, "工程项目不能为空");
			return false;
		}
		Date start = (Date) this.kDUnionStartDate.getValue();
		Date end = (Date) this.kDUnionEndDate.getValue();
		if(start!=null&&end!=null){
			if(start.after(end)){
					MsgBox.showWarning(this, "开始日期不能大于结束日期!");
					this.kDUnionStartDate.requestFocus();
					return false;
			}
		}
		return true;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output pbAccount_focusLost method
	 */
	protected void pbAccount_focusLost(java.awt.event.FocusEvent e) throws Exception {
		super.pbAccount_focusLost(e);
	}

	public Map getResult() {

		HashMap result = new HashMap();
		//////////////////////////
		if (this.bizProjectType.getValue() != null) {
			result.put(PROJECT_TYPE, FDCHelper.getF7Ids(bizProjectType));
		} else {
			//给出提示
		}
		String[] projIds = FDCHelper.getF7Ids(prmtProject);
		if (!FDCHelper.isEmpty(projIds)) {
			result.put(PROJECT_IDS, projIds);
			result.put("projectSet", FDCHelper.getSetByArray(projIds));
		}
		return result;
	}
	
	protected void bizProjectType_dataChanged(DataChangeEvent e)
			throws Exception {
		super.bizProjectType_dataChanged(e);
		prmtProject.setValue(null);
	}
	
	protected void prmtProject_willShow(SelectorEvent e) throws Exception {
		prmtProject.getQueryAgent().resetRuntimeEntityView();
		String[] projectTypeIds= FDCHelper.getF7Ids(bizProjectType);
		EntityViewInfo view = prmtProject.getEntityViewInfo();
		if(view==null){
			view=new EntityViewInfo();
		}
		FilterInfo filter=new FilterInfo();
		if(projectTypeIds!=null){
			filter.getFilterItems().add(new FilterItemInfo("projectType.id", FDCHelper.getSetByArray(projectTypeIds),CompareType.INCLUDE));
		}
		
		// modified by zhaoqin for R130922-0136 on 2013/11/06
		setCurrentUserFilter(filter);
		
		view.setFilter(filter);
		prmtProject.setEntityViewInfo(view);
		super.prmtProject_willShow(e);
	
	}
	
	/**
	 * R130922-0136: 当前用户具有权限的工程项目
	 * @author zhaoqin
	 * @date 2013/11/06
	 */
	private void setCurrentUserFilter(FilterInfo filterInfo) {
		try {
			// 当前用户具有权限的所有成本组织
			UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("id");
			selectors.add("user.id");
			selectors.add("org.id");
			EntityViewInfo v = new EntityViewInfo();
			FilterInfo f = new FilterInfo();
			f.getFilterItems().add(new FilterItemInfo("user.id", user.getId().toString()));
			//f.getFilterItems().add(new FilterItemInfo("org.isCostOrgUnit", Boolean.TRUE));	// 成本中心
			v.setFilter(f);
			v.setSelector(selectors);
			OrgRangeIncludeSubOrgCollection orisos = OrgRangeIncludeSubOrgFactory.getRemoteInstance().getOrgRangeIncludeSubOrgCollection(v);
			Set orgs = new HashSet();
			if(null != orisos) {
				for(int i = 0; i < orisos.size(); i++)
					orgs.add(orisos.get(i).getOrg().getId().toString());
			}
			//filterInfo.getFilterItems().add(new FilterItemInfo("costCenter.id", orgs, CompareType.INCLUDE));	// 只关联了中心的工程项目
			filterInfo.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", orgs, CompareType.INCLUDE));	// 所有工程项目
			filterInfo.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}