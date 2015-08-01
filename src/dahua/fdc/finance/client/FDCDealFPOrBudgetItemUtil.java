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
 * 处理付款单界面根据预算管理参数【BG056资金业务单据使用项目】显示预算项目还是计划项目的辅助类
 * 
 * @author cassiel 2010-09-30
 */
public class FDCDealFPOrBudgetItemUtil {
	/**
	 * 描述:返回false 界面显示预算项目 否则显示计划项目<p>
	 * added by Owen_wen：750 资金计划将参数 BG056删除了，只用预算项目，下面方法继续生效，仍返回false
	 */
	public static boolean getIsFpOrBg() {
		String paramValue;
		try {
			paramValue = ParamManager.getParamValue(null, null, "BG056");
			if (FMHelper.isEmpty(paramValue) || ("0").equals(paramValue)) {
				return false;
			} else {
				return true;//("1").equals(paramValue) 计划项目
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

	/** 预算控件，info，info的属性名:预算项目ID,预算项目Number,预算项目name */

	public static void storeFieldsBgItem(KDBizPromptBox prmt,
			CoreBaseInfo info, FDCBgItemProp bgItemProp) {
		VisualItemInfo bgItem = (VisualItemInfo) prmt.getValue();
		if (bgItem == null) {
			info.setString(bgItemProp.getBgItemIDProp(), null);
			info.setString(bgItemProp.getBgItemNumberProp(), null);
			info.setString(bgItemProp.getBgItemNameProp(), null);
			
			PaymentBillEntryInfo paymentBillEntryInfo = ((PaymentBillInfo) info).getEntries().get(0);
			if (paymentBillEntryInfo == null) {
				FDCMsgBox.showWarning("付款申请单转付款单的BOTP规则需要重新配置，保证付款单至少有一条分录，否则付款单没有意义。");
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
