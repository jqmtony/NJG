package com.kingdee.eas.fdc.basedata.app;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IBoAttchAsso;
import com.kingdee.eas.base.codingrule.CodingRuleEntryCollection;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.util.FdcCodingRuleUtil;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcEntityViewUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectValueUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

/**
 * 
 * 描述:房地产单据基类
 * 
 * @author liupd date:2006-10-13
 *         <p>
 * @version EAS5.1.3
 */
public class FDCBillControllerBean extends AbstractFDCBillControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean");

	//默认采用编码。
    protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        FDCBillInfo info = (FDCBillInfo)super._getValue(ctx,pk);
        String retValue = "";
        if(info.getNumber()!= null)
        {
        	retValue = info.getNumber();
            if(info.getName()!=null){
            	retValue = retValue + " " + info.getName();
            }
        }
        return retValue;
    }

    protected IObjectPK _addnew(Context ctx , IObjectValue model) throws BOSException , EASBizException
	{
    	FDCBillInfo fDCBillInfo = ((FDCBillInfo) model);
    	
		//取消名称的前后空格
		trimName(fDCBillInfo);
		
		//设置单据的一些属性
		setProps(ctx, fDCBillInfo);
		
		//处理期间
		dealPeriod(ctx, fDCBillInfo);	
		
		//处理原币
		dealAmount(ctx, fDCBillInfo);
		
    	return super._addnew(ctx,model);
	}
    
    //保存
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		FDCBillInfo fDCBillInfo = ((FDCBillInfo) model);

		if (fDCBillInfo.getState() == null)
			fDCBillInfo.setState(FDCBillStateEnum.SAVED);

		//		handleIntermitNumber(ctx,fDCBillInfo);//处理断号
		//设置单据的一些属性
		setPropsForBill(ctx, fDCBillInfo);

		//属性字段参与编码规则，如果值发生改变，则需要重新获取编码
		boolean flag = reHandleIntermitNumberByProperty(ctx, model);

		// 处理断号
		if (!flag) {
			if (fDCBillInfo.getId() == null || !this._exists(ctx, new ObjectUuidPK(fDCBillInfo.getId()))) {
				handleIntermitNumber(ctx, fDCBillInfo);
			}
		}

		//检查单据
		checkBill(ctx, model);

		//取消名称的前后空格
		trimName(fDCBillInfo);

		//处理期间
		dealPeriod(ctx, fDCBillInfo);

		super._save(ctx, pk, fDCBillInfo);
	}
	
	//保存
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		FDCBillInfo fDCBillInfo = ((FDCBillInfo) model);
		
		if(fDCBillInfo.getState() == null){
			fDCBillInfo.setState(FDCBillStateEnum.SAVED);
		}
//		handleIntermitNumber(ctx,fDCBillInfo);//处理断号
		
		//设置单据的一些属性
		setPropsForBill(ctx, fDCBillInfo);
	
		//属性字段参与编码规则，如果值发生改变，则需要重新获取编码
		boolean flag = reHandleIntermitNumberByProperty(ctx, model);

		// 处理断号
		if (!flag) {
			if (fDCBillInfo.getId() == null || !this._exists(ctx, new ObjectUuidPK(fDCBillInfo.getId())) || fDCBillInfo.getNumber() == null
					|| fDCBillInfo.getNumber().length() == 0) {
				handleIntermitNumber(ctx, fDCBillInfo);
			}
		}

		//检查单据
		checkBill(ctx, model);

		//取消名称的前后空格
		trimName(fDCBillInfo);
		 
		//处理期间
		dealPeriod(ctx, fDCBillInfo); 
		
		return super._save(ctx, fDCBillInfo);
	}

	//提交
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);
		FDCBillInfo.setState(FDCBillStateEnum.SUBMITTED);
//		handleIntermitNumber(ctx,FDCBillInfo);//处理断号
		
		//设置单据的一些属性
		setPropsForBill(ctx, FDCBillInfo);
		
		//属性字段参与编码规则，如果值发生改变，则需要重新获取编码
		boolean flag = reHandleIntermitNumberByProperty(ctx, model);

		// 处理断号
		if (!flag) {
			if (FDCBillInfo.getId() == null || !this._exists(ctx, new ObjectUuidPK(FDCBillInfo.getId())) || FDCBillInfo.getNumber() == null
					|| "".equals(FDCBillInfo.getNumber().trim())) {
				handleIntermitNumber(ctx, FDCBillInfo);
			}
		}
		 
		//检查单据
		checkBill(ctx, model);
		
		//取消名称的前后空格
		trimName(FDCBillInfo);
				 
		//处理期间
		dealPeriod(ctx, FDCBillInfo); 
			
		super._submit(ctx, pk, FDCBillInfo);
	}

	//提交
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);
		FDCBillInfo.setState(FDCBillStateEnum.SUBMITTED);
//		handleIntermitNumber(ctx,FDCBillInfo);//处理断号
		
		//设置单据的一些属性
		setPropsForBill(ctx, FDCBillInfo);

		//属性字段参与编码规则，如果值发生改变，则需要重新获取编码
		boolean flag = reHandleIntermitNumberByProperty(ctx, model);

		// 处理断号
		if (!flag) {
			if (FDCBillInfo.getId() == null || !this._exists(ctx, new ObjectUuidPK(FDCBillInfo.getId())) || FDCBillInfo.getNumber() == null
					|| "".equals(FDCBillInfo.getNumber().trim())) {
				handleIntermitNumber(ctx, FDCBillInfo);
			}
		}

		//检查单据
		checkBill(ctx, model);
		//取消名称的前后空格
		trimName(FDCBillInfo);

		//处理期间
		dealPeriod(ctx, FDCBillInfo);
		
		return super._submit(ctx, FDCBillInfo);
	}


	//设置单据的一些属性，对于导入单据可能组织设置存在问题，必须取工程项目对应的成本中心
	protected void setPropsForBill(Context ctx, FDCBillInfo fDCBillInfo) throws EASBizException, BOSException {
	
		CurProjectInfo projectInfo = null;
		if(fDCBillInfo.getOrgUnit() == null) {			
			if(fDCBillInfo.get("curProject")!=null){
				projectInfo =(CurProjectInfo)fDCBillInfo.get("curProject");
				if( projectInfo.getCostCenter()==null || projectInfo.getCU()==null){
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("CU.id");
					sic.add("costCenter.id");				
					projectInfo = CurProjectFactory.getLocalInstance(ctx)
						.getCurProjectInfo(new ObjectUuidPK(projectInfo.getId().toString()),sic);
				}
				if(projectInfo.getCostCenter()!=null) {
					FullOrgUnitInfo orgUnit = projectInfo.getCostCenter().castToFullOrgUnitInfo();
					if (fDCBillInfo.get("botpFrom") == null){
						fDCBillInfo.setOrgUnit(orgUnit);
					}
				}
			}else{
				FullOrgUnitInfo orgUnit = ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo();
		
				if (fDCBillInfo.get("botpFrom") == null){
					fDCBillInfo.setOrgUnit(orgUnit);
				}
			}
		}
		if(fDCBillInfo.getCU() == null) {
			if(fDCBillInfo.get("curProject")!=null){
				if(projectInfo==null){
					projectInfo =(CurProjectInfo)fDCBillInfo.get("curProject");
				}
				if(projectInfo.getCU()==null){
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("CU.id");				
					projectInfo = CurProjectFactory.getLocalInstance(ctx)
						.getCurProjectInfo(new ObjectUuidPK(projectInfo.getId().toString()),sic);
				}				
				fDCBillInfo.setCU(projectInfo.getCU());
				
			}else{
				CtrlUnitInfo currentCtrlUnit = ContextUtil.getCurrentCtrlUnit(ctx);
				fDCBillInfo.setCU(currentCtrlUnit);
			}
		}		
	}
	
	//处理期间
	protected void dealPeriod(Context ctx, FDCBillInfo fDCBillInfo) throws EASBizException, BOSException {
		
		if(fDCBillInfo.getBookedDate()==null ){
			if(fDCBillInfo.getCreateTime()==null){
				//fDCBillInfo.setCreateTime();
				fDCBillInfo.setCreateTime(new Timestamp(new Date().getTime()));
			}
			fDCBillInfo.setBookedDate(fDCBillInfo.getCreateTime());
		}
		
		//期间为空，则进行处理
		if(fDCBillInfo.getPeriod()==null && fDCBillInfo.get("curProject")!=null){
			CurProjectInfo projectInfo =(CurProjectInfo)fDCBillInfo.get("curProject");
			PeriodInfo period = FDCUtils.getCurrentPeriod(ctx,projectInfo.getId().toString(),isCost());			
			fDCBillInfo.setPeriod(period);
		}
	}

	//处理原币
	protected void dealAmount(Context ctx, FDCBillInfo fDCBillInfo) throws EASBizException, BOSException {
		if(fDCBillInfo.getOriginalAmount()==null ){
			fDCBillInfo.setOriginalAmount(fDCBillInfo.getAmount());
		}
	}
	
	/*protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {

		FDCBillInfo fDCBillInfo = ((FDCBillInfo) model);
		handleIntermitNumber(ctx,fDCBillInfo);//处理断号
		setPropsForBill(ctx, fDCBillInfo);

		checkBill(ctx, model);

		trimName(fDCBillInfo);
		
		super._addnew(ctx, pk, fDCBillInfo);
	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		FDCBillInfo fDCBillInfo = ((FDCBillInfo) model);
		handleIntermitNumber(ctx,fDCBillInfo);//处理断号
		setPropsForBill(ctx, fDCBillInfo);

		checkBill(ctx, model);
		
		trimName(fDCBillInfo);
		
		return super._addnew(ctx, fDCBillInfo);
	}*/

	/**
	 * 取消名称的前后空格
	 * @param fDCBillInfo
	 */
	//update by david_yang PT043562 2011.04.02 (扩充name到255个字符)
	protected void trimName(FDCBillInfo fDCBillInfo) {
		if(fDCBillInfo.getName() != null) {
			fDCBillInfo.setName(fDCBillInfo.getName().trim());
			
			if(fDCBillInfo.getName().length()>255) {
				fDCBillInfo.setName(fDCBillInfo.getName().substring(0,255));
			}
		}

	}

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		//此处多余,新增或者提交,应该调用submit,在submit中校验
//		checkBill(ctx, model);
//		
//		trimName((FDCBillInfo)model);
		super._update(ctx, pk, model);
	}

	/**
	 * 
	 * 描述：审批通过
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.app.AbstractFDCBillControllerBean#_audit(com.kingdee.bos.Context,
	 *      java.util.List)
	 */
	protected void _audit(Context ctx, List idList) throws BOSException,
			EASBizException {

		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();

			audit(ctx, BOSUuid.read(id));

		}

	}

	/**
	 * 
	 * 描述：反审批
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.app.AbstractFDCBillControllerBean#_unAudit(com.kingdee.bos.Context,
	 *      java.util.List)
	 */
	protected void _unAudit(Context ctx, List idList) throws BOSException,
			EASBizException {
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();

			unAudit(ctx, BOSUuid.read(id));

		}

	}

	/**
	 * 
	 * 描述：检查名称重复
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd 创建时间：2006-8-24
	 *               <p>
	 */
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
		if(!isUseName()) return;
		
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("name", billInfo.getName()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
		filter.getFilterItems()
				.add(new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
								.getId()));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NAME_DUP);
		}
	}

	/**
	 * 
	 * 描述：检查编码重复<p>
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @author liupd 创建时间：2006-8-24
	 *               <p>
	 */
	protected void checkNumberDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
		if(!isUseNumber()) return;
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("number", billInfo.getNumber()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));
		if (billInfo.getOrgUnit() != null){
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit().getId()));
		}
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}
	}

	/**
	 * 
	 * 描述：检查单据
	 * 
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd 创建时间：2006-10-13
	 *               <p>
	 */
	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);

		checkNumberDup(ctx, FDCBillInfo);

		checkNameDup(ctx, FDCBillInfo);

	}
	
	//审核时检查单据
	private void checkBillForAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo)throws BOSException, EASBizException {
		
	}
	
	//反审核时检查单据
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo)throws BOSException, EASBizException {
		
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();
		
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		billInfo.setAuditTime(new Date());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		
		//审核时检查单据
		checkBillForAudit( ctx,billId,billInfo);

		_updatePartial(ctx, billInfo, selector);
	}

	protected void _setAudittingStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		//update by david_yang R110421-081 2011.04.28
		
		FDCBillInfo billInfo = new FDCBillInfo();
		
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTING);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		billInfo.setAuditTime(new Date());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
	
		_updatePartial(ctx, billInfo, selector);
	}

	//反审核
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();

		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");

		//审核时检查单据
		checkBillForUnAudit( ctx,billId,billInfo);
		
		_updatePartial(ctx, billInfo, selector);
	}

	
	/**
	 * 
	 * 描述：成本月结
	 * @return
	 * @author:liupd
	 * 创建时间：2006-10-17 <p>
	 */
	protected boolean isCost() {
		return true;
	}
	
	/**
	 * 
	 * 描述：是否使用名称字段
	 * @return
	 * @author:liupd
	 * 创建时间：2006-10-17 <p>
	 */
	protected boolean isUseName() {
		return true;
	}
	
	/**
	 * 
	 * 描述：是否使用编码字段
	 * @return
	 * @author:liupd
	 * 创建时间：2006-10-17 <p>
	 */
	protected boolean isUseNumber() {
		return true;
	}
	
	
	/**
	 * 处理
	 * @author sxhong  		Date 2006-11-25
	 */
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException
	{
//		String orgId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
//		String bindingProperty = "contractType.number";
//		// 无当前组织，或者当前组织没定义编码规则，用集团的
//		if (orgId == null || orgId.trim().length() == 0) {			
//			orgId = OrgConstants.DEF_CU_ID;
//		}
//		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
//		
//		// 有ID，且启用了编码规则
//		if (orgId != null && orgId.trim().length() > 0
//				&& iCodingRuleManager.isExist(info, orgId, bindingProperty)) {			
//			// 如果使用了"不允许断号"
//			if (iCodingRuleManager.isUseIntermitNumber(info, orgId, bindingProperty)) {
//				String numberTemp = iCodingRuleManager.getNumber(info, orgId, bindingProperty, "");
//				info.setNumber(numberTemp);				
//			} else if (iCodingRuleManager.isAddView(info, orgId, bindingProperty)){				
//				// 判断是否修改了编码,是否改大了顺序号
//				if (iCodingRuleManager.isModifiable(info, orgId, bindingProperty)) {					
//					iCodingRuleManager.checkModifiedNumber(info, orgId,info.getNumber(), bindingProperty);
//				}				
//			} else {
//				// 什么都没选,新增不显示,允许断号,业务传空number值,在此设置number
//				String numberTemp = iCodingRuleManager.getNumber(info,orgId, bindingProperty, "");
//				info.setNumber(numberTemp);
//			}
//		}		
				
		
		//如果用户在客户端手工选择了断号,则此处不必在抢号
		if(info.getNumber() != null && info.getNumber().length() > 0) return;
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		
		//对成本中心进行处理
		String costUnitId= info.getOrgUnit().getId().toString();
			//ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		
       if(StringUtils.isEmpty(costUnitId)){
    	   return;
       }
       boolean isExist = true;
       if (!iCodingRuleManager.isExist(info, costUnitId)){
    	   costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
      		if (!iCodingRuleManager.isExist(info, costUnitId)){
      			isExist = false; 
           }
      }
       if(isExist){
    	   // 选择了断号支持或者没有选择新增显示,获取并设置编号
           if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId) || !iCodingRuleManager.isAddView(info, costUnitId))
           // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
           {
               // 启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
               String number = iCodingRuleManager.getNumber(info,costUnitId);
               info.setNumber(number);
           }
       }
	}
	
	
	//合同、无文本、付款申请，已经覆盖handleIntermitNumber
	protected void handleIntermitNumberForReset(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException
	{
		FilterInfo filter = null;
		int i=0;
		// 获取最大循环次数(用于编码规则)
		int cycleMaxIndex = getCycleMaxIndex4HandleIntermitNumber();
		
		do {
			//如果编码重复重新取编码
			handleIntermitNumber1(ctx, info, i);
			
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("number", info.getNumber()));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID.getValue(),CompareType.NOTEQUALS));		
			filter.getFilterItems()
					.add(new FilterItemInfo("orgUnit.id", info.getOrgUnit().getId()));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
			}
			i++;
		} while (_exists(ctx, filter) && i < cycleMaxIndex);
	}

	/**
	 * 描述：获取最大循环次数(用于编码规则)
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-6-13
	 */
	protected int getCycleMaxIndex4HandleIntermitNumber() {
		return 1000;
	}
	
	protected void handleIntermitNumber1(Context ctx, FDCBillInfo info, int count) throws BOSException, CodingRuleException,
			EASBizException
	{
		String orgId = info.getOrgUnit().getId().toString();
		// 无当前组织，或者当前组织没定义编码规则，用集团的
		if (orgId == null || orgId.trim().length() == 0) {			
			orgId = OrgConstants.DEF_CU_ID;
		}
	    if(StringUtils.isEmpty(orgId)){
	    	return;
	    }
	    ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	    boolean isExist = true;
	    if (!iCodingRuleManager.isExist(info, orgId)){
	    	orgId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
	      	if (!iCodingRuleManager.isExist(info, orgId)){
	      		isExist = false; 
	         }
	     }
		
		// 有ID，且启用了编码规则
		if (isExist) {			
			// 如果使用了"不允许断号"
			if (iCodingRuleManager.isUseIntermitNumber(info, orgId)) {
				String numberTemp = iCodingRuleManager.getNumber(info, orgId);
				info.setNumber(numberTemp);				
			} else if (iCodingRuleManager.isAddView(info, orgId)){				
				// 判断是否修改了编码,是否改大了顺序号
				if (iCodingRuleManager.isModifiable(info, orgId)) {					
					iCodingRuleManager.checkModifiedNumber(info, orgId,info.getNumber());
				}				
			} else {
				// 什么都没选,新增不显示,允许断号,业务传空number值,在此设置number
				String numberTemp = iCodingRuleManager.getNumber(info,orgId);
				info.setNumber(numberTemp);
			}
		}	
	}

	/**
	 * 设置提交状态(审批不通过时的自动节点)
	 */
	protected void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		setBillStatus(ctx, billId, FDCBillStateEnum.SUBMITTED);
		
	}

	private void setBillStatus(Context ctx, BOSUuid billId, FDCBillStateEnum state) throws BOSException, EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();

		billInfo.setId(billId);
		billInfo.setState(state);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");

		_updatePartial(ctx, billInfo, selector);
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
	
		recycleNumber(ctx, pk);
		
		super._delete(ctx, pk);
		
		this.delAttachment(ctx, new IObjectPK[] { pk });
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {

		for (int i = 0; i < arrayPK.length; i++) {
			recycleNumber(ctx, arrayPK[i]);
		}
		
		super._delete(ctx, arrayPK);
		this.delAttachment(ctx, arrayPK);
	}

	/**
	 * 描述：删除【附件与业务对象关联】列表里的记录以及 【附件】
	 * @param ctx
	 * @param arrayPK
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author：jian_cao
	 * @CreateTime：2013-3-7
	 */
	private void delAttachment(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {

		for (int i = 0; i < arrayPK.length; i++) {

			EntityViewInfo view = new EntityViewInfo();
			
			SelectorItemCollection itemColl = new SelectorItemCollection();
			itemColl.add(new SelectorItemInfo("id"));
			itemColl.add(new SelectorItemInfo("attachment.id"));
			itemColl.add(new SelectorItemInfo("attachment.isShared"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", arrayPK[i]));
			
			view.getSelector().addObjectCollection(itemColl);
			view.setFilter(filter);
			
			//获取【附件与业务对象关联】列表记录
			BoAttchAssoCollection boAttchColl = BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(view);
			if (!boAttchColl.isEmpty()) {
				ArrayList pkList = new ArrayList();
				IBoAttchAsso iBoAttchAsso = BoAttchAssoFactory.getLocalInstance(ctx);
				//循环列表记录，记录附件ID到pks数组，并删除【附件与业务对象关联】列表的记录
				for (int j = 0; j < boAttchColl.size(); j++) {
					
					BoAttchAssoInfo boAttchAssoInfo = boAttchColl.get(j);
					AttachmentInfo attachment = (AttachmentInfo) boAttchAssoInfo.getAttachment();
					//如果不是共享附件就记录下附件ID到集合中
					if (!attachment.isIsShared()) {
						pkList.add(new ObjectUuidPK(attachment.getId()));
					}
					//删除附件和业务对象的关联关系
					iBoAttchAsso.delete(new ObjectUuidPK(boAttchAssoInfo.getId()));
				}
				
				IObjectPK[] pkArr = this.checkAttachmentIsDel(iBoAttchAsso, pkList);
				
				if (pkArr.length > 0) {
					//删除附件列表
					AttachmentFactory.getLocalInstance(ctx).delete(pkArr);
				}
			}
		}
	}

	/**
	 * 描述：检测附件列表里的附件是否可以删除，并返回可以删除的附件PK数组
	 * @param pkList
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author：jian_cao
	 * @CreateTime：2013-3-8
	 */
	private IObjectPK[] checkAttachmentIsDel(IBoAttchAsso iBoAttchAsso, ArrayList pkList) throws BOSException, EASBizException {

		ArrayList delPKSet = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItemInfo = new FilterItemInfo("attachment.id", null);
		filter.getFilterItems().add(filterItemInfo);
		view.setFilter(filter);

		for (int i = 0, size = pkList.size(); i < size; i++) {
			
			IObjectPK pk = (IObjectPK) pkList.get(i);
			filterItemInfo.setCompareValue(pk);
			//如果列表中没有附件相关的记录，就把可以删除的附件的PK放到数组中
			if (!iBoAttchAsso.exists(filter)) {
				delPKSet.add(pk);
			}
			
		}
		
		//构造删除用的IObjectPK[]数值
		IObjectPK[] pkArr = new IObjectPK[delPKSet.size()];
		for (int i = 0; i < pkArr.length; i++) {
			pkArr[i] = (IObjectPK) delPKSet.get(i);
		}
		
		return pkArr;
	}

	/**
	 * 回收Number，如果配置了编码规则并支持断号的话
	 * @param ctx
	 * @param pk
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 */
	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		FDCBillInfo info = getValueForRecycleNumber(ctx, pk);
		
		//------ 销售组织下获取成本中心为空的处理 zhicheng_jin 090319
		OrgUnitInfo currentCostUnit = ContextUtil.getCurrentOrgUnit(ctx);
		if (currentCostUnit == null) {
			currentCostUnit = ContextUtil.getCurrentSaleUnit(ctx);
		}
		//-------- over --------
		
		logger.info("===============================================================================");
		logger.info("FDCBillControllerBean.recycleNumber(),start");
		logger.info("===============================================================================");

		logger.info("FDCBillControllerBean.recycleNumber().OrgUnit:" + ContextUtil.getCurrentOrgUnit(ctx));

		String curOrgId = currentCostUnit.getId().toString();

		logger.info("FDCBillControllerBean.recycleNumber().curOrgId:" + curOrgId);
		logger.info("FDCBillControllerBean.recycleNumber().info:" + info);
		logger.info("FDCBillControllerBean.recycleNumber().info.getNumber():" + info.getNumber());
		logger.info("回收组织为：" + curOrgId + " 的编码规则。");

		if (info.getNumber() != null && info.getNumber().length() > 0) {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);

			boolean isExistCodingRule = false;
			boolean isUseIntermitNumber = false;
			boolean flag = false;

			isExistCodingRule = iCodingRuleManager.isExist(info, curOrgId);
			if (isExistCodingRule) {
				isUseIntermitNumber = iCodingRuleManager.isUseIntermitNumber(info, curOrgId);
			}

			flag = isExistCodingRule && isUseIntermitNumber;
			logger.info("iCodingRuleManager.isExist(info, curOrgId):" + isExistCodingRule);
			logger.info("iCodingRuleManager.isUseIntermitNumber(info, curOrgId):" + isUseIntermitNumber);
			logger.info("iCodingRuleManager.isExist(info, curOrgId) && iCodingRuleManager.isUseIntermitNumber(info, curOrgId):" + flag);

			if (flag) {
				iCodingRuleManager.recycleNumber(info, curOrgId, info.getNumber());
			}
		} else {
			logger.info("if(info.getNumber()!=null&&info.getNumber().length()>0),IF判断未进入");
		}

		logger.info("===============================================================================");
		logger.info("FDCBillControllerBean.recycleNumber(),end");
		logger.info("===============================================================================");
	}

    //取编码，编码规则必须设置绑定属性 
	protected String getBindingProperty() {
		return null;
	}

	/**
	 * 作废
	 */
	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		setBillStatus(ctx, BOSUuid.read(pk.toString()), FDCBillStateEnum.INVALID);
	}
	
	/**
	 * 生效
	 */
	protected void _cancelCancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		setBillStatus(ctx, BOSUuid.read(pk.toString()), FDCBillStateEnum.AUDITTED);
		
	}
	
	/**
	 * 
	 * 描述：批量作废
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.app.AbstractFDCBillControllerBean#_audit(com.kingdee.bos.Context,
	 *      java.util.List)
	 */
	protected void _cancel(Context ctx, IObjectPK[] pkArray) throws BOSException,
			EASBizException {

		for (int i = 0; i < pkArray.length; i++) {
			
			_cancel(ctx, pkArray[i]);

		}

	}
	
	/**
	 * 
	 * 描述：批量生效
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.app.AbstractFDCBillControllerBean#_audit(com.kingdee.bos.Context,
	 *      java.util.List)
	 */
	protected void _cancelCancel(Context ctx, IObjectPK[] pkArray) throws BOSException,
			EASBizException {

		for (int i = 0; i < pkArray.length; i++) {

			_cancelCancel(ctx, pkArray[i]);

		}

	}

	//单据编辑界面初始数据粗粒度方法
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		Map initMap = new HashMap();
		
		OrgUnitInfo orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		
		//工程项目
		String projectId = (String) paramMap.get("projectId");
		
		CurProjectInfo curProjectInfo = null;
		initProject( ctx,  paramMap, initMap);
		if(initMap.get(FDCConstants.FDC_INIT_PROJECT)!=null){
			curProjectInfo = (CurProjectInfo)initMap.get(FDCConstants.FDC_INIT_PROJECT);
			projectId = curProjectInfo.getId().toString();
			paramMap.put("projectId",projectId);
			initMap.put("projectId",projectId);
		}
		
		//工程项目对应的组织
		String orgUnitId = null;
		if(curProjectInfo!=null &&  curProjectInfo.getCostCenter()!=null){
			orgUnitId = curProjectInfo.getCostCenter().getId().toString();
		}else{
			orgUnitId = orgUnit.getId().toString();
		}
		
		//获得当前组织		
		FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getLocalInstance(ctx)
			.getFullOrgUnitInfo(new ObjectUuidPK(orgUnitId));
		
		initMap.put(FDCConstants.FDC_INIT_ORGUNIT,orgUnitInfo);
		
		String comId = null;
		if(curProjectInfo!=null){
			comId = curProjectInfo.getFullOrgUnit().getId().toString();
		}else{
			comId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		}
		
		//财务组织
		CompanyOrgUnitInfo company =GlUtils.getCurrentCompany(ctx,comId,null,false);		
		initMap.put(FDCConstants.FDC_INIT_COMPANY,company);
		
		//本位币
		initMap.put(FDCConstants.FDC_INIT_CURRENCY,company.getBaseCurrency());
		
		//当前期间
		if(projectId!=null ){
			if( paramMap.get("isCost")==null || ((Boolean)paramMap.get("isCost")).booleanValue() == true ){
				initPeriod( ctx, projectId, curProjectInfo,comId, initMap,true );
			}else{
				initPeriod( ctx, projectId,curProjectInfo,comId,  initMap,false );
			}
		}
	
		//日期
		Date serverDate = new Date();
		initMap.put("serverDate",serverDate);
		
		return initMap;
	}
	
	//初始化工程项目
	protected void  initProject(Context ctx, Map paramMap,Map initMap) throws EASBizException, BOSException{
		String projectId = (String) paramMap.get("projectId");
		CurProjectInfo curProjectInfo = null;		
		if(paramMap.get("contractBillId")!=null) {
			//合同单据
			String contractBillId = (String)paramMap.get("contractBillId");
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("*");
			selector.add("contractType.isWorkLoadConfirm");
			selector.add("curProject.name");
			selector.add("curProject.number");
			selector.add("curProject.longNumber");
			selector.add("curProject.codingNumber");
			selector.add("curProject.displayName");
			selector.add("curProject.parent.id");
			selector.add("curProject.parent.number");
			selector.add("curProject.parent.name");
			selector.add("curProject.fullOrgUnit.name");
			selector.add("curProject.fullOrgUnit.code");
			selector.add("curProject.costCenter.id"); //modified by zhaoqin for R130711-0070 on 2013/12/3
			selector.add("curProject.CU.name");
			selector.add("curProject.CU.number");
			selector.add("curProject.CU.code");
			selector.add("currency.number");
			selector.add("currency.name");
			selector.add("respDept.number");
			selector.add("respDept.name");
			selector.add("partB.number");
			selector.add("partB.name");
			
			BOSObjectType  contractType=new ContractBillInfo().getBOSType();
			BOSObjectType  noTextContractType=new ContractWithoutTextInfo().getBOSType();
			if(BOSUuid.read(contractBillId).getType().equals(contractType)){
				ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
				getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
				initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
				
				//工程项目
				curProjectInfo=contractBill.getCurProject();
			}else if(BOSUuid.read(contractBillId).getType().equals(noTextContractType)){
				ContractWithoutTextInfo contractBill = ContractWithoutTextFactory.getLocalInstance(ctx).
				getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
				//initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
				
				//工程项目
				curProjectInfo=contractBill.getCurProject();
			}			
		}
		
		if(curProjectInfo==null&&projectId != null) {
			SelectorItemCollection selects = new SelectorItemCollection();
			selects.add("*");
			selects.add("isWholeAgeStage");
			selects.add("parent.id");
			selects.add("parent.number");
			selects.add("parent.name");
			selects.add("fullOrgUnit.name");
			selects.add("fullOrgUnit.code");
			selects.add("costCenter");
			selects.add("CU.name");
			selects.add("CU.number");
			selects.add("CU.code");
			curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId),selects);
		}
		initMap.put(FDCConstants.FDC_INIT_PROJECT,curProjectInfo);
	}
	
	//初始化期间
	protected void  initPeriod(Context ctx,String projectId, CurProjectInfo curProjectInfo,String comId,Map initMap,boolean isCost ) throws EASBizException, BOSException{
		
		//日期
		Date bookedDate = new Date();
		
		//是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		
		if(isInCore){
			//判断是否被冻结
			boolean isFreeze = FDCUtils.isFreeze(ctx,projectId,isCost);
			initMap.put(FDCConstants.FDC_INIT_ISFREEZE,Boolean.valueOf(isFreeze));
			
			//期间
			ProjectPeriodStatusUtil._save(ctx,new ObjectUuidPK(projectId),curProjectInfo);
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,projectId,isCost);
			
			if(bookedPeriod!=null){
				initMap.put(FDCConstants.FDC_INIT_BOOKEDPERIOD,bookedPeriod);		
				
				PeriodInfo curPeriod = null;
				if(isFreeze){
					curPeriod = PeriodUtils.getNextPeriodInfo(ctx,bookedPeriod);
				}else{
					curPeriod = bookedPeriod;
				}
				
				initMap.put(FDCConstants.FDC_INIT_PERIOD,curPeriod);
				
	
				if(bookedDate.before(curPeriod.getBeginDate())){
					bookedDate = curPeriod.getBeginDate();
				}
				if(bookedDate.after(curPeriod.getEndDate())){
					bookedDate = curPeriod.getEndDate();
				}		
	
			}
		}else{
			//期间
			PeriodInfo bookedPeriod = PeriodUtils.getPeriodInfo(ctx ,bookedDate ,new ObjectUuidPK(comId));
			initMap.put(FDCConstants.FDC_INIT_PERIOD,bookedPeriod);
			initMap.put(FDCConstants.FDC_INIT_BOOKEDPERIOD,bookedPeriod);		
		}
		
		initMap.put(FDCConstants.FDC_INIT_DATE,bookedDate);
	}
	
	//序时簿获取初始速数据方法
	protected Map _fetchFilterInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
	
		return null;
	}

	//检查能否提交
	protected boolean _checkCanSubmit(Context ctx, String id) throws BOSException, EASBizException {

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		if(_exists(ctx, new ObjectUuidPK(id))){
			FDCBillInfo billInfo = (FDCBillInfo)this.getValue(ctx,new ObjectUuidPK(id),selector);
			if(FDCBillStateEnum.AUDITTED.equals(billInfo.getState())
					|| FDCBillStateEnum.AUDITTING.equals(billInfo.getState())){
				return false;	
			}else{
				return true;
			}
		}
		
		return true;
	}

	//设置单据的一些属性,成本中心和ＣＵ都从工程项目获得
	protected void setProps (Context ctx, FDCBillInfo fDCBillInfo) throws EASBizException, BOSException {

		if(fDCBillInfo.get("curProject")!=null && (fDCBillInfo.getOrgUnit() == null ||fDCBillInfo.getCU() == null )) {
			CurProjectInfo projectInfo =(CurProjectInfo)fDCBillInfo.get("curProject");
			if( projectInfo.getCostCenter()==null || projectInfo.getCU()==null){
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("CU.id");
				sic.add("costCenter.id");				
				projectInfo = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(new ObjectUuidPK(projectInfo.getId().toString()),sic);
			}
			if(fDCBillInfo.getOrgUnit() == null && projectInfo.getCostCenter()!=null) {
				FullOrgUnitInfo orgUnit = projectInfo.getCostCenter().castToFullOrgUnitInfo();
				fDCBillInfo.setOrgUnit(orgUnit);
			}
			if(fDCBillInfo.getCU() == null) {
				CtrlUnitInfo currentCtrlUnit = projectInfo.getCU();
				fDCBillInfo.setCU(currentCtrlUnit);
			}
		}		
	}


	protected void _setRespite(Context ctx, BOSUuid billId, boolean value)
			throws BOSException, EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();
		billInfo.setId(billId);
		billInfo.setIsRespite(value);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("isRespite");
		_updatePartial(ctx, billInfo, selector);
		/**
		 * 
		 * EntityObjectInfo entity = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx).getEntity(BOSUuid.read(id).getType());
		DataTableInfo table = entity.getTable();
		String tableName=table.getName();
		 * 
		 * 
		 * 
		 */
	}


	protected void _setRespite(Context ctx, List ids, boolean value)
			throws BOSException, EASBizException {
		for(int i =0; i<ids.size();i++){
			_setRespite(ctx,BOSUuid.read(ids.get(i).toString()),value);
		}
	}

	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////

	/**
	 * 描述： 属性字段参与编码规则，如果值发生改变，则需要重新获取编码
	 * @param ctx
	 * @param model
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author：skyiter_wang
	 * @CreateTime：2013-10-10
	 */
	protected boolean reHandleIntermitNumberByProperty(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		if (!isNeedReHandleIntermitNumberByProperty()) {
			return false;
		}

		FDCBillInfo newInfo = (FDCBillInfo) model;
		if (newInfo.getId() == null || !this._exists(ctx, new ObjectUuidPK(newInfo.getId())) || FdcStringUtil.isBlank(newInfo.getNumber())) {
			handleIntermitNumber(ctx, newInfo);

			return true;
		}

		//////////////////////////////////////////////////////////////////////

		String bindingProperty = getBindingProperty();
		boolean isExistBindingProperty = FdcStringUtil.isNotBlank(bindingProperty);
		logger.info("bindingProperty:" + bindingProperty);
		
		//////////////////////////////////////////////////////////////////////

		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		FDCBillInfo oldInfo = (FDCBillInfo) getValueForRecycleNumber(ctx, new ObjectUuidPK(newInfo.getId()));
		String currentOrgId = getOrgUnitId(ctx, oldInfo);

		// ////////////////////////////////////////////////////////////////////

		Object bindingPropertyOldValue = FdcObjectValueUtil.get(oldInfo, bindingProperty);
		Object bindingPropertyNewValue = FdcObjectValueUtil.get(model, bindingProperty);
		boolean isChangedBindingProperty = !FdcObjectUtil.isEquals(bindingPropertyOldValue, bindingPropertyNewValue);

		logger.info("bindingPropertyOldValue:" + bindingPropertyOldValue);
		logger.info("bindingPropertyNewValue:" + bindingPropertyNewValue);
		logger.info("isChangedBindingProperty:" + isChangedBindingProperty);

		// ////////////////////////////////////////////////////////////////////

		logger.info("oldInfo:" + oldInfo);
		logger.info("newInfo:" + model);

		// ////////////////////////////////////////////////////////////////////

		boolean isRecycleParentOrgNumber = isRecycleParentOrgNumber();
		logger.info("isRecycleParentOrgNumber:" + isRecycleParentOrgNumber);
		ArrayList orgIdList = new ArrayList();
		// 当前组织ID放在循环列表最前面
		orgIdList.add(currentOrgId);
		if (isRecycleParentOrgNumber) {
			ContractUtil.findParentOrgUnitIdToList(ctx, currentOrgId, orgIdList);
		}
		// 清除掉集合中的重复值和Null值
		FdcCollectionUtil.clearDuplicateAndNull(orgIdList);
		logger.info("orgIdList:" + orgIdList);

		//////////////////////////////////////////////////////////////////////

		boolean flag = false;
		for (int i = 0, size = orgIdList.size(); i < size; i++) {
			String orgId = orgIdList.get(i).toString();
			logger.info("回收组织为：" + orgId + "的编码规则。");

			// 是否回收编码
			flag = FdcCodingRuleUtil.isRecycleNumber(iCodingRuleManager, oldInfo, orgId, bindingProperty);
			if (!flag) {
				continue;
			}

			// ////////////////////////////////////////////////////////////////////

			// 绑定属性发生变化
			if (isChangedBindingProperty) {
				// 回收编码
				if (FdcCodingRuleUtil.recycleNumber(iCodingRuleManager, oldInfo, orgId, bindingProperty, "", oldInfo.getNumber())) {
				}

				// 重新生成编码
				handleIntermitNumber(ctx, newInfo);

				// 跳出循环
				break;
			}

			// ////////////////////////////////////////////////////////////////////

			// 取得编码规则分录
			CodingRuleEntryCollection codingRuleEntryCollection = FdcCodingRuleUtil.getCodingRuleEntryCollection(ctx,
					oldInfo, orgId, bindingProperty);

			// 参与编码规则的属性
			Map valueAttributeMap = FdcObjectCollectionUtil.parsePropertyMap(codingRuleEntryCollection, "valueAttribute");
			Set valueAttributeSet = valueAttributeMap.keySet();

			if (FdcCollectionUtil.isNotEmpty(valueAttributeSet)) {
				String[] selectors = (String[]) valueAttributeSet.toArray(new String[0]);
				SelectorItemCollection sic = FdcEntityViewUtil.getSelector(selectors);
				sic.add(new SelectorItemInfo("*"));
				if (isExistBindingProperty) {
					sic.add(new SelectorItemInfo(bindingProperty));
				}
				oldInfo = (FDCBillInfo) _getValue(ctx, new ObjectUuidPK(newInfo.getId()), sic);

				Map newValueMap = new HashMap();
				Map oldValueMap = new HashMap();
				for (Iterator iterator = valueAttributeSet.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object newValue = FdcObjectValueUtil.get(model, key) + "";
					Object oldValue = FdcObjectValueUtil.get(oldInfo, key) + "";

					newValueMap.put(key, newValue);
					oldValueMap.put(key, oldValue);
				}

				//参与编码规则的属性发生变化
				if (!ObjectUtils.equals(newValueMap, oldValueMap)) {
					//回收编码
					if (FdcCodingRuleUtil.recycleNumber(iCodingRuleManager, oldInfo, orgId, bindingProperty, "", oldInfo.getNumber())) {
					}

					// 重新生成编码
					handleIntermitNumber(ctx, newInfo);

					// 跳出循环
					break;
				}
			}
		}

		return true;
	}

	/**
	 * 回收Number，如果配置了编码规则并支持断号的话
	 * @param ctx
	 * @param pk
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 */
	protected void recycleNumber2(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		logger.info("===============================================================================");
		logger.info("FDCBillControllerBean.recycleNumber(),start");
		logger.info("===============================================================================");

		//////////////////////////////////////////////////////////////////////

		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		FDCBillInfo oldInfo = getValueForRecycleNumber(ctx, pk);
		String currentOrgId = getOrgUnitId(ctx, oldInfo);
		String number = oldInfo.getNumber();

		//////////////////////////////////////////////////////////////////////

		logger.info("FDCBillControllerBean.recycleNumber2().curOrgId:" + currentOrgId);
		logger.info("FDCBillControllerBean.recycleNumber2().info:" + oldInfo);
		logger.info("FDCBillControllerBean.recycleNumber2().info.getNumber():" + number);
		if (FdcStringUtil.isBlank(number)) {
			logger.info("编码为空，不回收");
		}

		//////////////////////////////////////////////////////////////////////

		boolean isRecycleParentOrgNumber = isRecycleParentOrgNumber();
		logger.info("isRecycleParentOrgNumber:" + isRecycleParentOrgNumber);
		ArrayList orgIdList = new ArrayList();
		// 当前组织ID放在循环列表最前面
		orgIdList.add(currentOrgId);
		if (isRecycleParentOrgNumber) {
			ContractUtil.findParentOrgUnitIdToList(ctx, currentOrgId, orgIdList);
		}
		// 清除掉集合中的重复值和Null值
		FdcCollectionUtil.clearDuplicateAndNull(orgIdList);
		logger.info("orgIdList:" + orgIdList);
		
		String bindingProperty = getBindingProperty();
		boolean isExistBindingProperty = FdcStringUtil.isNotBlank(bindingProperty);
		logger.info("bindingProperty:" + bindingProperty);
		logger.info("isExistBindingProperty:" + isExistBindingProperty);

		boolean flag = false;
		for (int i = 0, size = orgIdList.size(); i < size; i++) {
			String orgId = orgIdList.get(i).toString();
			logger.info("回收组织为：" + orgId + "的编码规则。");

			//是否回收编码
			flag = FdcCodingRuleUtil.isRecycleNumber(iCodingRuleManager, oldInfo, orgId, bindingProperty);

			if (flag) {
				//如果回收成功了就跳出循环
				if (FdcCodingRuleUtil.recycleNumber(iCodingRuleManager, oldInfo, orgId, bindingProperty, "", oldInfo.getNumber())) {
					break;
				}
			}
		}

		logger.info("===============================================================================");
		logger.info("FDCBillControllerBean.recycleNumber2(),end");
		logger.info("===============================================================================");
	}

	/**
	 * 描述：属性字段参与编码规则，如果值发生改变，则需要重新获取编码
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-10-10
	 */
	protected String getOrgUnitId(Context ctx, IObjectValue model) {
		FDCBillInfo info = (FDCBillInfo) model;

		OrgUnitInfo orgUnitInfo = info.getOrgUnit();
		if (null == orgUnitInfo) {
			orgUnitInfo = ContextUtil.getCurrentOrgUnit(ctx);
		}
		String orgId = orgUnitInfo.getId().toString();

		return orgId;
	}

	/**
	 * 描述：属性字段参与编码规则，如果值发生改变，则需要重新获取编码
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-10-10
	 */
	protected boolean isNeedReHandleIntermitNumberByProperty() {
		return false;
	}

	/**
	 * 描述：是否回收上级组织传递的编码规则
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-10-10
	 */
	protected boolean isRecycleParentOrgNumber() {
		return false;
	}

	/**
	 * 描述：取得回收编码Value
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @Author：skyiter_wang
	 * @CreateTime：2013-10-10
	 */
	protected FDCBillInfo getValueForRecycleNumber(Context ctx, IObjectPK pk) throws EASBizException, BOSException {
		FDCBillInfo info = null;

		SelectorItemCollection sic = getSeletorForRecycleNumber();
		if (FdcObjectCollectionUtil.isEmpty(sic)) {
			info = (FDCBillInfo) _getValue(ctx, pk);
		} else {
			info = (FDCBillInfo) _getValue(ctx, pk, sic);
		}

		return info;
	}

	/**
	 * 描述：取得回收编码Seletor
	 * @return
	 * @author：skyiter_wang
	 * @createDate：2013-10-16
	 */
	protected SelectorItemCollection getSeletorForRecycleNumber() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("*"));

		String bindingProperty = getBindingProperty();
		// 是否存在绑定属性
		boolean isExistBindingProperty = FdcStringUtil.isNotBlank(bindingProperty);
		if (isExistBindingProperty) {
			sic.add(new SelectorItemInfo(bindingProperty));
		}

		return sic;
	}

	/**
	 * 描述：匹配编码规则，获取编码，设置编码
	 * 
	 * @param ctx
	 * @param info
	 * @param orgId
	 * @param bindingProperty
	 * @param iCodingRuleManager
	 * @param count
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-20
	 */
	protected boolean setNumber(Context ctx, FDCBillInfo info, String orgId, String bindingProperty,
			ICodingRuleManager iCodingRuleManager, int count) throws BOSException, EASBizException {

		if (FdcCodingRuleUtil.isExist(iCodingRuleManager, info, orgId, bindingProperty)) {
			// 获取编码规则对象
			CodingRuleInfo codingRuleInfo = FdcCodingRuleUtil.getCodingRule(iCodingRuleManager, info, orgId,
					bindingProperty);
			// 如果支持修改就要检查编码是否符合当前启用的编码规则的规则

			// 获取编码规则是否支持修改
			boolean flag = FdcCodingRuleUtil.isAllowModifyNumber(iCodingRuleManager, info, orgId, bindingProperty);
			// 判断是否修改了编码,是否改大了顺序号
			if (flag) {
				// CodingRuleFactory.getLocalInstance(ctx).checkModifiedNumber(info, codingRuleInfo,
				// info.getNumber());
			} else {
				// 如果是新增显示
				if (codingRuleInfo.isIsAddView()) {
					// 编码为空的时候要获取编码
					/*
					 * modified by zhaojie for BT868215 on 2015-03-13 update 'isNotBlank' to
					 * 'isBlank'
					 */
					if (FdcStringUtil.isBlank(info.getNumber())) {
						String number = FdcCodingRuleUtil.getNumber(iCodingRuleManager, info, orgId, bindingProperty);
						
						/* modified by zhaoqin for R140213-0169 on 2014/02/17 */
						//info.setNumber(number);
						info.setNumber(convertNumber(number));
					} else {
						// 当编码不为空的时候，count=0的时候不需要重新获取编码，当编码>0的时候证明编码重复要重新获取新的编码
						if (count > 0) {
							String number = FdcCodingRuleUtil.getNumber(iCodingRuleManager, info, orgId,
									bindingProperty);
							
							/* modified by zhaoqin for R140213-0169 on 2014/02/17 */
							//info.setNumber(number);
							info.setNumber(convertNumber(number));
						}
					}
				} else {
					// 否则就是不允许断号，每次都要重现获取
					String number = FdcCodingRuleUtil.getNumber(iCodingRuleManager, info, orgId, bindingProperty);
					
					/* modified by zhaoqin for R140213-0169 on 2014/02/17 */
					//info.setNumber(number);
					info.setNumber(convertNumber(number));
				}
			}

			return true;
		}

		return false;
	}

	// ////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////

	/**
	 * 转换编码number，将"!"变为"." - R140213-0169
	 * 
	 * @author zhaoqin
	 * @date 2014/02/14
	 */
	private String convertNumber(String orgNumber){
		return orgNumber.replaceAll("!", ".");
	}
}