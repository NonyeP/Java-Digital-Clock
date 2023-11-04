package clock;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ClockWithAlarm extends JFrame {
    private JLabel dateLabel;
    private JLabel timeLabel;
    private Timer timer;
    private ArrayList<String> reminders;
    private LocalTime alarmTime;
    private Timer alarmTimer;
    private JButton setDateButton, setAlarmButton, cancelAlarmButton,manageRemindersButton;
    private JButton createReminderButton, cancelReminderButton;


    public ClockWithAlarm() {
            Border border = BorderFactory.createLineBorder(Color.GREEN);
            setTitle("Digital Clock And Scheduler");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 250);
            setLocationRelativeTo(null);

            JPanel contentPane = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(new Color(173, 216, 230));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            ImageIcon imageClock = new ImageIcon("C:\\Users\\Paul\\IdeaProjects\\CurreancyProj\\src\\projects2\\timer\\clock.png");
            setIconImage(imageClock.getImage());//get icon of image

            dateLabel = new JLabel();
            dateLabel.setHorizontalAlignment(SwingConstants.CENTER);//sets the clock reading to the center of page
            dateLabel.setFont(new Font("MV Boli", Font.BOLD, 20));
            dateLabel.setBorder(border);
            dateLabel.setBackground(Color.LIGHT_GRAY);

            timeLabel = new JLabel();
            timeLabel.setHorizontalAlignment(SwingConstants.CENTER);//sets the clock reading to the center of page
            timeLabel.setFont(new Font("Arial", Font.BOLD, 50));
            timeLabel.setBorder(border);
            timeLabel.setBackground(Color.LIGHT_GRAY);



            contentPane.add(dateLabel, BorderLayout.NORTH);
            contentPane.add(timeLabel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            contentPane.add(buttonPanel, BorderLayout.SOUTH);

            setDateButton = new JButton("Set Date");
            setDateButton.setFont(new Font("MV Boli", Font.BOLD, 12));
            setAlarmButton = new JButton("Set Alarm Time");
            setAlarmButton.setFont(new Font("MV Boli", Font.BOLD, 12));
            cancelAlarmButton = new JButton("Cancel Alarm");
            cancelAlarmButton.setFont(new Font("MV Boli", Font.BOLD, 12));
            createReminderButton = new JButton("Create Reminder/Schedule");
            createReminderButton.setFont(new Font("MV Boli", Font.BOLD, 12));
            cancelReminderButton = new JButton("Cancel Reminder");
            cancelReminderButton.setFont(new Font("MV Boli", Font.BOLD, 12));
            manageRemindersButton = new JButton("Manage Reminders");
            manageRemindersButton.setFont(new Font("MV Boli", Font.BOLD, 12));

            manageRemindersButton.addActionListener(e -> openRemindersManager());

            buttonPanel.add(manageRemindersButton);
            add(contentPane);

            reminders = new ArrayList<>();


            buttonPanel.add(setDateButton);
            buttonPanel.add(setAlarmButton);
            buttonPanel.add(cancelAlarmButton);
            buttonPanel.add(createReminderButton);
            buttonPanel.add(cancelReminderButton);


            add(contentPane);



            timer = new Timer(1000, e -> updateDateTime());
            timer.start();

            setDateButton.addActionListener(e -> {
                String inputDate = JOptionPane.showInputDialog(null, "Enter date (yyyy-MM-dd):");
                try {
                    LocalDate newDate = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    dateLabel.setText(newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-MM-dd format.");
                }
            });



            setAlarmButton.addActionListener(e -> {
                String inputTime = JOptionPane.showInputDialog(null, "Enter alarm time (HH:mm:ss):");
                try {
                    alarmTime = LocalTime.parse(inputTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
                    scheduleAlarm(alarmTime);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid time format. Please use HH:mm:ss format.");
                }
            });

            cancelAlarmButton.addActionListener(e -> {
                if (alarmTimer != null) {
                    alarmTimer.stop();
                    JOptionPane.showMessageDialog(null, "Alarm canceled.");
                } else {
                    JOptionPane.showMessageDialog(null, "No alarm is set.");
                }
            });

            createReminderButton.addActionListener(e -> {
                String reminder = JOptionPane.showInputDialog(null, "Enter your reminder:");
                if (reminder != null && !reminder.isEmpty()) {
                    reminders.add(reminder);
                    System.out.println("Reminder/Schedule added: " + reminder);
                }
            });

            cancelReminderButton.addActionListener(e -> {
                if (!reminders.isEmpty()) {
                    int index = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter index of the reminder to remove:"));
                    if (index >= 0 && index < reminders.size()) {
                        reminders.remove(index);
                        System.out.println("Reminder removed at index " + index);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid index!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No reminders to remove!");
                }
            });

            reminders = new ArrayList<>();
    }
    private void openRemindersManager() {
            JFrame remindersFrame = new JFrame("Reminders Manager");
            JPanel remindersPanel = new JPanel(new GridLayout(0, 1));

            // Displaying existing reminders in a list
            for (String reminder : reminders) {
                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(e -> {
                    reminders.remove(reminder);
                    remindersPanel.remove(deleteButton.getParent());
                    remindersFrame.revalidate();
                    remindersFrame.repaint();
                });

                remindersPanel.add(new JLabel(reminder));
                remindersPanel.add(deleteButton);
            }

            remindersFrame.add(new JScrollPane(remindersPanel));
            remindersFrame.pack();
            remindersFrame.setVisible(true);
    }


    private void updateDateTime() {
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();

            dateLabel.setText(currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            timeLabel.setText(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    private void scheduleAlarm(LocalTime alarmTime) {
            if (alarmTimer != null) {
                alarmTimer.stop();
            }

            LocalTime currentTime = LocalTime.now();
            long delay = LocalTime.now().until(alarmTime, java.time.temporal.ChronoUnit.MILLIS);

            if (delay < 0) {
                delay += 24 * 60 * 60 * 1000; // add 24 hours in milliseconds if the time is in the past
            }

            alarmTimer = new Timer((int) delay, e -> {
                JOptionPane.showMessageDialog(null, "Alarm! It's " + alarmTime.toString());
                alarmTimer.stop();
            });

            alarmTimer.setRepeats(false);
            alarmTimer.start();
    }

    public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                ClockWithAlarm digitalClock = new ClockWithAlarm();
                digitalClock.setVisible(true);
            });
    }


}
