/**
 * output package name
 */
package com.kingdee.eas.fdc.gcftbiaoa.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.gcftbiaoa.SwbEntryCollection;
import com.kingdee.eas.fdc.gcftbiaoa.SwbEntryInfo;
import com.kingdee.eas.fdc.gcftbiaoa.SwbFactory;
import com.kingdee.eas.fdc.gcftbiaoa.SwbInfo;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class SwbEditUI extends AbstractSwbEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SwbEditUI.class);
    
    
	private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";
	private final static String CANTAUDITEDITSTATE = "cantAuditEditState";
	private final static String CANTUNAUDIT = "cantUnAudit";
	private final static String CANTAUDIT = "cantAudit";
    
	
	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning)
	throws Exception {
		// 检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		boolean b = editData != null && editData.getState() != null
		&& editData.getState().equals(state);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}

		if (getOprtState().equals(STATUS_EDIT)) {
			String warn = null;
			if (state.equals(FDCBillStateEnum.AUDITTED)) {
				warn = CANTUNAUDITEDITSTATE;
			} else {
				warn = CANTAUDITEDITSTATE;
			}
			MsgBox.showWarning(this, FDCClientUtils.getRes(warn));
			SysUtil.abort();
		}
	}
	public void onLoad() throws Exception {
		contcompany.setEnabled(false);
		State.setEnabled(false);
		txtVersion.setEditable(false);
		contProjectName.setVisible(true);
		chklasted.setEnabled(false);
	
		super.onLoad();
		chkMenuItemSubmitAndAddNew.setSelected(false); //连续新增设置不可编辑
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
		initui();
		kdtEntrys.getColumn("Sumproportion").getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn("Areaproportion").getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn("danwei").getStyleAttributes().setLocked(true);
		
		kdtEntrys.getRow(0).getStyleAttributes().setLocked(true);
		kdtEntrys.getRow(1).getStyleAttributes().setLocked(true);
		kdtEntrys.getRow(4).getStyleAttributes().setLocked(true);
		kdtEntrys.getRow(13).getStyleAttributes().setLocked(true);
		kdtEntrys.getRow(14).getStyleAttributes().setLocked(true);
		kdtEntrys.getRow(17).getStyleAttributes().setLocked(true);
	}
	
	private void initui() throws BOSException{
		KDTMergeManager mergeManager = kdtEntrys.getMergeManager();	//融合管理器类
    	this.kdtEntrys.getIndexColumn().getStyleAttributes().setHided(true);//隐藏行号
    	
    	mergeManager.mergeBlock(0, 8, 0, 9);
    	mergeManager.mergeBlock(1, 8, 1, 9);
    	mergeManager.mergeBlock(2, 8, 3, 9);
    	mergeManager.mergeBlock(4, 8, 4, 9);
    	mergeManager.mergeBlock(5, 4, 12, 4);
    	mergeManager.mergeBlock(5, 6, 12, 6);
    	mergeManager.mergeBlock(13, 8, 13, 9);
    	mergeManager.mergeBlock(14, 8, 14, 9);
    	mergeManager.mergeBlock(15, 8, 16, 9);
    	mergeManager.mergeBlock(17, 8, 17, 9);
    	mergeManager.mergeBlock(19, 2, 20, 2);
    	mergeManager.mergeBlock(19, 3, 20, 3);
    	mergeManager.mergeBlock(19, 5, 20, 5);
    	mergeManager.mergeBlock(19, 6, 20, 6);
    	mergeManager.mergeBlock(19, 7, 20, 7);
    	mergeManager.mergeBlock(18, 4, 24, 4);
    	mergeManager.mergeBlock(18, 6, 24, 6);
    	mergeManager.mergeBlock(25, 6, 25, 9);
	   
	   	this.kdtEntrys.getColumn("Sumproportion").getStyleAttributes().setNumberFormat("0.00%");
	   	this.kdtEntrys.getColumn("Areaproportion").getStyleAttributes().setNumberFormat("0.00%");
    	
	}
//  审核反审核
	public void actionAduit_actionPerformed(ActionEvent e) throws Exception {
		ControlState();
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED,CANTAUDIT);
		super.actionAduit_actionPerformed(e);
		FDCClientUtils.showOprtOK(this);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED,CANTUNAUDIT);
		super.actionUnAudit_actionPerformed(e);
		FDCClientUtils.showOprtOK(this);
		btnSubmit.setEnabled(true);
	}

	public void onShow() throws Exception {
		super.onShow();
		
		KDTSortManager sortMange = kdtEntrys.getSortMange();	//排序管理器
		sortMange.setSortAuto(false);
		kdtEntrys_detailPanel.getAddNewLineButton().setVisible(false);
		kdtEntrys_detailPanel.getRemoveLinesButton().setVisible(false);
		kdtEntrys_detailPanel.getInsertLineButton().setVisible(false);
	}
	
	public void ControlState(){
		if(editData.getState().equals(FDCBillStateEnum.SAVED)){
			MsgBox.showWarning("请先提交，再审核");
			abort();
		}
	}
    /**
     * output class constructor
     */
    public SwbEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
      if(editData.getEntrys().size() > 0){
//		//第1行
//    	kdtEntrys.getRow(0).getCell("title").setValue("1.硬景工程");
    	kdtEntrys.getRow(0).getCell("ComePrice").setValue("——(注：横杆为不需要录入项)");
//    	//第2行
//    	kdtEntrys.getRow(1).getCell("title").setValue("1.1按区域划分");
    	kdtEntrys.getRow(1).getCell("ComePrice").setValue("——");
//    	//第3行
//    	kdtEntrys.getRow(2).getCell("title").setValue("1.1.1样板区");
    	kdtEntrys.getRow(2).getCell("ComePrice").setValue("——");
//    	//第4行
//    	kdtEntrys.getRow(3).getCell("title").setValue("1.1.2交付区");
    	kdtEntrys.getRow(3).getCell("ComePrice").setValue("——");
//    	//第5行
//    	kdtEntrys.getRow(4).getCell("title").setValue("1.2按分部工程划分");
    	kdtEntrys.getRow(4).getCell("ComePrice").setValue("——");
//    	//第6行
//    	kdtEntrys.getRow(5).getCell("title").setValue("1.2.1道路工程");
    	kdtEntrys.getRow(5).getCell("danwei").setValue("元/平米");
//    	//第7行
//    	kdtEntrys.getRow(6).getCell("title").setValue("1.2.2雨污水管网工程");
    	kdtEntrys.getRow(6).getCell("danwei").setValue("元/平米");
//    	//第8行
//    	kdtEntrys.getRow(7).getCell("title").setValue("1.2.3围墙工程（含大门）");
    	kdtEntrys.getRow(7).getCell("danwei").setValue("元/平米");
//    	//第9行
//    	kdtEntrys.getRow(8).getCell("title").setValue("1.2.4小品工程（含水景）");
    	kdtEntrys.getRow(8).getCell("danwei").setValue("元/平米");
//    	//第10行
//    	kdtEntrys.getRow(9).getCell("title").setValue("1.2.5人行道、宅间道及园林道");
    	kdtEntrys.getRow(9).getCell("danwei").setValue("元/平米");
//    	//第11行
//    	kdtEntrys.getRow(10).getCell("title").setValue("1.2.6安装工程");
    	kdtEntrys.getRow(10).getCell("danwei").setValue("元/平米");
//    	//第12行
//    	kdtEntrys.getRow(11).getCell("title").setValue("1.2.7结构挡墙工程");
    	kdtEntrys.getRow(11).getCell("danwei").setValue("元/平米");
//    	//第13行
//    	kdtEntrys.getRow(12).getCell("title").setValue("1.2.8其他");
    	kdtEntrys.getRow(12).getCell("danwei").setValue("元/平米");
//    	//第14行
//    	kdtEntrys.getRow(13).getCell("title").setValue("2.绿化工程");
    	kdtEntrys.getRow(13).getCell("ComePrice").setValue("——");
//    	//第15行
//    	kdtEntrys.getRow(14).getCell("title").setValue("2.1按区域划分");
    	kdtEntrys.getRow(14).getCell("ComePrice").setValue("——");
//    	//第16行
//    	kdtEntrys.getRow(15).getCell("title").setValue("2.1.1样板区");
    	kdtEntrys.getRow(15).getCell("ComePrice").setValue("——");
//    	//第17行
//    	kdtEntrys.getRow(16).getCell("title").setValue("2.1.2交付区");
    	kdtEntrys.getRow(16).getCell("ComePrice").setValue("——");
//    	//第18行
//    	kdtEntrys.getRow(17).getCell("title").setValue("2.2按分部工程划分");
    	kdtEntrys.getRow(17).getCell("ComePrice").setValue("——");
//    	//第19行
//    	kdtEntrys.getRow(18).getCell("title").setValue("2.2.1上层苗木");
    	kdtEntrys.getRow(18).getCell("danwei").setValue("元/棵");
//    	//第20行
//    	kdtEntrys.getRow(19).getCell("title").setValue("2.2.2中层苗木");
    	kdtEntrys.getRow(19).getCell("danwei").setValue("元/棵");
//    	//第21行
//    	kdtEntrys.getRow(20).getCell("title").setValue("0000");
    	kdtEntrys.getRow(20).getCell("danwei").setValue("元/平米");
//    	//第22行
//    	kdtEntrys.getRow(21).getCell("title").setValue("2.2.3地被苗木");
    	kdtEntrys.getRow(21).getCell("danwei").setValue("元/棵");
//    	//第23行
//    	kdtEntrys.getRow(22).getCell("title").setValue("2.2.4水生苗木");
    	kdtEntrys.getRow(22).getCell("danwei").setValue("元/平米");
//    	//第24行
//    	kdtEntrys.getRow(23).getCell("title").setValue("2.2.5种植土");
    	kdtEntrys.getRow(23).getCell("danwei").setValue("元/立方");
//    	//第25行
//    	kdtEntrys.getRow(24).getCell("title").setValue("2.2.6其他");
    	kdtEntrys.getRow(24).getCell("danwei").setValue("元/平米");
//    	//第26行
//    	kdtEntrys.getRow(25).getCell("title").setValue("3.小区内其他设施");
    	kdtEntrys.getRow(25).getCell("danwei").setValue("——");
    	}
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected void verifyInput(ActionEvent actionevent) throws Exception {
		super.verifyInput(actionevent);
		FilterInfo filInfo = new FilterInfo();
		filInfo.getFilterItems().add(new FilterItemInfo("ProjectName.id",editData.getProjectName().getId()));
		if(editData.getId()!=null){
			filInfo.getFilterItems().add(new FilterItemInfo("id",editData.getId(),CompareType.NOTEQUALS));
			if(SwbFactory.getRemoteInstance().exists(filInfo)){
				MsgBox.showWarning("已有单据不能新增");
				SysUtil.abort();
			}
		}
	}
    /**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
    }

    /**
     * output menuItemEnterToNextRow_itemStateChanged method
     */
    protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.menuItemEnterToNextRow_itemStateChanged(e);
    }

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    	int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
        
		String columnKey = this.kdtEntrys.getColumnKey(colIndex);
		if(columnKey.equals("GreenArea")){
			if("1.2.1".equals(kdtEntrys.getCell(rowIndex, "key").getValue())){
				Object value = e.getValue();
				this.kdtEntrys.getCell(rowIndex+1, colIndex).setValue(value);
				this.kdtEntrys.getCell(rowIndex+2, colIndex).setValue(value);
				this.kdtEntrys.getCell(rowIndex+3, colIndex).setValue(value);
				this.kdtEntrys.getCell(rowIndex+4, colIndex).setValue(value);
				this.kdtEntrys.getCell(rowIndex+5, colIndex).setValue(value);
				this.kdtEntrys.getCell(rowIndex+6, colIndex).setValue(value);
				this.kdtEntrys.getCell(rowIndex+7, colIndex).setValue(value);
			}
			if("2.2.1".equals(kdtEntrys.getCell(rowIndex, "key").getValue())){
				Object value = e.getValue();
				this.kdtEntrys.getCell(rowIndex+1, colIndex).setValue(value);
				this.kdtEntrys.getCell(rowIndex+2, colIndex).setValue(value);
				this.kdtEntrys.getCell(rowIndex+3, colIndex).setValue(value);
				this.kdtEntrys.getCell(rowIndex+4, colIndex).setValue(value);
				this.kdtEntrys.getCell(rowIndex+5, colIndex).setValue(value);
				this.kdtEntrys.getCell(rowIndex+6, colIndex).setValue(value);
			}
		}
		
        IRow row = this.kdtEntrys.getRow(rowIndex);
        BigDecimal Price = BigDecimal.ZERO;
    	BigDecimal GreenArea = BigDecimal.ZERO;
        row.getCell("GreenAreaIndex").setValue(FDCHelper.divide(row.getCell("Price").getValue(), row.getCell("GreenArea").getValue(), 4, 4));
        if("1.1.2".equals(kdtEntrys.getCell(rowIndex, "key").getValue())){
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	GreenArea = FDCHelper.add(GreenArea, row.getCell("GreenArea").getValue());
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	GreenArea = FDCHelper.add(GreenArea, row.getCell("GreenArea").getValue());
        	row.getCell("Areaproportion").setValue(FDCHelper.divide(row.getCell("GreenArea").getValue(), GreenArea, 4, 4));
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);//1.1的汇总
        	row.getCell("Price").setValue(Price);
        	row.getCell("GreenArea").setValue(GreenArea);
        	row.getCell("GreenAreaIndex").setValue(FDCHelper.divide(Price,GreenArea , 4, 4));
        	row.getCell("Areaproportion").setValue(1);
        	row.getCell("Sumproportion").setValue(1);
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);//1.硬景
        	row.getCell("Price").setValue(Price);
        	row.getCell("GreenArea").setValue(GreenArea);
        	row.getCell("GreenAreaIndex").setValue(FDCHelper.divide(Price,GreenArea , 4, 4));
        	row.getCell("Areaproportion").setValue(1);
        	row.getCell("Sumproportion").setValue(1);
        	row = this.kdtEntrys.getRow(row.getRowIndex()+3);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row.getCell("Areaproportion").setValue(FDCHelper.divide(row.getCell("GreenArea").getValue(), GreenArea, 4, 4));
        	txtlandscape.setValue(GreenArea);
        	if(txtGreenH.getBigDecimalValue() != null && txtother.getBigDecimalValue() != null)
        		txtSumArea.setValue(GreenArea.add(txtother.getBigDecimalValue()).add(txtGreenH.getBigDecimalValue()));
        	else if(txtGreenH.getBigDecimalValue() != null)
        		txtSumArea.setValue(GreenArea.add(txtGreenH.getBigDecimalValue()));
        	else if(txtother.getBigDecimalValue() != null)
        		txtSumArea.setValue(GreenArea.add(txtother.getBigDecimalValue()));
        }
        if("1.2.8".equals(kdtEntrys.getCell(rowIndex, "key").getValue())){
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	GreenArea = FDCHelper.add(GreenArea, row.getCell("GreenArea").getValue());
        	//1.2.7
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	//1.2.6
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	//1.2.5
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	//1.2.4
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	//1.2.3
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	//1.2.2
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	//1.2.1
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row.getCell("Areaproportion").setValue(1);
        	//1.2
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	row.getCell("Price").setValue(Price);
        	row.getCell("GreenArea").setValue(GreenArea);
        	row.getCell("GreenAreaIndex").setValue(FDCHelper.divide(Price,GreenArea , 4, 4));
        	row.getCell("Areaproportion").setValue(1);
        	row.getCell("Sumproportion").setValue(1);
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        }
        if("2.1.2".equals(kdtEntrys.getCell(rowIndex, "key").getValue())){
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	GreenArea = FDCHelper.add(GreenArea, row.getCell("GreenArea").getValue());
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	GreenArea = FDCHelper.add(GreenArea, row.getCell("GreenArea").getValue());
        	row.getCell("Areaproportion").setValue(FDCHelper.divide(row.getCell("GreenArea").getValue(), GreenArea, 4, 4));
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	row.getCell("Price").setValue(Price);
        	row.getCell("GreenArea").setValue(GreenArea);
        	row.getCell("GreenAreaIndex").setValue(FDCHelper.divide(Price,GreenArea , 4, 4));
        	row.getCell("Areaproportion").setValue(1);
        	row.getCell("Sumproportion").setValue(1);
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	row.getCell("Price").setValue(Price);
        	row.getCell("GreenArea").setValue(GreenArea);
        	row.getCell("GreenAreaIndex").setValue(FDCHelper.divide(Price,GreenArea , 4, 4));
        	row.getCell("Areaproportion").setValue(1);
        	row.getCell("Sumproportion").setValue(1);
        	row = this.kdtEntrys.getRow(row.getRowIndex()+3);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row.getCell("Areaproportion").setValue(FDCHelper.divide(row.getCell("GreenArea").getValue(), GreenArea, 4, 4));
        	txtGreenH.setValue(GreenArea);
        	if(txtlandscape.getBigDecimalValue() != null && txtother.getBigDecimalValue() != null)
        		txtSumArea.setValue(GreenArea.add(txtother.getBigDecimalValue()).add(txtlandscape.getBigDecimalValue()));
        	else if(txtlandscape.getBigDecimalValue() != null)
        		txtSumArea.setValue(GreenArea.add(txtlandscape.getBigDecimalValue()));
        	else if(txtother.getBigDecimalValue() != null)
        		txtSumArea.setValue(GreenArea.add(txtother.getBigDecimalValue()));
        }
        if("2.2.6".equals(kdtEntrys.getCell(rowIndex, "key").getValue())){
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	GreenArea = FDCHelper.add(GreenArea, row.getCell("GreenArea").getValue());
        	//2.2.5
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	//2.2.4
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	//2.2.3
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	//2.2.2
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	//2.2.1
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	Price = FDCHelper.add(Price, row.getCell("Price").getValue());
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row.getCell("Areaproportion").setValue(1);
        	//2.2
        	row = this.kdtEntrys.getRow(row.getRowIndex()-1);
        	row.getCell("Price").setValue(Price);
        	row.getCell("GreenArea").setValue(GreenArea);
        	row.getCell("GreenAreaIndex").setValue(FDCHelper.divide(Price,GreenArea , 4, 4));
        	row.getCell("Areaproportion").setValue(1);
        	row.getCell("Sumproportion").setValue(1);
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        	row = this.kdtEntrys.getRow(row.getRowIndex()+1);
        	row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
        }
        if("3".equals(kdtEntrys.getCell(rowIndex, "key").getValue())){
        	txtother.setValue(row.getCell("GreenArea").getValue());
        	if(txtlandscape.getBigDecimalValue() != null && txtGreenH.getBigDecimalValue() != null)
        		txtSumArea.setValue(GreenArea.add(txtGreenH.getBigDecimalValue()).add(txtlandscape.getBigDecimalValue()));
        	else if(txtlandscape.getBigDecimalValue() != null)
        		txtSumArea.setValue(GreenArea.add(txtlandscape.getBigDecimalValue()));
        	else if(txtGreenH.getBigDecimalValue() != null)
        		txtSumArea.setValue(GreenArea.add(txtGreenH.getBigDecimalValue()));
        }
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	BigDecimal Price1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(1, "Price").getValue());
    	BigDecimal Price2 = UIRuleUtil.getBigDecimal( kdtEntrys.getCell(4, "Price").getValue());
    	BigDecimal GreenArea1 = UIRuleUtil.getBigDecimal( kdtEntrys.getCell(1, "GreenArea").getValue());
    	BigDecimal GreenArea2 = UIRuleUtil.getBigDecimal( kdtEntrys.getCell(4, "GreenArea").getValue());
    	if(Price1.compareTo(Price2)!=0){
    		MsgBox.showWarning("1.1的造价与1.2的造价不相等，请修改提交。");
    		SysUtil.abort();
    	}
    	if(GreenArea1.compareTo(GreenArea2)!=0){
    		MsgBox.showWarning("1.1的硬景与1.2的硬景不相等，请修改提交。");
    		SysUtil.abort();
    	}
        BigDecimal Price3 = UIRuleUtil.getBigDecimal( kdtEntrys.getCell(14, "Price").getValue());
        BigDecimal Price4 = UIRuleUtil.getBigDecimal( kdtEntrys.getCell(17, "Price").getValue());
        BigDecimal GreenArea3 = UIRuleUtil.getBigDecimal( kdtEntrys.getCell(14, "GreenArea").getValue());
        BigDecimal GreenArea4 = UIRuleUtil.getBigDecimal( kdtEntrys.getCell(17, "GreenArea").getValue());
        if(Price3.compareTo(Price4)!=0){
        	MsgBox.showWarning("1.1的造价与1.2的造价不相等，请修改提交。");
        	SysUtil.abort();
        }
        if(GreenArea3.compareTo(GreenArea4)!=0){
        	MsgBox.showWarning("1.1的硬景与1.2的硬景不相等，请修改提交。");
        	SysUtil.abort();
        }
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionCopyLine_actionPerformed
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionSendMail_actionPerformed
     */
    public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMail_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionNumberSign_actionPerformed
     */
    public void actionNumberSign_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNumberSign_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.gcftbiaoa.SwbFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
        return null;
    }

    
    public void addEntryData(SwbEntryCollection coll,String str,String key){
    	SwbEntryInfo entryInfo = new SwbEntryInfo();
    	entryInfo.put("title", str);
    	entryInfo.put("key", key);
    	coll.add(entryInfo);
    }
    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
    	if(getUIContext().get("SwbInfo")!=null){
    		SwbInfo info = (SwbInfo)getUIContext().get("SwbInfo");
    		info.setId(null);

    		info.setState(FDCBillStateEnum.SAVED);
    		info.setVersion(info.getVersion()+1);
    		info.setLasted(false);
    		for (int i = 0; i < info.getEntrys().size(); i++) 
    			info.getEntrys().get(i).setId(null);
    		return info;
    	}else{
    		com.kingdee.eas.fdc.gcftbiaoa.SwbInfo objectValue = new com.kingdee.eas.fdc.gcftbiaoa.SwbInfo();
    		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
    		objectValue.setCompany(SysContext.getSysContext().getCurrentFIUnit().getName());//新增单据时带出当前公司

    		CurProjectInfo projInfo = (CurProjectInfo)getUIContext().get("project"); 
    		if(projInfo==null){
    			MsgBox.showWarning("请选择节点");
    			abort();
    		}else{
    			objectValue.setProjectName(projInfo);
    		}
    		SwbEntryCollection entrColl = objectValue.getEntrys();
    		addEntryData(entrColl, "1.硬景工程", "1");
    		addEntryData(entrColl, "1.1按区域划分", "1.1");
    		addEntryData(entrColl, "1.1.1样板区", "1.1.1");
    		addEntryData(entrColl, "1.1.2交付区", "1.1.2");
    		addEntryData(entrColl, "1.2按分部工程划分", "1.2");
    		addEntryData(entrColl, "1.2.1道路工程", "1.2.1");
    		addEntryData(entrColl, "1.2.2雨污水管网工程", "1.2.2");
    		addEntryData(entrColl, "1.2.3围墙工程（含大门）", "1.2.3");
    		addEntryData(entrColl, "1.2.4小品工程（含水景）", "1.2.4");
    		addEntryData(entrColl, "1.2.5人行道、宅间道及园林道", "1.2.5");
    		addEntryData(entrColl, "1.2.6安装工程", "1.2.6");
    		addEntryData(entrColl, "1.2.7结构挡墙工程", "1.2.7");
    		addEntryData(entrColl, "1.2.8其他", "1.2.8");
    		addEntryData(entrColl, "2.绿化工程", "2");
    		addEntryData(entrColl, "2.1按区域划分", "2.1");
    		addEntryData(entrColl, "2.1.1样板区", "2.1.1");
    		addEntryData(entrColl, "2.1.2交付区", "2.1.2");
    		addEntryData(entrColl, "2.2按分部工程划分", "2.2");
    		addEntryData(entrColl, "2.2.1上层苗木", "2.2.1");
    		addEntryData(entrColl, "2.2.2中层苗木", "2.2.2");
    		addEntryData(entrColl, "2.2.3地被苗木", "2.2.3");
    		addEntryData(entrColl, "2.2.3地被苗木", "2.2.3");
    		addEntryData(entrColl, "2.2.4水生苗木", "2.2.4");
    		addEntryData(entrColl, "2.2.5种植土", "2.2.5");
    		addEntryData(entrColl, "2.2.6其他", "2.2.6");
    		addEntryData(entrColl, "3.小区内其他设施", "3");
    		
    		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
    		objectValue.setVersion(1); 
    		return objectValue;
    	}
    }
}