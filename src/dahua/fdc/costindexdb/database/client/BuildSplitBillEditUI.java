/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.database.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.IProductType;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryCollection;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryDetailCollection;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryDetailInfo;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryInfo;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitContract;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitDataType;
import com.kingdee.eas.fdc.costindexdb.database.IProfessionPointEntry;
import com.kingdee.eas.fdc.costindexdb.database.ProfessionPointEntryCollection;
import com.kingdee.eas.fdc.costindexdb.database.ProfessionPointEntryFactory;
import com.kingdee.eas.fdc.costindexdb.database.ProfessionPointEntryInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BuildSplitBillEditUI extends AbstractBuildSplitBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(BuildSplitBillEditUI.class);
    private Map<String,KDTable> tables = null;
    List<KDWorkButton> buttons = null;
    private KDWorkButton grabData = null;
    
    /**
     * output class constructor
     */
    public BuildSplitBillEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields() {
    	
    	super.storeFields();
        
    }
	private void storeDataFromTable() {
		BuildSplitBillEntryCollection billEntrys = editData.getEntrys();
        BuildSplitBillEntryInfo billEntryInfo = null;
        BuildSplitBillEntryDetailInfo detailInfo = null;
        KDTable table = null;
        for(int i = 0; i < billEntrys.size(); i++) {
        	billEntryInfo = billEntrys.get(i);
        	if(billEntryInfo.isSplitBuild()){
        		table = tables.get(billEntryInfo.getPointName());
        		billEntryInfo.getDetails().clear();
        		for(int j = 0; j < table.getRowCount3(); j++) {
        			detailInfo = new BuildSplitBillEntryDetailInfo();
        			detailInfo.setBuildNumber((BuildNumberInfo)table.getCell(j,"buildNumber").getValue());
        			detailInfo.setProductType((ProductTypeInfo)table.getCell(j,"productType").getValue());
        			detailInfo.setModelBuild((Boolean)table.getCell(j,"modelBuild").getValue());
        			detailInfo.setDataValue((BigDecimal)table.getCell(j,"dataValue").getValue());
        			detailInfo.setBeizhu((String)table.getCell(j,"beizhu").getValue());
        			billEntryInfo.getDetails().add(detailInfo);
				}
        	}
		}
	}

    public void onLoad() throws Exception {
    	super.onLoad();
    	actionAddNew.setVisible(false);
    	actionCopy.setVisible(false);
    	actionFirst.setVisible(false);
    	actionLast.setVisible(false);
    	actionPre.setVisible(false);
    	actionNext.setVisible(false);
    	actionWorkFlowG.setVisible(false);
    	actionCreateFrom.setVisible(false);
    	actionCreateTo.setVisible(false);
    	actionNextPerson.setVisible(false);
    	actionAuditResult.setVisible(false);
    	actionMultiapprove.setVisible(false);
    	actionSubmit.setVisible(false);
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	initPointEntrys();
    	loadDataToTable();
    	txtNumber.requestFocusInWindow();
    	if(OprtState.VIEW.equals(getOprtState())){
    		setBuutonAndTableState(false);
    		grabData.setEnabled(false);
    	}
    }
    
    public void removeIndexTable(String pointName) {
    	Component[] components = kDTabbedPane1.getComponents();
    	for (int j = 0; j < components.length; j++) {
			if(pointName.equals(components[j].getName())){
				kDTabbedPane1.remove(components[j]);
				tables.remove(pointName);
				break;
			}
		}
    }
    
    public void initPointEntrys(){
//    	kdtEntrys_detailPanel.getRemoveLinesButton().setVisible(false);
//    	kdtEntrys_detailPanel.getInsertLineButton().setVisible(false);
//    	kdtEntrys_detailPanel.getAddNewLineButton().setVisible(false);
    	kDContainer1.getContentPane().remove(kdtEntrys_detailPanel);
    	kDContainer1.getContentPane().add(kdtEntrys, BorderLayout.CENTER);
    	kdtEntrys.getColumn("pointName").getStyleAttributes().setLocked(true);
    	kdtEntrys.getColumn("dataValue").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
    	kdtEntrys.getColumn("splitBuild").getStyleAttributes().setLocked(true);
    	if(!BuildSplitDataType.professPoint.equals(editData.getDataType())){
    		kdtEntrys.getColumn("dataValue").getStyleAttributes().setLocked(true);
    	}
    	tables = new HashMap<String,KDTable>();
    	buttons = new ArrayList<KDWorkButton>();
    	grabData = new KDWorkButton("提取最新数据");
    	grabData.setName("grabData");
    	grabData.addActionListener(new MyActionListener(kdtEntrys));
    	kDContainer1.addButton(grabData);
    	if(!OprtState.EDIT.equals(getOprtState())){
    		grabData.setEnabled(false);
    	}
    	try {
    		final IProductType ipt = ProductTypeFactory.getRemoteInstance();
    		KDTEditAdapter ad = new KDTEditAdapter(){
    			public void editStopped(KDTEditEvent e) {
    				try {
    					table_editStopped(e,ipt);
    				} catch (Exception e1) {
    					handUIException(e1);
    				}
    			}
    		};
    		for(int i = 0; i < kdtEntrys.getRowCount3(); i++) {
    			if((Boolean)kdtEntrys.getCell(i,"splitBuild").getValue())
    				createDetailEntrys(i,ad);
    		}
		} catch (BOSException e2) {
			handUIException(e2);
		}
    }

    public void createDetailEntrys(int rowIndex,KDTEditAdapter adapter){
		KDTable table = new KDTable();
		String key = null;
		IColumn icol = table.addColumn();
		icol.setKey("buildNumber");
		initColumnForBuildNumber(icol);
		icol = table.addColumn();
		icol.setKey("productType");
		initColumnForF7(icol,"com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		icol.setWidth(200);
		icol.getStyleAttributes().setLocked(true);
		icol = table.addColumn();
		icol.setKey("modelBuild");
		initColumnForBoolean(icol);
		icol.getStyleAttributes().setLocked(true);
		icol = table.addColumn();
		icol.setKey("dataValue");
		initColumnForNum(icol);
		icol = table.addColumn();
		icol.setKey("beizhu");
		initColumnForText(icol);
		IRow row = table.addHeadRow();
		row.getCell("buildNumber").setValue("楼号");
		row.getCell("productType").setValue("产品类型");
		row.getCell("modelBuild").setValue("是否典型楼");
		row.getCell("dataValue").setValue("数值");
		row.getCell("beizhu").setValue("备注");
		KDContainer kdc = new KDContainer();
		kdc.getContentPane().setLayout(new BorderLayout(0,0));
		kdc.getContentPane().add(table,BorderLayout.CENTER);
		KDWorkButton addLine = new KDWorkButton("新增行");
		KDWorkButton insetLine = new KDWorkButton("插入行");
		KDWorkButton removeLine = new KDWorkButton("删除行");
		addLine.setName("addLine");
		insetLine.setName("insetLine");
		removeLine.setName("removeLine");
		addLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
		insetLine.setIcon(EASResource.getIcon("imgTbtn_insert"));
		removeLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		kdc.addButton(addLine);
		kdc.addButton(insetLine);
		kdc.addButton(removeLine);
		buttons.add(addLine);
		buttons.add(insetLine);
		buttons.add(removeLine);
		MyActionListener baseAL = new MyActionListener(table);
		addLine.addActionListener(baseAL);
		insetLine.addActionListener(baseAL);
		removeLine.addActionListener(baseAL);
		table.addKDTEditListener(adapter);
		key = (String)kdtEntrys.getCell(rowIndex,"pointName").getValue();
		kDTabbedPane1.add(key,kdc);
		tables.put(key,table);
    }
    
    private void loadDataToTable() {
    	BuildSplitBillEntryCollection billEntrys = editData.getEntrys();
        BuildSplitBillEntryInfo billEntryInfo = null;
        BuildSplitBillEntryDetailCollection details = null;
        BuildSplitBillEntryDetailInfo detailInfo = null;
        KDTable table = null;
        IRow row = null;
        for(int i = 0; i < billEntrys.size(); i++) {
        	billEntryInfo = billEntrys.get(i);
        	if(billEntryInfo.isSplitBuild()){
        		details = billEntryInfo.getDetails();
        		table = tables.get(billEntryInfo.getPointName());
        		for(int j = 0; j < details.size(); j++) {
        			detailInfo = details.get(j);
        			row = table.addRow();
        			row.getCell("buildNumber").setValue(detailInfo.getBuildNumber());
        			row.getCell("productType").setValue(detailInfo.getProductType());
        			row.getCell("modelBuild").setValue(detailInfo.isModelBuild());
        			row.getCell("dataValue").setValue(detailInfo.getDataValue());
        			row.getCell("beizhu").setValue(detailInfo.getBeizhu());
        			
				}
        	}
		}
    }
    
    protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
    	
    }
    
    protected void applyDefaultValue(IObjectValue vo) {
    	
    }
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	String key = null;
    	KDTable table = null;
    	for(Iterator<String> it=tables.keySet().iterator(); it.hasNext();){
    		key = it.next();
    		table = tables.get(key);
			if(table.getRowCount3() == 0){
				MsgBox.showInfo(key+"页签没有拆分到楼号！");
				SysUtil.abort();
			}
			for(int i = 0; i < table.getRowCount3(); i++) {
				if(table.getCell(i,"buildNumber").getValue() == null){
					MsgBox.showInfo(key+"页签的第"+(i+1)+"行楼号不能为空！");
					SysUtil.abort();
				}
			}
		}
    }

    private void setBuutonAndTableState(boolean flag){
    	for(Iterator<String> it=tables.keySet().iterator(); it.hasNext();){
			tables.get(it.next()).setEditable(flag);
		}
    	for(int i = 0; i < buttons.size(); i++) {
    		buttons.get(i).setEnabled(flag);
		}
    }
    
    public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
    	super.actionEdit_actionPerformed(arg0);
    	setBuutonAndTableState(true);
    	grabData.setEnabled(true);
    }
    
    private void initColumnForBuildNumber(IColumn icol){
    	KDBizPromptBox box = new KDBizPromptBox();
    	box.setQueryInfo("com.kingdee.eas.fdc.costindexdb.database.app.BuildNumberQuery");
    	box.setDisplayFormat("$number$");
    	box.setEditFormat("$number$");
    	box.setCommitFormat("$number$");
    	EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(editData.getProjectName() != null)
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", editData.getProjectName().getId().toString()));
		entityView.setFilter(filter);
		box.setEntityViewInfo(entityView);
    	ObjectValueRender kdtEntrys_baseUnit_OVR = new ObjectValueRender();
    	kdtEntrys_baseUnit_OVR.setFormat(new BizDataFormat("$name$"));
    	icol.setEditor(new KDTDefaultCellEditor(box));
    	icol.setRenderer(kdtEntrys_baseUnit_OVR);
    }
    
    private void initColumnForF7(IColumn icol, String queryInfo){
    	KDBizPromptBox box = new KDBizPromptBox();
		box.setQueryInfo(queryInfo);
		box.setDisplayFormat("$number$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$number$");
		ObjectValueRender kdtEntrys_baseUnit_OVR = new ObjectValueRender();
        kdtEntrys_baseUnit_OVR.setFormat(new BizDataFormat("$name$"));
		icol.setEditor(new KDTDefaultCellEditor(box));
		icol.setRenderer(kdtEntrys_baseUnit_OVR);
    }
    
    private void initColumnForText(IColumn icol){
    	KDTextField txt = new KDTextField();
		txt.setMaxLength(200);
		icol.setEditor(new KDTDefaultCellEditor(txt));
    }
    
    private void initColumnForBoolean(IColumn icol){
    	icol.setEditor(new KDTDefaultCellEditor(new KDCheckBox()));
    }

	private void initColumnForNum(IColumn icol){
		KDFormattedTextField numField = new KDFormattedTextField();
		numField.setHorizontalAlignment(2);
		numField.setDataType(1);
//		numField.setMinimumValue(new BigDecimal("-1.0E26"));
		numField.setMinimumValue(new BigDecimal("0.01"));
		numField.setMaximumValue(new BigDecimal("1.0E26"));
		numField.setPrecision(2);
		icol.setEditor(new KDTDefaultCellEditor(numField));
		icol.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//		icol.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // 右对齐
	}
    public void table_editStopped(KDTEditEvent e, IProductType ipt) throws Exception{
    	KDTable kdt = (KDTable)e.getSource();
		if(e.getColIndex()==kdt.getColumnIndex("buildNumber")){
			Object obj = kdt.getCell(e.getRowIndex(),e.getColIndex()).getValue();
			if(obj == null){
				kdt.getCell(e.getRowIndex(),"productType").setValue(null);
				kdt.getCell(e.getRowIndex(),"modelBuild").setValue(Boolean.FALSE);
			}else{
				BuildNumberInfo buildInfo = (BuildNumberInfo)obj;
				if(buildInfo.getProductType() != null){
					
					kdt.getCell(e.getRowIndex(),"productType").setValue(ipt.getProductTypeInfo(new ObjectUuidPK(buildInfo.getProductType().getId())));
					kdt.getCell(e.getRowIndex(),"modelBuild").setValue(buildInfo.isModelBuild());
				}
			}
		}
    }
//	class MyKDTEditAdapter extends KDTEditAdapter {
//		private KDTable table;
//		public MyKDTEditAdapter() {
//		}
//    	public MyKDTEditAdapter(KDTable tab) {
//    		table = tab;
//		}
//    	public void editStopped(KDTEditEvent e) {
//    		KDTable kdt = (KDTable)e.getSource();
//    		if(e.getColIndex()==kdt.getColumnIndex("buildNumber")){
//    			Object obj = kdt.getCell(e.getRowIndex(),e.getColIndex()).getValue();
//    			if(obj == null){
//    				kdt.getCell(e.getRowIndex(),"productType").setValue(null);
//    				kdt.getCell(e.getRowIndex(),"modelBuild").setValue(Boolean.FALSE);
//    			}else{
//    				BuildNumberInfo buildInfo = (BuildNumberInfo)obj;
//    				try {
//						kdt.getCell(e.getRowIndex(),"productType").setValue(ProductTypeFactory.getRemoteInstance().getProductTypeInfo(new ObjectUuidPK(buildInfo.getProductType().getId())));
//					} catch (EASBizException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					} catch (BOSException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//    				kdt.getCell(e.getRowIndex(),"modelBuild").setValue(buildInfo.isModelBuild());
//    			}
//    		}
//    	}
//	}
	
    class MyActionListener implements ActionListener{
    	private KDTable table;
    	
    	public MyActionListener() {
		}
    	public MyActionListener(KDTable tab) {
    		table = tab;
		}
    	public void actionPerformed(ActionEvent e) {
    		String type = ((KDWorkButton)e.getSource()).getName();
    		IRow row = null;
    		if("addLine".equals(type)){
    			row = table.addRow();
    			row.getCell("modelBuild").setValue(Boolean.FALSE);
    		}else if("insetLine".equals(type)){
    			if(table.getSelectManager().size() > 0) {
    	            int top = table.getSelectManager().get().getTop();
    	            if(isTableColumnSelected(table))
    	            	row = table.addRow();
    	            else
    	            	row = table.addRow(top);
    	        } else {
    	        	row = table.addRow();
    	        }
    			row.getCell("modelBuild").setValue(Boolean.FALSE);
    		}else if("removeLine".equals(type)){
    			if(table.getSelectManager().size() == 0){
    	            MsgBox.showInfo(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
    	            return;
    	        }
    	        int top = table.getSelectManager().get().getTop();
    	        if(table.getRow(top) == null){
    	            MsgBox.showInfo(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
    	            return;
    	        }
    	        table.removeRow(top);
    		}else if("grabData".equals(type)){
    			if(getUIContext().get("newData")!=null && UIRuleUtil.getBigDecimal(getUIContext().get("newData")).compareTo(UIRuleUtil.getBigDecimal(kdtEntrys.getCell(0,"dataValue").getValue()))!=0){
    				kdtEntrys.getCell(0,"dataValue").setValue(getUIContext().get("newData"));
    				MsgBox.showInfo("提取最新数据成功！");
    			}else{
    				MsgBox.showInfo("数据已是最新！");
    			}
    		}
    	}
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	storeDataFromTable();
    	super.actionSave_actionPerformed(e);
    }
    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add("entrys.Details.id");
    	sic.add("entrys.Details.buildNumber.id");
    	sic.add("entrys.Details.buildNumber.name");
    	sic.add("entrys.Details.buildNumber.number");
    	sic.add("entrys.Details.productType.id");
    	sic.add("entrys.Details.productType.name");
    	sic.add("entrys.Details.productType.number");
    	sic.add("entrys.Details.modelBuild");
    	sic.add("entrys.Details.dataValue");
    	sic.add("entrys.Details.beizhu");
    	return sic;
    }
    
    public void actionRemove_actionPerformed(ActionEvent arg0) throws Exception {
    	super.actionRemove_actionPerformed(arg0);
    	kdtEntrys.removeRows();
    	kDTabbedPane1.removeAll();
    }
    
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillFactory.getRemoteInstance();
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
        com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillInfo objectValue = new com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setBizDate(new Date());
        Map ctx = getUIContext();
        objectValue.setDataType((BuildSplitDataType)ctx.get("dataType"));
        objectValue.setProjectName((CurProjectInfo)ctx.get("projectName"));
        objectValue.setCostAccount((CostAccountInfo)ctx.get("costAccount"));
        objectValue.setContractLevel((BuildSplitContract)ctx.get("contractLevel"));
        objectValue.setSourceNumber((String)ctx.get("sourceNumber"));
        if(BuildSplitDataType.professPoint.equals(objectValue.getDataType())){
        	CostAccountInfo costInfo = objectValue.getCostAccount();
			try {
				IProfessionPointEntry ippe = ProfessionPointEntryFactory.getRemoteInstance();
				ProfessionPointEntryCollection coll=ippe.getProfessionPointEntryCollection("select costAccount.id,costAccount.longNumber,costAccount.isLeaf,splitBuild,pointName");
				ProfessionPointEntryInfo einfo = null;
//				CostAccountInfo oldCostInfo = null;
				Map<String,Boolean> points = new HashMap<String,Boolean>();
				for(int i = coll.size()-1; i >=0; i--) {
					einfo = coll.get(i);
					if(einfo.getCostAccount().getLongNumber().equals(costInfo.getLongNumber())){
						points.put(einfo.getPointName(),einfo.isSplitBuild());
					}
				}
				if(points.size() == 0){
					String caLongNumber = costInfo.getLongNumber();
					while(points.size() == 0){
						if(caLongNumber.lastIndexOf("!") == -1)
							break;
						caLongNumber = caLongNumber.substring(0,caLongNumber.lastIndexOf("!"));
						for(int i = coll.size()-1; i >=0; i--) {
							einfo = coll.get(i);
							if(einfo.getCostAccount().getLongNumber().equals(caLongNumber)){
								points.put(einfo.getPointName(),einfo.isSplitBuild());
							}
						}
					}
				}
				String key = null;
				for(Iterator<String> it=points.keySet().iterator(); it.hasNext();) {
					key = it.next();
					BuildSplitBillEntryInfo entryInfo = new BuildSplitBillEntryInfo();
		        	entryInfo.setPointName(key);
		        	entryInfo.setSplitBuild(points.get(key));
		        	objectValue.getEntrys().add(entryInfo);
				}
			} catch (BOSException e) {
				handUIException(e);
			}
        }else{
        	BuildSplitBillEntryInfo entryInfo = new BuildSplitBillEntryInfo();
        	entryInfo.setPointName((String)ctx.get("pointName"));
        	entryInfo.setDataValue((BigDecimal)ctx.get("pointValue"));
        	entryInfo.setSplitBuild(true);
        	objectValue.getEntrys().add(entryInfo);
        }
        return objectValue;
    }

}