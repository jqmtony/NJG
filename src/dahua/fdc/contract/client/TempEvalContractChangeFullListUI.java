/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.commonquery.client.DefaultPromptBoxFactory;
import com.kingdee.eas.base.commonquery.client.IPromptBoxFactory;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.CostPropertyEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class TempEvalContractChangeFullListUI extends AbstractTempEvalContractChangeFullListUI {
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	private Map proLongNameMap=new HashMap();
	/**
	 * output class constructor
	 */
	public TempEvalContractChangeFullListUI() throws Exception {
		super();
	}
	protected boolean isShowAttachmentAction() {
		
		return false;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIName() {
		return ContractChangeBillEditUI.class.getName();
	}

	protected void execQuery() {
		super.execQuery();
		
		FDCClientUtils.fmtFootNumber(this.tblMain, new String[]{"amount", "deductAmount","balanceAmount","changeAudit.costNouse"});
		FDCClientHelper.initTable(tblMain);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractChangeBillFactory.getRemoteInstance();
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());

		IPromptBoxFactory factory = new DefaultPromptBoxFactory() {
			public KDPromptBox create(String queryName,
					EntityObjectInfo entity, String propertyName) {
				return super.create(queryName, entity, propertyName);
			}

			public KDPromptBox create(String queryName, QueryInfo mainQuery,
					String queryFieldName) {
				final KDBizPromptBox f7 = (KDBizPromptBox) super.create(
						queryName, mainQuery, queryFieldName);
				if (queryName.equalsIgnoreCase("com.kingdee.eas.fdc.contract.app.F7ContractBillQueryForContractChangeBill")) {
					f7.addSelectorListener(new SelectorListener() {
						public void willShow(SelectorEvent e) {
							f7.getQueryAgent().resetRuntimeEntityView();
							EntityViewInfo view = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							FilterItemCollection filterItems = filter.getFilterItems();

							//取当前组织成本中心及所有下级成本中心
							BOSUuid id = SysContext.getSysContext().getCurrentOrgUnit().getId();
							Set idSet = null;
							try {
								idSet = FDCClientUtils.genOrgUnitIdSet(id);
							} catch (Exception exe) {
								handUIExceptionAndAbort(exe);
							}
							if (idSet != null && idSet.size() > 0) {
								filterItems.add(new FilterItemInfo(
										"orgUnit.id", idSet,
										CompareType.INCLUDE));
							}
							filterItems.add(new FilterItemInfo("isAmtWithoutCost", Boolean.valueOf(true), CompareType.NOTEQUALS));
							view.setFilter(filter);
							f7.setEntityViewInfo(view);
						}
					});

				}
				return f7;
			}
		};
		commonQueryDialog.setPromptBoxFactory(factory);
		return commonQueryDialog;
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
    	getUIContext().put("isFromTempEvalConChangeListUI", Boolean.TRUE);
	}
	private FilterInfo getTempEvalConChangeQuery() {
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isFromTempEvalChange",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("contractBill.costProperty",CostPropertyEnum.TEMP_EVAL_VALUE));
		return filter;
	}
	 protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
	    	viewInfo=(EntityViewInfo)viewInfo.clone();
	    	try{
	    		viewInfo.getFilter().mergeFilter(getTempEvalConChangeQuery(), "and");
	    	}catch(Exception e){
	    		handUIExceptionAndAbort(e);
	    	}
	    	return super.getQueryExecutor(queryPK, viewInfo);
	    }

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new TempEvalContractChangeFullFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		return this.filterUI;
	}

	/**
	 * 
	 * 初始化默认过滤条件
	 * 
	 * @return 如果重载了（即做了初始化动作），请返回true;默认返回false;
	 */
	protected boolean initDefaultFilter() {
		return true;
	}

	protected String getKeyFieldName() {
		return super.getKeyFieldName();
	}
	public void onShow() throws Exception {
		super.onShow();
	}
	public void onLoad() throws Exception {
		FDCClientUtils.checkCurrentCostCenterOrg(this);
		tblMain.checkParsed();

		
		FDCClientHelper.addSqlMenu(this,this.menuEdit);
		this.proLongNameMap=FDCHelper.createTreeDataMap(CurProjectFactory.getRemoteInstance(),"name","\\");
		super.onLoad();
		
		this.setUITitle("暂估价合同变更查询");
		
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionLocate.setEnabled(false);
		this.actionCreateTo.setEnabled(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		this.actionCopyTo.setEnabled(false);
		this.actionAuditResult.setEnabled(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionNextPerson.setEnabled(false);
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		
		FDCClientHelper.initTable(tblMain);
	}
	public void onGetRowSet(IRowSet rowSet) {
		try {
			rowSet.beforeFirst();
			while (rowSet.next()) {
				String displayName="";
				String id=rowSet.getString("curProject.id");
				String orgName=rowSet.getString("orgUnit.name");
				if(orgName!=null){
					displayName+=orgName;
				}
				String proName = (String) this.proLongNameMap.get(id);
				if(proName!=null){
					displayName+="\\"+proName;
				}
				rowSet.updateString("curProject.name",displayName);
			}
			rowSet.beforeFirst();
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		super.onGetRowSet(rowSet);
	}
	protected String getEditUIModal() {
		// TODO 自动生成方法存根
		return UIFactoryName.NEWTAB;
	}
    protected boolean isIgnoreCUFilter() {
        return true;
    }
    
    protected OrgType getMainBizOrgType() {
		return OrgType.CostCenter;
	}
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	
    }
    
	/**
     * 设置是否显示合计行
     * 2005-03-09 haiti_yang
     */
    protected boolean isFootVisible()
    {
        return true;
    }
}