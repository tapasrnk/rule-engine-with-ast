## Design 
###### Tables:
    1. Node
![image](https://github.com/user-attachments/assets/bca89018-1674-4da2-b2ec-c4076c3ec7e8)

## Test in local
1. Start my-sql database on port 3306.
    - One way is to start my-sql on docker container.
2. Create database name `rule_engine`
    ```
    create database rule_engine;
    ```
3. Set the admin username, admin password and your-api-key in application.properties.
    ```
    spring.datasource.username=root
    spring.datasource.password=password
    ```
4. Run the application on port 8080
5. Creating a rule by sending POST reqeust using the curl command
    ```
        curl --location 'http://localhost:8080/create-rule' \
        --header 'Content-Type: text/plain' \
        --data '"((age > 30 AND department = '\''Marketing'\'')) AND (salary > 20000 OR experience > 5)"'
    ```
6. Combine sending POST request using the curl command
    ```
    curl --location 'http://localhost:8080/combine-rule' \
    --header 'Content-Type: application/json' \
    --data '["2209c","306c5"]'
    ```
7. Evaluate rule by sending GET request using the curl command
    ```
    curl --location 'http://localhost:8080/evaluate-rule' \
    --header 'Content-Type: application/json' \
    --data '{
        "id":"ca9d7",
        "age":"10",
        "department":"test-department",
        "salary":"test-salary",
        "experience":"test-experience"
    }'
    ```
