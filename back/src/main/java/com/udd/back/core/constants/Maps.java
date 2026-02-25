package com.udd.back.core.constants;

import java.util.Map;

public class Maps {

    public static final Map<String, Boolean> FIELDS = Map.of(
            "forensicAnalystName", false,
            "certOrganization", false,
            "malwareOrThreatName", false,
            "behaviorDescription", false,
            "hash", true,
            "threatClassification", true
    );

}
