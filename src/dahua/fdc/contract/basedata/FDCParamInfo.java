package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import java.util.HashMap;

import com.kingdee.eas.base.param.ParamItemInfo;



public class FDCParamInfo implements Serializable, Cloneable  {
    
	private static final long serialVersionUID = -3703106304860789667L;

	private HashMap prop = null; // new
	
	// 科目Id
    private String base_AccountId = "none";
    
	// 凭证类型Id
    private String base_VoucherTypeId = "none";
    
    //目标成本
    private String base_AimCost = "none";
    private String base_Control = "none";
    
    
    public FDCParamInfo(HashMap prop) {
        super();
        this.prop = prop;
    }
    
    public HashMap getProperties() {
        return prop;
    }
    
    /**
     * @return
     */
    public String getBase_AccountId() {
        String ret = base_AccountId;
        String str = getString(FDCConstants.FDC_PARAM_ACCOUNTVIEW);
        if (str != null) {
            ret = str;
        }
        if (ret.equals("") || ret.equalsIgnoreCase("none")) {
            return null;
        } else {
            return ret;
        }
    }
    
    public String getString(String key) {
        
        if (prop.get(key) != null) {
            return ((ParamItemInfo) prop.get(key)).getValue();
        } else {
            return null;
        }
    }

	public String getBase_VoucherTypeId() {
		//return base_VoucherTypeId;
		
        String ret = base_VoucherTypeId;
        String str = getString(FDCConstants.FDC_PARAM_VOUCHERTYPE);
        if (str != null) {
            ret = str;
        }
        if (ret.equals("") || ret.equalsIgnoreCase("none")) {
            return null;
        } else {
            return ret;
        }
	}

	public String getBase_AimCost() {
        String ret = base_AimCost;
        String str = getString(FDCConstants.FDC_PARAM_OUTCOST);
        if (str != null) {
            ret = str;
        }
        if (ret.equals("") || ret.equalsIgnoreCase("none")) {
            return null;
        } else {
            return ret;
        }
	}
	
	/***
	 * 获取自定义参数FDC_PARAM_CONSETTTYPE
	 * @return
	 */
	public String getBase_ConSettType(){
		String ret = "rdContractOverRate"; 
		String str = getString(FDCConstants.FDC_PARAM_CONSETTTYPE);
		if(str != null){
			ret = str;
		}
		return ret;
	}
	
	public String getBase_CostCenterConstRate(){
		String ret = "0.00";
		String str = getString(FDCConstants.FDC_PARAM_COSTCENTERCONSTRATE);
		if(str != null){
			ret = str;
		}
		return ret;
	}
	

	public String getBase_Control() {
        String ret = base_Control;
        String str = getString(FDCConstants.FDC_PARAM_CONTROLTYPE);
        if (str != null) {
            ret = str;
        }
        if (ret.equals("") || ret.equalsIgnoreCase("none")) {
            return null;
        } else {
            return ret;
        }
	}
	public boolean isBudgetHint() {
       return !isBudgetStrictCtrl();
	}
	public boolean isBudgetStrictCtrl() {
        Object theValue = prop.get(FDCConstants.FDC_PARAM_BUDGET_STRICTCTRL);
        if(theValue != null){
			return Boolean.valueOf(((ParamItemInfo)theValue).getValue()).booleanValue();
		}
        return false;
	}
}
