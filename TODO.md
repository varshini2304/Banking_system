# Banking App Refactor to Distributed SOAP Architecture

## Completed
- Updated all monetary operations to use BigDecimal
- Created AdminService.java with JAX-WS annotations
- Created ManagerService.java with JAX-WS annotations
- Modified Exe.java to have mode selection: server or client

## Next Steps
1. [x] Generate client stubs using wsimport for AdminService and ManagerService
   - [x] Run AdminService to get WSDL: http://localhost:8080/AdminService?wsdl
   - [x] Run ManagerService to get WSDL: http://localhost:8081/ManagerService?wsdl
   - [x] Use wsimport to generate client classes

2. [x] Update client menus to call the services
   - [x] Admin client: call AdminService for createManager, changePassword
   - [ ] Manager client: call ManagerService for createEmployee, deleteEmployee, etc.
   - [ ] Employee client: call ManagerService for requestApproval on large withdrawals
   - [ ] Customer client: local operations, but for large withdrawals, call ManagerService

3. [x] Run servers in separate terminals
   - [x] java -cp src services.AdminService
   - [x] java -cp src services.ManagerService

4. [x] Run clients
   - [x] java -cp src exe.Exe (choose client mode)

5. [ ] Test the distributed system
   - [x] Start servers
   - [ ] Run multiple clients
   - [ ] Test approvals workflow
