/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;

/**
 * 描述:房地产项目F7界面
 * 
 * @author jackwang date:2007-6-7
 *         <p>
 * @version EAS5.3
 */
public class ProjectPromptUI extends AbstractProjectPromptUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ProjectPromptUI.class);

	protected boolean isCanceled = true;

	/**
	 * output class constructor
	 */
	public ProjectPromptUI() throws Exception {
		super();
	}

	public String getUITitle() {
		// String returnValue = EASResource.getString(isF7 ?
		// "com.kingdee.eas.fdc.basedata.FDCBaseDataResource.CostAccount" :
		// "com.kingdee.eas.fdc.basedata.FDCBaseDataResource.CostAccountImport");
		return "工程项目F7";
	}

	public void onLoad() throws Exception {
		// tblMain.checkParsed();// table解析!
		// this.tblMain.getDataRequestManager().addDataFillListener(new
		// KDTDataFillListener() {
		// public void afterDataFill(KDTDataRequestEvent e) {
		// // do something
		// String strTemp = "";
		// for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
		// strTemp = tblMain.getRow(i).getCell(1).getValue().toString();
		// strTemp = strTemp.replace('!', '.');
		// tblMain.getRow(i).getCell(1).setValue(strTemp);
		// tblMain.getRow(i).getCell(0).setValue(strTemp);
		// tblMain.getRow(i).getCell(2).setValue(strTemp);
		// }
		// }
		// });
		super.onLoad();

		buildProjectTree();
		this.treeMain.setSelectionRow(0);
		// loadDatas(filter);
	}

	private void buildProjectTree() throws Exception {
		// if (isOrgFilter) {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(true);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		// } else {
		// ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(true,
		// true);
		// treeMain.setShowsRootHandles(true);
		// projectTreeBuilder.build(this, treeMain, actionOnLoad);
		// }
		treeMain.expandAllNodes(true,(TreeNode) treeMain.getModel().getRoot());
	}

	// 返回前当所选择的科目对象，多选。
	public CurProjectInfo getReturnValues() throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return null;
			// if(lastSelectNode!=null){
			// node = this.lastSelectNode;
			// }else{return;}
		} else {
			if (node.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo projectInfo = (CurProjectInfo) node
						.getUserObject();
				return projectInfo;
			} else {
				return null;
			}
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output treeMain_mouseClicked method
	 */
	protected void treeMain_mouseClicked(java.awt.event.MouseEvent e)
			throws Exception {
		super.treeMain_mouseClicked(e);
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			this.isCanceled = false;
			this.getUIWindow().close();
		}
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		super.treeMain_valueChanged(e);
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
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// super.actionSave_actionPerformed(e);
		this.isCanceled = false;
		this.getUIWindow().close();
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		// super.actionCancel_actionPerformed(e);
		isCanceled = true;
		this.getUIWindow().close();
	}

}