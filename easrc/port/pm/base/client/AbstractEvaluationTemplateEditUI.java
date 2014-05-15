/**
 * output package name
 */
package com.kingdee.eas.port.pm.base.client;

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
public abstract class AbstractEvaluationTemplateEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEvaluationTemplateEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttemplateName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttemplateType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisWeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcomment;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntry_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkuse;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttemplateName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmttemplateType;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanecomment;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtcomment;
    protected com.kingdee.eas.port.pm.base.EvaluationTemplateInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractEvaluationTemplateEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractEvaluationTemplateEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttemplateName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttemplateType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisWeight = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contcomment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.chkuse = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txttemplateName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmttemplateType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.scrollPanecomment = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtcomment = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDPanel1.setName("kDPanel1");
        this.txtName.setName("txtName");
        this.txtSimpleName.setName("txtSimpleName");
        this.txtDescription.setName("txtDescription");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.conttemplateName.setName("conttemplateName");
        this.conttemplateType.setName("conttemplateType");
        this.chkisWeight.setName("chkisWeight");
        this.contcomment.setName("contcomment");
        this.kdtEntry.setName("kdtEntry");
        this.chkuse.setName("chkuse");
        this.txtNumber.setName("txtNumber");
        this.txttemplateName.setName("txttemplateName");
        this.prmttemplateType.setName("prmttemplateType");
        this.scrollPanecomment.setName("scrollPanecomment");
        this.txtcomment.setName("txtcomment");
        // CoreUI		
        this.setPreferredSize(new Dimension(625,420));		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);		
        this.kDLabelContainer4.setVisible(true);
        // kDPanel1		
        this.kDPanel1.setPreferredSize(new Dimension(621,408));
        // txtName
        // txtSimpleName		
        this.txtSimpleName.setMaxLength(80);
        // txtDescription
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // conttemplateName		
        this.conttemplateName.setBoundLabelText(resHelper.getString("conttemplateName.boundLabelText"));		
        this.conttemplateName.setBoundLabelLength(100);		
        this.conttemplateName.setBoundLabelUnderline(true);		
        this.conttemplateName.setVisible(true);
        // conttemplateType		
        this.conttemplateType.setBoundLabelText(resHelper.getString("conttemplateType.boundLabelText"));		
        this.conttemplateType.setBoundLabelLength(100);		
        this.conttemplateType.setBoundLabelUnderline(true);		
        this.conttemplateType.setVisible(true);
        // chkisWeight		
        this.chkisWeight.setText(resHelper.getString("chkisWeight.text"));		
        this.chkisWeight.setHorizontalAlignment(2);
        // contcomment		
        this.contcomment.setBoundLabelText(resHelper.getString("contcomment.boundLabelText"));		
        this.contcomment.setBoundLabelLength(25);		
        this.contcomment.setBoundLabelUnderline(true);		
        this.contcomment.setVisible(true);		
        this.contcomment.setBoundLabelAlignment(8);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"indicatorType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"indicatorName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"weight\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"isValid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"comment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{indicatorType}</t:Cell><t:Cell>$Resource{indicatorName}</t:Cell><t:Cell>$Resource{weight}</t:Cell><t:Cell>$Resource{isValid}</t:Cell><t:Cell>$Resource{comment}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

        this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntry.putBindContents("editData",new String[] {"seq","indicatorType","indicatorName","weight","isValid","comment"});


        this.kdtEntry.checkParsed();
        KDFormattedTextField kdtEntry_seq_TextField = new KDFormattedTextField();
        kdtEntry_seq_TextField.setName("kdtEntry_seq_TextField");
        kdtEntry_seq_TextField.setVisible(true);
        kdtEntry_seq_TextField.setEditable(true);
        kdtEntry_seq_TextField.setHorizontalAlignment(2);
        kdtEntry_seq_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntry_seq_CellEditor = new KDTDefaultCellEditor(kdtEntry_seq_TextField);
        this.kdtEntry.getColumn("seq").setEditor(kdtEntry_seq_CellEditor);
        final KDBizPromptBox kdtEntry_indicatorType_PromptBox = new KDBizPromptBox();
        kdtEntry_indicatorType_PromptBox.setQueryInfo("com.kingdee.eas.port.pm.base.app.EvaluationIndicatorsQuery");
        kdtEntry_indicatorType_PromptBox.setVisible(true);
        kdtEntry_indicatorType_PromptBox.setEditable(true);
        kdtEntry_indicatorType_PromptBox.setDisplayFormat("$number$");
        kdtEntry_indicatorType_PromptBox.setEditFormat("$number$");
        kdtEntry_indicatorType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntry_indicatorType_CellEditor = new KDTDefaultCellEditor(kdtEntry_indicatorType_PromptBox);
        this.kdtEntry.getColumn("indicatorType").setEditor(kdtEntry_indicatorType_CellEditor);
        ObjectValueRender kdtEntry_indicatorType_OVR = new ObjectValueRender();
        kdtEntry_indicatorType_OVR.setFormat(new BizDataFormat("$evalType$"));
        this.kdtEntry.getColumn("indicatorType").setRenderer(kdtEntry_indicatorType_OVR);
        			kdtEntry_indicatorType_PromptBox.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.pm.base.client.EvaluationIndicatorsListUI kdtEntry_indicatorType_PromptBox_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (kdtEntry_indicatorType_PromptBox_F7ListUI == null) {
					try {
						kdtEntry_indicatorType_PromptBox_F7ListUI = new com.kingdee.eas.port.pm.base.client.EvaluationIndicatorsListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(kdtEntry_indicatorType_PromptBox_F7ListUI));
					kdtEntry_indicatorType_PromptBox_F7ListUI.setF7Use(true,ctx);
					kdtEntry_indicatorType_PromptBox.setSelector(kdtEntry_indicatorType_PromptBox_F7ListUI);
				}
			}
		});
					
        KDTextField kdtEntry_indicatorName_TextField = new KDTextField();
        kdtEntry_indicatorName_TextField.setName("kdtEntry_indicatorName_TextField");
        kdtEntry_indicatorName_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtEntry_indicatorName_CellEditor = new KDTDefaultCellEditor(kdtEntry_indicatorName_TextField);
        this.kdtEntry.getColumn("indicatorName").setEditor(kdtEntry_indicatorName_CellEditor);
        KDFormattedTextField kdtEntry_weight_TextField = new KDFormattedTextField();
        kdtEntry_weight_TextField.setName("kdtEntry_weight_TextField");
        kdtEntry_weight_TextField.setVisible(true);
        kdtEntry_weight_TextField.setEditable(true);
        kdtEntry_weight_TextField.setHorizontalAlignment(2);
        kdtEntry_weight_TextField.setDataType(1);
        	kdtEntry_weight_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntry_weight_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntry_weight_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntry_weight_CellEditor = new KDTDefaultCellEditor(kdtEntry_weight_TextField);
        this.kdtEntry.getColumn("weight").setEditor(kdtEntry_weight_CellEditor);
        KDCheckBox kdtEntry_isValid_CheckBox = new KDCheckBox();
        kdtEntry_isValid_CheckBox.setName("kdtEntry_isValid_CheckBox");
        KDTDefaultCellEditor kdtEntry_isValid_CellEditor = new KDTDefaultCellEditor(kdtEntry_isValid_CheckBox);
        this.kdtEntry.getColumn("isValid").setEditor(kdtEntry_isValid_CellEditor);
        KDTextField kdtEntry_comment_TextField = new KDTextField();
        kdtEntry_comment_TextField.setName("kdtEntry_comment_TextField");
        kdtEntry_comment_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntry_comment_CellEditor = new KDTDefaultCellEditor(kdtEntry_comment_TextField);
        this.kdtEntry.getColumn("comment").setEditor(kdtEntry_comment_CellEditor);
        // chkuse		
        this.chkuse.setText(resHelper.getString("chkuse.text"));		
        this.chkuse.setVisible(true);		
        this.chkuse.setHorizontalAlignment(2);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // txttemplateName		
        this.txttemplateName.setHorizontalAlignment(2);		
        this.txttemplateName.setMaxLength(100);		
        this.txttemplateName.setRequired(false);
        // prmttemplateType		
        this.prmttemplateType.setQueryInfo("com.kingdee.eas.port.pm.base.app.JudgesQuery");		
        this.prmttemplateType.setEditable(true);		
        this.prmttemplateType.setDisplayFormat("$name$");		
        this.prmttemplateType.setEditFormat("$number$");		
        this.prmttemplateType.setCommitFormat("$number$");		
        this.prmttemplateType.setRequired(false);
        		prmttemplateType.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.port.pm.base.client.JudgesListUI prmttemplateType_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (prmttemplateType_F7ListUI == null) {
					try {
						prmttemplateType_F7ListUI = new com.kingdee.eas.port.pm.base.client.JudgesListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(prmttemplateType_F7ListUI));
					prmttemplateType_F7ListUI.setF7Use(true,ctx);
					prmttemplateType.setSelector(prmttemplateType_F7ListUI);
				}
			}
		});
					
        // scrollPanecomment
        // txtcomment		
        this.txtcomment.setRequired(false);		
        this.txtcomment.setMaxLength(255);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txttemplateName,prmttemplateType,chkisWeight,txtcomment,txtSimpleName,txtDescription,txtNumber,txtName,kdtEntry,chkuse}));
        this.setFocusCycleRoot(true);
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
        this.setBounds(new Rectangle(0, 0, 625, 420));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 625, 420));
        kDLabelContainer2.setBounds(new Rectangle(555, 512, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(555, 512, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(555, 549, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(555, 549, 270, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(555, 586, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(555, 586, 270, 19, 0));
        kDPanel1.setBounds(new Rectangle(2, 3, 621, 408));
        this.add(kDPanel1, new KDLayout.Constraints(2, 3, 621, 408, 0));
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtName);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtSimpleName);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtDescription);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(2, 3, 621, 408));        kDLabelContainer1.setBounds(new Rectangle(25, 21, 270, 19));
        kDPanel1.add(kDLabelContainer1, new KDLayout.Constraints(25, 21, 270, 19, 0));
        conttemplateName.setBounds(new Rectangle(323, 21, 270, 19));
        kDPanel1.add(conttemplateName, new KDLayout.Constraints(323, 21, 270, 19, 0));
        conttemplateType.setBounds(new Rectangle(25, 43, 270, 19));
        kDPanel1.add(conttemplateType, new KDLayout.Constraints(25, 43, 270, 19, 0));
        chkisWeight.setBounds(new Rectangle(323, 43, 270, 19));
        kDPanel1.add(chkisWeight, new KDLayout.Constraints(323, 43, 270, 19, 0));
        contcomment.setBounds(new Rectangle(25, 69, 567, 132));
        kDPanel1.add(contcomment, new KDLayout.Constraints(25, 69, 567, 132, 0));
        kdtEntry.setBounds(new Rectangle(25, 214, 566, 170));
        kdtEntry_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntry,new com.kingdee.eas.port.pm.base.EvaluationTemplateEntryInfo(),null,false);
        kDPanel1.add(kdtEntry_detailPanel, new KDLayout.Constraints(25, 214, 566, 170, 0));
        chkuse.setBounds(new Rectangle(26, 386, 270, 19));
        kDPanel1.add(chkuse, new KDLayout.Constraints(26, 386, 270, 19, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //conttemplateName
        conttemplateName.setBoundEditor(txttemplateName);
        //conttemplateType
        conttemplateType.setBoundEditor(prmttemplateType);
        //contcomment
        contcomment.setBoundEditor(scrollPanecomment);
        //scrollPanecomment
        scrollPanecomment.getViewport().add(txtcomment, null);

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
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "_multiLangItem");
		dataBinder.registerBinding("isWeight", boolean.class, this.chkisWeight, "selected");
		dataBinder.registerBinding("Entry.seq", int.class, this.kdtEntry, "seq.text");
		dataBinder.registerBinding("Entry", com.kingdee.eas.port.pm.base.EvaluationTemplateEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("Entry.indicatorType", java.lang.Object.class, this.kdtEntry, "indicatorType.text");
		dataBinder.registerBinding("Entry.indicatorName", String.class, this.kdtEntry, "indicatorName.text");
		dataBinder.registerBinding("Entry.weight", java.math.BigDecimal.class, this.kdtEntry, "weight.text");
		dataBinder.registerBinding("Entry.comment", String.class, this.kdtEntry, "comment.text");
		dataBinder.registerBinding("Entry.isValid", boolean.class, this.kdtEntry, "isValid.text");
		dataBinder.registerBinding("use", boolean.class, this.chkuse, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("templateName", String.class, this.txttemplateName, "text");
		dataBinder.registerBinding("templateType", com.kingdee.eas.port.pm.base.JudgesInfo.class, this.prmttemplateType, "data");
		dataBinder.registerBinding("comment", String.class, this.txtcomment, "text");		
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
	    return "com.kingdee.eas.port.pm.base.app.EvaluationTemplateEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.txttemplateName.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.pm.base.EvaluationTemplateInfo)ov;
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
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isWeight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.indicatorType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.indicatorName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.weight", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.comment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.isValid", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("use", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("templateName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("templateType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("comment", ValidateHelper.ON_SAVE);    		
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
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }


    /**
     * output kdtEntry_Changed(int rowIndex,int colIndex) method
     */
    public void kdtEntry_Changed(int rowIndex,int colIndex) throws Exception
    {
            if ("indicatorType".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
kdtEntry.getCell(rowIndex,"indicatorName").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"indicatorType").getValue(),"name")));

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
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isWeight"));
    	sic.add(new SelectorItemInfo("Entry.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entry.indicatorType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Entry.indicatorType.id"));
			sic.add(new SelectorItemInfo("Entry.indicatorType.evalType"));
			sic.add(new SelectorItemInfo("Entry.indicatorType.name"));
        	sic.add(new SelectorItemInfo("Entry.indicatorType.number"));
		}
    	sic.add(new SelectorItemInfo("Entry.indicatorName"));
    	sic.add(new SelectorItemInfo("Entry.weight"));
    	sic.add(new SelectorItemInfo("Entry.comment"));
    	sic.add(new SelectorItemInfo("Entry.isValid"));
        sic.add(new SelectorItemInfo("use"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("templateName"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("templateType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("templateType.id"));
        	sic.add(new SelectorItemInfo("templateType.number"));
        	sic.add(new SelectorItemInfo("templateType.name"));
		}
        sic.add(new SelectorItemInfo("comment"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.pm.base.client", "EvaluationTemplateEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.port.pm.base.client.EvaluationTemplateEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.base.EvaluationTemplateFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.base.EvaluationTemplateInfo objectValue = new com.kingdee.eas.port.pm.base.EvaluationTemplateInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
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