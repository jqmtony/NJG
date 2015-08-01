/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.CommitListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.DefaultPromptBoxFactory;
import com.kingdee.eas.base.commonquery.client.IPromptBoxFactory;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;
import com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class FDCBIRptBaseMainUI extends AbstractFDCBIRptBaseMainUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FDCBIRptBaseMainUI.class);

	/**
	 * output class constructor
	 */
	public FDCBIRptBaseMainUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
		
		hideButton();
	}

	/**
	 * output actionFilt_actionPerformed
	 */
	public void actionFilt_actionPerformed(ActionEvent e) throws Exception {
		super.actionFilt_actionPerformed(e);
		
		hideButton();
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionDisplayConfig_actionPerformed
	 */
	public void actionDisplayConfig_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDisplayConfig_actionPerformed(e);
	}

	/**
	 * output actionSwapAxes_actionPerformed
	 */
	public void actionSwapAxes_actionPerformed(ActionEvent e) throws Exception {
		super.actionSwapAxes_actionPerformed(e);
	}

	/**
	 * output actionJoinQuery_actionPerformed
	 */
	public void actionJoinQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionJoinQuery_actionPerformed(e);
	}

	protected BireportBaseFilterUI getQueryDialogUserPanel() throws Exception {
		/* TODO 自动生成方法存根 */
		return null;
	}

	protected IBireportBaseFacade getRemoteInstance() throws BOSException {
		/* TODO 自动生成方法存根 */
		return null;
	}

	protected RptParams getParamsForInit() {
		/* TODO 自动生成方法存根 */
		return null;
	}

	protected RptParams getParamsForRequest() {
		/* TODO 自动生成方法存根 */
		return null;
	}

	protected void onBeforeQuery() throws Exception {
		/* TODO 自动生成方法存根 */

	}

	protected void onAfterQuery() throws Exception {
		/* TODO 自动生成方法存根 */

	}

	protected KDTable getTableForPrintSetting() {
		/* TODO 自动生成方法存根 */
		return null;
	}

	protected void preparePrintPageHeader(HeadFootModel header) {
		/* TODO 自动生成方法存根 */

	}

	protected Map preparePrintVariantMap() {
		/* TODO 自动生成方法存根 */
		return null;
	}

	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		
		 hideButton();
	}
	
	//隐藏按钮
	public void  hideButton(){
		actionJoinQuery.setEnabled(false);
		actionJoinQuery.setVisible(false);
		btnJoinQuery.setVisible(false);
		
		actionSwapAxes.setEnabled(false); // 行列交换
		actionSwapAxes.setVisible(false);
		btnSwapAxes.setVisible(false);
		
		actionChartAnalysis.setEnabled(false); // 图形分析
		actionChartAnalysis.setVisible(false);
		btnChartAnalysis.setVisible(false);
		
		actionShowSortRank.setEnabled(false); // 排序
		actionShowSortRank.setVisible(false);
		btnShowSortRank.setVisible(false);

		actionDisplayConfig.setEnabled(false); // 显示设置
		actionDisplayConfig.setVisible(false);
		btnDisplayConfig.setVisible(false);
		
		actionShowSlice.setEnabled(false); // 切片
		actionShowSlice.setVisible(false);
		btnShowSlice.setVisible(false);		
		
		actionCustomChart.setEnabled(false); // 自定义图形
		actionCustomChart.setVisible(false);
		btnCustomChart.setVisible(false);
		
		//显示图形
		actionShowChart.setVisible(false);
		btnShowChart.setVisible(false);
		actionShowChart.setEnabled(false); 

	}
	
	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		super.onShow();

		actionShowChart.setEnabled(false); // 显示图形
		actionShowChart.setVisible(false);
		btnShowChart.setVisible(false);
	}

	protected IMetaDataPK getQueryMetaDataPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.basedata.app",
				"ProjectIndexDataQuery");
		
	}

	protected void initQueryDialog(CommonQueryDialog queryDialog) {
		super.initQueryDialog(queryDialog);
		queryDialog.setPromptBoxFactory(getMyPromptBoxFactory());
	}
	
	private IPromptBoxFactory getMyPromptBoxFactory(){
		IPromptBoxFactory factory = new DefaultPromptBoxFactory() {
			public KDPromptBox create(String queryName,
					EntityObjectInfo entity, String propertyName) {
				return super.create(queryName, entity, propertyName);
			}
			public KDPromptBox create(String queryName, QueryInfo mainQuery,
					String queryFieldName) {
				KDBizPromptBox f7 = (KDBizPromptBox) super.create(
						queryName, mainQuery, queryFieldName);
				if (queryName.equalsIgnoreCase("com.kingdee.eas.fdc.basedata.app.F7ApportionTypeQuery")) {
					if(queryFieldName!=null&&queryFieldName.equals("apportionType.fnumber")){
						f7.setEditFormat("$number$");
						f7.setDisplayFormat("$number$-$name$");
						f7.setCommitFormat("$number$");
					}
					if(queryFieldName!=null&&queryFieldName.equals("apportionType.fname")){
						f7.setEditFormat("$name$");
						f7.setDisplayFormat("$number$-$name$");
						f7.setCommitFormat("$name$");
					}
					f7.addSelectorListener(new SelectorListener() {
						public void willShow(SelectorEvent e) {
							KDBizPromptBox f7 =(KDBizPromptBox)e.getSource();
							f7.getQueryAgent().setDefaultFilterInfo(null);
							f7.getQueryAgent().setHasCUDefaultFilter(false);
							f7.getQueryAgent().resetRuntimeEntityView();
							EntityViewInfo view = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							try {
								filter.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
							} catch (BOSException e1) {
								handUIExceptionAndAbort(e1);
							}
							view.setFilter(filter);
							f7.setEntityViewInfo(view);
						}
					});
					f7.addCommitListener(new CommitListener(){
						public void willCommit(CommitEvent e) {
							KDBizPromptBox f7 =(KDBizPromptBox)e.getSource();
							f7.getQueryAgent().setDefaultFilterInfo(null);
							f7.getQueryAgent().setHasCUDefaultFilter(false);
							f7.getQueryAgent().resetRuntimeEntityView();
							EntityViewInfo view = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							try {
								filter.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
							} catch (BOSException e1) {
								handUIExceptionAndAbort(e1);
							}
							view.setFilter(filter);
							f7.setEntityViewInfo(view);
						}
					});
				}
				return f7;
			}
		};
		return factory;
	}
	protected boolean isQueryDialogShowFilter() {
		return true;
	}

	protected boolean isQueryDialogShowSorter() {
		return false;
	}
}