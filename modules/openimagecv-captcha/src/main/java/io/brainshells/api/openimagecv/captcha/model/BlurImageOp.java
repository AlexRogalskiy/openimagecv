package io.brainshells.api.openimagecv.captcha.model;

public class BlurImageOp extends AbstractConvolveImageOp {

    private static final float[][] matrix = {{1 / 16f, 2 / 16f, 1 / 16f},
        {2 / 16f, 4 / 16f, 2 / 16f}, {1 / 16f, 2 / 16f, 1 / 16f}};

    public BlurImageOp() {
        super(matrix);
    }
}
