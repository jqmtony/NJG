/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.LanguageInfo;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.util.UIHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.IAimCost;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.IProjectWithCostCenterOU;
import com.kingdee.eas.fdc.basedata.ObjectValueRenderImpl;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.IContractWithoutText;
import com.kingdee.eas.fdc.costdb.FdcMaterialFactory;
import com.kingdee.eas.fdc.costdb.IFdcMaterial;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ����:������Ŀ��ɱ����Ķ�Ӧ��ϵ���ý���
 * 
 * @author jackwang date:2006-8-7
 * @version EAS5.1
 */
public class ProjectWithCostCenterOUUI extends AbstractProjectWithCostCenterOUUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectWithCostCenterOUUI.class);
	private boolean isDetailPrjBudget=false;

	private ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection;

	/**
	 * output class constructor
	 */
	public ProjectWithCostCenterOUUI() throws Exception {
		super();
	}

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("curProject.*"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.*"));
		sic.add(new SelectorItemInfo("costCenterOU.*"));
		return sic;
	}

	public void onLoad() throws Exception {
		tblMain.checkParsed();
		//���ñ�¼table��Ԫ����ɫΪ��¼��
		this.tblMain.getColumn("project").getStyleAttributes().setBackground(tblMain.getRequiredColor());
		this.tblMain.getColumn("costCenter").getStyleAttributes().setBackground(tblMain.getRequiredColor());
		this.tblMain.getColumn("projectName").getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("costCenterName").getStyleAttributes().setLocked(true);
		super.onLoad();		
		initWorkButton();		
		loadData();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		if (this.tblMain.getRowCount() > 0) {
			this.tblMain.getSelectManager().select(0, 0);
		} else {
			this.btnSave.setEnabled(false);
		}
//		if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
//		if(!((SysContext.getSysContext().getCurrentFIUnit()!=null&&(!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
//				||(SysContext.getSysContext().getCurrentCostUnit()!=null&&SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()))){
		if(SysContext.getSysContext().getCurrentFIUnit()!=null
				&&SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()
				&&SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()
//				&&!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())
			){
			this.btnAddLine.setEnabled(true);
			this.btnDeleteLine.setEnabled(true);
			this.btnInsertLine.setEnabled(true);
			this.btnSave.setEnabled(true);
			this.menuAddLine.setEnabled(true);
			this.menuDeleteLine.setEnabled(true);
			this.menuInsertLine.setEnabled(true);
			this.menuSave.setEnabled(true);
		}else{
			this.btnAddLine.setEnabled(false);
			this.btnDeleteLine.setEnabled(false);
			this.btnInsertLine.setEnabled(false);
			this.btnSave.setEnabled(false);

			this.menuAddLine.setEnabled(false);
			this.menuDeleteLine.setEnabled(false);
			this.menuInsertLine.setEnabled(false);
			this.menuSave.setEnabled(false);
			tblMain.getColumn("project").getStyleAttributes().setLocked(true);
			tblMain.getColumn("costCenter").getStyleAttributes().setLocked(true);	
		}
//		if((SysContext.getSysContext().getCurrentFIUnit()!=null&&(!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
//				||(SysContext.getSysContext().getCurrentCostUnit()!=null&&SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())){
//			this.btnAddLine.setEnabled(true);
//			this.btnDeleteLine.setEnabled(true);
//			this.btnInsertLine.setEnabled(true);
//			this.btnSave.setEnabled(true);
//			this.menuAddLine.setEnabled(true);
//			this.menuDeleteLine.setEnabled(true);
//			this.menuInsertLine.setEnabled(true);
//			this.menuSave.setEnabled(true);
//		}else{
//			this.btnAddLine.setEnabled(false);
//			this.btnDeleteLine.setEnabled(false);
//			this.btnInsertLine.setEnabled(false);
//			this.btnSave.setEnabled(false);
//
//			this.menuAddLine.setEnabled(false);
//			this.menuDeleteLine.setEnabled(false);
//			this.menuInsertLine.setEnabled(false);
//			this.menuSave.setEnabled(false);
//		}	
//		this.tblMain.getColumn("project")
		
		tblMain.setAfterAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e) {
				isModify=true;
			}
		});
		this.menuBar.remove(this.menuHelp);
		this.menuBar.add(this.menuHelp);
	}

	/**
	 * ���ø���ť��������ͼ��״̬
	 */
	protected void initWorkButton() {
		this.actionSave.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_save"));
		this.actionAddLine.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_addline"));
		this.actionInsertLine.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_insert"));
		this.actionDeleteLine.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		// this.actionCancel.putValue(Action.SMALL_ICON,
		// EASResource.getIcon("imgTbtn_quit"));
	}

	/**
	 * �������ݡ�
	 */
	private void loadData() throws Exception {
		Locale locale = SysContext.getSysContext().getLocale();
		this.tblMain.removeRows();
		//����project�к�costCenter�е���ʾ��ʽ
//		tblMain.getColumn("project").getStyleAttributes().setNumberFormat("$longNumber$");
//		tblMain.getColumn("costCenter").getStyleAttributes().setNumberFormat("$longNumber$");
//		 ����һ��ObjectValueRender
		ObjectValueRenderImpl avr = new ObjectValueRenderImpl();
//		 ������ʾ��ʽ����ʽͬf7��
//		avr.setFormat(new BizDataFormat("$id$-$name$"));
		avr.setFormat(new BizDataFormat("$longNumber$"));
//		 ��render�󶨵����ϣ�Ҳ�ɰ󶨵��л�Ԫ���ϣ�
		tblMain.getColumn("project").setRenderer(avr);   	//�趨������Ŀ��ʾ��ʽ
		tblMain.getColumn("costCenter").setRenderer(avr);	//�趨�ɱ�������ʾ��ʽ
		KDTextField textField = new KDTextField();//���ó���
	    textField.setMaxLength(255);
		tblMain.getColumn("description").setEditor(new KDTDefaultCellEditor(textField));
		
		//��ȡ��������
		IProjectWithCostCenterOU iProjectWithCostCenterOU = ProjectWithCostCenterOUFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		evi.getSelector().add("*");
		evi.getSelector().add("curProject.*");
		evi.getSelector().add("costCenterOU.*");
		SorterItemInfo sort = new SorterItemInfo("curProject.longNumber");
		sort.setSortType(SortType.ASCEND);
		evi.getSorter().add(sort);
		
		//ֻ��ʾ��ǰ��֯�µĶ�Ӧ��ϵ
		boolean isGroup=false;
		if(!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())){
			FilterInfo filter=new FilterInfo();
			Set set=new HashSet();
			set.add(OrgConstants.SYS_CU_ID);
			set.add(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
			filter.getFilterItems().add(new FilterItemInfo("costCenterOU.CU.id",set,CompareType.INCLUDE));
			evi.setFilter(filter);
		}else{
			isGroup=true;
		}
		projectWithCostCenterOUCollection = iProjectWithCostCenterOU.getProjectWithCostCenterOUCollection(evi);
		if (projectWithCostCenterOUCollection.size() > 0) {
			IRow row;
			ProjectWithCostCenterOUInfo projectWithCostCenterOUInfo;
			ICell cellValueAttribute1, cellValueAttribute2;
			JTextField txtEnable1, txtEnable2;
			KDTDefaultCellEditor ceEnable1, ceEnable2;
			FullOrgUnitInfo fou  = null;
			///////////////////////////////////////////////////////////////////////
//			HashSet lnUps = new HashSet();		
//			EntityViewInfo evii = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
			IFdcMaterial iFdcMaterial = FdcMaterialFactory.getRemoteInstance();
			IContractBill iContractBill = ContractBillFactory.getRemoteInstance();
			IAimCost iAimCost = AimCostFactory.getRemoteInstance();
			IContractWithoutText iContractWithoutText = ContractWithoutTextFactory.getRemoteInstance();
			for (int i = 0; i < projectWithCostCenterOUCollection.size(); i++) {
				projectWithCostCenterOUInfo = projectWithCostCenterOUCollection.get(i);
				fou = projectWithCostCenterOUInfo.getCostCenterOU().castToFullOrgUnitInfo();
				//////////////////////
//				evii = new EntityViewInfo();
//				filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",fou.getId().toString()));
//				evii.setFilter(filter);			
//				CurProjectCollection cpc = CurProjectFactory.getRemoteInstance().getCurProjectCollection(evii);
//				lnUps = new HashSet();
//				for (int k = 0; k < cpc.size(); i++) {
//					lnUps.add(cpc.get(k).getId().toString());
//				}
				/**
				 * 20061229 jackwang
				 * ��������:����ɱ��������Ѿ��к�ͬ�����ı���ͬ�������Ŀ��ɱ�������Ĳ����豸������Ƴɱ������빤����Ŀ�Ķ�Ӧ��ϵ�����޸ġ�
				 */
				
				//�����豸����������Ŀ�ĳɱ�������֯�Ƿ�������
				//��ͬ���ݹ���������Ŀ�ĳɱ�������֯�Ƿ�������
				//Ŀ��ɱ�����������"��֯�򹤳���Ŀ"�Ƿ�������,���ǹ�����Ŀ,��Ҫ�жϹ�����Ŀ�ĳɱ�������֯�Ƿ�������
				//���ı���ͬ�Ĺ�����Ŀ�ĳɱ�������֯�Ƿ�������
				boolean hasRef = isGroup?true:iFdcMaterial.exists("select id where project.fullOrgUnit.id ='" + fou.getId().toString() + "'")
						||iContractBill.exists("select id where orgUnit.id ='" + fou.getId().toString() + "'")
						||iAimCost.exists("select id where orgOrProId ='" + fou.getId().toString() + "'")
						||iContractWithoutText.exists("select id where orgUnit.id ='" + fou.getId().toString() + "'");
				//���ż������õĶ����ɱ༭
				if(hasRef){//����,������Ϊ���ɱ༭����ɾ��״̬
						row = this.tblMain.addRow();
						// ���ù�����Ŀf7cell
						cellValueAttribute1 = row.getCell("project");
						
						cellValueAttribute1.getStyleAttributes().setLocked(false);
						row.getCell("project").setValue(projectWithCostCenterOUInfo.getCurProject());
						txtEnable1 = new JTextField();
						ceEnable1 = new KDTDefaultCellEditor(txtEnable1);
						ceEnable1 = new KDTDefaultCellEditor(createProjectF7());
						cellValueAttribute1.setEditor(ceEnable1);	
						// ���ù�����Ŀ����cell
						row.getCell("projectName").setValue(projectWithCostCenterOUInfo.getCurProject().getDisplayName(locale));
						// ���óɱ�����f7cell
						cellValueAttribute2 = row.getCell("costCenter");
						cellValueAttribute2.getStyleAttributes().setLocked(false);
						row.getCell("costCenter").setValue(projectWithCostCenterOUInfo.getCostCenterOU());
						txtEnable2 = new JTextField();
						ceEnable2 = new KDTDefaultCellEditor(txtEnable2);
						ceEnable2 = new KDTDefaultCellEditor(createCostCentertF7());
						cellValueAttribute2.setEditor(ceEnable2);
						// ���óɱ���������cell
						row.getCell("costCenterName").setValue(projectWithCostCenterOUInfo.getCostCenterOU().getName(locale));
						// ��������cell
						row.getCell("description").setValue(projectWithCostCenterOUInfo.getDescription(locale));
						//
						row.getCell("id").setValue(projectWithCostCenterOUInfo.getId().toString());
						row.getStyleAttributes().setLocked(true);
//						row.getCell("project").getStyleAttributes().setLocked(true);
//						row.getCell("costCenter").getStyleAttributes().setLocked(true);
					}else{
						row = this.tblMain.addRow();
						// ���ù�����Ŀf7cell
						cellValueAttribute1 = row.getCell("project");
						
						cellValueAttribute1.getStyleAttributes().setLocked(false);
						row.getCell("project").setValue(projectWithCostCenterOUInfo.getCurProject());
						txtEnable1 = new JTextField();
						ceEnable1 = new KDTDefaultCellEditor(txtEnable1);
						ceEnable1 = new KDTDefaultCellEditor(createProjectF7());
						cellValueAttribute1.setEditor(ceEnable1);	
						// ���ù�����Ŀ����cell
						row.getCell("projectName").setValue(projectWithCostCenterOUInfo.getCurProject().getDisplayName(locale));
						// ���óɱ�����f7cell
						cellValueAttribute2 = row.getCell("costCenter");
						cellValueAttribute2.getStyleAttributes().setLocked(false);
						row.getCell("costCenter").setValue(projectWithCostCenterOUInfo.getCostCenterOU());
						txtEnable2 = new JTextField();
						ceEnable2 = new KDTDefaultCellEditor(txtEnable2);
						ceEnable2 = new KDTDefaultCellEditor(createCostCentertF7());
						cellValueAttribute2.setEditor(ceEnable2);
						// ���óɱ���������cell
						row.getCell("costCenterName").setValue(projectWithCostCenterOUInfo.getCostCenterOU().getName(locale));
						// ��������cell
						row.getCell("description").setValue(projectWithCostCenterOUInfo.getDescription(locale));
						//
						row.getCell("id").setValue(projectWithCostCenterOUInfo.getId().toString());
					}				
			}	
		}		
	}

	private KDBizPromptBox createProjectF7() throws BOSException {
		KDBizPromptBox f7 = new KDBizPromptBox();
		f7.setDisplayFormat("$name$");
		f7.setEditFormat("$longNumber$");
		f7.setCommitFormat("$longNumber$");
//		f7.setEditFormat("$name$");
//		f7.setCommitFormat("$name$");
		f7.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		EntityViewInfo view=f7.getEntityViewInfo();
		if(view==null){
			view=new EntityViewInfo();
		}
		if(view.getFilter()==null)view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("fullOrgUnit.id",SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
		//�ſ���ϸ��Ŀ��������֧��Ԥ��
		if(!isDetailPrjBudget()){
			view.getFilter().appendFilterItem("level", new Integer(1));
		}
		f7.setEntityViewInfo(view);
		f7.setEditable(true);
//		
//		IProjectWithCostCenterOU iProjectWithCostCenterOU = ProjectWithCostCenterOUFactory.getRemoteInstance();
//		ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection = iProjectWithCostCenterOU.getProjectWithCostCenterOUCollection("select id ");
//		HashSet hs = new HashSet();
//		for(int i = 0;i<projectWithCostCenterOUCollection.size();i++){
//			hs.add(projectWithCostCenterOUCollection.get(i).getId().toString());
//		}
//		if(hs.size()!=0){
		
//			EntityViewInfo evi = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			if(SysContext.getSysContext().getCurrentCostUnit()!=null){
//				filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",SysContext.getSysContext().getCurrentCostUnit().getId().toString(), CompareType.EQUALS));
//			}else{
//				MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Current_HasNotCostCenter"));
//				SysUtil.abort();
//			}
//			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.valueOf(true), CompareType.EQUALS));
//			evi.setFilter(filter);
//			f7.setEntityViewInfo(evi);
		
//		}
		return f7;
	}

	private KDBizPromptBox createCostCentertF7() {
		KDBizPromptBox f7 = new KDBizPromptBox();
		if(!isDetailPrjBudget()){
			f7.setFilterInfoProducer(FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(OrgType.CostCenter));
		}
//		CostCenterF7 costCenterF7 = new CostCenterF7();
//		costCenterF7.setIsCUFilter(true);
//		setFilterInfoProducer(FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(OrgType));
//		f7.setSelector(costCenterF7);
		
		f7.setDisplayFormat("$name$");
		f7.setCommitFormat("$number$");
		f7.setEditFormat("$number$");
		f7.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
		EntityViewInfo view=f7.getEntityViewInfo();
		if(view==null){
			view=new EntityViewInfo();
		}
		if(view.getFilter()==null)view.setFilter(new FilterInfo());
		Set set=new HashSet();
		set.add(OrgConstants.SYS_CU_ID);
		set.add(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		view.getFilter().getFilterItems().add(new FilterItemInfo("CU.id",set,CompareType.INCLUDE));
		//��ӡ��Ƿ��桱�ֶι���   add by jian_cao  BT710576:�ɱ����ķ���ѡ��ɱ����ĵĵط����ܹ�ѡ�񵽷���ĳɱ�����
		view.getFilter().getFilterItems().add(new FilterItemInfo("isSealUp", Boolean.FALSE));
		f7.setEntityViewInfo(view);
		f7.setEditable(true);
		return f7;
	}
	
    /**
     * output tblMain_activeCellChanged method
     */
    protected void tblMain_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
//    	if(e.getPrevColumnIndex()==0){
//    		this.tblMain.getRow(e.getPrevRowIndex()).getCell(e.getPrevColumnIndex()).getValue()
//    	}
//    	if(e.getPrevColumnIndex()==2){
//    		
//    	}
    }
    
	/**
	 * output tblMain_editStopped method
	 */
	protected void tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		changeByEdit(e);
	}
	
	private void changeByEdit(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
		KDTable table = (KDTable) e.getSource();
		IColumn col = table.getColumn(e.getColIndex());
		IRow row = table.getRow(e.getRowIndex());
		if ((col.getKey().trim().toLowerCase().equals("project".toLowerCase()) && (row.getCell("project").getValue() != null))) {
			row.getCell("projectName").setValue(((CurProjectInfo) row.getCell("project").getValue()).getName());
		}
		if ((col.getKey().trim().toLowerCase().equals("costCenter".toLowerCase()) && (row.getCell("costCenter").getValue() != null))) {
			row.getCell("costCenterName").setValue(((CostCenterOrgUnitInfo) row.getCell("costCenter").getValue()).getName());
		}
		isModify=true;
	}

	/**
	 * output actionAddLine_actionPerformed method
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		this.tblMain.checkParsed();
		int index = -1;
		index = this.tblMain.getRowCount();
		IRow row;
		if (index != -1) {
			row = tblMain.addRow(index);
		} else {
			row = tblMain.addRow();
		}
		//
		ICell cellValueAttribute = row.getCell("project");
		cellValueAttribute.getStyleAttributes().setLocked(false);
		cellValueAttribute.setValue(null);
		JTextField txtEnable = new JTextField();
//		txtEnable.setRequestFocusEnabled(true);
//		txtEnable.getColorModel().getRed(1);
		
		KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(txtEnable);
		ceEnable = new KDTDefaultCellEditor(createProjectF7());
//		ceEnable.get
		cellValueAttribute.setEditor(ceEnable);
//		/*cellValueAttribute*/.getKDT
		//
		cellValueAttribute = row.getCell("costCenter");
		cellValueAttribute.getStyleAttributes().setLocked(false);
		cellValueAttribute.setValue(null);
		txtEnable = new JTextField();
		ceEnable = new KDTDefaultCellEditor(txtEnable);
		ceEnable = new KDTDefaultCellEditor(createCostCentertF7());
		cellValueAttribute.setEditor(ceEnable);
		//
		row.getCell("projectName").getStyleAttributes().setLocked(true);
		row.getCell("costCenterName").getStyleAttributes().setLocked(true);
		row.getCell("description").getStyleAttributes().setLocked(false);

		// ��������ݶ���ȷ���˵�ǰtblMain������,����水ť
		if (projectWithCostCenterOUCollection.size() == 0) {// ��ǰ����������Ϊ��ʱ����Ҫִ�м����,��Ϊ��ʱ���水ťĬ��Ϊ�ҵ���
			this.btnSave.setEnabled(true);
		}
	}

	/**
	 * output actionInsertLine_actionPerformed method
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		int i = -1;
		i = this.tblMain.getSelectManager().getActiveRowIndex();
		if (i == -1) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Selected_Insert"));
			return;
		}
		IRow row;
		row = tblMain.addRow(i);
		//
		ICell cellValueAttribute = row.getCell("project");
		cellValueAttribute.getStyleAttributes().setLocked(false);
		cellValueAttribute.setValue(null);
		JTextField txtEnable = new JTextField();
		KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(txtEnable);
		ceEnable = new KDTDefaultCellEditor(createProjectF7());
		cellValueAttribute.setEditor(ceEnable);
		//
		cellValueAttribute = row.getCell("costCenter");
		cellValueAttribute.getStyleAttributes().setLocked(false);
		cellValueAttribute.setValue(null);
		txtEnable = new JTextField();
		ceEnable = new KDTDefaultCellEditor(txtEnable);
		ceEnable = new KDTDefaultCellEditor(createCostCentertF7());
		cellValueAttribute.setEditor(ceEnable);
		//
		row.getCell("projectName").getStyleAttributes().setLocked(true);
		row.getCell("costCenterName").getStyleAttributes().setLocked(true);
		row.getCell("description").getStyleAttributes().setLocked(false);

	}

	/**
	 * output actionDeleteLine_actionPerformed method
	 */
	public void actionDeleteLine_actionPerformed(ActionEvent e) throws Exception {

		int i = -1;
		i = this.tblMain.getSelectManager().getActiveRowIndex();
		if (i == -1) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Selected_Delete"));
			return;
		}
		if(this.tblMain.getRow(i).getStyleAttributes().isLocked()){
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "ProjectWithCostCenter_Delete_Referenced"));
		}else{
			this.tblMain.removeRow(i);
		}
		
		// �����ǰtblMain��û���˼�¼,���ҳ�ʼ����������Ϊ��(��Ϊ��ʱ������,��������м�¼),��û�б�������,�ҵ����水ť
		if (!(this.tblMain.getRowCount() > 0) && (!(projectWithCostCenterOUCollection.size() > 0))) {
			this.btnSave.setEnabled(false);
		}

	}

	private ProjectWithCostCenterOUCollection beforeSubmit() {
		ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection = new ProjectWithCostCenterOUCollection();
		;
		if (this.tblMain.getRowCount() > 0) {
			ProjectWithCostCenterOUInfo projectWithCostCenterOUInfo;
			KDBizMultiLangBox solutionNameTextField = new KDBizMultiLangBox();
			List langList = KDBizMultiLangBox.getLanguageList();
			for (int j = 0; j < langList.size(); j++) {
				if (SysContext.getSysContext().getLocale().toString().equalsIgnoreCase(((LanguageInfo) (langList.get(j))).getLocale().toString()))
					solutionNameTextField.setSelectedLanguage((LanguageInfo) langList.get(j));
			}
			for (int i = 0; i < this.tblMain.getRowCount(); i++) {
				projectWithCostCenterOUInfo = new ProjectWithCostCenterOUInfo();
				projectWithCostCenterOUInfo.setCurProject((CurProjectInfo) this.tblMain.getRow(i).getCell("project").getValue());
				projectWithCostCenterOUInfo.setCostCenterOU((CostCenterOrgUnitInfo) this.tblMain.getRow(i).getCell("costCenter").getValue());
				if (this.tblMain.getRow(i).getCell("id").getValue() != null) {
					projectWithCostCenterOUInfo.setId(BOSUuid.read(this.tblMain.getRow(i).getCell("id").getValue().toString()));
				}
				if(this.tblMain.getRow(i).getCell("description").getValue()!=null){					
					solutionNameTextField.getEditor().setItem(this.tblMain.getRow(i).getCell("description").getValue().toString());
					UIHelper.storeMultiLangFields(solutionNameTextField, projectWithCostCenterOUInfo, "description");
				}
				projectWithCostCenterOUCollection.add(projectWithCostCenterOUInfo);
			}
		}
		return projectWithCostCenterOUCollection;
	}

	/**
	 * output actionSave_actionPerformed method
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if(verifyInput()){// ���������Ϣ
			ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection = beforeSubmit();
			IProjectWithCostCenterOU iProjectWithCostCenterOU = ProjectWithCostCenterOUFactory.getRemoteInstance();
			iProjectWithCostCenterOU.submitAll(projectWithCostCenterOUCollection);
			// �ɹ���ʾ
			MsgBox.showInfo(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Save_Successed"));
			isModify=false;
		}
	}

	/**
	 * ���������Ϣ
	 */
	private boolean verifyInput() {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			if (this.tblMain.getRow(i).getCell("project").getValue() == null) {
				MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Turn") + (i + 1)
						+ com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Line")
						+ com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Project_IsNull"));
				return false;
			} else if (this.tblMain.getRow(i).getCell("costCenter").getValue() == null) {
				MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Turn") + (i + 1)
						+ com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Line")
						+ com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostCenter_IsNull"));
				return false;
			}		
		}
		for(int i = 0;i<this.tblMain.getRowCount();i++){
			for(int j = i+1;j<this.tblMain.getRowCount();j++){
				if(((CurProjectInfo)this.tblMain.getRow(i).getCell("project").getValue()).getId().toString().equals(((CurProjectInfo)this.tblMain.getRow(j).getCell("project").getValue()).getId().toString())){
					MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "ProjectWithCostCenter_Exist"));
					return false;
				}
				if(((CostCenterOrgUnitInfo)this.tblMain.getRow(i).getCell("costCenter").getValue()).getId().toString().equals(((CostCenterOrgUnitInfo)this.tblMain.getRow(j).getCell("costCenter").getValue()).getId().toString())){
					MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "ProjectWithCostCenter_Exist"));
					return false;
				}
			}
		}
		
		if(isDetailPrjBudget()){
			/*
			 * ���Ӽ���:
			 * 0.��ϸ������Ŀ�����Ӧ����ϸ�ĳɱ�����
			 * 1.������Ŀ����֮ǰ���ϼ����뽨���˶�Ӧ��ϵ
			 * 2.������Ŀ��Ӧ�ĳɱ����ĵ��ϼ�Ϊ���ϼ�������Ŀ��Ӧ�ĳɱ�����
			 * 3.����¼���Ŀû�����ö�Ӧ��ϵ��һ��������Ŀ�����Ӧ��ʵ��ɱ�����
			 */
			for(int i = 0;i<this.tblMain.getRowCount();i++){
				CurProjectInfo curProjectInfo = (CurProjectInfo)this.tblMain.getRow(i).getCell("project").getValue();
				CostCenterOrgUnitInfo costCenterOrgUnitInfo = (CostCenterOrgUnitInfo)this.tblMain.getRow(i).getCell("costCenter").getValue();
				String msg = "��ϸ������Ŀ��Ӧ���˷���ϸ�ĳɱ����Ļ�ɱ�����û�����óɱ�����ʵ����֯!";
				if(curProjectInfo.isIsLeaf()&&!costCenterOrgUnitInfo.isIsBizUnit()){
					MsgBox.showDetailAndOK(this, msg, "��"+(i+1)+"��:\n"+msg,0);
					SysUtil.abort();
				}
				if(curProjectInfo.getLevel()>1){
					//�¼���У��
					boolean isExistParent=false;
					for(int j = 0;j<this.tblMain.getRowCount();j++){
						if(curProjectInfo.getParent().getId().toString().equals(((CurProjectInfo)this.tblMain.getRow(j).getCell("project").getValue()).getId().toString())){
							isExistParent=true;
							if(!costCenterOrgUnitInfo.getParent().getId().toString().equals(((CostCenterOrgUnitInfo)this.tblMain.getRow(j).getCell("costCenter").getValue()).getId().toString())){
								msg="������Ŀ��Ӧ�ĳɱ����ĵ��ϼ�����Ϊ���ϼ�������Ŀ��Ӧ�ĳɱ�����";
								MsgBox.showDetailAndOK(this, msg, "��"+(i+1)+"��:\n"+msg,0);
								SysUtil.abort();
							}
						}

					}
					if(!isExistParent){
						msg="������Ŀ���ϼ�û�����ö�Ӧ�ĳɱ�����";
						MsgBox.showDetailAndOK(this, msg, "��"+(i+1)+"��:\n"+msg,0);
						SysUtil.abort();
					}
				}else{
					//һ����У��:����¼���Ŀû�����ö�Ӧ��ϵ��һ��������Ŀ�����Ӧ��ʵ��ɱ�����
					boolean isExistChild=false;
					for(int j = 0;j<this.tblMain.getRowCount();j++){
						CurProjectInfo myPrj = (CurProjectInfo)this.tblMain.getRow(j).getCell("project").getValue();
						if(myPrj.getParent()!=null&&myPrj.getParent().getId().toString().equals(curProjectInfo.getId().toString())){
							isExistChild=true;
						}

					}
					if(!isExistChild&&!costCenterOrgUnitInfo.isIsBizUnit()){
						msg="�¼���Ŀû�����ö�Ӧ��ϵ��һ��������Ŀ�����Ӧ��ʵ��ɱ�����";
						MsgBox.showDetailAndOK(this, msg, "��"+(i+1)+"��:\n"+msg,0);
						SysUtil.abort();
					}
				}
				
			}

		}
		return true;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}
	
	private boolean isModify=false;
	protected boolean checkBeforeWindowClosing() {
		boolean isClose=true;
		if (isModify) {
			int ret = MsgBox.showConfirm3(this, "�������޸ģ��Ƿ񱣴棿");
			if (ret==MsgBox.YES) {
				btnSave.doClick();
			} else if(ret==MsgBox.CANCEL){
				isClose=false;
			}
		}
		return isClose;
	}

	protected void tblMain_editValueChanged(KDTEditEvent e) throws Exception {
		isModify=true;
	}
	private boolean isDetailPrjBudget(){
		isDetailPrjBudget=true;
		return isDetailPrjBudget;
	}
}
