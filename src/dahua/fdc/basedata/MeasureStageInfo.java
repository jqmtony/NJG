package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class MeasureStageInfo extends AbstractMeasureStageInfo implements Serializable 
{
    public MeasureStageInfo()
    {
        super();
    }
    protected MeasureStageInfo(String pkField)
    {
        super(pkField);
    }
    
    //定位阶段
    public static String ORIENTE ="Ozeozpe0T7q4o5qUyYcYt/AIFDI=";
    //方案设计阶段
    public static String DESIGN ="JQVfAvODTjWDjiRwoChcJ/AIFDI=";
    //施工图设计阶段
    public static String CONSTRUCT ="uHaBEPq7RACKc49ZDjbOWPAIFDI=";
    
}