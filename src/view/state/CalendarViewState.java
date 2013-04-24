package view.state;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import utils.Dialog;

import model.Activity;
import model.Developer;
import model.Project;
import model.TimeEntry;
import model.gui.ActivityListRenderer;
import model.gui.GenericComboBoxModel;
import model.gui.DeveloperListRenderer;
import model.gui.ProjectListRenderer;

import java.awt.Rectangle;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

@SuppressWarnings("serial")
public class CalendarViewState extends AbstractViewState {

	private JButton backButton;
	private JLabel developerNameLabel;
	private List<JPanel> dayPanels;
	private JPanel tuesday;
	private JPanel monday;
	private JPanel wednesday;
	private JPanel thursday;
	private JPanel friday;
	private JPanel saturday;
	private JPanel sunday;
	private JPanel toolbar;
	private JPanel legend;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private JPanel panel_7;
	private JPanel panel_8;
	private JLabel lblTue;
	private JLabel lblWed;
	private JLabel lblThu;
	private JLabel lblFri;
	private JLabel lblSat;
	private JLabel lblSun;
	private JPanel panel;
	private JButton btnAdd;
	private JTextField endTimeInput;
	private JLabel lblNewLabel;
	private JLabel label;
	private JTextField startTimeInput;
	private JPanel panel_3;
	private JComboBox<Project> cmbProject;
	private JComboBox<Activity> cmbActivity;
	private JLabel lblProject;
	private JLabel lblActivity;

	public CalendarViewState() {
		setMinimumSize(new Dimension(500, 580));
		setMaximumSize(new Dimension(500, 580));

		JPanel weekPanel = new JPanel();
		weekPanel.setPreferredSize(new Dimension(500, 300));
		weekPanel.setMinimumSize(new Dimension(500, 300));
		weekPanel.setMaximumSize(new Dimension(500, 300));
		GridBagLayout gbl_weekPanel = new GridBagLayout();
		gbl_weekPanel.columnWidths = new int[] { 71, 71, 71, 71, 71, 71, 71, 0 };
		gbl_weekPanel.rowHeights = new int[] { 300, 0 };
		gbl_weekPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_weekPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		weekPanel.setLayout(gbl_weekPanel);

		monday = new JPanel();
		monday.setBackground(Color.GRAY);
		monday.setPreferredSize(new Dimension(70, 300));
		monday.setMinimumSize(new Dimension(70, 300));
		monday.setLayout(null);
		GridBagConstraints gbc_monday = new GridBagConstraints();
		gbc_monday.fill = GridBagConstraints.BOTH;
		gbc_monday.insets = new Insets(0, 0, 0, 1);
		gbc_monday.gridx = 0;
		gbc_monday.gridy = 0;
		weekPanel.add(monday, gbc_monday);
		
		tuesday = new JPanel();
		tuesday.setBackground(Color.GRAY);
		tuesday.setPreferredSize(new Dimension(70, 300));
		tuesday.setMinimumSize(new Dimension(70, 300));
		tuesday.setLayout(null);
		GridBagConstraints gbc_tuesday = new GridBagConstraints();
		gbc_tuesday.fill = GridBagConstraints.BOTH;
		gbc_tuesday.insets = new Insets(0, 0, 0, 1);
		gbc_tuesday.gridx = 1;
		gbc_tuesday.gridy = 0;
		weekPanel.add(tuesday, gbc_tuesday);

		wednesday = new JPanel();
		wednesday.setBackground(Color.GRAY);
		wednesday.setPreferredSize(new Dimension(70, 300));
		wednesday.setMinimumSize(new Dimension(70, 300));
		wednesday.setLayout(null);
		GridBagConstraints gbc_wednesday = new GridBagConstraints();
		gbc_wednesday.fill = GridBagConstraints.BOTH;
		gbc_wednesday.insets = new Insets(0, 0, 0, 1);
		gbc_wednesday.gridx = 2;
		gbc_wednesday.gridy = 0;
		weekPanel.add(wednesday, gbc_wednesday);

		thursday = new JPanel();
		thursday.setBackground(Color.GRAY);
		thursday.setPreferredSize(new Dimension(70, 300));
		thursday.setMinimumSize(new Dimension(70, 300));
		thursday.setLayout(null);
		GridBagConstraints gbc_thursday = new GridBagConstraints();
		gbc_thursday.fill = GridBagConstraints.BOTH;
		gbc_thursday.insets = new Insets(0, 0, 0, 1);
		gbc_thursday.gridx = 3;
		gbc_thursday.gridy = 0;
		weekPanel.add(thursday, gbc_thursday);

		friday = new JPanel();
		friday.setBackground(Color.GRAY);
		friday.setPreferredSize(new Dimension(70, 300));
		friday.setMinimumSize(new Dimension(70, 300));
		friday.setLayout(null);
		GridBagConstraints gbc_friday = new GridBagConstraints();
		gbc_friday.fill = GridBagConstraints.BOTH;
		gbc_friday.insets = new Insets(0, 0, 0, 1);
		gbc_friday.gridx = 4;
		gbc_friday.gridy = 0;
		weekPanel.add(friday, gbc_friday);

		saturday = new JPanel();
		saturday.setBackground(Color.GRAY);
		saturday.setPreferredSize(new Dimension(70, 300));
		saturday.setMinimumSize(new Dimension(70, 300));
		saturday.setLayout(null);
		GridBagConstraints gbc_saturday = new GridBagConstraints();
		gbc_saturday.fill = GridBagConstraints.BOTH;
		gbc_saturday.insets = new Insets(0, 0, 0, 1);
		gbc_saturday.gridx = 5;
		gbc_saturday.gridy = 0;
		weekPanel.add(saturday, gbc_saturday);

		sunday = new JPanel();
		sunday.setBackground(Color.GRAY);
		sunday.setPreferredSize(new Dimension(70, 300));
		sunday.setMinimumSize(new Dimension(70, 300));
		sunday.setLayout(null);
		GridBagConstraints gbc_sunday = new GridBagConstraints();
		gbc_sunday.insets = new Insets(0, 0, 0, 1);
		gbc_sunday.fill = GridBagConstraints.BOTH;
		gbc_sunday.gridx = 6;
		gbc_sunday.gridy = 0;
		weekPanel.add(sunday, gbc_sunday);

		this.dayPanels = new ArrayList<JPanel>();
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel topPanel = new JPanel();
		add(topPanel);
		topPanel.setLayout(new GridLayout(0, 1, 0, 0));

		toolbar = new JPanel();
		topPanel.add(toolbar);
		toolbar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		this.backButton = new JButton("Back to menu");
		toolbar.add(backButton);
		
		JPanel header = new JPanel();
		topPanel.add(header);
		header.setBounds(new Rectangle(0, 0, 500, 0));
		header.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		this.developerNameLabel = new JLabel();
		developerNameLabel.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
		header.add(developerNameLabel);
		developerNameLabel.setText("Test");
		add(weekPanel);
		
		legend = new JPanel();
		legend.setPreferredSize(new Dimension(500, 20));
		legend.setMinimumSize(new Dimension(500, 20));
		legend.setMaximumSize(new Dimension(500, 20));
		add(legend);
		GridBagLayout gbl_legend = new GridBagLayout();
		gbl_legend.columnWidths = new int[] {71, 71, 71, 71, 71, 71, 71};
		gbl_legend.rowHeights = new int[] {300, 300, 0};
		gbl_legend.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_legend.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		legend.setLayout(gbl_legend);
		
		panel_1 = new JPanel();
		panel_1.setMaximumSize(new Dimension(70, 70));
		panel_1.setPreferredSize(new Dimension(70, 70));
		panel_1.setMinimumSize(new Dimension(70, 70));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 0, 1);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		legend.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblMon = new JLabel("Mon 13/4");
		lblMon.setHorizontalAlignment(SwingConstants.CENTER);
		lblMon.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lblMon);
		
		panel_2 = new JPanel();
		panel_2.setMaximumSize(new Dimension(70, 70));
		panel_2.setPreferredSize(new Dimension(70, 70));
		panel_2.setMinimumSize(new Dimension(70, 70));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.insets = new Insets(0, 0, 0, 1);
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 0;
		legend.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblTue = new JLabel("Mon 13/4");
		lblTue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTue.setAlignmentX(0.5f);
		panel_2.add(lblTue);
		
		panel_4 = new JPanel();
		panel_4.setMaximumSize(new Dimension(70, 70));
		panel_4.setPreferredSize(new Dimension(70, 70));
		panel_4.setMinimumSize(new Dimension(70, 70));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.insets = new Insets(0, 0, 0, 1);
		gbc_panel_4.gridx = 2;
		gbc_panel_4.gridy = 0;
		legend.add(panel_4, gbc_panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblWed = new JLabel("Mon 13/4");
		lblWed.setHorizontalAlignment(SwingConstants.CENTER);
		lblWed.setAlignmentX(0.5f);
		panel_4.add(lblWed);
		
		panel_5 = new JPanel();
		panel_5.setMaximumSize(new Dimension(70, 70));
		panel_5.setPreferredSize(new Dimension(70, 70));
		panel_5.setMinimumSize(new Dimension(70, 70));
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.insets = new Insets(0, 0, 0, 1);
		gbc_panel_5.gridx = 3;
		gbc_panel_5.gridy = 0;
		legend.add(panel_5, gbc_panel_5);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblThu = new JLabel("Mon 13/4");
		lblThu.setHorizontalAlignment(SwingConstants.CENTER);
		lblThu.setAlignmentX(0.5f);
		panel_5.add(lblThu);
		
		panel_6 = new JPanel();
		panel_6.setMaximumSize(new Dimension(70, 70));
		panel_6.setPreferredSize(new Dimension(70, 70));
		panel_6.setMinimumSize(new Dimension(70, 70));
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.insets = new Insets(0, 0, 0, 1);
		gbc_panel_6.gridx = 4;
		gbc_panel_6.gridy = 0;
		legend.add(panel_6, gbc_panel_6);
		panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblFri = new JLabel("Mon 13/4");
		lblFri.setHorizontalAlignment(SwingConstants.CENTER);
		lblFri.setAlignmentX(0.5f);
		panel_6.add(lblFri);
		
		panel_7 = new JPanel();
		panel_7.setMaximumSize(new Dimension(70, 70));
		panel_7.setPreferredSize(new Dimension(70, 70));
		panel_7.setMinimumSize(new Dimension(70, 70));
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.insets = new Insets(0, 0, 0, 1);
		gbc_panel_7.gridx = 5;
		gbc_panel_7.gridy = 0;
		legend.add(panel_7, gbc_panel_7);
		panel_7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblSat = new JLabel("Mon 13/4");
		lblSat.setHorizontalAlignment(SwingConstants.CENTER);
		lblSat.setAlignmentX(0.5f);
		panel_7.add(lblSat);
		
		panel_8 = new JPanel();
		panel_8.setMaximumSize(new Dimension(70, 70));
		panel_8.setPreferredSize(new Dimension(70, 70));
		panel_8.setMinimumSize(new Dimension(70, 70));
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.insets = new Insets(0, 0, 0, 1);
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.gridx = 6;
		gbc_panel_8.gridy = 0;
		legend.add(panel_8, gbc_panel_8);
		panel_8.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblSun = new JLabel("Mon 13/4");
		lblSun.setHorizontalAlignment(SwingConstants.CENTER);
		lblSun.setAlignmentX(0.5f);
		panel_8.add(lblSun);
		
		JPanel controls = new JPanel();
		add(controls);
		
		JButton btnPreviousWeek = new JButton("Previous week");
		controls.add(btnPreviousWeek);
		
		JButton btnNextWeek = new JButton("Next week");
		controls.add(btnNextWeek);
		
		panel = new JPanel();
		add(panel);
		
		label = new JLabel("Start time");
		label.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
		panel.add(label);
		
		startTimeInput = new JTextField();
		startTimeInput.setColumns(10);
		panel.add(startTimeInput);
		
		lblNewLabel = new JLabel("End time");
		lblNewLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
		panel.add(lblNewLabel);
		
		endTimeInput = new JTextField();
		panel.add(endTimeInput);
		endTimeInput.setColumns(10);
		
		btnAdd = new JButton("Add");
		panel.add(btnAdd);
		
		panel_3 = new JPanel();
		add(panel_3);
		
		cmbProject = new JComboBox<Project>();
		cmbProject.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
			}
		});
		
		lblProject = new JLabel("Project");
		lblProject.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
		panel_3.add(lblProject);
		panel_3.add(cmbProject);
		
		lblActivity = new JLabel("Activity");
		lblActivity.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
		panel_3.add(lblActivity);
		
		cmbActivity = new JComboBox<Activity>();
		panel_3.add(cmbActivity);
	}

	public void setProjects(List<Project> projects) {
		this.cmbProject.setModel(new GenericComboBoxModel<Project>(projects));
		this.cmbProject.setRenderer(new ProjectListRenderer());
	}

	public void setActivities(List<Activity> activities) {
		this.cmbActivity.setModel(new GenericComboBoxModel<Activity>(activities));
		this.cmbActivity.setRenderer(new ActivityListRenderer());
	}
	
	private void addTimeEntry(final TimeEntry timeEntry) {

		int fromMinute = timeEntry.getStartDate().get(Calendar.HOUR_OF_DAY) * 60 + timeEntry.getStartDate().get(Calendar.MINUTE);

		float minuteHeight =  300f / (24f * 60f);

		JPanel timeEntryPanel = new JPanel();
		timeEntryPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Dialog.message("" + timeEntry.getId());
			}
		});
		
		Color c = Color.getHSBColor(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat());
		timeEntryPanel.setBackground(c);
		int durationInMinutes = timeEntry.getDurationInMinutes();
		
		System.out.println("from: " + (int)(minuteHeight * fromMinute) + " - to: " + (int)(minuteHeight * (fromMinute + durationInMinutes)));
		timeEntryPanel.setBounds(0, (int)(minuteHeight * fromMinute), 70, (int)(minuteHeight * (fromMinute + durationInMinutes)));
		timeEntryPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		JLabel projectLabel = new JLabel("" + timeEntry.getActivity().getProject().getId());
		timeEntryPanel.add(projectLabel);
		
		wednesday.add(timeEntryPanel);
	}

	public JButton getBackButton() {
		return this.backButton;
	}

	public void setDeveloperName(String name) {
		this.developerNameLabel.setText(name);
	}

	public void setTimeEntries(List<TimeEntry> entries) {
		for (TimeEntry timeEntry : entries) {
			this.addTimeEntry(timeEntry);	
		}
	}
}
