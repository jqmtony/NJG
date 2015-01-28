package com.kingdee.eas.bpm.selectors;

import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.GuerdonBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFacadeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.util.app.ContextUtil;

public class SupplierFacade implements BillBaseSelector {

	public String[] ApproveBack(Context ctx, String strBTID,
			IObjectValue billInfo, String strXML) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] ApproveClose(Context ctx, String strBSID,
			IObjectValue billInfo, int procInstID,
			String processInstanceResult, String strComment, Date dtTime) {
		SupplierStockInfo Info = (SupplierStockInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		String sql = "";
		String sql1 = "";
		try {
			try {
				Info = SupplierStockFactory.getLocalInstance(ctx)
						.getSupplierStockInfo(new ObjectUuidPK(Info.getId()),
								getSelectors());
			} catch (EASBizException e) {
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			try {
				if ("1".equals(processInstanceResult)) {
					if (SupplierStateEnum.AUDITING.equals(Info.getState())) {
//						Info.setState(SupplierStateEnum.SUBMIT);
//						CompanyOrgUnitInfo company = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(Info.getCU().getId()));
//						AdminOrgUnitInfo admin = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitInfo(new ObjectUuidPK(Info.getCU().getId()));
//						ContextUtil.setCurrentFIUnit(ctx, company);
//						ContextUtil.setCurrentOrgUnit(ctx, admin);
//						GuerdonBillFactory.getLocalInstance(ctx).audit(Info.getId());
//						SupplierStockFactory.getLocalInstance(ctx)
//						Info.setState(SupplierStateEnum.YEATAUDIT);
						sql = " update T_FDC_SupplierStockAssign set Fstate='"+SupplierStateEnum.APPROVE_VALUE+"' where FSUPPLIERID='"+ Info.getId() + "'";
						FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
						bu.appendSql(sql);
						bu.executeUpdate(ctx);
						bu.clear();

					} else {
						str[2] = "����ͨ��ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if ("0".equals(processInstanceResult)) {
					if (SupplierStateEnum.AUDITING.equals(Info.getState())) {
						//Info.setState(SupplierStateEnum.AUDITUNPASS); // ����
					sql = " update T_FDC_SupplierStockAssign set Fstate='"+SupplierStateEnum.SAVE_VALUE+"' where FSUPPLIERID='"+ Info.getId() + "'";
					FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
					bu.appendSql(sql);
					bu.executeUpdate(ctx);
					bu.clear();
					
					String sql2 = " update T_FDC_SupplierStock set Fremark='BPM�ܾ�' where FID='"+ Info.getId() + "'";
					FDCSQLBuilder bu2 = new FDCSQLBuilder(ctx);
					bu2.appendSql(sql2);
					bu2.executeUpdate(ctx);
					bu2.clear();
					
					} else {
						str[2] = "������ͨ��ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if ("2".equals(processInstanceResult)) {
					if (SupplierStateEnum.AUDITING.equals(Info.getState()))
					{
					 sql = " update T_FDC_SupplierStockAssign set Fstate='"+SupplierStateEnum.SAVE_VALUE+"' where FSUPPLIERID='"+ Info.getId() + "'";
				     FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
				     bu.appendSql(sql);
				     bu.executeUpdate(ctx);
				     bu.clear();
					}
					else {
						str[2] = "�������ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if ("3".equals(processInstanceResult)) {
					if (SupplierStateEnum.AUDITING.equals(Info.getState())) {
						Info.setState(SupplierStateEnum.SAVE);
						sql = " update T_FDC_SupplierStockAssign set Fstate='"+SupplierStateEnum.SAVE_VALUE+"' where FSUPPLIERID='"+ Info.getId() + "'";
						FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
						bu.appendSql(sql);
						bu.executeUpdate(ctx);
						bu.clear();
						
						String sql2 = " update T_FDC_SupplierStock set FDESCRIPTION_L1='' where FID='"+ Info.getId() + "'";
						FDCSQLBuilder bu2 = new FDCSQLBuilder(ctx);
						bu2.appendSql(sql2);
						bu2.executeUpdate(ctx);
						bu2.clear();
						
					} else {
						str[2] = "����ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}

//				sql = " update T_FDC_SupplierStock set fState='"
//						+ Info.getState().getValue() + "' where fid='"
//						+ Info.getId() + "'";
//				FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
//				bu.appendSql(sql);
//				bu.executeUpdate(ctx);

			} catch (BOSException e) {
				str[2] = "���ݵ���stateֵ����״̬sqlʧ�ܣ�����getState�����Ƿ���ֵ,���鿴������log��־����sql��"
						+ sql;
				e.printStackTrace();
			}
		} catch (Exception e) {
			str[2] = "�����쳣����鿴������log��־��";
			e.printStackTrace();
		} finally {
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("��ͬ����ID��" + Info.getId() + "; ��ţ�"
						+ Info.getNumber());
				log.setDescription("BPM�����" + processInstanceResult
						+ "; EAS���:" + str[0] + "; ������Ϣ" + str[1] + str[2]);
				log.setSimpleName("BPM���̺ţ�" + procInstID + ";BPM������Ϣ:"
						+ strComment);
				log.setBeizhu("���ýӿڷ�����ApproveClose");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	public String[] GetbillInfo(Context ctx, String strBSID,
			IObjectValue billInfo) {
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
		SupplierStockInfo Info = (SupplierStockInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		try {
			try {
				Info = SupplierStockFactory.getLocalInstance(ctx)
						.getSupplierStockInfo(new ObjectUuidPK(Info.getId()),
								getSelectors());
			} catch (EASBizException e) {
				str[0] = "N";
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			try {
			    Info.setState(SupplierStateEnum.AUDITING);
				String sql = " update T_FDC_SupplierStock set fState='"+SupplierStateEnum.AUDITING_VALUE+"'"
						+ ", FDESCRIPTION_L1='" + procURL + "' "
						+ ", FsimpleName='" + procInstID + "' where fid='"
						+ Info.getId() + "'";
				FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
				bu.appendSql(sql);
				bu.executeUpdate(ctx);
				bu.clear();
				
				String sql2 = " update T_FDC_SupplierStockAssign set fState='"+SupplierStateEnum.AUDITING_VALUE+"' where FsupplierID='"+ Info.getId() + "'";
		        FDCSQLBuilder bu2 = new FDCSQLBuilder(ctx);
		        bu2.appendSql(sql2);
		        bu2.executeUpdate(ctx);
		        bu2.clear();
		
				
			} catch (BOSException e) {
				str[0] = "N";
				str[2] = "���ݵ���stateֵ����״̬sqlʧ�ܣ�����getState�����Ƿ���ֵ,���鿴������log��־��";
				e.printStackTrace();
			}
		} catch (Exception e) {
			str[0] = "N";
			str[2] = "�����쳣����鿴������log��־��";
			e.printStackTrace();
		} finally {
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("��ͬ����ID��" + Info.getId() + "; ��ţ�"
						+ Info.getNumber());
				log.setDescription("BPM�����" + success + "; EAS���:" + str[0]
						+ "; ������Ϣ:" + str[1] + str[2]);
				log.setSimpleName("BPM���̺ţ�" + procInstID + ";����URL:" + procURL);
				log.setBeizhu("���ýӿڷ�����_SubmitResult");
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
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("cu.id"));
		return sic;
	}

}
