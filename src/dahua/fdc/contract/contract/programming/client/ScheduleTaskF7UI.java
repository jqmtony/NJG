/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskFiledEnum;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 相关任务项F7界面
 * 
 * @author shangjing
 * 
 */
public class ScheduleTaskF7UI extends AbstractScheduleTaskF7UI {
	public ScheduleTaskF7UI() throws Exception {
		super();
	}

	private static final Logger logger = CoreUIObject.getLogger(ScheduleTaskF7UI.class);

	private boolean isCancel;

	public boolean isCancel() {
		return isCancel;
	}

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

	private FilterInfo filter;

	public void onShow() throws Exception {
		// 为表格创建左边的树
		createLevelTree();
	}

//	protected void btnQuery_actionPerformed(ActionEvent e) throws Exception {
//
//	}

	/**
	 * 刷新数据
	 */
	protected void btnRefresh_actionPerformed(ActionEvent e) throws Exception {
		getMainTable().removeRows();
		cols = null;
		fillTable();
		createLevelTree();
		getMainTable().revalidate();
	}

	/**
	 * 添加过滤查询功能
	 */
	public void actionBtnFilter_actionPerformed(ActionEvent e) throws Exception {

		// TODO Auto-generated method stub
		super.actionBtnFilter_actionPerformed(e);
		if (getMainTable().getRowCount() > 0) {
			getMainTable().removeRows();
		}
		boolean isBlurQuery = true;
		if (false == chkIsLike.isSelected()) {
			isBlurQuery = false;
		}

		getMainTable().checkParsed();
		getMainTable().getColumn("project").getStyleAttributes().setHided(true);
		getMainTable().getColumn("wbs").getStyleAttributes().setHided(true);
		getMainTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		if (getUIContext().get("filter") == null) {
			F7ScheduleTaskPromptBox parent = (F7ScheduleTaskPromptBox) getUIContext().get("Owner");
			this.filter = parent.getFilter();
		} else {
			this.filter = (FilterInfo) getUIContext().get("filter");
		}
		EntityViewInfo viewInfo = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("adminDept.name"));// add by warship at
														// 2010/07/26
		sic.add(new SelectorItemInfo("adminPerson.name"));
		sic.add(new SelectorItemInfo("schedule.id"));
		sic.add(new SelectorItemInfo("schedule.name"));
		sic.add(new SelectorItemInfo("schedule.number"));
		sic.add(new SelectorItemInfo("schedule.project.id"));
		sic.add(new SelectorItemInfo("schedule.project.name"));
		sic.add(new SelectorItemInfo("schedule.project.number"));
		sic.add(new SelectorItemInfo("wbs.number"));
		sic.add(new SelectorItemInfo("wbs.isLeaf"));
		//    	
		SorterItemCollection sort = new SorterItemCollection();
		sort.add(new SorterItemInfo("number"));

		viewInfo.setSorter(sort);
		viewInfo.setSelector(sic);

		// FilterInfo filter = new FilterInfo();
		StringBuffer str = new StringBuffer("select fid from t_sch_fdcscheduletask where ");
		if (ScheduleTaskFiledEnum.ALL.equals(cmbFilterField.getSelectedItem())) {
			// 如果是先择所有字段并且是模糊查询
			if (isBlurQuery) {
				List filedList = ScheduleTaskFiledEnum.getEnumList();
				for (int i = 0; i < filedList.size(); i++) {
					ScheduleTaskFiledEnum enumItem = (ScheduleTaskFiledEnum) filedList.get(i);

					if (!enumItem.getValue().equals("ALL")) {
						if (i > 1) {
							str.append(" or ");
						}
						str.append(enumItem.getValue());
						str.append(" like '%");
						if (txtValue.getText() != null && txtValue.getText().trim().length() > 0) {
							str.append(txtValue.getText().trim());
						}
						str.append("%'");
					}
				}
			} else {
				List filedList = ScheduleTaskFiledEnum.getEnumList();
				for (int i = 0; i < filedList.size(); i++) {
					ScheduleTaskFiledEnum enumItem = (ScheduleTaskFiledEnum) filedList.get(i);

					if (!enumItem.getValue().equals("ALL")) {
						if (i > 1) {
							str.append(" or ");
						}
						str.append(enumItem.getValue());
						str.append(" = '");
						if (txtValue.getText() != null && txtValue.getText().trim().length() > 0) {
							str.append(txtValue.getText().trim());
						}
						str.append("'");
					}
				}
			}
		} else if (ScheduleTaskFiledEnum.NAME.equals(cmbFilterField.getSelectedItem())) {
			if (isBlurQuery) {
				str.append(ScheduleTaskFiledEnum.NAME_VALUE);
				str.append(" like '%");
				if (txtValue.getText() != null && txtValue.getText().trim().length() > 0) {
					str.append(txtValue.getText().trim());
				}
				str.append("%'");
			} else {
				if (txtValue.getText() != null && txtValue.getText().trim().length() > 0) {
					str.append(ScheduleTaskFiledEnum.NAME_VALUE);
					str.append(" = '");
					str.append(txtValue.getText().trim());
					str.append("'");
				}
			}

		} else if (ScheduleTaskFiledEnum.NUMBER.equals(cmbFilterField.getSelectedItem())) {
			if (isBlurQuery) {
				str.append(ScheduleTaskFiledEnum.NUMBER_VALUE);
				str.append(" like '%");
				if (txtValue.getText() != null && txtValue.getText().trim().length() > 0) {
					str.append(txtValue.getText().trim());
				}
				str.append("%'");
			} else {
				if (txtValue.getText() != null && txtValue.getText().trim().length() > 0) {
					str.append(ScheduleTaskFiledEnum.NUMBER_VALUE);
					str.append(" = '");
					str.append(txtValue.getText().trim());
					str.append("'");
				}
			}
		}

		// for(Iterator it = filter.getFilterItems().iterator();it.hasNext();){
		// FilterItemInfo f = (FilterItemInfo) it.next();
		// if("id".equals(f.getPropertyName())){
		// filter.getFilterItems().remove(f);
		// }
		// f = null;
		// }

		for (int i = filter.getFilterItems().size() - 1; i >= 0; i--) {
			FilterItemInfo f = filter.getFilterItems().get(i);
			if (f != null && "id".equals(f.getPropertyName())) {
				filter.getFilterItems().remove(f);
			}
		}

		filter.getFilterItems().add(new FilterItemInfo("id", str.toString(), CompareType.INNER));

		viewInfo.setFilter(filter);
		FDCScheduleTaskCollection cols = null;
		try {
			cols = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(viewInfo);
		} catch (BOSException ex) {
			handUIExceptionAndAbort(ex);
		}
		if (cols != null && cols.size() > 0) {

			for (Iterator it = cols.iterator(); it.hasNext();) {

				FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) it.next();
				info.setIsLeaf(info.getWbs().isIsLeaf());
				IRow row = getMainTable().addRow();
				setRowData(info, row);
			}
		}
		createLevelTree();
		getMainTable().revalidate();

	}

	/**
	 * output class constructor
	 */

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionBtnFilter.setVisible(true);
		this.actionBtnFilter.setEnabled(true);
//		btnQuery.setIcon(EASResource.getIcon("imgTbtn_filter"));
		btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
		this.btnCancel.setEnabled(true);
		this.btnOk.setEnabled(true);
		fillTable();

	}

	private FDCScheduleTaskCollection cols = null;

	/**
	 * 为表填充数据
	 */
	private void fillTable() {
		getMainTable().checkParsed();
		getMainTable().getColumn("id").getStyleAttributes().setHided(true);
		getMainTable().getColumn("tasknumber").getStyleAttributes().setHided(true);
		getMainTable().getColumn("level").getStyleAttributes().setHided(true);
		getMainTable().getColumn("taskname").getStyleAttributes().setLocked(false);
		getMainTable().getColumn("start").getStyleAttributes().setLocked(true);
		getMainTable().getColumn("end").getStyleAttributes().setLocked(true);
		getMainTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		if (getUIContext().get("filter") == null) {
			F7ScheduleTaskPromptBox parent = (F7ScheduleTaskPromptBox) getUIContext().get("Owner");
			this.filter = parent.getFilter();
		} else {
			this.filter = (FilterInfo) getUIContext().get("filter");
		}
		EntityViewInfo viewInfo = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("adminDept.name"));
		// add by warship at 2010/07/26
		sic.add(new SelectorItemInfo("adminPerson.name"));
		sic.add(new SelectorItemInfo("schedule.id"));
		sic.add(new SelectorItemInfo("schedule.name"));
		sic.add(new SelectorItemInfo("schedule.complete"));
		sic.add(new SelectorItemInfo("schedule.number"));
		sic.add(new SelectorItemInfo("schedule.project.id"));
		sic.add(new SelectorItemInfo("schedule.project.name"));
		sic.add(new SelectorItemInfo("schedule.project.number"));
		sic.add(new SelectorItemInfo("wbs.number"));
		sic.add(new SelectorItemInfo("wbs.isLeaf"));

		SorterItemCollection sort = new SorterItemCollection();
		//根据 相关任务项所属单据和longnumber排序
		sort.add(new SorterItemInfo("schedule.id"));
		sort.add(new SorterItemInfo("longNumber"));

		for (int i = filter.getFilterItems().size() - 1; i >= 0; i--) {
			FilterItemInfo f = filter.getFilterItems().get(i);
			if (f != null && "id".equals(f.getPropertyName())) {
				filter.getFilterItems().remove(f);
			}
		}
		//得到最新版本的 并且是通过审核的相关任务项
		filter.appendFilterItem("schedule.isLatestVer", new Boolean(true));
//		filter.appendFilterItem("schedule.state", ScheduleStateEnum.AUDITTED);
		viewInfo.setSorter(sort);
		viewInfo.setSelector(sic);
		viewInfo.setFilter(filter);
		try {
			cols = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(viewInfo);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}

		if (cols != null && cols.size() > 0) {
			rowNum = cols.size();
			for (int i = 0; i < cols.size(); i++) {
				FDCScheduleTaskInfo info = cols.get(i);
				// 本来要去检查此info是否为叶子节点 但是排序根据其单据和longnumber排序 应该不需要了
				// info.setIsLeaf(chargeIsLeaf(info));
				IRow row = getMainTable().addRow();
				setRowData(info, row);

			}
		}

	}

	/**
	 * 为每行添加数据并设置树结构
	 * 
	 * @param info
	 *            FDCScheduleTaskInfo 进度任务info
	 * @param row
	 *            每一行
	 */
	private void setRowData(FDCScheduleTaskInfo info, IRow row) {
		CellTreeNode node = new CellTreeNode();
		row.setUserObject(info);
		node.setValue(info.getName());
		node.setTreeLevel(info.getLevel());
		row.getCell("id").setValue(info.getId());
		row.getCell("project").setValue(info.getSchedule().getProject().getName());
		row.getCell("wbs").setValue(info.getWbs().getNumber());
		row.getCell("tasknumber").setValue(node);
		row.getCell("tasknumber").setValue(info.getNumber());
		row.getCell("complete").getStyleAttributes().setNumberFormat("#,###.00");
		row.getCell("complete").setValue(info.getComplete());
		row.getCell("effecttime").setValue(info.getEffectTimes());
		row.getCell("start").setValue(info.getStart());
		row.getCell("end").setValue(info.getEnd());
		// add by warship at 2010/07/26
		row.getCell("adminDept").setValue(info.getAdminDept().getName());
		row.getCell("adminPerson").setValue(info.getAdminPerson().getName());
		row.getCell("isleaf").setValue(new Boolean(info.isIsLeaf()));
		row.getCell("iskeytask").setValue(new Boolean(info.isIsKey()));
		row.getCell("level").setValue(new Integer(info.getLevel()));
		row.getCell("islastver").setValue(new Boolean(info.getSchedule().isIsLatestVer()));
		row.getCell("taskexestate").setValue(info.getSchedule().getState());
		row.getStyleAttributes().setLocked(true);
		row.getCell("taskname").getStyleAttributes().setLocked(false);

		final int level = info.getLevel() - 1;
		final boolean isLeaf = info.isIsLeaf();
		node.setTreeLevel(level);

		if (isLeaf) {
			node.setHasChildren(false);
			if (level > 1) {
				row.getStyleAttributes().setHided(true);
			}
		} else {
			// row.getStyleAttributes().setBackground(Color.BLACK);
			if (level <= 1) {
				if (level == 0) {
					node.setCollapse(false);
				} else {
					node.setCollapse(true);
				}
			} else {
				node.setCollapse(true);// 是否只隐藏根结点
				row.getStyleAttributes().setHided(true);
			}
			node.addClickListener(new NodeClickListener() {
				public void doClick(CellTreeNode source, ICell cell, int type) {
					tblMain.revalidate();

				}
			});
			node.setHasChildren(true);
			row.getStyleAttributes().setBackground(FDCColorConstants.lockColor);
			row.getStyleAttributes().setLocked(true);
			row.getCell("taskname").getStyleAttributes().setLocked(false);
		}

		row.getCell("taskname").setValue(node);
	}

	private KDTable getMainTable() {
		return this.tblMain;
	}

	public void actionBtnOk_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		isCancel = false;
		IRow row = FDCTableHelper.getSelectedRow(getMainTable());
		if (row == null || row.getUserObject() == null) {
			FDCMsgBox.showWarning(this, "请选择行");
			SysUtil.abort();
		}
		this.getUIContext().put("selectedValue", row.getUserObject());
		this.disposeUIWindow();
	}

	public void actionBtnCancel_actionPerformed(ActionEvent e) throws Exception {
		isCancel = true;
		this.destroyWindow();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public Object getData() {
		return getUIContext().get("selectedValue");
	}

	//当前表格行数
	private int rowNum = 0;

	//判断某个相关任务项是否为叶子节点  排序OK了 应该不需要
	private boolean chargeIsLeaf(FDCScheduleTaskInfo info, FDCScheduleTaskCollection cols) {
		String id = info.getId().toString();
		for (int i = 0; i < rowNum; i++) {
			FDCScheduleTaskInfo parent = cols.get(i).getParent();
			if (parent == null)
				continue;
			if (id.equals(parent.getId().toString())) {
				return false;
			}
		}
		return true;

	}

	//建立左边的树
	private void createLevelTree() {

		if (this.tblMain.getRowCount() < 1) {
			return;
		}
		HashSet tbIds = new HashSet();
		int[] levelArray = new int[this.tblMain.getRowCount()];

		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);

			tbIds.add(row.getCell("id").getValue().toString());

			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
		}

		int maxLevel = 0;
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		tblMain.getTreeColumn().setDepth(maxLevel);

		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);

	}

	//检查选中的数据  只有叶子节点才能被选中
	public void checkSelected() {
		int rowsCount = tblMain.getBody().getRows().size();
		if (rowsCount == 0 || tblMain.getSelectManager().size() == 0 || tblMain.getSelectManager().getActiveRowIndex() < 0) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		int level = new Integer(tblMain.getCell(rowIndex, "level").getValue().toString()).intValue();
		if (rowIndex < rowsCount - 1) {
			int level_next = new Integer(tblMain.getCell(rowIndex + 1, "level").getValue().toString()).intValue();
			if (level < level_next) {
				MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
				SysUtil.abort();
			}
		}
	}

}