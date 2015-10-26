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
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.programming.client.CostAccountPromptBox;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BaseAndSinglePointEditUI extends AbstractBaseAndSinglePointEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(BaseAndSinglePointEditUI.class);
    
    /**
     * output class constructor
     */
    public BaseAndSinglePointEditUI() throws Exception
    {
        super();
    }
    
    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	initKdtable();
    	reBuildCostAccount();
    	initButton();
    }

	private void initButton() {
		actionRefix.setVisible(false);
    	actionUnAdudit.setVisible(false);
    	actionCopy.setVisible(false);
    	actionAddNew.setVisible(false);
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
		prmtCostAccount.setSelector(selector);
		prmtCostAccount.setEnabledMultiSelection(false);
		prmtCostAccount.setDisplayFormat("$longNumber$");
		prmtCostAccount.setEditFormat("$longNumber$");
		prmtCostAccount.setCommitFormat("$longNumber$");
		KDTDefaultCellEditor caEditor = new KDTDefaultCellEditor(prmtCostAccount);
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", OrgConstants.DEF_CU_ID, CompareType.EQUALS));
		entityView.setFilter(filter);
		prmtCostAccount.setEntityViewInfo(entityView);
		kdtEcost.getColumn("costAccount").setEditor(caEditor);
		ObjectValueRender kdtCostEntries_costAccount_OVR = new ObjectValueRender();
		kdtCostEntries_costAccount_OVR.setFormat(new BizDataFormat("$name$"));
		kdtEcost.getColumn("costAccount").setRenderer(kdtCostEntries_costAccount_OVR);
	}
    
    public void initKdtable() throws Exception {
    	kdtEntrys.getColumn("pointName").setRequired(true);
    	kdtEcost.getColumn("pointName").setRequired(true);
    	kdcBase.getContentPane().remove(kdtEntrys_detailPanel);
    	kdcSingle.getContentPane().remove(kdtEcost_detailPanel);
    	kdcBase.getContentPane().add(kdtEntrys, BorderLayout.CENTER);
    	kdcSingle.getContentPane().add(kdtEcost, BorderLayout.CENTER);
		KDWorkButton addLine = new KDWorkButton("������");
		KDWorkButton insetLine = new KDWorkButton("������");
		KDWorkButton removeLine = new KDWorkButton("ɾ����");
		KDWorkButton importTemp = new KDWorkButton("����ģ��");
		addLine.setName("addLine");
		insetLine.setName("insetLine");
		removeLine.setName("removeLine");
		importTemp.setName("importSingleTemp");
		addLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
		insetLine.setIcon(EASResource.getIcon("imgTbtn_insert"));
		removeLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		importTemp.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));
		kdcSingle.addButton(addLine);
		kdcSingle.addButton(importTemp);
		kdcSingle.addButton(insetLine);
		kdcSingle.addButton(removeLine);
		MyActionListener baseAL = new MyActionListener(kdtEcost);
		addLine.addActionListener(baseAL);
		insetLine.addActionListener(baseAL);
		removeLine.addActionListener(baseAL);
		importTemp.addActionListener(baseAL);
		KDWorkButton baseTemp = new KDWorkButton("����ģ��");
		baseTemp.setName("importBaseTemp");
		baseTemp.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));
		kdcBase.addButton(baseTemp);
		baseTemp.addActionListener(new MyActionListener(kdtEntrys));
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
    			UIContext uiContext = new UIContext(BaseAndSinglePointEditUI.this);
    			uiContext.put("kdtable", table);
    			IUIWindow ui = null;
				try {
					ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SingleImportUI.class.getName(),uiContext,null,OprtState.VIEW,
							WinStyle.SHOW_ONLYLEFTSTATUSBAR);
				} catch (UIException e1) {
					handUIException(e1);
				}
    			ui.show();
    		}else{
    			UIContext uiContext = new UIContext(BaseAndSinglePointEditUI.this);
    			uiContext.put("kdtable", table);
    			IUIWindow ui = null;
				try {
					ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BaseImportUI.class.getName(),uiContext,null,OprtState.VIEW,
							WinStyle.SHOW_ONLYLEFTSTATUSBAR);
				} catch (UIException e1) {
					handUIException(e1);
				}
    			ui.show();
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
    		MsgBox.showInfo("���ύ�ĵ��ݲ��ܽ�����˲�����");
    		return;
    	}
        super.actionAudit_actionPerformed(e);
        editData.setPointBillStatus(FDCBillStateEnum.AUDITTED);
        loadData();
        setOprtState(OprtState.VIEW);
        actionEdit.setEnabled(false);
        actionRemove.setEnabled(false);
        MsgBox.showInfo("��˳ɹ���");
    }
    
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	for(int i = 0; i < kdtEcost.getRowCount3(); i++) {
    		if(FDCHelper.isEmpty(kdtEcost.getCell(i,"pointName").getValue())){
    			MsgBox.showInfo("��"+(i+1)+"��Ҫ�ز���Ϊ�գ�");
    			kdtEcost.getEditManager().editCellAt(i,kdtEcost.getColumnIndex("pointName"));
    			SysUtil.abort();
    		}
		}
    	for(int i = 0; i < kdtEntrys.getRowCount3(); i++) {
    		if(FDCHelper.isEmpty(kdtEntrys.getCell(i,"pointName").getValue())){
    			MsgBox.showInfo("��"+(i+1)+"��Ҫ�ز���Ϊ�գ�");
    			kdtEntrys.getEditManager().editCellAt(i,kdtEntrys.getColumnIndex("pointName"));
    			SysUtil.abort();
    		}
		}
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