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
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.*;

/**
 * 设计变更审批表审批界面
 * output class name
 */
public class DesignApproveUI extends AbstractDesignApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(DesignApproveUI.class);
    
    /**
     * output class constructor
     */
    public DesignApproveUI() throws Exception
    {
        super();
    }  
    public void onLoad() throws Exception {
    	super.onLoad();
    	initUI();
    }


    private void initUI() throws BOSException, SQLException{
    	this.kDTable1.addColumns(12);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();

    	//第一行
    	IRow addRow = this.kDTable1.addRow();
    	addRow.getCell(0).setValue("事项名称");
    	addRow.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow.getCell(10).setValue("申请单编号");
    	addRow.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(0, 1, 0, 9);



    	//第二行
    	IRow addRowtwo = this.kDTable1.addRow();
    	addRowtwo.getCell(0).setValue("试用范围");
    	addRowtwo.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwo.getCell(10).setValue("提出时间");
    	addRowtwo.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(1, 1, 1, 9);

    	//第三行
    	IRow addRowthree = this.kDTable1.addRow();
    	addRowthree.getCell(0).setValue("提出方");
    	addRowthree.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthree.getCell(1).setValue("XX");
    	addRowthree.getCell(2).setValue("设计部");
    	addRowthree.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthree.getCell(3).setValue("XX");
    	addRowthree.getCell(4).setValue("工程部");
    	addRowthree.getCell(4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthree.getCell(5).setValue("XX");
    	addRowthree.getCell(6).setValue("前期配套部");
    	addRowthree.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthree.getCell(7).setValue("XX");
    	addRowthree.getCell(8).setValue("销售部");
    	addRowthree.getCell(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthree.getCell(9).setValue("XX");
    	addRowthree.getCell(10).setValue("其他");
    	addRowthree.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(2, 10, 2, 11);

    	//第四行
    	IRow addRowfour = this.kDTable1.addRow();
    	addRowfour.getCell(0).setValue("设计部");
    	addRowfour.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfour.getCell(1).setValue("有无图纸");
    	addRowfour.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfour.getCell(3).setValue("XX");
    	addRowfour.getCell(4).setValue("有");
    	addRowfour.getCell(4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfour.getCell(6).setValue("XX");
    	addRowfour.getCell(7).setValue("无");
    	addRowfour.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);    	
    	addRowfour.getCell(10).setValue("附图编号:");
    	addRowfour.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(3, 1, 3, 2);
    	mergeManager.mergeBlock(3, 4, 3, 5);
    	mergeManager.mergeBlock(3, 7, 3, 9);
    	mergeManager.mergeBlock(3, 0, 5, 0);

    	//第五行
    	IRow addRowfive = this.kDTable1.addRow();
    	addRowfive.getCell(0).setValue("设计部");
    	addRowfive.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfive.getCell(1).setValue("变更原因");
    	addRowfive.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(4, 1, 4, 2);
    	mergeManager.mergeBlock(4, 3, 4, 11);
    	mergeManager.mergeBlock(3, 0, 5, 0);


    	//第六行
    	IRow addRowsix = this.kDTable1.addRow();
    	addRowsix.getCell(0).setValue("设计部");
    	addRowsix.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(1).setValue("经办人");
    	addRowsix.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(3).setValue("审核人");
    	addRowsix.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(10).setValue("日期");
    	addRowsix.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(5, 4, 5, 9);
    	mergeManager.mergeBlock(3, 0, 5, 0);

    	//第七行
    	IRow addRowseven = this.kDTable1.addRow();
    	addRowseven.getCell(0).setValue("合约部");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(1).setValue("变更费用估算:");
    	addRowseven.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(2).setValue("XX");
    	addRowseven.getCell(3).setValue("增加");
    	addRowseven.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(4).setValue("XX");
    	addRowseven.getCell(5).setValue("减少");
    	addRowseven.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(6).setValue("XX");
    	addRowseven.getCell(7).setValue("零");
    	addRowseven.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(8).setValue("XX");
    	addRowseven.getCell(9).setValue("需返工");
    	addRowseven.getCell(9).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(10).setValue("XX");
    	addRowseven.getCell(11).setValue("不需返工");
    	addRowseven.getCell(11).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//第八行
    	IRow addRoweight = this.kDTable1.addRow();
    	addRoweight.getCell(0).setValue("合约部");
    	addRoweight.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(1).setValue("财务部");
    	addRoweight.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
//    	mergeManager.mergeBlock(5, 0, 7, 0);
//    	mergeManager.mergeBlock(7, 1, 7, 2);
//    	mergeManager.mergeBlock(7, 3, 7, 9);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//第九行
    	IRow addRownine = this.kDTable1.addRow();
    	addRownine.getCell(0).setValue("合约部");
    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(1).setValue("项目公司第一负责人");
    	addRownine.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
//    	mergeManager.mergeBlock(8, 0, 9, 0);
//    	mergeManager.mergeBlock(8, 1, 8, 2);
//    	mergeManager.mergeBlock(8, 3, 8, 9);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//第十行
    	IRow addRowten = this.kDTable1.addRow();
    	addRowten.getCell(0).setValue("合约部");
    	addRowten.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(1).setValue("城市公司/地区第一负责人");
    	addRowten.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
//    	mergeManager.mergeBlock(8, 0, 9, 0);
//    	mergeManager.mergeBlock(9, 1, 9, 2);
//    	mergeManager.mergeBlock(9, 3, 9, 9);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//第十一行
    	IRow addRowelev = this.kDTable1.addRow();
    	addRowelev.getCell(0).setValue("合约部");
    	addRowelev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(1).setValue("合约审算部");
    	addRowelev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
//    	mergeManager.mergeBlock(10, 0, 11, 0);
//    	mergeManager.mergeBlock(10, 1, 10, 2);
//    	mergeManager.mergeBlock(10, 3, 10, 9);
      	mergeManager.mergeBlock(6, 0, 12, 0);


    	//第十二行
    	IRow addRowtwev= this.kDTable1.addRow();
    	addRowtwev.getCell(0).setValue("合约部");
    	addRowtwev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwev.getCell(6).setValue("￥");
    	addRowtwev.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
      	mergeManager.mergeBlock(6, 0, 12, 0);
    	
    	//第十三行
    	IRow addRowthirt= this.kDTable1.addRow();
    	addRowthirt.getCell(0).setValue("合约部");
    	addRowthirt.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(6).setValue("￥");
    	addRowthirt.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
     	mergeManager.mergeBlock(6, 0, 12, 0);

    	//第十四行
    	IRow addRowfout= this.kDTable1.addRow();
    	addRowfout.getCell(0).setValue("公司第一负责人:");
    	addRowfout.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(13, 1, 13, 10);
    	mergeManager.mergeBlock(13, 0, 14, 0);

    	//第十五行
    	IRow addRowfift= this.kDTable1.addRow();
    	addRowfift.getCell(0).setValue("公司第一负责人:");
    	addRowfift.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(1).setValue("第一负责人签字:");
    	addRowfift.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(9).setValue("日期");
    	addRowfift.getCell(9).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(14, 1, 14, 2);
    	mergeManager.mergeBlock(14, 3, 14, 8);
    	mergeManager.mergeBlock(13, 0, 14, 0);

    	//第十六行
    	IRow addRowsist= this.kDTable1.addRow();
    	addRowsist.getCell(0).setValue("城市公司或地区总部第一负责人");
    	addRowsist.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(15, 1, 15, 10);
    	mergeManager.mergeBlock(15, 0, 16, 0);

    	//第十七行
    	IRow addRowsevent= this.kDTable1.addRow();
    	addRowsevent.getCell(0).setValue("城市公司或地区总部第一负责人");
    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsevent.getCell(1).setValue("第一负责人签字");
    	addRowsevent.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsevent.getCell(9).setValue("日期");
    	addRowsevent.getCell(9).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(16, 1, 16, 2);
    	mergeManager.mergeBlock(16, 3, 16, 8);
    	mergeManager.mergeBlock(15, 0, 16, 0);

    	//第十八行
    	IRow addRoweighteen= this.kDTable1.addRow();
    	addRoweighteen.getCell(0).setValue("备注");
    	addRoweighteen.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(17, 1, 17, 10);


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