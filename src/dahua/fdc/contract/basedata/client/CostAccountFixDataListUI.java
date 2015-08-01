package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeSelectionEvent;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFixDataFacadeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ICostAccountFixDataFacade;

public class CostAccountFixDataListUI extends CostAccountListUI{

	public CostAccountFixDataListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setEnabled(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionEdit.setVisible(false);
		this.actionAssignToOrg.setEnabled(false);
		this.actionAssignToOrg.setVisible(false);
		this.actionCancel.setEnabled(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setEnabled(false);
		this.actionCancelCancel.setVisible(false);
		this.actionImport.setEnabled(false);
		this.actionImport.setVisible(false);
		this.actionImportData.setEnabled(false);
		this.actionImportData.setVisible(false);
		this.actionProjectAttachment.setEnabled(false);
		this.actionProjectAttachment.setVisible(false);
		this.actionTemplateImport.setEnabled(false);
		this.actionTemplateImport.setVisible(false);

		btnAssign.setText("修复数据");
		btnAssign.setToolTipText("修复数据");
	}
	
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		this.btnAssign.setEnabled(true);
		this.actionAssign.setEnabled(true);
	}

	public void actionAssign_actionPerformed(ActionEvent e) throws Exception {

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			
			List list = new ArrayList();
			while (node.getParent() != null) {
				node = (DefaultKingdeeTreeNode) node.getParent();
				if (node.getUserObject() instanceof OrgStructureInfo) {
					OrgStructureInfo oui1 = (OrgStructureInfo) node.getUserObject();
					if (oui1 == null || oui1.getUnit() == null)
						return;
					FullOrgUnitInfo info1 = oui1.getUnit();
					list.add(info1);
				} else if (node.getUserObject() instanceof CurProjectInfo) {
					CurProjectInfo projectInfo1 = (CurProjectInfo) node.getUserObject();
					list.add(projectInfo1);
				}
			}
			ICostAccountFixDataFacade iCostAccountFacade = CostAccountFixDataFacadeFactory.getRemoteInstance();
			iCostAccountFacade.fixPrjSourseID(projectInfo.getId(), list);
			tblMain.removeRows();// 触发新查询
			
		} else if (node.getUserObject() instanceof OrgStructureInfo) {

			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui == null || oui.getUnit() == null)
				return;
			FullOrgUnitInfo info = oui.getUnit();
			
			List list = new ArrayList();
			while (node.getParent() != null) {
				node = (DefaultKingdeeTreeNode) node.getParent();
				if (node.getUserObject() instanceof OrgStructureInfo) {
					OrgStructureInfo oui1 = (OrgStructureInfo) node.getUserObject();
					if (oui1 == null || oui1.getUnit() == null)
						return;
					FullOrgUnitInfo info1 = oui1.getUnit();
					list.add(info1);
				}
			}

			ICostAccountFixDataFacade iCostAccountFacade = CostAccountFixDataFacadeFactory.getRemoteInstance();
			iCostAccountFacade.fixOrgSourseID(info.getId(), list);
			tblMain.removeRows();// 触发新查询
		}
	}
}
