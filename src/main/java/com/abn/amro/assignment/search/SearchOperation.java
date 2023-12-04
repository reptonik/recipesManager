package com.abn.amro.assignment.search;

public enum SearchOperation {
    CONTAINS, DOES_NOT_CONTAIN, EQUAL, NOT_EQUAL, ANY, ALL;
    public static final String[] SIMPLE_OPERATION_SET = {
            "cn", "nc", "eq", "ne" };

    public static SearchOperation getSerachOption(final String searchOption){
        return switch (searchOption) {
            case "all" -> ALL;
            case "any" -> ANY;
            default -> null;
        };
    }

    public static SearchOperation getSimpleOperation(
            final String input) {
        return switch (input) {
            case "cn" -> CONTAINS;
            case "nc" -> DOES_NOT_CONTAIN;
            case "eq" -> EQUAL;
            case "ne" -> NOT_EQUAL;
            default -> null;
        };
    }
}
