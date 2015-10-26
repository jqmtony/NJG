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
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.*;

/**
 * 技术核定审批表审批界面
 * output class name
 */
public class TechnicalverificationApproveUI extends AbstractTechnicalverificationApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(TechnicalverificationApproveUI.class);
    
    /**
     * output class constructor
     */
    public TechnicalverificationApproveUI() throws Exception
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
    	addRow1.getCell(0).setValue("项目名称");
    	addRow1.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow1.getCell(2).setValue("发文时间");
    	addRow1.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	IRow addRow2 = this.kDTable1.addRow();
    	addRow2.getCell(0).setValue("事由");
    	addRow2.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(1, 1, 1, 3);
    	
    	IRow addRow3 = this.kDTable1.addRow();
    	addRow3.getCell(0).setValue("合同名称");
    	addRow3.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow3.getCell(2).setValue("合同编号");
    	addRow3.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	
    	IRow addRow4 = this.kDTable1.addRow();
    	addRow4.getCell(0).setValue("核定内容");
    	mergeManager.mergeBlock(3, 0, 6, 3);
    	IRow addRow5 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(3, 0, 6, 3);
    	IRow addRow6 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(3, 0, 6, 3);
    	IRow addRow7 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(3, 0, 6, 3);
    	
    	IRow addRow8 = this.kDTable1.addRow();
    	addRow8.getCell(0).setValue("施工单位经办");
    	addRow8.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow8.getCell(2).setValue("施工单位审批");
    	addRow8.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	IRow addRow9 = this.kDTable1.addRow();
    	addRow9.getCell(0).setValue("监理单位签字、盖章");
    	addRow9.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(8, 1, 8, 3);
    	
    	IRow addRow10 = this.kDTable1.addRow();
    	addRow10.getCell(0).setValue("监理单位签字、盖章");
    	addRow10.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(9, 1, 9, 3);
    	
    	IRow addRow11 = this.kDTable1.addRow();
    	addRow11.getCell(0).setValue("监理单位签字、盖章");
    	addRow11.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(10, 1, 10, 3);
    	
    	this.kDTable1.getColumn(0).setWidth(200);
    	this.kDTable1.getColumn(1).setWidth(200);
    	this.kDTable1.getColumn(2).setWidth(200);
    	this.kDTable1.getColumn(3).setWidth(200);
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    	
    	//_________________________________________________________________________________________________________________________
    	this.kDTable2.addColumns(13);
    	KDTMergeManager mergeManager2 = kDTable2.getMergeManager();
    	
    	IRow addRow21 = this.kDTable2.addRow();
    	addRow21.getCell(0).setValue("合同名称");
    	addRow21.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager2.mergeBlock(0, 2, 0, 9);
    	mergeManager2.mergeBlock(0, 0, 0, 1);
    	
    	IRow addRow22 = this.kDTable2.addRow();
    	addRow22.getCell(0).setValue("合同编号");
    	addRow22.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager2.mergeBlock(1, 2, 1, 9);
    	mergeManager2.mergeBlock(1, 0, 1, 1);
    	
    	IRow addRow23 = this.kDTable2.addRow();
    	addRow23.getCell(0).setValue("提出方");
    	addRow23.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager2.mergeBlock(2, 0, 2, 1);
    	
    	addRow23.getCell(2).setValue("XX");
    	mergeManager2.mergeBlock(2, 2, 2, 3);
    	addRow23.getCell(4).setValue("设计部");
    	addRow23.getCell(4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow23.getCell(5).setValue("XX");
    	addRow23.getCell(6).setValue("工程部");
    	addRow23.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow23.getCell(7).setValue("XX");
    	addRow23.getCell(8).setValue("前期配套部");
    	addRow23.getCell(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow23.getCell(9).setValue("XX");
    	addRow23.getCell(10).setValue("销售部");
    	addRow23.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow23.getCell(11).setValue("XX");
    	addRow23.getCell(12).setValue("其他");
    	addRow23.getCell(12).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	IRow addRow24 = this.kDTable2.addRow();
    	addRow24.getCell(0).setValue("工程部");
    	addRow24.getCell(2).setValue("发起原因及内容：");
    	mergeManager2.mergeBlock(3, 0, 5, 1);
    	mergeManager2.mergeBlock(3, 2, 5, 12);
    	IRow addRow25 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(3, 0, 5, 1);
    	mergeManager2.mergeBlock(3, 2, 5, 12);
    	IRow addRow26 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(3, 0, 5, 1);
    	mergeManager2.mergeBlock(3, 2, 5, 12);
    	
    	IRow addRow27 = this.kDTable2.addRow();
    	addRow27.getCell(0).setValue("部门意见");
    	addRow27.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow27.getCell(2).setValue("部门");
    	mergeManager2.mergeBlock(6, 2, 6, 3);
    	mergeManager2.mergeBlock(6, 0, 16, 1);
    	addRow27.getCell(4).setValue("预估产生影响");
    	mergeManager2.mergeBlock(6, 4, 6, 10);
    	addRow27.getCell(11).setValue("负责人");
    	addRow27.getCell(12).setValue("确认意见");
    	
    	IRow addRow28 = this.kDTable2.addRow();
    	addRow28.getCell(2).setValue("设计部");
    	addRow28.getCell(4).setValue("产品品质：");
    	addRow28.getCell(5).setValue("XX");
    	addRow28.getCell(6).setValue("提高");
    	addRow28.getCell(7).setValue("XX");
    	addRow28.getCell(8).setValue("降低");
    	addRow28.getCell(9).setValue("XX");
    	addRow28.getCell(10).setValue("无影响");
    	mergeManager2.mergeBlock(6, 0, 16, 1);
    	mergeManager2.mergeBlock(7, 2, 7, 3);
    	IRow addRow29 = this.kDTable2.addRow();//--
    	addRow29.getCell(2).setValue("工程部");
    	addRow29.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow29.getCell(4).setValue("工期：");
    	addRow29.getCell(5).setValue("XX");
    	addRow29.getCell(6).setValue("缩短");
    	addRow29.getCell(7).setValue("XX");
    	addRow29.getCell(8).setValue("延长");
    	addRow29.getCell(9).setValue("XX");
    	addRow29.getCell(10).setValue("无影响");
    	mergeManager2.mergeBlock(6, 0, 16, 1);
    	mergeManager2.mergeBlock(8, 2, 9, 2);
    	mergeManager2.mergeBlock(8, 3, 9, 3);
    	IRow addRow210 = this.kDTable2.addRow();
    	addRow210.getCell(4).setValue("是否返工：");
    	addRow210.getCell(5).setValue("XX");
    	addRow210.getCell(6).setValue("需要返工");
    	addRow210.getCell(7).setValue("XX");
    	addRow210.getCell(8).setValue("不需要返工");
    	mergeManager2.mergeBlock(6, 0, 16, 1);
    	mergeManager2.mergeBlock(8, 2, 9, 2);
    	mergeManager2.mergeBlock(8, 3, 9, 3);
    	IRow addRow211 = this.kDTable2.addRow();
    	addRow211.getCell(2).setValue("销售部");
    	addRow211.getCell(4).setValue("销售：");
    	addRow211.getCell(5).setValue("XX");
    	addRow211.getCell(6).setValue("有利");
    	addRow211.getCell(7).setValue("XX");
    	addRow211.getCell(8).setValue("不利");
    	addRow211.getCell(9).setValue("XX");
    	addRow211.getCell(10).setValue("无影响");
    	mergeManager2.mergeBlock(6, 0, 16, 0);
    	mergeManager2.mergeBlock(6, 1, 16, 1);
    	mergeManager2.mergeBlock(10, 2, 10, 3);
    	IRow addRow212 = this.kDTable2.addRow();
    	addRow212.getCell(2).setValue("前期配套部");
    	mergeManager2.mergeBlock(6, 0, 16, 0);
    	mergeManager2.mergeBlock(6, 1, 16, 1);
    	mergeManager2.mergeBlock(11, 2, 11, 3);
    	
    	IRow addRow213 = this.kDTable2.addRow();
    	addRow213.getCell(2).setValue("合约部");
    	addRow213.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager2.mergeBlock(6, 0, 16, 0);
    	mergeManager2.mergeBlock(6, 1, 16, 1);
    	mergeManager2.mergeBlock(12, 2, 16, 2);
    	mergeManager2.mergeBlock(12, 3, 16, 3);
    	addRow213.getCell(4).setValue("估算工程费增减总价：");
    	addRow213.getCell(10).setValue("万元");
    	mergeManager2.mergeBlock(12, 4, 12, 8);
    	IRow addRow214 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(6, 0, 16, 0);
    	mergeManager2.mergeBlock(6, 1, 16, 1);
    	mergeManager2.mergeBlock(12, 2, 16, 2);
    	mergeManager2.mergeBlock(12, 3, 16, 3);
    	addRow214.getCell(4).setValue("其中返工造成的签证费用估算：");
    	addRow214.getCell(10).setValue("万元");
    	mergeManager2.mergeBlock(13, 4, 13, 8);
    	IRow addRow215 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(6, 0, 16, 0);
    	mergeManager2.mergeBlock(6, 1, 16, 1);
    	mergeManager2.mergeBlock(12, 2, 16, 2);
    	mergeManager2.mergeBlock(12, 3, 16, 3);
    	addRow215.getCell(4).setValue("本次变更累计占合同价的百分比");
    	addRow215.getCell(10).setValue("%");
    	mergeManager2.mergeBlock(14, 4, 14, 8);
    	IRow addRow216 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(6, 0, 16, 0);
    	mergeManager2.mergeBlock(6, 1, 16, 1);
    	mergeManager2.mergeBlock(12, 2, 16, 2);
    	mergeManager2.mergeBlock(12, 3, 16, 3);
    	addRow216.getCell(4).setValue("截至目前变更累计占合同价百分比");
    	addRow216.getCell(10).setValue("%");
    	mergeManager2.mergeBlock(15, 4, 15, 8);
    	IRow addRow217 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(6, 0, 16, 0);
    	mergeManager2.mergeBlock(6, 1, 16, 1);
    	mergeManager2.mergeBlock(12, 2, 16, 2);
    	mergeManager2.mergeBlock(12, 3, 16, 3);
    	addRow217.getCell(4).setValue("变更后合同金额是否超出目标成本限额");
    	addRow217.getCell(9).setValue("XX");
    	mergeManager2.mergeBlock(16, 4, 16, 8);
    	
    	IRow addRow218 = this.kDTable2.addRow();
    	addRow218.getCell(0).setValue("公司第一负责人：");
    	addRow218.getCell(2).setValue("（项目公司负责人意见）");
    	mergeManager2.mergeBlock(17, 2, 17, 12);
//    	mergeManager2.mergeBlock(17, 0, 18, 1);
    	IRow addRow219 = this.kDTable2.addRow();
    	
    	mergeManager2.mergeBlock(17, 0, 18, 1);
    	addRow219.getCell(2).setValue("第一负责人签字：");
    	mergeManager2.mergeBlock(18, 2, 18, 3);
    	mergeManager2.mergeBlock(18, 4, 18, 10);
    	addRow219.getCell(11).setValue("日期：");
    	
    	IRow addRow220 = this.kDTable2.addRow();
    	addRow220.getCell(0).setValue("第一负责人签字：");
    	addRow220.getCell(2).setValue("工程管理部：");
    	mergeManager2.mergeBlock(19, 2, 19, 3);
    	mergeManager2.mergeBlock(19, 4, 19, 10);
    	addRow220.getCell(11).setValue("合约审算部：");
    	IRow addRow221 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(19, 0, 20, 1);
    	addRow221.getCell(2).setValue("中心负责人：");
    	mergeManager2.mergeBlock(20, 2, 20, 3);
    	mergeManager2.mergeBlock(20, 4, 20, 10);
    	addRow221.getCell(11).setValue("分管副总裁审批：");
    	
    	this.kDTable2.getColumn(0).setWidth(90);
    	this.kDTable2.getColumn(1).setWidth(20);
    	this.kDTable2.getColumn(2).setWidth(80);
    	this.kDTable2.getColumn(3).setWidth(25);
    	this.kDTable2.getColumn(4).setWidth(70);
    	this.kDTable2.getColumn(5).setWidth(40);
    	this.kDTable2.getColumn(6).setWidth(70);
    	this.kDTable2.getColumn(7).setWidth(40);
    	this.kDTable2.getColumn(8).setWidth(70);
    	this.kDTable2.getColumn(9).setWidth(40);
    	this.kDTable2.getColumn(10).setWidth(70);
    	this.kDTable2.getColumn(11).setWidth(105);
    	this.kDTable2.getColumn(12).setWidth(70);
    	this.kDTable2.getIndexColumn().getStyleAttributes().setHided(true);
    	
    	
    	String billId = "FrYcumfRTUq/iL87J1M2VXARYRc=";
//    	StringBuffer sb = new StringBuffer();
//    	sb.append("  select ChangeAB.FCurProjectName ,ChangeAB.FNumber ,ChangeAB.Fname ,BaseU.Fname_l2 ,");
    	
    	
    	//工作流审批意见
    	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForMap(billId);
    	this.kDTable1.getCell(7, 1).setValue(apporveResultForMap.get("施工单位经办"));
    	this.kDTable1.getCell(7, 3).setValue(apporveResultForMap.get("施工单位审批"));
    	this.kDTable1.getCell(8, 1).setValue(apporveResultForMap.get("监理单位签字"));
    	this.kDTable1.getCell(8, 1).setValue(apporveResultForMap.get("监理单位盖章"));
    	this.kDTable1.getCell(9, 1).setValue(apporveResultForMap.get("设计单位签字"));
    	this.kDTable1.getCell(9, 1).setValue(apporveResultForMap.get("设计单位盖章"));
    	this.kDTable1.getCell(10, 1).setValue(apporveResultForMap.get("建设单位签字"));
    	this.kDTable1.getCell(10, 1).setValue(apporveResultForMap.get("建设单位盖章"));
    	
//    	工程部发起原因及内容
//    	设计部，影响，负责人，意见
//    	工程部，影响，负责人，意见
//    	销售部，影响，负责人，意见
//    	前期配套部，影响，负责人，意见
//    	合约部，影响（各项指标），负责人，意见
//    	公司第一负责人，意见，签字，日期
//    	工程管理部
//    	合约审算部
//    	中心负责人
//    	分管副总裁审批
    	this.kDTable2.getCell(3, 2).setValue(apporveResultForMap.get("工程部发起原因及内容"));
//    	this.kDTable2.getCell(10, 1).setValue(apporveResultForMap.get("设计部，影响"));
    	this.kDTable2.getCell(7, 11).setValue(apporveResultForMap.get("设计部，负责人"));
    	this.kDTable2.getCell(7, 12).setValue(apporveResultForMap.get("设计部，意见"));
//    	this.kDTable2.getCell(10, 1).setValue(apporveResultForMap.get("工程部，影响"));
    	this.kDTable2.getCell(8, 11).setValue(apporveResultForMap.get("工程部，负责人"));
    	this.kDTable2.getCell(8, 12).setValue(apporveResultForMap.get("工程部，意见"));
//    	this.kDTable2.getCell(10, 1).setValue(apporveResultForMap.get("销售部，影响"));
    	this.kDTable2.getCell(9, 11).setValue(apporveResultForMap.get("销售部，负责人"));
    	this.kDTable2.getCell(9, 12).setValue(apporveResultForMap.get("销售部，意见"));
//    	this.kDTable2.getCell(10, 1).setValue(apporveResultForMap.get("前期配套部，影响"));
    	this.kDTable2.getCell(10, 11).setValue(apporveResultForMap.get("前期配套部，负责人"));
    	this.kDTable2.getCell(10, 12).setValue(apporveResultForMap.get("前期配套部，意见"));
//    	this.kDTable2.getCell(10, 1).setValue(apporveResultForMap.get("合约部，影响"));
    	this.kDTable2.getCell(11, 11).setValue(apporveResultForMap.get("合约部，负责人"));
    	this.kDTable2.getCell(11, 12).setValue(apporveResultForMap.get("合约部，意见"));
    	this.kDTable2.getCell(17, 2).setValue(apporveResultForMap.get("公司第一负责人，意见"));
    	this.kDTable2.getCell(18, 4).setValue(apporveResultForMap.get("公司第一负责人，签字"));
    	this.kDTable2.getCell(18, 12).setValue(apporveResultForMap.get("公司第一负责人，日期"));
    	this.kDTable2.getCell(19, 4).setValue(apporveResultForMap.get("工程管理部"));
    	this.kDTable2.getCell(19, 12).setValue(apporveResultForMap.get("合约审算部"));
    	this.kDTable2.getCell(20, 4).setValue(apporveResultForMap.get("中心负责人"));
    	this.kDTable2.getCell(20, 12).setValue(apporveResultForMap.get("分管副总裁审批"));
    	
    }
    
    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
    	super.kDTable1_tableClicked(e);
    	FDCMsgBox.showInfo("行："+e.getRowIndex()+"\n列："+e.getColIndex());
    }
    
    protected void kDTable2_tableClicked(KDTMouseEvent e) throws Exception {
    	super.kDTable2_tableClicked(e);
    	FDCMsgBox.showInfo("行："+e.getRowIndex()+"\n列："+e.getColIndex());
    }

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

}