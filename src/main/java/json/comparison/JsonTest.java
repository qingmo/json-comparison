/**
 * 
 */
package json.comparison;
import com.alibaba.fastjson.JSON;

import json.comparison.deserialize.FastJSONReplacementDeserialize;
import json.comparison.deserialize.FastjsonDeserialize;
import json.comparison.deserialize.GsonDeserialize;
import json.comparison.deserialize.JacksonDeserialize;
import json.comparison.deserialize.JacksonReplaceFastjsonDeserialize;
import json.comparison.entity.ComplexEntity;
import json.comparison.entity.SimpleEntity;
import json.comparison.serialize.FastJSONReplacementSerialize;
import json.comparison.serialize.FastjsonSerialize;
import json.comparison.serialize.GsonSerialize;
import json.comparison.serialize.JacksonReplaceFastjsonSerialize;
import json.comparison.serialize.JacksonSerialize;



/**  
* @ClassName: JsonTest  
* @Description: json序列化测试类，测试json包括fastjson、jackson、gson，测试包括序列化大对象和单个对象，
* 测试对象包括复杂对象（多层嵌套）、基础对象，以及基础json和复杂json，
* @author zysrxx  
* @date 2020年8月16日    
*/
public class JsonTest {
	/**
	 * 样本数量 如果是BIG_DATA 是list 表示list size，如果是object表示产生object数量
	 */
	private static  int ENTITY_NUM = 0;
	/**
	 * 样本实体类型 sample 基础对象,complex 复杂对象,jsonsample 基础json对象,jsoncomplex 复杂json对象
	 */
	private static  String ENTITY_TYPE = "";
	/**
	 * 样本数据list 表示大对象,object 单个对象
	 */
	private static  String BIG_DATA = "";
	/**
	 * 序列化类型，serialize 序列化 deserialize  反序列化
	 */
	private static  String SERIALIZE_TYPE = "";
	/**
	 * 序列化标记
	 */
	private static String SERIALIZE = "serialize";
	/**
	 * 各json序列化处理类
	 */
	private  JsonSerialize fastjsonSerialize = new FastjsonSerialize();
	private  JsonSerialize jacksonSerialize = new JacksonSerialize();
	private  JsonSerialize gsonSerialize = new GsonSerialize();
	private  JsonSerialize mySerialize = new FastJSONReplacementSerialize();
	private  JsonSerialize replaceSerialize = new JacksonReplaceFastjsonSerialize();
	/**
	 * 各json反序列化处理类
	 */
	private  JsonDeserialize fastjsonDeserialize = new FastjsonDeserialize();
	private  JsonDeserialize jacksonDeserialize = new JacksonDeserialize();
	private  JsonDeserialize gsonDeserialize = new GsonDeserialize();
	private  JsonDeserialize myDeserialize = new FastJSONReplacementDeserialize();
	private  JsonDeserialize replaceDeserialize = new JacksonReplaceFastjsonDeserialize();
	public static void main(String[] args) throws Exception {
		
		if(args.length < 4){
			System.out.println(" four args,serializeType entityType bigData entityNum");
			System.exit(0);
		}
		SERIALIZE_TYPE = args[0];
		ENTITY_TYPE = args[1];
		BIG_DATA = args[2];
		ENTITY_NUM = Integer.valueOf(args[3]);
		JsonTest jsonTest = new JsonTest();
		if(SERIALIZE.equals(SERIALIZE_TYPE)){
			//序列化预热，避免json框架初始化耗时影响处理时间
			jsonTest.serializePreHeat();
			jsonTest.jsonSerializeTest();
		}else{
			//反序列化预热，避免json框架初始化耗时影响处理时间
			jsonTest.deserializePreHeat();
			jsonTest.jsonDeserialize();
		}
		System.out.println("===============");
	}
	/**
	 * 
	* jsonSerializeTest  json序列化测试方法
	* @param @throws Exception    参数  
	* @return void    返回类型  
	* @throws Exception
	 */
	public void jsonSerializeTest() throws Exception{
		//序列化list样本数据
		Object entity = null;
		long fastjsonSum = 0;
		long jacksonSum = 0;
		long gsonSum = 0;
		long mySum = 0;
		long replaceSum = 0;
		if("list".equals(BIG_DATA)){

			if("sample".equals(ENTITY_TYPE)){
				entity = SimpleDataBuilder.getBaseSampleList(ENTITY_NUM);
			}else if("complex".equals(ENTITY_TYPE)){
				entity = SimpleDataBuilder.getComplexList(ENTITY_NUM);
			}else if("jsonsample".equals(ENTITY_TYPE)){
				entity = SimpleDataBuilder.getBaseJsonList(ENTITY_NUM);
			}else {
				entity = SimpleDataBuilder.getComplexJsonList(ENTITY_NUM);
			}
			long startTime = System.nanoTime();
			fastjsonSerialize.serialize(entity);
			fastjsonSum += (System.nanoTime()-startTime);
			startTime = System.nanoTime();
			jacksonSerialize.serialize(entity);
			jacksonSum += (System.nanoTime()-startTime);
			startTime = System.nanoTime();
			gsonSerialize.serialize(entity);
			gsonSum += (System.nanoTime()-startTime);
			startTime = System.nanoTime();
			mySerialize.serialize(entity);
			mySum += (System.nanoTime()-startTime);
			startTime = System.nanoTime();
			replaceSerialize.serialize(entity);
			replaceSum += (System.nanoTime()-startTime);

			System.out.println("serialize,entityType:"+ENTITY_TYPE+",bigData:"+BIG_DATA+",entityNum:"+ENTITY_NUM);
			System.out.println("tool\t\t\t\tttime_cost");
			System.out.printf("%s\t\t\t%d%n", "fastjson"+ENTITY_NUM ,fastjsonSum);
			System.out.printf("%s\t\t\t\t%d%n", "jackson"+ENTITY_NUM ,jacksonSum);
			System.out.printf("%s\t\t\t\t%d%n", "gson" +ENTITY_NUM,gsonSum);
			System.out.printf("%s\t\t%d%n", "FastjsonReplacement" +ENTITY_NUM,mySum);
			System.out.printf("%s\t\t%d%n", "JacksonReplaceFastjson" +ENTITY_NUM,replaceSum);

		}else{
			for(int i=0;i< ENTITY_NUM;i++){
				if("sample".equals(ENTITY_TYPE)){
					entity = SimpleDataBuilder.getBaseSampleEntity();
				}else if("complex".equals(ENTITY_TYPE)){
					entity = SimpleDataBuilder.getComplexEntity();
				}else if("jsonsample".equals(ENTITY_TYPE)){
					entity = SimpleDataBuilder.getBaseJson();
				}else {
					entity = SimpleDataBuilder.getComplexJson();
				}

				long startTime = System.nanoTime();
				fastjsonSerialize.serialize(entity);
				fastjsonSum += (System.nanoTime()-startTime);

				startTime = System.nanoTime();
				jacksonSerialize.serialize(entity);
				jacksonSum += (System.nanoTime()-startTime);

				startTime = System.nanoTime();
				gsonSerialize.serialize(entity);
				gsonSum += (System.nanoTime()-startTime);
				startTime = System.nanoTime();
				mySerialize.serialize(entity);
				mySum += (System.nanoTime()-startTime);
				startTime = System.nanoTime();
				replaceSerialize.serialize(entity);
				replaceSum += (System.nanoTime()-startTime);

			}
			System.out.println("serialize,entityType:"+ENTITY_TYPE+",bigData:"+BIG_DATA+",entityNum:"+ENTITY_NUM);
			System.out.println("tool\t\t\t\tttime_cost");
			System.out.printf("%s\t\t\t%d%n", "fastjson"+ENTITY_NUM ,fastjsonSum);
			System.out.printf("%s\t\t\t\t%d%n", "jackson"+ENTITY_NUM ,jacksonSum);
			System.out.printf("%s\t\t\t\t%d%n", "gson" +ENTITY_NUM,gsonSum);
			System.out.printf("%s\t\t%d%n", "FastjsonReplacement" +ENTITY_NUM,mySum);
			System.out.printf("%s\t\t%d%n", "JacksonReplaceFastjson" +ENTITY_NUM,replaceSum);
		}
	}
	
	public void jsonDeserialize() throws Exception {
		Object entity = null;
		Class<?> clazz = null;
		long fastjsonSum = 0;
		long jacksonSum = 0;
		long gsonSum = 0;
		long mySum = 0;
		long replaceSum = 0;
		if("list".equals(BIG_DATA)){
						
			if("sample".equals(ENTITY_TYPE)){					
				entity = SimpleDataBuilder.getBaseSampleList(ENTITY_NUM);
				clazz = SimpleEntity.class;
			}else if("complex".equals(ENTITY_TYPE)){
				entity = SimpleDataBuilder.getComplexList(ENTITY_NUM);
				clazz = ComplexEntity.class;
			}else if("jsonsample".equals(ENTITY_TYPE)){
				entity = SimpleDataBuilder.getBaseJsonList(ENTITY_NUM);
			}else {
				entity = SimpleDataBuilder.getComplexJsonList(ENTITY_NUM);
			}
			String json = JSON.toJSONString(entity);
			long startTime = System.nanoTime();
			fastjsonDeserialize.deserialize(json, clazz,BIG_DATA);
			fastjsonSum += (System.nanoTime()-startTime);
			startTime = System.nanoTime();
			jacksonDeserialize.deserialize(json, clazz,BIG_DATA);
			jacksonSum += (System.nanoTime()-startTime);
			startTime = System.nanoTime();
			gsonDeserialize.deserialize(json, clazz,BIG_DATA);
			gsonSum += (System.nanoTime()-startTime);
			startTime = System.nanoTime();
			myDeserialize.deserialize(json, clazz,BIG_DATA);
			mySum += (System.nanoTime()-startTime);
			startTime = System.nanoTime();
			replaceDeserialize.deserialize(json, clazz,BIG_DATA);
			replaceSum += (System.nanoTime()-startTime);

			System.out.println("deserialize entityType:"+ENTITY_TYPE+",bigData:"+BIG_DATA+",entityNum:"+ENTITY_NUM);
			System.out.println("tool\t\t\t\tttime_cost");
			System.out.printf("%s\t\t\t%d%n", "fastjson"+ENTITY_NUM ,fastjsonSum);
			System.out.printf("%s\t\t\t\t%d%n", "jackson"+ENTITY_NUM ,jacksonSum);
			System.out.printf("%s\t\t\t\t%d%n", "gson" +ENTITY_NUM,gsonSum);
			System.out.printf("%s\t\t%d%n", "FastjsonReplacement" +ENTITY_NUM,mySum);
			System.out.printf("%s\t\t%d%n", "JacksonReplaceFastjson" +ENTITY_NUM,replaceSum);

		}else{
			for(int i=0;i< ENTITY_NUM;i++){				
				if("sample".equals(ENTITY_TYPE)){					
					entity = SimpleDataBuilder.getBaseSampleEntity();
					clazz = SimpleEntity.class;
				}else if("complex".equals(ENTITY_TYPE)){
					entity = SimpleDataBuilder.getComplexEntity();
					clazz = ComplexEntity.class;
				}else if("jsonsample".equals(ENTITY_TYPE)){
					entity = SimpleDataBuilder.getBaseJson();
				}else {
					entity = SimpleDataBuilder.getComplexJson();
				}
				String json = JSON.toJSONString(entity);
				long startTime = System.nanoTime();
				fastjsonDeserialize.deserialize(json, clazz,BIG_DATA);
				fastjsonSum += (System.nanoTime()-startTime);

				startTime = System.nanoTime();
				jacksonDeserialize.deserialize(json, clazz,BIG_DATA);
				jacksonSum += (System.nanoTime()-startTime);
				
				startTime = System.nanoTime();
				gsonDeserialize.deserialize(json, clazz,BIG_DATA);
				gsonSum += (System.nanoTime()-startTime);

				startTime = System.nanoTime();
				myDeserialize.deserialize(json, clazz,BIG_DATA);
				mySum += (System.nanoTime()-startTime);

				startTime = System.nanoTime();
				replaceDeserialize.deserialize(json, clazz,BIG_DATA);
				replaceSum += (System.nanoTime()-startTime);
			}
			System.out.println("deserialize entityType:"+ENTITY_TYPE+",bigData:"+BIG_DATA+",entityNum:"+ENTITY_NUM);
			System.out.println("tool\t\t\t\tttime_cost");
			System.out.printf("%s\t\t\t%d%n", "fastjson"+ENTITY_NUM ,fastjsonSum);
			System.out.printf("%s\t\t\t\t%d%n", "jackson"+ENTITY_NUM ,jacksonSum);
			System.out.printf("%s\t\t\t\t%d%n", "gson" +ENTITY_NUM,gsonSum);
			System.out.printf("%s\t\t%d%n", "FastjsonReplacement" +ENTITY_NUM,mySum);
			System.out.printf("%s\t\t%d%n", "JacksonReplaceFastjson" +ENTITY_NUM,replaceSum);
		}
	}
	/**
	 * 
	* @Title: serializePreHeat  序列化预热 
	* @param @throws Exception    参数  
	* @return void    返回类型  
	* @throws
	 */
	public void serializePreHeat() throws Exception{
		SimpleEntity sample = SimpleDataBuilder.getBaseSampleEntity();
		long startTime = System.nanoTime();
		fastjsonSerialize.serialize(sample);
		System.out.println("serialize fastjson preheat,time:"+(System.nanoTime()-startTime));
		startTime = System.nanoTime();
		jacksonSerialize.serialize(sample);
		System.out.println("serialize jackson preheat,time:"+(System.nanoTime()-startTime));
		startTime = System.nanoTime();
		gsonSerialize.serialize(sample);
		System.out.println("serialize gson preheat,time:"+(System.nanoTime()-startTime));
		startTime = System.nanoTime();
		mySerialize.serialize(sample);
		System.out.println("serialize FastjsonReplacement preheat,time:"+(System.nanoTime()-startTime));

		startTime = System.nanoTime();
		replaceSerialize.serialize(sample);
		System.out.println("serialize JacksonReplaceFastjson preheat,time:"+(System.nanoTime()-startTime));
	}
	/**
	 * 
	 * @Title: deserializePreHeat  反序列化预热 
	 * @param @throws Exception    参数  
	 * @return void    返回类型  
	 * @throws Exception
	 */
	public void deserializePreHeat() throws Exception{
		SimpleEntity sample = SimpleDataBuilder.getBaseSampleEntity();
		String json = JSON.toJSONString(sample);
		long startTime = System.nanoTime();
		fastjsonDeserialize.deserialize(json, SimpleEntity.class,"object");
		System.out.println("deserialize fastjson preheat,time:"+(System.nanoTime()-startTime));
		startTime = System.nanoTime();
		jacksonDeserialize.deserialize(json, SimpleEntity.class,"object");
		System.out.println("deserialize jackson preheat,time:"+(System.nanoTime()-startTime));
		startTime = System.nanoTime();
		gsonDeserialize.deserialize(json, SimpleEntity.class,"object");
		System.out.println("deserialize gson preheat,time:"+(System.nanoTime()-startTime));
		startTime = System.nanoTime();
		myDeserialize.deserialize(json, SimpleEntity.class,"object");
		System.out.println("deserialize FastjsonReplacement preheat,time:"+(System.nanoTime()-startTime));

		startTime = System.nanoTime();
		replaceDeserialize.deserialize(json, SimpleEntity.class,"object");
		System.out.println("deserialize JacksonReplaceFastjson preheat,time:"+(System.nanoTime()-startTime));

	}
	
}
