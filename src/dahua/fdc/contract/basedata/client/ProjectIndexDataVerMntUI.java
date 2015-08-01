/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.TargetTypeInfo;
import com.kingdee.eas.fdc.finance.ProductSettleBillCollection;
import com.kingdee.eas.fdc.finance.ProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.ProductSettleBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 指标修订编辑界面
 * 
 * @author liupd
 * 
 */
public class ProjectIndexDataVerMntUI extends AbstractProjectIndexDataVerMntUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ProjectIndexDataVerMntUI.class);

	/**
	 * output class constructor
	 */
	public ProjectIndexDataVerMntUI() throws Exception {
		super();
	}

	private static final String NEW_VALUE = "newValue";

	int curVerIdx = 0;

	List verList;

	public void onLoad() throws Exception {

		super.onLoad();

		getDetailTable().getColumn(APPORTION_TYPE_NAME).getStyleAttributes()
				.setLocked(true);
		getDetailTable().getColumn(APPORTION_TYPE_NUMBER).getStyleAttributes()
				.setLocked(true);
		getDetailTable().getColumn(INDEX_VALUE).getStyleAttributes().setLocked(
				true);
		getDetailTable().getColumn(MEASURE_UNIT_NAME).getStyleAttributes()
				.setLocked(true);
		getDetailTable().getColumn(DESC).getStyleAttributes().setLocked(true);

		KDTDefaultCellEditor indexValue_CellEditor = FDCClientHelper
				.getNumberCellEditor();
		getDetailTable().getColumn(NEW_VALUE).setEditor(indexValue_CellEditor);
		actionAudit.setEnabled(false);
		actionAudit.setVisible(false);
		actionUnAudit.setEnabled(false);
		actionUnAudit.setVisible(false);

		menuView.setMnemonic('v');
		
	}

	/**
	 * 
	 * 描述：初始化表格
	 * 
	 * @author:liupd 创建时间：2006-8-3
	 *               <p>
	 */
	protected void initTable() {
		FDCHelper.formatTableNumber(getDetailTable(), NEW_VALUE);
		getDetailTable().getColumn(INDEX_VALUE).getStyleAttributes()
				.setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		super.initTable();
	}

	protected void selectChange() throws Exception {

		prepareVerList();

		showThisVerData();
	}

	private void prepareVerList() throws BOSException, SQLException {
		StringBuffer sql = new StringBuffer(
				"select FVerNo from T_FDC_ProjectIndexData where");
		String proOrOrgId = ((CoreBaseInfo) getProjSelectedTreeNode()
				.getUserObject()).getId().toString();
		sql.append(" FprojOrOrgID = '").append(proOrOrgId).append("'");
		sql.append(" and FProjectStage = '").append(
				getProjectStage().getValue()).append("'");
		if (comboProductType.getSelectedItem() instanceof ProductTypeInfo) {
			String productTypeId = ((ProductTypeInfo) comboProductType
					.getSelectedItem()).getId().toString();
			sql.append(" and FproductTypeID = '").append(productTypeId).append(
					"'");
		} else {
			sql.append(" and FproductTypeID is null");
		}
		sql
				.append(" group by FVerNo order by to_number(substring(FVerNo,charIndex('V',FVerNo) + 1, len(FVerNo)))");

		IRowSet set = SQLExecutorFactory.getRemoteInstance(sql.toString())
				.executeSQL();

		verList = new ArrayList();
		while (set.next()) {
			verList.add(set.getString("FVerNo"));

		}

		if (verList.size() <= 1) {
			actionNext.setEnabled(false);
			actionFirst.setEnabled(false);
			actionLast.setEnabled(false);
			actionPre.setEnabled(false);
		} else {
			
//			curVerIdx = 0 ;
//			应试显示最后一个版本而不是第一个版本
			curVerIdx = verList.size() - 1;
			actionNext.setEnabled(false);
			actionLast.setEnabled(false);
			
			actionFirst.setEnabled(true);
			actionPre.setEnabled(true);
		}
		
	}

	public void fillTableByColl() throws Exception {
		super.fillTableByColl();

		if (getPreVer() != null) {
			ProjectIndexDataEntryCollection preColl = null;
			preColl = getReadyProjectIndexDataInfo(getPreVer()).getEntries();
			int count = getDetailTable().getRowCount();
			for (Iterator iter = preColl.iterator(); iter.hasNext();) {
				ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter
						.next();
				String appId = element.getApportionType().getId().toString();
				for (int i = 0; i < count; i++) {
					String id = (String) getDetailTable().getCell(i,
							APPORTION_TYPE_ID).getValue();
					if (id.equals(appId)) {
						getDetailTable().getCell(i, INDEX_VALUE).setValue(
								element.getIndexValue());
					}
				}

			}
		}
		selectFirstRow();

		// setTableLocked(true);
		setButtonInfo();

		notChange = true;
	}

	public void save(Map ver) throws Exception {

		final ProjectIndexDataInfo info = new ProjectIndexDataInfo();
		final ProjectIndexDataInfo oldInfo = (ProjectIndexDataInfo) getDataObject();
		info.setAuditor(oldInfo.getAuditor());
		info.setAuditTime(oldInfo.getAuditTime());
		setBuildAreaDif(oldInfo);
		
		String verNo = (String) ver.get("verNo");
		String verName = (String) ver.get("verName");
		String verRemark = (String) ver.get("verRemark");

		if(!isDisplayAreaIndex()){
			verName=oldInfo.getVerName();
		}
		info.setVerTime(new Timestamp(System.currentTimeMillis()));
		info.setVerNo(verNo);
		info.setVerName(verName);
		info.setVerRemark(verRemark);
		info.setIsLatestVer(true);

/*		BOSUuid curProjId = ((CurProjectInfo) getProjSelectedTreeNode()
				.getUserObject()).getId();*/
		info.setProjOrOrgID(oldInfo.getProjOrOrgID());

		info.setProjectStage(getProjectStage());
		
		if (comboProductType.getSelectedItem() instanceof ProductTypeInfo) {
			BOSUuid productTypeUuId = ((ProductTypeInfo) comboProductType
					.getSelectedItem()).getId();
			ProductTypeInfo productTypeInfo = new ProductTypeInfo();
			productTypeInfo.setId(productTypeUuId);
			info.setProductType(productTypeInfo);
		}

		ProjectIndexDataEntryInfo entryInfo = null;
		for (int i = 0; i < getDetailTable().getRowCount(); i++) {
			IRow row = getDetailTable().getRow(i);

			String appTypeId = (String) row.getCell(APPORTION_TYPE_ID)
					.getValue();
			BigDecimal indexValue = (BigDecimal) row.getCell(NEW_VALUE)
					.getValue();
			String tarTypeId = (String) row.getCell(TARGET_TYPE_ID).getValue();

			String remark = (String) row.getCell(REMARK).getValue();

			// 用户没有录入数据或为0或空的行不保存
			// 可为0 by sxhong
			/*
			 * if((indexValue == null || indexValue.signum() == 0) && (remark ==
			 * null || remark.length() == 0)) { continue; }
			 */
			if ((indexValue == null) && (remark == null || remark.length() == 0)) {
				continue;
			}

			ApportionTypeInfo appTypeInfo = new ApportionTypeInfo();
			appTypeInfo.setId(BOSUuid.read(appTypeId));
			entryInfo = new ProjectIndexDataEntryInfo();
			entryInfo.setApportionType(appTypeInfo);
			TargetTypeInfo tarType = new TargetTypeInfo();
			tarType.setId(BOSUuid.read(tarTypeId));
			entryInfo.setTargetType(tarType);
			entryInfo.setIndexValue(indexValue);
			entryInfo.setRemark(remark);

			info.getEntries().add(entryInfo);
		}

		String productType = info.getProductType() == null ? null : info.getProductType().getId().toString();
		ProjectIndexDataCollection projectIndexDataColl = ProjectIndexDataUtils.getProjectIndexDataCollByCond(getProjectStage(), productType, info.getProjOrOrgID().toString(), true);

		if (projectIndexDataColl.size() > 0) {
			ProjectIndexDataEntryCollection dbEntries = projectIndexDataColl.get(0).getEntries();

			Map curEntryMap = new HashMap();
			ProjectIndexDataEntryCollection curEntries = info.getEntries();
			for (Iterator iter = curEntries.iterator(); iter.hasNext();) {
				ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter.next();
				curEntryMap.put(element.getApportionType().getId().toString(), element);
			}

			for (Iterator iter = dbEntries.iterator(); iter.hasNext();) {
				ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter.next();
				String appId = element.getApportionType().getId().toString();
				if (!curEntryMap.containsKey(appId)) {
					element.setId(BOSUuid.create(element.getBOSType()));
					info.getEntries().add(element);
				}

			}
		}
		
		if (info.getEntries().size() > 0) {
			info.setState(FDCBillStateEnum.SUBMITTED);

			IObjectPK pk = ProjectIndexDataFactory.getRemoteInstance().submit(info);
			info.setId(BOSUuid.read(pk.toString()));

			// 07.1.16根据客户需求,去掉汇总功能
			// TreeNode node =
			// ProjectIndexDataUtils.getNodeById(info.getProjOrOrgID().toString());
			// ProjectIndexDataUtils.sumUp(node, productTypeId);
		}

		selectChange();
		notChange = true;

		// 产品可售面积及建筑面积汇总
		if (isSelectLeafProject() && info.getProductType() != null) {
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
				public void run() {
					try {
						sumIndexValue2(oldInfo, info);
					} catch (Exception e) {
						handUIExceptionAndAbort(e);
					}
				}
			});
		}
	}

	/**
	 * 
	 * 没有指标数据,不能修订
	 */
	private void check() throws Exception{
		if (notChange) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("notchange"));
			SysUtil.abort();
		}
		int count = getDetailTable().getRowCount();
		boolean has = false;
		for (int i = 0; i < count; i++) {
			if (getDetailTable().getCell(i, NEW_VALUE).getValue() != null) {
				has = true;
				break;
			}
		}

		if (!hasData || !has) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("noIndexData"));
			SysUtil.abort();
		}
		final ProjectIndexDataInfo info = (ProjectIndexDataInfo) getDataObject();
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("id", info.getId().toString());
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE,CompareType.NOTEQUALS));
		if (ProjectIndexDataFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showWarning(this, "指标未审批不允许修订");
			SysUtil.abort();
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {

		check();

		if (isNotChange()) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("notchange"));
			SysUtil.abort();
		}
		checkSettleProduct((ProjectIndexDataInfo)getDataObject());
//		checkSettleProduct();
/*		String proOrOrgId = ((CoreBaseInfo) getProjSelectedTreeNode()
				.getUserObject()).getId().toString();
		String productTypeId = null;
		if (comboProductType.getSelectedItem() instanceof ProductTypeInfo) {
			productTypeId = ((ProductTypeInfo) comboProductType
					.getSelectedItem()).getId().toString();
		}*/
		final ProjectIndexDataInfo indexDataInfo = (ProjectIndexDataInfo) getDataObject();
		if(indexDataInfo==null||indexDataInfo.getProjOrOrgID()==null){
			return;
		}
		String proOrOrgId=indexDataInfo.getProjOrOrgID().toString();
		String productTypeId = null;
		if(indexDataInfo.getProductType()!=null&&indexDataInfo.getProductType().getId()!=null){
			productTypeId=indexDataInfo.getProductType().getId().toString();
		}
		Map ver = ProjectIndexDateVersionUI.showDialogWindow(this, proOrOrgId,
				getProjectStage(), productTypeId);
		if (ver == null || ver.isEmpty()) {
			SysUtil.abort();
		}
		save(ver);

		showSaveOkMsg();
	}

	private void checkSettleProduct() throws BOSException, EASBizException {

		BigDecimal sellArea = FDCHelper.ZERO;
		BOSUuid curProjId = ((CurProjectInfo) getProjSelectedTreeNode()
				.getUserObject()).getId();
		String productID = null;
		if (comboProductType.getSelectedItem() instanceof ProductTypeInfo) {
			BOSUuid productTypeUuId = ((ProductTypeInfo) comboProductType
					.getSelectedItem()).getId();
			productID = productTypeUuId.toString();
		}
		String projectID = curProjId.toString();
		Set status = new HashSet();
		status.add(ProjectStatusInfo.settleID);
		status.add(ProjectStatusInfo.closeID);
		FilterInfo filterEx = new FilterInfo();
		filterEx.getFilterItems().add(new FilterItemInfo("id", projectID));
		filterEx.getFilterItems().add(
				new FilterItemInfo("projectStatus.id", status,
						CompareType.INCLUDE));
		if (CurProjectFactory.getRemoteInstance().exists(filterEx)) {
			MsgBox.showError(this, "工程项目已经全部竣工结算，不能修改指标数据！");
			SysUtil.abort();
		}
		if(productID!=null){
			FilterInfo filterExist = new FilterInfo();
			filterExist.getFilterItems().add(
					new FilterItemInfo("curProject.id", projectID));
			filterExist.getFilterItems().add(
					new FilterItemInfo("productType.id", productID));
			filterExist.getFilterItems().add(
					new FilterItemInfo("isCompSettle", Boolean.TRUE));
			if (CurProjProductEntriesFactory.getRemoteInstance()
					.exists(filterExist)) {
				MsgBox.showError(this, "产品已经全部竣工结算，不能修改指标数据！");
				SysUtil.abort();
			}
			for (int i = 0, count = getDetailTable().getRowCount(); i < count; i++) {
				IRow row = getDetailTable().getRow(i);
				if (row.getCell(APPORTION_TYPE_ID).getValue().toString().equals(
						FDCConstants.SALE_AREA_ID)) {
					sellArea = FDCHelper.toBigDecimal(row.getCell(NEW_VALUE)
							.getValue());
				}
			}
			if (getProjectStage().equals(ProjectStageEnum.DYNCOST)) {
				BigDecimal compArea = FDCHelper.ZERO;
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("curProjProductEntries.curProject.id",
								projectID));
				filter.getFilterItems().add(
						new FilterItemInfo("curProjProductEntries.productType.id",
								productID));
				view.setFilter(filter);
				ProductSettleBillCollection coll = ProductSettleBillFactory
						.getRemoteInstance().getProductSettleBillCollection(view);
				for (Iterator it = coll.iterator(); it.hasNext();) {
					ProductSettleBillInfo infoPr = (ProductSettleBillInfo) it
							.next();
					compArea = compArea.add(infoPr.getCompArea());
				}
				if (compArea.subtract(sellArea).compareTo(FDCHelper.ZERO) == 1) {
					MsgBox.showError(this, "可售面积不能小于已竣工面积：" + compArea.setScale(2)
							+ "！");
					SysUtil.abort();
				}
			}
		}
	}

	protected void tblEntries_editStopping(KDTEditEvent e) throws Exception {
		if (e.getColIndex() == getDetailTable().getColumnIndex(NEW_VALUE)) {
			Object oldValue = e.getOldValue();
			Object value = e.getValue();
			if (oldValue != null && value == null) {
				notChange = false;
			}
			if (oldValue == null && value != null) {
				notChange = false;
			}
			BigDecimal b1 = (BigDecimal) oldValue;
			BigDecimal b2 = (BigDecimal) value;

			if (b1 != null && b2 != null && b1.compareTo(b2) != 0) {
				notChange = false;
			}
			addchangeApportionType(e.getRowIndex());
		}
		
		if (e.getColIndex() == getDetailTable().getColumnIndex(REMARK)) {
			Object oldValue = e.getOldValue();
			Object value = e.getValue();
			if (oldValue != null && value == null) {
				notChange = false;
			}
			if (oldValue == null && value != null) {
				notChange = false;
			}
			String b1 = (String) oldValue;
			String b2 = (String) value;

			if (b1 != null && b2 != null && !b1.equals(b2)) {
				notChange = false;
			}
		}
	}

	private boolean isNotChange() {
		int count = getDetailTable().getRowCount();

		for (int i = 0; i < count; i++) {
			BigDecimal b1 = (BigDecimal) getDetailTable().getCell(i,
					INDEX_VALUE).getValue();
			BigDecimal b2 = (BigDecimal) getDetailTable().getCell(i, NEW_VALUE)
					.getValue();
			if (b1 == null && b2 == null)
				continue;
			if (b1 == null && b2 != null) {
				notChange = false;
				break;
			}
			if (b1 != null && b2 == null) {
				notChange = false;
				break;
			}
			if (b1 != null && b1.compareTo(b2) != 0) {
				notChange = false;
				break;
			}
		}

		return notChange;
	}

	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		checkModify();
		nextVer();
		showThisVerData();

	}

	private void showThisVerData() throws EASBizException, BOSException,
			Exception {
		String ver = getVer();
		ProjectIndexDataInfo info = getReadyProjectIndexDataInfo(ver);
		setDataObject(info);
		if(info.isIsLatestVer()){
			setOprtState(null);
		}else{
			setOprtState("FINDVIW");
		}
		fillTableByColl();
		selectFirstRow();

		setTableLocked(true);
		
		if (curVerIdx == verList.size() - 1) {
			this.actionNext.setEnabled(false);
			this.actionLast.setEnabled(false);
			if (verList.size() > 1) {
				this.actionPre.setEnabled(true);
				this.actionFirst.setEnabled(true);
			}else{
				this.actionPre.setEnabled(false);
				this.actionFirst.setEnabled(false);
			}
		}
	}

	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		checkModify();
		prevVer();
		showThisVerData();
	}

	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		checkModify();
		firstVer();
		showThisVerData();

	}

	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		checkModify();
		lastVer();
		showThisVerData();
	}

	void nextVer() {
		curVerIdx++;
		if (curVerIdx == verList.size() - 1) {
			this.actionNext.setEnabled(false);
			this.actionPre.setEnabled(true);
			
			this.actionFirst.setEnabled(true);
			this.actionLast.setEnabled(false);
			return ;
			
		}
		this.actionPre.setEnabled(true);
		this.actionFirst.setEnabled(true);
		this.actionLast.setEnabled(true);
	}

	void prevVer() {
		curVerIdx--;
		
		if (curVerIdx == 0) {
			
			this.actionPre.setEnabled(false);
			this.actionNext.setEnabled(true);
			
			this.actionFirst.setEnabled(false);
			this.actionLast.setEnabled(true);
			return ;	
		}
		
		this.actionNext.setEnabled(true);
		this.actionFirst.setEnabled(true);
		this.actionLast.setEnabled(true);
	}

	void firstVer() {
		curVerIdx = 0;
		this.actionNext.setEnabled(true);
		this.actionLast.setEnabled(true);
		
		this.actionPre.setEnabled(false);
		this.actionFirst.setEnabled(false);
	}

	void lastVer() {
		curVerIdx = verList.size() - 1;
		
		this.actionNext.setEnabled(false);
		this.actionLast.setEnabled(false);
		
		this.actionPre.setEnabled(true);
		this.actionFirst.setEnabled(true);
	}

	void ensureCapacity() {
		if (curVerIdx == 0) {
			actionPre.setEnabled(false);
			this.actionFirst.setEnabled(false);
		} else {
			actionPre.setEnabled(true);
			this.actionFirst.setEnabled(true);
		}

		if (curVerIdx == verList.size() - 1) {
			actionNext.setEnabled(false);
			this.actionLast.setEnabled(false);
		} else {
			actionNext.setEnabled(true);
			this.actionLast.setEnabled(true);
		}
	}

	String getVer() {
		String ver = null;

		if (verList == null)
			return null;
		if (curVerIdx >=0 && curVerIdx < verList.size()) {
			ver = (String) verList.get(curVerIdx);
		}

		return ver;
	}

	String getPreVer() {
		String ver = null;

		if (verList == null)
			return null;
		if (curVerIdx >= 1 && curVerIdx < verList.size()) {
			ver = (String) verList.get(curVerIdx - 1);
		}

		return ver;
	}

	protected String getAmountColKey() {
		return NEW_VALUE;
	}

	protected void initWorkButton() {

		super.initWorkButton();
		btnFirst.setIcon(FDCClientHelper.ICON_FIRST);
		btnPre.setIcon(FDCClientHelper.ICON_PREVIOUS);
		btnNext.setIcon(FDCClientHelper.ICON_NEXT);
		btnLast.setIcon(FDCClientHelper.ICON_LAST);
		menuItemFirst.setIcon(FDCClientHelper.ICON_FIRST);
		menuItemPre.setIcon(FDCClientHelper.ICON_PREVIOUS);
		menuItemNext.setIcon(FDCClientHelper.ICON_NEXT);
		menuItemLast.setIcon(FDCClientHelper.ICON_LAST);
	}

	/**
	 * 描述：设置采用KDLayout布局的容器的"OriginalBounds"客户属性。KDLayout设计思想采用了绝对布局方式，没有考虑Java中存在相对布局的情况。
	 * 必须在UI的非抽象类中重载方法public void initUIContentLayout()
	 * 
	 * @return
	 * @author:jelon 创建时间：2006-8-28
	 *               <p>
	 */
	public void setContainerLayout() {
		pnlLeftTree.putClientProperty("OriginalBounds", new Rectangle(10, 10,
				250, 609));
		pnlRight.putClientProperty("OriginalBounds", new Rectangle(270, 10,
				733, 609));
	}

	/**
	 * 描述：重载采用KDLayout布局的容器的"OriginalBounds"客户属性。KDLayout设计思想采用了绝对布局方式，没有考虑Java中存在相对布局的情况。
	 * 
	 * @return
	 * @author:jelon 创建时间：2006-08-28
	 *               <p>
	 */
	public void initUIContentLayout() {
		super.initUIContentLayout();

		setContainerLayout();
	}

	protected void treeTargetType_valueChanged(TreeSelectionEvent e)
			throws Exception {
		warnForSave();
		// 解决:指标修订中切换左下角的指标类型后指标数据版本就变成最后一个版本了
		//int thisV = curVerIdx;
		prepareVerList();
		//curVerIdx = thisV;
		showThisVerData();
	}

}