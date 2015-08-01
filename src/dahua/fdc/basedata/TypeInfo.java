package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class TypeInfo extends AbstractTypeInfo implements Serializable 
{
    public TypeInfo()
    {
        super();
    }
    protected TypeInfo(String pkField)
    {
        super(pkField);
    }
    /** 类别 结算款ID */
	public static final String settlementID = "Ga7RLQETEADgAAC/wKgOlOwp3Sw=";// 结算款

	/** 类别 进度款ID */
	public static final String progressID = "Ga7RLQETEADgAAC6wKgOlOwp3Sw=";// 进度款

	/** 类别 保修款ID */
	public static final String keepID = "Ga7RLQETEADgAADDwKgOlOwp3Sw=";//保修款
	
	/** 类别 暂估款ID	 */
    public static final String tempID = "Bd2bh+CHRDenvdQS3D72ouwp3Sw=";// 暂估款
}