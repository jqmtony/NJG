/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.MaterialPromptSelector;
import com.kingdee.eas.fdc.material.MaterialBillStateEnum;
import com.kingdee.eas.fdc.material.MaterialEnterSumInfo;
import com.kingdee.util.enums.EnumUtils;

/**
 * 材料进场计划汇总
 */
public class MaterialEnterSumEditUI extends AbstractMaterialEnterSumEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialEnterSumEditUI.class);
     
    public MaterialEnterSumEditUI() throws Exception
    {
        super();
    }
   
    public void storeFields()
    {
    	editData.setName(editData.getNumber());
        super.storeFields();
    }
    
    public void loadFields() {
    	editData.setName(editData.getNumber());
    	super.loadFields();
    }
   
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.material.MaterialEnterSumFactory.getRemoteInstance();
    }
  
    protected IObjectValue createNewDetailData(KDTable table)
    {		
        return null;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        MaterialEnterSumInfo objectValue = new MaterialEnterSumInfo();
        objectValue.setCreator((UserInfo)(SysContext.getSysContext().getCurrentUser()));
		try {
			objectValue.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			logger.info("未能获取服务器时间，原因：" + e.getMessage());
			e.printStackTrace();
		}
        
        return objectValue;
    }
    
	public void actionImportFormPlan_actionPerformed(ActionEvent e)
			throws Exception {
		UIContext importUIContext = new UIContext(this);
		importUIContext.put(UIContext.OWNER, this);
		IUIWindow importUIWindow = UIFactory.createUIFactory().create("com.kingdee.eas.fdc.material.client.MaterialEnterSumImportListUI", importUIContext, null,"VIEW");
		importUIWindow.show();
	}	

	public void carryMaterialName()throws Exception
	{
		for(int i=0;i<kdtEntrys.getRowCount();i++){
		  if (kdtEntrys.getCell(i,"materialName").getValue()==null && kdtEntrys.getCell(i,"material").getValue()!=null) {
				  kdtEntrys.getCell(i,"materialName").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntrys.getCell(i,"material").getValue(),"name")));				  
		     }
		}
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		setComponentValue();
		setComponentState();
	}

	public void setComponentState(){
		if(getOprtState().equals(OprtState.ADDNEW)||getOprtState().equals(OprtState.COPYADDNEW)){
			comboBoxState.setSelectedItem(MaterialBillStateEnum.NONE); //新增单据时设置状态为未订货状态
		}
		
		// 修改提交状态的单据，保存按钮要灰显
		if (editData != null && (FDCBillStateEnum.SUBMITTED.equals(editData.getState()))){
			this.btnSave.setEnabled(false);
		}
		
		btnAuditResult.setVisible(false);
		pkAuditTime.setEnabled(false);
		this.kdtEntrys.getColumn("quantity").getStyleAttributes().setLocked(false);
		this.kdtEntrys.getColumn("materialName").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("materialName").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn("model").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("model").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn("unit").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("unit").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kDDateCreateTime.setDatePattern("yyyy-MM-dd");
	}

	public void setComponentValue()
	{
		this.kdtEntrys_detailPanel.setTitle("物料明细");
		this.comboBoxState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.material.MaterialBillStateEnum").toArray());	
		//汇总日期应对应汇总表的汇总日期 
		// by tim_gao
		//this.pkBizDate.setValue(new Date());
		this.combState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());
		//Add by zhiyuan_tang 不知道为什么combState它的值一直都是空。在这里给它赋值，不然没有修改数据也会提示“数据已修改”
		this.combState.setSelectedItem(editData.getState());
	    KDBizPromptBox prmtCurProject = (KDBizPromptBox)kdtEntrys.getColumn("curProject").getEditor().getComponent();
	    //FDCClientUtils.initSupplierF7(this, prmtCurProject, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
	    
	    //add by liujianhua 
		prmtCurProject.setValue(curProject);
		prmtCurProject.setDisplayFormat("$number$ $name$");
		prmtCurProject.setEditFormat("$name$");
		prmtCurProject.setCommitFormat("$name$");
		prmtCurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		prmtCurProject.setEnabled(true);
		
		KDBizPromptBox kdtEntrys_material_PromptBox = new KDBizPromptBox();
//        kdtEntrys_material_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialBaseInfoQuery");
		kdtEntrys_material_PromptBox.setSelector(new MaterialPromptSelector(this));  // 使用自定义左树右表的F7 Added by owen_wen 2010-8-27
        kdtEntrys_material_PromptBox.setVisible(true);
        kdtEntrys_material_PromptBox.setEditable(true);
        kdtEntrys_material_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_material_PromptBox.setEditFormat("$number$");
        kdtEntrys_material_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_material_CellEditor = new KDTDefaultCellEditor(kdtEntrys_material_PromptBox);
        this.kdtEntrys.getColumn("material").setEditor(kdtEntrys_material_CellEditor);
        
        KDFormattedTextField price = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
        price.setPrecision(2);
        KDTDefaultCellEditor priceCellEditor = new KDTDefaultCellEditor(price);
        this.kdtEntrys.getColumn("quantity").setEditor(priceCellEditor);
        this.kdtEntrys.getColumn("planQuantity").setEditor(priceCellEditor);
        this.kdtEntrys.getColumn("orderQuantity").setEditor(priceCellEditor);
        
		if (editData != null && editData.getBillstate() != null) {
			this.comboBoxState.setSelectedItem(editData.getBillstate());
		}
		
		
	    KDBizPromptBox prmtSupplier = (KDBizPromptBox)kdtEntrys.getColumn("supplier").getEditor().getComponent();
	    FDCClientUtils.initSupplierF7(this, prmtSupplier, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());			   
	}

	protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		//检测分录
		for(int i = 0; i < kdtEntrys.getRowCount(); ++i)
		{
			IRow row = kdtEntrys.getRow(i);
			Integer index = new Integer(i+1);
			String warning = "第" + index.toString() + "行 、第" ;
			
			if(row.getCell("supplier").getValue()== null||row.getCell("supplier").getValue().toString() == null)
			{
				warning = warning + "2列供应商不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			if(row.getCell("material").getValue() == null)
			{
				warning = warning + "3列物料编码不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			if(row.getCell("planQuantity").getValue() == null)
			{
				warning = warning + "8列计划数量不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
		
		}
	
	}
	
	protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		if ("material".equalsIgnoreCase(kdtEntrys.getColumn(colIndex).getKey())) {
			MaterialInfo material = (MaterialInfo) e.getValue();
			if(material == null){
				material = (MaterialInfo) e.getOldValue();
			}
			if(material != null){
				kdtEntrys.getCell(rowIndex,"materialName").setValue(material.getName());	
				kdtEntrys.getCell(rowIndex,"model").setValue(material.getModel());	
				kdtEntrys.getCell(rowIndex,"unit").setValue(material.getBaseUnit());
				kdtEntrys.getCell(rowIndex,"material").setValue(material);
			}
		}
	}
	
}