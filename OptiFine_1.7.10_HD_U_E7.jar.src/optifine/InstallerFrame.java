/*     */ package optifine;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.File;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ public class InstallerFrame extends JFrame {
/*  22 */   private JLabel ivjLabelOfVersion = null;
/*  23 */   private JLabel ivjLabelMcVersion = null;
/*  24 */   private JPanel ivjPanelCenter = null;
/*  25 */   private JButton ivjButtonInstall = null;
/*  26 */   private JButton ivjButtonClose = null;
/*  27 */   private JPanel ivjPanelBottom = null;
/*  28 */   private JPanel ivjPanelContentPane = null;
/*  29 */   IvjEventHandler ivjEventHandler = new IvjEventHandler();
/*  30 */   private JTextArea ivjTextArea = null;
/*  31 */   private JButton ivjButtonExtract = null;
/*  32 */   private JLabel ivjLabelFolder = null;
/*  33 */   private JTextField ivjFieldFolder = null;
/*  34 */   private JButton ivjButtonFolder = null;
/*     */   
/*     */   class IvjEventHandler
/*     */     implements ActionListener {
/*     */     public void actionPerformed(ActionEvent e) {
/*  39 */       if (e.getSource() == InstallerFrame.this.getButtonClose())
/*  40 */         InstallerFrame.this.connEtoC2(e); 
/*  41 */       if (e.getSource() == InstallerFrame.this.getButtonExtract())
/*  42 */         InstallerFrame.this.connEtoC3(e); 
/*  43 */       if (e.getSource() == InstallerFrame.this.getButtonFolder())
/*  44 */         InstallerFrame.this.connEtoC4(e); 
/*  45 */       if (e.getSource() == InstallerFrame.this.getButtonInstall()) {
/*  46 */         InstallerFrame.this.connEtoC1(e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InstallerFrame() {
/*  56 */     initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void customInit() {
/*     */     try {
/*  67 */       pack();
/*     */       
/*  69 */       setDefaultCloseOperation(3);
/*     */       
/*  71 */       File dirMc = Utils.getWorkingDirectory();
/*  72 */       getFieldFolder().setText(dirMc.getPath());
/*     */       
/*  74 */       getButtonInstall().setEnabled(false);
/*  75 */       getButtonExtract().setEnabled(false);
/*     */       
/*  77 */       String ofVer = Installer.getOptiFineVersion();
/*  78 */       Utils.dbg("OptiFine Version: " + ofVer);
/*  79 */       String[] ofVers = Utils.tokenize(ofVer, "_");
/*     */       
/*  81 */       String mcVer = ofVers[1];
/*  82 */       Utils.dbg("Minecraft Version: " + mcVer);
/*     */       
/*  84 */       String ofEd = Installer.getOptiFineEdition(ofVers);
/*  85 */       Utils.dbg("OptiFine Edition: " + ofEd);
/*     */       
/*  87 */       String ofEdClear = ofEd.replace("_", " ");
/*  88 */       ofEdClear = ofEdClear.replace(" U ", " Ultra ");
/*  89 */       ofEdClear = ofEdClear.replace("L ", "Light ");
/*     */       
/*  91 */       getLabelOfVersion().setText("OptiFine " + ofEdClear);
/*  92 */       getLabelMcVersion().setText("for Minecraft " + mcVer);
/*     */       
/*  94 */       getButtonInstall().setEnabled(true);
/*  95 */       getButtonExtract().setEnabled(true);
/*     */       
/*  97 */       getButtonInstall().requestFocus();
/*     */       
/*  99 */       if (!Installer.isPatchFile()) {
/* 100 */         getButtonExtract().setVisible(false);
/*     */       }
/* 102 */     } catch (Exception e) {
/*     */       
/* 104 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*     */     try {
/* 113 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*     */       
/* 115 */       InstallerFrame frm = new InstallerFrame();
/*     */       
/* 117 */       Utils.centerWindow(frm, null);
/*     */       
/* 119 */       frm.show();
/*     */     }
/* 121 */     catch (Exception e) {
/*     */ 
/*     */       
/* 124 */       String msg = e.getMessage();
/* 125 */       if (msg != null && msg.equals("QUIET")) {
/*     */         return;
/*     */       }
/* 128 */       e.printStackTrace();
/* 129 */       String str = Utils.getExceptionStackTrace(e);
/* 130 */       str = str.replace("\t", "  ");
/* 131 */       JTextArea textArea = new JTextArea(str);
/* 132 */       textArea.setEditable(false);
/* 133 */       Font f = textArea.getFont();
/* 134 */       Font f2 = new Font("Monospaced", f.getStyle(), f.getSize());
/* 135 */       textArea.setFont(f2);
/* 136 */       JScrollPane scrollPane = new JScrollPane(textArea);
/* 137 */       scrollPane.setPreferredSize(new Dimension(600, 400));
/* 138 */       JOptionPane.showMessageDialog(null, scrollPane, "Error", 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleException(Throwable e) {
/* 150 */     String msg = e.getMessage();
/* 151 */     if (msg != null && msg.equals("QUIET")) {
/*     */       return;
/*     */     }
/* 154 */     e.printStackTrace();
/* 155 */     String str = Utils.getExceptionStackTrace(e);
/* 156 */     str = str.replace("\t", "  ");
/* 157 */     JTextArea textArea = new JTextArea(str);
/* 158 */     textArea.setEditable(false);
/* 159 */     Font f = textArea.getFont();
/* 160 */     Font f2 = new Font("Monospaced", f.getStyle(), f.getSize());
/* 161 */     textArea.setFont(f2);
/* 162 */     JScrollPane scrollPane = new JScrollPane(textArea);
/* 163 */     scrollPane.setPreferredSize(new Dimension(600, 400));
/* 164 */     JOptionPane.showMessageDialog(null, scrollPane, "Error", 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JLabel getLabelOfVersion() {
/* 174 */     if (this.ivjLabelOfVersion == null) {
/*     */       
/*     */       try {
/*     */         
/* 178 */         this.ivjLabelOfVersion = new JLabel();
/* 179 */         this.ivjLabelOfVersion.setName("LabelOfVersion");
/* 180 */         this.ivjLabelOfVersion.setBounds(2, 5, 385, 42);
/* 181 */         this.ivjLabelOfVersion.setFont(new Font("Dialog", 1, 18));
/* 182 */         this.ivjLabelOfVersion.setHorizontalAlignment(0);
/* 183 */         this.ivjLabelOfVersion.setPreferredSize(new Dimension(385, 42));
/* 184 */         this.ivjLabelOfVersion.setText("OptiFine ...");
/*     */ 
/*     */       
/*     */       }
/* 188 */       catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */         
/* 192 */         handleException(ivjExc);
/*     */       } 
/*     */     }
/* 195 */     return this.ivjLabelOfVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JLabel getLabelMcVersion() {
/* 205 */     if (this.ivjLabelMcVersion == null) {
/*     */       
/*     */       try {
/*     */         
/* 209 */         this.ivjLabelMcVersion = new JLabel();
/* 210 */         this.ivjLabelMcVersion.setName("LabelMcVersion");
/* 211 */         this.ivjLabelMcVersion.setBounds(2, 38, 385, 25);
/* 212 */         this.ivjLabelMcVersion.setFont(new Font("Dialog", 1, 14));
/* 213 */         this.ivjLabelMcVersion.setHorizontalAlignment(0);
/* 214 */         this.ivjLabelMcVersion.setPreferredSize(new Dimension(385, 25));
/* 215 */         this.ivjLabelMcVersion.setText("for Minecraft ...");
/*     */ 
/*     */       
/*     */       }
/* 219 */       catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */         
/* 223 */         handleException(ivjExc);
/*     */       } 
/*     */     }
/* 226 */     return this.ivjLabelMcVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getPanelCenter() {
/* 236 */     if (this.ivjPanelCenter == null) {
/*     */       
/*     */       try {
/*     */         
/* 240 */         this.ivjPanelCenter = new JPanel();
/* 241 */         this.ivjPanelCenter.setName("PanelCenter");
/* 242 */         this.ivjPanelCenter.setLayout((LayoutManager)null);
/* 243 */         this.ivjPanelCenter.add(getLabelOfVersion(), getLabelOfVersion().getName());
/* 244 */         this.ivjPanelCenter.add(getLabelMcVersion(), getLabelMcVersion().getName());
/* 245 */         this.ivjPanelCenter.add(getTextArea(), getTextArea().getName());
/* 246 */         this.ivjPanelCenter.add(getLabelFolder(), getLabelFolder().getName());
/* 247 */         this.ivjPanelCenter.add(getFieldFolder(), getFieldFolder().getName());
/* 248 */         this.ivjPanelCenter.add(getButtonFolder(), getButtonFolder().getName());
/*     */ 
/*     */       
/*     */       }
/* 252 */       catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */         
/* 256 */         handleException(ivjExc);
/*     */       } 
/*     */     }
/* 259 */     return this.ivjPanelCenter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JButton getButtonInstall() {
/* 269 */     if (this.ivjButtonInstall == null) {
/*     */       
/*     */       try {
/*     */         
/* 273 */         this.ivjButtonInstall = new JButton();
/* 274 */         this.ivjButtonInstall.setName("ButtonInstall");
/* 275 */         this.ivjButtonInstall.setPreferredSize(new Dimension(100, 26));
/* 276 */         this.ivjButtonInstall.setText("Install");
/*     */ 
/*     */       
/*     */       }
/* 280 */       catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */         
/* 284 */         handleException(ivjExc);
/*     */       } 
/*     */     }
/* 287 */     return this.ivjButtonInstall;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JButton getButtonClose() {
/* 297 */     if (this.ivjButtonClose == null) {
/*     */       
/*     */       try {
/*     */         
/* 301 */         this.ivjButtonClose = new JButton();
/* 302 */         this.ivjButtonClose.setName("ButtonClose");
/* 303 */         this.ivjButtonClose.setPreferredSize(new Dimension(100, 26));
/* 304 */         this.ivjButtonClose.setText("Cancel");
/*     */ 
/*     */       
/*     */       }
/* 308 */       catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */         
/* 312 */         handleException(ivjExc);
/*     */       } 
/*     */     }
/* 315 */     return this.ivjButtonClose;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getPanelBottom() {
/* 325 */     if (this.ivjPanelBottom == null) {
/*     */       
/*     */       try {
/*     */         
/* 329 */         this.ivjPanelBottom = new JPanel();
/* 330 */         this.ivjPanelBottom.setName("PanelBottom");
/* 331 */         this.ivjPanelBottom.setLayout(new FlowLayout(1, 15, 10));
/* 332 */         this.ivjPanelBottom.setPreferredSize(new Dimension(390, 55));
/* 333 */         this.ivjPanelBottom.add(getButtonInstall(), getButtonInstall().getName());
/* 334 */         this.ivjPanelBottom.add(getButtonExtract(), getButtonExtract().getName());
/* 335 */         this.ivjPanelBottom.add(getButtonClose(), getButtonClose().getName());
/*     */ 
/*     */       
/*     */       }
/* 339 */       catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */         
/* 343 */         handleException(ivjExc);
/*     */       } 
/*     */     }
/* 346 */     return this.ivjPanelBottom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getPanelContentPane() {
/* 356 */     if (this.ivjPanelContentPane == null) {
/*     */       
/*     */       try {
/*     */         
/* 360 */         this.ivjPanelContentPane = new JPanel();
/* 361 */         this.ivjPanelContentPane.setName("PanelContentPane");
/* 362 */         this.ivjPanelContentPane.setLayout(new BorderLayout(5, 5));
/* 363 */         this.ivjPanelContentPane.setPreferredSize(new Dimension(394, 203));
/* 364 */         this.ivjPanelContentPane.add(getPanelCenter(), "Center");
/* 365 */         this.ivjPanelContentPane.add(getPanelBottom(), "South");
/*     */ 
/*     */       
/*     */       }
/* 369 */       catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */         
/* 373 */         handleException(ivjExc);
/*     */       } 
/*     */     }
/* 376 */     return this.ivjPanelContentPane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*     */     try {
/* 388 */       setName("InstallerFrame");
/* 389 */       setSize(404, 236);
/* 390 */       setDefaultCloseOperation(0);
/* 391 */       setResizable(false);
/* 392 */       setTitle("OptiFine Installer");
/* 393 */       setContentPane(getPanelContentPane());
/* 394 */       initConnections();
/*     */     }
/* 396 */     catch (Throwable ivjExc) {
/*     */       
/* 398 */       handleException(ivjExc);
/*     */     } 
/*     */     
/* 401 */     customInit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInstall() {
/*     */     try {
/* 412 */       File dirMc = new File(getFieldFolder().getText());
/*     */       
/* 414 */       if (!dirMc.exists()) {
/*     */         
/* 416 */         Utils.showErrorMessage("Folder not found: " + dirMc.getPath());
/*     */         return;
/*     */       } 
/* 419 */       if (!dirMc.isDirectory()) {
/*     */         
/* 421 */         Utils.showErrorMessage("Not a folder: " + dirMc.getPath());
/*     */         
/*     */         return;
/*     */       } 
/* 425 */       Installer.doInstall(dirMc);
/*     */       
/* 427 */       Utils.showMessage("OptiFine is successfully installed.");
/*     */       
/* 429 */       dispose();
/*     */     }
/* 431 */     catch (Exception e) {
/*     */       
/* 433 */       handleException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onExtract() {
/*     */     try {
/* 444 */       File dirMc = new File(getFieldFolder().getText());
/*     */       
/* 446 */       if (!dirMc.exists()) {
/*     */         
/* 448 */         Utils.showErrorMessage("Folder not found: " + dirMc.getPath());
/*     */         return;
/*     */       } 
/* 451 */       if (!dirMc.isDirectory()) {
/*     */         
/* 453 */         Utils.showErrorMessage("Not a folder: " + dirMc.getPath());
/*     */         
/*     */         return;
/*     */       } 
/* 457 */       boolean ok = Installer.doExtract(dirMc);
/*     */       
/* 459 */       if (ok)
/*     */       {
/*     */         
/* 462 */         Utils.showMessage("OptiFine is successfully extracted.");
/*     */         
/* 464 */         dispose();
/*     */       }
/*     */     
/* 467 */     } catch (Exception e) {
/*     */       
/* 469 */       handleException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 477 */     dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void connEtoC1(ActionEvent arg1) {
/*     */     try {
/* 490 */       onInstall();
/*     */ 
/*     */     
/*     */     }
/* 494 */     catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */       
/* 498 */       handleException(ivjExc);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void connEtoC2(ActionEvent arg1) {
/*     */     try {
/* 512 */       onClose();
/*     */ 
/*     */     
/*     */     }
/* 516 */     catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */       
/* 520 */       handleException(ivjExc);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initConnections() throws Exception {
/* 532 */     getButtonFolder().addActionListener(this.ivjEventHandler);
/* 533 */     getButtonInstall().addActionListener(this.ivjEventHandler);
/* 534 */     getButtonExtract().addActionListener(this.ivjEventHandler);
/* 535 */     getButtonClose().addActionListener(this.ivjEventHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JTextArea getTextArea() {
/* 545 */     if (this.ivjTextArea == null) {
/*     */       
/*     */       try {
/*     */         
/* 549 */         this.ivjTextArea = new JTextArea();
/* 550 */         this.ivjTextArea.setName("TextArea");
/* 551 */         this.ivjTextArea.setBounds(15, 66, 365, 44);
/* 552 */         this.ivjTextArea.setEditable(false);
/* 553 */         this.ivjTextArea.setEnabled(true);
/* 554 */         this.ivjTextArea.setFont(new Font("Dialog", 0, 12));
/* 555 */         this.ivjTextArea.setLineWrap(true);
/* 556 */         this.ivjTextArea.setOpaque(false);
/* 557 */         this.ivjTextArea.setPreferredSize(new Dimension(365, 44));
/* 558 */         this.ivjTextArea.setText("This installer will install OptiFine in the official Minecraft launcher and will create a new profile \"OptiFine\" for it.");
/* 559 */         this.ivjTextArea.setWrapStyleWord(true);
/*     */ 
/*     */       
/*     */       }
/* 563 */       catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */         
/* 567 */         handleException(ivjExc);
/*     */       } 
/*     */     }
/* 570 */     return this.ivjTextArea;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JButton getButtonExtract() {
/* 580 */     if (this.ivjButtonExtract == null) {
/*     */       
/*     */       try {
/*     */         
/* 584 */         this.ivjButtonExtract = new JButton();
/* 585 */         this.ivjButtonExtract.setName("ButtonExtract");
/* 586 */         this.ivjButtonExtract.setPreferredSize(new Dimension(100, 26));
/* 587 */         this.ivjButtonExtract.setText("Extract");
/*     */ 
/*     */       
/*     */       }
/* 591 */       catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */         
/* 595 */         handleException(ivjExc);
/*     */       } 
/*     */     }
/* 598 */     return this.ivjButtonExtract;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void connEtoC3(ActionEvent arg1) {
/*     */     try {
/* 611 */       onExtract();
/*     */ 
/*     */     
/*     */     }
/* 615 */     catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */       
/* 619 */       handleException(ivjExc);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JLabel getLabelFolder() {
/* 630 */     if (this.ivjLabelFolder == null) {
/*     */       
/*     */       try {
/*     */         
/* 634 */         this.ivjLabelFolder = new JLabel();
/* 635 */         this.ivjLabelFolder.setName("LabelFolder");
/* 636 */         this.ivjLabelFolder.setBounds(15, 116, 47, 16);
/* 637 */         this.ivjLabelFolder.setPreferredSize(new Dimension(47, 16));
/* 638 */         this.ivjLabelFolder.setText("Folder");
/*     */ 
/*     */       
/*     */       }
/* 642 */       catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */         
/* 646 */         handleException(ivjExc);
/*     */       } 
/*     */     }
/* 649 */     return this.ivjLabelFolder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JTextField getFieldFolder() {
/* 659 */     if (this.ivjFieldFolder == null) {
/*     */       
/*     */       try {
/*     */         
/* 663 */         this.ivjFieldFolder = new JTextField();
/* 664 */         this.ivjFieldFolder.setName("FieldFolder");
/* 665 */         this.ivjFieldFolder.setBounds(62, 114, 287, 20);
/* 666 */         this.ivjFieldFolder.setEditable(false);
/* 667 */         this.ivjFieldFolder.setPreferredSize(new Dimension(287, 20));
/*     */ 
/*     */       
/*     */       }
/* 671 */       catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */         
/* 675 */         handleException(ivjExc);
/*     */       } 
/*     */     }
/* 678 */     return this.ivjFieldFolder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JButton getButtonFolder() {
/* 688 */     if (this.ivjButtonFolder == null) {
/*     */       
/*     */       try {
/*     */         
/* 692 */         this.ivjButtonFolder = new JButton();
/* 693 */         this.ivjButtonFolder.setName("ButtonFolder");
/* 694 */         this.ivjButtonFolder.setBounds(350, 114, 25, 20);
/* 695 */         this.ivjButtonFolder.setMargin(new Insets(2, 2, 2, 2));
/* 696 */         this.ivjButtonFolder.setPreferredSize(new Dimension(25, 20));
/* 697 */         this.ivjButtonFolder.setText("...");
/*     */ 
/*     */       
/*     */       }
/* 701 */       catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */         
/* 705 */         handleException(ivjExc);
/*     */       } 
/*     */     }
/* 708 */     return this.ivjButtonFolder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFolderSelect() {
/* 715 */     File dirMc = new File(getFieldFolder().getText());
/* 716 */     JFileChooser jfc = new JFileChooser(dirMc);
/* 717 */     jfc.setFileSelectionMode(1);
/* 718 */     jfc.setAcceptAllFileFilterUsed(false);
/* 719 */     if (jfc.showOpenDialog(this) == 0) {
/*     */       
/* 721 */       File dir = jfc.getSelectedFile();
/* 722 */       getFieldFolder().setText(dir.getPath());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void connEtoC4(ActionEvent arg1) {
/*     */     try {
/* 736 */       onFolderSelect();
/*     */ 
/*     */     
/*     */     }
/* 740 */     catch (Throwable ivjExc) {
/*     */ 
/*     */ 
/*     */       
/* 744 */       handleException(ivjExc);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\optifine\InstallerFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */