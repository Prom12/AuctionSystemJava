package rsa;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class rsa {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    
    
    private static final String PUBLIC_KEY_STRING = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDzEtRK02JSGxpd4NwmfIAjNI6dlwNWtgW1IdrzBnJnaSKQHGiC7x509pw3G2S/bOvR/5yL0QdiCltqdJmitutt/NPtZZRnOAcDLr5rvyTOx4RPYZqLlWmggXAoFGvbop4gQSbzUtTHYWt/rprT2UCmHQmW3U0Q3sGEs5VExZhnNwIDAQAB";
    private static final String PRIVATE_KEY_STRING = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAPMS1ErTYlIbGl3g3CZ8gCM0jp2XA1a2BbUh2vMGcmdpIpAcaILvHnT2nDcbZL9s69H/nIvRB2IKW2p0maK262380+1llGc4BwMuvmu/JM7HhE9hmouVaaCBcCgUa9uiniBBJvNS1Mdha3+umtPZQKYdCZbdTRDewYSzlUTFmGc3AgMBAAECgYAg/bpAmaYrnLAcc8uNkbJWlOiSbQ+v8i6eIgU0nPhkHrnpM2Hf+5J3r40OUbJ/5MmxC+90bp+A6AT2UvMTSZ50YHMC49c2U/09wj2pWJ5uFqCDr+suix6Bu3RfSLk5L+lisaJxpZv5v3vbRMgpfKG0rJ4CEGuoxWFZTUPprhjDEQJBAPNUkhJsUPnfDye+SG2KrgKnI7A8potVwcJsCpNT+4mkbdkN+7WgPnvsPdpFQRPl7mFzA6CLFZQkYyOJCefVj78CQQD/utXuzAr74zu71+Lmi83LQ7v7/2Vclsuqfiaf9bdBKjPyH5t8scalRWMjd6uOfmV4OQxyf5SEJFmFxF4JEAaJAkEA7GnMii3G9x6roNeVkyhcLKOrXv1uao3ldTp3g/DiwTaKq4qUdkC8tLYw2jLTKbwKQY/3hDHdw1MfJ1Vr1F2ADQJBAJ1T1Y+/CjbjGnm7HIXXlrkpp7ol6rcmso/mYl7grWgyzZh055S0TlCZOp+0XOAiVMy81DKQ2M7fChBoua17pXkCQDwkSMVRca5kr83wV9qfxHt+zZbSAZ3C97yAzFPmkRLwO8WHBQSV71OemCnWGmNL/pwG9cGsgcFePQCuBRlYU7g=";



    public void init(){
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            KeyPair pair = generator.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();

        } catch (Exception e) {
            System.out.println("RSA KEY : " + e.getMessage());
        }
    }

    public void printKeys(){
        System.out.println("Public key\n" + encode(publicKey.getEncoded()));
        System.out.println("Private key\n" + encode(privateKey.getEncoded()));
    }
    public void initFromStrings(){
        try {
            X509EncodedKeySpec keySpecPublic = new  X509EncodedKeySpec(decode(PUBLIC_KEY_STRING));
            PKCS8EncodedKeySpec keySpecPrivate = new  PKCS8EncodedKeySpec(decode(PRIVATE_KEY_STRING));

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            publicKey = keyFactory.generatePublic(keySpecPublic);
            privateKey = keyFactory.generatePrivate(keySpecPrivate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String message) throws Exception{

        byte[] messageToBytes = message.getBytes();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] encryptedBytes = cipher.doFinal(messageToBytes);
        return encode(encryptedBytes);
    }

    private String encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }

    public String decrypt(String encryptedMessage)throws Exception{
        byte[] encryptBytes = decode(encryptedMessage);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] decryptedMessage = cipher.doFinal(encryptBytes);
       
        return new String(decryptedMessage,"UTF8");
    }

    private byte[] decode(String data) throws Exception{
        return Base64.getDecoder().decode(data);
    }
    
    public static void main(String [] args){
        rsa rsa = new rsa();
        rsa.init();

        try{
            String encrypt = rsa.encrypt("kjhk");
            String decrypt = rsa.decrypt(encrypt);

            System.out.println("En :"+encrypt +" De " + decrypt);

            rsa.printKeys();

        }catch(Exception e){
            System.out.println("Server Error ..."+ e );
        }
    }
}
