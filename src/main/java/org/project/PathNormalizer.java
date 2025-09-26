package org.project;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class PathNormalizer {
  
  private static final List<ReplacementRule> RULES = Arrays.asList(
      // /projects/proj-abc123/ => /projects/.../
      new ReplacementRule(
          Pattern.compile("(/projects/)proj-[a-zA-Z0-9]{3,}(?=/|$)"),
          "$1..."
      ),
      
      // /proj-abc123 => /...
      new ReplacementRule(
          Pattern.compile("(/)proj-[a-zA-Z0-9]{3,}(?=/|$)"),
          "$1..."
      ),
      
      // /fold-abc123/ => /.../
      new ReplacementRule(
          Pattern.compile("(/)fold-[a-zA-Z0-9]{3,}(?=/|$)"),
          "$1..."
      ),
      
      // /grp-1213/asdf => /.../asdf
      new ReplacementRule(
          Pattern.compile("(/)grp-[a-zA-Z0-9]{3,}(?=/|$)"),
          "$1..."
      ),
      
      // /sa_proj-abc123-def => /...
      new ReplacementRule(
          Pattern.compile("(/)sa_proj-[a-zA-Z0-9]{3,}-[a-zA-Z0-9]+(?=/|$)"),
          "$1..."
      ),
      
      // /personal/username/ => /personal/.../
      new ReplacementRule(
          Pattern.compile("(/personal/)[a-zA-Z]{3,}(?=/|$)"),
          "$1..."
      ),
      
      // URL-encoded paths
      new ReplacementRule(
          Pattern.compile("(/resource-manager)%2Fprojects%2Fproj-[a-zA-Z0-9]{3,}+(?=/|$)"),
          "$1..."
      ),
      
      // Сегменты с 4+ цифрами: /4j5n6jjho3k8 => /...
      new ReplacementRule(
          Pattern.compile("(/)([^/]*(\\d[^/]*){5,}[^/]*)(?=/|$)"),
          "$1..."
      ),
      
      // /vtb12345 => /...
      new ReplacementRule(
          Pattern.compile("(/)vtb\\d{4,}(?=/|$)"),
          "$1..."
      ),
      
      // Числовые ID: /12345 => /...
      new ReplacementRule(
          Pattern.compile("(/)\\d+(?=/|$)"),
          "$1..."
      ),
      
//      // Версии: /v1 => / (убираем полностью)
//      new ReplacementRule(
//          Pattern.compile("/v\\d+"),
//          ""
//      ),
      
//      // Контекстные замены: /12345/edit => /.../edit
//      new ReplacementRule(
//          Pattern.compile("(/)\\d+(/edit)"),
//          "$1...$2"
//      ),
//      new ReplacementRule(
//          Pattern.compile("(/)\\d+(/delete)"),
//          "$1...$2"
//      ),
//      new ReplacementRule(
//          Pattern.compile("(/)\\d+(/update)"),
//          "$1...$2"
//      ),
//
      // Конкретные пути: /users/12345 => /users/...
      new ReplacementRule(
          Pattern.compile("(/users/)\\d+"),
          "$1..."
      ),
      new ReplacementRule(
          Pattern.compile("(/products/)\\d+"),
          "$1..."
      )
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
  
  private static String normalizeSingleSegment(String segment) {
    // Project IDs
    if (segment.matches("proj-[a-zA-Z0-9]{3,}")) return "...";
    
    // Fold IDs
    if (segment.matches("fold-[a-zA-Z0-9]{3,}")) return "...";
    
    // Group IDs
    if (segment.matches("grp-[a-zA-Z0-9]{3,}")) return "...";
    
    // Service Account IDs
    if (segment.matches("sa_proj-[a-zA-Z0-9]{3,}-[a-zA-Z0-9]+")) return "...";
    
    // VTB с цифрами
    if (segment.matches("vtb\\d{4,}")) return "...";
    
    // Сегменты с 4+ цифрами
    if (segment.chars().filter(Character::isDigit).count() >= 4) return "...";
    
    // Длинные числовые ID
    if (segment.matches("\\d{3,}")) return "...";
    
    // Personal names
    if (segment.matches("[a-zA-Z]{3,}") && !segment.equals("personal"))
      return "...";
    
    return segment;
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