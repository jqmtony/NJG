/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.MaterialPromptSelector;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.MaterialConfirmBillCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.enums.EnumUtils;

/**
 * 施工单位甲供材结算台账
 */
public class MaterialSettlementRptUI extends AbstractMaterialSettlementRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialSettlementRptUI.class);
    
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	actionQuery.setVisible(false);
    	actionView.setVisible(false);
    	actionEdit.setVisible(false);
    	actionRemove.setVisible(false);
    	actionLocate.setVisible(false);
    	actionAddNew.setVisible(false);
    	FDCClientUtils.initSupplierF7(this, this.prmtPartB,SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
    	prmtPartB.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				if(eventObj.getNewValue()!=null)
					{
					 tblMain.removeRows();
					 setTableColumnFormat();
					}
			}});
    	
    	this.pkStartDate.setValue(null);
		this.pkEndDate.setValue(null);
		
		prmtContract.setEnabledMultiSelection(true);
		prmtMaterial.setEnabledMultiSelection(true);
		initEditor();
		
	}

    //added by ken_liu..
	//R121120-0153，增加物料名称、领用合同、领用日期过滤条件
    /**
	 * 用于选择领用合同的filter
	 * @return
	 */
	private FilterInfo getContractFilter() {
		FilterInfo filter = new FilterInfo();
		//当领用单位为空时，过滤出该项目下存在材料领用的合同（即，该合同关联了材料确认单）
		Set prjSet = new HashSet();
		if (getSelectObj() != null && (getSelectObj() instanceof CurProjectInfo)) {
			prjSet.add(((CurProjectInfo) getSelectObj()).getId().toString());
		}

		EntityViewInfo materialConfirmView = new EntityViewInfo();
		FilterInfo materialConfirmFilter = new FilterInfo();
		materialConfirmFilter.getFilterItems().add(new FilterItemInfo("mainContractBill.curProject.id", prjSet, CompareType.INCLUDE));
		materialConfirmView.setFilter(materialConfirmFilter);

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("mainContractBill.id");
		sic.add("mainContractBill.curProject.id");
		materialConfirmView.setSelector(sic);

		MaterialConfirmBillCollection materialConfirmbillColl = null;
		try {
			materialConfirmbillColl = MaterialConfirmBillFactory.getRemoteInstance().getMaterialConfirmBillCollection(materialConfirmView);
		} catch (BOSException e1) {
			handUIException(e1);
			SysUtil.abort();
		}

		Set prjSet2 = new HashSet();
		if (materialConfirmbillColl != null) {
			for (int i = 0; i < materialConfirmbillColl.size(); i++) {
				String conId = materialConfirmbillColl.get(i).getMainContractBill().getId().toString();
				prjSet2.add(conId);
			}
		}

		if (prjSet2.size() <= 0) {
			prjSet2.add("com.kingdee.bos.metadata.entity.EntityViewInfo");
		}
		filter.getFilterItems().add(new FilterItemInfo("id", prjSet2, CompareType.INCLUDE));
		
		if (prmtPartB.getData() != null) {
			filter.getFilterItems().add(new FilterItemInfo("partB.id", ((SupplierInfo) prmtPartB.getData()).getId()));
		} 
		return filter;
	}
    
	/**
	 * 根据选择的合同，获取用于物料的filter
	 * @param conIDSet
	 * @return
	 */
	private FilterInfo getMaterialFilterByConIDSet(Set conIDSet) {
		FilterInfo filter = new FilterInfo();

		EntityViewInfo materialConfirmView = new EntityViewInfo();
		FilterInfo materialConfirmFilter = new FilterInfo();
		materialConfirmFilter.getFilterItems().add(new FilterItemInfo("parent.mainContractBill.id", conIDSet, CompareType.INCLUDE));
		materialConfirmView.setFilter(materialConfirmFilter);

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("material.id");
		sic.add("parent.mainContractBill.id");
		materialConfirmView.setSelector(sic);

		MaterialConfirmBillEntryCollection entryColl = null;
		try {
			entryColl = MaterialConfirmBillEntryFactory.getRemoteInstance().getMaterialConfirmBillEntryCollection(materialConfirmView);
		} catch (BOSException e1) {
			handUIException(e1);
			SysUtil.abort();
		}

		Set materialIDSet = new HashSet();
		if (entryColl != null) {
			for (int i = 0; i < entryColl.size(); i++) {
				materialIDSet.add(entryColl.get(i).getMaterial().getId().toString());
			}
		}
		if (materialIDSet.size() <= 0) {
			materialIDSet.add("com.kingdee.bos.metadata.entity.EntityViewInfo");
		}
		
		filter.getFilterItems().add(new FilterItemInfo("id", materialIDSet, CompareType.INCLUDE));
		
		return filter;
	}
	
	public void initEditor() {
		//领用合同的设置
		this.prmtContract.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				prmtContract.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = prmtContract.getEntityViewInfo();
				if (view == null)
					view = new EntityViewInfo();
				
				FilterInfo filter = getContractFilter();

				view.setFilter(filter);
				prmtContract.getQueryAgent().resetRuntimeEntityView();
				prmtContract.setEntityViewInfo(view);
				
			}
		});
    	
    	IFormatter contractFormatter = new IFormatter() {
			public void applyPattern(String pattern) {
			}

			public String valueToString(Object o) {
				if (o instanceof Object[]) {
					Object[] objects = (Object[]) o;
					StringBuffer name = new StringBuffer("");

					for (int i = 0; i < objects.length; i++) {
						if (objects[i] instanceof ContractBillInfo) {
							ContractBillInfo contract = (ContractBillInfo) objects[i];
							name.append(contract.getName());
							if (i != objects.length - 1) {
								name.append(";");
							}
						}
					}
					return name.toString();
				} else if (o instanceof ContractBillInfo) {
					ContractBillInfo contract = (ContractBillInfo) o;
					return contract.getName();
				} else
					return "";
			}
    	};
		prmtContract.setDisplayFormatter(contractFormatter);
		prmtContract.setEditFormatter(contractFormatter);
    	
    	
		//物料名称的设置，，，
		this.prmtContract.setValue(null);
		this.prmtMaterial.setValue(null);
		prmtMaterial.setSelector(new MaterialPromptSelector(this)); // 使用自定义左树右表的F7 Added by owen_wen 2010-8-27
		
		this.prmtMaterial.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				CacheServiceFactory.getInstance().discardType(BOSObjectType.create("4409E7F0"));
				prmtMaterial.getQueryAgent().resetRuntimeEntityView();
				
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();

				if (prmtContract.getValue() != null) {//根据“领用合同”过滤物料（领用合同关联的材料确认单上的物料）。
					if (prmtContract.getValue() instanceof Object[]) {
						Object[] contracts = (Object[]) prmtContract.getValue();

						Set conIDSet = new HashSet();
						for (int i = 0; i < contracts.length; i++) {
							//							if (contracts[i] instanceof ContractBillInfo) {
								conIDSet.add(((ContractBillInfo) contracts[i]).getId().toString());
							//							}
						}

						filter = getMaterialFilterByConIDSet(conIDSet);
					}
				}else  {
					EntityViewInfo conView = new EntityViewInfo();
					FilterInfo conFilter = getContractFilter();
					ContractBillCollection contractColl = null;
					
					conView.setFilter(conFilter);
					try {
						contractColl = ContractBillFactory.getRemoteInstance().getContractBillCollection(conView);
					} catch (BOSException e1) {
						handUIException(e1);
						SysUtil.abort();
					}
					
					Set conIDSet = new HashSet();
					if( contractColl !=null ) {
						for( int i=0; i<contractColl.size(); i++ ) {
							conIDSet.add(contractColl.get(i).getId().toString());
						}
					}
					
					filter = getMaterialFilterByConIDSet(conIDSet);
				}

				prmtMaterial.getQueryAgent().resetRuntimeEntityView();
				view.setFilter(filter);
				SelectorItemCollection sic = new SelectorItemCollection();
				/*sic.add("id");
				sic.add("name");
				sic.add("number");
				sic.add("model");*/
				sic.add("baseUnit.name");
				sic.add("baseUnit.id");
				sic.add("*");
				view.setSelector(sic);
				prmtMaterial.setEntityViewInfo(view);
				MaterialPromptSelector selector = (MaterialPromptSelector) prmtMaterial.getSelector();
				selector.getContext().put("filter", filter);
				
				IFormatter materialFormatter = new IFormatter() {
					public void applyPattern(String pattern) {
					}

					public String valueToString(Object o) {
						if (o instanceof Object[]) {
							Object[] objects = (Object[]) o;
							StringBuffer name = new StringBuffer("");

							for (int i = 0; i < objects.length; i++) {
								if (objects[i] instanceof MaterialInfo) {
									MaterialInfo material = (MaterialInfo) objects[i];
									name.append(material.getName());
									if (i != objects.length - 1) {
										name.append(";");
									}
								}
							}
							return name.toString();
						} else if (o instanceof MaterialInfo) {
							MaterialInfo material = (MaterialInfo) o;
							return material.getName();
						} else
							return "";
					}
				};
				prmtMaterial.setDisplayFormatter(materialFormatter);
				prmtMaterial.setEditFormatter(materialFormatter);
				
			}
			
		});
    }
	
	public void actionDoQuery_actionPerformed(ActionEvent e) throws Exception {

		tblMain.removeRows();
		setTableColumnFormat();
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }

    public String precisonFormat(int precision){
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("#######.00");
    	for(int i=2;i<precision;i++){
    		sb.append("0");
    	}
    	
    	return sb.toString();
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
    		EntityViewInfo viewInfo) {
    	FilterInfo filter = new FilterInfo();
    	viewInfo.setFilter(filter);
    	if(this.getDialog()!= null && this.getDialog().getCommonFilter()!=null){
    		FilterInfo commonFilter = this.getDialog().getCommonFilter();
    		try {
    			filter.mergeFilter(commonFilter, "and");
    		} catch (BOSException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    	}
    	
    	Set prjSet = new HashSet();
    	if(getSelectObj() != null &&  (getSelectObj() instanceof CurProjectInfo)){
    		prjSet.add(((CurProjectInfo)getSelectObj()).getId().toString());
    	}
    	if(prjSet.isEmpty()){
    			filter.getFilterItems().add(new FilterItemInfo("mainContractBill.number","com.kingdee.bos.metadata.entity.EntityViewInfo"));
    	}else{
    		filter.getFilterItems().add(new FilterItemInfo("mainContractBill.curProject.id",prjSet,CompareType.INCLUDE));
    		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
    		
    		if(prmtPartB.getData() != null){
    			filter.getFilterItems().add(new FilterItemInfo("mainContractBill.partB.id",((SupplierInfo)prmtPartB.getData()).getId()));
    		}
    		
    		if (prmtContract.getValue() != null) {
				if (prmtContract.getValue() instanceof Object[]) {
					Object[] contracts = (Object[]) prmtContract.getValue();
					Set conIDSet = new HashSet();
					
					for (int i = 0; i < contracts.length; i++) {
						if (contracts[i] instanceof ContractBillInfo) {
							conIDSet.add(((ContractBillInfo) contracts[i]).getId().toString());
						}
					}
					if (conIDSet.size() <= 0) {
						conIDSet.add("com.kingdee.bos.metadata.entity.EntityViewInfo");
					}
					filter.getFilterItems().add(new FilterItemInfo("mainContractBill.id", conIDSet, CompareType.INCLUDE));
				}
			}
    		
    		if (prmtMaterial.getValue() != null) {
    			if (prmtMaterial.getValue() instanceof Object[]) {
					Object[] materials = (Object[]) prmtMaterial.getValue();
					Set materialIDSet = new HashSet();

					for (int i = 0; i < materials.length; i++) {
						if (materials[i] instanceof MaterialInfo) {
							materialIDSet.add(((MaterialInfo) materials[i]).getId().toString());
						}
					}
					if (materialIDSet.size() <= 0) {
						materialIDSet.add("com.kingdee.bos.metadata.entity.EntityViewInfo");
					}
					filter.getFilterItems().add(new FilterItemInfo("material.id", materialIDSet, CompareType.INCLUDE));
				}
			}
    		
    		if (pkStartDate.getValue() != null) {
				filter.getFilterItems().add(new FilterItemInfo("supplyDate", pkStartDate.getValue(), CompareType.GREATER_EQUALS));
			}

			if (pkEndDate.getValue() != null) {
				filter.getFilterItems().add(new FilterItemInfo("supplyDate", pkEndDate.getValue(), CompareType.LESS_EQUALS));
			}
    		
    	}
    	SorterItemCollection sc = new SorterItemCollection();
		sc.add(new SorterItemInfo("material.number"));
		sc.add(new SorterItemInfo("entrys.quantity"));
		viewInfo.setSorter(sc);
		
    	return super.getQueryExecutor(queryPK, viewInfo);
    }
    
    protected void initTable() {
    	super.initTable();
    	
    	setTableColumnFormat();
    	this.tblMain.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	this.tblMain.getColumn("avgPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	this.tblMain.getColumn("quantity").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.initSectionColumn();
    }    
   
    /**
     * output class constructor
     */
    
    public MaterialSettlementRptUI() throws Exception
    {
        super();
    }

      /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
   //        super.tblMain_tableClicked(e);
    	return;
    	
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
//        super.tblMain_tableSelectChanged(e);
    	return;
    	
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	String cuId = null;
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
    	if(node != null){
    		if(node.getUserObject() instanceof CurProjectInfo){
    			CurProjectInfo project = (CurProjectInfo) node.getUserObject();
    			if(project != null && project.getCU() != null ){
    				cuId = project.getCU().getId().toString();	
    			}
    		}else if(node.getUserObject() instanceof OrgStructureInfo){
    			OrgStructureInfo info = (OrgStructureInfo) node.getUserObject();
    			if(info.getUnit() != null){
    				FullOrgUnitInfo fou = info.getUnit();
    				if(fou.getCU() != null){
    					cuId = fou.getCU().getId().toString();
    				}
    			}
    		}
    	}
    	
    	FDCClientUtils.initSupplierF7(this, this.prmtPartB,cuId == null ? SysContext.getSysContext().getCurrentCtrlUnit().getId().toString():cuId);
    	tblMain.removeRows();
    	
        super.treeMain_valueChanged(e);
//        tblMain.refresh();
    }

	protected String getEditUIName() {
		return null;
	}
	
	protected void fillTable() throws Exception {
		super.fillTable();
		setTableColumnFormat();
	}
	
	private void  setTableColumnFormat(){
		BigDecimal amount = FDCHelper.ZERO;
		int maxPricePrecision = 2;
		for(int i =0 ;i<tblMain.getRowCount();i++){
			if(tblMain.getCell(i, "pricePrecision").getValue() != null){
				int pricePrecision = Integer.valueOf(tblMain.getCell(i, "pricePrecision").getValue().toString()).intValue();
				if(pricePrecision > maxPricePrecision){
					maxPricePrecision = pricePrecision;
				}
				this.tblMain.getCell(i, "amount").getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
				this.tblMain.getCell(i, "avgPrice").getStyleAttributes().setNumberFormat(precisonFormat(pricePrecision));
			}
			
			if(tblMain.getCell(i, "qtyPrecision").getValue() != null){
				int qtyPrecision = Integer.valueOf(tblMain.getCell(i, "qtyPrecision").getValue().toString()).intValue();
				this.tblMain.getCell(i, "quantity").getStyleAttributes().setNumberFormat(precisonFormat(qtyPrecision));
			}
			
			if(tblMain.getCell(i, "amount") != null){
				amount = FDCHelper.add(amount, FDCHelper.toBigDecimal(tblMain.getCell(i, "amount").getValue()));
			}
		}		
		
		KDTFootManager footRowManager= tblMain.getFootManager();
		IRow footRow = null;
		if(footRowManager==null){

			footRowManager = new KDTFootManager(tblMain);
			footRowManager.addFootView();
			tblMain.setFootManager(footRowManager);
			
			footRow= footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
			tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			
		}else{
			footRow=tblMain.getFootRow(0);
			if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
				footRow=tblMain.addFootRow(1);
			}
		}
		footRow.getCell(0).setValue("合计");
		footRow.getCell(0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("left"));
		footRow.getCell("amount").getStyleAttributes().setNumberFormat(precisonFormat(maxPricePrecision));
		footRow.getCell("amount").setValue(amount.toString());
	}
	
	// added by ken_liu...界面没有id~也不需提供查看单据操作
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
	}
	
	protected String getSelectedKeyValue() {
		return "";
	}
	
	/**
	 * 初始化标段列
	 * @author owen_wen 2010-12-08
	 */
	private void initSectionColumn(){
		KDComboBox sectionBox = new KDComboBox();
		sectionBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.SectionEnum").toArray());
		KDTDefaultCellEditor sectionBoxCellEditor = new KDTDefaultCellEditor(sectionBox);
		this.tblMain.getColumn("section").setEditor(sectionBoxCellEditor);
	}
}