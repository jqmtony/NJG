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
	 * ��������Ŀһ�廯��������<p>
	 * 
	 * ģ��������򣺼���ģ��->����Ԫ(���������)->���뵽������֯<p>
	 * ��Ŀ�������ȡ��������������Ƶĵ�һ����ϸ��Ŀ<br>
	 *     1.���ϼ���Ŀ��ѯ�¼���Ŀʱ��Ӧ����������<br>
	 *     2.�¼��Ŀ�Ŀ�����ϼ����ÿ�Ŀ������ͬ�����Ϊ����ϸ����ȡ�¼�������������
	 *     
	 */
	//TODO ��������;���Ŀ������ڴ���Ӧ�ð�������Ŀ��������޸� by hpw 2010.08.12 
	protected void _impTemplate(Context ctx, String companyId) throws BOSException, EASBizException {			
		
		// 2010-5-19  add by pu_zhang ����Ԫ���Ա༭��
		//��ȡ����Ԫ
		EntityViewInfo fiView = new EntityViewInfo();
		FilterInfo fiFilter = new FilterInfo();
		fiFilter.getFilterItems().add(new FilterItemInfo("id",companyId));
		fiView.setFilter(fiFilter);
		CompanyOrgUnitInfo COUInfo = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection(fiView).get(0);
		CtrlUnitInfo cu = COUInfo.getCU();

		String promopt ="";
		//���Ǽ���ģ��		
		FilterInfo filter = new FilterInfo();	
		if(OrgConstants.DEF_CU_ID.equals(companyId)){
			filter.getFilterItems().add(new FilterItemInfo("company.id", OrgConstants.DEF_CU_ID));
			promopt="����";
		//���ǹ���Ԫ
		}else if (COUInfo.isIsCU()){
			//��ȡ�ϼ�����Ԫģ��
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
		//���ǲ�����֯
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
		// �����ƿ�Ŀ��ʱ������ƿ�Ŀ�������⴦���¼�ģ��Ļ�ƿ�Ŀ�뼯��ģ���ƿ�Ŀ�Ŀ�Ŀ����һ��
		String accountTableId = null; 
		int level=0;
		// У�鼯��ģ��
		if (super.exists(ctx, filter)) { 
			// ��ǰ������֯�Ƿ����
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
				// ����ģ��ֻ��һ��,�¼��뼯�Ż�ƿ�Ŀ����һ��
				accountTableId = info.getCompany().getAccountTable().getId().toString();
				FilterInfo filterCUExist = new FilterInfo();
				filterCUExist.getFilterItems().add(
						new FilterItemInfo("companyID.id", companyId));
				if(!iaccount.exists(filterCUExist)){
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�л�ƿ�Ŀ!"));
				}
	
				//�ۿ��ƿ�Ŀ
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
				//����ģ���е���������
				int deSize = deAccColl.size();
				
				if(info.getDeductAccountEntrys().size()==0){
					newInfo.getDeductAccountEntrys().clear();
				}else{
//					for(int i=0;i<info.getDeductAccountEntrys().size();i++){
					int number=0;
					//��ǰ��֯����������
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
					int count = 0;	//�������ϵڼ��и�ֵ
					int loopTimes = 0;
					loopTimes = bSize>=deSize ? bSize : deSize;
					for(int i=0;i<loopTimes;i++){
						DeductAccountEntrysInfo deAccInfo = new DeductAccountEntrysInfo();
						//����Ѿ��ﵽ���ݿ�ĳ��ȣ�˵���ø�ֵ���Ѿ������ˣ�����ֱ������
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
							throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+accInfo.getAccount().getLongNumber()+" ��ƿ�Ŀ!"));
						}
						
						DeductTypeInfo backdeInfo = accInfo.getDeductType();
						for(int j=0;j<ids.length;j++){
							if(backdeInfo.getId().toString().equals(ids[j])){
								vAccount.setFilter(fAccount);
								accViewColl = iaccount.getAccountViewCollection(vAccount);
								aViewInfo = accViewColl.get(0);
								level = accViewColl.get(0).getLevel();
								
								//��������ϸ��Ŀ
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
									throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getNumber()+" "+
											aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
								}
								deAccInfo.setAccount(aViewInfo);
								deAccInfo.setDeductType(accInfo.getDeductType());
								
								if(newInfo.getDeductAccountEntrys().size()<bSize){
									newInfo.getDeductAccountEntrys().add(deAccInfo);
								}else{
									newInfo.getDeductAccountEntrys().set(count,deAccInfo);
								}
								
								/**
								 * ע�⣺ֻ���ڽ�����������ۿ����ͣ��ż���ȡ��һ����
								 * ������ȡ���ݿ��е�ǰһ��������Ͻ��жԱȣ�
								 */
								number++;
								count++;
								break;
							}
							//��������ݿ���ȡ��ֵ���ڽ�����û�У������ȡ�ڶ������ݿ��е�ֵ
							if(j+1==ids.length){
								number++;
							}
						}
					}
					
				}

				//Ӧ���ʿ�_�����ɱ�_���ȿ�
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getProAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setProAccount(aViewInfo);
				filterAccount.getFilterItems().add(new FilterItemInfo("accountTableID.id", accountTableId));
				
				//Ӧ���ʿ�_�����ɱ�_�ݹ���
				//���ڼ��Ž���ģ���ʱ������"Ӧ���ʿ�_�����ɱ�_�ݹ���"�ֶ�����¼����ֶ�,��ʱ����¼���֯����ֶ���ֵ����Ҫ������ģ����ֶ�Ϊ�յ����
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
						throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getTempAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
						throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
								aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
					}
					newInfo.setTempAccount(aViewInfo);
				}
				
				
				//ΥԼ��Ӧ��Ŀ
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
						throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getCompensationAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
						throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
								aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
					}
					newInfo.setCompensationAccount(aViewInfo);
				}
					
				//������Ӧ��Ŀ
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
						throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getGuerdonAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
						throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
								aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
					}
					newInfo.setGuerdonAccount(aViewInfo);
				}
				
				//Ӧ���ʿ�_�����ɱ�_���޿�
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getSettAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setSettAccount(aViewInfo);
				
				//Ӧ���ʿ�_�����ɱ�_Ԥ��ɱ�
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getIntendAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setIntendAccount(aViewInfo);
				
				//Ӧ���ʿ�_����_���ȿ�
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getOtherProAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setOtherProAccount(aViewInfo);
				
				//Ӧ���ʿ�_����_���޿�
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getOtherSettAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setOtherSettAccount(aViewInfo);
				
				//Ӧ���ʿ�_����_Ԥ��ɱ�
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
						throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getOtherIntendAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
						throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
								aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
					}
					newInfo.setOtherIntendAccount(aViewInfo);
				}
				
				//Ԥ���ʿ�_�����ɱ�_�����ɱ�
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getBeforeOtherAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setBeforeOtherAccount(aViewInfo);
				
				//�����ɱ�_Ԥ��ɱ�
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getBeforeDevAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setBeforeDevAccount(aViewInfo);
				
				//�����ɱ�_�ɱ���ת
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getBeforeSettAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setBeforeSettAccount(aViewInfo);
				
				//�깤������Ʒ
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getProductAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setProductAccount(aViewInfo);
				
				//�������
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getAdminFees().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setAdminFees(aViewInfo);
				
				//Ӫ������
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getMarketFees().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setMarketFees(aViewInfo);
				
				newInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());	
				newInfo.setIsImptedTemplate(true); // added by owen_wen 2010-10-08 �Ƿ��뼯��ģ��
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�л�ƿ�Ŀ!"));
				}
	
//				�ۿ��ƿ�Ŀ
//				�ۿ��ƿ�Ŀ
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
					int count = 0;	//�������ϵڼ��и�ֵ
					int loopTimes = 0;
					loopTimes = bSize>=deSize ? bSize : deSize;
					for(int i=0;i<loopTimes;i++){
						DeductAccountEntrysInfo deAccInfo = new DeductAccountEntrysInfo();
						//����Ѿ��ﵽ���ݿ�ĳ��ȣ�˵���ø�ֵ���Ѿ������ˣ�����ֱ������
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
							throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+accInfo.getAccount().getLongNumber()+" ��ƿ�Ŀ!"));
						}
						
						DeductTypeInfo backdeInfo = accInfo.getDeductType();
						for(int j=0;j<ids.length;j++){
							if(backdeInfo.getId().toString().equals(ids[j])){
								vAccount.setFilter(fAccount);
								accViewColl = iaccount.getAccountViewCollection(vAccount);
								aViewInfo = accViewColl.get(0);
								level = accViewColl.get(0).getLevel();
								
								//��������ϸ��Ŀ
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
									throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
											aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
								}
								deAccInfo.setAccount(aViewInfo);
								deAccInfo.setDeductType(accInfo.getDeductType());
								
								if(newInfo.getDeductAccountEntrys().size()<bSize){
									newInfo.getDeductAccountEntrys().add(deAccInfo);
								}else{
									newInfo.getDeductAccountEntrys().set(count,deAccInfo);
								}
								
								/**
								 * ע�⣺ֻ���ڽ�����������ۿ����ͣ��ż���ȡ��һ����
								 * ������ȡ���ݿ��е�ǰһ��������Ͻ��жԱȣ�
								 */
								number++;
								count++;
								break;
							}
							//��������ݿ���ȡ��ֵ���ڽ�����û�У������ȡ�ڶ������ݿ��е�ֵ
							if(j+1==ids.length){
								number++;
							}
						}
					}
					
				}
				
				//Ӧ���ʿ�_�����ɱ�_���ȿ�
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getProAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setProAccount(aViewInfo);
				
				//ΥԼ��Ӧ��Ŀ
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
						throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getCompensationAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
						throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
								aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
					}
					newInfo.setCompensationAccount(aViewInfo);
				}
				
				//������Ӧ��Ŀ
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
						throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getGuerdonAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
						throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
								aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
					}
					newInfo.setGuerdonAccount(aViewInfo);
				}
				
				//Ӧ���ʿ�_�����ɱ�_���޿�
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
					// ��ʾ��һ��  by hpw 2009-12-08
					// throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getProAccount().getLongNumber()+" ��ƿ�Ŀ!"));
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getSettAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setSettAccount(aViewInfo);
				
				//Ӧ���ʿ�_�����ɱ�_Ԥ��ɱ�
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getIntendAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setIntendAccount(aViewInfo);
				
				//Ӧ���ʿ�_����_���ȿ�
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getOtherProAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setOtherProAccount(aViewInfo);
				
				//Ӧ���ʿ�_����_���޿�
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getOtherSettAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setOtherSettAccount(aViewInfo);
				
				//Ӧ���ʿ�_����_Ԥ��ɱ�
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
						throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getOtherIntendAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setOtherIntendAccount(aViewInfo);
				
				//Ԥ���ʿ�_�����ɱ�_�����ɱ�
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getBeforeOtherAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setBeforeOtherAccount(aViewInfo);
				
				//�����ɱ�_Ԥ��ɱ�
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getBeforeDevAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setBeforeDevAccount(aViewInfo);
				
				//�����ɱ�_�ɱ���ת
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getBeforeSettAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setBeforeSettAccount(aViewInfo);
				
				//�깤������Ʒ
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getProductAccount().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setProductAccount(aViewInfo);
				
				//�������
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getAdminFees().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setAdminFees(aViewInfo);
				
				//Ӫ������
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
					throw new EASBizException(new NumericExceptionSubItem("100","�ò�����֯û�б���Ϊ��"+info.getMarketFees().getLongNumber()+" ��ƿ�Ŀ!"));
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
					throw new EASBizException(new NumericExceptionSubItem("100","��ǰ��֯��Ŀ��"+aViewInfo.getLongNumber()+" "+
							aViewInfo.getLongName()+" δ���ã���������"+promopt+"ģ��!"));
				}
				newInfo.setMarketFees(aViewInfo);
				
				newInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
				newInfo.setIsImptedTemplate(true); // added by owen_wen 2010-10-08 �Ƿ��뼯��ģ��
				super.submit(ctx, newInfo);
			}
		}
		else{
			throw new EASBizException(new NumericExceptionSubItem("100",""+promopt+"δ����ģ��!"));
		}
	}
}