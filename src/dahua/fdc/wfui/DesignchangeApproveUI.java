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
 * 设计变更申请单审批界面
 * output class name
 */
public class DesignchangeApproveUI extends AbstractDesignchangeApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(DesignchangeApproveUI.class);
    
    /**
     * output class constructor
     */
    public DesignchangeApproveUI() throws Exception
    {
        super();
    }

    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	initUI();
    }
   
    private void initUI() throws BOSException, SQLException{
    	this.kDTable1.addColumns(13);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();
    	
    	//第一行
    	IRow addRow = this.kDTable1.addRow();
    	addRow.getCell(0).setValue("事项名称:");
    	addRow.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);

    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(0, 1, 0, 12);

    	//第二行
    	IRow addRowtwo = this.kDTable1.addRow();
    	addRowtwo.getCell(0).setValue("适用范围:");
    	addRowtwo.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor); 	
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(1, 1, 1, 12);
    	
    	//第三行
    	IRow addRowthree = this.kDTable1.addRow();
    	addRowthree.getCell(0).setValue("提出方:");
    	addRowthree.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthree.getCell(1).setValue("XX");
    	addRowthree.getCell(2).setValue("设计部");
    	addRowthree.getCell(3).setValue("XX");
    	addRowthree.getCell(4).setValue("工程部");
    	addRowthree.getCell(5).setValue("XX");
    	addRowthree.getCell(6).setValue("前期配套部");
    	addRowthree.getCell(7).setValue("XX");
    	addRowthree.getCell(8).setValue("销售部");
    	addRowthree.getCell(9).setValue("XX");
    	addRowthree.getCell(10).setValue("其他");    	
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(2, 0, 3, 0);
    	mergeManager.mergeBlock(2, 10,2, 12);

    	
    	
    	//第四行
    	IRow addRowfour = this.kDTable1.addRow();
    	addRowfour.getCell(0).setValue("提出方");
    	addRowfour.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfour.getCell(1).setValue("经办人:人员");
    	addRowfour.getCell(4).setValue("审核人:人员:");   
    	addRowfour.getCell(7).setValue("提出时间:");
    	addRowfour.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(3, 1, 3, 3);
    	mergeManager.mergeBlock(3, 4, 3, 6);
    	mergeManager.mergeBlock(3, 7, 3, 8);
    	mergeManager.mergeBlock(3, 9, 3, 12);
    	mergeManager.mergeBlock(2, 0, 3, 0);

    	//第五行
    	IRow addRowfive = this.kDTable1.addRow();
    	addRowfive.getCell(0).setValue("变更原因及建议方案");
    	addRowfive.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(4, 1, 4, 12);

    	
    	//第六行
    	IRow addRowsix = this.kDTable1.addRow();
    	addRowsix.getCell(0).setValue("部门意见");
    	addRowsix.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(1).setValue("部门");
    	addRowsix.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(5).setValue("预估产生影响");
    	addRowsix.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(11).setValue("负责人签字");
    	addRowsix.getCell(11).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(12).setValue("日期");
    	addRowsix.getCell(12).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(5, 2, 5, 10);
    	mergeManager.mergeBlock(5, 0, 10, 0);
    	
    	//第七行
    	IRow addRowseven = this.kDTable1.addRow();
    	addRowseven.getCell(0).setValue("部门意见");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(1).setValue("设计部");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(2).setValue("产品品质");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(3).setValue("XX");
    	addRowseven.getCell(4).setValue("提高");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(5).setValue("XX");
    	addRowseven.getCell(6).setValue("降低");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(7).setValue("XX");
    	addRowseven.getCell(8).setValue("无影响");
    	addRowseven.getCell(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(6, 8, 6, 10);
    	mergeManager.mergeBlock(5, 0, 10, 0);


    	
    	//第八行
    	IRow addRoweight = this.kDTable1.addRow();
    	addRoweight.getCell(0).setValue("部门意见");
    	addRoweight.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(1).setValue("工程部:");
    	addRoweight.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(2).setValue("工期");
    	addRoweight.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(3).setValue("XX");
    	addRoweight.getCell(4).setValue("缩短");
    	addRoweight.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(5).setValue("是否返工");
    	addRoweight.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(7).setValue("XX");
    	addRoweight.getCell(8).setValue("需返工");
    	addRoweight.getCell(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(9).setValue("XX");
    	addRoweight.getCell(10).setValue("无需返工");
    	addRoweight.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(7, 5, 7, 6);
    	mergeManager.mergeBlock(5, 0, 10, 0);
    	
    	
    	//第九行
    	IRow addRownine = this.kDTable1.addRow();
    	addRownine.getCell(0).setValue("部门意见");
    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(1).setValue("销售部:");
    	addRownine.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(2).setValue("销售");
    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(3).setValue("XX");
    	addRownine.getCell(4).setValue("有利");
    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(5).setValue("XX");
    	addRownine.getCell(6).setValue("不利");
    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(7).setValue("XX");
    	addRownine.getCell(8).setValue("无影响");
    	addRownine.getCell(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(8, 8, 8, 10);
    	mergeManager.mergeBlock(5, 0, 10, 0);

    	//第十行
    	IRow addRowten = this.kDTable1.addRow();
    	addRowten.getCell(0).setValue("部门意见");
    	addRowten.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(1).setValue("前期配套部");
    	addRowten.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(9, 2, 9, 10);
    	mergeManager.mergeBlock(5, 0, 10, 0);

    	
    	//第十一行
    	IRow addRowelev = this.kDTable1.addRow();
    	addRowelev.getCell(0).setValue("部门意见");
    	addRowelev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(1).setValue("合约部");
    	addRowelev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(2).setValue("成本");
    	addRowelev.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(3).setValue("XX");
    	addRowelev.getCell(4).setValue("增加");
    	addRowelev.getCell(4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(5).setValue("XX");
    	addRowelev.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(6).setValue("减少");
    	addRowelev.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(7).setValue("XX");
    	addRowelev.getCell(8).setValue("零");
    	addRowelev.getCell(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(9).setValue("费用估价：");
    	addRowelev.getCell(10).setValue("万元");
    	addRoweight.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(5, 0, 10, 0);

    	

    	//第十二行
    	IRow addRowtwev= this.kDTable1.addRow();
    	addRowtwev.getCell(0).setValue("公司第一负责人:");
    	addRowtwev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(11, 1, 11, 12);
    	mergeManager.mergeBlock(11, 0, 12, 0);
    	
    	//第十三行
    	IRow addRowthirt= this.kDTable1.addRow();
    	addRowthirt.getCell(0).setValue("公司第一负责人:");
    	addRowthirt.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(1).setValue("第一负责人签字:");
    	addRowthirt.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(11).setValue("日期");
    	addRowthirt.getCell(11).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(12, 1, 12, 2);
    	mergeManager.mergeBlock(12, 3, 12, 10);
    	mergeManager.mergeBlock(11, 0, 12, 0);
  	
    	//第十四行
    	IRow addRowfout= this.kDTable1.addRow();
    	addRowfout.getCell(0).setValue("城市公司或地区总部第一负责人");
    	addRowfout.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(13, 1, 13, 12);
    	mergeManager.mergeBlock(13, 0, 14, 0);
    	
    	//第十五行
    	IRow addRowfift= this.kDTable1.addRow();
    	addRowfift.getCell(0).setValue("城市公司或地区总部第一负责人");
    	addRowfift.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(1).setValue("第一负责人签字");
    	addRowfift.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(11).setValue("日期");
    	addRowfift.getCell(11).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(14, 1, 14, 2);
    	mergeManager.mergeBlock(14, 3, 14, 10);
    	mergeManager.mergeBlock(13, 0, 14, 0);
    	
    	
    	
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