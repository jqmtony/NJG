/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.EconomicIndexCollection;
import com.kingdee.eas.fdc.basedata.EconomicIndexFactory;
import com.kingdee.eas.fdc.basedata.EconomicIndexInfo;
import com.kingdee.eas.fdc.basedata.EconomicIndicatorsCollection;
import com.kingdee.eas.fdc.basedata.EconomicIndicatorsInfo;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.OperationPhasesInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class EconomicIndexEditUI extends AbstractEconomicIndexEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(EconomicIndexEditUI.class);
    OperationPhasesEditUI operationPhasesEditUI = null;
    
	OperationPhasesInfo operationPhasesInfo = null;
	EconomicIndexTable initTable = null;
    /**
     * output class constructor
     */
    public EconomicIndexEditUI() throws Exception
    {
        super();
    }

	protected IObjectValue createNewData() {
		EconomicIndexInfo info = new EconomicIndexInfo();
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return EconomicIndexFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		for(int i=0;i<this.toolBar.getComponentCount()-2;i++ ){
			toolBar.getComponent(i).setVisible(false);
		}
		if(this.getUIContext().get("operationPhasesInfo")!=null){
			operationPhasesInfo =(OperationPhasesInfo) getUIContext().get("operationPhasesInfo");
		}
		if(this.getUIContext().get("OperationPhasesEditUI")!=null){
			operationPhasesEditUI = (OperationPhasesEditUI) getUIContext().get("OperationPhasesEditUI");
		}
		super.onLoad();
		if(operationPhasesInfo!=null){
			editData.setHead(operationPhasesInfo);
			this.prmtOperationPhases.setValue(operationPhasesInfo);
			this.prmtProjectBase.setValue(operationPhasesInfo.getProjectBase());
			if(this.getUIContext().get("ID")!=null){
				if(this.getUIContext().get("AddNew")!=null){
					this.prmtMeasureStage.setValue(null);
				}else{
					this.prmtMeasureStage.setEnabled(false);
					this.contMeasureStage.setEnabled(false);
					this.txtVersion.setText(getVersion(editData.getVersion()));
				}
			}
		}
		prmtCreator.setValue(SysContext.getSysContext().getCurrentUserInfo());
		//src begin modified 2012-03-20 17:54
		this.btnComfire.setIcon(EASResource.getIcon("imgTbtn_submit"));
		//src end
	}
	
	private void loadEconomicEntry() throws EASBizException, BOSException{
		EconomicIndicatorsInfo economicIndicatorsInfo = null;
		EconomicIndicatorsCollection coll = editData.getIndicaEntry();
		if(coll.get(0) ==null){
			economicIndicatorsInfo = new EconomicIndicatorsInfo();
		}
		economicIndicatorsInfo=coll.get(0);
//		initTable = new EconomicIndexTable(economicIndicatorsInfo);
//		panelEntry.setPreferredSize(new Dimension(800,300));
		KDTable table  = initTable.getTable();
//		table.setBounds(new Rectangle(0, 0, panelEntry.getWidth(), panelEntry.getHeight()-10));
		this.panelEntry.add(table,new KDLayout.Constraints(0, 0, 981, 330, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
	}
	//增加分录
	public void actionAddRow_actionPerformed(ActionEvent e) throws Exception {
//		initTable.addRow(e);
	}
	//删除分录
	public void actionDelRow_actionPerformed(ActionEvent e) throws Exception {
//		initTable.deleteRow(e);
	}

    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    
    public SelectorItemCollection getSelectors() {
    	 SelectorItemCollection sic = new SelectorItemCollection();
         sic.add(new SelectorItemInfo("creator.*"));
         sic.add(new SelectorItemInfo("createTime"));
         sic.add(new SelectorItemInfo("lastUpdateUser.*"));
         sic.add(new SelectorItemInfo("lastUpdateTime"));
         sic.add(new SelectorItemInfo("projectBase.*"));
         sic.add(new SelectorItemInfo("version"));
         sic.add(new SelectorItemInfo("measureStage.*"));
         sic.add(new SelectorItemInfo("operationPhases.*"));
         sic.add(new SelectorItemInfo("indicaEntry.*"));
         sic.add(new SelectorItemInfo("indicaEntry.entrys.*"));
         sic.add(new SelectorItemInfo("indicaEntry.entrys.productType.*"));
         sic.add(new SelectorItemInfo("indicaEntry.entrys.propertyRightsNew.*"));
         return sic;
    }
    
    public void loadFields() {
    	super.loadFields();
		actionAddRow.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_addline"));
		actionDelRow.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		actionAddRow.setEnabled(true);
		actionDelRow.setEnabled(true);
		try {
			loadEconomicEntry();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    }
    //修订时变成下一个版本
    private String getVersion (String oldVersion){
    	String newVersion = oldVersion.substring(1);            
    	int Version = Double.valueOf(newVersion).intValue();
    	newVersion = String.valueOf(Version+1);
    	return "V"+newVersion+".0";
    }
    

    //校验不能为空
	protected void verifyInput(ActionEvent e) throws Exception {
		VerifyInputUtil.verifyNull(this, prmtMeasureStage, "测算阶段");
	}

	protected void prmtMeasureStage_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtMeasureStage_dataChanged(e);
		if(prmtMeasureStage.getValue()!=null){
			if(!e.getNewValue().equals(e.getOldValue())){
				getAutoVersion((MeasureStageInfo)prmtMeasureStage.getValue());
			}
		}else{
			this.txtVersion.setText("");
		}

	}
	//通过阶段测试自动得到最新版本
	private void getAutoVersion(MeasureStageInfo measureStageInfo){
		EconomicIndexCollection economicIndexSortColl = new EconomicIndexCollection();
		if(operationPhasesInfo==null){
			return;
		}
		EconomicIndexCollection economicIndexColl = operationPhasesInfo.getEconomicEntry();
		for(int i =0 ; i<economicIndexColl.size() ;i++){
			if(measureStageInfo.getId().equals(economicIndexColl.get(i).getMeasureStage().getId())){
				economicIndexSortColl.add(economicIndexColl.get(i));
			}
		}
		if(economicIndexSortColl.size()==0){
			this.txtVersion.setText("V1.0");
		}else{
//			CRMHelper.sortCollection(economicIndexSortColl, "lastUpdateTime", false);//src modified old:createTime  2012-03-29 15:02
			String versoin = economicIndexSortColl.get(0).getVersion();
			this.txtVersion.setText(getVersion(versoin));
		}
	}
	
	protected void disposeUIWindow() {
		super.disposeUIWindow();
	}
	protected void initOldData(IObjectValue dataObject) {
	}
	
	protected void btnIndexCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.disposeUIWindow();
		
	}
	protected void btnComfire_actionPerformed(ActionEvent e) throws Exception {
		int i = -1;
		if(prmtMeasureStage.getValue()==null){
			i = MsgBox.showConfirm2("阶段测试不能为空！是否不新增经济指标信息？");
		}
		if(i==2){
			return;
		}
//		if(operationPhasesEditUI.panel!=null){
//			String measureStage = ((MeasureStageInfo)prmtMeasureStage.getValue()).getName();
//			String version = txtVersion.getText();
//			if((measureStage+version).equals(operationPhasesEditUI.panel.getToolTipText())){
//				MsgBox.showWarning("请先保存"+measureStage+"的上一个版本");
//				return;
//			}
//		}
		super.disposeUIWindow();
		if(i==-1){
			editData.setProjectBase((ProjectBaseInfo)prmtProjectBase.getValue());
			editData.setVersion(this.txtVersion.getText());
			editData.setRemark(this.txtRemark.getText());
			editData.setMeasureStage((MeasureStageInfo)prmtMeasureStage.getValue());
			this.storeFields();
			editData.setId(null);
	    	EconomicIndicatorsCollection economicIndiColl = editData.getIndicaEntry();
	    	economicIndiColl.clear();
//	    	EconomicIndicatorsInfo economicIndicatorsInfo = initTable.getEconomicIndicatorsInfo();
//	    	economicIndiColl.add(economicIndicatorsInfo);
//			operationPhasesEditUI.getEconomicIndexInfo(editData);
		}
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractEconomicIndexEditUI#prmtMeasureStage_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent)
	 */
	protected void prmtMeasureStage_willCommit(CommitEvent e) throws Exception {
		super.prmtMeasureStage_willCommit(e);
		setMeasureStageF7Filter();
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractEconomicIndexEditUI#prmtMeasureStage_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent)
	 */
	protected void prmtMeasureStage_willShow(SelectorEvent e) throws Exception {
		super.prmtMeasureStage_willShow(e);
		setMeasureStageF7Filter();
	}
	
	private void setMeasureStageF7Filter() throws EASBizException, BOSException {
		if(this.getUIContext().get("AddNew")!=null){
			EntityViewInfo entityInfo = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			Set hashSet = new HashSet();
			if(operationPhasesInfo.getEconomicEntry()!=null){
				for(int i=0;i<operationPhasesInfo.getEconomicEntry().size();i++){
					EconomicIndexInfo info = (EconomicIndexInfo)operationPhasesInfo.getEconomicEntry().get(i);
					hashSet.add(info.getMeasureStage().getId().toString());
				}
			}
			//如果已经有测算阶段那么不出现
			filterInfo.getFilterItems().add(new FilterItemInfo("id", hashSet, CompareType.NOTINCLUDE));		
			entityInfo.setFilter(filterInfo);
			prmtMeasureStage.setEntityViewInfo(entityInfo);
			prmtMeasureStage.getQueryAgent().resetRuntimeEntityView();
		}
	}
}