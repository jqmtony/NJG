/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.finance.client.util.AddQueryFilterTool;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 进度计划任务选择f7UI
 * 
 *
 */
public class ScheduleTaskF7UI extends AbstractScheduleTaskF7UI {

	private static final Logger logger = CoreUIObject.getLogger(ScheduleTaskF7UI.class);
	
	private int selectMode = KDTSelectManager.ROW_SELECT;
	
	protected FDCScheduleInfo editData = new FDCScheduleInfo();
	

	private String projectId;
	
	private boolean isCancel;
	
	private boolean isMutilSelect = false;

	public ScheduleTaskF7UI() throws Exception {
		super();
	}

	
	public void queryData(FilterInfo filter) throws BOSException, SQLException {
		EntityViewInfo view = new EntityViewInfo();
		if(filter==null){
			filter = new FilterInfo();
		}
		FDCScheduleCollection mainSchedules = getMainSchedule(projectId, view, filter);
		if (!FDCHelper.isEmpty(mainSchedules) && mainSchedules.size() > 0) {
			 editData = mainSchedules.get(0);
			 loadSpecailTaskInMainTask(filter, mainSchedules);

			 /*
			 * 不知道这里为什么要做过滤，先过滤掉
			 */
			//			 filterBizType();
			 fillTable();
		}
	}
	
	private static final String BIZ_TYPE_CASHFLOW = "+BONIPDySL67xM7mEm9tXmLF6cA=";
	
	private void filterBizType() {


		FDCScheduleTaskCollection taskEntrys = editData.getTaskEntrys();
		if (null != taskEntrys) {
			for (int i = taskEntrys.size() - 1; i >= 0 ; i--) {
				FDCScheduleTaskInfo taskInfo = taskEntrys.get(i);
//				if (taskInfo.isIsLeaf()) {
//					continue;
//				}
				boolean isHasCash = false;
				if (taskInfo.getBizType() != null) {
					ScheduleTaskBizTypeCollection bizTypes = taskInfo.getBizType();
					ScheduleTaskBizTypeInfo bizType = null;
					int bizTypeSize = bizTypes.size();
					for (int k = 0; k < bizTypeSize; k++) {
						bizType = bizTypes.get(k);
						if (bizType.getBizType().getId().toString().equals(BIZ_TYPE_CASHFLOW)) {
							isHasCash = true;
						}
					}
				}

				if (!taskInfo.isIsLeaf()) {
					isHasCash = true;
				}

				if (!isHasCash) {
					taskEntrys.remove(taskInfo);
				}
			}
		}

		FDCScheduleTaskInfo preTaskInfo = null;
		//删除没有下级任务的上级任务
		if (null != taskEntrys) {
			for (int i = taskEntrys.size() - 1; i >= 0; i--) {
				FDCScheduleTaskInfo taskInfo = taskEntrys.get(i);
				boolean isHasCash = true;
				if (!taskInfo.isIsLeaf()) {
					if (preTaskInfo == null) {
						isHasCash = false;
					} else {
						if ((!preTaskInfo.isIsLeaf()) && preTaskInfo.getLevel() <= taskInfo.getLevel()) {
							isHasCash = false;
						}
					}
				}

				if (!isHasCash) {
					taskEntrys.remove(taskInfo);
				}
				preTaskInfo = taskInfo;
			}
		}
	}


	public void onLoad() throws Exception {
		super.onLoad();
		kDContainerTitle.setBounds(new Rectangle(11, 30, 779, 526));
		AddQueryFilterTool qF = new AddQueryFilterTool(this);
		btnOk.setEnabled(true);
		btnCancel.setEnabled(true);
		btnRefresh.setVisible(false);
	}

	public void onShow() throws Exception {
		super.onShow();
		
		queryData(null);
		
		initTable();
		
		fillTable();
	}
	
	
	private void initTable() {
		getMainTable().checkParsed();
		getMainTable().getStyleAttributes().setLocked(true);
		if(isMutilSelect){
			getMainTable().getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		}else{
			getMainTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		}
		getMainTable().getColumn("start").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		getMainTable().getColumn("end").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		getMainTable().getColumn("id").getStyleAttributes().setHided(true);
		getMainTable().getColumn("building").getStyleAttributes().setHided(true);
	}

	/**
	 * 为表填充数据
	 * @throws BOSException 
	 */
	private void fillTable() throws BOSException {
		FDCScheduleTaskCollection cols = editData.getTaskEntrys();
		getMainTable().removeRows();
		if (cols != null && cols.size() > 0) {
			for (int i = 0; i < cols.size(); i++) {
				FDCScheduleTaskInfo info = cols.get(i);
				IRow row = getMainTable().addRow();
				setRowData(info, row);

			}
			getMainTable().setRowCount(cols.size());
		}
	}


	protected void setRowData(FDCScheduleTaskInfo task, IRow row) throws BOSException {
		row.getCell("id").setValue(task.getId());
		String value = task.getName() == null ? "" : task.getName();
		CellTreeNode treeNode = new CellTreeNode();
		treeNode.setValue(value);
		treeNode.setTreeLevel(task.getLevel());
		treeNode.setHasChildren(!task.isIsLeaf());
		treeNode.setCollapse(false);
		treeNode.addClickListener(new NodeClickListener() {
			public void doClick(CellTreeNode celltreenode, ICell icell, int i) {
				tblMain.revalidate();
			}
		});
		row.getCell("taskname").setValue(treeNode);
		row.getCell("tasknumber").setValue(task.getNumber());
		row.getCell("start").setValue(task.getStart());
		row.getCell("end").setValue(task.getEnd());
		row.setUserObject(task);
		row.getStyleAttributes().setLocked(true);
		row.getCell("taskname").getStyleAttributes().setLocked(false);
		if (!task.isIsLeaf()) {
			row.getStyleAttributes().setBackground(new Color(210, 227, 202));
		}
	}


	private KDTable getMainTable() {
		return this.tblMain;
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {

		if (e.getClickCount() == 2) {
			actionBtnOk_actionPerformed(null);
		}

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


	public Object getData() {
		if(isMutilSelect){
			int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
			Object[] datas = new Object[selectRows.length];
			for (int i = 0; i < selectRows.length; i++) {
				int rowIndex = selectRows[i];
				FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) tblMain.getRow(rowIndex).getUserObject();
				datas[i] = info;
			}
			return datas;
		}else{
			 ArrayList datas = new ArrayList();
			datas.add(getUIContext().get("selectedValue"));
			return datas.toArray();
		}
	}


	// 建立左边的树
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

		tblMain.getSelectManager().setSelectMode(selectMode);

	}

	// 检查选中的数据 只有叶子节点才能被选中
	protected void checkSelected() {
		int rowsCount = tblMain.getBody().getRows().size();
		if (rowsCount == 0 || tblMain.getSelectManager().size() == 0 || tblMain.getSelectManager().getActiveRowIndex() < 0) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
		
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		
//		for (int i = 0; i < selectRows.length; i++) {
//			int rowIndex = selectRows[i];
//			FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) tblMain.getRow(rowIndex).getUserObject();
//			if (!info.isIsLeaf()) {
//				MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
//				SysUtil.abort();
//			}
//		}
	}
	
	/**
	 * 
	 * @description 嵌入相应专项任务到关联主项关联相关专项任务
	 * @author 杜红明
	 * @createDate 2011-9-28
	 * @param lowerLevelSpcailSchedule
	 *            相关专项及专项的子项任务
	 * @version EAS7.0
	 * @see
	 */
	private void putDependSpecailTask(FDCScheduleTaskCollection lowerLevelSpcailSchedule) {
		FDCScheduleTaskCollection taskEntrys = editData.getTaskEntrys();
//		if (lowerLevelSpcailSchedule != null && null != taskEntrys) {
//			for (int i = 0; i < taskEntrys.size(); i++) {
//				FDCScheduleTaskInfo scheduleTaskInfo = taskEntrys.get(i);
//				System.out.println("---------------------------"+scheduleTaskInfo.getId());
//				for (int j = 0; j < lowerLevelSpcailSchedule.size(); j++) {
//					FDCScheduleTaskInfo scheduleTaskInfo2 = lowerLevelSpcailSchedule.get(j);
//					if(scheduleTaskInfo2.getDependMainTaskID()!=null){
//						System.out.println(scheduleTaskInfo2.getDependMainTaskID().getId());
//					}
//					if (null != scheduleTaskInfo2.getSrcID() && null != scheduleTaskInfo.getSrcID()
//							&& scheduleTaskInfo.getSrcID().equals(scheduleTaskInfo2.getSrcID())) {
//						scheduleTaskInfo2.setLongNumber(scheduleTaskInfo.getLongNumber() + "!" + "121");
//						scheduleTaskInfo2.setLevel(scheduleTaskInfo.getLevel() + 1);
//						scheduleTaskInfo2.setParent(scheduleTaskInfo);
//						taskEntrys.add(scheduleTaskInfo2);
//					}
//				}
//			}
//		}
		editData.getTaskEntrys().addCollection(lowerLevelSpcailSchedule);
	}
	
	private void loadSpecailTaskInMainTask(FilterInfo filter, FDCScheduleCollection mainSchedules) throws BOSException, SQLException {
		FDCScheduleInfo mainSchedule = mainSchedules.get(0);

		// 获取到了获取到主项任务关联的专项计划ID、专项任务的longNumber
		FDCSQLBuilder builder = new FDCSQLBuilder();
		Map map = new HashMap();
		if (mainSchedule != null) {
			putSpecialScheduleData(map, mainSchedules, builder);
		}
		/** 获得相关联的专项任务的任务 **/
		
		FDCScheduleTaskCollection lowerLevelSpcailSchedule  = new FDCScheduleTaskCollection();
		for(Iterator ite = map.keySet().iterator();ite.hasNext(); ){
			String fid = (String) ite.next();
			Set longNumbers = (Set) map.get(fid);
			filter.getFilterItems().clear();
			filter = getSpecialScheduleFilter(filter, fid,longNumbers);
			if (filter.getFilterItems().size() != 0) {
				EntityViewInfo specailTaskView = new EntityViewInfo();
				SelectorItemCollection selector = specailTaskView.getSelector();
				selector = getSpecialRalateSelector(selector);
				specailTaskView.setFilter(filter);
				// 获得主项关联的相关专项任务以及下级任务集合
				lowerLevelSpcailSchedule.addCollection(FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(
						specailTaskView));
				
				
			}
		}
		/** 嵌入相应专项任务到关联主项关联相关专项任务 **/
		putDependSpecailTask(lowerLevelSpcailSchedule);
	}
	
	private SelectorItemCollection getSpecialRalateSelector(SelectorItemCollection selector) {
		selector.add("id");
		selector.add("name");
		selector.add("number");
		selector.add("longNumber");
		selector.add("level");
		selector.add("isLeaf");
		selector.add("duration");
		selector.add("schedule.id");
		selector.add("parent.*");
		selector.add("srcID");
		selector.add("dependMainTaskID.*");
		selector.add("bizType.*");
		return selector;
	}
	
	private FilterInfo getSpecialScheduleFilter(FilterInfo filter,String fid,Set longNumbers ) throws BOSException {
		int k=0;
		String mask = "";
			filter.getFilterItems().add(new FilterItemInfo("schedule", fid));
			if(k==0){
				mask="(#0 ";
			}else{
				mask = mask + " or (#"+k;
			}
			k++;
			int j =1;
			for (Iterator longNumber = longNumbers.iterator(); longNumber.hasNext();) {
				String number = (String) longNumber.next();
				filter.getFilterItems().add(new FilterItemInfo("longNumber", number + "%", CompareType.LIKE));
				
				if(j==1){
					if(j==longNumbers.size()){
						mask=mask + " and #"+k;
					}else{
						mask=mask + " and (#"+k;
					}
				}else{
					if(j==longNumbers.size()){
						mask=mask + " or #"+k+")";
					}else{
						mask=mask + " or #"+k;
					}
				}
				k++;
				j++;

			}
			mask = mask + ")";
		filter.setMaskString(mask);
		return filter;
	}
	
	private Map putSpecialScheduleData(Map map, FDCScheduleCollection mainSchedules, FDCSQLBuilder builder) throws BOSException, SQLException {
		builder.appendSql("select schedule.fid as fid,task.flongnumber as flongnumber ");
		builder.appendSql("from t_sch_fdcscheduletask as task inner join t_sch_fdcschedule schedule on schedule.fid=task.fscheduleid ");
		builder.appendSql("where schedule.fislatestver=1 and task.fdependmaintaskid in ");
		builder.appendSql("(select fsrcid from t_sch_fdcscheduletask where fscheduleid=?)");
		builder.addParam(mainSchedules.get(0).getId().toString());
		IRowSet rs = builder.executeQuery();
		Set set = null;
		while (rs.next()) {
			String key = rs.getString("fid");
			String value = rs.getString("flongnumber");
			if (!map.containsKey(key)) {
				set = new HashSet();
			}
			set.add(value);
			map.put(key, set);
		}
		return map;
	}
	
	
	private FDCScheduleCollection getMainSchedule(String rememberProjectID, EntityViewInfo view, FilterInfo filter) throws BOSException {
		SelectorItemCollection selectors = view.getSelector();
		view.getSelector().add("schedule.id");
		view.getSelector().add("id");
		selectors.add("isLeaf");
		selectors.add("level");
		selectors.add("longNumber");
		selectors.add("number");
		selectors.add("name");
		selectors.add("start");
		selectors.add("srcID");
		selectors.add("end");
		selectors.add("bizType.*");
		if(filter.getFilterItems().size()==0){
			filter.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial", null));
		}
		filter.getFilterItems().add(new FilterItemInfo("schedule.project.id", rememberProjectID));
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestver", "1"));
		view.setFilter(filter);
		
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo info = new SorterItemInfo();
		info.setPropertyName("longNumber");
		info.setSortType(SortType.ASCEND);
		sorter.add(info );
		view.setSorter(sorter );
		FDCScheduleTaskCollection mainSchedulesTask = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		FDCScheduleCollection mainSchedules = new FDCScheduleCollection();
		if(mainSchedulesTask.size()>0){
			FDCScheduleTaskInfo scheduleTaskInfo = mainSchedulesTask.get(0);
			FDCScheduleInfo schInfo = new FDCScheduleInfo();
			schInfo.setId(scheduleTaskInfo.getSchedule().getId());
			schInfo.getTaskEntrys().addCollection(mainSchedulesTask);
			mainSchedules.add(schInfo);
		}
		return mainSchedules ;
	}
	
	

	public boolean isMutilSelect() {
		return isMutilSelect;
	}

	public void setMutilSelect(boolean isMutilSelect) {
		this.isMutilSelect = isMutilSelect;
	}

	public boolean isCancel() {
		return isCancel;
	}
	
	public void setProjectId(String projectId){
		this.projectId = projectId;
	}

}