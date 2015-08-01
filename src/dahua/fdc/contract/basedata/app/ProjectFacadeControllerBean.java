package com.kingdee.eas.fdc.basedata.app;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjCostEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjCostEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjCostEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.HisProjectCollection;
import com.kingdee.eas.fdc.basedata.HisProjectFactory;
import com.kingdee.eas.fdc.basedata.HisProjectInfo;
import com.kingdee.eas.fdc.basedata.ICurProjCostEntries;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.IHisProject;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.jdbc.rowset.IRowSet;
/**
 * ����:������Ŀfacade
 * @author jackwang  date:2006-8-15 <p>
 * @version EAS5.1
 */
public class ProjectFacadeControllerBean extends AbstractProjectFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.ProjectFacadeControllerBean");
    protected IObjectCollection _getHisProjectCollection(Context ctx, String curProjectID)throws BOSException, EASBizException
    {
    	HisProjectCollection hisProjectCollection = new HisProjectCollection();
    	IHisProject iHisProject = HisProjectFactory.getLocalInstance(ctx);
		EntityViewInfo evi = new EntityViewInfo();
		evi.getSelector().add("*");
		evi.getSelector().add("landDeveloper.*");
		evi.getSelector().add("fullOrgUnit.*");
		evi.getSelector().add("hisProjProductEntries.*");
		evi.getSelector().add("hisProjProductEntries.hisProjProEntrApporData.*");
		evi.getSelector().add("hisProjCostEntries.*");
		evi.getSelector().add("hisProjCostEntries.apportionType.*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectID));
		evi.setFilter(filter);//ȡ��
		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("createTime");
	    sii.setSortType(SortType.DESCEND);
		evi.getSorter().add(sii);//����
		hisProjectCollection = iHisProject.getHisProjectCollection(evi);//
		return hisProjectCollection;
    }
	protected boolean _editProjectVersion(Context ctx, IObjectValue hisProjectInfo) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		return false;
	}
	protected boolean _updateHisProjectInfo(Context ctx, IObjectValue hisProjectInfo) throws BOSException, EASBizException {
		//��ʵ����ÿ�α���ɹ�(����Ҫ�ڽ���ͨ����֤)�������һ��������Ŀ��ʷ�汾
		IHisProject iHisProject = HisProjectFactory.getLocalInstance(ctx);
		HisProjectInfo hisInfo = (HisProjectInfo) hisProjectInfo;
		iHisProject.addnew(hisInfo);
		//���µ�ǰ������Ŀ����
		ICurProjCostEntries iCurProjCostEntries = CurProjCostEntriesFactory.getLocalInstance(ctx);
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", hisInfo.getCurProject().getId().toString()));		
		evi.setFilter(filter);
		CurProjCostEntriesCollection curProjCostEntriesCollection  =  iCurProjCostEntries.getCurProjCostEntriesCollection(evi);
		if(curProjCostEntriesCollection.size()!=0){
//			for(int i=0;i<curProjCostEntriesCollection.size();i++){
//				for(int j=0;j<hisInfo.getHisProjCostEntries().size();j++){
//					if(curProjCostEntriesCollection.get(i).getApportionType().getId().equals(hisInfo.getHisProjCostEntries().get(j).getApportionType().getId())){
//						curProjCostEntriesCollection.get(i).setValue(hisInfo.getHisProjCostEntries().get(j).getValue());
//						iCurProjCostEntries.update(new ObjectUuidPK(curProjCostEntriesCollection.get(i).getId()),(CurProjCostEntriesInfo)curProjCostEntriesCollection.get(i));
//						continue;
//					}
//				}
//			}
			
			for(int i = 0;i<hisInfo.getHisProjCostEntries().size();i++){
				boolean flag = false; 
				for(int j = 0 ;j<curProjCostEntriesCollection.size();j++){
					if(hisInfo.getHisProjCostEntries().get(i).getApportionType().getId().equals(curProjCostEntriesCollection.get(j).getApportionType().getId())){
						curProjCostEntriesCollection.get(j).setValue(hisInfo.getHisProjCostEntries().get(i).getValue());
						iCurProjCostEntries.update(new ObjectUuidPK(curProjCostEntriesCollection.get(i).getId()),(CurProjCostEntriesInfo)curProjCostEntriesCollection.get(i));
						flag = true;
						continue;
					}
				}
				if(!flag){//û�е�
					CurProjCostEntriesInfo cpcei = new CurProjCostEntriesInfo();
					cpcei.setCurProject(hisInfo.getCurProject());
					cpcei.setApportionType(hisInfo.getHisProjCostEntries().get(i).getApportionType());
					cpcei.setValue(hisInfo.getHisProjCostEntries().get(i).getValue());
					iCurProjCostEntries.addnew(cpcei);
				}
			}
		}
		
//		 String sql = "update t_fdc_curprojcostentries set FValue = ? where FApportionType = ? and FCurProject = ? ";
//	        BigDecimal unverifiedAmtFor = hisInfo.getHisProjCostEntries().getOriginalBalance();
//	        BigDecimal unverifiedAmtLocal = acctCussentHistoryInfo.getLocalBalance();
//	        BigDecimal unverifiedAmtRpt = acctCussentHistoryInfo.getReportBanlance();
//	        BigDecimal unverifiedAmtQty = acctCussentHistoryInfo.getQtyBalance();
//
//	        DbUtil.execute(ctx, sql, new Object[] { unverifiedAmtFor, unverifiedAmtLocal, unverifiedAmtRpt, unverifiedAmtQty, srcID });
//	   
		
		return true;
	}
	protected boolean _updateCurProjectInfo(Context ctx, IObjectValue curProjectInfo) throws BOSException, EASBizException {
		//
		ICurProject iCurProject = CurProjectFactory.getLocalInstance(ctx);
		CurProjectInfo info = (CurProjectInfo)curProjectInfo;
		iCurProject.update(new ObjectUuidPK(info.getId().toString()),info);
		return true;
	}
	protected Map _canAddNew(Context ctx, String curProjectId) throws BOSException, EASBizException {
		// ��Ҫ�жϹ�����Ŀ�Ƿ�����
		Map retMap = new HashMap();
		/****
		 * ������Ҫ�жϣ���ͬ��֣۱����֣ݣ��Ƿ���ڣ�����ֵ��˹�����Ŀ�ң���ֵ�����������Ŀ�����
		 
		 	--��ͬ���
		 	select con_cs_e_1.fparentid,con.fnumber,costacc.fcurproject,count(*) from t_con_contractcostsplitentry con_cs_e_1 inner join t_fdc_costaccount costacc on con_cs_e_1.fcostaccountid=costacc.fid 
			inner join t_con_contractcostsplit con_cs_1 on con_cs_1.fid=con_cs_e_1.fparentid
			inner join t_con_contractbill con on con_cs_1.fcontractbillid=con.fid
			where con_cs_e_1.fparentid in 
			(
			select con_cs_e.fparentid from t_con_contractcostsplitentry con_cs_e inner join 
			t_fdc_costaccount costacc on con_cs_e.fcostaccountid=costacc.fid 
			inner join t_con_contractcostsplit con_cs on con_cs_e.fparentid=con_cs.fid
			where costacc.fcurproject='ycOUmZm8St6IjhnQBY58I/nl6Ss='
			and con_cs.fisinvalid=0
			)
			group by con_cs_e_1.fparentid,con.fnumber,costacc.fcurproject
			having count(*)>1
			union
			select con_ncs_e_1.fparentid,con.fnumber,con_ncs_e_1.fcurprojectid,count(*) from  t_con_connocostsplitentry con_ncs_e_1 
			inner join t_con_connocostsplit con_ncs_1 on con_ncs_1.fid=con_ncs_e_1.fparentid
			inner join t_con_contractbill con on con_ncs_1.fcontractbillid=con.fid
			where con_ncs_e_1.fparentid in 
			(
			select con_ncs_e.fparentid from t_con_connocostsplitentry con_ncs_e 
			inner join t_con_connocostsplit con_ncs on con_ncs_e.fparentid=con_ncs.fid
			where con_ncs_e.fcurprojectid='ycOUmZm8St6IjhnQBY58I/nl6Ss='
			and con_ncs.fisinvalid=0
			)
			group by con_ncs_e_1.fparentid,con.fnumber,con_ncs_e_1.fcurprojectid
			having count(*)>1
			
			--������
			select chg_cs_e_1.fparentid,chg.fnumber,costacc.fcurproject,count(*) from  t_con_conchangesplitentry chg_cs_e_1 inner join t_fdc_costaccount costacc on chg_cs_e_1.fcostaccountid=costacc.fid 
			inner join t_con_conchangesplit chg_cs_1 on chg_cs_1.fid=chg_cs_e_1.fparentid
			inner join t_con_contractchangebill chg on chg_cs_1.fcontractchangeid=chg.fid
			where chg_cs_e_1.fparentid in 
			(
			select chg_cs_e.fparentid from t_con_conchangesplitentry chg_cs_e inner join 
			t_fdc_costaccount costacc on chg_cs_e.fcostaccountid=costacc.fid 
			inner join t_con_conchangesplit chg_cs on chg_cs_e.fparentid=chg_cs.fid
			where costacc.fcurproject='ycOUmZm8St6IjhnQBY58I/nl6Ss='
			and chg_cs.fisinvalid=0
			)
			group by chg_cs_e_1.fparentid,chg.fnumber,costacc.fcurproject
			having count(*)>1
			union
			select chg_ncs_e_1.fparentid,chg.fnumber,chg_ncs_e_1.fcurprojectid,count(*) from  t_con_changenosplitentry chg_ncs_e_1 
			inner join t_con_conchangenocostsplit chg_ncs_1 on chg_ncs_1.fid=chg_ncs_e_1.fparentid
			inner join t_con_contractchangebill chg on chg_ncs_1.fcontractchangeid=chg.fid
			where chg_ncs_e_1.fparentid in 
			(
			select chg_ncs_e.fparentid from t_con_changenosplitentry chg_ncs_e 
			inner join t_con_conchangenocostsplit chg_ncs on chg_ncs_e.fparentid=chg_ncs.fid
			where chg_ncs_e.fcurprojectid='ycOUmZm8St6IjhnQBY58I/nl6Ss='
			and chg_ncs.fisinvalid=0
			)
			group by chg_ncs_e_1.fparentid,chg.fnumber,chg_ncs_e_1.fcurprojectid
			having count(*)>1
		 
		 */
		try{
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("id");
			selector.add("parent.id");
			selector.add("CU.id");
			CurProjectInfo prj=CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(curProjectId));
			
			//��ȥ������Ŀ
			if(prj.getParent()!=null){
				/**
				 * ֻ�й�����Ŀ�����ϼ����ұ�������Ŀ���ϼ�������Ŀͬʱ�����˲�ֵ�ʱ��Ż�Բ����Ӱ��
				 *  by sxhong 2009-07-01 13:52:47
				 *  
				 *  db2��֧��inner join ��exists�Ӳ�ѯ�����ⲿ����
				 *  by hpw 2010-11-5
				 */
				//contract
				String conNumber = checkConSplitCantAddNew(ctx,prj);
				if(!FDCHelper.isEmpty(conNumber)){
					retMap.put("CONTRACTNUMBERS", conNumber);
					retMap.put("HASUSED",Boolean.TRUE);
				}
				//change
				String changeNumber=checkChangeSplitCantAddNew(ctx,prj);
				if(!FDCHelper.isEmpty(changeNumber)){
					retMap.put("CONTRACTCHANGENUMBERS",changeNumber);
					retMap.put("HASUSED",Boolean.TRUE);
				}
				//notext
				String noTextNumber=checkConNoTextSplitCantAddNew(ctx, prj);
				if(!FDCHelper.isEmpty(noTextNumber)){
					retMap.put("CONNOTEXTNUMBERS",noTextNumber);
					retMap.put("HASUSED",Boolean.TRUE);
				}
			}

			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			if(!retMap.containsKey("HASUSED")){
				//��ͬ
				builder.appendSql("select fid from t_con_contractbill where fcurprojectid=? ");
				builder.appendSql(" union  ");
				//���ı���ͬ
				builder.appendSql(" select fid from t_con_contractwithouttext where fcurprojectid=? ");
				builder.appendSql(" union ");
				//���гɱ�
				builder.appendSql(" select fid from t_aim_measurecost measurecost where measurecost.fprojectid=?");
				builder.appendSql(" union ");
				//ָ��ֵ
				builder.appendSql(" select fid from T_FDC_ProjectIndexData where fprojororgid=?");
				builder.appendSql(" union ");
				//Ŀ��ɱ�
				builder.appendSql(" Select fid From T_AIM_AIMCOST Where forgorproid=?");
				builder.addParam(curProjectId);
				builder.addParam(curProjectId);
				builder.addParam(curProjectId);
				builder.addParam(curProjectId);
				builder.addParam(curProjectId);
				if(builder.isExist()){
					retMap.put("HASUSED",Boolean.TRUE);
				}
			}
		}
		catch(SQLException e){
			logger.error(e.getMessage(), e);
			throw new BOSException(e);
		}
		
		return retMap;
	}
	/**
	 * ����Ƿ�����ϼ���Ŀ��������Ŀ��һ������ڵ����
	 *  by sxhong 2009-07-01 13:52:39
	 * @param ctx
	 * @param prj
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private String checkChangeSplitCantAddNew(Context ctx, CurProjectInfo prj) throws BOSException, SQLException {
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		/***
		 * Ȼ���Ǳ�����
		 * �䱻ע��,��Ҫɾ��
		 */
		/*//�ı�
		builder.appendSql("select distinct bill.fnumber as fnumber from T_con_ConChangeSplit split  \n");
		builder.appendSql("inner join T_con_contractChangeBill bill on bill.fid=split.fcontractchangeid \n");
		builder.appendSql("and exists ( \n");//�ϼ�������Ŀ�ڲ����
		builder.appendSql("select 1 from T_con_ConChangeSplitEntry entry  \n");
		builder.appendSql("inner join T_FDC_CostAccount acct on entry.fcostaccountid=acct.fid  \n");
		builder.appendSql("where entry.fparentid=split.fid and acct.fcurProject=? \n");
		builder.appendSql(") \n");
		builder.appendSql("and exists ( \n");//��������Ŀ�ڲ����
		builder.appendSql("select 1 from T_con_ConChangeSplitEntry entry  \n");
		builder.appendSql("inner join T_FDC_CostAccount acct on entry.fcostaccountid=acct.fid  \n");
		builder.appendSql("where entry.fparentid=split.fid and acct.fcurProject=? \n");
		builder.appendSql(") and split.fcontrolunitid=?  \n");

		builder.appendSql("union all  \n");
		//�ǳɱ�
		builder.appendSql("select distinct bill.fnumber from t_Con_ConChangeNoCostSplit split  \n");
		builder.appendSql("inner join T_con_contractChangeBill bill on bill.fid=split.fcontractchangeid \n");
		builder.appendSql("and exists ( \n");//�ϼ�������Ŀ�ڲ����
		builder.appendSql("select 1 from t_Con_Changenosplitentry entry  \n");
		builder.appendSql("where entry.fparentid=split.fid and entry.fcurProjectid=? \n");
		builder.appendSql(") \n");
		builder.appendSql("and exists ( \n");//��������Ŀ�ڲ����
		builder.appendSql("select 1 from t_Con_Changenosplitentry entry  \n");
		builder.appendSql("where entry.fparentid=split.fid and entry.fcurProjectid=? \n");
		builder.appendSql(") and split.fcontrolunitid=?  \n");
		
		builder.addParam(prj.getParent().getId().toString());
		builder.addParam(prj.getId().toString());
		builder.addParam(prj.getCU().getId().toString());
		
		builder.addParam(prj.getParent().getId().toString());
		builder.addParam(prj.getId().toString());
		builder.addParam(prj.getCU().getId().toString());
		IRowSet rs = builder.executeQuery();*/
		
		//�ɱ�����
		builder.appendSql("select distinct bill.fnumber as fnumber from T_con_ConChangeSplit split  \n");
		builder.appendSql("inner join T_con_contractChangeBill bill on bill.fid=split.fcontractchangeid \n");
		
		//�ϼ�������Ŀ�ڲ����
		builder.appendSql("inner join T_con_ConChangeSplitEntry entry1 on entry1.fparentid=split.fid \n");
		builder.appendSql("inner join T_FDC_CostAccount acct1 on entry1.fcostaccountid=acct1.fid and acct1.fcurProject=? \n");
		
		//��������Ŀ�ڲ����
		builder.appendSql("inner join T_con_ConChangeSplitEntry entry2 on entry2.fparentid=split.fid \n");
		builder.appendSql("inner join T_FDC_CostAccount acct2 on entry2.fcostaccountid=acct2.fid and acct2.fcurProject=? \n");
		builder.appendSql("where split.fcontrolunitid=?  \n");

		builder.appendSql("union all  \n");

		//�ǳɱ�����
		builder.appendSql("select distinct bill.fnumber from t_Con_ConChangeNoCostSplit split  \n");
		builder.appendSql("inner join T_con_contractChangeBill bill on bill.fid=split.fcontractchangeid \n");
		
		//�ϼ�������Ŀ�ڲ����
		builder.appendSql("inner join t_Con_Changenosplitentry entry1 on entry1.fparentid=split.fid and entry1.fcurProjectid=? \n");
		//��������Ŀ�ڲ����
		builder.appendSql("inner join t_Con_Changenosplitentry entry2 on entry2.fparentid=split.fid and entry2.fcurProjectid=? \n");
		builder.appendSql("where split.fcontrolunitid=?  \n");
		
		builder.addParam(prj.getParent().getId().toString());
		builder.addParam(prj.getId().toString());
		builder.addParam(prj.getCU().getId().toString());
		
		builder.addParam(prj.getParent().getId().toString());
		builder.addParam(prj.getId().toString());
		builder.addParam(prj.getCU().getId().toString());
		IRowSet rs = builder.executeQuery();
		
		if(rs!=null&&rs.size()>0){
			/***
			 * �з��������ı�����
			 */
			StringBuffer numbers = new StringBuffer();
			while(rs.next()){
				numbers.append(rs.getString("fnumber"));
				numbers.append(",");
			}
			String changeNumbers = numbers.toString();
			changeNumbers = changeNumbers.substring(0,changeNumbers.length()-1);
			return changeNumbers;
		}
		return null;
	}
	/**
	 * ����Ƿ�����ϼ���Ŀ��������Ŀ��һ������ڵ����
	 *  by sxhong 2009-07-01 13:53:00
	 * @param ctx
	 * @param prj
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private String checkConSplitCantAddNew(Context ctx,CurProjectInfo prj) throws BOSException, SQLException {
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		/***
		 * ���жϺ�ͬ��� 
		 * �˶�sql��ע�Ͳ�Ҫɾ��
		 */
		/*//�ı�
		builder.appendSql("select distinct bill.fnumber as fnumber from T_con_ContractCostSplit split  \n");
		builder.appendSql("inner join T_con_contractBill bill on bill.fid=split.fcontractbillid \n");
		builder.appendSql("and exists ( \n");//�ϼ�������Ŀ�ڲ����
		builder.appendSql("select 1 from T_con_ContractCostSplitEntry entry  \n");
		builder.appendSql("inner join T_FDC_CostAccount acct on entry.fcostaccountid=acct.fid  \n");
		builder.appendSql("where entry.fparentid=split.fid and acct.fcurProject=? \n");
		builder.appendSql(") \n");
		builder.appendSql("and exists ( \n");//��������Ŀ�ڲ����
		builder.appendSql("select 1 from T_con_ContractCostSplitEntry entry  \n");
		builder.appendSql("inner join T_FDC_CostAccount acct on entry.fcostaccountid=acct.fid  \n");
		builder.appendSql("where entry.fparentid=split.fid and acct.fcurProject=? \n");
		builder.appendSql(") and split.fcontrolunitid=?  \n");

		builder.appendSql("union all  \n");
		//�ǳɱ�
		builder.appendSql("select distinct bill.fnumber from t_Con_Connocostsplit split  \n");
		builder.appendSql("inner join T_con_contractBill bill on bill.fid=split.fcontractbillid \n");
		builder.appendSql("and exists ( \n");//�ϼ�������Ŀ�ڲ����
		builder.appendSql("select 1 from T_con_ConnoCostSplitEntry entry  \n");
		builder.appendSql("where entry.fparentid=split.fid and entry.fcurProjectid=? \n");
		builder.appendSql(") \n");
		builder.appendSql("and exists ( \n");//��������Ŀ�ڲ����
		builder.appendSql("select 1 from T_con_ConnoCostSplitEntry entry  \n");
		builder.appendSql("where entry.fparentid=split.fid and entry.fcurProjectid=? \n");
		builder.appendSql(") and split.fcontrolunitid=?  \n");
		
		builder.addParam(prj.getParent().getId().toString());
		builder.addParam(prj.getId().toString());
		builder.addParam(prj.getCU().getId().toString());
		
		builder.addParam(prj.getParent().getId().toString());
		builder.addParam(prj.getId().toString());
		builder.addParam(prj.getCU().getId().toString());
		IRowSet rs = builder.executeQuery();*/
		
		/**
		 * db2��֧��inner join ��exists�Ӳ�ѯ�����ⲿ���� by hpw 2010-11-5
		 */
		//�ɱ����ͬ
		builder.appendSql("select distinct bill.fnumber as fnumber from T_con_ContractCostSplit split  \n");
		builder.appendSql("inner join T_con_contractBill bill on bill.fid=split.fcontractbillid \n");
		
		//�ϼ�������Ŀ�ڲ����
		builder.appendSql("inner join T_con_ContractCostSplitEntry entry1 on entry1.fparentid=split.fid \n");
		builder.appendSql("inner join T_FDC_CostAccount acct1 on entry1.fcostaccountid=acct1.fid and acct1.fcurProject=?  \n");
		//����������Ŀ�ڲ����
		builder.appendSql("inner join T_con_ContractCostSplitEntry entry2 on entry2.fparentid=split.fid \n");
		builder.appendSql("inner join T_FDC_CostAccount acct2 on entry2.fcostaccountid=acct2.fid and acct2.fcurProject=?  \n");
		builder.appendSql("where split.fcontrolunitid=?  \n");
		
		builder.appendSql("union all  \n");
		
		//�ǳɱ���ͬ
		builder.appendSql("select distinct bill.fnumber from t_Con_Connocostsplit split  \n");
		builder.appendSql("inner join T_con_contractBill bill on bill.fid=split.fcontractbillid \n");
		//�ϼ�������Ŀ�ڲ����
		builder.appendSql("inner join T_con_ConnoCostSplitEntry entry1 on entry1.fparentid=split.fid and entry1.fcurProjectid=? \n");
		//����������Ŀ�ڲ����
		builder.appendSql("inner join T_con_ConnoCostSplitEntry entry2 on entry2.fparentid=split.fid and entry2.fcurProjectid=? \n");
		builder.appendSql("where split.fcontrolunitid=?  \n");
		
		builder.addParam(prj.getParent().getId().toString());
		builder.addParam(prj.getId().toString());
		builder.addParam(prj.getCU().getId().toString());
		
		builder.addParam(prj.getParent().getId().toString());
		builder.addParam(prj.getId().toString());
		builder.addParam(prj.getCU().getId().toString());
		IRowSet rs = builder.executeQuery();
		
		if(rs!=null&&rs.size()>0){
			/***
			 * �з��������ĺ�ͬ���
			 */
			StringBuffer numbers = new StringBuffer();
			while(rs.next()){
				numbers.append(rs.getString("fnumber"));
				numbers.append(",");
			}
			String conNumbers = numbers.toString();
			conNumbers = conNumbers.substring(0,conNumbers.length()-1);
			return conNumbers;
		}
		return null;
	}
	
	/**
	 * ����Ƿ�����ϼ���Ŀ��������Ŀ��һ������ڵ����
	 *  by sxhong 2009-07-01 13:53:11
	 * @param ctx
	 * @param prj
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private String checkConNoTextSplitCantAddNew(Context ctx,CurProjectInfo prj) throws BOSException, SQLException {
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		/***
		 * ���жϺ�ͬ���
		 */
		/*//�ı�
		builder.appendSql("select distinct bill.fnumber as fnumber from t_fnc_paymentsplit split  \n");
		builder.appendSql("inner join T_Con_ContractWithoutText bill on bill.fid=split.fconWithoutTextid \n");
		builder.appendSql("and exists ( \n");//�ϼ�������Ŀ�ڲ����
		builder.appendSql("select 1 from T_FNC_PaymentSplitEntry entry  \n");
		builder.appendSql("inner join T_FDC_CostAccount acct on entry.fcostaccountid=acct.fid  \n");
		builder.appendSql("where entry.fparentid=split.fid and acct.fcurProject=? \n");
		builder.appendSql(") \n");
		builder.appendSql("and exists ( \n");//��������Ŀ�ڲ����
		builder.appendSql("select 1 from T_FNC_PaymentSplitEntry entry  \n");
		builder.appendSql("inner join T_FDC_CostAccount acct on entry.fcostaccountid=acct.fid  \n");
		builder.appendSql("where entry.fparentid=split.fid and acct.fcurProject=? \n");
		builder.appendSql(") and split.fcontrolunitid=?  \n");

		builder.appendSql("union all  \n");
		//�ǳɱ�
		builder.appendSql("select distinct bill.fnumber from T_FNC_PaymentNoCostSplit split  \n");
		builder.appendSql("inner join T_Con_ContractWithoutText bill on bill.fid=split.fconWithoutTextid \n");
		builder.appendSql("and exists ( \n");//�ϼ�������Ŀ�ڲ����
		builder.appendSql("select 1 from T_FNC_PaymentNoCostSplitEntry entry  \n");
		builder.appendSql("where entry.fparentid=split.fid and entry.fcurProjectid=? \n");
		builder.appendSql(") \n");
		builder.appendSql("and exists ( \n");//��������Ŀ�ڲ����
		builder.appendSql("select 1 from T_FNC_PaymentNoCostSplitEntry entry  \n");
		builder.appendSql("where entry.fparentid=split.fid and entry.fcurProjectid=? \n");
		builder.appendSql(") and split.fcontrolunitid=?  \n");
		
		builder.addParam(prj.getParent().getId().toString());
		builder.addParam(prj.getId().toString());
		builder.addParam(prj.getCU().getId().toString());
		
		builder.addParam(prj.getParent().getId().toString());
		builder.addParam(prj.getId().toString());
		builder.addParam(prj.getCU().getId().toString());*/
		
		//�ɱ������ı�
		builder.appendSql("select distinct bill.fnumber as fnumber from t_fnc_paymentsplit split  \n");
		builder.appendSql("inner join T_Con_ContractWithoutText bill on bill.fid=split.fconWithoutTextid \n");
		
		//�ϼ�������Ŀ�ڲ����
		builder.appendSql("inner join T_FNC_PaymentSplitEntry entry1 on entry1.fparentid=split.fid \n");
		builder.appendSql("inner join T_FDC_CostAccount acct1 on entry1.fcostaccountid=acct1.fid and acct1.fcurProject=? \n");
		//��������Ŀ�ڲ����
		builder.appendSql("inner join T_FNC_PaymentSplitEntry entry2 on entry2.fparentid=split.fid \n");
		builder.appendSql("inner join T_FDC_CostAccount acct2 on entry2.fcostaccountid=acct2.fid and acct2.fcurProject=? \n");
		builder.appendSql("where split.fcontrolunitid=? \n");
		
		builder.appendSql("union all  \n");
		
		//�ǳɱ����ı�
		builder.appendSql("select distinct bill.fnumber as fnumber from T_FNC_PaymentNoCostSplit split  \n");
		builder.appendSql("inner join T_Con_ContractWithoutText bill on bill.fid=split.fconWithoutTextid \n");
		//�ϼ�������Ŀ�ڲ����
		builder.appendSql("inner join T_FNC_PaymentNoCostSplitEntry entry1 on entry1.fparentid=split.fid and entry1.fcurProjectid=? \n");
		//��������Ŀ�ڲ����
		builder.appendSql("inner join T_FNC_PaymentNoCostSplitEntry entry2 on entry2.fparentid=split.fid and entry2.fcurProjectid=? \n");
		builder.appendSql("where split.fcontrolunitid=? \n");
		
		builder.addParam(prj.getParent().getId().toString());
		builder.addParam(prj.getId().toString());
		builder.addParam(prj.getCU().getId().toString());
		
		builder.addParam(prj.getParent().getId().toString());
		builder.addParam(prj.getId().toString());
		builder.addParam(prj.getCU().getId().toString());
		
		IRowSet rs = builder.executeQuery();
		if(rs!=null&&rs.size()>0){
			/***
			 * �з������������ı���ͬ���
			 */
			StringBuffer numbers = new StringBuffer();
			while(rs.next()){
				numbers.append(rs.getString("fnumber"));
				numbers.append(",");
			}
			String conNoTextNumbers = numbers.toString();
			conNoTextNumbers = conNoTextNumbers.substring(0,conNoTextNumbers.length()-1);
			return conNoTextNumbers;
		}
		return null;
	}
	
	/**
	 * ��Ϊ����������Ŀ�������¼���ϸ������Ŀ������Ŀ��ɱ��������Ŀ��ɱ����ߺ�ͬ��������Ի��Բ������޸ģ�
	 * ��Ϊ�������в�����Ŀ�������¼���ϸ���в�����Ŀ�����в������ݣ�������Ի��Բ������޸ġ�
	 * 
	 * @param ctx
	 * @param curProjectID
	 * @return true�ѷ�������,false��
	 * @throws BOSException
	 * @throws SQLException
	 */
	protected boolean _checkBeforeModifyIsDevPrj(Context ctx,
			IObjectValue ObjectValue) throws BOSException, EASBizException {
		CurProjectInfo info = (CurProjectInfo)ObjectValue;
		boolean isDevPrj = info.isIsDevPrj();
		Map childInfos = ProjectHelper.getCurProjChildInfos(ctx, info.getId().toString());
		Set idSet = new HashSet(childInfos.keySet());
		if(idSet.size()==0){
			return false;
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		if(isDevPrj){
			//��ͬ
			builder.appendSql("select fid from t_con_contractbill where ");
			builder.appendParam("fcurprojectid", idSet.toArray());
			builder.appendSql(" union  ");
			//���ı���ͬ
			builder.appendSql(" select fid from t_con_contractwithouttext where ");
			builder.appendParam("fcurprojectid", idSet.toArray());
			builder.appendSql(" union ");
			//Ŀ��ɱ�����
			builder.appendSql(" select fid from t_aim_measurecost where fisaimmeasure = 1 and ");
			builder.appendParam("fprojectid", idSet.toArray());
			builder.appendSql(" union ");
			//Ŀ��ɱ�
			builder.appendSql(" select fid from t_aim_aimcost where ");
			builder.appendParam("forgorproid", idSet.toArray());
		}else{
			//���гɱ�����
			builder.appendSql(" select fid from t_aim_measurecost where fisaimmeasure = 0 and ");
			builder.appendParam("fprojectid", idSet.toArray());
		}
		IRowSet rs = builder.executeQuery();
		if(rs!=null&&rs.size()>0){
			return true;
		}
		return false;
	}
}