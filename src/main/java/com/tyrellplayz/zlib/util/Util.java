package com.tyrellplayz.zlib.util;

import com.tyrellplayz.zlib.ZLib;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.logging.log4j.core.util.Booleans;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;

public class Util {

    private Util() {}

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        }catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static <T> List<T> getInstances(Class<?> annotationClass, Class<T> instanceClass) {
        Type annotationType = Type.getType(annotationClass);
        List<ModFileScanData> allScanData = ModList.get().getAllScanData();
        List<String> classNames = new ArrayList<>();

        for (ModFileScanData scanData : allScanData) {
            for (ModFileScanData.AnnotationData annotation : scanData.getAnnotations()) {
                if(annotation.getAnnotationType().equals(annotationType)) classNames.add(annotation.getMemberName());
            }
        }

        List<T> instances = new ArrayList<>();
        for (String className : classNames) {
            try {
                Class<?> asmClass = Class.forName(className);
                Class<? extends T> asmInstanceClass = asmClass.asSubclass(instanceClass);
                instances.add(asmInstanceClass.newInstance());
            } catch (LinkageError | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                ZLib.LOGGER.error("Failed to load : {}",className,e);
            }
        }

        return instances;
    }

}
