package com.kingdee.eas.custom.richfacade;

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
import java.util.Date;

public interface IEASRichFacade extends IBizCtrl
{
    public String[] saveTempData(String String) throws BOSException;
    public String[] saveExamBill(Date date, String String) throws BOSException;
}