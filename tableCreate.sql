CREATE TABLE Users (
                       user_id NUMBER PRIMARY KEY,
                       username VARCHAR(255),
                       email VARCHAR(255) UNIQUE,
                       password VARCHAR(255),
                       total_assets NUMBER,
                       discount_rate DECIMAL(5, 2) NOT NULL,
                       gender VARCHAR(10),
                       age INT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Account (
                         account_id NUMBER PRIMARY KEY,
                         user_id NUMBER,
                         name VARCHAR(255),
                         account_number VARCHAR(255),
                         currency_code VARCHAR(10),
                         balance NUMBER,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (user_id) REFERENCES USERS(user_id)
);

CREATE TABLE Transfer_Detail (
                                transfer_detail_id NUMBER PRIMARY KEY,
                                account_id NUMBER,
                                remittance VARCHAR(255),
                                init_amount DECIMAL(20, 6),
                                last_amount DECIMAL(20, 6),
                                transfer_fee DECIMAL(20, 6),
                                status VARCHAR(50),
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (account_id) REFERENCES Account(account_id)
);

CREATE TABLE Bank_Account (
                             bank_account_id NUMBER PRIMARY KEY,
                             transfer_detail_id NUMBER,
                             bank_account_name VARCHAR(255),
                             currency_code VARCHAR(10),
                             balance NUMBER,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (transfer_detail_id) REFERENCES Transfer_Detail(transfer_detail_id)
);

CREATE TABLE Exchange_Rate (
                              exchange_rate_id NUMBER PRIMARY KEY,
                              base_currency VARCHAR(10),
                              target_currency VARCHAR(10),
                              base_exchange_rate DECIMAL(20, 2) NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Exchange_Detail (
                                exchange_detail_id NUMBER PRIMARY KEY,
                                exchange_rate_id NUMBER,
                                init_amount DECIMAL(20, 6),
                                final_amount DECIMAL(20, 6),
                                exchange_fee DECIMAL(20, 6),
                                status VARCHAR(50),
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (exchange_rate_id) REFERENCES Exchange_Rate(exchange_rate_id)
);



CREATE TABLE Revenue_Report (
                               revenue_report_id NUMBER PRIMARY KEY,
                               transfer_detail_id NUMBER,
                               exchange_detail_id NUMBER,
                               total_benefit NUMBER,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (transfer_detail_id) REFERENCES Transfer_Detail(transfer_detail_id),
                               FOREIGN KEY (exchange_detail_id) REFERENCES Exchange_Detail(exchange_detail_id)
);




