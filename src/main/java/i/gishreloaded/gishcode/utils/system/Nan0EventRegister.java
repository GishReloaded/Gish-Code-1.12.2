package i.gishreloaded.gishcode.utils.system;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.reflect.TypeToken;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.ASMEventHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.IEventListener;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class Nan0EventRegister {

    public static void register(EventBus bus, Object target) {
        ConcurrentHashMap<Object, ArrayList<IEventListener>> listeners = ReflectionHelper.getPrivateValue(EventBus.class, bus, "listeners");
        Map<Object, ModContainer> listenerOwners = ReflectionHelper.getPrivateValue(EventBus.class, bus, "listenerOwners");
        if (listeners.containsKey(target)) {
            return;
        }
        ModContainer activeModContainer = Loader.instance().getMinecraftModContainer();
        listenerOwners.put(target, activeModContainer);
        ReflectionHelper.setPrivateValue(EventBus.class, bus, listenerOwners, "listenerOwners");
        Set<? extends Class<?>> supers = TypeToken.of(target.getClass()).getTypes().rawTypes();
        for (Method method : target.getClass().getMethods()) {
            for (Class<?> cls : supers) {
                try {
                    Method real = cls.getDeclaredMethod(method.getName(), method.getParameterTypes());
                    if (real.isAnnotationPresent(SubscribeEvent.class)) {
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        Class<?> eventType = parameterTypes[0];
                        register(bus, eventType, target, method, activeModContainer);
                        break;
                    }
                } catch (NoSuchMethodException ignored) {
                }
            }
        }
    }

    private static void register(EventBus bus, Class<?> eventType, Object target, Method method, ModContainer owner) {
        try {
            int busID = ReflectionHelper.getPrivateValue(EventBus.class, bus, "busID");
            ConcurrentHashMap<Object, ArrayList<IEventListener>> listeners = ReflectionHelper.getPrivateValue(EventBus.class, bus, "listeners");
            Constructor<?> ctr = eventType.getConstructor();
            ctr.setAccessible(true);
            Event event = (Event) ctr.newInstance();
            ASMEventHandler listener = new ASMEventHandler(target, method, owner);
            event.getListenerList().register(busID, listener.getPriority(), listener);
            ArrayList<IEventListener> others = listeners.get(target);
            if (others == null) {
                others = new ArrayList<IEventListener>();
                listeners.put(target, others);
                ReflectionHelper.setPrivateValue(EventBus.class, bus, listeners, "listeners");
            }
            others.add(listener);
        } catch (Exception e) {
        }
    }
}
