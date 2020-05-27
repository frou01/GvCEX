/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectorRaw
/*     */ {
/*     */   public static Field getField(Class cls, Class<?> fieldType) {
/*     */     try {
/*  28 */       Field[] fileds = cls.getDeclaredFields();
/*  29 */       for (int i = 0; i < fileds.length; ) {
/*     */         
/*  31 */         Field field = fileds[i];
/*  32 */         if (field.getType() != fieldType) {
/*     */           i++; continue;
/*     */         } 
/*  35 */         field.setAccessible(true);
/*     */         
/*  37 */         return field;
/*     */       } 
/*     */       
/*  40 */       return null;
/*     */     }
/*  42 */     catch (Exception e) {
/*     */ 
/*     */       
/*  45 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getFields(Class cls, Class fieldType) {
/*     */     try {
/*  53 */       Field[] fields = cls.getDeclaredFields();
/*  54 */       return getFields(fields, fieldType);
/*     */     }
/*  56 */     catch (Exception e) {
/*     */ 
/*     */       
/*  59 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getFields(Field[] fields, Class<?> fieldType) {
/*     */     try {
/*  67 */       List<Field> list = new ArrayList();
/*  68 */       for (int i = 0; i < fields.length; i++) {
/*     */         
/*  70 */         Field field = fields[i];
/*  71 */         if (field.getType() == fieldType) {
/*     */ 
/*     */           
/*  74 */           field.setAccessible(true);
/*     */           
/*  76 */           list.add(field);
/*     */         } 
/*     */       } 
/*  79 */       Field[] fs = list.<Field>toArray(new Field[list.size()]);
/*     */       
/*  81 */       return fs;
/*     */     }
/*  83 */     catch (Exception e) {
/*     */ 
/*     */       
/*  86 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getFieldsAfter(Class cls, Field field, Class fieldType) {
/*     */     try {
/*  95 */       Field[] fields = cls.getDeclaredFields();
/*  96 */       List<Field> list = Arrays.asList(fields);
/*     */       
/*  98 */       int posStart = list.indexOf(field);
/*  99 */       if (posStart < 0) {
/* 100 */         return new Field[0];
/*     */       }
/* 102 */       List<Field> listAfter = list.subList(posStart + 1, list.size());
/*     */       
/* 104 */       Field[] fieldsAfter = listAfter.<Field>toArray(new Field[listAfter.size()]);
/*     */       
/* 106 */       return getFields(fieldsAfter, fieldType);
/*     */     }
/* 108 */     catch (Exception e) {
/*     */ 
/*     */       
/* 111 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getFields(Object obj, Field[] fields, Class<?> fieldType, Object value) {
/*     */     try {
/* 119 */       List<Field> list = new ArrayList<Field>();
/* 120 */       for (int i = 0; i < fields.length; i++) {
/*     */         
/* 122 */         Field field = fields[i];
/*     */         
/* 124 */         if (field.getType() == fieldType) {
/*     */ 
/*     */           
/* 127 */           boolean staticField = Modifier.isStatic(field.getModifiers());
/*     */           
/* 129 */           if (obj != null || staticField)
/*     */           {
/*     */             
/* 132 */             if (obj == null || !staticField) {
/*     */ 
/*     */               
/* 135 */               field.setAccessible(true);
/*     */               
/* 137 */               Object fieldVal = field.get(obj);
/*     */               
/* 139 */               if (fieldVal == value) {
/*     */                 
/* 141 */                 list.add(field);
/*     */ 
/*     */               
/*     */               }
/* 145 */               else if (fieldVal != null && value != null && fieldVal.equals(value)) {
/*     */                 
/* 147 */                 list.add(field);
/*     */               } 
/*     */             }  } 
/*     */         } 
/* 151 */       }  Field[] fs = list.<Field>toArray(new Field[list.size()]);
/*     */       
/* 153 */       return fs;
/*     */     }
/* 155 */     catch (Exception e) {
/*     */ 
/*     */       
/* 158 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Field getField(Class cls, Class fieldType, int index) {
/* 164 */     Field[] fields = getFields(cls, fieldType);
/* 165 */     if (index < 0 || index >= fields.length) {
/* 166 */       return null;
/*     */     }
/* 168 */     return fields[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public static Field getFieldAfter(Class cls, Field field, Class fieldType, int index) {
/* 173 */     Field[] fields = getFieldsAfter(cls, field, fieldType);
/* 174 */     if (index < 0 || index >= fields.length) {
/* 175 */       return null;
/*     */     }
/* 177 */     return fields[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getFieldValue(Object obj, Class cls, Class fieldType) {
/* 184 */     ReflectorField field = getReflectorField(cls, fieldType);
/*     */     
/* 186 */     if (field == null)
/* 187 */       return null; 
/* 188 */     if (!field.exists()) {
/* 189 */       return null;
/*     */     }
/* 191 */     return Reflector.getFieldValue(obj, field);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getFieldValue(Object obj, Class cls, Class fieldType, int index) {
/* 198 */     ReflectorField field = getReflectorField(cls, fieldType, index);
/*     */     
/* 200 */     if (field == null)
/* 201 */       return null; 
/* 202 */     if (!field.exists()) {
/* 203 */       return null;
/*     */     }
/* 205 */     return Reflector.getFieldValue(obj, field);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setFieldValue(Object obj, Class cls, Class fieldType, Object value) {
/* 212 */     ReflectorField field = getReflectorField(cls, fieldType);
/*     */     
/* 214 */     if (field == null)
/* 215 */       return false; 
/* 216 */     if (!field.exists()) {
/* 217 */       return false;
/*     */     }
/* 219 */     return Reflector.setFieldValue(obj, field, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setFieldValue(Object obj, Class cls, Class fieldType, int index, Object value) {
/* 226 */     ReflectorField field = getReflectorField(cls, fieldType, index);
/*     */     
/* 228 */     if (field == null)
/* 229 */       return false; 
/* 230 */     if (!field.exists()) {
/* 231 */       return false;
/*     */     }
/* 233 */     return Reflector.setFieldValue(obj, field, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReflectorField getReflectorField(Class cls, Class fieldType) {
/* 241 */     Field field = getField(cls, fieldType);
/* 242 */     if (field == null) {
/* 243 */       return null;
/*     */     }
/* 245 */     ReflectorClass rc = new ReflectorClass(cls);
/*     */     
/* 247 */     return new ReflectorField(rc, field.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReflectorField getReflectorField(Class cls, Class fieldType, int index) {
/* 255 */     Field field = getField(cls, fieldType, index);
/* 256 */     if (field == null) {
/* 257 */       return null;
/*     */     }
/* 259 */     ReflectorClass rc = new ReflectorClass(cls);
/*     */     
/* 261 */     return new ReflectorField(rc, field.getName());
/*     */   }
/*     */ }


/* Location:              C:\Minecraft\.minecraft1.7.10.forge_test_HMG_release_4_13\mods\OptiFine_1.7.10_HD_U_E7.jar!\ReflectorRaw.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */