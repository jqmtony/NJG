/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.RESchTemplateCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述：模板任务选择界面 <br>
 * copy from schedule package
 * 
 * @author weiqiang_chen
 */
public class RESchTemplateTaskF7UI extends AbstractRESchTemplateTaskF7UI {

	private static final String BIZ_TYPE_CASHFLOW = "+BONIPDySL67xM7mEm9tXmLF6cA=";

	private static final Logger logger = CoreUIObject.getLogger(RESchTemplateTaskF7UI.class);

	protected Map preTasksMap = new HashMap();
	protected Set needProcessRow = new HashSet();
	protected RESchTemplateTaskCollection alreadySelect;

	public RESchTemplateTaskF7UI() throws Exception {
		super();
	}

	private boolean isCancel;

	public boolean isCancel() {
		return isCancel;
	}

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

	public Object getData() {
		return getUIContext().get("selectedValue");
	}

	public void actionOk_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		isCancel = false;
		IRow row = FDCTableHelper.getSelectedRow(tblTask);
		if (row == null || row.getUserObject() == null) {
			FDCMsgBox.showWarning(this, "请选择行");
			SysUtil.abort();
		}
		this.getUIContext().put("selectedValue", row.getUserObject());
		this.disposeUIWindow();
	}

	protected void checkSelected() {
		if (tblTask.getRowCount() == 0 || tblTask.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	protected RESchTemplateTaskCollection getSelectedTask() {
		RESchTemplateTaskCollection cols = new RESchTemplateTaskCollection();
		RESchTemplateTaskInfo build = null;
		for (int i = 0; i < tblTask.getRowCount(); i++) {
			if (Boolean.valueOf(tblTask.getCell(i, "checked").getValue().toString())) {
				build = (RESchTemplateTaskInfo) tblTask.getRow(i).getUserObject();
				cols.add(build);
			}

		}
		return cols;
	}

	public void onLoad() throws Exception {

		super.onLoad();
		if (getUIContext().get("alreadySelect") != null) {
			alreadySelect = (RESchTemplateTaskCollection) getUIContext().get("alreadySelect");
		}
		//		this.btnSelectAll.setIcon(EASResource.getIcon("imgTbtn_selectall"));
		//		this.btnClearAll.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
		//		this.btnSelectAll.setEnabled(true);
		//		this.btnClearAll.setEnabled(true);
		//		this.contTask.addButton(this.btnSelectAll);
		//		this.contTask.addButton(this.btnClearAll);

		this.btnSelectAll.setVisible(false);
		this.btnClearAll.setVisible(false);

		initTable();

	}

	private void initTable() {
		tblTask.checkParsed();
		
		tblTask.getColumn("taskName").setWidth(330);
		
		tblTask.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblTask.addKDTMouseListener(new KDTMouseListener(){

			public void tableClicked(KDTMouseEvent e) {
				tblTask_clicked(e);
			}
		});
		KDCheckBox chkbox = new KDCheckBox();
		chkbox.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				KDCheckBox source = (KDCheckBox) e.getSource();
				if (source.isSelected()) {
					RESchTemplateTaskInfo task = (RESchTemplateTaskInfo) tblTask.getRow(tblTask.getSelectManager().getActiveRowIndex())
							.getUserObject();
					String id = task.getId().toString();
					unChecked(id);
				}
			}

		});
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(chkbox);
		tblTask.getColumn("checked").setEditor(editor);
		tblTask.getColumn("checked").getStyleAttributes().setHided(true);
		for(int i=0;i<tblTask.getColumnCount();i++){
			tblTask.getColumn(i).getStyleAttributes().setLocked(true);
		}
	}

	private void unChecked(String id) {
		int rowCount = tblTask.getRowCount();
		// 如果是取消勾选，则要判断是不是所有子任务都取消才把上级设置成不勾选
		for (int i = 0; i < rowCount; i++) {
			RESchTemplateTaskInfo task = (RESchTemplateTaskInfo) tblTask.getRow(i).getUserObject();
			if (task.getId().toString().equals(id)) {
				continue;
			} else {
				tblTask.getCell(i, "checked").setValue(false);
			}
		}
	}
	
	private void tblTask_clicked(KDTMouseEvent e) {
		if(e.getClickCount()==2){
			checkSelected();
			isCancel = false;
			IRow row = FDCTableHelper.getSelectedRow(tblTask);
			if (row == null || row.getUserObject() == null) {
				FDCMsgBox.showWarning(this, "请选择行");
				SysUtil.abort();
			}
			this.getUIContext().put("selectedValue", row.getUserObject());
			this.disposeUIWindow();
		}
	}

	public void actionSelectAll_actionPerformed(ActionEvent e) throws Exception {
		checkedTable(true);
	}

	public void actionClearAll_actionPerformed(ActionEvent e) throws Exception {
		checkedTable(false);
	}

	// 专项中需要导入主项计划模板，如果继续用以前的ID,保存时会报主键重复
	private void processCols(RESchTemplateTaskCollection cols) {
		int size = cols.size();
		RESchTemplateTaskInfo task = null;
		BOSUuid oldId = null;
		Map idMap = new HashMap();
		// 替换ID,前置任务最后做一次循环来处理
		for (int i = 0; i < size; i++) {
			task = cols.get(i);
			oldId = task.getId();
			task.put("oldid", oldId);
			task.setId(BOSUuid.create(task.getBOSType()));
			idMap.put(oldId, task.getId());
			// 处理业务类型
			if (task.getBusinessType() != null && task.getBusinessType().size() > 0) {
				RESchTemplateTaskBizTypeCollection bizs = task.getBusinessType();
				RESchTemplateTaskBizTypeInfo bizInfo = null;
				for (int j = 0; j < bizs.size(); j++) {
					bizInfo = bizs.get(j);
					bizInfo.setId(BOSUuid.create(bizInfo.getBOSType()));
					bizInfo.setParent(task);
				}
			}
			if (task.getParent() != null) {
				if (idMap.containsKey(task.getParent().getId())) {
					RESchTemplateTaskInfo pTask = task.getParent();
					pTask.setId(BOSUuid.read(idMap.get(task.getParent().getId()).toString()));
					task.setParent(pTask);
				} else {
					task.setParent(null);
				}
			}
		}

		RESchTemplateTaskPredecessorCollection preTasks = null;
		RESchTemplateTaskPredecessorInfo info = null;
		for (int i = 0; i < size; i++) {
			task = cols.get(i);
			if (task.getPredecessors() == null || task.getPredecessors().isEmpty()) {
				continue;
			}
			preTasks = task.getPredecessors();
			for (int j = preTasks.size(); j > 0; j--) {
				info = preTasks.get(j - 1);
				if (info.getPredecessorTask() != null) {
					RESchTemplateTaskInfo dependTask = info.getPredecessorTask();
					if (idMap.containsKey(dependTask.getId())) {
						dependTask.setId(BOSUuid.read(idMap.get(dependTask.getId()).toString()));
					} else {
						preTasks.remove(info);
					}
				}
			}

		}
	}

	private void checkedTable(boolean selected) {
		IRow row = null;
		for (int i = 0; i < tblTask.getRowCount(); i++) {
			row = tblTask.getRow(i);
			row.getCell("checked").setValue(selected);
		}
	}

	public void actionQuit_actionPerformed(ActionEvent e) throws Exception {
		disposeUIWindow();
	}

	protected void combTemplate_itemStateChanged(ItemEvent e) throws Exception {

//		RESchTemplateInfo info = (RESchTemplateInfo) e.getItem();
//		RESchTemplateTaskCollection cols = info.getEntry();
//		this.tblTask.removeRows(false);
//		this.tblTask.checkParsed();
//		fillTable(cols);

	}

	private void fillTable(RESchTemplateTaskCollection cols) {
		IRow row = null;
		for (int i = 0; i < cols.size(); i++) {
			row = tblTask.addRow();
			fillDataToTable(row, cols.get(i));
		}

	}

	protected void prmtCatagory_dataChanged(DataChangeEvent e) throws Exception {
//		if (e.getNewValue() != null) {
//			RESchTemplateCatagoryInfo info = null;
//			Set catagorys = new HashSet();
//			if (e.getNewValue() instanceof Object[]) {
//				Object[] objs = (Object[]) e.getNewValue();
//				for (int i = 0; i < objs.length; i++) {
//					if (objs[i] != null) {
//						info = (RESchTemplateCatagoryInfo) objs[i];
//						catagorys.add(info.getId().toString());
//					} else {
//						return;
//					}
//				}
//			} else if (e.getNewValue() instanceof RESchTemplateCatagoryInfo) {
//				info = (RESchTemplateCatagoryInfo) e.getNewValue();
//			}
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			//			filter.getFilterItems().add(gettemplatetypeFilterItem());
//			if (catagorys.isEmpty()) {
//				catagorys.add(info.getId().toString());
//			}
//
//			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
//			filter.getFilterItems().add(new FilterItemInfo("catagory.id", catagorys, CompareType.INCLUDE));
//			filter.getFilterItems().add(
//					new FilterItemInfo("orgUnit.longNumber", SysContext.getSysContext().getCurrentFIUnit().getLongNumber() + "%",
//							CompareType.LIKE));
//
//			view.setFilter(filter);
//
//			SelectorItemCollection sic = new SelectorItemCollection();
//			sic.add("id");
//			sic.add("name");
//			sic.add("number");
//			sic.add("state");
//			sic.add("templateType");
//			sic.add("entry.id");
//			sic.add("entry.taskType");
//			sic.add("entry.name");
//			sic.add("entry.number");
//			sic.add("entry.longNumber");
//			sic.add("entry.referenceday");
//			sic.add("entry.isLeaf");
//			sic.add("entry.level");
//			sic.add("entry.parent.id");
//			sic.add("entry.parent.name");
//			sic.add("entry.parent.number");
//			sic.add("entry.parent.level");
//			sic.add("entry.parent.isLeaf");
//			sic.add("entry.standardTask.id");
//			sic.add("entry.standardTask.name");
//			sic.add("entry.standardTask.number");
//			sic.add("entry.seq");
//
//			sic.add("entry.achievementType.id");
//			sic.add("entry.achievementType.name");
//			sic.add("entry.achievementType.number");
//
//			sic.add("entry.businessType.id");
//			sic.add("entry.businessType.bizType.id");
//			sic.add("entry.businessType.bizType.number");
//			sic.add("entry.businessType.bizType.name");
//
//			sic.add("entry.predecessors.id");
//			sic.add("entry.predecessors.predecessorTask.id");
//			sic.add("entry.predecessors.predecessorTask.name");
//			sic.add("entry.predecessors.predecessorTask.number");
//			sic.add("entry.predecessors.differenceDay");
//			sic.add("entry.predecessors.predecessorType");
//
//			view.setSelector(sic);
//
//			SorterItemCollection sorter = new SorterItemCollection();
//			SorterItemInfo si = new SorterItemInfo("entry.seq");
//			si.setSortType(SortType.ASCEND);
//			sorter.add(si);
//			view.setSorter(sorter);
//
//			RESchTemplateCollection cols = RESchTemplateFactory.getRemoteInstance().getRESchTemplateCollection(view);
//			/**
//			 * 过滤业务类型为现金流的模板任务，以后可能会用到，保留
//			 */
//		/*	for (int i = 0; i < cols.size(); i++) {
//				RESchTemplateInfo templateInfo = cols.get(i);
//				RESchTemplateTaskCollection entry = templateInfo.getEntry();
//				for (int j = entry.size() - 1; j >= 0; j--) {
//					RESchTemplateTaskInfo taskInfo = entry.get(j);
//					boolean isHasCash = false;
//					if (taskInfo.getRESchBusinessType() != null) {
//						RESchTemplateTaskBizTypeCollection bizTypes = taskInfo.getRESchBusinessType();
//						RESchTemplateTaskBizTypeCollection businessType = taskInfo.getBusinessType();
//						RESchTemplateTaskBizTypeInfo bizType = null;
//						int bizTypeSize = bizTypes.size();
//						for (int k = 0; k < bizTypeSize; k++) {
//							bizType = bizTypes.get(k);
//							if (bizType.getBizType().getId().toString().equals(BIZ_TYPE_CASHFLOW)) {
//								isHasCash = true;
//							}
//						}
//					}
//					if (!isHasCash) {
//						entry.remove(taskInfo);
//					}
//				}
//
//			}*/
//
//			fillTemplate(cols);
//
//		}

	}

	public FilterItemInfo gettemplatetypeFilterItem() {
		// TODO Auto-generated method stub
		return new FilterItemInfo("templateType", ScheduleTemplateTypeEnum.MainPlanTemplate);
	}

	private void fillTemplate(RESchTemplateCollection cols) {
		this.combTemplate.removeAllItems();
		this.tblTask.removeRows(false);
		for (int i = 0; i < cols.size(); i++) {
			this.combTemplate.addItem(cols.get(i));
		}

	}

	private void fillDataToTable(IRow row, RESchTemplateTaskInfo task) {
		row.getCell("checked").setValue(false);
		// row.getCell("id").setValue(task.getId() == null ? null :
		// task.getId());
		String value = task.getName() == null ? "" : task.getName();
		CellTreeNode treeNode = new CellTreeNode();
		treeNode.setValue(value);
		treeNode.setTreeLevel(task.getLevel());
		treeNode.setHasChildren(!task.isIsLeaf());
		treeNode.setCollapse(false);
		treeNode.addClickListener(new NodeClickListener() {
			public void doClick(CellTreeNode celltreenode, ICell icell, int i) {
				tblTask.revalidate();
			}
		});
		row.getCell("taskName").setValue(treeNode);
		RESchTemplateTaskPredecessorCollection preTasks = task.getPredecessors();
		processPredecssor(preTasks, row);
		task.setSeq(row.getRowIndex() + 1);
		row.setUserObject(task);
		if (task.getRESchBusinessType() != null) {
			RESchTemplateTaskBizTypeCollection bizTypes = task.getRESchBusinessType();
			StringBuffer bizTypeDesc = new StringBuffer();
			RESchTemplateTaskBizTypeInfo bizType = null;
			int bizTypeSize = bizTypes.size();
			for (int i = 0; i < bizTypeSize; i++) {
				bizType = bizTypes.get(i);
				if (bizType.getBizType() != null && bizType.getBizType().getName() != null) {
					bizTypeDesc.append(bizType.getBizType().getName());
					if (i < bizTypeSize - 1) {
						bizTypeDesc.append(";");
					}
				}
			}
			row.getCell("bizType").setValue(bizTypeDesc.toString());
		}
		if (!task.isIsLeaf()) {
			row.getStyleAttributes().setBackground(new Color(210, 227, 202));
		}
		row.getCell("taskType").setValue(task.getTaskType() == null ? null : task.getTaskType());
		row.getCell("duration").setValue(task.getReferenceDay());
		row.getCell("id").setValue(task.getId());
		preTasksMap.put(task.getId().toString(), task.getSeq());
		if (alreadySelect != null && !alreadySelect.isEmpty() && task.get("oldid") != null && alreadySelect.contains(task.get("oldid"))) {
			row.getCell("checked").setValue(true);
		}

	}

	private void processPredecssor(RESchTemplateTaskPredecessorCollection preTasks, IRow row) {
		int size = preTasks.size();
		RESchTemplateTaskPredecessorInfo preTask = null;
		StringBuffer desc = new StringBuffer();
		for (int i = 0; i < size; i++) {
			preTask = preTasks.get(i);
			TaskLinkTypeEnum type = preTask.getPredecessorType();
			int diff = preTask.getDifferenceDay();
			RESchTemplateTaskInfo task = preTask.getPredecessorTask();
			if (preTasksMap.containsKey(task.getId().toString())) {
				int seq = Integer.parseInt(preTasksMap.get(task.getId().toString()) + "");
				desc.append(seq);
				if (type.equals(TaskLinkTypeEnum.FINISH_FINISH)) {
					desc.append("FF");
				} else if (type.equals(TaskLinkTypeEnum.FINISH_START)) {
					if (diff == 0) {
						if (i < size - 1) {
							desc.append(",");
						}
						continue;
					}
					desc.append("FS");

				} else if (type.equals(TaskLinkTypeEnum.START_START)) {
					desc.append("SS");
				}
				if (diff > 0) {
					desc.append("+");
					desc.append(diff);
				}
			} else {
				needProcessRow.add(row.getRowIndex());
			}
			if (i < size - 1) {
				desc.append(",");
			}
		}

		if (desc.length() > 0) {
			row.getCell("preTask").setValue(desc.toString());
		}

	}

	protected void prmtCatagory_willShow(SelectorEvent e) throws Exception {

	}

}