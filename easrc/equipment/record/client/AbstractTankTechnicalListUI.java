/**
 * output package name
 */
package com.kingdee.eas.port.equipment.record.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractTankTechnicalListUI extends com.kingdee.eas.xr.client.XRBillBaseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTankTechnicalListUI.class);
    /**
     * output class constructor
     */
    public AbstractTankTechnicalListUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractTankTechnicalListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.port.equipment.record.app", "TankTechnicalQuery");
        //actionRemove
        String _tempStr = null;
        actionRemove.setEnabled(true);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
        this.actionRemove.setBindWorkFlow(true);
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol44\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol47\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style><c:Style id=\"sCol55\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol58\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol60\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol61\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol89\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"status\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdateUser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditor.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"CU.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"tankCapacity\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tankDiameter\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tankWallHeight\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shejiyali\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"gongzuoyali\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shejiwendu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"gongzuowendu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fushiyuliang\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shejimidu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"anquangaodu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"huanremianji\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bibanchicun\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"guanbibanone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"guanbibantwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"guandingbanone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"guandingbantwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"guandingbanthree\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bianyuanbanone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bianyuanbantwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bianyuanbanthree\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"zhongfubanone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"zhongfubantwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"zhongfubanthree\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jingyouguan\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"zhongxingaojy\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"falanjiekoujy\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"luoshuanjy\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"chuyouguan\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"zhongxingaocy\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"falanjiekoucy\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"luoshuancy\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"rukong\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"zhongxingaork\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"rukongshuliang\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol44\" /><t:Column t:key=\"luoshuanrk\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"touguangkong\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"touguangkongsl\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol47\" /><t:Column t:key=\"luoshuantgk\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yeweijifljkone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yeweijijkfltwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yeweijils\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yeweijiguige\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yeweijicd\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yeweijizxgone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jianzaoTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol55\" /><t:Column t:key=\"shejidanwei\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shigongdanweione\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"time\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol58\" /><t:Column t:key=\"guanrong\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dxtime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol60\" /><t:Column t:key=\"daxiushigongriqi\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol61\" /><t:Column t:key=\"shigongdanweitwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"daxiuneirong\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jiaobanqijkflone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jiaobanqijkfltwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jiaobanqils\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jiaobanqiguige\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jiaobanqicd\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jiaobanqizxg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"paomojkflone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"paomojkfltwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"paomols\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"paomoguige\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"paomocd\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"paomozxg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"youliangguanjkflone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"youliangguanjkfltwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"youliangguanls\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"youliangguanguige\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"youliangguancd\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"youliangguanzxg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"touguangkongone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"touguangkongthree\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"qingsaokongone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"qingsaokongtwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"qingsaokongthree\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"cehoujilu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"waiguan\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jianzaofeiyong\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol89\" /><t:Column t:key=\"qingsaokongfour\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"qingsaokongfive\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"luoshuanqs\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"huxifajkflone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"huxifajkfltwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"huxifals\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"huxifagg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bwcjkflone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bwcjkfltwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"huxifaCd\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"huxifazxg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bwcls\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"baowecenggg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"baowencengcd\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"baowencengzxg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tiebijkfaone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tiepijkfltwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"teipils\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tiepigg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tiepicd\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tiepizxg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"daoboguanjkflone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"daoboguanjkfltwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"daoboguanls\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"daoboguangg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"daoboguancd\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"daoboguanzxg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jiareqijkfaone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jiareqijkfltwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jiarqichang\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{status}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{lastUpdateUser.name}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell><t:Cell>$Resource{auditor.name}</t:Cell><t:Cell>$Resource{auditTime}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{CU.id}</t:Cell><t:Cell>$Resource{tankCapacity}</t:Cell><t:Cell>$Resource{tankDiameter}</t:Cell><t:Cell>$Resource{tankWallHeight}</t:Cell><t:Cell>$Resource{shejiyali}</t:Cell><t:Cell>$Resource{gongzuoyali}</t:Cell><t:Cell>$Resource{shejiwendu}</t:Cell><t:Cell>$Resource{gongzuowendu}</t:Cell><t:Cell>$Resource{fushiyuliang}</t:Cell><t:Cell>$Resource{shejimidu}</t:Cell><t:Cell>$Resource{anquangaodu}</t:Cell><t:Cell>$Resource{huanremianji}</t:Cell><t:Cell>$Resource{bibanchicun}</t:Cell><t:Cell>$Resource{guanbibanone}</t:Cell><t:Cell>$Resource{guanbibantwo}</t:Cell><t:Cell>$Resource{guandingbanone}</t:Cell><t:Cell>$Resource{guandingbantwo}</t:Cell><t:Cell>$Resource{guandingbanthree}</t:Cell><t:Cell>$Resource{bianyuanbanone}</t:Cell><t:Cell>$Resource{bianyuanbantwo}</t:Cell><t:Cell>$Resource{bianyuanbanthree}</t:Cell><t:Cell>$Resource{zhongfubanone}</t:Cell><t:Cell>$Resource{zhongfubantwo}</t:Cell><t:Cell>$Resource{zhongfubanthree}</t:Cell><t:Cell>$Resource{jingyouguan}</t:Cell><t:Cell>$Resource{zhongxingaojy}</t:Cell><t:Cell>$Resource{falanjiekoujy}</t:Cell><t:Cell>$Resource{luoshuanjy}</t:Cell><t:Cell>$Resource{chuyouguan}</t:Cell><t:Cell>$Resource{zhongxingaocy}</t:Cell><t:Cell>$Resource{falanjiekoucy}</t:Cell><t:Cell>$Resource{luoshuancy}</t:Cell><t:Cell>$Resource{rukong}</t:Cell><t:Cell>$Resource{zhongxingaork}</t:Cell><t:Cell>$Resource{rukongshuliang}</t:Cell><t:Cell>$Resource{luoshuanrk}</t:Cell><t:Cell>$Resource{touguangkong}</t:Cell><t:Cell>$Resource{touguangkongsl}</t:Cell><t:Cell>$Resource{luoshuantgk}</t:Cell><t:Cell>$Resource{yeweijifljkone}</t:Cell><t:Cell>$Resource{yeweijijkfltwo}</t:Cell><t:Cell>$Resource{yeweijils}</t:Cell><t:Cell>$Resource{yeweijiguige}</t:Cell><t:Cell>$Resource{yeweijicd}</t:Cell><t:Cell>$Resource{yeweijizxgone}</t:Cell><t:Cell>$Resource{jianzaoTime}</t:Cell><t:Cell>$Resource{shejidanwei}</t:Cell><t:Cell>$Resource{shigongdanweione}</t:Cell><t:Cell>$Resource{time}</t:Cell><t:Cell>$Resource{guanrong}</t:Cell><t:Cell>$Resource{dxtime}</t:Cell><t:Cell>$Resource{daxiushigongriqi}</t:Cell><t:Cell>$Resource{shigongdanweitwo}</t:Cell><t:Cell>$Resource{daxiuneirong}</t:Cell><t:Cell>$Resource{jiaobanqijkflone}</t:Cell><t:Cell>$Resource{jiaobanqijkfltwo}</t:Cell><t:Cell>$Resource{jiaobanqils}</t:Cell><t:Cell>$Resource{jiaobanqiguige}</t:Cell><t:Cell>$Resource{jiaobanqicd}</t:Cell><t:Cell>$Resource{jiaobanqizxg}</t:Cell><t:Cell>$Resource{paomojkflone}</t:Cell><t:Cell>$Resource{paomojkfltwo}</t:Cell><t:Cell>$Resource{paomols}</t:Cell><t:Cell>$Resource{paomoguige}</t:Cell><t:Cell>$Resource{paomocd}</t:Cell><t:Cell>$Resource{paomozxg}</t:Cell><t:Cell>$Resource{youliangguanjkflone}</t:Cell><t:Cell>$Resource{youliangguanjkfltwo}</t:Cell><t:Cell>$Resource{youliangguanls}</t:Cell><t:Cell>$Resource{youliangguanguige}</t:Cell><t:Cell>$Resource{youliangguancd}</t:Cell><t:Cell>$Resource{youliangguanzxg}</t:Cell><t:Cell>$Resource{touguangkongone}</t:Cell><t:Cell>$Resource{touguangkongthree}</t:Cell><t:Cell>$Resource{qingsaokongone}</t:Cell><t:Cell>$Resource{qingsaokongtwo}</t:Cell><t:Cell>$Resource{qingsaokongthree}</t:Cell><t:Cell>$Resource{cehoujilu}</t:Cell><t:Cell>$Resource{waiguan}</t:Cell><t:Cell>$Resource{jianzaofeiyong}</t:Cell><t:Cell>$Resource{qingsaokongfour}</t:Cell><t:Cell>$Resource{qingsaokongfive}</t:Cell><t:Cell>$Resource{luoshuanqs}</t:Cell><t:Cell>$Resource{huxifajkflone}</t:Cell><t:Cell>$Resource{huxifajkfltwo}</t:Cell><t:Cell>$Resource{huxifals}</t:Cell><t:Cell>$Resource{huxifagg}</t:Cell><t:Cell>$Resource{bwcjkflone}</t:Cell><t:Cell>$Resource{bwcjkfltwo}</t:Cell><t:Cell>$Resource{huxifaCd}</t:Cell><t:Cell>$Resource{huxifazxg}</t:Cell><t:Cell>$Resource{bwcls}</t:Cell><t:Cell>$Resource{baowecenggg}</t:Cell><t:Cell>$Resource{baowencengcd}</t:Cell><t:Cell>$Resource{baowencengzxg}</t:Cell><t:Cell>$Resource{tiebijkfaone}</t:Cell><t:Cell>$Resource{tiepijkfltwo}</t:Cell><t:Cell>$Resource{teipils}</t:Cell><t:Cell>$Resource{tiepigg}</t:Cell><t:Cell>$Resource{tiepicd}</t:Cell><t:Cell>$Resource{tiepizxg}</t:Cell><t:Cell>$Resource{daoboguanjkflone}</t:Cell><t:Cell>$Resource{daoboguanjkfltwo}</t:Cell><t:Cell>$Resource{daoboguanls}</t:Cell><t:Cell>$Resource{daoboguangg}</t:Cell><t:Cell>$Resource{daoboguancd}</t:Cell><t:Cell>$Resource{daoboguanzxg}</t:Cell><t:Cell>$Resource{jiareqijkfaone}</t:Cell><t:Cell>$Resource{jiareqijkfltwo}</t:Cell><t:Cell>$Resource{jiarqichang}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"status","number","bizDate","creator.name","createTime","lastUpdateUser.name","lastUpdateTime","auditor.name","auditTime","id","CU.id","tankCapacity","tankDiameter","tankWallHeight","shejiyali","gongzuoyali","shejiwendu","gongzuowendu","fushiyuliang","shejimidu","anquangaodu","huanremianji","bibanchicun","guanbibanone","guanbibantwo","guandingbanone","guandingbantwo","guandingbanthree","bianyuanbanone","bianyuanbantwo","bianyuanbanthree","zhongfubanone","zhongfubantwo","zhongfubanthree","jingyouguan","zhongxingaojy","falanjiekoujy","luoshuanjy","chuyouguan","zhongxingaocy","falanjiekoucy","luoshuancy","rukong","zhongxingaork","rukongshuliang","luoshuanrk","touguangkong","touguangkongsl","luoshuantgk","yeweijifljkone","yeweijijkfltwo","yeweijils","yeweijiguige","yeweijicd","yeweijizxgone","jianzaoTime","shejidanwei","shigongdanweione","time","guanrong","dxtime","daxiushigongriqi","shigongdanweitwo","daxiuneirong","jiaobanqijkflone","jiaobanqijkfltwo","jiaobanqils","jiaobanqiguige","jiaobanqicd","jiaobanqizxg","paomojkflone","paomojkfltwo","paomols","paomoguige","paomocd","paomozxg","youliangguanjkflone","youliangguanjkfltwo","youliangguanls","youliangguanguige","youliangguancd","youliangguanzxg","touguangkongone","touguangkongthree","qingsaokongone","qingsaokongtwo","qingsaokongthree","cehoujilu","waiguan","jianzaofeiyong","qingsaokongfour","qingsaokongfive","luoshuanqs","huxifajkflone","huxifajkfltwo","huxifals","huxifagg","bwcjkflone","bwcjkfltwo","huxifaCd","huxifazxg","bwcls","baowecenggg","baowencengcd","baowencengzxg","tiebijkfaone","tiepijkfltwo","teipils","tiepigg","tiepicd","tiepizxg","daoboguanjkflone","daoboguanjkfltwo","daoboguanls","daoboguangg","daoboguancd","daoboguanzxg","jiareqijkfaone","jiareqijkfltwo","jiarqichang"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        tblMain.setBounds(new Rectangle(10, 10, 996, 580));
        this.add(tblMain, new KDLayout.Constraints(10, 10, 996, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkFlow);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemCloudShare);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemSwitchView);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(menuItemQueryScheme);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnView);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWFViewdoProccess);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.record.app.TankTechnicalListUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }
	protected void Remove() throws Exception {
    	IObjectValue editData = getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
    	super.Remove();
    	recycleNumberByOrg(editData,"",editData.getString("number"));
    }
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

	public SelectorItemCollection getBOTPSelectors() {
			SelectorItemCollection sic = new SelectorItemCollection();
			return sic;
	}

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.name"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("CU.id"));
        sic.add(new SelectorItemInfo("tankCapacity"));
        sic.add(new SelectorItemInfo("tankDiameter"));
        sic.add(new SelectorItemInfo("tankWallHeight"));
        sic.add(new SelectorItemInfo("shejiyali"));
        sic.add(new SelectorItemInfo("gongzuoyali"));
        sic.add(new SelectorItemInfo("shejiwendu"));
        sic.add(new SelectorItemInfo("gongzuowendu"));
        sic.add(new SelectorItemInfo("fushiyuliang"));
        sic.add(new SelectorItemInfo("shejimidu"));
        sic.add(new SelectorItemInfo("anquangaodu"));
        sic.add(new SelectorItemInfo("huanremianji"));
        sic.add(new SelectorItemInfo("bibanchicun"));
        sic.add(new SelectorItemInfo("guanbibanone"));
        sic.add(new SelectorItemInfo("guanbibantwo"));
        sic.add(new SelectorItemInfo("guandingbanone"));
        sic.add(new SelectorItemInfo("guandingbantwo"));
        sic.add(new SelectorItemInfo("guandingbanthree"));
        sic.add(new SelectorItemInfo("bianyuanbanone"));
        sic.add(new SelectorItemInfo("bianyuanbantwo"));
        sic.add(new SelectorItemInfo("bianyuanbanthree"));
        sic.add(new SelectorItemInfo("zhongfubanone"));
        sic.add(new SelectorItemInfo("zhongfubantwo"));
        sic.add(new SelectorItemInfo("zhongfubanthree"));
        sic.add(new SelectorItemInfo("jingyouguan"));
        sic.add(new SelectorItemInfo("zhongxingaojy"));
        sic.add(new SelectorItemInfo("falanjiekoujy"));
        sic.add(new SelectorItemInfo("luoshuanjy"));
        sic.add(new SelectorItemInfo("chuyouguan"));
        sic.add(new SelectorItemInfo("zhongxingaocy"));
        sic.add(new SelectorItemInfo("falanjiekoucy"));
        sic.add(new SelectorItemInfo("luoshuancy"));
        sic.add(new SelectorItemInfo("rukong"));
        sic.add(new SelectorItemInfo("zhongxingaork"));
        sic.add(new SelectorItemInfo("rukongshuliang"));
        sic.add(new SelectorItemInfo("luoshuanrk"));
        sic.add(new SelectorItemInfo("touguangkong"));
        sic.add(new SelectorItemInfo("touguangkongsl"));
        sic.add(new SelectorItemInfo("luoshuantgk"));
        sic.add(new SelectorItemInfo("yeweijifljkone"));
        sic.add(new SelectorItemInfo("yeweijijkfltwo"));
        sic.add(new SelectorItemInfo("yeweijils"));
        sic.add(new SelectorItemInfo("yeweijiguige"));
        sic.add(new SelectorItemInfo("yeweijicd"));
        sic.add(new SelectorItemInfo("yeweijizxgone"));
        sic.add(new SelectorItemInfo("jianzaoTime"));
        sic.add(new SelectorItemInfo("shejidanwei"));
        sic.add(new SelectorItemInfo("shigongdanweione"));
        sic.add(new SelectorItemInfo("time"));
        sic.add(new SelectorItemInfo("guanrong"));
        sic.add(new SelectorItemInfo("dxtime"));
        sic.add(new SelectorItemInfo("daxiushigongriqi"));
        sic.add(new SelectorItemInfo("shigongdanweitwo"));
        sic.add(new SelectorItemInfo("daxiuneirong"));
        sic.add(new SelectorItemInfo("jiaobanqijkflone"));
        sic.add(new SelectorItemInfo("jiaobanqijkfltwo"));
        sic.add(new SelectorItemInfo("jiaobanqils"));
        sic.add(new SelectorItemInfo("jiaobanqiguige"));
        sic.add(new SelectorItemInfo("jiaobanqicd"));
        sic.add(new SelectorItemInfo("jiaobanqizxg"));
        sic.add(new SelectorItemInfo("paomojkflone"));
        sic.add(new SelectorItemInfo("paomojkfltwo"));
        sic.add(new SelectorItemInfo("paomols"));
        sic.add(new SelectorItemInfo("paomoguige"));
        sic.add(new SelectorItemInfo("paomocd"));
        sic.add(new SelectorItemInfo("paomozxg"));
        sic.add(new SelectorItemInfo("youliangguanjkflone"));
        sic.add(new SelectorItemInfo("youliangguanjkfltwo"));
        sic.add(new SelectorItemInfo("youliangguanls"));
        sic.add(new SelectorItemInfo("youliangguanguige"));
        sic.add(new SelectorItemInfo("youliangguancd"));
        sic.add(new SelectorItemInfo("youliangguanzxg"));
        sic.add(new SelectorItemInfo("touguangkongone"));
        sic.add(new SelectorItemInfo("touguangkongthree"));
        sic.add(new SelectorItemInfo("qingsaokongone"));
        sic.add(new SelectorItemInfo("qingsaokongtwo"));
        sic.add(new SelectorItemInfo("qingsaokongthree"));
        sic.add(new SelectorItemInfo("cehoujilu"));
        sic.add(new SelectorItemInfo("waiguan"));
        sic.add(new SelectorItemInfo("jianzaofeiyong"));
        sic.add(new SelectorItemInfo("qingsaokongfour"));
        sic.add(new SelectorItemInfo("qingsaokongfive"));
        sic.add(new SelectorItemInfo("luoshuanqs"));
        sic.add(new SelectorItemInfo("huxifajkflone"));
        sic.add(new SelectorItemInfo("huxifajkfltwo"));
        sic.add(new SelectorItemInfo("huxifals"));
        sic.add(new SelectorItemInfo("huxifagg"));
        sic.add(new SelectorItemInfo("bwcjkflone"));
        sic.add(new SelectorItemInfo("bwcjkfltwo"));
        sic.add(new SelectorItemInfo("huxifaCd"));
        sic.add(new SelectorItemInfo("huxifazxg"));
        sic.add(new SelectorItemInfo("bwcls"));
        sic.add(new SelectorItemInfo("baowecenggg"));
        sic.add(new SelectorItemInfo("baowencengcd"));
        sic.add(new SelectorItemInfo("baowencengzxg"));
        sic.add(new SelectorItemInfo("tiebijkfaone"));
        sic.add(new SelectorItemInfo("tiepijkfltwo"));
        sic.add(new SelectorItemInfo("teipils"));
        sic.add(new SelectorItemInfo("tiepigg"));
        sic.add(new SelectorItemInfo("tiepicd"));
        sic.add(new SelectorItemInfo("tiepizxg"));
        sic.add(new SelectorItemInfo("daoboguanjkflone"));
        sic.add(new SelectorItemInfo("daoboguanjkfltwo"));
        sic.add(new SelectorItemInfo("daoboguanls"));
        sic.add(new SelectorItemInfo("daoboguangg"));
        sic.add(new SelectorItemInfo("daoboguancd"));
        sic.add(new SelectorItemInfo("daoboguanzxg"));
        sic.add(new SelectorItemInfo("jiareqijkfaone"));
        sic.add(new SelectorItemInfo("jiareqijkfltwo"));
        sic.add(new SelectorItemInfo("jiarqichang"));
        return sic;
    }            protected java.util.List getQuerySorterFields() 
    { 
        java.util.List sorterFieldList = new ArrayList(); 
        sorterFieldList.add("createTime"); 
        return sorterFieldList; 
    } 

    	

    /**
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
	public RequestContext prepareActionRemove(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRemove(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemove() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.record.client", "TankTechnicalListUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.port.equipment.record.client.TankTechnicalEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.record.TankTechnicalFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.record.TankTechnicalInfo objectValue = new com.kingdee.eas.port.equipment.record.TankTechnicalInfo();		
        return objectValue;
    }

    /**
     * output getMergeColumnKeys method
     */
    public String[] getMergeColumnKeys()
    {
        return new String[] {"status","number","bizDate","creator.name","createTime","lastUpdateUser.name","lastUpdateTime","auditor.name","auditTime","id","CU.id","tankCapacity","tankDiameter","tankWallHeight","shejiyali","gongzuoyali","shejiwendu","gongzuowendu","fushiyuliang","shejimidu","anquangaodu","huanremianji","bibanchicun","guanbibanone","guanbibantwo","guandingbanone","guandingbantwo","guandingbanthree","bianyuanbanone","bianyuanbantwo","bianyuanbanthree","zhongfubanone","zhongfubantwo","zhongfubanthree","jingyouguan","zhongxingaojy","falanjiekoujy","luoshuanjy","chuyouguan","zhongxingaocy","falanjiekoucy","luoshuancy","rukong","zhongxingaork","rukongshuliang","luoshuanrk","touguangkong","touguangkongsl","luoshuantgk","yeweijifljkone","yeweijijkfltwo","yeweijils","yeweijiguige","yeweijicd","yeweijizxgone","jianzaoTime","shejidanwei","shigongdanweione","time","guanrong","dxtime","daxiushigongriqi","shigongdanweitwo","daxiuneirong","jiaobanqijkflone","jiaobanqijkfltwo","jiaobanqils","jiaobanqiguige","jiaobanqicd","jiaobanqizxg","paomojkflone","paomojkfltwo","paomols","paomoguige","paomocd","paomozxg","youliangguanjkflone","youliangguanjkfltwo","youliangguanls","youliangguanguige","youliangguancd","youliangguanzxg","touguangkongone","touguangkongthree","qingsaokongone","qingsaokongtwo","qingsaokongthree","cehoujilu","waiguan","jianzaofeiyong","qingsaokongfour","qingsaokongfive","luoshuanqs","huxifajkflone","huxifajkfltwo","huxifals","huxifagg","bwcjkflone","bwcjkfltwo","huxifaCd","huxifazxg","bwcls","baowecenggg","baowencengcd","baowencengzxg","tiebijkfaone","tiepijkfltwo","teipils","tiepigg","tiepicd","tiepizxg","daoboguanjkflone","daoboguanjkfltwo","daoboguanls","daoboguangg","daoboguancd","daoboguanzxg","jiareqijkfaone","jiareqijkfltwo","jiarqichang"};
    }



	protected String getTDFileName() {
    	return "/bim/port/equipment/record/TankTechnical";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.record.app.TankTechnicalQuery");
	}

}