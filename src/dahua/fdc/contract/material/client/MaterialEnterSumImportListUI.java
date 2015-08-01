/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.fd2.gui.util.MsgBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.MaterialEnterSumEntryInfo;
import com.kingdee.eas.fdc.material.MaterialEnterSumImportInfo;
import com.kingdee.eas.fdc.material.MaterialEnterSumInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class MaterialEnterSumImportListUI extends AbstractMaterialEnterSumImportListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialEnterSumImportListUI.class);
    private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	private MaterialEnterSumEditUI editUI;
    /**
     * output class constructor
     */
    public MaterialEnterSumImportListUI() throws Exception
    {
        super();
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    public void actionView_actionPerformed(ActionEvent e) throws Exception{
		 //do nothing
	}
    
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
	    editUI = (MaterialEnterSumEditUI)getUIContext().get(UIContext.OWNER);
		setComponentState();
	}
   
	public void setComponentState(){
		//���ñ���ѡ��ģʽ
		tblMain.getSelectManager().setSelectMode(0x0A);
	}
	
	protected CommonQueryDialog initCommonQueryDialog() {
		 this.mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.material.app","ImpMatPlanQuery");			
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}
	
	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new MaterialEnterSumFilterUI(this,this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	  protected boolean initDefaultFilter()
	    {
	        return true;
	    }

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
		this.impMatPlanQuery =this.getMainQuery();
		if(impMatPlanQuery.getSorter()==null)
		{
			this.impMatPlanQuery.setSorter( getDefalutSorterItemCollection());		
		}
		super.tblMain_doRequestRowSet(e);
	}
   protected SorterItemCollection getDefalutSorterItemCollection()
   {
	    SorterItemCollection sc = new SorterItemCollection();
		SorterItemInfo sorterSupplierNum = new SorterItemInfo("supplierNum");
		sorterSupplierNum.setSortType(SortType.ASCEND);
		sc.add(sorterSupplierNum);
		SorterItemInfo sorterCurProjectNum = new SorterItemInfo("curProjectNum");
		sorterCurProjectNum.setSortType(SortType.ASCEND);
		sc.add(sorterCurProjectNum);
		SorterItemInfo sorterEnterTime = new SorterItemInfo("entrys_enterTime");
		sorterEnterTime.setSortType(SortType.ASCEND);
		sc.add(sorterEnterTime);
		return sc;
   }
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
	
		int activeRow = tblMain.getSelectManager().getActiveRowIndex();
		//tblMain.getSelectManager().getS
		int[] selectedRows = KDTableUtil.getSelectedRows(tblMain);
		if(activeRow!=-1){
			ICell cell = tblMain.getRow(activeRow).getCell("selected");
			Boolean isSelected = (Boolean)cell.getValue();
			
			if(isSelected.booleanValue()){
				for(int i = 0;i<selectedRows.length;i++){
					tblMain.getCell(selectedRows[i],"selected").setValue(Boolean.FALSE);
				}
			}
			else{
				for(int i = 0;i<selectedRows.length;i++){
					tblMain.getCell(selectedRows[i],"selected").setValue(Boolean.TRUE);
				}
			}
		}
	}

	/**
	 * �Ӳ��Ͻ����ƻ����뵽���Ͻ����ƻ�����
	 * */
	protected void btnImport_actionPerformed(ActionEvent e) throws Exception {
	 
      HashSet set = getImportDataSet();
      if(set==null || set.size()==0)
      {
    	  MsgBox.show("����û��ѡ����Ҫ����ļ�¼!");
      }
      else{
    	  doImport(set);
          destroyWindow();
      }
	}
	
	/**
	 * ����ƻ���������͸�EditData�� <p>
	 *  �޸����ж��Ƿ��ظ�������㷨��ɾ����Tim_gaoд�Ĵ��롣 Owen_wen 2010-09-22 
	 * @�޸��� tim_gao <p>
	 * @���� �����������Ϣ�Ա����е��Ƿ�������ͬ�� <p>
	 * @�޸�ʱ�� 2010-9-1 <p>
	 */
	private void doImport(HashSet set)
	{
		if(editUI==null || set ==null )
			return ;
		editUI.storeFields();
	   MaterialEnterSumInfo editData = (MaterialEnterSumInfo)editUI.getEditData();
	   
	   //�ñ�����ӵĲ�����Ϣ�Ա����е��Ƿ�����ͬ��
	   boolean isAllowReduplicate = true; 
	   Map mat_id_name_map = new HashMap(); // �ظ����ϵ�ID��name�ŵ�һ��Map��
	   for (Iterator it = set.iterator(); it.hasNext();){
		   MaterialEnterSumImportInfo importData= (MaterialEnterSumImportInfo)it.next();;
		   for (Iterator it1 = editData.getEntrys().iterator(); it1.hasNext();){
			   MaterialEnterSumEntryInfo matEnterSumEntry = (MaterialEnterSumEntryInfo )it1.next();
			   if (matEnterSumEntry.getSourceBill() != null && matEnterSumEntry.getSourceBill().equals(importData.getSourceBillId())){
				   mat_id_name_map.put(importData.getMaterial().getId().toString(), importData.getMaterial().getName());
			   }
		   }		   
	   }
	   
	   if (mat_id_name_map.size()>0) { // ���ظ�������
			int confirResult = com.kingdee.eas.util.client.MsgBox.showConfirm3(this, "�����ظ��������ϵĽ����ƻ����Ƿ�Ҫ�ظ�����?", 
				mat_id_name_map.values().toString() + " �����ظ����롣");
			if (confirResult == com.kingdee.eas.util.client.MsgBox.NO){
				isAllowReduplicate = false; 
			}
	   }
	   
	   for (Iterator iterator = set.iterator(); iterator.hasNext();)
	   {	
		   MaterialEnterSumImportInfo importData= (MaterialEnterSumImportInfo)iterator.next();;
		   MaterialEnterSumEntryInfo entryInfo = new MaterialEnterSumEntryInfo();
   
		   if (!isAllowReduplicate) //����������ظ����룬
		   {
			   // �ж�ѡ�еĽ����ƻ��Ƿ��ظ�
			   if (mat_id_name_map.keySet().contains(importData.getMaterial().getId().toString())) // ��ǰ��¼�Ѿ��������ƻ����ܹ����ظ�ѡ����
			   continue;
		   }
		   
		   entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
		   entryInfo.setCurProject(importData.getCurProject());
		   entryInfo.setSupplier(importData.getSupplier());
		   entryInfo.setMaterial(importData.getMaterial());
		   entryInfo.setModel(importData.getModel());
		   entryInfo.setUnit(importData.getUnit());
		   entryInfo.setQuantity(importData.getQuantity());
		   //����ʱĬ�ϼƻ�����������������
		   entryInfo.setPlanQuantity(importData.getQuantity());
		   entryInfo.setOrderQuantity(importData.getOrderQuantity());
		   entryInfo.setEnterTime(importData.getEnterTime());
		   entryInfo.setContractUnit(importData.getContractUnit());
		   entryInfo.setContractBill(importData.getContractBill());
		   entryInfo.setMaterialBill(importData.getMaterialBill());
		   entryInfo.setSourceBill(importData.getSourceBillId());
		   editData.getEntrys().add(entryInfo);
	   }
	   editUI.loadFields();
	   try{
		   editUI.carryMaterialName();  //ˢ�´�����������
	   }catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	}
	private HashSet getImportDataSet()
	{
		HashSet importSet = new HashSet();
		for(int i=0;i<tblMain.getRowCount3();i++ ){
			Boolean isSelected = (Boolean)tblMain.getRow(i).getCell("selected").getValue();
		    if(isSelected.booleanValue()){
		    	Object id = null;
		    	Object number = null;
		    	Object name = null;
		    	MaterialEnterSumImportInfo info  = new MaterialEnterSumImportInfo();
		    	CurProjectInfo curProject = new CurProjectInfo();
		    	id = tblMain.getRow(i).getCell("curProjectId").getValue();
		    	number = tblMain.getRow(i).getCell("curProjectNum").getValue();
		    	name =tblMain.getRow(i).getCell("curProject").getValue();
		    	setBaseDataInfo(curProject,id,number,name);
		    	info.setCurProject(curProject);
		    	
		    	SupplierInfo supplier= new SupplierInfo();
		    	id = tblMain.getRow(i).getCell("supplierId").getValue();
		    	number = tblMain.getRow(i).getCell("supplierNum").getValue();
		    	name = tblMain.getRow(i).getCell("supplier").getValue();
		    	setBaseDataInfo(supplier,id,number,name);
		    	info.setSupplier(supplier);
		    	
		    	MaterialInfo material = new MaterialInfo();
		    	id = tblMain.getRow(i).getCell("materialId").getValue();
		    	number = tblMain.getRow(i).getCell("materialNum").getValue();
		    	name = tblMain.getRow(i).getCell("materialName").getValue();
		    	setBaseDataInfo(material,id,number,name);
		    	info.setMaterial(material);
		    	
		    	Object objModel = tblMain.getRow(i).getCell("model").getValue();
		    	if(objModel!=null)
		    	{
		    		info.setModel(objModel.toString());
		    	}
		    	
		    	MeasureUnitInfo unit = new MeasureUnitInfo();
		    	id = tblMain.getRow(i).getCell("unitId").getValue();
		    	number = tblMain.getRow(i).getCell("unitNum").getValue();
		    	name = tblMain.getRow(i).getCell("unit").getValue();
		    	setBaseDataInfo(unit,id,number,name);
		    	info.setUnit(unit);
		    	
		    	Object objQty = tblMain.getRow(i).getCell("quantity").getValue();
		    	if(objQty!=null)
		    		info.setQuantity(new BigDecimal(objQty.toString()));
		    	
//		    	Object objorderQty = tblMain.getRow(i).getCell("orderQuantity").getValue();
//		    	if(objQty!=null)
//		    		info.setQuantity(new BigDecimal(objorderQty.toString()));
		    	
		    	Object objEnterTime = tblMain.getRow(i).getCell("enterTime").getValue();
		    	if(objEnterTime!=null){
		    		try{
 		    		info.setEnterTime(strToDate(objEnterTime.toString()));
 		    		System.out.println(info.getEnterTime());
		    		}catch(Exception e )
		    		{
		    			e.printStackTrace();
		    		}
		    	}
		    	
		    	SupplierInfo contractUnit = new SupplierInfo();
		    	id = tblMain.getRow(i).getCell("contractUnitId").getValue();
		    	number = tblMain.getRow(i).getCell("contractUnitNum").getValue();
		    	name = tblMain.getRow(i).getCell("contractUnit").getValue();
		    	setBaseDataInfo(contractUnit,id,number,name);
		    	info.setContractUnit(contractUnit);
		    	
		    	ContractBillInfo contractBill = new ContractBillInfo();
		    	id = tblMain.getRow(i).getCell("contractBillId").getValue();
		    	number = tblMain.getRow(i).getCell("contractBillNum").getValue();
		    	name = tblMain.getRow(i).getCell("contractBill").getValue();
		    	setContractBillInfo(contractBill,id,number,name);
		    	info.setContractBill(contractBill);
		    	
		    	ContractBillInfo materialBill = new ContractBillInfo();
		    	id = tblMain.getRow(i).getCell("materialBillId").getValue();
		    	number = tblMain.getRow(i).getCell("materialBillNum").getValue();
		    	name = tblMain.getRow(i).getCell("materialBill").getValue();
		    	setContractBillInfo(materialBill,id,number,name);
		    	info.setMaterialBill(materialBill);
		    	
		    	id = tblMain.getRow(i).getCell("sourceBillId").getValue();
		    	if(id!=null && !"".equals(id.toString()))
		    		info.setSourceBillId(id.toString());
		    	importSet.add(info);
		    }
		}
		return importSet;
	}
	
	public static Date strToDate(String str) {
		if (str == null){
			return null;
		}
		String[] strArray = str.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(strArray[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(strArray[1])-1);
		cal.set(Calendar.DATE, Integer.parseInt(strArray[2]));
		return DateTimeUtils.truncateDate(cal.getTime());

	}
	
	
	private void setContractBillInfo(ContractBillInfo billInfo,Object id ,Object number,Object name)
	{
		if(id!=null)
			billInfo.setId(BOSUuid.read(id.toString()));
		 if(number!=null)
			 billInfo.setNumber(number.toString());
		 if(name!=null)
             billInfo.setName(name.toString());
	}
  private void setBaseDataInfo(DataBaseInfo info,Object id ,Object number,Object name)
  {
	 if(id!=null)
		 info.setId(BOSUuid.read(id.toString()));
	 if(number!=null)
		 info.setNumber(number.toString());
	 if(name!=null)
		 info.setName(name.toString());
  }
	protected void btnImportCancel_actionPerformed(ActionEvent e)
			throws Exception {
		destroyWindow();
	}

	protected void chkSelectAll_stateChanged(ChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.chkSelectAll_stateChanged(e);
		if(this.chkSelectAll.getSelectState()==32)
		{
			for(int i=0;i<tblMain.getRowCount3();i++){
				tblMain.getCell(i, "selected").setValue(Boolean.TRUE);
			}
		}
		else
		{
			for(int i=0;i<tblMain.getRowCount3();i++){
				tblMain.getCell(i, "selected").setValue(Boolean.FALSE);
			}
		}
	}
	
	/**
	 * ��Ҫ�������еĲ��Ͻ����ƻ�����˲���Ҫ��cu���루modify by zqy at 2010-09-06��
	 */
	 protected boolean isIgnoreCUFilter() {
		return true;
	}
	 
	 /**
	  * ���������嶨λ�ֶΣ�������Ŀ����Ӧ�̡��������ơ����ϱ���
	  * @author zhiyuan_tang
	  */
	 protected String[] getLocateNames() {
		return new String[]{"curProject", "supplier", "materialName", "materialNum"};
	}
}