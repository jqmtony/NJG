/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
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
import com.kingdee.eas.base.param.ParamControlFactory;
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
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.IIncomeAccount;
import com.kingdee.eas.fdc.basedata.IIncomeAccountFacade;
import com.kingdee.eas.fdc.basedata.IncomeAccountCollection;
import com.kingdee.eas.fdc.basedata.IncomeAccountFacadeFactory;
import com.kingdee.eas.fdc.basedata.IncomeAccountFactory;
import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class IncomeAccountListUI extends AbstractIncomeAccountListUI
{
    private static final Logger logger = CoreUIObject.getLogger(IncomeAccountListUI.class);
    
    private IncomeAccountTreeRender treeRender;

	private boolean isSetTreeDispalyStyle = true;
    
    /**
     * output class constructor
     */
    public IncomeAccountListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	if(!isIncomeJoinCost()){
			FDCMsgBox.showError(this, "“成本测算与收入测算是否联用”参数未启用，该功能不可使用！");
			SysUtil.abort();
		}
    	
		super.onLoad();
		FDCTableHelper.setColumnMoveable(tblMain, true);
		this.chkIncludeChild.setSelected(true);
		this.chkIncludeChild.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				isSetTreeDispalyStyle = chkIncludeChild.isSelected();
				tblMain.removeRows();
			}
		});
		treeRender = new IncomeAccountTreeRender();
		tblMain.getColumn("number").setRenderer(treeRender);

		buildProjectTree();
		//按钮
		this.btnAssign.setIcon(EASResource.getIcon("imgTbtn_distribute"));// 分配按钮		
		this.btnDisAssign.setIcon(EASResource.getIcon("imgTbtn_undistribute"));// 反分配按钮
		this.btnAssignToOrg.setIcon(EASResource.getIcon("imgTbtn_distributebatch"));//逐级分配
		this.btnIncomeAccountImport.setIcon(EASResource.getIcon("imgTbtn_citetree"));//引入按钮
		this.btnTemplateImport.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));//引入按钮
		//菜单
		this.menuItemAssign.setIcon(EASResource.getIcon("imgTbtn_distribute"));
		this.menuItemDisAssign.setIcon(EASResource.getIcon("imgTbtn_undistribute"));	
		this.menuItemAssignToOrg.setIcon(EASResource.getIcon("imgTbtn_distributebatch"));//逐级分配
		this.menuItemImport.setIcon(EASResource.getIcon("imgTbtn_citetree"));
		this.menuItemTemplateImport.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));
		
		actionEnterDB.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_staruse"));
		actionCancelEnterDB.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_forbid"));
		
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
			this.menuItemTemplateImport.setVisible(true);
			this.menuItemTemplateImport.setEnabled(true);

			this.btnTemplateImport.setVisible(true);
			this.btnTemplateImport.setEnabled(true);
			
			actionEnterDB.setEnabled(true);
			actionCancelEnterDB.setEnabled(true);
		} else {
			this.menuItemTemplateImport.setVisible(true);
			this.menuItemTemplateImport.setEnabled(false);
			this.btnTemplateImport.setVisible(true);
			this.btnTemplateImport.setEnabled(false);
			actionEnterDB.setVisible(false);
			actionEnterDB.setEnabled(false);
			actionCancelEnterDB.setVisible(false);
			actionCancelEnterDB.setEnabled(false);
			//tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(true);
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
		//tblMain.getColumn("isIncomeAccount").setWidth(50);
//		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
//		this.tblMain.getSelectManager().setSelectMode(2);// 行选
		
		actionQuery.setEnabled(false);
		actionQuery.setVisible(false);
		
		this.actionImport.setEnabled(false);//引入
		this.actionImport.setVisible(false);
		
		this.actionGroupAddNew.setEnabled(false);
		this.actionGroupAddNew.setVisible(false);
		
		//集团的科目导入功能		
		if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())){
			actionImportData.setVisible(false);
			actionImportData.setEnabled(false);
			
			this.actionAddNew.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionEdit.setEnabled(true);			
		}else{
			actionImportData.setVisible(false);
			actionImportData.setEnabled(false);			
			
			this.actionAddNew.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionEdit.setEnabled(false);			
		}
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		//仅允许财务组织的逐级分配
		if(SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()){
			actionAssignToOrg.setEnabled(true);
		}else{
			actionAssignToOrg.setEnabled(false);
		}
		//反分配在客户现场出现了不可修复的数据错误且对性能有较大的影响,暂时不提供此功能
		actionDisAssign.setEnabled(false);
		actionDisAssign.setVisible(false);
		//设置可以保存当前样式
		tHelper = new TablePreferencesHelper(this);
	}
    
    /**
	 * 初始化时构造Tree，几乎不用重载
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
			// 选中第二列(number)时
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
				// 随着每一行的isEnabled的值改变，两个WBT的状态也改变
				changeWBTEnabeld(status);
				// 刷新编辑按钮状态
			}
			// 不是集团
			if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
					|| (SysContext.getSysContext().getCurrentCostUnit() != null && SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())) {
				if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isSource") != null) {
					boolean statusSource = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isSource").getValue()).booleanValue();
					if (statusSource) {// 源，已分配的源不允许删除(允许修改)
						// this.btnRemove.setEnabled(false);
						// this.menuItemRemove.setEnabled(false);
						// this.btnEdit.setEnabled(false);
						// this.menuItemEdit.setEnabled(false);//
						if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("assigned") != null
								 && isSelectCurOrg) {
							boolean statusAssign = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("assigned").getValue()).booleanValue();
							if (statusAssign) {
//								this.btnRemove.setEnabled(false);
//								this.btnEdit.setEnabled(true);
//								this.btnAddNew.setEnabled(true);
//								
//								this.menuItemRemove.setEnabled(false);
//								this.menuItemEdit.setEnabled(true);
//								this.menuItemAddNew.setEnabled(true);
							} else {
//								this.btnAddNew.setEnabled(true);
//								this.btnEdit.setEnabled(true);
//								this.btnRemove.setEnabled(true);
//
//								this.menuItemAddNew.setEnabled(true);
//								this.menuItemEdit.setEnabled(true);
//								this.menuItemRemove.setEnabled(true);

							}
						}
						
					} else {// 非源(分配下来的),不允许修改删除,不允许禁用
//						this.btnRemove.setEnabled(false);
//						this.btnEdit.setEnabled(false);
//						this.btnCancel.setEnabled(false);
//
//						this.menuItemRemove.setEnabled(false);
//						this.menuItemEdit.setEnabled(false);
//						this.menuItemCancel.setEnabled(false);

						// 如果选中的不是非源的叶结点,不允许新增
						if (((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isLeaf").getValue()).booleanValue()
								 && (isSelectCurOrg || (curProject!=null&&curProject.isIsLeaf()&&!curProject.isIsDevPrj()))) {//可研，明细项目允许
							// 如果选中的是叶节点,但是该叶节点所在组织
//							this.btnAddNew.setEnabled(true);
//							this.menuItemAddNew.setEnabled(true);
						} else {//如果选中的不是叶结点，要判断其下级节电是否是源
							if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex() + 1) != null) {
								if (((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex() + 1).getCell("isSource").getValue()).booleanValue()
										 && isSelectCurOrg) {
									//下级为源
//									this.btnAddNew.setEnabled(true);
//									this.menuItemAddNew.setEnabled(true);
								} else {
									//非源
//									this.btnAddNew.setEnabled(false);
//									this.menuItemAddNew.setEnabled(false);
								}
							}

						}
						disabledWBT();
					}
				}

			} else {// 如果当前的组织是集团，并且当前选中的数据其节点不是集团，那么不允许编辑
				if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("fullOrgUnit.id").getValue() != null) {
					String ouid = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("fullOrgUnit.id").getValue().toString();
					if (!OrgConstants.DEF_CU_ID.equals(ouid)) {
						this.btnAddNew.setEnabled(false);
						this.btnEdit.setEnabled(false);
						this.btnRemove.setEnabled(false);						
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
						this.btnAssign.setEnabled(true);
						this.btnDisAssign.setEnabled(true);

						this.menuItemImport.setEnabled(true);
						this.menuItemAddNew.setEnabled(true);
						this.menuItemEdit.setEnabled(true);
						this.menuItemRemove.setEnabled(true);
						this.menuItemAssign.setEnabled(true);
						this.menuItemDisAssign.setEnabled(true);
						// 已分配的不允许删除
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

	protected String getGroupEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getQueryFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		// TODO Auto-generated method stub
		return null;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getEditUIName() {
		return IncomeAccountEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return IncomeAccountFactory.getRemoteInstance();
	}
	
	private class IncomeAccountTreeRender implements IBasicRender {

		private int TABSIZE = 8;

		private int ICONSIZE = 10;

		private int margin = 2;

		protected SimpleTextRender simpleRender = new SimpleTextRender();

		protected HashMap IncomeAccountIdToPos = new HashMap();

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
			String IncomeAccountId = numberExpandInfo.getIncomeAccountId();
			java.awt.Rectangle rec = (java.awt.Rectangle) IncomeAccountIdToPos.get(IncomeAccountId);
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
				IncomeAccountIdToPos.put(numberExpandInfo.getIncomeAccountId(), iconRect);

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
		private String IncomeAccountId;

		private String number;

		private boolean isExpandStatus;

		// private String signNumber;
		private int level;

		private boolean isLeaf;

		/**
		 * @return 返回 isExpandStatus。
		 */
		public boolean isExpandStatus() {
			return isExpandStatus;
		}

		/**
		 * @param isExpandStatus
		 *            要设置的 isExpandStatus。
		 */
		public void setExpandStatus(boolean isExpandStatus) {
			this.isExpandStatus = isExpandStatus;
		}

		/**
		 * @return 返回 number。
		 */
		public String getNumber() {
			return number;
		}

		/**
		 * @param number
		 *            要设置的 number。
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

		public String getIncomeAccountId() {
			return IncomeAccountId;
		}

		public void setIncomeAccountId(String IncomeAccountId) {
			this.IncomeAccountId = IncomeAccountId;
		}
	}

	/**
	 * 
	 * 描述：设置行的显示格式，被tblMain_tableClicked调用
	 * 
	 */
	private void setTreeDisplayStyle(IRow row) {

		boolean isIncomeAccountLeaf = ((Boolean) row.getCell("isLeaf").getValue()).booleanValue();
		if (isIncomeAccountLeaf) {
			return;
		} else {
			expandIncomtAccount(row);
		}
	}
	
	/**
	 * 
	 * 描述：用于非叶子结点时，显示下级 1.设置本行信息 2.设置下级信息
	 */
	private void expandIncomtAccount(IRow row) {
		NumberExpandInfo IncomeAccountNumberExpandInfo = (NumberExpandInfo) row.getCell("number").getValue();
		String IncomeAccountNumber = IncomeAccountNumberExpandInfo.getNumber();
		boolean isExpandStatus = IncomeAccountNumberExpandInfo.isExpandStatus();
		String IncomeAccountId = (String) row.getCell("id").getValue();
		if (isExpandStatus) {
			IncomeAccountNumberExpandInfo.setExpandStatus(false);

			int i = row.getRowIndex() + 1;
			tblMain.setRefresh(false);
			IRow child;
			for (int count = tblMain.getRowCount(); i < count; i++) {
				child = tblMain.getRow(i);
				if (child.getCell("number").getValue() instanceof NumberExpandInfo) {
					NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo) child.getCell("number").getValue();
					String childNumber = childNumberExpandInfo.getNumber();
					if (childNumber.startsWith(IncomeAccountNumber)) {
						child.getStyleAttributes().setHided(true);
					} else {
						break;
					}
				}
			}
			tblMain.setRefresh(true);
			tblMain.reLayoutAndPaint();
		} else {
			IncomeAccountNumberExpandInfo.setExpandStatus(true);
			int i = row.getRowIndex() + 1;
			boolean hasChildrenDataGotten = false;
			String curProjectId, fullOrgUnitId;

			if (i < tblMain.getRowCount()) {
				IRow next = tblMain.getRow(i);
				if (next.getCell("number").getValue() instanceof NumberExpandInfo) {
					NumberExpandInfo nextNumberExpandInfo = (NumberExpandInfo) next.getCell("number").getValue();
					if (nextNumberExpandInfo.getNumber().startsWith(IncomeAccountNumber)) {
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
				childFilter.getFilterItems().add(new FilterItemInfo("parent.id", IncomeAccountId));
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
									childNumberExpandInfo.setIncomeAccountId(rowset.getString("id"));
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
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
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

					if (childNumber.startsWith(IncomeAccountNumber)) {
						String parentId = (String) child.getCell("parent.id").getValue();
						if (parentId != null && parentId.equals(IncomeAccountId)) {
							// 当下级结点还有下级时，分级显示下级
							expandChildIncomeAccount(child);
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
	 * 描述：递归方法，用于下级结点还有下级时，分级显示
	 * 
	 */
	private void expandChildIncomeAccount(IRow row) {
		NumberExpandInfo IncomeAccountNumberExpandInfo = (NumberExpandInfo) row.getCell("number").getValue();
		String IncomeAccountNumber = IncomeAccountNumberExpandInfo.getNumber();
		boolean isExpandStatus = IncomeAccountNumberExpandInfo.isExpandStatus();
		String IncomeAccountId = (String) row.getCell("id").getValue();
		row.getStyleAttributes().setHided(false);
		if (isExpandStatus) {
			int i = row.getRowIndex() + 1;
			IRow child;
			for (int count = tblMain.getRowCount(); i < count; i++) {
				child = tblMain.getRow(i);
				NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo) child.getCell("number").getValue();
				String childNumber = childNumberExpandInfo.getNumber();

				if (childNumber.startsWith(IncomeAccountNumber)) {
					String parentId = (String) child.getCell("parent.id").getValue();
					if (parentId != null && parentId.equals(IncomeAccountId)) {
						expandChildIncomeAccount(child);
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
	 * 随着每一行的isEnabled的值改变，两个btn的状态也改变
	 * 
	 * @param isEnabled
	 *            boolean
	 */
	private void changeWBTEnabeld(boolean isEnabled) {
		this.actionCancel.setEnabled(isEnabled);// 禁用按钮
		this.actionCancelCancel.setEnabled(!isEnabled);// 启用按钮
	}

	/**
	 * 把启用/禁止按钮disabled
	 */
	private void disabledWBT() {
		this.actionCancel.setEnabled(false);// 禁用按钮
		this.actionCancelCancel.setEnabled(false);// 启用按钮
	}
	
	/**
	 * 树形的CU过滤处理。
	 */
	protected FilterInfo getDefaultFilterForTree() {
		return super.getDefaultFilterForTree();
	}

	/**
	 * 默认进行当前CU的过滤。子类可重载。
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
			tblMain.removeRows();// 触发新查询
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
			tblMain.removeRows();// 触发新查询

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
			tblMain.removeRows();// 触发新查询
			// 如果当前的组织是集团，并且当前选中的节点是工程项目,不允许新增
			if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
					|| (SysContext.getSysContext().getCurrentCostUnit() != null && SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())) {
				// 不是集团,不能新增非明细节点
				if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
//					this.btnAddNew.setEnabled(false);
//					this.btnEdit.setEnabled(false);
//					this.btnRemove.setEnabled(false);
					// this.btnIncomeAccountImport.setEnabled(false);
					// this.btnAssign.setEnabled(false);
					// this.btnDisAssign.setEnabled(false);
//					this.menuItemAddNew.setEnabled(false);
//					this.menuItemEdit.setEnabled(false);
//					this.menuItemRemove.setEnabled(false);
				}
				if(node.isLeaf()&&!projectInfo.isIsDevPrj()){
//					this.btnAddNew.setEnabled(true);
//					this.btnEdit.setEnabled(true);
//					this.btnRemove.setEnabled(true);
//					this.menuItemAddNew.setEnabled(true);
//					this.menuItemEdit.setEnabled(true);
//					this.menuItemRemove.setEnabled(true);
				}
				//

			} else {// 是集团
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);				
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
			tblMain.removeRows();// 触发新查询
			// 如果当前的组织是集团，并且当前选中的节点不是集团，那么不允许新增
			if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
					|| (SysContext.getSysContext().getCurrentCostUnit() != null && SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())) {
				// 不是集团,不能新增非明细节点
				if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
//					this.btnAddNew.setEnabled(false);
//					this.btnEdit.setEnabled(false);
//					this.btnRemove.setEnabled(false);
					// this.btnIncomeAccountImport.setEnabled(false);
					// this.btnAssign.setEnabled(false);
					// this.btnDisAssign.setEnabled(false);
//					this.menuItemAddNew.setEnabled(false);
//					this.menuItemEdit.setEnabled(false);
//					this.menuItemRemove.setEnabled(false);
				}

			} else {// 是虚体,包括集团
				if (!OrgConstants.DEF_CU_ID.equals(info.getId().toString())) {
					this.btnAddNew.setEnabled(false);
					this.btnEdit.setEnabled(false);
					this.btnRemove.setEnabled(false);					
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
		
		//当前组织是集团且选择的是集团结点，可以操作是否进行成本数据库
		if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())
				&&(node.getUserObject() instanceof OrgStructureInfo)
				&&node.getLevel()==0){
			actionEnterDB.setEnabled(true);
			actionEnterDB.setVisible(true);
			actionCancelEnterDB.setEnabled(true);
			actionCancelEnterDB.setVisible(true);
			//tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(false);
		}else{
			actionEnterDB.setEnabled(false);
			actionEnterDB.setVisible(false);
			actionCancelEnterDB.setEnabled(false);
			actionCancelEnterDB.setVisible(false);
			//tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(true);
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
		// 上级获取
		int rowNo = tblMain.getSelectManager().getActiveRowIndex();
		if (rowNo != -1) {// 选中了上级
			IRow row = this.tblMain.getRow(rowNo);
			this.getUIContext().put("upId", row.getCell("id").getValue().toString());
		} else {
			// 如果没有选中
			// 节点获取
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node == null) {
				MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "IncomeAccount_Add_SelectNode"));
				return;
			} else {
				if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
						|| (SysContext.getSysContext().getCurrentCostUnit() != null && SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())) {
					MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "IncomeAccount_Add_OnlyDetail"));
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
//		//		判断是否被拆分引用
//		if (this.getUIContext().get("upId") != null) {
//			String id = this.getUIContext().get("upId").toString();
//			if (ContractCostSplitEntryFactory.getRemoteInstance().exists("select id where IncomeAccount.id = '" + id + "'")
//					|| SettlementCostSplitEntryFactory.getRemoteInstance().exists("select id where IncomeAccount.id = '" + id + "'")
//					|| ConChangeSplitEntryFactory.getRemoteInstance().exists("select id where IncomeAccount.id = '" + id + "'")
//					|| PaymentSplitEntryFactory.getRemoteInstance().exists("select id where IncomeAccount.id = '" + id + "'")) {
//				if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "IncomeAccountSplit")))) {
//					// 不继续
//					return;
//				}
//			}
//		}
//		//判断是否被目标成本引用
//		if (this.getUIContext().get("upId") != null) {
//			String id = this.getUIContext().get("upId").toString();
//			if (CostEntryFactory.getRemoteInstance().exists("select id where IncomeAccount.id = '" + id + "'")) {
////			if(true){
//				MsgBox.showWarning(this, "科目被目标成本引用，不能新增下级！");//候艳要求加此约束
//				SysUtil.abort();
//			}
//		}
		super.actionAddNew_actionPerformed(e);
	}
	
	/**
	 * 对新增，准备UI参数的时候，需要传入父结点参数
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
	}

	/**
	 * 对新增，准备UI参数的时候，需要传入父结点参数
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
		 * 解决manQuery缓存问题
		 */
		this.mainQuery = new EntityViewInfo();
		mainQuery.setFilter(getDefaultFilterForQuery());
		super.actionQuery_actionPerformed(e);
	}

	// 是否需要进行表格排序。因为实现了分级显示，所以不能排序
	protected boolean isOrderForClickTableHead() {
		return false;
	}

	/**
	 * 
	 * 描述：覆盖超类行为，右键菜单没有新增、删除
	 */
	protected void initPopmenu() {
		/* 增加显示右键菜单的代码 */
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
	 * 描述：添加tblMain的监听器
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
	 * 描述：tblMain显示树状格式
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
			String IncomeAccountNumber = (String) numberValue;
			boolean isIncomeAccountLeaf = ((Boolean) row.getCell("isLeaf").getValue()).booleanValue();
			NumberExpandInfo IncomeAccountNumberExpandInfo = new NumberExpandInfo();
			IncomeAccountNumberExpandInfo.setIncomeAccountId(id);
			IncomeAccountNumberExpandInfo.setLevel(level);
			IncomeAccountNumberExpandInfo.setLeaf(isIncomeAccountLeaf);
			IncomeAccountNumberExpandInfo.setNumber(IncomeAccountNumber);
			IncomeAccountNumberExpandInfo.setExpandStatus(true);
			row.getCell("number").setValue(IncomeAccountNumberExpandInfo);
			if (!isSetTreeDispalyStyle) {
				if (level == 1) {
					row.getStyleAttributes().setHided(false);
					IncomeAccountNumberExpandInfo.setExpandStatus(false);
				} else {
					row.getStyleAttributes().setHided(true);
				}
			} else
				IncomeAccountNumberExpandInfo.setExpandStatus(true);
		}
	}

	/**
	 * 启用
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		// super.actionCancelCancel_actionPerformed(e);

		checkSelected();
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(activeRowIndex);
		// if (row == null)
		// return;
		String id = row.getCell("id").getValue().toString().trim();
		IIncomeAccount iIncomeAccount = IncomeAccountFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		
		IncomeAccountInfo IncomeAccountInfo = iIncomeAccount.getIncomeAccountInfo(pk);
		//srcIncomeAccount 初始化时为空字符串，要做长度判断  by hpw
//		if(IncomeAccountInfo.getSrcIncomeAccountId() != null &&!IncomeAccountInfo.isIsEnabled()) {
		if(IncomeAccountInfo.getSrcIncomeAccountId() != null && IncomeAccountInfo.getSrcIncomeAccountId().length()>0&&!IncomeAccountInfo.isIsEnabled()) {
			MsgBox.showWarning(this, "上级分配下来的已禁用的科目，下级不能启用");
			SysUtil.abort();
		}
		if (iIncomeAccount.enable(pk)) {
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			showMessage();
			//不进行查询,直接在界面进行更新即可
			//tblMain.getRow(activeRowIndex).getCell("isEnabled").setValue(Boolean.TRUE);
			//tblMain_tableSelectChanged(null);
			tblMain.removeRows();// 触发新查询
//			this.btnCancel.setEnabled(false);
//			this.menuItemCancel.setEnabled(false);
//			this.btnCancelCancel.setEnabled(true);
//			this.menuItemCancelCancel.setEnabled(true);
			
			tblMain.getSelectManager().select(activeRowIndex, 0);
			tblMain.scrollToVisible(activeRowIndex, 0);
		}
	}

	/**
	 * 禁用
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		// super.actionCancel_actionPerformed(e);
		checkSelected();
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(activeRowIndex);
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		IIncomeAccount iIncomeAccount = IncomeAccountFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		if (iIncomeAccount.disable(pk)) {
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
			showMessage();
			//不进行查询,直接在界面进行更新即可
			//tblMain.getRow(activeRowIndex).getCell("isEnabled").setValue(Boolean.FALSE);
			//tblMain_tableSelectChanged(null);
			
			tblMain.removeRows();// 触发新查询
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
	 * 分配
	 */
	public void actionAssign_actionPerformed(ActionEvent e) throws Exception {
//		UIContext uiContext = new UIContext(this);
//		IUIWindow assignUI = UIFactory.createUIFactory().create(IncomeAccountAssignUI.class.getName(), uiContext);
//		assignUI.show();
		
		if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "IncomeAccountAssign_Note")))) {
			// 提示未启用的成本科目将不会分配下去
			return;
		}
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {// 应该提示"请选中欲分配的节点"
			MsgBox.showWarning(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "IncomeAccount_Assign_Selected"));
			//			
			return;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectInfo.getId().toString()));
//			filter.getFilterItems().add(new FilterItemInfo("assigned", Boolean.TRUE));
//			boolean ass = IncomeAccountFactory.getRemoteInstance().exists(filter);
//			if(ass) {
				if (!MsgBox.isYes(MsgBox.showConfirm2(this, "要分配的科目的上级科目如果已经有数据，则无法分配，这样会导致该科目上级汇总数不正确，是否继续？"))) {
					// 提示
					return;
				}
//			}
			
			IIncomeAccountFacade iIncomeAccountFacade = IncomeAccountFacadeFactory.getRemoteInstance();
			List errorList = iIncomeAccountFacade.assignProjsIncomeAccount(new ObjectStringPK(projectInfo.getId().toString()));
			
			showErrorInfo(errorList);
			tblMain.removeRows();// 触发新查询
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			
			
			
			// 组织之间的分配
			if ((node.getChildCount() > 0) && (((DefaultKingdeeTreeNode) node.getChildAt(0)).getUserObject() instanceof OrgStructureInfo)) {

				OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
				if (oui == null || oui.getUnit() == null)
					return;
				FullOrgUnitInfo info = oui.getUnit();
				
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("assigned", Boolean.TRUE));
				boolean ass = IncomeAccountFactory.getRemoteInstance().exists(filter);
				if(ass) {
					if (!MsgBox.isYes(MsgBox.showConfirm2(this, "要分配的科目的上级科目如果已经有数据，则无法分配(仅有数据的无法分配)，这样会导致该科目上级汇总数不正确，是否继续？"))) {
						// 提示未启用的成本科目将不会分配下去
						return;
					}
				}
				
				IIncomeAccountFacade iIncomeAccountFacade = IncomeAccountFacadeFactory.getRemoteInstance();
				iIncomeAccountFacade.assignOrgsIncomeAccount(new ObjectStringPK(info.getId().toString()));
				tblMain.removeRows();// 触发新查询

			} else {
				// 若选择结点为组织与工程项目临界点,则为组织与工程项目之间的分配
				OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
				if (oui == null || oui.getUnit() == null)
					return;
				FullOrgUnitInfo info = oui.getUnit();
				
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("assigned", Boolean.TRUE));
				boolean ass = IncomeAccountFactory.getRemoteInstance().exists(filter);
				if(ass) {
					if (!MsgBox.isYes(MsgBox.showConfirm2(this, "要分配的科目的上级科目如果已经有数据，则无法分配，这样会导致该科目上级汇总数不正确，是否继续？"))) {
						// 提示未启用的成本科目将不会分配下去
						return;
					}
				}
				
				IIncomeAccountFacade iIncomeAccountFacade = IncomeAccountFacadeFactory.getRemoteInstance();
				List errorlist = iIncomeAccountFacade.assignOrgProject(new ObjectStringPK(info.getId().toString()));
				showErrorInfo(errorlist);
				
				tblMain.removeRows();// 触发新查询
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
		String title = "部分科目由于其上级已经有数据，未分配成功，其余已经分配成功！未分配成功的科目请查看详细信息";
		
		String error = errorStr.toString();
		error = error.replace('!', '.');
		MsgBox.showDetailAndOK(this, title, error, 1);
	}

	/**
	 * 反分配
	 */
	public void actionDisAssign_actionPerformed(ActionEvent e) throws Exception {
//		MsgBox.showWarning(this, "");
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			// 请先指定项
			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "IncomeAccount_DisAssign_CheckSelected"));
			return;
		}
		IncomeAccountCollection cacSelect = new IncomeAccountCollection();
		IncomeAccountInfo caiSelect;
		KDTSelectBlock selectBlock = null;
		ICell cell = null;
		FullOrgUnitInfo foui;
		CurProjectInfo cpi;
		for (int i = 0; i < tblMain.getSelectManager().size(); i++) {
			caiSelect = new IncomeAccountInfo();
			selectBlock = tblMain.getSelectManager().get(i);
			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
				IRow row = this.tblMain.getRow(j);
				// this.verifyNotAccepted(row);//验证是否为已分配项
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
					if (row.getCell("srcIncomeAccountId").getValue() != null) {
						caiSelect.setSrcIncomeAccountId(row.getCell("srcIncomeAccountId").getValue().toString());
					}
				}
				caiSelect.setName(row.getCell("name").getValue().toString(), SysContext.getSysContext().getLocale());
				cacSelect.add(caiSelect);
			}
		}

		IIncomeAccountFacade iIncomeAccountFacade = IncomeAccountFacadeFactory.getRemoteInstance();

		ArrayList al = iIncomeAccountFacade.disAssignSelectOrgProject(cacSelect);
		if (al.size() != 0) {
			ArrayList alOrgAndProject = new ArrayList();
			for (int i = 0; i < al.size(); i++) {
				alOrgAndProject.addAll(i, (ArrayList) al.get(i));
			}
			MsgBox.showDetailAndOK(this, "详细信息列出了无法反分配的明细情况!", alOrgAndProject.toString(), 1);

		}
		actionRefresh_actionPerformed(e);
	}

	/**
	 * 模板导入
	 */
	public void actionTemplateImport_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		boolean checkGroupNodeSelected = true;
		if (node != null) {
			// 提示选中节点
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
			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "IncomeAccount_TemplateImport_CheckNodeSelected"));
			return;
		}
		HashMap map = new HashMap();
		map.put("Owner", this); // 必须。被启动UI的父UI对象
		UIContext uiContext = new UIContext(this);
		// 供子类定义要传递到EditUI中扩展的属性

		prepareUIContext(uiContext, e);
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory"); // 以模态对话框方式启动
		IUIWindow uiWindow = uiFactory.create("com.kingdee.eas.fdc.basedata.client.IncomeAccountTemplateDataImportUI", /* 被启动对象的类名称 */
		uiContext, null, OprtState.EDIT);
		uiWindow.show();
		actionRefresh_actionPerformed(e);

	}
	
	/**
	 * 成本科目引入
	 */
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			// 提示选中节点
			// MsgBox.showWarning("请指定需要引入的节点!");//ProjectImport_CheckNodeSelected
			MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "ProjectImport_CheckNodeSelected"));
			return;
		}
		// if (OrgViewUtils.isTreeNodeDisable(node)) {
		// return;
		// }
		HashMap map = new HashMap();
		map.put("Owner", this); // 必须。被启动UI的父UI对象
		UIContext uiContext = new UIContext(this);
		// 供子类定义要传递到EditUI中扩展的属性
		if (node.getUserObject() != null) {
			if (node.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo info = (CurProjectInfo) node.getUserObject();
				uiContext.put("address", info);
				uiContext.put("isOrgFilter", Boolean.valueOf(true));
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				// if
				// ((node.getChildCount()>0)&&(((DefaultKingdeeTreeNode)node.getChildAt(0)).getUserObject()
				// instanceof OrgStructureInfo)) {// 选择结点不适合新增,灰掉新增按钮
				// return;
				// }
				// 需求规定不存在这种情况,提示只能对工程项目进行数据引入
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
		uiFactory = UIFactory.createUIFactory(UIModelDialogFactory.class.getName()); // 以模态对话框方式启动
		IUIWindow uiWindow = uiFactory.create(IncomeAccountImportUI.class.getName(), uiContext, null, OprtState.EDIT);
		uiWindow.show();
		actionRefresh_actionPerformed(e);
	}
	
	protected String getEditUIModal()
    {
        // return UIFactoryName.MODEL;
        // return UIFactoryName.NEWWIN;
        // 2006-4-29 胡博要求加入根据配置项来读取UI打开方式。
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
		String solutionName = "eas.fdc.fdcbasedata.fdcincomeaccount";
		param.solutionName = solutionName;
		param.alias = "房地产收入科目";
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;
    }

    public void actionAssignToOrg_actionPerformed(ActionEvent e) throws Exception {
		//提示未启用的收入科目将不会分配下去
		if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "IncomeAccountAssign_Note")))) {
			return;
		}
		
		if (!MsgBox.isYes(MsgBox.showConfirm2(this, "要分配的科目的上级科目如果已经有数据，则无法分配(仅有数据的无法分配)，这样会导致该科目上级汇总数不正确，是否继续？"))) {
			return;
		}
	
		UIContext uiContext = new UIContext(this);
		IUIWindow assignUI = UIFactory.createUIFactory().create(IncomeAccountAssignUI.class.getName(), uiContext);
		assignUI.show();
		
		tblMain.removeRows();// 触发新查询
	} 
    
    private HashMap params = null;
	private HashMap getParams() {
		if(params==null){
	        HashMap hmParamIn = new HashMap();	        
	        //成本测算与收入测算是否联用
	        hmParamIn.put(FDCConstants.FDC_PARAM_ISINCOMEJOINCOST, null);
	        try{
	        	HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
	        	params=hmAllParam;
	        }catch(Exception e){
	        	handUIExceptionAndAbort(e);
	        }
		}
		if(params==null){
			params=new HashMap();
		}
        return params;
	}
	/**
	 * 成本测算与收入测算是否联用
	 * @return
	 */
	protected boolean isIncomeJoinCost() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_ISINCOMEJOINCOST);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：是否支持EAS高级统计(EAS800新增的功能)
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-1
	 */
	// @Override
	protected boolean isSupportEASPivot() {
		// return super.isSupportEASPivot();
		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}