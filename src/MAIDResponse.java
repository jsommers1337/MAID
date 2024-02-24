import javax.swing.*;
import java.awt.*;

public class MAIDResponse extends JDialog {
    private JPanel responseMAIDPanel;
    private JButton okayButton;
    private JLabel responseLabel;

    public MAIDResponse(JFrame owner, String chatGPTResponse) {
        super(owner);
        setTitle("MAID Response");
        setContentPane(responseMAIDPanel);
        setMinimumSize(new Dimension(750, 500));
        setModal(true);
        responseLabel.setText(chatGPTResponse);

        // displays dialogue in the center of the frame
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // implements an action listener for when the "okasy" button is clicked
        okayButton.addActionListener(e -> {
            dispose();
        });
        setVisible(true);
    }
}
