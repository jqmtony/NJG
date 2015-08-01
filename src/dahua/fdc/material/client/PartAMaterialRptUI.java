/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.material.MaterialRptFacadeFactory;

/**
 * 项目甲供材料台账
 */
public class PartAMaterialRptUI extends AbstractPartAMaterialRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(PartAMaterialRptUI.class);
    
    private CommonQueryDialog commonQueryDialog= null;
    
    private RetValue returnValue = null;
    
    /**
     * output class constructor
     */
    
    public PartAMaterialRptUI() throws Exception
    {
        super();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	return ;
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    	return ;
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
       if(!isSelectLeafPrj()){
    	   return ;
       }
       if(getSelectObj() == null)
       {
    	   return ;
       }
       fetchData();
       fillTable();
       
       //添加统计行 Added By Owen_wen 2010-12-09
       FDCTableHelper.apendFootRow(tblMain, new String[]{"plannumber", "enternumber", "notenternumber",});
    }
    
    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
    	if(commonQueryDialog != null &&commonQueryDialog.getUserPanel(0)!= null){
        PartAMaterialDefaultFilterUI ui =(PartAMaterialDefaultFilterUI) commonQueryDialog.getUserPanel(0);
        Set prjSet = new HashSet();
        if(getSelectObj() != null &&  (getSelectObj() instanceof CurProjectInfo)){
    		prjSet.add(((CurProjectInfo)getSelectObj()).getId().toString());
        ui.setCurPrjSet(prjSet);}}
    	super.actionQuery_actionPerformed(e);
    }
    
	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionView.setVisible(false);
		this.actionLocate.setVisible(false);
	}
	
	private PartAMaterialDefaultFilterUI custPanel = null;
	public PartAMaterialDefaultFilterUI getFilterUI() {
		
		if(custPanel == null){
			try {
				custPanel = new PartAMaterialDefaultFilterUI();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return custPanel;
	}
	
	protected void execQuery() {
		PartAMaterialDefaultFilterUI filterUI = (PartAMaterialDefaultFilterUI) commonQueryDialog.getUserPanel(0);
		FilterInfo filter = filterUI.getFilterInfo();
		Map custParam = new HashMap();
		if(filter != null){
			if(filter.getFilterItems() != null){
				FilterItemCollection fic = filter.getFilterItems();
				for(Iterator it = fic.iterator();it.hasNext();){
					FilterItemInfo info = (FilterItemInfo) it.next();
					if(info.getCompareValue()!="")
					custParam.put(info.getPropertyName(),info.getCompareValue());					
				}				
			}
		}
				
		RetValue param = new RetValue();
		Set prjSet = new HashSet();
		if(getSelectObj() != null &&  (getSelectObj() instanceof CurProjectInfo)){
		prjSet.add(((CurProjectInfo)getSelectObj()).getId().toString());
		param.put("prjs", prjSet);}
		else{
			FDCMsgBox.showWarning("请选择工程项目！");
			return;
		}		
		
		param.put("custParam", custParam); 
		
		try {
			returnValue = MaterialRptFacadeFactory.getRemoteInstance().getPartAMaterialValues(param);
			fillTable();
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 初始化查询窗口
	 */
	protected CommonQueryDialog initCommonQueryDialog() {
	   if(commonQueryDialog != null){
		   return commonQueryDialog;
	   }
	   commonQueryDialog = super.initCommonQueryDialog();
	   commonQueryDialog.setShowSorter(false);
	   commonQueryDialog.addUserPanel(this.getFilterUI());
	   commonQueryDialog.setShowFilter(false);
	   return commonQueryDialog;
	}

	protected void fetchData() throws Exception {
		RetValue param = new RetValue();
		Set prjSet = new HashSet();
		prjSet.add(((CurProjectInfo)getSelectObj()).getId().toString());
		param.put("prjs", prjSet);
        returnValue = MaterialRptFacadeFactory.getRemoteInstance().getPartAMaterialValues(param);
	}
	
	protected void fillTable() throws Exception {
		this.tblMain.removeRows();
		   if(returnValue.get("rs") != null){
		    KDTMergeManager mm = tblMain.getMergeManager();
			RowSet rs = (RowSet) returnValue.get("rs");
			int startMergeRow = 0;
			int rows = 0;
			int index = 0;
			String mname = "";	
			try{
			while(rs.next()){
				IRow row = tblMain.addRow();
				row.getCell("material.number").setValue(rs.getString("fnumber"));
				row.getCell("material.name").setValue(rs.getString("fname_l2"));
				row.getCell("material.model").setValue(rs.getString("fmodel"));
				row.getCell("material.unit").setValue(rs.getString("unitName"));
				row.getCell("plannumber").setValue(rs.getBigDecimal("auditQty"));
				row.getCell("enternumber").setValue(rs.getBigDecimal("fquantity"));
				row.getCell("contract").setValue(rs.getString("contractName"));
				row.getCell("notenternumber").setValue(rs.getBigDecimal("NOTENTERPLAN"));
				if(mname.equals(rs.getString("fnumber"))){
					rows++;
				}else{
					mname = rs.getString("fnumber");
					startMergeRow = index;
					rows = 0;
				}
				if(rows > 0){
					mm.mergeBlock(startMergeRow, 0,  index, 0, KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(startMergeRow, 1,  index, 1, KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(startMergeRow, 2,  index, 2, KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(startMergeRow, 3,  index, 3, KDTMergeManager.SPECIFY_MERGE);
				}
				index++;
			}}catch (SQLException ex){
				throw new SQLException();
			}finally{
				if(rs != null){
					rs.close();
				}
			}
		   }
	}
	
	protected void initTable() {
		// TODO Auto-generated method stub
		super.initTable();
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		this.tblMain.getColumn("plannumber").getStyleAttributes().setNumberFormat("####0.00");
		this.tblMain.getColumn("enternumber").getStyleAttributes().setNumberFormat("####0.00");
		this.tblMain.getColumn("notenternumber").getStyleAttributes().setNumberFormat("####0.00");
	}
	
	protected String getSelectedKeyValue() {
		// TODO Auto-generated method stub
		return "" ;				
	}
	
	/**
	 * 处理右键导出的中断，因为没有绑定Query，直接返回-1
	 * @author owen_wen 2010-12-15
	 */
	public int getRowCountFromDB() {
		return -1;
	}
}