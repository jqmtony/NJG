/**
 * output package name
 */
package com.kingdee.eas.fdc.wfui;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.freechart.ui.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.*;

/**
 * 
 * 技术经济签证单审批界面
 * output class name
 */
public class TechnologyeconomyApproveUI extends AbstractTechnologyeconomyApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(TechnologyeconomyApproveUI.class);
    
    /**
     * output class constructor
     */
    public TechnologyeconomyApproveUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	initUI();
    }
    
    private void initUI() throws BOSException, SQLException{
    	
    	this.kDTable1.addColumns(4);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();
    	
    	IRow addRow1 = this.kDTable1.addRow();
    	addRow1.getCell(0).setValue("公司名称");
    	addRow1.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow1.getCell(2).setValue("合同编号");
    	addRow1.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	IRow addRow2 = this.kDTable1.addRow();
    	addRow2.getCell(0).setValue("签证事由");
    	addRow2.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(1, 1, 1, 3);
    	
    	
    	IRow addRow3 = this.kDTable1.addRow();
    	addRow3.getCell(0).setValue("承包单位");
    	addRow3.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow3.getCell(2).setValue("项目完成时间");
    	addRow3.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	
    	IRow addRow4 = this.kDTable1.addRow();
    	addRow4.getCell(0).setValue("施工负责人");
    	addRow4.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow4.getCell(2).setValue("经办人");
    	addRow4.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	
    	IRow addRow5 = this.kDTable1.addRow();
    	addRow5.getCell(0).setValue("实际工作内容描述：");
    	addRow5.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
    	
    	IRow addRow6 = this.kDTable1.addRow();
    	addRow6.getCell(0).setValue("实际工作内容描述：");
    	addRow6.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
    	
    	IRow addRow7 = this.kDTable1.addRow();
    	addRow7.getCell(0).setValue("实际工作内容描述：");
    	addRow7.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
    	
    	IRow addRow8 = this.kDTable1.addRow();
    	addRow8.getCell(0).setValue("实际工作内容描述：");
    	addRow8.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
    	
    	
    	IRow addRow9 = this.kDTable1.addRow();
    	addRow9.getCell(0).setValue("签证申报费用：");
    	addRow9.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow9.getCell(3).setValue("签章");
//    	addRow9.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(8, 1, 8, 2);
    	
    	IRow addRow10 = this.kDTable1.addRow();
    	addRow10.getCell(0).setValue("项目主管工程部（项目经理）意见");
    	addRow10.getCell(0).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.LEFT);//对齐
    	IRow addRow11 = this.kDTable1.addRow();
    	IRow addRow12 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(9, 0, 11, 3);
    	
    	IRow addRow13 = this.kDTable1.addRow();
    	addRow13.getCell(0).setValue("造价工程师意见");
    	addRow13.getCell(0).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.LEFT);
    	IRow addRow14 = this.kDTable1.addRow();
    	IRow addRow15 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(12, 0, 14, 3);
    	
    	IRow addRow16 = this.kDTable1.addRow();
    	addRow16.getCell(0).setValue("工程经理部");
    	addRow16.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow16.getCell(2).setValue("项目公司常务副总经理");
    	addRow16.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	IRow addRow17 = this.kDTable1.addRow();
    	addRow17.getCell(0).setValue("收文签字");
    	addRow17.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow17.getCell(2).setValue("时间");
    	addRow17.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	this.kDTable1.getColumn(0).setWidth(200);
    	this.kDTable1.getColumn(1).setWidth(200);
    	this.kDTable1.getColumn(2).setWidth(200);
    	this.kDTable1.getColumn(3).setWidth(200);
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    	
    	

    	String billId = "FrYcumfRTUq/iL87J1M2VXARYRc=";
//    	StringBuffer sb = new StringBuffer();
//    	sb.append("  select ChangeAB.FCurProjectName ,ChangeAB.FNumber ,ChangeAB.Fname ,BaseU.Fname_l2 ,");
    	
    	//工作流审批意见
    	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForMap(billId);
    	this.kDTable1.getCell(9, 0).setValue(apporveResultForMap.get("项目主管工程师（项目经理）意见；审核人"));
    	this.kDTable1.getCell(12, 0).setValue(apporveResultForMap.get("造价工程师意见；审核人"));
    	this.kDTable1.getCell(15, 1).setValue(apporveResultForMap.get("工程部经理；审核人"));
    	this.kDTable1.getCell(15, 3).setValue(apporveResultForMap.get("项目工资常务副总经理；审核人"));
    	this.kDTable1.getCell(16, 1).setValue(apporveResultForMap.get("收文签证；审核人"));
    	this.kDTable1.getCell(16, 3).setValue(apporveResultForMap.get("时间；时间"));
    	
    }
    
    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
    	super.kDTable1_tableClicked(e);
    	FDCMsgBox.showInfo("行："+e.getRowIndex()+"\n列："+e.getColIndex());
    }

    protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

}