package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import core.HDATEntry;
import core.HDATParser;
import model.HDATEntryListModel;
import net.miginfocom.swing.MigLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JTextField;

public class HDATParserGui {

	private static final Font BOLD = new Font("Tahoma", Font.BOLD, 14);
	private static final Font PLAIN = new Font("Tahoma", Font.PLAIN, 14);
	private static final Charset WINDOWS1251 = Charset.forName("windows-1251");
	private static final Charset WINDOWS1250 = Charset.forName("windows-1250");
	private static final Color BACKGROUND = new Color(240, 240, 240);
	private JFrame frmHotadatParserGui;
	private final JFileChooser chooser = new JFileChooser();
	private JList<HDATEntry> listEntryList;
	private JLabel lblPath;
	private Path hotaDatPath;
	private JTextArea textAreaEditor;
	private JButton btnWriteHotaDat;
	private JRadioButton rdbtnCharset1251;
	private JRadioButton rdbtnCharset1250;
	private HDATEntryListModel listModel;
	private int lastSelectedIndex = -1;
	private HDATParser parser;
	private JTextField searchField;
	private boolean changed = false;

	/**
	 * Launch the application.
	 */
	public static void launchGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HDATParserGui window = new HDATParserGui();
					window.frmHotadatParserGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HDATParserGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHotadatParserGui = new JFrame();
		frmHotadatParserGui.setTitle("HotA.dat Parser GUI");
		frmHotadatParserGui.setBounds(10, 10, 800, 1000);
		frmHotadatParserGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHotadatParserGui.getContentPane()
				.setLayout(new MigLayout("", "[175px][500px,grow,fill][]", "[][900px,grow][]"));

		this.initSearchField();
		frmHotadatParserGui.getContentPane().add(searchField, "cell 0 0,grow");

		lblPath = new JLabel("");
		lblPath.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPath.setFont(BOLD);
		frmHotadatParserGui.getContentPane().add(lblPath, "cell 1 0");

		JButton btnOpen = new JButton("Open");
		btnOpen.setFont(PLAIN);
		btnOpen.addActionListener(e -> {
			this.openDatFile();
		});
		frmHotadatParserGui.getContentPane().add(btnOpen, "cell 2 0");

		JScrollPane panelList = new JScrollPane();
		this.initList();
		panelList.setBorder(BorderFactory.createTitledBorder("File List"));
		panelList.setViewportView(listEntryList);
		frmHotadatParserGui.getContentPane().add(panelList, "cell 0 1,grow");

		JScrollPane panelEditor = new JScrollPane();
		this.initEditor();
		
		panelEditor.setBorder(BorderFactory.createTitledBorder("Editor"));
		panelEditor.setViewportView(textAreaEditor);
		frmHotadatParserGui.getContentPane().add(panelEditor, "cell 1 1 2 1,grow");

		btnWriteHotaDat = new JButton("Write HotA.dat");
		btnWriteHotaDat.setEnabled(false);
		btnWriteHotaDat.setFont(PLAIN);
		btnWriteHotaDat.addActionListener(e -> {
			this.writeHotaDat();
		});
		frmHotadatParserGui.getContentPane().add(btnWriteHotaDat, "cell 0 2,alignx center,growy");

		JPanel panelCharset = new JPanel();
		panelCharset.setLayout(new MigLayout("", "[][]", "[]"));
		ButtonGroup charsetButtons = new ButtonGroup();
		rdbtnCharset1251 = new JRadioButton("Windows-1251");
		rdbtnCharset1251.setSelected(true);
		panelCharset.add(rdbtnCharset1251, "cell 0 0,alignx center,aligny center");
		charsetButtons.add(rdbtnCharset1251);
		rdbtnCharset1250 = new JRadioButton("Windows-1250");
		panelCharset.add(rdbtnCharset1250, "cell 1 0,alignx center,aligny center");
		charsetButtons.add(rdbtnCharset1250);
		panelCharset.setBorder(BorderFactory.createTitledBorder("Charset"));
		frmHotadatParserGui.getContentPane().add(panelCharset, "cell 1 2 2 1, grow 0");

		this.setupChooser();
	}

	private void setupChooser() {
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		chooser.setPreferredSize(new Dimension(750, 550));
		chooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				return f.getName().equalsIgnoreCase("hota.dat");
			}

			@Override
			public String getDescription() {
				return "HotA.dat";
			}
		});
		
	}

	private void initEditor() {
		textAreaEditor = new JTextArea();
		textAreaEditor.setEnabled(false);
		textAreaEditor.setBackground(BACKGROUND);
		textAreaEditor.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textAreaEditor.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				changed = true;
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changed = true;
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				changed = true;
			}
		});		
	}

	private void initSearchField() {
		searchField = new JTextField();
		searchField.setFont(PLAIN);
		searchField.setEditable(false);
		searchField.setColumns(10);
		searchField.getDocument().addDocumentListener(new DocumentListener() {

			private void filter() {
				listEntryList.clearSelection();
				listModel.filter(searchField.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				filter();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filter();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}

		});

	}

	private void writeHotaDat() {
		if (this.parser == null) {
			JOptionPane.showMessageDialog(frmHotadatParserGui, "Parser is null.", "NullPointerException",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (this.hotaDatPath == null) {
			JOptionPane.showMessageDialog(frmHotadatParserGui, "HotA.dat path is null.", "NullPointerException",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.parser.setPath(hotaDatPath);
		this.parser.setCharset(getCharset());
		if (changed) {
			HDATEntry temp = null;
			temp = HDATEntry.fromText(this.textAreaEditor.getText());
			if (temp != null && lastSelectedIndex >= 0) {
				((HDATEntryListModel) this.listEntryList.getModel()).saveEntry(lastSelectedIndex, temp);
			}
		}
		if (this.parser.writeHDAT(((HDATEntryListModel) this.listEntryList.getModel()).getEntries())) {
			JOptionPane.showMessageDialog(frmHotadatParserGui,
					"File successfully written to:\n" + this.hotaDatPath.getParent().resolve("output.dat").toString(),
					"Success!", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(frmHotadatParserGui, "Something went wrong writing the output file :(",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void openDatFile() {
		if (chooser.showOpenDialog(frmHotadatParserGui) == JFileChooser.APPROVE_OPTION) {
			File selected = chooser.getSelectedFile();
			if (!selected.getName().equalsIgnoreCase("hota.dat")) {
				JOptionPane.showMessageDialog(frmHotadatParserGui, "Please select the HotA.dat file.");
				return;
			}
			this.hotaDatPath = selected.toPath();
			this.lblPath.setText(this.hotaDatPath.toString());
			this.textAreaEditor.setEnabled(true);
			this.btnWriteHotaDat.setEnabled(true);
			this.searchField.setEditable(true);
			this.initializeListModel();
		}
	}

	private void initializeListModel() {
		this.parser = new HDATParser(this.hotaDatPath, this.getCharset());
		this.listModel = new HDATEntryListModel(parser.parseHDAT());
		this.listEntryList.setModel(this.listModel);
	}

	private Charset getCharset() {
		return this.rdbtnCharset1250.isSelected() ? WINDOWS1250 : WINDOWS1251;
	}

	private void initList() {
		listEntryList = new JList<HDATEntry>();
		listEntryList.setBackground(BACKGROUND);
		listEntryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listEntryList.setFont(PLAIN);
		listEntryList.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				if (lastSelectedIndex != -1 && changed) {
					HDATEntry temp = null;
					temp = HDATEntry.fromText(textAreaEditor.getText());
					if (temp == null) {
						return;
					}
					listModel.saveEntry(lastSelectedIndex, temp);
				}
				int newIndex = listEntryList.getSelectedIndex();
				if (newIndex != -1) {
					HDATEntry current = listModel.getElementAt(newIndex);
					this.setText(current.toExportString());
					lastSelectedIndex = newIndex;
				} else {
					textAreaEditor.setText("");
					lastSelectedIndex = -1;
				}
			}
		});
	}

	private void setText(String text) {
		this.textAreaEditor.setText(text.replace("\r\n", "\n"));
		this.textAreaEditor.setCaretPosition(0);
		this.changed = false;
	}
}
