package clock;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DigitalClock extends JFrame {
    private JLabel dateLabel,timeLabel;
    private  JButton colorButton;
    private Timer timer;

    public DigitalClock() {
        Border border = BorderFactory.createLineBorder(Color.GREEN);
        setTitle("Digital Clock And Date");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);


        JPanel contentPane = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255,255,0));//red,green,blue==yellow
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        //getContentPane().setBackground(new Color(255,255,0));//red,green,blue==yellow
        setVisible(true);


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

        updateTime();

        ImageIcon imageClock = new ImageIcon("C:\\Users\\Paul\\IdeaProjects\\CurreancyProj\\src\\projects2\\timer\\clock.png");
        setIconImage(imageClock.getImage());//get icon of image

        colorButton = new JButton("Change Color");
        colorButton.setFont(new Font("MV Boli", Font.BOLD, 16));
        colorButton.setBorder(border);
        colorButton.setBackground(Color.LIGHT_GRAY);

        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null, "Choose Clock Color", Color.BLACK);
                dateLabel.setForeground(newColor);
            }
        });
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null, "Choose Clock Color", Color.BLACK);
                timeLabel.setForeground(newColor);
            }
        });


        contentPane.add(dateLabel, BorderLayout.NORTH);
        contentPane.add(timeLabel, BorderLayout.CENTER);
        contentPane.add(colorButton, BorderLayout.SOUTH);

        setContentPane(contentPane);


        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();
    }

    //create a method that updates the local time and converts it to a string using date time formatter
    private void updateTime() {
        LocalTime currentTime = LocalTime.now();
        //String time = String.format("%02d:%02d:%02d", currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
        //timeLabel.setText(time);
        LocalDate currentDate = LocalDate.now();


        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String time = currentTime.format(timeFormatter);
        String date = currentDate.format(dateFormatter);

        String dateTime = date + "\n" + time;
        dateLabel.setText(date);
        timeLabel.setText(time);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DigitalClock digitalClock = new DigitalClock();
            digitalClock.setVisible(true);
        });
    }
}

