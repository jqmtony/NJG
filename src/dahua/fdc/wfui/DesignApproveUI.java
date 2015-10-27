/**
 * output package name
 */
package com.kingdee.eas.fdc.wfui;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.ICoreBase;
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
//    	for (int i = 0; i < kDTable1.getRowCount(); i++) {
//        	KDTableHelper.autoFitRowHeight(this.kDTable1, i);
//		}
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
    	addRoweight.getCell(1).setValue("估算工程费增减总价：");
    	addRoweight.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(5).setValue("万元");
    	addRoweight.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(7, 1, 7, 2);
    	mergeManager.mergeBlock(7, 3, 7, 4);
    	mergeManager.mergeBlock(7, 5, 7, 6);
    	mergeManager.mergeBlock(7, 7, 7, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//第九行
    	IRow addRownine = this.kDTable1.addRow();
    	addRownine.getCell(0).setValue("合约部");
    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(1).setValue("其中返工造成的签证费用估算：");
    	addRownine.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	KDTableHelper.autoFitRowHeight(this.kDTable1, 8);
    	addRownine.getCell(6).setValue("万元");
    	addRownine.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(8, 1, 8, 2);
    	mergeManager.mergeBlock(8, 3, 8, 4);
    	mergeManager.mergeBlock(8, 5, 8, 6);
    	mergeManager.mergeBlock(8, 7, 8, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//第十行
    	IRow addRowten = this.kDTable1.addRow();
    	addRowten.getCell(0).setValue("合约部");
    	addRowten.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(1).setValue("本次变更占合同价的百分比（%）：");
    	addRowten.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(5).setValue("%");
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(9, 1, 9, 3);
    	mergeManager.mergeBlock(9, 5, 9, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//第十一行
    	IRow addRowelev = this.kDTable1.addRow();
    	addRowelev.getCell(0).setValue("合约部");
    	addRowelev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(1).setValue("截止目前变更累计合同总价的百分比（%）：");
    	addRowelev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(5).setValue("%");
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(10, 1, 10, 3);
    	mergeManager.mergeBlock(10, 5, 10, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);


    	//第十二行
    	IRow addRowtwev= this.kDTable1.addRow();
    	addRowtwev.getCell(0).setValue("合约部");
    	addRowtwev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwev.getCell(1).setValue("变更后合同金额是否超出目标成本限额：");
    	addRowtwev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwev.getCell(4).setValue("XX");
    	addRowtwev.getCell(5).setValue("是");
    	addRowtwev.getCell(6).setValue("XX");
    	addRowtwev.getCell(7).setValue("否");
    	
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(11, 1, 11, 3);
      	mergeManager.mergeBlock(6, 0, 12, 0);
    	
    	//第十三行
    	IRow addRowthirt= this.kDTable1.addRow();
    	addRowthirt.getCell(0).setValue("合约部");
    	addRowthirt.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(1).setValue("经办人");
    	addRowthirt.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(3).setValue("审核人");
    	addRowthirt.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(10).setValue("日期");
    	addRowthirt.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(12, 4, 12, 9);
     	mergeManager.mergeBlock(6, 0, 12, 0);

     	
    	//第十四行
    	IRow addRowfout= this.kDTable1.addRow();
    	addRowfout.getCell(0).setValue("公司第一负责人:");
    	addRowfout.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfout.getCell(1).setValue("（项目公司负责人意见）");
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(13, 1, 13, 10);
    	mergeManager.mergeBlock(13, 0, 14, 0);

    	//第十五行
    	IRow addRowfift= this.kDTable1.addRow();
    	addRowfift.getCell(0).setValue("公司第一负责人:");
    	addRowfift.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(1).setValue("第一负责人签字:");
    	addRowfift.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(10).setValue("日期");
    	addRowfift.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(14, 1, 14, 2);
    	mergeManager.mergeBlock(14, 3, 14, 9);
    	mergeManager.mergeBlock(13, 0, 14, 0);

    	//第十六行
    	IRow addRowsist= this.kDTable1.addRow();
    	addRowsist.getCell(0).setValue("城市公司或地区总部第一负责人");
    	addRowsist.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsist.getCell(1).setValue("（城市公司或地区总部负责人意见）");
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(15, 1, 15, 10);
    	mergeManager.mergeBlock(15, 0, 16, 0);

    	//第十七行
    	IRow addRowsevent= this.kDTable1.addRow();
    	addRowsevent.getCell(0).setValue("城市公司或地区总部第一负责人");
    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsevent.getCell(1).setValue("第一负责人签字");
    	addRowsevent.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsevent.getCell(10).setValue("日期");
    	addRowsevent.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(16, 1, 16, 2);
    	mergeManager.mergeBlock(16, 3, 16, 9);
    	mergeManager.mergeBlock(15, 0, 16, 0);

    	//第十八行
    	IRow addRoweighteen= this.kDTable1.addRow();
    	addRoweighteen.getCell(0).setValue("备注");
    	addRoweighteen.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//融合(1)-(3)是行 2-4是列
    	mergeManager.mergeBlock(17, 1, 17, 10);

    	this.kDTable1.getColumn(0).setWidth(105);
    	this.kDTable1.getColumn(1).setWidth(85);
    	this.kDTable1.getColumn(2).setWidth(60);
    	this.kDTable1.getColumn(3).setWidth(100);
    	this.kDTable1.getColumn(4).setWidth(50);
    	this.kDTable1.getColumn(5).setWidth(40);
    	this.kDTable1.getColumn(6).setWidth(75);
    	this.kDTable1.getColumn(7).setWidth(40);
    	this.kDTable1.getColumn(8).setWidth(50);
    	this.kDTable1.getColumn(9).setWidth(60);
    	this.kDTable1.getColumn(10).setWidth(70);
    	this.kDTable1.getColumn(11).setWidth(75);
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    	
    	String billId = "FrYcumfRTUq/iL87J1M2VXARYRc=";
//    	StringBuffer sb = new StringBuffer();
//    	sb.append("  select ChangeAB.FCurProjectName ,ChangeAB.FNumber ,ChangeAB.Fname ,BaseU.Fname_l2 ,");
//    	sb.append(" ChangeAB.Freadesc ,ChangeAB.CFQuality ,ChangeAB.CFTimeLi ,ChangeAB.CFSale ,CFCost ");
//    	sb.append("  from T_CON_ChangeAuditBill ChangeAB ");
//    	sb.append(" left join T_ORG_BaseUnit BaseU on BaseU.fid=ChangeAB.FConductDeptID");
//    	sb.append(" where ChangeAB.fid = '").append(billId).append("'");
//    	
//    	IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
//    	while(rowset.next()){
//    		this.kDTable1.getCell(0, 1).setValue(rowset.getString(3));
//    	}
    	
    	
    	//工作流审批意见
    	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForMap(billId);
    	this.kDTable1.getCell(5, 2).setValue(apporveResultForMap.get("设计部经办人;审核人"));
    	this.kDTable1.getCell(5, 4).setValue(apporveResultForMap.get("设计部审核人;审核人"));
    	this.kDTable1.getCell(5, 11).setValue(apporveResultForMap.get("日期;审核日期"));
    	this.kDTable1.getCell(12, 2).setValue(apporveResultForMap.get("合约部经办人；审核人"));
    	this.kDTable1.getCell(12, 4).setValue(apporveResultForMap.get("合约部审核人；审核人"));
    	this.kDTable1.getCell(12, 11).setValue(apporveResultForMap.get("合约部日期；审核日期"));
    	this.kDTable1.getCell(13, 1).setValue(apporveResultForMap.get("公司第一负责人，意见"));
    	this.kDTable1.getCell(14, 3).setValue(apporveResultForMap.get("公司第一负责人，签字"));
    	this.kDTable1.getCell(14, 11).setValue(apporveResultForMap.get("公司第一负责人，日期"));
    	this.kDTable1.getCell(15, 1).setValue(apporveResultForMap.get("城市公司或地区总部第一负责人，意见"));
    	this.kDTable1.getCell(16, 3).setValue(apporveResultForMap.get("城市公司或地区总部第一负责人，签字"));
    	this.kDTable1.getCell(16, 11).setValue(apporveResultForMap.get("城市公司或地区总部第一负责人，日期"));
    	
    	
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