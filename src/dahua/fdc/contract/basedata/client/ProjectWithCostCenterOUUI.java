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
 * 描述:工程项目与成本中心对应关系设置界面
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
		//设置必录table单元格颜色为必录项
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
	 * 设置各按钮的文字与图标状态
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
	 * 加载数据。
	 */
	private void loadData() throws Exception {
		Locale locale = SysContext.getSysContext().getLocale();
		this.tblMain.removeRows();
		//设置project列和costCenter列的显示格式
//		tblMain.getColumn("project").getStyleAttributes().setNumberFormat("$longNumber$");
//		tblMain.getColumn("costCenter").getStyleAttributes().setNumberFormat("$longNumber$");
//		 创建一个ObjectValueRender
		ObjectValueRenderImpl avr = new ObjectValueRenderImpl();
//		 设置显示格式（格式同f7）
//		avr.setFormat(new BizDataFormat("$id$-$name$"));
		avr.setFormat(new BizDataFormat("$longNumber$"));
//		 将render绑定到列上（也可绑定到行或单元格上）
		tblMain.getColumn("project").setRenderer(avr);   	//设定工程项目显示格式
		tblMain.getColumn("costCenter").setRenderer(avr);	//设定成本中心显示格式
		KDTextField textField = new KDTextField();//设置长度
	    textField.setMaxLength(255);
		tblMain.getColumn("description").setEditor(new KDTDefaultCellEditor(textField));
		
		//获取加载数据
		IProjectWithCostCenterOU iProjectWithCostCenterOU = ProjectWithCostCenterOUFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		evi.getSelector().add("*");
		evi.getSelector().add("curProject.*");
		evi.getSelector().add("costCenterOU.*");
		SorterItemInfo sort = new SorterItemInfo("curProject.longNumber");
		sort.setSortType(SortType.ASCEND);
		evi.getSorter().add(sort);
		
		//只显示当前组织下的对应关系
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
				 * 需求描述:如果成本中心下已经有合同、无文本合同、导入的目标成本表、导入的材料设备表，请控制成本中心与工程项目的对应关系不能修改。
				 */
				
				//材料设备关联工程项目的成本中心组织是否有数据
				//合同单据关联工程项目的成本中心组织是否有数据
				//目标成本的自有属性"组织或工程项目"是否有数据,若是工程项目,则要判断工程项目的成本中心组织是否有数据
				//无文本合同的工程项目的成本中心组织是否有数据
				boolean hasRef = isGroup?true:iFdcMaterial.exists("select id where project.fullOrgUnit.id ='" + fou.getId().toString() + "'")
						||iContractBill.exists("select id where orgUnit.id ='" + fou.getId().toString() + "'")
						||iAimCost.exists("select id where orgOrProId ='" + fou.getId().toString() + "'")
						||iContractWithoutText.exists("select id where orgUnit.id ='" + fou.getId().toString() + "'");
				//集团及被引用的都不可编辑
				if(hasRef){//若有,则设置为不可编辑不可删除状态
						row = this.tblMain.addRow();
						// 设置工程项目f7cell
						cellValueAttribute1 = row.getCell("project");
						
						cellValueAttribute1.getStyleAttributes().setLocked(false);
						row.getCell("project").setValue(projectWithCostCenterOUInfo.getCurProject());
						txtEnable1 = new JTextField();
						ceEnable1 = new KDTDefaultCellEditor(txtEnable1);
						ceEnable1 = new KDTDefaultCellEditor(createProjectF7());
						cellValueAttribute1.setEditor(ceEnable1);	
						// 设置工程项目名称cell
						row.getCell("projectName").setValue(projectWithCostCenterOUInfo.getCurProject().getDisplayName(locale));
						// 设置成本中心f7cell
						cellValueAttribute2 = row.getCell("costCenter");
						cellValueAttribute2.getStyleAttributes().setLocked(false);
						row.getCell("costCenter").setValue(projectWithCostCenterOUInfo.getCostCenterOU());
						txtEnable2 = new JTextField();
						ceEnable2 = new KDTDefaultCellEditor(txtEnable2);
						ceEnable2 = new KDTDefaultCellEditor(createCostCentertF7());
						cellValueAttribute2.setEditor(ceEnable2);
						// 设置成本中心名称cell
						row.getCell("costCenterName").setValue(projectWithCostCenterOUInfo.getCostCenterOU().getName(locale));
						// 设置描述cell
						row.getCell("description").setValue(projectWithCostCenterOUInfo.getDescription(locale));
						//
						row.getCell("id").setValue(projectWithCostCenterOUInfo.getId().toString());
						row.getStyleAttributes().setLocked(true);
//						row.getCell("project").getStyleAttributes().setLocked(true);
//						row.getCell("costCenter").getStyleAttributes().setLocked(true);
					}else{
						row = this.tblMain.addRow();
						// 设置工程项目f7cell
						cellValueAttribute1 = row.getCell("project");
						
						cellValueAttribute1.getStyleAttributes().setLocked(false);
						row.getCell("project").setValue(projectWithCostCenterOUInfo.getCurProject());
						txtEnable1 = new JTextField();
						ceEnable1 = new KDTDefaultCellEditor(txtEnable1);
						ceEnable1 = new KDTDefaultCellEditor(createProjectF7());
						cellValueAttribute1.setEditor(ceEnable1);	
						// 设置工程项目名称cell
						row.getCell("projectName").setValue(projectWithCostCenterOUInfo.getCurProject().getDisplayName(locale));
						// 设置成本中心f7cell
						cellValueAttribute2 = row.getCell("costCenter");
						cellValueAttribute2.getStyleAttributes().setLocked(false);
						row.getCell("costCenter").setValue(projectWithCostCenterOUInfo.getCostCenterOU());
						txtEnable2 = new JTextField();
						ceEnable2 = new KDTDefaultCellEditor(txtEnable2);
						ceEnable2 = new KDTDefaultCellEditor(createCostCentertF7());
						cellValueAttribute2.setEditor(ceEnable2);
						// 设置成本中心名称cell
						row.getCell("costCenterName").setValue(projectWithCostCenterOUInfo.getCostCenterOU().getName(locale));
						// 设置描述cell
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
		//放开明细项目的限制以支持预算
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
		//添加“是否封存”字段过滤   add by jian_cao  BT710576:成本中心封存后，选择成本中心的地方还能够选择到封存后的成本中心
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

		// 添加行数据动作确保了当前tblMain有数据,激活保存按钮
		if (projectWithCostCenterOUCollection.size() == 0) {// 当前加载数据数为零时才需要执行激活动作,因为此时保存按钮默认为灰掉的
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
		
		// 如果当前tblMain中没有了记录,并且初始加载数据数为零(不为零时允许保存,以清除所有记录),则没有保存意义,灰掉保存按钮
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
		if(verifyInput()){// 检查输入信息
			ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection = beforeSubmit();
			IProjectWithCostCenterOU iProjectWithCostCenterOU = ProjectWithCostCenterOUFactory.getRemoteInstance();
			iProjectWithCostCenterOU.submitAll(projectWithCostCenterOUCollection);
			// 成功提示
			MsgBox.showInfo(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Save_Successed"));
			isModify=false;
		}
	}

	/**
	 * 检查输入信息
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
			 * 增加检验:
			 * 0.明细工程项目必须对应到明细的成本中心
			 * 1.工程项目对照之前其上级必须建立了对应关系
			 * 2.工程项目对应的成本中心的上级为其上级工程项目对应的成本中心
			 * 3.如果下级项目没有设置对应关系则一级工程项目必须对应到实体成本中心
			 */
			for(int i = 0;i<this.tblMain.getRowCount();i++){
				CurProjectInfo curProjectInfo = (CurProjectInfo)this.tblMain.getRow(i).getCell("project").getValue();
				CostCenterOrgUnitInfo costCenterOrgUnitInfo = (CostCenterOrgUnitInfo)this.tblMain.getRow(i).getCell("costCenter").getValue();
				String msg = "明细工程项目对应到了非明细的成本中心或成本中心没有设置成本中心实体组织!";
				if(curProjectInfo.isIsLeaf()&&!costCenterOrgUnitInfo.isIsBizUnit()){
					MsgBox.showDetailAndOK(this, msg, "第"+(i+1)+"行:\n"+msg,0);
					SysUtil.abort();
				}
				if(curProjectInfo.getLevel()>1){
					//下级的校验
					boolean isExistParent=false;
					for(int j = 0;j<this.tblMain.getRowCount();j++){
						if(curProjectInfo.getParent().getId().toString().equals(((CurProjectInfo)this.tblMain.getRow(j).getCell("project").getValue()).getId().toString())){
							isExistParent=true;
							if(!costCenterOrgUnitInfo.getParent().getId().toString().equals(((CostCenterOrgUnitInfo)this.tblMain.getRow(j).getCell("costCenter").getValue()).getId().toString())){
								msg="工程项目对应的成本中心的上级必须为其上级工程项目对应的成本中心";
								MsgBox.showDetailAndOK(this, msg, "第"+(i+1)+"行:\n"+msg,0);
								SysUtil.abort();
							}
						}

					}
					if(!isExistParent){
						msg="工程项目的上级没有设置对应的成本中心";
						MsgBox.showDetailAndOK(this, msg, "第"+(i+1)+"行:\n"+msg,0);
						SysUtil.abort();
					}
				}else{
					//一级的校验:如果下级项目没有设置对应关系则一级工程项目必须对应到实体成本中心
					boolean isExistChild=false;
					for(int j = 0;j<this.tblMain.getRowCount();j++){
						CurProjectInfo myPrj = (CurProjectInfo)this.tblMain.getRow(j).getCell("project").getValue();
						if(myPrj.getParent()!=null&&myPrj.getParent().getId().toString().equals(curProjectInfo.getId().toString())){
							isExistChild=true;
						}

					}
					if(!isExistChild&&!costCenterOrgUnitInfo.isIsBizUnit()){
						msg="下级项目没有设置对应关系则一级工程项目必须对应到实体成本中心";
						MsgBox.showDetailAndOK(this, msg, "第"+(i+1)+"行:\n"+msg,0);
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
			int ret = MsgBox.showConfirm3(this, "数据已修改，是否保存？");
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
