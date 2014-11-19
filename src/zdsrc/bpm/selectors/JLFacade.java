package com.kingdee.eas.bpm.selectors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.xerces.xni.parser.XMLDocumentFilter;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.bsf.annotation.Return;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.BillBaseSelector;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.bpm.common.ViewXmlUtil;
import com.kingdee.eas.bpm.common.XMLUtil;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.GuerdonBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.util.app.ContextUtil;
//奖励单
public class JLFacade implements BillBaseSelector{

	public String[] ApproveBack(Context ctx, String strBTID,
			IObjectValue billInfo, String strXML) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] ApproveClose(Context ctx, String strBSID,
			IObjectValue billInfo, int procInstID,
			String processInstanceResult, String strComment, Date dtTime) {
		GuerdonBillInfo Info = (GuerdonBillInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
    	String sql = "";
    	String sql1 = "";
		try {
			try{
				Info = GuerdonBillFactory.getLocalInstance(ctx).getGuerdonBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try{
				if("1".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
					{
//						Info.setState(FDCBillStateEnum.SUBMITTED);
//						CompanyOrgUnitInfo company = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(Info.getCU().getId()));
//						AdminOrgUnitInfo admin=AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitInfo(new ObjectUuidPK(Info.getCU().getId()));                            
//						ContextUtil.setCurrentFIUnit(ctx, company);
//						ContextUtil.setCurrentOrgUnit(ctx, admin);
					    GuerdonBillFactory.getLocalInstance(ctx).audit(Info.getId());
					    Info.setState(FDCBillStateEnum.AUDITTED); 
					}
					else{
						str[2] = "审批通过失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if("0".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
					{	
					 Info.setState(FDCBillStateEnum.SAVED);   //作废改为以保存
					}
					else{
						str[2] = "审批不通过失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if("2".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.SAVED);
					else{
						str[2] = "审批打回失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if("3".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState())){
						Info.setState(FDCBillStateEnum.SAVED);
						sql = " update T_CON_GuerdonBill set fDescription='' where fid='"+Info.getId()+"'";
						FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
						bu.appendSql(sql);
						bu.executeUpdate(ctx);
					}
					else{
						str[2] = "撤销失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
			   
				sql = " update T_CON_GuerdonBill set fState='"+Info.getState().getValue()+"' where fid='"+Info.getId()+"'";
				FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
				bu.appendSql(sql);
				bu.executeUpdate(ctx);

			}
			catch (BOSException e) {
				str[2] = "根据单据state值更新状态sql失败，请检查getState方法是否有值,并查看服务器log日志！【sql】"+sql;
				e.printStackTrace();
			}
		}catch (Exception e) {
			str[2] = "其他异常，请查看服务器log日志！";
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("合同单据ID："+Info.getId()+"; 编号："+Info.getNumber());
				log.setDescription("BPM结果："+processInstanceResult+"; EAS结果:"+str[0]+"; 错误信息"+str[1]+str[2]);
				log.setSimpleName("BPM流程号："+procInstID+";BPM反馈信息:"+strComment);
				log.setBeizhu("调用接口方法：ApproveClose");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	public String[] GetbillInfo(Context ctx, String strBSID,
			IObjectValue billInfo) {
//		GuerdonBillInfo Info = (GuerdonBillInfo) billInfo;
//		String[] str = new String[3];
//		str[0] = "Y";
//		StringBuffer xml = new StringBuffer();
//		try {
//			try {
//				Info = GuerdonBillFactory.getLocalInstance(ctx)
//						.getGuerdonBillInfo(
//								new ObjectUuidPK(Info.getId()), getSelectors());
//			} catch (EASBizException e) {
//				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
//				e.printStackTrace();
//			}
//			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			str=ViewXmlUtil.getViewXmlString(ctx, "007",Info.getId().toString());
//		}finally
//		{
//			
//		}
//			xml.append("<DATA>\n");
//			xml.append("<OriginalAmount>"+Info.getOriginalAmount()+"</OriginalAmount>\n");  //奖励原币金额 
//			xml.append("<Amount>"+Info.getAmount()+"</Amount>\n");    //本币金额
//			xml.append("</DATA>");
			//str[1] = xml.toString();
//		} catch (Exception e) {
//			str[0] = "N";
//			str[2] = "其他异常，请查看服务器log日志！";
//			e.printStackTrace();
//		} finally {
//			BPMLogInfo log = new BPMLogInfo();
//			try {
//				log.setLogDate(new Date());
//				log.setName("结算单单据ID：" + Info.getId() + "; 编号："
//						+ Info.getNumber());
//				log.setDescription("EAS结果:" + str[0] + "; 错误信息" + str[1]
//						+ str[2]);
//				log.setBeizhu("调用接口方法：GetbillInfo");
//				BPMLogFactory.getLocalInstance(ctx).save(log);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	//	return str;
		return null;
	}

	public String[] GetrRelatedBillInfo(Context ctx, String strBTID,
			IObjectValue billInfo, String strRelatedCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] SubmitResult(Context ctx, String strBSID,
			IObjectValue billInfo, boolean success, int procInstID,
			String procURL, String strMessage) {
		GuerdonBillInfo Info =(GuerdonBillInfo) billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
		try {
			try{
				Info = GuerdonBillFactory.getLocalInstance(ctx).getGuerdonBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[0] = "N";
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try{
				Info.setState(FDCBillStateEnum.AUDITTING);
				String sql = " update T_CON_GuerdonBill set fState='"+Info.getState().getValue()+"'" +
						", fDescription='"+procURL+"' " +
						", FSourceFunction='"+procInstID+"' where fid='"+Info.getId()+"'";
				FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
				bu.appendSql(sql);
				bu.executeUpdate(ctx);
			}
			catch (BOSException e) {
				str[0] = "N";
				str[2] = "根据单据state值更新状态sql失败，请检查getState方法是否有值,并查看服务器log日志！";
				e.printStackTrace();
			}
		}catch (Exception e) {
			str[0] = "N";
			str[2] = "其他异常，请查看服务器log日志！";
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("合同单据ID："+Info.getId()+"; 编号："+Info.getNumber());
				log.setDescription("BPM结果："+success+"; EAS结果:"+str[0]+"; 错误信息:"+str[1]+str[2]);
				log.setSimpleName("BPM流程号："+procInstID+";流程URL:"+procURL);
				log.setBeizhu("调用接口方法：_SubmitResult");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("State"));
		sic.add(new SelectorItemInfo("Description"));
		sic.add(new SelectorItemInfo("SourceFunction"));
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("contract.id"));
		sic.add(new SelectorItemInfo("contract.name"));
		sic.add(new SelectorItemInfo("contract.number"));
		sic.add(new SelectorItemInfo("currency.name"));
		sic.add(new SelectorItemInfo("exRate"));
		sic.add(new SelectorItemInfo("putOutType"));
		sic.add(new SelectorItemInfo("guerdonDes"));
		sic.add(new SelectorItemInfo("guerdonThings"));
		sic.add(new SelectorItemInfo("fiVouchered"));
		
		return sic;
	}

}
