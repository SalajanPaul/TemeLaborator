import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentInfoApp {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Student Info Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);


        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);


        JLabel nameLabel = new JLabel("Nume:");
        JTextField nameField = new JTextField(15);
        nameField.setPreferredSize(new Dimension(100, 20));
        nameField.setMinimumSize(new Dimension(100, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);


        JLabel specLabel = new JLabel("Spec:");
        JRadioButton miButton = new JRadioButton("MI");
        JRadioButton infoButton = new JRadioButton("Info");
        JRadioButton ieButton = new JRadioButton("IE");
        ButtonGroup specGroup = new ButtonGroup();
        specGroup.add(miButton);
        specGroup.add(infoButton);
        specGroup.add(ieButton);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(specLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(miButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(infoButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(ieButton, gbc);


        JCheckBox bursierCheckBox = new JCheckBox("Bursier");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(bursierCheckBox, gbc);


        JLabel anStudiuLabel = new JLabel("An studiu:");
        JComboBox<Integer> anStudiuComboBox = new JComboBox<>(new Integer[] {1, 2, 3, 4});
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(anStudiuLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(anStudiuComboBox, gbc);


        JButton displayButton = new JButton("Afișează datele");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(displayButton, gbc);


        JTextArea textArea = new JTextArea(10, 30);


        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 15;
        panel.add(scrollPane, gbc);


        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String spec = miButton.isSelected() ? "MI" :
                        infoButton.isSelected() ? "Info" : "IE";
                String bursier = bursierCheckBox.isSelected() ? "Da" : "Nu";
                int anStudiu = (int) anStudiuComboBox.getSelectedItem();

                String output = "Nume: " + name + "\n" +
                        "Specializarea: " + spec + "\n" +
                        "Bursier: " + bursier + "\n" +
                        "An studiu: " + anStudiu;

                textArea.setText(output);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
