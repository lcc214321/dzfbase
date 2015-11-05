package dzf;

import java.util.Arrays;

//CREATE OR REPLACE AND COMPILE JAVA SOURCE NAMED Corp AS
//import java.util.Arrays;
//
//public class Corp {
//	private static final char[] CORPKEYS={'!','#','$','&','*','+','-','.',
//		'/','0','1','2','3','4','5','6','7','8','9',':','<','>','?','@',
//		'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
//		'Q','R','S','T','U','V','W','X','Y','Z','[',']','^','a','b',
//		'c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
//		's','t','u','v','w','x','y','z','{','|','}'};
//	private static final char[] CORPKEYS1={'0','1','2','3','4','5','6','7','8','9',
//		'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
//		'Q','R','S','T','U','V','W','X','Y','Z','a','b',
//		'c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
//		's','t','u','v','w','x','y','z'};
//	public Corp() {
//		// TODO Auto-generated constructor stub
//	}
//	public static String NewId(String oid){
//		oid=oid.trim();
//		String s1=oid.substring(0,4);
//		s1=NewCorp(s1);
//		s1=s1+"00"+oid.substring(4);
//		return s1;
//	}
//	public static String NewCorp(String oldCorp){
//		oldCorp=oldCorp.trim();
//		int len=CORPKEYS.length;
//		long v=0;
//		
//			v=Arrays.binarySearch(CORPKEYS, oldCorp.charAt(0))-9; 
//			v=v*len+ Arrays.binarySearch(CORPKEYS, oldCorp.charAt(1))-9; 
//			v=v*len+ Arrays.binarySearch(CORPKEYS, oldCorp.charAt(2))-9;
//			v=v*len+ Arrays.binarySearch(CORPKEYS, oldCorp.charAt(3))-9; 
//		
//		len=CORPKEYS1.length;
//		long v1=(int) (v%len);
//		v=(int) (v/len);
//		String s="";
//		while(v1>0||v>0){
//		s=String.valueOf(CORPKEYS1[(int) v1])+s;
//		//v1=(int) (v/len);
//		v1=(int) (v%len);
//		v=(int) (v/len);
//		}
//		len=6-s.length();
//		for(int i=0;i<len;i++){
//			s='0'+s;
//		}
//		return s;
//	}
//		
//}
//
//CREATE OR REPLACE FUNCTION NewCorp(r IN char) RETURN char AS
//LANGUAGE JAVA NAME 'Corp.NewCorp(java.lang.String) return java.lang.String';
//CREATE OR REPLACE FUNCTION NewId(r IN char) RETURN char AS
//LANGUAGE JAVA NAME 'Corp.NewId(java.lang.String) return java.lang.String';
public class Corp {
	private static final char[] CORPKEYS={'!','#','$','&','*','+','-','.',
		'/','0','1','2','3','4','5','6','7','8','9',':','<','>','?','@',
		'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
		'Q','R','S','T','U','V','W','X','Y','Z','[',']','^','a','b',
		'c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
		's','t','u','v','w','x','y','z','{','|','}'};
	private static final char[] CORPKEYS1={'0','1','2','3','4','5','6','7','8','9',
		'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
		'Q','R','S','T','U','V','W','X','Y','Z','a','b',
		'c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
		's','t','u','v','w','x','y','z'};
	public Corp() {
		// TODO Auto-generated constructor stub
	}
	public static String NewId(String oid){
		oid=oid.trim();
		String s1=oid.substring(0,4);
		s1=NewCorp(s1);
		s1=s1+"00"+oid.substring(4);
		return s1;
	}
	public static String NewCorp(String oldCorp){
		oldCorp=oldCorp.trim();
		int len=CORPKEYS.length;
		long v=0;
		
			v=Arrays.binarySearch(CORPKEYS, oldCorp.charAt(0))-9; 
			v=v*len+ Arrays.binarySearch(CORPKEYS, oldCorp.charAt(1))-9; 
			v=v*len+ Arrays.binarySearch(CORPKEYS, oldCorp.charAt(2))-9;
			v=v*len+ Arrays.binarySearch(CORPKEYS, oldCorp.charAt(3))-9; 
		
		len=CORPKEYS1.length;
		long v1=(int) (v%len);
		v=(int) (v/len);
		String s="";
		while(v1>0||v>0){
		s=String.valueOf(CORPKEYS1[(int) v1])+s;
		//v1=(int) (v/len);
		v1=(int) (v%len);
		v=(int) (v/len);
		}
		len=6-s.length();
		for(int i=0;i<len;i++){
			s='0'+s;
		}
		return s;
	}
		
}
