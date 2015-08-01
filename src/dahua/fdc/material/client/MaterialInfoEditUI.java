/*
 * @(#)PartAMaterialImportorListUI.java
 * 
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.jackrabbit.uuid.UUID;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDNumberTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroupInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.MaterialIndexInfo;
import com.kingdee.eas.fdc.material.MaterialIndexValueCollection;
import com.kingdee.eas.fdc.material.MaterialIndexValueInfo;
import com.kingdee.eas.fdc.material.MaterialInfoCollection;
import com.kingdee.eas.fdc.material.MaterialInfoFactory;
import com.kingdee.eas.fdc.material.MaterialInfoInfo;
import com.kingdee.eas.fdc.material.MaterialStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.IUILifeCycleListener;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.framework.client.UILifeListenerListIdList;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * 描述：材料报价信息编辑界面<p>
 * @author 顾蛟
 * @version EAS 7.0
 * @see 
 */
public class MaterialInfoEditUI extends AbstractMaterialInfoEditUI {
	
	/**缺省版本标识*/
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = CoreUIObject
			.getLogger(MaterialInfoEditUI.class);
	/** 材料ID **/
	private String tblMaterialId = null;
	/** 材料类别ID **/
	private String materialGroupInfoID = null;
	
	/** 材料类别longNumber **/
	private String materialGroupInfoLongNumber = null;
	
	/** 指标数组 **/
	private ArrayList trait = null;
	/** 材料信息表ID **/
	private String materialInfoId = null;
	/** 资源文件路径 */
	private static final String RESOURCE_PATH = "com.kingdee.eas.fdc.material.MaterialIndexResource";
	/** 材料类别信息 **/
	private MaterialGroupInfo materialGroupInfo = null;
	/** 供应商名字 **/
	private String supplierName = null;
	/** 界面初始化时编程有关的控件的初始值数组 **/
	private Map beforeUpdate = null;
	
	boolean closeWindow = false;
	public void onLoad() throws Exception {
		super.onLoad();
		/* 获得List页面传过来的信息 */
		this.getMaterialInfomation();
		
		/* 如果材料类别ID和材料ID不为空时，把特性指标显示到界面上 */
		if(null != tblMaterialId && !"".equals(tblMaterialId)
				&& !"".equals(materialGroupInfoID) && null != materialGroupInfoID){
			/*插入新增的特性指标值*/
			if (!getOprtState().equals(OprtState.ADDNEW)) {
				insertAddMaterialIndexValueData();
			}
			/*获取特性指标数组*/
			trait = traitIndexQuery();
			/* 调整界面 */
			adjustMaterialInfoEditUI(trait);
		}
		
		/* 初始化控件 */
		this.initKDControl();
	}
	
	/**
	 * @description	插入新增的特性指标值
	 * @author			luoxiaolong
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private int insertAddMaterialIndexValueData() throws BOSException{
		
		int result = 0;
		
		List materialIndexs = this.getMaterialIndexIdByMaterialId(this.tblMaterialId);
		
		MaterialInfoCollection materialInfoCollection = this.getMaterialBaseDataByMaterialInfoId(getUIContext().get("ID").toString().trim());
		MaterialInfoInfo materialInfoInfo = materialInfoCollection.get(0);
		
		for(int m = 0; m < materialIndexs.size(); m ++){
			
			Map materialIndexMap = (Map) materialIndexs.get(m);
			
			String allMaterialIndex = materialIndexMap.get("FID").toString().trim();
			
			MaterialIndexValueCollection materialIndexValueCollection = materialInfoInfo.getIndexValue();
			
			int a = 0;
			for(int i = 0; i < materialIndexValueCollection.size(); i ++){
				MaterialIndexValueInfo materialIndexValueInfo = materialIndexValueCollection.get(i);
				MaterialIndexInfo materialIndexInfo = materialIndexValueInfo.getMaterialIndex();
				if(null != materialIndexInfo){
					String materialIndexId = String.valueOf(materialIndexInfo.getId() == null ? "" : materialIndexInfo.getId().toString().trim());
					if(allMaterialIndex.equals(materialIndexId)){
						a = 1;
						break;
					}
				}
			}
			
			if(a == 0){
				if(null != getUIContext().get("ID")){
					result = insertMaterialIndexValueData(allMaterialIndex,getUIContext().get("ID").toString().trim());
				}
			}
		}
		return result;
	}
	
	/**
	 * @description		插入特性指标的值
	 * @author			顾蛟	
	 * @createDate		2010-12-2
	 * @param			materialIndexId特性指标ID，materialInfoId材料信息表的ID
	 * @return			int 是否插入成功的状态		
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private int insertMaterialIndexValueData(String materialIndexId,String materialInfoId){
		
		/*声明一个返回值变量*/
		int result = 0;
		
		List indexParams = new ArrayList();
		
		/*插入bosID*/
		String bosID = this.getBOSID("6E5BD60C");
		indexParams.add(bosID);
		
		/*插入materialId*/
		indexParams.add(materialInfoId);
		
		/*插入materialIndexId*/
		indexParams.add(materialIndexId);
		
		/*插入特性指标值*/
		indexParams.add("");
		
		try {
			MaterialInfoFactory.getRemoteInstance().addMaterialData(indexParams, "importMaterialIndexInfoSql");
			result = 1;
		} catch (Exception e) {
			handUIException(e);
		}
		return result;
	}
	
	/**
	 * 描述：根据MaterialInfoId获得材料基础数据信息
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * @throws BOSException 
	 * 
	 * @see
	 */
	private MaterialInfoCollection getMaterialBaseDataByMaterialInfoId(String materialInfoId) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("material.*"));
		view.getSelector().add(new SelectorItemInfo("material.baseUnit.*"));
		view.getSelector().add(new SelectorItemInfo("material.materialGroup.*"));
		view.getSelector().add(new SelectorItemInfo("project.*"));
		view.getSelector().add(new SelectorItemInfo("indexValue.*"));
		view.getSelector().add(new SelectorItemInfo("indexValue.materialIndex.*"));
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", materialInfoId));
		view.setFilter(filter);
		MaterialInfoCollection mic = MaterialInfoFactory.getRemoteInstance().getMaterialInfoCollection(view);

		return mic;
	}

	/**
	 * @description
	 * @author 顾蛟
	 * @createDate 2010-11-18
	 * @param
	 * @return
	 * 
	 * @version EAS1.0
	 * @see
	 */
	public void onShow() throws Exception {
		super.onShow();
		this.txtPrice.setRequired(true);
	}

	/**
	 * output class constructor
	 */
	public MaterialInfoEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		MaterialInfo materialInfo = new MaterialInfo();
		materialInfo.setId(BOSUuid.read(this.tblMaterialId));

		editData.setMaterial(materialInfo);
		editData.setIsLatest(true);
		super.storeFields();
		/* 设置编码不能重复 */
		this.editData.setNumber(UUID.randomUUID().toString());
		/* 设置名称不能重复 */
		this.editData.setName(UUID.randomUUID().toString());
	}

	/**
	 * @description		
	 * @author			顾蛟	
	 * @createDate		2010-12-2
	 * @param	
	 * @return			Map	特性指标ID和Value键值对			
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private Map getMaterialIndexInfo() throws BOSException{
		Map map = new HashMap();
		/*获得kDPanel1上的所有组件*/
		Component[] kdPanelComponents = this.kDPanel1.getComponents();
		/*循环取类型为KDLabelContainer的组件*/
		for(int i = 0; i < kdPanelComponents.length; i ++){
			if (kdPanelComponents[i] instanceof KDLabelContainer) {
				KDLabelContainer kdLable = (KDLabelContainer)kdPanelComponents[i];
				/*获得KDLabelContainer中的所有组件*/
				Component[] kdLables = kdLable.getComponents();
				/*循环取类型 为KDTextField的所有组件*/
				for(int k = 0; k < kdLables.length; k ++){
					if(kdLables[k] instanceof KDTextField){
						KDTextField kdText = (KDTextField)kdLables[k];
						String kdName = kdText.getName();
						/*前面在动态生成特性指标的时候取得ID加到名字后面，这里通过截取字符串得到ID*/
						String materialIndexId = kdName.substring(kdName.indexOf("_") + 1);
						/*得到特性指标的值*/
						String materialIndexValue = kdText.getText().toString().trim();
						/*将特性指标ID和名字作为Map的键值对存入Map中*/
						map.put(materialIndexId, materialIndexValue);
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		
		Map indexValueMap = getMaterialIndexInfo();
		
		/* 取得指标值数组 */
//		ArrayList values = this.getTextFieldValue(trait);
		/* 获得供应商信息 */
		SupplierInfo supplierInfo = (SupplierInfo) this.prmtSupplier.getData();
		/* 判断是否获得供应商信息 */
		if (null == supplierInfo) {
			FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH,
					"pleaseChooseSupplier"));
			abort();
		}

		/* 获得供应商ID */
		String supplierId = String.valueOf(supplierInfo.getId() == null ? ""
				: supplierInfo.getId().toString().trim());
		/* 判断价格是否为空 */
		if (null == this.txtPrice.getText()
				|| "".equals(this.txtPrice.getText())) {
			FDCMsgBox.showInfo("单价不能为空");
			abort();
		}
		/* 获得价格信息 */
		BigDecimal price = this.txtPrice.getBigDecimalValue();
				
		if (price.toString().trim().length() > 12) {
			FDCMsgBox.showInfo("单价  " + EASResource.getString(RESOURCE_PATH,
			"theNumberIsTooBig"));
			abort();
		}
		
		
		editData.setPrice(null);
		editData.setPrice(price);
		/* 判断最新报价时间是否输入 */
		if (this.pkQuoteTime.getText() == null
				|| "".equals(this.pkQuoteTime.getText())) {
			FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH,
					"pleaseEnterDate"));
			abort();
		}
		/* 获得最新报价时间 */
		String pkQuoteTime = this.pkQuoteTime.getText().toString().trim();
		/*获取有效时间*/
		String pkValidTime = this.pkValidDate.getText().toString().trim();
		/*设置时间格式*/
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (pkQuoteTime != null && pkValidTime != null
				&& !pkQuoteTime.equals("") && !pkValidTime.equals("")) {
			try {
				long excelTimeL = format.parse(pkQuoteTime).getTime();
				long validateL = format.parse(pkValidTime).getTime();

				/* 有效日期不能小于报价时间 */
				if (excelTimeL > validateL) {
					FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH,
							"rightDate"));
					abort();
				}
			} catch (Exception ex) {
				handUIException(ex);
				abort();
			}
		}
		/* 如果List界面的操作状态为新增，执得以下操作 */
		if (getOprtState().equals(OprtState.ADDNEW)) {
			/* 验证供应商，价格，最新报价时间三个字段是否存在，如果不存在执得以下操作 */
			if (!this.validateRequiredField(supplierId, price, pkQuoteTime)) {

				String bUuid = MaterialInfoFactory.getRemoteInstance().getBOSID("9D390CBB");
				/* 更新materialInfo中isLatest字段为1 */
				MaterialInfoFactory.getRemoteInstance().updateIsLatest(supplierId);
				/* 插入指标值 */
				ProjectInfo projectInfo = (ProjectInfo) this.prmtProject.getData();

				/* 工程项目id */
				String projectId = null;
				if (null != projectInfo) {
					projectId = String.valueOf(projectInfo.getId() == null ? ""
							: projectInfo.getId().toString().trim());
				}

				/* 合同id */
				ContractBillInfo cBillInfo = (ContractBillInfo) this.prmtContractBill
						.getData();
				String cbillId = null;
				if (null != cBillInfo) {
					cbillId = String.valueOf(cBillInfo.getId() == null ? ""
							: cBillInfo.getId().toString().trim());
				} /* 有效无效 */
				String state = null;
				if (null != this.comboState.getSelectedItem()) {
					state = String
							.valueOf((MaterialStateEnum.VALID.toString())
									.equals(this.comboState.getSelectedItem()
											.toString().trim()) ? MaterialStateEnum.VALID_VALUE
									: MaterialStateEnum.INVALID_VALUE);
				}

				/* 有效日期 */
				String validate = null;
				if (null == this.pkValidDate.getValue()
						|| "".equals(this.pkValidDate.getValue().toString())) {
					validate = "2050-12-31 00:00:00";
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					validate = sdf.format(this.pkValidDate.getValue())
							.toString().trim();
				}

				/* 设置是否最新 */
				int isLastest = 1;
				/* 设置是否启用 */
				int isEnable = 1;
				/* 参数List */
				List params = new ArrayList();
				/* FID */
				params.add(bUuid);
				/* MaterialId */
				params.add(this.tblMaterialId);
				/* FProjectID */
				params.add(projectId);
				/* FContractBillID */
				params.add(cbillId);
				/* FPrice */
				params.add(price);
				/* FQuoteTime */
				params.add(pkQuoteTime);
				/* SupplierId */
				params.add(supplierId);
				
				/* 存入创建人ID */
				/* userId */
				params.add(SysContext.getSysContext().getCurrentUserInfo().getId().toString());
				/* FCreateTime */
				params.add(new SimpleDateFormat("yyyy-MM-dd")
						.format(FDCDateHelper.getServerTimeStamp()));
				/* 有效时间 */
				params.add(validate);
				/* 是否最新 */
				params.add(new Integer(isLastest));
				/* 是否启用 */
				params.add(new Integer(isEnable));
				/* 空 */
				params.add("");
				/* state初始状态 */
				params.add("1SAVED");
				/* mstate初始状态 */
				params.add(state);
				/*控制单元*/
				String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString().trim();
    			params.add(cuId);
    			/*存入组织单元*/
    			String orgUnitId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString().trim();
    			params.add(orgUnitId);
				
				try {
					MaterialInfoFactory.getRemoteInstance().addMaterialData(params, "importMaterialInfoSql");
					
					List materialIndexs = this.getMaterialIndexIdByMaterialId(this.tblMaterialId);
										
					for(int m = 0; m < materialIndexs.size(); m ++){
						
						Map materialIndexMap = (Map) materialIndexs.get(m);
						
						String allMaterialIndex = materialIndexMap.get("FID").toString().trim();
						
						List indexParams = new ArrayList();
						
						/*插入bosID*/
						String bosID = this.getBOSID("6E5BD60C");
						indexParams.add(bosID);
						
						/*插入materialId*/
    					indexParams.add(bUuid);
    					
    					/*插入materialIndexId*/
    					indexParams.add(allMaterialIndex);
    					
    					Iterator iterator = indexValueMap.keySet().iterator();
    					
    					/*插入指标值*/
						String materialIndexValue = "";
						
						/*循环插入指标值*/
						while(iterator.hasNext()){

							String materialIndexId = iterator.next().toString().trim();
							
							if(materialIndexId.equals(allMaterialIndex)){
								materialIndexValue = indexValueMap.get(materialIndexId).toString().trim();
								break ;
							}
						}
						
						indexParams.add(materialIndexValue);
						MaterialInfoFactory.getRemoteInstance().addMaterialData(indexParams, "importMaterialIndexInfoSql");
					}
					/*清空特性指标数据*/
					Component[] kdPanelComponents = this.kDPanel1.getComponents();
					for(int i = 0; i < kdPanelComponents.length; i ++){
						if (kdPanelComponents[i] instanceof KDLabelContainer) {
							KDLabelContainer kdLable = (KDLabelContainer)kdPanelComponents[i];
							Component[] kdLables = kdLable.getComponents();
							for(int k = 0; k < kdLables.length; k ++){
								if(kdLables[k] instanceof KDTextField){
									KDTextField kdText = (KDTextField)kdLables[k];
									kdText.setText("");
								}
							}
						}
					}
					
					FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH,
							"saveSuccessful"));
					
					/*刷新主界面*/
					refreshMaterialInfoUI();
					
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					handUIException(ex);
					abort();
				}

				// super.actionSave_actionPerformed(e);
				/* 判断材料类别信息是否为空 */
				if (getUIContext().get("materialNodeInfo") != null) {
					materialGroupInfo = (MaterialGroupInfo) getUIContext().get(
							"materialNodeInfo");
				}
				/* 新增以后清空界面 */
				this.prmtMaterial.setValue(materialGroupInfo.getDisplayName());
				this.prmtContractBill.setValue(null);
				this.prmtSupplier.setValue(null);
				this.prmtProject.setValue(null);
				this.txtPrice.setText(null);
				this.pkValidDate.setValue(null);
				this.clearMaterialIndexValue();
			} else {
				FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH,
						"thisMessageWasExist"));
			}

			/* 新增后设置状态为新增 */
			this.setOprtState(OprtState.ADDNEW);
			beforeUpdate = this.getContent();
		} else if (getOprtState().equals(OprtState.EDIT)) {
			//增加update的剃重方法
			/* 获取输入的特性指标值 */
			if (trait != null && !this.judgeIsRepeatData(supplierName, String.valueOf(this.txtPrice.getValue() == null ? "0" : this.txtPrice.getValue().toString().trim()), pkQuoteTime)) {
				for (int i = 0; i < trait.size(); i++) {
					MaterialIndexValueBean mivbBean = (MaterialIndexValueBean) trait
							.get(i);
					String id = mivbBean.getMaterialIndexValueId();
					Component[] components = this.kDPanel1.getComponents();
					String fValue = null;
					for (int j = 0; j < components.length; j++) {
						if (null != components[j]
								&& components[j] instanceof KDLabelContainer) {
							KDLabelContainer kdlContainer = (KDLabelContainer) components[j];
							Component[] comps = kdlContainer.getComponents();

							for (int k = 0; k < comps.length; k++) {
								if (null != comps[k]
										&& comps[k] instanceof KDTextField
										&& comps[k].getName().equals(
												"textField_" + mivbBean.getId())) {
									KDTextField kdField = (KDTextField) comps[k];
									if (kdField.getText() != null) {
										fValue = kdField.getText();
									} else {
										fValue = "";
									}
								}
							}
						}
					}
					if (null != fValue) {
						MaterialInfoFactory.getRemoteInstance()
								.updateTraitIndexValue(id, fValue);
					} else {
						FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH,
								"traitIndexisNotFound"));
					}
				}
				super.actionSave_actionPerformed(e);
				
				beforeUpdate = this.getContent();
				
				/*刷新主界面*/
				refreshMaterialInfoUI();
			}else{
				FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH,"thisMessageWasExist"));
				abort();
			}
		}		
	}

	
	 /**
	* 描述：<根据bosType，获得BOSID>
	* @author <luoxiaolong>
	* @param  <bosType>
	* @return  <String>
	* 创建时间  <2010/11/16> <p>
	* 
	* 修改人：<修改人> <p>
	* 修改时间：<yyyy/mm/dd> <p>
	* 修改描述：<修改描述> <p>
	*
	* @see  <相关的类>
	*/
	public String getBOSID(String bosType){
		
		/**声明一个返回值变量*/
		String bosID = null;
		
		try {
			bosID = MaterialInfoFactory.getRemoteInstance().getBOSID(bosType);
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIException(e);
		}
		
		return bosID;		
	}
		
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		this.setOprtState(OprtState.EDIT);
		
		/**存入数据是否被修改*/
		boolean boo = true;
		boo = checkContentUpdate();
		/*退出界面时判断内容是否有更改*/
		if(!boo){
			/*清空值*/
			clearMaterialIndexValue();	
		}else{
			int isExit = FDCMsgBox.showConfirm2("数据已经修改，是否保存！");
			
			/*判断是否需要退出*/
			if(isExit == 0){
				SupplierInfo supplierInfo = (SupplierInfo)this.prmtSupplier.getData();
				String supplierId = String.valueOf(supplierInfo == null ? "" : supplierInfo.getId().toString().trim());
				String quoteTime = String.valueOf(pkQuoteTime.getValue() == null ? "" : pkQuoteTime.getValue().toString().trim());
				if("".equals(quoteTime)){
					FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH,"pleaseEnterDate"));
					return ;
				}
				/*若该条信息已存在，就给出提示，否则就直接保存*/
				if (this.validateRequiredField(supplierId, txtPrice.getBigDecimalValue(), pkQuoteTime.getText().trim())) {
					FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH,"thisMessageWasExist"));
					this.setOprtState(OprtState.EDIT);
					return ;
				}else{
					this.actionSave_actionPerformed(null);
					
					/*刷新主界面*/
					refreshMaterialInfoUI();
				}
			}
		}
		
		this.setOprtState(OprtState.ADDNEW);
		
		/*清空值*/
		clearMaterialIndexValue();
		
		beforeUpdate = this.getContent();
	}
	
	/**
	 * 清空特性指标值
	 * @throws BOSException 
	 */
	private void clearMaterialIndexValue() throws BOSException{
		this.prmtProject.setValue(null);
		this.prmtContractBill.setValue(null);
		this.prmtSupplier.setValue(null);
		this.txtPrice.setValue(null);
		this.pkValidDate.setValue(Timestamp.valueOf("2050-12-31 00:00:00"));
		this.pkQuoteTime.setValue(null);
		this.comboState.setSelectedIndex(0);

		/*清空特性指标数据*/
		Component[] kdPanelComponents = this.kDPanel1.getComponents();
		for(int i = 0; i < kdPanelComponents.length; i ++){
			if (kdPanelComponents[i] instanceof KDLabelContainer) {
				KDLabelContainer kdLable = (KDLabelContainer)kdPanelComponents[i];
				Component[] kdLables = kdLable.getComponents();
				for(int k = 0; k < kdLables.length; k ++){
					if(kdLables[k] instanceof KDTextField){
						KDTextField kdText = (KDTextField)kdLables[k];
						kdText.setText("");
					}
				}
			}
		}
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		boolean flag = this.getMaterialInfoState();
		if (!flag) {
			super.actionEdit_actionPerformed(e);
			ArrayList kdl = new ArrayList();
			Component[] c = kDPanel1.getComponents();
			Component[] tx = null;
			new ArrayList();
			for (int i = 0; i < c.length; i++) {
				if (c[i] instanceof KDLabelContainer) {
					kdl.add((KDLabelContainer) c[i]);
				}
			}
			for (int j = 0; j < kdl.size(); j++) {
				KDLabelContainer kContainer = (KDLabelContainer) kdl.get(j);
				tx = kContainer.getComponents();
				for (int l = 0; l < tx.length; l++) {
					if (tx[l] instanceof KDTextField) {
						// textfield.add((KDTextField) tx[l]);
						((KDTextField) tx[l]).setEditable(true);
					}
				}
			}
		}
		this.prmtContractBill.setEditable(false);
		this.prmtProject.setEditable(false);
		this.prmtSupplier.setEditable(false);
		this.btnRemove.setEnabled(true);
		this.btnAddNew.setEnabled(true);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		boolean flag = this.getMaterialInfoState();
		if (!flag && materialInfoId != null) {
			if(!judgeRecordExist()){
				SysUtil.abort();
			}
			if (FDCMsgBox.showConfirm2(EASResource.getString(RESOURCE_PATH,
					"doYouWantToDelete")) == 0) {
				MaterialInfoFactory.getRemoteInstance()
						.deleteMaterialInfoRecord(
								new ObjectUuidPK(BOSUuid.read(materialInfoId)));
				FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH, "delete"));
			} else {
				SysUtil.abort();
			}
		}
		/*清空*/
		this.clearMaterialIndexValue();
		
		/*刷新主界面*/
		refreshMaterialInfoUI();
	}

	/**
	 * 判断该记录是否存在
	 */
	private boolean judgeRecordExist(){
		Map filterMap = new HashMap();
		filterMap.put("FID", materialInfoId);
		List list = new ArrayList();
		list.add("FID");
		try {
			List materialInfos = MaterialInfoFactory.getRemoteInstance().getMaterialData(filterMap, "T_MTR_MaterialInfo", list);
			if(materialInfos.size() == 0){
				return false;
			}
		} catch (Exception e) {
			handUIException(e);
		}
		return true;
	}
	
	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MaterialInfoFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected IObjectValue createNewData() {
		MaterialInfoInfo info = new MaterialInfoInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		return info;
	}
	
	/**
	 * @description 
	 *              从T_MTR_MaterialIndex和T_MTR_MaterialIndexValue表得到特性指标，并返回特性指标数组
	 * @author 顾蛟
	 * @createDate 2010-11-14
	 * @param
	 * @return
	 * 
	 * @version EAS1.0
	 * @throws BOSException
	 * @throws SQLException
	 * @see
	 */
	private ArrayList traitIndexQuery() throws BOSException, UuidException,
			SQLException, EASBizException {

		ArrayList thisMaterialTraitIndex = new ArrayList();// 定义特性指标实体数组

		/*新增*/
		if (getOprtState().equals(OprtState.ADDNEW)) {
			IRowSet rowSet;
			try {
				rowSet = MaterialInfoFactory.getRemoteInstance().selectTraitAndSuperIndex(materialGroupInfoLongNumber);
				/* 将特性指标值和名字封装到实体中并将实体加到数组中*/
				while (rowSet.next()) {
					/* 定义用于封装特性指标及其值的实体*/
					MaterialIndexValueBean mivbBean = new MaterialIndexValueBean();
					/* 从T_MTR_MaterialIndex中得到该特性指标值*/
					String indexID = rowSet.getString("FID");
					/* 得到特性指标的名字*/
					String indexName = rowSet.getString("FName_l2");
					/* 将ID和名字封装到实体中*/
					mivbBean.setId(indexID);
					mivbBean.setName(indexName);
					/* 将实体加到数组中*/
					thisMaterialTraitIndex.add(mivbBean);
				}
			} catch (EASBizException e) {
				logger.error(e.getMessage());
				handUIException(e);
			}
		} else {
			/*修改、查看*/
			if(this.materialInfoId != null){
				MaterialInfoCollection materialInfoCollection = this.getMaterialBaseDataByMaterialInfoId(getUIContext().get("ID").toString().trim());
				MaterialInfoInfo materialInfoInfo = materialInfoCollection.get(0);
				MaterialIndexValueCollection materialIndexValueCollection = materialInfoInfo.getIndexValue();
				for(int i = 0; i < materialIndexValueCollection.size(); i ++){
					MaterialIndexValueInfo materialIndexValueInfo = materialIndexValueCollection.get(i);
					MaterialIndexValueBean mivbBean = new MaterialIndexValueBean();
					MaterialIndexInfo materialIndexInfo = materialIndexValueInfo.getMaterialIndex();
					if(null != materialIndexInfo){
						if(materialIndexInfo.isIsEnabled()){
							mivbBean.setMaterialIndexValueId(materialIndexValueInfo.getId().toString().trim());
							mivbBean.setId(materialIndexInfo.getId().toString());
							mivbBean.setName(materialIndexInfo.getName().toString());
							mivbBean.setValue(materialIndexValueInfo.getValue());
							thisMaterialTraitIndex.add(mivbBean);
						}
					}
				}
			}
		}
		return thisMaterialTraitIndex;
	}

	/**
	 * 描述：<根据物料id，获得特性指标id>
	 * <p>
	 * 重新修改了该方法的实现 By Owen_Wen 2011-02-24
	 * 
	 * @author <luoxiaolong>
	 * @param <materialGruopId>
	 * @return <null> 创建时间 <2010/11/16>
	 *         <p>
	 * 
	 *         修改人：<修改人>
	 *         <p>
	 *         修改时间：<yyyy/mm/dd>
	 *         <p>
	 *         修改描述：<修改描述>
	 *         <p>
	 * 
	 * @see <相关的类>
	 */
	private List getMaterialIndexIdByMaterialId(String materialId) {
		List resultList = new ArrayList();
		try {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("materialGroup.longNumber"));
			MaterialInfo materialInfo = MaterialFactory.getRemoteInstance().getMaterialInfo(new ObjectUuidPK(materialId), sic);
			IRowSet rowSet = MaterialInfoFactory.getRemoteInstance().selectTraitAndSuperIndex(materialInfo.getMaterialGroup().getLongNumber());
				while (rowSet.next()) {
				/* 从T_MTR_MaterialIndex中得到该特性指标ID */
				String indexID = rowSet.getString("FID");
				Map tempMap = new HashMap();
				tempMap.put("FID", indexID);
				resultList.add(tempMap);
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage(), e);
			e.printStackTrace();
		} catch (EASBizException e) {
			logger.debug(e.getMessage(), e);
			e.printStackTrace();
		} catch (BOSException e) {
			logger.debug(e.getMessage(), e);
			e.printStackTrace();
		}

		return resultList;
	}
	
	/**
     * 描述：<根据物料id，获得物料类型>
     * @param  <materialId>
     * @return <String>
	 * @throws <> 
     */
	public String getMaterialGroupIdByMaterialId(Map filterMap,String tableName,String fieldName){
		List fieldList = new ArrayList();
		fieldList.add(fieldName);
		String materialGroupId = null;
		try{
			List materialGroupList = MaterialInfoFactory.getRemoteInstance().getMaterialData(filterMap, tableName, fieldList);
			if(null != materialGroupList && materialGroupList.size() > 0){
				Map materialGroupMap = (Map) materialGroupList.get(0);
				materialGroupId = materialGroupMap.get(fieldName).toString();
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			handUIException(e);
		}
		return materialGroupId;
	}
	
	/**
	 * @description 根据材料特性指标动态生成界面
	 * @author 顾蛟
	 * @createDate 2010-11-14
	 * @param
	 * @return
	 * 
	 * @version EAS1.0
	 * @see
	 */
	/* traitIndex为从traitIndexQuery方法返回的特性指标数组*/
	protected void adjustMaterialInfoEditUI(ArrayList traitIndex) {
		int traitIndexCount = 0;
		MaterialIndexValueBean mivb = null;
		/* 判断得到的特性指标数组是否为空*/
		if (traitIndex != null) {
			traitIndexCount = traitIndex.size();
		} else {
			return;
		}
		/* 加载特性指标到界面上*/
		for (int i = 0; i < traitIndexCount; i++) {
			/* 创建KDLabelContainer容器*/
			KDLabelContainer traitIndexLabel = new KDLabelContainer();
			/* 创建文本框*/
			KDTextField tx = new KDTextField();
			if (traitIndex.get(i) != null) {
				/* 得到封装指标及其值的实体*/
				mivb = (MaterialIndexValueBean) traitIndex.get(i);
			}
			/* 设置KDLabelContainer控件的名字*/
			traitIndexLabel.setName("traitIndex_" + mivb.getId());
			/* 设置KDLabelContainer显示的名称*/
			traitIndexLabel.setBoundLabelText(mivb.getName());
			/* 设置宽度*/
			traitIndexLabel.setBoundLabelLength(100);
			/*设置是否有下划线。（跟界面上其他控件保持一致）*/
			traitIndexLabel.setBoundLabelUnderline(true);
			tx.setName("textField_" + mivb.getId());// 设置文本框的名字
			tx.setMaxLength(1024);
			if (getOprtState().equals(OprtState.VIEW)) {
				tx.setEditable(false);
			}
			/* 设置文本框的内容。内容从实体中取得*/
			tx.setText(mivb.getValue());
			traitIndexLabel.setBoundEditor(tx);
			/* 特性指标索引为偶数时加到界面右边列下面*/
			if (i % 2 == 0) {
				traitIndexLabel.setBounds(new Rectangle(this.model.getX(),
						this.model.getY() + (i / 2 + 1) * 35, 240, 20));
				kDPanel1.add(traitIndexLabel, new KDLayout.Constraints(
						this.model.getX(),
						this.model.getY() + (i / 2 + 1) * 35, 240, 20, 0));
				/* 特性指标索引为奇数时加到界面左边列下面*/
			} else {
				traitIndexLabel
						.setBounds(new Rectangle(this.contName.getX(),
								this.contName.getY() + ((i - 1) / 2 + 1) * 35,
								240, 20));
				kDPanel1.add(traitIndexLabel, new KDLayout.Constraints(
						this.contName.getX(), this.contName.getY()
								+ ((i - 1) / 2 + 1) * 35, 240, 20, 0));
			}
		}
	}

	/**
	 * @description 获取材料类别等界面所需的初始值
	 * @author 顾蛟
	 * @createDate 2010-11-14
	 * @param
	 * @return
	 * 
	 * @version EAS1.0
	 * @throws BOSException 
	 * @see
	 */
	private void getMaterialInfomation() throws BOSException {
		if (null != getUIContext().get("ID")) {			
			if (!getOprtState().equals(OprtState.ADDNEW)) {
				MaterialInfoCollection materialInfoCollection = this.getMaterialBaseDataByMaterialInfoId(getUIContext().get("ID").toString().trim());
				MaterialInfoInfo materialInfoInfo = materialInfoCollection.get(0);
				MaterialInfo materialInfo = materialInfoInfo.getMaterial();
				MeasureUnitInfo materialUnit = materialInfo.getBaseUnit();
				MaterialIndexValueCollection materialIndexValueCollection = materialInfoInfo.getIndexValue();
				materialGroupInfoID = materialInfo.getMaterialGroup().getId().toString().trim();
				materialGroupInfoLongNumber = materialInfo.getMaterialGroup().getLongNumber();
				String materialGroupDisplayName = materialInfo.getMaterialGroup().getDisplayName();
				materialInfoId = materialInfoInfo.getId().toString().trim();
				tblMaterialId = materialInfo.getId().toString();
				
				/*设置材料类别初始值*/
				this.prmtMaterial.setValue(materialGroupDisplayName);
				/*设置编码初始值*/
				this.txtNumber.setValue(materialInfo.getNumber());
				/*设置材料名称初始值*/
				this.txtName.setValue(materialInfo.getName());
				/*设置规格型号初始值*/
				this.KDModel.setValue(materialInfo.getModel());
				/*设置单位初始值*/
				this.KDUnit.setValue(materialUnit.getDisplayName());
				DecimalFormat df = new DecimalFormat("###.00");
				String price = df.format(materialInfoInfo.getPrice());
				this.txtPrice.setValue(materialInfoInfo.getPrice());
				this.pkQuoteTime.setValue(materialInfoInfo.getQuoteTime());
			}else{
				MaterialGroupInfo materialGroupInfo = (MaterialGroupInfo) getUIContext().get("materialNodeInfo");
				/*设置材料类别初始值*/
				this.prmtMaterial.setValue(materialGroupInfo.getDisplayName());
				/*设置编码初始值*/
				this.txtNumber.setValue(getUIContext().get("tblNumberValue"));
				/*设置材料名称初始值*/
				this.txtName.setValue(getUIContext().get("tblNameValue"));
				/*设置规格型号初始值*/
				this.KDModel.setValue(getUIContext().get("tblModelValue"));
				/*设置单位初始值*/
				this.KDUnit.setValue(getUIContext().get("tblUnitValue")); 
				materialGroupInfoID = materialGroupInfo.getId().toString().trim();
				materialGroupInfoLongNumber = materialGroupInfo.getLongNumber();
				tblMaterialId = getUIContext().get("tblMaterialId").toString();
			}
		}
	}

	/**
	 * @description 校验供应商，最新报价时间，单价信息是否已存在
	 * @author 顾蛟
	 * @createDate 2010-11-16
	 * @param	supplier供应商ID，price报价，pkQuoteTime最新报价时间
	 * @return	boolean返回验证是否存在该条记录
	 * 
	 * @version EAS1.0
	 * @see
	 */
	protected boolean validateRequiredField(String supplier, BigDecimal price,
			String pkQuoteTime) {
 		if (supplier != null && price != null && pkQuoteTime != null
				&& tblMaterialId != null) {
			try {
				String[] quotes = pkQuoteTime.split("-");
				String newQuoteTime = "";
				/*日期格式转换*/
				for(int i = 0; i < quotes.length; i ++){
					if(i == quotes.length -1){
						newQuoteTime += Integer.parseInt(quotes[i]) + "";
					}else{
						newQuoteTime += Integer.parseInt(quotes[i]) + "-";
					}
				}
				/*若返回rowSet2不为空，表明该条记录已存在*/
				IRowSet rowSet2 = MaterialInfoFactory.getRemoteInstance()
						.requiredFields(supplier, price, newQuoteTime,
								tblMaterialId);
				if (rowSet2 == null) {
					return false;
				}
			} catch (Exception e) {
				handUIException(e);
			}

		}
		return true;
	}

	/**
	 * @description 根据界面判断是否有相同的数据
	 * @author 顾蛟
	 * @createDate 2010-11-16
	 * @param
	 * @return
	 * 
	 * @version EAS1.0
	 * @see
	 */
	private boolean judgeIsRepeatData(String supplier, String price,
			String pkQuoteTime){
		if(null == getUIContext().get("materialInfoId")){
			return false;
		}
		materialInfoId = getUIContext().get("materialInfoId").toString();
		/*在父UI右下角表中，若没有数据就不存在重复数据*/
		if(null == getUIContext().get("materialInfoTable")){
			return false;
		}
		
		/**获得父UI右下角表中的数据*/
		KDTable materialInfoTable = (KDTable)getUIContext().get("materialInfoTable");
		
		/*循环判断数据是否重复*/
		for(int i = 0; i < materialInfoTable.getRowCount(); i ++){
			/**获得价格*/
			String pPriceString = "0";
			if(null != materialInfoTable.getRow(i) && null != materialInfoTable.getRow(i).getCell("price") && null != materialInfoTable.getRow(i).getCell("price").getValue()){
				pPriceString = materialInfoTable.getRow(i).getCell("price").getValue().toString();
			}
			double pPriceDouble = Double.parseDouble(pPriceString);
			double sPriceDouble = Double.parseDouble(price);
			/**获得报价时间*/
			String pQuoteTimeString = materialInfoTable.getRow(i).getCell("quoteTime").getValue().toString();
			if(pQuoteTimeString.indexOf(" ") != -1){
				pQuoteTimeString = pQuoteTimeString.substring(0,pQuoteTimeString.indexOf(" "));
			}
			/**获得供应商*/
			String pSupString = String.valueOf(materialInfoTable.getRow(i).getCell("supplier").getValue() == null ? "" : materialInfoTable.getRow(i).getCell("supplier").getValue().toString());
			String sSupString = this.prmtSupplier.getText().toString().trim();
			
			/**获得id*/
			String pMaterialInfoId = materialInfoTable.getRow(i).getCell("id").getValue().toString();  
			if(pPriceDouble == sPriceDouble && pQuoteTimeString.equals(pkQuoteTime) && pSupString.equals(sSupString)){
				if(!materialInfoId.equals(pMaterialInfoId)){
//					MsgBox.showInfo("该信息已存在！");
					return true;
				}
			}
			
		}
		return false;
	}
	
	
	/**
	 * @description 控件初始化
	 * @author 顾蛟
	 * @createDate 2010-11-16
	 * @param
	 * @return
	 * 
	 * @version EAS1.0
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @see
	 */
	private void initKDControl() throws EASBizException, BOSException {
		/*设置工具栏按扭状态*/
		this.btnAuditResult.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnAddNew.setEnabled(true);
		this.btnRemove.setEnabled(true);
		this.btnAudit.setVisible(true);
		this.btnRemove.setEnabled(true);
		this.btnAudit.setVisible(false);
		
		/* 材料类别控件绑定--材料信息*/			
		this.prmtMaterial.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialGroupQuery");
		this.prmtMaterial.setEnabled(false);
		
		/*材料编码控件绑定--材料信息*/
		this.txtNumber.setQueryInfo("com.kingdee.eas.st.basedata.app.MaterialCodeQuery");
		this.txtNumber.setEnabled(false);
		
		/* 材料名称控件绑定--材料信息*/			
		this.txtName.setQueryInfo("com.kingdee.eas.fdc.material.app.F7FDCMaterialQuery");
		this.txtName.setEnabled(false);
		
		/* 规格型号控件绑定--材料信息*/
		this.KDModel.setQueryInfo("com.kingdee.eas.fdc.material.app.F7MaterialModeQuery");
		this.KDModel.setEnabled(false);
		
		/* 单位控件绑定--材料信息*/
		this.KDUnit.setEnabled(false);
		
		
		/* 项目名称控件绑定--报价信息*/
		this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		this.prmtProject.setEditable(true);
		
		/* 合同名称控件绑定--报价信息*/
		this.prmtContractBill.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillF7Query");
		this.prmtContractBill.setEditable(true);
		this.prmtContractBill.setDisplayFormat("$name$");
		this.prmtContractBill.setEditFormat("$name$");
		this.prmtContractBill.setCommitFormat("$name$");
		
		/* 供应商控件绑定--报价信息*/
		this.prmtSupplier.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");
		this.prmtSupplier.setEditable(true);
		
		/* 状态--报价信息*/
		this.comboState.setEditable(true);
		
		/* 报价时间--报价信息*/
		this.pkQuoteTime.setEditable(true);
		
		/* 有效日期--报价信息*/
		this.pkValidDate.setEditable(true);

		/* 单价--报价信息*/
		this.txtPrice.setEditable(true);
		
		/*新增、修改、查看状态时各控件的状态*/
		if (getOprtState().equals(OprtState.VIEW)) {
			/*设置是否可编辑--报价信息*/
			this.prmtProject.setEditable(false);
			this.prmtContractBill.setEditable(false);
			this.prmtSupplier.setEditable(false);
			this.comboState.setEditable(false);
			this.pkQuoteTime.setEditable(false);
			this.txtPrice.setEditable(false);
			
			/*设置工具栏按扭状态*/
			this.btnAddNew.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.btnAudit.setVisible(false);
			
			/*判断 采购组织 实体  或  虚体*/
			if(null != this.getUIContext().get("isViewEnbled") && !((Boolean)this.getUIContext().get("isViewEnbled")).booleanValue()){
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.btnViewSignature.setEnabled(false);
			}
			
			/*审核状态*/
			boolean flag = this.getMaterialState();
			if(flag){
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
			}else{
				this.btnEdit.setEnabled(true);
			}
			
			this.beforeUpdate = this.getContent();
		}else if(getOprtState().equals(OprtState.ADDNEW)){
			/*设置工具栏按扭状态*/
			this.btnAddNew.setEnabled(true);
			
			/*设置报价信息初始状态*/
			this.pkValidDate.setValue(Timestamp.valueOf("2050-12-31 00:00:00"));
			this.comboState.setSelectedIndex(0);
			
			this.prmtProject.setEditable(false);
			this.prmtContractBill.setEditable(false);
			this.prmtSupplier.setEditable(false);
			this.pkQuoteTime.setEditable(false);
			this.pkValidDate.setEditable(false);
			this.comboState.setEditable(false);
			this.btnRemove.setEnabled(false);
			
			this.beforeUpdate = this.getContent();
		}else{
			/*设置工具栏按扭状态*/
			this.actionEdit.setEnabled(false);
			this.actionAddNew.setEnabled(true);
			this.btnEdit.setEnabled(false);	
			
			this.beforeUpdate = this.getContent();
		}

		/*从供应商库进入、设置控件状态*/
		if(getOprtState().equals("FINDVIEW")){
			this.actionEdit.setEnabled(false);
			this.actionAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
		}
	}

	protected KDTextField getNumberCtrl() {
		KDTextField kf = new KDTextField();
		kf.setText(UUID.randomUUID().toString());
		return kf;
	}

	protected KDBizMultiLangBox getNameCtrl() {

		KDBizMultiLangBox kl = new KDBizMultiLangBox();
		kl.setToolTipText(UUID.randomUUID().toString());
		return kl;
	}

	/**
	 * @description		退出界面时刷新主界面	
	 * @author					
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public boolean destroyWindow() {
		
		/**存入数据是否被修改*/
		boolean boo = true;
		try {
			boo = checkContentUpdate();
		} catch (BOSException e) {
			handUIException(e);
		}
		
		/*退出界面时判断内容是否有更改*/
		if(!boo){
			try{
				disposeUIWindow();
				return false;
			}catch(Exception e){
				handUIException(e);
			}
		}
		
		int isExit = FDCMsgBox.showConfirm3("数据已经修改，是否保存并退出！");
		
		/*判断是否需要退出*/
		if(isExit == 0){
			try {
				/*保存*/
				this.actionSave_actionPerformed(null);
				
				/*关闭窗体*/
				disposeUIWindow();
				
				/*刷新主界面*/
				refreshMaterialInfoUI();
			} catch (Exception e) {
				handUIException(e);
			}
		}else if(isExit == 1){

			/*关闭窗体*/
			disposeUIWindow();
			
			/*刷新主界面*/
			refreshMaterialInfoUI();
		}else{
			/*刷新主界面*/
			refreshMaterialInfoUI();
		}
		
		return true;
	}
	
	/**
	 * @description		刷新主界面
	 * @author					
	 * @createDate			2010-12-2
	 * @param				
	 * @return						
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void refreshMaterialInfoUI(){
		MaterialInfoUI materialInfoUI = null;
		 Object ui = getUIContext().get("Owner");
		 IUILifeCycleListener uiLifeListener = new UILifeListenerListIdList(idList, this);
	        if(ui instanceof ListUI)
	        {
	            ListUI listui = (ListUI)getUIContext().get("Owner");
	            if(ui != null && uiLifeListener != null)
	                listui.getUILifeCycleHandler().removeRuleListener(uiLifeListener);
	        }
	        if(ui instanceof MaterialInfoUI){
	        	materialInfoUI = (MaterialInfoUI)ui;
	        	try {
					materialInfoUI.refreahAfterAuditAndUnAudit(null);
				} catch (Exception e) {
					handUIException(e);
				}
	        }
	}
	
	/**
	 * @description		得到特性指标值	
	 * @author					
	 * @createDate		2010-12-2
	 * @param			traitIndex特性指标数组
	 * @return			ArrayList		
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	protected ArrayList getTextFieldValue(ArrayList traitIndex) {

		ArrayList kdl = new ArrayList();
		ArrayList textfield = new ArrayList();
		Component[] c = kDPanel1.getComponents();
		Component[] tx = null;
		MaterialIndexValueBean mivb = null;
		ArrayList values = new ArrayList();
		for (int i = 0; i < c.length; i++) {
			if (c[i] instanceof KDLabelContainer) {
				kdl.add((KDLabelContainer) c[i]);
			}
		}
		for (int j = 0; j < kdl.size(); j++) {
			KDLabelContainer kContainer = (KDLabelContainer) kdl.get(j);
			tx = kContainer.getComponents();
			for (int l = 0; l < tx.length; l++) {
				if (tx[l] instanceof KDTextField) {
					textfield.add((KDTextField) tx[l]);
				}
			}
		}
		for (int k = 0; k < textfield.size(); k++) {
			mivb = (MaterialIndexValueBean) traitIndex.get(k);
			mivb.setValue(((KDTextField) textfield.get(k)).getText());
			values.add(mivb);
		}
		return values;
	}
	/**
	 * @description		得到材料信息表状态	
	 * @author					
	 * @createDate		2010-12-2
	 * @param			
	 * @return			boolean		
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private boolean getMaterialInfoState() throws EASBizException, BOSException {
		if (materialInfoId != null) {
			try {
				MaterialInfoInfo info = (MaterialInfoInfo) MaterialInfoFactory
						.getRemoteInstance().getValue(
								new ObjectUuidPK(BOSUuid.read(materialInfoId)));
				if (info.getState().equals(FDCBillStateEnum.AUDITTED)) {
					FDCMsgBox.showWarning(this, EASResource.getString(
							RESOURCE_PATH, "cannotDothis"));
					return true;
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				handUIException(e);
				return false;
			}
		}
		return false;
	}
	/**
	 * @description		得到材料状态	
	 * @author					
	 * @createDate		2010-12-2
	 * @param			
	 * @return			boolean		
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private boolean getMaterialState() throws EASBizException, BOSException {
		if (materialInfoId != null) {
			try {
				MaterialInfoInfo info = (MaterialInfoInfo) MaterialInfoFactory
						.getRemoteInstance().getValue(
								new ObjectUuidPK(BOSUuid.read(materialInfoId)));
				if (info.getState().equals(FDCBillStateEnum.AUDITTED)) {
					return true;
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				handUIException(e);
				return false;
			}
		}
		return false;
	}
	
	/**
	 * @description		检查页面内容是否被更改	
	 * @author					
	 * @createDate			2010-12-2
	 * @param			
	 * @return				boolean		
	 *	
	 * @version			EAS7.0
	 * @throws 				BOSException 
	 * @see						
	 */
	private boolean checkContentUpdate() throws BOSException{
		Map afterUpdate = this.getContent();
		
		int result = 0;
		
		if(null == beforeUpdate){
			return false;
		}
		
		/*获得kDPanel1上的所有组件*/
		Component[] kdPanel1Components = this.kDPanel1.getComponents();
		/*循环取类型为KDLabelContainer的组件*/
		for(int i = 0; i < kdPanel1Components.length; i ++){
			if (kdPanel1Components[i] instanceof KDLabelContainer) {
				KDLabelContainer kdLable = (KDLabelContainer)kdPanel1Components[i];
				/*获得KDLabelContainer中的所有组件*/
				Component[] kdLables = kdLable.getComponents();
				/*循环取类型 为KDTextField的所有组件*/
				for(int k = 0; k < kdLables.length; k ++){
					String beforeContent = null;
					String afterContent = null;
					if(kdLables[k] instanceof KDTextField){
						KDTextField kdText = (KDTextField)kdLables[k];
						beforeContent = String.valueOf(beforeUpdate.get(kdText.getName()) == null ? "" : beforeUpdate.get(kdText.getName()).toString().trim());
						afterContent = afterUpdate.get(kdText.getName()).toString().trim();
					}else if(kdLables[k] instanceof KDBizPromptBox){
						KDBizPromptBox kdBox = (KDBizPromptBox)kdLables[k];
						if(null != kdBox.getName()){
							beforeContent = String.valueOf(beforeUpdate.get(kdBox.getName()) == null  ? "" : beforeUpdate.get(kdBox.getName()).toString().trim());
							afterContent = String.valueOf(afterUpdate.get(kdBox.getName()) == null ? "" : afterUpdate.get(kdBox.getName()).toString().trim());
						}
					}
					
					if(null != beforeContent && null != afterContent){
						if(!beforeContent.equals(afterContent)){
							result = 1;
							break ;
						}
					}
				}
			}
		}
		
		if(result == 0){
			/*获得kDPanel1上的所有组件*/
			Component[] kdPanel2Components = this.kDPanel2.getComponents();
			/*循环取类型为KDLabelContainer的组件*/
			for(int i = 0; i < kdPanel2Components.length; i ++){
				if (kdPanel2Components[i] instanceof KDLabelContainer) {
					KDLabelContainer kdLable = (KDLabelContainer)kdPanel2Components[i];
					/*获得KDLabelContainer中的所有组件*/
					Component[] kdLables = kdLable.getComponents();
					/*循环取类型 为KDTextField的所有组件*/
					for(int k = 0; k < kdLables.length; k ++){
						String beforeContent = null;
						String afterContent = null;
						if(kdLables[k] instanceof KDTextField){
							KDTextField kdText = (KDTextField)kdLables[k];
							beforeContent = beforeUpdate.get(kdText.getName()).toString().trim();
							afterContent = afterUpdate.get(kdText.getName()).toString().trim();
						}else if(kdLables[k] instanceof KDBizPromptBox){
							KDBizPromptBox kdBox = (KDBizPromptBox)kdLables[k];
							beforeContent = beforeUpdate.get(kdBox.getName()).toString().trim();
							afterContent = afterUpdate.get(kdBox.getName()).toString().trim();
						}else if(kdLables[k] instanceof KDDatePicker){
							KDDatePicker kdDate = (KDDatePicker)kdLables[k];
							beforeContent = beforeUpdate.get(kdDate.getName()).toString().trim();
							afterContent = afterUpdate.get(kdDate.getName()).toString().trim();
						}else if(kdLables[k] instanceof KDComboBox){
							KDComboBox kdComBox = (KDComboBox)kdLables[k];
							beforeContent = beforeUpdate.get(kdComBox.getName()).toString().trim();
							afterContent = afterUpdate.get(kdComBox.getName()).toString().trim();
						}else if(kdLables[k] instanceof KDNumberTextField){
							KDNumberTextField kdNumberText = (KDNumberTextField)kdLables[k];
							DecimalFormat df = new DecimalFormat("###.00");
							beforeContent = beforeUpdate.get(kdNumberText.getName()).toString().trim();
							afterContent = afterUpdate.get(kdNumberText.getName()).toString().trim();
							if(!"".equals(beforeContent) && !"".equals(afterContent)){
								beforeContent = df.format(Double.parseDouble(beforeContent));
								afterContent = df.format(Double.parseDouble(afterContent));
							}
						}
						if(null != beforeContent && null != afterContent){
							if(!beforeContent.equals(afterContent)){
								result = 1;
								break ;
							}
						}
					}
				}
			}
		}
		if(result == 1){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @description		得到界面上控件值的数组	
	 * @author					
	 * @createDate		2010-12-2
	 * @param			
	 * @return			ArrayList		
	 *	
	 * @version			EAS1.0
	 * @throws BOSException 
	 * @see						
	 */
	private Map getContent() throws BOSException{
		/*界面上控件值的数组*/
		Map content = new HashMap();
		
		/*得到项目名称--初始状态*/
		String project = this.prmtProject.getText().trim();
		content.put(this.prmtProject.getName(),project);
		
		/*得到合同名称--初始状态*/
		String contractBill = this.prmtContractBill.getText().trim();
		content.put(this.prmtContractBill.getName(),contractBill);
		
		/*得到供应商名称--初始状态*/
		String supplierName = this.prmtSupplier.getText().trim();
		content.put(this.prmtSupplier.getName(),supplierName);
		
		/*得到报价时间--初始状态*/
		String quoteTime = String.valueOf(this.pkQuoteTime.getValue() == null ? "" : this.pkQuoteTime.getValue().toString().trim());
		content.put(this.pkQuoteTime.getName().toString().trim(),quoteTime);
		
		/*得到价格--初始状态*/
		String price = this.txtPrice.getText().toString().trim();
		if(price.indexOf(",") != -1){
			String[] p = price.split(",");
			String m = "";
			for(int k = 0; k < p.length; k ++){
				m += p[k];
			}
			price = m;
		}
		content.put(this.txtPrice.getName().toString().trim(),price);
		
		/*得到有效日期--初始状态*/
		String validTime = String.valueOf(this.pkValidDate.getValue() == null ? "2050-12-31" : this.pkValidDate.getValue().toString().trim());
		content.put(this.pkValidDate.getName().toString().trim(),validTime);
		
		/*得到状态--初始状态*/
		String comboState = this.comboState.getSelectedItem().toString().trim();
		content.put(this.comboState.getName().toString().trim(),comboState);
		
		/*将灰显的数据存入Map中*/
		content.put(this.prmtMaterial.getName().toString().trim(),"");
		content.put(this.txtNumber.getName().toString().trim(),"");
		content.put(this.txtName.getName().toString().trim(),"");
		content.put(this.KDModel.getName().toString().trim(),"");
		content.put(this.KDUnit.getName().toString().trim(),"");
		
		/*得到初始时界面特性指标的值--初始状态*/
		Component[] kdPanelComponents = this.kDPanel1.getComponents();
		/*循环取类型为KDLabelContainer的组件*/
		for(int i = 0; i < kdPanelComponents.length; i ++){
			if (kdPanelComponents[i] instanceof KDLabelContainer) {
				KDLabelContainer kdLable = (KDLabelContainer)kdPanelComponents[i];
				/*获得KDLabelContainer中的所有组件*/
				Component[] kdLables = kdLable.getComponents();
				/*循环取类型 为KDTextField的所有组件*/
				for(int k = 0; k < kdLables.length; k ++){
					if(kdLables[k] instanceof KDTextField){
						KDTextField kdText = (KDTextField)kdLables[k];
						String kdName = kdText.getName();
						/*得到特性指标的值*/
						String materialIndexValue = kdText.getText().toString().trim();
						/*将特性指标ID和名字作为Map的键值对存入Map中*/
						content.put(kdName, materialIndexValue);
					}
				}
			}
		}
		return content;
	}
	
}
