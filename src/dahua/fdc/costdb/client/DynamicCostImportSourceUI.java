/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.event.TreeNodeStateChangeEvent;
import com.kingdee.bos.ctrl.swing.event.TreeNodeStateChangeListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotFactory;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotSettEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotSettEntryInfo;
import com.kingdee.eas.fdc.aimcost.IDynCostSnapShot;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.costdb.DBDynCostSnapShotCollection;
import com.kingdee.eas.fdc.costdb.DBDynCostSnapShotFactory;
import com.kingdee.eas.fdc.costdb.DBDynCostSnapShotInfo;
import com.kingdee.eas.fdc.costdb.DBDynCostSnapShotProTypEntryCollection;
import com.kingdee.eas.fdc.costdb.DBDynCostSnapShotProTypEntryInfo;
import com.kingdee.eas.fdc.costdb.DBDynCostSnapShotSettEntryCollection;
import com.kingdee.eas.fdc.costdb.DBDynCostSnapShotSettEntryInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:动态成本导入选择
 * 
 * @author jackwang date:2006-12-5
 *         <p>
 * @version EAS5.2
 */
public class DynamicCostImportSourceUI extends AbstractDynamicCostImportSourceUI {
	FilterInfo newFilterInfo = new FilterInfo();

	private static final Logger logger = CoreUIObject.getLogger(DynamicCostImportSourceUI.class);

	/**
	 * output class constructor
	 */
	public DynamicCostImportSourceUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		buildProjectTree();
		loadAccountTableData();
		this.chkAutoSelectAllSub.setSelected(true);
	}

	private void buildProjectTree() throws Exception {
		treeSelect.setShowCheckBox(true);
		// treeSelect.gete.setExpandsSelectedPaths(true);
		treeSelect.addTreeNodeStateChangeListener(new TreeNodeStateChangeListener() {
			public void nodeStateChange(TreeNodeStateChangeEvent event) {
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeSelect.getLastSelectedPathComponent();
				changeSelectSub(node);
				changeSelectParent(node);
				// treeSelect.lostFocus(event,this);
				selectBtnSave();
			}
		});// addTreeNodeStateChangeListener
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false, false, true);
		treeSelect.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeSelect, actionOnLoad);
	}

	public void selectBtnSave() {
		this.chkAutoSelectAllSub.nextFocus();
	}

	private void changeSelectSub(DefaultKingdeeTreeNode node) {
		DefaultKingdeeTreeNode treeNode = null;
		if (node.isChecked()) {
			if (node.getChildCount() == 0) {// 没有下级

			} else {// 有下级
				for (int i = 0; i < node.getChildCount(); i++) {
					treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
					if (treeNode.getUserObject() instanceof ProductTypeInfo) {
						if (this.chkAutoSelectAllSub.isSelected()) {
							treeNode.setChecked(true);
						}
					} else {
						treeNode.setChecked(true);
					}
					changeSelectSub(treeNode);
				}
			}
		} else {
			if (node.getChildCount() == 0) {// 没有下级

			} else {// 有下级
				for (int i = 0; i < node.getChildCount(); i++) {
					treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
					treeNode.setChecked(false);
					changeSelectSub(treeNode);
				}
			}
		}
	}

	private void changeSelectParent(DefaultKingdeeTreeNode node) {
		DefaultKingdeeTreeNode treeNode = null;
		if (node.isChecked()) {// 如果选中,上级也要选中,因为"选中下级组织或工程项目，则该组织或工程项目所有上级节点均选中"
			if (node.getParent() != null) {
				treeNode = (DefaultKingdeeTreeNode) node.getParent();
				treeNode.setChecked(true);
				changeSelectParent(treeNode);
			}
		} else {
			// 如果不选,其上级是否不选要看其下级是否选中,如果下级不选,那么上级也就不必选了,因为"?选中上级组织或工程项目，下级的组织或工程项目自动选中"
			if (node.getChildCount() != 0) {
				treeNode = (DefaultKingdeeTreeNode) node.getChildAt(0);
//				if (!treeNode.isChecked()) {
//					if (node.getParent() != null) {
//						treeNode = (DefaultKingdeeTreeNode) node.getParent();
//						treeNode.setChecked(false);
//						changeSelectParent(treeNode);
//					}
//				}
				//自己不选了，上级是否取消要看同级有没有其他勾选的
				if (node.getParent() != null) {
					boolean flag = false;
					treeNode = (DefaultKingdeeTreeNode) node.getParent();
					for(int i = 0;i<treeNode.getChildCount();i++){
						if(((DefaultKingdeeTreeNode)treeNode.getChildAt(i)).isChecked()){
							flag = true;
							break;
						}
					}
					treeNode.setChecked(flag);
					changeSelectParent(treeNode);
				}
				
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
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
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
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
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
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	// 初始化下拉列表。
	public void loadAccountTableData() throws Exception {
		// DefaultComboBoxModel model = new DefaultComboBoxModel();
		// // IDynCostSnapShot iDynCostSnapShot =
		// DynCostSnapShotFactory.getRemoteInstance();
		// IDynCostSnapShot iDynCostSnapShot =
		// DynCostSnapShotFactory.getRemoteInstance();
		// EntityViewInfo evi = new EntityViewInfo();
		// evi.getSelector().add(new SelectorItemInfo("createTime"));
		// SorterItemInfo sorter = new SorterItemInfo("createTime");
		// sorter.setSortType(SortType.DESCEND);
		// evi.getSorter().add(sorter);
		//
		// // DynCostSnapShotCollection dcssc =
		// iDynCostSnapShot.getDynCostSnapShotCollection(evi);
		// DynCostSnapShotCollection dcssc =
		// iDynCostSnapShot.getDynCostSnapShotCollection(evi);
		// Iterator ter = dcssc.iterator();
		// ArrayList al = new ArrayList();
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		IDynCostSnapShot iDynCostSnapShot = DynCostSnapShotFactory.getRemoteInstance();
		// ICostAccount iDynCostSnapShot =
		// CostAccountFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		evi.getSelector().add(new SelectorItemInfo("createTime"));
		SorterItemInfo sorter = new SorterItemInfo("createTime");
		sorter.setSortType(SortType.DESCEND);
		evi.getSorter().add(sorter);

		DynCostSnapShotCollection dcssc = iDynCostSnapShot.getDynCostSnapShotCollection(evi);
		// CostAccountCollection dcssc =
		// iDynCostSnapShot.getCostAccountCollection(evi);
		Iterator ter = dcssc.iterator();
		ArrayList al = new ArrayList();
		Iterator date;
		String str;
		while (ter.hasNext()) {
			// DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Object o = ((DynCostSnapShotInfo) ter.next()).getCreateTime();
			// model.addElement(o.toString().substring(0,10));
			str = o.toString().substring(0, 10);
			if (!al.contains(str)) {
				al.add(str);
				model.addElement(str);
			}
		}
		this.kdcSaveDate.setModel(model);
		if(kdcSaveDate.getItemCount()!=0){
			Object firstItem = this.kdcSaveDate.getItemAt(0);
			String firstStr = firstItem.toString();
	
			Calendar worldTour;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date d;
			worldTour = Calendar.getInstance();
			// worldTour.add(Calendar.DATE, -1);
			d = worldTour.getTime();
			worldTour.setTime(d);
			String todayStr = df.format(d);
	
			if (!todayStr.equals(firstStr)) {
				// this.showMessage()//提示用户，当前日期的动态成本还未保存，请保存全项目和各产品动态成本。
				MsgBox.showInfo(EASResource.getString("com.kingdee.eas.fdc.costdb.CostDBResource", "Import_DynamicCost_HasNoToday"));
				SysUtil.abort();
	
			}
		}else{
			MsgBox.showInfo(EASResource.getString("com.kingdee.eas.fdc.costdb.CostDBResource", "Import_DynamicCost_HasNoToday"));
			SysUtil.abort();
		}

		// /////////////////
		// 定标时间FDecideData,可以为空,注意时间格式校验
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// Object o = ((DataToken) hsData.get("FDecideDate")).data;
		// if (o != null && o.toString().length() > 0) {
		// try {
		// o = df.parse(o.toString());
		// } catch (ParseException pex) {
		// throw new TaskExternalException(getResource(ctx,
		// "Import_FdcMaterial_DecideDataFormatError"));
		// }
		// if (o != null && o instanceof Date) {
		// Date d = (Date) o;
		// if (d != null) {
		// info.setDecideDate(d);
		// }
		// }
		// }
	}

	protected void kdcSaveDate_itemStateChanged(ItemEvent e) throws Exception {
		super.kdcSaveDate_itemStateChanged(e);
		newFilterInfo = new FilterInfo();
		if (e.getStateChange() == ItemEvent.SELECTED) {
			// accountTableChanged(e);
			Calendar worldTour = Calendar.getInstance();
			Date d = worldTour.getTime();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Object o = this.kdcSaveDate.getSelectedItem();
			if (o != null && o.toString().length() > 0) {
				o = df.parse(o.toString());
				if (o != null && o instanceof Date) {
					d = (Date) o;
				}
			}
			// worldTour.add(Calendar.DATE, +1);
			// d = worldTour.getTime();
			worldTour.setTime(d);

			String selectDate = df.format(d);
			selectDate = selectDate + " 00:00:00";
			Date dateBreakStart;
			dateBreakStart = df.parse(selectDate);
			FilterItemInfo filterItemInfo1;
			filterItemInfo1 = new FilterItemInfo("createTime", dateBreakStart, CompareType.getEnum(">="));
			newFilterInfo.getFilterItems().add(filterItemInfo1);

			String tomorrowtomorrow = selectDate + " 23:59:59";

			Date dateDayEnd;

			dateDayEnd = df.parse(tomorrowtomorrow);

			dateDayEnd = d;
			FilterItemInfo filterItemInfo2;
			filterItemInfo2 = new FilterItemInfo("createTime", dateDayEnd, CompareType.getEnum("<"));

			newFilterInfo.getFilterItems().add(filterItemInfo2);

			// sbFormula.append(leftBracket).append("
			// ").append("#").append(String.valueOf(newFilterInfo.getFilterItems().size()
			// - 2)).append(" ").append("AND").append(" ").append("#")
			// .append(String.valueOf(newFilterInfo.getFilterItems().size() -
			// 1)).append(" ").append(rightBracket).append("
			// ").append(logic).append(" ");
			//        
			newFilterInfo.getFilterItems();
		}
	}

	/**
	 * output actionSave_actionPerformed method
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		Date d = null;	
		TreeNode root = (TreeNode) treeSelect.getModel().getRoot();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) root;		
		HashSet projectHs = new HashSet();	
		if(node.isChecked()){		
			if(node.getUserObject() instanceof CurProjectInfo){
				String id = ((CurProjectInfo) node.getUserObject()).getId().toString();
				projectHs.add(id);
			}
		}
		getProjectIdSet(projectHs,node);// 选中的工程项目		
		
		
		HashSet productTypeHs = new HashSet();
		HashMap hm = new HashMap();
		getProductTypeIdSet(hm,productTypeHs,node);// 选中的组织		
		IDynCostSnapShot iDynCostSnapShot = DynCostSnapShotFactory.getRemoteInstance();
		DynCostSnapShotCollection scssc = null;
		if (projectHs != null && projectHs.size() != 0) {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			//加上指定的日期过滤
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Object o = this.kdcSaveDate.getSelectedItem();					
			if (o != null && o.toString().length() > 0) {				
					o = df.parse(o.toString());				
				if (o != null && o instanceof Date) {
					d = (Date) o;					
				}
			}
			if (d != null) {
				filter.getFilterItems().add(new FilterItemInfo("snapShotDate", d, CompareType.EQUALS));
			}		
			filter.getFilterItems().add(new FilterItemInfo("projectId", projectHs, CompareType.INCLUDE));
			evi.setFilter(filter);
			evi.getSelector().add(new SelectorItemInfo("*"));//			
			evi.getSelector().add(new SelectorItemInfo("settEntries.*"));//			
			evi.getSelector().add(new SelectorItemInfo("settEntries.changeType.*"));
			evi.getSelector().add(new SelectorItemInfo("proTypEntries.*"));	//				
			evi.getSelector().add(new SelectorItemInfo("proTypEntries.productType.*"));	
			scssc = iDynCostSnapShot.getDynCostSnapShotCollection(evi);
		}
		if (scssc != null && scssc.size() != 0) {// 获取了数据,下面需要复制,复制过程中根据产品类型productTypeHs再过滤一次
			//head
			DBDynCostSnapShotInfo info = null;
			DynCostSnapShotInfo oldInfo = null;
			//结算
			DBDynCostSnapShotSettEntryInfo setEntryInfo = null;
			DynCostSnapShotSettEntryInfo oldSetEntryInfo = null;
			DBDynCostSnapShotSettEntryCollection setEntryColl = null;
			DynCostSnapShotSettEntryCollection oldSetEntryColl = null;
			//产品
			DBDynCostSnapShotProTypEntryInfo proTypEntryInfo = null;
			DynCostSnapShotProTypEntryInfo oldProTypEntryInfo = null;
			DBDynCostSnapShotProTypEntryCollection proTypEntryColl = null;
			DynCostSnapShotProTypEntryCollection oldProTypEntryColl = null;
			CostAccountInfo cai = null;
			CurProjectInfo cpi = null;
			ApportionTypeInfo ati = null; 
			for (int i = 0; i < scssc.size(); i++) {
				// head
				oldInfo = scssc.get(i);
//				info = new DBDynCostSnapShotInfo();
				if (oldInfo == null)
					continue;
				info = new DBDynCostSnapShotInfo();
				cpi = new CurProjectInfo();
				cpi.setId(oldInfo.getProjectId());
				info.setProject(cpi);
				cai = new CostAccountInfo();
				cai.setId(oldInfo.getCostAccountId());
				info.setCostAccount(cai);
				info.setUnSettSignAmt(oldInfo.getUnSettSignAmt());
				info.setSettSignAmt(oldInfo.getSettSignAmt());
				info.setSettAdjustAmt(oldInfo.getSettAdjustAmt());
				info.setUnContractCostAmt(oldInfo.getUnContractCostAmt());
				info.setSoFarHasAmt(oldInfo.getSoFarHasAmt());
				info.setSoFarToAmt(oldInfo.getSoFarToAmt());
				info.setDynCostAmt(oldInfo.getDynCostAmt());
				info.setAimCostAmt(oldInfo.getAimCostAmt());
				info.setDiffAmt(oldInfo.getDiffAmt());
				info.setSalableAmt(oldInfo.getSalableAmt());
				info.setConstrAmt(oldInfo.getConstrAmt());
				ati = new ApportionTypeInfo();
				ati.setId(oldInfo.getApprotionTypeId());
				info.setApprotionType(ati);
				info.setSnapShotDate(oldInfo.getSnapShotDate());
				

				// 结算分录
				oldSetEntryColl = oldInfo.getSettEntries();
				setEntryColl = new DBDynCostSnapShotSettEntryCollection();
				ChangeTypeInfo cti = null;
				for (int j = 0; j < oldSetEntryColl.size(); j++) {
					setEntryInfo = new DBDynCostSnapShotSettEntryInfo();
					oldSetEntryInfo = oldSetEntryColl.get(j);
					cti = new ChangeTypeInfo();
					cti.setId(oldSetEntryInfo.getChangeTypeId());
					setEntryInfo.setChangeType(cti);
					setEntryInfo.setUnSettleAmt(oldSetEntryInfo.getUnSettleAmt());
					setEntryInfo.setSettleAmt(oldSetEntryInfo.getSettleAmt());
					setEntryInfo.setParent(info);
					setEntryColl.add(setEntryInfo);
				}

				info.getSettEntries().addCollection(setEntryColl);///设置结算分录
				//产品分录
				ArrayList al = (ArrayList)(hm.get(info.getProject().getId().toString()));
				oldProTypEntryColl = oldInfo.getProTypEntries();
				proTypEntryColl = new DBDynCostSnapShotProTypEntryCollection();
				ProductTypeInfo pti = null;
				for(int k = 0;k<oldProTypEntryColl.size();k++){
					proTypEntryInfo = new DBDynCostSnapShotProTypEntryInfo();
					oldProTypEntryInfo = oldProTypEntryColl.get(k);
					if(al==null||al.size()==0||!al.contains(oldProTypEntryInfo.getProductTypeId().toString()))
						continue;
					proTypEntryInfo.setAimCostAmt(oldProTypEntryInfo.getAimCostAmt());
					proTypEntryInfo.setAimSaleUnitAmt(oldProTypEntryInfo.getAimSaleUnitAmt());
					pti = new ProductTypeInfo();
					pti.setId(oldProTypEntryInfo.getProductTypeId());
					proTypEntryInfo.setProductType(pti);
					proTypEntryInfo.setHasHappenAmt(oldProTypEntryInfo.getHasHappenAmt());
					proTypEntryInfo.setNotHappenAmt(oldProTypEntryInfo.getNotHappenAmt());
					proTypEntryInfo.setDynCostAmt(oldProTypEntryInfo.getDynCostAmt());
					proTypEntryInfo.setDynSaleUnitAmt(oldProTypEntryInfo.getDynSaleUnitAmt());					
					proTypEntryInfo.setParent(info);
					proTypEntryColl.add(proTypEntryInfo);
				}
				info.getProTypEntries().addCollection(proTypEntryColl);///设置结算分录			
				//保存
				EntityViewInfo evi = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();				
				if (d != null) {
					filter.getFilterItems().add(new FilterItemInfo("snapShotDate", d, CompareType.EQUALS));//加上指定的日期过滤
				}
                filter.getFilterItems().add(new FilterItemInfo("project.id",info.getProject().getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("costAccount.id",info.getCostAccount().getId().toString()));
				evi.setFilter(filter);
				DBDynCostSnapShotCollection dbdcssc = DBDynCostSnapShotFactory.getRemoteInstance().getDBDynCostSnapShotCollection(evi);
				if(dbdcssc.size()!=0){
					info.setId(dbdcssc.get(0).getId());
					DBDynCostSnapShotFactory.getRemoteInstance().update(new ObjectUuidPK(dbdcssc.get(0).getId().toString()),info);
				}else{
					DBDynCostSnapShotFactory.getRemoteInstance().addnew(info);
				}		
			}
		}
		FDCClientUtils.showOprtOK(this);
	}
	private FilterInfo getToDayFilter(){
		FilterInfo filter = new FilterInfo();
		//
        Calendar worldTour;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        worldTour = Calendar.getInstance();
        worldTour.add(Calendar.DATE, 0);
        d = worldTour.getTime();
        worldTour.setTime(d);

        String today = df.format(d);
        today = today + " 00:00:00";

        Date dateBreakStart;
        try {
            dateBreakStart = df.parse(today);
            dateBreakStart = getDate((Date) dateBreakStart);
            FilterItemInfo filterItemInfo1;
            filterItemInfo1 = new FilterItemInfo("createTime", dateBreakStart, CompareType.getEnum(">="));
            filter.getFilterItems().add(filterItemInfo1);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        worldTour = Calendar.getInstance();
        worldTour.add(Calendar.DATE, +1);
        d = worldTour.getTime();
        worldTour.setTime(d);
        String tomorrow = df.format(d);
        tomorrow = tomorrow + " 00:00:00";
        Date dateDayEnd;
        try {
            dateDayEnd = df.parse(tomorrow);

            dateDayEnd = getDate((Date) dateDayEnd);
            FilterItemInfo filterItemInfo2;
            filterItemInfo2 = new FilterItemInfo("createTime", dateDayEnd, CompareType.getEnum("<"));

            filter.getFilterItems().add(filterItemInfo2);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return filter;
	}

	/**
	 * 根据日期格式类型获得相应的format
	 * @param dateType
	 * @return
	 */
	private  Date getDate(Date date)
	{
		Date initDate = date;
        Calendar cal = new GregorianCalendar();
		cal.setTime(date);      
        initDate = new Timestamp(cal.getTime().getTime());        
		return initDate;
	}

	/**
	 * output actionCancel_actionPerformed method
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		this.getUIWindow().close();
	}

	/**
	 * 
	 * 描述：递归获取选中的产品类型集合
	 * 
	 * @param node
	 * @param leafNodesIdSet
	 * @author:jackwang
	 *            <p>
	 */
	public void getProductTypeIdSet(HashMap hm,HashSet productTypeSet, DefaultKingdeeTreeNode node) {
		
		
		if (node == null) {
			return ;
		}
		int count = node.getChildCount();
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			if (treeNode.getUserObject() instanceof ProductTypeInfo) {				
				if (treeNode.isChecked()) {
					String projectKey = null;
					ArrayList al = null;
					if(treeNode.getParent()!=null){
						 projectKey = ((CurProjectInfo)(((DefaultKingdeeTreeNode)treeNode.getParent()).getUserObject())).getId().toString(); 
					}
//					if (treeNode.isLeaf()) {
						String id = ((ProductTypeInfo) treeNode.getUserObject()).getId().toString();
						productTypeSet.add(id);
						if(hm.get(projectKey)!=null){
							al = (ArrayList)hm.get(projectKey);
							al.add(id);
						}else{
							al = new ArrayList();
							al.add(id);
						}
						hm.put(projectKey,al);
						
//					}
				}
			}else{
				getProductTypeIdSet(hm,productTypeSet,treeNode);
			}
		}		
	}

	/**
	 * 
	 * 描述：递归获取选中的工程项目集合
	 * 
	 * @param node
	 * @param leafNodesIdSet
	 * @author:jackwang
	 *            <p>
	 */
	public void getProjectIdSet(HashSet projectSet,DefaultKingdeeTreeNode node) {
		
		if (node == null) {
			return ;
		}
		
		int count = node.getChildCount();
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			if (treeNode.getUserObject() instanceof CurProjectInfo) {
				if (treeNode.isChecked()) {
//					if (treeNode.isLeaf()) {
						String id = ((CurProjectInfo) treeNode.getUserObject()).getId().toString();
						projectSet.add(id);
						getProjectIdSet(projectSet,treeNode);
//					}
				}
			}else{
				getProjectIdSet(projectSet,treeNode);
			}
		}
	}

	/**
	 * output treeSelect_valueChanged method
	 */
	protected void treeSelect_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		// DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)
		// treeSelect.getLastSelectedPathComponent();
		// if (node == null) {
		// return;
		// }
		// if (OrgViewUtils.isTreeNodeDisable(node)) {
		// return;
		// }
		// DefaultKingdeeTreeNode treeNode = null;
		// if (node.getUserObject() instanceof CurProjectInfo) {
		// if(node.isChecked()){
		// if (node.getChildCount() == 0) {// 没有下级
		//					
		// }else{//有下级
		// for(int i = 0;i<node.getChildCount();i++){
		// treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
		// if(treeNode.getUserObject() instanceof ProductTypeInfo){
		// if(this.chkAutoSelectAllSub.isSelected()){
		// treeNode.setChecked(true);
		// }
		// }else{
		// treeNode.setChecked(true);
		// }
		// }
		// }
		// }else{
		// if (node.getChildCount() == 0) {// 没有下级
		//					
		// }else{//有下级
		// for(int i = 0;i<node.getChildCount();i++){
		// treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
		// treeNode.setChecked(false);
		// }
		// }
		// }
		// }else if (node.getUserObject() instanceof OrgStructureInfo) {
		//			
		// }
	}

	/**
	 * output treeSelect_mouseClicked method
	 */
	protected void treeSelect_mouseClicked(java.awt.event.MouseEvent e) throws Exception {
		// DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)
		// treeSelect.getLastSelectedPathComponent();
		// if (node == null) {
		// return;
		// }
		// if (OrgViewUtils.isTreeNodeDisable(node)) {
		// return;
		// }
		// DefaultKingdeeTreeNode treeNode = null;
		// if (node.getUserObject() instanceof CurProjectInfo) {
		// if(node.isChecked()){
		// if (node.getChildCount() == 0) {// 没有下级
		//					
		// }else{//有下级
		// for(int i = 0;i<node.getChildCount();i++){
		// treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
		// if(treeNode.getUserObject() instanceof ProductTypeInfo){
		// if(this.chkAutoSelectAllSub.isSelected()){
		// treeNode.setChecked(true);
		// }
		// }else{
		// treeNode.setChecked(true);
		// }
		// }
		// }
		// }else{
		// if (node.getChildCount() == 0) {// 没有下级
		//					
		// }else{//有下级
		// for(int i = 0;i<node.getChildCount();i++){
		// treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
		// treeNode.setChecked(false);
		// }
		// }
		// }
		// }else if (node.getUserObject() instanceof OrgStructureInfo) {
		//			
		// }
	}

	/**
	 * output treeSelect_focusLost method
	 */
	protected void treeSelect_focusLost(java.awt.event.FocusEvent e) throws Exception {
	}

	/**
	 * output chkAutoSelectAllSub_stateChanged method
	 */
	protected void chkAutoSelectAllSub_stateChanged(javax.swing.event.ChangeEvent e) throws Exception {
	}
}