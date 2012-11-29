package searchEngine;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.UIManager;

@SuppressWarnings("unchecked")
public class JSearchBar extends JComboBox<SearchEngine>{

	private static final long serialVersionUID = -2572118297350048178L;
	private static final String uiClassID = "SearchBarComboBoxUI";
    @Override public String getUIClassID() {
        return uiClassID;
    }
    @Override public SearchBarComboBoxUI getUI() {
        return (SearchBarComboBoxUI)ui;
    }
    
    @Override public void updateUI() {
        if(UIManager.get(getUIClassID())!=null) {
            setUI((SearchBarComboBoxUI)UIManager.getUI(this));
        }else{
            setUI(new BasicSearchBarComboBoxUI());
        }
        UIManager.put("ComboBox.font", getFont()); //XXX: ???
//        JButton arrowButton = (JButton)getComponent(0);
        SearchEngine se = (SearchEngine)getItemAt(0);
 //       if(se!=null) arrowButton.setIcon(se.favicon);
    }
    public JSearchBar() {
        super();
        setModel(new DefaultComboBoxModel<SearchEngine>());
        init();
    }
    public JSearchBar(ComboBoxModel<SearchEngine> aModel) {
        super();
        setModel(aModel);
        init();
    }
    public JSearchBar(final Object[] items) {
        super();
        setModel(new DefaultComboBoxModel<SearchEngine>((SearchEngine[]) items));
        init();
    }
    public JSearchBar(java.util.Vector<?> items) {
        super();
        setModel(new DefaultComboBoxModel(items));
        init();
    }
    private void init() {
        installAncestorListener();
        updateUI();
    }
    @Override protected void processFocusEvent(java.awt.event.FocusEvent e) {
        System.out.println("processFocusEvent");
    }
}