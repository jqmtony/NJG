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
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		
 *		
 * @author		�쿡
 * @version		EAS7.0		
 * @createDate	2011-6-9	 
 * @see						
 */
public class ChangeAuditBillTransmission extends AbstractDataTransmission {
	private static Logger logger = Logger.getLogger(ChangeAuditBillTransmission.class);
	/**
	 * @description		
	 * @author			�쿡		
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
	 * @author			�쿡		
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
		
		//��֯����
		String fOrgUnitNumber=(String) ((DataToken) hsData.get("FOrgUnit_number")).data;
		//������Ŀ����
		String fCurProjectLongNumber=(String) ((DataToken) hsData.get("FCurProject_longNumber")).data;
		//���ݱ��
		String fNumber=(String) ((DataToken) hsData.get("FNumber")).data;
		//����
		String fName=(String) ((DataToken) hsData.get("FName")).data;
		//״̬
		String fState=(String) ((DataToken) hsData.get("FState")).data;
		//ҵ������
		String fBizDate=(String) ((DataToken) hsData.get("FBizDate")).data;
		//�������
		String fAuditTypeName=(String) ((DataToken) hsData.get("FAuditType_name_l2")).data;
		//���ԭ��
		String fChangeReasonName=(String) ((DataToken) hsData.get("FChangeReason_name_l2")).data;
		//�а�����
		String fJobTypeName=(String) ((DataToken) hsData.get("FJobType_name_l2")).data;
		//�������
		String fChangeSubject=(String) ((DataToken) hsData.get("FChangeSubject")).data;
		//�������
		String fConductDeptName=(String) ((DataToken) hsData.get("FConductDept_name_l2")).data;
		//רҵ����
		String fSpecialtyTypeName=(String) ((DataToken) hsData.get("FSpecialtyType_name_l2")).data;
		//�����̶�
		String fUrgentDegree=(String) ((DataToken) hsData.get("FUrgentDegree")).data;
		//�����λ
		String fConductUnitName=(String) ((DataToken) hsData.get("FConductUnit_name_l2")).data;
		//ʩ����λ
		String fConstrUnitName=(String) ((DataToken) hsData.get("FConstrUnit_name_l2")).data;
		//��Ƶ�λ
		String fDesignUnitName=(String) ((DataToken) hsData.get("FDesignUnit_name_l2")).data;
		//�����
		String fOffer=(String) ((DataToken) hsData.get("FOffer")).data;
		//ʩ����λ
		String fConstrSite=(String) ((DataToken) hsData.get("FConstrSite")).data;
		//ԭ��˵��
		String fReaDesc=(String) ((DataToken) hsData.get("FReaDesc")).data;
		//��ͼ���
		String fGraphCount=(String) ((DataToken) hsData.get("FGraphCount")).data;
		//�Ƿ��ش���
		String fIsImportChange=(String) ((DataToken) hsData.get("FIsImportChange")).data;
		//�Ƿ������Ч�ɱ�
		String fIsNoUse=(String) ((DataToken) hsData.get("FIsNoUse")).data;
		//�漰��Ч�ɱ��Ľ��
		String fCostNouse=(String) ((DataToken) hsData.get("FCostNouse")).data;
		//��Ч�ɱ�ԭ��
		String fInvalidCostReasonName=(String) ((DataToken) hsData.get("FInvalidCostReason_name_l2")).data;
		//������
		String fCreatorName=(String) ((DataToken) hsData.get("FCreator_name_l2")).data;
		//����ʱ��
		String fCreateTime=(String) ((DataToken) hsData.get("FCreateTime")).data;
		//�����
		String fAuditorName=(String) ((DataToken) hsData.get("FAuditor_name_l2")).data;
		//����ʱ��
		String fAuditTime=(String) ((DataToken) hsData.get("FAuditTime")).data;
		
		/** 
		 * ��֯����
		 * ������Ŀ���ڳɱ����ı��룬�ڻ���������֯��Ԫ�д��ڸóɱ����ġ����û��¼�룬��ô�ӹ�����Ŀ��Ӧ�ĳɱ����Ļ�ȡ
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
		
		/** * ������Ŀ����*/
		if (StringUtils.isEmpty(fCurProjectLongNumber)) {
			throw new TaskExternalException("������Ŀ���벻��Ϊ�գ�");
		}
		if (fCurProjectLongNumber != null && fCurProjectLongNumber.length() > 40) {
			fCurProjectLongNumber = fCurProjectLongNumber.substring(0, 40);
		}
		try {
			CurProjectInfo curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo("where FLongNumber='"+fCurProjectLongNumber+"'" );
			if(curProjectInfo==null){
				throw new TaskExternalException("������Ŀ����"+fCurProjectLongNumber+"��ϵͳ�в����ڣ�");
			}
			else{
				info.setCurProject(curProjectInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/**  * ���ݱ��*/
		if (StringUtils.isEmpty(fNumber)) {
			throw new TaskExternalException("���ݱ�Ų���Ϊ�գ�");
		}
		if (fNumber != null && fNumber.length() > 80) {
			info.setName(fNumber.substring(0, 80));
		}

		/** * ��������*/
		if (StringUtils.isEmpty(fName)) {
			throw new TaskExternalException("�������Ʋ���Ϊ�գ�");
		}
		if (fName != null && fName.length() > 80) {
			info.setName(fName.substring(0, 80));
		}
		
		/**
		 * ״̬���壺���桢�ύ�������������·�����ǩ֤���ѽ��㡣Ĭ��������
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
		
		/** * ҵ������ */
		if (StringUtils.isEmpty(fBizDate)) {
			throw new TaskExternalException("ҵ�����ڲ���Ϊ�գ�");
		}
		DateFormat df = null;
		if(fBizDate.trim().length() <= "yyyy-MM-dd".length()){ // ���� "yyyy-MM-d"
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
			throw new TaskExternalException("ҵ�����ڸ�ʽ����ȷ��ȷ��ҵ�����ڵĵ�Ԫ���ʽΪ�ı��ͣ�����ʹ�ø�ʽ�����ڣ�2011-06-10 ");
		}
		
		/** * ������� */
		if (StringUtils.isEmpty(fAuditTypeName)) {
			throw new TaskExternalException("������Ͳ���Ϊ�գ�");
		}
		try {
			ChangeTypeInfo changeTypeInfo = ChangeTypeFactory.getLocalInstance(ctx).getChangeTypeInfo("where FName='"+fAuditTypeName+"'" );
			if(changeTypeInfo==null){
				throw new TaskExternalException("�������"+fAuditTypeName+"��ϵͳ�в����ڣ�");
			}
			else{
				info.setAuditType(changeTypeInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/** * ���ԭ�� */
		if (StringUtils.isEmpty(fChangeReasonName)) {
			throw new TaskExternalException("���ԭ����Ϊ�գ�");
		}
		try {
			ChangeReasonInfo changeReasonInfo = ChangeReasonFactory.getLocalInstance(ctx).getChangeReasonInfo("where FName='"+fChangeReasonName+"'" );
			if(changeReasonInfo==null){
				throw new TaskExternalException("���ԭ��"+fChangeReasonName+"��ϵͳ�в����ڣ�");
			}
			else{
				info.setChangeReason(changeReasonInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/** * �а����� */
		if (StringUtils.isEmpty(fJobTypeName)) {
			throw new TaskExternalException("�а����Ͳ���Ϊ�գ�");
		}
		try {
			JobTypeInfo jobTypeInfo = JobTypeFactory.getLocalInstance(ctx).getJobTypeInfo("where FName='"+fJobTypeName+"'" );
			if(jobTypeInfo==null){
				throw new TaskExternalException("�а�����"+fJobTypeName+"��ϵͳ�в����ڣ�");
			}
			else{
				info.setJobType(jobTypeInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/** * ������� */
		if (fChangeSubject.length() > 40) {
			info.setChangeSubject(fChangeSubject.substring(0, 40));
		}
		
		/** * �������  */
		if (StringUtils.isEmpty(fConductDeptName)) {
			throw new TaskExternalException("������Ų���Ϊ�գ�");
		}
		try {
			AdminOrgUnitCollection adminOrgUnitCollection = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection("where FName = '"+fConductDeptName+"'");
			if(adminOrgUnitCollection != null && adminOrgUnitCollection.size() > 0)
				info.setConductDept(adminOrgUnitCollection.get(0));
			else{
				throw new TaskExternalException("�������"+fConductDeptName+"��ϵͳ�в����ڣ�");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/** * רҵ����  */
		if (StringUtils.isEmpty(fSpecialtyTypeName)) {
			throw new TaskExternalException("רҵ���Ͳ���Ϊ�գ�");
		}
		try {
			SpecialtyTypeInfo specialtyTypeInfo = SpecialtyTypeFactory.getLocalInstance(ctx).getSpecialtyTypeInfo("where FName='"+fSpecialtyTypeName+"'" );
			if(specialtyTypeInfo==null){
				throw new TaskExternalException("רҵ����"+fSpecialtyTypeName+"��ϵͳ�в����ڣ�");
			}
			else{
				info.setSpecialtyType(specialtyTypeInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
		
		/** 
		 *  �����̶� 
		 *  ��ͨ�������Ϊ��ʱ��Ĭ��Ϊ��ͨ
		 */
		if (StringUtils.isEmpty(fUrgentDegree)) {
			throw new TaskExternalException("�����̶Ȳ���Ϊ�գ�");
		}
		if(fUrgentDegree.trim().equals("Urgent")){
			info.setUrgentDegree(ChangeUrgentDegreeEnum.Urgent);
		}else {
			info.setUrgentDegree(ChangeUrgentDegreeEnum.Normal);
		}
		
		/** 
		 *  �����λ 
		 *  �����Ϊ����˾��ʱ�����ֶ��ǷǱ�¼�Ϊ����ʱ���Ǳ�¼��
		 */
		if (OfferEnum.SELFCOM.equals(info.getOffer())) {
			if (StringUtils.isEmpty(fConductUnitName)) {
				throw new TaskExternalException("�����λ����Ϊ�գ�");
			}
		}
		
		/** ʩ����λ*/
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
		
		/** ��Ƶ�λ*/
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
		 * �����
		 * ��˾����ƹ�˾��ʩ������������Ϊ��ʱ��Ĭ��Ϊ ��˾
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
		
		/**ʩ����λ*/
		if (fConstrSite.length() > 40) {
			info.setConstrSite(fConstrSite.substring(0, 40));
		}
		
		/**ԭ��˵��*/
		if (fReaDesc.length() > 40) {
			info.setReaDesc(fReaDesc.substring(0, 40));
		}
		
		/**
		 * ��ͼ���
		 * ���ļ��������ļ���ֽ���ļ���Ϊ��ʱ��Ĭ��Ϊ���ļ�
		 */
		if(fGraphCount.trim().equals("ElectFile")){
			info.setGraphCount(GraphCountEnum.ElectFile);
		}else if(fGraphCount.trim().equals("PaperFile")){
			info.setGraphCount(GraphCountEnum.PaperFile);
		}else {
			info.setGraphCount(GraphCountEnum.NoFile);
		}
		
		/**
		 * �Ƿ��ش���
		 * �� or ��Ϊ��ʱ��Ĭ��Ϊ ��
		 */
		if(fIsImportChange.trim().equals("��") ||fIsImportChange.trim().equals("true")){
			info.setIsImportChange(true);
		}else {
			info.setIsImportChange(false);
		}
		
		/**�Ƿ������Ч�ɱ�
		 * �� or ��Ϊ��ʱ��Ĭ��Ϊ ��
		 */
			if(fIsNoUse.trim().equals("��") ||fIsNoUse.trim().equals("true")){
				info.setIsNoUse(true);
			}else {
				info.setIsNoUse(false);
			}
		
		/**
		 * ��Ч�ɱ����
		 * ������Ч�ɱ�ʱ�����ֶ�Ϊ��¼�����ֵУ�顣������У��
		 */
		if(fIsNoUse.trim().equals("��")||fIsNoUse.trim().equals("true")) {
			if (StringUtils.isEmpty(fCostNouse)) {
				throw new TaskExternalException("��������Ч�ɱ�ʱ����Ч�ɱ�����Ϊ�գ�");
			}
		}
		
		/**��Ч�ɱ�ԭ��*/
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
		
		/**�Ƶ�������*/
		if (StringUtils.isEmpty(fCreatorName)) {
			throw new TaskExternalException("�Ƶ������Ʋ���Ϊ�գ�");
		}
		this.getUserInfo(ctx, fCreatorName);
		
		/**�Ƶ�ʱ��*/
		if (StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException("�Ƶ�ʱ�䲻��Ϊ�գ�");
		}
		DateFormat df1 = null;
		if(fCreateTime.trim().length() <= "yyyy-MM-dd".length()){ // ���� "yyyy-MM-d"
			df1 = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = null;
			try {
				d1 = df1.parse(fCreateTime.trim());
				info.setCreateTime(new Timestamp(d1.getTime()));
			} catch (ParseException e) {
				logger.info(e.getMessage(), e);
				throw new TaskExternalException("�Ƶ�ʱ���ʽ����ȷ��ȷ���Ƶ�ʱ��ĵ�Ԫ���ʽΪ�ı��ͣ�����ʹ�ø�ʽ�����ڣ�2011-06-10 ");
			}
		}else{
			throw new TaskExternalException("�Ƶ�ʱ���ʽ����ȷ��ȷ���Ƶ�ʱ��ĵ�Ԫ���ʽΪ�ı��ͣ�����ʹ�ø�ʽ�����ڣ�2011-06-10 ");
		}
		
		/**
		 * ���������
		 * ����״̬Ϊ�����������·�����ǩ֤���ѽ���ʱ�����ֶ�Ϊ��¼�Ϊ�ջ���ϵͳ���Ҳ���ʱ����ʾ������״̬Ϊ���桢�ύʱ�����ֶ�Ϊ�Ǳ�¼�
		 */
		if(fState.trim().equals("AUDITTED") || fState.trim().equals("ANNOUNCE")|| fState.trim().equals("VISA")|| fState.trim().equals("CANCEL") ) {
			if (StringUtils.isEmpty(fAuditorName)) {
				throw new TaskExternalException("��������Ʋ���Ϊ�գ�");
			}
			this.getUserInfo(ctx, fAuditorName);
		
		}
		
		/**���ʱ��
		 * ����״̬Ϊ�����������·�����ǩ֤���ѽ���ʱ�����ֶ�Ϊ��¼�Ϊ�ջ��ʽ����ʱ����ʾ������״̬Ϊ���桢�ύʱ�����ֶ�Ϊ�Ǳ�¼�
		 */
		if(fState.trim().equals("AUDITTED") || fState.trim().equals("ANNOUNCE")|| fState.trim().equals("VISA")|| fState.trim().equals("CANCEL") ) {
			if (StringUtils.isEmpty(fAuditTime)) {
				throw new TaskExternalException("���ʱ�䲻��Ϊ�գ�");
			}
			DateFormat df2 = null;
			if(fAuditTime.trim().length() <= "yyyy-MM-dd".length()){ // ���� "yyyy-MM-d"
				df2 = new SimpleDateFormat("yyyy-MM-dd");
				Date d2 = null;
				try {
					d2 = df2.parse(fAuditTime);
				} catch (ParseException e) {
					e.printStackTrace();
					throw new TaskExternalException("���ʱ���ʽ����ȷ��ȷ�����ʱ��ĵ�Ԫ���ʽΪ�ı��ͣ�����ʹ�ø�ʽ�����ڣ�2011-06-10 ", e);
				}
				info.setAuditTime(d2);
			}else{
				throw new TaskExternalException("���ʱ���ʽ����ȷ��ȷ�����ʱ��ĵ�Ԫ���ʽΪ�ı��ͣ�����ʹ�ø�ʽ�����ڣ�2011-06-10 ");
			}
		}
		return info;
	}

	/**
	 * 
	 * @description		��ô�����/����˵�������Ϣ
	 * @author			�쿡	
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
				throw new TaskExternalException(userName+"��ϵͳ�в����ڣ�");
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
