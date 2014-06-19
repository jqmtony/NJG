/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.render.IBasicRender;
import com.kingdee.bos.ctrl.kdf.util.render.SimpleTextRender;
import com.kingdee.bos.ctrl.swing.KDSeparator;
import com.kingdee.bos.ctrl.swing.event.TreePopupMenuEvent;
import com.kingdee.bos.ctrl.swing.event.TreePopupMenuListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICostAccountFacade;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ����:�ɱ���Ŀ���²�
 * 
 * @author jackwang date:2006-9-6
 *         <p>
 * @version EAS5.1
 */

public class CostAccountListUI extends AbstractCostAccountListUI {
	private static final Logger logger = CoreUIObject.getLogger(CostAccountListUI.class);

	private CostAccountTreeRender treeRender;

	private boolean isSetTreeDispalyStyle = true;

	/**
	 * output class constructor
	 */
	public CostAccountListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCTableHelper.setColumnMoveable(tblMain, true);
		this.chkIncludeChild.setSelected(true);
		this.chkIncludeChild.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				isSetTreeDispalyStyle = chkIncludeChild.isSelected();
				try {
					tblMain.removeRows();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		treeRender = new CostAccountTreeRender();
		tblMain.getColumn("number").setRenderer(treeRender);

		buildProjectTree();
		//��ť
		this.btnAssign.setIcon(EASResource.getIcon("imgTbtn_distribute"));// ���䰴ť		
		this.btnDisAssign.setIcon(EASResource.getIcon("imgTbtn_undistribute"));// �����䰴ť
		this.btnProjectAttachment.setIcon(EASResource.getIcon("imgTbtn_createcredence"));// ���̸�����ť
		this.btnCostAccountImport.setIcon(EASResource.getIcon("imgTbtn_citetree"));//���밴ť
		this.btnTemplateImport.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));//���밴ť
		//�˵�
		this.menuItemAssign.setIcon(EASResource.getIcon("imgTbtn_distribute"));
		this.menuItemDisAssign.setIcon(EASResource.getIcon("imgTbtn_undistribute"));
		this.menuItemProjectAttachment.setIcon(EASResource.getIcon("imgTbtn_createcredence"));
		this.menuItemImport.setIcon(EASResource.getIcon("imgTbtn_citetree"));
		this.menuItemTemplateImport.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));
		
		actionEnterDB.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_staruse"));
		actionCancelEnterDB.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_forbid"));

		this.btnProjectAttachment.setEnabled(true);
		this.menuItemProjectAttachment.setEnabled(true);
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
			this.menuItemTemplateImport.setVisible(true);
			this.menuItemTemplateImport.setEnabled(true);

			this.btnTemplateImport.setVisible(true);
			this.btnTemplateImport.setEnabled(true);
			
			actionEnterDB.setEnabled(true);
			actionCancelEnterDB.setEnabled(true);
		} else {
			this.menuItemTemplateImport.setVisible(false);
			this.btnTemplateImport.setVisible(false);
			actionEnterDB.setVisible(false);
			actionEnterDB.setEnabled(false);
			actionCancelEnterDB.setVisible(false);
			actionCancelEnterDB.setEnabled(false);
			tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(true);
		}
//		this.treeMain.setSelectionRow(0);
		if (treeMain.getRowCount() >= 1) {
			treeMain.setSelectionRow(0);
		}
		tblMain.getColumn("number").setWidth(160);
		tblMain.getColumn("name").setWidth(140);
		tblMain.getColumn("description").setWidth(100);
		
		tblMain.getColumn("isEnabled").setWidth(50);
		tblMain.getColumn("assigned").setWidth(50);
		tblMain.getColumn("isCostAccount").setWidth(50);
//		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
//		this.tblMain.getSelectManager().setSelectMode(2);// ��ѡ
		
		actionQuery.setEnabled(false);
		actionQuery.setVisible(false);
		//���ŵĿ�Ŀ���빦��
		
		if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())){
			actionImportData.setVisible(true);
			actionImportData.setEnabled(true);
		}else{
			actionImportData.setVisible(false);
			actionImportData.setEnabled(false);
		}
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		//�����������֯���𼶷���
		if(SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()){
			actionAssignToOrg.setEnabled(true);
		}else{
			actionAssignToOrg.setEnabled(false);
		}
		//�������ڿͻ��ֳ������˲����޸������ݴ����Ҷ������нϴ��Ӱ��,��ʱ���ṩ�˹��� by sxhong 2007-12-04
		actionDisAssign.setEnabled(false);
		actionDisAssign.setVisible(false);
		//���ÿ��Ա��浱ǰ��ʽ
		tHelper = new TablePreferencesHelper(this);
	}

	/**
	 * ��ʼ��ʱ����Tree��������������
	 */
	protected void initTree() throws Exception {

	}

	protected void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		projectTreeBuilder.setDevPrjFilter(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 1) {
			int columnIndex = e.getColIndex();
			// ѡ�еڶ���(number)ʱ
			if (columnIndex == 0) {
				int rowIndex = e.getRowIndex();
				IRow row = tblMain.getRow(rowIndex);
				if (row == null)
					return;
				Object obj = row.getCell("number").getValue();
				if (obj instanceof NumberExpandInfo) {
					NumberExpandInfo numberExpandInfo = (NumberExpandInfo) row.getCell("number").getValue();
					if (treeRender.inRect(numberExpandInfo, e.getX(), e.getY())) {
						setTreeDisplayStyle(row);
					}
				}
				return;
			}
		}
		super.tblMain_tableClicked(e);
	}

	public DefaultKingdeeTreeNode getSelectedTreeNode1() {
		return (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
	}

	public KDTreeNode getSelectedTreeNode() {
		return null;
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		boolean isSelectCurOrg = false;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CostCenterOrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentCostUnit();
		CurProjectInfo curProject = null;
		if(!currentCostUnit.isIsBizUnit()) {
			Object userObject2 = node.getUserObject();
			if(userObject2 instanceof OrgStructureInfo) {
				String id = ((OrgStructureInfo)userObject2).getUnit().getId().toString();
				if(id.equals(currentCostUnit.getId().toString())) {
					isSelectCurOrg = true;
				}
			}else if(userObject2 instanceof CurProjectInfo){
				curProject = (CurProjectInfo)userObject2;
			}
		}
		else {
			isSelectCurOrg = true;
		}
		
		
		if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
			if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled") != null) {
				boolean status = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue()).booleanValue();
				// ����ÿһ�е�isEnabled��ֵ�ı䣬����WBT��״̬Ҳ�ı�
				changeWBTEnabeld(status);
				// ˢ�±༭��ť״̬
			}
			// ���Ǽ���
			if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
					|| (SysContext.getSysContext().getCurrentCostUnit() != null && SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())) {
				if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isSource") != null) {
					boolean statusSource = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isSource").getValue()).booleanValue();
					if (statusSource) {// Դ���ѷ����Դ������ɾ��(�����޸�)
						// this.btnRemove.setEnabled(false);
						// this.menuItemRemove.setEnabled(false);
						// this.btnEdit.setEnabled(false);
						// this.menuItemEdit.setEnabled(false);//
						if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("assigned") != null
								 && isSelectCurOrg) {
							boolean statusAssign = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("assigned").getValue()).booleanValue();
							if (statusAssign) {
								this.btnRemove.setEnabled(false);
								this.btnEdit.setEnabled(true);
								this.btnAddNew.setEnabled(true);
								
								this.menuItemRemove.setEnabled(false);
								this.menuItemEdit.setEnabled(true);
								this.menuItemAddNew.setEnabled(true);
							} else {
								this.btnAddNew.setEnabled(true);
								this.btnEdit.setEnabled(true);
								this.btnRemove.setEnabled(true);

								this.menuItemAddNew.setEnabled(true);
								this.menuItemEdit.setEnabled(true);
								this.menuItemRemove.setEnabled(true);

							}
						}
						
					} else {// ��Դ(����������),�������޸�ɾ��,���������
						this.btnRemove.setEnabled(false);
						this.btnEdit.setEnabled(false);
						this.btnCancel.setEnabled(false);

						this.menuItemRemove.setEnabled(false);
						this.menuItemEdit.setEnabled(false);
						this.menuItemCancel.setEnabled(false);

						// ���ѡ�еĲ��Ƿ�Դ��Ҷ���,����������
						if (((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isLeaf").getValue()).booleanValue()
								 && (isSelectCurOrg || (curProject!=null&&curProject.isIsLeaf()&&!curProject.isIsDevPrj()))) {//���У���ϸ��Ŀ����
							// ���ѡ�е���Ҷ�ڵ�,���Ǹ�Ҷ�ڵ�������֯
							this.btnAddNew.setEnabled(true);
							this.menuItemAddNew.setEnabled(true);
						} else {//���ѡ�еĲ���Ҷ��㣬Ҫ�ж����¼��ڵ��Ƿ���Դ
							if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex() + 1) != null) {
								if (((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex() + 1).getCell("isSource").getValue()).booleanValue()
										 && isSelectCurOrg) {
									//�¼�ΪԴ
									this.btnAddNew.setEnabled(true);
									this.menuItemAddNew.setEnabled(true);
								} else {
									//��Դ
									this.btnAddNew.setEnabled(false);
									this.menuItemAddNew.setEnabled(false);
								}
							}

						}
						disabledWBT();
					}
				}

			} else {// �����ǰ����֯�Ǽ��ţ����ҵ�ǰѡ�е�������ڵ㲻�Ǽ��ţ���ô������༭
				if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("fullOrgUnit.id").getValue() != null) {
					String ouid = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("fullOrgUnit.id").getValue().toString();
					if (!OrgConstants.DEF_CU_ID.equals(ouid)) {
						this.btnAddNew.setEnabled(false);
						this.btnEdit.setEnabled(false);
						this.btnRemove.setEnabled(false);
						this.btnCostAccountImport.setEnabled(false);
						this.btnAssign.setEnabled(false);
						this.btnDisAssign.setEnabled(false);

						this.menuItemAddNew.setEnabled(false);
						this.menuItemEdit.setEnabled(false);
						this.menuItemRemove.setEnabled(false);
						this.menuItemImport.setEnabled(false);
						this.menuItemAssign.setEnabled(false);
						this.menuItemDisAssign.setEnabled(false);

					} else {
						this.btnAddNew.setEnabled(true);
						this.btnEdit.setEnabled(true);
						this.btnCostAccountImport.setEnabled(true);
						this.btnAssign.setEnabled(true);
						this.btnDisAssign.setEnabled(true);

						this.menuItemImport.setEnabled(true);
						this.menuItemAddNew.setEnabled(true);
						this.menuItemEdit.setEnabled(true);
						this.menuItemRemove.setEnabled(true);
						this.menuItemAssign.setEnabled(true);
						this.menuItemDisAssign.setEnabled(true);
						// �ѷ���Ĳ�����ɾ��
						if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("assigned") != null) {
							boolean status = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("assigned").getValue()).booleanValue();
							if (status) {
								this.btnRemove.setEnabled(false);
								this.menuItemRemove.setEnabled(false);
							} else {
								this.btnRemove.setEnabled(true);
								this.menuItemRemove.setEnabled(true);
							}
						}

					}
				} else {
					this.btnAddNew.setEnabled(false);
					this.btnEdit.setEnabled(false);
					this.btnRemove.setEnabled(false);
					this.btnCostAccountImport.setEnabled(false);
					this.btnAssign.setEnabled(false);
					this.btnDisAssign.setEnabled(false);

					this.menuItemAddNew.setEnabled(false);
					this.menuItemEdit.setEnabled(false);
					this.menuItemRemove.setEnabled(false);
					this.menuItemImport.setEnabled(false);
					this.menuItemAssign.setEnabled(false);
					this.menuItemDisAssign.setEnabled(false);
				}
				if(!isSelectCurOrg) {
					disabledWBT();
				}
				
			}

		} else {
			disabledWBT();
		}

	}

	// �ݹ����ѡ�нڵ㡣
	private void getSelectNode(DefaultKingdeeTreeNode selectNode) throws Exception {
		if (selectNode.getChildCount() == 0) {
			treeMain.setSelectionNode(selectNode);
		} else {
			for (int j = 0; j < selectNode.getChildCount(); j++) {
				DefaultKingdeeTreeNode tempNode = (DefaultKingdeeTreeNode) selectNode.getChildAt(j);
				if (tempNode.getUserObject() instanceof OrgStructureInfo) {
					getSelectNode(tempNode);
				} else {
					TreeBaseInfo tempTree = (TreeBaseInfo) tempNode.getUserObject();
					TreeBaseInfo node = (TreeBaseInfo) selectNode.getUserObject();
					if (StringUtility.isMatch(tempTree.getId().toString(), node.getId().toString(), true)) {
						treeMain.setSelectionNode((DefaultKingdeeTreeNode) tempNode);
						break;
					} else {
						getSelectNode(tempNode);
					}
				}
			}
		}
	}

	/**
	 * ����ÿһ�е�isEnabled��ֵ�ı䣬����btn��״̬Ҳ�ı�
	 * 
	 * @param isEnabled
	 *            boolean
	 */
	private void changeWBTEnabeld(boolean isEnabled) {
		this.actionCancel.setEnabled(isEnabled);// ���ð�ť
		this.actionCancelCancel.setEnabled(!isEnabled);// ���ð�ť
	}

	/**
	 * ������/��ֹ��ťdisabled
	 */
	private void disabledWBT() {
		this.actionCancel.setEnabled(false);// ���ð�ť
		this.actionCancelCancel.setEnabled(false);// ���ð�ť
	}

	/**
	 * ���ε�CU���˴���
	 */
	protected FilterInfo getDefaultFilterForTree() {
		return super.getDefaultFilterForTree();
	}

	/**
	 * Ĭ�Ͻ��е�ǰCU�Ĺ��ˡ���������ء�
	 */
	protected FilterInfo getDefaultFilterForQuery() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return null;
		}
		if (OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id", projectInfo.getId().toString(), CompareType.EQUALS));
			this.mainQuery.setFilter(filterInfo);
			tblMain.removeRows();// �����²�ѯ
		} else if (node.getUserObject() instanceof OrgStructureInfo) {

			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui == null || oui.getUnit() == null)
				return null;
			FullOrgUnitInfo info = oui.getUnit();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString(), CompareType.EQUALS));
			// filterInfo.setMaskString(" #0 ");
			this.mainQuery.setFilter(filterInfo);
			// execQuery();
			tblMain.removeRows();// �����²�ѯ

		}
		return this.mainQuery.getFilter();
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		//		if (OrgViewUtils.isTreeNodeDisable(node)) {
		//			return;
		//		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id", projectInfo.getId().toString(), CompareType.EQUALS));
			this.mainQuery.setFilter(filterInfo);
			tblMain.removeRows();// �����²�ѯ
			// �����ǰ����֯�Ǽ��ţ����ҵ�ǰѡ�еĽڵ��ǹ�����Ŀ,����������
			if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
					|| (SysContext.getSysContext().getCurrentCostUnit() != null && SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())) {
				// ���Ǽ���,������������ϸ�ڵ�
				if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
					this.btnAddNew.setEnabled(false);
					this.btnEdit.setEnabled(false);
					this.btnRemove.setEnabled(false);
					// this.btnCostAccountImport.setEnabled(false);
					// this.btnAssign.setEnabled(false);
					// this.btnDisAssign.setEnabled(false);
					this.menuItemAddNew.setEnabled(false);
					this.menuItemEdit.setEnabled(false);
					this.menuItemRemove.setEnabled(false);
				}
				if(node.isLeaf()&&!projectInfo.isIsDevPrj()){
					this.btnAddNew.setEnabled(true);
					this.btnEdit.setEnabled(true);
					this.btnRemove.setEnabled(true);
					this.menuItemAddNew.setEnabled(true);
					this.menuItemEdit.setEnabled(true);
					this.menuItemRemove.setEnabled(true);
				}
				//

			} else {// �Ǽ���
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.btnCostAccountImport.setEnabled(false);
				this.btnAssign.setEnabled(false);
				this.btnDisAssign.setEnabled(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
				
				this.menuItemImport.setEnabled(false);
			}
			//
			if (node.isLeaf()) {
				actionAssign.setEnabled(false);
				actionDisAssign.setEnabled(false);
			} else {
				actionAssign.setEnabled(true);
				actionDisAssign.setEnabled(true);
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {

			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui == null || oui.getUnit() == null)
				return;
			FullOrgUnitInfo info = oui.getUnit();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString(), CompareType.EQUALS));
			// filterInfo.setMaskString(" #0 ");
			this.mainQuery.setFilter(filterInfo);
			// execQuery();
			tblMain.removeRows();// �����²�ѯ
			// �����ǰ����֯�Ǽ��ţ����ҵ�ǰѡ�еĽڵ㲻�Ǽ��ţ���ô����������
			if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
					|| (SysContext.getSysContext().getCurrentCostUnit() != null && SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())) {
				// ���Ǽ���,������������ϸ�ڵ�
				if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
					this.btnAddNew.setEnabled(false);
					this.btnEdit.setEnabled(false);
					this.btnRemove.setEnabled(false);
					// this.btnCostAccountImport.setEnabled(false);
					// this.btnAssign.setEnabled(false);
					// this.btnDisAssign.setEnabled(false);
					this.menuItemAddNew.setEnabled(false);
					this.menuItemEdit.setEnabled(false);
					this.menuItemRemove.setEnabled(false);
				}

			} else {// ������,��������
				if (!OrgConstants.DEF_CU_ID.equals(info.getId().toString())) {
					this.btnAddNew.setEnabled(false);
					this.btnEdit.setEnabled(false);
					this.btnRemove.setEnabled(false);
					this.btnCostAccountImport.setEnabled(false);
					this.btnAssign.setEnabled(false);
					this.btnDisAssign.setEnabled(false);
					this.menuItemAddNew.setEnabled(false);
					this.menuItemEdit.setEnabled(false);
					this.menuItemRemove.setEnabled(false);
					
					this.menuItemImport.setEnabled(false);
					//					this.btnCancel.setVisible(false);
					//					this.menuItemCancel.setVisible(false);
					//					this.btnCancelCancel.setVisible(false);
					//					this.menuItemCancelCancel.setVisible(false);

				} else {
					this.btnAddNew.setEnabled(true);
					this.btnEdit.setEnabled(true);
					this.btnRemove.setEnabled(true);
					this.btnCostAccountImport.setEnabled(true);
					this.btnAssign.setEnabled(true);
					this.btnDisAssign.setEnabled(true);
					this.menuItemAddNew.setEnabled(true);
					this.menuItemEdit.setEnabled(true);
					this.menuItemRemove.setEnabled(true);
					
					this.menuItemImport.setEnabled(true);
					//					this.btnCancel.setVisible(true);
					//					this.btnCancelCancel.setVisible(true);
					//					this.menuItemCancel.setVisible(true);
					//					this.menuItemCancelCancel.setVisible(true);
				}
			}
		}
		if (node.isLeaf()) {
			this.btnAssign.setEnabled(false);
			this.btnDisAssign.setEnabled(false);
		} else {
			this.btnAssign.setEnabled(true);
			this.btnDisAssign.setEnabled(true);
		}
		
		//��ǰ��֯�Ǽ�����ѡ����Ǽ��Ž�㣬���Բ����Ƿ���гɱ����ݿ�
		if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())
				&&(node.getUserObject() instanceof OrgStructureInfo)
				&&node.getLevel()==0){
			actionEnterDB.setEnabled(true);
			actionEnterDB.setVisible(true);
			actionCancelEnterDB.setEnabled(true);
			actionCancelEnterDB.setVisible(true);
			tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(false);
		}else{
			actionEnterDB.setEnabled(false);
			actionEnterDB.setVisible(false);
			actionCancelEnterDB.setEnabled(false);
			actionCancelEnterDB.setVisible(false);
			tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(true);
		}
	}

	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// �ϼ���ȡ
		int rowNo = tblMain.getSelectManager().getActiveRowIndex();
		if (rowNo != -1) {// ѡ�����ϼ�
			IRow row = this.tblMain.getRow(rowNo);
			this.getUIContext().put("upId", row.getCell("id").getValue().toString());
		} else {
			// ���û��ѡ��
			// �ڵ��ȡ
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node == null) {
				MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccount_Add_SelectNode"));
				return;
			} else {
				if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
						|| (SysContext.getSysContext().getCurrentCostUnit() != null && SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())) {
					MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccount_Add_OnlyDetail"));
					return;
				}
			}
			if (node.getUserObject() != null) {
				if (node.getUserObject() instanceof CurProjectInfo) {
					CurProjectInfo info = (CurProjectInfo) node.getUserObject();
					this.getUIContext().put("source", info);
				} else if (node.getUserObject() instanceof OrgStructureInfo) {
					OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
					if (oui == null || oui.getUnit() == null)
						return;
					FullOrgUnitInfo info = oui.getUnit();
					this.getUIContext().put("source", info);
				}
			}
			this.getUIContext().put("upId", null);
		}
		//		�ж��Ƿ񱻲������
		if (this.getUIContext().get("upId") != null) {
			String id = this.getUIContext().get("upId").toString();
			if (ContractCostSplitEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'")
					|| SettlementCostSplitEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'")
					|| ConChangeSplitEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'")
					|| PaymentSplitEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'")) {
				if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccountSplit")))) {
					// ������
					return;
				}
			}
		}
		//�ж��Ƿ�Ŀ��ɱ�����
		if (this.getUIContext().get("upId") != null) {
			String id = this.getUIContext().get("upId").toString();
//			if (CostEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'")) {
//			if(true){
//				MsgBox.showWarning(this, "��Ŀ��Ŀ��ɱ����ã����������¼���");//����Ҫ��Ӵ�Լ��
//				SysUtil.abort();
//			}
		}
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * ��������׼��UI������ʱ����Ҫ���븸������
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
	}

	/**
	 * ��������׼��UI������ʱ����Ҫ���븸������
	 */
	protected void prepareUIContext1(UIContext uiContext, ActionEvent e) {
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// this.checkSelected();
		checkSelected();
		int selectRow=getMainTable().getSelectManager().getActiveRowIndex();
		super.actionEdit_actionPerformed(e);
		getMainTable().getSelectManager().select(selectRow, 0);
		getMainTable().scrollToVisible(selectRow, 0);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		super.actionView_actionPerformed(e);
	}

	protected void refresh(ActionEvent e) throws Exception {
		//		super.refresh(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CacheServiceFactory.getInstance().discardQuery(this.mainQueryPK);
		execQuery();
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		/**
		 * ���manQuery��������
		 */
		this.mainQuery = new EntityViewInfo();
		mainQuery.setFilter(getDefaultFilterForQuery());
		super.actionQuery_actionPerformed(e);
	}

	protected String getQueryFieldName() {
		// TODO �Զ����ɷ������
		return null;
	}

	protected String getGroupEditUIName() {
		// TODO �Զ����ɷ������
		return null;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		// TODO �Զ����ɷ������
		return null;
	}

	protected String getEditUIName() {
		return CostAccountEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CostAccountFactory.getRemoteInstance();
	}

	private class CostAccountTreeRender implements IBasicRender {

		private int TABSIZE = 8;

		private int ICONSIZE = 10;

		private int margin = 2;

		protected SimpleTextRender simpleRender = new SimpleTextRender();

		protected HashMap costAccountIdToPos = new HashMap();

		protected void drawExpanded(Graphics g, int x, int y) {
			g.drawRect(x, y, ICONSIZE, ICONSIZE);
			int lineSize = ICONSIZE - 2 * margin;
			g.drawLine(x + margin, y + ICONSIZE / 2, x + margin + lineSize, y + ICONSIZE / 2);
		}

		protected void drawCollapsed(Graphics g, int x, int y) {
			g.drawRect(x, y, ICONSIZE, ICONSIZE);
			int lineSize = ICONSIZE - 2 * margin;
			g.drawLine(x + margin, y + ICONSIZE / 2, x + margin + lineSize, y + ICONSIZE / 2);
			g.drawLine(x + ICONSIZE / 2, y + margin, x + ICONSIZE / 2, y + margin + lineSize);
		}

		protected void drawLeaf(Graphics g, int x, int y) {
			int lineSize = ICONSIZE - 2 * margin;

		}

		public boolean inRect(NumberExpandInfo numberExpandInfo, int x, int y) {
			String costAccountId = numberExpandInfo.getcostAccountId();
			java.awt.Rectangle rec = (java.awt.Rectangle) costAccountIdToPos.get(costAccountId);
			if (rec != null) {
				return rec.getX() < x && (rec.getX() + rec.getWidth()) > x;
			}
			return false;
		}

		public void draw(Graphics graphics, Shape clip, Object obj, com.kingdee.bos.ctrl.kdf.util.style.Style style) {
			if (obj instanceof NumberExpandInfo) {
				NumberExpandInfo numberExpandInfo = (NumberExpandInfo) obj;
				int ident = numberExpandInfo.getLevel() * TABSIZE;
				java.awt.Rectangle rect = clip.getBounds();
				int x = rect.x + ident;
				int y = rect.y + (rect.height - ICONSIZE) / 2;
				Rectangle iconRect = new Rectangle(x, y, ICONSIZE, ICONSIZE);
				costAccountIdToPos.put(numberExpandInfo.getcostAccountId(), iconRect);

				simpleRender.draw(graphics, (Shape) new Rectangle(x + ICONSIZE + TABSIZE, rect.y, rect.width - x - ICONSIZE - TABSIZE, rect.height), numberExpandInfo.getNumber(), style);
				paintIcon(graphics, numberExpandInfo, iconRect);
			} else if (obj != null) {
				simpleRender.draw(graphics, clip, obj.toString(), style);
			}
		}

		private void paintIcon(Graphics graphics, NumberExpandInfo numberExpandInfo, java.awt.Rectangle iconRect) {
			if (numberExpandInfo.isLeaf()) {
				drawLeaf(graphics, iconRect.x, iconRect.y);
			} else if (numberExpandInfo.isExpandStatus()) {
				drawExpanded(graphics, iconRect.x, iconRect.y);
			} else if (!numberExpandInfo.isExpandStatus()) {
				drawCollapsed(graphics, iconRect.x, iconRect.y);
			}
		}
	}

	private class NumberExpandInfo {
		private String costAccountId;

		private String number;

		private boolean isExpandStatus;

		// private String signNumber;
		private int level;

		private boolean isLeaf;

		/**
		 * @return ���� isExpandStatus��
		 */
		public boolean isExpandStatus() {
			return isExpandStatus;
		}

		/**
		 * @param isExpandStatus
		 *            Ҫ���õ� isExpandStatus��
		 */
		public void setExpandStatus(boolean isExpandStatus) {
			this.isExpandStatus = isExpandStatus;
		}

		/**
		 * @return ���� number��
		 */
		public String getNumber() {
			return number;
		}

		/**
		 * @param number
		 *            Ҫ���õ� number��
		 */
		public void setNumber(String number) {
			this.number = number;
		}

		public String toString() {
			return number;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public boolean isLeaf() {
			return isLeaf;
		}

		public void setLeaf(boolean isLeaf) {
			this.isLeaf = isLeaf;
		}

		public String getcostAccountId() {
			return costAccountId;
		}

		public void setcostAccountId(String costAccountId) {
			this.costAccountId = costAccountId;
		}
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		return null;
	}

	// �Ƿ���Ҫ���б��������Ϊʵ���˷ּ���ʾ�����Բ�������
	protected boolean isOrderForClickTableHead() {
		return false;
	}

	/**
	 * 
	 * ���������ǳ�����Ϊ���Ҽ��˵�û��������ɾ��
	 */
	protected void initPopmenu() {
		/* ������ʾ�Ҽ��˵��Ĵ��� */
		// 2004-9-10 by Jerry
		final JPopupMenu menu = treeMain.getPopupMenu();
		Object[] ls = treeMain.getListeners(TreePopupMenuListener.class);

		if (ls == null || ls.length == 0) {
			treeMain.addTreePopupMenu(new TreePopupMenuListener() {
				public boolean popMenu(TreePopupMenuEvent event) {
					return true;
				}
			});
			menu.add(new KDSeparator());
			JMenuItem itemEdit = new JMenuItem(actionGroupEdit);
			itemEdit.setIcon(EASResource.getIcon("imgTbtn_edit"));
			menu.add(itemEdit);
		}
	}

	/**
	 * 
	 * �����������е���ʾ��ʽ����tblMain_tableClicked����
	 * 
	 */
	private void setTreeDisplayStyle(IRow row) {

		boolean isCostAccountLeaf = ((Boolean) row.getCell("isLeaf").getValue()).booleanValue();
		if (isCostAccountLeaf) {
			return;
		} else {
			expandCostAccount(row);
		}
	}

	/**
	 * 
	 * ���������ڷ�Ҷ�ӽ��ʱ����ʾ�¼� 1.���ñ�����Ϣ 2.�����¼���Ϣ
	 */
	private void expandCostAccount(IRow row) {
		NumberExpandInfo costAccountNumberExpandInfo = (NumberExpandInfo) row.getCell("number").getValue();
		String costAccountNumber = costAccountNumberExpandInfo.getNumber();
		boolean isExpandStatus = costAccountNumberExpandInfo.isExpandStatus();
		String costAccountId = (String) row.getCell("id").getValue();
		if (isExpandStatus) {
			costAccountNumberExpandInfo.setExpandStatus(false);

			int i = row.getRowIndex() + 1;
			tblMain.setRefresh(false);
			IRow child;
			for (int count = tblMain.getRowCount(); i < count; i++) {
				child = tblMain.getRow(i);
				if (child.getCell("number").getValue() instanceof NumberExpandInfo) {
					NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo) child.getCell("number").getValue();
					String childNumber = childNumberExpandInfo.getNumber();
					if (childNumber.startsWith(costAccountNumber)) {
						child.getStyleAttributes().setHided(true);
					} else {
						break;
					}
				}
			}
			tblMain.setRefresh(true);
			tblMain.reLayoutAndPaint();
		} else {
			costAccountNumberExpandInfo.setExpandStatus(true);
			int i = row.getRowIndex() + 1;
			boolean hasChildrenDataGotten = false;
			String curProjectId, fullOrgUnitId;

			if (i < tblMain.getRowCount()) {
				IRow next = tblMain.getRow(i);
				if (next.getCell("number").getValue() instanceof NumberExpandInfo) {
					NumberExpandInfo nextNumberExpandInfo = (NumberExpandInfo) next.getCell("number").getValue();
					if (nextNumberExpandInfo.getNumber().startsWith(costAccountNumber)) {
						hasChildrenDataGotten = true;
					}
				}
			} else {
				hasChildrenDataGotten = true;
			}
			if (!hasChildrenDataGotten) {
				this.setCursorOfWair();
				EntityViewInfo childQuery = new EntityViewInfo();
				FilterInfo childFilter = new FilterInfo();
				childQuery.setFilter(childFilter);
				childFilter.getFilterItems().add(new FilterItemInfo("parent.id", costAccountId));
				if (row.getCell("curProject.id").getValue() != null) {
					curProjectId = row.getCell("curProject.id").getValue().toString();
					childFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectId));
				} else {
					fullOrgUnitId = row.getCell("fullOrgUnit.id").getValue().toString();
					childFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", fullOrgUnitId));
				}
				// childFilter.setMaskString("#0" AND "#1");
				IRowSet rowset = null;
				try {
					rowset = getQueryExecutor(this.mainQueryPK, childQuery).executeQuery();
					if (rowset != null && rowset.size() > 0) {
						IRow child;
						int start = row.getRowIndex() + 1;
						while (rowset.next()) {
							child = tblMain.addRow(start);
							start++;
							for (int col = 0, colcount = tblMain.getColumnCount(); col < colcount; col++) {
								IColumn column = tblMain.getColumn(col);
								String field = column.getFieldName();
								if (field == null || field.length() < 1) {
									continue;
								}
								Object value = rowset.getObject(field);
								if (field.equals("number")) {
									NumberExpandInfo childNumberExpandInfo = new NumberExpandInfo();
									childNumberExpandInfo.setcostAccountId(rowset.getString("id"));
									childNumberExpandInfo.setLevel(rowset.getInt("level"));
									childNumberExpandInfo.setLeaf(rowset.getBoolean("isLeaf"));
									childNumberExpandInfo.setNumber(rowset.getString("number"));
									childNumberExpandInfo.setExpandStatus(false);
									value = childNumberExpandInfo;
								}
								child.getCell(col).setValue(value);
							}
						}
						tblMain.setRowCount(tblMain.getRowCount() + rowset.size());
					}
				} catch (BOSException e) {
					handleException(e);
				} catch (SQLException e) {
					handleException(e);
				} finally {
					this.setCursorOfDefault();
				}
			}

			IRow child;
			for (int count = tblMain.getRowCount(); i < count; i++) {
				child = tblMain.getRow(i);
				if (child.getCell("number").getValue() instanceof NumberExpandInfo) {
					NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo) child.getCell("number").getValue();
					String childNumber = childNumberExpandInfo.getNumber();

					if (childNumber.startsWith(costAccountNumber)) {
						String parentId = (String) child.getCell("parent.id").getValue();
						if (parentId != null && parentId.equals(costAccountId)) {
							// ���¼���㻹���¼�ʱ���ּ���ʾ�¼�
							expandChildCostAccount(child);
						} else {
							continue;
						}
					} else {
						break;
					}
				}
			}

		}
	}

	/**
	 * 
	 * �������ݹ鷽���������¼���㻹���¼�ʱ���ּ���ʾ
	 * 
	 */
	private void expandChildCostAccount(IRow row) {
		NumberExpandInfo costAccountNumberExpandInfo = (NumberExpandInfo) row.getCell("number").getValue();
		String costAccountNumber = costAccountNumberExpandInfo.getNumber();
		boolean isExpandStatus = costAccountNumberExpandInfo.isExpandStatus();
		String costAccountId = (String) row.getCell("id").getValue();
		row.getStyleAttributes().setHided(false);
		if (isExpandStatus) {
			int i = row.getRowIndex() + 1;
			IRow child;
			for (int count = tblMain.getRowCount(); i < count; i++) {
				child = tblMain.getRow(i);
				NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo) child.getCell("number").getValue();
				String childNumber = childNumberExpandInfo.getNumber();

				if (childNumber.startsWith(costAccountNumber)) {
					String parentId = (String) child.getCell("parent.id").getValue();
					if (parentId != null && parentId.equals(costAccountId)) {
						expandChildCostAccount(child);
					} else {
						continue;
					}
				} else {
					break;
				}
			}
		}
	}

	/**
	 * 
	 * ���������tblMain�ļ�����
	 * 
	 */
	protected void initListener() {
		super.initListener();
		tblMain.addKDTDataFillListener(new KDTDataFillListener() {
			public void afterDataFill(KDTDataRequestEvent e) {
				int start = e.getFirstRow();
				int end = e.getLastRow();
				setTreeDisplayStyle(start, end + 1);
			}
		});
	}

	/**
	 * 
	 * ������tblMain��ʾ��״��ʽ
	 * 
	 */
	private void setTreeDisplayStyle(int start, int end) {
		IRow row;
		for (int i = start, count = end; i < count; i++) {
			row = tblMain.getRow(i);
			String id = (String) row.getCell("id").getValue();
			int level = ((Integer) row.getCell("level").getValue()).intValue();
			Object numberValue = row.getCell("number").getValue();
			if (!(numberValue instanceof String)) {
				return;
			}
			String costAccountNumber = (String) numberValue;
			boolean isCostAccountLeaf = ((Boolean) row.getCell("isLeaf").getValue()).booleanValue();
			NumberExpandInfo costAccountNumberExpandInfo = new NumberExpandInfo();
			costAccountNumberExpandInfo.setcostAccountId(id);
			costAccountNumberExpandInfo.setLevel(level);
			costAccountNumberExpandInfo.setLeaf(isCostAccountLeaf);
			costAccountNumberExpandInfo.setNumber(costAccountNumber);
			costAccountNumberExpandInfo.setExpandStatus(true);
			row.getCell("number").setValue(costAccountNumberExpandInfo);
			if (!isSetTreeDispalyStyle) {
				if (level == 1) {
					row.getStyleAttributes().setHided(false);
					costAccountNumberExpandInfo.setExpandStatus(false);
				} else {
					row.getStyleAttributes().setHided(true);
				}
			} else
				costAccountNumberExpandInfo.setExpandStatus(true);
		}
	}

	/**
	 * ����
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		// super.actionCancelCancel_actionPerformed(e);

		checkSelected();
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(activeRowIndex);
		// if (row == null)
		// return;
		String id = row.getCell("id").getValue().toString().trim();
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		
		CostAccountInfo costAccountInfo = iCostAccount.getCostAccountInfo(pk);
		if(costAccountInfo.getSrcCostAccountId() != null && !costAccountInfo.isIsEnabled()) {
			MsgBox.showWarning(this, "�ϼ������������ѽ��õĿ�Ŀ���¼���������");
			SysUtil.abort();
		}
		if (iCostAccount.enable(pk)) {
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			showMessage();
			//�����в�ѯ,ֱ���ڽ�����и��¼���
			//tblMain.getRow(activeRowIndex).getCell("isEnabled").setValue(Boolean.TRUE);
			//tblMain_tableSelectChanged(null);
			tblMain.removeRows();// �����²�ѯ
//			this.btnCancel.setEnabled(false);
//			this.menuItemCancel.setEnabled(false);
//			this.btnCancelCancel.setEnabled(true);
//			this.menuItemCancelCancel.setEnabled(true);
			
			tblMain.getSelectManager().select(activeRowIndex, 0);
			tblMain.scrollToVisible(activeRowIndex, 0);
		}
	}

	/**
	 * ����
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		// super.actionCancel_actionPerformed(e);
		checkSelected();
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(activeRowIndex);
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		if (iCostAccount.disable(pk)) {
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
			showMessage();
			//�����в�ѯ,ֱ���ڽ�����и��¼���
			//tblMain.getRow(activeRowIndex).getCell("isEnabled").setValue(Boolean.FALSE);
			//tblMain_tableSelectChanged(null);
			
			tblMain.removeRows();// �����²�ѯ
//			
//			this.btnCancelCancel.setEnabled(false);
//			this.menuItemCancelCancel.setEnabled(false);
//			this.btnCancel.setEnabled(true);
//			this.menuItemCancel.setEnabled(true);
			tblMain.getSelectManager().select(activeRowIndex, 0);
			tblMain.scrollToVisible(activeRowIndex, 0);
		}
	}

	/**
	 * ����
	 */
	public void actionAssign_actionPerformed(ActionEvent e) throws Exception {
//		UIContext uiContext = new UIContext(this);
//		IUIWindow assignUI = UIFactory.createUIFactory().create(CostAccountAssignUI.class.getName(), uiContext);
//		assignUI.show();
		
		if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccountAssign_Note")))) {
			// ��ʾδ���õĳɱ���Ŀ�����������ȥ
			return;
		}
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {// Ӧ����ʾ"��ѡ��������Ľڵ�"
			MsgBox.showWarning(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "CostAccount_Assign_Selected"));
			//			
			return;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectInfo.getId().toString()));
//			filter.getFilterItems().add(new FilterItemInfo("assigned", Boolean.TRUE));
//			boolean ass = CostAccountFactory.getRemoteInstance().exists(filter);
//			if(ass) {
				if (!MsgBox.isYes(MsgBox.showConfirm2(this, "Ҫ����Ŀ�Ŀ���ϼ���Ŀ����Ѿ������ݣ����޷����䣬�����ᵼ�¸ÿ�Ŀ�ϼ�����������ȷ���Ƿ������"))) {
					// ��ʾδ���õĳɱ���Ŀ�����������ȥ
					return;
				}
//			}
			
			ICostAccountFacade iCostAccountFacade = CostAccountFacadeFactory.getRemoteInstance();
			List errorList = iCostAccountFacade.assignProjsCostAccount(new ObjectStringPK(projectInfo.getId().toString()));
			
			showErrorInfo(errorList);
			tblMain.removeRows();// �����²�ѯ
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui == null || oui.getUnit() == null)
				return;
			FullOrgUnitInfo info = oui.getUnit();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("assigned", Boolean.TRUE));
			boolean ass = CostAccountFactory.getRemoteInstance().exists(filter);
			if(ass) {
				if (!MsgBox.isYes(MsgBox.showConfirm2(this, "Ҫ����Ŀ�Ŀ���ϼ���Ŀ����Ѿ������ݣ����޷�����(�������ݵ��޷�����)�������ᵼ�¸ÿ�Ŀ�ϼ�����������ȷ���Ƿ������"))) {
					// ��ʾδ���õĳɱ���Ŀ�����������ȥ
					return;
				}
			}
			// ��֮֯��ķ���
			if ((node.getChildCount() > 0) && (((DefaultKingdeeTreeNode) node.getChildAt(0)).getUserObject() instanceof OrgStructureInfo)) {
				ICostAccountFacade iCostAccountFacade = CostAccountFacadeFactory.getRemoteInstance();
				iCostAccountFacade.assignOrgsCostAccount(new ObjectStringPK(info.getId().toString()));
				tblMain.removeRows();// �����²�ѯ
			} else {
				// ��ѡ����Ϊ��֯�빤����Ŀ�ٽ��,��Ϊ��֯�빤����Ŀ֮��ķ���
				ICostAccountFacade iCostAccountFacade = CostAccountFacadeFactory.getRemoteInstance();
				List errorlist = iCostAccountFacade.assignOrgProject(new ObjectStringPK(info.getId().toString()));
				showErrorInfo(errorlist);
				tblMain.removeRows();// �����²�ѯ
			}
		}
	}

	private void showErrorInfo(List errorlist) {
		if(errorlist == null || errorlist.size() == 0) return;
		StringBuffer errorStr = new StringBuffer();
		String sep = "\n";
		for (Iterator iter = errorlist.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			errorStr.append(element);
			errorStr.append(sep);
		}
		String title = "���ֿ�Ŀ�������ϼ��Ѿ������ݣ�δ����ɹ��������Ѿ�����ɹ���δ����ɹ��Ŀ�Ŀ��鿴��ϸ��Ϣ";
		
		String error = errorStr.toString();
		error = error.replace('!', '.');
		MsgBox.showDetailAndOK(this, title, error, 1);
	}

	// /**
	// * ������
	// */
	// public void actionDisAssign_actionPerformed(ActionEvent e) throws
	// Exception
	// {
	// DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)
	// treeMain.getLastSelectedPathComponent();
	// if (node == null) {//Ӧ����ʾ"��ѡ����������Ľڵ�"
	// return;
	// }
	// if (node.getUserObject() instanceof CurProjectInfo) {
	// CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
	// ICostAccountFacade iCostAccountFacade =
	// CostAccountFacadeFactory.getRemoteInstance();
	// iCostAccountFacade.disAssignProjsCostAccount(new
	// ObjectStringPK(projectInfo.getId().toString()));
	// tblMain.removeRows();// �����²�ѯ
	// } else if (node.getUserObject() instanceof OrgStructureInfo) {
	// //��֮֯��ķ�����
	// if
	// ((node.getChildCount()>0)&&(((DefaultKingdeeTreeNode)node.getChildAt(0)).getUserObject()
	// instanceof OrgStructureInfo)) {
	// OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
	// if (oui == null || oui.getUnit() == null)
	// return;
	// FullOrgUnitInfo info = oui.getUnit();
	// ICostAccountFacade iCostAccountFacade =
	// CostAccountFacadeFactory.getRemoteInstance();
	// iCostAccountFacade.disAssignOrgsCostAccount(new
	// ObjectStringPK(info.getId().toString()));
	// tblMain.removeRows();// �����²�ѯ
	// }else{
	// //��ѡ����Ϊ��֯�빤����Ŀ�ٽ��,��Ϊ��֯�빤����Ŀ֮��ķ�����
	// OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
	// if (oui == null || oui.getUnit() == null)
	// return;
	// FullOrgUnitInfo info = oui.getUnit();
	// ICostAccountFacade iCostAccountFacade =
	// CostAccountFacadeFactory.getRemoteInstance();
	// iCostAccountFacade.disAssignOrgProject(new
	// ObjectStringPK(info.getId().toString()));
	// tblMain.removeRows();// �����²�ѯ
	// }
	// }
	// }
	/**
	 * ������
	 */
	public void actionDisAssign_actionPerformed(ActionEvent e) throws Exception {
//		MsgBox.showWarning(this, "");
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			// ����ָ����
			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccount_DisAssign_CheckSelected"));
			return;
		}
		CostAccountCollection cacSelect = new CostAccountCollection();
		CostAccountInfo caiSelect;
		KDTSelectBlock selectBlock = null;
		ICell cell = null;
		FullOrgUnitInfo foui;
		CurProjectInfo cpi;
		for (int i = 0; i < tblMain.getSelectManager().size(); i++) {
			caiSelect = new CostAccountInfo();
			selectBlock = tblMain.getSelectManager().get(i);
			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
				IRow row = this.tblMain.getRow(j);
				// this.verifyNotAccepted(row);//��֤�Ƿ�Ϊ�ѷ�����
				cell = row.getCell(getKeyFieldName());
				if (cell == null) {
					MsgBox.showError(EASResource.getString(FrameWorkClientUtils.strResource + "Error_KeyField_Fail"));
					SysUtil.abort();
				}
				Object keyValue = cell.getValue();

				BOSUuid id = BOSUuid.read((String) keyValue);
				caiSelect.setId(id);
				caiSelect.setLongNumber(row.getCell("longNumber").getValue().toString());
				if (row.getCell("fullOrgUnit.id").getValue() != null) {
					foui = new FullOrgUnitInfo();
					foui.setId(BOSUuid.read(row.getCell("fullOrgUnit.id").getValue().toString()));
					foui.setLongNumber(row.getCell("fullOrgUnit.longNumber").getValue().toString());
					caiSelect.setFullOrgUnit(foui);
				} else if (row.getCell("curProject.id").getValue() != null) {
					cpi = new CurProjectInfo();
					cpi.setId(BOSUuid.read(row.getCell("curProject.id").getValue().toString()));
					cpi.setLongNumber(row.getCell("curProject.longNumber").getValue().toString());
					caiSelect.setCurProject(cpi);
				}
				if (((Boolean) (row.getCell("isSource").getValue())).booleanValue()) {
					caiSelect.setIsSource(true);
				} else {
					caiSelect.setIsSource(false);
					if (row.getCell("srcCostAccountId").getValue() != null) {
						caiSelect.setSrcCostAccountId(row.getCell("srcCostAccountId").getValue().toString());
					}
				}
				caiSelect.setName(row.getCell("name").getValue().toString(), SysContext.getSysContext().getLocale());
				cacSelect.add(caiSelect);
			}
		}

		ICostAccountFacade iCostAccountFacade = CostAccountFacadeFactory.getRemoteInstance();

		ArrayList al = iCostAccountFacade.disAssignSelectOrgProject(cacSelect);
		if (al.size() != 0) {
			ArrayList alOrgAndProject = new ArrayList();
			for (int i = 0; i < al.size(); i++) {
				alOrgAndProject.addAll(i, (ArrayList) al.get(i));
			}
			MsgBox.showDetailAndOK(this, "��ϸ��Ϣ�г����޷����������ϸ���!", alOrgAndProject.toString(), 1);

		}
		actionRefresh_actionPerformed(e);
	}

	/**
	 * ģ�嵼��
	 */
	public void actionTemplateImport_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		boolean checkGroupNodeSelected = true;
		if (node != null) {
			// ��ʾѡ�нڵ�
			if (node.getUserObject() != null) {
				if (node.getUserObject() instanceof CurProjectInfo) {
					checkGroupNodeSelected = false;
				} else if (node.getUserObject() instanceof OrgStructureInfo) {
					OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
					if (oui == null || oui.getUnit() == null) {
						checkGroupNodeSelected = false;
					} else {
						FullOrgUnitInfo info = oui.getUnit();
						if (!OrgConstants.DEF_CU_ID.equals(info.getId().toString())) {
							checkGroupNodeSelected = false;
						}
					}
				}
			} else {
				checkGroupNodeSelected = false;
			}
		} else {
			checkGroupNodeSelected = false;
		}
		if (!checkGroupNodeSelected) {
			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccount_TemplateImport_CheckNodeSelected"));
			return;
		}
		HashMap map = new HashMap();
		map.put("Owner", this); // ���롣������UI�ĸ�UI����
		UIContext uiContext = new UIContext(this);
		// �����ඨ��Ҫ���ݵ�EditUI����չ������

		prepareUIContext(uiContext, e);
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory"); // ��ģ̬�Ի���ʽ����
		IUIWindow uiWindow = uiFactory.create("com.kingdee.eas.fdc.basedata.client.CostAccountTemplateDataImportUI", /* ����������������� */
		uiContext, null, OprtState.EDIT);
		uiWindow.show();
		actionRefresh_actionPerformed(e);

	}

	/**
	 * �ɱ���Ŀ����
	 */
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			// ��ʾѡ�нڵ�
			// MsgBox.showWarning("��ָ����Ҫ����Ľڵ�!");//ProjectImport_CheckNodeSelected
			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "ProjectImport_CheckNodeSelected"));
			return;
		}
		// if (OrgViewUtils.isTreeNodeDisable(node)) {
		// return;
		// }
		HashMap map = new HashMap();
		map.put("Owner", this); // ���롣������UI�ĸ�UI����
		UIContext uiContext = new UIContext(this);
		// �����ඨ��Ҫ���ݵ�EditUI����չ������
		if (node.getUserObject() != null) {
			if (node.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo info = (CurProjectInfo) node.getUserObject();
				uiContext.put("address", info);
				uiContext.put("isOrgFilter", Boolean.valueOf(true));
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				// if
				// ((node.getChildCount()>0)&&(((DefaultKingdeeTreeNode)node.getChildAt(0)).getUserObject()
				// instanceof OrgStructureInfo)) {// ѡ���㲻�ʺ�����,�ҵ�������ť
				// return;
				// }
				// ����涨�������������,��ʾֻ�ܶԹ�����Ŀ������������
				throw new FDCBasedataException(FDCBasedataException.IMPORT_ONLYTOPROJECT);
				// OrgStructureInfo oui = (OrgStructureInfo)
				// node.getUserObject();
				// if (oui == null || oui.getUnit() == null)
				// return;
				// FullOrgUnitInfo info = oui.getUnit();
				// uiContext.put("address", info);
			}
		} else {
			return;
		}
		prepareUIContext(uiContext, e);
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIModelDialogFactory.class.getName()); // ��ģ̬�Ի���ʽ����
		IUIWindow uiWindow = uiFactory.create(CostAccountImportUI.class.getName(), uiContext, null, OprtState.EDIT);
		uiWindow.show();
		actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionProjectAttachment_actionPerformed method
	 */
	public void actionProjectAttachment_actionPerformed(ActionEvent e) throws Exception {
		// this.checkSelected();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CurProjectInfo cpi = null;
		if (node == null) {
			return;
		}
		if (OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			cpi = (CurProjectInfo) node.getUserObject();
		} else {
			//			 ����ָ����
			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Project_CheckSelected"));
			// MsgBox.showWarning("��ָ��������Ŀ!");//Project_CheckSelected
			return;
		}
		//		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
		//			
		//		}
		super.actionProjectAttachment_actionPerformed(e);
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		String contractId = this.getSelectedKeyValue();
		// ContractBillInfo contract = ContractBillFactory
		// .getRemoteInstance()
		// .getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
		//		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());

		acm.showAttachmentListUIByBoID(cpi.getId().toString(), this);
	}
    protected String getEditUIModal()
    {
        // return UIFactoryName.MODEL;
        // return UIFactoryName.NEWWIN;
        // 2006-4-29 ����Ҫ������������������ȡUI�򿪷�ʽ��
        String openModel = UIConfigUtility.getOpenModel();
        if (openModel != null)
        {
            return openModel;
        }
        else
        {
            return UIFactoryName.MODEL;
        }
    }
    
    protected ArrayList getImportParam()
    {
    	DatataskParameter param = new DatataskParameter();
		String solutionName = "eas.fdc.fdcbasedata.fdccostaccount";
		param.solutionName = solutionName;
		param.alias = "���ز��ɱ���Ŀ";
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;
    }
    public void actionEnterDB_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	
    	int index = getMainTable().getSelectManager().getActiveRowIndex();
    	CostAccountInfo acct=new CostAccountInfo();
    	Object obj = getMainTable().getCell(index, "isEnterDB").getValue();
    	Object objID = getMainTable().getCell(index, "id").getValue();
    	Object longNumber = getMainTable().getCell(index, "longNumber").getValue();
    	if(obj==null||obj.equals(Boolean.TRUE)||objID==null||longNumber==null){
    		return;
    	}
    	
    	acct.setId(BOSUuid.read(objID.toString()));
    	acct.setLongNumber(longNumber.toString().trim().replaceAll("\\.", "!"));
    	acct.setIsEnabled(false);
    	CostAccountFacadeFactory.getRemoteInstance().handleEnterDB(acct, true);
    	this.refreshList();
    }
    public void actionCancelEnterDB_actionPerformed(ActionEvent e)
    		throws Exception {
    	checkSelected();
    	int index = getMainTable().getSelectManager().getActiveRowIndex();
    	CostAccountInfo acct=new CostAccountInfo();
    	Object obj = getMainTable().getCell(index, "isEnterDB").getValue();
    	Object objID = getMainTable().getCell(index, "id").getValue();
    	Object longNumber = getMainTable().getCell(index, "longNumber").getValue();
    	if(obj==null||obj.equals(Boolean.FALSE)||objID==null||longNumber==null){
    		return;
    	}
    	
    	acct.setId(BOSUuid.read(objID.toString()));
    	acct.setLongNumber(longNumber.toString().trim().replaceAll("\\.", "!"));
    	acct.setIsEnterDB(true);
    	CostAccountFacadeFactory.getRemoteInstance().handleEnterDB(acct, false);
    	this.refreshList();
    }

	public void actionAssignToOrg_actionPerformed(ActionEvent e) throws Exception {
		//��ʾδ���õĳɱ���Ŀ�����������ȥ
		if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccountAssign_Note")))) {
			return;
		}
		
		if (!MsgBox.isYes(MsgBox.showConfirm2(this, "Ҫ����Ŀ�Ŀ���ϼ���Ŀ����Ѿ������ݣ����޷�����(�������ݵ��޷�����)�������ᵼ�¸ÿ�Ŀ�ϼ�����������ȷ���Ƿ������"))) {
			return;
		}
	
		UIContext uiContext = new UIContext(this);
		IUIWindow assignUI = UIFactory.createUIFactory().create(CostAccountAssignUI.class.getName(), uiContext);
		assignUI.show();
		
		tblMain.removeRows();// �����²�ѯ
	}    
}
