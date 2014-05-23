/**
 * output package name
 */
package com.kingdee.eas.port.markesupplier.subase.client;

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
public abstract class AbstractMarketSupplierAppraiseTemplateEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketSupplierAppraiseTemplateEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisEnable;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtDescription;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAccreditationType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAccreditationType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneremake;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contremake;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtremake;
    protected com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractMarketSupplierAppraiseTemplateEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractMarketSupplierAppraiseTemplateEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisEnable = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.txtDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAccreditationType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAccreditationType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.scrollPaneremake = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contremake = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtremake = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.chkisEnable.setName("chkisEnable");
        this.kDContainer1.setName("kDContainer1");
        this.kDPanel1.setName("kDPanel1");
        this.txtDescription.setName("txtDescription");
        this.kdtEntry.setName("kdtEntry");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.contAccreditationType.setName("contAccreditationType");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDContainer2.setName("kDContainer2");
        this.txtSimpleName.setName("txtSimpleName");
        this.prmtAccreditationType.setName("prmtAccreditationType");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.scrollPaneremake.setName("scrollPaneremake");
        this.contremake.setName("contremake");
        this.txtremake.setName("txtremake");
        // CoreUI		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);		
        this.kDLabelContainer4.setVisible(true);
        // chkisEnable		
        this.chkisEnable.setText(resHelper.getString("chkisEnable.text"));		
        this.chkisEnable.setVisible(false);		
        this.chkisEnable.setHorizontalAlignment(2);
        // kDContainer1		
        this.kDContainer1.setEnableActive(false);		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // txtDescription		
        this.txtDescription.setVisible(false);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"Accreditationwd\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"IndexName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"threeStandard\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"IndexDesc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"ScoreType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"qz\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"remake\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{Accreditationwd}</t:Cell><t:Cell>$Resource{IndexName}</t:Cell><t:Cell>$Resource{threeStandard}</t:Cell><t:Cell>$Resource{IndexDesc}</t:Cell><t:Cell>$Resource{ScoreType}</t:Cell><t:Cell>$Resource{qz}</t:Cell><t:Cell>$Resource{remake}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));
        kdtEntry.addKDTEditListener(new KDTEditAdapter() {
		public void editStopped(KDTEditEvent e) {
			try {
				kdtEntry_Changed(e.getRowIndex(),e.getColIndex());
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});


                this.kdtEntry.putBindContents("editData",new String[] {"seq","Accreditationwd","IndexName","threeStandard","IndexDesc","ScoreType","qz","remake"});


        this.kdtEntry.checkParsed();
        KDTextField kdtEntry_Accreditationwd_TextField = new KDTextField();
        kdtEntry_Accreditationwd_TextField.setName("kdtEntry_Accreditationwd_TextField");
        kdtEntry_Accreditationwd_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_Accreditationwd_CellEditor = new KDTDefaultCellEditor(kdtEntry_Accreditationwd_TextField);
        this.kdtEntry.getColumn("Accreditationwd").setEditor(kdtEntry_Accreditationwd_CellEditor);
        final KDBizPromptBox kdtEntry_IndexName_PromptBox = new KDBizPromptBox();
        kdtEntry_IndexName_PromptBox.setQueryInfo("com.kingdee.eas.port.markesupplier.subase.app.MarketSplAuditIndexQuery");
        kdtEntry_IndexName_PromptBox.setVisible(true);
        kdtEntry_IndexName_PromptBox.setEditable(true);
        kdtEntry_IndexName_PromptBox.setDisplayFormat("$number$");
        kdtEntry_IndexName_PromptBox.setEditFormat("$number$");
        kdtEntry_IndexName_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_IndexName_CellEditor = new KDTDefaultCellEditor(kdtEntry_IndexName_PromptBox);
        this.kdtEntry.getColumn("IndexName").setEditor(kdtEntry_IndexName_CellEditor);
        ObjectValueRender kdtEntry_IndexName_OVR = new ObjectValueRender();
        kdtEntry_IndexName_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("IndexName").setRenderer(kdtEntry_IndexName_OVR);
        			kdtEntry_IndexName_PromptBox.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.markesupplier.subase.client.MarketSplAuditIndexListUI kdtEntry_IndexName_PromptBox_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (kdtEntry_IndexName_PromptBox_F7ListUI == null) {
					try {
						kdtEntry_IndexName_PromptBox_F7ListUI = new com.kingdee.eas.port.markesupplier.subase.client.MarketSplAuditIndexListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(kdtEntry_IndexName_PromptBox_F7ListUI));
					kdtEntry_IndexName_PromptBox_F7ListUI.setF7Use(true,ctx);
					kdtEntry_IndexName_PromptBox.setSelector(kdtEntry_IndexName_PromptBox_F7ListUI);
				}
			}
		});
					
        KDTextField kdtEntry_threeStandard_TextField = new KDTextField();
        kdtEntry_threeStandard_TextField.setName("kdtEntry_threeStandard_TextField");
        kdtEntry_threeStandard_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_threeStandard_CellEditor = new KDTDefaultCellEditor(kdtEntry_threeStandard_TextField);
        this.kdtEntry.getColumn("threeStandard").setEditor(kdtEntry_threeStandard_CellEditor);
        KDTextField kdtEntry_IndexDesc_TextField = new KDTextField();
        kdtEntry_IndexDesc_TextField.setName("kdtEntry_IndexDesc_TextField");
        kdtEntry_IndexDesc_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_IndexDesc_CellEditor = new KDTDefaultCellEditor(kdtEntry_IndexDesc_TextField);
        this.kdtEntry.getColumn("IndexDesc").setEditor(kdtEntry_IndexDesc_CellEditor);
        KDComboBox kdtEntry_ScoreType_ComboBox = new KDComboBox();
        kdtEntry_ScoreType_ComboBox.setName("kdtEntry_ScoreType_ComboBox");
        kdtEntry_ScoreType_ComboBox.setVisible(true);
        kdtEntry_ScoreType_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.port.markesupplier.subase.AppraiseTypeEnum").toArray());
        KDTDefaultCellEditor kdtEntry_ScoreType_CellEditor = new KDTDefaultCellEditor(kdtEntry_ScoreType_ComboBox);
        this.kdtEntry.getColumn("ScoreType").setEditor(kdtEntry_ScoreType_CellEditor);
        KDFormattedTextField kdtEntry_qz_TextField = new KDFormattedTextField();
        kdtEntry_qz_TextField.setName("kdtEntry_qz_TextField");
        kdtEntry_qz_TextField.setVisible(true);
        kdtEntry_qz_TextField.setEditable(true);
        kdtEntry_qz_TextField.setHorizontalAlignment(2);
        kdtEntry_qz_TextField.setDataType(1);
        	kdtEntry_qz_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtEntry_qz_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtEntry_qz_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry_qz_CellEditor = new KDTDefaultCellEditor(kdtEntry_qz_TextField);
        this.kdtEntry.getColumn("qz").setEditor(kdtEntry_qz_CellEditor);
        KDTextArea kdtEntry_remake_TextArea = new KDTextArea();
        kdtEntry_remake_TextArea.setName("kdtEntry_remake_TextArea");
        kdtEntry_remake_TextArea.setMaxLength(255);
        KDTDefaultCellEditor kdtEntry_remake_CellEditor = new KDTDefaultCellEditor(kdtEntry_remake_TextArea);
        this.kdtEntry.getColumn("remake").setEditor(kdtEntry_remake_CellEditor);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // contAccreditationType		
        this.contAccreditationType.setBoundLabelText(resHelper.getString("contAccreditationType.boundLabelText"));		
        this.contAccreditationType.setBoundLabelLength(100);		
        this.contAccreditationType.setBoundLabelUnderline(true);		
        this.contAccreditationType.setVisible(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDContainer2		
        this.kDContainer2.setEnableActive(false);		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // txtSimpleName		
        this.txtSimpleName.setMaxLength(80);
        // prmtAccreditationType		
        this.prmtAccreditationType.setQueryInfo("com.kingdee.eas.port.markesupplier.subase.app.MarketAccreditationTypeQuery");		
        this.prmtAccreditationType.setVisible(true);		
        this.prmtAccreditationType.setEditable(true);		
        this.prmtAccreditationType.setDisplayFormat("$name$");		
        this.prmtAccreditationType.setEditFormat("$number$");		
        this.prmtAccreditationType.setCommitFormat("$number$");		
        this.prmtAccreditationType.setRequired(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setRequired(true);
        // scrollPaneremake
        // contremake		
        this.contremake.setBoundLabelText(resHelper.getString("contremake.boundLabelText"));		
        this.contremake.setBoundLabelLength(0);		
        this.contremake.setBoundLabelUnderline(true);		
        this.contremake.setVisible(true);
        // txtremake		
        this.txtremake.setVisible(true);		
        this.txtremake.setRequired(false);		
        this.txtremake.setMaxLength(255);
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
        this.setBounds(new Rectangle(0, 0, 636, 420));
        this.setLayout(null);
        kDLabelContainer4.setBounds(new Rectangle(739, 84, 270, 19));
        this.add(kDLabelContainer4, null);
        chkisEnable.setBounds(new Rectangle(483, 110, 270, 19));
        this.add(chkisEnable, null);
        kDContainer1.setBounds(new Rectangle(5, 212, 627, 203));
        this.add(kDContainer1, null);
        kDPanel1.setBounds(new Rectangle(5, 5, 627, 205));
        this.add(kDPanel1, null);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtDescription);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kdtEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry,new com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateEntryInfo(),null,false);
        kDContainer1.getContentPane().add(kdtEntry_detailPanel, BorderLayout.CENTER);
		kdtEntry_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
vo.put("ScoreType","WEIGHT");
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});
        //kDPanel1
        kDPanel1.setLayout(null);        kDLabelContainer3.setBounds(new Rectangle(341, 41, 270, 19));
        kDPanel1.add(kDLabelContainer3, null);
        contAccreditationType.setBounds(new Rectangle(17, 41, 270, 19));
        kDPanel1.add(contAccreditationType, null);
        kDLabelContainer1.setBounds(new Rectangle(17, 17, 270, 19));
        kDPanel1.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(341, 17, 270, 19));
        kDPanel1.add(kDLabelContainer2, null);
        kDContainer2.setBounds(new Rectangle(16, 61, 595, 131));
        kDPanel1.add(kDContainer2, null);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtSimpleName);
        //contAccreditationType
        contAccreditationType.setBoundEditor(prmtAccreditationType);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtName);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(scrollPaneremake, BorderLayout.CENTER);
        //scrollPaneremake
        scrollPaneremake.getViewport().add(contremake, null);
        //contremake
        contremake.setBoundEditor(txtremake);

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
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isEnable", boolean.class, this.chkisEnable, "selected");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "_multiLangItem");
		dataBinder.registerBinding("Entry.seq", int.class, this.kdtEntry, "seq.text");
		dataBinder.registerBinding("Entry", com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("Entry.Accreditationwd", String.class, this.kdtEntry, "Accreditationwd.text");
		dataBinder.registerBinding("Entry.IndexName", java.lang.Object.class, this.kdtEntry, "IndexName.text");
		dataBinder.registerBinding("Entry.threeStandard", String.class, this.kdtEntry, "threeStandard.text");
		dataBinder.registerBinding("Entry.IndexDesc", String.class, this.kdtEntry, "IndexDesc.text");
		dataBinder.registerBinding("Entry.ScoreType", com.kingdee.util.enums.Enum.class, this.kdtEntry, "ScoreType.text");
		dataBinder.registerBinding("Entry.qz", java.math.BigDecimal.class, this.kdtEntry, "qz.text");
		dataBinder.registerBinding("Entry.remake", String.class, this.kdtEntry, "remake.text");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("AccreditationType", com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo.class, this.prmtAccreditationType, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("remake", String.class, this.txtremake, "text");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtDescription, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtSimpleName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtDescription, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtSimpleName, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtDescription, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtNumber, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtSimpleName, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.markesupplier.subase.app.MarketSupplierAppraiseTemplateEditUIHandler";
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
        this.editData = (com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"NONE",editData.getString("number"));
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
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("NONE");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
		}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????ßµ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("isEnable", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.Accreditationwd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.IndexName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.threeStandard", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.IndexDesc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.ScoreType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.qz", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.remake", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AccreditationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remake", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtDescription.setEnabled(true);
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtDescription.setEnabled(true);
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(false);
		            this.txtDescription.setEnabled(false);
		            this.txtNumber.setEnabled(false);
		            this.txtSimpleName.setEnabled(false);
        }
    }


    /**
     * output kdtEntry_Changed(int rowIndex,int colIndex) method
     */
    public void kdtEntry_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("IndexName".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"threeStandard").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"IndexName").getValue(),"threeStandard")));

}

    if ("IndexName".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"IndexDesc").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"IndexName").getValue(),"remake")));

}


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
        sic.add(new SelectorItemInfo("isEnable"));
        sic.add(new SelectorItemInfo("description"));
    	sic.add(new SelectorItemInfo("Entry.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("Entry.Accreditationwd"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.IndexName.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Entry.IndexName.id"));
			sic.add(new SelectorItemInfo("Entry.IndexName.name"));
        	sic.add(new SelectorItemInfo("Entry.IndexName.number"));
		}
    	sic.add(new SelectorItemInfo("Entry.threeStandard"));
    	sic.add(new SelectorItemInfo("Entry.IndexDesc"));
    	sic.add(new SelectorItemInfo("Entry.ScoreType"));
    	sic.add(new SelectorItemInfo("Entry.qz"));
    	sic.add(new SelectorItemInfo("Entry.remake"));
        sic.add(new SelectorItemInfo("simpleName"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("AccreditationType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("AccreditationType.id"));
        	sic.add(new SelectorItemInfo("AccreditationType.number"));
        	sic.add(new SelectorItemInfo("AccreditationType.name"));
		}
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("remake"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.markesupplier.subase.client", "MarketSupplierAppraiseTemplateEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.port.markesupplier.subase.client.MarketSupplierAppraiseTemplateEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo objectValue = new com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


        
					protected void beforeStoreFields(ActionEvent arg0) throws Exception {
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtAccreditationType.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"∆¿…Û¿‡–Õ"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtNumber.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"±‡¬Î"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtName.getDefaultLangItemData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"√˚≥∆"});
		}
			super.beforeStoreFields(arg0);
		}

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntry;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}