/**
 * output package name
 */
package com.kingdee.eas.fdc.wfui;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustFactory;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.client.DesignChangeAuditEditUI;
import com.kingdee.eas.fdc.contract.client.ProjectChangeAuditEditUI;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

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
    	this.kDTable1.getStyleAttributes().setWrapText(true);
    	super.onLoad();
    	initUI();
    	kDTable1.getStyleAttributes().setLocked(true);
    	if (getOprtState().equals("设计部修改")){//如果是自定义状态打开
    		kDTable1.getRow(3).getStyleAttributes().setLocked(false);
    	}
    	if (getOprtState().equals("成本部修改")){
    		kDTable1.getRow(6).getStyleAttributes().setLocked(false);
    		kDTable1.getRow(11).getStyleAttributes().setLocked(false);

    	}

    }


    private void initUI() throws BOSException, SQLException{
    	
    	//新增布尔控件
    	KDCheckBox cb = new KDCheckBox();
    	KDTDefaultCellEditor editor = new KDTDefaultCellEditor(cb);
    	
    	this.kDTable1.addColumns(12);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();

    	//第一行
    	IRow addRow = this.kDTable1.addRow();
    	addRow.getCell(0).setValue("事项名称");
    	addRow.getCell(6).setValue("申请单编号");
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(0, 1, 0, 5);
    	mergeManager.mergeBlock(0, 7, 0, 11);



    	//第二行
    	IRow addRowtwo = this.kDTable1.addRow();
    	addRowtwo.getCell(0).setValue("试用范围");
    	addRowtwo.getCell(6).setValue("提出时间");
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(1, 1, 1, 5);
    	mergeManager.mergeBlock(1, 7, 1, 11);

    	//第三行
    	IRow addRowthree = this.kDTable1.addRow();
    	addRowthree.getCell(0).setValue("提出方");
    	addRowthree.getCell(1).setEditor(editor);
    	addRowthree.getCell(1).setValue(Boolean.FALSE);
    	addRowthree.getCell(2).setValue("设计部");
    	addRowthree.getCell(3).setEditor(editor);
    	addRowthree.getCell(3).setValue(Boolean.FALSE);
    	addRowthree.getCell(4).setValue("工程部");
    	addRowthree.getCell(5).setEditor(editor);
    	addRowthree.getCell(5).setValue(Boolean.FALSE);
    	addRowthree.getCell(6).setValue("前期配套部");
    	addRowthree.getCell(7).setEditor(editor);
    	addRowthree.getCell(7).setValue(Boolean.FALSE);
    	addRowthree.getCell(8).setValue("销售部");
    	addRowthree.getCell(9).setEditor(editor);
    	addRowthree.getCell(9).setValue(Boolean.FALSE);
    	addRowthree.getCell(10).setValue("其他");
//    	mergeManager.mergeBlock(2, 10, 2, 11);

    	//第四行
    	IRow addRowfour = this.kDTable1.addRow();
    	addRowfour.getCell(0).setValue("设计部");
    	addRowfour.getCell(1).setValue("有无图纸");
    	addRowfour.getCell(2).setEditor(editor);
    	addRowfour.getCell(2).setValue(Boolean.FALSE);
    	addRowfour.getCell(3).setValue("有");
    	addRowfour.getCell(4).setEditor(editor);
    	addRowfour.getCell(4).setValue(Boolean.FALSE);
    	addRowfour.getCell(5).setValue("无");
    	addRowfour.getCell(6).setValue("附图编号:");
    	//融合(1)-(3)是行 2-4是列
//    	mergeManager.mergeBlock(3, 1, 3, 2);
//    	mergeManager.mergeBlock(3, 4, 3, 5);
    	mergeManager.mergeBlock(3, 7, 3, 11);
    	mergeManager.mergeBlock(3, 0, 5, 0);

    	//第五行
    	IRow addRowfive = this.kDTable1.addRow();
    	addRowfive.getCell(1).setValue("变更原因");
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(4, 1, 4, 2);
    	mergeManager.mergeBlock(4, 3, 4, 11);
    	mergeManager.mergeBlock(3, 0, 5, 0);


    	//第六行
    	IRow addRowsix = this.kDTable1.addRow();
    	addRowsix.getCell(1).setValue("经办人");
    	addRowsix.getCell(3).setValue("审核人");
    	addRowsix.getCell(6).setValue("日期");
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(5, 4, 5, 5);
    	mergeManager.mergeBlock(5, 7, 5, 11);
    	mergeManager.mergeBlock(3, 0, 5, 0);

    	//第七行
    	IRow addRowseven = this.kDTable1.addRow();
    	addRowseven.getCell(1).setValue("变更费用估算");
//    	addRowseven.getCell(2).setValue("XX");
    	addRowseven.getCell(2).setEditor(editor);
    	addRowseven.getCell(2).setValue(Boolean.FALSE);
    	addRowseven.getCell(3).setValue("增加");
//    	addRowseven.getCell(4).setValue("XX");
    	addRowseven.getCell(4).setEditor(editor);
    	addRowseven.getCell(4).setValue(Boolean.FALSE);
    	addRowseven.getCell(5).setValue("减少");
//    	addRowseven.getCell(6).setValue("XX");
    	addRowseven.getCell(6).setEditor(editor);
    	addRowseven.getCell(6).setValue(Boolean.FALSE);
    	addRowseven.getCell(7).setValue("零");
//    	addRowseven.getCell(8).setValue("XX");
    	addRowseven.getCell(8).setEditor(editor);
    	addRowseven.getCell(8).setValue(Boolean.FALSE);
    	addRowseven.getCell(9).setValue("需返工");
//    	addRowseven.getCell(10).setValue("XX");
    	addRowseven.getCell(10).setEditor(editor);
    	addRowseven.getCell(10).setValue(Boolean.FALSE);
    	addRowseven.getCell(11).setValue("不需返工");
    	//融合(1)-(3)是行 2-4是列
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//第八行
    	IRow addRoweight = this.kDTable1.addRow();
    	addRoweight.getCell(1).setValue("估算工程费增减总价");
    	addRoweight.getCell(5).setValue("万元");
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(7, 1, 7, 2);
    	mergeManager.mergeBlock(7, 3, 7, 4);
    	mergeManager.mergeBlock(7, 5, 7, 6);
    	mergeManager.mergeBlock(7, 7, 7, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//第九行
    	IRow addRownine = this.kDTable1.addRow();
    	addRownine.getCell(1).setValue("其中返工造成的签证费用估算");
    	KDTableHelper.autoFitRowHeight(this.kDTable1, 8);
    	addRownine.getCell(6).setValue("万元");
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(8, 1, 8, 2);
    	mergeManager.mergeBlock(8, 3, 8, 4);
    	mergeManager.mergeBlock(8, 5, 8, 6);
    	mergeManager.mergeBlock(8, 7, 8, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//第十行
    	IRow addRowten = this.kDTable1.addRow();
    	addRowten.getCell(1).setValue("本次变更占合同价的百分比（%）");
    	addRowten.getCell(5).setValue("%");
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(9, 1, 9, 3);
    	mergeManager.mergeBlock(9, 5, 9, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//第十一行
    	IRow addRowelev = this.kDTable1.addRow();
    	addRowelev.getCell(1).setValue("截止目前变更累计合同总价的百分比（%）");
    	addRowelev.getCell(5).setValue("%");
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(10, 1, 10, 3);
    	mergeManager.mergeBlock(10, 5, 10, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);


    	//第十二行
    	IRow addRowtwev= this.kDTable1.addRow();
    	addRowtwev.getCell(1).setValue("变更后合同金额是否超出合约规划金额");
//    	addRowtwev.getCell(4).setValue("XX");
    	addRowtwev.getCell(4).setEditor(editor);
    	addRowtwev.getCell(4).setValue(Boolean.FALSE);
    	addRowtwev.getCell(5).setValue("是");
//    	addRowtwev.getCell(6).setValue("XX");
    	addRowtwev.getCell(6).setEditor(editor);
    	addRowtwev.getCell(6).setValue(Boolean.FALSE);
    	addRowtwev.getCell(7).setValue("否");
    	
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(11, 1, 11, 3);
      	mergeManager.mergeBlock(6, 0, 12, 0);
    	
    	//第十三行
    	IRow addRowthirt= this.kDTable1.addRow();
    	addRowthirt.getCell(0).setValue("成本部");
    	addRowthirt.getCell(1).setValue("经办人");
    	addRowthirt.getCell(3).setValue("审核人");
    	addRowthirt.getCell(10).setValue("日期");
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(12, 4, 12, 9);
     	mergeManager.mergeBlock(6, 0, 12, 0);

     	
    	IRow addRowfout= this.kDTable1.addRow();
    	IRow addRowfift= this.kDTable1.addRow();
    	mergeManager.mergeBlock(13, 1, 14, 11);
    	IRow addRowsist= this.kDTable1.addRow();
    	addRowsist.getCell(0).setValue("公司第一负责人:");
    	addRowsist.getCell(6).setValue("第一负责人签字：");
    	addRowsist.getCell(10).setValue("日期：");
    	mergeManager.mergeBlock(15, 6, 15, 7);
    	mergeManager.mergeBlock(15, 8, 15, 9);
    	mergeManager.mergeBlock(15, 1, 15, 5);
    	mergeManager.mergeBlock(13, 0, 15, 0);

    	IRow addRowsevent= this.kDTable1.addRow();

    	IRow addRoweighteen= this.kDTable1.addRow();
    	mergeManager.mergeBlock(16, 1, 17, 11);
    	IRow addRow19= this.kDTable1.addRow();
    	addRow19.getCell(0).setValue("城市公司或地区总部第一负责人");
    	addRow19.getCell(6).setValue("第一负责人签字：");
    	addRow19.getCell(10).setValue("日期：");
    	mergeManager.mergeBlock(18, 6, 18, 7);
    	mergeManager.mergeBlock(18, 8, 18, 9);
    	mergeManager.mergeBlock(18, 1, 18, 5);
    	mergeManager.mergeBlock(16, 0, 18, 0);
    	
    	IRow addRow20= this.kDTable1.addRow();
    	addRow20.getCell(1).setValue("工程管理中心");
    	mergeManager.mergeBlock(19, 2, 19, 11);
    	IRow addRow21= this.kDTable1.addRow();
    	addRow21.getCell(1).setValue("营销管理中心");
    	mergeManager.mergeBlock(20, 2, 20, 11);
    	IRow addRow22= this.kDTable1.addRow();
    	addRow22.getCell(1).setValue("商业管理中心");
    	mergeManager.mergeBlock(21, 2, 21, 11);
    	IRow addRow23= this.kDTable1.addRow();
    	addRow23.getCell(1).setValue("成本管理中心");
    	mergeManager.mergeBlock(22, 2, 22, 11);
    	IRow addRow24= this.kDTable1.addRow();
    	addRow24.getCell(1).setValue("运营管理中心");
    	mergeManager.mergeBlock(23, 2, 23, 11);
    	IRow addRow25= this.kDTable1.addRow();
    	addRow25.getCell(0).setValue("审阅栏");
    	addRow25.getCell(1).setValue("设计管理中心");
    	mergeManager.mergeBlock(24, 2, 24, 11);
    	mergeManager.mergeBlock(19, 0, 24, 0);
    	
    	IRow addRow26= this.kDTable1.addRow();
    	addRow26.getCell(1).setValue("设计营销副总裁");
    	mergeManager.mergeBlock(25, 2, 25, 11);
    	IRow addRow27= this.kDTable1.addRow();
    	addRow27.getCell(1).setValue("工程成本副总裁");
    	mergeManager.mergeBlock(26, 2, 26, 11);
    	IRow addRow28= this.kDTable1.addRow();
    	addRow28.getCell(1).setValue("运营商管副总裁");
    	mergeManager.mergeBlock(27, 2, 27, 11);
    	IRow addRow29= this.kDTable1.addRow();
    	addRow29.getCell(1).setValue("执行副总裁");
    	mergeManager.mergeBlock(28, 2, 28, 11);
    	IRow addRow30= this.kDTable1.addRow();
    	addRow30.getCell(0).setValue("审批栏");
    	addRow30.getCell(1).setValue("总裁");
    	mergeManager.mergeBlock(29, 2, 29, 11);
    	mergeManager.mergeBlock(25, 0, 29, 0);
    	IRow addRow31= this.kDTable1.addRow();
    	addRow31.getCell(0).setValue("备   注");
    	mergeManager.mergeBlock(30, 1, 30, 11);
    	
    	

    	//行宽
    	int i;
    	for(i=0;i<kDTable1.getRowCount()-1;i++)
    	{
    		kDTable1.getRow(i).setHeight(27);
    	}
    	kDTable1.getRow(kDTable1.getRowCount()-1).setHeight(150);
    	//列宽
    	for(i=0;i<kDTable1.getColumnCount();i++)
    	{
    		kDTable1.getColumn(i).setWidth(80);
    	}
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    	
    	String billId = editData.getId()!=null?editData.getId().toString():"q7emQGR4RXO5s86vNyJxJXARYRc=";
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select ChangeAB.FCurProjectName 项目名称1 ,ChangeAB.FNumber 申请编号2 , ChangeAB.Freadesc 适用范围 ,BaseU.Fname_l2 提出部门 ,to_char(ChangeAB.CFPutForwardTime,'yyyy-mm-dd') 提出时间5");
    	sb.append(" ,ChangeAB.CFBgyy 变更原因,bill.FNumber,u.Fname_l2,u.Fname_l2,ChangeAE.FIsBack isBack,ChangeAB.CFremark 备注");
    	sb.append(" from T_CON_ChangeAuditBill ChangeAB ");
    	sb.append(" left join T_ORG_BaseUnit BaseU on BaseU.fid=ChangeAB.FConductDeptID");
    	sb.append(" left join T_CON_ChangeAuditEntry ChangeAE on ChangeAB.fid=ChangeAE.FParentID");
    	sb.append(" left join T_CON_ChangeAuditBill bill on ChangeAB.FsourcebillID=bill.FID");
    	sb.append(" left join T_PM_User u on u.fid = ChangeAB.FCreatorID");
    	sb.append(" left join T_PM_User u on u.fid =ChangeAB.FAuditorID");
    	sb.append(" where ChangeAB.fid = '").append(billId).append("'");
    	
    	IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
    	StringBuffer Yuanyi = new StringBuffer();
    	StringBuffer beizu = new StringBuffer();
    	//部门
    	String Bm = null;
    	//返工
    	boolean fg = false;
    	while(rowset.next()){
    		//项目
    		kDTextField1.setText(rowset.getString(1));
    		//变更单编号
    		kDTextField2.setText(rowset.getString(2));
    		
    		this.kDTable1.getCell(0, 1).setValue(rowset.getString(1));
    		this.kDTable1.getCell(1, 1).setValue(rowset.getString(3));
    		Bm = rowset.getString(4);
    		this.kDTable1.getCell(1, 7).setValue(rowset.getString(5));
    		Yuanyi.append(rowset.getString("变更原因")+"\n");
    		beizu.append(rowset.getString("备注")+"\n");
    		this.kDTable1.getCell(0, 7).setValue(rowset.getString(7));
//    		this.kDTable1.getCell(5, 2).setValue(rowset.getString(8));
//    		this.kDTable1.getCell(5, 4).setValue(rowset.getString(9));
    		if(rowset.getBoolean("isBack"))
    			fg = true;

    	}
    	this.kDTable1.getCell(4, 3).setValue(Yuanyi.toString());
    	this.kDTable1.getCell(4, 3).getStyleAttributes().setWrapText(true);
    	this.kDTable1.getCell(30, 1).setValue(beizu.toString());
    	this.kDTable1.getCell(30, 1).getStyleAttributes().setWrapText(true);
    	if(Bm != null && Bm.indexOf("设计")!=-1)
    	{
    		addRowthree.getCell(1).setValue(Boolean.TRUE);
    	}
    	else if(Bm.indexOf("工程")!=-1)
    	{
    		addRowthree.getCell(3).setValue(Boolean.TRUE);
    	}
    	else if(Bm.indexOf("配套")!=-1)
    	{
    		addRowthree.getCell(5).setValue(Boolean.TRUE);
    	}
    	else if(Bm.indexOf("销售")!=-1)
    	{
    		addRowthree.getCell(7).setValue(Boolean.TRUE);
    	}
    	else 
    	{
    		addRowthree.getCell(9).setValue(Boolean.TRUE);
    	}
    	//返工
    	if(fg){
    		this.kDTable1.getCell(6, 8).setValue(Boolean.TRUE);
    	}
    	else{
    		this.kDTable1.getCell(6, 10).setValue(Boolean.TRUE); 	
    	}
    	//工作流审批意见
    	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForMap(billId);
    	if(apporveResultForMap.get("设计部") != null){
    		//总意见
    		String result = apporveResultForMap.get("设计部");
    		
    		String person = result.substring(0,result.indexOf("!"));
    		String date = result.substring(result.indexOf("@")+1);
    		this.kDTable1.getCell(5, 4).setValue(person);
    		if(!"".equals(date))
    			this.kDTable1.getCell(5, 7).setValue(date);
    	}
    	if(apporveResultForMap.get("成本部") != null){
    		//总意见
    		String result = apporveResultForMap.get("成本部");
    		
    		String person = result.substring(0,result.indexOf("!"));
    		String date = result.substring(result.indexOf("@")+1);
    		this.kDTable1.getCell(12, 4).setValue(person);
    		if(!"".equals(date))
    			this.kDTable1.getCell(12, 11).setValue(date);
    	}
    	if(apporveResultForMap.get("公司第一负责人") != null){
    		//总意见
    		String result = apporveResultForMap.get("公司第一负责人");
    		
    		String person = result.substring(0,result.indexOf("!"));
    		String date = result.substring(result.indexOf("@")+1);
    		this.kDTable1.getCell(15, 8).setValue(person);
    		if(!"".equals(date))
    			this.kDTable1.getCell(15, 11).setValue(date);
    	}
    	if(apporveResultForMap.get("城市公司第一负责人") != null){
    		String result = apporveResultForMap.get("城市公司第一负责人");
    		String person = result.substring(0,result.indexOf("!"));
    		String date = result.substring(result.indexOf("@")+1);
    		this.kDTable1.getCell(18, 8).setValue(person);
    		if(!"".equals(date))
    			this.kDTable1.getCell(18, 11).setValue(date);
    	}
    	else {
    		String result = apporveResultForMap.get("地区总部第一负责人");
    		if(result != null){
    			String person = result.substring(0,result.indexOf("!"));
    			String date = result.substring(result.indexOf("@")+1);
    			this.kDTable1.getCell(18, 8).setValue(person);
    			if(!"".equals(date))
    				this.kDTable1.getCell(18, 11).setValue(date);
    		}
    	}
    	//工作流审批意见
    	Map<String, String> apporveResultForMaptwo = WFResultApporveHelper.getApporveResultForPerson(billId);
    			this.kDTable1.getCell(19, 2).setValue(apporveResultForMaptwo.get("工程管理中心"));
    			this.kDTable1.getCell(20, 11).setValue(apporveResultForMaptwo.get("营销管理中心"));
    			this.kDTable1.getCell(21, 11).setValue(apporveResultForMaptwo.get("商业管理中心"));
    			this.kDTable1.getCell(22, 11).setValue(apporveResultForMaptwo.get("成本管理中心"));
    			this.kDTable1.getCell(23, 11).setValue(apporveResultForMaptwo.get("运营管理中心"));
    			this.kDTable1.getCell(24, 11).setValue(apporveResultForMaptwo.get("设计管理中心"));
    			this.kDTable1.getCell(25, 11).setValue(apporveResultForMaptwo.get("设计营销副总裁"));
    			this.kDTable1.getCell(26, 11).setValue(apporveResultForMaptwo.get("工程成本副总裁"));
    			this.kDTable1.getCell(27, 11).setValue(apporveResultForMaptwo.get("运营商管副总裁"));
    			this.kDTable1.getCell(28, 11).setValue(apporveResultForMaptwo.get("执行副总裁"));
    			this.kDTable1.getCell(29, 11).setValue(apporveResultForMaptwo.get("总裁"));
    }
    
//    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
//    	super.kDTable1_tableClicked(e);
//    	FDCMsgBox.showInfo("行："+e.getRowIndex()+"\n列："+e.getColIndex());
//    }

    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSave_actionPerformed(e);
    }
    
    
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	super.verifyInput(actionevent);
    	int i = 0;
    	//设计部修改
    	if(getOprtState().equals("设计部修改")){
    		if((Boolean)kDTable1.getCell(3, 2).getValue())
    			i++;
    		if((Boolean)kDTable1.getCell(3, 4).getValue())
    			i++;			
    		if(i == 0){
    			FDCMsgBox.showInfo("你并没有勾选");
    			SysUtil.abort();
    		}else if(i > 1){
    			FDCMsgBox.showInfo("你只能勾选一个");
    			SysUtil.abort();
			if((Boolean) kDTable1.getCell(3, 2).getValue()){
				editData.setYwtz(Boolean.TRUE);
			}
			else if((Boolean)kDTable1.getCell(3, 4).getValue()) 		
				editData.setYwtz(Boolean.FALSE);
    		
    		}
    	}
    	//成本部
    	if(getOprtState().equals("成本部修改")){
    		 i = 0;
    		if((Boolean)kDTable1.getCell(6, 2).getValue())
    			i++;
    		if((Boolean)kDTable1.getCell(6, 4).getValue())
    			i++;			
    		if((Boolean)kDTable1.getCell(6, 6).getValue())
    			i++;			
    		if(i == 0){
    			FDCMsgBox.showInfo("你并没有勾选");
    			SysUtil.abort();
    		}else if(i > 1){
    			FDCMsgBox.showInfo("你只能勾选一个");
    			SysUtil.abort();
    		}
			if((Boolean)kDTable1.getCell(6, 2).getValue()){
				editData.setCost("增加");
			}
			else if((Boolean)kDTable1.getCell(6, 4).getValue()) 		
				editData.setCost("减少");
			else if((Boolean)kDTable1.getCell(6, 6).getValue()) 		
				editData.setCost("零");
		}
	}
    
    //回到原单
	protected void yd_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", editData.getId());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(DesignChangeAuditEditUI.class.getName(),uiContext,null,OprtState.VIEW);
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