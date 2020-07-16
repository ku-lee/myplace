package com.myplace.member.domain;

import com.myplace.member.exception.CryptException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.thymeleaf.util.StringUtils;

import javax.persistence.Embeddable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.UUID;

@Slf4j
@Embeddable
@NoArgsConstructor
public class CryptPassword {
    private String cryptPassword;
    private String salt;

    public CryptPassword(final String plainPassword) throws CryptException{
        if(StringUtils.isEmpty(plainPassword)){
            throw new IllegalArgumentException("password is empty");
        }

        salt = makeSalt();
        cryptPassword = makeCryptText(salt, plainPassword);
        log.info("created >"+cryptPassword+" salt>"+salt);
    }

    private String makeSalt(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
//    private String makeHash(String salt){
//        String hashKey ="";
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(salt.getBytes());
//
//            hashKey = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
//        }catch(NoSuchAlgorithmException e){
//           log.error("crypt exception occured! ", e);
//           throw new CryptException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("internal server error"));
//        }
//
//        return hashKey;
//    }

    //TODO : 단방향 암호화 방식으로 변경
    private String makeCryptText(String key, String plainText) throws CryptException{
        String cryptText = "";
        try{
//            final SecretKeySpec secret = new SecretKeySpec(key.getBytes(), "AES");
//            final Cipher encryptCiper = Cipher.getInstance("AES");

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            md.update(plainText.getBytes());

            cryptText = String.format("%0128x", new BigInteger(1, md.digest()));
//            encryptCiper.init(Cipher.ENCRYPT_MODE, secret);
//            cryptText = new String(encryptCiper.doFinal(plainText.getBytes()));
        }catch(Exception e){
           log.error("crypt exception occured! ", e);
           throw new CryptException();
        }

        return cryptText;
    }

    public byte[] stringToByte(String input) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);

        } else {
            return Base64.encodeBase64(input.getBytes());
        }
    }

    public boolean validate(String plainPassword) throws CryptException {
        String inputCryptPassword = makeCryptText(this.salt, plainPassword);
        return this.cryptPassword.equals(inputCryptPassword);
    }


}
