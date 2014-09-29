/**
 * output package name
 */
package com.kingdee.eas.port.pm.invite.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStock;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockFactory;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo;
import com.kingdee.eas.port.pm.base.EvaluationIndicatorsFactory;
import com.kingdee.eas.port.pm.base.EvaluationTemplateEntryCollection;
import com.kingdee.eas.port.pm.base.EvaluationTemplateFactory;
import com.kingdee.eas.port.pm.base.EvaluationTemplateInfo;
import com.kingdee.eas.port.pm.invite.EvaluationFactory;
import com.kingdee.eas.port.pm.invite.IInviteReport;
import com.kingdee.eas.port.pm.invite.InviteReportE6Collection;
import com.kingdee.eas.port.pm.invite.InviteReportE6Info;
import com.kingdee.eas.port.pm.invite.InviteReportE7Collection;
import com.kingdee.eas.port.pm.invite.InviteReportE7Info;
import com.kingdee.eas.port.pm.invite.InviteReportFactory;
import com.kingdee.eas.port.pm.invite.InviteReportInfo;
import com.kingdee.eas.port.pm.invite.JudgesComfirmCollection;
import com.kingdee.eas.port.pm.invite.JudgesComfirmEntryCollection;
import com.kingdee.eas.port.pm.invite.JudgesComfirmEntryInfo;
import com.kingdee.eas.port.pm.invite.JudgesComfirmFactory;
import com.kingdee.eas.port.pm.invite.JudgesComfirmInfo;
import com.kingdee.eas.port.pm.invite.OpenRegistrationCollection;
import com.kingdee.eas.port.pm.invite.OpenRegistrationEntryCollection;
import com.kingdee.eas.port.pm.invite.OpenRegistrationEntryInfo;
import com.kingdee.eas.port.pm.invite.OpenRegistrationFactory;
import com.kingdee.eas.port.pm.invite.OpenRegistrationInfo;
import com.kingdee.eas.port.pm.invite.judgeSolution;
import com.kingdee.eas.port.pm.utils.FDCClientHelper;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class EvaluationEditUI extends AbstractEvaluationEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(EvaluationEditUI.class);
    
    /**
     * output class constructor
     */
    public EvaluationEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	initConpomentAttr();
    	super.onLoad();
    	this.kDTable3.setEnabled(false);
    	
    	this.kDToolBar1.setVisible(false);
    	
    	this.kDTable2.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                	kDTable2_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
    	
    	ProjectInfo info = (ProjectInfo) getUIContext().get("treeInfo");
    	if(info != null) {
    		EntityViewInfo evi = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		evi.setFilter(filter);
    		filter.getFilterItems().add(new FilterItemInfo("proName.longnumber", info.getLongNumber()+"%", CompareType.LIKE));
    		filter.getFilterItems().add(new FilterItemInfo("status", "4", CompareType.EQUALS));
    		prmtinviteReport.setEntityViewInfo(evi);
		}
    }
    private void initConpomentAttr() {
    	this.kdtEntryScore.setVisible(false);
    	this.kdtEntryScore_detailPanel.setVisible(false);
    	this.kdtEntryTotal.setVisible(false);
    	this.kdtEntryTotal_detailPanel.setVisible(false);
    	this.kdtEntryUnit.setVisible(false);
    	this.kdtEntryUnit_detailPanel.setVisible(false);
    	this.kdtEntryValid.setVisible(false);
    	this.kdtEntryValid_detailPanel.setVisible(false);
    	this.contCU.setVisible(false);
    	this.contBizStatus.setVisible(false);
    	this.contDescription.setVisible(false);
    	this.contBizDate.setVisible(false);
    	this.txtbasePrice.setEnabled(false);
    	this.evaSolution.setEnabled(false);
    	this.txtprjName.setEnabled(false);
    	this.pkevaDate.setRequired(true);
    	this.prmtinviteReport.setRequired(true);
    }
    
    @Override
    protected void verifyInput(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this, prmtinviteReport, "招标方案");
    	com.kingdee.eas.xr.helper.ClientVerifyXRHelper.verifyNull(this, pkevaDate, "评标日期");
    	
    	InviteReportInfo planInfo = (InviteReportInfo)prmtinviteReport.getValue();
		
		String oql = "select id where inviteReport.id='"+planInfo.getId()+"'";
		if(editData.getId()!=null)
			oql = oql+"and id <>'"+editData.getId()+"'";
		if(EvaluationFactory.getRemoteInstance().exists(oql))
		{
			MsgBox.showWarning("当前招标方案申报<"+planInfo.getReportName()+">已有对应的评标，不允许重复评标！");SysUtil.abort();
		}
    	super.verifyInput(e);
    }
    public void prmtinviteReport_Changed() throws Exception {
    	// TODO Auto-generated method stub
    	super.prmtinviteReport_Changed();
    	this.kDTable1.removeRows();
    	this.kDTable1.removeColumns();
    	this.kDTable2.removeRows();
    	this.kDTable2.removeColumns();
    	this.kDTable3.removeRows();
    	this.kDTable3.removeColumns();
    	
    	InviteReportInfo reportInfo = (InviteReportInfo) this.prmtinviteReport.getValue();
    	IInviteReport iinviteReport = InviteReportFactory.getRemoteInstance();
    	if(reportInfo != null) {
    		reportInfo = iinviteReport.getInviteReportInfo(new ObjectUuidPK(reportInfo.getId()));//招标方案完整信息
    		//过滤出该招标方案对应的专家信息
//    		EntityViewInfo evi = new EntityViewInfo();
//    		FilterInfo filter = new FilterInfo();
//    		filter.getFilterItems().add(new FilterItemInfo("planName.id", reportInfo.getId(), CompareType.EQUALS));
//    		evi.setFilter(filter);
    		String oql = "where planName.id='" + reportInfo.getId() + "'";
    		JudgesComfirmCollection judgeComColl = JudgesComfirmFactory.getRemoteInstance().getJudgesComfirmCollection(oql);
    		JudgesComfirmInfo judgeComInfo = judgeComColl.get(0);
    		JudgesComfirmEntryCollection judgeComEntryColl = judgeComInfo.getEntry();//专家确定分录
    		
    		//获取符合性审查模板
    		InviteReportE7Collection evaTmpEntryColl = reportInfo.getE7();
//			evaTempInfo = EvaluationTemplateFactory.getRemoteInstance().getEvaluationTemplateInfo(new ObjectUuidPK(evaTempInfo.getId()));
//			EvaluationTemplateEntryCollection evaTmpEntryColl = evaTempInfo.getEntry();
    		
    		//过滤该招标方案对应的开标登记信息
//    		evi = new EntityViewInfo();
//    		filter = new FilterInfo();
//    		filter.getFilterItems().add(new FilterItemInfo("reportName.id", reportInfo.getId(), CompareType.EQUALS));
//    		evi.setFilter(filter);
    		oql = "where reportName.id='" + reportInfo.getId() + "' and cancel<>'1' and status='4'";
    		OpenRegistrationCollection openRegColl = OpenRegistrationFactory.getRemoteInstance().getOpenRegistrationCollection(oql);
    		OpenRegistrationInfo openRegInfo = openRegColl.get(0);
    		if(openRegColl.size()<1)
    			return;
    		OpenRegistrationEntryCollection openRegEntrycoll = openRegInfo.getEntry();//开标登记分录(获取投标单位)
    		
    		//构建展示分录表头
    		this.kDTable1.addHeadRow(0);
    		IColumn col = this.kDTable1.addColumn();
    		col.setKey("Judges");
    		col.getStyleAttributes().setLocked(true);
    		this.kDTable1.getHeadRow(0).getCell("Judges").setValue("评委");
    		col = this.kDTable1.addColumn();
    		col.setKey("Indicator");
    		col.setWidth(150);
    		col.getStyleAttributes().setLocked(true);
    		this.kDTable1.getHeadRow(0).getCell("Indicator").setValue("评审指标");
    		//构建表头公司名称
    		IMarketSupplierStock isupplier = MarketSupplierStockFactory.getRemoteInstance();
    		//设置单元格为checkBox
    		KDCheckBox valid_CheckBox = new KDCheckBox();
    		valid_CheckBox.setName("valid_CheckBox");
            KDTDefaultCellEditor valid_CellEditor = new KDTDefaultCellEditor(valid_CheckBox);
            
    		for(int i = 0; i < openRegEntrycoll.size(); i++) {
    			OpenRegistrationEntryInfo entryInfo = openRegEntrycoll.get(i);
    			if(!entryInfo.isIsPresent()||!entryInfo.isIsQualified())
    				continue;
    			MarketSupplierStockInfo supplierInfo = entryInfo.getSupplierName();
    			supplierInfo = isupplier.getMarketSupplierStockInfo(new ObjectUuidPK(supplierInfo.getId()));
    			col = this.kDTable1.addColumn();
    			col.setKey("Unit"+i);
    			col.setWidth(220);
    			col.setEditor(valid_CellEditor);
    			this.kDTable1.getHeadRow(0).getCell("Unit"+i).setValue(supplierInfo.getSupplierName());
    			
    		}
    		col = this.kDTable1.addColumn();
    		col.setKey("remake");
    		col.setWidth(220);
    		col.getStyleAttributes().setLocked(false);
    		this.kDTable1.getHeadRow(0).getCell("remake").setValue("备注");    		
    		
    		//构建评委，评审指标信息
    		for(int i = 0; i < judgeComEntryColl.size(); i++) {
    			JudgesComfirmEntryInfo juEntryInfo = judgeComEntryColl.get(i);
    			juEntryInfo.getJuName();
    			for(int j = 0; j < evaTmpEntryColl.size(); j++) {
    				InviteReportE7Info evaEntryInfo = evaTmpEntryColl.get(j);
    				IRow rowAdd = this.kDTable1.addRow();
    				rowAdd.getCell("Judges").setValue(juEntryInfo.getJudgesName());
    				rowAdd.getCell("Indicator").setValue(evaEntryInfo.getEvaluationName());
    			}
    			IRow rowAdd = this.kDTable1.addRow();
				rowAdd.getCell("Judges").setValue(juEntryInfo.getJuName());
				rowAdd.getCell("Indicator").setValue("评审结果");
				rowAdd.getCell("Indicator").getStyleAttributes().setBackground(Color.RED);
				rowAdd.getStyleAttributes().setLocked(true);
				
				rowAdd.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
    		}
    		//设置符合性审查评委融合
    		for(int i = 0; i < kDTable1.getRowCount(); i += evaTmpEntryColl.size()+1) {
    			kDTable1.getMergeManager().mergeBlock(i, 0, evaTmpEntryColl.size()+i, 0);
    		}
    		//设置单元格checkBox默认值为false
    		for(int i = 0; i < this.kDTable1.getRowCount(); i++) {
    			for(int j = 2; j < this.kDTable1.getColumnCount(); j++) {
//    	            this.kDTable1.getColumn(j).setEditor(valid_CellEditor);
    				if(!this.kDTable1.getColumnKey(j).equals("remake"))
    					this.kDTable1.getRow(i).getCell(j).setValue(Boolean.TRUE);
    			}
    		}
    		
    		//设置打分分录
    		if(judgeSolution.integrate.equals(this.evaSolution.getSelectedItem())) {
    			//获取综合打分模板
    			InviteReportE6Collection InviteTempCollection = reportInfo.getE6();
    			//获取技术分总分
    			BigDecimal techScore = new BigDecimal(reportInfo.getTechScore());
    			//构建展示分录表头
        		this.kDTable2.addHeadRow(0);
        		IColumn column = this.kDTable2.addColumn();
        		column.setKey("Judges");
        		column.getStyleAttributes().setLocked(true);
        		this.kDTable2.getHeadRow(0).getCell("Judges").setValue("评委");
        		column = this.kDTable2.addColumn();
        		column.setKey("Indicator");
        		column.setWidth(150);
        		column.getStyleAttributes().setLocked(true);
        		this.kDTable2.getHeadRow(0).getCell("Indicator").setValue("评审指标");
        		column = this.kDTable2.addColumn();
        		column.setKey("fullScore");
        		column.getStyleAttributes().setLocked(true);
        		this.kDTable2.getHeadRow(0).getCell("fullScore").setValue("满分");
        		
        		//构建字段为小数
        		KDFormattedTextField TextField = new KDFormattedTextField();
    			TextField.setName("TextField");
    			TextField.setVisible(true);
    			TextField.setEditable(true);
    			TextField.setHorizontalAlignment(2);
    			TextField.setDataType(1);
    			TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
    			TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
    			TextField.setPrecision(4);
    			TextField.setValue(0);
    	        KDTDefaultCellEditor CellEditor = new KDTDefaultCellEditor(TextField);
        		//构建表头公司名称
//        		IMarketSupplierStock isupplier = MarketSupplierStockFactory.getRemoteInstance();
        		for(int i = 0; i < openRegEntrycoll.size(); i++) {
        			OpenRegistrationEntryInfo entryInfo = openRegEntrycoll.get(i);
        			if(!entryInfo.isIsPresent()||!entryInfo.isIsQualified())
        				continue;
        			MarketSupplierStockInfo supplierInfo = entryInfo.getSupplierName();
        			supplierInfo = isupplier.getMarketSupplierStockInfo(new ObjectUuidPK(supplierInfo.getId()));
        			column = this.kDTable2.addColumn();
        			column.setKey("Unit"+i);
        			column.setWidth(220);
        			column.setEditor(CellEditor);
        			this.kDTable2.getHeadRow(0).getCell("Unit"+i).setValue(supplierInfo.getSupplierName());
        		}
        		
//        		for(int j = 3; j < kDTable2.getColumnCount(); j++) {
//        	        this.kDTable2.getColumn(j).setEditor(CellEditor);
//            	}
        		//构建展示分录信息
        		for(int i = 0; i < judgeComEntryColl.size(); i++) {
        			JudgesComfirmEntryInfo juEntryInfo = judgeComEntryColl.get(i);
        			for(int j = 0; j < InviteTempCollection.size(); j++) {
        				InviteReportE6Info InviteEntryInfo = InviteTempCollection.get(j);
        				IRow rowAdd = this.kDTable2.addRow();
        				rowAdd.getCell("Judges").setValue(juEntryInfo.getJudgesName());
        				rowAdd.getCell("Indicator").setValue(InviteEntryInfo.getEvaluationNameTex());
        				rowAdd.getCell("fullScore").setValue(InviteEntryInfo.getWeight());
        			}
        			IRow rowAdd = this.kDTable2.addRow();
    				rowAdd.getCell("Judges").setValue(juEntryInfo.getJuName());
    				rowAdd.getCell("Indicator").setValue("总分");
    				rowAdd.getCell("Indicator").getStyleAttributes().setBackground(Color.RED);
    				rowAdd.getCell("fullScore").setValue("100");
    				rowAdd.getStyleAttributes().setLocked(true);
    				rowAdd.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
        		}
        		//设置评分分录评委融合
        		for(int i = 0; i < kDTable2.getRowCount(); i += InviteTempCollection.size()+1 ) {
        			kDTable2.getMergeManager().mergeBlock(i, 0, InviteTempCollection.size()+i, 0);
        		}
        		//设置默认值
        		for(int i = 0; i < kDTable2.getRowCount(); i++) {
        			for(int j = 3; j < kDTable2.getColumnCount(); j++) 
        				kDTable2.getRow(i).getCell(j).setValue(0);
        		}
    		}
    		
    		//构建总分
    		//构建展示分录表头
    		this.kDTable3.addHeadRow(0);
    		col = this.kDTable3.addColumn();
    		col.setKey("enterprise");
    		col.setWidth(150);
    		col.getStyleAttributes().setLocked(true);
    		this.kDTable3.getHeadRow(0).getCell("enterprise").setValue("投标单位");
    		//构建表头公司名称
    		for(int i = 0; i < openRegEntrycoll.size(); i++) {
    			OpenRegistrationEntryInfo entryInfo = openRegEntrycoll.get(i);
    			if(!entryInfo.isIsPresent()||!entryInfo.isIsQualified())
    				continue;
    			MarketSupplierStockInfo supplierInfo = entryInfo.getSupplierName();
    			supplierInfo = isupplier.getMarketSupplierStockInfo(new ObjectUuidPK(supplierInfo.getId()));
    			col = this.kDTable3.addColumn();
    			col.setKey("Unit"+i);
    			col.setWidth(220);
    			this.kDTable3.getHeadRow(0).getCell("Unit"+i).setValue(supplierInfo.getSupplierName());
    		}
    		IRow row = this.kDTable3.addRow();
    		row.getCell("enterprise").setValue("投标报价");
    		row = this.kDTable3.addRow();
    		row.getCell("enterprise").setValue("符合性结果");
    		row = this.kDTable3.addRow();
    		row.getCell("enterprise").setValue("技术标得分");
    		row = this.kDTable3.addRow();
    		row.getCell("enterprise").setValue("商务标得分");
    		row = this.kDTable3.addRow();
    		row.getCell("enterprise").setValue("总分");
    		row = this.kDTable3.addRow();
    		row.getCell("enterprise").setValue("排名");
    	}
    }
    
    protected void kDTable2_editStopped(KDTEditEvent e) throws Exception 
    {
    	if(e.getColIndex()==-1||e.getRowIndex()==-1)
    		return;
    	
    	String key = this.kDTable2.getColumnKey(e.getColIndex());
    	
    	if(!key.contains("Unit"))
    		return;
    	
    	IRow row = this.kDTable2.getRow(e.getRowIndex());
    	
    	BigDecimal lasScore = UIRuleUtil.getBigDecimal(row.getCell(e.getColIndex()).getValue());
    	BigDecimal fullScore = UIRuleUtil.getBigDecimal(row.getCell("fullScore").getValue());
    	
    	if(lasScore.compareTo(fullScore)==1)
    	{
    		MsgBox.showWarning("输入的分数不能大于对应评审指标的满分项！");
    		row.getCell(e.getColIndex()).setValue(BigDecimal.ZERO);
    		SysUtil.abort();
    	}
    	
    }
    
    @Override
    protected void kDTable1_editStopped(KDTEditEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.kDTable1_editStopped(e);
    	InviteReportInfo reportInfo = (InviteReportInfo) this.prmtinviteReport.getValue();
     	IInviteReport iinviteReport = InviteReportFactory.getRemoteInstance();
     	reportInfo = iinviteReport.getInviteReportInfo(new ObjectUuidPK(reportInfo.getId()));//招标方案完整信息
     	//获取符合性审查模板
 		int count = reportInfo.getE7().size() + 1;//指标数+评审结果行
 		//自动 得出符合性评审结果s
 		for(int c = 2; c < this.kDTable1.getColumnCount()-1; c++) {
 			for(int r = 0; r < this.kDTable1.getRowCount(); r += count) {
 				int invalid = 0;
 				for(int i = r; i < r+count-1; i++) {
 					if(kDTable1.getRow(i).getCell(c).getValue().equals(false))
 						invalid++;
 				}
 				if(invalid > 0)
 					kDTable1.getRow(r+count-1).getCell(c).setValue(false);
 				else
 					kDTable1.getRow(r+count-1).getCell(c).setValue(true);
 			}
 			int resultInvalid = 0;
 			for(int r = count - 1; r < kDTable1.getRowCount(); r += count) {
 				if(kDTable1.getRow(r).getCell(c).getValue().equals(false)) 
 					resultInvalid++;
 			}
 			//不合格的无需打分，单元格锁定
 			if(this.evaSolution.getSelectedItem().equals(judgeSolution.integrate)) {
 				if(resultInvalid >= 2)
 				{
 					kDTable2.getColumn(c+1).getStyleAttributes().setLocked(true);
 					kDTable2.getColumn(c+1).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
 					
 					for (int i = 0; i < kDTable2.getRowCount(); i++) 
 					{
 						kDTable2.getRow(i).getCell(c+1).setValue(BigDecimal.ZERO);
					}
 				}
 				else
 				{
 					kDTable2.getColumn(c+1).getStyleAttributes().setLocked(false);
 					kDTable2.getColumn(c+1).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
 				}
 			}
 		}
 		
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    	//展现保存的符合性审查信息
        if(this.kdtEntryValid.getRowCount() > 0) {
        	this.kDTable1.removeRows();
        	this.kDTable1.removeColumns();
        	this.kDTable1.addHeadRow();
        	//构建展示分录表头
        	IColumn col = this.kDTable1.addColumn();
    		col.setKey("Judges");
    		col.getStyleAttributes().setLocked(true);
    		this.kDTable1.getHeadRow(0).getCell("Judges").setValue("评委");
    		col = this.kDTable1.addColumn();
    		col.setKey("Indicator");
    		col.setWidth(150);
    		col.getStyleAttributes().setLocked(true);
    		this.kDTable1.getHeadRow(0).getCell("Indicator").setValue("评审指标");
    		//构建表头投标单位
    		for(int i = 0; i < this.kdtEntryUnit.getRowCount(); i++) {
    			IRow row = this.kdtEntryUnit.getRow(i);
    			col = this.kDTable1.addColumn();
    			col.setKey("Unit"+i);
    			col.setWidth(220);
    			this.kDTable1.getHeadRow(0).getCell("Unit"+i).setValue(row.getCell("enterprise").getValue());
    		}
    		col = this.kDTable1.addColumn();
    		col.setKey("remake");
    		col.setWidth(220);
    		col.getStyleAttributes().setLocked(false);
    		this.kDTable1.getHeadRow(0).getCell("remake").setValue("备注"); 
    		
    		//设置单元格为checkBox
    		KDCheckBox isValid_CheckBox = new KDCheckBox();
    		isValid_CheckBox.setSelected(false);
    		isValid_CheckBox.setName("isValid_CheckBox");
            KDTDefaultCellEditor isValid_CellEditor = new KDTDefaultCellEditor(isValid_CheckBox);
        	for(int j = 2; j < kDTable1.getColumnCount()-1; j++) {
                this.kDTable1.getColumn(j).setEditor(isValid_CellEditor);
        	}
        	
        	
        	
    		//恢复符合性分录勾选信息
        	for(int i = 0; i < this.kdtEntryValid.getRowCount(); i += this.kdtEntryUnit.getRowCount()) {
        		IRow rowAdd = this.kDTable1.addRow();
        		for(int j = i; j < this.kdtEntryUnit.getRowCount() + i; j++) {
        			IRow row = this.kdtEntryValid.getRow(j);
        			rowAdd.getCell("Judges").setValue(row.getCell("judges").getValue());
        			rowAdd.getCell("Indicator").setValue(row.getCell("indicators").getValue());
        			rowAdd.getCell("remake").setValue(row.getCell("remake").getValue());
        			rowAdd.getCell(j-i+2).setValue(row.getCell("valid").getValue().equals("true") ? true : false);
        			if(row.getCell("indicators").getValue().equals("评审结果")) {
//        				rowAdd.getCell("Indicator").getStyleAttributes().setBackground(Color.RED);
        				rowAdd.getStyleAttributes().setLocked(true);
        				rowAdd.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
        			}
        		}
        	}
        	//获取符合性审查模板
        	InviteReportInfo reportInfo = (InviteReportInfo) this.prmtinviteReport.getValue();
			try {
				reportInfo = InviteReportFactory.getRemoteInstance().getInviteReportInfo(new ObjectUuidPK(reportInfo.getId()));//招标方案完整信息
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			InviteReportE7Collection evaTmpEntryColl = reportInfo.getE7();
        	//设置符合性审查评委融合
    		for(int i = 0; i < kDTable1.getRowCount(); i += evaTmpEntryColl.size() + 1) {
    			kDTable1.getMergeManager().mergeBlock(i, 0, evaTmpEntryColl.size()+i, 0);
    		}
    		//构建评分分录信息
    		if(this.kdtEntryScore.getRowCount() > 0) {
    			this.kDTable2.removeRows();
            	this.kDTable2.removeColumns();
            	this.kDTable2.addHeadRow();
            	//构建展示分录表头
            	IColumn column = this.kDTable2.addColumn();
            	column.setKey("Judges");
            	column.getStyleAttributes().setLocked(true);
        		this.kDTable2.getHeadRow(0).getCell("Judges").setValue("评委");
        		column = this.kDTable2.addColumn();
        		column.setKey("Indicator");
        		column.setWidth(150);
        		column.getStyleAttributes().setLocked(true);
        		this.kDTable2.getHeadRow(0).getCell("Indicator").setValue("评审指标");
        		column = this.kDTable2.addColumn();
        		column.setKey("fullScore");
        		column.getStyleAttributes().setLocked(true);
        		this.kDTable2.getHeadRow(0).getCell("fullScore").setValue("满分");
        		//构建表头投标单位
        		for(int i = 0; i < this.kdtEntryUnit.getRowCount(); i++) {
        			IRow row = this.kdtEntryUnit.getRow(i);
        			col = this.kDTable2.addColumn();
        			col.setKey("Unit"+i);
        			col.setWidth(220);
        			this.kDTable2.getHeadRow(0).getCell("Unit"+i).setValue(row.getCell("enterprise").getValue());
        		}
        	
        		//恢复评委，审查指标，打分信息
        		int expRow = 0;
        		InviteReportE6Collection InviteTempCollection = reportInfo.getE6();
        		for(int i = 0; i < this.kdtEntryScore.getRowCount(); i += this.kdtEntryUnit.getRowCount()) {
            		IRow rowAdd = this.kDTable2.addRow();
            		for(int j = i; j < this.kdtEntryUnit.getRowCount() + i; j++) {
            			IRow row = this.kdtEntryScore.getRow(j);
            			rowAdd.getCell("Judges").setValue(row.getCell("judges").getValue());
            			rowAdd.getCell("Indicator").setValue(row.getCell("indicators").getValue());
            			rowAdd.getCell("fullScore").setValue(row.getCell("fullScore").getValue());
            			rowAdd.getCell(j-i+3).setValue(row.getCell("score").getValue());
            			if(row.getCell("indicators").getValue().equals("总分")) 
            			{
            				rowAdd.getStyleAttributes().setLocked(true);
            				rowAdd.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);            				
//            				CellSumExpr.setCellSumExpr(rowAdd.getCell(j-i+3), 0, 2);
            			}
            		}
            		expRow+=1;
            	}
        		for(int i = 0; i < kDTable2.getRowCount(); i += InviteTempCollection.size()+1) {
        			kDTable2.getMergeManager().mergeBlock(i, 0, InviteTempCollection.size()+i, 0);
        		}
    		}
    		  
    		//构建总分分录信息
    		this.kDTable3.removeRows();
    		this.kDTable3.removeColumns();
    		this.kDTable3.addHeadRow(0);
    		col = this.kDTable3.addColumn();
    		col.setKey("enterprise");
    		col.setWidth(150);
    		col.getStyleAttributes().setLocked(true);
    		this.kDTable3.getHeadRow(0).getCell("enterprise").setValue("投标单位");

    		//构建表头投标单位
    		for(int i = 0; i < this.kdtEntryUnit.getRowCount(); i++) {
    			IRow row = this.kdtEntryUnit.getRow(i);
    			col = this.kDTable3.addColumn();
    			col.setKey("Unit"+i);
    			col.setWidth(220);
    			this.kDTable3.getHeadRow(0).getCell("Unit"+i).setValue(row.getCell("enterprise").getValue());
    		}
    		
    		IRow row = this.kDTable3.addRow();
    		row.getCell("enterprise").setValue("投标报价");
    		row = this.kDTable3.addRow();
    		row.getCell("enterprise").setValue("符合性结果");
    		row = this.kDTable3.addRow();
    		row.getCell("enterprise").setValue("技术标得分");
    		row = this.kDTable3.addRow();
    		row.getCell("enterprise").setValue("商务标得分");
    		row = this.kDTable3.addRow();
    		row.getCell("enterprise").setValue("总分");
    		row = this.kDTable3.addRow();
    		row.getCell("enterprise").setValue("排名");
    		
    		if(this.kdtEntryTotal.getRowCount() > 0) {
    			//恢复总分分录
    			int line = 0;
    			for(int i = 0; i < this.kdtEntryTotal.getRowCount(); i += this.kdtEntryUnit.getRowCount()) {
            		IRow rowAdd = this.kDTable3.getRow(line++);
            		for(int j = i; j < this.kdtEntryUnit.getRowCount() + i; j++) {
            			IRow resultRow = this.kdtEntryTotal.getRow(j);
            			rowAdd.getCell("enterprise").setValue(resultRow.getCell("indicators").getValue());
            			rowAdd.getCell(j-i+1).setValue(resultRow.getCell("result").getValue());
            		}
            	}		
    		}
    		//根据总分分录符合性设置评分分录的对应单位的可编辑性
    		if(judgeSolution.integrate.equals(this.evaSolution.getSelectedItem())) {
	    		IRow rowValid = this.kDTable3.getRow(1);
	    		for(int c = 1; c < kDTable3.getColumnCount(); c++) {
	    			if("不合格".equals(rowValid.getCell(c).getValue()))
	 				{
	 					kDTable2.getColumn(c+2).getStyleAttributes().setLocked(true);
	 					kDTable2.getColumn(c+2).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
	 					
	 					for (int i = 0; i < kDTable2.getRowCount(); i++) 
	 					{
	 						kDTable2.getRow(i).getCell(c+2).setValue(BigDecimal.ZERO);
						}
	 				}
	 				else
	 				{
	 					kDTable2.getColumn(c+2).getStyleAttributes().setLocked(false);
	 					kDTable2.getColumn(c+2).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
	 				}
	    		}
    		}
        }
        
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	super.storeFields();
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
    	this.kdtEntryUnit.removeRows();
        this.kdtEntryValid.removeRows();
        this.kdtEntryScore.removeRows();
        this.kdtEntryTotal.removeRows();
        
        InviteReportInfo reportInfo = (InviteReportInfo) this.prmtinviteReport.getValue();
        if(reportInfo == null) {
        	MsgBox.showWarning("招标方案申报不能为空");
        	SysUtil.abort();
        }
    	IInviteReport iinviteReport = InviteReportFactory.getRemoteInstance();
    	reportInfo = iinviteReport.getInviteReportInfo(new ObjectUuidPK(reportInfo.getId()));//招标方案完整信息
    	//获取符合性审查模板
		EvaluationTemplateInfo evaTmpInfo = reportInfo.getValidTemplate();
		evaTmpInfo = EvaluationTemplateFactory.getRemoteInstance().getEvaluationTemplateInfo(new ObjectUuidPK(evaTmpInfo.getId()));
		EvaluationTemplateEntryCollection evaEntryColl = evaTmpInfo.getEntry();//审查模板分录
		int count = evaEntryColl.size() + 1;
		//自动 得出符合性评审结果
//		for(int c = 2; c < this.kDTable1.getColumnCount(); c++) {
//			for(int r = 0; r < this.kDTable1.getRowCount(); r += count) {
//				int invalid = 0;
//				for(int i = r; i < r+count-1; i++) {
//					if(kDTable1.getRow(i).getCell(c).getValue().equals(false))
//						invalid++;
//				}
//				if(invalid > 0) 
//					kDTable1.getRow(r+count-1).getCell(c).setValue(false);
//				else
//					kDTable1.getRow(r+count-1).getCell(c).setValue(true);
//			}
//		}

		//保存评委，评审指标,符合性信息
		for(int i = 0; i < this.kDTable1.getRowCount(); i++) {
			IRow row = this.kDTable1.getRow(i);
			for(int j = 2; j < this.kDTable1.getColumnCount()-1; j++) {
				IRow rowAdd = this.kdtEntryValid.addRow();
				rowAdd.getCell("judges").setValue(row.getCell("Judges").getValue());
				rowAdd.getCell("indicators").setValue(row.getCell("Indicator").getValue());
				rowAdd.getCell("remake").setValue(row.getCell("remake").getValue());
				rowAdd.getCell("valid").setValue(row.getCell(j).getValue().toString());
			}
		}
    	//保存投标单位信息
    	for(int i = 2; i < this.kDTable1.getColumnCount()-1; i++) {
    		IRow headRow = this.kDTable1.getHeadRow(0);
    		IRow rowAdd = this.kdtEntryUnit.addRow();
    		rowAdd.getCell("enterprise").setValue(headRow.getCell(i).getValue());
    	}
    	
    	//评分分录保存
    	if(judgeSolution.integrate.equals(this.evaSolution.getSelectedItem())) {
    		//获取评分模板
    		InviteReportE6Collection InviteTempCollection = reportInfo.getE6();
    		int cont = InviteTempCollection.size();
    		//自动 得出评分结果
    		for(int c = 3; c < this.kDTable2.getColumnCount(); c++) {
    			for(int r = 0; r < this.kDTable2.getRowCount(); r += cont + 1) {
    				BigDecimal fullscore = new BigDecimal(0);
    				for(int i = r; i < r+cont; i++) {
    						fullscore = fullscore.add(new BigDecimal(kDTable2.getRow(i).getCell(c).getValue().toString()));
    				}
    				kDTable2.getRow(r+cont).getCell(c).setValue(fullscore.toString());
    			}
    		}

    		//保存评分信息
    		for(int i = 0; i < this.kDTable2.getRowCount(); i++) {
    			IRow row = this.kDTable2.getRow(i);
    			for(int j = 3; j < this.kDTable2.getColumnCount(); j++) {
    				IRow rowAdd = this.kdtEntryScore.addRow();
    				rowAdd.getCell("judges").setValue(row.getCell("Judges").getValue());
    				rowAdd.getCell("indicators").setValue(row.getCell("Indicator").getValue());
    				rowAdd.getCell("fullScore").setValue(row.getCell("fullScore").getValue());
    				rowAdd.getCell("score").setValue(row.getCell(j).getValue());
    			}
    		}
    	}

    	for(int i = 1; i < kDTable3.getColumnCount(); i++) {
    		kDTable3.getRow(2).getCell(i).setValue("0");
    		kDTable3.getRow(3).getCell(i).setValue("0");
    		kDTable3.getRow(4).getCell(i).setValue("0");
    	}
		//过滤开标登记
//		EntityViewInfo evi = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("reportName.id", reportInfo.getId(), CompareType.EQUALS));
//		evi.setFilter(filter);
    	String oql = "where reportName.id='" + reportInfo.getId() + "' and cancel<>'1' and status='4'";
		OpenRegistrationCollection openRegColl = OpenRegistrationFactory.getRemoteInstance().getOpenRegistrationCollection(oql);
		if(openRegColl.size()<1)
			return;
		OpenRegistrationInfo openRegInfo = openRegColl.get(0);
		
		OpenRegistrationEntryCollection openRegEntryColl = openRegInfo.getEntry();
		//设置总分分录报价
		for(int i = 0; i < openRegEntryColl.size(); i++) {
			OpenRegistrationEntryInfo openRegEntryInfo = openRegEntryColl.get(i);
			if(!openRegEntryInfo.isIsPresent()||!openRegEntryInfo.isIsQualified())
				continue;
			this.kDTable3.getRow(0).getCell(i+1).setValue(openRegEntryInfo.getQuotedPrice());
		}
    	//总分分录符合性
    	for(int c = 2; c < kDTable1.getColumnCount()-1; c++) {
    		int invalid = 0;
    		for(int r = count - 1; r < this.kDTable1.getRowCount(); r += count) {
				if(this.kDTable1.getRow(r).getCell(c).getValue().equals(false))
					invalid++;	
			}
    		if(invalid >= 2)
    			this.kDTable3.getRow(1).getCell(c-1).setValue("不合格"); 
    		else
    			this.kDTable3.getRow(1).getCell(c-1).setValue("合格");
    	}
    	//总分分录技术评分
    	if(judgeSolution.integrate.equals(this.evaSolution.getSelectedItem())) {
    		//获取评分模板
    		EvaluationTemplateInfo evaTempInfo = reportInfo.getEvaTemplate();
    		evaTempInfo = EvaluationTemplateFactory.getRemoteInstance().getEvaluationTemplateInfo(new ObjectUuidPK(evaTempInfo.getId()));
    		EvaluationTemplateEntryCollection evaTempEntryColl = evaTempInfo.getEntry();
    		int cont = evaTempEntryColl.size();
    		//获取专家确定
//    		EntityViewInfo evijudge = new EntityViewInfo();
//    		FilterInfo filterju = new FilterInfo();
//    		filterju.getFilterItems().add(new FilterItemInfo("planName.id", reportInfo.getId(), CompareType.EQUALS));
//    		evijudge.setFilter(filterju);
    		oql = "where planName.id='" + reportInfo.getId() + "'";
    		JudgesComfirmCollection judgeComColl = JudgesComfirmFactory.getRemoteInstance().getJudgesComfirmCollection(oql);
    		JudgesComfirmInfo judgeComInfo = judgeComColl.get(0);
    		JudgesComfirmEntryCollection judgeComEntryColl = judgeComInfo.getEntry();//专家确定分录
    		int jucount = judgeComEntryColl.size(); //评分专家人数
    		
    		//技术标得分
    		for(int c = 3; c < this.kDTable2.getColumnCount(); c++) {
        		BigDecimal fullscore = new BigDecimal(0);
    			for(int r = cont; r < this.kDTable2.getRowCount(); r += cont + 1) {	
    					fullscore = fullscore.add(new BigDecimal(kDTable2.getRow(r).getCell(c).getValue().toString()));
    				}
    			kDTable3.getRow(2).getCell(c-2).setValue(fullscore.divide(new BigDecimal(jucount), 2, RoundingMode.HALF_UP).toString());
    		}
    		//计算评标基准价
    		ArrayList<String> price = new ArrayList<String>();//存放有效报价(通过符合性审查的)
    		IRow rowPrice = this.kDTable3.getRow(0);//总分分录报价行
			IRow rowValid = this.kDTable3.getRow(1);//总分分录符合性审查结果行
//			BigDecimal coefficient = new BigDecimal(openRegInfo.getCoefficient());
    		for(int i = 1; i < this.kDTable3.getColumnCount(); i++) {
    			if("合格".equals(rowValid.getCell(i).getValue().toString().trim()))
    				price.add(rowPrice.getCell(i).getValue().toString().trim());
    		}

    		Collections.sort(price);
    		BigDecimal basePrice = new BigDecimal(0);
    		int rmLow = Integer.parseInt(reportInfo.getRmlow());//去除几个最低
    		int rmHigh = Integer.parseInt(reportInfo.getRmhigh());//去除几个最高
    		int validCount = price.size() - rmLow - rmHigh;
    		if(validCount > 0) {
	    		basePrice = new BigDecimal(0);
	    		for(int i = rmLow; i < price.size()-rmHigh; i++) {
	    			basePrice = basePrice.add(new BigDecimal(price.get(i)));
	    		}
	    		basePrice = basePrice.divide(new BigDecimal(validCount), 2, BigDecimal.ROUND_HALF_UP);
	    	    this.txtbasePrice.setValue(basePrice);
    		}
    		
    		//计算得分(商务分，总分)
    		BigDecimal businessScore = new BigDecimal(reportInfo.getBusinessScore());//商务分满分
    		if(basePrice.compareTo(new BigDecimal(0)) > 0) {
	    		for(int c = 1; c < this.kDTable3.getColumnCount(); c++) {
	    			BigDecimal techScore = new BigDecimal(this.kDTable3.getRow(2).getCell(c).getValue().toString());//技术分
	    			BigDecimal score = businessScore;//商务分满分
	    			BigDecimal evaprice = new BigDecimal(kDTable3.getRow(0).getCell(c).getValue().toString());//获得报价
	    			BigDecimal sub = evaprice.subtract(basePrice);//报价基准价之差
	    			if(sub.compareTo(new BigDecimal(0)) > 0) {
	    				score = score.subtract(sub.abs().divide(basePrice, 2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).multiply(new BigDecimal(reportInfo.getReduHigh())));
	    				if(score.compareTo(new BigDecimal(0)) <= 0)
	    					score = new BigDecimal(0);
	    			} else if(evaprice.subtract(basePrice).compareTo(new BigDecimal(0)) < 0) {
	    				score = score.subtract(sub.abs().divide(basePrice, 2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).multiply(new BigDecimal(reportInfo.getReduLow())));
	    				if(score.compareTo(new BigDecimal(0)) <= 0)
	    					score = new BigDecimal(0);
	    			}
	    			if(this.kDTable3.getRow(1).getCell(c).getValue().equals("合格")) {
	    				this.kDTable3.getRow(3).getCell(c).setValue(score.toString());//总分分录商务分
	    				this.kDTable3.getRow(4).getCell(c).setValue(score.add(techScore).toString());//总分行
	    			} else {
	    				this.kDTable3.getRow(2).getCell(c).setValue(0);
	    				this.kDTable3.getRow(3).getCell(c).setValue(0);//总分分录商务分
	    				this.kDTable3.getRow(4).getCell(c).setValue(0);//总分行
	    			}
	    		}
    		} else {
    			if(price.size() == 1) {
    				for(int c = 1; c < kDTable3.getColumnCount(); c++) {
    					IRow rowvalid = kDTable3.getRow(1);
    					IRow rowTech = kDTable3.getRow(2);
    					if("合格".equals(rowvalid.getCell(c).getValue().toString().trim())) {
    						BigDecimal score = new BigDecimal(reportInfo.getBusinessScore());
    						kDTable3.getRow(3).getCell(c).setValue(score.toPlainString());
    						kDTable3.getRow(4).getCell(c).setValue(score.add(new BigDecimal(rowTech.getCell(c).getValue().toString())));
    						kDTable3.getRow(5).getCell(c).setValue("1");
    					}
    				}
    			} else {
    				for(int c = 1; c < kDTable3.getColumnCount(); c++) {
    					this.kDTable3.getRow(2).getCell(c).setValue(0);
    					this.kDTable3.getRow(3).getCell(c).setValue(0);//总分分录商务分
    					this.kDTable3.getRow(4).getCell(c).setValue(0);//总分行
    				}
    			}
    		}
    	}
    	
    	//计算排名
    	if(judgeSolution.integrate.equals(this.evaSolution.getSelectedItem())) {
    		//综合评分法排名
    		IRow rowTotal = this.kDTable3.getRow(4);//总分行
    		IRow rowValid = this.kDTable3.getRow(1);//符合审查结果行
    		for(int c = 1; c < kDTable3.getColumnCount(); c++)
    			kDTable3.getRow(5).getCell(c).setValue("");
    		ArrayList<String> total = new ArrayList<String>();
    		HashMap<String, String> hmap = new HashMap<String, String>();
    		for(int i = 1; i < this.kDTable3.getColumnCount(); i++) {
    			if("合格".equals(rowValid.getCell(i).getValue().toString().trim()))
    				total.add(rowTotal.getCell(i).getValue().toString());
    		}
    		Collections.sort(total);
    		for(int i = total.size()-1; i >= 0; i--) {
    			if(hmap.containsKey(total.get(i))) 
    				continue;
    			int index = total.size()-i;
    			hmap.put(total.get(i), String.valueOf(index));
    		}
    		IRow row = this.kDTable3.getRow(5);
    		for(int i = 1; i < this.kDTable3.getColumnCount(); i++) {
    			if("合格".equals(rowValid.getCell(i).getValue().toString().trim()))
    			row.getCell(i).setValue(hmap.get(rowTotal.getCell(i).getValue().toString()));
    		}
    	} else {
    		//最低价评分法排名
    		IRow rowPrice = this.kDTable3.getRow(0);//报价行
    		IRow rowValid = this.kDTable3.getRow(1);//符合审查结果行
    		ArrayList<String> price = new ArrayList<String>();
    		HashMap<String, String> hmap = new HashMap<String, String>();
    		for(int i = 1; i < this.kDTable3.getColumnCount();i++) {
    			if("合格".equals(rowValid.getCell(i).getValue().toString().trim()))
    				price.add(rowPrice.getCell(i).getValue().toString().trim());
    		}
    		Collections.sort(price);
    		for(int i = 0; i < price.size(); i++) {
    			if(hmap.containsKey(price.get(i)))
    				continue;
    			hmap.put(price.get(i), String.valueOf(i+1));
    		}
    		IRow row = this.kDTable3.getRow(5);
    		for(int i = 1; i < this.kDTable3.getColumnCount(); i++) {
    			row.getCell(i).setValue(hmap.get(rowPrice.getCell(i).getValue().toString()));
    		}
    	}
    	
    	//保存总分分录
    	for(int i = 0; i < this.kDTable3.getRowCount(); i++) {
    		IRow row = this.kDTable3.getRow(i);
    		for(int j = 1; j < this.kDTable3.getColumnCount(); j++) {
    			IRow rowAdd = this.kdtEntryTotal.addRow();
    			rowAdd.getCell("indicators").setValue(row.getCell("enterprise").getValue());
    			rowAdd.getCell("result").setValue(row.getCell(j).getValue());
    		}
    	}
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
//    	this.kdtEntryUnit.removeRows();
//        this.kdtEntryValid.removeRows();
//        
//        InviteReportInfo reportInfo = (InviteReportInfo) this.prmtinviteReport.getValue();
//    	IInviteReport iinviteReport = InviteReportFactory.getRemoteInstance();
//    	reportInfo = iinviteReport.getInviteReportInfo(new ObjectUuidPK(reportInfo.getId()));//招标方案完整信息
//    	//获取符合性审查模板
//		EvaluationTemplateInfo evaTmpInfo = reportInfo.getValidTemplate();
//		evaTmpInfo = EvaluationTemplateFactory.getRemoteInstance().getEvaluationTemplateInfo(new ObjectUuidPK(evaTmpInfo.getId()));
//		EvaluationTemplateEntryCollection evaEntryColl = evaTmpInfo.getEntry();//审查模板分录
//		int count = evaEntryColl.size();
//		//自动 得出评审结果
//		for(int c = 2; c < this.kDTable1.getColumnCount(); c++) {
//			for(int r = 0; r < this.kDTable1.getRowCount(); r += count + 1) {
//				int invalid = 0;
//				for(int i = r; i < r+count; i++) {
//					if(kDTable1.getRow(i).getCell(c).getValue().equals(false))
//						invalid++;
//				}
//				if(invalid > 0) 
//					kDTable1.getRow(r+count).getCell(c).setValue(false);
//				else
//					kDTable1.getRow(r+count).getCell(c).setValue(true);
//			}
//		}
//		
//        for(int i = 0; i < this.kDTable1.getRowCount(); i++) {
//    		IRow row = this.kDTable1.getRow(i);
//    		IRow rowAdd = this.kdtEntryValid.addRow();
//    		//保存评委,以及指标信息
//    		rowAdd.getCell("judges").setValue(row.getCell("Judges").getValue());
//    		rowAdd.getCell("indicators").setValue(row.getCell("Indicator").getValue());
//    		
//    		//保存每一行分数信息
//    		ArrayList<String> validList = new ArrayList<String>();
//    		for(int j = 2; j < this.kDTable1.getColumnCount(); j++) {
//    			validList.add(row.getCell(j).getValue().equals(true) ? "1" : "0");
//    		}  		
//    		rowAdd.getCell("valid").setValue(validList);
//    	}
//    	//保存投标单位信息
//    	for(int i = 2; i < this.kDTable1.getColumnCount(); i++) {
//    		IRow headRow = this.kDTable1.getHeadRow(0);
//    		IRow rowAdd = this.kdtEntryUnit.addRow();
//    		rowAdd.getCell("enterprise").setValue(headRow.getCell(i).getValue());
//    	}
    	actionSave_actionPerformed(e);
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
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invite.EvaluationFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invite.EvaluationInfo objectValue = new com.kingdee.eas.port.pm.invite.EvaluationInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setEvaDate(new Date());
        return objectValue;
    }
	@Override
	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return null;
	}

}