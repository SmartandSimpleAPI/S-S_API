package fr.dojo59.api.provided.Depre_DataBaseAPI.utilsDb;

import java.lang.reflect.Field;

public class FieldTable {

    public void setFieldInt(String fieldName, int value) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.setInt(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setFieldInt(String fieldName, int value, boolean typeMoney) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (value < 0 && typeMoney) {
                field.setInt(this, 0);
                return;
            }
            field.setInt(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setFieldLong(String fieldName, long value) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.setLong(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setFieldLong(String fieldName, long value, boolean typeMoney) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (value < 0 && typeMoney) {
                field.setLong(this, 0L);
                return;
            }
            field.setLong(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setFieldFloat(String fieldName, float value) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.setFloat(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setFieldFloat(String fieldName, float value, boolean typeMoney) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (value < 0 && typeMoney) {
                field.setFloat(this, 0f);
                return;
            }
            field.setFloat(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setFieldDouble(String fieldName, double value) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.setDouble(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setFieldDouble(String fieldName, double value, boolean typeMoney) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (value < 0 && typeMoney) {
                field.setDouble(this, 0.00);
                return;
            }
            field.setDouble(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setFieldBoolean(String fieldName, boolean value) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.setBoolean(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setFieldString(String fieldName, String value) throws IllegalAccessException {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        Field field = findFieldByPartialName(fieldName);
        if (field != null) {
            field.setAccessible(true);
            field.set(this, value);
        } else {

        }
    }

    public Object[] getFieldAll() {
        Field[] fields = this.getClass().getDeclaredFields(); // Récupère tous les champs déclarés dans la classe
        Object[] fieldValues = new Object[fields.length];

        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true); // Permet d'accéder aux champs privés
                fieldValues[i] = fields[i].get(this); // Récupère la valeur du champ
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return fieldValues;
    }

    public int getFieldInt(String fieldName) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.getInt(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Long getFieldLong(String fieldName) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.getLong(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public Float getFieldFloat(String fieldName) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.getFloat(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0f;
    }

    public double getFieldDouble(String fieldName) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.getDouble(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public boolean getFieldBoolean(String fieldName) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.getBoolean(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getFieldString(String fieldName) throws IllegalAccessException {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        Field field = findFieldByPartialName(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return (String) field.get(this);
        }
        return null;
    }

    public void incrementFieldInt(String fieldName, int value) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType() == int.class) {
                int currentValue = field.getInt(this);
                field.setInt(this, currentValue + value);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void incrementFieldInt(String fieldName, int value, boolean typeMoney) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType() == int.class) {
                int currentValue = field.getInt(this);
                int result = currentValue + value;
                if (result < 0 && typeMoney) {
                    field.setInt(this, 0);
                    return;
                }
                field.setInt(this, result);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void incrementFieldLong(String fieldName, long value) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType() == int.class) {
                long currentValue = field.getLong(this);
                field.setLong(this, currentValue + value);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void incrementFieldLong(String fieldName, long value, boolean typeMoney) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType() == int.class) {
                long currentValue = field.getLong(this);
                long result = currentValue + value;
                if (result < 0 && typeMoney) {
                    field.setLong(this, 0L);
                    return;
                }
                field.setLong(this, result);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void incrementFieldFloat(String fieldName, float value) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType() == int.class) {
                float currentValue = field.getFloat(this);
                field.setFloat(this, currentValue + value);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void incrementFieldFloat(String fieldName, float value, boolean typeMoney) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType() == int.class) {
                float currentValue = field.getFloat(this);
                float result = currentValue + value;
                if (result < 0 && typeMoney) {
                    field.setFloat(this, 0f);
                    return;
                }
                field.setFloat(this, result);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour incrémenter un champ de type double
    public void incrementFieldDouble(String fieldName, double value) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType() == double.class) {
                double currentValue = field.getDouble(this);
                field.setDouble(this, currentValue + value);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void incrementFieldDouble(String fieldName, double value, boolean typeMoney) {
        if (Character.isUpperCase(fieldName.charAt(0))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType() == double.class) {
                double currentValue = field.getDouble(this);
                double result = currentValue + value;
                if (result < 0 && typeMoney) {
                    field.setDouble(this, 0.00);
                    return;
                }
                field.setDouble(this, result);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Field findFieldByPartialName(String fieldNamePart) {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getName().contains(fieldNamePart)) {
                return field;
            }
        }
        return null;
    }
}
