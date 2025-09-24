package org.project;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class PathNormalizer {
  
  private static final List<ReplacementRule> RULES = Arrays.asList(
      // Новые правила для project и service account
//      new ReplacementRule(
//          Pattern.compile("projects/proj-[a-zA-Z0-9]/service_accounts/sa_proj-[a-zA-Z0-9]-[a-zA-Z0-9]+"),
//          "projects/../service_accounts/.."
//      ),
      new ReplacementRule(
          Pattern.compile("/projects/proj-[a-zA-Z0-9]{3,}"),
          "/projects/..."
      ),
      new ReplacementRule(
          Pattern.compile("/proj-[a-zA-Z0-9]"),
          "/..."
      ),
      new ReplacementRule(
          Pattern.compile("/fold-[a-zA-Z0-9]{3,}"),
          "/..."
      ),
      new ReplacementRule(
          Pattern.compile("/grp-[a-zA-Z0-9]{3,}/"),
          "/..."
      ),
      new ReplacementRule(
          Pattern.compile("/sa_proj-[a-zA-Z0-9]{3,}-[a-zA-Z0-9]+"),
          "/..."
      ),
      new ReplacementRule(
          Pattern.compile("/personal/[a-zA-Z]{3,}/"),
          "/personal/..."
      ),
// Еще более универсальная - для любого пути после resource-manager
      new ReplacementRule(
          Pattern.compile("/resource-manager%2F[^%]+%2[a-zA-Z0-9]+-[a-zA-Z0-9]+"),
          "/resource-manager..."
      ),
      new ReplacementRule(
          Pattern.compile("(^|[^/])/([^/]*(\\d[^/]*){4,}([^/]*|$))"),  // 4+ цифр, но не после //
          "$1/..."
      ),
      new ReplacementRule(
          Pattern.compile("/vtb\\d{4,}"),  // 4 или более цифр
          "/..."
      ),
      new ReplacementRule(Pattern.compile("/\\d+"), "/..."),
      new ReplacementRule(Pattern.compile("/v\\d+"), ""),
      new ReplacementRule(Pattern.compile("/\\d+/edit"), "/.../edit"),
      new ReplacementRule(Pattern.compile("/\\d+/delete"), "/.../delete"),
      new ReplacementRule(Pattern.compile("/\\d+/update"), "/.../update"),
      new ReplacementRule(Pattern.compile("/users/\\d+"), "/users/..."),
      new ReplacementRule(Pattern.compile("/products/\\d+"), "/products/...")
  );
  
  public static String normalizePathWithDots(String path) {
    if (path == null || path.isEmpty()) {
      return path;
    }
    
    String normalized = path;
    for (ReplacementRule rule : RULES) {
      normalized = rule.pattern.matcher(normalized).replaceAll(rule.replacement);
      
    }
    
    return normalized;
  }
  
  private static class ReplacementRule {
    Pattern pattern;
    String replacement;
    
    ReplacementRule(Pattern pattern, String replacement) {
      this.pattern = pattern;
      this.replacement = replacement;
    }
  }
  
}