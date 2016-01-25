/**
 * output package name
 */
package com.kingdee.eas.fdc.wfui;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.client.ProjectChangeAuditEditUI;
import com.kingdee.eas.fdc.costindexdb.client.BuildPriceIndexEditUI;
import com.kingdee.eas.framework.ICoreBase;
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
    	
    	kDTable2.getStyleAttributes().setLocked(true);
    	if (getOprtState().equals("设计部修改")){
    		kDTable2.getRow(7).getStyleAttributes().setLocked(false);
    	}
    	if (getOprtState().equals("工程部修改")){
    		kDTable2.getRow(5).getStyleAttributes().setLocked(false);
    		kDTable2.getRow(8).getStyleAttributes().setLocked(false);
//    		kDTable2.getRow(9).getStyleAttributes().setLocked(false);
    	}
    	if (getOprtState().equals("销售部修改")){
    		kDTable2.getRow(10).getStyleAttributes().setLocked(false);
    		kDTable2.getRow(11).getStyleAttributes().setLocked(false);
    	}
    	if (getOprtState().equals("前期配套部修改")){
    		kDTable2.getRow(12).getStyleAttributes().setLocked(false);
    	}
//    	if (getOprtState().equals("成本部修改")){
//    		kDTable1.getRow(6).getStyleAttributes().setLocked(false);
//    		kDTable1.getRow(11).getStyleAttributes().setLocked(false);
//    	}
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
    	addRow9.getCell(2).setValue("工程部印章");
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
    	addRow211.getCell(9).setEditor(editor);
    	addRow211.getCell(9).setValue(Boolean.FALSE);
    	addRow211.getCell(10).setValue("无影响");
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
    	addRow214.getCell(10).setValue("元");
    	mergeManager2.mergeBlock(13, 4, 13, 8);
    	
    	
    	
    	IRow addRow215 = this.kDTable2.addRow();
    	addRow215.getCell(4).setValue("其中返工造成的签证费用估算：");
    	addRow215.getCell(10).setValue("元");
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
    	addRow218.getCell(4).setValue("变更后合同金额是否超出合约规划金额");
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
    	mergeManager2.mergeBlock(21, 2, 21, 3);
    	mergeManager2.mergeBlock(21, 4, 21, 12);
    	IRow addRow223 = this.kDTable2.addRow();
    	addRow223.getCell(2).setValue("成本管理中心");
    	mergeManager2.mergeBlock(22, 2, 22, 3);
    	mergeManager2.mergeBlock(22, 4, 22, 12);
    	IRow addRow224 = this.kDTable2.addRow();
    	addRow224.getCell(2).setValue("营销管理中心");
    	mergeManager2.mergeBlock(23, 2, 23, 3);
    	mergeManager2.mergeBlock(23, 4, 23, 12);
    	IRow addRow225 = this.kDTable2.addRow();
    	addRow225.getCell(2).setValue("商业管理中心");
    	mergeManager2.mergeBlock(24, 2, 24, 3);
    	mergeManager2.mergeBlock(24, 4, 24, 12);
    	IRow addRow226 = this.kDTable2.addRow();
    	addRow226.getCell(0).setValue("审阅栏");
    	addRow226.getCell(2).setValue("运营管理中心");
    	mergeManager2.mergeBlock(25, 2, 25, 3);
    	mergeManager2.mergeBlock(25, 4, 25, 12);
    	mergeManager2.mergeBlock(21, 0, 25, 1);
    	
    	IRow addRow227 = this.kDTable2.addRow();
    	addRow227.getCell(2).setValue("工程成本副总裁");
    	mergeManager2.mergeBlock(26, 2, 26, 3);
    	mergeManager2.mergeBlock(26, 4, 26, 12);
    	IRow addRow228 = this.kDTable2.addRow();
    	addRow228.getCell(2).setValue("执行副总裁");
    	mergeManager2.mergeBlock(27, 2, 27, 3);
    	mergeManager2.mergeBlock(27, 4, 27, 12);
    	IRow addRow229 = this.kDTable2.addRow();
    	addRow229.getCell(0).setValue("审批栏");
    	addRow229.getCell(2).setValue("总裁");
    	mergeManager2.mergeBlock(28, 2, 28, 3);
    	mergeManager2.mergeBlock(28, 4, 28, 12);
    	mergeManager2.mergeBlock(26, 0, 28, 1);
    	
    	
    	
    	//行宽
    	int i;
    	for(i=0;i<kDTable1.getRowCount();i++)
    	{
    		kDTable1.getRow(i).setHeight(50);
    		addRow3.setHeight(1);
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
    	
    	String billId = editData.getId()!=null?editData.getId().toString():"2MBmc4+oSsaUTg2k2uOxx3ARYRc=";
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select ChangeAB.FCurProjectName 项目名称1,ChangeAB.FNumber 申请编号2 ,to_char(ChangeAB.CFPutForwardTime,'yyyy-mm-dd') 提出时间, ChangeAB.Freadesc 事由,BaseU.Fname_l2 提出部门,");
    	sb.append(" contractB.fname 合同名称6,contractB.fnumber 合同编号7,lier.fname_l2  主送,Su.fname_l2  抄送,ChangeAE.FChangeContent,u.Fname_l2,ChangeAE.FIsBack isBack");
    	sb.append(" ,ChangeAB.CFQuality 产品品质 ,ChangeAB.CFTimeLi 工期 ,ChangeAB.CFSale 销售 ,CFCost 成本");
    	sb.append(" ,ChangeAB.CFSFEJJD 二级节点 ,ChangeAB.CFXSCN 销售承诺,ChangeAB.CFBJZB 报建指标 ,ChangeAB.CFContractAmPro 合同百分比,ChangeAB.CFTotalChangeAmount 累计百分比,ChangeAB.FTotalCost 估算工程金额,ChangeAB.CFReworkVisa 返工签证费用 ");
    	sb.append(" from T_CON_ChangeAuditBill ChangeAB");
    	sb.append(" left join T_ORG_BaseUnit BaseU on BaseU.fid=ChangeAB.FConductDeptID");
    	sb.append(" left join T_CON_ChangeAuditEntry ChangeAE on ChangeAB.fid=ChangeAE.FParentID");
    	sb.append(" left join T_CON_ChangeSupplierEntry ChangeSE on ChangeAB.fid=ChangeSE.FParentID");
    	sb.append(" left join T_CON_CopySupplierEntry copySE on ChangeSE.fid=copySE.FParentID");
    	sb.append(" left join T_BD_Supplier lier on lier.fid =  ChangeSE.FMainSuppID");
    	sb.append(" left join T_BD_Supplier Su on Su.fid =  copySE.fCopySuppID");
    	sb.append(" left join T_CON_ContractBill contractB on contractB.fid=ChangeSE.FContractBillID");
      	sb.append(" left join T_PM_User u on u.fid = ChangeAB.FCreatorID");
    	sb.append(" where ChangeAB.fid = '").append(billId).append("'");
    	
    	IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
    	//部门
    	String Bm = null;
    	//返工
    	boolean fg = false;
    	while(rowset.next()){
    		kDTextField1.setText(rowset.getString(1));
    		kDTextField2.setText(rowset.getString(2));
    		
    		this.kDTable1.getCell(0, 1).setValue(rowset.getString(1));
//    		this.kDTable1.getCell(0, 3).setValue(rowset.getString(2));
    		Bm = rowset.getString(5);
    		this.kDTable1.getCell(1, 1).setValue(rowset.getString(7));
    		this.kDTable1.getCell(3, 1).setValue(rowset.getString(8));
    		this.kDTable1.getCell(3, 3).setValue(rowset.getString(9));
    		this.kDTable1.getCell(4, 1).setValue(rowset.getString(10) + "\n");
    		this.kDTable1.getCell(9, 1).setValue(rowset.getString(11));
    		
    		this.kDTable2.getCell(0, 2).setValue(rowset.getString(6));
    		this.kDTable2.getCell(1, 2).setValue(rowset.getString(7));
    		//内容
    		this.kDTable2.getCell(3, 5).setValue(rowset.getString(10) + "\n");
    		this.kDTable2.getCell(1, 11).setValue(rowset.getString(3));
    		//估算工程金额
        	BigDecimal je = rowset.getBigDecimal("估算工程金额");
    		this.kDTable2.getCell(13, 9).setValue(je);
    		this.kDTable2.getCell(14, 9).setValue(rowset.getBigDecimal("返工签证费用"));
    		this.kDTable2.getCell(15, 9).setValue(rowset.getString("合同百分比"));
    		this.kDTable2.getCell(16, 9).setValue(rowset.getString("累计百分比"));
    		
    		if(rowset.getBoolean("isBack"))
    			fg = true;	
    		if(Bm != null && Bm.indexOf("设计")!=-1)
        	{
    			addRow23.getCell(2).setValue(Boolean.TRUE);
        	}
        	else if(Bm.indexOf("工程")!=-1)
        	{
        		addRow23.getCell(5).setValue(Boolean.TRUE);
        	}
        	else if(Bm.indexOf("配套")!=-1)
        	{
        		addRow23.getCell(7).setValue(Boolean.TRUE);
        	}
        	else if(Bm.indexOf("销售")!=-1)
        	{
        		addRow23.getCell(9).setValue(Boolean.TRUE);
        	}
        	else 
        	{
        		addRow23.getCell(11).setValue(Boolean.TRUE);
        	}
        	//返工
        	if(fg){
        		this.kDTable2.getCell(9, 5).setValue(Boolean.TRUE);
        	}
        	else{
        		this.kDTable2.getCell(9, 7).setValue(Boolean.TRUE);
        	}
        	//返工去双选
    		if((Boolean)kDTable2.getCell(9, 5).getValue() && (Boolean)kDTable2.getCell(9, 7).getValue()){
    			this.kDTable2.getCell(9, 7).setValue(Boolean.FALSE);
    		}
        	//填充二级节点
        	if(rowset.getBoolean("二级节点"))
        		this.kDTable2.getCell(5, 5).setValue(Boolean.TRUE);
        	else
        		this.kDTable2.getCell(5, 7).setValue(Boolean.TRUE);
        	//填充产品品质
        	String quality = rowset.getString("产品品质")!=null?rowset.getString("产品品质"):"";
        	if(quality.equals("提高"))
        		this.kDTable2.getCell(7, 5).setValue(Boolean.TRUE);
        	if(quality.equals("降低"))
        		this.kDTable2.getCell(7, 7).setValue(Boolean.TRUE);
        	if(quality.equals("无影响"))
        		this.kDTable2.getCell(7, 9).setValue(Boolean.TRUE);
        	//填充工期
        	String TimeLi = rowset.getString("工期")!=null?rowset.getString("工期"):"";
        	if(TimeLi.equals("缩短"))
        		this.kDTable2.getCell(8, 5).setValue(Boolean.TRUE);
        	if(TimeLi.equals("延长"))
        		this.kDTable2.getCell(8, 7).setValue(Boolean.TRUE);
        	if(TimeLi.equals("无影响"))
        		this.kDTable2.getCell(8, 9).setValue(Boolean.TRUE);
        	//填充销售
        	String Sale = rowset.getString("销售")!=null?rowset.getString("销售"):"";
        	if(Sale.equals("有利"))
        		this.kDTable2.getCell(10, 5).setValue(Boolean.TRUE);
        	if(Sale.equals("无利"))
        		this.kDTable2.getCell(10, 7).setValue(Boolean.TRUE);
        	if(Sale.equals("无影响"))
        		this.kDTable2.getCell(10, 9).setValue(Boolean.TRUE);
        	//填充销售承诺
        	if(rowset.getBoolean("销售承诺"))
        		this.kDTable2.getCell(11, 5).setValue(Boolean.TRUE);
        	else
        		this.kDTable2.getCell(11, 7).setValue(Boolean.TRUE);
        	//填充报建指标
        	if(rowset.getBoolean("报建指标"))
        		this.kDTable2.getCell(12, 5).setValue(Boolean.TRUE);
        	else
        		this.kDTable2.getCell(12, 7).setValue(Boolean.TRUE);
    	}
    	   	
    	//工作流审批意见
    	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForMap(billId);
    	if(apporveResultForMap.get("工程部经理") != null){
    		//总意见
    		String result = apporveResultForMap.get("工程部经理");	
    		String person = result.substring(0,result.indexOf("!"));
    		this.kDTable1.getCell(9, 3).setValue(person);

    	}
    	if(apporveResultForMap.get("设计部") != null){
    		String result = apporveResultForMap.get("设计部");
    		
    		String person = result.substring(0,result.indexOf("!"));  		
    		String yijian = result.substring(result.indexOf("!")+1,result.indexOf("@"));	
    		this.kDTable2.getCell(7, 12).setValue(yijian);  		
    		this.kDTable2.getCell(7, 11).setValue(person);
    	}
    	if(apporveResultForMap.get("工程部") != null){
    		String result = apporveResultForMap.get("工程部");
    		
    		String person = result.substring(0,result.indexOf("!"));  		
    		String yijian = result.substring(result.indexOf("!")+1,result.indexOf("@"));	
    		this.kDTable2.getCell(8, 12).setValue(yijian);  		
    		this.kDTable2.getCell(8, 11).setValue(person);
    		this.kDTable2.getCell(9, 12).setValue(yijian);  		
    		this.kDTable2.getCell(9, 11).setValue(person);
    	}
    	if(apporveResultForMap.get("销售部") != null){
    		String result = apporveResultForMap.get("销售部");
    		
    		String person = result.substring(0,result.indexOf("!"));  		
    		String yijian = result.substring(result.indexOf("!")+1,result.indexOf("@"));	
    		this.kDTable2.getCell(10, 12).setValue(yijian);  		
    		this.kDTable2.getCell(10, 11).setValue(person);
    		this.kDTable2.getCell(11, 12).setValue(yijian);  		
    		this.kDTable2.getCell(11, 11).setValue(person);
    	}
    	if(apporveResultForMap.get("前期配套部") != null){
    		String result = apporveResultForMap.get("前期配套部");
    		
    		String person = result.substring(0,result.indexOf("!"));  		
    		String yijian = result.substring(result.indexOf("!")+1,result.indexOf("@"));	
    		this.kDTable2.getCell(12, 12).setValue(yijian);  		
    		this.kDTable2.getCell(12, 11).setValue(person);
    	}
    	if(apporveResultForMap.get("成本部") != null){
    		String result = apporveResultForMap.get("成本部");
    		
    		String person = result.substring(0,result.indexOf("!"));  		
    		String yijian = result.substring(result.indexOf("!")+1,result.indexOf("@"));	
    		this.kDTable2.getCell(13, 12).setValue(yijian);  		
    		this.kDTable2.getCell(13, 11).setValue(person);
    		this.kDTable2.getCell(14, 12).setValue(yijian);  		
    		this.kDTable2.getCell(14, 11).setValue(person);
    		this.kDTable2.getCell(15, 12).setValue(yijian);  		
    		this.kDTable2.getCell(15, 11).setValue(person);
    		this.kDTable2.getCell(16, 12).setValue(yijian);  		
    		this.kDTable2.getCell(16, 11).setValue(person);
    		this.kDTable2.getCell(17, 12).setValue(yijian);  		
    		this.kDTable2.getCell(17, 11).setValue(person);
    	}
    	if(apporveResultForMap.get("公司第一负责人") != null){
    		//总意见
    		String result = apporveResultForMap.get("公司第一负责人");
    		
    		String person = result.substring(0,result.indexOf("!"));  		
    		String yijian = result.substring(result.indexOf("!")+1,result.indexOf("@"));	
    		String date = result.substring(result.indexOf("@")+1);
    		this.kDTable2.getCell(18, 2).setValue(yijian);  		
    		this.kDTable2.getCell(20, 10).setValue(person);
    		if(!"".equals(date))
    			this.kDTable2.getCell(20, 12).setValue(date);
    	}
    	
    	//工作流审批意见
    	Map<String, String> apporveResultForMaptwo = WFResultApporveHelper.getApporveResultForPerson(billId);
    			this.kDTable2.getCell(21, 4).setValue(apporveResultForMaptwo.get("工程管理中心"));
    			this.kDTable2.getCell(22, 4).setValue(apporveResultForMaptwo.get("成本管理中心"));
    			this.kDTable2.getCell(23, 4).setValue(apporveResultForMaptwo.get("营销管理中心"));
    			this.kDTable2.getCell(24, 4).setValue(apporveResultForMaptwo.get("商业管理中心"));
    			this.kDTable2.getCell(25, 4).setValue(apporveResultForMaptwo.get("运营管理中心"));
    			this.kDTable2.getCell(26, 4).setValue(apporveResultForMaptwo.get("工程成本副总裁"));
    			this.kDTable2.getCell(27, 4).setValue(apporveResultForMaptwo.get("执行副总裁"));
    			this.kDTable2.getCell(28, 4).setValue(apporveResultForMaptwo.get("总裁"));
    	
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
    	
    	//设计部修改
    	if(getOprtState().equals("设计部修改")){
    		int i = 0;
    		if((Boolean)kDTable2.getCell(7, 5).getValue())
    			i++;
    		if((Boolean)kDTable2.getCell(7, 7).getValue())
    			i++;	
    		if((Boolean)kDTable2.getCell(7, 9).getValue())
    			i++;	
    		if(i == 0){
    			FDCMsgBox.showInfo("产品品质：你并没有勾选");
    			SysUtil.abort();
    		}else if(i > 1){
    			FDCMsgBox.showInfo("产品品质：你只能勾选一个");
    			SysUtil.abort();
    		}
    		//产品品质
    		if((Boolean)kDTable2.getCell(7, 5).getValue()){
    			editData.setQuality("提高");
			}
			else if((Boolean)kDTable2.getCell(7, 7).getValue()) 		
				editData.setQuality("降低");
			else if((Boolean)kDTable2.getCell(7, 7).getValue()) 		
				editData.setQuality("无影响");
    	}
    		
    	//工程部修改
    	if(getOprtState().equals("工程部修改")){
    		int i = 0;
    		//二级节点
    		if((Boolean)kDTable2.getCell(5, 5).getValue())
    			i++;
    		if((Boolean)kDTable2.getCell(5, 7).getValue())
    			i++;			
    		if(i == 0){
    			FDCMsgBox.showInfo("二级节点：你并没有勾选");
    			SysUtil.abort();
    		}else if(i > 1){
    			FDCMsgBox.showInfo("二级节点：你只能勾选一个");
    			SysUtil.abort();
    		}
			if((Boolean)kDTable2.getCell(5, 5).getValue()){
				editData.setSfejjd(Boolean.TRUE);
			}
			else if((Boolean)kDTable2.getCell(5, 7).getValue()) 		
				editData.setSfejjd(Boolean.FALSE);
			
			//工期
			if((Boolean)kDTable2.getCell(8, 5).getValue())
    			i++;
    		if((Boolean)kDTable2.getCell(8, 7).getValue())
    			i++;
    		if((Boolean)kDTable2.getCell(8, 9).getValue())
    			i++;
    		if(i == 1){
    			FDCMsgBox.showInfo("工期：你并没有勾选");
    			SysUtil.abort();
    		}else if(i > 2){
    			FDCMsgBox.showInfo("工期：你只能勾选一个");
    			SysUtil.abort();
    		}
    		if((Boolean)kDTable2.getCell(8, 5).getValue()){
    			editData.setTimeLi("缩短");
			}
			else if((Boolean)kDTable2.getCell(8, 7).getValue()) 		
				editData.setTimeLi("延长");
			else if((Boolean)kDTable2.getCell(8, 9).getValue()) 		
				editData.setTimeLi("无影响");
    	}
    	//销售部修改
    	if(getOprtState().equals("销售部修改")){
    		int i = 0;
    		//销售
    		if((Boolean)kDTable2.getCell(10, 5).getValue())
    			i++;
    		if((Boolean)kDTable2.getCell(10, 7).getValue())
    			i++;			
    		if((Boolean)kDTable2.getCell(10, 9).getValue())
    			i++;			
    		if(i == 0){
    			FDCMsgBox.showInfo("销售：你并没有勾选");
    			SysUtil.abort();
    		}else if(i > 1){
    			FDCMsgBox.showInfo("销售：你只能勾选一个");
    			SysUtil.abort();
    		}
			if((Boolean)kDTable2.getCell(10, 5).getValue()){
				editData.setSale("有利");
			}
			else if((Boolean)kDTable2.getCell(10, 7).getValue()) 		
				editData.setSale("无利");
			else if((Boolean)kDTable1.getCell(10, 9).getValue())
    			editData.setSale("无影响");
			//销售承诺
			if((Boolean)kDTable2.getCell(11, 5).getValue())
    			i++;
    		if((Boolean)kDTable2.getCell(11, 7).getValue())
    			i++;
    		if(i == 1){
    			FDCMsgBox.showInfo("销售承诺：你并没有勾选");
    			SysUtil.abort();
    		}else if(i > 2){
    			FDCMsgBox.showInfo("销售承诺：你只能勾选一个");
    			SysUtil.abort();
    		}
    		if((Boolean)kDTable2.getCell(11, 5).getValue()){
    			editData.setXscn(Boolean.TRUE);
			}
			else if((Boolean)kDTable2.getCell(11, 7).getValue()) 		
				editData.setXscn(Boolean.FALSE);
    	}
    	//前期配套部修改
    	if(getOprtState().equals("前期配套部修改")){
    		int i = 0;
    		//报建指标
    		if((Boolean)kDTable2.getCell(12, 5).getValue())
    			i++;
    		if((Boolean)kDTable2.getCell(12, 7).getValue())
    			i++;			
    		if(i == 0){
    			FDCMsgBox.showInfo("报建指标：你并没有勾选");
    			SysUtil.abort();
    		}else if(i > 1){
    			FDCMsgBox.showInfo("报建指标：你只能勾选一个");
    			SysUtil.abort();
    		}
			if((Boolean)kDTable2.getCell(12, 5).getValue()){
				editData.setBjzb(Boolean.TRUE);
			}
			else if((Boolean)kDTable2.getCell(12, 7).getValue()) 		
				editData.setBjzb(Boolean.FALSE);
    	}
    }
    
    //回到原单
	protected void yd_actionPerformed(ActionEvent e) throws Exception {
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", editData.getId());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProjectChangeAuditEditUI.class.getName(),uiContext,null,OprtState.VIEW);
		uiWindow.show();
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