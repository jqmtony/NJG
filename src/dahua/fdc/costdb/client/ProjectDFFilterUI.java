/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DanFangTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeCollection;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class ProjectDFFilterUI extends AbstractProjectDFFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectDFFilterUI.class);
    
    /**
     * 描述：绑定Query不能满足现有动太报表的取数，现在行业开发为了保存查询方案随便与一Query绑定。因为目前bos对绑定query的都会执行相关代码，对查询方案的字段进行匹配。<p>
     * 所以如下查询方案参数需与query某个字段匹配，但实际中的SQL是通过写代码取参数来执行查询方案的而不是通过Query<p>
     */
    public static final String PROJECT_TYPE_IDS = "curProject.id";//"projectTypeIds ";

	public static final String PRODUCT_TYPE_IDS = "curProject.number";//"productTypeIds";

	public static final String DF_TYPE_VALUE = "state";//"dfTypeValue";
	
	public static final String PROJECT_IDS = "id";//"projectIds";
	
	public static final String PROJECT_TYPE_NAMES = "curProject.name";//projectTypeNames";
	
	public static final String DF_TYPE_NAME = "currency.name";//dfTypeName";
	public static final String CHKWHOLEPROJECT = "name";//chkWholeProject";
	
	//add starting date and ending date for limitation
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	
	protected ItemAction actionListOnLoad;


	protected ListUI listUI;


	/**
	 * output class constructor
	 */
	public ProjectDFFilterUI(ListUI listUI, ItemAction actionListOnLoad)
			throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
	}
    
    /**
     * output class constructor
     */
    public ProjectDFFilterUI() throws Exception
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

    public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();
		
		Object[] data = FDCHelper.getF7Data(prmtProjectType);
		if(!FDCHelper.isEmpty(data)){
			String projectTypeIds [] = new String[data.length];
			String projectTypeNames [] = new String[data.length];
			for(int i=0;i<data.length;i++){
				if(data[i] instanceof ProjectTypeInfo){
					projectTypeIds[i]=((ProjectTypeInfo)data[i]).getId().toString();
					projectTypeNames[i]=((ProjectTypeInfo)data[i]).getName();
				}
			}
			if(!FDCHelper.isEmpty(projectTypeIds)) {
				param.add(PROJECT_TYPE_IDS, projectTypeIds);
				param.add(PROJECT_TYPE_NAMES, projectTypeNames);
			}
			
		}
		String[] projIds = FDCHelper.getF7Ids(prmtProject);
		if(!FDCHelper.isEmpty(projIds)) {
			param.add(PROJECT_IDS, projIds);
		}
		
		String[] productTypeIds = FDCHelper.getF7Ids(prmtProductType);
		if(!FDCHelper.isEmpty(productTypeIds)) {
			param.add(PRODUCT_TYPE_IDS, productTypeIds);
		}
		
		DanFangTypeEnum dfType = (DanFangTypeEnum)comboDFType.getSelectedItem();
		
		if(dfType != null) {
			param.add(DF_TYPE_VALUE, dfType.getValue());
			param.add(DF_TYPE_NAME, dfType.getAlias());
		}
		
        /*
         * add starting date and ending date for limitation
         * two dates can be empty
         */
		Date startDate = null;
		Date endDate = null;
		if(this.kDStartDate.getValue()== null || this.kDEndDate.getValue() == null)
		{
			java.text.SimpleDateFormat   df   =   new   java.text.SimpleDateFormat("yyyy-MM-dd");   
	        try {
				startDate = df.parse("1900-01-01");
			} catch (ParseException e) {
				e.printStackTrace();
			}  
			endDate = new Date();
		}else{
			startDate = (Date)this.kDStartDate.getValue();
			endDate = (Date)this.kDEndDate.getValue();
			//相隔一天时不能查到结束日期的记录,，所以加一天。如start2007-07-10end2007-07-11不能查询到2007-07-11记录
//			endDate = endDate.after(startDate)?endDate:DateTimeUtils.addDay(endDate,1);
		}
//		startDate = DateTimeUtils.addMonth(startDate, 1);
//		endDate = DateTimeUtils.addMonth(endDate, 1);
		param.add(START_DATE, startDate);
		param.add(END_DATE, endDate);
		
		param.add(CHKWHOLEPROJECT, chkWholeProject.isSelected());
		return param.getCustomerParams();
    }
    
	public void setCustomerParams(CustomerParams  cp) {
		
		if(cp == null) return;
		
		FDCCustomerParams para = new FDCCustomerParams(cp);
		
		if(para.getString(PROJECT_TYPE_IDS) != null) {
			String[] ids = (String[])para.getStringArray(PROJECT_TYPE_IDS);
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
			prmtProjectType.setValue(infos.toArray());
		}
		if(para.getString(PROJECT_IDS) != null) {
			String[] ids = (String[])para.getStringArray(PROJECT_IDS);
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
		if(para.getString(PRODUCT_TYPE_IDS) != null) {
			String[] ids = (String[])para.getStringArray(PRODUCT_TYPE_IDS);
			if(!FDCHelper.isEmpty(ids)) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.getSetByArray(ids), CompareType.INCLUDE));
				view.setFilter(filter);
				view.getSelector().add("id");
				view.getSelector().add("number");
				view.getSelector().add("name");
				
				ProductTypeCollection colls = null;
				try {
					colls = ProductTypeFactory.getRemoteInstance().getProductTypeCollection(view);
				} catch (Exception e) {
					handUIException(e);
				} 
				prmtProductType.setValue(colls.toArray());
			}
		}
		
		if(para.getString(DF_TYPE_VALUE) != null) {
			String value = para.getString(DF_TYPE_VALUE);
			DanFangTypeEnum enume = DanFangTypeEnum.getEnum(value);
			FDCClientHelper.setSelectObject(comboDFType, enume);

		}

		/**
		 * 开始日期与结束日期存入方案。
		 * @author pengwei_hou Date: 2008-11-22
		 */
		Date startDate = null;
		Date endDate = null;  
		if(para.get(START_DATE) != null && para.get(END_DATE) != null){
//			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");   
//			try {
//				startDate = df.parse(para.get(START_DATE));
//				endDate = df.parse(para.get(END_DATE));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}  
			startDate = para.getDate(START_DATE);
			endDate = para.getDate(END_DATE);
		}else{
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");   
	        try {
				startDate   =   df.parse("1900-01-01");
			} catch (ParseException e) {
				e.printStackTrace();
			}  
			endDate = new Date();
		}
//		startDate = DateTimeUtils.addMonth(startDate, 1);
//		endDate = DateTimeUtils.addMonth(endDate, 1);
		this.kDStartDate.setValue(startDate);
		this.kDEndDate.setValue(endDate);
		
		chkWholeProject.setSelected(para.getBoolean(CHKWHOLEPROJECT));
	}
	
	protected void prmtProjectType_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtProjectType_dataChanged(e);
		prmtProject.setValue(null);
//		initProjectDlg(null);
	}

	public boolean verify() {
//		FDCClientVerifyHelper.verifyEmpty(this, prmtProjectType);
		Object content = prmtProject.getData();
		if(content==null||(content.getClass().isArray() &&FDCHelper.isEmpty(((Object[])content)))){
			FDCClientVerifyHelper.verifyEmpty(this, null, prmtProject, null, false);
			return false;
		}
		Date start = (Date) kDStartDate.getValue();
		Date end = (Date) kDEndDate.getValue();
		if(start!=null&&end!=null){
			if(start.after(end)){
					MsgBox.showWarning(this, "开始日期不能大于结束日期!");
					this.kDStartDate.requestFocus();
					return false;
			}
		}
		return super.verify();
	}
	
	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		FilterInfo filter=new FilterInfo();
		if(para.isNotNull(ProjectDFFilterUI.PROJECT_IDS)){
			filter.getFilterItems().add(new FilterItemInfo(ProjectDFFilterUI.PROJECT_IDS,FDCHelper.getSetByArray(para.getStringArray(ProjectDFFilterUI.PROJECT_IDS)),CompareType.INCLUDE));
		}else{
			MsgBox.showWarning(this,"请选择工程项目");
			SysUtil.abort();
		}
		if(para.isNotNull(ProjectDFFilterUI.PRODUCT_TYPE_IDS)){
			filter.getFilterItems().add(new FilterItemInfo(ProjectDFFilterUI.PRODUCT_TYPE_IDS,FDCHelper.getSetByArray(para.getStringArray(ProjectDFFilterUI.PRODUCT_TYPE_IDS)),CompareType.INCLUDE));
		}
		if(para.isNotNull(ProjectDFFilterUI.DF_TYPE_VALUE)){
			filter.getFilterItems().add(new FilterItemInfo(ProjectDFFilterUI.DF_TYPE_VALUE,para.getString(ProjectDFFilterUI.DF_TYPE_VALUE)));
		}
		
		if(para.isNotNull(ProjectDFFilterUI.DF_TYPE_NAME)){
			filter.getFilterItems().add(new FilterItemInfo(ProjectDFFilterUI.DF_TYPE_NAME,para.getString(ProjectDFFilterUI.DF_TYPE_NAME)));
		}
		
		if(para.isNotNull(ProjectDFFilterUI.PROJECT_TYPE_NAMES)){
			filter.getFilterItems().add(new FilterItemInfo(ProjectDFFilterUI.PROJECT_TYPE_NAMES,FDCHelper.getSetByArray(para.getStringArray(ProjectDFFilterUI.PROJECT_TYPE_NAMES)),CompareType.INCLUDE));
		}
		if(para.isNotNull(ProjectDFFilterUI.CHKWHOLEPROJECT)){
			filter.getFilterItems().add(new FilterItemInfo(ProjectDFFilterUI.CHKWHOLEPROJECT,para.getString(CHKWHOLEPROJECT)));
		}
		
		/*
		 * add two restrictions of start-date and end-date 
		 */
		if(para.isNotNull(ProjectDFFilterUI.START_DATE))
		{
			filter.getFilterItems().add(
					new FilterItemInfo("createTime", DateTimeUtils.truncateDate((Date)para.getDate(ProjectDFFilterUI.START_DATE)),
							CompareType.GREATER_EQUALS));
		}
		if(para.isNotNull(ProjectDFFilterUI.END_DATE))
		{
			filter.getFilterItems().add(
					new FilterItemInfo("createTime", DateTimeUtils.truncateDate((Date)para.getDate(ProjectDFFilterUI.END_DATE)),
							CompareType.LESS_EQUALS));
		}
		
		return filter;
	}
	
	protected void prmtProject_willShow(SelectorEvent e) throws Exception {
		prmtProject.getQueryAgent().resetRuntimeEntityView();
		String[] projectTypeIds= FDCHelper.getF7Ids(prmtProjectType);
		EntityViewInfo view = prmtProject.getEntityViewInfo();
		if(view==null){
			view=new EntityViewInfo();
		}
		FilterInfo filter=new FilterInfo();
		if(projectTypeIds!=null){
			filter.getFilterItems().add(new FilterItemInfo("projectType.id", FDCHelper.getSetByArray(projectTypeIds),CompareType.INCLUDE));
		}
		view.setFilter(filter);
		prmtProject.setEntityViewInfo(view);
		super.prmtProject_willShow(e);
	
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		prmtProjectType.setEnabledMultiSelection(true);
		prmtProjectType.setDisplayFormat("$name$");
		prmtProjectType.setEditFormat("$number$");
		prmtProjectType.setCommitFormat("$number$");
		prmtProjectType.setRequired(false);
		prmtProject.setEnabledMultiSelection(true);
		prmtProject.setDisplayFormat("$name$");
		prmtProject.setEditFormat("$number$");
		prmtProject.setCommitFormat("$number$");
		prmtProject.setRequired(true);
		prmtProductType.setEnabledMultiSelection(true);
		prmtProductType.setDisplayFormat("$name$");
		prmtProductType.setEditFormat("$number$");
		prmtProductType.setCommitFormat("$number$");
		
//		this.kDStartDate.setValue(null);
//		this.kDEndDate.setValue(null);
		
	}
	protected void chkWholeProject_stateChanged(ChangeEvent e) throws Exception {
		super.chkWholeProject_stateChanged(e);
		prmtProductType.setEnabled(!chkWholeProject.isSelected());
	}
}