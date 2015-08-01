/*
 * @(#)PartAMaterialImportorListUI.java
 * 
 * �����������������޹�˾��Ȩ���� 
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
 * ���������ϱ�����Ϣ�༭����<p>
 * @author ����
 * @version EAS 7.0
 * @see 
 */
public class MaterialInfoEditUI extends AbstractMaterialInfoEditUI {
	
	/**ȱʡ�汾��ʶ*/
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = CoreUIObject
			.getLogger(MaterialInfoEditUI.class);
	/** ����ID **/
	private String tblMaterialId = null;
	/** �������ID **/
	private String materialGroupInfoID = null;
	
	/** �������longNumber **/
	private String materialGroupInfoLongNumber = null;
	
	/** ָ������ **/
	private ArrayList trait = null;
	/** ������Ϣ��ID **/
	private String materialInfoId = null;
	/** ��Դ�ļ�·�� */
	private static final String RESOURCE_PATH = "com.kingdee.eas.fdc.material.MaterialIndexResource";
	/** ���������Ϣ **/
	private MaterialGroupInfo materialGroupInfo = null;
	/** ��Ӧ������ **/
	private String supplierName = null;
	/** �����ʼ��ʱ����йصĿؼ��ĳ�ʼֵ���� **/
	private Map beforeUpdate = null;
	
	boolean closeWindow = false;
	public void onLoad() throws Exception {
		super.onLoad();
		/* ���Listҳ�洫��������Ϣ */
		this.getMaterialInfomation();
		
		/* ����������ID�Ͳ���ID��Ϊ��ʱ��������ָ����ʾ�������� */
		if(null != tblMaterialId && !"".equals(tblMaterialId)
				&& !"".equals(materialGroupInfoID) && null != materialGroupInfoID){
			/*��������������ָ��ֵ*/
			if (!getOprtState().equals(OprtState.ADDNEW)) {
				insertAddMaterialIndexValueData();
			}
			/*��ȡ����ָ������*/
			trait = traitIndexQuery();
			/* �������� */
			adjustMaterialInfoEditUI(trait);
		}
		
		/* ��ʼ���ؼ� */
		this.initKDControl();
	}
	
	/**
	 * @description	��������������ָ��ֵ
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
	 * @description		��������ָ���ֵ
	 * @author			����	
	 * @createDate		2010-12-2
	 * @param			materialIndexId����ָ��ID��materialInfoId������Ϣ���ID
	 * @return			int �Ƿ����ɹ���״̬		
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private int insertMaterialIndexValueData(String materialIndexId,String materialInfoId){
		
		/*����һ������ֵ����*/
		int result = 0;
		
		List indexParams = new ArrayList();
		
		/*����bosID*/
		String bosID = this.getBOSID("6E5BD60C");
		indexParams.add(bosID);
		
		/*����materialId*/
		indexParams.add(materialInfoId);
		
		/*����materialIndexId*/
		indexParams.add(materialIndexId);
		
		/*��������ָ��ֵ*/
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
	 * ����������MaterialInfoId��ò��ϻ���������Ϣ
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
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
	 * @author ����
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
		/* ���ñ��벻���ظ� */
		this.editData.setNumber(UUID.randomUUID().toString());
		/* �������Ʋ����ظ� */
		this.editData.setName(UUID.randomUUID().toString());
	}

	/**
	 * @description		
	 * @author			����	
	 * @createDate		2010-12-2
	 * @param	
	 * @return			Map	����ָ��ID��Value��ֵ��			
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private Map getMaterialIndexInfo() throws BOSException{
		Map map = new HashMap();
		/*���kDPanel1�ϵ��������*/
		Component[] kdPanelComponents = this.kDPanel1.getComponents();
		/*ѭ��ȡ����ΪKDLabelContainer�����*/
		for(int i = 0; i < kdPanelComponents.length; i ++){
			if (kdPanelComponents[i] instanceof KDLabelContainer) {
				KDLabelContainer kdLable = (KDLabelContainer)kdPanelComponents[i];
				/*���KDLabelContainer�е��������*/
				Component[] kdLables = kdLable.getComponents();
				/*ѭ��ȡ���� ΪKDTextField���������*/
				for(int k = 0; k < kdLables.length; k ++){
					if(kdLables[k] instanceof KDTextField){
						KDTextField kdText = (KDTextField)kdLables[k];
						String kdName = kdText.getName();
						/*ǰ���ڶ�̬��������ָ���ʱ��ȡ��ID�ӵ����ֺ��棬����ͨ����ȡ�ַ����õ�ID*/
						String materialIndexId = kdName.substring(kdName.indexOf("_") + 1);
						/*�õ�����ָ���ֵ*/
						String materialIndexValue = kdText.getText().toString().trim();
						/*������ָ��ID��������ΪMap�ļ�ֵ�Դ���Map��*/
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
		
		/* ȡ��ָ��ֵ���� */
//		ArrayList values = this.getTextFieldValue(trait);
		/* ��ù�Ӧ����Ϣ */
		SupplierInfo supplierInfo = (SupplierInfo) this.prmtSupplier.getData();
		/* �ж��Ƿ��ù�Ӧ����Ϣ */
		if (null == supplierInfo) {
			FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH,
					"pleaseChooseSupplier"));
			abort();
		}

		/* ��ù�Ӧ��ID */
		String supplierId = String.valueOf(supplierInfo.getId() == null ? ""
				: supplierInfo.getId().toString().trim());
		/* �жϼ۸��Ƿ�Ϊ�� */
		if (null == this.txtPrice.getText()
				|| "".equals(this.txtPrice.getText())) {
			FDCMsgBox.showInfo("���۲���Ϊ��");
			abort();
		}
		/* ��ü۸���Ϣ */
		BigDecimal price = this.txtPrice.getBigDecimalValue();
				
		if (price.toString().trim().length() > 12) {
			FDCMsgBox.showInfo("����  " + EASResource.getString(RESOURCE_PATH,
			"theNumberIsTooBig"));
			abort();
		}
		
		
		editData.setPrice(null);
		editData.setPrice(price);
		/* �ж����±���ʱ���Ƿ����� */
		if (this.pkQuoteTime.getText() == null
				|| "".equals(this.pkQuoteTime.getText())) {
			FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH,
					"pleaseEnterDate"));
			abort();
		}
		/* ������±���ʱ�� */
		String pkQuoteTime = this.pkQuoteTime.getText().toString().trim();
		/*��ȡ��Чʱ��*/
		String pkValidTime = this.pkValidDate.getText().toString().trim();
		/*����ʱ���ʽ*/
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (pkQuoteTime != null && pkValidTime != null
				&& !pkQuoteTime.equals("") && !pkValidTime.equals("")) {
			try {
				long excelTimeL = format.parse(pkQuoteTime).getTime();
				long validateL = format.parse(pkValidTime).getTime();

				/* ��Ч���ڲ���С�ڱ���ʱ�� */
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
		/* ���List����Ĳ���״̬Ϊ������ִ�����²��� */
		if (getOprtState().equals(OprtState.ADDNEW)) {
			/* ��֤��Ӧ�̣��۸����±���ʱ�������ֶ��Ƿ���ڣ����������ִ�����²��� */
			if (!this.validateRequiredField(supplierId, price, pkQuoteTime)) {

				String bUuid = MaterialInfoFactory.getRemoteInstance().getBOSID("9D390CBB");
				/* ����materialInfo��isLatest�ֶ�Ϊ1 */
				MaterialInfoFactory.getRemoteInstance().updateIsLatest(supplierId);
				/* ����ָ��ֵ */
				ProjectInfo projectInfo = (ProjectInfo) this.prmtProject.getData();

				/* ������Ŀid */
				String projectId = null;
				if (null != projectInfo) {
					projectId = String.valueOf(projectInfo.getId() == null ? ""
							: projectInfo.getId().toString().trim());
				}

				/* ��ͬid */
				ContractBillInfo cBillInfo = (ContractBillInfo) this.prmtContractBill
						.getData();
				String cbillId = null;
				if (null != cBillInfo) {
					cbillId = String.valueOf(cBillInfo.getId() == null ? ""
							: cBillInfo.getId().toString().trim());
				} /* ��Ч��Ч */
				String state = null;
				if (null != this.comboState.getSelectedItem()) {
					state = String
							.valueOf((MaterialStateEnum.VALID.toString())
									.equals(this.comboState.getSelectedItem()
											.toString().trim()) ? MaterialStateEnum.VALID_VALUE
									: MaterialStateEnum.INVALID_VALUE);
				}

				/* ��Ч���� */
				String validate = null;
				if (null == this.pkValidDate.getValue()
						|| "".equals(this.pkValidDate.getValue().toString())) {
					validate = "2050-12-31 00:00:00";
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					validate = sdf.format(this.pkValidDate.getValue())
							.toString().trim();
				}

				/* �����Ƿ����� */
				int isLastest = 1;
				/* �����Ƿ����� */
				int isEnable = 1;
				/* ����List */
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
				
				/* ���봴����ID */
				/* userId */
				params.add(SysContext.getSysContext().getCurrentUserInfo().getId().toString());
				/* FCreateTime */
				params.add(new SimpleDateFormat("yyyy-MM-dd")
						.format(FDCDateHelper.getServerTimeStamp()));
				/* ��Чʱ�� */
				params.add(validate);
				/* �Ƿ����� */
				params.add(new Integer(isLastest));
				/* �Ƿ����� */
				params.add(new Integer(isEnable));
				/* �� */
				params.add("");
				/* state��ʼ״̬ */
				params.add("1SAVED");
				/* mstate��ʼ״̬ */
				params.add(state);
				/*���Ƶ�Ԫ*/
				String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString().trim();
    			params.add(cuId);
    			/*������֯��Ԫ*/
    			String orgUnitId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString().trim();
    			params.add(orgUnitId);
				
				try {
					MaterialInfoFactory.getRemoteInstance().addMaterialData(params, "importMaterialInfoSql");
					
					List materialIndexs = this.getMaterialIndexIdByMaterialId(this.tblMaterialId);
										
					for(int m = 0; m < materialIndexs.size(); m ++){
						
						Map materialIndexMap = (Map) materialIndexs.get(m);
						
						String allMaterialIndex = materialIndexMap.get("FID").toString().trim();
						
						List indexParams = new ArrayList();
						
						/*����bosID*/
						String bosID = this.getBOSID("6E5BD60C");
						indexParams.add(bosID);
						
						/*����materialId*/
    					indexParams.add(bUuid);
    					
    					/*����materialIndexId*/
    					indexParams.add(allMaterialIndex);
    					
    					Iterator iterator = indexValueMap.keySet().iterator();
    					
    					/*����ָ��ֵ*/
						String materialIndexValue = "";
						
						/*ѭ������ָ��ֵ*/
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
					/*�������ָ������*/
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
					
					/*ˢ��������*/
					refreshMaterialInfoUI();
					
				} catch (Exception ex) {
					logger.error(ex.getMessage());
					handUIException(ex);
					abort();
				}

				// super.actionSave_actionPerformed(e);
				/* �жϲ��������Ϣ�Ƿ�Ϊ�� */
				if (getUIContext().get("materialNodeInfo") != null) {
					materialGroupInfo = (MaterialGroupInfo) getUIContext().get(
							"materialNodeInfo");
				}
				/* �����Ժ���ս��� */
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

			/* ����������״̬Ϊ���� */
			this.setOprtState(OprtState.ADDNEW);
			beforeUpdate = this.getContent();
		} else if (getOprtState().equals(OprtState.EDIT)) {
			//����update�����ط���
			/* ��ȡ���������ָ��ֵ */
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
				
				/*ˢ��������*/
				refreshMaterialInfoUI();
			}else{
				FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH,"thisMessageWasExist"));
				abort();
			}
		}		
	}

	
	 /**
	* ������<����bosType�����BOSID>
	* @author <luoxiaolong>
	* @param  <bosType>
	* @return  <String>
	* ����ʱ��  <2010/11/16> <p>
	* 
	* �޸��ˣ�<�޸���> <p>
	* �޸�ʱ�䣺<yyyy/mm/dd> <p>
	* �޸�������<�޸�����> <p>
	*
	* @see  <��ص���>
	*/
	public String getBOSID(String bosType){
		
		/**����һ������ֵ����*/
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
		
		/**���������Ƿ��޸�*/
		boolean boo = true;
		boo = checkContentUpdate();
		/*�˳�����ʱ�ж������Ƿ��и���*/
		if(!boo){
			/*���ֵ*/
			clearMaterialIndexValue();	
		}else{
			int isExit = FDCMsgBox.showConfirm2("�����Ѿ��޸ģ��Ƿ񱣴棡");
			
			/*�ж��Ƿ���Ҫ�˳�*/
			if(isExit == 0){
				SupplierInfo supplierInfo = (SupplierInfo)this.prmtSupplier.getData();
				String supplierId = String.valueOf(supplierInfo == null ? "" : supplierInfo.getId().toString().trim());
				String quoteTime = String.valueOf(pkQuoteTime.getValue() == null ? "" : pkQuoteTime.getValue().toString().trim());
				if("".equals(quoteTime)){
					FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH,"pleaseEnterDate"));
					return ;
				}
				/*��������Ϣ�Ѵ��ڣ��͸�����ʾ�������ֱ�ӱ���*/
				if (this.validateRequiredField(supplierId, txtPrice.getBigDecimalValue(), pkQuoteTime.getText().trim())) {
					FDCMsgBox.showInfo(EASResource.getString(RESOURCE_PATH,"thisMessageWasExist"));
					this.setOprtState(OprtState.EDIT);
					return ;
				}else{
					this.actionSave_actionPerformed(null);
					
					/*ˢ��������*/
					refreshMaterialInfoUI();
				}
			}
		}
		
		this.setOprtState(OprtState.ADDNEW);
		
		/*���ֵ*/
		clearMaterialIndexValue();
		
		beforeUpdate = this.getContent();
	}
	
	/**
	 * �������ָ��ֵ
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

		/*�������ָ������*/
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
		/*���*/
		this.clearMaterialIndexValue();
		
		/*ˢ��������*/
		refreshMaterialInfoUI();
	}

	/**
	 * �жϸü�¼�Ƿ����
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
	 *              ��T_MTR_MaterialIndex��T_MTR_MaterialIndexValue��õ�����ָ�꣬����������ָ������
	 * @author ����
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

		ArrayList thisMaterialTraitIndex = new ArrayList();// ��������ָ��ʵ������

		/*����*/
		if (getOprtState().equals(OprtState.ADDNEW)) {
			IRowSet rowSet;
			try {
				rowSet = MaterialInfoFactory.getRemoteInstance().selectTraitAndSuperIndex(materialGroupInfoLongNumber);
				/* ������ָ��ֵ�����ַ�װ��ʵ���в���ʵ��ӵ�������*/
				while (rowSet.next()) {
					/* �������ڷ�װ����ָ�꼰��ֵ��ʵ��*/
					MaterialIndexValueBean mivbBean = new MaterialIndexValueBean();
					/* ��T_MTR_MaterialIndex�еõ�������ָ��ֵ*/
					String indexID = rowSet.getString("FID");
					/* �õ�����ָ�������*/
					String indexName = rowSet.getString("FName_l2");
					/* ��ID�����ַ�װ��ʵ����*/
					mivbBean.setId(indexID);
					mivbBean.setName(indexName);
					/* ��ʵ��ӵ�������*/
					thisMaterialTraitIndex.add(mivbBean);
				}
			} catch (EASBizException e) {
				logger.error(e.getMessage());
				handUIException(e);
			}
		} else {
			/*�޸ġ��鿴*/
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
	 * ������<��������id���������ָ��id>
	 * <p>
	 * �����޸��˸÷�����ʵ�� By Owen_Wen 2011-02-24
	 * 
	 * @author <luoxiaolong>
	 * @param <materialGruopId>
	 * @return <null> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
	private List getMaterialIndexIdByMaterialId(String materialId) {
		List resultList = new ArrayList();
		try {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("materialGroup.longNumber"));
			MaterialInfo materialInfo = MaterialFactory.getRemoteInstance().getMaterialInfo(new ObjectUuidPK(materialId), sic);
			IRowSet rowSet = MaterialInfoFactory.getRemoteInstance().selectTraitAndSuperIndex(materialInfo.getMaterialGroup().getLongNumber());
				while (rowSet.next()) {
				/* ��T_MTR_MaterialIndex�еõ�������ָ��ID */
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
     * ������<��������id�������������>
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
	 * @description ���ݲ�������ָ�궯̬���ɽ���
	 * @author ����
	 * @createDate 2010-11-14
	 * @param
	 * @return
	 * 
	 * @version EAS1.0
	 * @see
	 */
	/* traitIndexΪ��traitIndexQuery�������ص�����ָ������*/
	protected void adjustMaterialInfoEditUI(ArrayList traitIndex) {
		int traitIndexCount = 0;
		MaterialIndexValueBean mivb = null;
		/* �жϵõ�������ָ�������Ƿ�Ϊ��*/
		if (traitIndex != null) {
			traitIndexCount = traitIndex.size();
		} else {
			return;
		}
		/* ��������ָ�굽������*/
		for (int i = 0; i < traitIndexCount; i++) {
			/* ����KDLabelContainer����*/
			KDLabelContainer traitIndexLabel = new KDLabelContainer();
			/* �����ı���*/
			KDTextField tx = new KDTextField();
			if (traitIndex.get(i) != null) {
				/* �õ���װָ�꼰��ֵ��ʵ��*/
				mivb = (MaterialIndexValueBean) traitIndex.get(i);
			}
			/* ����KDLabelContainer�ؼ�������*/
			traitIndexLabel.setName("traitIndex_" + mivb.getId());
			/* ����KDLabelContainer��ʾ������*/
			traitIndexLabel.setBoundLabelText(mivb.getName());
			/* ���ÿ��*/
			traitIndexLabel.setBoundLabelLength(100);
			/*�����Ƿ����»��ߡ����������������ؼ�����һ�£�*/
			traitIndexLabel.setBoundLabelUnderline(true);
			tx.setName("textField_" + mivb.getId());// �����ı��������
			tx.setMaxLength(1024);
			if (getOprtState().equals(OprtState.VIEW)) {
				tx.setEditable(false);
			}
			/* �����ı�������ݡ����ݴ�ʵ����ȡ��*/
			tx.setText(mivb.getValue());
			traitIndexLabel.setBoundEditor(tx);
			/* ����ָ������Ϊż��ʱ�ӵ������ұ�������*/
			if (i % 2 == 0) {
				traitIndexLabel.setBounds(new Rectangle(this.model.getX(),
						this.model.getY() + (i / 2 + 1) * 35, 240, 20));
				kDPanel1.add(traitIndexLabel, new KDLayout.Constraints(
						this.model.getX(),
						this.model.getY() + (i / 2 + 1) * 35, 240, 20, 0));
				/* ����ָ������Ϊ����ʱ�ӵ��������������*/
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
	 * @description ��ȡ�������Ƚ�������ĳ�ʼֵ
	 * @author ����
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
				
				/*���ò�������ʼֵ*/
				this.prmtMaterial.setValue(materialGroupDisplayName);
				/*���ñ����ʼֵ*/
				this.txtNumber.setValue(materialInfo.getNumber());
				/*���ò������Ƴ�ʼֵ*/
				this.txtName.setValue(materialInfo.getName());
				/*���ù���ͺų�ʼֵ*/
				this.KDModel.setValue(materialInfo.getModel());
				/*���õ�λ��ʼֵ*/
				this.KDUnit.setValue(materialUnit.getDisplayName());
				DecimalFormat df = new DecimalFormat("###.00");
				String price = df.format(materialInfoInfo.getPrice());
				this.txtPrice.setValue(materialInfoInfo.getPrice());
				this.pkQuoteTime.setValue(materialInfoInfo.getQuoteTime());
			}else{
				MaterialGroupInfo materialGroupInfo = (MaterialGroupInfo) getUIContext().get("materialNodeInfo");
				/*���ò�������ʼֵ*/
				this.prmtMaterial.setValue(materialGroupInfo.getDisplayName());
				/*���ñ����ʼֵ*/
				this.txtNumber.setValue(getUIContext().get("tblNumberValue"));
				/*���ò������Ƴ�ʼֵ*/
				this.txtName.setValue(getUIContext().get("tblNameValue"));
				/*���ù���ͺų�ʼֵ*/
				this.KDModel.setValue(getUIContext().get("tblModelValue"));
				/*���õ�λ��ʼֵ*/
				this.KDUnit.setValue(getUIContext().get("tblUnitValue")); 
				materialGroupInfoID = materialGroupInfo.getId().toString().trim();
				materialGroupInfoLongNumber = materialGroupInfo.getLongNumber();
				tblMaterialId = getUIContext().get("tblMaterialId").toString();
			}
		}
	}

	/**
	 * @description У�鹩Ӧ�̣����±���ʱ�䣬������Ϣ�Ƿ��Ѵ���
	 * @author ����
	 * @createDate 2010-11-16
	 * @param	supplier��Ӧ��ID��price���ۣ�pkQuoteTime���±���ʱ��
	 * @return	boolean������֤�Ƿ���ڸ�����¼
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
				/*���ڸ�ʽת��*/
				for(int i = 0; i < quotes.length; i ++){
					if(i == quotes.length -1){
						newQuoteTime += Integer.parseInt(quotes[i]) + "";
					}else{
						newQuoteTime += Integer.parseInt(quotes[i]) + "-";
					}
				}
				/*������rowSet2��Ϊ�գ�����������¼�Ѵ���*/
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
	 * @description ���ݽ����ж��Ƿ�����ͬ������
	 * @author ����
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
		/*�ڸ�UI���½Ǳ��У���û�����ݾͲ������ظ�����*/
		if(null == getUIContext().get("materialInfoTable")){
			return false;
		}
		
		/**��ø�UI���½Ǳ��е�����*/
		KDTable materialInfoTable = (KDTable)getUIContext().get("materialInfoTable");
		
		/*ѭ���ж������Ƿ��ظ�*/
		for(int i = 0; i < materialInfoTable.getRowCount(); i ++){
			/**��ü۸�*/
			String pPriceString = "0";
			if(null != materialInfoTable.getRow(i) && null != materialInfoTable.getRow(i).getCell("price") && null != materialInfoTable.getRow(i).getCell("price").getValue()){
				pPriceString = materialInfoTable.getRow(i).getCell("price").getValue().toString();
			}
			double pPriceDouble = Double.parseDouble(pPriceString);
			double sPriceDouble = Double.parseDouble(price);
			/**��ñ���ʱ��*/
			String pQuoteTimeString = materialInfoTable.getRow(i).getCell("quoteTime").getValue().toString();
			if(pQuoteTimeString.indexOf(" ") != -1){
				pQuoteTimeString = pQuoteTimeString.substring(0,pQuoteTimeString.indexOf(" "));
			}
			/**��ù�Ӧ��*/
			String pSupString = String.valueOf(materialInfoTable.getRow(i).getCell("supplier").getValue() == null ? "" : materialInfoTable.getRow(i).getCell("supplier").getValue().toString());
			String sSupString = this.prmtSupplier.getText().toString().trim();
			
			/**���id*/
			String pMaterialInfoId = materialInfoTable.getRow(i).getCell("id").getValue().toString();  
			if(pPriceDouble == sPriceDouble && pQuoteTimeString.equals(pkQuoteTime) && pSupString.equals(sSupString)){
				if(!materialInfoId.equals(pMaterialInfoId)){
//					MsgBox.showInfo("����Ϣ�Ѵ��ڣ�");
					return true;
				}
			}
			
		}
		return false;
	}
	
	
	/**
	 * @description �ؼ���ʼ��
	 * @author ����
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
		/*���ù�������Ť״̬*/
		this.btnAuditResult.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnAddNew.setEnabled(true);
		this.btnRemove.setEnabled(true);
		this.btnAudit.setVisible(true);
		this.btnRemove.setEnabled(true);
		this.btnAudit.setVisible(false);
		
		/* �������ؼ���--������Ϣ*/			
		this.prmtMaterial.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialGroupQuery");
		this.prmtMaterial.setEnabled(false);
		
		/*���ϱ���ؼ���--������Ϣ*/
		this.txtNumber.setQueryInfo("com.kingdee.eas.st.basedata.app.MaterialCodeQuery");
		this.txtNumber.setEnabled(false);
		
		/* �������ƿؼ���--������Ϣ*/			
		this.txtName.setQueryInfo("com.kingdee.eas.fdc.material.app.F7FDCMaterialQuery");
		this.txtName.setEnabled(false);
		
		/* ����ͺſؼ���--������Ϣ*/
		this.KDModel.setQueryInfo("com.kingdee.eas.fdc.material.app.F7MaterialModeQuery");
		this.KDModel.setEnabled(false);
		
		/* ��λ�ؼ���--������Ϣ*/
		this.KDUnit.setEnabled(false);
		
		
		/* ��Ŀ���ƿؼ���--������Ϣ*/
		this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		this.prmtProject.setEditable(true);
		
		/* ��ͬ���ƿؼ���--������Ϣ*/
		this.prmtContractBill.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillF7Query");
		this.prmtContractBill.setEditable(true);
		this.prmtContractBill.setDisplayFormat("$name$");
		this.prmtContractBill.setEditFormat("$name$");
		this.prmtContractBill.setCommitFormat("$name$");
		
		/* ��Ӧ�̿ؼ���--������Ϣ*/
		this.prmtSupplier.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");
		this.prmtSupplier.setEditable(true);
		
		/* ״̬--������Ϣ*/
		this.comboState.setEditable(true);
		
		/* ����ʱ��--������Ϣ*/
		this.pkQuoteTime.setEditable(true);
		
		/* ��Ч����--������Ϣ*/
		this.pkValidDate.setEditable(true);

		/* ����--������Ϣ*/
		this.txtPrice.setEditable(true);
		
		/*�������޸ġ��鿴״̬ʱ���ؼ���״̬*/
		if (getOprtState().equals(OprtState.VIEW)) {
			/*�����Ƿ�ɱ༭--������Ϣ*/
			this.prmtProject.setEditable(false);
			this.prmtContractBill.setEditable(false);
			this.prmtSupplier.setEditable(false);
			this.comboState.setEditable(false);
			this.pkQuoteTime.setEditable(false);
			this.txtPrice.setEditable(false);
			
			/*���ù�������Ť״̬*/
			this.btnAddNew.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.btnAudit.setVisible(false);
			
			/*�ж� �ɹ���֯ ʵ��  ��  ����*/
			if(null != this.getUIContext().get("isViewEnbled") && !((Boolean)this.getUIContext().get("isViewEnbled")).booleanValue()){
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.btnViewSignature.setEnabled(false);
			}
			
			/*���״̬*/
			boolean flag = this.getMaterialState();
			if(flag){
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
			}else{
				this.btnEdit.setEnabled(true);
			}
			
			this.beforeUpdate = this.getContent();
		}else if(getOprtState().equals(OprtState.ADDNEW)){
			/*���ù�������Ť״̬*/
			this.btnAddNew.setEnabled(true);
			
			/*���ñ�����Ϣ��ʼ״̬*/
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
			/*���ù�������Ť״̬*/
			this.actionEdit.setEnabled(false);
			this.actionAddNew.setEnabled(true);
			this.btnEdit.setEnabled(false);	
			
			this.beforeUpdate = this.getContent();
		}

		/*�ӹ�Ӧ�̿���롢���ÿؼ�״̬*/
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
	 * @description		�˳�����ʱˢ��������	
	 * @author					
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public boolean destroyWindow() {
		
		/**���������Ƿ��޸�*/
		boolean boo = true;
		try {
			boo = checkContentUpdate();
		} catch (BOSException e) {
			handUIException(e);
		}
		
		/*�˳�����ʱ�ж������Ƿ��и���*/
		if(!boo){
			try{
				disposeUIWindow();
				return false;
			}catch(Exception e){
				handUIException(e);
			}
		}
		
		int isExit = FDCMsgBox.showConfirm3("�����Ѿ��޸ģ��Ƿ񱣴沢�˳���");
		
		/*�ж��Ƿ���Ҫ�˳�*/
		if(isExit == 0){
			try {
				/*����*/
				this.actionSave_actionPerformed(null);
				
				/*�رմ���*/
				disposeUIWindow();
				
				/*ˢ��������*/
				refreshMaterialInfoUI();
			} catch (Exception e) {
				handUIException(e);
			}
		}else if(isExit == 1){

			/*�رմ���*/
			disposeUIWindow();
			
			/*ˢ��������*/
			refreshMaterialInfoUI();
		}else{
			/*ˢ��������*/
			refreshMaterialInfoUI();
		}
		
		return true;
	}
	
	/**
	 * @description		ˢ��������
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
	 * @description		�õ�����ָ��ֵ	
	 * @author					
	 * @createDate		2010-12-2
	 * @param			traitIndex����ָ������
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
	 * @description		�õ�������Ϣ��״̬	
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
	 * @description		�õ�����״̬	
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
	 * @description		���ҳ�������Ƿ񱻸���	
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
		
		/*���kDPanel1�ϵ��������*/
		Component[] kdPanel1Components = this.kDPanel1.getComponents();
		/*ѭ��ȡ����ΪKDLabelContainer�����*/
		for(int i = 0; i < kdPanel1Components.length; i ++){
			if (kdPanel1Components[i] instanceof KDLabelContainer) {
				KDLabelContainer kdLable = (KDLabelContainer)kdPanel1Components[i];
				/*���KDLabelContainer�е��������*/
				Component[] kdLables = kdLable.getComponents();
				/*ѭ��ȡ���� ΪKDTextField���������*/
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
			/*���kDPanel1�ϵ��������*/
			Component[] kdPanel2Components = this.kDPanel2.getComponents();
			/*ѭ��ȡ����ΪKDLabelContainer�����*/
			for(int i = 0; i < kdPanel2Components.length; i ++){
				if (kdPanel2Components[i] instanceof KDLabelContainer) {
					KDLabelContainer kdLable = (KDLabelContainer)kdPanel2Components[i];
					/*���KDLabelContainer�е��������*/
					Component[] kdLables = kdLable.getComponents();
					/*ѭ��ȡ���� ΪKDTextField���������*/
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
	 * @description		�õ������Ͽؼ�ֵ������	
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
		/*�����Ͽؼ�ֵ������*/
		Map content = new HashMap();
		
		/*�õ���Ŀ����--��ʼ״̬*/
		String project = this.prmtProject.getText().trim();
		content.put(this.prmtProject.getName(),project);
		
		/*�õ���ͬ����--��ʼ״̬*/
		String contractBill = this.prmtContractBill.getText().trim();
		content.put(this.prmtContractBill.getName(),contractBill);
		
		/*�õ���Ӧ������--��ʼ״̬*/
		String supplierName = this.prmtSupplier.getText().trim();
		content.put(this.prmtSupplier.getName(),supplierName);
		
		/*�õ�����ʱ��--��ʼ״̬*/
		String quoteTime = String.valueOf(this.pkQuoteTime.getValue() == null ? "" : this.pkQuoteTime.getValue().toString().trim());
		content.put(this.pkQuoteTime.getName().toString().trim(),quoteTime);
		
		/*�õ��۸�--��ʼ״̬*/
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
		
		/*�õ���Ч����--��ʼ״̬*/
		String validTime = String.valueOf(this.pkValidDate.getValue() == null ? "2050-12-31" : this.pkValidDate.getValue().toString().trim());
		content.put(this.pkValidDate.getName().toString().trim(),validTime);
		
		/*�õ�״̬--��ʼ״̬*/
		String comboState = this.comboState.getSelectedItem().toString().trim();
		content.put(this.comboState.getName().toString().trim(),comboState);
		
		/*�����Ե����ݴ���Map��*/
		content.put(this.prmtMaterial.getName().toString().trim(),"");
		content.put(this.txtNumber.getName().toString().trim(),"");
		content.put(this.txtName.getName().toString().trim(),"");
		content.put(this.KDModel.getName().toString().trim(),"");
		content.put(this.KDUnit.getName().toString().trim(),"");
		
		/*�õ���ʼʱ��������ָ���ֵ--��ʼ״̬*/
		Component[] kdPanelComponents = this.kDPanel1.getComponents();
		/*ѭ��ȡ����ΪKDLabelContainer�����*/
		for(int i = 0; i < kdPanelComponents.length; i ++){
			if (kdPanelComponents[i] instanceof KDLabelContainer) {
				KDLabelContainer kdLable = (KDLabelContainer)kdPanelComponents[i];
				/*���KDLabelContainer�е��������*/
				Component[] kdLables = kdLable.getComponents();
				/*ѭ��ȡ���� ΪKDTextField���������*/
				for(int k = 0; k < kdLables.length; k ++){
					if(kdLables[k] instanceof KDTextField){
						KDTextField kdText = (KDTextField)kdLables[k];
						String kdName = kdText.getName();
						/*�õ�����ָ���ֵ*/
						String materialIndexValue = kdText.getText().toString().trim();
						/*������ָ��ID��������ΪMap�ļ�ֵ�Դ���Map��*/
						content.put(kdName, materialIndexValue);
					}
				}
			}
		}
		return content;
	}
	
}
