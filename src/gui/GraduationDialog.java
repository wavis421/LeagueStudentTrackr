package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.jdatepicker.impl.JDatePickerImpl;
import org.joda.time.DateTime;

public class GraduationDialog extends JDialog implements ActionListener {
	private final static int FRAME_WIDTH = 600;
	private final static int FRAME_HEIGHT = 500;

	// Main panel heights
	private final static int CONTROL_PANEL_HEIGHT = 205;
	private final static int TABLE_PANEL_HEIGHT = 150;
	private final static int BUTTON_PANEL_HEIGHT = 80;

	// Control sub-panel height
	private final static int CONTROL_SUB_PANEL_HEIGHT = 35;

	// Panel widths
	private final static int PANEL_WIDTH = FRAME_WIDTH - 10;
	private final static int PASSWORD_FIELD_WIDTH = 20;

	// Table width/height
	private final static int GRAD_TABLE_WIDTH = PANEL_WIDTH - 50;
	private final static int GRAD_TABLE_HEIGHT = TABLE_PANEL_HEIGHT - 10;

	// GUI components
	private JComboBox<String> emailUserList;
	private JPasswordField emailPwField;
	private JComboBox<String> gradLevelList;
	private ArrayList<GraduationModel> gradList;
	private JDatePickerImpl gradDatePicker;
	private JCheckBox emailParentCheckBox;
	private JTable gradTable;
	private GradTableModel gradTableModel;
	private JButton cancelButton;
	private JButton okButton;
	private JLabel errorField;
	private String gradClassName;

	// Temporary: for test only
	String[] teacherEmails = { "wendy.avis@jointheleague.org", "jackie.a@jointheleague.org" };
	String[] gradLevels = { "Level 0  ", "Level 1  ", "Level 2  ", "Level 3  ", "Level 4  ", "Level 5  ", "Level 6  ",
			"Level 7  ", "Level 8  ", "Level 9  " };

	public GraduationDialog(String clientID, String gradClassName) {
		setModal(true);
		this.gradClassName = gradClassName;

		// Create top level panels
		JPanel controlPanel = new JPanel();
		JPanel tablePanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		// Create sub-panels inside of control panel
		JPanel emailPanel = new JPanel();
		JPanel passwordPanel = new JPanel();
		JPanel levelPanel = new JPanel();
		JPanel gradDatePanel = new JPanel();
		JPanel emailParentPanel = new JPanel();

		// Create sub-panels inside of button panel
		JPanel errorPanel = new JPanel();
		JPanel okCancelPanel = new JPanel();

		// Create labels and right justify
		JLabel emailUserLabel = new JLabel("Teacher email: ");
		JLabel emailPwLabel = new JLabel(" Password: ");
		JLabel levelLabel = new JLabel("Level passed: ");
		JLabel gradDateLabel = new JLabel("Graduation date: ");

		emailUserLabel.setHorizontalAlignment(JLabel.RIGHT);
		emailPwLabel.setHorizontalAlignment(JLabel.RIGHT);
		levelLabel.setHorizontalAlignment(JLabel.RIGHT);
		gradDateLabel.setHorizontalAlignment(JLabel.RIGHT);

		// Create text input fields
		emailUserList = new JComboBox<String>(teacherEmails);
		emailPwField = new JPasswordField(PASSWORD_FIELD_WIDTH);
		gradLevelList = new JComboBox<String>(gradLevels);
		gradDatePicker = new DatePicker(new DateTime()).getDatePicker();
		emailParentCheckBox = new JCheckBox("Send email to parent: ");
		emailParentCheckBox.setHorizontalTextPosition(JCheckBox.LEFT);

		// Create table field
		gradList = new ArrayList<GraduationModel>();
		gradList.add(new GraduationModel("Alice Anderson", false, ""));
		gradList.add(new GraduationModel("Betty Broderson", false, ""));
		gradList.add(new GraduationModel("Cathy Clarke", false, ""));
		gradList.add(new GraduationModel("Donald Duck", false, ""));
		gradList.add(new GraduationModel("Emerson Elliot", false, ""));
		gradList.add(new GraduationModel("Frank Ferguson", false, ""));
		gradList.add(new GraduationModel("Gregory Grayson", false, ""));
		gradList.add(new GraduationModel("Herb Halstead", false, ""));

		gradTableModel = new GradTableModel(gradList);
		gradTable = new JTable(gradTableModel);
		gradTable.addMouseListener(new GradTableListener());
		JScrollPane gradScrollPane = createTablePanel(gradTable);
		gradScrollPane.setPreferredSize(new Dimension(GRAD_TABLE_WIDTH, GRAD_TABLE_HEIGHT));

		// Create error field and OK/Cancel buttons
		errorField = new JLabel(" ");
		errorField.setForeground(CustomFonts.TITLE_COLOR);
		errorField.setPreferredSize(new Dimension(PANEL_WIDTH - 20, errorField.getPreferredSize().height));
		errorField.setHorizontalTextPosition(JLabel.CENTER);
		errorField.setHorizontalAlignment(JLabel.CENTER);
		cancelButton = new JButton("Cancel");
		okButton = new JButton("Submit Scores");

		// Set panel height/width
		controlPanel.setPreferredSize(new Dimension(PANEL_WIDTH, CONTROL_PANEL_HEIGHT));
		emailPanel.setPreferredSize(new Dimension(PANEL_WIDTH, CONTROL_SUB_PANEL_HEIGHT));
		passwordPanel.setPreferredSize(new Dimension(PANEL_WIDTH, CONTROL_SUB_PANEL_HEIGHT));
		levelPanel.setPreferredSize(new Dimension(PANEL_WIDTH, CONTROL_SUB_PANEL_HEIGHT));
		gradDatePanel.setPreferredSize(new Dimension(PANEL_WIDTH, CONTROL_SUB_PANEL_HEIGHT + 5));
		emailParentPanel.setPreferredSize(new Dimension(PANEL_WIDTH, CONTROL_SUB_PANEL_HEIGHT));
		buttonPanel.setPreferredSize(new Dimension(PANEL_WIDTH, BUTTON_PANEL_HEIGHT));

		// Add orange borders for all input fields
		Border innerBorder = BorderFactory.createLineBorder(CustomFonts.TITLE_COLOR, 2, true);
		Border outerBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		emailUserList.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		emailPwField.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		gradLevelList.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		gradDatePicker.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		emailParentCheckBox.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		gradScrollPane.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		// Add all of the above to the panels
		emailPanel.add(emailUserLabel);
		emailPanel.add(emailUserList);
		passwordPanel.add(emailPwLabel);
		passwordPanel.add(emailPwField);
		levelPanel.add(levelLabel);
		levelPanel.add(gradLevelList);
		gradDatePanel.add(gradDateLabel);
		gradDatePanel.add(gradDatePicker);
		emailParentPanel.add(emailParentCheckBox);

		controlPanel.add(emailPanel);
		controlPanel.add(passwordPanel);
		controlPanel.add(levelPanel);
		controlPanel.add(gradDatePanel);
		controlPanel.add(emailParentPanel);

		tablePanel.add(gradScrollPane);
		errorPanel.add(errorField, JPanel.CENTER_ALIGNMENT);
		okCancelPanel.add(cancelButton);
		okCancelPanel.add(okButton);
		buttonPanel.add(errorPanel, JPanel.CENTER_ALIGNMENT);
		buttonPanel.add(okCancelPanel);

		// Add panels to dialog
		setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// Add listener to buttons
		cancelButton.addActionListener(this);
		okButton.addActionListener(this);

		// Set icon
		// TODO: Don't hard-code file name
		ImageIcon icon = new ImageIcon(getClass().getResource("PPicon24_Color_F16412.png"));
		setIconImage(icon.getImage());

		// Configure dialog window
		// TODO: Locate dialog relative to parent
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Graduate class '" + gradClassName + "'");
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setLocation(300, 300);
		setResizable(false);
		pack();
		setVisible(true);
	}

	private JScrollPane createTablePanel(JTable table) {
		// Set up table parameters
		table.setFont(CustomFonts.TABLE_TEXT_FONT);
		table.setGridColor(CustomFonts.TABLE_GRID_COLOR);
		table.setShowGrid(true);
		table.getTableHeader().setFont(CustomFonts.TABLE_HEADER_FONT);

		// Configure column widths
		table.getColumnModel().getColumn(GradTableModel.STUDENT_NAME_COLUMN).setPreferredWidth(180);
		table.getColumnModel().getColumn(GradTableModel.IS_SELECTED_COLUMN).setMaxWidth(70);
		table.getColumnModel().getColumn(GradTableModel.SCORE_COLUMN).setMaxWidth(80);

		// Set table properties
		table.setDefaultRenderer(Object.class, new GradTableRenderer());
		table.getColumnModel().getColumn(GradTableModel.SCORE_COLUMN).setCellEditor(new GradCellEditor());
		table.setCellSelectionEnabled(true);

		// Create scroll pane for table
		JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

		return scrollPane;
	}

	// TODO: Make this a class to share with "github dialog"
	private boolean generateAndSendEmail(String emailUser, String emailPassword, String emailBody) {
		// Currently hard-coded to send using gmail SMTP
		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailUser, new String(emailPassword));
			}
		});

		// Set cursor to "wait" cursor
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		try {
			// Set message fields
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailUser));
			message.setSubject("Graduate class");
			message.setText(emailBody, "utf-8");
			message.setSentDate(new Date());

			// Set email recipient
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailUser));

			// Send email
			Transport.send(message);

			// Set cursor back to default
			this.setCursor(Cursor.getDefaultCursor());

			return true;

		} catch (MessagingException e) {
			System.out.println(e.getMessage());
			errorField.setText("Failure sending email to " + emailUser);
		}

		// Set cursor back to default
		this.setCursor(Cursor.getDefaultCursor());
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			String pw = emailPwField.getText();
			int countSelected = 0;

			// Check that all fields are filled in, then create body and send email
			if (!pw.equals("")) {
				String body = "Test Graduation for class " + gradClassName;
				for (int i = 0; i < gradTableModel.getRowCount(); i++) {
					if ((boolean) gradTableModel.getValueAt(i, GradTableModel.IS_SELECTED_COLUMN)) {
						try {
							String scoreString = ((JTextField) gradTableModel.getValueAt(i,
									GradTableModel.SCORE_COLUMN)).getText();
							int score = Integer.parseInt(scoreString);
							if (score < 70 || score > 100) {
								errorField.setText("Student must have a passing score (%) between 70 and 100");
								return;
							}
							body += "\n\tStudent Name: "
									+ gradTableModel.getValueAt(i, GradTableModel.STUDENT_NAME_COLUMN) + ", Score: "
									+ scoreString;
							countSelected++;

						} catch (NumberFormatException e2) {
							errorField.setText("Student score (%) must be between 70 and 100");
							return;
						}
					}
				}
				if (countSelected == 0)
					errorField.setText("No students selected");

				else if (generateAndSendEmail(emailUserList.getSelectedItem().toString(), pw, body)) {
					setVisible(false);
					dispose();
				}

			} else {
				errorField.setText("Teacher email password required");
			}

		} else {
			setVisible(false);
			dispose();
		}
	}

	private class GraduationModel {
		private boolean isChecked;
		private String studentName;
		private JTextField score = new JTextField();

		public GraduationModel(String studentName, boolean isChecked, String score) {
			this.studentName = studentName;
			this.isChecked = isChecked;
			this.score.setText(score);
		}

		public boolean isChecked() {
			return isChecked;
		}

		public String getStudentName() {
			return studentName;
		}

		public String getScoreString() {
			return score.getText();
		}

		public JTextField getScoreTextField() {
			return score;
		}
	}

	private class GradTableModel extends AbstractTableModel {
		public static final int STUDENT_NAME_COLUMN = 0;
		public static final int IS_SELECTED_COLUMN = 1;
		public static final int SCORE_COLUMN = 2;

		private Object[][] tableObjects;
		private final String[] colNames = { " Student Name ", " Submit ", " Score " };

		public GradTableModel(ArrayList<GraduationModel> grads) {
			tableObjects = new Object[grads.size()][colNames.length];

			for (int row = 0; row < grads.size(); row++) {
				tableObjects[row][STUDENT_NAME_COLUMN] = grads.get(row).getStudentName();
				tableObjects[row][IS_SELECTED_COLUMN] = grads.get(row).isChecked();
				tableObjects[row][SCORE_COLUMN] = grads.get(row).getScoreTextField();
			}
		}

		@Override
		public int getColumnCount() {
			return colNames.length;
		}

		@Override
		public String getColumnName(int column) {
			return colNames[column];
		}

		@Override
		public int getRowCount() {
			if (tableObjects == null)
				return 0;
			else
				return tableObjects.length;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if (tableObjects.length == 0)
				return Object.class;
			else
				return tableObjects[0][columnIndex].getClass();
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			if (col == IS_SELECTED_COLUMN || col == SCORE_COLUMN)
				return true;
			else
				return false;
		}

		public void setCheckedStatus(int row, boolean checked) {
			tableObjects[row][IS_SELECTED_COLUMN] = checked;
		}

		@Override
		public Object getValueAt(int row, int col) {
			return tableObjects[row][col];
		}
	}

	private class GradCellEditor extends AbstractCellEditor implements TableCellEditor {
		JTextField editor;

		@Override
		public Object getCellEditorValue() {
			return editor.getText();
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			editor = (JTextField) ((GradTableModel) table.getModel()).getValueAt(row, column);
			return editor;
		}
	}

	private class GradTableRenderer extends JLabel implements TableCellRenderer {
		private GradTableRenderer() {
			super();
			super.setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			String text;
			if (column == GradTableModel.SCORE_COLUMN) {
				text = ((JTextField) value).getText();
			} else
				text = ((String) value);
			setText(text);

			if (column != -1) {
				setFont(CustomFonts.TABLE_TEXT_FONT);
				super.setForeground(Color.black);

				if (isSelected)
					super.setBackground(CustomFonts.SELECTED_BACKGROUND_COLOR);
				else
					super.setBackground(CustomFonts.UNSELECTED_BACKGROUND_COLOR);

				if (column == GradTableModel.STUDENT_NAME_COLUMN) {
					super.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0)); // left pad
					super.setHorizontalAlignment(LEFT);
				} else {
					super.setBorder(BorderFactory.createEmptyBorder());
					super.setHorizontalAlignment(CENTER);
				}
			}
			return this;
		}
	}

	private class GradTableListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			int row = gradTable.getSelectedRow();
			int col = gradTable.getSelectedColumn();

			if (e.getButton() == MouseEvent.BUTTON1 && row > -1) {
				if (col == GradTableModel.IS_SELECTED_COLUMN) {
					boolean checked = (boolean) gradTable.getValueAt(row, col);
					gradTableModel.setCheckedStatus(row, !checked);

				}
			}
		}
	}
}
