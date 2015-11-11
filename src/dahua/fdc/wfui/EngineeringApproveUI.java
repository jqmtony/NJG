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
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 工程指令单审批界面
 * output class name
 */
public class EngineeringApproveUI extends AbstractEngineeringApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(EngineeringApproveUI.class);
    
    /**
     * output class constructor
     */
    public EngineeringApproveUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.kDTable2.getStyleAttributes().setWrapText(true);
    	initUI();
    }
    
    private void initUI() throws BOSException, SQLException{
    	
    	//新增布尔控件
    	KDCheckBox cb = new KDCheckBox();
    	KDTDefaultCellEditor editor = new KDTDefaultCellEditor(cb);
    	
    	this.kDTable1.addColumns(4);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();
    	IRow addRow1 = this.kDTable1.addRow();
    	addRow1.getCell(0).setValue("项目名称");
    	addRow1.getCell(2).setValue("核定编号");
    	
    	IRow addRow2 = this.kDTable1.addRow();
    	addRow2.getCell(0).setValue("合同编号");
    	mergeManager.mergeBlock(1, 1, 1, 3);
    	
    	IRow addRow3 = this.kDTable1.addRow();
//    	addRow3.getCell(0).setValue("建设单位");
//    	addRow3.getCell(2).setValue("工程图纸编号");
    	
    	IRow addRow4 = this.kDTable1.addRow();
    	addRow4.getCell(0).setValue("主送");
    	addRow4.getCell(2).setValue("抄送");
    	
    	IRow addRow5 = this.kDTable1.addRow();
    	addRow5.getCell(0).setValue("内容");
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
//    	mergeManager.mergeBlock(4, 4, 7, );
    	IRow addRow6 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
    	IRow addRow7 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
    	IRow addRow8 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
    	
    	IRow addRow9 = this.kDTable1.addRow();
    	addRow9.getCell(2).setValue("工程部印章：");
    	mergeManager.mergeBlock(8, 0, 8, 1);
    	
    	IRow addRow10 = this.kDTable1.addRow();
    	addRow10.getCell(0).setValue("经办人");
    	addRow10.getCell(2).setValue("工程部经理");
    	
    	
    	this.kDTable1.getColumn(0).setWidth(201);
    	this.kDTable1.getColumn(1).setWidth(201);
    	this.kDTable1.getColumn(2).setWidth(201);
//    	this.kDTable1.getColumn(3).setWidth(203);
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    	//-----------------------------------------------------------------------------------------------------------------
    	this.kDTable2.addColumns(13);
    	KDTMergeManager mergeManager2 = kDTable2.getMergeManager();
    	
    	IRow addRow21 = this.kDTable2.addRow();
    	addRow21.getCell(0).setValue("合同名称");
    	mergeManager2.mergeBlock(0, 2, 0, 12);
    	mergeManager2.mergeBlock(0, 0, 0, 1);
    	
    	IRow addRow22 = this.kDTable2.addRow();
    	addRow22.getCell(0).setValue("合同编号");
    	mergeManager2.mergeBlock(1, 2, 1, 9);
    	mergeManager2.mergeBlock(1, 0, 1, 1);
    	addRow22.getCell(10).setValue("提出时间");
    	mergeManager2.mergeBlock(1, 11, 1, 12);
    	
    	IRow addRow23 = this.kDTable2.addRow();
    	addRow23.getCell(0).setValue("提出方");
    	mergeManager2.mergeBlock(2, 0, 2, 1);
    	addRow23.getCell(2).setEditor(editor);
    	addRow23.getCell(2).setValue(Boolean.FALSE);
    	mergeManager2.mergeBlock(2, 2, 2, 3);
    	
    	addRow23.getCell(4).setValue("设计部");
//    	addRow23.getCell(5).setValue("XX");
    	addRow23.getCell(5).setEditor(editor);
    	addRow23.getCell(5).setValue(Boolean.FALSE);
    	addRow23.getCell(6).setValue("工程部");
//    	addRow23.getCell(7).setValue("XX");
    	addRow23.getCell(7).setEditor(editor);
    	addRow23.getCell(7).setValue(Boolean.FALSE);
    	addRow23.getCell(8).setValue("前期配套部");
//    	addRow23.getCell(9).setValue("XX");
    	addRow23.getCell(9).setEditor(editor);
    	addRow23.getCell(9).setValue(Boolean.FALSE);
    	addRow23.getCell(10).setValue("销售部");
//    	addRow23.getCell(11).setValue("XX");
    	addRow23.getCell(11).setEditor(editor);
    	addRow23.getCell(11).setValue(Boolean.FALSE);
    	addRow23.getCell(12).setValue("其他");
    	
    	IRow addRow24 = this.kDTable2.addRow();
    	addRow24.getCell(0).setValue("工程部");
    	addRow24.getCell(2).setValue("发起原因及内容：");
//    	mergeManager2.mergeBlock(3, 0, 5, 0);
//    	mergeManager2.mergeBlock(3, 2, 3, 4);
//    	mergeManager2.mergeBlock(3, 5, 3, 12);
    	
    	IRow addRow25 = this.kDTable2.addRow();
//    	addRow25.getCell(2).setValue("发起原因及内容：");
//    	mergeManager2.mergeBlock(3, 0, 5, 0);
//    	mergeManager2.mergeBlock(4, 2, 4, 4);
//    	mergeManager2.mergeBlock(4, 5, 4, 12);
    	IRow addRow26 = this.kDTable2.addRow();
    	addRow26.getCell(2).setValue("是否影响二级及以上节点：");
    	mergeManager2.mergeBlock(3, 0, 5, 0);
    	mergeManager2.mergeBlock(5, 2, 5, 4);
    	addRow26.getCell(5).setEditor(editor);
    	addRow26.getCell(5).setValue(Boolean.FALSE);
    	addRow26.getCell(6).setValue("是");
    	addRow26.getCell(7).setEditor(editor);
    	addRow26.getCell(7).setValue(Boolean.FALSE);
    	addRow26.getCell(8).setValue("否");
    	mergeManager2.mergeBlock(5, 8, 5, 12);
    	
    	mergeManager2.mergeBlock(3, 0, 5, 1);
    	mergeManager2.mergeBlock(3, 2, 4, 4);
    	mergeManager2.mergeBlock(3, 5, 4, 12);
    	
    	IRow addRow27 = this.kDTable2.addRow();
    	addRow27.getCell(0).setValue("部门意见");
    	addRow27.getCell(2).setValue("部门");
    	mergeManager2.mergeBlock(6, 2, 6, 3);
    	addRow27.getCell(4).setValue("预估产生影响");
    	mergeManager2.mergeBlock(6, 4, 6, 10);
    	addRow27.getCell(11).setValue("负责人");
    	addRow27.getCell(12).setValue("确认意见");
    	
    	IRow addRow28 = this.kDTable2.addRow();
    	addRow28.getCell(2).setValue("设计部");
    	addRow28.getCell(4).setValue("产品品质：");
//    	addRow28.getCell(5).setValue("XX");
    	addRow28.getCell(5).setEditor(editor);
    	addRow28.getCell(5).setValue(Boolean.FALSE);
    	addRow28.getCell(6).setValue("提高");
//    	addRow28.getCell(7).setValue("XX");
    	addRow28.getCell(7).setEditor(editor);
    	addRow28.getCell(7).setValue(Boolean.FALSE);
    	addRow28.getCell(8).setValue("降低");
//    	addRow28.getCell(9).setValue("XX");
    	addRow28.getCell(9).setEditor(editor);
    	addRow28.getCell(9).setValue(Boolean.FALSE);
    	addRow28.getCell(10).setValue("无影响");
    	mergeManager2.mergeBlock(7, 2, 7, 3);
    	IRow addRow29 = this.kDTable2.addRow();//--
    	addRow29.getCell(2).setValue("工程部");
    	addRow29.getCell(4).setValue("工期：");
//    	addRow29.getCell(5).setValue("XX");
    	addRow29.getCell(5).setEditor(editor);
    	addRow29.getCell(5).setValue(Boolean.FALSE);
    	addRow29.getCell(6).setValue("缩短");
//    	addRow29.getCell(7).setValue("XX");
    	addRow29.getCell(7).setEditor(editor);
    	addRow29.getCell(7).setValue(Boolean.FALSE);
    	addRow29.getCell(8).setValue("延长");
//    	addRow29.getCell(9).setValue("XX");
    	addRow29.getCell(9).setEditor(editor);
    	addRow29.getCell(9).setValue(Boolean.FALSE);
    	addRow29.getCell(10).setValue("无影响");
    	mergeManager2.mergeBlock(8, 2, 9, 3);
    	IRow addRow210 = this.kDTable2.addRow();
    	addRow210.getCell(4).setValue("是否返工：");
//    	addRow210.getCell(5).setValue("XX");
    	addRow210.getCell(5).setEditor(editor);
    	addRow210.getCell(5).setValue(Boolean.FALSE);
    	addRow210.getCell(6).setValue("需要返工");
//    	addRow210.getCell(7).setValue("XX");
    	addRow210.getCell(7).setEditor(editor);
    	addRow210.getCell(7).setValue(Boolean.FALSE);
    	addRow210.getCell(8).setValue("不需要返工");
    	mergeManager2.mergeBlock(8, 2, 9, 3);
    	
    	IRow addRow211 = this.kDTable2.addRow();
    	addRow211.getCell(2).setValue("销售部");
    	addRow211.getCell(4).setValue("销售：");
//    	addRow211.getCell(5).setValue("XX");
    	addRow211.getCell(5).setEditor(editor);
    	addRow211.getCell(5).setValue(Boolean.FALSE);
    	addRow211.getCell(6).setValue("有利");
    	addRow211.getCell(7).setValue("XX");
    	addRow211.getCell(7).setEditor(editor);
    	addRow211.getCell(7).setValue(Boolean.FALSE);
    	addRow211.getCell(8).setValue("不利");
    	mergeManager2.mergeBlock(10, 2, 10, 3);
    	
    	IRow addRow212 = this.kDTable2.addRow();
    	addRow212.getCell(4).setValue("销售承诺");
    	addRow212.getCell(5).setEditor(editor);
    	addRow212.getCell(5).setValue(Boolean.FALSE);
    	addRow212.getCell(6).setValue("影响");
    	addRow212.getCell(7).setEditor(editor);
    	addRow212.getCell(7).setValue(Boolean.FALSE);
    	addRow212.getCell(8).setValue("无影响");
    	
    	

    	mergeManager2.mergeBlock(10, 2, 11, 3);
    	
    	IRow addRow213 = this.kDTable2.addRow();
    	
    	addRow213.getCell(2).setValue("前期配套部");
    	addRow213.getCell(4).setValue("报建指标");
    	addRow213.getCell(5).setEditor(editor);
    	addRow213.getCell(5).setValue(Boolean.FALSE);
    	addRow213.getCell(6).setValue("影响");
    	addRow213.getCell(7).setEditor(editor);
    	addRow213.getCell(7).setValue(Boolean.FALSE);
    	addRow213.getCell(8).setValue("无影响");
    	mergeManager2.mergeBlock(12, 2, 12, 3);
    	
    	
    	IRow addRow214 = this.kDTable2.addRow();
    	addRow214.getCell(2).setValue("成 本 部");
    	addRow214.getCell(4).setValue("估算工程费增减总价：");
    	addRow214.getCell(10).setValue("万元");
    	mergeManager2.mergeBlock(13, 4, 13, 8);
    	
    	
    	
    	IRow addRow215 = this.kDTable2.addRow();
    	addRow215.getCell(4).setValue("其中返工造成的签证费用估算：");
    	addRow215.getCell(10).setValue("万元");
    	mergeManager2.mergeBlock(14, 4, 14, 8);
    	IRow addRow216 = this.kDTable2.addRow();
    	addRow216.getCell(4).setValue("本次变更累计占合同价的百分比");
    	addRow216.getCell(10).setValue("%");
    	mergeManager2.mergeBlock(15, 4, 15, 8);
    	
    	IRow addRow217 = this.kDTable2.addRow();
    	addRow217.getCell(4).setValue("截至目前变更累计占合同价百分比");
    	addRow217.getCell(10).setValue("%");
    	mergeManager2.mergeBlock(16, 4, 16, 8);
    	
    	IRow addRow218 = this.kDTable2.addRow();
    	addRow218.getCell(4).setValue("变更后合同金额是否超出目标成本限额");
    	addRow218.getCell(7).setEditor(editor);
    	addRow218.getCell(7).setValue(Boolean.FALSE);
    	addRow218.getCell(8).setValue("是");
    	addRow218.getCell(9).setEditor(editor);
    	addRow218.getCell(9).setValue(Boolean.FALSE);
    	addRow218.getCell(10).setValue("否");
    	mergeManager2.mergeBlock(17, 4, 17, 6);
    	
    	mergeManager2.mergeBlock(13, 2, 17, 3);
    	mergeManager2.mergeBlock(6, 0, 17, 1);
    	
    	
    	
    	IRow addRow219 = this.kDTable2.addRow();
    	addRow219.getCell(0).setValue("公司第一负责人:");
    	
    	IRow addRow220 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(18, 2, 19, 12);
    	
    	IRow addRow221 = this.kDTable2.addRow();
    	
    	addRow221.getCell(8).setValue("第一负责人签字:");
    	addRow221.getCell(11).setValue("日期");
    	
    	mergeManager2.mergeBlock(20, 2, 20, 7);
    	mergeManager2.mergeBlock(20, 8, 20, 9);
    	mergeManager2.mergeBlock(18, 0, 20, 1);
//    	
    	IRow addRow222 = this.kDTable2.addRow();
    	addRow222.getCell(2).setValue("工程管理中心");
    	mergeManager2.mergeBlock(21, 4, 21, 12);
    	IRow addRow223 = this.kDTable2.addRow();
    	addRow223.getCell(2).setValue("成本管理中心");
    	mergeManager2.mergeBlock(22, 4, 22, 12);
    	IRow addRow224 = this.kDTable2.addRow();
    	addRow224.getCell(2).setValue("营销管理中心");
    	mergeManager2.mergeBlock(23, 4, 23, 12);
    	IRow addRow225 = this.kDTable2.addRow();
    	addRow225.getCell(2).setValue("商业管理中心");
    	mergeManager2.mergeBlock(24, 4, 24, 12);
    	IRow addRow226 = this.kDTable2.addRow();
    	addRow226.getCell(0).setValue("审阅栏");
    	addRow226.getCell(2).setValue("运营管理中心");
    	mergeManager2.mergeBlock(25, 4, 25, 12);
    	mergeManager2.mergeBlock(21, 0, 25, 1);
    	
    	IRow addRow227 = this.kDTable2.addRow();
    	addRow227.getCell(2).setValue("工程成本副总裁");
    	mergeManager2.mergeBlock(26, 4, 26, 12);
    	IRow addRow228 = this.kDTable2.addRow();
    	addRow228.getCell(2).setValue("执行副总裁");
    	mergeManager2.mergeBlock(27, 4, 27, 12);
    	IRow addRow229 = this.kDTable2.addRow();
    	addRow229.getCell(0).setValue("审批栏");
    	addRow229.getCell(2).setValue("总裁");
    	mergeManager2.mergeBlock(28, 4, 28, 12);
    	mergeManager2.mergeBlock(26, 0, 28, 1);
    	
    	
    	
    	//行宽
    	int i;
    	for(i=0;i<kDTable1.getRowCount();i++)
    	{
    		kDTable1.getRow(i).setHeight(50);
    	}
    	//列宽
    	for(i=0;i<kDTable1.getColumnCount();i++)
    	{
    		kDTable1.getColumn(i).setWidth(200);
    	}
    	
    	//行宽
    	for(i=0;i<kDTable2.getRowCount();i++)
    	{
    		kDTable2.getRow(i).setHeight(35);
    	}
    	//列宽
    	for(i=0;i<kDTable2.getColumnCount();i++)
    	{
    		kDTable2.getColumn(i).setWidth(61);
    	}
    	this.kDTable2.getIndexColumn().getStyleAttributes().setHided(true);
    	
    	String billId = editData.getId()!=null?editData.getId().toString():"NuDk97fJRYGlkjRCTg9zcnARYRc=";
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select ChangeAB.FCurProjectName 项目名称1,ChangeAB.FNumber 申请编号2 ,to_char(ChangeAB.CFPutForwardTime,'yyyy-mm-dd') 提出时间, ChangeAB.Freadesc 事由,BaseU.Fname_l2 提出部门,");
    	sb.append(" contractB.fname 合同名称6,contractB.fnumber 合同编号7,ChangeAB.cfzs,ChangeAB.cfcs,ChangeAE.FChangeContent");
//    	sb.append(" ChangeAB.CFQuality 产品品质 ,ChangeAB.CFTimeLi 工期 ,ChangeAB.CFSale 销售 ,CFCost 成本");
    	sb.append(" from T_CON_ChangeAuditBill ChangeAB");
    	sb.append(" left join T_ORG_BaseUnit BaseU on BaseU.fid=ChangeAB.FConductDeptID");
    	sb.append(" left join T_CON_ChangeAuditEntry ChangeAE on ChangeAB.fid=ChangeAE.FParentID");
    	sb.append(" left join T_CON_ChangeSupplierEntry ChangeSE on ChangeAB.fid=ChangeSE.FParentID");
    	sb.append(" left join T_CON_ContractBill contractB on contractB.fid=ChangeSE.FContractBillID");
    	sb.append(" where ChangeAB.fid = '").append(billId).append("'");
    	
    	IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
    	while(rowset.next()){
    		kDTextField1.setText(rowset.getString(1));
    		kDTextField2.setText(rowset.getString(2));
    		
    		this.kDTable1.getCell(0, 1).setValue(rowset.getString(1));
//    		this.kDTable1.getCell(0, 3).setValue(rowset.getString(2));
    		this.kDTable1.getCell(1, 1).setValue(rowset.getString(7));
    		this.kDTable1.getCell(3, 1).setValue(rowset.getString(8));
    		this.kDTable1.getCell(3, 3).setValue(rowset.getString(9));
    		this.kDTable1.getCell(4, 1).setValue(rowset.getString(10));
    		
    		this.kDTable2.getCell(0, 2).setValue(rowset.getString(6));
    		this.kDTable2.getCell(1, 2).setValue(rowset.getString(7));
    		this.kDTable2.getCell(1, 11).setValue(rowset.getString(3));
    	}
    	
    	
    	//工作流审批意见
    	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForMap(billId);
    	this.kDTable1.getCell(9, 1).setValue(apporveResultForMap.get("经办人;审核人"));
    	this.kDTable1.getCell(9, 3).setValue(apporveResultForMap.get("工程部经理;审核人"));
    	this.kDTable2.getCell(17, 2).setValue(apporveResultForMap.get("公司第一负责人，意见"));
    	this.kDTable2.getCell(18, 4).setValue(apporveResultForMap.get("公司第一负责人，签字"));
    	this.kDTable2.getCell(18, 12).setValue(apporveResultForMap.get("公司第一负责人，日期"));
    	this.kDTable2.getCell(19, 4).setValue(apporveResultForMap.get("工程管理部;审核人"));
    	this.kDTable2.getCell(19, 12).setValue(apporveResultForMap.get("合约审算部;审核人"));
    	this.kDTable2.getCell(20, 4).setValue(apporveResultForMap.get("中心负责人;审核人"));
    	this.kDTable2.getCell(20, 12).setValue(apporveResultForMap.get("分管副总裁审批;审核人"));
    	
    }
    
//    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
//    	super.kDTable1_tableClicked(e);
//    	FDCMsgBox.showInfo("行："+e.getRowIndex()+"\n列："+e.getColIndex());
//    }
//    protected void kDTable2_tableClicked(KDTMouseEvent e) throws Exception {
//    	super.kDTable2_tableClicked(e);
//    	FDCMsgBox.showInfo("行："+e.getRowIndex()+"\n列："+e.getColIndex());
//    }
    

    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSave_actionPerformed(e);
    }
    
    
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	super.verifyInput(actionevent);
    	if(getOprtState().equals("自定义")){//如果是自定义状态打开
    		//如果为空则提示
    		if(1!=1){
    			FDCMsgBox.showInfo("自定义状态");
    			SysUtil.abort();
    		}
//    		SelectorItemCollection sic = new SelectorItemCollection();
//    		sic.add("number");
    		editData.setNumber("1111位");
//    		AimAimCostAdjustFactory.getRemoteInstance().updatePartial(editData, sic);
    	}
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSubmit_actionPerformed(e);
    }

	protected IObjectValue createNewDetailData(KDTable kdtable) {
		return null;
	}

	protected KDTable getDetailTable() {
		return kDTable1;
	}


	protected ICoreBase getBizInterface() throws Exception {
		return ChangeAuditBillFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		return new ChangeAuditBillInfo();
	}


}