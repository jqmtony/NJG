/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.ICostCenterOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaUtil;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractEntryCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractEntryFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ���������ı���ͬ�ƻ���ĿF7����
 * <p>
 * ���ߣ� ���ƽ
 * <p>
 * ����ʱ�䣺2011/03/07
 * <p>
 */
public class F7FDCDepConPayPlanUI extends AbstractF7FDCDepConPayPlanUI {
	private static final Logger logger = CoreUIObject
			.getLogger(F7FDCDepConPayPlanUI.class);

	private boolean isCancel = false;
	private Date bizDate = null;
	private CurProjectInfo curProjectInfo = null;
	private String longNumber = "";

	// ��¼�������·ݳ�ʼĬ�Ͽ�ʼ�к�
	private static final int START_CON = 11;
	private static final int START_UNC = 5;
	private static final int START_NOC = 4;
	public final static String STATUS_VIEW = "VIEW";

	private Set parentNC_idSet = new HashSet();
	private int cycle = 0;

	public F7FDCDepConPayPlanUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		bizDate = (Date) this.getUIContext().get("bizDate");
		curProjectInfo = (CurProjectInfo) this.getUIContext().get("curProject");
		// this.prmtCurProject.setData(curProjectInfo);

		initF7();
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.bizDate);
		txtEditMonth.setText(cal.get(Calendar.YEAR) + "��"
				+ (cal.get(Calendar.MONTH) + 1) + "��");
		txtEditMonth.setEnabled(false);
		Object obj = prmtCostCenter.getData();
		if (obj != null) {
			AdminOrgUnitInfo baseOrg = (AdminOrgUnitInfo) obj;
			if (baseOrg.isIsLeaf()) {
				longNumber = baseOrg.getLongNumber();
				initTableColumn();
				loadEntryData(longNumber);
			}
		}
		tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
	}

	/**
	 * ���������ǹ�����
	 */
	public void initUIToolBarLayout() {
	}

	/**
	 * ��ʼ�����Ʋ���F7
	 * 
	 * @author masb ����ʱ�䣺2010/10/10
	 * @throws Exception
	 */
	protected void initF7() throws Exception {
		// CtrlUnitInfo obj = editData.getCU();
		// if (obj == null) {
		CtrlUnitInfo obj = SysContext.getSysContext().getCurrentCtrlUnit();
		// }
		String cuId = obj.getId().toString();
		FDCClientUtils.setRespDeptF7(prmtCostCenter, this,
				canSelecOtherOrgPerson() ? null : cuId);
		//		
		// CostCenterOrgUnitInfo costCenter = getCurCostCenter();
		// KDBizPromptBox bizPromptBox = this.prmtCostCenter;
		// String cuId = costCenter.getId().toString();
		// AdminOrgUnitInfo dpt = (AdminOrgUnitInfo) getUIContext().get("dpt");
		// if (dpt != null) {
		// CostCenterOrgUnitInfo dt = (CostCenterOrgUnitInfo) dpt
		// .castToFullOrgUnitInfo().cast(CostCenterOrgUnitInfo.class);
		// if (dt != null) {
		// SelectorItemCollection sic = new SelectorItemCollection();
		// sic.add("id");
		// sic.add("number");
		// sic.add("name");
		// sic.add("isBizUnit");
		// sic.add("longNumber");
		// dt = CostCenterOrgUnitFactory.getRemoteInstance()
		// .getCostCenterOrgUnitInfo(new ObjectUuidPK(dt.getId()),
		// sic);
		// this.prmtCostCenter.setData(dt);
		// }
		// } else {
		// this.prmtCostCenter.setData(costCenter);
		// }
		//
		// // ���ù������� ������ÿ��Ϊ�գ�ֻ��ѡ��ǰ�ɱ����ĺ͵�ǰ�ɱ��ɱ������¼�������ֻ��ѡ���ÿ��
		// FilterInfo filter = new FilterInfo();
		// FilterItemCollection fic = filter.getFilterItems();
		// if (cuId != null) {
		// fic.add(new FilterItemInfo("CU.id", cuId));
		// }
		// // if (dpt != null) {
		// // fic.add(new FilterItemInfo("id", dpt.getId().toString()));
		// // }
		// EntityViewInfo view = new EntityViewInfo();
		//
		// SorterItemCollection sorc = view.getSorter();
		// SorterItemInfo sort = new SorterItemInfo("number");
		// sorc.add(sort);
		//
		// view.setFilter(filter);
		// bizPromptBox.setEntityViewInfo(view);
		//
		// CostCenterF7 f7 = new CostCenterF7(this);
		// f7.showCheckBoxOfShowingAllOUs();
		// f7.setIsCUFilter(true);
		// f7.setRootUnitID(cuId);
		// if (cuId != null) {
		// f7.setCurrentCUID(cuId);
		// }
		// bizPromptBox.setSelector(f7);
	}

	// �Ƿ�ȡ��
	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

	public boolean isCancel() {
		return this.isCancel;
	}

	/**
	 * ����Ƿ�ѡ����
	 * 
	 * @return
	 */
	public boolean checkIsSelected() {
		return ((tblMain.getRowCount() > 0 && tblMain.getSelectManager().size() > 0));
	}

	/**
	 * ִ�в�ѯ,�������Queryʱ������removeRows()������ִ��ȡ��
	 */
	protected void executeQuery(FilterInfo filter) throws Exception {
		if (mainQuery == null) {
			mainQuery = new EntityViewInfo();
		}
		mainQuery.setFilter(filter);
		execQuery();
	}
	
	/**
	 * ������˫������ͬ�ڵ��ȷ��
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// ˫������е�ͬ���ȷ��
		if (e.getClickCount() == 2) {
			if (e.getType() == 0) {
				return;
			}
			confirm();
		}
		int colIndex = e.getColIndex();
		if (STATUS_VIEW.equals(getOprtState())) {
			if (colIndex > START_CON && (colIndex - START_CON) % 5 == 3) {
				KDDetailedAreaUtil.showDialog(this, tblMain);
			}
		}
	}

	/**
	 * �����������ѯ��ť���²�ѯ����
	 */
	protected void btnQueryData_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		Object obj = prmtCostCenter.getData();
		if (obj != null) {
			AdminOrgUnitInfo baseOrg = (AdminOrgUnitInfo) obj;
			if (!baseOrg.isIsLeaf()) {
				FDCMsgBox.showWarning("��ѡ����ĩ��������֯��");
				SysUtil.abort();
			}
			longNumber = baseOrg.getLongNumber();
		} else {
			FDCMsgBox.showWarning("��ѡ����֯��");
			SysUtil.abort();
		}
		this.refresh(null);
		initTableColumn();
		loadEntryData(longNumber);
	}

	/**
	 * ������ȷ������
	 * 
	 * @author masb ����ʱ�䣺2010/10/10
	 */
	protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnConfirm_actionPerformed(e);
		confirm();
		disposeUIWindow();
	}

	/**
	 * ������ȡ������
	 * 
	 * @author masb ����ʱ�䣺2010/10/10
	 */
	public void actionQuit_actionPerformed(ActionEvent e) throws Exception {
		disposeUIWindow();
		setCancel(true);
	}

	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
		setCancel(true);
	}

	// ȷ��
	public void confirm() {
		checkSelected();
		getData();
		setCancel(false);
	}

	/**
	 * ��������ȡѡ�е����ݣ�����ֵ������ΪObject[]
	 * 
	 * @return
	 */
	public Object getData() {
		if (!checkIsSelected()) {
			return null;
		}
		Object[] datas = new Object[1];
		String selID = this.getSelectedKeyValue();

		// ��ѯ����
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("head.id");
		sic.add("head.number");
		sic.add("head.name");
		sic.add("head.deptment.name");
		sic.add("project.id");
		sic.add("payMatters");
		sic.add("payMattersName");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", selID));
		view.setSelector(sic);
		view.setFilter(filter);

		// disposeUIWindow();

		FDCDepConPayPlanNoContractInfo entryInfo = null;
		try {
			entryInfo = (FDCDepConPayPlanNoContractInfo) getBizInterface()
					.getCollection(view).get(0);
			// String prjID = (String) getUIContext().get("prjID");
			// if (prjID != null &&
			// prjID.equals(entryInfo.getProject().getId())) {
			// getUIContext().put("fdcDepConPayPlanMonth", entryInfo);
			datas[0] = entryInfo;
			return datas;
			// }else{
			//				
			// }
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return null;
	}

	/**
	 * ��ȡĬ�ϳɱ����ļ��¼��ɱ�����
	 * 
	 * @return
	 */
	public CostCenterOrgUnitCollection getAllOrgUnits(
			CostCenterOrgUnitInfo parent) throws Exception {
		ICostCenterOrgUnit dao = CostCenterOrgUnitFactory.getRemoteInstance();

		CostCenterOrgUnitCollection colls = new CostCenterOrgUnitCollection();
		CostCenterOrgUnitInfo model = parent;
		String lgNumber = model.getLongNumber();

		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("longNumber", lgNumber));
		fic.add(new FilterItemInfo("longNumber", lgNumber + "!%",
				CompareType.LIKE));
		filter.setMaskString("#0 or #1");

		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);

		colls = dao.getCostCenterOrgUnitCollection(view);
		return colls;
	}

	/**
	 * ��������ȡ��ǰ�ɱ�����
	 * 
	 * @author masb ����ʱ�䣺2010/10/10
	 * @return
	 */
	public CostCenterOrgUnitInfo getCurCostCenter() {
		return SysContext.getSysContext().getCurrentCostUnit();
	}

	/**
	 * ��������ȡָ��ȡ��ҵ��ӿ�
	 */
	protected ICoreBase getBizInterface() throws BOSException {
		return FDCDepConPayPlanNoContractFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	public void loadFields() {
		super.loadFields();
		try {
			initTableColumn();
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}
	
	/**
	 * ���ݼƻ����ڳ��ȣ���ʼ��3�����Ķ�̬��
	 */
	private void initTableColumn() throws Exception {
		tblMain.checkParsed();
		// ��ɾ����̬��
		for (int i = tblMain.getColumnCount(); i > 3; i--) {
			tblMain.removeColumn(i);
		}

		tblMain.getColumn("parentNC.payMatters").setEditor(getCellEditor("text"));
		tblMain.getColumn("parentNC.payMattersName").setEditor(getCellEditor("text"));
		Calendar cal = Calendar.getInstance();
		Date editMonth = this.bizDate;
		cal.setTime(editMonth);

		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select cycle.Fcycle as cycle,head.Fyear as year1,head.FMonth as month1,cone.FParentNC as parentNC, ");
		builder.appendSql("head.FVersion as version ");//���Ӱ汾���� added by andy_liu 2012-3-26
		builder.appendSql("from T_FNC_FDCDepConPayPlanNoCon as con ");
		builder.appendSql("left join T_FNC_FDCDepConPayPlanBill as head on head.Fid = con.FHeadID ");
		builder.appendSql("left join T_FDC_PayPlanCycle as cycle on head.Fpayplancycleid = cycle.Fid ");
		builder.appendSql("left join T_FNC_FDCDepConPayPlanNCEntry as cone on con.FId = cone.FParentNC ");
		builder.appendSql("left join T_ORG_Admin as org on org.Fid = head.Fdeptmentid ");
		builder.appendSql("where org.FLongNumber like  ");
		builder.appendParam(longNumber + "%");
		//���ӱ����·ݵĹ��ˡ�added andy_liu 2012-3-26
		builder.appendSql(" and head.FMonth > " + cal.get(Calendar.MONTH));
		builder.appendSql(" and head.Fyear >= " + cal.get(Calendar.YEAR));
		builder.appendSql(" and cone.FMonth >= ");
		builder.appendParam(BudgetViewUtils.getFirstDay(editMonth));
		builder.appendSql(" and cone.FMonth <= ");
		builder.appendParam(BudgetViewUtils.getLastDay(editMonth));
		//���ӹ��������������ϱ������޶����ֿ������ݡ�added andy_liu 2012-3-26
		builder.appendSql(" and (head.FState = '4AUDITTED' or head.FState = '10PUBLISH' or head.FState = '12REVISE')");
		//���汾�������С���Ȼ������ʾ���°档added andy_liu 2012-3-27
		builder.appendSql(" order by head.FVersion desc");
		IRowSet rowSet;
		int year = 0;
		int month = 0;
		rowSet = builder.executeQuery();
		//���˳����°汾��added andy_liu 2012-3-27
		double ver = 1.0D;
		while (rowSet.next() && rowSet.getDouble("version") >= ver) {
			cycle = rowSet.getInt("cycle");
			year = rowSet.getInt("year1");
			month = rowSet.getInt("month1");
			parentNC_idSet.add(rowSet.getString("parentNC"));
			ver = rowSet.getDouble("version");
		}
		if (cycle > 0) {
			// ��ͷ
			IRow noCHead0 = tblMain.getHeadRow(0);
			IRow noCHead1 = tblMain.getHeadRow(1);
			// �ںϹ���
			KDTMergeManager noCMarge = tblMain.getHeadMergeManager();
			// �б�
			int index = 0;
			for (int i = 0; i < cycle; i++) {
				if (month > 12) {
					year += 1;
					month = 1;
				}
				String monthStr;
				if (month < 10) {
					monthStr = year + "��0" + month + "��";
				} else {
					monthStr = year + "��" + month + "��";
				}
				String KeyHead = "MONTH" + year + "" + month;

				// �޺�ͬ����ƻ�����
				IColumn col = tblMain.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "id");
				noCHead0.getCell(index).setValue(monthStr);
				noCHead1.getCell(index).setValue("id");
				col.getStyleAttributes().setHided(true);
				col.setRequired(true);

				col = tblMain.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				noCHead1.getCell(index).setValue("�ƻ�֧�����");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

				col = tblMain.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "define");
				noCHead1.getCell(index).setValue("��������");
				col.setEditor(getCellEditor("define"));
				col.setRequired(true);

				col = tblMain.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "explain");
				noCHead1.getCell(index).setValue("�ÿ�˵��");
				col.setEditor(getCellEditor("explain"));
				col.setWidth(200);
				col.setRequired(true);

				col = tblMain.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "official");
				noCHead1.getCell(index).setValue("�����������");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setLocked(true);
				col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

				noCMarge.mergeBlock(0, START_NOC + (i * 5), 0, START_NOC + (i * 5) + 4);

				month++;
			}
		}
	}

	private ICellEditor getCellEditor(String type) {
		if (type == null) {
			return null;
		} else if ("amount".equals(type)) {
			return FDCClientHelper.getNumberCellEditor();
		} else if ("explain".equals(type)) {
			KDDetailedArea explain = new KDDetailedArea(250, 200);
			explain.setMaxLength(1000);
			return new KDTDefaultCellEditor(explain);
		} else if ("define".equals(type)) {
			KDBizPromptBox prmtDefine = new KDBizPromptBox();
			prmtDefine
					.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
			prmtDefine.setDisplayFormat("$name$");
			prmtDefine.setEditFormat("$number$");
			prmtDefine.setCommitFormat("$number$");
			prmtDefine.setRequired(true);
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.TRUE));
			view.setFilter(filter);
			prmtDefine.setEntityViewInfo(view);
			return new KDTDefaultCellEditor(prmtDefine);
		} else if ("contract".equals(type)) {
			KDBizPromptBox prmtcontract = new KDBizPromptBox();
			prmtcontract.setDisplayFormat("$name$");
			prmtcontract.setEditFormat("$number$");
			prmtcontract.setCommitFormat("$number$");
			prmtcontract.setRequired(true);
			prmtcontract
					.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractBillQueryForPayPlanQuery");

			prmtcontract.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
					f7.getQueryAgent().resetRuntimeEntityView();
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					// ��Ҫ���ݹ�����Ŀ�����β��Ź��˳���ͬ
					Object dpt = prmtCostCenter.getValue();
					AdminOrgUnitInfo admin = null;
					if (dpt == null) {
						admin = SysContext.getSysContext()
								.getCurrentAdminUnit();
					} else {
						admin = (AdminOrgUnitInfo) dpt;
					}
					filter.getFilterItems().add(
							new FilterItemInfo("respDept.id", admin.getId()
									.toString(), CompareType.EQUALS));// ���β���
					filter.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.AUDITTED_VALUE,
									CompareType.EQUALS));// ������
					filter.getFilterItems().add(
							new FilterItemInfo("isCoseSplit", Boolean.TRUE));
					view.setFilter(filter);
					f7.setEntityViewInfo(view);
				}
			});
			return new KDTDefaultCellEditor(prmtcontract);
		} else if ("project".equals(type)) {
			KDBizPromptBox prmtCurProject = new KDBizPromptBox();
			prmtCurProject.setDisplayFormat("$name$");
			prmtCurProject.setEditFormat("$number$");
			prmtCurProject.setCommitFormat("$number$");
			prmtCurProject.setRequired(true);
			prmtCurProject
					.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");
			return new KDTDefaultCellEditor(prmtCurProject);
		} else if ("text".equals(type)) {
			KDTextField txt = new KDTextField();
			txt.setMaxLength(80);
			return new KDTDefaultCellEditor(txt);
		}
		return null;
	}

	private void loadEntryData(String longNumber) {
		// �����޺�ͬ�ƻ�
		Date editMonth = this.bizDate;
		Calendar cal = Calendar.getInstance();
		cal.setTime(editMonth);

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("parentNC.id");
		sic.add("parentNC.payMatters");
		sic.add("parentNC.payMattersName");
		sic.add("parentNC.project.name");
		sic.add("month");
		sic.add("planPay");
		sic.add("moneyDefine.id");
		sic.add("moneyDefine.name");
		sic.add("explain");
		sic.add("officialPay");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		AdminOrgUnitInfo costCenter = (AdminOrgUnitInfo) prmtCostCenter.getData();
		filter.getFilterItems().add(new FilterItemInfo("parentNC.head.deptment.longNumber", costCenter.getLongNumber() + "%", CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("parentNC", parentNC_idSet, CompareType.INCLUDE));
		view.getSorter().add(new SorterItemInfo("parentNC.seq"));
		view.setFilter(filter);

		try {
			FDCDepConPayPlanNoContractEntryCollection col = FDCDepConPayPlanNoContractEntryFactory.getRemoteInstance()
					.getFDCDepConPayPlanNoContractEntryCollection(view);
			// ��������ݣ����� = col.size()/cycle
			for (int i = 0; i < col.size(); i = i + cycle) { 
				FDCDepConPayPlanNoContractInfo info = col.get(i).getParentNC();
				IRow row = tblMain.addRow();
				row.getCell("id").setValue(info.getId().toString());
				row.getCell("project.name").setValue(info.getProject().getName());
				row.getCell("parentNC.payMatters").setValue(info.getPayMatters());
				row.getCell("parentNC.payMattersName").setValue(info.getPayMattersName());				
				for (int j = 0; j < cycle; j++) { // ���������
					FDCDepConPayPlanNoContractEntryInfo entry = col.get(i + j);
					cal.setTime(entry.getMonth());
					String keyHead = "MONTH" + cal.get(Calendar.YEAR) + "" + (cal.get(Calendar.MONTH) + 1);					
				    row.getCell(keyHead + "plan").setValue(entry.getPlanPay());
					row.getCell(keyHead + "define").setValue(entry.getMoneyDefine());
					row.getCell(keyHead + "explain").setValue(entry.getExplain());
					row.getCell(keyHead + "official").setValue(entry.getOfficialPay());
				}
			}
		} catch (BOSException e1) {
			handUIExceptionAndAbort(e1);
		}
	}

	protected void tblNoContract_tableClicked(KDTMouseEvent e) throws Exception {
		int colIndex = e.getColIndex();
		if (STATUS_VIEW.equals(getOprtState())) {
			if (colIndex > START_CON && (colIndex - START_CON) % 5 == 3) {
				KDDetailedAreaUtil.showDialog(this, tblMain);
			}
		}
	}
	
	// �������Ƿ����ѡ���������ŵ���Ա
	private boolean canSelecOtherOrgPerson() {
		boolean canSelectOtherOrgPerson = false;
		try {
			canSelectOtherOrgPerson = FDCUtils.getDefaultFDCParamByKey(null,
					null, FDCConstants.FDC_PARAM_SELECTPERSON);
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return canSelectOtherOrgPerson;
	}
}