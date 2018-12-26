-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: blog
-- ------------------------------------------------------
-- Server version	5.7.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `blog_article`
--

DROP TABLE IF EXISTS `blog_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_article` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `article_title` varchar(50) NOT NULL COMMENT '文章标题',
  `article_createtime` datetime NOT NULL COMMENT '文章创建时间',
  `article_updatetime` datetime NOT NULL COMMENT '文章更新时间',
  `channel_id` int(11) NOT NULL COMMENT '文章所属栏目ID',
  `article_status` char(1) NOT NULL COMMENT '文章状态 1是发布 0是草稿',
  `article_content` text COMMENT '文章内容',
  `article_remark` varchar(400) DEFAULT NULL COMMENT '文章摘要',
  `user_id` int(11) DEFAULT NULL COMMENT '文章发布者',
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='栏目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_article`
--

LOCK TABLES `blog_article` WRITE;
/*!40000 ALTER TABLE `blog_article` DISABLE KEYS */;
INSERT INTO `blog_article` VALUES (2,'2018-11-28 第一版本V0.0.1','2018-11-28 19:13:11','2018-11-28 19:13:11',2,'1','该网站是个人独立开发，供本人写一些文字使用的。\n\n第一版正式部署时间（部署到了朋友的阿里云ECS）是2018-11-28。SSM框架、MYSQL数据库。前台可以浏览发布的文章，后台包括文章管理、栏目管理、用户管理及标签管理等功能。未经历详细测试工作，后续边使用边发现问题。','该网站是个人独立开发，供本人写一些文字使用的。\n第一版正式部署时间（部署到了朋友的阿里云ECS）是2018-11-28。SSM框架、MYSQL数据库。前台可以浏览发布的文章，后台包括文章管理、栏目管理、用户管理及标签管理等功能。未经历详细测试工作，后续边使用边发现问题。\n',2),(3,'Java注解','2018-11-28 19:15:09','2018-11-28 19:15:09',1,'1','## 概念\n> 注解是一系列元数据，它提供数据用来解释程序代码，但是注解并非是所解释的代码本身的一部分。注解对于代码的运行效果没有直接影响。注解有许多用处，主要如下： \n- 提供信息给编译器： 编译器可以利用注解来探测错误和警告信息 \n- 编译阶段时的处理： 软件工具可以用来利用注解信息来生成代码、Html文档或者做其它相应处理。 \n- 运行时的处理： 某些注解可以在程序运行的时候接受代码的提取\n\n注解就是给代码打标签，这些标签不会影响到被标签代码的运行，但一些情况下程序会根据这些标签运行某些逻辑。\n## 注解分类\n- 根据生命周期活运行周期\n	- 源码注。只在源码中存在\n	- 编译时注解。在源码和class中存在 @Override \n	- 运行时注解。在运行时仍然存在 @Autowired\n- 根据来源\n	- JDK\n	- 第三方\n	- 自定义\n\n## 元注解\n给注解使用的注解，自定义注解中会使用到。\n\n	@Target 在什么地方使用\n	@Retention 运行周期\n	@Documented 容许生成JavaDoc\n	@Inherited 容许子类继承父类的该注解\n	@Repeatable 1.8后，可重复使用\n\n#### @Target\n\n	ElemenetType.CONSTRUCTOR 构造器声明\n	ElemenetType.FIELD 域声明（包括 enum 实例）\n	ElemenetType.LOCAL_VARIABLE 局部变量声明\n	ElemenetType.METHOD 方法声明\n	ElemenetType.PACKAGE 包声明\n	ElemenetType.PARAMETER 参数声明\n	ElemenetType.TYPE 类，接口（包括注解类型）或enum声明\n#### @Retention\n\n	RetentionPolicy.SOURCE 在源码中\n	RetentionPolicy.CLASS 在class中存在\n	RetentionPolicy.RUNTIME 在运行中使用,可以通过反射调用注解信息\n\n## 实例DEMO\n#### 通过注解生成SQL\nMyTypeAnnotation\n```\nimport java.lang.annotation.ElementType;\nimport java.lang.annotation.Retention;\nimport java.lang.annotation.RetentionPolicy;\nimport java.lang.annotation.Target;\n\n/**\n * 类上注解\n * @author RYF\n *\n */\n@Target(ElementType.TYPE)\n@Retention(RetentionPolicy.RUNTIME)\npublic @interface MyTypeAnnotation {\n	String value();\n}\n```\nMyFieldAnnotation\n```\nimport java.lang.annotation.ElementType;\nimport java.lang.annotation.Retention;\nimport java.lang.annotation.RetentionPolicy;\nimport java.lang.annotation.Target;\n\n/**\n * 字段上的注解\n * @author RYF\n *\n */\n@Target(ElementType.FIELD)\n@Retention(RetentionPolicy.RUNTIME)\npublic @interface MyFieldAnnotation {\n\n	String value();\n	\n}\n```\nUserBean\n```\n/**\n * 模拟用户对象\n * @author RYF\n *\n */\n@MyTypeAnnotation(\"user\")\npublic class UserBean {\n\n	@MyFieldAnnotation(\"id\")\n	private Integer id;\n	\n	@MyFieldAnnotation(\"name\")\n	private String name;\n	\n	@MyFieldAnnotation(\"sex\")\n	private Integer sex;\n\n	 \n	public String getName() {\n		return name;\n	}\n\n	public void setName(String name) {\n		this.name = name;\n	}\n\n	public Integer getId() {\n		return id;\n	}\n\n	public void setId(Integer id) {\n		this.id = id;\n	}\n\n	public Integer getSex() {\n		return sex;\n	}\n\n	public void setSex(Integer sex) {\n		this.sex = sex;\n	}\n}\n```\nTest\n```\nimport java.lang.reflect.Field;\nimport java.lang.reflect.InvocationTargetException;\nimport java.lang.reflect.Method;\n\n/**\n * 注解使用方式测试\n * @author RYF\n *\n */\npublic class Test {\n	\n	public static void main(String[] args) {\n		UserBean user1 = new UserBean();\n		user1.setName(\"zhangsan\");\n		String result = query(user1);\n		System.out.println(result);\n		/**\n		 * Output\n		 * SELECT * FROM user where 1=1  and name=zhangsan\n		 * \n		 */\n		UserBean user2 = new UserBean();\n		user2.setId(12);\n		String result2 = query(user2);\n		System.out.println(result2);\n		/**\n		 * Output\n		 * SELECT * FROM user where 1=1  and id=12\n		 * \n		 */\n		\n	}\n	\n	/**\n	 * 组合查询语句并返回\n	 * @param user 用户对象\n	 * @return 返回查询语句\n	 */\n	public static String query(UserBean user) {\n		\n		StringBuffer sb = new StringBuffer();\n		sb.append(\"SELECT * FROM \");\n		\n		//获取类上的表名注解\n		MyTypeAnnotation table = user.getClass().getAnnotation(MyTypeAnnotation.class);\n		if(table != null) {\n			String tableName = table.value();\n			sb.append(tableName +\" where 1=1 \" );\n		}\n		\n		//获取所有的字段\n		Field[] fields = user.getClass().getDeclaredFields();\n		for (Field field : fields) {\n			\n			//获取字段上列名注解\n			MyFieldAnnotation column = field.getAnnotation(MyFieldAnnotation.class);\n			if(column != null) {\n				//列名\n				String columnName = column.value();\n				//字段名\n				String fieldName = field.getName();\n				//通过字段名组合get方法名称\n				String fieldGetMethodName = \"get\"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);\n				try {\n					//反射获取方法的值\n					Method fieldMethod = user.getClass().getMethod(fieldGetMethodName);\n					Object fieldValue =  fieldMethod.invoke(user);\n					if(fieldValue == null) {\n						continue;\n					}\n					//值不为空则作为条件\n					sb.append(\" and \"+columnName+\"=\"+ fieldValue);\n				}  catch (NoSuchMethodException e) {\n					e.printStackTrace();\n				} catch (SecurityException e) {\n					e.printStackTrace();\n				}catch (IllegalAccessException e) {\n					e.printStackTrace();\n				} catch (IllegalArgumentException e) {\n					e.printStackTrace();\n				} catch (InvocationTargetException e) {\n					e.printStackTrace();\n				}\n			}\n		}\n		return sb.toString();\n	}\n}\n```\n*注：该DEMO只是为了演示自定义注解及使用，生成的SQL逻辑不严谨*\n\n#### 方法注解DEMO\nLogAnnotation\n```\n/**\n * 日志注解\n * @author RYF\n *\n */\n@Target({ElementType.METHOD})\n@Retention(RetentionPolicy.RUNTIME)\npublic @interface LogAnnotation {\n	String desc(); //操作描述信息\n	int operType() ;//操作类型 默认未分类 \n	int operModule() ;//操作模块 默认未分类\n	int operUserId() default 0;//操作用户ID\n	String operDate() default \"\";//操作时间\n	String operIp() default \"\";//操作ip\n	int operResult() default 0;// 1 正常 0 异常\n	String exceptionDesc() default \"\"; //异常描述\n	String note() default \"\";//备注\n}\n```\n方法使用\n\n	@LogAnnotation(desc=\"新增/注册用户\",operType = 1,operModule=1)\n	public UserBean login(UserBean user) {\n		return userDao.searchUserByNameAndPwd(user);\n	}\n日志调用\n\n	LogAnnotation log = method.getAnnotation(LogAnnotation.class);\n\n*注：该注解可配合springAOP使用用来记录日志*\n\n## 参考资料\n- https://blog.csdn.net/briblue/article/details/73824058\n\n\n','概念\n注解是一系列元数据，它提供数据用来解释程序代码，但是注解并非是所解释的代码本身的一部分。注解对于代码的运行效果没有直接影响。注解有许多用处，主要如下： \n\n提供信息给编译器： 编译器可以利用注解来探测错误和警告信息 编译阶段时的处理： 软件工具可以用来利用注解信息来生成代码、Html文档或者做其它相应处理。 运行时的处理： 某些注解可以在程序运行的时候接受代码的提取\n\n注解就是给代码打标签，',2),(4,'POI下拉框及二级级联','2018-12-12 10:10:52','2018-12-12 10:52:53',1,'1','## 原理\n新建一个sheet，作为下拉数据的存放页。然后在该页签中设置名称管理器，在另一个sheet中要使用的单元格设置数据验证。\n### 数据验证的有效性\n通过设置数据验证的有效性，将某一列数据设置为序列，或者将来源直接设置为名称管理器的名称。\n### 名称管理器\n将以及下拉和相关的二级下拉数据都设置为名称管理器中。\n## 示例\n### pom\n```\n<dependency>\n	    <groupId>org.apache.poi</groupId>\n	    <artifactId>poi</artifactId>\n	    <version>4.0.1</version>\n</dependency>\n\n<dependency>\n	    <groupId>org.apache.poi</groupId>\n	    <artifactId>poi-ooxml</artifactId>\n	    <version>4.0.1</version>\n</dependency>\n\n```\n### 代码\n```\nimport java.io.File;\nimport java.io.FileOutputStream;\nimport java.io.IOException;\nimport java.util.ArrayList;\nimport java.util.Arrays;\nimport java.util.List;\nimport java.util.Map;\n\nimport org.apache.commons.collections4.map.HashedMap;\nimport org.apache.poi.hssf.usermodel.DVConstraint;\nimport org.apache.poi.hssf.usermodel.HSSFDataValidation;\nimport org.apache.poi.hssf.usermodel.HSSFWorkbook;\nimport org.apache.poi.ss.usermodel.Cell;\nimport org.apache.poi.ss.usermodel.DataValidation;\nimport org.apache.poi.ss.usermodel.Name;\nimport org.apache.poi.ss.usermodel.Row;\nimport org.apache.poi.ss.usermodel.Sheet;\nimport org.apache.poi.ss.usermodel.Workbook;\nimport org.apache.poi.ss.util.CellRangeAddressList;\n\n/**\n * POI 二级级联\n * @author RYF\n *\n */\npublic class Cascade {\n	\n	private static Workbook wb;\n	private static final String HIDDENSHEET_NAME=\"隐藏sheet\";//给名称管理器使用的sheet名称\n	private static final String PARENTNAME = \"父类\";//父类名称管理器的名称\n\n	/**\n	 * 设置父类的数据\n	 * @param parentList 父类数据\n	 * @param sheet sheet页\n	 */\n	public static void createParentData(List<String> parentList,Sheet sheet) {\n		for(int i=0;i<parentList.size();i++) {\n			Row row = sheet.createRow(i);\n			Cell cell = row.createCell(0);\n			cell.setCellValue(parentList.get(i));\n		}\n	}\n	\n	/**\n	 * 给父类设置“名称管理器”\n	 * @param size 总个数\n	 */\n	public static void setParentName(int size) {\n		Name parentName = wb.createName();\n		parentName.setNameName(PARENTNAME);\n		parentName.setRefersToFormula(HIDDENSHEET_NAME+\"!$A$1:$A$\"+size);\n	}\n	\n	/**\n	 * 设置子类数据并给子类设置“名称管理器”\n	 * @param parentList\n	 * @param childrenMap\n	 * @param sheet\n	 */\n	public static void  childrenData(List<String> parentList,Map<String, List<String>> childrenMap,Sheet sheet) {\n		for(int i=0;i<parentList.size();i++) {\n			 String key = parentList.get(i);\n			 List<String> children =  childrenMap.get(key);\n			 if(children != null && children.size()>0) {\n				 int letterNum = i+1; //哪一列\n				 int rowCount = sheet.getLastRowNum();\n				 if(rowCount == 0 && sheet.getRow(0) == null) {\n					 sheet.createRow(0);\n				 }\n				 for(int j=0;j<children.size();j++) {\n					 if(j<=rowCount) {//已经创建过行的直接获取\n						 Row row = sheet.getRow(j);\n						 Cell cell = row.createCell(letterNum);\n						 cell.setCellValue(children.get(j));\n					 }else {//没有创建过的行，继续创建\n						 Row row = sheet.createRow(j);\n						 Cell cell = row.createCell(i+1);\n						 cell.setCellValue(children.get(j));\n					 }\n				 }\n				 //给每个子类设置“名称选择器”\n				 Name childrenName = wb.createName();\n				 childrenName.setNameName(key);\n				 //根据列数获取字母表示形式\n				 String letter = getColumnName(letterNum);\n				 childrenName.setRefersToFormula(HIDDENSHEET_NAME+\"!$\"+letter+\"$1:$\"+letter+\"$\"+children.size());\n			 }\n		}\n	}\n	\n	/**\n	 * 给要使用的单元格添加有效性验证\n	 */\n	public static void addVali() {\n		Sheet dataSheet = wb.createSheet(\"级联下拉\");\n		DVConstraint parentDVC = DVConstraint.createFormulaListConstraint(PARENTNAME);\n		CellRangeAddressList parentRange = new CellRangeAddressList(1, 65535, 1, 1);\n		DataValidation parentDataV = new HSSFDataValidation(parentRange, parentDVC);\n		parentDataV.createPromptBox(\"提示\", \"请从下拉数据中选择\");\n		dataSheet.addValidationData(parentDataV);\n		\n		DVConstraint childDVC = DVConstraint.createFormulaListConstraint(\"INDIRECT($B1)\");\n		CellRangeAddressList  childRange = new CellRangeAddressList(1, 65535, 2, 2);\n		DataValidation  childDataV = new HSSFDataValidation( childRange,  childDVC);\n		childDataV.createPromptBox(\"提示\", \"请从下拉数据中选择并先选中父类\");\n		dataSheet.addValidationData(childDataV);\n	}\n	\n	/**\n	 * 单个下拉\n	 */\n	public static void oneDown() {\n		String[] data = {\"下拉1\",\"下拉2\",\"下拉3\"};\n		Sheet sheet = wb.createSheet(\"单个下拉\");\n		DVConstraint dv = DVConstraint.createExplicitListConstraint(data);\n		CellRangeAddressList range = new CellRangeAddressList(0, 65535, 0, 0);\n		DataValidation dataV = new HSSFDataValidation(range, dv);\n		dataV.createPromptBox(\"提示\", \"请从下拉数据中选择\");\n		sheet.addValidationData(dataV);\n	}\n	\n	\n	/**\n     * 根据数据值确定单元格位置（比如：0-A, 27-AA）\n     *\n     * @param index\n     * @return\n     */\n    public static String getColumnName(int index) {\n        StringBuilder s = new StringBuilder();\n        while (index >= 26) {\n            s.insert(0, (char) (\'A\' + index % 26));\n            index = index / 26 - 1;\n        }\n        s.insert(0, (char) (\'A\' + index));\n        return s.toString();\n    }\n	\n	public static void main(String[] args) {\n		\n		List<String> parentList = new ArrayList<>();\n		parentList.add(\"父类1\");\n		parentList.add(\"父类2\");\n		parentList.add(\"父类3\");\n		Map<String, List<String>> childrenMap = new HashedMap<>();\n		childrenMap.put(\"父类1\", Arrays.asList(\"子类11\",\"子类12\",\"子类13\"));\n		childrenMap.put(\"父类2\", Arrays.asList(\"子类21\",\"子类22\"));\n		childrenMap.put(\"父类3\", Arrays.asList(\"子类31\",\"子类32\",\"子类33\",\"子类34\"));\n		\n		FileOutputStream out = null;\n		try {\n			out = new FileOutputStream(new File(\"D:/poi_cascade3.xlsx\"));\n			wb = new HSSFWorkbook();\n			Sheet sheet =  wb.createSheet(HIDDENSHEET_NAME);\n			//sheet中添加需要级联的数据\n			//父类数据\n			createParentData(parentList,sheet);\n			//给父类设置“名称选择器”\n			setParentName(parentList.size());\n			//子类数据\n			childrenData(parentList,childrenMap,sheet);\n			//给使用单元格设置数据的验证有效性\n			addVali();\n			//单个下拉\n			oneDown();\n			\n			out.flush();\n			wb.write(out);\n		} catch (IOException e) {\n			e.printStackTrace();\n		} finally {\n			if(out != null) {\n				try {\n					out.close();\n				} catch (IOException e) {\n					e.printStackTrace();\n				}\n			}\n		}\n\n	}\n	\n}\n```','原理新建一个sheet，作为下拉数据的存放页。然后在该页签中设置名称管理器，在另一个sheet中要使用的单元格设置数据验证。\n数据验证的有效性通过设置数据验证的有效性，将某一列数据设置为序列，或者将来源直接设置为名称管理器的名称。\n名称管理器将以及下拉和相关的二级下拉数据都设置为名称管理器中。\n示例pom<dependency><groupId>org.apache.poi</groupId><a',2),(5,'ORA-01795: 列表中的最大表达式数为 1000','2018-12-16 13:37:22','2018-12-16 13:45:23',3,'1','','',2),(6,'spring事务','2018-12-19 22:36:45','2018-12-19 22:41:12',1,'1','## 两种声明式事务配置示例\n### 使用XML\n```\n <!-- 无论哪种方式，事务管理器都是要配置的 -->\n <bean id=\"transactionManager\"   class=\"org.springframework.jdbc.datasource.DataSourceTransactionManager\">  \n        <property name=\"dataSource\" ref=\"dataSource\" />  \n    </bean>  \n    \n    <tx:advice id=\"txadvice\" transaction-manager=\"transactionManager\"> \n        <tx:attributes> \n            <tx:method name=\"add*\" propagation=\"REQUIRED\" rollback-for=\"Exception\" /> \n            <tx:method name=\"del*\" propagation=\"REQUIRED\" rollback-for=\"Exception\"/>\n            <tx:method name=\"edit*\" propagation=\"REQUIRED\" rollback-for=\"Exception\" />\n            <tx:method name=\"update*\" propagation=\"REQUIRED\" rollback-for=\"Exception\"/> \n        </tx:attributes> \n    </tx:advice> \n        \n    <aop:config> \n        <aop:pointcut id=\"serviceMethod\" expression=\"execution(* com.ren.blog.service.*.*(..))\"/> \n        <aop:advisor pointcut-ref=\"serviceMethod\" advice-ref=\"txadvice\"/> \n    </aop:config>  \n```\n### Transactional配置项\n直接在需要事务的地方加上Transactional注解，同时不要忘了加上\n```\n   	<tx:annotation-driven transaction-manager=\"transactionManager\"/>\n\n```\nTransactional使用的是spring aop，spring aop使用的动态代理，所以对静态方法和非public方法，该注解是失效的。\n## 数据库的ACID特性\n- 原子性 Atomicity\n	事务是不可分割的，事务中的操作要么发生，要么都不发生\n- 一致性 Consistency\n	事务执行前后数据的完整性要保持一致\n- 隔离性 Isolation\n	多个事务之间的隔离程度\n- 持久性 Durability\n	事务一旦被提交，它对数据库的修改就是永久性的\n\n## 隔离级别\n隔级别是为了解决脏读、不可重复读、幻读的问题。\n- 脏读 dirty read\n	允许一个事务去读取另一个事务未提交的数据\n- 读/写提交 read commit\n	一个事务只能读取另一个事务已经提交的数据\n- 可重复读 repeatable read \n	事务开启，不允许其他事务的UPDATE修改操作\n- 序列化 serializable\n\n### isolation\n- DEFAULT\n- READ_UNCOMMITTED\n- READ_COMMITTED\n- REPEATABLE_READ\n- SERIALIZABLE\n\n注解Transactional默认的隔离级别是DEFAULT,即随着数据库变化而变化。MySQL默认是可重复读的隔离级别，且四种功能隔离级别都支持。Oracle默认是读写提交，且只支持读写提交和序列化两种隔离级别。\n## 传播行为 Propagation\n可以用来解决业务层方法之间的相互调用问题产生的事务处理。\n- REQUIRED\n	不存在事务，启用事务，存在则继续使用该事务\n- SUPPORTS\n	不存在事务，不启用事务，存在则沿用\n- MANDATORY\n	方法不存在事务的话就抛异常\n- REQUIRES_NEW\n	无论当前是否存在事务，都会在新的事务中运行\n- NOT_SUPPORTED\n	不支持事务，不存在则不用事务，存在则挂起事务，完成后恢复事务\n- NEVER\n	不支持事务，有事务则抛异常\n- NESTED\n	嵌套事务，只回滚本身的SQL。在支持事务保持点的是数据库中，发生异常回滚可以回滚到保存点上，而不是全部回滚。如果不支持的话，就相当于REQUIRES_NEW\n\nspring默认的传播行为是REQUIRED，另外比较多的就是REQUIRES_NEW和NESTED\n\n\n\n\n\n\n\n','两种声明式事务配置示例使用XML <!-- 无论哪种方式，事务管理器都是要配置的 --> <bean id=\"transactionManager\" class=\"org.springframework.jdbc.datasource.DataSourceTransactionManager\"><property name=\"dataSource\" ref=\"dataSource\" /></bea',2),(7,'2018-12-19 第一版本V0.0.2','2018-12-19 22:42:22','2018-12-19 22:42:22',2,'1','本次版本新增了图片上传功能','本次版本新增了图片上传功能\n',2);
/*!40000 ALTER TABLE `blog_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blog_article_tag`
--

DROP TABLE IF EXISTS `blog_article_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_article_tag` (
  `article_tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tag_id` int(11) NOT NULL COMMENT '标签ID',
  `article_id` int(11) NOT NULL COMMENT '文章ID',
  PRIMARY KEY (`article_tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='文章标签关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_article_tag`
--

LOCK TABLES `blog_article_tag` WRITE;
/*!40000 ALTER TABLE `blog_article_tag` DISABLE KEYS */;
INSERT INTO `blog_article_tag` VALUES (4,3,2),(5,1,3),(6,2,3),(7,4,3),(12,1,4),(13,5,4),(14,6,4),(15,7,4),(16,8,5),(17,9,5),(25,11,6),(26,12,6),(27,1,6);
/*!40000 ALTER TABLE `blog_article_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blog_channel`
--

DROP TABLE IF EXISTS `blog_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_channel` (
  `channel_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '栏目ID',
  `channel_name` varchar(50) NOT NULL COMMENT '栏目名称',
  `channel_order` int(11) NOT NULL COMMENT '栏目排序',
  `channel_createtime` datetime NOT NULL COMMENT '栏目创建时间',
  `channel_updatetime` datetime NOT NULL COMMENT '栏目更新时间',
  `channel_desc` varchar(300) DEFAULT NULL COMMENT '栏目描述',
  PRIMARY KEY (`channel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='栏目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_channel`
--

LOCK TABLES `blog_channel` WRITE;
/*!40000 ALTER TABLE `blog_channel` DISABLE KEYS */;
INSERT INTO `blog_channel` VALUES (1,'JAVA技术',1,'2018-11-28 18:54:29','2018-11-28 18:56:42','该栏目包含JAVA计数相关的文章'),(2,'网站历史',99,'2018-11-28 19:05:39','2018-11-28 19:05:39','改栏目包含网站的更新历史文章'),(3,'异常记录',2,'2018-12-16 12:41:38','2018-12-16 12:41:38','该栏目是记录开发中的异常信息及解决方法');
/*!40000 ALTER TABLE `blog_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blog_homestatistics`
--

DROP TABLE IF EXISTS `blog_homestatistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_homestatistics` (
  `home_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `home_ip` varchar(100) DEFAULT NULL COMMENT '访问IP',
  `home_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`home_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_homestatistics`
--

LOCK TABLES `blog_homestatistics` WRITE;
/*!40000 ALTER TABLE `blog_homestatistics` DISABLE KEYS */;
/*!40000 ALTER TABLE `blog_homestatistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blog_log`
--

DROP TABLE IF EXISTS `blog_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `log_desc` varchar(100) DEFAULT NULL COMMENT '日志简单描述信息',
  `log_opertype` int(11) DEFAULT NULL COMMENT '操作类型 ',
  `log_opermodule` int(11) DEFAULT NULL COMMENT '操作模块 ',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `log_operdate` varchar(32) DEFAULT NULL COMMENT ' 操作日期',
  `log_operip` varchar(50) DEFAULT NULL COMMENT '操作IP',
  `log_result` int(1) DEFAULT NULL COMMENT '操作结果 1是正常 2是异常',
  `log_resultdesc` varchar(500) DEFAULT NULL COMMENT '异常描述',
  `note` varchar(4000) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_log`
--

LOCK TABLES `blog_log` WRITE;
/*!40000 ALTER TABLE `blog_log` DISABLE KEYS */;
INSERT INTO `blog_log` VALUES (1,'新增/注册用户',1,4,0,'2018-11-28 18:51:03','124.200.183.90',1,NULL,''),(2,'登录',5,4,1,'2018-11-28 18:51:09','124.200.183.90',1,NULL,''),(3,'登录',5,4,0,'2018-11-28 18:53:10','124.200.183.90',1,NULL,''),(4,'登录',5,4,1,'2018-11-28 18:53:22','124.200.183.90',1,NULL,''),(5,'新增栏目',1,2,1,'2018-11-28 18:54:29','124.200.183.90',1,NULL,''),(6,'更新用户',2,4,1,'2018-11-28 18:54:45','124.200.183.90',1,NULL,''),(7,'新增新文章',1,1,1,'2018-11-28 18:56:04','124.200.183.90',1,NULL,''),(8,'更新栏目',2,2,1,'2018-11-28 18:56:42','124.200.183.90',1,NULL,''),(9,'新增栏目',1,2,1,'2018-11-28 19:05:39','124.200.183.90',1,NULL,''),(10,'新增新文章',1,1,1,'2018-11-28 19:13:11','124.200.183.90',1,NULL,''),(11,'更新文章状态',2,1,1,'2018-11-28 19:13:42','124.200.183.90',1,NULL,''),(12,'删除文章',3,1,1,'2018-11-28 19:13:46','124.200.183.90',1,NULL,''),(13,'新增新文章',1,1,1,'2018-11-28 19:15:09','124.200.183.90',1,NULL,''),(14,'登录',5,4,1,'2018-12-12 09:57:14','124.200.183.90',1,NULL,''),(15,'登录',5,4,1,'2018-12-12 10:00:28','124.200.183.90',1,NULL,''),(16,'新增新文章',1,1,1,'2018-12-12 10:10:52','124.200.183.90',1,NULL,''),(17,'登录',5,4,1,'2018-12-12 10:51:43','124.200.183.90',1,NULL,''),(18,'更新文章',2,1,1,'2018-12-12 10:52:53','124.207.110.206',1,NULL,''),(19,'登录',5,4,1,'2018-12-16 12:40:37','124.200.183.90',1,NULL,''),(20,'新增栏目',1,2,1,'2018-12-16 12:41:38','124.200.183.90',1,NULL,''),(21,'新增新文章',1,1,1,'2018-12-16 13:37:22','124.200.183.90',1,NULL,''),(22,'登录',5,4,1,'2018-12-16 13:44:57','124.200.183.90',1,NULL,''),(23,'更新文章',2,1,1,'2018-12-16 13:45:23','124.200.183.90',1,NULL,''),(24,'登录',5,4,1,'2018-12-17 09:07:57','124.200.183.90',1,NULL,''),(25,'登录',5,4,1,'2018-12-19 21:03:37','221.219.170.137',1,NULL,''),(26,'登录',5,4,1,'2018-12-19 22:36:28','221.219.170.137',1,NULL,''),(27,'新增新文章',1,1,1,'2018-12-19 22:36:45','221.219.170.137',1,NULL,''),(28,'更新文章',2,1,1,'2018-12-19 22:38:46','221.219.170.137',1,NULL,''),(29,'更新文章',2,1,1,'2018-12-19 22:40:29','221.219.170.137',1,NULL,''),(30,'更新文章',2,1,1,'2018-12-19 22:40:54','221.219.170.137',1,NULL,''),(31,'更新文章',2,1,1,'2018-12-19 22:41:12','221.219.170.137',1,NULL,''),(32,'新增新文章',1,1,1,'2018-12-19 22:42:22','221.219.170.137',1,NULL,''),(33,'登录',5,4,1,'2018-12-26 14:33:22','124.200.183.90',1,'',''),(34,'新增/注册用户',1,4,1,'2018-12-26 14:35:00','124.200.183.90',1,'','');
/*!40000 ALTER TABLE `blog_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blog_tag`
--

DROP TABLE IF EXISTS `blog_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `tag_name` varchar(15) NOT NULL COMMENT '标签名称',
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_tag`
--

LOCK TABLES `blog_tag` WRITE;
/*!40000 ALTER TABLE `blog_tag` DISABLE KEYS */;
INSERT INTO `blog_tag` VALUES (1,'JAVA'),(2,'注解'),(3,'第一篇'),(4,'技术第一篇'),(5,'POI'),(6,'下拉框'),(7,'二级级联'),(8,'异常'),(9,'SQL'),(10,'spring'),(11,'事务'),(12,'SPRING');
/*!40000 ALTER TABLE `blog_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blog_user`
--

DROP TABLE IF EXISTS `blog_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_username` varchar(30) NOT NULL COMMENT '登录名',
  `user_nickname` varchar(30) DEFAULT NULL COMMENT '昵称',
  `user_password` varchar(32) NOT NULL COMMENT '密码',
  `user_email` varchar(50) NOT NULL COMMENT '邮箱',
  `user_createtime` datetime NOT NULL COMMENT '用户创建时间',
  `user_updatetime` datetime NOT NULL COMMENT '用户更新时间',
  `user_status` int(11) NOT NULL COMMENT '用户状态 0 禁用 1 启用',
  `user_type` int(11) NOT NULL COMMENT '用户类型 0 是管理员 1是普通用户',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_user`
--

LOCK TABLES `blog_user` WRITE;
/*!40000 ALTER TABLE `blog_user` DISABLE KEYS */;
INSERT INTO `blog_user` VALUES (1,'admin','系统管理员','A24FF9C091F2C801A2CAB1029B489E01','yuanfangrenryf@163.com','2018-11-28 18:51:03','2018-11-28 18:54:45',1,0),(2,'yuanfang','远方','1FDCEFD96C3EB34D5A0BE7F2A986FF52','yuanfangrenryf@163.com','2018-12-26 14:35:00','2018-12-26 14:35:00',1,1);
/*!40000 ALTER TABLE `blog_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-26 14:54:47
