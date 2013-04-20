package fr.irit.smac.may.lib.components.scheduling;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SchedulingControllerGUIImpl extends	SchedulingControllerGUI {

	private final int ms;
	
	public SchedulingControllerGUIImpl(int ms) {
		this.ms = ms;
	}
	
	private void createAndShowGUI() {
		JFrame frame = new JFrame();
		frame.add(new ControlComponent());
		frame.setLayout(new FlowLayout());
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				control().pause();
			}
		});
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	protected void start() {
		super.start();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
	
	private class ControlComponent extends JPanel {
		private static final long serialVersionUID = 2828817250904194454L;

		private final SchedulerSlider speedSlider;
		private final JButton stepButton;

		public ControlComponent() {
			super();
			setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Scheduler control"),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));

			stepButton = new JButton("step");
			speedSlider = new SchedulerSlider();
			speedSlider.addChangeListener(new SchedulerSliderListener());
			this.add(speedSlider);
			this.add(stepButton);
		}

		/**
		 * This inner class defines the slider
		 * 
		 * The slider has four states: Pause, Step-by-step, Slow and Fast
		 */
		private class SchedulerSlider extends JSlider {

			private static final long serialVersionUID = -5591439208368228848L;

			public SchedulerSlider() {
				super(SwingConstants.HORIZONTAL, 1, 3, 1);

				Dictionary<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
				labelTable.put(new Integer(1), new JLabel("Pause"));
				labelTable.put(new Integer(2), new JLabel("Slow (" + ms +"msec)"));
				labelTable.put(new Integer(3), new JLabel("Fast "));

				setLabelTable(labelTable);
				setMinorTickSpacing(1);
				setPaintTicks(true);
				setPaintLabels(true);

			}
		}
		
		/**
		 * This inner class defines the slider listener
		 * 
		 * When the state of the linked slider changes, the listener changes the
		 * state of the process accordingly. If the state of the slider is
		 * set on "Step-by-step", the listener will also enables the "step" button.
		 * If, when enabled, the "step" button is pushed, the process will move 
		 * one step forward
		 * 
		 */
		private class SchedulerSliderListener implements ChangeListener {

			public SchedulerSliderListener() {
				stepButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (stepButton.isEnabled())
							control().step(false);
					}
				});

			}

			public void stateChanged(ChangeEvent e) {

				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int state = source.getValue();
					switch (state) {
					case 1:
						control().pause();
						stepButton.setEnabled(true);
						break;
					case 2:
						control().run(ms);
						stepButton.setEnabled(false);
						break;
					case 3:
						control().run(0);
						stepButton.setEnabled(false);
						break;
					}
				}
			}

		}
	}
}
