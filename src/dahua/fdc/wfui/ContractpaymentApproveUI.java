/**
 * output package name
 */
package com.kingdee.eas.fdc.wfui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustFactory;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 合同付款申请单
 * output class name
 */
public class ContractpaymentApproveUI extends AbstractContractpaymentApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractpaymentApproveUI.class);
    
    /**
     * output class constructor
     */
    public ContractpaymentApproveUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	kDLabel1.setText("");
    	kDLabel1 = new KDLabel("Hello",JLabel.CENTER);
    	kDLabel1.setFont(resHelper.getFont("kDLabel1.font"));
    	
    	kDLabel1.setBounds(new Rectangle(177, 3, 588, 31));
        this.add(kDLabel1, new KDLayout.Constraints(177, 3, 588, 31, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        initUI();
    }

    
    private void initUI() throws BOSException, SQLException, EASBizException{
    	this.kDTable1.addColumns(10);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();
    	
    	//第一行
    	IRow addRow = this.kDTable1.addRow();
    	addRow.getCell(0).setValue("申请部门");
    	addRow.getCell(0).getStyleAttributes().setLocked(true);//锁定
//    	addRow.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow.getCell(5).setValue("申请金额");
//    	addRow.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(0, 1, 0, 4);
    	mergeManager.mergeBlock(0, 6, 0, 9);
    	
    	//第二行
    	IRow addRowtwo = this.kDTable1.addRow();
    	addRowtwo.getCell(0).setValue("收款单位");
//    	addRowtwo.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(1, 1, 1, 9);
    	
    	//第三行
    	IRow addRowthree = this.kDTable1.addRow();
    	addRowthree.getCell(0).setValue("项目名称");
//    	addRowthree.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(2, 1, 2, 9);
    	
    	
    	//第四行
    	IRow addRowfour = this.kDTable1.addRow();
    	addRowfour.getCell(0).setValue("合同名称");
//    	addRowfour.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(3, 1, 3, 9);

    	
    	//第五行
    	IRow addRowfive = this.kDTable1.addRow();
    	addRowfive.getCell(0).setValue("合同编号");
//    	addRowfive.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(4, 1, 4, 9);
    	
    	//款项内容
    	IRow addRowKx = this.kDTable1.addRow();
    	addRowKx.getCell(0).setValue("款项内容");
//    	addRowKx.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(5, 1, 5, 9);

    	
    	//第六行
    	IRow addRowsix = this.kDTable1.addRow();
    	addRowsix.getCell(0).setValue("合同总价(大写)");
//    	addRowsix.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(6).setValue("￥");
    	addRowsix.getCell(6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(6, 1, 6, 5);
    	mergeManager.mergeBlock(6, 7, 6, 9);
    	
    	//第七行
    	IRow addRowseven = this.kDTable1.addRow();
    	addRowseven.getCell(0).setValue("合同外金额(大写)");
//    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(6).setValue("￥");
    	addRowseven.getCell(6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(7, 1, 7, 5);
    	mergeManager.mergeBlock(7, 7, 7, 9);
    	
    	//第八行
    	IRow addRoweight = this.kDTable1.addRow();
    	addRoweight.getCell(0).setValue("合同结算总价(大写)");
//    	addRoweight.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(6).setValue("￥");
    	addRoweight.getCell(6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(8, 1, 8, 5);
    	mergeManager.mergeBlock(8, 7, 8, 9);
    	
    	//第九行
    	IRow addRownine = this.kDTable1.addRow();
    	addRownine.getCell(0).setValue("本次核定金额付款(大写)");
//    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(6).setValue("￥");
    	addRownine.getCell(6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(9, 1, 9, 5);
    	mergeManager.mergeBlock(9, 7, 9, 9);

    	//第十行
    	IRow addRowten = this.kDTable1.addRow();
    	addRowten.getCell(0).setValue("累计核定金额付款(大写)");
//    	addRowten.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(6).setValue("￥");
    	addRowten.getCell(6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(10, 1, 10, 5);
    	mergeManager.mergeBlock(10, 7, 10, 9);
    	
    	//第十一行
    	IRow addRowelev = this.kDTable1.addRow();
    	addRowelev.getCell(0).setValue("以前已累计支付(大写)");
//    	addRowelev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(6).setValue("￥");
    	addRowelev.getCell(6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(11, 1, 11, 5);
    	mergeManager.mergeBlock(11, 7, 11, 9);

    	//第十二行
    	IRow addRowtwev= this.kDTable1.addRow();
    	addRowtwev.getCell(0).setValue("本次申请支付(大写)");
//    	addRowtwev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwev.getCell(6).setValue("￥");
    	addRowtwev.getCell(6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(12, 1, 12, 5);
    	mergeManager.mergeBlock(12, 7, 12, 9);
    	
    	//第十三行
    	IRow addRowthirt= this.kDTable1.addRow();
    	addRowthirt.getCell(0).setValue("累计支付(大写)");
//    	addRowthirt.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(6).setValue("￥");
    	addRowthirt.getCell(6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(13, 1, 13, 5);
    	mergeManager.mergeBlock(13, 7, 13, 9);

    	
    	//第十四行
    	IRow addRowfout= this.kDTable1.addRow();
    	addRowfout.getCell(0).setValue("合同总价(或结算)余额(大写)");
//    	addRowfout.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfout.getCell(6).setValue("￥");
    	addRowfout.getCell(6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(14, 1, 14, 5);
    	mergeManager.mergeBlock(14, 7, 14, 9);
    	
    	//第十五行
    	IRow addRowfift= this.kDTable1.addRow();
    	addRowfift.getCell(0).setValue("业务经办人");
//    	addRowfift.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(3).setValue("经办部门负责人");
//    	addRowfift.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(15, 1, 15, 2);
    	mergeManager.mergeBlock(15, 3, 15, 4);
    	mergeManager.mergeBlock(15, 5, 15, 9);
    	
    	//第十六行
    	IRow addRowsist= this.kDTable1.addRow();
    	addRowsist.getCell(0).setValue("相关部门联审");
//    	addRowsist.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsist.getCell(3).setValue("联审部门负责人;");
//    	addRowsist.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(16, 1, 16, 2);
    	mergeManager.mergeBlock(16, 3, 16, 4);
    	mergeManager.mergeBlock(16, 5, 16, 9);

    	
    	//第十七行
    	IRow addRowsevent= this.kDTable1.addRow();
    	addRowsevent.getCell(0).setValue("财务部门负责人");
//    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsevent.getCell(3).setValue("单位主(分)管负责人");
//    	addRowsevent.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(17, 1, 17, 2);
    	mergeManager.mergeBlock(17, 3, 17, 4);
    	mergeManager.mergeBlock(17, 5, 17, 9);
    	
    	//第十八行
    	IRow addRoweighteen= this.kDTable1.addRow();
    	addRoweighteen.getCell(0).setValue("单位负责人");
//    	addRoweighteen.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweighteen.getCell(3).setValue("集团管理中心负责人");
//    	addRoweighteen.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(18, 1, 18, 2);
    	mergeManager.mergeBlock(18, 3, 18, 4);
    	mergeManager.mergeBlock(18, 5, 18, 9);
    	
    	//第十九行
    	IRow addRowninteen= this.kDTable1.addRow();
    	addRowninteen.getCell(0).setValue("分管副总裁");
//    	addRowninteen.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowninteen.getCell(3).setValue("执行副总裁");
//    	addRowninteen.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(19, 1, 19, 2);
    	mergeManager.mergeBlock(19, 3, 19, 4);
    	mergeManager.mergeBlock(19, 5, 19, 9);
    	
    	//第二十行
    	IRow addRowtwenty= this.kDTable1.addRow();
    	addRowtwenty.getCell(0).setValue("集团总裁");
//    	addRowtwenty.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);

    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(20, 1, 20, 9);
    	
    	//第二十一行
    	IRow addRowtwentyO= this.kDTable1.addRow();
    	addRowtwentyO.getCell(0).setValue("批准金额");
//    	addRowtwentyO.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(21, 1, 21, 9);
    	
    	//第二十二行
    	IRow addRowtwentyT= this.kDTable1.addRow();
    	addRowtwentyT.getCell(0).setValue("备注");
//    	addRowtwentyT.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(22, 1, 22, 9);
    	
//    	//第二十三行
//    	IRow addRowtwentyTh= this.kDTable1.addRow();
//    	addRowtwentyTh.getCell(0).setValue("");
//    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//    	addRowtwentyTh.getCell(3).setValue("申请部门批准后签收");
//    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//    	//融合(1)-(3)是行 2-4是列
//    	mergeManager.mergeBlock(22, 1, 22, 2);
//    	mergeManager.mergeBlock(22, 3, 22, 4);   	
//    	mergeManager.mergeBlock(22, 5, 22, 9);
//    	
//    	//第二十四行
//    	IRow addRowtwentyF= this.kDTable1.addRow();
//    	addRowtwentyF.getCell(0).setValue("");
//    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//    	addRowtwentyF.getCell(3).setValue("日期");
//    	addRowsevent.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//    	//融合(1)-(3)是行 2-4是列
//    	mergeManager.mergeBlock(23, 1, 23, 2);
//    	mergeManager.mergeBlock(23, 3, 23, 4);
//    	mergeManager.mergeBlock(23, 5, 23, 9);
    	
    	
    	
    	
  	
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
    	
    	String billId = editData.getId()!=null?editData.getId().toString():"++7pgRwQRf2paClUcxTBtsmlqGk=";
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select org.Fname_l2,bill.FBizDate,bill.FNumber,min.Fname_l2,try.FOriginalAmount,ier.Fname_l2, ject.Fname_l2,bill.FContractName");
    	sb.append(" ,bill.FContractNo,bill.CFkxnr,con.FOriginalAmount,try.FAmount,bill.FMoneyDesc,u.Fname_l2");
    	sb.append(" from T_CON_PayRequestBill bill  ");
    	sb.append(" left join T_FDC_CurProject ject on ject.fid = bill.FCurProjectID  ");
    	sb.append(" left join T_ORG_BaseUnit org on org.fid = ject.FFullOrgUnit ");
    	sb.append(" left join T_ORG_Admin min on min.fid = bill.FUseDepartmentID ");
    	sb.append(" left join T_CON_PayRequestBillEntry try on try.FParentID = bill.fid ");
    	sb.append(" left join T_BD_Supplier ier on ier.fid = bill.FSupplierID ");
    	sb.append(" left join T_CON_ContractBill con on con.fid = bill.FContractId ");
    	sb.append(" left join T_PM_User u on u.fid = bill.FCreatorID");
    	sb.append(" where bill.fid = '").append(billId).append("'");
    	
    	IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
    	while(rowset.next()){
    		kDLabel1.setText(rowset.getString(1));
    		//日期
    		if(rowset.getString(2) != null){
    			kDTextField2.setText(rowset.getString(2));
    		}
    		else{
    			kDTextField2.setText("业务日期为空");
    		}
    		//编号
    			kDTextField3.setText(rowset.getString(3));
    		
    		this.kDTable1.getCell(0, 1).setValue(rowset.getString(4));
    		this.kDTable1.getCell(0, 6).setValue(rowset.getString(5));
    		this.kDTable1.getCell(1, 1).setValue(rowset.getString(6));
    		this.kDTable1.getCell(2, 1).setValue(rowset.getString(7));
    		this.kDTable1.getCell(3, 1).setValue(rowset.getString(8));
    		this.kDTable1.getCell(4, 1).setValue(rowset.getString(9));
    		this.kDTable1.getCell(5, 1).setValue(rowset.getString(10));
    		this.kDTable1.getCell(22, 1).setValue(rowset.getString(13));
    		this.kDTable1.getCell(15, 1).setValue(rowset.getString(14));
    		//合同总价
    		this.kDTable1.getCell(6, 7).setValue(rowset.getString(11));
    		BigDecimal Htzj = UIRuleUtil.getBigDecimal(this.kDTable1.getCell(6, 7).getValue()) ;
    		//变更金额
    		BigDecimal Bgje = UIRuleUtil.getBigDecimal(this.kDTable1.getCell(6, 7).getValue());
    		this.kDTable1.getCell(6, 1).setValue(FDCClientHelper.getChineseFormat(Bgje,false));

    		//变更签证
    		BigDecimal Bgqz = getContractChange(billId);    	
    		this.kDTable1.getCell(7, 7).setValue(Bgqz);
    		this.kDTable1.getCell(7, 1).setValue(FDCClientHelper.getChineseFormat(Bgqz, false));
    		
    		//结算金额 	
    		BigDecimal jsje = getJsjine(billId);
    		this.kDTable1.getCell(8, 7).setValue(jsje);
    		this.kDTable1.getCell(8,1).setValue(FDCClientHelper.getChineseFormat(jsje, false));
    	 	
	 		//本次核定付款金额
	 		this.kDTable1.getCell(9, 7).setValue(rowset.getString(12)==null?0:rowset.getString(12));
	 		BigDecimal Bchdje = UIRuleUtil.getBigDecimal(this.kDTable1.getCell(9, 7).getValue());
	 		this.kDTable1.getCell(9, 1).setValue(FDCClientHelper.getChineseFormat(Bchdje,false));

	 		//累计核定金额
	 		BigDecimal lj = getljsf(billId);
	 		this.kDTable1.getCell(10, 7).setValue(Bchdje.add(lj));
	 		this.kDTable1.getCell(10, 1).setValue(FDCClientHelper.getChineseFormat(Bchdje.add(lj), false));
	 		
		//以前累计金额
 			this.kDTable1.getCell(11, 7).setValue(lj);
 			this.kDTable1.getCell(11, 1).setValue(FDCClientHelper.getChineseFormat(lj, false));				
 			
		//本次申请支付
 			this.kDTable1.getCell(12, 7).setValue(Bchdje);
 			this.kDTable1.getCell(12, 1).setValue(FDCClientHelper.getChineseFormat(Bchdje, false));	
 	 					
		//累计支付金额
 			this.kDTable1.getCell(13, 7).setValue(Bchdje.add(lj));
 			this.kDTable1.getCell(13, 1).setValue(FDCClientHelper.getChineseFormat(Bchdje.add(lj), false));
 			
		//合同总价
		this.kDTable1.getCell(14, 7).setValue(Htzj.subtract(Bchdje.add(lj)));
		this.kDTable1.getCell(14, 1).setValue(FDCClientHelper.getChineseFormat(Htzj.subtract(lj), false));			
 			
 			//工作流审批意见
	 		Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForPerson(billId);
//	    	this.kDTable1.getCell(15, 1).setValue(apporveResultForMap.get("业务经办人"));
	    	this.kDTable1.getCell(15, 5).setValue(apporveResultForMap.get("经办部门负责人"));
	    	this.kDTable1.getCell(16, 1).setValue(apporveResultForMap.get("相关部门联审"));
	    	this.kDTable1.getCell(16, 5).setValue(apporveResultForMap.get("联审部门负责人"));
	    	this.kDTable1.getCell(17, 1).setValue(apporveResultForMap.get("财务部门负责人"));
	    	this.kDTable1.getCell(17, 5).setValue(apporveResultForMap.get("单位主(分)管负责人"));
	    	this.kDTable1.getCell(18, 1).setValue(apporveResultForMap.get("单位负责人"));
	    	this.kDTable1.getCell(18, 5).setValue(apporveResultForMap.get("集团管理中心负责人"));
	    	this.kDTable1.getCell(19, 1).setValue(apporveResultForMap.get("分管副总裁"));
	    	this.kDTable1.getCell(19, 5).setValue(apporveResultForMap.get("执行副总裁"));
	    	this.kDTable1.getCell(20, 1).setValue(apporveResultForMap.get("集团总裁"));
	 		
    	}
	 		
    }
    
    //最终结算金额
    private BigDecimal getJsjine(String id) throws EASBizException, BOSException, SQLException{
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("contractId");
    	sic.add("hasSettled");
    	String contractId = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(id),sic).getContractId();
    	
    	ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)),sic);
    	
    	FDCSQLBuilder builder=new FDCSQLBuilder();
    	BigDecimal settleAmt=FDCHelper.ZERO;//已实现产值
		BigDecimal finalSettAmt=FDCHelper.ZERO;//最终结算金额
			
		if(contract.isHasSettled()){//如果是最终结算
			builder.clear();
			builder.appendSql("select  FSettlePrice \n ");
			builder.appendSql(" from t_con_contractSettlementbill where FContractBillId=?  and FIsSettled=1 and FIsFinalSettle=1 and fstate='4AUDITTED' \n ");
			builder.addParam(contractId);
			IRowSet rowSet1=builder.executeQuery();
			if(rowSet1.next()){
				settleAmt=FDCHelper.toBigDecimal(rowSet1.getBigDecimal("FSettlePrice"), 2);
				finalSettAmt=settleAmt;
			}
		}else{//非最终结算
			builder.clear();
			builder.appendSql("select sum(FCurSettlePrice) FSettlePrice  from t_con_contractSettlementbill \n ");
			builder.appendSql(" where FContractBillId=? and FIsSettled=0 and FIsFinalSettle=0 and fstate='4AUDITTED' \n ");
			builder.addParam(contractId);
			IRowSet rowSet2=builder.executeQuery();
			if(rowSet2.next()){
				settleAmt=FDCHelper.toBigDecimal(rowSet2.getBigDecimal("FSettlePrice"),2);
			}			
		}
		return settleAmt;
    }
    
    //传本单据的ID，变更金额
    private BigDecimal getContractChange(String id) throws BOSException, EASBizException{
    	//增加了参数是否包含变更金额 
		boolean isIncludeChangeAudit = FDCUtils.isIncludeChangeAudit(null);
		BigDecimal changeTotal = FDCHelper.ZERO;
		Map changeMap  = null;
		EntityViewInfo view = null;
		FilterInfo filter = null;
		
		view = new EntityViewInfo();
		view.getSelector().add("amount");
		view.getSelector().add("balanceAmount");
		view.getSelector().add("hasSettled");
		view.getSelector().add("changeType.id");
		filter = new FilterInfo();
		
		//过滤到未审批的变更单
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("contractId");
		String contractId = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(id),sic).getContractId();
		
		if(contractId!=null)
			filter.getFilterItems().add(new FilterItemInfo("contractBill.Id", contractId));
		else
			filter.getFilterItems().add(new FilterItemInfo("contractBill.Id", "111"));
		
		filter.getFilterItems().add(new FilterItemInfo("state","\'"+FDCBillStateEnum.ANNOUNCE_VALUE+"\','"+FDCBillStateEnum.VISA_VALUE+"\',\'"+FDCBillStateEnum.AUDITTED_VALUE+"\'",CompareType.INCLUDE));
	
		view.setFilter(filter);
		
		ContractChangeBillCollection changes = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(view);
		for (int i = 0; i < changes.size(); i++) {
			ContractChangeBillInfo change = changes.get(i);
			if(change.getChangeType()==null){
				continue;
			}
			BigDecimal changeAmount = FDCHelper.toBigDecimal(change.getAmount(),2);
			if(change.isHasSettled()){
				changeAmount=FDCHelper.toBigDecimal(change.getBalanceAmount(),2);
			}
			if(changeAmount==null){
				changeAmount=FDCHelper.ZERO;
			}
			changeTotal = FDCHelper.toBigDecimal(changeTotal.add(changeAmount),2);
		}
		//变更金额
		return changeTotal;
    }
    
   
    //上次累计实付
    private BigDecimal getljsf(String id) throws EASBizException, BOSException {
    	BigDecimal lstReqAmt = FDCHelper.ZERO; //上期累计申请
		
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 */
		BigDecimal lstPrjAllReqOrgAmt = FDCHelper.ZERO; //截至上次累计申请原币
		
		BigDecimal lstaddProjectAmt = FDCHelper.ZERO; //新增工程款		
		BigDecimal lstpayPartAMatAmt = FDCHelper.ZERO; //上期累计申请甲供材款
		FilterInfo filter;
		EntityViewInfo view;
		
		SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("contractId");
    	sic.add("createTime");
    	PayRequestBillInfo payInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(id),sic);
    	
		
		view = new EntityViewInfo();
		view.getSelector().add("projectPriceInContract");
		view.getSelector().add("projectPriceInContractOri");
		view.getSelector().add("addProjectAmt");
		view.getSelector().add("payPartAMatlAmt");
		view.getSelector().add("hasClosed");
		view.getSelector().add("entrys.projectPriceInContract");
		view.getSelector().add("entrys.addProjectAmt");
		view.getSelector().add("entrys.payPartAMatlAmt");
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("createTime",payInfo.getCreateTime(),CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("contractId",payInfo.getContractId()));
		
		view.setFilter(filter);
		PayRequestBillCollection cols = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
		//截至上期间累计申请 ，关闭则取分录累计，未关闭则取实际申请取申请请单金额 by hpw 2010.9.22
		for(Iterator it = cols.iterator();it.hasNext();){
			PayRequestBillInfo info = (PayRequestBillInfo) it.next();
			if(info.isHasClosed()){
				for(Iterator iter=info.getEntrys().iterator();iter.hasNext();){
					PayRequestBillEntryInfo entry = (PayRequestBillEntryInfo)iter.next();
					lstReqAmt = FDCHelper.add(lstReqAmt,entry.getProjectPriceInContract());
					lstaddProjectAmt = FDCHelper.add(lstaddProjectAmt,entry.getAddProjectAmt());
					lstpayPartAMatAmt = FDCHelper.add(lstpayPartAMatAmt,entry.getPayPartAMatlAmt());
					
					/* modified by zhaoqin for R140227-0281 on 2014/03/21 */
					lstPrjAllReqOrgAmt = FDCHelper.add(lstPrjAllReqOrgAmt,entry.getAddProjectAmt());
				}
			}else{
				lstReqAmt = FDCHelper.add(lstReqAmt,info.getProjectPriceInContract());
				lstaddProjectAmt = FDCHelper.add(lstaddProjectAmt,info.getAddProjectAmt());
				lstpayPartAMatAmt = FDCHelper.add(lstpayPartAMatAmt,info.getPayPartAMatlAmt());
				
				/* modified by zhaoqin for R140227-0281 on 2014/03/21 */
				lstPrjAllReqOrgAmt = FDCHelper.add(lstPrjAllReqOrgAmt,info.getProjectPriceInContractOri());
			}
		}
		
		return lstReqAmt;
    }

//    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
//    	super.kDTable1_tableClicked(e);
//    	FDCMsgBox.showInfo("行："+e.getRowIndex()+"\n列："+e.getColIndex());
//    }
//    
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
		return PayRequestBillFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		return new PayRequestBillInfo();
	}
	
}