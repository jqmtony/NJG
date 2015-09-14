package com.kingdee.eas.fdc.dahuaschedule.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

public interface IDahuaScheduleFacade extends IBizCtrl
{
    public String[] createTempData(String xml) throws BOSException;
    public String[] createScheduleBill() throws BOSException;
}