package edu.java.bot.util;

import edu.java.bot.service.AcceptableResource;
import java.net.URL;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlChecker {
    public static boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return matchesAnyPattern(url);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean matchesAnyPattern(String url) {
        for (AcceptableResource resource : AcceptableResource.values()) {
            if (resource.matches(url)) {
                return true;
            }
        }
        return false;
    }
}
