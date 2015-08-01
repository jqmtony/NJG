/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlBillEntryCollection;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlBillEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlBillFactory;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlBillInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlCostActItemsCollection;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlCostActItemsInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlItemCollection;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlItemInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlTypeEnum;
import com.kingdee.eas.fdc.aimcost.IAimCostCtrlBill;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ICurProjProductEntries;
import com.kingdee.eas.fdc.basedata.IMeasureStage;
import com.kingdee.eas.fdc.basedata.MeasureStageCollection;
import com.kingdee.eas.fdc.basedata.MeasureStageFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class AimCostCtrlBillEditUI extends AbstractAimCostCtrlBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AimCostCtrlBillEditUI.class);
    
    private static final String DESCRIPTION  ="description";
    
    private static final String CTRLTYPE = "ctrlType";// 控制方式
    
    private static final String CTRLCONDITION= "ctrlCondition";//控制条件
    
    private static final String PRODUCT="product";
    
    private  String[] aimCostCtrlCellStrs;				//测算阶段条目列名集合
    private  Map aimcostCtrlCellMap;
    
    private List measuStages;							//测算阶段	
    
    private Set productTypes ;
    
    
    
    protected KDWorkButton btnAddnewLine;
    protected KDWorkButton btnInserLine;
	protected KDWorkButton btnRemoveLines;

	private void setSmallButton() {

		btnAddnewLine = new KDWorkButton();
		btnInserLine = new KDWorkButton();
		btnRemoveLines = new KDWorkButton();
		
		btnAddnewLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addLine(getDetailTable());
			}
		});
		btnInserLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertLine(getDetailTable());
			}
		});
		btnRemoveLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeLine(getDetailTable());
			}
		});
		
		setButtonStyle(kDContainer1, btnAddnewLine, "新增行", "imgTbtn_addline");
		setButtonStyle(kDContainer1, btnInserLine, "插入行", "imgTbtn_insert");
		setButtonStyle(kDContainer1, btnRemoveLines, "删除行", "imgTbtn_deleteline");
		
	}
	
	
	protected void updateButtonStatus() {
		
	}
	
	protected void addLine(KDTable table) {
		isEditEntry();
		try {
			setRowEditor(table.addRow());
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}
	protected void insertLine(KDTable table) {
		isEditEntry();
		if (table.getSelectManager().getActiveRowIndex() < 0) {
			FDCMsgBox.showInfo("请选择行");
		}
		try {
			setRowEditor(table.addRow(table.getSelectManager().getActiveRowIndex()));
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}
	protected void removeLine(KDTable table) {
		isEditEntry();
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0) {
			FDCMsgBox.showInfo("请选择行");
			return;
		}
		super.removeLine(table);
	}
	/**
	 * 设置按钮样式
	 * 
	 * @param kdContainer
	 * @param button
	 * @param text
	 * @param icon
	 */
	private void setButtonStyle(KDContainer kdContainer, KDWorkButton button, String text, String icon) {
		button.setText(text);
		button.setToolTipText(text);
		button.setVisible(true);
		button.setIcon(EASResource.getIcon(icon));
		kdContainer.addButton(button);
	}
    /**
     * output class constructor
     */
    public AimCostCtrlBillEditUI() throws Exception
    {
        super();
        
        this.menuItemCopy.setVisible(false);
        this.menuItemCopyFrom.setVisible(false);
        this.menuItemTraceDown.setVisible(false);
        this.menuItemTraceUp.setVisible(false);
        this.menuItemCreateFrom.setVisible(false);
        this.menuItemCreateTo.setVisible(false);
        
        this.menuItemAddNew.setVisible(false);
        this.menuSubmitOption.setVisible(false);
        
        this.menuView.setVisible(false);
        
    }
    
    private static final String COLKEY_PER = "k1" ; 
    
	/**
	 * 控制范围F7
	 * @return 
	 */
	private  KDBizPromptBox   getf7Product(){
		KDBizPromptBox kdProduct = new KDBizPromptBox();
		kdProduct.setDisplayFormat("$name$");
		kdProduct.setEditFormat("$number$");
		kdProduct.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");	
		kdProduct.setEditable(false);
//		kdProduct.addDataChangeListener(new DataChangeListener() {
//			public void dataChanged(DataChangeEvent e) {
//				if(e.getNewValue()!=null){
//					int rowIndex = kdtEntrys.getSelectManager().getActiveRowIndex();
//					ICell cell = kdtEntrys.getCell(rowIndex,CTRLCONDITION);
//					ICellEditor    editor = cell.getEditor();
//					KDBizPromptBox costMutilF7  = (KDBizPromptBox) editor.getComponent();
//					EntityViewInfo view = new EntityViewInfo();
//					FilterInfo  filter = new FilterInfo();
//					filter.getFilterItems().add(new FilterItemInfo("type",CostAccountTypeEnum.MAIN_VALUE,CompareType.EQUALS));
//					view.setFilter(filter);
//					costMutilF7.setEntityViewInfo(view);
//				}else{
//					int rowIndex = kdtEntrys.getSelectManager().getActiveRowIndex();
//					ICell cell = kdtEntrys.getCell(rowIndex,CTRLCONDITION);
//					ICellEditor    editor = cell.getEditor();
//					KDBizPromptBox costMutilF7  = (KDBizPromptBox) editor.getComponent();
//					costMutilF7.setEntityViewInfo(null);
//				}
//			}
//		});
		FilterInfo filter =  new FilterInfo();
		if(productTypes!=null){
			filter.getFilterItems().add(new FilterItemInfo("id",productTypes,CompareType.INCLUDE));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id","0",CompareType.EQUALS));
		}
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		kdProduct.setEntityViewInfo(view);
		return kdProduct;
	}
	/**
	 * 控制方式下拉列表
	 * 
	 * @throws BOSException
	 */
	private void initCtrlType() throws BOSException {
		KDComboBox cboCtrlType = new KDComboBox();

		//cboCtrlType.addItem(new String());
		cboCtrlType.addItem(AimCostCtrlTypeEnum.CONTROL_HIGH);
		cboCtrlType.addItem(AimCostCtrlTypeEnum.CONTROL_LOW);
		cboCtrlType.setSelectedIndex(1);
		ICellEditor f7AdjustTypeEditor = new KDTDefaultCellEditor(cboCtrlType);
		getDetailTable().getColumn(CTRLTYPE).setEditor(f7AdjustTypeEditor);
		
		getDetailTable().getColumn(CTRLTYPE).setRequired(true);
	}
    
	/**
	 * 多选成本科目F7 按钮
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
	private ObjectValueRender getIDataFormat() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				String retValue = "";
				if(o!=null){
					Object[] objs = (Object[])o;
					for(int i =0,size = objs.length;i<size; i++){
						if(  objs[i] instanceof  CostAccountInfo){
							CostAccountInfo  costAccountInfo = (CostAccountInfo)objs[i];
							retValue+=costAccountInfo.getName()+";";
						}
					}
				}
				return retValue;
			}
		});
		return objectValueRender;
	}
	
	
	

    public void onLoad() throws Exception {
		super.onLoad();
		txtVerNumber.setPrecision(1);
		Object projectId = getUIContext().get("projectId");
		if(this.prmtProject.getValue() ==null && projectId!=null ){
			CurProjectInfo project = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projectId.toString()));
		    this.prmtProject.setValue(project);
		}
		getUIContext().put("project", this.prmtProject.getValue());
		
		
		CurProjectInfo curProjectInfo = (CurProjectInfo) this.prmtProject.getValue();
		setProjectType(curProjectInfo.getId().toString());
		//getDetailTable().getColumn(CTRLCONDITION).setEditor(new KDTDefaultCellEditor(getf7CostAccount()));
		getDetailTable().getColumn(CTRLCONDITION).setRenderer(getIDataFormat());
		getDetailTable().getColumn(CTRLCONDITION).setRequired(true);
		getDetailTable().getColumn(PRODUCT).setEditor(new KDTDefaultCellEditor(getf7Product()));

		
		KDTextField kdtf  = new KDTextField();
		kdtf.setMaxLength(1000);
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(kdtf);
		getDetailTable().getColumn(DESCRIPTION).setEditor(cellEditor);
		
		btnAddLine.setVisible(false);
		btnInsertLine.setVisible(false);
		btnRemoveLine.setVisible(false);
		this.menuItemAddNew.setEnabled(false);
		this.menuItemAddNew.setVisible(false);
		this.menuSubmitOption.setEnabled(false);
		this.menuSubmitOption.setVisible(false);
		initEntriesDynStage();

		initTabalFormat();

		inItEntryValue();

		setSmallButton();

		initButton();
		
	}
	public void onShow() throws Exception {
    	super.onShow();
		initCtrlType();    	
	}
	/**
	 * 动态加载测算阶段
	 */
	private void initEntriesDynStage() {
		initCache();
		if(measureStageCache != null && !measureStageCache.isEmpty()){
    		Iterator iter = measureStageCache.entrySet().iterator();
			MeasureStageInfo mcInfo = null ;
			while(iter.hasNext()){
				Entry entry = (Entry) iter.next();
				mcInfo = (MeasureStageInfo) entry.getValue();
				IColumn col = this.kdtEntrys.addColumn();
				col.setKey(COLKEY_PER+mcInfo.getNumber());
				
			}
			
			iter = measureStageCache.entrySet().iterator();
			aimCostCtrlCellStrs=new String[measureStageCache.entrySet().size()];
			aimcostCtrlCellMap=new HashMap();
			measuStages=new ArrayList();
			int i=0;
			while(iter.hasNext()){
				Entry entry = (Entry) iter.next();
				mcInfo = (MeasureStageInfo) entry.getValue();
				kdtEntrys.getHeadRow(0).getCell(COLKEY_PER+mcInfo.getNumber()).setValue(mcInfo.getName()+"(%)");
				aimCostCtrlCellStrs[i]=COLKEY_PER+mcInfo.getNumber();
				aimcostCtrlCellMap.put(mcInfo.getName(), COLKEY_PER+mcInfo.getNumber());
				measuStages.add(mcInfo);
				i=i+1;
			}
		}
	}
    
	protected void attachListeners() {
		initCache();
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AimCostCtrlBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	protected IObjectValue createNewData() {
		AimCostCtrlBillInfo vo = new AimCostCtrlBillInfo();
		vo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		vo.setId(BOSUuid.create(vo.getBOSType()));
	
		vo.setVerNumber(FDCHelper.ONE);
		vo.setOrgUnit(orgUnitInfo);
		return vo;
	}
	
	private Map measureStageCache = new TreeMap();
	
	/**
	 * 
	 */
	private void initCache() {		
		try {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",new Integer(1)));
			SorterItemCollection sic = new SorterItemCollection();
			SorterItemInfo sortInfo = new SorterItemInfo("number");
			sortInfo.setSortType(SortType.ASCEND);
			sic.add(sortInfo);
			
			view.setFilter(filter);
			view.setSorter(sic);
			
			IMeasureStage iMeasureSrv = MeasureStageFactory.getRemoteInstance();
			
			MeasureStageCollection mc = iMeasureSrv.getMeasureStageCollection(view);
			MeasureStageInfo measureStage = null ;
			
			for(int i=0;i<mc.size();i++){
				measureStage = mc.get(i);
				measureStageCache.put(measureStage.getNumber(), measureStage);
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}
	
	protected IObjectValue createNewDetailData(KDTable table) {
		AimCostCtrlBillEntryInfo entryInfo = new AimCostCtrlBillEntryInfo();
		Iterator iter = measureStageCache.entrySet().iterator();		
		while(iter.hasNext()){
			Entry entry =(Entry)iter.next();
			AimCostCtrlItemInfo itemInfo = new AimCostCtrlItemInfo();
			itemInfo.setMeasuStage((MeasureStageInfo) entry.getValue());
			entryInfo.getStageItems().add(itemInfo);
		}		
		return entryInfo;
	}
	
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkEditData();
		super.actionSubmit_actionPerformed(e);
		if(STATUS_ADDNEW.equals(this.getOprtState()))
		{
			this.kdtEntrys.removeAll();
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {		
		checkEditData();
		super.actionSave_actionPerformed(e);		
	}
	
	
	/**
	 * 保存设置editData数据
	 * @throws Exception
	 */
	public void checkEditData() throws Exception{
		//verifyInput();
		
		int rowCount =  this.kdtEntrys.getRowCount();
		if(rowCount==0){
			FDCMsgBox.showInfo("请选增加控制单明细信息");
			SysUtil.abort();
		}
		editData.getEntrys().clear();
		for (int i = 0; i<rowCount;i++){
			AimCostCtrlBillEntryInfo entryInfo=new AimCostCtrlBillEntryInfo();

			IRow row = this.kdtEntrys.getRow(i);
			setRowEditor(row);
			Object productObj  = row.getCell(PRODUCT).getValue();
			if(productObj!=null){
				  ProductTypeInfo product  =  (ProductTypeInfo) productObj;
				  entryInfo.setProduct(product);
			}
			//控制说明
			Object descriptionObj= row.getCell(DESCRIPTION).getValue();
			if(descriptionObj!=null){
				entryInfo.setDescription(descriptionObj.toString());
			}else{
				FDCMsgBox.showInfo("请输入控制说明");
				SysUtil.abort();
			}
			//控制方式
			Object ctrlTypeObj= row.getCell(CTRLTYPE).getValue();
			if(ctrlTypeObj!=null){
				AimCostCtrlTypeEnum ctrlType  = (AimCostCtrlTypeEnum) ctrlTypeObj;
				entryInfo.setCtrlType(ctrlType);
			}else{
				FDCMsgBox.showInfo("请选择控制方式");
				SysUtil.abort();
			}
			//控制条件
			Object costAccountObject=  row.getCell(CTRLCONDITION).getValue();
			if(costAccountObject!=null){
				Object[] objects  = (Object[]) costAccountObject;
				for(int j=0,size=objects.length;j<size;j++){
					AimCostCtrlCostActItemsInfo ctrlCondition =new AimCostCtrlCostActItemsInfo();
					CostAccountInfo costAccountInfo=((CostAccountInfo)objects[j]);
					ctrlCondition.setCostAccount(costAccountInfo);
					entryInfo.getCostAccount().add(ctrlCondition);
				}
				
			}else{
				FDCMsgBox.showInfo("请选择控制条件");
				SysUtil.abort();
			}
			//测算阶段
			for(int k=0;k<aimCostCtrlCellStrs.length;k++){
				AimCostCtrlItemInfo aimCostCtrlItemInfo=new AimCostCtrlItemInfo();
				Object costCellStr = row.getCell(aimCostCtrlCellStrs[k]).getValue();
				if(costCellStr!=null){
					aimCostCtrlItemInfo.setValue(Float.parseFloat(costCellStr.toString()));
					aimCostCtrlItemInfo.setMeasuStage((MeasureStageInfo)measuStages.get(k));
					entryInfo.getStageItems().add(aimCostCtrlItemInfo);
				}
			}
			editData.getEntrys().add(entryInfo);
		}
		
		editData.setIsLatestVer(false);
		
	}

	/**
	 * @author jie_chen 
	 * 初始化分录信息
	 * @throws Exception 
	 */
	private void inItEntryValue() throws Exception{
		
		boolean isModify = false;
		if(getUIContext().get("modify")!=null){ //如果是修订,把对应记录条数ID设为null
			isModify  = true;
		}
		if(isModify){
			editData.setId(null);
			editData.setState(FDCBillStateEnum.SAVED);
			IAimCostCtrlBill iAimCostCtrlBill =  getServiceInterface();
			BigDecimal verNumber  = iAimCostCtrlBill.getMaxVerNumber(editData.getProject().getId().toString());
			verNumber = verNumber.add(new BigDecimal("0.1"));
			verNumber  =verNumber.setScale(1,BigDecimal.ROUND_HALF_UP);//设置精度舍入规则
			this.txtVerNumber.setValue(verNumber);
			this.prmtAuditor.setValue(null);
			this.pkAuditTime.setValue(null);
		}
		AimCostCtrlBillEntryCollection aimCostBillEntry =  this.editData.getEntrys();
		kdtEntrys.removeRows();
		for(int i = 0 ,size =aimCostBillEntry.size();i<size;i++){
			IRow  row =  kdtEntrys.addRow();
			setRowEditor(row);
			AimCostCtrlBillEntryInfo billEntryInfo  =    aimCostBillEntry.get(i);//分录信息
			if(isModify){
				billEntryInfo.setId(null);
			}
			AimCostCtrlCostActItemsCollection costCtryEntryItems =  billEntryInfo.getCostAccount();//分录的目标成本分录
			// 测算阶段禁用科目不在了，就不显示 edit by emanon
			// int costAccountCount = costCtryEntryItems.size();
			List costList = new ArrayList();
			for (int j = 0; j < costCtryEntryItems.size(); j++) {				
				AimCostCtrlCostActItemsInfo aimCostAct =  costCtryEntryItems.get(j);
				if(isModify){
					aimCostAct.setId(null);
				}
				if (aimCostAct.getCostAccount() != null
						&& !FDCHelper.isEmpty(aimCostAct.getCostAccount()
								.getName())) {
					costList.add(aimCostAct.getCostAccount());
				}
				// costAccountItemsObj[j] =aimCostAct.getCostAccount();
			}
			// Object[] costAccountItemsObj = new Object [costList.size()];
			Object[] costAccountItemsObj = costList.toArray();
			row.getCell(CTRLCONDITION).setValue(costAccountItemsObj);
			row.getCell(PRODUCT).setValue(billEntryInfo.getProduct());
			row.getCell(DESCRIPTION).setValue(billEntryInfo.getDescription());
			row.getCell(CTRLTYPE).setValue(billEntryInfo.getCtrlType());
			
			//测算阶段
			AimCostCtrlItemCollection aimCostCtrlItems= billEntryInfo.getStageItems();
			
			for(int j=0;j<aimCostCtrlItems.size();j++){
				AimCostCtrlItemInfo aimCostCtrlItem=aimCostCtrlItems.get(j);
				if(isModify){
					aimCostCtrlItem.setId(null);
				}
				Object aimCostCtrlItemObject = aimcostCtrlCellMap.get(aimCostCtrlItem.getMeasuStage().getName());
				if(aimCostCtrlItemObject!=null){
				ICell cell = row.getCell(aimCostCtrlItemObject.toString());
					if(cell!=null){
						cell.setValue(aimCostCtrlItem.getValue()+"");
					}
				}
			}
		}
		
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("verNumber"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("auditor.*"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("verNumber"));
		sic.add(new SelectorItemInfo("isLatestVer"));
		sic.add(new SelectorItemInfo("project.*"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		sic.add(new SelectorItemInfo("entrys.ctrlType"));
		sic.add(new SelectorItemInfo("entrys.description"));
		sic.add(new SelectorItemInfo("entrys.costAccount.id"));
		sic.add(new SelectorItemInfo("entrys.costAccount.costAccount.*"));
		sic.add(new SelectorItemInfo("entrys.stageItems.*"));
		sic.add(new SelectorItemInfo("entrys.stageItems.measuStage.*"));
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("entrys.product.*"));
        return sic;
	}
	

	private void initTabalFormat(){
		KDTDefaultCellEditor cellEditor0 = new KDTDefaultCellEditor(new KDFormattedTextField());
		KDFormattedTextField kdftf = (KDFormattedTextField) cellEditor0.getComponent();
		kdftf.setDataType(1);
		kdftf.setSupportedEmpty(true);
		kdftf.setMinimumValue(new BigDecimal("0.01"));
		kdftf.setMaximumValue(FDCHelper.ONE_THOUSAND);
		kdftf.setPrecision(2);
		for(int i=0;i<aimCostCtrlCellStrs.length;i++){
			IColumn  column = kdtEntrys.getColumn(aimCostCtrlCellStrs[i]);
			column.setEditor(cellEditor0);
			column.getStyleAttributes().setNumberFormat("#0.00");
		}
	}
//	private void verifyInput(){
//		if (FDCHelper.isEmpty(txtNumber.getText())) {
//			FDCMsgBox.showInfo("请录入单据编码");
//			txtNumber.requestFocus();
//			SysUtil.abort();
//		}
//		if (FDCHelper.isEmpty(txtName.getText())) {
//			FDCMsgBox.showInfo("请录入单据名称");
//			txtName.requestFocus();
//			SysUtil.abort();
//		}
//	}
	private void initButton(){
		if (this.oprtState.equals(OprtState.EDIT) && !this.editData.getState().equals(FDCBillStateEnum.SAVED)) {
			this.btnSave.setEnabled(false);
		}
		this.btnAddNew.setVisible(false);
		this.btnFirst.setEnabled(false);
		this.btnFirst.setVisible(false);
		this.btnPre.setEnabled(false);
		this.btnPre.setVisible(false);
		this.btnNext.setEnabled(false);
		this.btnNext.setVisible(false);
		this.btnTraceUp.setEnabled(false);
		this.btnTraceUp.setVisible(false);
		this.btnTraceDown.setEnabled(false);
		this.btnTraceDown.setVisible(false);
		this.btnCreateFrom.setEnabled(false);
		this.btnCreateFrom.setVisible(false);
		this.btnAuditResult.setEnabled(false);
		this.btnAuditResult.setVisible(false);
		this.btnMultiapprove.setEnabled(false);
		this.btnMultiapprove.setVisible(false);
		this.btnNextPerson.setEnabled(false);
		this.btnNextPerson.setVisible(false);
		this.btnCalculator.setEnabled(false);
		this.btnCalculator.setVisible(false);
		this.btnCopy.setEnabled(false);
		this.btnCopy.setVisible(false);
		this.btnAttachment.setEnabled(false);
		this.btnAttachment.setVisible(false);
		this.btnLast.setEnabled(false);
		this.btnLast.setVisible(false);
		this.btnRemove.setVisible(false);
	}
	private IAimCostCtrlBill getServiceInterface() throws Exception {
		IAimCostCtrlBill service = (IAimCostCtrlBill)getBizInterface();
		return service;
	}
	
	private void isEditEntry(){
		if(this.btnSave.isEnabled()  || this.btnSubmit.isEnabled()){		
		}else{
			SysUtil.abort();
		}
	}
	
	/**
	 * 设置项目下的产品信息
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
		if (curProjectCol != null && curProjectCol.size() > 0) {			
			productTypes =new  HashSet();
			for(int i =  0 ; i<curProjectCol.size(); i++){
				CurProjProductEntriesInfo  curProjectEntriesInfo  =  curProjectCol.get(i);
				productTypes.add(curProjectEntriesInfo.getProductType().getId().toString());
			}			
		}
	}	
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if(!editData.isIsLatestVer()){
			throw new EASBizException(new NumericExceptionSubItem("1", "非最新版本不允许反审核"));
		}
		super.actionUnAudit_actionPerformed(e);
	}	
	
	/**
	 * 设置列的信息
	 * @param row
	 * @throws Exception
	 */
	private void setRowEditor(IRow row) throws Exception{
		row.getCell(CTRLCONDITION).setEditor(new KDTDefaultCellEditor(getf7CostAccount()));
		for(int k=0;k<aimCostCtrlCellStrs.length;k++){
			if(row.getCell(aimCostCtrlCellStrs[k]).getValue()==null){
				row.getCell(aimCostCtrlCellStrs[k]).setValue(FDCHelper.ONE_HUNDRED);
			}
		}
	}
}