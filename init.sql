CREATE TABLE submission
(
    id        VARCHAR(50) PRIMARY KEY,
    score     INT,
    timestamp INT
);

CREATE INDEX idx_submission_timestamp ON submission (timestamp);

CREATE TABLE selection
(
    submission_id   VARCHAR(50),
    selection_value VARCHAR(10),
    PRIMARY KEY (submission_id, selection_value),
    FOREIGN KEY (submission_id) REFERENCES submission (id)
);