package com.kingdee.eas.fdc.finance.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.ma.budget.VisualItemInfo;
import com.kingdee.eas.util.SysUtil;

/**
 * ������������Ԥ����������BG056�ʽ�ҵ�񵥾�ʹ����Ŀ����ʾԤ����Ŀ���Ǽƻ���Ŀ�ĸ�����
 * 
 * @author cassiel 2010-09-30
 */
public class FDCDealFPOrBudgetItemUtil {
	/**
	 * ����:����false ������ʾԤ����Ŀ ������ʾ�ƻ���Ŀ<p>
	 * added by Owen_wen��750 �ʽ�ƻ������� BG056ɾ���ˣ�ֻ��Ԥ����Ŀ�����淽��������Ч���Է���false
	 */
	public static boolean getIsFpOrBg() {
		String paramValue;
		try {
			paramValue = ParamManager.getParamValue(null, null, "BG056");
			if (FMHelper.isEmpty(paramValue) || ("0").equals(paramValue)) {
				return false;
			} else {
				return true;//("1").equals(paramValue) �ƻ���Ŀ
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static FDCBgItemProp getBgItemProps() {
		return new FDCBgItemProp("outBgItemId", "outBgItemNumber",
				"outBgItemName");
	}

	public static void loadFieldsBgItem(KDBizPromptBox prmt, CoreBaseInfo info,
			FDCBgItemProp bgItemProp) {
		String valueBgItemID = info.getString(bgItemProp.getBgItemIDProp());
		String valueBgItemNumber = info.getString(bgItemProp
				.getBgItemNumberProp());
		String valueBgItemName = info.getString(bgItemProp.getBgItemNameProp());
		if (valueBgItemNumber == null) {
			prmt.setValue(null);
			return;
		} else {
			VisualItemInfo bgItem = new VisualItemInfo();
			BOSUuid uuId = valueBgItemID == null ? null : BOSUuid
					.read(valueBgItemID);
			bgItem.setId(uuId);
			bgItem.setNumber(valueBgItemNumber);
			bgItem.setName(valueBgItemName);
			prmt.setValue(bgItem);
		}
	}

	/** Ԥ��ؼ���info��info��������:Ԥ����ĿID,Ԥ����ĿNumber,Ԥ����Ŀname */

	public static void storeFieldsBgItem(KDBizPromptBox prmt,
			CoreBaseInfo info, FDCBgItemProp bgItemProp) {
		VisualItemInfo bgItem = (VisualItemInfo) prmt.getValue();
		if (bgItem == null) {
			info.setString(bgItemProp.getBgItemIDProp(), null);
			info.setString(bgItemProp.getBgItemNumberProp(), null);
			info.setString(bgItemProp.getBgItemNameProp(), null);
			
			PaymentBillEntryInfo paymentBillEntryInfo = ((PaymentBillInfo) info).getEntries().get(0);
			if (paymentBillEntryInfo == null) {
				FDCMsgBox.showWarning("�������뵥ת�����BOTP������Ҫ�������ã���֤���������һ����¼�����򸶿û�����塣");
				SysUtil.abort();
			}
			paymentBillEntryInfo.setOutBgItemId(null);
			paymentBillEntryInfo.setOutBgItemName(null);
			paymentBillEntryInfo.setOutBgItemNumber(null);
		} else {
			String bgItemId = bgItem.getId() == null ? null : bgItem.getId().toString();
			info.setString(bgItemProp.getBgItemIDProp(), bgItemId);
			info.setString(bgItemProp.getBgItemNumberProp(), bgItem.getNumber());
			info.setString(bgItemProp.getBgItemNameProp(), bgItem.getName());
			
			PaymentBillEntryInfo paymentBillEntryInfo = ((PaymentBillInfo) info).getEntries().get(0);
			paymentBillEntryInfo.setOutBgItemId(bgItemId);
			paymentBillEntryInfo.setOutBgItemName(bgItem.getName());
			paymentBillEntryInfo.setOutBgItemNumber(bgItem.getNumber());
		}
	}
}
