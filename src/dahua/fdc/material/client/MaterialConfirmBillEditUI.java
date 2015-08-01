/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
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
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.MaterialPromptSelector;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.material.IMaterialOrderBizBillEntry;
import com.kingdee.eas.fdc.material.MaterialConfirmBillCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryInfo;
import com.kingdee.eas.fdc.material.MaterialConfirmBillFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillInfo;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryCollection;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryFactory;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryInfo;
import com.kingdee.eas.fdc.material.PartAMaterialEntryCollection;
import com.kingdee.eas.fdc.material.PartAMaterialEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.JdbcRowSet;
import com.kingdee.util.enums.EnumUtils;

/** 
 * 材料确认单编辑
 * @version EAS 6.0
 */
public class MaterialConfirmBillEditUI extends AbstractMaterialConfirmBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialConfirmBillEditUI.class);

    private  BigDecimal paid = FDCHelper.ZERO;
    private  BigDecimal supply = FDCHelper.ZERO;
    private  BigDecimal confirm = FDCHelper.ZERO;
    
    private int pricePrecision = 2;
	private int unitPrecision = 2;
    
    private static Map numMap = new HashMap();
    /**确认单所选择的材料合同ID只有一个*/
    private String materialConId = null;
    
    //本币金额
    private BigDecimal sumAmount = FDCHelper.ZERO;
    
    public ContractBillInfo contractInfo;
    public ContractBillInfo getContractInfo() {
		return contractInfo;
	}
	public void setContractInfo(ContractBillInfo contractInfo) {
		this.contractInfo = contractInfo;
	}

    public MaterialConfirmBillEditUI() throws Exception
    {
        super();
        
    }
    protected void prmtMaterialContractBill_dataChanged(DataChangeEvent e)
			throws Exception {
		
	}
    
	public boolean destroyWindow() {
		boolean destroyWindow = super.destroyWindow();
		if(destroyWindow){
			MaterialDetailUI.clear();
		}
		return destroyWindow;
	}

    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    	if (this.prmtMaterialContractBill.getValue() == null) {
			MsgBox.showWarning(this, "请先选择材料合同");
			SysUtil.abort();
		}
        super.actionAddLine_actionPerformed(e);
        setHeadAmt();
    }

    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.prmtMaterialContractBill.getValue() == null){
			MsgBox.showWarning(this, PartAUtils.getRes("materialConNumberNotNull"));
			SysUtil.abort();
		}
        super.actionInsertLine_actionPerformed(e);
        setHeadAmt();
    }
    
    private String precisonFormat(int precision) {
		StringBuffer sb = new StringBuffer();
		sb.append("#,##0.");
		for (int i = 0; i < precision; i++) {
			sb.append("0");
		}
		return sb.toString();
	}
    
	/**
	 * 往分录行中插入数据
	 * @param rowIndex 光标所在行
	 * @param materials 待插入的物料数组，若其中某个为null，表示有重复不需要插入
	 * @author owen_wen 2010-6-23
	 */
	private void insertDataToRow(Object[] materials) {
	
		//获取活动行的行号
		int activeRow = kdtEntrys.getSelectManager().getActiveRowIndex();
		
		KDTable table  = getDetailTable();
		IRow row = table.getRow(activeRow);
		for(int i = 0; i < materials.length; i++){
			MaterialInfo materialInfo = null;
			if(materials[i] instanceof MaterialInfo)
				materialInfo = (MaterialInfo)materials[i];
			
			if(i == 0 && materialInfo == null)
			{
				table.removeRow(activeRow);
				continue;
			}
			
			//如果是多选(即i>0时)，则要判断是否要addLine（即新增一行分录）
			if(i > 0){
				if(materialInfo != null)
				{
					table.addRow(table.getSelectManager().getActiveRowIndex()); //在活动行处添加一行 
					row = table.getRow(table.getSelectManager().getActiveRowIndex()); 
					IObjectValue detailData = createNewDetailData(table);
					row.setUserObject(detailData);
				}
				else
				{
					continue;
				}
			}
			
			// 当 i==0, and material != null 的情况，直接将materialInfo放入到当前行中
			row.getCell(MaterialConfirmContants.MATERIAL_NUMBER).setValue(materialInfo.getNumber());
			row.getCell(MaterialConfirmContants.MATERIAL_NAME).setValue(materialInfo.getName());
			row.getCell(MaterialConfirmContants.MODEL).setValue(materialInfo.getModel());
			row.getCell(MaterialConfirmContants.UNIT).setValue(materialInfo.getBaseUnit().getName());
			
			//此处是自由添加的物料，没有单价和原币价，所以置为0 
			row.getCell(MaterialConfirmContants.PRICE).setValue(FDCConstants.ZERO);
			row.getCell(MaterialConfirmContants.ORIGINAL_PRICE).setValue(FDCConstants.ZERO);
			row.getCell("materialId").setValue(materialInfo.getId());
		
			KDFormattedTextField qty = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);					
			KDFormattedTextField price = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
			
			pricePrecision = materialInfo.getPricePrecision(); //物料的单价精度
			unitPrecision = materialInfo.getBaseUnit().getQtyPrecision(); // 物料->计量单位->数量精度
			
		    qty.setPrecision(unitPrecision);
		    qty.setHorizontalAlignment(KDFormattedTextField.RIGHT);
			KDTDefaultCellEditor editor = new KDTDefaultCellEditor(qty);
			row.getCell("quantity").setEditor(editor);
			
			price.setPrecision(pricePrecision);
			price.setHorizontalAlignment(KDFormattedTextField.RIGHT);
			KDTDefaultCellEditor priceEditor = new KDTDefaultCellEditor(price);

			row.getCell("amount").setEditor(priceEditor);
			row.getCell("confirmAmount").setEditor(priceEditor);
			row.getCell("confirmOriAmt").setEditor(priceEditor);
			row.getCell("originalPrice").setEditor(priceEditor);
			row.getCell("price").setEditor(priceEditor);
			row.getCell("confirmPrice").setEditor(priceEditor);
			row.getCell("confirmOriginalPrice").setEditor(priceEditor);

			row.getCell(MaterialConfirmContants.QUANTITY).getStyleAttributes().setNumberFormat(precisonFormat(unitPrecision));
			row.getCell(MaterialConfirmContants.PRICE).getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
			row.getCell(MaterialConfirmContants.ORIGINAL_PRICE).getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
			row.getCell(MaterialConfirmContants.AMOUNT).getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
			row.getCell("confirmOriginalPrice").getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
			row.getCell("confirmPrice").getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
			row.getCell("confirmAmount").getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
			row.getCell("confirmOriAmt").getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));			
			
			if(row.getUserObject() != null && 
					row.getUserObject() instanceof MaterialConfirmBillEntryInfo)
			{
				MaterialConfirmBillEntryInfo entry = (MaterialConfirmBillEntryInfo)row.getUserObject();
				entry.setMaterial(materialInfo);
			}
		}	
	}
	
	/**
	 * 对物料F7中多选的物料数组进行筛选过滤，如果表中已有某物料，将其赋为null<p>
	 * 过滤条件：如果表中有相同物料编码的行则过滤掉
	 * @param e KDTEditEvent KDTable编辑事件 
	 * @author owen_wen 2010-6-23
	 * @return 筛选后的物料数组
	 */
	private Object[] filterMaterial(KDTEditEvent e){
		KDTable table = getDetailTable();
		if( (e.getValue() != null) 
				&& (e.getValue() instanceof Object[])){
			Object[] materials = (Object[]) e.getValue();
							
			int activeRow = table.getSelectManager().getActiveRowIndex();
			
			//过滤重复物料
			for(int i = 0; i < materials.length; i++)
			{
				MaterialInfo material = null;
				if(materials[i] instanceof MaterialInfo)
					material = (MaterialInfo)materials[i];
				
				for(int j = 0; j < table.getRowCount(); j++)
				{
					//如果j移动到活动行上，不需要判断是否重复，因为该活动行有可能选择了多个物料
					if(j != activeRow)
					{
						if(table.getRow(j).getCell(MaterialConfirmContants.MATERIAL_NUMBER).getValue() == null)
							continue;   //碰到没有物料的行，跳过
												
						String preMateralNumber = ""; //获取第j行的物料编码
						
						//有可能是刚新增未保存的物料行，getValue得到的还不是MaterialInfo对象，因此要判断处理
						if (table.getRow(j).getCell(MaterialConfirmContants.MATERIAL_NUMBER).getValue() instanceof MaterialInfo) 
							preMateralNumber = ((MaterialInfo)table.getRow(j).getCell(MaterialConfirmContants.MATERIAL_NUMBER).getValue()).getNumber();
						else
							preMateralNumber = table.getRow(j).getCell(MaterialConfirmContants.MATERIAL_NUMBER).getValue().toString();
																		
						if(material.getNumber().equals(preMateralNumber)){
							materials[i] = null; //将第i个物料，标记为空
							break; 		//物料重复,且合同重复，直接跳出循环，对下一行物料进行判断
						}
					}
				}
			}
			
			return materials;
		}else {
			return null;
		}
	}

    /**
     * 只能录原币，换算本位币
	 *
     * “数量”、“确认本币单价/确认原币单价”、“确认金额”这几个字段之间自动换算：<p>
     *	1.	当“数量”改变时，“确认本币单价/确认原币单价”不变，自动计算“确认金额” <p>
     *	2.	当“确认本币单价/确认原币单价”改变时，“数量”不变，自动计算“确认金额” <P>
     *	3.	当“确认金额”改变时，“数量”不变，自动计算“确认本币单价/确认原币单价”<p>
	 *
     *	注意：当数量改变时，前面的“本币金额”、“原币金额”及“金额”之间的自动换算关系为：“本币/原币单价”不变，自动计算“金额”
     */
    protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
    	
    	if(e.getColIndex() == getDetailTable().getColumnIndex("confirmOriAmt")){
    		
    		if(getDetailTable().getCell(e.getRowIndex(),"confirmOriAmt" ).getValue() != null && 
    				getDetailTable().getCell(e.getRowIndex(),"quantity" ).getValue() != null ){
    			BigDecimal confirmOriAmt =  FDCHelper.toBigDecimal(getDetailTable().getCell(e.getRowIndex(),"confirmOriAmt" ).getValue(),8);
    			BigDecimal qty =  FDCHelper.toBigDecimal(getDetailTable().getCell(e.getRowIndex(),"quantity" ).getValue(),8);
    			BigDecimal rate = this.txtRate.getBigDecimalValue();
    			ICell confirmPrice = getDetailTable().getCell(e.getRowIndex(),"confirmPrice" );//本币
    			ICell confirmOriginalPrice = getDetailTable().getCell(e.getRowIndex(),"confirmOriginalPrice" );//原币单价
    			ICell confirmAmtCell = getDetailTable().getCell(e.getRowIndex(),"confirmAmount" );//确认本币金额
    		    BigDecimal originalNum = FDCHelper.divide(confirmOriAmt, qty, pricePrecision,BigDecimal.ROUND_HALF_UP);
    		    BigDecimal confirmAmt  = FDCHelper.multiply(confirmOriAmt, rate);
                BigDecimal num = FDCHelper.multiply(originalNum, rate);
    		    confirmPrice.setValue(originalNum);
    		    confirmOriginalPrice.setValue(num);
    		    confirmAmtCell.setValue(confirmAmt);   			
    		}    			
    	}
    	
    	if (e.getColIndex() == getDetailTable().getColumn(MaterialConfirmContants.MATERIAL_NUMBER).getColumnIndex()) {
    		
			if(e.getValue()!=null){
				Object[] materials = filterMaterial(e);
				if (materials != null)
					insertDataToRow(materials);
			}
		}
    	
    	//如果事件行在最后一行，且前述代码把最后一行被删除了，直接return
    	if (kdtEntrys.getRowCount()<= e.getRowIndex() )
    		return;
    	
		getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.ORIGINAL_PRICE).getValue();
		if(getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.ORIGINAL_PRICE).getValue() == null){
			getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.PRICE).setValue(null);
			getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.AMOUNT).setValue(null);
		}
		
		if(getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.MATERIAL_NUMBER).getValue()==null){
			getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.MATERIAL_NAME).setValue(null);
			getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.MODEL).setValue(null);
			getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.UNIT).setValue(null);
			getDetailTable().getCell(e.getRowIndex(),"materialId").setValue(null);
		}
		if(getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.ORIGINAL_PRICE).getValue() !=null&&e.getValue()!=null){
			Object objRate = txtRate.getBigDecimalValue();
			Object objOPrice = getDetailTable().getCell(e.getRowIndex(),PartAMaterialContants.ORIGINAL_PRICE).getValue();
			BigDecimal oPrice = FDCHelper.toBigDecimal(objOPrice, pricePrecision);
			BigDecimal rate = (BigDecimal) objRate;
			BigDecimal price = FDCHelper.ZERO;
			
			if(objOPrice!=null&&objRate!=null){
				//本币单价=原币单价*rate
				price = oPrice.multiply(rate);
				getDetailTable().getCell(e.getRowIndex(),PartAMaterialContants.PRICE).setValue(price);
			}
			Object objNum = getDetailTable().getCell(e.getRowIndex(),PartAMaterialContants.QUANTITY).getValue();
			if(objNum != null){
				//BigDecimal num = new BigDecimal(objNum.toString());
				BigDecimal num = FDCHelper.toBigDecimal(objNum,unitPrecision);
				BigDecimal amount = price.multiply(num);
				getDetailTable().getCell(e.getRowIndex(),PartAMaterialContants.AMOUNT).setValue(amount);
				if(PartAUtils.getAmtSize(amount.toString()) > 11){
					MsgBox.showWarning(PartAUtils.getRes("outOfAmount"));
					getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.QUANTITY).setValue(FDCHelper.ZERO);
					getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.ORIGINAL_PRICE).setValue(FDCHelper.ZERO);
					getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.PRICE).setValue(FDCHelper.ZERO);
					getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.AMOUNT).setValue(FDCHelper.ZERO);
				}
//				getDetailTable().getCell(e.getRowIndex(),PartAMaterialContants.AMOUNT).setValue(amount);
			}
		}
		
		
		
		if(getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.QUANTITY).getValue() == null){
			getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.AMOUNT).setValue(null);
		}
    	countAmt(e);
    	setHeadAmt();
    	
    	
    	initAllAmount();
		initEntityTable();
    }
    
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
    	
    	super.actionRemoveLine_actionPerformed(e);
    	setHeadAmt();
    }
    
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
    }
    
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
    }
    
    public SelectorItemCollection getSelectors(){
    	SelectorItemCollection sic =super.getSelectors();
		//补充父类的sic
	    sic.add(new SelectorItemInfo("state"));
	    sic.add(new SelectorItemInfo("entrys.material.*"));
	    sic.add(new SelectorItemInfo("entrys.material.baseUnit.*"));
		return sic;
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionEdit_actionPerformed(e);
    	actionInsertLine.setEnabled(true);
    	actionAddLine.setEnabled(true);
    	actionRemoveLine.setEnabled(true);
    }

	protected void attachListeners() {
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MaterialConfirmBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	protected  void fetchInitData() throws Exception{
		String contractBillId = (String) getUIContext().get("contractBillId");

		Map initparam = new HashMap();
		if(contractBillId!=null){
			initparam.put("contractBillId",contractBillId);
		}else{
			if(editData!=null && editData.getMainContractBill()!=null){
				initparam.put("contractBillId",editData.getMainContractBill().getId().toString());
			}else{
				if(getUIContext().get("ID")!=null){
					initparam.put("ID",getUIContext().get("ID"));	
				}
				if(getUIContext().containsKey("isFromWorkflow")){
					if(getUIContext().get("DATAOBJECTS")!=null){
						HashMap objects = (HashMap)getUIContext().get("DATAOBJECTS");
						editData = (MaterialConfirmBillInfo)objects.get("billInfo");
						if(editData!=null&&editData.getMainContractBill()!=null)
							initparam.put("contractBillId",editData.getMainContractBill().getId().toString());
					}
				}	
			}		
		}
		Map initData = (Map)ActionCache.get("FDCBillEditUIHandler.initData");
		if(initData==null){
			initData = ((IFDCBill)getBizInterface()).fetchInitData(initparam);
		}
		//本位币
		baseCurrency = (CurrencyInfo)initData.get(FDCConstants.FDC_INIT_CURRENCY);
		//本位币
		company = (CompanyOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_COMPANY);
		if(company==null){
			company =  SysContext.getSysContext().getCurrentFIUnit();
		}
		//
		orgUnitInfo = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		if(orgUnitInfo==null){
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		}
		
		//合同单据
		contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
		
		//当前日期
		bookedDate = (Date)initData.get(FDCConstants.FDC_INIT_DATE);
		if(bookedDate==null){
			bookedDate = new Date(FDCDateHelper.getServerTimeStamp().getTime());
		}		
		//当前期间
		curPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_PERIOD);
		//是否已经冻结
		if(initData.get(FDCConstants.FDC_INIT_ISFREEZE)!=null){
			isFreeze = ((Boolean)initData.get(FDCConstants.FDC_INIT_ISFREEZE)).booleanValue();
		}
		//可录入期间
		if(isFreeze){
			canBookedPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_BOOKEDPERIOD);
		}else{
			canBookedPeriod = curPeriod;
		}		

		//工程项目
		curProject = (CurProjectInfo) initData.get(FDCConstants.FDC_INIT_PROJECT);
			
		//当前服务器日期
		serverDate = (Date)initData.get("serverDate");
		if(serverDate==null){
			serverDate = bookedDate;
		}
	}
	protected  void fetchInitParam() throws Exception{
		if(company==null){
			return ;
		}
		//启用成本财务一体化
		isIncorporation = FDCUtils.IsInCorporation(null, company.getId().toString());
	}
	public void onLoad() throws Exception {
		super.onLoad();
		initTable();
		
		if(editData.getMaterialContractBill() != null){
			materialConId = editData.getMaterialContractBill().getId().toString();
			this.txtRate.setPrecision(FDCClientHelper.getExRatePrecOfCurrency(editData.getMaterialContractBill().getCurrency().getId().toString()));
		}
		//材料合同F7
		setMaterialConF7();
		
		afterPressDeleteButton();
		
		loadToDateAmt();
		
		this.txtConfirmAmt.setPrecision(4);
		this.txtOrigialAmount.setPrecision(4);
		this.txtToDateConfirmAmt.setPrecision(4);
		this.txtPaidAmt.setPrecision(4);
		this.txtToDatePaidAmt.setPrecision(4);
		this.txtToDateSupplyAmt.setPrecision(4);
		this.txtSupplyAmt.setPrecision(4);	
		
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionPrint.setEnabled(true);
		
		initAllAmount();
		initEntityTable();
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		setEntryPrecision();	
	}
	public void loadFields() {
		super.loadFields();
		
		//金额在实体中未定义；取数时计算
		for(int i=0;i<getDetailTable().getRowCount();i++){
			if(getDetailTable().getRow(i).getUserObject() instanceof MaterialConfirmBillEntryInfo){
				MaterialConfirmBillEntryInfo entryInfo = (MaterialConfirmBillEntryInfo)getDetailTable().getRow(i).getUserObject();
				if(entryInfo.getPartAMaterialEntry()==null){
					getDetailTable().getRow(i).getCell(MaterialConfirmContants.PRICE).setValue(entryInfo.getPrice());
					getDetailTable().getRow(i).getCell(MaterialConfirmContants.ORIGINAL_PRICE).setValue(entryInfo.getOriginalPrice());
					getDetailTable().getRow(i).getCell(MaterialConfirmContants.UNIT).setValue(entryInfo.getMaterial().getBaseUnit().getName());
				}
			}
			Object qty = getDetailTable().getRow(i).getCell(MaterialConfirmContants.QUANTITY).getValue();
			Object prc = getDetailTable().getRow(i).getCell(MaterialConfirmContants.PRICE).getValue();
			if(qty != null && prc != null){
				BigDecimal quantity = new BigDecimal(qty.toString());
				BigDecimal price = new BigDecimal(prc.toString());
				getDetailTable().getRow(i).getCell(MaterialConfirmContants.AMOUNT).setValue(price.multiply(quantity));
			}
			
		}
		
	}

    private void initTable(){
    	
    	KDBizPromptBox kdtEntrys_materialNumber_PromptBox = new KDBizPromptBox();
    	kdtEntrys_materialNumber_PromptBox.setSelector(new MaterialPromptSelector(this));  // 使用自定义左树右表的F7 Added by owen_wen 2010-8-27
//		kdtEntrys_materialNumber_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		kdtEntrys_materialNumber_PromptBox.setEntityViewInfo(view);
		SelectorItemCollection sic = new  SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("model");
		sic.add("baseUnit.*");
		sic.add("*");
		kdtEntrys_materialNumber_PromptBox.setSelectorCollection(sic);
		kdtEntrys_materialNumber_PromptBox.setVisible(true);
		kdtEntrys_materialNumber_PromptBox.setEditable(true);
		kdtEntrys_materialNumber_PromptBox.setDisplayFormat("$number$");
		kdtEntrys_materialNumber_PromptBox.setEditFormat("$number$");
		kdtEntrys_materialNumber_PromptBox.setCommitFormat("$number$");
		kdtEntrys_materialNumber_PromptBox.setEnabledMultiSelection(true);
		KDTDefaultCellEditor kdtEntrys_materialNumber_CellEditor = new KDTDefaultCellEditor(
				kdtEntrys_materialNumber_PromptBox);
		this.kdtEntrys.getColumn(MaterialConfirmContants.MATERIAL_NUMBER).setEditor(
				kdtEntrys_materialNumber_CellEditor);
		ObjectValueRender kdtEntrys_materialNumber_OVR = new ObjectValueRender();
		kdtEntrys_materialNumber_OVR.setFormat(new BizDataFormat("$number$"));
		this.kdtEntrys.getColumn(MaterialConfirmContants.MATERIAL_NUMBER).setRenderer(
				kdtEntrys_materialNumber_OVR);

		//Add by zhiyuan_tang 2010/07/29  金额，单价只能输入数字
		KDFormattedTextField qty = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);					
		KDFormattedTextField price = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		
	    qty.setPrecision(2);
	    qty.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(qty);
		this.kdtEntrys.getColumn(MaterialConfirmContants.QUANTITY).setEditor(editor);

		price.setPrecision(2);
		price.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		KDTDefaultCellEditor priceEditor = new KDTDefaultCellEditor(price);

		this.kdtEntrys.getColumn(MaterialConfirmContants.ORIGINAL_PRICE).setEditor(priceEditor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.PRICE).setEditor(priceEditor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.AMOUNT).setEditor(priceEditor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.CONFIRM_AMOUNT).setEditor(priceEditor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.CONFIRM_ORI_AMT).setEditor(priceEditor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.CONFIRM_ORIGINAL_PRICE).setEditor(priceEditor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.CONFIRM_PRICE).setEditor(priceEditor);

		this.kdtEntrys.getColumn(MaterialConfirmContants.MATERIAL_NUMBER).getStyleAttributes().setBackground(
				FDCColorConstants.requiredColor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.MATERIAL_NUMBER).getStyleAttributes().setLocked(false);
		
		this.kdtEntrys.getColumn(MaterialConfirmContants.MATERIAL_NAME).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.MODEL).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.UNIT).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		
		
		this.kdtEntrys.getColumn(MaterialConfirmContants.ORIGINAL_PRICE).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.PRICE).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.AMOUNT).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.CONFIRM_PRICE).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.CONFIRM_AMOUNT).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.CONFIRM_ORI_AMT).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn(MaterialConfirmContants.CONFIRM_PRICE).getStyleAttributes().setLocked(true);
		
		this.kdtEntrys.getColumn(MaterialConfirmContants.CONFIRM_ORI_AMT).getStyleAttributes().setLocked(false);
		this.kdtEntrys.getColumn(MaterialConfirmContants.CONFIRM_ORI_AMT).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
		setEntryPrecision();
		txtDesc.setMaxLength(250);
		
		this.initSectionColumn();
    }

    
    private void setEntryPrecision(){
    	if(getDetailTable().getRowCount() > 0){
    		MaterialConfirmBillEntryCollection  cols = this.editData.getEntrys();
    		
    		KDFormattedTextField qty = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
    		KDFormattedTextField price = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
    		KDTDefaultCellEditor editor = null;
    		for(int i = 0;i<cols.size();i++){
    			MaterialInfo material = cols.get(i).getMaterial();
    			pricePrecision = material.getPricePrecision();
    			unitPrecision = material.getBaseUnit().getQtyPrecision();
    			
    		    qty.setPrecision(unitPrecision);
    		    qty.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    			editor = new KDTDefaultCellEditor(qty);
    			this.kdtEntrys.getCell(i, "quantity").setEditor(editor);
    			editor = null;
    			price.setPrecision(pricePrecision);
    			price.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    			editor = new KDTDefaultCellEditor(price);
    			
    			this.kdtEntrys.getCell(i, "amount").setEditor(editor);
    			this.kdtEntrys.getCell(i, "confirmAmount").setEditor(editor);
    			this.kdtEntrys.getCell(i, "confirmOriAmt").setEditor(editor);
    			this.kdtEntrys.getCell(i, "originalPrice").setEditor(editor);
    			this.kdtEntrys.getCell(i, "price").setEditor(editor);
    			this.kdtEntrys.getCell(i, "confirmPrice").setEditor(editor);
    			this.kdtEntrys.getCell(i, "confirmOriginalPrice").setEditor(editor);
    			
    			this.kdtEntrys.getCell(i, "amount").getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
				this.kdtEntrys.getCell(i, "confirmAmount").getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
				this.kdtEntrys.getCell(i, "confirmOriAmt").getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
				this.kdtEntrys.getCell(i, "originalPrice").getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
				this.kdtEntrys.getCell(i, "price").getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
				this.kdtEntrys.getCell(i, "confirmPrice").getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
				this.kdtEntrys.getCell(i, "confirmOriginalPrice").getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
				this.kdtEntrys.getCell(i, "quantity").getStyleAttributes().setNumberFormat(precisonFormat(unitPrecision));
    		}	    		
    	}
    }
    
    protected IObjectValue createNewData() {
    	MaterialConfirmBillInfo info=new MaterialConfirmBillInfo();
    	info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
    	try {
			info.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	info.setSupplyDate(new Date());
    	info.setMainContractBill(this.contractBill);
    	info.setState(FDCBillStateEnum.SAVED);
    	
    	EntityViewInfo view = new EntityViewInfo(); 
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("mainContractBill", this.contractBill.getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("toDateSupplyAmt"));
    	view.setFilter(filter);
    	view.setSelector(sic);
    	SorterItemCollection sorters = new SorterItemCollection();
    	SorterItemInfo item = new SorterItemInfo("createTime");
    	item.setSortType(SortType.DESCEND);
    	sorters.add(item);
    	view.setSorter(sorters );
    	
    	BigDecimal toDateSupplyAmt = FDCConstants.ZERO;
    	try {
    		MaterialConfirmBillCollection coll = MaterialConfirmBillFactory.getRemoteInstance().getMaterialConfirmBillCollection(view);
    		if(coll.size()>0){
    			toDateSupplyAmt = coll.get(0).getToDateSupplyAmt();
    		}
		} catch (BOSException e) {
			this.handleException(e);
		} 
    	info.setToDateSupplyAmt(toDateSupplyAmt);
    	
    	return info;
    }
    protected IObjectValue createNewDetailData(KDTable table) {
		if(curAddEntry==null){
			return new  MaterialConfirmBillEntryInfo();
		}else{
			return curAddEntry;
		}
	}
	
    private MaterialConfirmBillEntryCollection selectEntrys=null;
    private MaterialConfirmBillEntryInfo curAddEntry=null;
    public void setSelectEntrys(PartAMaterialEntryCollection partAMaterialEntrys){
    	this.curAddEntry=null;
    	this.selectEntrys=new MaterialConfirmBillEntryCollection();
    	MaterialConfirmBillEntryInfo entry = null;
    	for(Iterator iter=partAMaterialEntrys.iterator();iter.hasNext();){
    		PartAMaterialEntryInfo partAEntry=(PartAMaterialEntryInfo)iter.next();
    		entry = (MaterialConfirmBillEntryInfo)createNewDetailData(getDetailTable());
    		entry.setPartAMaterialEntry(partAEntry);
    		entry.setMaterial(partAEntry.getMaterial());
    		entry.setQuantity(partAEntry.getQuantity());
    		entry.setOriginalPrice(partAEntry.getOriginalPrice());
    		entry.setPrice(partAEntry.getPrice());
    		entry.setAmount(partAEntry.getAmount());
    		entry.setOriginalAmount(partAEntry.getQuantity().multiply(partAEntry.getOriginalPrice()));
    		this.selectEntrys.add(entry);
    	}
    }
    
    public MaterialConfirmBillEntryCollection getSelectEntrys(){
    	return this.selectEntrys; 
    }

	private void showMaterialDetailUI() throws BOSException {
		String mainContractId=FDCHelper.getF7Id(prmtMainContractBill);
    	String materialContractId=FDCHelper.getF7Id(prmtMaterialContractBill);
    	if(materialContractId==null){
    		FDCMsgBox.showWarning(this, PartAUtils.getRes("selectMaterialConNumber"));
    		SysUtil.abort();
    	}
    	materialConId = materialContractId;
    	Set detailSet=new HashSet();
    	for(int i=0;i<getDetailTable().getRowCount();i++){
    		ICell cell = getDetailTable().getRow(i).getCell("partAMaterialEntryId");
    		if(cell!=null&&cell.getValue()!=null){
    			String partAMaterialEntryId=cell.getValue().toString();
    			detailSet.add(partAMaterialEntryId);
    		}
    	}
    	PartAMaterialEntryCollection partAMaterialEntrys = MaterialDetailUI.showUI(mainContractId, materialContractId, detailSet);
    	if(partAMaterialEntrys==null||partAMaterialEntrys.size()==0){
    		SysUtil.abort();
    	}else{
    		for(int i = 0;i<partAMaterialEntrys.size();i++){
    			if(detailSet.contains(partAMaterialEntrys.get(i).getId().toString())){
    				SysUtil.abort();
    			}
    		}
    	}
    	setSelectEntrys(partAMaterialEntrys);
	}
	
	public void actionImportMaterialEntry_actionPerformed(ActionEvent e)
			throws Exception {
		showMaterialDetailUI();
		if(getSelectEntrys()==null){
    		return;
    	}
    	for(int i=0;i<this.getSelectEntrys().size();i++){
    		curAddEntry=this.getSelectEntrys().get(i);
    		super.addLine(this.kdtEntrys);
    		
    	}
    	setEntryPrecision();
    	curAddEntry=null;
    	getSelectEntrys().clear();
        setHeadAmt();
	}
	/**
	 * 动态计算金额，确认单价，确认金额
	 * @author pengwei_hou
	 * @param e
	 */
	private void countAmt(KDTEditEvent e) {
		int rowIndex = e.getRowIndex();
		//优化部分代码 Date: 2008-09-25
		Object objPrice = FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex, MaterialConfirmContants.PRICE).getValue(), pricePrecision);
		Object objQty = FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex, MaterialConfirmContants.QUANTITY).getValue(), unitPrecision);
		Object objCOPrc = FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex, MaterialConfirmContants.CONFIRM_ORIGINAL_PRICE).getValue(),
				pricePrecision);
		Object objCfmPrc = FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex, MaterialConfirmContants.CONFIRM_PRICE).getValue(),
				pricePrecision);

		BigDecimal rate = FDCHelper.toBigDecimal(txtRate.getBigDecimalValue());
		BigDecimal num = FDCHelper.ZERO;
		if(objQty != null)
			num = FDCHelper.toBigDecimal(objQty,pricePrecision);

		if(e.getValue()!=null){
			//金额
			if(objPrice != null && objQty != null){
				BigDecimal price = new BigDecimal(objPrice.toString());
				BigDecimal amount = price.multiply(num);
				getDetailTable().getCell(rowIndex, MaterialConfirmContants.AMOUNT).setValue(amount);
				if(PartAUtils.getAmtSize(amount.toString()) > 11){
					MsgBox.showWarning(PartAUtils.getRes("inputQuantityAgain"));
					getDetailTable().getCell(rowIndex,
							MaterialConfirmContants.QUANTITY).setValue(FDCHelper.ZERO);
					getDetailTable().getCell(rowIndex,
							MaterialConfirmContants.AMOUNT).setValue(FDCHelper.ZERO);
					getDetailTable().getCell(rowIndex,
							MaterialConfirmContants.CONFIRM_AMOUNT).setValue(FDCHelper.ZERO);
					txtSupplyAmt.setValue(FDCHelper.ZERO);
					txtConfirmAmt.setValue(FDCHelper.ZERO);
					SysUtil.abort();
				}
			}
			//确认单价
			if(objCOPrc!=null&&rate!=null){
				BigDecimal confirmOriginalPrice = new BigDecimal(objCOPrc.toString());
				BigDecimal confirmPrice = confirmOriginalPrice.multiply(rate);
				getDetailTable().getCell(rowIndex,
						MaterialConfirmContants.CONFIRM_PRICE).setValue(confirmPrice);
				objCfmPrc = confirmPrice;
			}	
			//确认金额
			if(e.getColIndex() != getDetailTable().getColumnIndex("confirmOriAmt")){
			if(objCfmPrc != null){
				if(num != null && objCfmPrc !=null){
					/***
					 * 此处由于外币核算时会出现问题，更改过：
					 * 以前代码:
					 * BigDecimal confirmOriAmt = cfmPrc.multiply(num);
                     * BigDecimal confirmAmt = confirmOriAmt.multiply(rate);
                     * 更改为：
                     *if(rate.compareTo(BigDecimal.ZERO) != 0)
					 *confirmOriAmt = confirmAmt.divide(rate);
					 */
					
					BigDecimal cfmPrc = new BigDecimal(objCfmPrc.toString());
//					BigDecimal confirmAmt = cfmPrc.multiply(num);
					BigDecimal confirmAmt = FDCHelper.toBigDecimal(cfmPrc.multiply(num),pricePrecision);
					BigDecimal confirmOriAmt = FDCHelper.ZERO;
					if(rate.compareTo(FDCHelper.ZERO) != 0){
						confirmOriAmt = FDCHelper.divide(confirmAmt, rate, pricePrecision, BigDecimal.ROUND_HALF_UP);
					}
						
//					BigDecimal confirmOriAmt = cfmPrc.multiply(num);
//					BigDecimal confirmAmt = confirmOriAmt.multiply(rate);
						getDetailTable().getCell(rowIndex,
							MaterialConfirmContants.CONFIRM_AMOUNT).setValue(confirmAmt);
					getDetailTable().getCell(rowIndex,
							MaterialConfirmContants.CONFIRM_ORI_AMT).setValue(confirmOriAmt);
					if(PartAUtils.getAmtSize(confirmAmt.toString()) > 11){
						MsgBox.showWarning(PartAUtils.getRes("inputOriginalPriceAgain"));
						getDetailTable().getCell(rowIndex,
								MaterialConfirmContants.CONFIRM_ORIGINAL_PRICE).setValue(FDCHelper.ZERO);
						getDetailTable().getCell(rowIndex,
								MaterialConfirmContants.CONFIRM_PRICE).setValue(FDCHelper.ZERO);
						getDetailTable().getCell(rowIndex,
								MaterialConfirmContants.CONFIRM_AMOUNT).setValue(FDCHelper.ZERO);
						getDetailTable().getCell(rowIndex,
								MaterialConfirmContants.CONFIRM_ORI_AMT).setValue(FDCHelper.ZERO);
						txtConfirmAmt.setValue(FDCHelper.ZERO);
						txtOrigialAmount.setValue(FDCHelper.ZERO);
						SysUtil.abort();
					}
				}
			}}
		}
		if(objQty == null){
			getDetailTable().getCell(rowIndex,
					MaterialConfirmContants.AMOUNT).setValue(null);
			getDetailTable().getCell(rowIndex,
					MaterialConfirmContants.CONFIRM_AMOUNT).setValue(null);
			getDetailTable().getCell(rowIndex,
					MaterialConfirmContants.CONFIRM_ORI_AMT).setValue(null);
		}
		if(objCOPrc == null){
			getDetailTable().getCell(rowIndex,
					MaterialConfirmContants.CONFIRM_PRICE).setValue(null);
			getDetailTable().getCell(rowIndex,
					MaterialConfirmContants.CONFIRM_AMOUNT).setValue(null);
			getDetailTable().getCell(rowIndex,
					MaterialConfirmContants.CONFIRM_ORI_AMT).setValue(null);
		}
	}
	
	/**
	 * 计算表头本次供货金额，本次确认金额
	 */
	private void setHeadAmt(){
		//本次供货金额
		BigDecimal amt = FDCHelper.ZERO;
		// 本次确认本币金额
		BigDecimal confirmAmt = FDCHelper.ZERO;
		// 本次确认原币金额
		BigDecimal confirmOriAmt = FDCHelper.ZERO;
		
		for (int i = 0; i < getDetailTable().getRowCount(); i++) {
			Object objAmt = getDetailTable().getCell(i, MaterialConfirmContants.AMOUNT).getValue();
			BigDecimal amount = FDCHelper.toBigDecimal(objAmt);
			amt = amt.add(amount);
			Object objConfirmAmt = getDetailTable().getCell(i, MaterialConfirmContants.CONFIRM_AMOUNT).getValue();
			confirmAmt = confirmAmt.add(FDCHelper.toBigDecimal(objConfirmAmt));
			
			Object objConfirmOriAmt = getDetailTable().getCell(i, MaterialConfirmContants.CONFIRM_ORI_AMT).getValue();
			confirmOriAmt = confirmOriAmt.add(FDCHelper.toBigDecimal(objConfirmOriAmt));
			
		}
		this.txtSupplyAmt.setValue(amt);
		this.txtConfirmAmt.setValue(confirmAmt);
		this.txtOrigialAmount.setValue(confirmOriAmt);
		
	}
	
	protected void updateButtonStatus() {
		super.updateButtonStatus();
		
		this.pkSupplyDate.setSupportedEmpty(false);
		this.txtToDatePaidAmt.setSupportedEmpty(false);
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		actionCreateFrom.setVisible(false);
		actionCreateFrom.setEnabled(false);
		actionPre.setVisible(false);
		actionPre.setEnabled(false);
		actionFirst.setVisible(false);
		actionFirst.setEnabled(false);
		actionLast.setVisible(false);
		actionLast.setEnabled(false);
		actionNext.setVisible(false);
		actionNext.setEnabled(false);
		actionTraceUp.setVisible(false);
		actionTraceUp.setEnabled(false);
		actionTraceDown.setVisible(false);
		actionTraceDown.setEnabled(false);
		actionAddNew.setVisible(false);
		actionAddNew.setEnabled(false);
		actionCopy.setVisible(false);
		actionCopy.setEnabled(false);
		
		menuItemCopyFrom.setVisible(false);
		menuItemCopyFrom.setEnabled(false);
		menuSubmitOption.setVisible(false);
		menuSubmitOption.setEnabled(false);
		menuView.setVisible(false);
    	menuBiz.setVisible(false);
    	
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		
		if(getNumberCtrl().isEnabled()){
			
			if(this.txtNumber.getText().length() == 0 || txtNumber.getText() == null){
				MsgBox.showWarning(this, PartAUtils.getRes("billNumberNotNull"));
				SysUtil.abort();
			}
			
		}
		
		
		
		if(this.editData != null && getDetailTable().getRowCount() < 1) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("atLeastOneEntry"));
			SysUtil.abort();
		}
		
	    
		if (editData.getDescription() != null && editData.getDescription().length() > 300) {
			MsgBox.showWarning(this, PartAUtils.getRes("desLimit300Char"));
			SysUtil.abort();
		}
		if(this.prmtMaterialContractBill.getValue() == null){
			MsgBox.showWarning(this, PartAUtils.getRes("materialConNumberNotNull"));
			SysUtil.abort();
		}
		
		//分录校验
		for(int i=0;i<getDetailTable().getRowCount();i++){
			IRow row = getDetailTable().getRow(i);
			//Add by zhiyuan_tang 2010/07/29  添加对物料编码不能为空的校验
			if(row.getCell(MaterialConfirmContants.MATERIAL_NUMBER).getValue() == null){
				String info = PartAUtils.getRes("argsCanNotBeNull");
				String[] args =new String[]{(i+1)+"", "物料编码"};
				MsgBox.showWarning(this, FDCClientHelper.formatMessage(info, args));
				SysUtil.abort();
			}
			if(row.getCell(MaterialConfirmContants.QUANTITY).getValue() == null
					|| row.getCell(MaterialConfirmContants.CONFIRM_ORIGINAL_PRICE).getValue() == null){
				String info = PartAUtils.getRes("QuatityOrConfirmOriginalPriceIsNull");
				String[] args =new String[]{(i+1)+""};
				MsgBox.showWarning(this, FDCClientHelper.formatMessage(info, args));
				SysUtil.abort();
			}
			Object desc = row.getCell("desc").getValue();
	        if(desc != null && desc.toString().length() > 80){
	        	String info = PartAUtils.getRes("moreRemark");
	        	String[] args = new String[]{Integer.toString(i+1)};
	        	MsgBox.showWarning(FDCClientHelper.formatMessage(info, args));
	        	SysUtil.abort();
	        }
		}
		
		//校验确认单材料数量是否大于计划数量
		if(editData.getEntrys().get(0)!= null){
			verifyQuantity(e);
		}
	}
	
	private void verifyQuantity(ActionEvent e) throws Exception{		
		int rowCount = editData.getEntrys().size();		
		List partAEntryIdList = new ArrayList();
		for(int i=0; i<rowCount; i++){
			if(editData.getEntrys().get(i).getPartAMaterialEntry()!=null)
				partAEntryIdList.add(editData.getEntrys().get(i).getPartAMaterialEntry().getId().toString());
		}
		
		//取当前单分录材料明细单计划材料数量
		IRowSet set = new JdbcRowSet();
		if (partAEntryIdList.size() > 0){
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select FID,FQuantity from T_PAM_PartAMaterialEntry where FID in (");
			builder.appendParam(partAEntryIdList.toArray());
			builder.appendSql(")");
			set = builder.executeQuery();
		}
		
		while(set.next()){
			String id = set.getString("FID");	
			int quantity = set.getInt("FQuantity");
			numMap.put(id, quantity+"");
		}
		
		//变动后的数量与计划数量比较，超过则提示
		BigDecimal planQty = FDCHelper.ZERO;
		BigDecimal confirmQty = FDCHelper.ZERO;
		StringBuffer sb = new StringBuffer();
		sb.append(PartAUtils.getRes("materialNumOutOfPlanNum"));
		for(int i=0; i<rowCount; i++){
			if(this.editData.getEntrys().get(i).getPartAMaterialEntry()!=null){
				String partAId = this.editData.getEntrys().get(i).getPartAMaterialEntry().getId().toString();
				Object actualNum = getDetailTable().getRow(i).getCell(MaterialConfirmContants.QUANTITY).getValue();
				Object planNum = numMap.get(partAId);
				if(actualNum != null && planNum != null){
					BigDecimal n1 = FDCHelper.toBigDecimal(planNum);
					BigDecimal n2 = FDCHelper.toBigDecimal(actualNum);
					if(n2.compareTo(n1)>0){
						planQty = planQty.add(n1);
						confirmQty = confirmQty.add(n2);
						sb.append(editData.getEntrys().get(i).getPartAMaterialEntry().getMaterial().getNumber());
						sb.append(", ");
					}
				}
			}
		}
		
		sb.replace(sb.length()-2, sb.length()-1, " !");
		if(confirmQty.compareTo(planQty)>0){
			MsgBox.showWarning(sb.toString());
		}
	}
	
	public void onShow() throws Exception {
		super.onShow();
		String state = getOprtState();
		if(STATUS_ADDNEW.equals(state)){
    		setUITitle(getRes("confmTitleAddNew"));
    		actionEdit.setEnabled(false);
    		actionRemove.setEnabled(false);
    		
    		actionSave.setEnabled(true);
    		actionSubmit.setEnabled(true);
    		actionAddLine.setEnabled(true);
    		actionRemoveLine.setEnabled(true);
    		actionInsertLine.setEnabled(true);
    		
    	}else if(STATUS_EDIT.equals(state)){
    		
    		setUITitle(getRes("confmTitleEdit"));
    		actionEdit.setEnabled(false);
    		if(editData.getState() == FDCBillStateEnum.SUBMITTED){
        		actionSave.setEnabled(false);
        	}else{
        		actionSave.setEnabled(true);
    		}
    		actionSubmit.setEnabled(true);
    		actionInsertLine.setEnabled(true);
        	actionAddLine.setEnabled(true);
        	actionRemoveLine.setEnabled(true);
        	
    	}else if(STATUS_VIEW.equals(state)){
    		
    		setUITitle(getRes("confmTitleView"));
	    	actionSave.setEnabled(false);
	    	actionSubmit.setEnabled(false);
	    	actionInsertLine.setEnabled(false);
	    	actionAddLine.setEnabled(false);
	    	actionRemoveLine.setEnabled(false);
	    	
    	}else{
    		
    		setUITitle(getRes("confmTitleView"));
    		actionEdit.setEnabled(false);
    		actionRemove.setEnabled(false);
    	}
	}

	
	private void setMaterialConF7() throws BOSException{
		
		CtrlUnitInfo CU = SysContext.getSysContext().getCurrentCtrlUnit();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		//显示甲供材合同,审批状态
		filter.getFilterItems().add(new FilterItemInfo("isPartAMaterialCon", Boolean.TRUE,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		
		//按CU隔离
		if(!CU.getId().toString().equals(OrgConstants.DEF_CU_ID)){
			filter.getFilterItems().add(new FilterItemInfo("CU.id", CU.getId().toString()));
		}
		view.setFilter(filter);
		
		SelectorItemCollection selector=new  SelectorItemCollection();
		selector.add("number");
		selector.add("name");
		selector.add("partB.name");
		selector.add("partB.number");
		selector.add("id");
		selector.add("amount");
		selector.add("currency.name");
		selector.add("exRate");
		prmtMaterialContractBill.setSelectorCollection(selector);
		prmtMaterialContractBill.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7MeterialConBillQuery");
		prmtMaterialContractBill.setEntityViewInfo(view);
		prmtMaterialContractBill.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				
				//当重新选择合同时，表头金额重新计算
//				setHeadAmt();
				
				ContractBillInfo data=(ContractBillInfo)eventObj.getNewValue();
				if(data!=null){
					contractInfo = data;
					txtMaterialContractName.setText(data.getName());
					if(data.getPartB()!=null){
						txtMaterialConPartB.setText(data.getPartB().getName());
						if(data.getPartB().getNumber()!=null)
							//txtMaterialConPartB.setText(data.getPartB().getNumber()+"-"+data.getPartB().getName());
						if(data.getPartB().getNumber()==null){
							//txtMaterialConPartB.setText(data.getPartB().getName());
						}
					}
					if(data.getAmount()!=null)
						txtMaterialConAmt.setValue(data.getAmount());
					if(data.getCurrency()!=null)
						txtCurrency.setText(data.getCurrency().getName());
					if(data.getExRate()!=null)
						txtRate.setValue(data.getExRate());
					
					if(!data.getId().toString().equals(materialConId) && materialConId != null){
						getDetailTable().removeRows();
						editData.getEntrys().clear();
						txtSupplyAmt.setValue(null);
						txtConfirmAmt.setValue(null);
						txtOrigialAmount.setValue(null);
						txtToDateSupplyAmt.setValue(supply);
						txtToDateConfirmAmt.setValue(confirm);
						txtToDatePaidAmt.setValue(paid);
					}
				}else{
					data = editData.getMaterialContractBill();
					txtMaterialContractName.setText(data.getName());
					if(data.getPartB()!=null){
						if(data.getPartB().getNumber()!=null)
							txtMaterialConPartB.setText(data.getPartB().getNumber()+"-"+data.getPartB().getName());
						if(data.getPartB().getNumber()==null){
							txtMaterialConPartB.setText(data.getPartB().getName());
						}
					}
					if(data.getAmount()!=null)
						txtMaterialConAmt.setValue(data.getAmount());
					if(data.getCurrency()!=null)
						txtCurrency.setText(data.getCurrency().getName());
					if(data.getExRate()!=null)
						txtRate.setValue(data.getExRate());
					
					if(!data.getId().toString().equals(materialConId) && materialConId != null){
						getDetailTable().removeRows();
						editData.getEntrys().clear();
						txtSupplyAmt.setValue(null);
						txtConfirmAmt.setValue(null);
						txtOrigialAmount.setValue(null);
						txtToDateSupplyAmt.setValue(supply);
						txtToDateConfirmAmt.setValue(confirm);
						txtToDatePaidAmt.setValue(paid);
					}
				}
			}
		});

		txtPaidAmt.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				//已供货
				String txtPaidAmtStr = txtPaidAmt.getText();
				if(txtPaidAmtStr.length() >0){
					BigDecimal paidAmt = PartAUtils.toBigDecimal(txtPaidAmtStr);
					if(paid == null)
						txtToDatePaidAmt.setValue(paidAmt);
					txtToDatePaidAmt.setValue(FDCNumberHelper.add(paidAmt, paid));
				}
			}
		});
		
		txtSupplyAmt.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				//已确定
				String txtSupplyAmtStr = txtSupplyAmt.getText();
				if(txtSupplyAmtStr.length() >0){
					BigDecimal supplyAmt = PartAUtils.toBigDecimal(txtSupplyAmtStr);
					if(supply == null)
						txtToDateSupplyAmt.setValue(supplyAmt);
					txtToDateSupplyAmt.setValue(FDCNumberHelper.add(supplyAmt, supply));
				}
			}
		});
		
		txtConfirmAmt.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				//已付款
				String txtConfirmAmtStr = txtConfirmAmt.getText();
				if(txtConfirmAmtStr.length() >0){
					BigDecimal confirmAmt = PartAUtils.toBigDecimal(txtConfirmAmtStr);
					if(confirm == null)
						txtToDateConfirmAmt.setValue(confirmAmt);
					txtToDateConfirmAmt.setValue(FDCNumberHelper.add(confirmAmt, confirm));
				}
			}
		});
	}
	
	/**
	 * 当状态为审批时，取历史审批累计金额
	 * @author pengwei_hou Date 2008-09-19
	 */
	private void loadToDateAmt(){
		String mainConId = this.contractBill.getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
		filter.getFilterItems().add(new FilterItemInfo("createTime", editData.getCreateTime(), CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("mainContractBill.id", mainConId));
		view.setFilter(filter);
		
		try {
			MaterialConfirmBillCollection materialConfirmBillColl = MaterialConfirmBillFactory
				.getRemoteInstance().getMaterialConfirmBillCollection(view);
			
			for(Iterator iter = materialConfirmBillColl.iterator(); iter.hasNext();){
				
				MaterialConfirmBillInfo entry = (MaterialConfirmBillInfo) iter.next();
				supply =supply.add(FDCHelper.toBigDecimal(entry.getSupplyAmt()));
				confirm = confirm.add(FDCHelper.toBigDecimal(entry.getConfirmAmt()));
				paid = paid.add(FDCHelper.toBigDecimal(entry.getPaidAmt()));
			}
				
			} catch (BOSException e) {
				e.printStackTrace();
		}
		if(getOprtState() == STATUS_ADDNEW){
			txtToDateConfirmAmt.setValue(confirm);
			txtToDateSupplyAmt.setValue(supply);
			txtToDatePaidAmt.setValue(paid);
			
		}
	}

	/**
	 * 当按下Delete键删除单元格数据时，同时删除其它关联数据
	 * @author pengwei_hou Date 2008-09-25
	 */
	private void afterPressDeleteButton(){
		
		kdtEntrys.setBeforeAction(new BeforeActionListener(){

			public void beforeAction(BeforeActionEvent e) {
				if(BeforeActionEvent.ACTION_DELETE == e.getType()){
					Set set = new HashSet();
					set.add(MaterialConfirmContants.QUANTITY);
					set.add(MaterialConfirmContants.CONFIRM_ORIGINAL_PRICE);
					for (int i = 0; i <kdtEntrys.getSelectManager().size(); i++)
					{
						KDTSelectBlock block = kdtEntrys.getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
						{
							for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
								String colKey = getDetailTable().getColumnKey(colIndex);
								if(set.contains(colKey)){
									KDTEditEvent event = new KDTEditEvent(kdtEntrys, null, null, rowIndex, colIndex, true, 1);
									getDetailTable().getCell(rowIndex, colIndex).setValue(null);
									try {
										kdtEntrys_editStopped(event);
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
			
		});
	}
	
	private String getRes(String resName){
		return PartAUtils.getRes(resName);
	}
	
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		setEntryPrecision();
		if(getOprtState().equals(OprtState.ADDNEW)){
        	KDTable table=this.kdtEntrys; //tblMain
    		IRow footRow = null;
    		if(null != table.getFootRow(0)){ //如果不存在表脚列
    			footRow = table.getFootRow(0);
    			footRow.getCell("amount").setValue(FDCHelper.ZERO);
    		}
        }
	}
	
	
	/**
	 * 打印
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null
				&& !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData
						.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		MaterialPrintDataProvider data = new MaterialPrintDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * 打印预览
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		logger.info("打印预览");
		ArrayList idList = new ArrayList();
		if (editData != null
				&& !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData
						.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		MaterialPrintDataProvider data = new MaterialPrintDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	// 获得无文本合同套打对应的目录
	protected String getTDFileName() {
		return "/bim/fdc/material/MaterialContractBillPrint";
	}

	// 对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.material.app.MaterialContractBillPrintQuery");
	}
	
	/**
	 * 从材料订货单导入 add by ywm 2010-7-3
	 */
	public void actionImportMaterialOrder_actionPerformed(ActionEvent e) throws Exception {
		if (this.prmtMaterialContractBill.getValue()== null){
			FDCMsgBox.showInfo("请选择材料合同编码！");
			this.prmtMaterialContractBill.requestFocus(true);
			SysUtil.abort();
		}
		
		UIContext importUIContext = new UIContext(this);
		importUIContext.put(UIContext.OWNER, this);
		IUIWindow importUIWindow = UIFactory.createUIFactory().create("com.kingdee.eas.fdc.material.client.ImpMatOrderBillUI", importUIContext, null,"VIEW");
		importUIWindow.show();
		if (getSelectedOrderBillIds_sum4RevQty() == null || getSelectedOrderBillIds_sum4RevQty().size() == 0)
			return ;
		else{   //据据选择要ID，把材料订货单汇总分录行导入
			doImportMatOrderData(getSelectedOrderBillIds_sum4RevQty());
		}
	}

	/**
	 * 根据物料订单选中的分录行的ID集，填充确认单的分录
	 * 
	 * @param orderEntryID_sum4RevQty
	 *            订单的分录ID与到货数量组成的Map
	 * @throws BOSException
	 */
	protected void doImportMatOrderData(Map orderEntryID_sum4RevQty) throws BOSException
	{	   
	    if(contractInfo != null){
	    	editData.setMaterialContractBill(contractInfo);
	    }
	    
	    Map materialMap = new HashMap();
	    for(int i=0;i<editData.getEntrys().size();i++){
	    	MaterialConfirmBillEntryInfo confirmEntryInfo = editData.getEntrys().get(i);
	    	materialMap.put(confirmEntryInfo.getMaterial().getId().toString(), Boolean.TRUE);
	    }
	    
	    if (editData.getSupplyAmt()==null)
	    	editData.setSupplyAmt(FDCConstants.ZERO);
	    
	    EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// 必须重新包装成真正的HashSet, 因为keySet()返回的Set是未序列化的
		Set idSet = new HashSet(orderEntryID_sum4RevQty.keySet()); 
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		evi.setFilter(filter);
		evi.setSelector(getImpOrderSelector());
		IMaterialOrderBizBillEntry iMatConfirm = MaterialOrderBizBillEntryFactory.getRemoteInstance();
	    MaterialOrderBizBillEntryCollection mc = iMatConfirm.getMaterialOrderBizBillEntryCollection(evi);
	    for(int i =0;i<mc.size();i++){
	    	MaterialOrderBizBillEntryInfo orderEntryInfo = mc.get(i);
	    	if(orderEntryInfo.getMaterialNumber()!=null&&!materialMap.containsKey(orderEntryInfo.getMaterialNumber().getId().toString())){
	    		MaterialConfirmBillEntryInfo confirmEntryInfo = new MaterialConfirmBillEntryInfo();
		        confirmEntryInfo.setId(BOSUuid.create(confirmEntryInfo.getBOSType()));
		        confirmEntryInfo.setMaterial(orderEntryInfo.getMaterialNumber());
		        // 取订货单分录中的单价，因为订货单分录中没有原币价格
		        confirmEntryInfo.setOriginalPrice(orderEntryInfo.getPrice());
		        confirmEntryInfo.setPrice(orderEntryInfo.getPrice());
		        confirmEntryInfo.setAmount(orderEntryInfo.getAmount());
		        BigDecimal sum4RevAmt = FDCHelper.toBigDecimal(orderEntryID_sum4RevQty.get(orderEntryInfo.getId().toString()));
		        confirmEntryInfo.setQuantity(orderEntryInfo.getQuantity().subtract(sum4RevAmt));
		        confirmEntryInfo.setOriginalAmount(confirmEntryInfo.getQuantity().multiply(confirmEntryInfo.getOriginalPrice()));		        
		        confirmEntryInfo.setAmount(confirmEntryInfo.getQuantity().multiply(confirmEntryInfo.getPrice()));		        
		        confirmEntryInfo.setEntrySrcBillID(orderEntryInfo.getId().toString());
		        editData.getEntrys().add(confirmEntryInfo);	
		        editData.setToDateSupplyAmt(editData.getToDateSupplyAmt().add((confirmEntryInfo.getOriginalAmount())));
		        editData.setSupplyAmt(editData.getSupplyAmt().add(orderEntryInfo.getPrice().multiply(orderEntryInfo.getQuantity())));
	    	}
	    }
	    this.editData.setNumber(this.txtNumber.getText()); // 新增时单据时，若要没有这句，调用loadField()方法后，编码控件的值会被清空掉。
		this.loadFields();
	    setHeadAmt();
	    setEntryPrecision();
	}
	private  SelectorItemCollection getImpOrderSelector()
	{
		SelectorItemCollection cl = new SelectorItemCollection();
		cl.add(new SelectorItemInfo("*"));
		cl.add(new SelectorItemInfo("materialNumber.*"));
		cl.add(new SelectorItemInfo("materialNumber.baseUnit.name"));
		return cl;
	}

	/**
	 * Map： Key是订单分录的ID, value是累计到货数量
	 */
	private Map orderEntryID_sum4RevQty;

	public Map getSelectedOrderBillIds_sum4RevQty() {
		return orderEntryID_sum4RevQty;
	}
	
	public void setSelectedOrderBillIds_sum4RevQty(Map orderEntryID_sum4RevAmt) {
		this.orderEntryID_sum4RevQty = orderEntryID_sum4RevAmt;
	}
	
	/**
	 * 初始化标段列
	 * @author owen_wen 2010-12-08
	 */
	private void initSectionColumn(){
		KDComboBox sectionBox = new KDComboBox();
		sectionBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.SectionEnum").toArray());
		KDTDefaultCellEditor sectionBoxCellEditor = new KDTDefaultCellEditor(sectionBox);
		this.kdtEntrys.getColumn("section").setEditor(sectionBoxCellEditor);
	}
	
	//add by duyu 2011-8-15  增加合计行
	public void initEntityTable(){
		IRow footRow = FDCTableHelper.generateFootRow(kdtEntrys);
		if(sumAmount!=null){
			footRow.getCell("confirmAmount").setValue(sumAmount);
		}else{
			footRow.getCell("confirmAmount").setValue(FDCHelper.ZERO);
		}
		footRow.getCell("confirmAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    }
    public void initAllAmount(){
    	sumAmount = FDCHelper.ZERO;
    	kdtEntrys.checkParsed();
		int count = kdtEntrys.getRowCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				sumAmount = sumAmount.add(kdtEntrys.getRow(i).getCell("confirmAmount").getValue() != null ? (BigDecimal) kdtEntrys.getRow(i).getCell("confirmAmount").getValue() : FDCHelper.ZERO);
			}
		}
    }
}