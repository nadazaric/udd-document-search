package com.udd.back.core.constants;

import java.util.regex.Pattern;

public class RegexPattern {

    // PARSING REGEX
    public static final Pattern ANALYST         = Pattern.compile("(?im)forensic\\s*analyst\\s*name\\s*[:\\-]\\s*(.+)$");
    public static final Pattern ORG             = Pattern.compile("(?im)cert\\s*organization\\s*[:\\-]\\s*(.+)$");
    public static final Pattern MALWARE         = Pattern.compile("(?im)(?:malware|threat)\\s*name\\s*[:\\-]\\s*(.+)$");
    public static final Pattern DESCRIPTION     = Pattern.compile("(?im)description\\s*[:\\-]\\s*(.+)$");
    public static final Pattern CLASSIFICATION  = Pattern.compile("(?im)threat\\s*classification\\s*[:\\-]\\s*(.+)$");
    public static final Pattern ADDRESS         = Pattern.compile("(?im)address\\s*[:\\-]\\s*(.+)$");
    public static final Pattern HASH            = Pattern.compile("(?im)\\b(?:md5|sha-?256)\\b\\s*[:\\-]?\\s*([a-f0-9]{32}|[a-f0-9]{64})\\b");

}
