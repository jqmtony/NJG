/**
 * output package name
 */
package com.kingdee.eas.fdc.wfui;

import java.awt.event.*;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.*;

/**
 * 目标成本单审批界面
 * output class name
 */
public class TargetApproveUI extends AbstractTargetApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(TargetApproveUI.class);
    
    /**
     * output class constructor
     */
    public TargetApproveUI() throws Exception
    {
        super();
    }
    
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	initUI();
    }
   
    private void initUI() throws BOSException, SQLException{
    	this.kDTable1.addColumns(10);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();
    	
    	//第一行
    	IRow addRow = this.kDTable1.addRow();
    	addRow.getCell(0).setValue("成本简介:");
    	addRow.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow.getCell(1).setValue("营业收入:");
    	addRow.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow.getCell(5).setValue("项目成本开发");
    	addRow.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(0, 1, 0, 2);
      	mergeManager.mergeBlock(0, 3, 0, 4);
      	mergeManager.mergeBlock(0, 5, 0, 6);
    	mergeManager.mergeBlock(0, 7, 0, 9);
    	
    	//第二行
    	IRow addRowtwo = this.kDTable1.addRow();
    	addRowtwo.getCell(0).setValue("项目名称:");
    	addRowtwo.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(1, 1, 1, 9);
    	
    	//第三行
    	IRow addRowthree = this.kDTable1.addRow();
    	addRowthree.getCell(0).setValue("工程地址:");
    	addRowthree.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(2, 1, 2, 9);
    	
    	
    	//第四行
    	IRow addRowfour = this.kDTable1.addRow();
    	addRowfour.getCell(0).setValue("建设单位:");
    	addRowfour.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(3, 1, 3, 9);

    	//第五行
    	IRow addRowfive = this.kDTable1.addRow();
    	addRowfive.getCell(0).setValue("编制单位:");
    	addRowfive.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(4, 1, 4, 9);

    	
    	//第六行
    	IRow addRowsix = this.kDTable1.addRow();
    	addRowsix.getCell(0).setValue("编制部门:");
    	addRowsix.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(1).setValue("前期(配套)部:");
    	addRowsix.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(5).setValue("设计部:");
    	addRowsix.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(5, 1, 5, 2);
    	mergeManager.mergeBlock(5, 3, 5, 4);
    	mergeManager.mergeBlock(5, 5, 5, 6);
    	mergeManager.mergeBlock(5, 7, 5, 9);
    	mergeManager.mergeBlock(5, 0, 8, 0);
    	
    	//第七行
    	IRow addRowseven = this.kDTable1.addRow();
    	addRowseven.getCell(0).setValue("编制部门:");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(1).setValue("营销部:");
    	addRowseven.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(5).setValue("行政部:");
    	addRowseven.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(6, 1, 6, 2);
    	mergeManager.mergeBlock(6, 3, 6, 4);
    	mergeManager.mergeBlock(6, 5, 6, 6);
    	mergeManager.mergeBlock(6, 7, 6, 9);
    	mergeManager.mergeBlock(5, 0, 8, 0);



    	
    	//第八行
    	IRow addRoweight = this.kDTable1.addRow();
    	addRoweight.getCell(0).setValue("编制部门:");
    	addRoweight.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(1).setValue("工程部:");
    	addRoweight.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(5).setValue("成本部:");
    	addRoweight.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(7, 1, 7, 2);
       	mergeManager.mergeBlock(7, 3, 7, 4);
       	mergeManager.mergeBlock(7, 5, 7, 6);
       	mergeManager.mergeBlock(7, 7, 7, 9);
       	mergeManager.mergeBlock(5, 0, 8, 0);

    	
    	
    	//第九行
    	IRow addRownine = this.kDTable1.addRow();
    	addRownine.getCell(0).setValue("编制部门:");
    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(1).setValue("财务部:");
    	addRownine.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(8, 1, 8, 2);
    	mergeManager.mergeBlock(8, 3, 8, 4);
    	mergeManager.mergeBlock(8, 5, 8, 6);
    	mergeManager.mergeBlock(8, 7, 8, 9);
    	mergeManager.mergeBlock(5, 0, 8, 0);

    	//第十行
    	IRow addRowten = this.kDTable1.addRow();
    	addRowten.getCell(0).setValue("审核人:");
    	addRowten.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(1).setValue("项目公司第一负责人:");
    	addRowten.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(5).setValue("城市公司/地区总部第一负责人:");
    	addRowten.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(9, 1, 9, 2);
    	mergeManager.mergeBlock(9, 3, 9, 4);
    	mergeManager.mergeBlock(9, 5, 9, 6);
    	mergeManager.mergeBlock(9, 7, 9, 9);

    	
    	//第十一行
    	IRow addRowelev = this.kDTable1.addRow();
    	addRowelev.getCell(0).setValue("集团审核部门:");
    	addRowelev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(1).setValue("成本管理中心:");
    	addRowelev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(5).setValue("设计管理中心:");
    	addRowelev.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(10, 1, 10, 2);
    	mergeManager.mergeBlock(10, 3, 10, 4);
    	mergeManager.mergeBlock(10, 5, 10, 6);
    	mergeManager.mergeBlock(10, 7, 10, 9);
    	mergeManager.mergeBlock(10, 0, 12, 0);

    	

    	//第十二行
    	IRow addRowtwev= this.kDTable1.addRow();
    	addRowtwev.getCell(0).setValue("集团审核部门:");
    	addRowtwev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwev.getCell(1).setValue("营销管理中心:");
    	addRowtwev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwev.getCell(5).setValue("工程管理中心:");
    	addRowtwev.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(11, 1, 11, 2);
    	mergeManager.mergeBlock(11, 3, 11, 4);
    	mergeManager.mergeBlock(11, 5, 11, 6);
    	mergeManager.mergeBlock(11, 7, 11, 9);
    	mergeManager.mergeBlock(10, 0, 12, 0);
    	
    	//第十三行
    	IRow addRowthirt= this.kDTable1.addRow();
    	addRowthirt.getCell(0).setValue("集团审核部门:");
    	addRowthirt.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(1).setValue("营运管理中心:");
    	addRowthirt.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(5).setValue("财务管理中心:");
    	addRowthirt.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(12, 1, 12, 2);
    	mergeManager.mergeBlock(12, 3, 12, 4);
    	mergeManager.mergeBlock(12, 5, 12, 6);
    	mergeManager.mergeBlock(12, 7, 12, 9);
    	mergeManager.mergeBlock(10, 0, 12, 0);
  	
    	//第十四行
    	IRow addRowfout= this.kDTable1.addRow();
    	addRowfout.getCell(0).setValue("集团审批人:");
    	addRowfout.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfout.getCell(2).setValue("工程成本副总裁:");
    	addRowfout.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(13, 1, 13, 2);
    	mergeManager.mergeBlock(13, 3, 13, 9);
    	mergeManager.mergeBlock(13, 0, 15, 0);
    	
    	//第十五行
    	IRow addRowfift= this.kDTable1.addRow();
    	addRowfift.getCell(0).setValue("集团审批人:");
    	addRowfift.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(2).setValue("执行副总裁:");
    	addRowfift.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(14, 1, 14, 2);
    	mergeManager.mergeBlock(14, 3, 14, 9);
    	mergeManager.mergeBlock(13, 0, 15, 0);
    	

    	
    	//第十六行
    	IRow addRowsist= this.kDTable1.addRow();
    	addRowsist.getCell(0).setValue("集团审批人:");
    	addRowsist.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsist.getCell(1).setValue("总裁:");
    	addRowsist.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(15, 1, 15, 2);
    	mergeManager.mergeBlock(15, 3, 15, 9);
    	mergeManager.mergeBlock(13, 0, 15, 0);
    	


    	
    	//第十七行
    	IRow addRowsevent= this.kDTable1.addRow();
    	addRowsevent.getCell(5).setValue("(业务日期)");
    	addRowsevent.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsevent.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(16, 0, 16, 9);
    	
    	this.kDTable1.getColumn(0).setWidth(100);
    	this.kDTable1.getColumn(1).setWidth(120);
    	this.kDTable1.getColumn(2).setWidth(50);
    	this.kDTable1.getColumn(3).setWidth(75);
    	this.kDTable1.getColumn(4).setWidth(80);
    	this.kDTable1.getColumn(5).setWidth(75);
    	this.kDTable1.getColumn(6).setWidth(75);
    	this.kDTable1.getColumn(7).setWidth(50);
    	this.kDTable1.getColumn(8).setWidth(50);
    	this.kDTable1.getColumn(9).setWidth(50);
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    }

  

	protected IObjectValue createNewData() {
		return null;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

}