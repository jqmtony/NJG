package com.kingdee.eas.fdc.basedata.app;


import java.sql.SQLException;

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
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.IAccountView;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.DeductAccountEntrysCollection;
import com.kingdee.eas.fdc.basedata.DeductAccountEntrysFactory;
import com.kingdee.eas.fdc.basedata.DeductAccountEntrysInfo;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class BeforeAccountViewControllerBean extends
		AbstractBeforeAccountViewControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.basedata.app.BeforeAccountViewControllerBean");

	protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		BeforeAccountViewInfo info = (BeforeAccountViewInfo) model;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("company.id", info.getCompany().getId().toString()));
		if (info.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", info.getId().toString(),
							CompareType.NOTEQUALS));
		}
		if (_exists(ctx, filter)) {
			throw new FDCBasedataException(FDCBasedataException.COMPANY_SAME);
		}
		super._addnew(ctx, pk, model);
	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		BeforeAccountViewInfo info = (BeforeAccountViewInfo) model;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("company.id", info.getCompany().getId().toString()));
		if (info.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", info.getId().toString(),
							CompareType.NOTEQUALS));
		}
		if (_exists(ctx, filter)) {
			throw new FDCBasedataException(FDCBasedataException.COMPANY_SAME);
		}
		return super._addnew(ctx, model);
	}

	/**
	 * 描述：科目一体化设置引入<p>
	 * 
	 * 模板引入规则：集团模板->管理单元(引入或新增)->引入到财务组织<p>
	 * 科目引入规则：取编码左相等右类似的第一个明细科目<br>
	 *     1.以上级科目查询下级科目时按应按编码排序<br>
	 *     2.下级的科目（与上级设置科目编码相同）如果为非明细，则取下级，类似向下推
	 *     
	 */
	//TODO 增加描述;另科目引入存在错误应该按上述科目引入规则修改 by hpw 2010.08.12 
	protected void _impTemplate(Context ctx, String companyId) throws BOSException, EASBizException {			
		
		// 2010-5-19  add by pu_zhang 管理单元可以编辑。
		//获取管理单元
		EntityViewInfo fiView = new EntityViewInfo();
		FilterInfo fiFilter = new FilterInfo();
		fiFilter.getFilterItems().add(new FilterItemInfo("id",companyId));
		fiView.setFilter(fiFilter);
		CompanyOrgUnitInfo COUInfo = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection(fiView).get(0);
		CtrlUnitInfo cu = COUInfo.getCU();

		String promopt ="";
		//当是集团模板		
		FilterInfo filter = new FilterInfo();	
		if(OrgConstants.DEF_CU_ID.equals(companyId)){
			filter.getFilterItems().add(new FilterItemInfo("company.id", OrgConstants.DEF_CU_ID));
			promopt="集团";
		//当是管理单元
		}else if (COUInfo.isIsCU()){
			//获取上级管理单元模板
			EntityViewInfo fiViewP = new EntityViewInfo();
			FilterInfo fiFilterP = new FilterInfo();
			String longNumber = COUInfo.getLongNumber().substring(0,(COUInfo.getLongNumber().lastIndexOf("!")!=-1)?COUInfo.getLongNumber().lastIndexOf("!"):COUInfo.getLongNumber().length());
			fiFilterP.getFilterItems().add(new FilterItemInfo("longNumber",longNumber));
			fiViewP.setFilter(fiFilterP);
			CompanyOrgUnitInfo COUInfoP = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection(fiViewP).get(0);
//			CtrlUnitInfo cuP = COUInfoP.getCU();
			filter.getFilterItems().add(
					new FilterItemInfo("company.id", COUInfoP.getId().toString()));
			promopt=COUInfoP.getName();
		//当是财务组织
		}else{
			filter.getFilterItems().add(
					new FilterItemInfo("company.id", cu.getId().toString()));
			promopt=COUInfo.getName();
		}		
			
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("proAccount.longNumber");
		view.getSelector().add("tempAccount.longNumber");
		view.getSelector().add("settAccount.longNumber");
		view.getSelector().add("intendAccount.longNumber");
		view.getSelector().add("otherProAccount.longNumber");
		view.getSelector().add("otherSettAccount.longNumber");
		view.getSelector().add("otherIntendAccount.longNumber");
		view.getSelector().add("beforeOtherAccount.longNumber");
		view.getSelector().add("beforeDevAccount.longNumber");
		view.getSelector().add("beforeSettAccount.longNumber");
		view.getSelector().add("productAccount.longNumber");
		view.getSelector().add("adminFees.longNumber");
		view.getSelector().add("marketFees.longNumber");
		view.getSelector().add("compensationAccount.longNumber");
		view.getSelector().add("guerdonAccount.longNumber");
		view.getSelector().add("deductAccountEntrys.*");
		view.getSelector().add("deductAccountEntrys.account.*");
		view.getSelector().add("deductAccountEntrys.account.longNumber");
//		view.getSelector().add("deductAccountEntrys.deductType.name");
		view.getSelector().add("company.accountTable");
		IAccountView iaccount = AccountViewFactory.getLocalInstance(ctx);
		AccountViewCollection accViewColl;
		AccountViewInfo aViewInfo;
		// 多个会计科目表时，串会计科目串表问题处理：下级模板的会计科目与集团模板会计科目的科目表保持一致
		String accountTableId = null; 
		int level=0;
		// 校验集团模板
		if (super.exists(ctx, filter)) { 
			// 当前财务组织是否存在
			FilterInfo filterExist = new FilterInfo();
			filterExist.getFilterItems().add(new FilterItemInfo("company.id", companyId));
			if(super.exists(ctx, filterExist)){
//				throw new FDCBasedataException(FDCBasedataException.COMPANY_SAME); 
				EntityViewInfo viewExist = new EntityViewInfo();
				viewExist.setFilter(filterExist);
				BeforeAccountViewInfo newInfo = super.getBeforeAccountViewCollection(ctx, viewExist).get(0);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("*");
				selector.add("cu.*");
				newInfo.setCompany(CompanyOrgUnitFactory.getLocalInstance(ctx)
						.getCompanyOrgUnitInfo(
								new ObjectUuidPK(BOSUuid.read(companyId)),selector));
				newInfo.setCU(newInfo.getCompany().getCU());
				BeforeAccountViewInfo info = super.getBeforeAccountViewCollection(
						ctx, view).get(0); 
				// 集团模板只有一个,下级与集团会计科目保持一致
				accountTableId = info.getCompany().getAccountTable().getId().toString();
				FilterInfo filterCUExist = new FilterInfo();
				filterCUExist.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
				if(!iaccount.exists(filterCUExist)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有会计科目!"));
				}
	
				//扣款会计科目
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		    	String sql = "select * from T_fdc_deductType a where a.fid in (select distinct b.fdeducttypeid from " +
		    			"T_FDC_DeductAccountEntrys b where a.fid=b.fdeducttypeid and b.fparentid=?) " +
		    			"or fisenabled=1 order by a.fnumber";
		    	sqlBuilder.appendSql(sql);
		    	sqlBuilder.addParam(companyId);
				IRowSet rowSet = sqlBuilder.executeQuery();
		    	
				EntityViewInfo vInfo = new EntityViewInfo();
	    		FilterInfo fInfo = new FilterInfo();
	    		vInfo.getSelector().add("id");
	    		vInfo.getSelector().add("account.*");
	    		vInfo.getSelector().add("deductType.*");
	    		vInfo.getSorter().add(new SorterItemInfo("deductType.number"));
	    		fInfo.getFilterItems().add(new FilterItemInfo("parent.id",info.getId().toString()));
	    		vInfo.setFilter(fInfo);
				
				DeductAccountEntrysCollection deAccColl = DeductAccountEntrysFactory.getLocalInstance(ctx).getDeductAccountEntrysCollection(vInfo);
				//集团模板中的数据条数
				int deSize = deAccColl.size();
				
				if(info.getDeductAccountEntrys().size()==0){
					newInfo.getDeductAccountEntrys().clear();
				}else{
//					for(int i=0;i<info.getDeductAccountEntrys().size();i++){
					int number=0;
					//当前组织中数据条数
					int bSize = rowSet.size();
					String[] ids= new String[bSize];
					try {
						int length = 0;
						while(rowSet.next()){
							ids[length] = rowSet.getString("fid");
							length++;
						}
					} catch (SQLException e1) {
						logger.error(e1.getMessage(), e1);
						throw new BOSException(e1);
					}
//					while(rowSet.next()){
					int count = 0;	//给界面上第几行赋值
					int loopTimes = 0;
					loopTimes = bSize>=deSize ? bSize : deSize;
					for(int i=0;i<loopTimes;i++){
						DeductAccountEntrysInfo deAccInfo = new DeductAccountEntrysInfo();
						//如果已经达到数据库的长度，说明该赋值的已经赋完了，可以直接跳出
						if(number == deSize){
							continue;
						}
						DeductAccountEntrysInfo accInfo = deAccColl.get(number);
						EntityViewInfo vAccount = new EntityViewInfo();
						FilterInfo fAccount = new FilterInfo();
						fAccount.getFilterItems().add(
								new FilterItemInfo("companyID.id", companyId));
						fAccount.getFilterItems().add(
								new FilterItemInfo("longNumber", /*info.getDeductAccountEntrys().get(i).getAccount().getLongNumber()+"%"*/
										accInfo.getAccount().getLongNumber()+"%", CompareType.LIKE));
						fAccount.getFilterItems().add(
								new FilterItemInfo("isLeaf", "1"));
						fAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
						if(!iaccount.exists(fAccount)){
							throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+accInfo.getAccount().getLongNumber()+" 会计科目!"));
						}
						
						DeductTypeInfo backdeInfo = accInfo.getDeductType();
						for(int j=0;j<ids.length;j++){
							if(backdeInfo.getId().toString().equals(ids[j])){
								vAccount.setFilter(fAccount);
								accViewColl = iaccount.getAccountViewCollection(vAccount);
								aViewInfo = accViewColl.get(0);
								level = accViewColl.get(0).getLevel();
								
								//计算最明细科目
								for(int k=1; k<accViewColl.size();k++){
									AccountViewInfo accViewInfo = accViewColl.get(k);
									if(level==accViewInfo.getLevel()){
										if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
											aViewInfo = accViewInfo;
										}
									}else if(level<accViewInfo.getLevel()){
										level=accViewInfo.getLevel();
										aViewInfo = accViewInfo;
									}
								}
								fAccount.getFilterItems().add(
										new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
								fAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
								if(!iaccount.exists(fAccount)){
									throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getNumber()+" "+
											aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
								}
								deAccInfo.setAccount(aViewInfo);
								deAccInfo.setDeductType(accInfo.getDeductType());
								
								if(newInfo.getDeductAccountEntrys().size()<bSize){
									newInfo.getDeductAccountEntrys().add(deAccInfo);
								}else{
									newInfo.getDeductAccountEntrys().set(count,deAccInfo);
								}
								
								/**
								 * 注意：只有在界面上有这个扣款类型，才继续取下一个；
								 * 否则还是取数据库中的前一个与界面上进行对比；
								 */
								number++;
								count++;
								break;
							}
							//如果在数据库中取得值，在界面上没有，则继续取第二条数据库中的值
							if(j+1==ids.length){
								number++;
							}
						}
					}
					
				}

				//应付帐款_开发成本_进度款
				EntityViewInfo viewAccount = new EntityViewInfo();
				FilterInfo filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getProAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getProAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setProAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setProAccount(aViewInfo);
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				
				//应付帐款_开发成本_暂估款
				//由于集团建立模板的时候允许"应付帐款_开发成本_暂估款"字段允许录入空字段,此时如果下级组织这个字段有值，需要处理集团模板该字段为空的情况
				if(info.getTempAccount()==null){
					newInfo.setTempAccount(null);
				}else{
					viewAccount = new EntityViewInfo();
					filterAccount = new FilterInfo();
					filterAccount.getFilterItems().add(
							new FilterItemInfo("companyID.id", companyId));
					filterAccount.getFilterItems().add(
							new FilterItemInfo("longNumber", info.getTempAccount()
									.getLongNumber()+"%", CompareType.LIKE));
					filterAccount.getFilterItems().add(
							new FilterItemInfo("isLeaf", "1"));
					filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
					if(!iaccount.exists(filterAccount)){
						throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getTempAccount().getLongNumber()+" 会计科目!"));
					}
					viewAccount.setFilter(filterAccount);
					accViewColl = iaccount.getAccountViewCollection(viewAccount);
					aViewInfo = accViewColl.get(0);
					level = accViewColl.get(0).getLevel();
					for(int i=1; i<accViewColl.size();i++){
						AccountViewInfo accViewInfo = accViewColl.get(i);
						if(level==accViewInfo.getLevel()){
							if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
								aViewInfo = accViewInfo;
							}
						}else if(level<accViewInfo.getLevel()){
							level=accViewInfo.getLevel();
							aViewInfo = accViewInfo;
						}
					}
					filterAccount.getFilterItems().add(
							new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
					filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
					if(!iaccount.exists(filterAccount)){
						throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
								aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
					}
					newInfo.setTempAccount(aViewInfo);
				}
				
				
				//违约对应科目
				if (info.getCompensationAccount() != null) {
					viewAccount = new EntityViewInfo();
					filterAccount = new FilterInfo();
					filterAccount.getFilterItems().add(
							new FilterItemInfo("companyID.id", companyId));
	//				filterAccount.getFilterItems().add(
	//						new FilterItemInfo("longNumber", info.getCompensationAccount()
	//								.getLongNumber()));
					filterAccount.getFilterItems().add(
							new FilterItemInfo("longNumber", info.getCompensationAccount().getLongNumber()+"%", CompareType.LIKE));
					filterAccount.getFilterItems().add(
							new FilterItemInfo("isLeaf", "1"));
					filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
					if(!iaccount.exists(filterAccount)){
						throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getCompensationAccount().getLongNumber()+" 会计科目!"));
					}
					viewAccount.setFilter(filterAccount);
	//				newInfo.setCompensationAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
					accViewColl = iaccount.getAccountViewCollection(viewAccount);
					aViewInfo = accViewColl.get(0);
					level = accViewColl.get(0).getLevel();
					for(int i=1; i<accViewColl.size();i++){
						AccountViewInfo accViewInfo = accViewColl.get(i);
						if(level==accViewInfo.getLevel()){
							if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
								aViewInfo = accViewInfo;
							}
						}else if(level<accViewInfo.getLevel()){
							level=accViewInfo.getLevel();
							aViewInfo = accViewInfo;
						}
					}
					filterAccount.getFilterItems().add(
							new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
					filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
					if(!iaccount.exists(filterAccount)){
						throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
								aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
					}
					newInfo.setCompensationAccount(aViewInfo);
				}
					
				//奖励对应科目
				if (info.getGuerdonAccount() != null) {
					viewAccount = new EntityViewInfo();
					filterAccount = new FilterInfo();
					filterAccount.getFilterItems().add(
							new FilterItemInfo("companyID.id", companyId));
					if(info.getGuerdonAccount()!=null){
						filterAccount.getFilterItems().add(
							new FilterItemInfo("longNumber", info.getGuerdonAccount().getLongNumber()+"%", CompareType.LIKE));
					}
					filterAccount.getFilterItems().add(
							new FilterItemInfo("isLeaf", "1"));
					filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
					if(!iaccount.exists(filterAccount)){
						throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getGuerdonAccount().getLongNumber()+" 会计科目!"));
					}
					viewAccount.setFilter(filterAccount);
					accViewColl = iaccount.getAccountViewCollection(viewAccount);
					aViewInfo = accViewColl.get(0);
					level = accViewColl.get(0).getLevel();
					for(int i=1; i<accViewColl.size();i++){
						AccountViewInfo accViewInfo = accViewColl.get(i);
						if(level==accViewInfo.getLevel()){
							if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
								aViewInfo = accViewInfo;
							}
						}else if(level<accViewInfo.getLevel()){
							level=accViewInfo.getLevel();
							aViewInfo = accViewInfo;
						}
					}
					filterAccount.getFilterItems().add(
							new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
					filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
					if(!iaccount.exists(filterAccount)){
						throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
								aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
					}
					newInfo.setGuerdonAccount(aViewInfo);
				}
				
				//应付帐款_开发成本_保修款
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getSettAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getSettAccount().getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getSettAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);

				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setSettAccount(aViewInfo);
				
				//应付帐款_开发成本_预提成本
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getIntendAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getIntendAccount().getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getIntendAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setIntendAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setIntendAccount(aViewInfo);
				
				//应付帐款_其他_进度款
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getOtherProAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getOtherProAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getOtherProAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setOtherProAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setOtherProAccount(aViewInfo);
				
				//应付帐款_其他_保修款
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getOtherSettAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getOtherSettAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getOtherSettAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setOtherSettAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setOtherSettAccount(aViewInfo);
				
				//应付帐款_其他_预提成本
				if(info.getOtherIntendAccount() != null){
					viewAccount = new EntityViewInfo();
					filterAccount = new FilterInfo();
					filterAccount.getFilterItems().add(
							new FilterItemInfo("companyID.id", companyId));
//					filterAccount.getFilterItems().add(
//							new FilterItemInfo("longNumber", info.getOtherIntendAccount()
//									.getLongNumber()));
					filterAccount.getFilterItems().add(
							new FilterItemInfo("longNumber", info.getOtherIntendAccount()
									.getLongNumber()+"%", CompareType.LIKE));
					filterAccount.getFilterItems().add(
							new FilterItemInfo("isLeaf", "1"));
					filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
					if(!iaccount.exists(filterAccount)){
						throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getOtherIntendAccount().getLongNumber()+" 会计科目!"));
					}
					viewAccount.setFilter(filterAccount);
//					newInfo.setOtherIntendAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
					accViewColl = iaccount.getAccountViewCollection(viewAccount);
					aViewInfo = accViewColl.get(0);
					level = accViewColl.get(0).getLevel();
					for(int i=1; i<accViewColl.size();i++){
						AccountViewInfo accViewInfo = accViewColl.get(i);
						if(level==accViewInfo.getLevel()){
							if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
								aViewInfo = accViewInfo;
							}
						}else if(level<accViewInfo.getLevel()){
							level=accViewInfo.getLevel();
							aViewInfo = accViewInfo;
						}
					}
					filterAccount.getFilterItems().add(
							new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
					filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
					if(!iaccount.exists(filterAccount)){
						throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
								aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
					}
					newInfo.setOtherIntendAccount(aViewInfo);
				}
				
				//预付帐款_开发成本_其他成本
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getBeforeOtherAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getBeforeOtherAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getBeforeOtherAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setBeforeOtherAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}
					else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setBeforeOtherAccount(aViewInfo);
				
				//开发成本_预提成本
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getBeforeDevAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getBeforeDevAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getBeforeDevAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setBeforeDevAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setBeforeDevAccount(aViewInfo);
				
				//开发成本_成本结转
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getBeforeSettAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getBeforeSettAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getBeforeSettAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setBeforeSettAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setBeforeSettAccount(aViewInfo);
				
				//完工开发产品
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getProductAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getProductAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getProductAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setProductAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setProductAccount(aViewInfo);
				
				//管理费用
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getAdminFees()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getAdminFees()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getAdminFees().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setAdminFees(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setAdminFees(aViewInfo);
				
				//营销费用
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getMarketFees()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getMarketFees()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getMarketFees().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setMarketFees(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setMarketFees(aViewInfo);
				
				newInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());	
				newInfo.setIsImptedTemplate(true); // added by owen_wen 2010-10-08 是否导入集团模板
				super.submit(ctx, newInfo);
			}else{
				BeforeAccountViewInfo newInfo = new BeforeAccountViewInfo();
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("*");
				selector.add("cu.*");
				newInfo.setCompany(CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(BOSUuid.read(companyId)),selector));
				newInfo.setCU(newInfo.getCompany().getCU());
				BeforeAccountViewInfo info = super.getBeforeAccountViewCollection(ctx, view).get(0);
				accountTableId = info.getCompany().getAccountTable().getId().toString();
				FilterInfo filterCUExist = new FilterInfo();
				filterCUExist.getFilterItems().add(new FilterItemInfo("companyID.id", companyId));
				if(!iaccount.exists(filterCUExist)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有会计科目!"));
				}
	
//				扣款会计科目
//				扣款会计科目
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		    	String sql = "select * from T_fdc_deductType a where a.fid in (select distinct b.fdeducttypeid from " +
		    			"T_FDC_DeductAccountEntrys b where a.fid=b.fdeducttypeid and b.fparentid=?) " +
		    			"or fisenabled=1 order by a.fnumber";
		    	sqlBuilder.appendSql(sql);
		    	sqlBuilder.addParam(companyId);
				IRowSet rowSet = sqlBuilder.executeQuery();
		    	
				EntityViewInfo vInfo = new EntityViewInfo();
	    		FilterInfo fInfo = new FilterInfo();
	    		vInfo.getSelector().add("id");
	    		vInfo.getSelector().add("account.*");
	    		vInfo.getSelector().add("deductType.*");
	    		vInfo.getSorter().add(new SorterItemInfo("deductType.number"));
	    		fInfo.getFilterItems().add(new FilterItemInfo("parent.id",info.getId().toString()));
	    		vInfo.setFilter(fInfo);
				
				DeductAccountEntrysCollection deAccColl = DeductAccountEntrysFactory.getLocalInstance(ctx).getDeductAccountEntrysCollection(vInfo);
				int deSize = deAccColl.size();
				
				if(info.getDeductAccountEntrys().size()==0){
					newInfo.getDeductAccountEntrys().clear();
				}else{
//					for(int i=0;i<info.getDeductAccountEntrys().size();i++){
					int number=0;
					int bSize = rowSet.size();
					String[] ids= new String[bSize];
					try {
						int length = 0;
						while(rowSet.next()){
							ids[length] = rowSet.getString("fid");
							length++;
						}
					} catch (SQLException e1) {
						logger.error(e1.getMessage(), e1);
						throw new BOSException(e1);
					}
//					while(rowSet.next()){
					int count = 0;	//给界面上第几行赋值
					int loopTimes = 0;
					loopTimes = bSize>=deSize ? bSize : deSize;
					for(int i=0;i<loopTimes;i++){
						DeductAccountEntrysInfo deAccInfo = new DeductAccountEntrysInfo();
						//如果已经达到数据库的长度，说明该赋值的已经赋完了，可以直接跳出
						if(number == deSize){
							continue;
						}
						DeductAccountEntrysInfo accInfo = deAccColl.get(number);
						EntityViewInfo vAccount = new EntityViewInfo();
						FilterInfo fAccount = new FilterInfo();
						fAccount.getFilterItems().add(
								new FilterItemInfo("companyID.id", companyId));
						fAccount.getFilterItems().add(
								new FilterItemInfo("longNumber", /*info.getDeductAccountEntrys().get(i).getAccount().getLongNumber()+"%"*/
										accInfo.getAccount().getLongNumber()+"%", CompareType.LIKE));
						fAccount.getFilterItems().add(
								new FilterItemInfo("isLeaf", "1"));
						fAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
						if(!iaccount.exists(fAccount)){
							throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+accInfo.getAccount().getLongNumber()+" 会计科目!"));
						}
						
						DeductTypeInfo backdeInfo = accInfo.getDeductType();
						for(int j=0;j<ids.length;j++){
							if(backdeInfo.getId().toString().equals(ids[j])){
								vAccount.setFilter(fAccount);
								accViewColl = iaccount.getAccountViewCollection(vAccount);
								aViewInfo = accViewColl.get(0);
								level = accViewColl.get(0).getLevel();
								
								//计算最明细科目
								for(int k=1; k<accViewColl.size();k++){
									AccountViewInfo accViewInfo = accViewColl.get(k);
									if(level==accViewInfo.getLevel()){
										if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
											aViewInfo = accViewInfo;
										}
									}else if(level<accViewInfo.getLevel()){
										level=accViewInfo.getLevel();
										aViewInfo = accViewInfo;
									}
								}
								fAccount.getFilterItems().add(
										new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
								fAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
								if(!iaccount.exists(fAccount)){
									throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
											aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
								}
								deAccInfo.setAccount(aViewInfo);
								deAccInfo.setDeductType(accInfo.getDeductType());
								
								if(newInfo.getDeductAccountEntrys().size()<bSize){
									newInfo.getDeductAccountEntrys().add(deAccInfo);
								}else{
									newInfo.getDeductAccountEntrys().set(count,deAccInfo);
								}
								
								/**
								 * 注意：只有在界面上有这个扣款类型，才继续取下一个；
								 * 否则还是取数据库中的前一个与界面上进行对比；
								 */
								number++;
								count++;
								break;
							}
							//如果在数据库中取得值，在界面上没有，则继续取第二条数据库中的值
							if(j+1==ids.length){
								number++;
							}
						}
					}
					
				}
				
				//应付帐款_开发成本_进度款
				EntityViewInfo viewAccount = new EntityViewInfo();
				FilterInfo filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getProAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getProAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getProAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setProAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setProAccount(aViewInfo);
				
				//违约对应科目
				if (info.getCompensationAccount() != null) {
					viewAccount = new EntityViewInfo();
					filterAccount = new FilterInfo();
					filterAccount.getFilterItems().add(
							new FilterItemInfo("companyID.id", companyId));
	//				filterAccount.getFilterItems().add(
	//						new FilterItemInfo("longNumber", info.getCompensationAccount()
	//								.getLongNumber()));
					filterAccount.getFilterItems().add(
							new FilterItemInfo("longNumber", info.getCompensationAccount().getLongNumber()+"%", CompareType.LIKE));
					filterAccount.getFilterItems().add(
							new FilterItemInfo("isLeaf", "1"));
					filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
					if(!iaccount.exists(filterAccount)){
						throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getCompensationAccount().getLongNumber()+" 会计科目!"));
					}
					viewAccount.setFilter(filterAccount);
	//				newInfo.setCompensationAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
					accViewColl = iaccount.getAccountViewCollection(viewAccount);
					aViewInfo = accViewColl.get(0);
					level = accViewColl.get(0).getLevel();
					for(int i=1; i<accViewColl.size();i++){
						AccountViewInfo accViewInfo = accViewColl.get(i);
						if(level==accViewInfo.getLevel()){
							if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
								aViewInfo = accViewInfo;
							}
						}else if(level<accViewInfo.getLevel()){
							level=accViewInfo.getLevel();
							aViewInfo = accViewInfo;
						}
					}
					filterAccount.getFilterItems().add(
							new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
					filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
					if(!iaccount.exists(filterAccount)){
						throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
								aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
					}
					newInfo.setCompensationAccount(aViewInfo);
				}
				
				//奖励对应科目
				if (info.getGuerdonAccount() != null) {
					viewAccount = new EntityViewInfo();
					filterAccount = new FilterInfo();
					filterAccount.getFilterItems().add(
							new FilterItemInfo("companyID.id", companyId));
					filterAccount.getFilterItems().add(
							new FilterItemInfo("longNumber", info.getGuerdonAccount().getLongNumber()+"%", CompareType.LIKE));
					filterAccount.getFilterItems().add(
							new FilterItemInfo("isLeaf", "1"));
					filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
					if(!iaccount.exists(filterAccount)){
						throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getGuerdonAccount().getLongNumber()+" 会计科目!"));
					}
					viewAccount.setFilter(filterAccount);
					accViewColl = iaccount.getAccountViewCollection(viewAccount);
					aViewInfo = accViewColl.get(0);
					level = accViewColl.get(0).getLevel();
					for(int i=1; i<accViewColl.size();i++){
						AccountViewInfo accViewInfo = accViewColl.get(i);
						if(level==accViewInfo.getLevel()){
							if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
								aViewInfo = accViewInfo;
							}
						}else if(level<accViewInfo.getLevel()){
							level=accViewInfo.getLevel();
							aViewInfo = accViewInfo;
						}
					}
					filterAccount.getFilterItems().add(
							new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
					filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
					if(!iaccount.exists(filterAccount)){
						throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
								aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
					}
					newInfo.setGuerdonAccount(aViewInfo);
				}
				
				//应付帐款_开发成本_保修款
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getSettAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getSettAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					// 提示不一致  by hpw 2009-12-08
					// throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getProAccount().getLongNumber()+" 会计科目!"));
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getSettAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setSettAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setSettAccount(aViewInfo);
				
				//应付帐款_开发成本_预提成本
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getIntendAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getIntendAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getIntendAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setIntendAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setIntendAccount(aViewInfo);
				
				//应付帐款_其他_进度款
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getOtherProAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getOtherProAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getOtherProAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setOtherProAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setOtherProAccount(aViewInfo);
				
				//应付帐款_其他_保修款
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getOtherSettAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getOtherSettAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getOtherSettAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
//				newInfo.setOtherSettAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setOtherSettAccount(aViewInfo);
				
				//应付帐款_其他_预提成本
				if(info.getOtherIntendAccount() != null){
					viewAccount = new EntityViewInfo();
					filterAccount = new FilterInfo();
					filterAccount.getFilterItems().add(
							new FilterItemInfo("companyID.id", companyId));
//					filterAccount.getFilterItems().add(
//							new FilterItemInfo("longNumber", info.getOtherIntendAccount()
//									.getLongNumber()));
					filterAccount.getFilterItems().add(
							new FilterItemInfo("longNumber", info.getOtherIntendAccount()
									.getLongNumber()+"%", CompareType.LIKE));
					filterAccount.getFilterItems().add(
							new FilterItemInfo("isLeaf", "1"));
					filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
					if(!iaccount.exists(filterAccount)){
						throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getOtherIntendAccount().getLongNumber()+" 会计科目!"));
					}
					viewAccount.setFilter(filterAccount);
					newInfo.setOtherIntendAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
					accViewColl = iaccount.getAccountViewCollection(viewAccount);
					aViewInfo = accViewColl.get(0);
					level = accViewColl.get(0).getLevel();
					for(int i=1; i<accViewColl.size();i++){
						AccountViewInfo accViewInfo = accViewColl.get(i);
						if(level==accViewInfo.getLevel()){
							if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
								aViewInfo = accViewInfo;
							}
						}else if(level<accViewInfo.getLevel()){
							level=accViewInfo.getLevel();
							aViewInfo = accViewInfo;
						}
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setOtherIntendAccount(aViewInfo);
				
				//预付帐款_开发成本_其他成本
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getBeforeOtherAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getBeforeOtherAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getBeforeOtherAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
				newInfo.setBeforeOtherAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setBeforeOtherAccount(aViewInfo);
				
				//开发成本_预提成本
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getBeforeDevAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getBeforeDevAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getBeforeDevAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
				newInfo.setBeforeDevAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setBeforeDevAccount(aViewInfo);
				
				//开发成本_成本结转
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getBeforeSettAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getBeforeSettAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getBeforeSettAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
				newInfo.setBeforeSettAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setBeforeSettAccount(aViewInfo);
				
				//完工开发产品
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getProductAccount()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getProductAccount()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getProductAccount().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
				newInfo.setProductAccount(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setProductAccount(aViewInfo);
				
				//管理费用
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getAdminFees()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getAdminFees()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getAdminFees().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
				newInfo.setAdminFees(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setAdminFees(aViewInfo);
				
				//营销费用
				viewAccount = new EntityViewInfo();
				filterAccount = new FilterInfo();
				filterAccount.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
//				filterAccount.getFilterItems().add(
//						new FilterItemInfo("longNumber", info.getMarketFees()
//								.getLongNumber()));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber", info.getMarketFees()
								.getLongNumber()+"%", CompareType.LIKE));
				filterAccount.getFilterItems().add(
						new FilterItemInfo("isLeaf", "1"));
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","该财务组织没有编码为："+info.getMarketFees().getLongNumber()+" 会计科目!"));
				}
				viewAccount.setFilter(filterAccount);
				newInfo.setMarketFees(iaccount.getAccountViewCollection(viewAccount).get(0));
				accViewColl = iaccount.getAccountViewCollection(viewAccount);
				aViewInfo = accViewColl.get(0);
				level = accViewColl.get(0).getLevel();
				for(int i=1; i<accViewColl.size();i++){
					AccountViewInfo accViewInfo = accViewColl.get(i);
					if(level==accViewInfo.getLevel()){
						if(aViewInfo.getLongNumber().compareTo(accViewInfo.getLongNumber())>0){
							aViewInfo = accViewInfo;
						}
					}else if(level<accViewInfo.getLevel()){
						level=accViewInfo.getLevel();
						aViewInfo = accViewInfo;
					}
				}
				filterAccount.getFilterItems().add(
						new FilterItemInfo("longNumber",aViewInfo.getLongNumber()));
				filterAccount.getFilterItems().add(new FilterItemInfo("isCFreeze","0"));
				if(!iaccount.exists(filterAccount)){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织科目："+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" 未启用，不能引入"+promopt+"模板!"));
				}
				newInfo.setMarketFees(aViewInfo);
				
				newInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
				newInfo.setIsImptedTemplate(true); // added by owen_wen 2010-10-08 是否导入集团模板
				super.submit(ctx, newInfo);
			}
		}
		else{
			throw new EASBizException(new NumericExceptionSubItem("100",""+promopt+"未建立模板!"));
		}
	}
}