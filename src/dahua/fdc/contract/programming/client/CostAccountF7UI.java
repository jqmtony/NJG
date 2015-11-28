/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.mm.control.client.TableCellComparator;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CostAccountF7UI extends AbstractCostAccountF7UI {
	private static final Logger logger = CoreUIObject.getLogger(CostAccountF7UI.class);
	private static final Color canntSelectColor = new Color(0xFEFED3);
	private boolean isCancel = true;
	
	private FilterInfo filView = new FilterInfo();

	public boolean isCancel() {
		return isCancel;
	}

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

	public CostAccountF7UI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		menuBar.setVisible(false);
		toolBar.setVisible(false);
		super.onLoad();
		btnConfirm.setEnabled(true);
		btnExit.setEnabled(true);
		
		isSeeSplit.setSelected(true);
		tblMain.getColumn("fullOrgUnit.name").getStyleAttributes().setHided(true);
	}
	protected void isSeeSplit_itemStateChanged(ItemEvent e) throws Exception {
		super.isSeeSplit_itemStateChanged(e);
		boolean selected = isSeeSplit.isSelected();
		Map map = this.getUIContext();
		if (map.get("view") != null) {
			
			
			FilterInfo clone = (FilterInfo) filView.clone();
			
			EntityViewInfo viewInfo = this.getMainQuery();
			
			if (selected) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isSplit", new Boolean(selected)));
				clone.mergeFilter(filter, "and");
			}
			
			
			viewInfo.setFilter(clone);
		}
		refresh(null);
		
		if (tblMain.getRowCount()>0 && map.get("projectF7") != null) {
			Map aimAmountMap = new HashMap();
			CurProjectInfo curInfo = (CurProjectInfo) map.get("projectF7");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.isLastVersion",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("head.orgOrProId",curInfo.getId().toString()));
			
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add("costAccount.id");
			coll.add("costAmount");
			view.setSelector(coll );
			CostEntryCollection costEntryCollection = CostEntryFactory.getRemoteInstance().getCostEntryCollection(view);
			for(int i=0;i<costEntryCollection.size();i++){
				CostEntryInfo costEntryInfo = costEntryCollection.get(i);
				String costId = costEntryInfo.getCostAccount().getId().toString();
				if(aimAmountMap.containsKey(costId)){
					aimAmountMap.put(costId, FDCHelper.add(aimAmountMap.get(costId), costEntryInfo.getCostAmount()));
				}else{
					aimAmountMap.put(costId, costEntryInfo.getCostAmount());
				}
			}
			IColumn addColumn = tblMain.addColumn();
			addColumn.setKey("aimAmount");
			addColumn.getStyleAttributes().setNumberFormat(FDCHelper.strDataFormat);
			addColumn.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			addColumn.setWidth(150);
			tblMain.getHeadRow(0).getCell("aimAmount").setValue("目标成本");
			for(int i=0;i<tblMain.getRowCount();i++){
				String  id = (String) tblMain.getCell(i, "id").getValue();
				if(aimAmountMap.containsKey(id)){
					tblMain.getCell(i, "aimAmount").setValue(aimAmountMap.get(id));
				}
			}
		}
		
		tblMain.addKDTMouseListener(new KDTSortManager(tblMain));
		tblMain.getSortMange().setSortAuto(false);
		List rows = this.tblMain.getBody().getRows();
		Collections.sort(rows, new TableCellComparator(tblMain.getColumnIndex("longNumber"), KDTSortManager.SORT_ASCEND));

		int maxLevel = 0;
		int[] levelArray = new int[tblMain.getRowCount()];

		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);

			Object name = row.getCell("name").getValue();
			if (name != null && name.toString().trim().length() > 0) {
				String blank = setNameIndent(level);
				row.getCell("name").setValue(blank + name.toString().trim());
			}
		}

		for (int i = 0; i < tblMain.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		tblMain.getTreeColumn().setDepth(maxLevel);
		tblMain.getColumn("level").getStyleAttributes().setHided(true);
		// 设置非明细科目的底色为黄色
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row.getCell("isLeaf").getValue() != null) {
				boolean isIsLeaf = ((Boolean) row.getCell("isLeaf").getValue()).booleanValue();
				if (!isIsLeaf) {
					row.getStyleAttributes().setBackground(canntSelectColor);
				}
			}
		}
		tblMain.reLayoutAndPaint();
				this.tblMain.setRefresh(false);
		
	}
	public void onShow() throws Exception {
		super.onShow();
		tblMain.addKDTMouseListener(new KDTSortManager(tblMain));
		tblMain.getSortMange().setSortAuto(false);
		
		List rows = this.tblMain.getBody().getRows();
        Collections.sort(rows, new TableCellComparator(tblMain.getColumnIndex("longNumber"), KDTSortManager.SORT_ASCEND));
		
    	int maxLevel = 0;
		int[] levelArray = new int[tblMain.getRowCount()];

		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
			
			Object name =  row.getCell("name").getValue();
			if(name != null && name.toString().trim().length() > 0){
				String blank = setNameIndent(level);
				row.getCell("name").setValue(blank+name.toString().trim());
			}
		}

		for (int i = 0; i < tblMain.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		tblMain.getTreeColumn().setDepth(maxLevel);
		tblMain.getColumn("level").getStyleAttributes().setHided(true);
		// 设置非明细科目的底色为黄色
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row.getCell("isLeaf").getValue() != null) {
				boolean isIsLeaf = ((Boolean) row.getCell("isLeaf").getValue()).booleanValue();
				if (!isIsLeaf) {
					row.getStyleAttributes().setBackground(canntSelectColor);
				}
			}
		}
		this.tblMain.setRefresh(true);
	}
	
	public void loadFields() {
    	super.loadFields();
	}
	
	private String setNameIndent(int level){
		StringBuffer blank = new StringBuffer("");
		for(int i = level ; i > 1 ; i--){
			blank.append("        ");
		}
		return blank.toString();
	}
	
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
		Map map = this.getUIContext();
		if(map.get("view") != null){
			EntityViewInfo entityView = (EntityViewInfo)map.get("view");
			EntityViewInfo viewInfo = this.getMainQuery();
			viewInfo.setFilter(entityView.getFilter());
			
			filView = entityView.getFilter();
		}
	}
	
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			if (e.getType() == 0) {
				return;
			} else {
				confirm();
				setCancel(false);
			}
		}
	}

	public CostAccountInfo[] getData() throws Exception {
		ArrayList IDList = getSelectedIdValues();
		CostAccountInfo [] info = new CostAccountInfo[IDList.size()];
		for(int i = 0 ; i < IDList.size() ; i++){
			CostAccountInfo costAccount = new CostAccountInfo();
			String id = IDList.get(i).toString();
			if (id == null)
				return null;
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("id");
			view.getSelector().add("name");
			view.getSelector().add("longNumber");
			view.getSelector().add("curProject.id");
			view.getSelector().add("curProject.displayName");
			view.getSelector().add("isLeaf");
			view.getSelector().add("CU.id");
			view.getSelector().add("curProject.longNumber");
			view.getSelector().add("isEnabled");
			view.getSelector().add("isSplit");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id));
			view.setFilter(filter);
			costAccount = (CostAccountInfo) CostAccountFactory.getRemoteInstance().getCostAccountCollection(view).get(0);
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("parent.id", id));
			filterInfo.getFilterItems().add(
					new FilterItemInfo("isSplit", new Integer(1), CompareType.EQUALS));
			view.setFilter(filterInfo);
			CostAccountCollection costAccounts = CostAccountFactory.getRemoteInstance()
					.getCostAccountCollection(view);
             // 修改只允许选择明细科目的逻辑
			if (!costAccount.isIsLeaf() && (getUIContext().get("isShowParent")==null || (Boolean)getUIContext().get("isShowParent"))) {
				if (!costAccount.isIsSplit()) {
					MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
					SysUtil.abort();
				}
				if (costAccounts.size() > 0) {
					MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
					SysUtil.abort();
				}
			}
			info[i] = costAccount;
		}
		disposeUIWindow();
		return info;
	}

	private void confirm() throws Exception {
		checkSelected();
		getData();
		setCancel(true);
	}

	public void checkSelected() {
		if (tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0 || tblMain.getSelectManager().getActiveRowIndex() < 0) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
	}
	
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		confirm();
		setCancel(false);
	}
	
	public void actionExit_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
		setCancel(true);
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		// 不按“确认”直接按回车时，会调用这里，这时不需查看成本科目，
		// 调用“确认”按钮的代码表示选中了当前的成本科目  Added by Owen_wen 2012-10-11
		actionConfirm_actionPerformed(e);
	}

	protected String getEditUIName() {
		return null;
	}
}