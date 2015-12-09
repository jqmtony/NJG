/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.programming.client.CostAccountPromptBox;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitDataType;
import com.kingdee.eas.fdc.costindexdb.database.client.BuildSplitBillEditUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class BaseAndSinglePointEditUI extends AbstractBaseAndSinglePointEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(BaseAndSinglePointEditUI.class);
    private CurProjectInfo projectInfo = null;
    private boolean isSaveAction = false;
    /**
     * output class constructor
     */
    public BaseAndSinglePointEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	initKdtable();
    	reBuildCostAccount();
    	initButton();
    	projectInfo=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(editData.getProjectId()));
    }

	private void initButton() {
		actionRefix.setVisible(false);
    	actionUnAdudit.setVisible(false);
    	actionCopy.setVisible(false);
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
		//
		if(FDCBillStateEnum.AUDITTED.equals(editData.getPointBillStatus())){
        	actionSave.setEnabled(false);
        	actionSubmit.setEnabled(false);
        	actionRemove.setEnabled(false);
        	actionAudit.setEnabled(false);
        	actionEdit.setEnabled(false);
        }
	}

	private void reBuildCostAccount() {
		//rebuild costaccount
    	CostAccountPromptBox selector = new CostAccountPromptBox(this);
		KDBizPromptBox prmtCostAccount = new KDBizPromptBox() {
			protected String valueToString(Object o) {
				String str = null;
				if (o != null && o instanceof CostAccountInfo) {
					str = ((CostAccountInfo) o).getLongNumber().replace('!', '.');
				}
				return str;
			}
		};
		selector.setShowParent(false);
		prmtCostAccount.setSelector(selector);
		prmtCostAccount.setEnabledMultiSelection(false);
		prmtCostAccount.setDisplayFormat("$longNumber$");
		prmtCostAccount.setEditFormat("$longNumber$");
		prmtCostAccount.setCommitFormat("$longNumber$");
		KDTDefaultCellEditor caEditor = new KDTDefaultCellEditor(prmtCostAccount);
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", OrgConstants.DEF_CU_ID, CompareType.EQUALS));
		if(editData.getProjectId() == null)
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", "error", CompareType.EQUALS));
		else
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", editData.getProjectId(), CompareType.EQUALS));
		entityView.setFilter(filter);
		prmtCostAccount.setEntityViewInfo(entityView);
		kdtEcost.getColumn("costAccount").setEditor(caEditor);
		ObjectValueRender kdtCostEntries_costAccount_OVR = new ObjectValueRender();
		kdtCostEntries_costAccount_OVR.setFormat(new BizDataFormat("$name$"));
		kdtEcost.getColumn("costAccount").setRenderer(kdtCostEntries_costAccount_OVR);
	}
    
    public void initKdtable() throws Exception {
    	kdtEntrys.getColumn("isModel").getStyleAttributes().setLocked(true);
    	kdtEcost.getColumn("isModel").getStyleAttributes().setLocked(true);
    	kdtEntrys.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
    	kdtEcost.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
    	kdtEntrys.getColumn("pointName").setRequired(true);
    	kdtEcost.getColumn("pointName").setRequired(true);
    	kdcBase.getContentPane().remove(kdtEntrys_detailPanel);
    	kdcSingle.getContentPane().remove(kdtEcost_detailPanel);
    	kdcBase.getContentPane().add(kdtEntrys, BorderLayout.CENTER);
    	kdcSingle.getContentPane().add(kdtEcost, BorderLayout.CENTER);
		KDWorkButton addLine = new KDWorkButton("新增行");
		KDWorkButton insetLine = new KDWorkButton("插入行");
		KDWorkButton removeLine = new KDWorkButton("删除行");
		KDWorkButton importTemp = new KDWorkButton("导入模板");
		KDWorkButton singleSplit = new KDWorkButton("拆分到楼号");
		addLine.setName("addLine");
		insetLine.setName("insetLine");
		removeLine.setName("removeLine");
		importTemp.setName("importSingleTemp");
		singleSplit.setName("singleSplit");
		addLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
		insetLine.setIcon(EASResource.getIcon("imgTbtn_insert"));
		removeLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		importTemp.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));
		singleSplit.setIcon(EASResource.getIcon("imgTbtn_split"));
		kdcSingle.addButton(addLine);
		kdcSingle.addButton(singleSplit);
		kdcSingle.addButton(importTemp);
		kdcSingle.addButton(insetLine);
		kdcSingle.addButton(removeLine);
		MyActionListener baseAL = new MyActionListener(kdtEcost);
		addLine.addActionListener(baseAL);
		singleSplit.addActionListener(baseAL);
		insetLine.addActionListener(baseAL);
		removeLine.addActionListener(baseAL);
		importTemp.addActionListener(baseAL);
		KDWorkButton baseTemp = new KDWorkButton("导入模板");
		KDWorkButton baseSplit = new KDWorkButton("拆分到楼号");
		baseTemp.setName("importBaseTemp");
		baseTemp.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));
		baseSplit.setName("baseSplit");
		baseSplit.setIcon(EASResource.getIcon("imgTbtn_split"));
		kdcBase.addButton(baseSplit);
		kdcBase.addButton(baseTemp);
		MyActionListener basekdtEntrys = new MyActionListener(kdtEntrys);
		baseTemp.addActionListener(basekdtEntrys);
		baseSplit.addActionListener(basekdtEntrys);
		
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	if(OprtState.VIEW.equals(getOprtState())){
    		setButtonStatus(false);
    	}
    	for(int i = 0; i < kdtEntrys.getRowCount3(); i++) {
			if((Boolean)kdtEntrys.getCell(i,"isModel").getValue()){
				kdtEntrys.getCell(i,"isCombo").getStyleAttributes().setLocked(true);
			}
		}
		for(int i = 0; i < kdtEcost.getRowCount3(); i++) {
			if((Boolean)kdtEcost.getCell(i,"isModel").getValue()){
				kdtEcost.getCell(i,"isCombo").getStyleAttributes().setLocked(true);
			}
		}
    }
    
    private void setButtonStatus(boolean flag){
    	Object[] btns = kdcSingle.getButtons();
		for(int i = 0; i < btns.length; i++) {
			((KDWorkButton)btns[i]).setEnabled(flag);
		}
		btns = kdcBase.getButtons();
		for(int i = 0; i < btns.length; i++) {
			((KDWorkButton)btns[i]).setEnabled(flag);
		}
    }
    
    public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
    	super.actionEdit_actionPerformed(arg0);
    	setButtonStatus(true);
    }
    
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
    			row.getCell("isCombo").setValue(Boolean.FALSE);
    			row.getCell("isModel").setValue(Boolean.FALSE);
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
    			row.getCell("isCombo").setValue(Boolean.FALSE);
    			row.getCell("isModel").setValue(Boolean.FALSE);
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
    		}else if("importSingleTemp".equals(type)){
    			//单项要素的模板导入
    			UIContext uiContext = new UIContext(BaseAndSinglePointEditUI.this);
    			uiContext.put("kdtable", table);
    			IUIWindow ui = null;
				try {
					ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SingleImportUI.class.getName(),uiContext,null,OprtState.VIEW,
							WinStyle.SHOW_ONLYLEFTSTATUSBAR);
					ui.show();
				} catch (UIException e1) {
					handUIException(e1);
				}
    		}else if("importBaseTemp".equals(type)){
    			//基本要素的模板导入
    			UIContext uiContext = new UIContext(BaseAndSinglePointEditUI.this);
    			uiContext.put("kdtable", table);
    			IUIWindow ui = null;
				try {
					ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BaseImportUI.class.getName(),uiContext,null,OprtState.VIEW,
							WinStyle.SHOW_ONLYLEFTSTATUSBAR);
					ui.show();
				} catch (UIException e1) {
					handUIException(e1);
				}
    		}else if("baseSplit".equals(type)){
    			//基本要素的楼号拆分
    			if(table.getSelectManager().size() == 0){
    	            MsgBox.showInfo("请选择一条记录！");
    	            return;
    	        }
    	        int top = table.getSelectManager().get().getTop();
    	        row = table.getRow(top);
    	        if(row == null){
    	            MsgBox.showInfo("请选择一条记录！");
    	            return;
    	        }
    			if(!(Boolean)row.getCell("isCombo").getValue()){
    				MsgBox.showInfo("该项不需要拆分到楼号，如需要拆分，请修改");
    	            return;
    			}else if(FDCHelper.isEmpty(row.getCell("pointName").getValue())){
    				MsgBox.showInfo("要素不能为空！");
    	            return;
    			}else if(FDCHelper.isEmpty(row.getCell("pointValue").getValue())){
    				MsgBox.showInfo("数值不能为空！");
    	            return;
    			}
				try {
					UIContext uiContext = new UIContext(BaseAndSinglePointEditUI.this);
					IUIWindow ui = null;
					String pointName = (String)row.getCell("pointName").getValue();
					FDCSQLBuilder builder = new FDCSQLBuilder();
					builder.appendSql("select bill.fid from CT_DAT_BuildSplitBill bill left join CT_DAT_BuildSplitBillEntry entry on bill.fid=entry.fparentid ");
					builder.appendSql("where bill.CFDataType='basePoint' and entry.CFPointName='"+pointName+"' ");
					builder.appendSql("and bill.CFProjectNameID='"+projectInfo.getId().toString()+"'");
					IRowSet rs = builder.executeQuery();
					String state = null;
					if(rs.next()){
						state = OprtState.VIEW;
						uiContext.put("ID",rs.getString(1));
						uiContext.put("newData",row.getCell("pointValue").getValue());
					}else{
						uiContext.put("dataType",BuildSplitDataType.basePoint);
						uiContext.put("pointName",pointName);
						uiContext.put("projectName",projectInfo);
						uiContext.put("pointValue",row.getCell("pointValue").getValue());
						state = OprtState.ADDNEW;
					}
					ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BuildSplitBillEditUI.class.getName(),uiContext,null,state);
					ui.show();
					//判读是否已拆分，如果是，则是否需要拆分单元格不允许用户操作
					rs = builder.executeQuery();
					if(rs.next()){
						row.getCell("isModel").setValue(Boolean.TRUE);
						row.getCell("isCombo").getStyleAttributes().setLocked(true);
					}else{
						row.getCell("isModel").setValue(Boolean.FALSE);
						row.getCell("isCombo").getStyleAttributes().setLocked(false);
					}
				} catch (Exception e1) {
					handUIException(e1);
				} 
    		}else if("singleSplit".equals(type)){
    			//单项要素的楼号拆分
    			if(table.getSelectManager().size() == 0){
    	            MsgBox.showInfo("请选择一条记录！");
    	            return;
    	        }
    	        int top = table.getSelectManager().get().getTop();
    	        row = table.getRow(top);
    	        if(row == null){
    	            MsgBox.showInfo("请选择一条记录！");
    	            return;
    	        }
    	        if(!(Boolean)row.getCell("isCombo").getValue()){
    				MsgBox.showInfo("该项不需要拆分到楼号，如需要拆分，请修改");
    	            return;
    			}else if(FDCHelper.isEmpty(row.getCell("costAccount").getValue())){
    				MsgBox.showInfo("成本科目不能为空！");
    	            return;
    			}else if(FDCHelper.isEmpty(row.getCell("pointName").getValue())){
    				MsgBox.showInfo("要素不能为空！");
    	            return;
    			}else if(FDCHelper.isEmpty(row.getCell("pointValue").getValue())){
    				MsgBox.showInfo("数值不能为空！");
    	            return;
    			}
    	        try {
					UIContext uiContext = new UIContext(BaseAndSinglePointEditUI.this);
					IUIWindow ui = null;
					String pointName = (String)row.getCell("pointName").getValue();
					FDCSQLBuilder builder = new FDCSQLBuilder();
					builder.appendSql("select bill.fid from CT_DAT_BuildSplitBill bill left join CT_DAT_BuildSplitBillEntry entry on bill.fid=entry.fparentid ");
					builder.appendSql("where bill.CFDataType='singlePoint' and entry.CFPointName='"+pointName+"' ");
					builder.appendSql("and bill.CFProjectNameID='"+projectInfo.getId().toString()+"' ");
					builder.appendSql("and bill.CFCostAccountID='"+((CostAccountInfo)row.getCell("costAccount").getValue()).getId().toString()+"'");
					IRowSet rs = builder.executeQuery();
					String state = null;
					if(rs.next()){
						state = OprtState.VIEW;
						uiContext.put("ID",rs.getString(1));
						uiContext.put("newData",row.getCell("pointValue").getValue());
					}else{
						uiContext.put("dataType",BuildSplitDataType.singlePoint);
						uiContext.put("pointName",pointName);
						uiContext.put("projectName",projectInfo);
						uiContext.put("pointValue",row.getCell("pointValue").getValue());
						uiContext.put("costAccount",row.getCell("costAccount").getValue());
						state = OprtState.ADDNEW;
					}
					ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BuildSplitBillEditUI.class.getName(),uiContext,null,state);
					ui.show();
					//判读是否已拆分，如果是，则是否需要拆分单元格不允许用户操作
					rs = builder.executeQuery();
					if(rs.next()){
						row.getCell("isModel").setValue(Boolean.TRUE);
						row.getCell("isCombo").getStyleAttributes().setLocked(true);
					}else{
						row.getCell("isModel").setValue(Boolean.FALSE);
						row.getCell("isCombo").getStyleAttributes().setLocked(false);
					}
				} catch (Exception e1) {
					handUIException(e1);
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
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }



    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(!FDCBillStateEnum.SUBMITTED.equals(editData.getPointBillStatus())){
    		MsgBox.showInfo("已提交的单据才能进行审核操作！");
    		return;
    	}
        super.actionAudit_actionPerformed(e);
        editData.setPointBillStatus(FDCBillStateEnum.AUDITTED);
        loadData();
        setOprtState(OprtState.VIEW);
        actionEdit.setEnabled(false);
        actionRemove.setEnabled(false);
        MsgBox.showInfo("审核成功！");
    }
    
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	if(isSaveAction()){
    		verifyForSave();
    		return;
    	}
    	verifyForSubmit();
    }

	public boolean isSaveAction() {
		return isSaveAction;
	}

	public void setSaveAction(boolean isSaveAction) {
		this.isSaveAction = isSaveAction;
	}

	private void verifyForSave() {
		for(int i = 0; i < kdtEcost.getRowCount3(); i++) {
    		if(FDCHelper.isEmpty(kdtEcost.getCell(i,"pointName").getValue())){
    			MsgBox.showInfo("第"+(i+1)+"行要素不能为空！");
    			kdtEcost.getEditManager().editCellAt(i,kdtEcost.getColumnIndex("pointName"));
    			SysUtil.abort();
    		}
		}
    	for(int i = 0; i < kdtEntrys.getRowCount3(); i++) {
    		if(FDCHelper.isEmpty(kdtEntrys.getCell(i,"pointName").getValue())){
    			MsgBox.showInfo("第"+(i+1)+"行要素不能为空！");
    			kdtEntrys.getEditManager().editCellAt(i,kdtEntrys.getColumnIndex("pointName"));
    			SysUtil.abort();
    		}
		}
	}
    
	private void verifyForSubmit() {
		for(int i = 0; i < kdtEntrys.getRowCount3(); i++) {
			if((Boolean)kdtEntrys.getCell(i,"isCombo").getValue() && !(Boolean)kdtEntrys.getCell(i,"isModel").getValue()){
				MsgBox.showInfo("基本要素第"+(i+1)+"行需要拆分，实际未拆分！");
				SysUtil.abort();
			}
			
		}
		for(int i = 0; i < kdtEcost.getRowCount3(); i++) {
			if((Boolean)kdtEcost.getCell(i,"isCombo").getValue() && !(Boolean)kdtEcost.getCell(i,"isModel").getValue()){
				MsgBox.showInfo("单项要素第"+(i+1)+"行需要拆分，实际未拆分！");
				SysUtil.abort();
			}
			
		}
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		setSaveAction(true);
		super.actionSave_actionPerformed(e);
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		setSaveAction(false);
		super.actionSubmit_actionPerformed(e);
	}

    /**
     * output actionUnAdudit_actionPerformed
     */
    public void actionUnAdudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAdudit_actionPerformed(e);
    }

    /**
     * output actionRefix_actionPerformed
     */
    public void actionRefix_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefix_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointFactory.getRemoteInstance();
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
        BaseAndSinglePointInfo objectValue = (BaseAndSinglePointInfo)getUIContext().get("info");
        if(objectValue == null){
        	objectValue = new BaseAndSinglePointInfo();
        	objectValue.setVersion(1);
        	CurProjectInfo cpinfo = (CurProjectInfo)getUIContext().get("treeSelectedObj");
        	objectValue.setProjectName(cpinfo.getName());
        	objectValue.setProjectId(cpinfo.getId().toString());
        }else{
        	objectValue.setVersion(objectValue.getVersion()+1);
        	objectValue.setId(null);
        	objectValue.setNumber(null);
        	objectValue.setIsLatest(false);
        	objectValue.setPointBillStatus(FDCBillStateEnum.SAVED);
        	objectValue.setBizDate(new Date());
        	objectValue.setCreateTime(null);
        	objectValue.setAuditor(null);
        	objectValue.setAudiTime(null);
        	objectValue.setLastUpdateUser(null);
        	objectValue.setLastUpdateTime(null);
        	for(int i=0;i<objectValue.getEcost().size();i++){
        		objectValue.getEcost().get(i).setId(null);
			}
        	for(int i=0;i<objectValue.getEntrys().size();i++){
        		objectValue.getEntrys().get(i).setId(null);
        	}
        }
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        return objectValue;
    }

}