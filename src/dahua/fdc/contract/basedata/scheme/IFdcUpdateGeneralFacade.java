package com.kingdee.eas.fdc.basedata.scheme;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;

public interface IFdcUpdateGeneralFacade extends IBizCtrl
{
    public Map updateData(Map param) throws BOSException;
    public Map getData(Map param) throws BOSException;
}