package io.brainshells.api.openimagecv.captcha.word;

import java.util.Random;

public class RandomWordFactory implements WordFactory {

    protected String characters;
    protected int minLength;
    protected int maxLength;

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public RandomWordFactory() {
        characters = "absdegkmnopwx23456789";
        minLength = 6;
        maxLength = 6;
    }

    @Override
    public String getNextWord() {
        final Random rnd = new Random();
        final StringBuilder sb = new StringBuilder();
        final int l = minLength + (maxLength > minLength ? rnd
            .nextInt(maxLength - minLength) : 0);

        for (int i = 0; i < l; i++) {
            int j = rnd.nextInt(characters.length());
            sb.append(characters.charAt(j));
        }

        return sb.toString();
    }
}
