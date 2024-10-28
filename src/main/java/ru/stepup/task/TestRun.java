package ru.stepup.task;

import com.sun.jdi.Value;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class TestRun {


    private byte cntBeforeSuite = 0;
    private byte cntAfterSuite = 0;
    private Method methodBefore;
    private Method methodAfter;
    private HashMap<Method, Integer> methodIntegerHashMap;
    private List<Object> objectListReturn;
    public void run (Class clz) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        this.methodIntegerHashMap = new HashMap<>();

        Object object = clz.newInstance();

        Map<Method, Integer> sorted = new LinkedHashMap<>();

        for(Method method:clz.getDeclaredMethods()){
            //System.out.println(method);

            if (method.isAnnotationPresent(BeforeSuite.class)) {
                this.addCnt("BeforeSuite");
                this.methodBefore = method;
                continue;
            }

            if (method.isAnnotationPresent(AfterSuite.class)) {
                this.addCnt("AfterSuite");
                this.methodAfter = method;
                continue;
            }
            if (method.isAnnotationPresent(Test.class)) methodIntegerHashMap.put(method, method.getAnnotation(Test.class).value());

//            Test test = method.getAnnotation(Test.class);
        }
        sorted = methodIntegerHashMap.entrySet().stream()
//                .sorted(Comparator.comparingInt(e -> e.getValue()))
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a,b) -> {throw new AssertionError();},
                        LinkedHashMap::new
                ));

        this.runMethods(sorted, object);


    }
    private void runMethods(Map<Method, Integer> sorted, Object object) throws InvocationTargetException, IllegalAccessException {



        Object objectRet = new Object();
        if (this.methodBefore != null){
            objectRet = runMethod(this.methodBefore, object);
            if (objectRet != null) objectListReturn.add(objectRet);
        }
        sorted.entrySet().forEach(System.out::println);
        Iterator<Map.Entry<Method, Integer>> itr = methodIntegerHashMap.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Method, Integer> entry = itr.next();
            System.out.println(entry.getKey() + "=" + entry.getValue());
            Method mtd = entry.getKey();
            objectRet = runMethod(mtd, object);
            if (objectRet != null) objectListReturn.add(objectRet);

        }

        if (this.methodAfter != null){
            objectRet = runMethod(this.methodAfter, object);
            if (objectRet != null) objectListReturn.add(objectRet);
        }
    }

    private Object[] getParam(String str, Object[] obj){
        Object[] arg = new Object[obj.length];
        int idx = 0;
        for(String word : str.split(",")){
            arg[idx] = word;
            idx++;
        }
        return arg;
    }
    private Object runMethod(Method mtd, Object obj) throws InvocationTargetException, IllegalAccessException {
        if (mtd.isAnnotationPresent(CsvSource.class)) return mtd.invoke(obj, getParam(mtd.getAnnotation(CsvSource.class).value(), mtd.getParameters()));
        else return mtd.invoke(obj);
    }

    private void addCnt(String atr) {
        if (Objects.equals(atr, "BeforeSuite")) this.cntBeforeSuite++;

        if (Objects.equals(atr, "AfterSuite")) this.cntAfterSuite++;

        if (this.cntBeforeSuite > 1) throw new IllegalArgumentException("Анотация BeforeSuite может быть только у одного метода.");
    }
}

