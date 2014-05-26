/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.stateManage.ObjectState;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.core.fm.ClientVerifyHelper;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.hr.emp.client.EmployeeMultiF7PromptBox;
import com.kingdee.eas.port.pm.base.ReviewerE1Collection;
import com.kingdee.eas.port.pm.base.ReviewerE1Factory;
import com.kingdee.eas.port.pm.base.ReviewerE1Info;
import com.kingdee.eas.port.pm.invest.AccredTypeEnum;
import com.kingdee.eas.port.pm.invest.IYIPlanAccredE1;
import com.kingdee.eas.port.pm.invest.ObjectStateEnum;
import com.kingdee.eas.port.pm.invest.YIPlanAccredCollection;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1Collection;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1E2Info;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1Factory;
import com.kingdee.eas.port.pm.invest.YIPlanAccredE1Info;
import com.kingdee.eas.port.pm.invest.YIPlanAccredFactory;
import com.kingdee.eas.port.pm.invest.YIPlanAccredInfo;
import com.kingdee.eas.port.pm.invest.YearInvestPlanCollection;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.eas.xr.helper.ClientVerifyXRHelper;
import com.kingdee.eas.xr.helper.PersonXRHelper;

/**
 * output class name
 */
public class YIPlanAccredEditUI extends AbstractYIPlanAccredEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(YIPlanAccredEditUI.class);
    
    /**
     * output class constructor
     */
    public YIPlanAccredEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
    	detachListeners();
        super.loadFields();
        attachListeners();
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    /**
     * output onLoad method
     */
	public void onLoad() throws Exception {
		this.setPreferredSize(new Dimension(1000,555));
		super.onLoad();
		this.actionAddNew.setVisible(false);
		contDescription.setVisible(false);
		txtDescription.setVisible(false);
		contBizDate.setVisible(false);
		pkBizDate.setVisible(false);
		contBizStatus.setVisible(false);
		comboBizStatus.setVisible(false);
		contaccredPerson.setVisible(false);
		prmtaccredPerson.setVisible(false);
		kdtE1.getColumn("seq").getStyleAttributes().setHided(true);
		kdtE2.getColumn("seq").getStyleAttributes().setHided(true);
		kdtE2.getColumn("accredDpart").getStyleAttributes().setLocked(true);
		kdtE2.getColumn("accredPerson").getStyleAttributes().setLocked(true);
		accredType.setEnabled(false);
		//当评审时，选定评审表类型
		if(OprtState.ADDNEW.equals(getOprtState()))
		{
			if(((this.editData.getDescription()!=null)?this.editData.getDescription():"").equals("初审"))
			{
				accredType.setSelectedItem(AccredTypeEnum.trial);
			}
			else if(((this.editData.getDescription()!=null)?this.editData.getDescription():"").equals("评审"))
			{
				accredType.setSelectedItem(AccredTypeEnum.accred);
			}
			else
			{
				accredType.setSelectedItem(AccredTypeEnum.approve);
			}
		}
		pkaccredDate.setEnabled(false);
		EntityViewInfo evInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		Set set = new HashSet();
		ReviewerE1Collection revE1Coll = ReviewerE1Factory.getRemoteInstance().getReviewerE1Collection();
		for (int i = 0; i < revE1Coll.size(); i++) {
			ReviewerE1Info e1Info = revE1Coll.get(i);
			set.add(e1Info.getJudges().getId().toString());
		}
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		evInfo.setFilter(filter);
		prmtaccredPerson.setEntityViewInfo(evInfo);
		
		this.kDContainer1.getContentPane().add(this.kdtE1,BorderLayout.CENTER);
		this.kDContainer2.getContentPane().add(this.kdtE2,BorderLayout.CENTER);
		initProWorkButton(this.kDContainer1, false);
		initWorkButton(this.kDContainer2, false);
		this.kDContainer1.setTitle("投资信息");
		this.kDContainer2.setTitle("评审信息");
	
		KDComboBox kdtE1_accredResu_ComboBox = new KDComboBox();
        kdtE1_accredResu_ComboBox.setName("kdtE1_accredResu_ComboBox");
        kdtE1_accredResu_ComboBox.setVisible(true);
        List list = new ArrayList();
        
        KDComboBox kdtE2_accreConclu_ComboBox = new KDComboBox();
        kdtE2_accreConclu_ComboBox.setName("kdtE2_accreConclu_ComboBox");
        kdtE2_accreConclu_ComboBox.setVisible(true);
        List listE2 = new ArrayList();
        
		if(accredType.getSelectedItem().equals(AccredTypeEnum.trial)){
			kDContainer2.getContentPane().removeAll();
			kDContainer2.getContentPane().add(kDLabelContainer1, BorderLayout.CENTER);
	        list.add(ObjectStateEnum.throughAudit);
	        list.add(ObjectStateEnum.complement);
	        list.add(ObjectStateEnum.veto);
		}
		//分录投资信息”评审结果“，分录评审信息”评审结论“枚举值设为“评审通过”，“补充完善”，“否决”
		else if(accredType.getSelectedItem().equals(AccredTypeEnum.accred))  {
			kDContainer2.getContentPane().removeAll();
			kDContainer2.getContentPane().add(kdtE2_detailPanel, BorderLayout.CENTER);
			this.kDContainer2.getContentPane().add(this.kdtE2,BorderLayout.CENTER);
			kdtE2.getColumn("seq").getStyleAttributes().setHided(true);
	        list.add(ObjectStateEnum.accredit);
	        list.add(ObjectStateEnum.complement);
	        list.add(ObjectStateEnum.veto);
	        listE2.add(ObjectStateEnum.accredit);
	        listE2.add(ObjectStateEnum.complement);
	        listE2.add(ObjectStateEnum.veto);
		}
		else {
			kDContainer2.getContentPane().removeAll();
			kDContainer2.getContentPane().add(kdtE2_detailPanel, BorderLayout.CENTER);
			this.kDContainer2.getContentPane().add(this.kdtE2,BorderLayout.CENTER);
			kdtE2.getColumn("seq").getStyleAttributes().setHided(true);
	        list.add(ObjectStateEnum.approval);
	        list.add(ObjectStateEnum.complement);
	        list.add(ObjectStateEnum.veto);
	        listE2.add(ObjectStateEnum.approval);
	        listE2.add(ObjectStateEnum.complement);
	        listE2.add(ObjectStateEnum.veto);
		}
		    kdtE1_accredResu_ComboBox.addItems(list.toArray());
	        KDTDefaultCellEditor kdtE1_accredResu_CellEditor = new KDTDefaultCellEditor(kdtE1_accredResu_ComboBox);
	        this.kdtE1.getColumn("accredResu").setEditor(kdtE1_accredResu_CellEditor);
	        
	        kdtE2_accreConclu_ComboBox.addItems(listE2.toArray());
	        KDTDefaultCellEditor kdtE2_accreConclu_CellEditor = new KDTDefaultCellEditor(kdtE2_accreConclu_ComboBox);
	        this.kdtE2.getColumn("accreConclu").setEditor(kdtE2_accreConclu_CellEditor);
	        
	}
	/**
	 * 分录“accredResu”列值改变时“projectConclude”列值默认带出“同意”
	 */
	protected void kdtE1_editStopped(KDTEditEvent e) throws Exception {
		super.kdtE1_editStopped(e);
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		String key = kdtE1.getColumnKey(colIndex);
		if(key.equals("accredResu")){
			if(UIRuleUtil.getObject(this.kdtE1.getCell(rowIndex, "accredResu").getValue()).
					equals(ObjectStateEnum.throughAudit)||UIRuleUtil.getObject(this.kdtE1.getCell(rowIndex, "accredResu").getValue()).
					equals(ObjectStateEnum.accredit)||UIRuleUtil.getObject(this.kdtE1.getCell(rowIndex, "accredResu").getValue()).
					equals(ObjectStateEnum.approval)){
	        	this.kdtE1.getCell(rowIndex,"projectConclude").setValue("同意");
	        }else{
	        	this.kdtE1.getCell(rowIndex,"projectConclude").setValue("");
	        }
		}
	}
	/**
	 * 校验分录列"projectConclude"值不能为空
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		int rowindex= 1;
		for (int i = 0; i < this.kdtE1.getRowCount(); i++) 
		{
			ClientVerifyXRHelper.verifyKDTCellNull(this, this.kdtE1, i, "accredResu");
			ObjectStateEnum objState = (ObjectStateEnum)UIRuleUtil.getObject(this.kdtE1.getCell(i, "accredResu").getValue());
			if(objState.equals(ObjectStateEnum.complement)||objState.equals(ObjectStateEnum.veto))
			{
				if(UIRuleUtil.isNull(this.kdtE1.getCell(i, "projectConclude").getValue()))
					MsgBox.showWarning("第{"+rowindex+"}行评审结果为{"+objState.getAlias()+"}的项目结论不能为空！");SysUtil.abort();
			}
			rowindex+=1;
		}
		super.verifyInput(e);
	}
	
	protected void initProWorkButton(KDContainer container, boolean flse) {
		KDWorkButton btnperson = new KDWorkButton();
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();
		btnAddRowinfo.setEnabled((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) ? true : false);
		btnInsertRowinfo.setEnabled((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) ? true : false);
		btnDeleteRowinfo.setEnabled((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) ? true : false);
		btnperson.setEnabled((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) ? true : false);
		
		btnperson.setIcon(EASResource.getIcon("imgTbtn_addline"));
		container.addButton(btnperson);
		btnperson.setText("评审人员");
		btnperson.setSize(new Dimension(140, 19));
		btnperson.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					EntityViewInfo evInfo = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					Set set = new HashSet();
					ReviewerE1Collection revE1Coll = ReviewerE1Factory.getRemoteInstance().getReviewerE1Collection();
					for (int i = 0; i < revE1Coll.size(); i++) {
						ReviewerE1Info e1Info = revE1Coll.get(i);
						set.add(e1Info.getJudges().getId().toString());
					}
					filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
					evInfo.setFilter(filter);
					EmployeeMultiF7PromptBox person = new EmployeeMultiF7PromptBox();
					person.setIsSingleSelect(false);
					person.showNoPositionPerson(false);
					person.setIsShowAllAdmin(true);
					person.setNopositionPersonFilter(filter);
					person.show();
					if(person.getData() instanceof Object[]&&((Object[]) person.getData()).length>0){
						Object[] obj = (Object[]) person.getData();
						
						KDTSelectBlock sb = null;
						int size = kdtE1.getSelectManager().size();
						if(size>0){
							for (int i = 0; i < size; i++) 
							{ 
								sb = kdtE1.getSelectManager().get(i); 
								
								for (int j = sb.getTop(); j <= sb.getBottom(); j++)  
								{  
									setKdtE2Person(obj, j);
								} 
							}
						}else{
							for (int m = 0; m < kdtE1.getRowCount(); m++) 
							{
								setKdtE2Person(obj, m);
							}
						}
						storeFields();
						loadFields();
					}
				} catch (BOSException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		if(accredType.getSelectedItem().equals(AccredTypeEnum.trial)){
			btnperson.setEnabled(false);
		}
		
		btnAddRowinfo.setIcon(EASResource.getIcon("imgTbtn_addline"));
		container.addButton(btnAddRowinfo);
		btnAddRowinfo.setText("新增分录");
		btnAddRowinfo.setSize(new Dimension(140, 19));
		btnAddRowinfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					kdtE1_detailPanel.actionAddnewLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnInsertRowinfo.setIcon(EASResource.getIcon("imgTbtn_insert"));
		container.addButton(btnInsertRowinfo);
		btnInsertRowinfo.setText("插入分录");
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		btnInsertRowinfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					kdtE1_detailPanel.actionInsertLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnDeleteRowinfo.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		container.addButton(btnDeleteRowinfo);
		btnDeleteRowinfo.setText("删除分录");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		btnDeleteRowinfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					kdtE1_detailPanel.actionRemoveLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	private void setKdtE2Person(Object obj[],int rowIndex)
	{
		YIPlanAccredE1Info enrtry1= (YIPlanAccredE1Info) kdtE1.getRow(rowIndex).getUserObject();
		List<String> list = new ArrayList<String>();
		
		for (int k = 0; k < enrtry1.getE2().size(); k++) {
			if(enrtry1.getE2().get(k).getAccredPerson()!=null)
				list.add(enrtry1.getE2().get(k).getAccredPerson().getId().toString());
		}
		
		for (int h = 0; h < obj.length; h++) 
		{	
			PersonInfo personInfo=((PersonInfo)obj[h]);
			if(list.contains(personInfo.getId().toString())){continue;}
			AdminOrgUnitInfo info =PersonXRHelper.getPosiMemByDeptUser(personInfo);
			YIPlanAccredE1E2Info e2 = new YIPlanAccredE1E2Info();
			
			e2.setAccredPerson(personInfo);
			e2.setAccredDpart(info);
        	enrtry1.getE2().add(e2);
	     }
	}
	
	protected void initWorkButton(KDContainer container, boolean flse) {
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		btnAddRowinfo.setEnabled((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) ? true : false);
		btnInsertRowinfo.setEnabled((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) ? true : false);
		btnDeleteRowinfo.setEnabled((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) ? true : false);

		btnAddRowinfo.setIcon(EASResource.getIcon("imgTbtn_addline"));
		container.addButton(btnAddRowinfo);
		btnAddRowinfo.setText("新增分录");
		btnAddRowinfo.setSize(new Dimension(140, 19));
		btnAddRowinfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					kdtE2_detailPanel.actionAddnewLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnInsertRowinfo.setIcon(EASResource.getIcon("imgTbtn_insert"));
		container.addButton(btnInsertRowinfo);
		btnInsertRowinfo.setText("插入分录");
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		btnInsertRowinfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					kdtE2_detailPanel.actionInsertLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnDeleteRowinfo.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		container.addButton(btnDeleteRowinfo);
		btnDeleteRowinfo.setText("删除分录");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		btnDeleteRowinfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					kdtE2_detailPanel.actionRemoveLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		if(accredType.getSelectedItem().equals(AccredTypeEnum.trial)){
			btnAddRowinfo.setVisible(false);
			btnInsertRowinfo.setVisible(false);
			btnDeleteRowinfo.setVisible(false);
		}
	}
	
	public void onShow() throws Exception {
		super.onShow();
	}
	/**
     * output accredType_actionPerformed method
     */
	protected void accredType_actionPerformed(ActionEvent e) throws Exception {
		super.accredType_actionPerformed(e);
		
	}
    /**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
        
        
    }
    protected void prmtaccredPerson_dataChanged(DataChangeEvent e)throws Exception {
    	super.prmtaccredPerson_dataChanged(e);

    }
    protected void kdtE1_tableClicked(KDTMouseEvent e) throws Exception {
    	super.kdtE1_tableClicked(e);
    	
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
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
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
        this.kDContainer1.removeAllButton();
        initProWorkButton(this.kDContainer1, false);
        this.kDContainer2.removeAllButton();
        initWorkButton(this.kDContainer2, false);
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
        return com.kingdee.eas.port.pm.invest.YIPlanAccredFactory.getRemoteInstance();
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
        com.kingdee.eas.port.pm.invest.YIPlanAccredInfo objectValue = new com.kingdee.eas.port.pm.invest.YIPlanAccredInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setAccredDate(new Date());
        return objectValue;
    }
	protected void attachListeners() {
		addDataChangeListener(this.prmtaccredPerson);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtaccredPerson);
	}
	protected KDTextField getNumberCtrl() {
		return null;
	}

}