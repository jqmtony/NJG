package com.kingdee.eas.fdc.material.app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialPurchasingCollection;
import com.kingdee.eas.basedata.master.material.MaterialPurchasingFactory;
import com.kingdee.eas.basedata.master.material.UsedStatusEnum;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.PartAMaterialEntryCollection;
import com.kingdee.eas.fdc.material.PartAMaterialEntryFactory;
import com.kingdee.eas.fdc.material.PartAMaterialEntryInfo;
import com.kingdee.eas.fdc.material.PartAMaterialInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;

/**
 * 材料明细分录引入 数据转换接口
 * @author owen_wen 2010-12-16 
 */

public class PartAMaterialEntryTransmission extends AbstractDataTransmission {

	private static final Logger logger = CoreUIObject.getLogger(PartAMaterialEntryTransmission.class);
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return PartAMaterialEntryFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			logger.debug(e.getMessage());
			throw new TaskExternalException(e.getMessage(), e);
		}		
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		checkMaterialIsExsit(hsData, ctx);		
		checkQuantity(hsData);		
		checkContractIsExist(hsData, ctx); 
		checkContractIsAudited(hsData.get("FMainContractBill_number").toString(), ctx);
		checkArriveDate(hsData);
		return null;
	}

	/**
	 * 检查数量：1)是否为空；2)是否格式不正确，不能转换成double
	 * @param hsData
	 * @throws TaskExternalException
	 * @author owen_wen 2010-12-24
	 */
	private void checkQuantity(Hashtable hsData) throws TaskExternalException {
		if (hsData.get("FQuantity").toString() != null && 
				hsData.get("FQuantity").toString().length() == 0)
			throw new TaskExternalException("数量不能为空！");
		else{
			try {
				Double.parseDouble(hsData.get("FQuantity").toString());
			}catch (NumberFormatException e) {
				throw new TaskExternalException("数量格式不正确！");
			}
		}
		
	}
	
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		PartAMaterialEntryInfo info = (PartAMaterialEntryInfo)coreBaseInfo;
		info.setParent((PartAMaterialInfo)getContextParameter().get("parentBill"));
		
		// 处理 采购单价（原币），当导入数据为0时，需要从物料的采购属性中取单价
		if(FDCNumberHelper.toBigDecimal(info.getOriginalPrice()).compareTo(FDCHelper.ZERO) == 0){
			try {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("Material", info.getMaterial().getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("OrgUnit", 
						com.kingdee.eas.util.app.ContextUtil.getCurrentCtrlUnit(ctx).getId().toString()));
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				MaterialPurchasingCollection infoColl =MaterialPurchasingFactory.getLocalInstance(ctx).getMaterialPurchasingCollection(view);
				if (infoColl.size() > 0){
					// 取当前登录组织所对应的参考价				
					info.setOriginalPrice(FDCNumberHelper.toBigDecimal(infoColl.get(0).getPrice())); 
				}
			} catch (BOSException e) {
				logger.debug(e.getMessage(), e.getCause());
				e.printStackTrace();
			}
		}
		
		// 如果导入数据为空，采购属性中也没有性，手动赋值为0
		if (info.getOriginalPrice() == null) 
			info.setOriginalPrice(FDCHelper.ZERO);
		
		// 采购单价（本币）= 采购单价（原币）* 汇率
		info.setPrice(info.getOriginalPrice().multiply(((PartAMaterialInfo)getContextParameter().get("parentBill")).getContractBill().getExRate()));
		
		// 金额  =  采购单价（本币）× 数量
		info.setAmount(info.getPrice().multiply(info.getQuantity()));
		
		if (info.getDescription() != null
				&& info.getDescription().length() > 80) // 只取前80个字符
			info.setDescription(info.getDescription().substring(0, 80));
				
		//如果是覆盖引入		
		if (this.isSltImportUpdate()) { 	
			FilterInfo filter = new FilterInfo();
			if (info.getMainContractBill() != null){ // 有可能Excel中没有合同项，或者合同不存在
				filter.getFilterItems().add(new FilterItemInfo("MainContractBill", info.getMainContractBill().getId().toString()));
			}else {
				filter.getFilterItems().add(new FilterItemInfo("MainContractBill", null));
			}
			filter.getFilterItems().add(new FilterItemInfo("Material", info.getMaterial().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("Parent", info.getParent().getId().toString()));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			
			PartAMaterialEntryCollection infoColl;
			try {
				infoColl = PartAMaterialEntryFactory.getLocalInstance(ctx).getPartAMaterialEntryCollection(view);
				if (infoColl.size() > 0){
					info.setId(infoColl.get(0).getId()); // info有了ID，则在super.submit(info, ctx)中就会覆盖更新了
				}
			} catch (BOSException e) {
				logger.debug(e.getMessage(), e.getCause());
				e.printStackTrace();
			}
		}
		
		super.submit(info, ctx);
	}
	
	/**
	 * 检查合同是否审批<p>
	 * 描述：当领用合同为未审批状态时，即状态为保存、提交、审批中时，给出提示信息； 
	 * 当合同不存在时，引入引出功能会自动做校验，提示合同不存在。
	 * 已审批的领用合同系统中存在时，可正常导入显示对应的合同编码
	 * @author owen_wen 2010-12-16
	 * @throws TaskExternalException 
	 * @throws BOSException 
	 */
	private void checkContractIsAudited(String contractNumber, Context ctx) throws TaskExternalException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", contractNumber));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		try {
			ContractBillCollection contractColl = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
			if (contractColl.size() > 0){
				ContractBillInfo contractInfo = contractColl.get(0);
				if (!FDCBillStateEnum.AUDITTED_VALUE.equals(contractInfo.getState().getValue())){
					throw new TaskExternalException("合同未审批。");
				}
			}
		} catch (BOSException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 检查合同是否存在，
	 * @throws TaskExternalException 
	 */
	private void checkContractIsExist(Hashtable hsData, Context ctx) throws TaskExternalException{
		try {
			String contractNumber = hsData.get("FMainContractBill_number").toString();
			String contractName = hsData.get("FMainContractBill_name").toString();
			
			if ((StringUtils.isEmpty(contractNumber) && !StringUtils.isEmpty(contractName))){
				throw new TaskExternalException("领用合同名称有值时，领用合同编码不能为空。");
			}
			if (!StringUtils.isEmpty(contractNumber) && StringUtils.isEmpty(contractName)){
				throw new TaskExternalException("领用合同编码有值时，领用合同名称不能为空。");
			}
			
			// 只有当领用合同编码和名称都不为空时，才判断是否存在
			if (!StringUtils.isEmpty(contractNumber) && !StringUtils.isEmpty(contractName)){ 
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("number", contractNumber));
				filter.getFilterItems().add(new FilterItemInfo("name", contractName));
				if (!ContractBillFactory.getLocalInstance(ctx).exists(filter)) {
					throw new TaskExternalException("系统中不存在此合同：领用合同编码为：" + contractNumber 
							+ "领用合同名称为：" + contractName);
				}
			}
			
		} catch (EASBizException e) {
			logger.debug(e.getMessage(), e.getCause());
			e.printStackTrace();
		} catch (BOSException e) {
			logger.debug(e.getMessage(), e.getCause());
			e.printStackTrace();
		}
	}
		
	/**
	 * 校验：
	 * 1）	为空时，提示：物料编码不能为空！<p>
	 * 2）	物料编码在系统中找不到匹配的，提示：该物料在系统中不存在！ <p>
	 * 3）	导入未核准的物料时，提示：未核准的物料不能导入！ <p>

	 * @param materialNumber 物料编码
	 * @param ctx 上下文
	 */
	private void checkMaterialIsExsit(Hashtable hsData, Context ctx) throws TaskExternalException{
		StringBuffer msg  = new StringBuffer("");
		String materialNumber = hsData.get("FMaterial_number").toString();
		if (StringUtils.isEmpty(materialNumber)){
			msg.append("物料编码不能为空！");
			throw new TaskExternalException(msg.toString());
		}
		
		try {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", materialNumber));
			msg.append("物料编码＝" + materialNumber);
			if (!StringUtils.isEmpty(hsData.get("FMaterial_name_l2").toString())){
				filter.getFilterItems().add(new FilterItemInfo("name", hsData.get("FMaterial_name_l2").toString()));
				msg.append("，物料名称＝" + hsData.get("FMaterial_name_l2").toString());
			}
			
			if (!StringUtils.isEmpty(hsData.get("FMaterial_model").toString())) {
				filter.getFilterItems().add(new FilterItemInfo("model", hsData.get("FMaterial_model").toString()));
				msg.append("，物料规格型号＝" + hsData.get("FMaterial_model").toString());
			}
			
			if (!StringUtils.isEmpty(hsData.get("FMaterial$baseUnit_name_l2").toString())){
				filter.getFilterItems().add(new FilterItemInfo("baseUnit.name", hsData.get("FMaterial$baseUnit_name_l2").toString()));
				msg.append("，物料单位＝" + hsData.get("FMaterial$baseUnit_name_l2").toString());
			}
			
			if (!MaterialFactory.getLocalInstance(ctx).exists(filter)){
				msg.append("，该物料在系统中不存在！");
				throw new TaskExternalException(msg.toString());
			}
	
			filter.getFilterItems().clear();
			filter.getFilterItems().add(new FilterItemInfo("number", materialNumber));
			filter.getFilterItems().add(new FilterItemInfo("status", new Integer(UsedStatusEnum.APPROVED_VALUE), CompareType.NOTEQUALS));
			if (MaterialFactory.getLocalInstance(ctx).exists(filter)){
				msg.append("未核准的物料不能导入！");
				throw new TaskExternalException(msg.toString());
			}
		} catch (EASBizException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (BOSException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}		
	}
	
	/**
	 * 校验到货日期，如果用户录入的格式不正确，给出出错异常提示信息
	 * @author owen_wen
	 */
	private void checkArriveDate(Hashtable hsData) throws TaskExternalException{
		String arriveDate = hsData.get("FArriveDate").toString();
		if(StringUtils.isEmpty(arriveDate))
		{
			return;
		}
		
		DateFormat df = null;
		if(arriveDate.trim().length() <= "yyyy-MM-dd".length()){ // 处理 "yyyy-MM-d"
			df = new SimpleDateFormat("yyyy-MM-dd");
		}else if(arriveDate.trim().length() <= "yyyy-MM-dd  HH:mm".length()){ //处理 yyyy-MM-d HH:mm情况
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm");	
		}else if(arriveDate.trim().length() <= "yyyy-MM-dd  HH:mm:ss".length()){
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		}else{
			throw new TaskExternalException("到货日期格式不正确；确保到货日期的单元格格式为文本型，建议使用格式类似于：2010-12-05 ");
		}
		
		try {
			Date d = df.parse(arriveDate);
			((DataToken)hsData.get("FArriveDate")).data = df.format(d);
		} catch (ParseException e) {
			logger.debug(e.getMessage(), e.getCause());
			e.printStackTrace();
			throw new TaskExternalException("到货日期格式不正确；确保到货日期的单元格格式为文本型，建议使用格式类似于：2010-12-05 ");
		}
			
	}
}
