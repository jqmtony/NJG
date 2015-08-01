package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.RetValue;

public interface IMaterialRptFacade extends IBizCtrl
{
    public RetValue getMaterialContractValues(RetValue param) throws BOSException, EASBizException;
    public RetValue getPartAMaterialValues(RetValue param) throws BOSException, EASBizException;
    public RetValue getMaterialContractMonthValues(RetValue param) throws BOSException, EASBizException;
}