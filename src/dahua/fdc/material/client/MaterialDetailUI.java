/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import bsh.This;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.ui.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.material.PartAMaterialEntryCollection;
import com.kingdee.eas.fdc.material.PartAMaterialEntryFactory;
import com.kingdee.eas.fdc.material.PartAMaterialEntryInfo;
import com.kingdee.eas.fdc.material.PartAMaterialFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class MaterialDetailUI extends AbstractMaterialDetailUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialDetailUI.class);
    /**
     * output class constructor
     */
    public MaterialDetailUI() throws Exception
    {
        super();
    }

	protected ICoreBase getBizInterface() throws Exception {
		return PartAMaterialFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		initTable();
		fillTable();
		initCtrlListener();
		
		FDCHelper.formatTableNumber(tblMain, "oriPrice");
		FDCHelper.formatTableNumber(tblMain, "price");
		FDCHelper.formatTableNumber(tblMain, "amount");
		FDCHelper.formatTableNumber(tblMain, "quantity");
		//tblMain.getColumn("quantity").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}
	public void initTable(){
		tblMain.checkParsed();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		
		
	}
	public void fillTable() throws BOSException{
		this.tblMain.removeRows();
		String mainConId=(String)getUIContext().get("mainContractId");
		String materialConId=(String)getUIContext().get("materialContractId");
		if(FDCHelper.isEmpty(mainConId)||FDCHelper.isEmpty(materialConId)){
			//主合同及材料合同的ID不能为空!
			FDCMsgBox.showError(this, PartAUtils.getRes("mainConAndMtlConIdCanNotNull"));
			SysUtil.abort();
		}
		Set detailSet=(Set)getUIContext().get("detailSet");
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("mainContractBill.number");
		view.getSelector().add("mainContractBill.name");
		view.getSelector().add("material.number");
		view.getSelector().add("material.name");
		view.getSelector().add("material.model");
		view.getSelector().add("material.baseUnit.name");
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("mainContractBill.id", mainConId);
		view.getFilter().appendFilterItem("parent.contractBill.id", materialConId);
		view.getFilter().appendFilterItem("parent.state", "4AUDITTED");
		view.getFilter().appendFilterItem("parent.isLatestVer", Boolean.TRUE);
		
		if(detailSet!=null&&detailSet.size()>0){
			view.getFilter().getFilterItems().add(new FilterItemInfo("id",detailSet,CompareType.NOTINCLUDE));
		}
		PartAMaterialEntryCollection c = PartAMaterialEntryFactory.getRemoteInstance().getPartAMaterialEntryCollection(view);
		for(Iterator iter=c.iterator();iter.hasNext();){
			PartAMaterialEntryInfo entry=(PartAMaterialEntryInfo)iter.next();
			IRow row=tblMain.addRow();
			loadRow(row,entry);
		}
	}
	public void loadRow(IRow row,PartAMaterialEntryInfo entry){
		row.getCell("id").setValue(entry.getId().toString());
		row.getCell("mainNumber").setValue(entry.getMainContractBill().getNumber());
		row.getCell("mainName").setValue(entry.getMainContractBill().getName());
		row.getCell("materialNumber").setValue(entry.getMaterial().getNumber());
		row.getCell("materialName").setValue(entry.getMaterial().getName());
		row.getCell("model").setValue(entry.getMaterial().getModel());
		row.getCell("unit").setValue(entry.getMaterial().getBaseUnit().getName());
		row.getCell("oriPrice").setValue(entry.getOriginalPrice());
		row.getCell("price").setValue(entry.getPrice());
		row.getCell("quantity").setValue(entry.getQuantity()+"");
		row.getCell("amount").setValue(entry.getAmount());
		row.getCell("arriveDate").setValue(entry.getArriveDate());
		row.getCell("desc").setValue(entry.getDescription());
		row.setUserObject(entry);
		
	}
	/**
	 * 根据传入的参数显示材料明细单的查询界面
	 * @param mainContractId	主合同ID
	 * @param materialContractId	材料合同ID
	 * @param detailSet				材料明细单分录Id集合
	 * @throws BOSException
	 */
	public static PartAMaterialEntryCollection showUI(String mainContractId,String materialContractId,Set detailSet) throws BOSException{
		Map context=new HashMap();
		context.put("mainContractId", mainContractId);
		context.put("materialContractId", materialContractId);
		context.put("detailSet", detailSet);
		getWin(context).show();
		PartAMaterialEntryCollection entryColl = (PartAMaterialEntryCollection) getWin(
				context).getUIObject().getUIContext().get("returnValue");
		getWin(context).getUIObject().getUIContext().put("returnValue", null);
		return entryColl;
	}
	
	private static IUIWindow win=null;
	private static IUIWindow getWin(Map context) throws BOSException{
		if(win==null){
			win=UIFactory.createUIFactory(UIFactoryName.MODEL).create(MaterialDetailUI.class.getName(),context, null, null);
		}else{
			MaterialDetailUI materialDetailUI = (MaterialDetailUI)win.getUIObject();
			materialDetailUI.getUIContext().putAll(context);
			materialDetailUI.fillTable();
		}
		return win;
	}
	public void initCtrlListener(){
		tblMain.addKDTMouseListener(new KDTMouseListener(){
			public void tableClicked(KDTMouseEvent e) {
				try {
					btnOK_actionPerformed(null);
				} catch (Exception e1) {
					handUIException(e1);
				}
			}
		});
	}
	protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
		if(tblMain.getRowCount()==0){
			return;
		}
		int[] selectedRows = KDTableUtil.getSelectedRows(tblMain);
		PartAMaterialEntryCollection c=new PartAMaterialEntryCollection();
		for(int i=0;i<selectedRows.length;i++){
			IRow row=tblMain.getRow(selectedRows[i]);
			PartAMaterialEntryInfo entry=(PartAMaterialEntryInfo)row.getUserObject();
			c.add(entry);
		}
		getUIContext().put("returnValue", c);
		this.destroyWindow();
	}
	protected void btnNo_actionPerformed(ActionEvent e) throws Exception {
		PartAMaterialEntryCollection c=new PartAMaterialEntryCollection();
		getUIContext().put("returnValue", c);
		destroyWindow();
	}
	
	public static void clear(){
		MaterialDetailUI.win=null;
	}
}