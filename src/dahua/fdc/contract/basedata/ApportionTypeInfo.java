package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;

public class ApportionTypeInfo extends AbstractApportionTypeInfo implements
		Serializable {
	public static final String buildAreaType = "qHQt0wEMEADgAAaBwKgTuzW0boA=";

	public static final String sellAreaType = "qHQt0wEMEADgAAaHwKgTuzW0boA=";

	public static final String placeAreaType = "qHQt0wEMEADgAAaMwKgTuzW0boA=";

	public static final String aimCostType = "qHQt0wEMEADgAAaOoKgTuzW0boA=";
	// Ö¸¶¨·ÖÌ¯
	public static final String appointType = "CaLPJgEXEADgAAe8wKgSzDW0boA=";
	

	public ApportionTypeInfo() {
		super();
	}

	protected ApportionTypeInfo(String pkField) {
		super(pkField);
	}
	
	public static FilterInfo getCUFilter(CtrlUnitInfo cu){
    	FilterInfo cuFilter=new  FilterInfo();
    	Set set=new HashSet();
    	cuFilter.getFilterItems().add(new FilterItemInfo("CU.id",OrgConstants.SYS_CU_ID));
    	if(cu!=null&&cu.getLongNumber()!=null){
    		String splits[]=cu.getLongNumber().split("!");
    		String innerSql=null;
    		for(int i=0;i<splits.length;i++){
    			if(innerSql==null){
    				innerSql=splits[i];
    			}else{
    				innerSql+="!"+splits[i];
    			}
    			set.add(innerSql);
    			
    		}
    		cuFilter.getFilterItems().add(new FilterItemInfo("CU.longNumber",set,CompareType.INCLUDE));
    		cuFilter.setMaskString("#0 or #1");
    	}
    	return cuFilter;
	}
}