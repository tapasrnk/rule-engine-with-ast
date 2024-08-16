package com.tapas.rule_engine_with_ast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class RuleController {

    private final RuleService ruleService;
    private static final Logger logger = LoggerFactory.getLogger(RuleController.class);

    @Autowired
    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping("/create-rule")
    public ResponseEntity<String> createRule(@RequestBody String rule) {
        logger.info("Controller log" + rule);
        String id = ruleService.createRule(rule);
        logger.info("Controller return " + id);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/combine-rule")
    public ResponseEntity<String> combineRule(@RequestBody List<String> rules) {
        String id = ruleService.combineRule(rules);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/evaluate-rule")
    public ResponseEntity<Boolean> evaluateRule(@RequestBody EvaluateRuleRequest evaluateRuleRequest) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("age", evaluateRuleRequest.getAge());
        hashMap.put("department", evaluateRuleRequest.getDepartment());
        hashMap.put("salary", evaluateRuleRequest.getSalary());
        hashMap.put("experience", evaluateRuleRequest.getExperience());
        logger.info(evaluateRuleRequest.toString());
        Boolean result = ruleService.evaluateRule(evaluateRuleRequest.getId(), hashMap);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test sucessfull ");
    }
}
