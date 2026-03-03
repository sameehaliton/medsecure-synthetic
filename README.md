# MedSecure Synthetic Target

This is a synthetic "MedSecure backend" repository used as the CodeQL scanning target for the SecurityPilot demo. It contains intentional security vulnerabilities across Java and Python to exercise CodeQL's data-flow analysis across multiple files.

## Intentional Vulnerabilities

### Java (CWE-89, CWE-22, CWE-798)
- **CWE-89 SQL Injection**: `UserController` → `UserService` → `UserRepository` — user-supplied `id` parameter flows through three layers into raw JDBC string concatenation
- **CWE-22 Path Traversal**: `UserController` → `UserService` — user-supplied `path` parameter flows into `new File()` without canonicalization
- **CWE-798 Hardcoded Credentials**: Database password hardcoded in `application.properties`

### Python (CWE-89, CWE-79)
- **CWE-89 SQL Injection**: `patient_api.py` — `patient_id` from query param inserted into f-string SQL
- **CWE-79 XSS**: `patient_api.py` + `patient_detail.html` — unsanitized patient name passed to Jinja2 template with `| safe` filter

## Purpose

This repository is NOT production code. All vulnerabilities are intentional for SecurityPilot demonstration purposes. Do not use any patterns from this codebase in real applications.
