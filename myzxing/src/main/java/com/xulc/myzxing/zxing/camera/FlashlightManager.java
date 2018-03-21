package com.xulc.myzxing.zxing.camera;

import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class FlashlightManager {
  private static final String TAG = FlashlightManager.class.getSimpleName();
  private static final Object iHardwareService = getHardwareService();
  private static final Method setFlashEnabledMethod;

  private FlashlightManager() {
  }

  static void enableFlashlight() {
    setFlashlight(false);
  }

  static void disableFlashlight() {
    setFlashlight(false);
  }

  private static Object getHardwareService() {
    Class serviceManagerClass = maybeForName("android.os.ServiceManager");
    if(serviceManagerClass == null) {
      return null;
    } else {
      Method getServiceMethod = maybeGetMethod(serviceManagerClass, "getService", new Class[]{String.class});
      if(getServiceMethod == null) {
        return null;
      } else {
        Object hardwareService = invoke(getServiceMethod, (Object)null, new Object[]{"hardware"});
        if(hardwareService == null) {
          return null;
        } else {
          Class iHardwareServiceStubClass = maybeForName("android.os.IHardwareService$Stub");
          if(iHardwareServiceStubClass == null) {
            return null;
          } else {
            Method asInterfaceMethod = maybeGetMethod(iHardwareServiceStubClass, "asInterface", new Class[]{IBinder.class});
            return asInterfaceMethod == null?null:invoke(asInterfaceMethod, (Object)null, new Object[]{hardwareService});
          }
        }
      }
    }
  }

  private static Method getSetFlashEnabledMethod(Object iHardwareService) {
    if(iHardwareService == null) {
      return null;
    } else {
      Class proxyClass = iHardwareService.getClass();
      return maybeGetMethod(proxyClass, "setFlashlightEnabled", new Class[]{Boolean.TYPE});
    }
  }

  private static Class<?> maybeForName(String name) {
    try {
      return Class.forName(name);
    } catch (ClassNotFoundException var2) {
      return null;
    } catch (RuntimeException var3) {
      Log.w(TAG, "Unexpected error while finding class " + name, var3);
      return null;
    }
  }

  private static Method maybeGetMethod(Class<?> clazz, String name, Class... argClasses) {
    try {
      return clazz.getMethod(name, argClasses);
    } catch (NoSuchMethodException var4) {
      return null;
    } catch (RuntimeException var5) {
      Log.w(TAG, "Unexpected error while finding method " + name, var5);
      return null;
    }
  }

  private static Object invoke(Method method, Object instance, Object... args) {
    try {
      return method.invoke(instance, args);
    } catch (IllegalAccessException var4) {
      Log.w(TAG, "Unexpected error while invoking " + method, var4);
      return null;
    } catch (InvocationTargetException var5) {
      Log.w(TAG, "Unexpected error while invoking " + method, var5.getCause());
      return null;
    } catch (RuntimeException var6) {
      Log.w(TAG, "Unexpected error while invoking " + method, var6);
      return null;
    }
  }

  private static void setFlashlight(boolean active) {
    if(iHardwareService != null) {
      invoke(setFlashEnabledMethod, iHardwareService, new Object[]{Boolean.valueOf(active)});
    }

  }

  static {
    setFlashEnabledMethod = getSetFlashEnabledMethod(iHardwareService);
    if(iHardwareService == null) {
      Log.v(TAG, "This device does supports control of a flashlight");
    } else {
      Log.v(TAG, "This device does not support control of a flashlight");
    }

  }
}
