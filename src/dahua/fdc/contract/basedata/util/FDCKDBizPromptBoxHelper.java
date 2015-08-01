package com.kingdee.eas.fdc.basedata.util;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.text.JTextComponent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OUEditUI;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.news.StrategyPactInfo;
import com.kingdee.eas.fdc.invite.news.TenderDiscusstionInfo;
import com.kingdee.eas.fdc.invite.news.client.InviteNewProjectEditUI;
import com.kingdee.eas.fdc.invite.news.client.InviteWFBillViewUI;
import com.kingdee.eas.fdc.invite.news.client.StrategyPactEditUI;
import com.kingdee.eas.fdc.invite.news.client.TenderDiscusstionEditUI;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.client.SupplierStockEditUI;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCKDBizPromptBoxHelper {

	public static KDTextField txtField = null;

	public static void initBox(KDBizPromptBox prmtBox) {
		initBox(prmtBox, "");
	}
	public static void initBox(){}
	public static void initBox(KDBizPromptBox prmtBox, final String editUIName) {

		JTextComponent txt = (JTextComponent) prmtBox.getEditor();
		txt.setBorder(BorderFactory.createLineBorder(Color.BLUE));

		txt.setSelectedTextColor(Color.BLUE);
		txt.setSelectionColor(Color.WHITE);

		txt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				JTextComponent object = (JTextComponent) e.getSource();
				if (object.getParent() instanceof KDBizPromptBox) {
					KDBizPromptBox prmtF7 = (KDBizPromptBox) object.getParent();
					if (prmtF7.getData() instanceof IObjectValue) {
						IObjectValue objectValue = (IObjectValue) prmtF7.getData();
						String id = objectValue.getString("id");
						try {
							String ui = editUIName;

							if (ui == null || ui.length() == 0) {
								ui = getEditUIName(objectValue);
							}

							if (ui == null || ui.length() == 0)
								ui = BTPManagerFactory.getRemoteInstance().getEntityObjectInfoExtendPro(new ObjectUuidPK(id).getObjectType().toString(), "editUI");

							if (ui == null || ui.length() == 0) {
								return;
							}

							UIContext uiContext = new UIContext(this);
							uiContext.put(UIContext.ID, id);
							initUIContext(objectValue, uiContext);
							IUIWindow uiWindow = null;
							uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ui, uiContext, null, OprtState.VIEW, WinStyle.SHOW_TITLEPANEL);

							CoreUI uiObject = (CoreUI) uiWindow.getUIObject();
							FDCClientHelper.setComponentEnable(uiObject, false);
							uiWindow.show();

						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
	}
	
	protected static UIContext initUIContext(IObjectValue value, UIContext uiContextCC){
		if (value != null && value instanceof InviteProjectInfo) {
			String inviteID = ((FDCBillInfo)value).getId().toString();
			StringBuffer sb = new StringBuffer();
			sb.append("select invprj.fid invprjID, qualify.fid qualifyID,  busTech.fid busTechID,  busbiz.fid busbizID, distri.fid distriID,")
			  .append(	" startbid.fid startbidID,invAppra.fid invAppraID from t_inv_inviteProject invprj");
			sb.append( " left join T_INV_SupplierQualify qualify on qualify.FInviteProjectID = invprj.FID ");
			sb.append(" left join T_INV_BiddingDocumentTech busTech on busTech.FInviteProjectID = invprj.FID ");
			sb.append(" left join T_INV_BiddingDocumentBusiness busbiz on busbiz.FInviteProjectID = invprj.FID ");
			sb.append(" left join T_INV_InviteDistributed distri on distri.FInvProID = invprj.FID \n");
			
			sb.append(" left join T_INV_StartBid startbid on startbid.FInviteProjectID = invprj.FID \n");
			sb.append(" left join T_INV_InviteAppraising invAppra on invAppra.FInvProID = invprj.FID \n");
			sb.append(" where invprj.fid='"+inviteID+"'");
			FDCSQLBuilder strSql = new FDCSQLBuilder();
			strSql.appendSql(sb.toString());

			IRowSet quitSet = null;
			try {
				quitSet = strSql.executeQuery(); //²éÑ¯½á¹û
			} catch (BOSException e1) {
				Logger.getLogger(FDCKDBizPromptBoxHelper.class).error(e1.getMessage());
			}
			try {
				if (quitSet!=null && quitSet.next()) {
					
					String suppQualiID = quitSet.getString("qualifyID");
					String techID = quitSet.getString("busTechID");
					String bizID = quitSet.getString("busbizID");
					String distributeID = quitSet.getString("distriID");
					String startBidID = quitSet.getString("startbidID");
					String appraisingID = quitSet.getString("invAppraID");
					uiContextCC.put(UIContext.ID, null);
					uiContextCC.put("inviteID", inviteID);
					uiContextCC.put("suppQualifyID", suppQualiID);
					uiContextCC.put("techID", techID);
					uiContextCC.put("bizID", bizID);
					uiContextCC.put("distributeID", distributeID);
					uiContextCC.put("startBidID", startBidID);
					uiContextCC.put("appraisingID", appraisingID);
			
					return uiContextCC;
				}
			
			} catch(Exception e2){
				Logger.getLogger(FDCKDBizPromptBoxHelper.class).error(e2.getMessage());
			}
		}
		return uiContextCC;
	}
	
	protected static String getEditUIName(IObjectValue objectValue) {
		if (objectValue instanceof SupplierStockInfo) {
			return SupplierStockEditUI.class.getName();
		} else if (objectValue instanceof FullOrgUnitInfo) {
			return OUEditUI.class.getName();
		}else if(objectValue instanceof InviteProjectInfo){
			return InviteNewProjectEditUI.class.getName();
		}else if(objectValue instanceof TenderDiscusstionInfo){
			return TenderDiscusstionEditUI.class.getName();
		}else if(objectValue instanceof StrategyPactInfo){
			return StrategyPactEditUI.class.getName();
		}
		return null;
	}

}
