/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.backport.Collections;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewByMonthCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByMonthInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleDatazCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleDatazInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewDataCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewDataInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewFactory;
import com.kingdee.eas.fdc.finance.PayPlanNewInfo;
import com.kingdee.eas.fm.common.client.FMClientHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述：合约规划拆分
 * 
 * @author weiqiang_chen
 */
public class ProgrammingSplitUI extends AbstractProgrammingSplitUI {
	
	private static final Logger logger = CoreUIObject
			.getLogger(ProgrammingSplitUI.class);
	
	protected ProgrammingContractInfo editData;
	
	protected int splitCount = 2;
	
	protected List returnLst = new ArrayList();

	public ProgrammingSplitUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		editData = (ProgrammingContractInfo) getUIContext().get("programmingContract");
		
		initHead();
		
		initTable(splitCount);
	}

	private void initHead() {
		contMsg.setBoundLabelText(editData.getName() + ":规划金额" + editData.getAmount().setScale(2) + "元");
		spinSplitCount.setValue(splitCount);
		
		SpinnerNumberModel model = new SpinnerNumberModel(2, 2, 20, 1);
		spinSplitCount.setModel(model);
		
		model.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				splitCount = spinSplitCount.getIntegerVlaue().intValue();
				initTable(splitCount);
			}
		});
	}
	
	protected void tblMain_editStarting(KDTEditEvent e) throws Exception {
		super.tblMain_editStarting(e);
	}
	
	
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		super.tblMain_editStopped(e);
		
		if (e.getValue() == null) {
			return;
		}
		IRow row = tblMain.getRow(e.getRowIndex());
		ProgrammingContracCostInfo cInfo = (ProgrammingContracCostInfo) row.getUserObject();
		if (tblMain.getColumnKey(e.getColIndex()).startsWith("scale")) {
			BigDecimal scale = (BigDecimal) row.getCell(e.getColIndex()).getValue();
			
			row.getCell(e.getColIndex() + 1).setValue(
					FDCHelper.divide(FDCHelper.multiply(cInfo.getContractAssign(), scale),FDCHelper.ONE_HUNDRED));
		}else if (tblMain.getColumnKey(e.getColIndex()).startsWith("amount")) {
			BigDecimal amount = (BigDecimal) row.getCell(e.getColIndex()).getValue();
			
			row.getCell(e.getColIndex() - 1).setValue(
					FDCHelper.multiply(FDCHelper.divide(amount,cInfo.getContractAssign()),FDCHelper.ONE_HUNDRED));
		}
	}
	

	protected void initTable(int count) {
		tblMain.checkParsed();
		
		tblMain.removeColumns();
		
		String[] columnKeys = new String[1 + count * 3];
		Object[][] head = new String[2][1 + count * 3];
		columnKeys[0] = "costAccount";
		head[0][0] = "成本科目名称";
		
		for(int i = 0;i < count ;i ++){
			columnKeys[i * 3 + 1] = "building" + i;
			columnKeys[i * 3 + 2] = "scale" + i;
			columnKeys[i * 3 + 3] = "amount" + i;
			head[0][i * 3 + 1] = editData.getName() + (i + 1) ;
			head[1][i * 3 + 1] = "涉及楼栋";
			head[1][i * 3 + 2] = "比例";
			head[1][i * 3 + 3] = "分配金额";
		}
		
		KDTableHelper.initTable(tblMain, columnKeys, head, null);
		
		
		KDTMergeManager mm = tblMain.getHeadMergeManager();
		mm.mergeBlock(0, 0, 1, 0);
		for(int i = 0;i < count ;i ++){
			mm.mergeBlock(0, i * 3 + 1, 0, i * 3 + 3);
			
			initFormattedTextCell(tblMain,"scale" + i,2);
			initFormattedTextCell(tblMain,"amount" + i,2);
			
			tblMain.getColumn("building" + i).getStyleAttributes().setHided(true);
			/*KDBizPromptBox box = initF7Cell(tblMain, "building" + i);
			//			initCurBuilding(box);
			tblMain.getColumn("building" + i).setRenderer(new ObjectValueRender(){
				public String getText(Object obj) {
					if(obj instanceof Object[]){
						Object[] objs = (Object[]) obj;
						StringBuffer txt = new StringBuffer();
						for(int i  = 0;i < objs.length;i ++){
							txt.append(format.format(objs[i]));
							txt.append(";");
						}
						return txt.toString();
					}else{
						return super.getText(obj);
					}
				}
			});
			
			ObjectValueRender render = (ObjectValueRender) tblMain.getColumn("building" + i).getRenderer();
			render.setFormat(new BizDataFormat("$name$"));*/
		}
		
		ProgrammingContracCostCollection costEntries = editData.getCostEntries();
		
		for(int i = 0;i < costEntries.size();i ++){
			ProgrammingContracCostInfo cInfo = costEntries.get(i);
			
			IRow row = tblMain.addRow();
			row.setUserObject(cInfo);
			row.getCell("costAccount").setValue(cInfo.getCostAccount());
			/*ProContractCostBuildingCollection buildings = cInfo.getBuildings();
			for(int j = 0; j < count ;j ++){
				row.getCell("building" + j).setValue(getBuildingsFromCollections(buildings));
			}*/
			
		}
		
		tblMain.getColumn("costAccount").getStyleAttributes().setLocked(true);
		
	}

	/*	private Object[] getBuildingsFromCollections(ProContractCostBuildingCollection buildings) {
			if(buildings == null || buildings.size() < 1){
				return null;
			}
			List lst = new ArrayList();
			for(int i = 0;i < buildings.size();i ++){
				lst.add(buildings.get(i).getBuilding());
			}
			return lst.toArray();
		}*/
	
	public void verifyData() throws Exception {
		super.verifyData();
		tblMain.getEditManager().editingStopped();
		
		int count = spinSplitCount.getIntegerVlaue().intValue();
		for (int i = 0, rowCount = tblMain.getRowCount(); i < rowCount; i++) {
			IRow row = tblMain.getRow(i);
			ProgrammingContracCostInfo cInfo = (ProgrammingContracCostInfo) row.getUserObject();
			
			BigDecimal sum = FDCHelper.ZERO;
			for(int j = 0;j < count;j ++){
				sum = FDCHelper.add(sum, row.getCell("amount" + j).getValue());
			}
			
			if (FDCHelper.compareTo(sum, cInfo.getContractAssign()) != 0) {
				MsgBox.showInfo("分录第 " + (i + 1) + " 行，待分配金额合计不等于原合约规划待分配金额！");
				SysUtil.abort();
			}
		}
	}
	
	protected ProgrammingContractInfo createNewData(int index,ProgrammingContractInfo head, PayPlanNewInfo pInfo) {
        ProgrammingContractInfo newDetailInfo = new ProgrammingContractInfo();
        newDetailInfo.setId(BOSUuid.create(newDetailInfo.getBOSType()));
        newDetailInfo.setLevel(head.getLevel() + 1);
        if(head != null){
        	newDetailInfo.setParent(head);
        	newDetailInfo.setNumber(head.getNumber() + "." + (index+1));
        	newDetailInfo.setName(head.getName() + (index + 1));
        	newDetailInfo.setLongNumber(head.getLongNumber() + "." + (index + 1));
			//        	newDetailInfo.setDepartment(head.getDepartment());
        	newDetailInfo.setDisplayName(head.getDisplayName() + "." + newDetailInfo.getName());
        }
        
        
        newDetailInfo.setSignUpAmount(FDCHelper.ZERO);
        newDetailInfo.setChangeAmount(FDCHelper.ZERO);
        newDetailInfo.setSettleAmount(FDCHelper.ZERO);


        BigDecimal sumAmount = FDCHelper.ZERO;
        Map costAcct2AmountMap = new HashMap();
        Map costAcct2ScaleMap = new HashMap();
        for (int i = 0, rowCount = tblMain.getRowCount(); i < rowCount; i++) {
			IRow row = tblMain.getRow(i);
			ProgrammingContracCostInfo cInfo = (ProgrammingContracCostInfo) row.getUserObject();
			
			ProgrammingContracCostInfo ncInfo = (ProgrammingContracCostInfo) cInfo.clone();
			ncInfo.setId(null);
			
			BigDecimal amount = (BigDecimal) row.getCell("amount" + index).getValue();
			
			BigDecimal scale = (BigDecimal) row.getCell("scale" + index).getValue();
			
			String key = ncInfo.getCostAccount().getId().toString();
			costAcct2AmountMap.put(key, amount);
			costAcct2ScaleMap.put(key, scale);
			
			ncInfo.setContractAssign(amount);
			ncInfo.setContractScale(FDCHelper.divide(FDCHelper.multiply(scale,cInfo.getContractScale()),FDCHelper.ONE_HUNDRED));
			
			sumAmount = FDCHelper.add(amount, sumAmount);

			
			newDetailInfo.getCostEntries().add(ncInfo);
			
        }
        if(FDCHelper.compareTo(FDCHelper.ZERO, sumAmount)==0){
        	sumAmount=FDCHelper.divide(editData.getAmount(), new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP);
        }
        newDetailInfo.setAmount(sumAmount);
        newDetailInfo.setBalance(sumAmount);
        
    	newDetailInfo.setReservedChangeRate(head.getReservedChangeRate());
		BigDecimal bigDecimal = FDCHelper.toBigDecimal(FDCHelper.multiply(sumAmount, FDCHelper.subtract(FDCHelper.ONE, head
				.getReservedChangeRate())), 2);
		newDetailInfo.setControlBalance(bigDecimal);
		newDetailInfo.setControlAmount(bigDecimal);
        
        if(pInfo != null){
        	PayPlanNewInfo info = (PayPlanNewInfo) pInfo.clone();
        	
			info.setId(null);
			PayPlanNewByScheduleCollection  sColl = info.getBySchedule();
			for(int i = 0;sColl != null && i < sColl.size();i ++){
				PayPlanNewByScheduleInfo sInfo = sColl.get(i);
				sInfo.setSrcID(sInfo.getId());
				sInfo.setId(null);
				
				
				PayPlanNewByScheduleTaskCollection task = sInfo.getTask();
				for (int j = 0; j < task.size(); j++) {
					PayPlanNewByScheduleTaskInfo taskInfo = task.get(j);
					taskInfo.setId(null);
				}
				
				PayPlanNewByScheduleDatazCollection dataz = sInfo.getDataz();
				for (int j = 0; j < dataz.size(); j++) {
					PayPlanNewByScheduleDatazInfo datazInfo = dataz.get(j);
					datazInfo.setId(null);
				}
			}
			

			
			PayPlanNewDataCollection data = info.getData();
			for (int i = 0; i < data.size(); i++) {
				PayPlanNewDataInfo dataInfo = data.get(i);
				dataInfo.setId(null);
			}
			
			newDetailInfo.put("PayPlan", info);
        }
        
        return newDetailInfo;
	}

	/**
	 * 描述：计算采购控制价
	 * 规划金额/（1+预留变更签证率），两位小数
	 */
	private void calPurControl(ProgrammingContractInfo newDetailInfo) {
		BigDecimal amount = newDetailInfo.getAmount();
		BigDecimal reservedChangeRate = newDetailInfo.getReservedChangeRate();

		if (FDCHelper.isNullZero(amount)) {
			newDetailInfo.setControlAmount(amount);
		} else {
			newDetailInfo.setControlAmount(FDCHelper.toBigDecimal(FDCHelper.multiply(amount, FDCHelper.subtract(FDCHelper.ONE,
					reservedChangeRate)), 2));
		}
	}

	protected void btnComfirm_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnComfirm_actionPerformed(e);
		
		verifyData();
		
		returnLst.clear();
		int count = spinSplitCount.getIntegerVlaue().intValue();
		PayPlanNewInfo pInfo = getPayPlanInfo();
		for (int i = count - 1; i >= 0; i--) {
			returnLst.add(createNewData(i, editData,pInfo));
		}
		
		//排序
		Collections.sort(returnLst, new Comparator() {

			public int compare(Object o1, Object o2) {
				ProgrammingContractInfo d1 = (ProgrammingContractInfo) o1;
				ProgrammingContractInfo d2 = (ProgrammingContractInfo) o2;
				return d1.getName().compareTo(d2.getName());
			}
		});
		
		editData.getCostEntries().clear();
		editData.put("deletePayPlan", Boolean.TRUE);
		
		this.disposeUIWindow();
		
	}

	private PayPlanNewInfo getPayPlanInfo() throws BOSException {
		PayPlanNewInfo pInfo = (PayPlanNewInfo) editData.get("PayPlan");
		if(pInfo == null){
			EntityViewInfo evi = new EntityViewInfo(); 
			FilterInfo filter = new FilterInfo(); 
			filter.getFilterItems().add(new FilterItemInfo("programming.id",editData.getId().toString()));
			evi.setFilter(filter);
			
			evi.setSelector(getPayPlanNewSelectors());
			
			PayPlanNewCollection coll = PayPlanNewFactory
					.getRemoteInstance().getPayPlanNewCollection(evi);
			if(coll != null && coll.size() > 0){
				pInfo = coll.get(0);
			}
		}
		pInfo.remove("uiInfo");
		return pInfo;
	}
	
	public SelectorItemCollection getPayPlanNewSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("Data.*");
		sic.add("BySchedule.Task.task.name");
		sic.add("BySchedule.Task.task.start");
		sic.add("BySchedule.Task.task.end");
		sic.add("BySchedule.Dataz.*");
		sic.add("ByBuilding.curBuilding.*");
		sic.add("ByBuilding.*");
		sic.add("ByBuilding.Datas.*");
		sic.add("ByMonth.*");
		sic.add("ByMonth.costAccount.number");
		sic.add("BySchedule.paymentType.*");
		sic.add("BySchedule.costAccount.*");
		sic.add("BySchedule.*");
		sic.add("ByMonth.costAccount.*");
		return sic;
	}
	
	
	public List getReturnValues(){
		return returnLst;
	}

	protected void btnCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnCancel_actionPerformed(e);
		
		this.disposeUIWindow();
	}
	
	private  KDBizPromptBox initF7Cell(KDTable tblMain, String name) {
		KDBizPromptBox box = new KDBizPromptBox();
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(box);
		tblMain.getColumn(name).setEditor(editor);
		return box;
	}
	
	
	/**
	 * 
	 * 描述：初始化表格金额输入控件
	 * 
	 */
	private  KDFormattedTextField initFormattedTextCell(KDTable tblMain,
			String name, int precision) {
		KDFormattedTextField formatField = new KDFormattedTextField(
				KDFormattedTextField.BIGDECIMAL_TYPE);
		formatField.setPrecision(precision);
		FMClientHelper.initCurrencyField(formatField);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(formatField);
		tblMain.getColumn(name).setEditor(editor);
		tblMain.getColumn(name).getStyleAttributes().setNumberFormat(
				getKDTCurrencyFormat(precision));
		tblMain.getColumn(name).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		return formatField;
	}
	
	public static String getKDTCurrencyFormat(int precision){
		 return "%r-[ ]0." + precision + "n";
	}

	/*	private void initCurBuilding(KDBizPromptBox box){
			box.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurBuildingQuery");
			box.setVisible(true);
			box.setEditable(true);
			box.setDisplayFormat("$name$");
			box.setEditFormat("$number$");
			box.setCommitFormat("$number$");
			Map ctx = new HashMap();
			ctx.put(CurBuildingPromptBox.KEY_CURPROJECT, getUIContext().get("project"));
			CurBuildingPromptBox selector = new CurBuildingPromptBox(this,ctx);
			selector.setMultiSelect(true);
			box.setSelector(selector);
		}*/

}