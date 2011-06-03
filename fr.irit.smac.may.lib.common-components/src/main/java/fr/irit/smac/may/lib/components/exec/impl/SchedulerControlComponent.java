package fr.irit.smac.may.lib.components.exec.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SchedulerControlComponent extends JPanel {

	private static final long serialVersionUID = 2828817250904194454L;

	private final SchedulerSlider speedSlider;
	private final JButton stepButton;

	public SchedulerControlComponent(AlternateStateThreadPoolExecutor pe) {
		super();
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Scheduler control"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		stepButton = new JButton("step");
		speedSlider = new SchedulerSlider(); // see the SchedulerSlider
												// inner class
		speedSlider.addChangeListener(new SchedulerSliderListener(pe,
				stepButton));
		// see the SchedulerSliderListener inner class
		this.add(speedSlider);
		this.add(stepButton);
	}

	// //////////////////////////////////////////////////////////////////////////////
	//
	// This inner class defines the slider
	//
	// The slider has four states: Pause, Step-by-step, Slow and Fast
	//
	// //////////////////////////////////////////////////////////////////////////////

	private class SchedulerSlider extends JSlider {

		private static final long serialVersionUID = -5591439208368228848L;

		public SchedulerSlider() {
			super(SwingConstants.HORIZONTAL, 1, 4, 2);

			Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
			labelTable.put(new Integer(1), new JLabel("Pause"));
			labelTable.put(new Integer(2), new JLabel("Step-by-step"));
			labelTable.put(new Integer(3), new JLabel("Slow"));
			labelTable.put(new Integer(4), new JLabel("Fast"));

			setLabelTable(labelTable);
			setMinorTickSpacing(1);
			setPaintTicks(true);
			setPaintLabels(true);

		}
	}

	// //////////////////////////////////////////////////////////////////////////////
	//
	// This inner class defines the slider listener
	//
	// When the state of the linked slider changes, the listener changes the
	// state
	// of the process accordingly. If the state of the slider is
	// set on "Step-by-step", the listener will also enables the "step"
	// button.
	// If, when enabled, the "step" button is pushed, the process will move
	// one
	// step forward
	//
	// //////////////////////////////////////////////////////////////////////////////

	private static class SchedulerSliderListener implements ChangeListener {

		private final AlternateStateThreadPoolExecutor processExe;
		private final JButton stepButton;

		public SchedulerSliderListener(AlternateStateThreadPoolExecutor pe,
				JButton b) {
			processExe = pe;
			stepButton = b;
			stepButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (stepButton.isEnabled())
						processExe.nextStep();
				}
			});

		}

		public void stateChanged(ChangeEvent e) {

			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				int state = source.getValue();
				switch (state) {
				case 1:
					processExe.pause();
					stepButton.setEnabled(false);
					break;
				case 2:
					processExe.pause();
					stepButton.setEnabled(true);
					break;
				case 3:
					processExe.goSlow();
					stepButton.setEnabled(false);
					break;
				case 4:
					processExe.goFast();
					stepButton.setEnabled(false);
					break;
				}
			}
		}

	}
}