package ru.happy_giraffe.androidbehaviors.utils;

import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import ru.happy_giraffe.androidbehaviors.annotations.BExtra;
import ru.happy_giraffe.androidbehaviors.behaviors.ActivityBehavior;
import ru.happy_giraffe.androidbehaviors.exceptions.NotSupportedExtrasType;

/**
 * Created by JimmDiGriz on 26.12.2016.
 */
public class ExtrasLoader {
    private static Field field;
    private static String name;
    private static AppCompatActivity activity;
    private static ActivityBehavior behavior;

    private static Map<Class, Runnable> configs;

    private static Map<Class, Runnable> getConfigs() {
        if (configs != null) {
            return configs;
        }

        configs = new HashMap<>();

        configs.put(String.class, new Runnable() {
            @Override
            public void run() {
                loadString();
            }
        });

        configs.put(Integer.class, new Runnable() {
            @Override
            public void run() {
                loadInt();
            }
        });

        configs.put(Float.class, new Runnable() {
            @Override
            public void run() {
                loadFloat();
            }
        });

        configs.put(Double.class, new Runnable() {
            @Override
            public void run() {
                loadDouble();
            }
        });

        configs.put(Boolean.class, new Runnable() {
            @Override
            public void run() {
                loadBoolean();
            }
        });

        configs.put(String[].class, new Runnable() {
            @Override
            public void run() {
                loadStringArr();
            }
        });

        configs.put(Integer[].class, new Runnable() {
            @Override
            public void run() {
                loadIntArr();
            }
        });

        configs.put(Float[].class, new Runnable() {
            @Override
            public void run() {
                loadFloatArr();
            }
        });

        return configs;
    }

    @SuppressWarnings("unchecked")
    public static void loadExtras(ActivityBehavior b, AppCompatActivity a) {
        behavior = b;
        activity = a;

        for (Field f: b.getClass().getFields()) {
            try {
                field = f;
                if (!field.isAnnotationPresent(BExtra.class)) {
                    continue;
                }

                BExtra annotation = field.getAnnotation(BExtra.class);

                name = annotation.name();

                if (name.equals("unnamed")) {
                    name = field.getName();
                }

                Class type = field.getType();

                if (!getConfigs().containsKey(type)) {
                    throw new NotSupportedExtrasType();
                }

                getConfigs().get(type).run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        behavior = null;
        activity = null;
        field = null;
        name = null;
    }

    private static void loadString() {
        try {
            field.set(behavior, activity.getIntent().getExtras().getString(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadFloat() {
        try {
            field.set(behavior, activity.getIntent().getExtras().getFloat(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadInt() {
        try {
            field.set(behavior, activity.getIntent().getExtras().getInt(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadDouble() {
        try {
            field.set(behavior, activity.getIntent().getExtras().getDouble(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadBoolean() {
        try {
            field.set(behavior, activity.getIntent().getExtras().getBoolean(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadIntArr() {
        try {
            field.set(behavior, activity.getIntent().getExtras().getIntArray(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadFloatArr() {
        try {
            field.set(behavior, activity.getIntent().getExtras().getFloatArray(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadStringArr() {
        try {
            field.set(behavior, activity.getIntent().getExtras().getStringArray(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
