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
public abstract class AbstractEquIdListUI extends com.kingdee.eas.xr.client.XRBillBaseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEquIdListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btninUse;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnoutUse;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExcel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExcelFoced;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportFacard;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportCard;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExcelEqu;
    protected ActionInUse actionInUse = null;
    protected ActionOutUse actionOutUse = null;
    protected ActionRegistChange actionRegistChange = null;
    protected ActionExcel actionExcel = null;
    protected actionImportFacard actionImportFacard = null;
    protected ActionExcelFoced actionExcelFoced = null;
    protected actionImportCard actionImportCard = null;
    protected ActionExcelEqu actionExcelEqu = null;
    /**
     * output class constructor
     */
    public AbstractEquIdListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractEquIdListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.port.equipment.record.app", "EquIdQuery");
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
        //actionInUse
        this.actionInUse = new ActionInUse(this);
        getActionManager().registerAction("actionInUse", actionInUse);
        this.actionInUse.setExtendProperty("canForewarn", "true");
        this.actionInUse.setExtendProperty("userDefined", "true");
        this.actionInUse.setExtendProperty("isObjectUpdateLock", "false");
         this.actionInUse.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionInUse.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionOutUse
        this.actionOutUse = new ActionOutUse(this);
        getActionManager().registerAction("actionOutUse", actionOutUse);
        this.actionOutUse.setExtendProperty("canForewarn", "true");
        this.actionOutUse.setExtendProperty("userDefined", "true");
        this.actionOutUse.setExtendProperty("isObjectUpdateLock", "false");
         this.actionOutUse.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionOutUse.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionRegistChange
        this.actionRegistChange = new ActionRegistChange(this);
        getActionManager().registerAction("actionRegistChange", actionRegistChange);
        this.actionRegistChange.setExtendProperty("canForewarn", "true");
        this.actionRegistChange.setExtendProperty("userDefined", "true");
        this.actionRegistChange.setExtendProperty("isObjectUpdateLock", "false");
         this.actionRegistChange.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRegistChange.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionExcel
        this.actionExcel = new ActionExcel(this);
        getActionManager().registerAction("actionExcel", actionExcel);
        this.actionExcel.setExtendProperty("canForewarn", "true");
        this.actionExcel.setExtendProperty("userDefined", "true");
        this.actionExcel.setExtendProperty("isObjectUpdateLock", "false");
         this.actionExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionExcel.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionImportFacard
        this.actionImportFacard = new actionImportFacard(this);
        getActionManager().registerAction("actionImportFacard", actionImportFacard);
         this.actionImportFacard.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExcelFoced
        this.actionExcelFoced = new ActionExcelFoced(this);
        getActionManager().registerAction("actionExcelFoced", actionExcelFoced);
        this.actionExcelFoced.setExtendProperty("canForewarn", "true");
        this.actionExcelFoced.setExtendProperty("userDefined", "true");
        this.actionExcelFoced.setExtendProperty("isObjectUpdateLock", "false");
         this.actionExcelFoced.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionExcelFoced.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionImportCard
        this.actionImportCard = new actionImportCard(this);
        getActionManager().registerAction("actionImportCard", actionImportCard);
         this.actionImportCard.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExcelEqu
        this.actionExcelEqu = new ActionExcelEqu(this);
        getActionManager().registerAction("actionExcelEqu", actionExcelEqu);
        this.actionExcelEqu.setExtendProperty("canForewarn", "true");
        this.actionExcelEqu.setExtendProperty("userDefined", "true");
        this.actionExcelEqu.setExtendProperty("isObjectUpdateLock", "false");
         this.actionExcelEqu.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionExcelEqu.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.btninUse = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnoutUse = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExcelFoced = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportFacard = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportCard = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExcelEqu = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btninUse.setName("btninUse");
        this.btnoutUse.setName("btnoutUse");
        this.btnExcel.setName("btnExcel");
        this.btnExcelFoced.setName("btnExcelFoced");
        this.btnImportFacard.setName("btnImportFacard");
        this.btnImportCard.setName("btnImportCard");
        this.btnExcelEqu.setName("btnExcelEqu");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol25\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol26\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol27\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol29\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol30\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol31\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol32\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"model\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"innerNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"size\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"weight\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"qyDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"serialNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"sbStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"special\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"ccNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"tzdaNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"tzsbStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"assetValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"EqmCategory\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"wxOrgUnit.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"specialType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"cityPeriod\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"portPeriod\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"code\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"engineNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"carNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"ratedWeight\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"textDate1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"dayone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /><t:Column t:key=\"daytow\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"testDay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"status\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" /><t:Column t:key=\"telePhoneNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"actrueTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"responsible\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" t:styleID=\"sCol31\" /><t:Column t:key=\"inStreet\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" t:styleID=\"sCol32\" /><t:Column t:key=\"textType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" /><t:Column t:key=\"equTypeone.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" /><t:Column t:key=\"SpareInfo.materialName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" /><t:Column t:key=\"TechnologyPar.parName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" /><t:Column t:key=\"TechnologyPar.parValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{model}</t:Cell><t:Cell>$Resource{innerNumber}</t:Cell><t:Cell>$Resource{size}</t:Cell><t:Cell>$Resource{weight}</t:Cell><t:Cell>$Resource{qyDate}</t:Cell><t:Cell>$Resource{serialNumber}</t:Cell><t:Cell>$Resource{sbStatus}</t:Cell><t:Cell>$Resource{special}</t:Cell><t:Cell>$Resource{ccNumber}</t:Cell><t:Cell>$Resource{tzdaNumber}</t:Cell><t:Cell>$Resource{tzsbStatus}</t:Cell><t:Cell>$Resource{assetValue}</t:Cell><t:Cell>$Resource{EqmCategory}</t:Cell><t:Cell>$Resource{wxOrgUnit.id}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{specialType.name}</t:Cell><t:Cell>$Resource{cityPeriod}</t:Cell><t:Cell>$Resource{portPeriod}</t:Cell><t:Cell>$Resource{code}</t:Cell><t:Cell>$Resource{engineNumber}</t:Cell><t:Cell>$Resource{carNumber}</t:Cell><t:Cell>$Resource{ratedWeight}</t:Cell><t:Cell>$Resource{textDate1}</t:Cell><t:Cell>$Resource{dayone}</t:Cell><t:Cell>$Resource{daytow}</t:Cell><t:Cell>$Resource{testDay}</t:Cell><t:Cell>$Resource{status}</t:Cell><t:Cell>$Resource{telePhoneNumber}</t:Cell><t:Cell>$Resource{actrueTime}</t:Cell><t:Cell>$Resource{responsible}</t:Cell><t:Cell>$Resource{inStreet}</t:Cell><t:Cell>$Resource{textType.name}</t:Cell><t:Cell>$Resource{equTypeone.name}</t:Cell><t:Cell>$Resource{SpareInfo.materialName}</t:Cell><t:Cell>$Resource{TechnologyPar.parName}</t:Cell><t:Cell>$Resource{TechnologyPar.parValue}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"number","name","model","innerNumber","size","weight","qyDate","serialNumber","sbStatus","special","ccNumber","tzdaNumber","tzsbStatus","assetValue","EqmCategory","wxOrgUnit.id","id","specialType.name","cityPeriod","portPeriod","code","engineNumber","carNumber","ratedWeight","textDate1","dayone","daytow","testDay","status","telePhoneNumber","actrueTime","responsible","inStreet","textType.name","equTypeone.name","SpareInfo.materialName","TechnologyPar.parName","TechnologyPar.parValue","createTime","lastUpdateTime"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);
        // btninUse
        this.btninUse.setAction((IItemAction)ActionProxyFactory.getProxy(actionInUse, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btninUse.setText(resHelper.getString("btninUse.text"));		
        this.btninUse.setToolTipText(resHelper.getString("btninUse.toolTipText"));
        // btnoutUse
        this.btnoutUse.setAction((IItemAction)ActionProxyFactory.getProxy(actionOutUse, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnoutUse.setText(resHelper.getString("btnoutUse.text"));		
        this.btnoutUse.setToolTipText(resHelper.getString("btnoutUse.toolTipText"));
        // btnExcel
        this.btnExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExcel.setText(resHelper.getString("btnExcel.text"));
        // btnExcelFoced
        this.btnExcelFoced.setAction((IItemAction)ActionProxyFactory.getProxy(actionExcelFoced, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExcelFoced.setText(resHelper.getString("btnExcelFoced.text"));
        // btnImportFacard
        this.btnImportFacard.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportFacard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportFacard.setText(resHelper.getString("btnImportFacard.text"));
        this.btnImportFacard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnImportFacard_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnImportCard
        this.btnImportCard.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportCard, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportCard.setText(resHelper.getString("btnImportCard.text"));		
        this.btnImportCard.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTree_import"));
        // btnExcelEqu
        this.btnExcelEqu.setAction((IItemAction)ActionProxyFactory.getProxy(actionExcelEqu, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExcelEqu.setText(resHelper.getString("btnExcelEqu.text"));
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
        this.toolBar.add(btninUse);
        this.toolBar.add(btnoutUse);
        this.toolBar.add(btnExcel);
        this.toolBar.add(btnExcelFoced);
        this.toolBar.add(btnImportFacard);
        this.toolBar.add(btnImportCard);
        this.toolBar.add(btnExcelEqu);
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
	    return "com.kingdee.eas.port.equipment.record.app.EquIdListUIHandler";
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

    /**
     * output btnImportFacard_actionPerformed method
     */
    protected void btnImportFacard_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
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
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("model"));
        sic.add(new SelectorItemInfo("size"));
        sic.add(new SelectorItemInfo("weight"));
        sic.add(new SelectorItemInfo("qyDate"));
        sic.add(new SelectorItemInfo("serialNumber"));
        sic.add(new SelectorItemInfo("sbStatus"));
        sic.add(new SelectorItemInfo("special"));
        sic.add(new SelectorItemInfo("ccNumber"));
        sic.add(new SelectorItemInfo("tzdaNumber"));
        sic.add(new SelectorItemInfo("tzsbStatus"));
        sic.add(new SelectorItemInfo("assetValue"));
        sic.add(new SelectorItemInfo("EqmCategory"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("wxOrgUnit.id"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("specialType.name"));
        sic.add(new SelectorItemInfo("cityPeriod"));
        sic.add(new SelectorItemInfo("portPeriod"));
        sic.add(new SelectorItemInfo("code"));
        sic.add(new SelectorItemInfo("engineNumber"));
        sic.add(new SelectorItemInfo("carNumber"));
        sic.add(new SelectorItemInfo("ratedWeight"));
        sic.add(new SelectorItemInfo("textDate1"));
        sic.add(new SelectorItemInfo("dayone"));
        sic.add(new SelectorItemInfo("daytow"));
        sic.add(new SelectorItemInfo("testDay"));
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("telePhoneNumber"));
        sic.add(new SelectorItemInfo("actrueTime"));
        sic.add(new SelectorItemInfo("responsible"));
        sic.add(new SelectorItemInfo("inStreet"));
        sic.add(new SelectorItemInfo("textType.name"));
        sic.add(new SelectorItemInfo("equTypeone.name"));
        sic.add(new SelectorItemInfo("SpareInfo.materialName"));
        sic.add(new SelectorItemInfo("TechnologyPar.parName"));
        sic.add(new SelectorItemInfo("TechnologyPar.parValue"));
        sic.add(new SelectorItemInfo("innerNumber"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        return sic;
    }            protected java.util.List getQuerySorterFields() 
    { 
        java.util.List sorterFieldList = new ArrayList(); 
        return sorterFieldList; 
    } 
    protected java.util.List getQueryPKFields() 
    { 
        java.util.List pkList = new ArrayList(); 
        pkList.add("id"); 
        return pkList;
    }
    	

    /**
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    	

    /**
     * output actionInUse_actionPerformed method
     */
    public void actionInUse_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.record.EquIdInfo editData = (com.kingdee.eas.port.equipment.record.EquIdInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().inUse(editData);
    }
    	

    /**
     * output actionOutUse_actionPerformed method
     */
    public void actionOutUse_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.record.EquIdInfo editData = (com.kingdee.eas.port.equipment.record.EquIdInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().outUse(editData);
    }
    	

    /**
     * output actionRegistChange_actionPerformed method
     */
    public void actionRegistChange_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.record.EquIdInfo editData = (com.kingdee.eas.port.equipment.record.EquIdInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().actionRegistChange(editData);
    }
    	

    /**
     * output actionExcel_actionPerformed method
     */
    public void actionExcel_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.record.EquIdInfo editData = (com.kingdee.eas.port.equipment.record.EquIdInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().excel(editData);
    }
    	

    /**
     * output actionImportFacard_actionPerformed method
     */
    public void actionImportFacard_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExcelFoced_actionPerformed method
     */
    public void actionExcelFoced_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.record.EquIdInfo editData = (com.kingdee.eas.port.equipment.record.EquIdInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().excelFoced(editData);
    }
    	

    /**
     * output actionImportCard_actionPerformed method
     */
    public void actionImportCard_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExcelEqu_actionPerformed method
     */
    public void actionExcelEqu_actionPerformed(ActionEvent e) throws Exception
    {
        if (getSelectedKeyValue() == null) return;
com.kingdee.eas.port.equipment.record.EquIdInfo editData = (com.kingdee.eas.port.equipment.record.EquIdInfo)getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance().excelEqu(editData);
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
	public RequestContext prepareActionInUse(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInUse() {
    	return false;
    }
	public RequestContext prepareActionOutUse(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOutUse() {
    	return false;
    }
	public RequestContext prepareActionRegistChange(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRegistChange() {
    	return false;
    }
	public RequestContext prepareActionExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExcel() {
    	return false;
    }
	public RequestContext prepareactionImportFacard(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionImportFacard() {
    	return false;
    }
	public RequestContext prepareActionExcelFoced(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExcelFoced() {
    	return false;
    }
	public RequestContext prepareactionImportCard(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionImportCard() {
    	return false;
    }
	public RequestContext prepareActionExcelEqu(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExcelEqu() {
    	return false;
    }

    /**
     * output ActionInUse class
     */     
    protected class ActionInUse extends ItemAction {     
    
        public ActionInUse()
        {
            this(null);
        }

        public ActionInUse(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionInUse.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInUse.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInUse.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdListUI.this, "ActionInUse", "actionInUse_actionPerformed", e);
        }
    }

    /**
     * output ActionOutUse class
     */     
    protected class ActionOutUse extends ItemAction {     
    
        public ActionOutUse()
        {
            this(null);
        }

        public ActionOutUse(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionOutUse.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOutUse.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOutUse.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdListUI.this, "ActionOutUse", "actionOutUse_actionPerformed", e);
        }
    }

    /**
     * output ActionRegistChange class
     */     
    protected class ActionRegistChange extends ItemAction {     
    
        public ActionRegistChange()
        {
            this(null);
        }

        public ActionRegistChange(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRegistChange.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRegistChange.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRegistChange.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdListUI.this, "ActionRegistChange", "actionRegistChange_actionPerformed", e);
        }
    }

    /**
     * output ActionExcel class
     */     
    protected class ActionExcel extends ItemAction {     
    
        public ActionExcel()
        {
            this(null);
        }

        public ActionExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdListUI.this, "ActionExcel", "actionExcel_actionPerformed", e);
        }
    }

    /**
     * output actionImportFacard class
     */     
    protected class actionImportFacard extends ItemAction {     
    
        public actionImportFacard()
        {
            this(null);
        }

        public actionImportFacard(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionImportFacard.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImportFacard.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImportFacard.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdListUI.this, "actionImportFacard", "actionImportFacard_actionPerformed", e);
        }
    }

    /**
     * output ActionExcelFoced class
     */     
    protected class ActionExcelFoced extends ItemAction {     
    
        public ActionExcelFoced()
        {
            this(null);
        }

        public ActionExcelFoced(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExcelFoced.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelFoced.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelFoced.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdListUI.this, "ActionExcelFoced", "actionExcelFoced_actionPerformed", e);
        }
    }

    /**
     * output actionImportCard class
     */     
    protected class actionImportCard extends ItemAction {     
    
        public actionImportCard()
        {
            this(null);
        }

        public actionImportCard(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionImportCard.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImportCard.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionImportCard.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdListUI.this, "actionImportCard", "actionImportCard_actionPerformed", e);
        }
    }

    /**
     * output ActionExcelEqu class
     */     
    protected class ActionExcelEqu extends ItemAction {     
    
        public ActionExcelEqu()
        {
            this(null);
        }

        public ActionExcelEqu(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExcelEqu.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelEqu.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelEqu.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEquIdListUI.this, "ActionExcelEqu", "actionExcelEqu_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.record.client", "EquIdListUI");
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
        return com.kingdee.eas.port.equipment.record.client.EquIdEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.record.EquIdInfo objectValue = new com.kingdee.eas.port.equipment.record.EquIdInfo();		
        return objectValue;
    }

    /**
     * output getMergeColumnKeys method
     */
    public String[] getMergeColumnKeys()
    {
        return new String[] {"number","name","model","innerNumber","size","weight","qyDate","serialNumber","sbStatus","special","ccNumber","tzdaNumber","tzsbStatus","assetValue","EqmCategory","wxOrgUnit.id","id","specialType.name","cityPeriod","portPeriod","code","engineNumber","carNumber","ratedWeight","textDate1","dayone","daytow","testDay","status","telePhoneNumber","actrueTime","responsible","inStreet","textType.name","equTypeone.name","createTime","lastUpdateTime"};
    }



	protected String getTDFileName() {
    	return "/bim/port/equipment/record/EquId";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.record.app.EquIdQuery");
	}

}