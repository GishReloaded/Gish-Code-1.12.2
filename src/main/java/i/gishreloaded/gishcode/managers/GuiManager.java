package i.gishreloaded.gishcode.managers;

import i.gishreloaded.gishcode.gui.click.ClickGui;
import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.elements.CheckButton;
import i.gishreloaded.gishcode.gui.click.elements.Dropdown;
import i.gishreloaded.gishcode.gui.click.elements.ExpandingButton;
import i.gishreloaded.gishcode.gui.click.elements.Frame;
import i.gishreloaded.gishcode.gui.click.elements.KeybindMods;
import i.gishreloaded.gishcode.gui.click.elements.Slider;
import i.gishreloaded.gishcode.gui.click.listener.CheckButtonClickListener;
import i.gishreloaded.gishcode.gui.click.listener.ComponentClickListener;
import i.gishreloaded.gishcode.gui.click.listener.SliderChangeListener;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.visual.GLUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.Value;
import i.gishreloaded.gishcode.value.types.BooleanValue;
import i.gishreloaded.gishcode.value.types.DoubleValue;
import i.gishreloaded.gishcode.value.types.IntegerValue;
import i.gishreloaded.gishcode.value.types.ModeValue;

public class GuiManager extends ClickGui {

	public void Init(int frameHeight, int frameWidth) {
		int right = GLUtils.getScreenWidth();
		int framePosX = 20;
		int framePosY = 20;

		for (HackCategory category : HackCategory.values()) {
			int hacksCount = 0;
			String name = Character.toString(category.toString().toLowerCase().charAt(0)).toUpperCase()
					+ category.toString().toLowerCase().substring(1);
			Frame frame = new Frame(framePosX, framePosY, frameWidth, frameHeight, name);

			for (final Hack hack : HackManager.getHacks()) {
				if (hack.getCategory() == category) {
					ExpandingButton expandingButton = new ExpandingButton(0, 0, frameWidth, 14, frame, hack.getName(),
							hack) {
						@Override
						public void onUpdate() {
							setEnabled(this.hack.isToggled());
						}
					};
					expandingButton.addListner(new ComponentClickListener() {
						@Override
						public void onComponenetClick(Component component, int button) {
							hack.toggle();
						}
					});
					expandingButton.setEnabled(hack.isToggled());

					if (!hack.getValues().isEmpty()) {
						for (Value value : hack.getValues()) {
							if (value instanceof BooleanValue) {
								final BooleanValue booleanValue = (BooleanValue) value;
								CheckButton button = new CheckButton(0, 0, expandingButton.getDimension().width, 14,
										expandingButton, booleanValue.getName(), booleanValue.getValue(), null);
								button.addListeners(new CheckButtonClickListener() {

									@Override
									public void onCheckButtonClick(CheckButton checkButton) {
										for (Value value1 : hack.getValues()) {
											if (value1.getName().equals(booleanValue.getName())) {
												value1.setValue(checkButton.isEnabled());
											}
										}
									}

								});
								expandingButton.addComponent(button);

							} else if (value instanceof DoubleValue) {
								DoubleValue doubleValue = (DoubleValue) value;
								Slider slider = new Slider(
										doubleValue.getName(),
										doubleValue.getMin(),
										doubleValue.getMax(),
										doubleValue.getValue(),
										expandingButton);
								slider.addListener(new SliderChangeListener() {

									@Override
									public void onSliderChange(Slider slider) {
										for (Value value1 : hack.getValues()) {
											if (value1.getName().equals(value.getName())) {
												value1.setValue(slider.getValue());
											}
										}
									}
								});
								expandingButton.addComponent(slider);

							} else if (value instanceof IntegerValue) {
								IntegerValue integerValue = (IntegerValue) value;
								Slider slider = new Slider(
										integerValue.getName(),
										integerValue.getMin(),
										integerValue.getMax(),
										integerValue.getValue(),
										expandingButton);
								slider.addListener(new SliderChangeListener() {

									@Override
									public void onSliderChange(Slider slider) {
										for (Value value1 : hack.getValues()) {
											if (value1.getName().equals(value.getName())) {
												value1.setValue(slider.getValue());
											}
										}
									}
								});
								expandingButton.addComponent(slider);

							} else if (value instanceof ModeValue) {
								Dropdown dropdown = new Dropdown(0, 0, frameWidth, 14, frame, value.getName());

								final ModeValue modeValue = (ModeValue) value;

								for (Mode mode : modeValue.getModes()) {
									CheckButton button = new CheckButton(0, 0, expandingButton.getDimension().width, 14,
											expandingButton, mode.getName(), mode.isToggled(), modeValue);

									button.addListeners(new CheckButtonClickListener() {
										@Override
										public void onCheckButtonClick(CheckButton checkButton) {
											for (Mode mode1 : modeValue.getModes()) {
												if (mode1.getName().equals(mode.getName())) {
													mode1.setToggled(checkButton.isEnabled());
												}
											}
										}
									});
									dropdown.addComponent(button);
								}
								expandingButton.addComponent(dropdown);
							}
						}
					}
					KeybindMods keybind = new KeybindMods(0, 0, 8, 14, expandingButton, hack);
					expandingButton.addComponent(keybind);
					frame.addComponent(expandingButton);
					hacksCount++;
				}
			}

			if (framePosX + frameWidth + 10 < right) {
				framePosX += frameWidth + 10;
			} else {
				framePosX = 20;
				framePosY += 60;
			}

			frame.setMaximizible(true);
			frame.setPinnable(true);
			this.addFrame(frame);
		}
		if (!FileManager.CLICKGUI.exists())
			FileManager.saveClickGui();
		else
			FileManager.loadClickGui();
	}
}
