package ru.happy_giraffe.androidbehaviors.utils;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ru.happy_giraffe.androidbehaviors.annotations.resources.BAnimationRes;
import ru.happy_giraffe.androidbehaviors.annotations.resources.BBooleanRes;
import ru.happy_giraffe.androidbehaviors.annotations.resources.BColorRes;
import ru.happy_giraffe.androidbehaviors.annotations.resources.BColorStateListRes;
import ru.happy_giraffe.androidbehaviors.annotations.resources.BDrawableRes;
import ru.happy_giraffe.androidbehaviors.annotations.resources.BIntArrayRes;
import ru.happy_giraffe.androidbehaviors.annotations.resources.BIntRes;
import ru.happy_giraffe.androidbehaviors.annotations.resources.BLayoutRes;
import ru.happy_giraffe.androidbehaviors.annotations.resources.BStringArrayRes;
import ru.happy_giraffe.androidbehaviors.annotations.resources.BStringRes;
import ru.happy_giraffe.androidbehaviors.annotations.resources.BTextArrayRes;
import ru.happy_giraffe.androidbehaviors.annotations.resources.BTextRes;
import ru.happy_giraffe.androidbehaviors.core.Behavior;
import ru.happy_giraffe.androidbehaviors.core.Container;

/**
 * Created by JimmDiGriz on 26.12.2016.
 */
public class ResourcesLoader {
    private static List<ResourceConfig> configs;

    private static Field field;
    private static Annotation annotation;
    private static AppCompatActivity activity;
    private static Behavior behavior;

    private static List<ResourceConfig> getConfigs() {
        if (configs != null) {
            return configs;
        }

        configs = new ArrayList<>();

        configs.add(new ResourceConfig(BIntRes.class, Integer.class, new ResourceLoader() {
            @Override
            public void load() {
                loadIntResources();
            }
        }));

        configs.add(new ResourceConfig(BStringRes.class, String.class, new ResourceLoader() {
            @Override
            public void load() {
                loadStringResources();
            }
        }));

        configs.add(new ResourceConfig(BColorRes.class, Integer.class, new ResourceLoader() {
            @Override
            public void load() {
                loadColorResources();
            }
        }));

        configs.add(new ResourceConfig(BDrawableRes.class, Drawable.class, new ResourceLoader() {
            @Override
            public void load() {
                loadDrawableResources();
            }
        }));

        configs.add(new ResourceConfig(BAnimationRes.class, XmlResourceParser.class, new ResourceLoader() {
            @Override
            public void load() {
                loadAnimationResources();
            }
        }));

        configs.add(new ResourceConfig(BBooleanRes.class, Boolean.class, new ResourceLoader() {
            @Override
            public void load() {
                loadBooleanResources();
            }
        }));

        configs.add(new ResourceConfig(BColorStateListRes.class, ColorStateList.class, new ResourceLoader() {
            @Override
            public void load() {
                loadColorStateListResources();
            }
        }));

        configs.add(new ResourceConfig(BIntArrayRes.class, Integer[].class, new ResourceLoader() {
            @Override
            public  void load() {
                loadIntArrayResources();
            }
        }));

        configs.add(new ResourceConfig(BLayoutRes.class, XmlResourceParser.class, new ResourceLoader() {
            @Override
            public void load() {
                loadLayoutResources();
            }
        }));

        configs.add(new ResourceConfig(BStringArrayRes.class, String[].class, new ResourceLoader() {
            @Override
            public void load() {
                loadStringArrayResources();
            }
        }));

        configs.add(new ResourceConfig(BTextArrayRes.class, CharSequence[].class, new ResourceLoader() {
            @Override
            public void load() {
                loadTextArrayResources();
            }
        }));

        configs.add(new ResourceConfig(BTextRes.class, CharSequence.class, new ResourceLoader() {
            @Override
            public void load() {
                loadTextResources();
            }
        }));

        return configs;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Behavior> void loadResources(Class<? extends Behavior> object, AppCompatActivity a, T b) {
        activity = a;
        behavior = b;

        for (Field f: object.getFields()) {
            try {
                field = f;
                for (ResourceConfig config : getConfigs()) {
                    if (!field.isAnnotationPresent(config.annotationClass)) {
                        continue;
                    }

                    Class type = field.getType();

                    if (!config.resultClass.isAssignableFrom(type)) {
                        break;
                    }

                    annotation = field.getAnnotation(config.annotationClass);

                    config.method.load();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        activity = null;
        behavior = null;
        field = null;
        annotation = null;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadIntResources() {
        try {
            int id = ((BIntRes) annotation).value();
            field.set(behavior, activity.getResources().getInteger(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadStringResources() {
        try {
            int id = ((BStringRes) annotation).value();
            field.set(behavior, activity.getResources().getString(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadColorResources() {
        try {
            int id = ((BColorRes) annotation).value();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                field.set(behavior, activity.getResources().getColor(id, null));
            } else {
                field.set(behavior, activity.getResources().getColor(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadDrawableResources() {
        try {
            int id = ((BDrawableRes) annotation).value();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                field.set(behavior, activity.getResources().getDrawable(id, null));
            } else {
                field.set(behavior, activity.getResources().getDrawable(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadAnimationResources() {
        try {
            int id = ((BAnimationRes) annotation).value();
            field.set(behavior, activity.getResources().getAnimation(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadBooleanResources() {
        try {
            int id = ((BBooleanRes) annotation).value();
            field.set(behavior, activity.getResources().getBoolean(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadColorStateListResources() {
        try {
            int id = ((BColorStateListRes) annotation).value();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                field.set(behavior, activity.getResources().getColorStateList(id, null));
            } else {
                field.set(behavior, activity.getResources().getColorStateList(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadIntArrayResources() {
        try {
            int id = ((BIntArrayRes) annotation).value();
            field.set(behavior, activity.getResources().getIntArray(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadLayoutResources() {
        try {
            int id = ((BLayoutRes) annotation).value();
            field.set(behavior, activity.getResources().getLayout(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadStringArrayResources() {
        try {
            int id = ((BStringArrayRes) annotation).value();
            field.set(behavior, activity.getResources().getStringArray(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadTextArrayResources() {
        try {
            int id = ((BTextArrayRes) annotation).value();
            field.set(behavior, activity.getResources().getTextArray(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadTextResources() {
        try {
            int id = ((BTextRes) annotation).value();
            field.set(behavior, activity.getResources().getText(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ResourceConfig {
        Class annotationClass;
        Class resultClass;
        ResourceLoader method;

        ResourceConfig(Class annotationClass, Class resultClass, ResourceLoader method) {
            this.annotationClass = annotationClass;
            this.resultClass = resultClass;
            this.method = method;
        }
    }

    private interface ResourceLoader {
        void load();
    }
}
