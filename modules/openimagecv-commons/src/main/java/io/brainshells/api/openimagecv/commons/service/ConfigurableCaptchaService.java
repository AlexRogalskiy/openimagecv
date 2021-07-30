package io.brainshells.api.openimagecv.commons.service;

import io.brainshells.api.openimagecv.commons.background.SingleColorBackgroundFactory;
import io.brainshells.api.openimagecv.commons.color.SingleColorFactory;
import io.brainshells.api.openimagecv.commons.filter.CurvesRippleFilterFactory;
import io.brainshells.api.openimagecv.commons.font.RandomFontFactory;
import io.brainshells.api.openimagecv.commons.renderer.BestFitTextRenderer;
import io.brainshells.api.openimagecv.commons.word.AdaptiveRandomWordFactory;

public class ConfigurableCaptchaService extends AbstractCaptchaService {

    public ConfigurableCaptchaService() {
        this.backgroundFactory = new SingleColorBackgroundFactory();
        this.wordFactory = new AdaptiveRandomWordFactory();
        this.fontFactory = new RandomFontFactory();
        this.textRenderer = new BestFitTextRenderer();
        this.colorFactory = new SingleColorFactory();
        this.filterFactory = new CurvesRippleFilterFactory(colorFactory);
        this.textRenderer.setLeftMargin(10);
        this.textRenderer.setRightMargin(10);
        this.width = 160;
        this.height = 70;
    }
}
