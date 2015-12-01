/**
 * output package name
 */
package com.kingdee.eas.fdc.gcftbiaoa.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.gcftbiaoa.AllocationIndex;
import com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryDetailInfo;
import com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryInfo;
import com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo;
import com.kingdee.eas.fdc.gcftbiaoa.TreeNodeInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;
import com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class GcftbEditUI extends AbstractGcftbEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(GcftbEditUI.class);
	private final static String CANTUNAUDIT = "cantUnAudit";
	private final static String CANTAUDIT = "cantAudit";
	private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";
	private final static String CANTAUDITEDITSTATE = "cantAuditEditState";
	
	private KDWorkButton importExcelButton = new KDWorkButton("导入Excel");
	private KDWorkButton outExcelButton = new KDWorkButton("导出Excel模板");

	/**
	 * output class constructor
	 */
	public GcftbEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		btnModify.setEnabled(true);
		contengineeringProject.setEnabled(false);
		contstatus.setEnabled(false);
		chkisLast.setEnabled(false);
		contbbh.setEnabled(false);
		contgsmc.setEnabled(false);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("FullOrgUnit.id", SysContext.getSysContext()
						.getCurrentOrgUnit().getId()));
		// 工程项目过滤
		KDBizPromptBox gcxm = (KDBizPromptBox) this.kdtEntrys.getColumn(
				"engineeringProject").getEditor().getComponent();
		gcxm.setEntityViewInfo(view);
		// 收益项目过滤
		KDBizPromptBox syxm = (KDBizPromptBox) this.kdtDetail.getColumn(
				"benefitProject").getEditor().getComponent();
		syxm.setEntityViewInfo(view);
		// 显示名称
		ObjectValueRender kdtEntrys_engineeringProject_OVR = new ObjectValueRender();
		kdtEntrys_engineeringProject_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtEntrys.getColumn("engineeringProject").setRenderer(kdtEntrys_engineeringProject_OVR);
		// 设置颜色
		kdtEntrys.getColumn("engineeringProject").setRequired(true);
		kdtEntrys.getColumn("facilityName").setRequired(true);
		kdtEntrys.getColumn("proptreyRight").setRequired(true);
		kdtEntrys.getColumn("constructionArea").setRequired(true);
		kdtEntrys.getColumn("startTime").setRequired(true);
		kdtEntrys.getColumn("completionTime").setRequired(true);
		kdtEntrys.getColumn("totalCost").setRequired(true);
		kdtEntrys.getColumn("totalAmount").setRequired(true);
		kdtEntrys.getColumn("share").setRequired(true);
		kdtEntrys.getColumn("sharePrice").setRequired(true);
		kdtDetail.getColumn("benefitProject").setRequired(true);
		kdtDetail.getColumn("allocationBase").setRequired(true);
		kdtDetail.getColumn("shareAmount").setRequired(true);
		//分录不可编辑
		kdtEntrys.getColumn("share").getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn("sharePrice").getStyleAttributes().setLocked(true);
		kdtDetail.getColumn("allocationBase").getStyleAttributes().setLocked(true);
		kdtDetail.getColumn("shareAmount").getStyleAttributes().setLocked(true);
		
		kdtEntrys.getColumn("share").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		kdtEntrys.getColumn("sharePrice").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		kdtDetail.getColumn("allocationBase").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		kdtDetail.getColumn("shareAmount").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		
		//连续提交设置
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		
		setDetailPanel(kdtEntrys_detailPanel, kDContainer1);
		setDetailPanel(kdtDetail_detailPanel, kDContainer2);
		
		KDComboBox kdtEntrys_allocationIndex_ComboBox = new KDComboBox();
        kdtEntrys_allocationIndex_ComboBox.setName("kdtEntrys_allocationIndex_ComboBox");
        kdtEntrys_allocationIndex_ComboBox.setVisible(true);
        kdtEntrys_allocationIndex_ComboBox.addItems(AllocationIndex.getEnumList().toArray());
        KDTDefaultCellEditor kdtEntrys_allocationIndex_CellEditor = new KDTDefaultCellEditor(kdtEntrys_allocationIndex_ComboBox);
        this.kdtEntrys.getColumn("allocationIndex").setEditor(kdtEntrys_allocationIndex_CellEditor);
        
        setEntryButtonAction();
        setEntryDetailButtonAction();
        //合计
        setTableToSumField(kdtEntrys,new String[]{"totalCost","totalAmount","costHasOccurred","share"});
        setTableToSumField(kdtDetail,new String[]{"allocationBase","shareAmount"});   
        
        
        importExcelButton.setIcon(EASResource.getIcon(""));
		outExcelButton.setIcon(EASResource.getIcon(""));
		
		kDContainer1.addButton(importExcelButton);
		kDContainer1.addButton(outExcelButton);
		
		importExcelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				importExcelButton_actionPerformed(e);
			}
		});
		outExcelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				outExcelButton_actionPerformed(e);
			}
		});
	}
	
	private void setDetailPanel(DetailPanel detail,KDContainer kDContainer){
		KDWorkButton addNewLineButton = detail.getAddNewLineButton();
		KDWorkButton insertLineButton = detail.getInsertLineButton();
		KDWorkButton removeLinesButton = detail.getRemoveLinesButton();
		
		addNewLineButton.setText("新增分录");
		insertLineButton.setText("插入分录");
		removeLinesButton.setText("删除分录");
		kDContainer.addButton(addNewLineButton);
		kDContainer.addButton(insertLineButton);
		kDContainer.addButton(removeLinesButton);
		kDContainer.getContentPane().add(detail.getEntryTable(),BorderLayout.CENTER);
	}
	
	private void importExcelButton_actionPerformed(ActionEvent e) {
		
	}
	
	private void outExcelButton_actionPerformed(ActionEvent e) {
		
	}

	protected void beforeStoreFields(ActionEvent arg0) throws Exception {
		super.beforeStoreFields(arg0);
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		boolean flse = (oprtType.equals("EDIT")||oprtType.equals("ADDNEW"))?true:false;
		importExcelButton.setEnabled(flse);
		if(kdtDetail_detailPanel!=null){
			kdtDetail_detailPanel.getAddNewLineButton().setEnabled(flse);
			kdtDetail_detailPanel.getInsertLineButton().setEnabled(flse);
			kdtDetail_detailPanel.getRemoveLinesButton().setEnabled(flse);
		}
		if(kdtEntrys_detailPanel!=null){
			kdtEntrys_detailPanel.getAddNewLineButton().setEnabled(flse);
			kdtEntrys_detailPanel.getInsertLineButton().setEnabled(flse);
			kdtEntrys_detailPanel.getRemoveLinesButton().setEnabled(flse);
		}
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "engineeringProject").getValue())) {
				MsgBox.showWarning("项目不能为空！");
				SysUtil.abort();
			}
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "facilityName").getValue())) {
				MsgBox.showWarning("设施名称不能为空！");
				SysUtil.abort();
			}
			//if(UIRuleUtil.isNull(kdtEntrys.getCell(i,"proptreyRight").getValue
			// ())){
			// MsgBox.showWarning("产权情况不能为空！");
			// SysUtil.abort();
			// }
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "constructionArea")
					.getValue())) {
				MsgBox.showWarning("建筑面积不能为空！");
				SysUtil.abort();
			}
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "startTime").getValue())) {
				MsgBox.showWarning("开工时间不能为空！");
				SysUtil.abort();
			}
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "completionTime")
					.getValue())) {
				MsgBox.showWarning("竣工时间不能为空！");
				SysUtil.abort();
			}
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "totalCost").getValue())) {
				MsgBox.showWarning("已发生成本不能为空！");
				SysUtil.abort();
			}
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "totalAmount")
					.getValue())) {
				MsgBox.showWarning("应分摊总量不能为空！");
				SysUtil.abort();
			}
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "share").getValue())){
				MsgBox.showWarning("待分摊总量不能为空！");
				SysUtil.abort();
			}else{
				if(((BigDecimal) (kdtEntrys.getCell(i, "share").getValue())).compareTo(BigDecimal.ZERO)==-1){
					MsgBox.showWarning("待分摊总量不能为负数！");
					SysUtil.abort();
				}
			}
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "sharePrice").getValue())) {
				MsgBox.showWarning("分摊单价不能为空！");
				SysUtil.abort();
			}
		}
		
		for(int x= 0; x<kdtEntrys.getRowCount(); x++){
			IRow row = kdtEntrys.getRow(x);
			String xmId = ((CurProjectInfo)row.getCell("engineeringProject").getValue()).getId().toString();
			String lxId =((ProductTypeInfo)row.getCell("facilityName").getValue()).getId().toString();
			for(int l= 1+x; l<kdtEntrys.getRowCount();l++){
				row = kdtEntrys.getRow(l);
				String xmIdT = ((CurProjectInfo)row.getCell("engineeringProject").getValue()).getId().toString();
				String lxIdT =((ProductTypeInfo)row.getCell("facilityName").getValue()).getId().toString();
				if((xmId+lxId).equals(xmIdT+lxIdT)){
					MsgBox.showWarning("第"+(x+1)+"行与第"+(l+1)+"行工程项目+设施名称重复！！！");
					SysUtil.abort();
				}
			}
		}
		storeFields();
		
		for (int j = 0; j < editData.getEntrys().size(); j++) {
			GcftbEntryInfo gcftbEntryInfo = editData.getEntrys().get(j);
			
			Set<String> projectSet = new HashSet<String>();
			for (int k = 0; k < gcftbEntryInfo.getDetail().size(); k++) {
				GcftbEntryDetailInfo detailInfo = gcftbEntryInfo.getDetail().get(k);
				
				if (UIRuleUtil.isNull(detailInfo.getBenefitProject())) {
					MsgBox.showWarning(gcftbEntryInfo.getEngineeringProject().getName()+ "对应的分摊项目不能为空！");
					SysUtil.abort();
				}
				if (UIRuleUtil.isNull(detailInfo.getAllocationBase())) {
					MsgBox.showWarning(gcftbEntryInfo.getEngineeringProject().getName()+ "对应的分摊基数不能为空！");
					SysUtil.abort();
				}
				if (UIRuleUtil.isNull(detailInfo.getShareAmount())) {
					MsgBox.showWarning(gcftbEntryInfo.getEngineeringProject().getName()+ "对应的分摊金额不能为空！");
					SysUtil.abort();
				}
				projectSet.add(detailInfo.getBenefitProject().getId().toString());
			}
			
			if(projectSet.size()!=gcftbEntryInfo.getDetail().size()){
				FDCMsgBox.showWarning(gcftbEntryInfo.getEngineeringProject().getName()+ "对应的分摊项目重复！");
				SysUtil.abort();
			}
		}
	}

	// 编辑停止事件
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		super.kdtEntrys_editStopped(e);
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		 //项目是否结束
		 if(colIndex == kdtEntrys.getColumnIndex("engineeringProject") &&
		 kdtEntrys.getCell(rowIndex, colIndex).getValue() != null)
		 {
		 CurProjectInfo xminfo = (CurProjectInfo)kdtEntrys.getCell(rowIndex,
		 colIndex).getValue();
		 boolean end = xminfo.isProjectEnd();
		 if(end == true){
		 MsgBox.showWarning("项目已结束，请选择别的项目");
		 SysUtil.abort();
		 }
		 }
		changeTableDataLinens(kdtEntrys);
		 setTableToSumField(kdtEntrys,new String[]{"totalCost","totalAmount","costHasOccurred","share"});
	     setTableToSumField(kdtDetail,new String[]{"allocationBase","shareAmount"});    
	}

	
	private void setEntryDetailButtonAction() {
		ActionListener[] actions = kdtDetail_detailPanel.getRemoveLinesButton().getActionListeners();
		if (actions != null && actions.length > 0)
			kdtDetail_detailPanel.getRemoveLinesButton().removeActionListener(actions[0]);
		kdtDetail_detailPanel.getRemoveLinesButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						removeDetailEntry();
					}
			});
		kdtDetail_detailPanel.getInsertLineButton().removeActionListener(actions[0]);
		kdtDetail_detailPanel.getInsertLineButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
			});
		kdtDetail_detailPanel.getAddNewLineButton().removeActionListener(actions[0]);
		kdtDetail_detailPanel.getAddNewLineButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		kdtDetail_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
	              public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
	            	  GcftbEntryInfo entryInfo = checkSelectEntry();
	            	  IObjectValue vo = event.getObjectValue();
	            	  vo.put("id", BOSUuid.create(new GcftbEntryDetailInfo().getBOSType()));
	            	  vo.put("parent1", entryInfo);
	              }
	              public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
	              }
	      });
		kdtDetail_detailPanel.addInsertListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener(){
			public void beforeEvent(DetailPanelEvent detailpanelevent) throws Exception {
				GcftbEntryInfo entryInfo = checkSelectEntry();
				IObjectValue vo = detailpanelevent.getObjectValue();
				vo.put("id", BOSUuid.create(new GcftbEntryDetailInfo().getBOSType()));
				vo.put("parent1", entryInfo);
			}
			public void afterEvent(DetailPanelEvent detailpanelevent) throws Exception {
			}
		});
	}
	
	private void setEntryButtonAction() {
		ActionListener[] actions = kdtEntrys_detailPanel.getRemoveLinesButton().getActionListeners();
		if (actions != null && actions.length > 0)
			kdtEntrys_detailPanel.getRemoveLinesButton().removeActionListener(actions[0]);
		kdtEntrys_detailPanel.getRemoveLinesButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						kdtEntrys.removeRow(kdtEntrys.getSelectManager().getActiveRowIndex());
					}
			});
		kdtEntrys_detailPanel.getInsertLineButton().removeActionListener(actions[0]);
		kdtEntrys_detailPanel.getInsertLineButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
			});
		kdtEntrys_detailPanel.getAddNewLineButton().removeActionListener(actions[0]);
		kdtEntrys_detailPanel.getAddNewLineButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		kdtEntrys_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
	              public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
	            	  IObjectValue vo = event.getObjectValue();
	            	  vo.put("id", BOSUuid.create(new GcftbEntryInfo().getBOSType()));
	              }
	              public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
	              }
	      });
		kdtEntrys_detailPanel.addInsertListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener(){
			public void beforeEvent(DetailPanelEvent detailpanelevent) throws Exception {
				IObjectValue vo = detailpanelevent.getObjectValue();
				vo.put("id", BOSUuid.create(new GcftbEntryInfo().getBOSType()));
			}
			public void afterEvent(DetailPanelEvent detailpanelevent) throws Exception {
			}
		});
	}
	
	private void removeDetailEntry(){
		int activeRowIndex = kdtDetail.getSelectManager().getActiveRowIndex();
		if(activeRowIndex==-1)return;
		GcftbEntryDetailInfo detailInfo = (GcftbEntryDetailInfo)kdtDetail.getRow(activeRowIndex).getUserObject();
		
		String afterId = "";
		if(detailInfo.getParent1()!=null)
			afterId = detailInfo.getParent1().getId().toString();
		for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow row = this.kdtEntrys.getRow(i);
			if(row.getUserObject()==null ||!(row.getUserObject() instanceof GcftbEntryInfo))
				continue;
			GcftbEntryInfo entryInfo = (GcftbEntryInfo)row.getUserObject();
			if(afterId.equals(entryInfo.getId().toString())){
				entryInfo.getDetail().remove(detailInfo);
			}
		}
		kdtDetail.removeRow(kdtDetail.getSelectManager().getActiveRowIndex());
		try {
			changeTableDataLinens(kdtEntrys);
		} catch (BOSException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	

	private GcftbEntryInfo checkSelectEntry(){
		int activeRowIndex = this.kdtEntrys.getSelectManager().getActiveRowIndex();
		if(this.kdtEntrys.getSelectManager().getActiveRowIndex()==-1){
			FDCMsgBox.showWarning("请先选中明细分录！");
			SysUtil.abort();
		}
		
		return (GcftbEntryInfo)this.kdtEntrys.getRow(activeRowIndex).getUserObject();
	}
	
	//分摊基数赋值
	protected void kdtDetail_editStopped(KDTEditEvent e) throws Exception {
		super.kdtDetail_editStopped(e);
		changeTableDataLinens(kdtDetail);
		
		 setTableToSumField(kdtEntrys,new String[]{"totalCost","totalAmount","costHasOccurred","share"});
	     setTableToSumField(kdtDetail,new String[]{"allocationBase","shareAmount"});    
		
	}
	
	
	private void changeTableDataLinens(KDTable table) throws BOSException, SQLException{
		int indexRow = table.getSelectManager().getActiveRowIndex();
		if(indexRow==-1)return;
		IRow row = table.getRow(indexRow);
		if(table.getName().equals("kdtEntrys")){
			if(UIRuleUtil.isNull(row.getCell("costHasOccurred").getValue())){
				//单价
				row.getCell("sharePrice").setValue(FDCHelper.divide(row.getCell("totalCost").getValue(), row.getCell("totalAmount").getValue(),2,4));
			}else{
				row.getCell("sharePrice").setValue(FDCHelper.divide(row.getCell("costHasOccurred").getValue(), row.getCell("totalAmount").getValue(),2,4));
			}
			AllocationIndex cationIndex = (AllocationIndex)row.getCell("allocationIndex").getValue();
			updateDetailBaseAmount(-1,cationIndex);
			row.getCell("share").setValue(FDCHelper.subtract(row.getCell("totalAmount").getValue(), UIRuleUtil.sum(kdtDetail, "allocationBase")));
		}else{
			//判断项目是否结束
			CurProjectInfo syxmInfo = (CurProjectInfo)row.getCell("benefitProject").getValue();
			boolean end = syxmInfo.isProjectEnd();
			if(end == false){
				int activeRowIndex = this.kdtEntrys.getSelectManager().getActiveRowIndex();
				if(activeRowIndex==-1)return;
				AllocationIndex cationIndex = (AllocationIndex)this.kdtEntrys.getCell(activeRowIndex, "allocationIndex").getValue();
				updateDetailBaseAmount(indexRow,cationIndex);
				this.kdtEntrys.getCell(activeRowIndex, "share").setValue(FDCHelper.subtract(this.kdtEntrys.getCell(activeRowIndex,"totalAmount").getValue(), UIRuleUtil.sum(kdtDetail, "allocationBase")));
			}else{
				MsgBox.showWarning("项目已结束，请选择别的项目");
				SysUtil.abort();
			}
		}
	} 
	
	
	private void updateDetailBaseAmount(int rowIndex,AllocationIndex cationIndex) throws BOSException, SQLException{
		if(rowIndex==-1){
			for (int i = 0; i < this.kdtDetail.getRowCount(); i++)
				updateDetail(i, cationIndex);
		}else{
			updateDetail(rowIndex, cationIndex);
		}
	}
	
	private void updateDetail(int rowIndex,AllocationIndex cationIndex) throws BOSException, SQLException{
		IRow row = this.kdtDetail.getRow(rowIndex);
		CurProjectInfo syxmInfo = (CurProjectInfo)row.getCell("benefitProject").getValue();
		//项目结束就不联动；
		if(syxmInfo.isProjectEnd())
			return;
		GcftbEntryDetailInfo detailInfo = new GcftbEntryDetailInfo();
		if(row.getUserObject()!=null && (row.getUserObject() instanceof GcftbEntryDetailInfo))
			detailInfo = (GcftbEntryDetailInfo)row.getUserObject();
		if(row.getCell("benefitProject").getValue()==null){
			row.getCell("allocationBase").setValue(BigDecimal.ZERO);
			row.getCell("shareAmount").setValue(BigDecimal.ZERO);
			detailInfo.setAllocationBase(BigDecimal.ZERO);
			detailInfo.setShareAmount(BigDecimal.ZERO);
			return;
		}
		CurProjectInfo projectInfo = (CurProjectInfo)row.getCell("benefitProject").getValue();
		BigDecimal baseAmount = getBaseAmount(projectInfo.getId().toString(), cationIndex);
		row.getCell("allocationBase").setValue(baseAmount);
		detailInfo.setAllocationBase(baseAmount);
		
		BigDecimal sharePrice = BigDecimal.ZERO;
		int activeRowIndex = this.kdtEntrys.getSelectManager().getActiveRowIndex();
		if(activeRowIndex!=-1)
			sharePrice = UIRuleUtil.getBigDecimal(this.kdtEntrys.getCell(activeRowIndex, "sharePrice").getValue());
		row.getCell("shareAmount").setValue(FDCHelper.multiply(baseAmount, sharePrice,4));
		detailInfo.setShareAmount(FDCHelper.multiply(baseAmount, sharePrice,4));
	}
	
	private BigDecimal getBaseAmount(String projectId,AllocationIndex index) throws BOSException, SQLException{
		BigDecimal amount = BigDecimal.ZERO;
		StringBuffer sb = new StringBuffer();
		// 产品建筑指标 == 动态——竣工查账 优先取：项目规划指标 == 目标指标
		sb.append(" select case when max(case when pe.fname_l2 ='建筑面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end)=0");
		sb.append(" and max(case when pe.fname_l2 ='可售面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end) = 0");
		sb.append(" then max(case when pe.fname_l2 ='建筑面积' and data.FVerName ='1AIMCOSTAREA' then isnull(entry.FIndexValue,0) else 0 end) else");
		sb.append(" max(case when pe.fname_l2 ='建筑面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end) end,");
		sb.append("   ");
		sb.append(" case when max(case when pe.fname_l2 ='建筑面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end)=0");
		sb.append(" and max(case when pe.fname_l2 ='可售面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end) = 0");
		sb.append(" then max(case when pe.fname_l2 ='可售面积' and data.FVerName ='1AIMCOSTAREA' then isnull(entry.FIndexValue,0) else 0 end) else");
		sb.append(" max(case when pe.fname_l2 ='可售面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end) end");
		sb.append(" from T_FDC_ProjectIndexDataEntry entry  ");
		sb.append(" left join T_FDC_ApportionType  pe on pe.fid = entry.FApportionTypeID");
		sb.append(" left join T_FDC_ProjectIndexData data on data.fid=entry.FParentID ");
		sb.append(" left join T_FDC_CurProject  ct on ct.fid = data.FProjOrOrgID ");
		sb.append(" left join T_FDC_TargetType  tag on tag.fid =entry.FTargetTypeID ");
		sb.append(" where ct.fid ='").append(projectId).append("'");
		sb.append(" and (data.fisLatestVer=1 OR data.fisLatestSubVer=1)");
		sb.append(" and data.FProductTypeID is null");
		IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
		while(rowset.next())
			amount = index.equals(AllocationIndex.coveredArea)?UIRuleUtil.getBigDecimal(rowset.getBigDecimal(1)):UIRuleUtil.getBigDecimal(rowset.getBigDecimal(2));
		return amount;
	}
	
	/**
	 * output loadFields method
	 */
	public void loadFields() {
		super.loadFields();
		setAuditBtnEnable();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		setAuditBtnEnable();
	}

	/**
	 * output btnAddLine_actionPerformed method
	 */
	protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnAddLine_actionPerformed(e);
	}

	/**
	 * 设置审批按钮显示
	 */
	private void setAuditBtnEnable() {
		if (editData.getStatus() == null) {
			aduitAction.setEnabled(false);
			unAduit.setEnabled(false);
		} else if (FDCBillStateEnum.SUBMITTED.equals(editData.getStatus())) {
			aduitAction.setEnabled(true);
			unAduit.setEnabled(false);
			// actionSave.setEnabled(false);
			actionEdit.setEnabled(true);
		} else if (FDCBillStateEnum.AUDITTED.equals(editData.getStatus())) {
			aduitAction.setEnabled(false);
			actionEdit.setEnabled(false);
			unAduit.setEnabled(true);
		}
	}

	// 是否修订
	private boolean isBillModify() {
		// Boolean isSet = (Boolean) getUIContext().get(IS_MODIFY);
		// return isSet != null && isSet.booleanValue();
		// return getUIContext().containsKey(IS_MODIFY);
		return false;
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (isModify()) {
			FDCMsgBox.showInfo("单据已被修改，请先提交。");
			this.abort();
		}
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED, CANTAUDIT);
		super.actionAudit_actionPerformed(e);
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setAuditBtnEnable();
	}

	public void actionUnaudit_actionPerformed(ActionEvent e) throws Exception {
		if (!editData.isIsLast()) {
			FDCMsgBox.showInfo("不是最新版，不能反审批！");
			this.abort();
		}
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, CANTUNAUDIT);
		super.actionUnaudit_actionPerformed(e);
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setAuditBtnEnable();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.lockUIForViewStatus();
		setAuditBtnEnable();
	}

	private void handleOldData() {
		if (!(getOprtState() == STATUS_FINDVIEW || getOprtState() == STATUS_VIEW)) {
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}

	private void syncDataFromDB() throws Exception {
		// 由传递过来的ID获取值对象
		if (getUIContext().get(UIContext.ID) == null) {
			String s = EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_IDIsNull");
			MsgBox.showError(s);
			SysUtil.abort();
		}
		IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
		setDataObject(getValue(pk));
		loadFields();
	}

	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning)
			throws Exception {
		// 检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		boolean b = editData != null && editData.getStatus() != null
				&& editData.getStatus().equals(state);
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

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.gcftbiaoa.GcftbFactory.getRemoteInstance();
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return null;
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		if (getUIContext().get("ForInfo") != null) {
			GcftbInfo info = (GcftbInfo) getUIContext().get("ForInfo");
			info.setBbh(String.valueOf(getUIContext().get("Bbh")));
			info.setId(null);
			info.setIsLast(false);
			for (int i = 0; i < info.getEntrys().size(); i++) {
				GcftbEntryInfo entryInfo = info.getEntrys().get(i);
				entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
				for (int j = 0; j < entryInfo.getDetail().size(); j++) {
					entryInfo.getDetail().get(j).setId(BOSUuid.create(new GcftbEntryDetailInfo().getBOSType()));
				}
			}
			return info;
		} else {
			com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo objectValue = new com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo();
			objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
							.getSysContext().getCurrentUser()));
			if (getUIContext().get("treeNodeInfo") == null) {
				MsgBox.showWarning("合同不能为空！");
				SysUtil.abort();
			}
			TreeNodeInfo conInfo = (TreeNodeInfo) getUIContext().get(
					"treeNodeInfo");
			objectValue.setEngineeringProject(conInfo);
			objectValue.setGsmc(conInfo.getCompany().getName());
			objectValue.setBbh("1");
			objectValue.setBizDate(new Date());
			// objectValue.setAmount(BigDecimal.ZERO);
			return objectValue;

		}
	}

}