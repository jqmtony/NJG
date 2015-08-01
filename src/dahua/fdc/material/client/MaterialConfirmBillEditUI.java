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
 * ����ȷ�ϵ��༭
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
    /**ȷ�ϵ���ѡ��Ĳ��Ϻ�ͬIDֻ��һ��*/
    private String materialConId = null;
    
    //���ҽ��
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
			MsgBox.showWarning(this, "����ѡ����Ϻ�ͬ");
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
	 * ����¼���в�������
	 * @param rowIndex ���������
	 * @param materials ��������������飬������ĳ��Ϊnull����ʾ���ظ�����Ҫ����
	 * @author owen_wen 2010-6-23
	 */
	private void insertDataToRow(Object[] materials) {
	
		//��ȡ��е��к�
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
			
			//����Ƕ�ѡ(��i>0ʱ)����Ҫ�ж��Ƿ�ҪaddLine��������һ�з�¼��
			if(i > 0){
				if(materialInfo != null)
				{
					table.addRow(table.getSelectManager().getActiveRowIndex()); //�ڻ�д����һ�� 
					row = table.getRow(table.getSelectManager().getActiveRowIndex()); 
					IObjectValue detailData = createNewDetailData(table);
					row.setUserObject(detailData);
				}
				else
				{
					continue;
				}
			}
			
			// �� i==0, and material != null �������ֱ�ӽ�materialInfo���뵽��ǰ����
			row.getCell(MaterialConfirmContants.MATERIAL_NUMBER).setValue(materialInfo.getNumber());
			row.getCell(MaterialConfirmContants.MATERIAL_NAME).setValue(materialInfo.getName());
			row.getCell(MaterialConfirmContants.MODEL).setValue(materialInfo.getModel());
			row.getCell(MaterialConfirmContants.UNIT).setValue(materialInfo.getBaseUnit().getName());
			
			//�˴���������ӵ����ϣ�û�е��ۺ�ԭ�Ҽۣ�������Ϊ0 
			row.getCell(MaterialConfirmContants.PRICE).setValue(FDCConstants.ZERO);
			row.getCell(MaterialConfirmContants.ORIGINAL_PRICE).setValue(FDCConstants.ZERO);
			row.getCell("materialId").setValue(materialInfo.getId());
		
			KDFormattedTextField qty = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);					
			KDFormattedTextField price = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
			
			pricePrecision = materialInfo.getPricePrecision(); //���ϵĵ��۾���
			unitPrecision = materialInfo.getBaseUnit().getQtyPrecision(); // ����->������λ->��������
			
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
	 * ������F7�ж�ѡ�������������ɸѡ���ˣ������������ĳ���ϣ����丳Ϊnull<p>
	 * ���������������������ͬ���ϱ����������˵�
	 * @param e KDTEditEvent KDTable�༭�¼� 
	 * @author owen_wen 2010-6-23
	 * @return ɸѡ�����������
	 */
	private Object[] filterMaterial(KDTEditEvent e){
		KDTable table = getDetailTable();
		if( (e.getValue() != null) 
				&& (e.getValue() instanceof Object[])){
			Object[] materials = (Object[]) e.getValue();
							
			int activeRow = table.getSelectManager().getActiveRowIndex();
			
			//�����ظ�����
			for(int i = 0; i < materials.length; i++)
			{
				MaterialInfo material = null;
				if(materials[i] instanceof MaterialInfo)
					material = (MaterialInfo)materials[i];
				
				for(int j = 0; j < table.getRowCount(); j++)
				{
					//���j�ƶ�������ϣ�����Ҫ�ж��Ƿ��ظ�����Ϊ�û���п���ѡ���˶������
					if(j != activeRow)
					{
						if(table.getRow(j).getCell(MaterialConfirmContants.MATERIAL_NUMBER).getValue() == null)
							continue;   //����û�����ϵ��У�����
												
						String preMateralNumber = ""; //��ȡ��j�е����ϱ���
						
						//�п����Ǹ�����δ����������У�getValue�õ��Ļ�����MaterialInfo�������Ҫ�жϴ���
						if (table.getRow(j).getCell(MaterialConfirmContants.MATERIAL_NUMBER).getValue() instanceof MaterialInfo) 
							preMateralNumber = ((MaterialInfo)table.getRow(j).getCell(MaterialConfirmContants.MATERIAL_NUMBER).getValue()).getNumber();
						else
							preMateralNumber = table.getRow(j).getCell(MaterialConfirmContants.MATERIAL_NUMBER).getValue().toString();
																		
						if(material.getNumber().equals(preMateralNumber)){
							materials[i] = null; //����i�����ϣ����Ϊ��
							break; 		//�����ظ�,�Һ�ͬ�ظ���ֱ������ѭ��������һ�����Ͻ����ж�
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
     * ֻ��¼ԭ�ң����㱾λ��
	 *
     * ������������ȷ�ϱ��ҵ���/ȷ��ԭ�ҵ��ۡ�����ȷ�Ͻ��⼸���ֶ�֮���Զ����㣺<p>
     *	1.	�����������ı�ʱ����ȷ�ϱ��ҵ���/ȷ��ԭ�ҵ��ۡ����䣬�Զ����㡰ȷ�Ͻ� <p>
     *	2.	����ȷ�ϱ��ҵ���/ȷ��ԭ�ҵ��ۡ��ı�ʱ�������������䣬�Զ����㡰ȷ�Ͻ� <P>
     *	3.	����ȷ�Ͻ��ı�ʱ�������������䣬�Զ����㡰ȷ�ϱ��ҵ���/ȷ��ԭ�ҵ��ۡ�<p>
	 *
     *	ע�⣺�������ı�ʱ��ǰ��ġ����ҽ�����ԭ�ҽ�������֮����Զ������ϵΪ��������/ԭ�ҵ��ۡ����䣬�Զ����㡰��
     */
    protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
    	
    	if(e.getColIndex() == getDetailTable().getColumnIndex("confirmOriAmt")){
    		
    		if(getDetailTable().getCell(e.getRowIndex(),"confirmOriAmt" ).getValue() != null && 
    				getDetailTable().getCell(e.getRowIndex(),"quantity" ).getValue() != null ){
    			BigDecimal confirmOriAmt =  FDCHelper.toBigDecimal(getDetailTable().getCell(e.getRowIndex(),"confirmOriAmt" ).getValue(),8);
    			BigDecimal qty =  FDCHelper.toBigDecimal(getDetailTable().getCell(e.getRowIndex(),"quantity" ).getValue(),8);
    			BigDecimal rate = this.txtRate.getBigDecimalValue();
    			ICell confirmPrice = getDetailTable().getCell(e.getRowIndex(),"confirmPrice" );//����
    			ICell confirmOriginalPrice = getDetailTable().getCell(e.getRowIndex(),"confirmOriginalPrice" );//ԭ�ҵ���
    			ICell confirmAmtCell = getDetailTable().getCell(e.getRowIndex(),"confirmAmount" );//ȷ�ϱ��ҽ��
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
    	
    	//����¼��������һ�У���ǰ����������һ�б�ɾ���ˣ�ֱ��return
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
				//���ҵ���=ԭ�ҵ���*rate
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
		//���丸���sic
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
		//��λ��
		baseCurrency = (CurrencyInfo)initData.get(FDCConstants.FDC_INIT_CURRENCY);
		//��λ��
		company = (CompanyOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_COMPANY);
		if(company==null){
			company =  SysContext.getSysContext().getCurrentFIUnit();
		}
		//
		orgUnitInfo = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		if(orgUnitInfo==null){
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		}
		
		//��ͬ����
		contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
		
		//��ǰ����
		bookedDate = (Date)initData.get(FDCConstants.FDC_INIT_DATE);
		if(bookedDate==null){
			bookedDate = new Date(FDCDateHelper.getServerTimeStamp().getTime());
		}		
		//��ǰ�ڼ�
		curPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_PERIOD);
		//�Ƿ��Ѿ�����
		if(initData.get(FDCConstants.FDC_INIT_ISFREEZE)!=null){
			isFreeze = ((Boolean)initData.get(FDCConstants.FDC_INIT_ISFREEZE)).booleanValue();
		}
		//��¼���ڼ�
		if(isFreeze){
			canBookedPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_BOOKEDPERIOD);
		}else{
			canBookedPeriod = curPeriod;
		}		

		//������Ŀ
		curProject = (CurProjectInfo) initData.get(FDCConstants.FDC_INIT_PROJECT);
			
		//��ǰ����������
		serverDate = (Date)initData.get("serverDate");
		if(serverDate==null){
			serverDate = bookedDate;
		}
	}
	protected  void fetchInitParam() throws Exception{
		if(company==null){
			return ;
		}
		//���óɱ�����һ�廯
		isIncorporation = FDCUtils.IsInCorporation(null, company.getId().toString());
	}
	public void onLoad() throws Exception {
		super.onLoad();
		initTable();
		
		if(editData.getMaterialContractBill() != null){
			materialConId = editData.getMaterialContractBill().getId().toString();
			this.txtRate.setPrecision(FDCClientHelper.getExRatePrecOfCurrency(editData.getMaterialContractBill().getCurrency().getId().toString()));
		}
		//���Ϻ�ͬF7
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
		
		//�����ʵ����δ���壻ȡ��ʱ����
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
    	kdtEntrys_materialNumber_PromptBox.setSelector(new MaterialPromptSelector(this));  // ʹ���Զ��������ұ��F7 Added by owen_wen 2010-8-27
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

		//Add by zhiyuan_tang 2010/07/29  ������ֻ����������
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
	 * ��̬�����ȷ�ϵ��ۣ�ȷ�Ͻ��
	 * @author pengwei_hou
	 * @param e
	 */
	private void countAmt(KDTEditEvent e) {
		int rowIndex = e.getRowIndex();
		//�Ż����ִ��� Date: 2008-09-25
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
			//���
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
			//ȷ�ϵ���
			if(objCOPrc!=null&&rate!=null){
				BigDecimal confirmOriginalPrice = new BigDecimal(objCOPrc.toString());
				BigDecimal confirmPrice = confirmOriginalPrice.multiply(rate);
				getDetailTable().getCell(rowIndex,
						MaterialConfirmContants.CONFIRM_PRICE).setValue(confirmPrice);
				objCfmPrc = confirmPrice;
			}	
			//ȷ�Ͻ��
			if(e.getColIndex() != getDetailTable().getColumnIndex("confirmOriAmt")){
			if(objCfmPrc != null){
				if(num != null && objCfmPrc !=null){
					/***
					 * �˴�������Һ���ʱ��������⣬���Ĺ���
					 * ��ǰ����:
					 * BigDecimal confirmOriAmt = cfmPrc.multiply(num);
                     * BigDecimal confirmAmt = confirmOriAmt.multiply(rate);
                     * ����Ϊ��
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
	 * �����ͷ���ι���������ȷ�Ͻ��
	 */
	private void setHeadAmt(){
		//���ι������
		BigDecimal amt = FDCHelper.ZERO;
		// ����ȷ�ϱ��ҽ��
		BigDecimal confirmAmt = FDCHelper.ZERO;
		// ����ȷ��ԭ�ҽ��
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
		
		//��¼У��
		for(int i=0;i<getDetailTable().getRowCount();i++){
			IRow row = getDetailTable().getRow(i);
			//Add by zhiyuan_tang 2010/07/29  ��Ӷ����ϱ��벻��Ϊ�յ�У��
			if(row.getCell(MaterialConfirmContants.MATERIAL_NUMBER).getValue() == null){
				String info = PartAUtils.getRes("argsCanNotBeNull");
				String[] args =new String[]{(i+1)+"", "���ϱ���"};
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
		
		//У��ȷ�ϵ����������Ƿ���ڼƻ�����
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
		
		//ȡ��ǰ����¼������ϸ���ƻ���������
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
		
		//�䶯���������ƻ������Ƚϣ���������ʾ
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
		//��ʾ�׹��ĺ�ͬ,����״̬
		filter.getFilterItems().add(new FilterItemInfo("isPartAMaterialCon", Boolean.TRUE,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		
		//��CU����
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
				
				//������ѡ���ͬʱ����ͷ������¼���
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
				//�ѹ���
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
				//��ȷ��
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
				//�Ѹ���
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
	 * ��״̬Ϊ����ʱ��ȡ��ʷ�����ۼƽ��
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
	 * ������Delete��ɾ����Ԫ������ʱ��ͬʱɾ��������������
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
    		if(null != table.getFootRow(0)){ //��������ڱ����
    			footRow = table.getFootRow(0);
    			footRow.getCell("amount").setValue(FDCHelper.ZERO);
    		}
        }
	}
	
	
	/**
	 * ��ӡ
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
	 * ��ӡԤ��
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		logger.info("��ӡԤ��");
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

	// ������ı���ͬ�״��Ӧ��Ŀ¼
	protected String getTDFileName() {
		return "/bim/fdc/material/MaterialContractBillPrint";
	}

	// ��Ӧ���״�Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.material.app.MaterialContractBillPrintQuery");
	}
	
	/**
	 * �Ӳ��϶��������� add by ywm 2010-7-3
	 */
	public void actionImportMaterialOrder_actionPerformed(ActionEvent e) throws Exception {
		if (this.prmtMaterialContractBill.getValue()== null){
			FDCMsgBox.showInfo("��ѡ����Ϻ�ͬ���룡");
			this.prmtMaterialContractBill.requestFocus(true);
			SysUtil.abort();
		}
		
		UIContext importUIContext = new UIContext(this);
		importUIContext.put(UIContext.OWNER, this);
		IUIWindow importUIWindow = UIFactory.createUIFactory().create("com.kingdee.eas.fdc.material.client.ImpMatOrderBillUI", importUIContext, null,"VIEW");
		importUIWindow.show();
		if (getSelectedOrderBillIds_sum4RevQty() == null || getSelectedOrderBillIds_sum4RevQty().size() == 0)
			return ;
		else{   //�ݾ�ѡ��ҪID���Ѳ��϶��������ܷ�¼�е���
			doImportMatOrderData(getSelectedOrderBillIds_sum4RevQty());
		}
	}

	/**
	 * �������϶���ѡ�еķ�¼�е�ID�������ȷ�ϵ��ķ�¼
	 * 
	 * @param orderEntryID_sum4RevQty
	 *            �����ķ�¼ID�뵽��������ɵ�Map
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
		// �������°�װ��������HashSet, ��ΪkeySet()���ص�Set��δ���л���
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
		        // ȡ��������¼�еĵ��ۣ���Ϊ��������¼��û��ԭ�Ҽ۸�
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
	    this.editData.setNumber(this.txtNumber.getText()); // ����ʱ����ʱ����Ҫû����䣬����loadField()�����󣬱���ؼ���ֵ�ᱻ��յ���
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
	 * Map�� Key�Ƕ�����¼��ID, value���ۼƵ�������
	 */
	private Map orderEntryID_sum4RevQty;

	public Map getSelectedOrderBillIds_sum4RevQty() {
		return orderEntryID_sum4RevQty;
	}
	
	public void setSelectedOrderBillIds_sum4RevQty(Map orderEntryID_sum4RevAmt) {
		this.orderEntryID_sum4RevQty = orderEntryID_sum4RevAmt;
	}
	
	/**
	 * ��ʼ�������
	 * @author owen_wen 2010-12-08
	 */
	private void initSectionColumn(){
		KDComboBox sectionBox = new KDComboBox();
		sectionBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.SectionEnum").toArray());
		KDTDefaultCellEditor sectionBoxCellEditor = new KDTDefaultCellEditor(sectionBox);
		this.kdtEntrys.getColumn("section").setEditor(sectionBoxCellEditor);
	}
	
	//add by duyu 2011-8-15  ���Ӻϼ���
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