package org.project.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
@Service
public class PathNormalizerService {
  
  private  final List<ReplacementRule> RULES = Arrays.asList(
      new ReplacementRule(
          Pattern.compile("^[a-z]+://[^:]+:\\d+"),
          ""
      ),
      // /projects/proj-abc123/ => /projects/.../
      
//      new ReplacementRule(
//          Pattern.compile("(projects/)legacy-[a-zA-Z0-9_-]+(?=/|$)"),
//          "/{context_type}/{context_id}"
//      ),
      new ReplacementRule(
          Pattern.compile("(/projects/|/folders/)(fold|proj|legacy)" +
              "-[a-zA-Z0-9-]+(?=/|$)"),
          "/{context_type}/{context_id}"
      ),
      new ReplacementRule(
          Pattern.compile("(product-catalog/api/v2/)(graphs/|products/|\\{context_type\\}/\\{context_id\\}/products/|item_visual_templates/item_visual_template/cluster/)[a-zA-Z0-9_-]+(?=/|$)"),
          "$1$2{contaier_name}"
      ),
      
      new ReplacementRule(
          Pattern.compile("(product-catalog/api/v2/)(services/|actions/)[a-zA-Z0-9_-]+(?=/|$)"),
          "$1$2{contaier_action}"
      ),
//      // /fold-abc123/ => /.../
//      new ReplacementRule(
//          Pattern.compile("(/)fold-[a-zA-Z0-9]{3,}(?=/|$)"),
//          "/{context_type}/{context_id}"
//      ),
//
      // /grp-1213/asdf => /.../asdf
      new ReplacementRule(
          Pattern.compile("(/)grp-[a-zA-Z0-9]{3,}(?=/|$)"),
          "$1{group_id}"
      ),
      
      // /sa_proj-abc123-def => /...
      new ReplacementRule(
          Pattern.compile("(/)sa_proj-[a-zA-Z0-9]{3,}-[a-zA-Z0-9]+(?=/|$)"),
          "/{context_type}/{context_id}"
      ),
      new ReplacementRule(
          Pattern.compile("/organizations/"),
          "/{context_type}/"
      ),
      new ReplacementRule(
          Pattern.compile("(/items/)[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}" +
              "-[0-9a-f]{4}-[0-9a-f]{12}(?=/|$)"),
          "$1{item_id}"
      ),
      new ReplacementRule(
          Pattern.compile("(/orders/)[0-9a-f]{8}-[0-9a-f]{4}-" +
              "[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}(?=/|$)"),
          "$1{order_id}"
      ),
      
      // /personal/username/ => /personal/.../
      new ReplacementRule(
          Pattern.compile("(/personal/)[a-zA-Z]{3,}(?=/|$)"),
          "$1..."
      ),
      // URL-encoded paths
      new ReplacementRule(
          Pattern.compile("(/resource-manager)%2F(projects|folders|" +
              "organizations)%2F[a-zA-Z0-9_-]{3,}"),
          "$1/$2/..."
      ),
      
//      // Сегменты с 4+ цифрами: /4j5n6jjho3k8 => /...
//      new ReplacementRule(
//          Pattern.compile("(/)([^/]*(\\d[^/]*){5,}[^/]*)(?=/|$)"),
//          "$1..."
//      ),
      
      // /vtb12345 => /...
      new ReplacementRule(
          Pattern.compile("(/)vtb\\d{4,}(?=/|$)"),
          "$1{vtbUser}"
      ),
      
      // Числовые ID: /12345 => /...
      new ReplacementRule(
          Pattern.compile("(/)\\d+(?=/|$)"),
          "$1{NumberId}"
      ),
//
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
          Pattern.compile("(/products/)[a-zA-Z0-9-]+(?=/|$)"),
          "$1{product_id}"
      ),
        new ReplacementRule(
      Pattern.compile("(/product-catalog/api/v1/graphs/)[a-zA-Z0-9-]+(?=/|$)"),
          "$1{graphs_id}"
              )
  );
  
  public  String normalizePathWithDots(String path) {
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