package jbl.assertion.generator.getters;

import java.util.HashMap;
import java.util.Map;

public class DefaultValueGenerator {

    private static final Map<String, String> commonTypes = new HashMap<String, String>() {{
        put("boolean", "false");
        put("java.lang.Boolean", "false");
        put("int", "0");
        put("java.lang.Integer", "0");
        put("long", "0L");
        put("java.lang.Long", "0L");
        put("byte", "(byte)0");
        put("java.lang.Byte", "(byte)0");
        put("short", "(short)0");
        put("java.lang.Short", "(short)0");
        put("float", "0.0F");
        put("java.lang.Float", "0.0F");
        put("char", "\'\'");
        put("java.lang.Character", "\'\'");
        put("double", "0.0D");
        put("java.lang.Double", "0.0D");
        put("java.lang.String", "\"\"");
        put("java.math.BigDecimal", "new BigDecimal(\"0\")");
        put("java.time.LocalDateTime", "LocalDateTime.now()");
        put("java.time.LocalDate", "LocalDate.now()");
        put("java.util.Date", "new Date()");
    }};

    public static String getDefaultValueForClass(final String clazz) {
        return commonTypes.getOrDefault(clazz, "new Object()");
    }

}
