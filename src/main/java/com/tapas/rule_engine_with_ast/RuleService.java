package com.tapas.rule_engine_with_ast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RuleService {

    private final NodeRepository nodeRepository;
    private final RuleUtility ruleUtility;
    private static final Logger logger = LoggerFactory.getLogger(RuleService.class);


    @Autowired
    public RuleService(NodeRepository nodeRepository, RuleUtility ruleUtility) {
        this.nodeRepository = nodeRepository;
        this.ruleUtility = ruleUtility;
    }


    String createRule(String rule) {
        logger.info("Service log " + rule);
        List<String> tokens = ruleUtility.getToken(rule);
        String rootNode = ruleUtility.buildTree(tokens);
        return rootNode;
    }

    String combineRule(List<String> rules) {
        int rulesSize = rules.size();
        String currentNode = rules.getFirst();
        for (int i = 1; i < rulesSize; i++) {
            Node node = new Node();
            node.setLeft_node(currentNode);
            node.setRight_node(rules.get(i));
            currentNode = node.getNode_id();
            nodeRepository.save(node);
        }
        return currentNode;
    }

    Boolean evaluateRule(String id, HashMap<String, String> hashMap) {
        Optional<Node> optionalNode = nodeRepository.findById(id);
        if (optionalNode.isPresent()) {
            Node node = optionalNode.get();
            String leftToken = null, rightToken = null;
            Boolean leftVal = null, rightVal = null;
            if (node.getLeft_node() != null) {
                Optional<Node> optionaLeftNode = nodeRepository.findById(node.getLeft_node());
                if (optionaLeftNode.isPresent()) {
                    leftToken = optionaLeftNode.get().getToken();
                    leftVal = evaluateRule(optionaLeftNode.get().getNode_id(), hashMap);
                }
            }
            if (node.getRight_node() != null) {
                Optional<Node> optionalRightNode = nodeRepository.findById(node.getRight_node());
                if (optionalRightNode.isPresent()) {
                    rightToken = optionalRightNode.get().getToken();
                    rightVal = evaluateRule(optionalRightNode.get().getNode_id(), hashMap);
                }
            }
            switch (node.getToken()) {
                case ">":
                    leftToken = hashMap.get(leftToken);
                    return leftToken.compareTo(rightToken) == 1;
                case "<":
                    leftToken = hashMap.get(leftToken);
                    return leftToken.compareTo(rightToken) == -1;
                case "=":
                    leftToken = hashMap.get(leftToken);
                    return leftToken.compareTo(rightToken) == 0;
                case "AND":
                    return Boolean.TRUE.equals(leftVal) && Boolean.TRUE.equals(rightVal);
                case "OR":
                    return Boolean.TRUE.equals(leftVal) || Boolean.TRUE.equals(rightVal);
            }
        } else {

        }
        return true;
    }
}
