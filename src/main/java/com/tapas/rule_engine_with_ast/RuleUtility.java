package com.tapas.rule_engine_with_ast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class RuleUtility {

    private final NodeRepository nodeRepository;
    private static final Logger logger = LoggerFactory.getLogger(RuleUtility.class);

    @Autowired
    public RuleUtility(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public List<String> getToken(String rule) {
        logger.info("Utility log" + rule);
        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < rule.length(); i++) {
            if (rule.charAt(i) == '(' || rule.charAt(i) == ')' || rule.charAt(i) == '>' || rule.charAt(i) == '<' || rule.charAt(i) == '=') {
                tokens.add(String.valueOf(rule.charAt(i)));
            } else if (rule.charAt(i) == ' ' || rule.charAt(i) == '\'') {
                continue;
            } else {
                String currentToken = "";
                while (i < rule.length() && (!(rule.charAt(i) == '(' || rule.charAt(i) == ')' || rule.charAt(i) == '>' || rule.charAt(i) == '<' || rule.charAt(i) == '=' || rule.charAt(i) == ' ' || rule.charAt(i) == '\'' || rule.charAt(i) == '\"'))) {
                    currentToken = currentToken + String.valueOf(rule.charAt(i));
                    i++;
                }
                tokens.add(currentToken);
            }
        }
        return tokens;
    }

    public String buildTree(List<String> tokens) {
        logger.info("Builder Tree tokens " + tokens.toString());
        List<String> postfix = new ArrayList<>();
        Stack<String> stk = new Stack<>();
        for (String s : tokens) {
            if (s.equals(" ") || s.equals("(")) continue;
            if (s.equals(">") || s.equals("<") || s.equals("=")) {
                stk.push(s);
            } else if (s.equals("AND") || s.equals("OR")) {
                if (!stk.isEmpty())postfix.add(stk.pop());
                stk.push(s);
            } else if (s.equals(")")) {
                while (!stk.isEmpty()) {
                    postfix.add(stk.pop());
                }
            } else {
                postfix.add(s);
            }
        }
        Stack<String> nodeStack = new Stack<>();
        logger.info("postfix" + postfix.toString());
        for (String s : postfix) {
            if (s.equals(">") || s.equals("<") || s.equals("=") || s.equals("AND") || s.equals("OR")) {
                String right = nodeStack.pop();
                String left = nodeStack.pop();
                Node node = new Node(s);
                node.setLeft_node(left);
                node.setRight_node(right);
                nodeRepository.save(node);
                String nodeId = node.getNode_id();
                nodeStack.push(nodeId);
            } else {
                Node node = new Node(s);
                nodeStack.push(node.getNode_id());
            }
        }
        return nodeStack.pop();
    }
}
