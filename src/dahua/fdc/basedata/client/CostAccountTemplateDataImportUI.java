/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
*
* ����:�ɱ���Ŀϵͳģ����������
* @author Jackwang  date:2006-09-21 <p>
* @version EAS5.1
*/
public class CostAccountTemplateDataImportUI extends AbstractCostAccountTemplateDataImportUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostAccountTemplateDataImportUI.class);
    private HashSet hs = new HashSet() ;
    /**
     * output class constructor
     */
    public CostAccountTemplateDataImportUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.btnAllSelect.setIcon(EASResource.getIcon("imgTbtn_selectall"));
		this.btnNoneSelect.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
		// ���ſ�Ŀ����
		CostAccountCollection cacOld = CostAccountFactory.getRemoteInstance().getCostAccountCollection("select longNumber where CU.id = '"+OrgConstants.DEF_CU_ID+"' and fullOrgUnit.id = '"+OrgConstants.DEF_CU_ID+"'");
		hs = new HashSet();
		for(int i=0;i<cacOld.size();i++){
			hs.add(cacOld.get(i).getLongNumber());
		}
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		// ϵͳģ�����
		filterItems.add(new FilterItemInfo("CU.id", OrgConstants.SYS_CU_ID, CompareType.EQUALS));			
		this.loadDatas(filter);		
	}
    
	private void loadDatas(FilterInfo filter) throws BOSException {
		this.tblMain.removeRows();
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		EntityViewInfo evInfo = new EntityViewInfo();
		evInfo.getSelector().add("id");
		evInfo.getSelector().add("longNumber");
		evInfo.getSelector().add("name");
		evInfo.getSelector().add("isLeaf");
		evInfo.getSelector().add("parent.id");
		evInfo.getSelector().add("type");
		evInfo.setFilter(filter);
		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("longNumber");
		sii.setSortType(SortType.ASCEND);
		evInfo.getSorter().add(sii);
		CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evInfo);
		tblMain.checkParsed();// table����!
		tblMain.getColumn("select").setWidth(50);
		tblMain.getColumn("number").setWidth(200);
		tblMain.getColumn("number").getStyleAttributes().setLocked(true);
		tblMain.getColumn("name").setWidth(180);
		tblMain.getColumn("name").getStyleAttributes().setLocked(true);
		tblMain.getColumn("id").getStyleAttributes().setHided(true);
		boolean flag = false;
		if (costAccountCollection.size() != 0) {
			IRow row;
			for (int i = 0; i < costAccountCollection.size(); i++) {
				row = this.tblMain.addRow();
				row.getCell("select").setValue(Boolean.valueOf(false));
				row.getCell("number").setValue(costAccountCollection.get(i).getLongNumber().replace('!', '.'));
				row.getCell("name").setValue(costAccountCollection.get(i).getName(SysContext.getSysContext().getLocale()));
				row.getCell("id").setValue(costAccountCollection.get(i).getId().toString());
				if(costAccountCollection.get(i).getParent()!=null){
					row.getCell("parent.id").setValue(costAccountCollection.get(i).getParent().getId());
				}
				row.setUserObject(costAccountCollection.get(i));
				if(!costAccountCollection.get(i).isIsLeaf()){
					row.getStyleAttributes().setBackground(this.tblMain.getRequiredColor());
					row.getCell("select").getStyleAttributes().setLocked(true);
				}
				if(hs.contains(costAccountCollection.get(i).getLongNumber())){
					row.getStyleAttributes().setBackground(this.tblMain.getRequiredColor());
					row.getCell("select").getStyleAttributes().setLocked(true);
				}
				// row.getCell("isenabled").setValue(String.valueOf(costAccountCollection.get(j).getAsstAccount().getName()));
			}
			for (int i = 0; i < this.tblMain.getRowCount(); i++) {
				if(!tblMain.getRow(i).getCell("select").getStyleAttributes().isLocked()){
					flag = true;
					break;
				}
			}
		}
		
		if(flag){
			this.btnAllSelect.setEnabled(true);
			this.btnNoneSelect.setEnabled(true);
		}else{
			this.btnAllSelect.setEnabled(false);
			this.btnNoneSelect.setEnabled(false);
		}
	}

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
		IRow row;
		CostAccountCollection cac = new CostAccountCollection();
		boolean flag = false;
		for (int i = 0, count = tblMain.getRowCount(); i < count; i++) {
			row = tblMain.getRow(i);
			if (((Boolean) row.getCell("select").getValue()).booleanValue()) {
				CostAccountInfo cai = (CostAccountInfo) row.getUserObject();
				cac.add(cai);
				flag = true;
			}
		}
		if (flag) {
			ArrayList al = CostAccountFactory.getRemoteInstance().importTemplateDatas(cac);
			if(al.size()!=0){
				//ʹ���쳣��ԭ������Ϣ
				String error=EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,"import_dupAccount");
				MsgBox.showDetailAndOK(this, error, al.toString(), 1);
				SysUtil.abort();
//				throw (new FDCBasedataException(FDCBasedataException.COSTACCOUNT_TEMP_IMPORT, new Object[] { al }));
			}else
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,"Import_success"));
		}
		
	}
	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		this.getUIWindow().close();
	}

    /**
     * output actionAllSelect_actionPerformed
     */
    public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception
    {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			//if(!this.tblMain.getRow(i).getStyleAttributes().getBackground().equals(this.tblMain.getRequiredColor())){	
				this.tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(true));
			//}
		}
    }

    /**
     * output actionNoneSelect_actionPerformed
     */
    public void actionNoneSelect_actionPerformed(ActionEvent e) throws Exception
    {
    	for (int i = 0; i < this.tblMain.getRowCount(); i++) {
    		this.tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(false));
		}
    }

    /**
     * output tblMain_editValueChanged method
     */
    protected void tblMain_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    	editedCellAfter(e);
    }
    
    /**
     * ����ָ����Ԫ�����ֵ����
     * 
     * @param rowIndex
     * @param colIndex
     */
    private void editedCellAfter(KDTEditEvent e) throws Exception {
        int rowIndex = e.getRowIndex();
        if(e.getValue() == null) return;
        boolean isSelected = ((Boolean)tblMain.getRow(rowIndex).getCell("select").getValue()).booleanValue();
        //boolean isSelected = ((Boolean)e.getValue()).booleanValue();
	            
        if(tblMain.getRow(rowIndex).getCell("parent.id").getValue() != null){
	        String parentId = tblMain.getRow(rowIndex).getCell("parent.id").getValue().toString();

	        for(int i = rowIndex;i >= 0;i--){    		
    			if(parentId.equals(tblMain.getRow(i).getCell("id").getValue().toString())){//�Ǹ�
    				tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(isSelected));
    				if(tblMain.getRow(i).getCell("parent.id").getValue() != null){
    					parentId = tblMain.getRow(i).getCell("parent.id").getValue().toString();
    				}
    			}    				
			}
	    	
	        if(!isSelected){
	        	HashMap parentMap = new HashMap();
		    	for(int i = this.tblMain.getRowCount()-1;i >= 0;i--){    
		    		if(((Boolean)this.tblMain.getRow(i).getCell("select").getValue()).booleanValue() && i != rowIndex){
		    			parentMap.put(tblMain.getRow(i).getCell("parent.id").getValue().toString(),null);
		    		}

		    		if(parentMap.containsKey(tblMain.getRow(i).getCell("id").getValue().toString())){
		    			tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(true));
		    			if(tblMain.getRow(i).getCell("parent.id").getValue() != null){
		    				parentMap.put(tblMain.getRow(i).getCell("parent.id").getValue().toString(),null);
		    			}
		    		}
		    	}
	    	}
    	}
    }
}