package io.brainshells.api.openimagecv.captcha2.general;

import java.util.Random;

public abstract class AbstractMathCaptcha extends BaseCaptcha {

    /**
     * 生成随机加减验证码
     *
     * @return 验证码字符数组
     */
    @Override
    protected char[] alphas() {
        // 生成随机类
        Random random = new Random();
        char[] cs = new char[4];
        int rand0 = random.nextInt(10);
        if (rand0 == 0) {
            rand0 = 1;
        }
        int rand1 = random.nextInt(10);
        boolean rand2 = random.nextBoolean();
        int rand3 = random.nextInt(10);
        cs[0] = (char) ('0' + rand0);
        cs[1] = (char) ('0' + rand1);
        cs[2] = rand2 ? '+' : '-';
        cs[3] = (char) ('0' + rand3);

        int num1 = rand0 * 10 + rand1;
        int result = rand2 ? num1 + rand3 : num1 - rand3;
        chars = String.valueOf(result);
        return cs;
    }
}
