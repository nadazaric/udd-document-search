package com.udd.back.core.constants;

import java.util.regex.Pattern;

public class RegexPattern {

    // PARSING REGEX
    public static final String L_ORG = "(?:Organizacija|Организација)";
    public static final String L_CLASS = "(?:Klasifikacija|Класификација)";
    public static final String L_SIGNATURE = "(?:Potpis forenzičara|Потпис форензичара)";
    public static final String L_BEHAVIOR = "(?:Opis\\s+ponašanja\\s+malvera\\s*/\\s*prijetnje|Опис\\s+понашања\\s+малвера\\s*/\\s*пријетње)";

    public static final Pattern ORG_AND_ADDRESS = Pattern.compile(
            "(?s)" + L_ORG + "\\s*:\\s*(.+?)\\s*(?:\\r?\\n)+\\s*(.+?)\\s*(?:\\r?\\n)",
            Pattern.UNICODE_CASE
    );

    public static final Pattern CLASS_AND_HASH = Pattern.compile(
            "(?im)" + L_CLASS + "\\s*:\\s*(niska|srednja|visoka|kritična|ниска|средња|висока|критична)\\s*,\\s*([a-fA-F0-9]{32,64})\\b",
            Pattern.UNICODE_CASE
    );

    public static final Pattern THREAT = Pattern.compile(
            "(?is)(?:ukazuje\\s+na|указује\\s+на)\\s+(.+?)\\s*\\.",
            Pattern.UNICODE_CASE
    );

    public static final Pattern BEHAVIOR_AND_ANALYST = Pattern.compile(
            "(?s)" + L_BEHAVIOR + "\\s*:\\s*(.+?)\\s*(?:\\r?\\n)+\\s*([\\p{L}]+\\s+[\\p{L}]+)\\s*(?:\\r?\\n)+\\s*" + L_SIGNATURE,
            Pattern.UNICODE_CASE
    );

    // OTHER
    public static final Pattern TERM_PATTERN = Pattern.compile("([a-zA-Z][a-zA-Z0-9_]*)\\s*:\\s*(\"([^\"]*)\"|[^\\s()]+)");

}
