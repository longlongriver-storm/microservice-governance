/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.algorithm.demo.ast;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import org.eclipse.jdt.core.dom.*;

/**
 * 解析Java类中的各类元素及方法的对外调用情况
 * @author lixin
 */
public class ParseOneJavaFile {
    public static void main(String[] args) throws Exception {
        File file=new File(args[0]);
        if(!file.exists()){
            System.out.println("指定的不是一个合法的文件,path="+args[0]);
            return;
        }
        //解析java源文件
        //String content = read("D:\\Develop\\GitStore\\monitor\\core\\src\\main\\java\\com\\mycompany\\app\\ifinance\\service\\CombineProdServiceImpl.java");
        String content = read(args[0]);
        //创建解析器  
        ASTParser parsert = ASTParser.newParser(AST.JLS4);
        //设定解析器的源代码字符  
        parsert.setSource(content.toCharArray());
        //使用解析器进行解析并返回AST上下文结果(CompilationUnit为根节点)  
        CompilationUnit result = (CompilationUnit) parsert.createAST(null);

        //获取类型  
        List types = result.types();
        //取得类型声明  (可能有多个类定义)
        TypeDeclaration typeDec = (TypeDeclaration) types.get(0);

        //##############获取源代码结构信息#################  
        //引用import  
        List importList = result.imports();
        //取得包名  
        PackageDeclaration packetDec = result.getPackage();
        String packageName = packetDec.getName().toString();
        //取得类名  
        String className = typeDec.getName().toString();
        //取得函数(Method)声明列表  
        MethodDeclaration methodDec[] = typeDec.getMethods();
        //取得函数(Field)声明列表  
        FieldDeclaration fieldDec[] = typeDec.getFields();

        Map<String, String> _usingClass = new HashMap();   //import的所有类+同包下的类  <UserInfoOptService,com.mycompany.....UserInfoOptService>
        Map<String, String> _classParams = new HashMap();   //所有类变量格式： <userInfoOptService,com.mycompany.....UserInfoOptService>
        List<String> _importStars = new ArrayList();      //import *的包名
        Set<String> _methodNames = new HashSet<String>();        //本类中的所有方法

        //继承的类或者实现的接口
        for (Object obj : typeDec.superInterfaceTypes()) {
            System.out.println("interface:" + obj);
        }
        System.out.println("extends:" + typeDec.getSuperclassType());

        //输出包名  
        System.out.println("包:" + packetDec.getName());
        //输出类名  
        System.out.println("类:" + className);
        //输出引用import  
        System.out.println("引用import:");
        for (Object obj : importList) {
            ImportDeclaration importDec = (ImportDeclaration) obj;
            System.out.println("   " + importDec.getName());
            String[] importName = resolveImport(importDec);
            if (importName != null) {
                if (importDec.toString().replaceAll(";", "").replaceAll("\\n", "").trim().endsWith("*")) {   //import xx.*;
                    _importStars.add(importName[1]);
                } else {
                    _usingClass.put(importName[0], importName[1]);
                }
            }
        }
        //实现的接口
        for (Object obj : typeDec.superInterfaceTypes()) {
            System.out.println("interface:" + obj);
        }

        //继承类
        if (typeDec.getSuperclassType() != null && typeDec.getSuperclassType().toString().length() > 0) {
            String superTypeName = typeDec.getSuperclassType().toString();
            System.out.println("extends:" + superTypeName);
        }

        //循环输出函数名称  
        System.out.println("========================");
        //循环输出变量  
        System.out.println("可识别的类变量:");
        for (FieldDeclaration fieldDecEle : fieldDec) {
            String paramType = fieldDecEle.getType().toString();
            String paramName = null;

            for (Object obj : fieldDecEle.fragments()) {
                if (obj instanceof VariableDeclarationFragment) {
                    paramName = ((VariableDeclarationFragment) obj).getName().toString();
                    break;
                }
            }
            if (_usingClass.keySet().contains(paramType)) {   //能找到对应import类
                _classParams.put(paramName, _usingClass.get(paramType));
                System.out.println("    变量名："+paramName+",类型:"+_usingClass.get(paramType));
            } else {   //找不到import的，可能是import *,需要从第一遍扫描中所获取的类名中查找
                for (String importStar : _importStars) {
                    //...
                }
            }

        }
        System.out.println("========================");
        System.out.println("类方法解析:");
        for (MethodDeclaration method : methodDec) {
            System.out.println("==========================解析方法：" + method.getName() + "===========================");
            System.out.println("Javadoc:" + method.getJavadoc());

            String _fullClassName=packageName + "." + className;
            Map<String, String> _classParams4Method = new HashMap();
            _classParams4Method.putAll(_classParams);
            //方法体上的一些修饰符，包括@标签，@Override、void、public等等
            for (Object obj : method.modifiers()) {
                System.out.print(obj.getClass().getCanonicalName() + ":" + obj.toString() + "-");
            }
            System.out.println();
            Block body = method.getBody();
            if (body == null) {
                continue;
            }
            //方法内变量
            List<SingleVariableDeclaration> mParams = method.parameters();
            if (mParams != null) {
                for (int i = 0; i < mParams.size(); i++) {
                    SingleVariableDeclaration sVar = mParams.get(i);
                    System.out.println("     方法变量：" + sVar.getType().toString() + " " + sVar.getName().toString());
                }
            }

            //方法内的逻辑方法块
            List statements = body.statements();   //get the statements of the method body  
            Iterator iter = statements.iterator();
            while (iter.hasNext()) {
                Statement stmt = (Statement) iter.next();
                String blockType = stmt.getClass().getSimpleName();
                blockType = judgeBlockType(blockType);

                String methodBodyContent = stmt.toString();
                String[] strArray = methodBodyContent.split("\n");
                //****************遍历代码块的每行****************
                for (int i = 0; i < strArray.length; i++) {
                    String methodOneLineContext = strArray[i];
                    //方法内的类变量
                    for (String oneUsingClazz : _usingClass.keySet()) {
                        String clzVarName = getClzVarName(oneUsingClazz, methodOneLineContext); 
                        if (clzVarName != null) {
                            if("combineAssetInfo".equals(clzVarName)){
                                System.out.println("combineAssetInfo");
                            }
                            _classParams4Method.put(clzVarName, _usingClass.get(oneUsingClazz));
                        }
                    }

                    //****************遍历类参数+方法内参数****************
                    Iterator<String> itr = _classParams4Method.keySet().iterator();
                    Set<String> funNameSet = new HashSet();   //这一行中已经判定的方法名称
                    while (itr.hasNext()) {
                        String clazzParamName = itr.next();   //类参数名称
                        String txt = methodOneLineContext;
                        int idx = -1;
                        while ((idx = txt.indexOf(clazzParamName + ".")) >= 0) {
                            txt = txt.substring(idx + clazzParamName.length() + 1);
                            String funName = null;
                            if (txt.indexOf("(") > 0) {
                                funName = txt.substring(0, txt.indexOf("("));
                            } else {
                                funName = "";
                            }
                            if ("".equals(funName) || checkNameOk(funName) == false) {  //获得的方法名要符合规范
                                continue;
                            }
                            funNameSet.add(funName);   //这一行的方法名已经有主了，归于某个具体的类了
                            txt = txt.substring(txt.indexOf("("));
                            System.out.println(_fullClassName + "#" + method.getName().toString()+"调用了方法："+_classParams4Method.get(clazzParamName) + "#" + funName);
                        }
                    }

                    //****************遍历本类方法****************
                    itr = _methodNames.iterator();
                    while (itr.hasNext()) {
                        String methodName = itr.next();   //类参数名称
                        if (methodName.equals(method.getName().toString())) {   //跳过自己
                            continue;
                        }
                        if (funNameSet.contains(methodName)) {   //方法如果已经被用过了，则不能再被本地方法所使用，除非包含这样的语句a.funname(funname())
                            continue;
                        }
                        String txt = methodOneLineContext;
                        if (txt.indexOf(methodName + "(") >= 0) {
                            System.out.println(_fullClassName + "#" + method.getName().toString()+"调用了方法："+_fullClassName + "#" + method.getName().toString());
                        }
                    }

                    //****************遍历静态类方法****************
                    itr = _usingClass.keySet().iterator();
                    while (itr.hasNext()) {
                        String clazzClzName = itr.next();   //类名称
                        String txt = methodOneLineContext;
                        int idx = -1;
                        while ((idx = txt.indexOf(clazzClzName + ".")) >= 0) {
                            txt = txt.substring(idx + clazzClzName.length() + 1);
                            String funName = null;
                            if (txt.indexOf("(") > 0) {
                                funName = txt.substring(0, txt.indexOf("("));
                            } else {
                                funName = "";
                            }
                            if ("".equals(funName) || checkNameOk(funName) == false) {  //获得的方法名要符合规范
                                continue;
                            }
                            funNameSet.add(funName);   //这一行的方法名已经有主了，归于某个具体的类了
                            txt = txt.substring(txt.indexOf("("));
                            System.out.println(_fullClassName + "#" + method.getName().toString()+"调用了方法："+_usingClass.get(clazzClzName) + "#" + funName);
                        }
                    }

                }
            }
        }

    }

    /**
     * 根据类名称，找到一行代码中的类变量
     */
    private static String getClzVarName(String clzName, String strContent) {
        String str = strContent.trim();
        String regex = "^" + clzName + "[ ]+[a-zA-Z_]+[a-zA-Z0-9_\\\\$]*[ ]*[=,;]+";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String match = str.substring(start, end);
            match = match.substring(match.indexOf(clzName) + clzName.length());
            match = match.replaceAll(";", "").replaceAll("=", "").trim();
            return match;
        }
        return null;
    }

    private static String[] resolveImport(ImportDeclaration importDec) {
        String name = importDec.getName().toString();
        if (name.startsWith("java.") || name.startsWith("sun")) {
            return null;
        }
        String shortName = name.substring(name.lastIndexOf(".") + 1);
        return new String[]{shortName, name};
    }

    private static String read(String filename) throws IOException {
        File file = new File(filename);
        byte[] b = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(b);
        return new String(b, "UTF-8");

    }
    
    private static boolean checkNameOk(String ss) {
        String regex = "^[a-zA-Z_]+[a-zA-Z0-9_\\$]*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(ss);
        return m.matches();
    }
    
    /**
     * 判断代码块的类型
     */
    private static String judgeBlockType(String blockType) {
        String result = "";
        if ("EnhancedForStatement".equals(blockType)) {
            return "for";
        } else if ("IfStatement".equals(blockType)) {
            return "if";
        } else if ("WhileStatement".equals(blockType)) {
            return "while";
        }
        return result;
    }
}
