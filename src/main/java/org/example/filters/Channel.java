package org.example.filters;

public enum Channel {
        NONE,
        FIRST,
        SECOND,
        THIRD;

        public static Channel fromInteger(int x) {
                return switch (x) {
                        case 0 -> NONE;
                        case 1 -> FIRST;
                        case 2 -> SECOND;
                        case 3 -> THIRD;
                        default -> null;
                };
        }

        public static int toInteger(Channel x) {
                return switch (x) {
                        case NONE -> 0;
                        case FIRST -> 1;
                        case SECOND -> 2;
                        case THIRD -> 3;
                };
        }
}
