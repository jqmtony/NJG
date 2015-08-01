/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlBillEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlBillEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlBillFactory;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlBillInfo;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlEntryItemsCollection;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlEntryItemsInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.FullDynamicCostMap;
import com.kingdee.eas.fdc.aimcost.IDynCostCtrlBill;
import com.kingdee.eas.fdc.aimcost.util.FDCSplitBillUtil;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICurProjProductEntries;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class DynCostCtrlBillEditUI extends AbstractDynCostCtrlBillEditUI {
	private static final Logger logger = CoreUIObject.getLogger(DynCostCtrlBillEditUI.class);

	
	
	// ��¼��Ϣ
	private static final String ENTRY_ID = "id"; //��¼ID
	
	private static final String COSTACCOUNT_NUMBER = "costAccountNumber";// �ɱ���Ŀ����

	private static final String COSTACCOUNT_NAME = "costAccountName";// �ɱ���Ŀ����

	private static final String PRODUCT = "product";// ���Ʒ�Χ

	private static final String AIMCOST = "aimCost";// Ŀ��ɱ�

	private static final String ALERM_VALUE = "alermValue";// ������ʾ����

	private static final String STRICT_VALUE = "strictValue";// �ϸ���Ʊ���
	
	private boolean isModify =  false;   //�Ƿ��޶�
	
	
	private Map aimCostMap;
	
	private ICostAccount iCostAccount; 
	
	private Set productTypes ;  //����Ŀ�µĲ�Ʒ

	/**
	 * output class constructor
	 */
	public DynCostCtrlBillEditUI() throws Exception {
		super();
		setBtnAttr();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
    
	private void setBtnAttr(){
		this.btnAddNew.setVisible(false);
		this.prmtCurProject.setEnabled(false);
		this.txtVerNumber.setEnabled(false);
		this.btnCopy.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnTraceUp.setVisible(false);
		this.btnTraceDown.setVisible(false);
		this.btnCreateFrom.setVisible(false);
		this.btnAuditResult.setVisible(false);
		this.btnMultiapprove.setVisible(false);
		this.btnWFViewdoProccess.setVisible(false);
		this.btnNextPerson.setVisible(false);
		this.btnUnAudit.setVisible(false);
		this.btnRemove.setVisible(false);
		
		this.menuItemAudit.setEnabled(true);
        this.menuItemUnAudit.setEnabled(true);
		
        this.menuItemAudit.setVisible(true);
        this.menuItemUnAudit.setVisible(true);
        
        this.menuItemCopy.setVisible(false);
        this.menuItemCopyFrom.setVisible(false);
        this.menuItemTraceDown.setVisible(false);
        this.menuItemTraceUp.setVisible(false);
        this.menuItemCreateFrom.setVisible(false);
        this.menuItemCreateTo.setVisible(false);
        this.menuItemAddNew.setEnabled(false);
        this.menuItemAddNew.setVisible(false);
        this.menuSubmitOption.setEnabled(false);
        this.menuSubmitOption.setVisible(false);
        
        this.menuView.setVisible(false);
        
	}
	
	protected void updateButtonStatus() {
		
	}
	
	/**
	 * @author jie_chen 
	 * ��ʼ����¼��Ϣ
	 * @throws Exception 
	 */
	private void inItEntryValue() throws Exception{
		CurProjectInfo curProjectInfo = (CurProjectInfo) this.prmtCurProject.getValue();
		FullDynamicCostMap fullDynamicCostMap = FDCCostRptFacadeFactory.getRemoteInstance().getFullDynamicCost(curProjectInfo.getId().toString(),
				null);
		aimCostMap = fullDynamicCostMap.getAimCostMap();
		DynCostCtrlBillEntryCollection dynCostBillEntry =  this.editData.getEntrys();
		if(isModify){
			editData.setId(null);
			editData.setState(FDCBillStateEnum.SAVED);
			IDynCostCtrlBill iDynCostCtrlBill =  getServiceInterface();
			BigDecimal verNumber  = iDynCostCtrlBill.getMaxVerNumber(editData.getCurProject().getId().toString());
			verNumber = verNumber.add(new BigDecimal("0.1"));
			verNumber  =verNumber.setScale(1,BigDecimal.ROUND_HALF_UP);//���þ����������
			this.txtVerNumber.setValue(verNumber);
		}
		for(int i = 0 ,size =dynCostBillEntry.size();i<size;i++){
			IRow  row =  kdtEntrys.addRow();
			setRowEditor(row);
			DynCostCtrlBillEntryInfo billEntryInfo  =    dynCostBillEntry.get(i);
			if(isModify){
				billEntryInfo.setId(null);
			}
			DynCostCtrlEntryItemsCollection costCtryEntryItems =  billEntryInfo.getItems();
			int costAccountCount =  costCtryEntryItems.size();
			Object[] costAccountItemsObj = new Object [costAccountCount];
			for(int j = 0 ;j<costAccountCount;j++){
				DynCostCtrlEntryItemsInfo costCtrlItem = costCtryEntryItems.get(j);
				if(isModify){
					costCtrlItem.setId(null);
				}
				costAccountItemsObj[j] =costCtrlItem.getCostAccount();
				setCostAccountF7RelInfo(costAccountItemsObj, row);
			}
			if(!isModify)
				row.getCell(ENTRY_ID).setValue(billEntryInfo.getId().toString());
			row.getCell(COSTACCOUNT_NUMBER).setValue(costAccountItemsObj) ;
			row.getCell(PRODUCT).setValue(billEntryInfo.getProduct());
			row.getCell(ALERM_VALUE).setValue(billEntryInfo.getAlermValue());
			row.getCell(STRICT_VALUE).setValue(billEntryInfo.getStrictValue());
		}
	}
	
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		
	
		
		txtVerNumber.setPrecision(1);//�����޶��汾�Ľ���
		if(getUIContext().get("modify")!=null){ //������޶�,�Ѷ�Ӧ��¼����ID��Ϊnull
			isModify  = true;
		}
		Object projectId = getUIContext().get("projectId");
		if(this.prmtCurProject.getValue() ==null && projectId!=null ){
			CurProjectInfo project = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projectId.toString()));
		    this.prmtCurProject.setValue(project);
		}
		getUIContext().put("project", this.prmtCurProject.getValue());
		CurProjectInfo curProjectInfo = (CurProjectInfo) this.prmtCurProject.getValue();
		setProjectType(curProjectInfo.getId().toString());
		//��ʼ��̧ͷ��Ϣ
		iCostAccount  = CostAccountFactory.getRemoteInstance();
		getDetailTable().getColumn(COSTACCOUNT_NUMBER).setRenderer(getIDataFormat());
		getDetailTable().getColumn(COSTACCOUNT_NUMBER).setRequired(true);
		//getDetailTable().getColumn(COSTACCOUNT_NUMBER).setEditor(new KDTDefaultCellEditor(getf7CostAccount()));
		getDetailTable().getColumn(PRODUCT).setEditor( new KDTDefaultCellEditor(getf7Product()));
		getDetailTable().getColumn(COSTACCOUNT_NAME).getStyleAttributes().setLocked(true);
		getDetailTable().getColumn(AIMCOST).getStyleAttributes().setLocked(true);
		
		
		KDTDefaultCellEditor cellEditor0 = new KDTDefaultCellEditor(new KDFormattedTextField());
		KDFormattedTextField kdftf = (KDFormattedTextField) cellEditor0.getComponent();
		kdftf.setDataType(1);
		kdftf.setSupportedEmpty(true);
		kdftf.setMinimumValue(new BigDecimal("0.01"));
		kdftf.setMaximumValue(FDCHelper.ONE_THOUSAND);
		kdftf.setPrecision(2);
		getDetailTable().getColumn(ALERM_VALUE).setEditor(cellEditor0);
		getDetailTable().getColumn(ALERM_VALUE).getStyleAttributes().setNumberFormat("#0.00");
		getDetailTable().getColumn(STRICT_VALUE).setEditor(cellEditor0);
		getDetailTable().getColumn(STRICT_VALUE).getStyleAttributes().setNumberFormat("#0.00");
		
		inItEntryValue();
		
		//added by ken_liu...BT717295���ύ״̬�����治����
		if( this.editData.getState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			//super.setOprtState(STATUS_EDIT);
		} else if (STATUS_EDIT.equals(this.oprtState)) {
		} else if (STATUS_VIEW.equals(this.oprtState)) {
		} else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		}
	}

	protected void detachListeners() {
		// TODO Auto-generated method stub

	}

	protected ICoreBase getBizInterface() throws Exception {
		return DynCostCtrlBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected void attachListeners() {

	}

	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		isEditEntry();
		KDTable table   = getDetailTable();
		IRow   row ;
		if (table.getSelectManager().size() > 0) {
			
			int top = table.getSelectManager().get().getTop();

			if (isTableColumnSelected(table)) {
				row =table.addRow();
			} else {
				row=table.addRow(top);
			}
		} else {
			row=table.addRow();
		}
		if(row!=null){
			setRowEditor(row);
		}
	}

	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		isEditEntry();
		super.actionRemoveLine_actionPerformed(e);
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		isEditEntry();
		IRow  row = getDetailTable().addRow();
		setRowEditor(row);
	}

	protected IObjectValue createNewData() {
		DynCostCtrlBillInfo vo = new DynCostCtrlBillInfo();
		vo.setId(BOSUuid.create(vo.getBOSType()));
		vo.setVerNumber(FDCHelper.ONE);
		vo.setOrgUnit(this.orgUnitInfo);
		return vo;
	}

	
	private void isEditEntry(){
		if(this.btnSave.isEnabled()  || this.btnSubmit.isEnabled()){		
		}else{
			SysUtil.abort();
		}
	}
	/**
	 * ���Ʒ�ΧF7
	 * @return 
	 */
	private  KDBizPromptBox   getf7Product(){
		KDBizPromptBox kdProduct = new KDBizPromptBox();
//		kdProduct.addDataChangeListener(new DataChangeListener() {
//			public void dataChanged(DataChangeEvent e) {
//				if(e.getNewValue()!=null){
//					int rowIndex = kdtEntrys.getSelectManager().getActiveRowIndex();
//					ICell cell = kdtEntrys.getCell(rowIndex,COSTACCOUNT_NUMBER);
//					ICellEditor    editor = cell.getEditor();
//					KDBizPromptBox costMutilF7  = (KDBizPromptBox) editor.getComponent();
//					EntityViewInfo view = new EntityViewInfo();
//					FilterInfo  filter = new FilterInfo();
//					filter.getFilterItems().add(new FilterItemInfo("type",CostAccountTypeEnum.MAIN_VALUE,CompareType.EQUALS));
//					view.setFilter(filter);
//					costMutilF7.setEntityViewInfo(view);
//				}else{
//					int rowIndex = kdtEntrys.getSelectManager().getActiveRowIndex();
//					ICell cell = kdtEntrys.getCell(rowIndex,COSTACCOUNT_NUMBER);
//					ICellEditor    editor = cell.getEditor();
//					KDBizPromptBox costMutilF7  = (KDBizPromptBox) editor.getComponent();
//					costMutilF7.setEntityViewInfo(null);
//				}
//			}
//		});
		kdProduct.setDisplayFormat("$name$");
		kdProduct.setEditFormat("$number$");
		kdProduct.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");
		kdProduct.setEditable(false);
		FilterInfo filter =  new FilterInfo();
		if(productTypes!=null){
			filter.getFilterItems().add(new FilterItemInfo("id",productTypes,CompareType.INCLUDE));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id","0",CompareType.EQUALS));
		}
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		kdProduct.setEntityViewInfo(view);
		//.setFilter(filter);
		return kdProduct;
	}
	
	
	/**
	 * ��ѡ�ɱ���ĿF7 ��ť
	 * @return
	 * @throws Exception 
	 */
	private KDBizPromptBox getf7CostAccount() throws Exception {
		CostCtrlMutilPromptBox selector=new CostCtrlMutilPromptBox(this);
		final KDBizPromptBox prmtCostAccount=new KDBizPromptBox(){
			protected String valueToString(Object o) {
				String str = "";
				if (o != null && o instanceof Object[]) {
					Object[] objs = (Object[]) o;
					for (int i = 0, size = objs.length; i < size; i++) {
						if (objs[i] instanceof CostAccountInfo) {
							CostAccountInfo costAccountInfo = (CostAccountInfo) objs[i];
							str += costAccountInfo.getName() + ";";
						}
					}
				}
				return str;
			}
		};
		prmtCostAccount.setEditable(false);
		
		prmtCostAccount.setEnabledMultiSelection(true);		
		prmtCostAccount.setSelector(selector);	
		//���Ӽ���,��д�ɱ���Ŀ���ƺ�Ŀ��ɱ�֮��
		prmtCostAccount.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				if (e.getNewValue() != null) {
					int rowIndex = kdtEntrys.getSelectManager().getActiveRowIndex();
					IRow row = kdtEntrys.getRow(rowIndex);
					Object[] objs = (Object[]) e.getNewValue();
					try {
						setCostAccountF7RelInfo(objs,row);
					} catch (BOSException e1) {
						handUIExceptionAndAbort(e1);
					}
				}
			}
		});
		
		prmtCostAccount.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				int rowIndex = kdtEntrys.getSelectManager().getActiveRowIndex();
				ICell cell = kdtEntrys.getCell(rowIndex,PRODUCT);
				if(cell.getValue()!=null){
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo  filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("type",CostAccountTypeEnum.MAIN_VALUE,CompareType.EQUALS));
					view.setFilter(filter);
					prmtCostAccount.setEntityViewInfo(view);
				}else{
					prmtCostAccount.setEntityViewInfo(null);
				}
			}
		});
		
		return prmtCostAccount;
	}
	
	/**
	 * ���óɱ���Ŀ������Ϣ
	 * @param objs
	 * @param row
	 * @throws BOSException 
	 */
	private void setCostAccountF7RelInfo(Object[] objs,IRow row) throws BOSException{
		String costAccountName = "";//����Ŀ����
		BigDecimal aimCost =FDCHelper.ZERO;//Ŀ��ɱ�֮��
		for (int i = 0, size = objs.length; i < size; i++) {
			if (objs[i] instanceof CostAccountInfo) {
				CostAccountInfo costAccountInfo = (CostAccountInfo) objs[i];
				costAccountName += costAccountInfo.getName() + ";";
				if (costAccountInfo.isIsLeaf()) {// ��ϸ �ڵ�
					Object o = aimCostMap.get(costAccountInfo.getId().toString());
					aimCost = FDCHelper.add(aimCost, o);
				} else {// ���ڵ�
					CostAccountCollection costAccountCol = FDCSplitBillUtil.getCostAccounts(costAccountInfo, iCostAccount);
					for (int j = 0, jSize = costAccountCol.size(); j < jSize; j++) {
						Object o = aimCostMap.get(costAccountCol.get(j).getId().toString());
						aimCost = FDCHelper.add(aimCost, o);
					}
				}
			}
		}
		row.getCell(COSTACCOUNT_NAME).setValue(costAccountName); 
		row.getCell(AIMCOST).setValue(aimCost);
	}
	
	
	private ObjectValueRender getIDataFormat() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				String retValue = "";
				if (o != null && o instanceof Object[]) {
					Object[] objs = (Object[]) o;
					for (int i = 0, size = objs.length; i < size; i++) {
						if (objs[i] instanceof CostAccountInfo) {
							CostAccountInfo costAccountInfo = (CostAccountInfo) objs[i];
							retValue += costAccountInfo.getLongNumber() + ";";
						}
					}
				}
				return retValue.replace('!', '.');
			}
		});
		return objectValueRender;
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		checkEditData();
		super.actionSave_actionPerformed(e);

	
	}
	
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkEditData();
		super.actionSubmit_actionPerformed(e);
		if(STATUS_ADDNEW.equals(this.getOprtState()))
		{
			this.kdtEntrys.removeAll();
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("verNumber"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("isTemp"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("entrys.items.id"));
		sic.add(new SelectorItemInfo("entrys.alermValue"));
		sic.add(new SelectorItemInfo("entrys.strictValue"));
		sic.add(new SelectorItemInfo("entrys.id"));
		sic.add(new SelectorItemInfo("entrys.items.costAccount.id"));
		sic.add(new SelectorItemInfo("entrys.items.costAccount.name"));
		sic.add(new SelectorItemInfo("entrys.items.costAccount.isLeaf"));
		sic.add(new SelectorItemInfo("entrys.items.costAccount.longNumber"));
		sic.add(new SelectorItemInfo("entrys.items.costAccount.curProject.id"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("entrys.product.id"));
		sic.add(new SelectorItemInfo("entrys.product.number"));
		sic.add(new SelectorItemInfo("entrys.product.name"));
		//by hpw 2011.11.05
		sic.add(new SelectorItemInfo("isLatestVer"));
		return sic;
	}
	
	
	
	private void checkEditData(){
		//editData.getEntrys().add();
		int rowCount =  this.kdtEntrys.getRowCount();
		editData.getEntrys().clear();
		for (int i = 0; i<rowCount;i++){
			DynCostCtrlBillEntryInfo entryInfo =  new DynCostCtrlBillEntryInfo();
			IRow row = this.kdtEntrys.getRow(i);
			Object costAccountNumber =	row.getCell(COSTACCOUNT_NUMBER).getValue();
			if(costAccountNumber!=null){
				Object[] objects  =  (Object[] )costAccountNumber;
				for(int j = 0 , size =objects.length;j<size;j++ ){
					DynCostCtrlEntryItemsInfo  costCtrlEntryItemsInfo  = new DynCostCtrlEntryItemsInfo();
					costCtrlEntryItemsInfo.setCostAccount((CostAccountInfo)objects[j]);
					entryInfo.getItems().add(costCtrlEntryItemsInfo);
				}
			}else{
				FDCMsgBox.showInfo("��ϸ�ɱ���Ŀ����Ϊ��");
				SysUtil.abort();
			}
			
			Object objId =    row.getCell(ENTRY_ID).getValue();
			if(objId!=null){
				entryInfo.setId(BOSUuid.read(objId.toString()));
			}
			Object productObj  =  row.getCell(PRODUCT).getValue();
			if(productObj!=null){
				ProductTypeInfo product = (ProductTypeInfo)productObj ;
				entryInfo.setProduct(product);
			}
			
			Object alermObj =  row.getCell(ALERM_VALUE).getValue();
			boolean  isHasValue =  false;
			if(alermObj!=null){
				BigDecimal alermValue   =  new BigDecimal(alermObj.toString());
				entryInfo.setAlermValue(alermValue);
				isHasValue = true;
			}
			Object strictObj =  row.getCell(STRICT_VALUE).getValue();
			if(strictObj!=null){
				BigDecimal strictValue   =  new BigDecimal(strictObj.toString());
				entryInfo.setStrictValue(strictValue);
				isHasValue = true;
			}
			if(!isHasValue){
				FDCMsgBox.showInfo("��ʾ���Ʊ������ϸ���Ʊ���������д����֮һ");
				SysUtil.abort();
			}
			editData.getEntrys().add(entryInfo);
			
		}
		if(editData.getEntrys().size()==0){
			FDCMsgBox.showInfo("����ӳɱ���Ŀ������ϸ");
			SysUtil.abort();
		}
	}
	protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		
	}
	
	
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		kdtEntrys.removeRows();
	}
	
	private IDynCostCtrlBill getServiceInterface() throws Exception {
		IDynCostCtrlBill service = (IDynCostCtrlBill)getBizInterface();
		return service;
	}
	
	
	/**
	 * ������Ŀ�µĲ�Ʒ��Ϣ
	 * @param curProjectId
	 * @throws BOSException
	 */
	private void setProjectType(String curProjectId) throws BOSException{
		ICurProjProductEntries  iCurProjProductEntries =  CurProjProductEntriesFactory.getRemoteInstance();
		EntityViewInfo view  = new EntityViewInfo();
		FilterInfo filter  = new FilterInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("productType.id"));
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("curProject.id",curProjectId,CompareType.EQUALS));
		
		CurProjProductEntriesCollection  curProjectCol =  iCurProjProductEntries.getCurProjProductEntriesCollection(view);
		if(curProjectCol!=null && curProjectCol.size()>0){
			
			productTypes =new  HashSet();
			for(int i =  0 ; i<curProjectCol.size(); i++){
				CurProjProductEntriesInfo  curProjectEntriesInfo  =  curProjectCol.get(i);
				productTypes.add(curProjectEntriesInfo.getProductType().getId().toString());
			}
			
		}
	}
	
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if(!editData.isIsLatestVer()){
			throw new EASBizException(new NumericExceptionSubItem("1", "�����°汾���������"));
		}
		super.actionUnAudit_actionPerformed(e);
	}
	
	/**
	 * �����е���Ϣ
	 * @param row
	 * @throws Exception
	 */
	private void setRowEditor(IRow row) throws Exception{
		row.getCell(COSTACCOUNT_NUMBER).setEditor(new KDTDefaultCellEditor(getf7CostAccount()));
		row.getCell(AIMCOST).getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_NUMBER_FTM);
	}
	
	
	/**
	 * @author haiou_li
	 * @date:2013/12/18
	 * @description:�����ʱ��У���Ƿ����ͬ��Ʒͬ�ɱ���Ŀ������
	 */
	protected void verifyInputForSubmint() throws Exception{
		super.verifyInputForSubmint();
		Map accountIDs = new HashMap();
		for(int i = 0; i < kdtEntrys.getRowCount(); i ++){
			Object[] account = (Object[])kdtEntrys.getCell(i, COSTACCOUNT_NUMBER).getValue();
			ProductTypeInfo product = (ProductTypeInfo)kdtEntrys.getCell(i, PRODUCT).getValue();
			String productId = product != null ?product.getId().toString():"";
			if(accountIDs.containsKey(((CostAccountInfo)account[0]).getId().toString() + productId)){
				FDCMsgBox.showError("��" +(i+1)+"�к͵�" + accountIDs.get(((CostAccountInfo)account[0]).getId().toString() + productId) + "������ͬ�ɱ���Ŀ��ͬ��Ʒ���͵ķ�¼!");
				SysUtil.abort();
			} else {
				accountIDs.put(((CostAccountInfo)account[0]).getId().toString() + productId, new Integer(i+1));
			}
			
		}
	}
}