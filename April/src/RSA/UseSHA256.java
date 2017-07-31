package RSA;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UseSHA256 {

	  public String SHA256(final String strText)  
	  {  
	    return SHA(strText, "SHA-256");  
	  } 
	  
	  private String SHA(final String strText, final String strType)  
	  {  
	    // ����ֵ  
	    String strResult = null;  
	  
	    // �Ƿ�����Ч�ַ���  
	    if (strText != null && strText.length() > 0)  
	    {  
	      try  
	      {  
	        // SHA ���ܿ�ʼ  
	        // �������ܶ��� ������������  
	        MessageDigest messageDigest = MessageDigest.getInstance(strType);  
	        // ����Ҫ���ܵ��ַ���  
	        messageDigest.update(strText.getBytes());  
	        // �õ� byte ��ͽ��  
	        byte byteBuffer[] = messageDigest.digest();  
	  
	        // �� byte �D�Q�� string  
	        StringBuffer strHexString = new StringBuffer();  
	        // ��v byte buffer  
	        for (int i = 0; i < byteBuffer.length; i++)  
	        {  
	          String hex = Integer.toHexString(0xff & byteBuffer[i]);  
	          if (hex.length() == 1)  
	          {  
	            strHexString.append('0');  
	          }  
	          strHexString.append(hex);  
	        }  
	        // �õ����ؽY��  
	        strResult = strHexString.toString();  
	      }  
	      catch (NoSuchAlgorithmException e)  
	      {  
	        e.printStackTrace();  
	      }  
	    }  
	  
	    return strResult;  
	  }  
	  
	 // public static void main(String args[]){
	//	  System.out.println(SHA256("456789"));
	 // }
	
}
