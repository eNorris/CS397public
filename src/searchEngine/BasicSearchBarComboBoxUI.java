package searchEngine;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class BasicSearchBarComboBoxUI extends SearchBarComboBoxUI{
	public static javax.swing.plaf.ComponentUI createUI(JComponent c) {
		return new BasicSearchBarComboBoxUI();
	}

	@Override protected void installDefaults() {
		super.installDefaults();
		comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
	}
	@Override protected void installListeners() {
		super.installListeners();
		if((popupMenuListener = createPopupMenuListener()) != null) {
			comboBox.addPopupMenuListener(popupMenuListener);
		}
	}
	@Override protected void uninstallListeners() {
		super.installListeners();
		if(popupMenuListener != null) {
			comboBox.removePopupMenuListener(popupMenuListener);
		}
	}
	protected PopupMenuListener popupMenuListener;
	protected PopupMenuListener createPopupMenuListener() {
		if(popupMenuListener == null) {
			popupMenuListener = new PopupMenuListener() {
				private String str;
				@Override 
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					@SuppressWarnings("unchecked")
					JComboBox<String> combo = (JComboBox<String>)e.getSource();
					str = combo.getEditor().getItem().toString();
				}
				@Override 
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					Object o = listBox.getSelectedValue();
					if(o!=null && o instanceof SearchEngine) {
						SearchEngine se = (SearchEngine) o;
						arrowButton.setIcon(se.favicon);
					}
					EventQueue.invokeLater(new Runnable() {
						@Override 
						public void run() {
							comboBox.getEditor().setItem(str);
						}
					});
				}
				@Override public void popupMenuCanceled(PopupMenuEvent e) {}
			};
		}
		return popupMenuListener;
	}

	@Override protected KeyListener createKeyListener() {
		if(keyListener==null) {
			keyListener = new KeyAdapter() {};
		}
		return keyListener;
	}
	protected Action loupeAction = new AbstractAction() {
		private static final long serialVersionUID = 1444162763606468050L;

		@Override public void actionPerformed(ActionEvent e) {
			comboBox.setPopupVisible(false);
			Object o = listBox.getSelectedValue();
			if(o==null) o = comboBox.getItemAt(0);
			System.out.println(o + ": " +comboBox.getEditor().getItem());
		}
	};
	@Override protected void configureEditor() {
		editor.setEnabled(comboBox.isEnabled());
		editor.setFocusable(comboBox.isFocusable());
		editor.setFont(comboBox.getFont());
		if(editor instanceof JComponent) {
			((JComponent)editor).setInheritsPopupMenu(true);
		}
		comboBox.configureEditor(comboBox.getEditor(),comboBox.getSelectedItem());
		editor.addPropertyChangeListener(propertyChangeListener);

		((JComponent)editor).setBorder(BorderFactory.createEmptyBorder(0,4,0,0));
		((JComponent)editor).getActionMap().put("loupe", loupeAction);
		InputMap im = ((JComponent)editor).getInputMap(JComponent.WHEN_FOCUSED);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "loupe");
	}
	
	@Override protected JButton createArrowButton() {
		return new TriangleArrowButton();
	}
	
	@Override public void configureArrowButton() {
		super.configureArrowButton();
		if(arrowButton != null) {
			arrowButton.setBackground(UIManager.getColor("Panel.background"));
			arrowButton.setHorizontalAlignment(SwingConstants.LEFT);
			arrowButton.setOpaque(true);
			arrowButton.setFocusPainted(false);
			arrowButton.setContentAreaFilled(false);
			arrowButton.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createMatteBorder(0,0,0,1, new Color(127,157,185)),
					BorderFactory.createEmptyBorder(1,1,1,1)));
		}
	}
	@Override protected void installComponents() {
		arrowButton = createArrowButton();
		comboBox.add(arrowButton);
		if(arrowButton != null) {
			configureArrowButton();
		}
		loupeButton = createLoupeButton();
		comboBox.add(loupeButton);
		if(loupeButton != null) {
			configureLoupeButton();
		}
		addEditor();
		comboBox.add(currentValuePane);
	}
	@Override protected void uninstallComponents() {
		if(loupeButton != null) {
			unconfigureLoupeButton();
		}
		loupeButton = null;
		super.uninstallComponents();
	}
	
	protected JButton loupeButton;
	protected JButton createLoupeButton() {
		JButton button = new JButton(loupeAction);
		ImageIcon loupe = new ImageIcon(getClass().getResource("loupe.png"));
		button.setIcon(loupe);
		button.setRolloverIcon(makeRolloverIcon(loupe));
		return button;
	}
	public void configureLoupeButton() {
		if(loupeButton != null) {
			loupeButton.setName("ComboBox.loupeButton");
			loupeButton.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
			loupeButton.setEnabled(comboBox.isEnabled());
			loupeButton.setFocusable(comboBox.isFocusable());
			loupeButton.setOpaque(false);
			loupeButton.setRequestFocusEnabled(false);
			loupeButton.setFocusPainted(false);
			loupeButton.setContentAreaFilled(false);
			loupeButton.resetKeyboardActions();
			loupeButton.setInheritsPopupMenu(true);
		}
	}
	public void unconfigureLoupeButton() {
		if(loupeButton != null) {
			loupeButton.setAction(null);
		}
	}
	@Override protected ListCellRenderer createRenderer() {
		return new DefaultListCellRenderer() {
			private static final long serialVersionUID = 780659837505625347L;

			@Override public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel l = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if(value instanceof SearchEngine) {
					SearchEngine se = (SearchEngine)value;
					l.setIcon(se.favicon);
				}
				return l;
			}
		};
	}
	@Override protected LayoutManager createLayoutManager() {
		return new LayoutManager() {
			@Override public void addLayoutComponent(String name, Component comp) {}
			@Override public void removeLayoutComponent(Component comp) {}
			@Override public Dimension preferredLayoutSize(Container parent) {
				return parent.getPreferredSize();
			}
			@Override public Dimension minimumLayoutSize(Container parent) {
				return parent.getMinimumSize();
			}
			@Override public void layoutContainer(Container parent) {
				if(!(parent instanceof JComboBox)) return;
				JComboBox cb     = (JComboBox)parent;
				int width        = cb.getWidth();
				int height       = cb.getHeight();
				Insets insets    = cb.getInsets();
				int buttonHeight = height - insets.top - insets.bottom;
				int buttonWidth  = buttonHeight;
				int loupeWidth   = buttonHeight;

				JButton arrowButton = (JButton)cb.getComponent(0);
				if(arrowButton != null) {
					Insets arrowInsets = arrowButton.getInsets();
					buttonWidth = arrowButton.getPreferredSize().width + arrowInsets.left + arrowInsets.right;
					arrowButton.setBounds(insets.left, insets.top, buttonWidth, buttonHeight);
				}
				JButton loupeButton = null;
				for(Component c: cb.getComponents()) {
					if("ComboBox.loupeButton".equals(c.getName())) {
						loupeButton = (JButton)c;
						break;
					}
				}

				if(loupeButton != null) {
					Insets loupeInsets = loupeButton.getInsets();
					loupeButton.setBounds(width - insets.right - loupeWidth, insets.top, loupeWidth, buttonHeight);
				}
				JTextField editor = (JTextField)cb.getEditor().getEditorComponent();
				if(editor != null) {
					editor.setBounds(insets.left + buttonWidth, insets.top,
							width  - insets.left - insets.right - buttonWidth - loupeWidth,
							height - insets.top  - insets.bottom);
				}
			}
		};
	}
	private static Icon makeRolloverIcon(Icon srcIcon) {
		RescaleOp op = new RescaleOp(
				new float[] { 1.2f,1.2f,1.2f,1.0f },
				new float[] { 0f,0f,0f,0f }, null);
		BufferedImage img = new BufferedImage(
				srcIcon.getIconWidth(), srcIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		srcIcon.paintIcon(null, g, 0, 0);
		g.dispose();
		return new ImageIcon(op.filter(img, null));
	}
	static class TriangleIcon implements Icon {
		@Override public void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setPaint(Color.GRAY);
			g2.translate(x,y);
			g2.drawLine( 2, 3, 6, 3 );
			g2.drawLine( 3, 4, 5, 4 );
			g2.drawLine( 4, 5, 4, 5 );
			g2.translate(-x,-y);
		}
		@Override public int getIconWidth()  { return 9; }
		@Override public int getIconHeight() { return 9; }
	}
	static class TriangleArrowButton extends JButton {

		private static final long serialVersionUID = -681123842888291709L;
		private transient Icon triangleIcon = new TriangleIcon();
		@Override public void setIcon(Icon favicon) {
			super.setIcon(favicon);
			if(favicon!=null) setRolloverIcon(makeRolloverIcon(favicon));
		}
		@Override protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if(getModel().isArmed()) {
				g2.setColor(new Color(220,220,220));
			}else if(isRolloverEnabled() && getModel().isRollover()) {
				g2.setColor(new Color(220,220,220));
			}else if(hasFocus()) {
				g2.setColor(new Color(220,220,220));
			}else{
				g2.setColor(getBackground());
			}
			Rectangle r = getBounds();
			r.grow(1,1);
			g2.fill(r);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			g2.setColor(getBackground());
			super.paintComponent(g);

			Insets i = getInsets();
			int x = r.width-i.right-triangleIcon.getIconWidth()-2;
			int y = i.top+(r.height-i.top-i.bottom-triangleIcon.getIconHeight())/2;
			triangleIcon.paintIcon(this, g, x, y);
		}
		@Override public Dimension getPreferredSize() {
			Insets i = getInsets();
			Icon favicon = getIcon();
			int fw = (favicon!=null)?favicon.getIconWidth():16;
			int w  = fw + triangleIcon.getIconWidth() + i.left + i.right;
			return new Dimension(w, w);
		}
		@Override public void setBorder(Border border) {
			if(border instanceof CompoundBorder) {
				super.setBorder(border);
			}
		}
	}
}