package xyz.damt.util;

public enum DefaultTexture {

    MINE_ENABLED("50f4ef7d6c5d8da3618275223bdca147c9cfaac77d37e8ae81d757a593676ac2"),
    MINE_DISABLED("b254e2426d2acc84c5c658869a86d7a1cc9a4f604866aae467b5b49665404f5d"),
    ARROW_LEFT("37aee9a75bf0df7897183015cca0b2a7d755c63388ff01752d5f4419fc645"),
    ARROW_RIGHT("682ad1b9cb4dd21259c0d75aa315ff389c3cef752be3949338164bac84a96e");

    String texture;

    DefaultTexture(String texture) {
        this.texture = texture;
    }

    public String getTexture() {
        return texture;
    }
}
