package statistics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import main.Common;
import util.StringUtil;

//hyy
public class dataInitial {
	
	public static void getDataFromTable() {
		getSecurityCheckMethodsTable();
		getPublicMethodsInStubServiceTable();
		getCallerCalleeRelationship();		
	}
	
	//------get data from .csv files
		private static void getCallerCalleeRelationship() {
			// ʹ�ö��ļ���ʽ��ȡ���ݿ��е������ļ�
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader("CALLEDGE.csv"));
				reader.readLine();// ��һ����Ϣ��Ϊ������Ϣ�����ã������Ҫ��ע�͵�
				String line = null;
				while ((line = reader.readLine()) != null) {
					String tmp = line.trim().toString();
					String item[] = tmp.split("\\*");// CSV��ʽ�ļ�Ϊ���ŷָ����ļ���������ݶ����з�
		/*			System.out.println("--" + item[1]);
					System.out.println(item[2]);*/
					if (!statistics.commonData.callerSigMapCalleeSig.containsKey(item[1])) {
						Set<String> callees = new HashSet<>();
						callees.add(item[2]);
						statistics.commonData.callerSigMapCalleeSig.put(item[1], callees);
					} else {
						statistics.commonData.callerSigMapCalleeSig.get(item[1]).add(item[2]);
					}
													
					if (!statistics.commonData.calleeSigMapCallerSig.containsKey(item[2])) {
						Set<String> callers = new HashSet<>();
						callers.add(item[1]);
						statistics.commonData.calleeSigMapCallerSig.put(item[2], callers);
					} else {
						statistics.commonData.calleeSigMapCallerSig.get(item[2]).add(item[1]);
					}								
				}
			} catch (Exception e) {
				e.printStackTrace();
			}		
	/*		long  cnt=0;
			for(String caller:callerSigMapCalleeSig.keySet()) {
				Set<String> callee = callerSigMapCalleeSig.get(caller);
				cnt=cnt+callee.size();
			}
			System.out.println("--"+cnt);*/		
		}
		
		
		
		private static void getPublicMethodsInStubServiceTable() {
			// ʹ�ö��ļ���ʽ��ȡ���ݿ��е������ļ�
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader("PublicMethodsInStubService.csv"));
				reader.readLine();// ��һ����Ϣ��Ϊ������Ϣ�����ã������Ҫ��ע�͵�
				String line = null;
				while ((line = reader.readLine()) != null) {
					String tmp = line.trim().toString();
					String item[] = tmp.split("\\*");// CSV��ʽ�ļ�Ϊ���ŷָ����ļ���������ݶ����з�
					statistics.commonData.publicMethods.add(item[2]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("publicMethodsNum:"+statistics.commonData.publicMethods.size());
		}
		
		
		private static void getSecurityCheckMethodsTable() {
			// ʹ�ö��ļ���ʽ��ȡ���ݿ��е������ļ�
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader("SecurityCheckMethods1.csv"));
				reader.readLine();// ��һ����Ϣ��Ϊ������Ϣ�����ã������Ҫ��ע�͵�
				String line = null;
				while ((line = reader.readLine()) != null) {
					String tmp = line.trim().toString();
					String item[] = tmp.split("\\*");// CSV��ʽ�ļ�Ϊ���ŷָ����ļ���������ݶ����з�

					if (!statistics.commonData.securiyCheckTable.containsKey(item[1])) {
						ArrayList<String> Sig = new ArrayList<>();
						Sig.add(item[2]);
						statistics.commonData.securiyCheckTable.put(item[1], Sig);
					} else {
						statistics.commonData.securiyCheckTable.get(item[1]).add(item[2]);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	       
			/*		 //ʹ��jxl.jar�������ݿ��е�����xls�ļ�
			Sheet SecurityCheckMethodsTable;
	        Workbook workbook;
	        Cell cell1;      
	       try { 

	        	File inputWb= new File("SecurityCheckMethods.xls");
	        	   //t.xlsΪҪ��ȡ��excel�ļ���
	        	workbook= Workbook.getWorkbook(inputWb);             
	            //��õ�һ�����������(ecxel��sheet�ı�Ŵ�0��ʼ,0,1,2,3,....)
	        	SecurityCheckMethodsTable=workbook.getSheet(0); 
	            //��ȡ���Ͻǵĵ�Ԫ��
	            cell1=SecurityCheckMethodsTable.getCell(0,0);
	            System.out.println("���⣺"+cell1.getContents());        	       	
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
			*/
			
			
			//ֱ�Ӷ�db���ݿ��bug
			/*String tableName="SecurityCheckMethods";
			ResultSet resultSet=Common.database.select(	
					"SELECT * FROM "+tableName+";"
					);
			try {
				
				//if( !resultSet.next()) {System.out.println("1111");}
				
				String TYPE=resultSet.getString("Type");
				String METHODNAME=resultSet.getString("MethodName");
				System.out.println(TYPE+ "---" +METHODNAME );
				
				while(resultSet!=null && resultSet.next()){ // resultSet.next() Ϊfalse
					
					;
					System.out.println("oooo");
					String TYPE=resultSet.getString("Type");
					String METHODNAME=resultSet.getString("MethodName");
					System.out.println(TYPE+ "---" +METHODNAME );
					
					//int rank=resultSet.getInt("RANKING");
					//parcelableClassNameMapRanking.put(className,rank);
					
					if(!securiyCheckTable.containsKey(TYPE))
						securiyCheckTable.put(TYPE,new Set<METHODNAME>());
					securiyCheckTable.get(TYPE).addAll(methodAndValidation.validations);
				}
							
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
		}
		
		
		
		
		
		
		//----------save memory data to database------------------------
		//callerMethodSignatureMapCalleeMethodSignatures.get(sootMethod.getSignature()).add(callee.getSignature());
		private static void saveCallerCalleeRelationship() {
			createTableInDataProvider();
			for(String caller: main.Memory.callerMethodSignatureMapCalleeMethodSignatures.keySet()) {
				for(String callee:main.Memory.callerMethodSignatureMapCalleeMethodSignatures.get(caller)) {
					String value=StringUtil.sqlString(caller)+", "+ StringUtil.sqlString(callee);
					Common.database.executeUpdate(
							"INSERT INTO CallRelation (Caller,Callee) VALUES ("+value+");");	
				}
			}
		}
		
			
		private static void createTableInDataProvider(){
			String tableName="CallRelation";
			Common.database.executeUpdate(
					"CREATE TABLE IF NOT EXISTS "+tableName+" ("+
							"ID				INTEGER  PRIMARY KEY AUTOINCREMENT,"+
							"Caller		TEXT,"+
							"Callee     TEXT"+
							");"
					);
		}


}
