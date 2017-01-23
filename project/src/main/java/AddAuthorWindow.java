import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;

/**
 * Created by christine on 20.01.17.
 */
public class AddAuthorWindow {
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;

    public AddAuthorWindow() {
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

    }

}
