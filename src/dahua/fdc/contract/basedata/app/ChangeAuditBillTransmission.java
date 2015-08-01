/**
 * 
 */
package com.kingdee.eas.fdc.basedata.app;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.ChangeReasonFactory;
import com.kingdee.eas.fdc.basedata.ChangeReasonInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonFactory;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo;
import com.kingdee.eas.fdc.basedata.JobTypeFactory;
import com.kingdee.eas.fdc.basedata.JobTypeInfo;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeFactory;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum;
import com.kingdee.eas.fdc.contract.GraphCountEnum;
import com.kingdee.eas.fdc.contract.OfferEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		
 *		
 * @author		朱俊
 * @version		EAS7.0		
 * @createDate	2011-6-9	 
 * @see						
 */
public class ChangeAuditBillTransmission extends AbstractDataTransmission {
	private static Logger logger = Logger.getLogger(ChangeAuditBillTransmission.class);
	/**
	 * @description		
	 * @author			朱俊		
	 * @createDate		2011-6-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#getController(com.kingdee.bos.Context)					
	 */
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = ChangeAuditBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * @description		
	 * @author			朱俊		
	 * @createDate		2011-6-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)					
	 */
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		
		ChangeAuditBillInfo info = new ChangeAuditBillInfo();
		
		//组织编码
		String fOrgUnitNumber=(String) ((DataToken) hsData.get("FOrgUnit_number")).data;
		//工程项目编码
		String fCurProjectLongNumber=(String) ((DataToken) hsData.get("FCurProject_longNumber")).data;
		//单据编号
		String fNumber=(String) ((DataToken) hsData.get("FNumber")).data;
		//名称
		String fName=(String) ((DataToken) hsData.get("FName")).data;
		//状态
		String fState=(String) ((DataToken) hsData.get("FState")).data;
		//业务日期
		String fBizDate=(String) ((DataToken) hsData.get("FBizDate")).data;
		//变更类型
		String fAuditTypeName=(String) ((DataToken) hsData.get("FAuditType_name_l2")).data;
		//变更原因
		String fChangeReasonName=(String) ((DataToken) hsData.get("FChangeReason_name_l2")).data;
		//承包类型
		String fJobTypeName=(String) ((DataToken) hsData.get("FJobType_name_l2")).data;
		//变更主题
		String fChangeSubject=(String) ((DataToken) hsData.get("FChangeSubject")).data;
		//提出部门
		String fConductDeptName=(String) ((DataToken) hsData.get("FConductDept_name_l2")).data;
		//专业类型
		String fSpecialtyTypeName=(String) ((DataToken) hsData.get("FSpecialtyType_name_l2")).data;
		//紧急程度
		String fUrgentDegree=(String) ((DataToken) hsData.get("FUrgentDegree")).data;
		//提出单位
		String fConductUnitName=(String) ((DataToken) hsData.get("FConductUnit_name_l2")).data;
		//施工单位
		String fConstrUnitName=(String) ((DataToken) hsData.get("FConstrUnit_name_l2")).data;
		//设计单位
		String fDesignUnitName=(String) ((DataToken) hsData.get("FDesignUnit_name_l2")).data;
		//提出方
		String fOffer=(String) ((DataToken) hsData.get("FOffer")).data;
		//施工部位
		String fConstrSite=(String) ((DataToken) hsData.get("FConstrSite")).data;
		//原因说明
		String fReaDesc=(String) ((DataToken) hsData.get("FReaDesc")).data;
		//附图情况
		String fGraphCount=(String) ((DataToken) hsData.get("FGraphCount")).data;
		//是否重大变更
		String fIsImportChange=(String) ((DataToken) hsData.get("FIsImportChange")).data;
		//是否存在无效成本
		String fIsNoUse=(String) ((DataToken) hsData.get("FIsNoUse")).data;
		//涉及无效成本的金额
		String fCostNouse=(String) ((DataToken) hsData.get("FCostNouse")).data;
		//无效成本原因
		String fInvalidCostReasonName=(String) ((DataToken) hsData.get("FInvalidCostReason_name_l2")).data;
		//创建者
		String fCreatorName=(String) ((DataToken) hsData.get("FCreator_name_l2")).data;
		//创建时间
		String fCreateTime=(String) ((DataToken) hsData.get("FCreateTime")).data;
		//审核人
		String fAuditorName=(String) ((DataToken) hsData.get("FAuditor_name_l2")).data;
		//审批时间
		String fAuditTime=(String) ((DataToken) hsData.get("FAuditTime")).data;
		
		/** 
		 * 组织编码
		 * 工程项目所在成本中心编码，在基础资料组织单元中存在该成本中心。如果没有录入，那么从工程项目对应的成本中心获取
		 */
		if (fOrgUnitNumber != null && fOrgUnitNumber.length() > 40) {
			fOrgUnitNumber = fOrgUnitNumber.substring(0, 40);
		}
		try {
			FullOrgUnitInfo fullOrgUnitInfo = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo("where FNumber='"+fOrgUnitNumber+"'" );
			if(fullOrgUnitInfo.getNumber().trim()==null){
				CurProjectInfo curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo("where FLongNumber='"+fCurProjectLongNumber+"'");
				CostCenterOrgUnitInfo costCenterOrgUnitInfo = curProjectInfo.getCostCenter();
				fullOrgUnitInfo = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo("where FNumber='"+costCenterOrgUnitInfo.getNumber()+"'" );;
				info.setOrgUnit(fullOrgUnitInfo);
			}else {
				info.setOrgUnit(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/** * 工程项目编码*/
		if (StringUtils.isEmpty(fCurProjectLongNumber)) {
			throw new TaskExternalException("工程项目编码不能为空！");
		}
		if (fCurProjectLongNumber != null && fCurProjectLongNumber.length() > 40) {
			fCurProjectLongNumber = fCurProjectLongNumber.substring(0, 40);
		}
		try {
			CurProjectInfo curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo("where FLongNumber='"+fCurProjectLongNumber+"'" );
			if(curProjectInfo==null){
				throw new TaskExternalException("工程项目编码"+fCurProjectLongNumber+"在系统中不存在！");
			}
			else{
				info.setCurProject(curProjectInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/**  * 单据编号*/
		if (StringUtils.isEmpty(fNumber)) {
			throw new TaskExternalException("单据编号不能为空！");
		}
		if (fNumber != null && fNumber.length() > 80) {
			info.setName(fNumber.substring(0, 80));
		}

		/** * 单据名称*/
		if (StringUtils.isEmpty(fName)) {
			throw new TaskExternalException("单据名称不能为空！");
		}
		if (fName != null && fName.length() > 80) {
			info.setName(fName.substring(0, 80));
		}
		
		/**
		 * 状态定义：保存、提交、已审批、已下发、已签证、已结算。默认已审批
		 */
		if(fState.trim().equals("SAVED")){
			info.setState(FDCBillStateEnum.SAVED);
		}else if(fState.trim().equals("SUBMITTED")){
			info.setState(FDCBillStateEnum.SUBMITTED);
		}else if(fState.trim().equals("ANNOUNCE")){
			info.setState(FDCBillStateEnum.ANNOUNCE);
		}else if(fState.trim().equals("VISA")){
			info.setState(FDCBillStateEnum.VISA);
		}else if(fState.trim().equals("CANCEL")){
			info.setState(FDCBillStateEnum.CANCEL);
		}else {
			info.setState(FDCBillStateEnum.AUDITTED);
		}
		
		/** * 业务日期 */
		if (StringUtils.isEmpty(fBizDate)) {
			throw new TaskExternalException("业务日期不能为空！");
		}
		DateFormat df = null;
		if(fBizDate.trim().length() <= "yyyy-MM-dd".length()){ // 处理 "yyyy-MM-d"
			df = new SimpleDateFormat("yyyy-MM-dd");
			Date d = null;
			try {
				d = df.parse(fBizDate);
			} catch (ParseException e) {
				//@AbortException
				e.printStackTrace();
			}
			info.setBizDate(d);
		}else{
			throw new TaskExternalException("业务日期格式不正确；确保业务日期的单元格格式为文本型，建议使用格式类似于：2011-06-10 ");
		}
		
		/** * 变更类型 */
		if (StringUtils.isEmpty(fAuditTypeName)) {
			throw new TaskExternalException("变更类型不能为空！");
		}
		try {
			ChangeTypeInfo changeTypeInfo = ChangeTypeFactory.getLocalInstance(ctx).getChangeTypeInfo("where FName='"+fAuditTypeName+"'" );
			if(changeTypeInfo==null){
				throw new TaskExternalException("变更类型"+fAuditTypeName+"在系统中不存在！");
			}
			else{
				info.setAuditType(changeTypeInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/** * 变更原因 */
		if (StringUtils.isEmpty(fChangeReasonName)) {
			throw new TaskExternalException("变更原因不能为空！");
		}
		try {
			ChangeReasonInfo changeReasonInfo = ChangeReasonFactory.getLocalInstance(ctx).getChangeReasonInfo("where FName='"+fChangeReasonName+"'" );
			if(changeReasonInfo==null){
				throw new TaskExternalException("变更原因"+fChangeReasonName+"在系统中不存在！");
			}
			else{
				info.setChangeReason(changeReasonInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/** * 承包类型 */
		if (StringUtils.isEmpty(fJobTypeName)) {
			throw new TaskExternalException("承包类型不能为空！");
		}
		try {
			JobTypeInfo jobTypeInfo = JobTypeFactory.getLocalInstance(ctx).getJobTypeInfo("where FName='"+fJobTypeName+"'" );
			if(jobTypeInfo==null){
				throw new TaskExternalException("承包类型"+fJobTypeName+"在系统中不存在！");
			}
			else{
				info.setJobType(jobTypeInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/** * 变更主题 */
		if (fChangeSubject.length() > 40) {
			info.setChangeSubject(fChangeSubject.substring(0, 40));
		}
		
		/** * 提出部门  */
		if (StringUtils.isEmpty(fConductDeptName)) {
			throw new TaskExternalException("提出部门不能为空！");
		}
		try {
			AdminOrgUnitCollection adminOrgUnitCollection = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection("where FName = '"+fConductDeptName+"'");
			if(adminOrgUnitCollection != null && adminOrgUnitCollection.size() > 0)
				info.setConductDept(adminOrgUnitCollection.get(0));
			else{
				throw new TaskExternalException("提出部门"+fConductDeptName+"在系统中不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/** * 专业类型  */
		if (StringUtils.isEmpty(fSpecialtyTypeName)) {
			throw new TaskExternalException("专业类型不能为空！");
		}
		try {
			SpecialtyTypeInfo specialtyTypeInfo = SpecialtyTypeFactory.getLocalInstance(ctx).getSpecialtyTypeInfo("where FName='"+fSpecialtyTypeName+"'" );
			if(specialtyTypeInfo==null){
				throw new TaskExternalException("专业类型"+fSpecialtyTypeName+"在系统中不存在！");
			}
			else{
				info.setSpecialtyType(specialtyTypeInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/** 
		 *  紧急程度 
		 *  普通或紧急，为空时，默认为普通
		 */
		if (StringUtils.isEmpty(fUrgentDegree)) {
			throw new TaskExternalException("紧急程度不能为空！");
		}
		if(fUrgentDegree.trim().equals("Urgent")){
			info.setUrgentDegree(ChangeUrgentDegreeEnum.Urgent);
		}else {
			info.setUrgentDegree(ChangeUrgentDegreeEnum.Normal);
		}
		
		/** 
		 *  提出单位 
		 *  提出方为“我司”时，该字段是非必录项；为其他时，是必录项
		 */
		if (OfferEnum.SELFCOM.equals(info.getOffer())) {
			if (StringUtils.isEmpty(fConductUnitName)) {
				throw new TaskExternalException("提出单位不能为空！");
			}
		}
		
		/** 施工单位*/
		try {
			SupplierInfo supplierInfo = SupplierFactory.getLocalInstance(ctx).getSupplierInfo("where FName='"+fConstrUnitName+"'" );
			if(supplierInfo != null){
				info.setConstrUnit(supplierInfo);
			}
			else{
				info.setConstrUnit(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/** 设计单位*/
		try {
			SupplierInfo supplierInfo1 = SupplierFactory.getLocalInstance(ctx).getSupplierInfo("where FName='"+fDesignUnitName+"'" );
			if(supplierInfo1 != null){
				info.setDesignUnit(supplierInfo1);
			}
			else{
				info.setDesignUnit(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/**
		 * 提出方
		 * 我司、设计公司、施工方、监理方，为空时，默认为 我司
		 */
		if(fOffer.trim().equals("DESIGNCOM")){
			info.setOffer(OfferEnum.DESIGNCOM);
		}else if(fOffer.trim().equals("CONSTRPARTY")){
			info.setOffer(OfferEnum.CONSTRPARTY);
		}else if(fOffer.trim().equals("SUPERVISER")){
			info.setOffer(OfferEnum.SUPERVISER);
		}else {
			info.setOffer(OfferEnum.SELFCOM);
		}
		
		/**施工部位*/
		if (fConstrSite.length() > 40) {
			info.setConstrSite(fConstrSite.substring(0, 40));
		}
		
		/**原因说明*/
		if (fReaDesc.length() > 40) {
			info.setReaDesc(fReaDesc.substring(0, 40));
		}
		
		/**
		 * 附图情况
		 * 无文件、电子文件、纸质文件。为空时，默认为无文件
		 */
		if(fGraphCount.trim().equals("ElectFile")){
			info.setGraphCount(GraphCountEnum.ElectFile);
		}else if(fGraphCount.trim().equals("PaperFile")){
			info.setGraphCount(GraphCountEnum.PaperFile);
		}else {
			info.setGraphCount(GraphCountEnum.NoFile);
		}
		
		/**
		 * 是否重大变更
		 * 是 or 否，为空时，默认为 否
		 */
		if(fIsImportChange.trim().equals("是") ||fIsImportChange.trim().equals("true")){
			info.setIsImportChange(true);
		}else {
			info.setIsImportChange(false);
		}
		
		/**是否存在无效成本
		 * 是 or 否，为空时，默认为 否
		 */
			if(fIsNoUse.trim().equals("是") ||fIsNoUse.trim().equals("true")){
				info.setIsNoUse(true);
			}else {
				info.setIsNoUse(false);
			}
		
		/**
		 * 无效成本金额
		 * 存在无效成本时，该字段为必录项，做空值校验。否则不做校验
		 */
		if(fIsNoUse.trim().equals("是")||fIsNoUse.trim().equals("true")) {
			if (StringUtils.isEmpty(fCostNouse)) {
				throw new TaskExternalException("当存在无效成本时，无效成本金额不能为空！");
			}
		}
		
		/**无效成本原因*/
		try {
			InvalidCostReasonInfo invalidCostReasonInfo = InvalidCostReasonFactory.getLocalInstance(ctx).getInvalidCostReasonInfo("where FName='"+fInvalidCostReasonName+"'" );
			if(invalidCostReasonInfo != null){
				info.setInvalidCostReason(invalidCostReasonInfo);
			}
			else{
				info.setInvalidCostReason(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/**制单人名称*/
		if (StringUtils.isEmpty(fCreatorName)) {
			throw new TaskExternalException("制单人名称不能为空！");
		}
		this.getUserInfo(ctx, fCreatorName);
		
		/**制单时间*/
		if (StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException("制单时间不能为空！");
		}
		DateFormat df1 = null;
		if(fCreateTime.trim().length() <= "yyyy-MM-dd".length()){ // 处理 "yyyy-MM-d"
			df1 = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = null;
			try {
				d1 = df1.parse(fCreateTime.trim());
				info.setCreateTime(new Timestamp(d1.getTime()));
			} catch (ParseException e) {
				logger.info(e.getMessage(), e);
				throw new TaskExternalException("制单时间格式不正确；确保制单时间的单元格格式为文本型，建议使用格式类似于：2011-06-10 ");
			}
		}else{
			throw new TaskExternalException("制单时间格式不正确；确保制单时间的单元格格式为文本型，建议使用格式类似于：2011-06-10 ");
		}
		
		/**
		 * 审核人名称
		 * 单据状态为已审批、已下发、已签证、已结算时，该字段为必录项，为空或在系统中找不到时给提示。单据状态为保存、提交时，该字段为非必录项。
		 */
		if(fState.trim().equals("AUDITTED") || fState.trim().equals("ANNOUNCE")|| fState.trim().equals("VISA")|| fState.trim().equals("CANCEL") ) {
			if (StringUtils.isEmpty(fAuditorName)) {
				throw new TaskExternalException("审核人名称不能为空！");
			}
			this.getUserInfo(ctx, fAuditorName);
		
		}
		
		/**审核时间
		 * 单据状态为已审批、已下发、已签证、已结算时，该字段为必录项，为空或格式不对时给提示。单据状态为保存、提交时，该字段为非必录项。
		 */
		if(fState.trim().equals("AUDITTED") || fState.trim().equals("ANNOUNCE")|| fState.trim().equals("VISA")|| fState.trim().equals("CANCEL") ) {
			if (StringUtils.isEmpty(fAuditTime)) {
				throw new TaskExternalException("审核时间不能为空！");
			}
			DateFormat df2 = null;
			if(fAuditTime.trim().length() <= "yyyy-MM-dd".length()){ // 处理 "yyyy-MM-d"
				df2 = new SimpleDateFormat("yyyy-MM-dd");
				Date d2 = null;
				try {
					d2 = df2.parse(fAuditTime);
				} catch (ParseException e) {
					e.printStackTrace();
					throw new TaskExternalException("审核时间格式不正确；确保审核时间的单元格格式为文本型，建议使用格式类似于：2011-06-10 ", e);
				}
				info.setAuditTime(d2);
			}else{
				throw new TaskExternalException("审核时间格式不正确；确保审核时间的单元格格式为文本型，建议使用格式类似于：2011-06-10 ");
			}
		}
		return info;
	}

	/**
	 * 
	 * @description		获得创建人/审核人的名字信息
	 * @author			朱俊	
	 * @createDate		2011-6-11
	 * @param ctx
	 * @param userName
	 * @return
	 * @throws TaskExternalException UserInfo
	 * @version			EAS1.0
	 * @see
	 */
	private UserInfo getUserInfo(Context ctx,String userName) throws TaskExternalException
	{
		try {
			UserInfo userInfo = UserFactory.getLocalInstance(ctx).getUserInfo("where FName='"+userName+"'" );
			if(userInfo==null){
				throw new TaskExternalException(userName+"在系统中不存在！");
			}
			else{
				return userInfo;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new TaskExternalException(e.getMessage(), e);
		}
	}
}
