package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import java.util.Set;

public interface IFDCDepConPayPlanFacade extends IBizCtrl
{
    public Map getProjectPayPlanExeData(Set prjIds, Map param) throws BOSException, EASBizException;
    public void autoUpdateConPayPlan(String id, boolean isAudit) throws BOSException, EASBizException;
}