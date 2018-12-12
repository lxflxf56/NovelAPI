package cn.stormzi.novelapi.facade.security;

public interface Encryption {
    String sign(String text);
    boolean verify(String originText,String nowText);
}
