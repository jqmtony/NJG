package com.kingdee.eas.fdc.contract.client;



import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ObjectNotFoundException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.LandDeveloperFactory;
import com.kingdee.eas.fdc.basedata.LandDeveloperInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.framework.EffectedStatusEnum;
import com.kingdee.eas.minghua.contract.BcxyDocE9Collection;
import com.kingdee.eas.minghua.contract.BcxyDocE9Factory;
import com.kingdee.eas.minghua.contract.BcxyDocE9Info;
import com.kingdee.eas.minghua.contract.BcxyDocFactory;
import com.kingdee.eas.minghua.contract.BcxyDocInfo;
import com.kingdee.eas.minghua.contract.ZcbhtDocFactory;
import com.kingdee.eas.minghua.contract.ZcbhtDocInfo;
import com.kingdee.eas.minghua.contract.ZyfbhtDocFactory;
import com.kingdee.eas.minghua.contract.ZyfbhtDocInfo;
import com.kingdee.eas.minghua.jczlwh.mj.SfshEnum;
import com.kingdee.eas.minghua.material.Jzygclcght2DocFactory;
import com.kingdee.eas.minghua.material.Jzygclcght2DocInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class CustomerContractUtil {
	public static final String T_ZCBHT = "T_CON_ZcbhtDoc";
	public static final String T_ZYFB = "T_CON_ZyfbhtDoc";
	public static final String T_CLCG = "T_MAT_Jzygclcght2Doc";
	public static final String T_BCXY = "T_CON_BcxyDoc";
	public static final String GROUPID = "00000000-0000-0000-0000-000000000000CCE7AED4";
	public static final String SHLS = "上海龙赛建设实业有限公司";
	
	public static boolean currentDCenterIsSync() throws Exception {
		CtrlUnitInfo cuInfo = CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(GROUPID));
		if("大华集团".equals(cuInfo.getName())) {
			String oql = "select id where longnumber like 'DH!DAILI%' and id='"+SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()+"'";
			if(CtrlUnitFactory.getRemoteInstance().getCtrlUnitCollection(oql).size() == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static String getIdByProAndType(String proId) throws Exception {
		String id = null;
		FDCSQLBuilder fdc = new FDCSQLBuilder();
		StringBuffer buffer = new StringBuffer();
		buffer.append("select contract.FID from T_CON_ContractBill contract left outer join T_FDC_ContractType contype on contract.FCONTRACTTYPEID=contype.FID ");
		buffer.append("where contract.FCURPROJECTID=? and contract.fcontrolunitid=? and contract.FcontractPropert='DIRECT' and contype.fname_l2='[建安]' ");
		fdc.appendSql(buffer.toString());
		fdc.addParam(proId);
		fdc.addParam(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		IRowSet set = fdc.executeQuery();
		if(set.next()){
			id = set.getString(1);
		}
		return id;
	}
	
	public static String getZbht(String sourceId ,String cuid) throws Exception {
		FDCSQLBuilder fdc = new FDCSQLBuilder();
		String id = null;
		fdc.appendSql("select FID from T_CON_ZcbhtDoc where FSOURCEBILLID=? and FCONTROLUNITID=? ");
		fdc.addParam(sourceId);
		fdc.addParam(cuid);
		IRowSet set = fdc.executeQuery();
		if(set.next()){
			id = set.getString(1);
		}
		return id;
	}
	
	public static String isFirstSync(String tableName, String sourceId ,String cuid) throws Exception {
		FDCSQLBuilder fdc = new FDCSQLBuilder();
		String id = null;
		fdc.appendSql("select FID from ").appendSql(tableName).appendSql(" where FSOURCEBILLID='").appendSql(sourceId).appendSql("' and ");
		fdc.appendSql("FCONTROLUNITID='").appendSql(cuid).appendSql("'");
		IRowSet set = fdc.executeQuery();
		if(set.next()){
			id = set.getString(1);
		}
		return id;
	}
	
	//在合同录入中，乙方的基础资料维护的是供应商，总包合同中建设方维护的是客户
	public static CustomerInfo getCustomerInfoByName1(String number, String name, String cuid, String gid) throws Exception{
		FDCSQLBuilder fdc = new FDCSQLBuilder();
		String id = null;
		CustomerInfo info = null;
		fdc.appendSql("select fid from T_BD_Customer where fname_l2  like '%").appendSql(name.replace(" ", "")).appendSql("%'");
		IRowSet set = fdc.executeQuery();
		if(set.next()){
			id = set.getString(1);
		}
		if(id == null){
			info = new CustomerInfo();
			info.setNumber(number);
			info.setName(name);
			info.setIsInternalCompany(false);
			info.setVersion(2);
			info.setEffectedStatus(EffectedStatusEnum.EFFECTED);
			info.setUsedStatus(UsedStatusEnum.APPROVED);
			IObjectPK pk = CustomerFactory.getRemoteInstance().addnew(info);
			id = pk.toString();
			updateCUAndGroup(cuid, id, gid);
		}
		info = CustomerFactory.getRemoteInstance().getCustomerInfo(new ObjectUuidPK(id));
		return info;
	}
	
	public static CustomerInfo getCustomerInfoByName(String name, String cuid) throws Exception{
		FDCSQLBuilder fdc = new FDCSQLBuilder();
		String id = null;
		CustomerInfo info = null;
		fdc.appendSql("select fid from T_BD_Customer where fname_l2 like '%"+name.replace(" ", "")+"%' ");
		IRowSet set = fdc.executeQuery();
		if(set.next()){
			id = set.getString(1);
		}
		if(id == null){
			return null;
		}
		
		info = CustomerFactory.getRemoteInstance().getCustomerInfo(new ObjectUuidPK(id));
		return info;
	}
	
	public static void updateCUAndGroup(String cuid, String id, String gid) throws Exception {
		FDCSQLBuilder fdc = new FDCSQLBuilder();
		fdc.appendSql("update T_BD_Customer set FCREATORID='', FLASTUPDATEUSERID='', FCONTROLUNITID=?, ");
		fdc.appendSql("FBROWSEGROUPID=?,FADMINCUID=? where fid=?");
		fdc.addParam(cuid);
		fdc.addParam(gid);
		fdc.addParam(cuid);
		fdc.addParam(id);
		fdc.executeUpdate();
		
		String detailId = BOSUuid.create(new CustomerGroupDetailInfo().getBOSType()).toString();
		fdc.clear();
		fdc.appendSql("insert into T_BD_CustomerGroupDetail values(?,?,?,?,?)");
		fdc.addParam(detailId);
		fdc.addParam("00000000-0000-0000-0000-000000000001BC122A7F");
		fdc.addParam(gid);
		fdc.addParam(id);
		fdc.addParam("内部客户");
		fdc.executeUpdate();
	}
	
	
	public static GeneralAsstActTypeInfo getInfoByName(SupplierInfo sup,String cu, String gid) throws Exception {
		FDCSQLBuilder fdc = new FDCSQLBuilder();
		String id = null;
		GeneralAsstActTypeInfo info = null;
		fdc.appendSql("select fid from T_BD_GeneralAsstActType where fname_l2=? and FCONTROLUNITID=? and FGROUPID=? ");
		fdc.addParam(sup.getName().trim());
		fdc.addParam(cu);
		fdc.addParam(gid);
		
		IRowSet set = fdc.executeQuery();
		if(set.next()){
			id = set.getString(1);
		}
		if(id == null){
			info = new GeneralAsstActTypeInfo();
			String cuNumber = CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(cu)).getNumber();
			info.setNumber(cuNumber+"_"+sup.getNumber());
			info.setName(sup.getName());
			info.setLongNumber(sup.getNumber());
			info.setId(BOSUuid.create(info.getBOSType()));
			Timestamp time = new Timestamp(new Date().getTime());
			info.setCreateTime(time);
			info.setLastUpdateTime(time);
			//组别为工程处
			id = info.getId().toString();
			insertGeneralAsstActTypeInfo(info, cu, gid);
			
		}
		else 
			info = GeneralAsstActTypeFactory.getRemoteInstance().getGeneralAsstActTypeInfo(new ObjectUuidPK(id));
		return info;
	}
	
	//根据控制单元的名称获得ID
	public static String getCUIDByName(String name) throws Exception {
		FDCSQLBuilder fdc = new FDCSQLBuilder();
		String id = null;
		fdc.appendSql("select fid from T_ORG_CtrlUnit where fname_l2='").appendSql(name.trim()).appendSql("'");
		IRowSet set = fdc.executeQuery();
		if(set.next()){
			id = set.getString(1);
		}
		return id;
	}
	
	public static String checkZbht(String typeId,String projectId) throws Exception {
		FDCSQLBuilder fdc = new FDCSQLBuilder();
		String id = null;
		fdc.appendSql("select fid from T_CON_ZcbhtDoc left out jion T_CON_ContractBill on where FCurProjectID='");
		fdc.appendSql(projectId).appendSql("' and FContractTypeID='").appendSql(typeId).appendSql("'");
		IRowSet set = fdc.executeQuery();
		if(set.next()){
			id = set.getString(1);
		}
		return id;
	}
	
	public static int contractType(String name){
		if("[建安]".equals(name)){
			return 1;
		}
		else if("[施工]".equals(name)){
			return 2;
		}
		else if("[材料]".equals(name)){
			return 3;
		}
		else if("[分包]".equals(name)){
			return 4;
		}
		return -1;
	}
	
	public static void syncData(List idList) throws Exception {
		ContractBillInfo contract = null;
		int flag = 0;
		HashMap<String,Integer> tips = new HashMap<String,Integer>();
		for(int i=0,size = idList.size(); i<size; i++) {
			contract = ContractBillFactory.getRemoteInstance().getContractBillInfo("select *,contractType.name where id='"+(String)idList.get(i)+"'");
			flag = contractType(contract.getContractType().getName());
			//判断合同类型
			if(flag != -1 ) {
				String mContractId = null;
				LandDeveloperInfo land = LandDeveloperFactory.getRemoteInstance().getLandDeveloperInfo(new ObjectUuidPK(contract.getLandDeveloper().getId()));
				String destOrg = null;
				if(flag == 1) {
					destOrg = SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(contract.getPartB().getId())).getName();
					if(SHLS.equals(destOrg)) {
						continue;
					}
				}
				else {
					destOrg = land.getName();
					if(SHLS.equals(destOrg)) {
						continue;
					}
					mContractId = checkBeforeSync(contract.getCurProject().getId().toString());
					if(mContractId == null) {
						MsgBox.showInfo("请先审批该项目对应的总包合同，系统结束此次同步操作！");
						SysUtil.abort();
					}
				}
				if(ContractPropertyEnum.DIRECT.equals(contract.getContractPropert())) {
					String cuid = getCUIDByName(destOrg);
					if(cuid == null){
						MsgBox.showInfo("无\""+destOrg+"\"控制单元，系统结束此次同步操作！");
						SysUtil.abort();
					}
					if(flag == 1){
						countTips(tips,syncZbht(contract,cuid,land,destOrg));
					}
					else if(flag == 2){
						countTips(tips,syncBcxy(contract,cuid,mContractId,destOrg));
					}
					else if(flag == 4){
						countTips(tips,syncZyfb(contract, cuid));
					}
					else if(flag == 3){
						countTips(tips,syncClcg(contract, cuid, land, destOrg));
					}
				}else if(ContractPropertyEnum.SUPPLY.equals(contract.getContractPropert())) {
					ContractBillEntryCollection coll = null;
					coll = ContractBillEntryFactory.getRemoteInstance().getContractBillEntryCollection("select content where rowKey='nu' and parent.id='"+contract.getId().toString()+"'");
					if(coll.size() > 0) {
						String mainContractId = coll.get(0).getContent();
						SelectorItemCollection sic = new SelectorItemCollection();
						if(flag == 1) {
							ZcbhtDocInfo zcbht = ZcbhtDocFactory.getRemoteInstance().getZcbhtDocInfo("select zzj where sourcebillid='"+mainContractId+"'");
							sic.add("zzj");
							zcbht.setZzj(zcbht.getZzj().add(contract.getCeremonyb()));
							ZcbhtDocFactory.getRemoteInstance().updatePartial(zcbht, sic);	
						}else if(flag == 2){
							BcxyDocInfo bcxy = BcxyDocFactory.getRemoteInstance().getBcxyDocInfo("select zzj where sourcebillid='"+mainContractId+"'");
							sic.add("zzj");
							bcxy.setZzj(bcxy.getZzj().add(contract.getCeremonyb()));
							ContractBillEntryCollection newes = ContractBillEntryFactory.getRemoteInstance().getContractBillEntryCollection("where parent.id='"+contract.getId().toString()+"'");
							String desc = newes.get(0).getDesc();
							if(desc != null && !"".equals(desc)) {
								ContractBillEntryCollection oldes = ContractBillEntryFactory.getRemoteInstance().getContractBillEntryCollection("where parent.id='"+mainContractId+"'");
								ContractBillEntryInfo entryInfo = oldes.get(0);
								BigDecimal oldValue = new BigDecimal(entryInfo.getDesc());
								BigDecimal nValue = oldValue.add(new BigDecimal(desc));
								entryInfo.setContent("三大材造价（原币金额）："+FDCClientHelper.getChineseFormat(nValue, false));
								entryInfo.setDesc(nValue.toString());
								SelectorItemCollection sicoll = new SelectorItemCollection();
								sicoll.add("content");
								sicoll.add("desc");
								ContractBillEntryFactory.getRemoteInstance().update(new ObjectUuidPK(entryInfo.getId()), entryInfo);
								sic.add("bz");
								bcxy.setBz(entryInfo.getContent()+entryInfo.getDesc());
							}
							BcxyDocFactory.getRemoteInstance().updatePartial(bcxy, sic);
						}else if(flag == 3){
							Jzygclcght2DocInfo jzyg = Jzygclcght2DocFactory.getRemoteInstance().getJzygclcght2DocInfo("select htje where sourcebillid='"+mainContractId+"'");
							sic.add("htje");
							jzyg.setHtje(jzyg.getHtje().add(contract.getCeremonyb()));
							Jzygclcght2DocFactory.getRemoteInstance().update(new ObjectUuidPK(jzyg.getId()), jzyg);
						}else if(flag == 4){
							ZyfbhtDocInfo zyht = ZyfbhtDocFactory.getRemoteInstance().getZyfbhtDocInfo("select hshtje where sourcebillid='"+mainContractId+"'");
							sic.add("hshtje");
							zyht.setHshtje(zyht.getHshtje().add(contract.getCeremonyb()));
							ZyfbhtDocFactory.getRemoteInstance().update(new ObjectUuidPK(zyht.getId()), zyht);
						}
					}
				}
			}
		}
		String showInfo = showTipsInfo(tips);
		if(showInfo != null) {
			MsgBox.showConfirm3a("合同信息同步成功！", showInfo);
		}
	}
	
	private static void countTips(HashMap<String,Integer> tips, String result) {
		if(result != null) {
			Integer count = tips.get(result);
			if(count != null) {
				tips.put(result, ++count);
			}else {
				tips.put(result, 1);
			}
		}
	}
	
	public static String showTipsInfo(HashMap<String,Integer> tips) {
		if(tips.size() > 0) {
			String key = null;
			StringBuffer buffer = new StringBuffer();
			for(Iterator<String> it=tips.keySet().iterator(); it.hasNext(); ) {
				key = it.next();
				if("zbnew".equals(key) && tips.get(key) != null) {
					buffer.append("[建安]同步新增条数：").append(tips.get(key)).append("\n");
				}else if("zbupdate".equals(key) && tips.get(key) != null) {
					buffer.append("[建安]同步更新条数：").append(tips.get(key)).append("\n");
				}else if("bcnew".equals(key) && tips.get(key) != null) {
					buffer.append("[施工]同步新增条数：").append(tips.get(key)).append("\n");
				}else if("bcupdate".equals(key) && tips.get(key) != null) {
					buffer.append("[施工]同步更新条数：").append(tips.get(key)).append("\n");
				}else if("zynew".equals(key) && tips.get(key) != null) {
					buffer.append("[分包]同步新增条数：").append(tips.get(key)).append("\n");
				}else if("zyupdate".equals(key) && tips.get(key) != null) {
					buffer.append("[分包]同步更新条数：").append(tips.get(key)).append("\n");
				}else if("clnew".equals(key) && tips.get(key) != null) {
					buffer.append("[材料]同步新增条数：").append(tips.get(key)).append("\n");
				}else if("clupdate".equals(key) && tips.get(key) != null) {
					buffer.append("[材料]同步更新条数：").append(tips.get(key)).append("\n");
				}
			}
			return buffer.toString();
		}
		return null;
	}
	
	private static GeneralAsstActTypeInfo firstSyncNum(ContractBillInfo contract, String cuid) throws Exception {
		//根据合同编号检索在目标组织下，是否存在自定义核算项目【06，合同】
		GeneralAsstActTypeInfo gaa = new GeneralAsstActTypeInfo();
		gaa.setNumber(contract.getNumber());
		gaa.setName(contract.getName());
		gaa.setLongNumber(contract.getNumber());
		gaa.setId(BOSUuid.create(gaa.getBOSType()));
		Timestamp time = new Timestamp(new Date().getTime());
		gaa.setCreateTime(time);
		gaa.setLastUpdateTime(time);
		insertGeneralAsstActTypeInfo(gaa,cuid,"iXhSqAEUEADgAWgywKgA0gXSzQw=");
		return gaa;
	}
	
	private static GeneralAsstActTypeInfo firstSyncProject(CurProjectInfo projectInfo, String cuid) throws Exception {
		String gaaId = isExistInCP(cuid, projectInfo.getName(), false);
		GeneralAsstActTypeInfo project = null;
		//根据工程项目检索在目标组织下，是否存在自定义核算项目【楼盘名称】
		if(gaaId == null){
			project = new GeneralAsstActTypeInfo();
			String cuNumber = CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(cuid)).getNumber();
			project.setNumber(cuNumber+"_"+projectInfo.getNumber());
			project.setName(projectInfo.getName());
			project.setLongNumber(projectInfo.getNumber());
			project.setId(BOSUuid.create(project.getBOSType()));
			Timestamp time = new Timestamp(new Date().getTime());
			project.setCreateTime(time);
			project.setLastUpdateTime(time);
			insertGeneralAsstActTypeInfo(project,cuid,"iXhSqAEUEADgAWb/wKgA0gXSzQw=");
		}else {
			project = GeneralAsstActTypeFactory.getRemoteInstance().getGeneralAsstActTypeInfo("select id where id='"+gaaId+"'");
		}
		return project;
	}
	
	//同步总包合同
	private static String syncZbht(ContractBillInfo info,String cuid, LandDeveloperInfo land,String destOrg) throws Exception {
		CustomerInfo customer = getCustomerInfoByName(land.getName().trim(),cuid);
		if(customer == null) {
			MsgBox.showInfo("请为\'"+destOrg+"\'分配客户："+land.getName()+"！系统结束此次同步操作！");
			SysUtil.abort();
		}
		ZcbhtDocInfo zcbht = null;
		String zbhtId = isFirstSync(T_ZCBHT,info.getId().toString(),cuid);
		if(zbhtId != null) {
			//第二次同步，应该为更新操作
			zcbht = ZcbhtDocFactory.getRemoteInstance().getZcbhtDocInfo("select djzt,htmc,zzj,qdrq,jfhtbh.number where id='"+zbhtId+"'");
			if(!zcbht.getDjzt().equals(SfshEnum.yes)) {
				SelectorItemCollection sic = new SelectorItemCollection();
				if(!info.getName().equals(zcbht.getHtmc())) {
					zcbht.setHtmc(info.getName());
					sic.add("htmc");
				}
				if(info.getCeremonyb().compareTo(zcbht.getZzj()) != 0) {
					zcbht.setZzj(info.getCeremonyb());
					sic.add("zzj");
				}
				if(info.getSignDate().compareTo(zcbht.getQdrq()) != 0) {
					zcbht.setQdrq(info.getSignDate());
					sic.add("qdrq");
				}
				boolean isNewNumber = false;
				if(!info.getNumber().equals(zcbht.getJfhtbh().getNumber())) {
					syncGaaInfo(info.getName(),info.getNumber(),zcbht.getJfhtbh().getId().toString());
					SelectorItemCollection selector = new SelectorItemCollection();
					sic.add("number");
					sic.add("contractPropert");
					ContractBillCollection bills = contractFilter(info, selector);;
					ContractBillInfo tempInfo = null;
					for(int i=0, size=bills.size(); i<size; i++) {
						tempInfo = bills.get(i);
						tempInfo.setNumber(info.getNumber()+tempInfo.getNumber().substring(zcbht.getJfhtbh().getNumber().length()));
						ContractBillFactory.getRemoteInstance().updatePartial(tempInfo, sic);
						if(ContractPropertyEnum.DIRECT.equals(tempInfo.getContractPropert())) {
							syncFBNumber(tempInfo.getContractType().getName(), tempInfo.getName(),tempInfo.getNumber(), tempInfo.getId().toString());
						}
					}
					isNewNumber = true;
				}
				if(sic.size() > 0) {
					ZcbhtDocFactory.getRemoteInstance().updatePartial(zcbht, sic);
				}
				if(sic.size() > 0 || isNewNumber) {
					BcxyDocE9Collection e9s = BcxyDocE9Factory.getRemoteInstance().getBcxyDocE9Collection("select jfhtbh,htmc,zzj where zcbhtid='"+zbhtId+"'");
					BcxyDocE9Info e9 = null;
					sic.clear();
					sic.add("jfhtbh");
					sic.add("htmc");
					sic.add("zzj");
					for(int i=0, size=e9s.size(); i<size; i++) {
						e9 = e9s.get(i);
						e9.setJfhtbh(zcbht.getJfhtbh().getNumber());
						e9.setHtmc(zcbht.getHtmc());
						e9.setZzj(zcbht.getZzj());
						BcxyDocE9Factory.getRemoteInstance().update(new ObjectUuidPK(e9.getId()), e9);
					}
					return "zbupdate";
				}
			}else {
				showMsgAndAbort();
			}
		}
		else {
			zcbht = new ZcbhtDocInfo();
			zcbht.setDjzt(SfshEnum.not);
			zcbht.setHtmc(info.getName());
			CurProjectInfo project = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(info.getCurProject().getId()));
			zcbht.setProjectName(firstSyncProject(project, cuid));
			zcbht.setZzj(info.getCeremonyb());
			zcbht.setQdrq(info.getSignDate());
			zcbht.setJsf(customer);
			zcbht.setJfhtbh(firstSyncNum(info, cuid));
			zcbht.setIsFromDH(true);
			zcbht.setSourceBillId(info.getId().toString());
			IObjectPK pk = ZcbhtDocFactory.getRemoteInstance().addnew(zcbht);
			updateCUID(T_ZCBHT,cuid, pk.toString());
			return "zbnew";
		}
		return null;
	}
	
	//同步执行合同
	private static String syncBcxy(ContractBillInfo info,String cuid,String mid,String destOrg) throws Exception {
		String custName = ContractBillFactory.getRemoteInstance().getContractBillInfo("select landDeveloper.name where id='"+mid+"'").getLandDeveloper().getName();
		CustomerInfo customer = getCustomerInfoByName(custName,cuid);
		if(customer == null) {
			MsgBox.showInfo("请为\'"+destOrg+"\'分配客户："+custName+"！系统结束此次同步操作！");
			SysUtil.abort();
		}
		BcxyDocInfo bcxy = null;
		String bcxyId = isFirstSync(T_BCXY,info.getId().toString(),cuid);
		SupplierInfo supplier = SupplierFactory.getRemoteInstance().getSupplierInfo("select name,number where id='"+info.getPartB().getId().toString()+"'");
		if(bcxyId != null) {
			//第二次同步，应该为更新操作
			bcxy = BcxyDocFactory.getRemoteInstance().getBcxyDocInfo("select djzt,htmc,zzj,qdrq,jsfhtbh.number,gcchfbf.name where id='"+bcxyId+"'");
			if(!bcxy.getDjzt().equals(SfshEnum.yes)) {
				SelectorItemCollection sic = new SelectorItemCollection();
				if(!info.getName().equals(bcxy.getHtmc())) {
					bcxy.setHtmc(info.getName());
					sic.add("htmc");
				}
				if(info.getCeremonyb().compareTo(bcxy.getZzj()) != 0) {
					bcxy.setZzj(info.getCeremonyb());
					sic.add("zzj");
				}
				if(info.getSignDate().compareTo(bcxy.getQdrq()) != 0) {
					bcxy.setQdrq(info.getSignDate());
					sic.add("qdrq");
				}
				if(!info.getNumber().equals(bcxy.getJsfhtbh().getNumber())) {
					syncGaaInfo(info.getName(),info.getNumber(),bcxy.getJsfhtbh().getId().toString());
					
				}
				if(!supplier.getName().equals(bcxy.getGcchfbf().getName())) {
					bcxy.setGcchfbf(getInfoByName(supplier,cuid,"iXhSqAEUEADgBLXBwKgA0gXSzQw="));
					sic.add("gcchfbf");
				}
				ContractBillEntryCollection billes = ContractBillEntryFactory.getRemoteInstance().getContractBillEntryCollection("where parent.id='"+info.getId().toString()+"'");
				String bz = "备注信息";
				for(int i = 0;i<billes.size();i++){
					bz = bz + "，" +billes.get(i).getContent()+"， "+billes.get(i).getDesc();
				}
				bcxy.setBz(bz);
				sic.add("bz");
				BcxyDocFactory.getRemoteInstance().update(new ObjectUuidPK(bcxy.getId()), bcxy);
				return "bcupdate";
			}
			else {
				showMsgAndAbort();
			}
		}
		else {
			CurProjectInfo pro = CurProjectFactory.getRemoteInstance().getCurProjectInfo("select name,number where id='"+info.getCurProject().getId().toString()+"'");
			bcxy = new BcxyDocInfo();
			ContractBillEntryCollection billes = ContractBillEntryFactory.getRemoteInstance().getContractBillEntryCollection("where parent.id='"+info.getId().toString()+"'");
			String bz = "备注信息";
			for(int i = 0;i<billes.size();i++){
				bz = bz + "，" +billes.get(i).getContent()+"， "+billes.get(i).getDesc();
			}
			bcxy.setBz(bz);
			bcxy.setHtmc(info.getName());
			bcxy.setZzj(info.getCeremonyb());
			bcxy.setQdrq(info.getSignDate());
			bcxy.setGcxm(firstSyncProject(pro,cuid));
			bcxy.setJsf(customer);
			bcxy.setJsfhtbh(firstSyncNum(info, cuid));
			bcxy.setGcchfbf(getInfoByName(supplier,cuid,"iXhSqAEUEADgBLXBwKgA0gXSzQw="));
			bcxy.setDjzt(SfshEnum.not);
			bcxy.setIsFromDH(true);
			bcxy.setSourceBillId(info.getId().toString());
			IObjectPK pk = BcxyDocFactory.getRemoteInstance().addnew(bcxy);
			updateCUID(T_BCXY, cuid, pk.toString());
			//保存补充协议分录实体信息
			{	bcxy.setId(BOSUuid.read(pk.toString()));
				BcxyDocE9Info e9 = new BcxyDocE9Info();
				String zbid = getZbht(mid,cuid);
				ZcbhtDocInfo zcbInfo = ZcbhtDocFactory.getRemoteInstance().getZcbhtDocInfo("select htbh,jfhtbh.number,htmc,zzj,lh,jzmj where id='"+zbid+"'");
				e9.setZcbhtid(zcbInfo.getId().toString());
				e9.setJfhtbh(zcbInfo.getJfhtbh().getNumber());
				if(zcbInfo.getHtbh()!=null)
					e9.setHtbh(zcbInfo.getHtbh());
				if(zcbInfo.getHtmc()!=null)
					e9.setHtmc(zcbInfo.getHtmc());
				if(zcbInfo.getZzj()!=null)
					e9.setZzj(zcbInfo.getZzj());
				if(zcbInfo.getLh()!=null)
					e9.setLh(zcbInfo.getLh());
				if(zcbInfo.getJzmj()!=null)
					e9.setJzmj(zcbInfo.getJzmj());
				e9.setParent(bcxy);
				e9.setSeq(1);
				BcxyDocE9Factory.getRemoteInstance().addnew(e9);
			}
			return "bcnew";
		}
		return null;
	}
	
	//同步专业分包合同
	private static String syncZyfb(ContractBillInfo info, String cuid) throws Exception {
		ZyfbhtDocInfo zyht = null;
		String zyhtId = isFirstSync(T_ZYFB,info.getId().toString(),cuid);
		SupplierInfo supplier = SupplierFactory.getRemoteInstance().getSupplierInfo("select name,number where id='"+info.getPartB().getId().toString()+"'");
		if(zyhtId != null){
			//第二次同步，应该为更新操作
			zyht = ZyfbhtDocFactory.getRemoteInstance().getZyfbhtDocInfo("select djzt,htmc,hshtje,qdrq,zyfbhtbh.number,fbf.name where id='"+zyhtId+"'");
			if(!zyht.getDjzt().equals(SfshEnum.yes)) {
				SelectorItemCollection sic = new SelectorItemCollection();
				if(!info.getName().equals(zyht.getHtmc())) {
					zyht.setHtmc(info.getName());
					sic.add("htmc");
				}
				if(info.getCeremonyb().compareTo(zyht.getHshtje()) != 0) {
					zyht.setHshtje(info.getCeremonyb());
					sic.add("zzj");
				}
				if(info.getSignDate().compareTo(zyht.getQdrq()) != 0) {
					zyht.setQdrq(info.getSignDate());
					sic.add("qdrq");
				}
				if(!info.getNumber().equals(zyht.getZyfbhtbh().getNumber())) {
					syncGaaInfo(info.getName(),info.getNumber(),zyht.getZyfbhtbh().getId().toString());
				}
				if(!supplier.getName().equals(zyht.getFbf().getName())) {
					GeneralAsstActTypeInfo fbf = getInfoByName(supplier,cuid,"iXhSqAEUEADgBLXBwKgA0gXSzQw=");
					zyht.setFbf(fbf);
					sic.add("gcchfbf");
				}
				if(sic.size() > 0) {
					ZyfbhtDocFactory.getRemoteInstance().update(new ObjectUuidPK(zyht.getId()), zyht);
					return "zyupdate";
				}
			}else {
				showMsgAndAbort();
			}
		}
		else {
			zyht = new ZyfbhtDocInfo();
			zyht.setHtmc(info.getName());
			//默认带出的是含税金额
			zyht.setHshtje(info.getCeremonyb());
			zyht.setQdrq(info.getSignDate());
			zyht.setZyfbhtbh(firstSyncNum(info, cuid));
			zyht.setFbf(getInfoByName(supplier,cuid,"iXhSqAEUEADgBLXBwKgA0gXSzQw="));
			zyht.setDjzt(SfshEnum.not);
			zyht.setIsFromDH(true);
			zyht.setSourceBillId(info.getId().toString());
			IObjectPK pk = ZyfbhtDocFactory.getRemoteInstance().addnew(zyht);
			updateCUID(T_ZYFB,cuid, pk.toString());
			return "zynew";
		}
		return null;
	}

	private static void showMsgAndAbort() {
		MsgBox.showInfo("该合同对应的名华合同已审批，无法执行此操作！");
		SysUtil.abort();
	}
	
	
	//同步材料采购合同
	private static String syncClcg(ContractBillInfo info, String cuid,LandDeveloperInfo land,String destOrg) throws Exception {
		CustomerInfo xfdw = getCustomerInfoByName(land.getName(),cuid);
		SupplierInfo supplier = SupplierFactory.getRemoteInstance().getSupplierInfo("select name,number where id='"+info.getPartB().getId().toString()+"'");
		CustomerInfo gydw = getCustomerInfoByName(supplier.getName(),cuid);
		if(xfdw == null){
			MsgBox.showInfo("请为\'"+destOrg+"\'分配客户："+land.getName()+"！系统结束此次同步操作！");
			SysUtil.abort();
		}
		if(gydw == null){
			MsgBox.showInfo("请为\'"+destOrg+"\'分配客户："+supplier.getName()+"！系统结束此次同步操作！");
			SysUtil.abort();
		}
		Jzygclcght2DocInfo jzyg = null;
		String jzygId = isFirstSync(T_CLCG,info.getId().toString(),cuid);
		if(jzygId != null){
			//第二次同步，应该为更新操作
			jzyg = Jzygclcght2DocFactory.getRemoteInstance().getJzygclcght2DocInfo("select djzt,htmc,htje,qdrq,cghtbh.number where id='"+jzygId+"'");
			if(!jzyg.getDjzt().equals(SfshEnum.yes)) {
				SelectorItemCollection sic = new SelectorItemCollection();
				if(!info.getName().equals(jzyg.getHtmc())) {
					jzyg.setHtmc(info.getName());
					sic.add("htmc");
				}
				if(info.getCeremonyb().compareTo(jzyg.getHtje()) != 0) {
					jzyg.setHtje(info.getCeremonyb());
					sic.add("zzj");
				}
				if(info.getSignDate().compareTo(jzyg.getQdrq()) != 0) {
					jzyg.setQdrq(info.getSignDate());
					sic.add("qdrq");
				}
				if(!info.getNumber().equals(jzyg.getCghtbh().getNumber())) {
					syncGaaInfo(info.getName(),info.getNumber(),jzyg.getCghtbh().getId().toString());
				}
				if(sic.size() > 0) {
					Jzygclcght2DocFactory.getRemoteInstance().update(new ObjectUuidPK(jzyg.getId()), jzyg);
					return "clupdate";
				}
			}else {
				showMsgAndAbort();
			}
		}
		else {
			jzyg = new Jzygclcght2DocInfo();
			jzyg.setHtmc(info.getName());
			jzyg.setHtje(info.getCeremonyb());
			jzyg.setQdrq(info.getSignDate());
			jzyg.setCghtbh(firstSyncNum(info, cuid));
			jzyg.setXfdw(xfdw);
			jzyg.setGydw(gydw);
			jzyg.setDjzt(SfshEnum.not);
			jzyg.setIsFromDH(true);
			jzyg.setSourceBillId(info.getId().toString());
			IObjectPK pk = Jzygclcght2DocFactory.getRemoteInstance().addnew(jzyg);
			updateCUID(T_CLCG,cuid, pk.toString());
			return "clnew";
		}
		return null;
	}
	
	public static void updateCUID(String tableName, String cuid, String fid) throws Exception{
		FDCSQLBuilder fdc = new FDCSQLBuilder();
		fdc.appendSql("update ").appendSql(tableName).appendSql(" set FCREATORID='', FLASTUPDATEUSERID='', FCONTROLUNITID='");
		fdc.appendSql(cuid).appendSql("' where FID='").appendSql(fid).appendSql("'");
		fdc.executeUpdate();
	}

	
	private static String isExistInCP (String partbCUId, String param, boolean flag) throws Exception{
		FDCSQLBuilder fdc = new FDCSQLBuilder();
		if(flag) {
			fdc.appendSql("select FID from T_BD_GeneralAsstActType where FCONTROLUNITID=? and FNUMBER=? and ( ");
			fdc.appendSql("FGROUPID='iXhSqAEUEADgAWgywKgA0gXSzQw=' or FGROUPID='afIGbPamTlqAY8Zl6lKbsQXSzQw=' )");
		}else {
			fdc.appendSql("select FID from T_BD_GeneralAsstActType where FCONTROLUNITID=? and FNAME_L2=? and ( ");
			fdc.appendSql("FGROUPID='iXhSqAEUEADgAWb/wKgA0gXSzQw=' or FGROUPID='WdZnIfGXTQqOd0XYrgcRqwXSzQw=' )");
		}
		
		fdc.addParam(partbCUId);
		fdc.addParam(param);
		fdc.getSql();
		String id = null;
		IRowSet set = fdc.executeQuery();
		if(set.next()){
			id = set.getString(1);
		}
		return id;
	}
	
	//在同步分包时，检测总包有没有同步，如果没有提示用户先同步总包
	public static String checkBeforeSync(String proId) throws Exception{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", proId));
		filter.getFilterItems().add(new FilterItemInfo("contractType.name", "[建安]"));
		filter.getFilterItems().add(new FilterItemInfo("state", "4AUDITTED"));
		filter.getFilterItems().add(new FilterItemInfo("contractPropert", "DIRECT"));
		filter.getFilterItems().add(new FilterItemInfo("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
		view.setFilter(filter);
		ContractBillCollection coll = ContractBillFactory.getRemoteInstance().getContractBillCollection(view);
		if(coll.size() == 1){
			return coll.get(0).getId().toString();
		}
		
		return null;
	}
	
	public static void insertGeneralAsstActTypeInfo(GeneralAsstActTypeInfo info, String cuid, String gid) throws Exception{
		FDCSQLBuilder fdc = new FDCSQLBuilder();
		fdc.appendSql("insert into T_BD_GeneralAsstActType(FID,FCREATETIME,FLASTUPDATETIME,FCONTROLUNITID,FNAME_L2,FNUMBER,FISLEAF,FLEVEL,FLONGNUMBER,FGROUPID,FISENABLED,FDISPLAYNAME_L2,FCREATORCOMPANYID) ");
		fdc.appendSql("values(?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		fdc.addParam(info.getId().toString());
		fdc.addParam(info.getCreateTime());
		fdc.addParam(info.getLastUpdateTime());
		fdc.addParam(cuid);
		fdc.addParam(info.getName());
		fdc.addParam(info.getNumber());
		fdc.addParam(Integer.valueOf(1));
		fdc.addParam(Integer.valueOf(1));
		fdc.addParam(info.getLongNumber());
		fdc.addParam(gid);
		fdc.addParam(Integer.valueOf(1));
		fdc.addParam(info.getName());
		fdc.addParam(cuid);
		fdc.executeUpdate();
	}
	
	public static void syncGaaInfo(String name,String newNumber, String gaaId) throws BOSException, EASBizException {
		GeneralAsstActTypeInfo gaa = GeneralAsstActTypeFactory.getRemoteInstance().getGeneralAsstActTypeInfo("select number where id='"+gaaId+"'");
		SelectorItemCollection sicl = new SelectorItemCollection();
		sicl.add("number");
		sicl.add("name");
		sicl.add("longNumber");
		sicl.add("displayName");
		gaa.setNumber(newNumber);
		gaa.setName(name);
		gaa.setLongNumber(newNumber);
		gaa.setDisplayName(name);
		GeneralAsstActTypeFactory.getRemoteInstance().updatePartial(gaa,sicl);
	}
	
	public static ContractBillCollection contractFilter(ContractBillInfo oldBillInfo,SelectorItemCollection sic) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", oldBillInfo.getCurProject().getId()));
		filter.getFilterItems().add(new FilterItemInfo("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("contractType.name", "[施工]"));
		filter.getFilterItems().add(new FilterItemInfo("contractType.name", "[材料]"));
		filter.getFilterItems().add(new FilterItemInfo("contractType.name", "[分包]"));
		filter.setMaskString("#0 and #1 and (#2 or #3 or #4)");
		view.setSelector(sic);
		return ContractBillFactory.getRemoteInstance().getContractBillCollection(view);
	}
	
	public static void syncFBNumber(String typeName, String billName, String billNum, String billId) throws BOSException,EASBizException {
		if("[施工]".equals(typeName)) {
			try {
				BcxyDocInfo bcxy = BcxyDocFactory.getRemoteInstance().getBcxyDocInfo("select jsfhtbh.id where sourceBillId='"+billId+"'");
				syncGaaInfo(billName,billNum, bcxy.getJsfhtbh().getId().toString());
			} catch (ObjectNotFoundException e2) {
			}
		}else if("[材料]".equals(typeName)) {
			try {
				Jzygclcght2DocInfo jzyg = Jzygclcght2DocFactory.getRemoteInstance().getJzygclcght2DocInfo("select cghtbh.id where sourceBillId='"+billId+"'");
				syncGaaInfo(billName,billNum, jzyg.getCghtbh().getId().toString());
			} catch (ObjectNotFoundException e2) {
			}
		}else {
			try {
				ZyfbhtDocInfo zyfb = ZyfbhtDocFactory.getRemoteInstance().getZyfbhtDocInfo("select zyfbhtbh.id where sourceBillId='"+billId+"'");
				syncGaaInfo(billName,billNum, zyfb.getZyfbhtbh().getId().toString());
			} catch (ObjectNotFoundException e2) {
			}
		}
	}
	
}
