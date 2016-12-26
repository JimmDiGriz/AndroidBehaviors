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

    private static List<ResourceConfig> getConfigs() {
        if (configs != null) {
            return configs;
        }

        configs = new ArrayList<>();

        configs.add(new ResourceConfig(BIntRes.class, Integer.class, new ResourceLoader() {
            @Override
            public <T extends Behavior> void load(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
                loadIntResources(field, annotation, activity, behavior);
            }
        }));

        configs.add(new ResourceConfig(BStringRes.class, String.class, new ResourceLoader() {
            @Override
            public <T extends Behavior> void load(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
                loadStringResources(field, annotation, activity, behavior);
            }
        }));

        configs.add(new ResourceConfig(BColorRes.class, Integer.class, new ResourceLoader() {
            @Override
            public <T extends Behavior> void load(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
                loadColorResources(field, annotation, activity, behavior);
            }
        }));

        configs.add(new ResourceConfig(BDrawableRes.class, Drawable.class, new ResourceLoader() {
            @Override
            public <T extends Behavior> void load(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
                loadDrawableResources(field, annotation, activity, behavior);
            }
        }));

        configs.add(new ResourceConfig(BAnimationRes.class, XmlResourceParser.class, new ResourceLoader() {
            @Override
            public <T extends Behavior> void load(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
                loadAnimationResources(field, annotation, activity, behavior);
            }
        }));

        configs.add(new ResourceConfig(BBooleanRes.class, Boolean.class, new ResourceLoader() {
            @Override
            public <T extends Behavior> void load(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
                loadBooleanResources(field, annotation, activity, behavior);
            }
        }));

        configs.add(new ResourceConfig(BColorStateListRes.class, ColorStateList.class, new ResourceLoader() {
            @Override
            public <T extends Behavior> void load(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
                loadColorStateListResources(field, annotation, activity, behavior);
            }
        }));

        configs.add(new ResourceConfig(BIntArrayRes.class, Integer[].class, new ResourceLoader() {
            @Override
            public <T extends Behavior> void load(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
                loadIntArrayResources(field, annotation, activity, behavior);
            }
        }));

        configs.add(new ResourceConfig(BLayoutRes.class, XmlResourceParser.class, new ResourceLoader() {
            @Override
            public <T extends Behavior> void load(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
                loadLayoutResources(field, annotation, activity, behavior);
            }
        }));

        configs.add(new ResourceConfig(BStringArrayRes.class, String[].class, new ResourceLoader() {
            @Override
            public <T extends Behavior> void load(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
                loadStringArrayResources(field, annotation, activity, behavior);
            }
        }));

        configs.add(new ResourceConfig(BTextArrayRes.class, CharSequence[].class, new ResourceLoader() {
            @Override
            public <T extends Behavior> void load(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
                loadTextArrayResources(field, annotation, activity, behavior);
            }
        }));

        configs.add(new ResourceConfig(BTextRes.class, CharSequence.class, new ResourceLoader() {
            @Override
            public <T extends Behavior> void load(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
                loadTextResources(field, annotation, activity, behavior);
            }
        }));

        return configs;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Behavior> void loadResources(Class<? extends Behavior> object, AppCompatActivity activity, T behavior) {
        for (Field field: object.getFields()) {
            try {
                for (ResourceConfig config : getConfigs()) {
                    if (!field.isAnnotationPresent(config.annotationClass)) {
                        continue;
                    }

                    Class type = field.getType();

                    if (!config.resultClass.isAssignableFrom(type)) {
                        break;
                    }

                    config.method.load(field, field.getAnnotation(config.annotationClass), activity, behavior);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadIntResources(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
        try {
            int id = ((BIntRes) annotation).value();
            field.set(behavior, activity.getResources().getInteger(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadStringResources(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
        try {
            int id = ((BStringRes) annotation).value();
            field.set(behavior, activity.getResources().getString(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadColorResources(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
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
    private static <T extends Behavior> void loadDrawableResources(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
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
    private static <T extends Behavior> void loadAnimationResources(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
        try {
            int id = ((BAnimationRes) annotation).value();
            field.set(behavior, activity.getResources().getAnimation(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadBooleanResources(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
        try {
            int id = ((BBooleanRes) annotation).value();
            field.set(behavior, activity.getResources().getBoolean(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadColorStateListResources(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
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
    private static <T extends Behavior> void loadIntArrayResources(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
        try {
            int id = ((BIntArrayRes) annotation).value();
            field.set(behavior, activity.getResources().getIntArray(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadLayoutResources(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
        try {
            int id = ((BLayoutRes) annotation).value();
            field.set(behavior, activity.getResources().getLayout(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadStringArrayResources(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
        try {
            int id = ((BStringArrayRes) annotation).value();
            field.set(behavior, activity.getResources().getStringArray(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadTextArrayResources(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
        try {
            int id = ((BTextArrayRes) annotation).value();
            field.set(behavior, activity.getResources().getTextArray(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Behavior> void loadTextResources(Field field, Annotation annotation, AppCompatActivity activity, T behavior) {
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
        <T extends Behavior> void load(Field field, Annotation annotation, AppCompatActivity activity, T behavior);
    }
}
