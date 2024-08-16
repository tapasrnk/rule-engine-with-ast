package com.tapas.rule_engine_with_ast;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "node_data")
public class Node {
    @Id
    @Column(name = "node_id")
    private String node_id;

    @Column(name = "token")
    private String token;

    @Column(name = "left_node")
    private String left_node;

    @Column(name = "right_node")
    private String right_node;

    public Node() {
        String uuid = UUID.randomUUID().toString().substring(0, 5);
        node_id = uuid;
        token = null;
        left_node = null;
        right_node = null;
    }
    public Node(String token) {
        String uuid = UUID.randomUUID().toString().substring(0, 5);
        node_id = uuid;
        this.token = token;
        left_node = null;
        right_node = null;
    }

}
