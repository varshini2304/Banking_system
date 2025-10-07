# Banking Management Application TODO

## Overview
Build a banking management application in pure Java with no database, featuring 4 roles: Admin, Manager, Employee (Teller), Customer.

## Key Features
- Admin: Unique username/password, create managers, change manager passwords.
- Manager: Created by admin, manage employees (create/delete, change passwords), view reports (customers handled, day reports, transactions, accounts created), approve transactions >50k.
- Employee: Created by manager (up to 10), ID format VBIE**, create customers (3 account types), manage deposits/withdrawals, view balances, log work activities.
- Customer: Created by employee, check balance, deposit (withdraw requires password verification).
- Default: 3 employees, 5 customers.
- Exceptions: InsufficientBalance, AccountNotFound.
- Collections: ArrayList, Set, Queue, HashSet.
- Modular code, separate classes/packages.
- Clean CLI.

## Current State
- Basic classes: Person, Address, Employee, Customer, Account (interface), Savings/Checking/CurrentAccount, AccountType enum.
- Incomplete InsufficientBalanceException.
- Basic CLI in Exe.java for creating/viewing employees/customers.

## Tasks
- [x] Complete InsufficientBalanceException and create AccountNotFoundException.
- [x] Create Admin class extending Person.
- [x] Create Manager class extending Person.
- [x] Create Transaction class for logging activities.
- [x] Create Approval class for pending approvals.
- [x] Create DataManager class to centralize data (ArrayList for lists, HashSet for unique items, Queue for approvals).
- [x] Modify Employee class: add employeeId (VBIE**), workLogs (List<Transaction>).
- [x] Modify Customer class: add password, account.
- [x] Implement authentication in DataManager.
- [x] Add default data initialization in DataManager.
- [x] Redesign Exe.java: role selection loop, auth, menus.
- [x] Implement Admin menu: create manager, change manager password.
- [x] Implement Manager menu: create/delete employee, change employee password, reports, approvals.
- [x] Implement Employee menu: create customer, manage accounts, transactions, view logs.
- [x] Implement Customer menu: check balance, deposit/withdraw.
- [x] Add transaction approval workflow (>50k via manager).
- [x] Ensure modularity and use of collections.
- [x] Test application and refine CLI.
